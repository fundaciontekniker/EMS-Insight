package es.tekniker.eefrmwrk.cep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.cep.listeners.UpdateListenerFactory;
import es.tekniker.eefrmwrk.cep.entity.CepEvent;
import es.tekniker.eefrmwrk.cep.entity.CepPrediction;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.commons.AlarmI;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.config.CepProperties;
import es.tekniker.eefrmwrk.database.nosql.HectorFactory;
import es.tekniker.eefrmwrk.database.sql.manage.AlarmManager;
import es.tekniker.eefrmwrk.database.sql.manage.CepEngineManager;
import es.tekniker.eefrmwrk.database.sql.manage.CepRuleManager;
import es.tekniker.eefrmwrk.database.sql.manage.HibernateUtil;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.Alarm;
import es.tekniker.eefrmwrk.database.sql.model.CepEngine;
import es.tekniker.eefrmwrk.database.sql.model.CepRule;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

/*
MODIFICADO 05/09/2014
Se han añadido la clase mensajes, para controlar el CEP
Se han añadido el atributo predict a los eventos para inicar valores predichos o reales
Reglas por defecto:
	-Todos los eventos llegan al stream allEvents, desde donde se reparten a cepEVENTS y cepPREDICT
	-si se tiene un variable predicha y se detecta una variable real con un timestamp superior, la variable predicha se borra en el CEP
	-si llega el mensaje con la palabra 'CLEAR', se borran todos los valores predichos //TODO refinar para borrar un nombre concreto
*/
public class CEP {

	private static final Log log = LogFactory.getLog(CEP.class);

	private EPRuntime cepRT;
	private EPServiceProvider cepSP;
	private Configuration cepConfig;
	
	final String cepEVENTS = "cepEVENTS";
	final String cepPREDICT = "cepPREDICT";
	final String cepPropFile = "CEP.properties";
	final String cepUrlProperty = "CEP_URL";
	final String cepRuleOK = "Ok";
	final String cepRuleFail = "Fail";

	private enum CepStatus {
		NOT_INITIALIZED, RUNNING, STOPPED, FINISHED
	};

	private CepStatus estado;

	public HashMap<String, Rule> listaReglas;

	private String cepURL;

	public CEP() throws BaseException {
		estado = CepStatus.NOT_INITIALIZED;
		listaReglas = new HashMap<String, Rule>();

		log.debug("Initializing CEP");
		cepConfig = new Configuration();

		//Nacho 2016/02/05		
		//if this code is commented then Rule does not load 
		cepConfig.addEventType(cepEVENTS,CepEvent.class.getName());
		cepConfig.addEventType(cepPREDICT+"Stream",CepPrediction.class.getName());
		//cepConfig.addEventType(cepPREDICT,CepPrediction.class.getName());
	
		try {
			new CepProperties();
		} catch (IOException e) {
			throw new BaseException("CEP_INIT0",
					"Could not load CEP Properties");
		}

		cepURL = CepProperties.getStringParam(cepUrlProperty);
		if (cepURL == null || cepURL.isEmpty())
			throw new BaseException("CEP_INIT1",
					"Could not read URL from CEP Properties");

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			
			CepEngine cEng = CepEngineManager.findByUrl(cepURL, session);// TODO
			cepSP = EPServiceProviderManager.getProvider(cEng.getCengDesc(),cepConfig);
			cepRT = cepSP.getEPRuntime();
			
			//Nacho 2016/02/05
			EPAdministrator cepAD=cepSP.getEPAdministrator();	
			cepAD.createEPL("CREATE WINDOW "+cepPREDICT+".win:keepall() AS SELECT * FROM "+cepPREDICT+"Stream");
			cepAD.createEPL("INSERT INTO "+cepPREDICT+" SELECT * FROM "+cepPREDICT+"Stream");			
			EPStatement eps =cepAD.createEPL("ON "+cepEVENTS+" as A DELETE FROM "+cepPREDICT+" AS B WHERE B.varName=A.varName AND B.timestamp<=A.timestamp");
			eps = cepAD.createEPL("ON "+cepPREDICT+" as A DELETE FROM "+cepPREDICT+" AS B WHERE B.varName=A.varName AND B.predictDate<A.predictDate");
			
			
			//TODO
			log.debug("Adding rules to CEP");
			for (CepRule cr : CepRuleManager.findByCepEngine(cEng.getCengId(),session))
				addRuleToEngine(cr.getCepName(), new Rule(cr));
			
			session.getTransaction().commit();
						
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		estado = CepStatus.RUNNING;
	}

