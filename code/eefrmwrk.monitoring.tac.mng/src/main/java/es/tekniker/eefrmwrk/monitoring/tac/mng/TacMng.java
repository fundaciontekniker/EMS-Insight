package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.cepmngr.client.CepClient;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.home.client.HomeClient;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event;

/**
 * TacMng
 */
public class TacMng {
	
	private static Log log =LogFactory.getLog(TacMng.class);

	private String stringLoopTime = null;
	private static long loopTime=0; 
	private long LOOPTIME_DEFAULT= 10000;
	
	private String stringTimeout= null;
	private long timeout =0; 
	private String CEPurl = null;	
	private String HOMEurl = null;
	
	private List<String> listTacVariablesId = null;
	private List<String> listTacVariablesName = null;
		
	private String stringTacAirConditioningFloor2Id =  null;
	private Long tacAirConditioningFloor2Id = null;
	private String tacAirConditioningFloor2Type = null;
	
	private List<String> listAirConditioningFloor2ZoneId = null;
	
	private long TIMEOUT_DEFAULT= 6000;
		
	public static short AIRCONDITIONER_STATE_STOPPED = 0;
	public static short AIRCONDITIONER_STATE_RUNNING = 1;
	public static short AIRCONDITIONER_STATE_AUTOMATIC = 2;
	
	private String stringTacAirConditioningFloor2Name = null;	
	
	private String configurationFile ="tac.config.properties"; 
	
	private CepClient cep = null;
	private HomeClient home = null;
	
	public TacMng() throws IOException{	
		try {
			initGetConfigurationParameters();
			initCepClient();		
			initHomeClient();			
			//initMapAirConditionerState();
			//initMapLastTemperature();
		} catch (IOException e) {
			log.error("TacMng : Configuration file " + configurationFile+ " not found.", e);			
			throw e;
		}
	}

