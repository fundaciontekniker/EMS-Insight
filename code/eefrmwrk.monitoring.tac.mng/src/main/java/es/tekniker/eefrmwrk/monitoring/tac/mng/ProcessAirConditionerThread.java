package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.cepmngr.client.CepClient;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event;


public class ProcessAirConditionerThread extends Thread{

	private static Log log =LogFactory.getLog(ProcessTemperatureThread.class);
	private boolean active =true;
	
	private long loopTime;
	private CepClient cep = null;
	private String stringTacAirConditioningFloor2Name = null;
	private List<String> listAirConditioningFloor2ZoneId = null;
	private String tacAirConditioningFloor2Type = null;
	private TreeMap<Integer, ArrayList<TimeInterval>> mapAirConditioner = null;
	
	private Long tacAirConditioningFloor2Id = null;
	private Event eventAirConditioner =  null;
	private boolean boolAirConditionerStateOn = false ;
	private long airConditionerStateOnTime = 0;
	private int nUTARunning = 0;
	private int nUTAAutomatic =0;
	private static String splitHour =":";
	private static String splitPeriod="~";
	private static String characterRangeValues="\\^";

	
	public ProcessAirConditionerThread(String stringTacAirConditioningFloor2Name, String tacAirConditioningFloor2Type, List<String> listAirConditioningFloor2ZoneId , CepClient cep, long loopTimeProcess, long tacAirConditioningFloor2Id){
		this.loopTime=loopTimeProcess;
		this.cep=cep;		
		this.stringTacAirConditioningFloor2Name = stringTacAirConditioningFloor2Name;
		this.listAirConditioningFloor2ZoneId = listAirConditioningFloor2ZoneId;
		this.tacAirConditioningFloor2Type = tacAirConditioningFloor2Type;
		this.tacAirConditioningFloor2Id = tacAirConditioningFloor2Id;

		initMapAirConditionerState();
	}	
	
	private void initMapAirConditionerState(){		
		//define seven days of week
		mapAirConditioner = new TreeMap<Integer,ArrayList<TimeInterval>>();
		
		mapAirConditioner.put(0, new ArrayList<TimeInterval>());
		mapAirConditioner.put(1, new ArrayList<TimeInterval>());
		mapAirConditioner.put(2, new ArrayList<TimeInterval>());
		mapAirConditioner.put(3, new ArrayList<TimeInterval>());
		mapAirConditioner.put(4, new ArrayList<TimeInterval>());
		mapAirConditioner.put(5, new ArrayList<TimeInterval>());
		mapAirConditioner.put(6, new ArrayList<TimeInterval>());
				
	}
	
	
	@Override
	public void run() {
		log.debug("ProcessAirConditionerThread START");
		while(active){			
			log.debug("ProcessAirConditionerThread while loop inside");
			try {				
				//do work
				processActivationAirConditioner();
			    Thread.sleep(loopTime);
			    
				
			} catch (InterruptedException e) {
				//no será interrumpido,  excepto por el usuario
				log.warn("ProcessAirConditionerThread interrupted ", e);
			}
		}
		log.debug("ProcessAirConditionerThread active is false");	
	}

	
	public boolean processActivationAirConditioner(){
		boolean boolOK =  true;
		log.debug("processActivationAirConditioner START");
		
		setnUTAAutomatic(processTacDataAirConditionerManualIsStateStoppedRunning());
		
		if(getnUTAAutomatic() == 0){
			log.debug("processActivationAirConditioner no UTA in automatic mode");
			
			boolOK = sendCEPAirConditionerState(getnUTARunning());
			
		}else{
			log.debug("processActivationAirConditioner nUTAAutomatic = " + getnUTAAutomatic());
		

			boolOK = processTacDataAirConditioner();
			
		}
		
		log.debug("processActivationAirConditioner END");
		
		return boolOK;
	}
	