	public void stop() {
		estado = CepStatus.STOPPED;
	}

	public void resume() {
		estado = CepStatus.RUNNING;
	}

	public void exit() throws BaseException {
		if (!estado.equals(CepStatus.FINISHED))
			cepSP.destroy();
		estado = CepStatus.FINISHED;
		try {
			HectorFactory.exit();
		} catch (Exception e) {
			throw new BaseException("CEP_EXIT0","Could not finalize Cassandra DB",e);					
		}
	}

	public CepStatus getStatus() {
		return estado;
	}

	public String getCepURL() {
		return cepURL;
	}

	/*public void addEvent(String varName, String value, long timestamp)
			throws BaseException {
		CepEvent cEv= new CepEvent();
		
		VarMetadata vmd = VarMetadataManager.findByName(varName);
		if (vmd == null) {
			log.error("Variable NOT found in database");
			throw new BaseException("CEP_ADDEVENT1","Variable NOT found in database");
		}
		cEv.setVarName(varName);
		cEv.setValue(toVmdType(vmd, value));
		cEv.setTimestamp(timestamp);

		if (estado.equals(CepStatus.RUNNING)) {
			log.debug("addEvent(" + varName + ": " + value + ")");
			cepRT.sendEvent(cEv);
		} else {
			log.warn("CEP engine is NOT running:[" + estado + "]");
			throw new BaseException("CEP_ADDEVENT0",
					"CEP engine is NOT running");
		}
	}*/
	
	public void addEvent(String varName, String value, long timestamp)
			throws BaseException {
		
		
		VarMetadata vmd = VarMetadataManager.findByName(varName);
		if (vmd == null) {
			log.error("Variable NOT found in database");
			throw new BaseException("CEP_ADDEVENT1",
					"Variable NOT found in database");
		}
		
		Object o =toVmdType(vmd, value);
		CepEvent cEv= new CepEvent(varName,o,true);
		cEv.setTimestamp(timestamp);

		if (estado.equals(CepStatus.RUNNING)) {
			log.debug("addEvent(" + varName + ": " + value + ")");
			cepRT.sendEvent(cEv);
		} else {
			log.warn("CEP engine is NOT running:[" + estado + "]");
			throw new BaseException("CEP_ADDEVENT0",
					"CEP engine is NOT running");
		}
	}
	
	public void addPredictionEvent(String varName, String value, long timestamp,long predDate)
			throws BaseException {
		VarMetadata vmd = VarMetadataManager.findByName(varName);
		if (vmd == null) {
			log.error("Variable NOT found in database");
			throw new BaseException("CEP_ADDEVENT1",
					"Variable NOT found in database");
		}
		
		Object o =toVmdType(vmd, value);
		CepPrediction cEv= new CepPrediction(varName,o,timestamp,predDate);

		if (estado.equals(CepStatus.RUNNING)) {
			log.debug("addPredictionEvent(" + varName + ": " + value + ")");
			cepRT.sendEvent(cEv);
		} else {
			log.warn("CEP engine is NOT running:[" + estado + "]");
			throw new BaseException("CEP_ADDEVENT0",
					"CEP engine is NOT running");
		}
	}

