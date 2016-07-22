package es.tekniker.eefrmwrk.cep.listeners;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.espertech.esper.client.EventBean;

import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.Rule;
import es.tekniker.eefrmwrk.cep.entity.CepEvent;
import es.tekniker.eefrmwrk.cep.entity.CepPrediction;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.manage.AlarmManager;
import es.tekniker.eefrmwrk.database.sql.manage.HibernateUtil;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.Alarm;
import es.tekniker.eefrmwrk.database.sql.model.CepRule;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

public class Alarm_Listener implements AbstractCEP_Listener {
	//private String ruleName;	
	//private CEP cep;
	private RuleEntity ruleEntity = null;
	
	private static final Log log = LogFactory.getLog(Alarm_Listener.class);
	
	//public Alarm_Listener(String ruleName, CEP cep)throws BaseException {
	public Alarm_Listener(RuleEntity rule)throws BaseException {
		log.debug("Alarm_Listener created");
		//this.ruleName = ruleName;
		//this.cep=cep;
		this.ruleEntity=rule;
		
		/*Alarm a = AlarmManager.findByCode(alarmCode);
		if (a == null) {
			throw new BaseException("AlarmListener_Create0","Alarm "+alarmCode+" NOT Found in database");
		}*/
	}

	//@Override
	public void update(EventBean[] newData, EventBean[] oldData) {
		log.debug("Updating with Alarm_Listener["+ ruleEntity.getCepName() + "]");		
		if (newData != null) {
			
			String value=null, 
			varName=null;
			try {
				try{
					if(newData[0].getUnderlying() instanceof CepEvent){
						
						CepEvent cE = (CepEvent) newData[0].getUnderlying();
						value=cE.getValue()+"";
						varName=cE.getVarName();
					}
					else if(newData[0].getUnderlying() instanceof CepPrediction){
						
						CepPrediction cE = (CepPrediction) newData[0].getUnderlying();
						value=cE.getValue()+"";
						varName=cE.getVarName();
					}
					else {
						try{
							CepEvent cE =(CepEvent)newData[0].get("event");
							value=cE.getValue()+"";
							varName=cE.getVarName();
						}	
						catch(Exception e){
						
						}
					}
				}catch(Exception e){
				
				}
				
				

				Session session = null;
				try {
					log.debug("Opening session");
					session = HibernateUtil.currentSession();
					session.beginTransaction();
					
					VarMetadata vmd = null;
					if(varName!=null){
						vmd = VarMetadataManager.findByName(varName, session);
						if (vmd == null) {
							throw new Exception("Variable "+varName+" NOT Found in database");
						}
					}
					
					//CepRule cR=cep.listaReglas.get(ruleName).getCepRule();
					Alarm a = new Alarm();
					a.setAlarmCode(ruleEntity.getCepName()+"_"+System.currentTimeMillis());
					a.setAlarmState(Alarm.STATE_PENDING);
					a.setAlarmSeverity(ruleEntity.getCepSeverity());
					a.setAlarmTimespan(new Date(System.currentTimeMillis()));
					a.setAlarmDuedate(0);
					a.setAlarmType("RULE ALARM");
					a.setAlarmRule(ruleEntity.getCepId());
					a.setAlarmDesc(ruleEntity.getCepEpl());			
					a.setAlarmMessage(alarmMsgGenerator(ruleEntity.getCepMessage(), vmd, value));
					AlarmManager.save(a, session);
					log.info("New Alarm :"+a.getAlarmMessage());
					session.getTransaction().commit();
					log.debug("Closing session");
					HibernateUtil.closeSession();
				} catch (BaseException e) {
					session.getTransaction().rollback();
					log.debug("Closing session");
					HibernateUtil.closeSession();
					throw e;
				}

			} catch (Exception e) {
				log.error("Error updating with Alarm_Listener:"+e.getMessage());
				//cep.listaReglas.get(ruleName).setStatus(Rule.status_UP_FAIL);
				//cep.listaReglas.get(ruleName).setInfo(e.getMessage());
			}
		}
	}

	/*
	 *	ALARMTYPE_VALURTHREESHOLD_TIME_THREESHOLD 
	 * 
	 */
	private String alarmMsgGenerator(String message, VarMetadata vmd, String value){
		log.info("alarmMsgGenerator("+value+")");
		String r=message;
		if (vmd!=null){
			r=r+ " ["+vmd.getVmdDescription()+": "+value +" "+vmd.getVmdMeasureunit()+"]";
		}
		return r;
	}
	
	//@Override
	public boolean checkRule(String epl) throws BaseException {
		return true;
	}

}