	private boolean processTacDataAirConditioner(){
		boolean boolOK =  false;
		Event eventAirConditionerLastValue = null;
		int nUTASActive = getnUTARunning();
		
		log.debug("processTacDataAirConditioner : START ");
		
		try {
			if (this.listAirConditioningFloor2ZoneId !=null ){			
				log.debug("processTacDataAirConditioner : listAirConditioningFloor2ZoneId is  not Null. Num " + listAirConditioningFloor2ZoneId.size());
								
				//GET AIRCONDITIONER CALENDAR					
				if(tacAirConditioningFloor2Id != null && tacAirConditioningFloor2Type != null){
					eventAirConditionerLastValue = getAirConditioningCalendar(tacAirConditioningFloor2Id, tacAirConditioningFloor2Type);						 
					if(eventAirConditionerLastValue !=null){
						
						log.debug("processTacDataAirConditioner getAirConditioningCalendar LastValue  old : " + eventAirConditionerLastValue.getObjectOldValue() + " new : " + eventAirConditionerLastValue.getObjectNewValue());
						
						if(eventAirConditioner == null || eventAirConditionerLastValue.getEventId()!=eventAirConditioner.getEventId() ){							
							log.debug("processTacDataAirConditioner eventAirConditionerLastValue is NOT equals to eventAirConditioner");
							eventAirConditioner = eventAirConditionerLastValue;
							log.debug("parsing eventAirConditioner");
							parseAirConditionerConfiguration(eventAirConditioner);							
							printMapAirConditioner();														
						}
						airConditionerStateOnTime = eventAirConditioner.getEventTime().getTime();		
						boolAirConditionerStateOn= isAirConditionerConfigurationStateOnNow();
						if (boolAirConditionerStateOn) nUTASActive = getnUTARunning() + getnUTAAutomatic();
						log.debug("processActivationAirConditioner Active UTAS: " + nUTASActive);
						sendCEPAirConditionerState(nUTASActive);
						boolOK=true;
					 }else
						log.error("processTacDataAirConditioner : eventAirConditioner is NULL ");
				}else{
					log.error("processTacDataAirConditioner tacAirConditioningFloor2Id or tacAirConditioningFloor2Type has no values. Review configuration file"); 
				}
			
			}else{
				log.error("processTacDataAirConditioner listAirConditioningFloor2ZoneId has no values. Review configuration file");
			}
			
		} catch (Exception e1) {
	    	log.error("processTacDataAirConditioner EXCEPTION " + e1.getMessage());
			e1.printStackTrace();
		}
		log.debug("processTacDataAirConditioner : END ");
		
		return boolOK;
	}

	
	private int processTacDataAirConditionerManualIsStateStoppedRunning(){
		int id =0;
		Event eventManual = null;
		int UTAState = TacMng.AIRCONDITIONER_STATE_STOPPED;
		int nUTAAutomatic = 0;
		setnUTARunning(0);
		
		log.debug("processTacDataAirConditionerManualIsStateStoppedRunning : START ");
		
		try {
			if (this.listAirConditioningFloor2ZoneId !=null ){			
				log.debug("processTacDataAirConditionerManualIsStateStoppedRunning : listAirConditioningFloor2ZoneId is  not Null. Num " + listAirConditioningFloor2ZoneId.size());
				// FJD 26/02/2016 We have to calculate the percentaje of UTAs running in automatic/manual
				//GET MANUAL ACTIVATION/DEACTIVATION			        	
				for(String stringId: listAirConditioningFloor2ZoneId){
					id= Short.valueOf(stringId);				
					eventManual = getEventByIdLast(id);
					
					if(eventManual !=null){
						log.debug("processTacDataAirConditionerManualIsStateStoppedRunning : eventManual is NOT null for id " + id + " New value :" + eventManual.getObjectNewValue());
						UTAState = Integer.valueOf(eventManual.getObjectNewValue().toString());
						airConditionerStateOnTime =eventManual.getEventTime().getTime();
						if(UTAState == TacMng.AIRCONDITIONER_STATE_STOPPED ){
							// No sum
						}
						else if (UTAState == TacMng.AIRCONDITIONER_STATE_RUNNING ){
							setnUTARunning(getnUTARunning()+1);						
						}
						else if (UTAState == TacMng.AIRCONDITIONER_STATE_AUTOMATIC ){
							nUTAAutomatic = nUTAAutomatic+1;
						}
						else {
							log.error("processTacDataAirConditionerManualIsStateStoppedRunning : eventManual for id " + id + " is not reconigzed state is " + eventManual.getObjectNewValue().toString());
							
						}
					}else
						log.debug("processTacDataAirConditionerManualIsStateStoppedRunning : eventManual is null for id " + id);
				}		
			 }else
				log.error("processTacDataAirConditionerManualIsStateStoppedRunning : eventAirConditioner is NULL ");		
			
		} catch (Exception e1) {
	    	log.error("processTacDataAirConditionerManualIsStateStoppedRunning EXCEPTION " + e1.getMessage());
			e1.printStackTrace();
		}
		log.debug("processTacDataAirConditionerManualIsStateStoppedRunning : END ");
		return nUTAAutomatic; 
	}
	
