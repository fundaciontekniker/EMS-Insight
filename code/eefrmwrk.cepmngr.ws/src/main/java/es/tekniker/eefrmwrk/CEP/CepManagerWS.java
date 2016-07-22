package es.tekniker.eefrmwrk.cep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.cepmngr.ICepManagerWS;
import es.tekniker.eefrmwrk.commons.AlarmI;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.RuleI;
import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.database.nosql.HectorFactory;
import es.tekniker.eefrmwrk.database.sql.model.Alarm;

public class CepManagerWS implements ICepManagerWS {

	private static final Log log = LogFactory.getLog(CepManagerWS.class);
	private CEP cep;

	public CepManagerWS() throws WSException {

		try {
			log.debug("Starting CEP WebService");
			cep = new CEP();
		} catch (Exception e) {
			log.error("Error starting CEP WebService");
			throw new WSException("CepManagerWS_CONSTRUCTOR0",
					"Error starting CEP WebService", e);
		}
	}

	@Override
	public void finalize() throws Throwable {
		log.info("Shutting down CEP WS");
		cep.exit();
		super.finalize();
	}

	/*
	 * public boolean addEvent(CepEventI event) throws WSException {
	 * log.debug("Adding event to CEP"); if (event == null) { throw new
	 * WSException("CepManagerWS_ADDEVENT1", "Event is null"); } try { CepEvent
	 * cEv = new CepEvent(event); cep.addEvent(cEv); return true; } catch
	 * (BaseException e) { log.error("Error adding event to CEP"); throw new
	 * WSException("CepManagerWS_ADDEVENT0", "Error adding event to CEP", e); }
	 * }
	 */
	@Override
	public boolean addEvent(String varName, String value, Long timestamp)
			throws WSException {
		log.debug("Adding event to CEP");
		if (varName == null || varName.isEmpty()) {
			log.error("Variable name can NOT be empty");
			throw new WSException("CepManagerWS_ADDEVENT1",
					"Variable name can NOT be empty");
		}

		// TODO SE PERMITEN VALORES EN BLANCO, PERO NO NULOS
		if (value == null) {
			log.error("Value can NOT be empty");
			throw new WSException("CepManagerWS_ADDEVENT2",
					"Value can NOT be null");
		}
		
		long t =System.currentTimeMillis();
		if(timestamp !=null){
			t=timestamp;
		}
		
		try {
			cep.addEvent(varName, value, t);
			return true;
		} catch (BaseException e) {
			log.error("Error adding event to CEP");
			throw new WSException("CepManagerWS_ADDEVENT0",
					"Error adding event to CEP", e);
		}
	}


	@Override
	public boolean addPredictionEvent(String varName, String value,
			Long timestamp, Long predictDate) throws WSException {
		log.debug("Adding prediction event to CEP");
		if (varName == null || varName.isEmpty()) {
			log.error("Variable name can NOT be empty");
			throw new WSException("CepManagerWS_ADDEVENT1",
					"Variable name can NOT be empty");
		}

		// TODO SE PERMITEN VALORES EN BLANCO, PERO NO NULOS
		if (value == null) {
			log.error("Value can NOT be empty");
			throw new WSException("CepManagerWS_ADDEVENT2",
					"Value can NOT be null");
		}
		
		long t =System.currentTimeMillis();
		if(timestamp !=null){
			t=timestamp;
		}
		
		try {
			cep.addPredictionEvent(varName, value, t,predictDate);
			return true;
		} catch (BaseException e) {
			log.error("Error adding event to CEP");
			throw new WSException("CepManagerWS_ADDEVENT0",
					"Error adding event to CEP", e);
		}
	}