	public void addElectricEvent(GeneralElectricMeasure event)
			throws BaseException {
		if (estado.equals(CepStatus.RUNNING)) {
			log.debug("addElectricEvent(" + event.getDeviceId() + ": "
					+ new Date(event.getTimestamp()) + " )");
			cepRT.sendEvent(event);
		} else {
			log.warn("CEP engine is NOT running:[" + estado + "]");
			throw new BaseException("CEP_ADDELECTRICEVENT0",
					"CEP engine is NOT running");
		}
	}

	// ----RULES----------------
	private void addRuleToEngine(String ruleName, Rule cR) {
		log.debug("addRule(" + cR.getCepRule().getCepEpl() + ") --> "+cR.getCepRule().getCepListener());
		
		RuleEntity ruleEntity  = null;
		try {
			EPAdministrator cepAdm = cepSP.getEPAdministrator();
			cR.setStatement(cepAdm.createEPL(cR.getCepRule().getCepEpl()));
			
			ruleEntity = new RuleEntity();
			
			ruleEntity.setActiv(cR.getCepRule().getActiv());
			ruleEntity.setCepEngine(cR.getCepRule().getCepEngine());
			ruleEntity.setCepEpl(cR.getCepRule().getCepEpl());
			ruleEntity.setCepId(cR.getCepRule().getCepId());
			ruleEntity.setCepListener(cR.getCepRule().getCepListener());
			ruleEntity.setCepMessage(cR.getCepRule().getCepMessage());
			ruleEntity.setCepName(cR.getCepRule().getCepName());
			ruleEntity.setCepSeverity(cR.getCepRule().getCepSeverity());
			ruleEntity.setInfo(cR.getInfo());
			ruleEntity.setStatus(cR.getStatus());
			/*
			AbstractCEP_Listener uL = UpdateListenerFactory.create(
					cR.getCepRule().getCepListener(), 
					cR.getCepRule().getCepName(),
					cR.getCepRule().getCepEpl(), this);
			*/
			AbstractCEP_Listener uL = UpdateListenerFactory.create(ruleEntity);
			if(uL!=null)
				cR.getStatement().addListener(uL);
			cR.setStatus(cepRuleOK);
		} catch (BaseException e) {
			log.warn("Error creating EPL statement", e);
			cR.setStatus(cepRuleFail);
			cR.setInfo(e.getMessage());
		} catch (Exception e) {
			log.warn("Error creating EPL statement", e);
			cR.setStatus(cepRuleFail);
			cR.setInfo(e.getMessage());
		}
		listaReglas.put(cR.getCepRule().getCepName(), cR);
	}

	/*
	 * private void addRuleToEngine(String ruleName,Rule cR) {
	 * log.debug("addRule(" + cR.getCepRule().getCepEpl() + ")"); try {
	 * EPStatementObjectModel model = new EPStatementObjectModel(); SelectClause
	 * sc=SelectClause.create(); for (SelectField sf:selList){
	 * if(sf.alias!=null&&!sf.alias.isEmpty()){
	 * sc.addWithAsProvidedName(sf.field, sf.alias); }else{ sc.add(sf.field); }
	 * }
	 * model.setSelectClause(SelectClause.create().addWithAsProvidedName("value"
	 * , "DemoVar:activeIn").add("timestamp"));
	 * model.setFromClause(FromClause.create(FilterStream.create("cepEVENTS")));
	 * EPStatement epS=cepSP.getEPAdministrator().create(model);
	 * cR.setStatement(epS);
	 * 
	 * 
	 * cR.getStatement().addListener(UpdateListenerFactory.create(cR.getCepRule()
	 * .getCepListener(),cR.getCepRule().getCepName(),this));
	 * cR.setStatus(cepRuleOK); } catch (BaseException e) {
	 * log.warn("Error creating EPL statement", e); cR.setStatus(cepRuleFail);
	 * cR.setInfo(e.getMessage()); }
	 * listaReglas.put(cR.getCepRule().getCepName(), cR); }
	 */

	private Boolean deleteRuleFromEngine(String ruleName) {
		log.debug("deleteRule(" + ruleName + ")");
		if (!listaReglas.containsKey(ruleName))
			return false;
		else {
			Rule delRule = listaReglas.remove(ruleName);
			delRule.getStatement().destroy();
			return true;
		}
	}