	private boolean processTacDataAirConditionerManualByState(short state){
		boolean airConditionerStateOn =  false;
		short id =0;
		Event eventManual = null;
		
		log.debug("processTacDataAirConditionerManualState : START ");
		
		try {
			if (this.listAirConditioningFloor2ZoneId !=null ){			
				log.debug("processTacDataAirConditionerManualState : listAirConditioningFloor2ZoneId is  not Null. Num " + listAirConditioningFloor2ZoneId.size());
						 
				//GET MANUAL ACTIVATION/DEACTIVATION			        	
				for(String stringId: listAirConditioningFloor2ZoneId){
					id= Short.valueOf(stringId);				
					eventManual = getEventByIdLast(id);
					
					if(eventManual !=null){
						log.debug("processTacDataAirConditionerManualState : eventManual is NOT null for id " + id + " New value :" + eventManual.getObjectNewValue());
						
						if(Integer.valueOf(eventManual.getObjectNewValue().toString())== state){
							log.debug("processTacDataAirConditionerManualState : eventManual state is equals as searched " + state);
							airConditionerStateOn = true;
							airConditionerStateOnTime =eventManual.getEventTime().getTime();
							break;
						}						
					}else
						log.debug("processTacDataAirConditionerManualState : eventManual is null for id " + id);
				}		
			 }else
				log.error("processTacDataAirConditionerManualState : eventAirConditioner is NULL ");		
			
		} catch (Exception e1) {
	    	log.error("processTacDataAirConditionerManualState EXCEPTION " + e1.getMessage());
			e1.printStackTrace();
		}
		log.debug("processTacDataAirConditionerManualState : END ");
		return airConditionerStateOn;
	}
	
	private boolean isAirConditionerConfigurationStateOnNow() {
		boolean boolOK =false;
		
		ArrayList<TimeInterval> ranges = null;
		Iterator<TimeInterval> rangesIterator = null;
		TimeInterval timeInterval = null;
		
		Calendar now = Calendar.getInstance();		
		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = now.get(Calendar.HOUR_OF_DAY);
		int minutes = now.get(Calendar.MINUTE);
		log.debug("Now day of week: " +dayOfWeek + " hourOfDay: " + hourOfDay  + " minutes: " +minutes ); 
		
		//CALENDAR RETURN THE FOLLOWING VALUES 1 = SUNDAY 2= MONDAY 3=TUESDAY 4=WEDNESDAY 5=THURSDAY 6=FRIDAY 7=SATURDAY
		//MASK IS DEFINED BASED ON MONDAY TUESTDAY WEDNESDAY THURSDAY FRIDAY SATURDAY SUNDAY 
		//We have to take in consideration that the Sunday must be the last and Array index start at 0
		
		dayOfWeek = (dayOfWeek+5) %7;
		log.debug("Array Index to check: " + dayOfWeek);
		// We only use hour and minute
		Calendar nowTruncated = parseTime(String.valueOf(hourOfDay) + splitHour + String.valueOf(minutes)); 
		log.debug("Now truncated: " + nowTruncated.toString());
		
		ranges = mapAirConditioner.get(dayOfWeek);
		
		if(ranges == null){
			log.debug("isAirConditionerConfigurationStateOnNow ranges NO has values for dayOfWeek : " +dayOfWeek ); 
			
		}else{
			log.debug("isAirConditionerConfigurationStateOnNow ranges has values for dayOfWeek : " +dayOfWeek ); 
			rangesIterator = ranges.iterator();
			while (rangesIterator.hasNext()){
				timeInterval = rangesIterator.next();
				log.debug("range : fromHour: " + timeInterval.getFrom().toString() + " toHour: "+ timeInterval.getTo().toString());
				if (nowTruncated.after(timeInterval.getFrom()) && nowTruncated.before(timeInterval.getTo())){
					log.debug("isAirConditionerConfigurationStateOnNow FOUND schedule dayOfWeek is " + dayOfWeek);
					boolOK = true;
					break;
				}
			}
		}

		if(boolOK)
			log.debug("isAirConditionerConfigurationStateOnNow boolOK TRUE");
		else
			log.debug("isAirConditionerConfigurationStateOnNow boolOK FALSE");
		
		log.debug("isAirConditionerConfigurationStateOnNow END ");

		return boolOK;		
	}

	
	private boolean processTacDataAirConditionerManualActivation(){
		boolean airConditionerStateOn =  false;
		short id =0;
		Event eventManual = null;
		
		log.debug("processTacDataAirConditionerManualActivation : START ");
		
		try {
			if (this.listAirConditioningFloor2ZoneId !=null ){			
				log.debug("processTacDataAirConditionerManualActivation : listAirConditioningFloor2ZoneId is  not Null. Num " + listAirConditioningFloor2ZoneId.size());
						 
				//GET MANUAL ACTIVATION/DEACTIVATION			        	
				for(String stringId: listAirConditioningFloor2ZoneId){
					id= Short.valueOf(stringId);				
					eventManual = getManualActivationDeactivation(id);
					
					if(eventManual !=null){
						log.debug("processTacDataAirConditionerManualActivation : eventManual is NOT null for id " + id + " New value :" + eventManual.getObjectNewValue());
						
						if(eventManual.getObjectOldValue().equals(TacMng.AIRCONDITIONER_STATE_STOPPED) && eventManual.getObjectNewValue().equals(TacMng.AIRCONDITIONER_STATE_AUTOMATIC)){
							airConditionerStateOn = true;
							airConditionerStateOnTime =eventManual.getEventTime().getTime();
							break;
						}						
					}else
						log.debug("processTacDataAirConditionerManualActivation : eventManual is null for id " + id);
				}		
			 }else
				log.error("processTacDataAirConditionerManualActivation : eventAirConditioner is NULL ");		
			
		} catch (Exception e1) {
	    	log.error("processTacDataAirConditionerManualActivation EXCEPTION " + e1.getMessage());
			e1.printStackTrace();
		}
		log.debug("processTacDataAirConditionerManualActivation : END ");
		return airConditionerStateOn;
	}
	
	
	