	/*public boolean addElectricEvent(GeneralElectricMeasureI event)
			throws WSException {
		log.debug("Adding energy event to CEP from device");
		if (event == null) {
			throw new WSException("CepManagerWS_ADDELECTRICEVENT1",
					"Event is null");
		}
		try {
			// TODO Hay que controlar si los elementos de event son NULL...
			// TODO Cambiar parametros? addElectrircEvent(gemI)
			// TODO -> addElectricEvent(dev,reac1,reac2,qReac1
			// qReac2,timestamp,...)
			// TODO Comprobar en constructor? GeneralElectricMeasure(event)
			GeneralElectricMeasure gem = new GeneralElectricMeasure(event);
			cep.addElectricEvent(gem);
			return true;
		} catch (BaseException e) {
			log.error("Error adding energy event to CEP");
			throw new WSException("CepManagerWS_ADDELECTRICEVENT0",
					"Error adding GEM event to CEP", e);
		}
	}

	@Override
	public boolean addElectricEvent(String devName, String CUPid,
			long timestamp, String activeIn, String activeOut,
			String qActiveIn, String qActiveOut, String reactive1,
			String reactive2, String reactive3, String reactive4,
			String qReactive1, String qReactive2, String qReactive3,
			String qReactive4, String gotten) throws WSException {
		log.debug("Adding energy event to CEP from device");
		if (devName == null || devName.isEmpty()) {
			throw new WSException("CepManagerWS_ADDELECTRICEVENT1",
					"Device name can NOT be empty");
		}
		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				throw new WSException("CepManagerWS_ADDELECTRICEVENT2",
						"Device NOT found in database");
			}
			GeneralElectricMeasure gem = new GeneralElectricMeasure();
			gem.setDeviceId(d.getDevId());
			gem.setCUPid(CUPid);
			gem.setTimestamp(timestamp);
			gem.setGotten(gotten);
			gem.setActiveOut(toFloat("activeIn", activeIn));
			gem.setActiveOut(toFloat("activeOut", activeOut));
			gem.setActiveOut(toFloat("qActiveIn", qActiveIn));
			gem.setActiveOut(toFloat("qActiveOut", qActiveOut));
			gem.setActiveOut(toFloat("reactive1", reactive1));
			gem.setActiveOut(toFloat("qReactive1", qReactive1));
			gem.setActiveOut(toFloat("reactive2", reactive2));
			gem.setActiveOut(toFloat("qReactive2", qReactive2));
			gem.setActiveOut(toFloat("reactive3", reactive3));
			gem.setActiveOut(toFloat("qReactive3", qReactive3));
			gem.setActiveOut(toFloat("reactive4", reactive4));
			gem.setActiveOut(toFloat("qReactive4", qReactive4));

			cep.addElectricEvent(gem);
			return true;
		} catch (BaseException e) {
			log.error("Error adding energy event to CEP");
			throw new WSException("CepManagerWS_ADDELECTRICEVENT0",
					"Error adding GEM event to CEP", e);
		}
	}

	private Float toFloat(String field, String s) throws BaseException {
		try {
			if (s == null || s.isEmpty())
				return null;
			Float f = new Float(s);
			return f;
		} catch (Exception e) {
			throw new BaseException("CepManagerWS_" + field + "_TOFLOAT", s
					+ " is NOT a valid FLOAT number");
		}
	}*/

	public String addRule(String name, String rule, String listener,Long severity,String message)
			throws WSException {
		if (name == null || name.isEmpty()) {
			throw new WSException("CepManagerWS_ADDRULE1",
					"Rule name can NOT be empty");
		}
		if (rule == null || rule.isEmpty()) {
			throw new WSException("CepManagerWS_ADDRULE2",
					"Rule EPL can NOT be empty");
		}
		if (listener == null || listener.isEmpty()) {
			throw new WSException("CepManagerWS_ADDRULE3",
					"Rule listener can NOT be empty");
		}
		try {
			cep.addRule(name, rule, listener,severity,message);
			return name;
		} catch (BaseException e) {
			log.error("Error adding rule", e);
			throw new WSException("CepManagerWS_ADDRULE0", "Error adding rule",
					e);
		}
	}

	public String deleteRule(String name) throws WSException {
		if (name == null || name.isEmpty()) {
			throw new WSException("CepManagerWS_DELETERULE1",
					"Rule Name is null");
		}
		try {
			cep.deleteRule(name);
			return "OK";
		} catch (BaseException e) {
			log.error("Error deleting rule", e);
			throw new WSException("CepManagerWS_DELETERULE0",
					"Error deleting rule", e);
		}
	}

	public String updateRule(String name, String rule, String listener,Long severity,String message)
			throws WSException {
		try {
			if (name == null || name.isEmpty()) {
				throw new WSException("CepManagerWS_UPDATERULE1",
						"Rule name can NOT be empty");
			}
			if (rule == null || rule.isEmpty()) {
				throw new WSException("CepManagerWS_UPDATERULE2",
						"Rule EPL can NOT be empty");
			}
			if (listener == null || listener.isEmpty()) {
				throw new WSException("CepManagerWS_UPDATERULE3",
						"Rule listener can NOT be empty");
			}
			cep.updateRule(name, rule, listener,severity,message);
			return "OK";

		} catch (Exception e) {
			log.error("Error updating rule", e);
			throw new WSException("CepManagerWS_UPDATERULE0",
					"Error updating rule", e);

		}
	}