	/**
	 * Comprueba si la regla contiene lenguaje EPL válido
	 * @throws BaseException 
	 */
	private boolean correctEPL(String EPL) throws BaseException {
		try {
			EPAdministrator cepAdm = cepSP.getEPAdministrator();
			cepAdm.createEPL(EPL);

			// correctSelect(EPL);
			return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			throw new BaseException("CEP_ADDRULE0",
					"Wrong EPL. (Check syntax, names, castings...)",e);
			
		}
	}

	private boolean correctListener(String listener, String epl)
			throws BaseException {
		RuleEntity ruleEntity = new RuleEntity();
		ruleEntity.setCepListener(listener);
		ruleEntity.setCepEpl(epl);
		
		//UpdateListenerFactory.create(listener, null, epl, null);
		UpdateListenerFactory.create(ruleEntity);
		return true;
	}

	// /-----------------------------------------------------

	public void addRule(String name, String rule, String listener,Long severity,String message)
			throws BaseException {
		log.debug("Adding Rule");

		if (!correctEPL(rule)) {
			log.error("Wrong EPL");
			throw new BaseException("CEP_ADDRULE0",
					"Wrong EPL. (Check syntax, names, castings...)");
		}

		if (!correctListener(listener, rule)) {
			log.error("Wrong Listener");
			throw new BaseException("CEP_ADDRULE1",
					"Wrong Listener Name (Should be one of the following...");
		}

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			CepEngine cEng = CepEngineManager.findByUrl(cepURL, session);
			if (cEng == null) {
				log.error(cepURL + " not found in DB");
				throw new BaseException("CEP_ADDRULE2", "Cep Engine for url '"
						+ cepURL + "' not found in DB");
			}

			CepRule cr = CepRuleManager.findByName(name, session);
			if (cr != null) {
				log.error("Name already exists");
				throw new BaseException("CEP_ADDRULE3", "Name already exists");
			}

			cr = new CepRule(cEng.getCengId(), name, rule, listener,severity,message,1);
			long crId = CepRuleManager.save(cr, session);
			cr.setCepId(crId);
			log.debug("Rule " + name + " added to DB");

			Rule r = new Rule(cr);
			addRuleToEngine(cr.getCepName(), r);
			log.debug("Rule " + name + " added to CEP engine");

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
	}

	public void deleteRule(String name) throws BaseException {
		log.debug("Deleting Rule");

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			CepRule cr = CepRuleManager.findByName(name, session);
			if (cr == null) {
				log.error("Rule name " + name + " does NOT exist");
				throw new BaseException("CEP_DELETE0","Rule name does NOT exist");
			}

			deleteRuleFromEngine(cr.getCepName());
			log.debug("Rule deleted from CEP");

			CepRuleManager.delete(cr.getCepId(), session);
			log.debug("Rule deleted from Database");

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
	}

	public void updateRule(String name, String rule, String listener,Long severity,String message)
			throws BaseException {

		log.debug("Updating Rule");
		if (!correctEPL(rule)) {
			log.error("Wrong EPL");
			throw new BaseException("CEP_UPDATE0",	"Wrong EPL. (Check syntax, names, castings...)");
		}

		if (!correctListener(listener, rule)) {
			log.error("Wrong Listener");
			throw new BaseException("CEP_UPDATE1","Wrong Listener Name (Should be one of the following...");					
		}

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			CepRule cR = CepRuleManager.findByName(name, session);
			if (cR == null) {
				log.error("Rule name NOT found in DB");
				throw new BaseException("CEP_UPDATE2","Rule name NOT found in DB");						
			}

			if (!listaReglas.containsKey(name)) {
				log.error("Rule NOT found in engine");
				throw new BaseException("CEP_UPDATE3","Rule NOT found in engine");
			}

			Rule r = listaReglas.get(name);
			cR.setCepEpl(rule);
			cR.setCepListener(listener);
			cR.setCepSeverity(severity);
			cR.setCepMessage(message);
			
			r.setCepRule(cR);

			deleteRuleFromEngine(name);
			addRuleToEngine(name, r);
			log.debug("Rule updated in CEP");

			CepRuleManager.update(cR, session);
			log.debug("Rule updated in DB");

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
	}