	private void initGetConfigurationParameters() throws IOException{		
		ConfigFile configFile =null;
		
		try {
			configFile = new ConfigFile(configurationFile);
						
			stringLoopTime = configFile.getStringParam("tac.looptime");			
			if(stringLoopTime != null){
				loopTime = Long.valueOf(stringLoopTime);
				log.info("loopTime: " + loopTime);
			}else{
				loopTime = LOOPTIME_DEFAULT;	
				log.info("loopTime set DEFAULT: " + loopTime);
			}
			
			listTacVariablesId = configFile.getListStringParam("tacVariableId");
						
			if(listTacVariablesId != null){
				log.debug("listTacVariablesId is not null : Num " + listTacVariablesId.size());
			}			
			else{
				log.error("listTacVariablesId is NULL");
			}
			
			listTacVariablesName = configFile.getListStringParam("tacVariableName");
			
			if(listTacVariablesName != null){
				log.debug("listTacVariablesName is not null : Num " + listTacVariablesName.size());
			}			
			else{
				log.error("listTacVariablesName is NULL");
			}
			
			
			stringTacAirConditioningFloor2Name = configFile.getStringParam("tac.airconditioner.floor2.name");
			
			if(stringTacAirConditioningFloor2Name != null){
				log.info("stringTacAirConditioningFloor2Name: " + stringTacAirConditioningFloor2Name);
			}
			
			stringTacAirConditioningFloor2Id = configFile.getStringParam("tac.airconditioner.floor2.id");
			
			if(stringTacAirConditioningFloor2Id != null){
				tacAirConditioningFloor2Id = Long.valueOf(stringTacAirConditioningFloor2Id);
				log.info("tacAirConditioningId: " + tacAirConditioningFloor2Id);
			}
			
			tacAirConditioningFloor2Type = configFile.getStringParam("tac.airconditioner.floor2.type");
			
			if(tacAirConditioningFloor2Type != null){				
				log.info("tacAirConditioningType: " + tacAirConditioningFloor2Type);
			}
						
			listAirConditioningFloor2ZoneId = configFile.getListStringParam("tac.airconditioner.floor2.zone.id");
			
			if(listAirConditioningFloor2ZoneId != null){
				log.debug("listAirConditioningFloorZoneId is not null : Num " + listAirConditioningFloor2ZoneId.size());
			}			
			else{
				log.error("listAirConditioningFloorZoneId is NULL");
			}
			
			stringTimeout = configFile.getStringParam("cep.client.timeout");
			
			if(stringTimeout != null){
				timeout = Long.valueOf(stringTimeout);
				log.info("timeout: " + timeout);
			}else{
				timeout = TIMEOUT_DEFAULT;	
				log.info("timeout set DEFAULT: " + timeout);
			}
			
			CEPurl = configFile.getStringParam("cep.client.url");
			
			if(CEPurl != null){
				log.debug("CEP URL " + CEPurl);
			}else{
				log.error("CEP URL not defined" );
			}
			
			HOMEurl = configFile.getStringParam("home.client.url");
			
			if(HOMEurl != null){
				log.debug("HOME URL " + HOMEurl);
			}else{
				log.error("HOME URL not defined" );
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("iniGetConfigurationParameters : Configuration file " + configurationFile+ " not found.", e);
			throw e;			
		}
	}
	
	private void initCepClient(){		
		try {		
			if(CEPurl != null){ 
				cep = new CepClient(CEPurl,timeout);
				
				if(cep !=null)
					log.debug("intCepClient cep instance is NOT null");
				else
					log.error("intCepClient cep instance is null");
			}else
				log.error("intCepClient . CEPurl is null");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("intCepClient . Exception " + e.getMessage());
		}
	}
	
	private void initHomeClient(){		
		try {		
			if(HOMEurl != null){ 
				home = new HomeClient(HOMEurl,timeout);
				
				if(home !=null)
					log.debug("initHomeClient home instance is NOT null");
				else
					log.error("initHomeClient home instance is null");
			}else
				log.error("initHomeClient . HOMEurl is null");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("initHomeClient . Exception " + e.getMessage());
		}
	}
	
	
	public boolean processTacData(){
		boolean boolOK =  false;
		log.debug("processTacData : START ");
		
		ProcessTemperatureThread temperatureThread = new ProcessTemperatureThread(cep, home, loopTime, listTacVariablesId , listTacVariablesName);
		
		if(temperatureThread != null){
			log.debug("processTacData : temperatureThread is NOT null launch it to START ");
			temperatureThread.start();		
		}else
			log.error("processTacData : temperatureThread is NULL NOT launch it ");
		
		ProcessAirConditionerThread airConditionerThread = new ProcessAirConditionerThread(stringTacAirConditioningFloor2Name, tacAirConditioningFloor2Type, listAirConditioningFloor2ZoneId , cep, loopTime, tacAirConditioningFloor2Id);
		if(airConditionerThread != null){
			log.debug("processTacData : airConditionerThread is NOT null launch it to START ");
			airConditionerThread.start();
		}else
			log.error("processTacData : airConditionerThread is NULL NOT launch it ");
		
		log.debug("processTacData : END ");
		
		return boolOK;
	}
	
	
	
	
	
	
	public void connect(String interfaceAddress, String deviceAddress, String settings,int timeout){
	}
	
	public void read( Object connection,
			List<Object> containers,
          	Object containerListHandle, 
          	String samplingGroup, 
          	int timeout){
	}
	
	public void disconnect(Object connection){
		
	}
	
    public static void main( String[] args )
    {
        log.debug("Wellcome to Tac Manager!" );
        
        TacMng manager = null;
        TacDB db = null;
        short id234 =234;
        short id232 =232;
        
        try{        
        	//db = new TacDB();        	
        	//TrendLog instance = db.getTrendLog(id232);
			//TrendLogValue value = db.getTrendLogValue(id234);        	
        	//List<TrendLogValue> list = db.getListTrendLogValueSinceLogTime(id232, null);
        	
        	
        	
        	manager = new TacMng();
	        log.debug("ProcessTacData starting..." );
	        
	        manager.processTacData();
	        
	        /*
	        for (int i = 0 ; i< 3 ;i++){
	        
	        	manager.processTacData();
	        	
	        	try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	        */
	        /*
	        Timer time = new Timer(); // Instantiate Timer Object
			//ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
			time.schedule(manager, 0, loopTime); // Create Repetitively task for every time defined in loopTime variable milliseconds
*/
			/*
			//for demo only.
			for (int i = 0; i <= 5; i++) {
				log.debug("Execution in Main Thread...." + i);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i == 5) {
					log.debug("Test  Terminates");
					System.exit(0);
				}
			}
	        */
	        
	        log.debug("ProcessTacData END" );
        }catch (Exception e){
        	log.error("ProcessTacData Exception " + e.getMessage() );
        }
    }
}