	public List<RuleI> getRules() throws WSException {
		log.debug("Getting active rules");
		if (cep.listaReglas == null || cep.listaReglas.size() == 0) {
			throw new WSException("CepManagerWS_GETRULES0",
					"There are NO rules in CEP engine");
		}
		List<RuleI> result = new ArrayList<RuleI>();
		for (Rule r : cep.listaReglas.values()) {
			RuleI rI = new RuleI();
			rI.setRuleName(r.getCepRule().getCepName());
			if (r.getStatement() != null)
				rI.setRuleEPL(r.getStatement().getText());
			else
				rI.setRuleEPL(r.getCepRule().getCepEpl());
			rI.setRuleListener(r.getCepRule().getCepListener());
			rI.setRuleStatus(r.getStatus());
			rI.setRuleSeverity(r.getCepRule().getCepSeverity());
			rI.setRuleMessage(r.getCepRule().getCepMessage());
			rI.setRuleInfo(r.getInfo());
			result.add(rI);
		}
		return result;
	}

	public String getStatus() throws WSException {
		log.debug("Getting cep status");
		if (cep != null)
			return cep.getStatus() + "";
		else
			throw new WSException("CepManagerWS_GETSTATUS0",
					"CEP is not instantiated");
	}

	public List<AlarmI> getAlarms(String status) throws WSException {

		if (!validStatus(status)) {
			log.error("Invalid alarm status:" + status);
			throw new WSException("CepManagerWS_GETALARMS1",
					"Alarm status is NOT valid");
		}
		try {
			return cep.getAlarms(status);
		} catch (Exception e) {
			throw new WSException("CepManagerWS_GETALARMS0",
					"Error getting alarms");
		}
	}

	public String addAlarm(String alarmCode, String alarmType,
			String alarmDesc, String alarmMessage, long alarmTimespan,
			long alarmDuedate, long alarmSeverity)
			throws WSException {
		if (alarmCode == null || alarmCode.isEmpty()) {
			throw new WSException("CepManagerWS_ADDALARM1",
					"Alarm name can NOT be empty");
		}
		if (alarmMessage == null || alarmMessage.isEmpty()) {
			throw new WSException("CepManagerWS_ADDALARM2",
					"Alarm message name can NOT be empty");
		}
		if (alarmType == null || alarmType.isEmpty()) {
			throw new WSException("CepManagerWS_ADDALARM3",
					"Alarm type can NOT be empty");
		}
		try {
			cep.addAlarm(alarmCode, alarmType, alarmDesc, alarmMessage,
					alarmTimespan, alarmDuedate, alarmSeverity);
		} catch (Exception e) {
			throw new WSException("CepManagerWS_ADDALARM0",
					"Exception adding alarm", e);
		}
		return alarmCode;
	}

	public String updateAlarm(String alarmCode, String alarmType,
			String alarmDesc, String alarmMessage, long alarmTimespan,
			long alarmDuedate, long alarmSeverity,
			String alarmState) throws WSException {
		if (alarmCode == null || alarmCode.isEmpty()) {
			throw new WSException("CepManagerWS_UPDATEALARM1",
					"Alarm name can NOT be empty");
		}
		if (alarmMessage == null || alarmMessage.isEmpty()) {
			throw new WSException("CepManagerWS_UPDATEALARM2",
					"Alarm message name can NOT be empty");
		}
		if (alarmType == null || alarmType.isEmpty()) {
			throw new WSException("CepManagerWS_UPDATEALARM3",
					"Alarm type can NOT be empty");
		}
		if (alarmState == null || alarmState.isEmpty()) {
			throw new WSException("CepManagerWS_UPDATEALARM4",
					"Alarm state can NOT be empty");
		}
		if (!validStatus(alarmState)) {
			log.error("Invalid alarm state:" + alarmState);
			throw new WSException("CepManagerWS_UPDATEALARM5",
					"Alarm state is NOT valid");
		}
		try {
			cep.updateAlarm(alarmCode, alarmType, alarmDesc, alarmMessage,
					alarmTimespan, alarmDuedate, alarmSeverity,
					alarmState);
		} catch (Exception e) {
			throw new WSException("CepManagerWS_UPDATEALARM0",
					"Exception updating alarm", e);
		}
		return "OK";
	}