	// ----------------------------
	private Object toVmdType(VarMetadata vmd, String value)
			throws BaseException {
		Object rVal;
		String type = vmd.getVmdDigtype();
		if (type == null || type.isEmpty()) {
			type = VarMetadata.DigType_string;
		} else {
			type = type.trim();
		}
		try {
			if (type.equals(VarMetadata.DigType_boolean)) {
				rVal = new Boolean(value);
				log.debug("Converting value to boolean:" + value);
			} else if (type.equals(VarMetadata.DigType_integer)) {
				rVal = new Integer(value);
				log.debug("Converting value to integer:" + value);
			} else if (type.equals(VarMetadata.DigType_double)) {
				rVal = new Double(value);
				log.debug("Converting value to double:" + value);
			} else if (type.equals(VarMetadata.DigType_float)) {
				rVal = new Float(value);
				log.debug("Converting value to float:" + value);
			} else if (type.equals(VarMetadata.DigType_long)) {
				rVal = new Long(value);
				log.debug("Converting value to long:" + value);
			} else if (type.equals(VarMetadata.DigType_string)) {
				rVal = value;
				log.debug("Converting value to string:" + value);
			} else {
				log.error("Unknown type:" + type);
				throw new BaseException("CEP_TOVMDTYPE1", "Unknown type:"
						+ type);
			}
		} catch (BaseException e) {
			throw e;
		} catch (Exception e) {
			throw new BaseException("CEP_TOVMDTYPE0",
					"Exception converting value", e);
		}
		return rVal;
	}

	public ArrayList<AlarmI> getAlarms(String status) throws BaseException {
		ArrayList<AlarmI> aList = new ArrayList<AlarmI>();

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			for (Alarm a : AlarmManager.findByStatus(status,null,session)) {  
				AlarmI aI = new AlarmI();
				aI.setAlarmCode(a.getAlarmCode());
				aI.setAlarmState(a.getAlarmState());
				aI.setAlarmDesc(a.getAlarmDesc());
				aI.setAlarmDuedate(a.getAlarmDuedate());
				aI.setAlarmMessage(a.getAlarmMessage());
				aI.setAlarmType(a.getAlarmType());
				aI.setAlarmSeverity(a.getAlarmSeverity());
				aI.setAlarmTimespan(a.getAlarmTimespan().getTime());
				CepRule cr = CepRuleManager.find(a.getAlarmRule(), session);
				if (cr == null)
					aI.setAlarmRule(Alarm.RULE_NOT_FOUND);
				else
					aI.setAlarmRule(cr.getCepName());
				aList.add(aI);
				/*if (status == null || status.isEmpty()) {
					
				} else {
					if (a.getAlarmState().equals(status)) {
						aList.add(aI);
					}
				}*/
			}

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}