	private boolean sendCEPAirConditionerState(int state){
		boolean boolOK =  false;
		Calendar now = Calendar.getInstance();
		long nowTimeInMilliseconds =0 ;
		try {
			
			nowTimeInMilliseconds = now.getTimeInMillis();
			
			if(cep != null){
				//boolOK = cep.sendEvent(stringTacAirConditioningFloor2Name, String.valueOf(state), airConditionerStateOnTime);
				boolOK = cep.sendEvent(stringTacAirConditioningFloor2Name, String.valueOf(state), nowTimeInMilliseconds);
				
				if(boolOK )
					log.debug("processTacDataAirConditioner : Data Sent to CEP sucessful for " + stringTacAirConditioningFloor2Name + "  state " + state + " time " + airConditionerStateOnTime);
				else
					log.error("processTacDataAirConditioner : Data Sent to CEP ERROR");
			}else{
				 log.error("processTacDataTemperature CEP is null : Data NOT sent to CEP");
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("processTacDataAirConditioner : Data Sent to CEP EXCEPTION " + e.getMessage());
		}	
		return boolOK ;
	}




	public Event getEventByIdLast(long id){
		Event value = null;
		TacDB db = null;
		
		try{
			db = new TacDB();
			value = db.getEventByIdLast(id);
			
			if(value != null){
				log.debug("getEventByIdLast Value is not null for id: " + id + " value: " + value.getObjectNewValue());
			}
			else{
				log.error("getEventByIdLast Value is null ");
			}
		}catch(Exception e){
			log.error("getEventByIdLast Exception " + e.getMessage());
		}
		return value;		
	}
	
	public Event getEventByState(long id, short state){
		Event value = null;
		TacDB db = null;
		
		try{
			db = new TacDB();
			value = db.getEventByState(id,state);
			
			if(value != null){
				log.debug("getEventByState Value is not null for id: " + id + " state : " + state + " value: " + value.getObjectNewValue());
			}
			else{
				log.error("getEventByState Value is null ");
			}
		}catch(Exception e){
			log.error("getEventByState Exception " + e.getMessage());
		}
		return value;		
	}
	
	public Event getAirConditioningCalendar(long id, String type){
		Event value = null;
		TacDB db = null;
		
		try{
			db = new TacDB();			
			value = db.getAirConditiongCalendar(id, type);
			
			if(value != null){
				log.debug("getAirConditiongCalendar Value is not null for id: " + id + " value: " + value);
			}
			else{
				log.error("getAirConditiongCalendar Value is null ");
			}
		}catch(Exception e){
			log.error("getAirConditiongCalendar Exception " + e.getMessage());
		}
		return value;		
	}
	
	public Event getManualActivationDeactivation(long id){
		Event value = null;
		TacDB db = null;
		
		try{
			db = new TacDB();
			value = db.getManualActivationDeactivation(id);
			
			if(value != null){
				log.debug("getManualActivationDeactivation Value is not null for id: " + id + " value: " + value);
			}
			else{
				log.error("getManualActivationDeactivation Value is null ");
			}
		}catch(Exception e){
			log.error("getManualActivationDeactivation Exception " + e.getMessage());
		}
		return value;		
	}
	
	public Calendar parseTime(String timeText){
		log.debug("Parsing time: " + timeText);
		Calendar calendar = null;
		String[] timeParts = timeText.split(splitHour);
		if(timeParts !=null && timeParts.length == 2){					
			calendar = Calendar.getInstance();
			calendar.set(0, 0, 0);
			calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(timeParts[0]));	
			calendar.set(Calendar.MINUTE, Integer.valueOf(timeParts[1]));	
		}
		return calendar;
	}
	