	public String deleteAlarm(String alarmCode) throws WSException {
		if (alarmCode == null || alarmCode.isEmpty()) {
			throw new WSException("CepManagerWS_DELETEALARM1",
					"Alarm name can NOT be empty");
		}
		try {
			cep.deleteAlarm(alarmCode);
		} catch (Exception e) {
			throw new WSException("CepManagerWS_DELETEALARM0",
					"Exception deleting alarm", e);
		}
		return "OK";
	}

	private boolean validStatus(String status) {
		return (status == null || status.isEmpty()
				|| status.equals(Alarm.STATE_PENDING)
				|| status.equals(Alarm.STATE_CHECKED)
				|| status.equals(Alarm.STATE_PROCESSING) || status
					.equals(Alarm.STATE_CANCELLED));
	}

	static CepManagerWS cepWS;
	public static void main(String[] args) {
		try {
			long t1 = System.currentTimeMillis();

			long t2 = System.currentTimeMillis()+5000;

			long t3 = System.currentTimeMillis()+10000;

			long t4 = System.currentTimeMillis()+15000;
			
			cepWS = new CepManagerWS();
			
			
			/*startSimulation();
*/
			do {
				System.out.println("-------------------------------------");
				System.out.println("-  Pulsa x para salir               -");
				System.out.println("-------------------------------------");
				String input = readInput();
				if (input.equals("x"))
					break;
				else if (input.equals("1")) {
					for (RuleI r : cepWS.getRules()) {
						System.out.println(r.getRuleEPL());
					}
				} else if (input.equals("2")) {
					for (AlarmI a : cepWS.getAlarms(null)) {
						System.out.println(a.getAlarmCode() + ": "
								+ a.getAlarmMessage());
					}
				}else if (input.equals("q")) {
					long t = System.currentTimeMillis();
					int var=(int) (Math.random()*(4-1)+1);
					int ki =(int) (Math.random()*(9100-8800)+8800);
					cepWS.addPredictionEvent("var"+var, ki+"", t,t1);
				}else if (input.equals("w")) {
					long t = System.currentTimeMillis();
					int var=(int) (Math.random()*(4-1)+1);
					int ki =(int) (Math.random()*(9100-8800)+8800);
					cepWS.addPredictionEvent("var"+var, ki+"", t,t2);
				}else if (input.equals("e")) {
					long t = System.currentTimeMillis();
					int var=(int) (Math.random()*(4-1)+1);
					int ki =(int) (Math.random()*(9100-8800)+8800);
					cepWS.addPredictionEvent("var"+var, ki+"", t,t3);
				}else if (input.equals("r")) {
					long t = System.currentTimeMillis();
					int var=(int) (Math.random()*(4-1)+1);
					int ki =(int) (Math.random()*(9100-8800)+8800);
					cepWS.addPredictionEvent("var"+var, ki+"", t,t4);
				}
				else {
					long t = System.currentTimeMillis();
					int var=(int) (Math.random()*(4-1)+1);
					int ki =(int) (Math.random()*(9100-8800)+8800);
					cepWS.addEvent("var"+var, ki+"", t);
				}
			} while (true);
			
			

			/*stopSimulation();*/
		} catch (Exception e) {
			e.printStackTrace();		
		}
		HectorFactory.exit();
	}

	private static String readInput() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String s = null;
		do
			try {
				s = stdin.readLine();
			} catch (IOException e) {
				System.out.println(e);
			}
		while ((s == null) || (s.length() == 0));
		return s;
	}

	
	
	
	private static boolean simulate = false;
	private static final ScheduledExecutorService simulator = Executors.newScheduledThreadPool(100);
			

	@Override
	public List<AlarmI> getVariableAlarms(String varName) throws WSException {
		List<AlarmI> filterList = new ArrayList<AlarmI>();
		if (varName == null || varName.isEmpty()) {
			throw new WSException("CepManagerWS_GETVARIABLEALARMS1",
					"Variable name must be provided");
		}
		try {
			List<AlarmI> alarms = cep.getAlarms(null);
			for (AlarmI a : alarms) {
				String[] args = a.getAlarmType().split("_");
				if (args[0].equals(Alarm.UnderType) || args[0].equals(Alarm.OverType))
						if(args[2].matches(varName)) 
					filterList.add(a);
			}
		} catch (Exception e) {
			throw new WSException("CepManagerWS_GETALARMS0",
					"Error getting alarms");
		}
		// TODO Auto-generated method stub
		return filterList;
	}
}