		return aList;

	}

	public void addAlarm(String alarmCode, String alarmType, String alarmDesc,
			String alarmMessage, long alarmTimespan, long alarmDuedate, long alarmSeverity) throws BaseException {

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			Alarm a = AlarmManager.findByCode(alarmCode, session);
			if (a != null) {
				throw new BaseException("CEP_ADDALARM1", "Alarm already exists");
			}

			a = new Alarm();
			a.setAlarmCode(alarmCode);
			a.setAlarmDesc(alarmDesc);
			a.setAlarmDuedate(alarmDuedate);
			a.setAlarmTimespan(new Date(alarmTimespan));
			a.setAlarmMessage(alarmMessage);
			a.setAlarmState(Alarm.STATE_PENDING);
			a.setAlarmType(alarmType);
			a.setAlarmSeverity(alarmSeverity);
			/*if (alarmRule == null || alarmRule.isEmpty()) {
				// Rule id 0 if empty or null
			} else {
				CepRule c = CepRuleManager.findByName(alarmRule, session);
				if (c == null) {
					throw new BaseException("CEP_ADDALARM2",
							"CepRule NOT found in database");
				}
				a.setAlarmRule(c.getCepId());
			}*/
			AlarmManager.save(a, session);

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}

	}

	public void updateAlarm(String alarmCode, String alarmType,
			String alarmDesc, String alarmMessage, long alarmTimespan,
			long alarmDuedate,long alarmSeverity,
			String alarmState) throws BaseException {

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			Alarm a = AlarmManager.findByCode(alarmCode, session);
			if (a == null) {
				throw new BaseException("CEP_UPDATEALARM1",
						"Alarm NOT found in database");
			}
			/*CepRule c = CepRuleManager.findByName(alarmRule, session);
			if (c == null) {
				throw new BaseException("CEP_ADDALARM2",
						"CepRule NOT found in database");
			}*/
			a.setAlarmDesc(alarmDesc);
			a.setAlarmDuedate(alarmDuedate);
			a.setAlarmTimespan(new Date(alarmTimespan));
			a.setAlarmMessage(alarmMessage);
			a.setAlarmState(alarmState);
			a.setAlarmType(alarmType);
			a.setAlarmSeverity(alarmSeverity);
			//a.setAlarmRule(c.getCepId());
			AlarmManager.update(a, session);

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}

	}

	public void deleteAlarm(String alarmCode) throws BaseException {

		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			Alarm a = AlarmManager.findByCode(alarmCode, session);
			if (a == null) {
				throw new BaseException("CEP_DELETEALARM1",
						"Alarm NOT found in database");
			}
			AlarmManager.delete(a.getAlarmId(), session);

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}

	}

	public static void main(String[] args) {

	try {
		CEP c = new CEP();
		/*try{c.deleteRule("ARULE");}catch(Exception e){}
		try{c.deleteRule("DRULE");}catch(Exception e){}
		c.addRule("ARULE",
				  "select * From pattern[every (timer:interval(10 sec) and not notEvent=cepEVENTS(varName regexp 'Var.*'))]",
				  "VR_LOG",
				  3l,
				  "NoEvent_10s");
		c.addRule("DRULE",
				  "select * from cepEVENTS(varName regexp 'Var.*')",
				  "VR_LOG",
				  3l,
				  "NoEvent_10s");*/
		
		/*
		try{c.deleteRule("ARULE");}catch(Exception e){}
		c.addRule("ARULE",
		   "select event from pattern[event = cepEVENTS(varName ='Var1' AND numValue>15 )->(timer:interval(15sec) and not b=cepEVENTS(varName = 'Var1' AND numValue<=15))]", 
		 // "select avg(numValue) AS avg, numValue From cepEVENTS(varName regexp 'Var.*').win:time(10 sec) having avg(numValue) >= 20",
		  "VR_LOG",
		  3l,
		  "NoEvent_10s");
		  
		  	*/
		/*c.addRule("ARULE",
				  "select avg(numValue) AS avg, numValue From cepEVENTS(varName regexp 'Var.*').win:time(10 sec) having avg(numValue) >= 20",
				  "VR_LOG",
				  3l,
				  "NoEvent_10s");	*/
		do {
			System.out.println("-------------------------------------");
			System.out.println("-  Pulsa x para salir               -");
			System.out.println("-------------------------------------");
			
			String input = readInput();
			long date= System.currentTimeMillis();
			if (input.equals("x"))
				break;
			
			/*
			else if (input.equals("1")) {
				c.addEvent("Var1", "10",date);
			}
			else if (input.equals("2")) {
				c.addEvent("Var1", "20", date);
			} 
			else if (input.equals("3")) {
				c.addEvent("Var1", "30", date);
			}else if (input.equals("q")) {
				c.addPredictionEvent("Var1", "10", date+10*1000,date);
			}
			else if (input.equals("w")) {
				c.addPredictionEvent("Var2", "20", date+10*1000,date);
			} 
			else if (input.equals("e")) {
				c.addPredictionEvent("Var3", "30", date+10*1000,date);
			}
			
			*/

		} while (true);
		
		c.exit();
	} catch (BaseException e) {
		e.printStackTrace();
	};
  }
	
	
	private static String readInput() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			
		String s = null;
		do
			try {
				s = stdin.readLine();
			} catch (IOException e) {
				System.err.println(e);
			}
		while ((s == null) || (s.length() == 0));
		return s;
	}
	
	
	/*
	
	public static void main(String[] args) {
		try {

			CEP cep = new CEP();

			//cep.deleteRule("Regex");
			/*cep.addRule(
					"Temp_log",
					"Select * From cepEVENTS(varName regexp 'Tibucon_.*_TEMP')",
					"VR_LOG");
			
			//cep.deleteRule("Temp");
			cep.addRule(
					"Temp_pred_log",
					"Select * From cepPREDICT(varName regexp 'Tibucon_.*_TEMP')",
					"VR_LOG");*/
			
			//cep.deleteRule("Hum Time");
			/*cep.addRule(
					"LogEmAll",
					"Select * From cepEVENTS",
					"VR_LOG");*/
			/*cep.addRule(
					"DaRule",
					"Select last_event From pattern[every last_event=cepEVENTS(varName regexp 'Tibucon_.*_Hum')->(timer:interval(20 min) and not b=cepEVENTS(varName regexp 'Tibucon_.*_Hum' AND last_event.varName=b.varName))]",
					"CEP_ALARM");*/
			/*cep.addRule(
					"Hum Time",
					"insert into cepEVENTS(vn,v) select 'avg_'+varName as vn, avg(numValue) as v From cepEVENTS.win:time(10 sec) group by varName",
					"VR_LOG");
			do {
				System.out.println("-------------------------------------");
				System.out.println("-  Pulsa x para salir               -");
				System.out.println("-------------------------------------");
				String input = readInput();
				if (input.equals("x"))
					break;
				else if (input.equals("1")) {
					for (Rule r : cep.listaReglas.values()) {
						System.out.println(r.getCepRule().getCepName() + ":"+ r.getCepRule().getCepEpl());
					}
				} else if (input.equals("2")) {
					for (AlarmI a : cep.getAlarms(null)) {
						System.out.println(a.getAlarmCode() + ": "	+ a.getAlarmMessage());
					}
				} else {
					String[] in_arr = input.split(" ");
					if(in_arr[0].equals("T")){try {String v="Tibucon_"+in_arr[1].trim()+"_Temp";
						cep.addEvent(v, in_arr[2].trim(),System.currentTimeMillis(),false);
					} catch (Exception e) {
						e.printStackTrace();
					}}
					if(in_arr[0].equals("TP")){try {String v="Tibucon_"+in_arr[1].trim()+"_Temp";
					cep.addEvent(v, in_arr[2].trim(),System.currentTimeMillis()+5000,true);
					} catch (Exception e) {
					e.printStackTrace();
					}}
					if(in_arr[0].equals("H")){try {String v="Tibucon_"+in_arr[1].trim()+"_Hum";
						cep.addEvent(v, in_arr[2].trim(),System.currentTimeMillis(),false);
					} catch (Exception e) {
						e.printStackTrace();
					}}
					if(in_arr[0].equals("L")){try {String v="Tibucon_"+in_arr[1].trim()+"_Lum";
						cep.addEvent(v, in_arr[2].trim(),System.currentTimeMillis(),false);
					} catch (Exception e) {
						e.printStackTrace();
					}}
					
				}

			} while (true);
			//cep.deleteRule("DaRule");
			//cep.deleteRule("Temp_pred_log");
			// cep.exit();
		} catch (Exception e) {

			e.printStackTrace();
			HectorFactory.exit();
		}
		
	}*/
}