	private void parseAirConditionerConfiguration(Event eventAirConditioner2) {
		
		String[] newValueRanges = null;		
		String[] newValueRangesValues = null;
		
		String fromText = null;
		String toText = null;
		String mask = null;
		
	
		int state =0;
		TimeInterval intervalTemp = null;
		Calendar from = null;
		Calendar to = null;
		
		this.initMapAirConditionerState();

				
		String newValue = eventAirConditioner2.getObjectNewValue();
		
		log.debug("parseAirConditionerConfiguration newValue " + newValue ); 
		
		newValueRanges = newValue.split(splitPeriod);
		
		for(int i = 0 ; i<newValueRanges.length ; i++){
			
			log.debug("parseAirConditionerConfiguration newValueRanges " + i + " - "  + newValueRanges[i]);
			
			newValueRangesValues = newValueRanges[i].split(characterRangeValues);
			if(newValueRangesValues !=null && newValueRangesValues.length==3){
				fromText = newValueRangesValues[0];
				toText = newValueRangesValues[1];
				mask = newValueRangesValues[2];								
				
				
				for ( int j =0; j<7 ;j++){					
					state = Integer.valueOf(mask.substring(j,j+1));
					// FJD 29/02/2016 we only add activated intervals
					if(state == 1){					
						from = parseTime(fromText);
						if (from == null) {
							log.error("Error parsing from clause: " + fromText);
						}
						else {
							to = parseTime(toText);
							if (to == null) {
								log.error("Error parsing to clause: " + toText);
							}
							else {
								intervalTemp = new TimeInterval(from,to);
								mapAirConditioner.get(j).add(intervalTemp);
								log.debug("parseAirConditionerConfiguration : day " + j + " has mask value " + state + " from :  "  +fromText  + " to: " + toText  );								
							}

						}
					}
				}
			}else
				log.error("parseAirConditionerConfiguration newValuePeriodsValues num " + newValueRangesValues.length  + "  " + newValueRangesValues.toString());
		}
	}

	private void printMapAirConditioner(){
		log.debug("printMapAirConditioner start");
		
		Iterator<Integer> mapIterator = mapAirConditioner.keySet().iterator();
		Integer mapIndex = null;
		ArrayList<TimeInterval> timeIntervals = null;
		Iterator<TimeInterval> timeIntervalIterator = null;
		TimeInterval timeTemp = null;
		while (mapIterator.hasNext()){
			mapIndex = mapIterator.next();
			timeIntervals = mapAirConditioner.get(mapIndex);
			timeIntervalIterator = timeIntervals.iterator();
			while (timeIntervalIterator.hasNext()){
				timeTemp = timeIntervalIterator.next();
				log.debug("Day: " + mapIndex + " From: " + timeTemp.getFrom().toString() + " To: " + timeTemp.getTo().toString());
			}
			
		}
		log.debug("printMapAirConditioner end");	
	}

	public int getnUTARunning() {
		return nUTARunning;
	}

	public void setnUTARunning(int nUTARunning) {
		this.nUTARunning = nUTARunning;
	}

	public int getnUTAAutomatic() {
		return nUTAAutomatic;
	}

	public void setnUTAAutomatic(int nUTAAutomatic) {
		this.nUTAAutomatic = nUTAAutomatic;
	}
	
	
	
}
