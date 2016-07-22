package es.tekniker.eefrmwrk.tac;

import java.io.IOException;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.monitoring.tac.mng.TacMng;
import es.tekniker.eefrmwrk.monitoring.tac.mng.TacMngTimerTask;
import es.tekniker.eefrmwrk.tac.mng.api.ITacManagerWS;

public class TacManagerWS implements ITacManagerWS {
	
	private static final Log log = LogFactory.getLog(TacManagerWS.class);
	private static final long LOOPTIME_DEFAULT = 300000;
	private TacMng tacManager;
	private TacMngTimerTask tacMngTimerTask = null;

	private long loopTime;
	
	private String configurationFile ="tac.config.properties"; 
	
	public TacManagerWS() throws Throwable {
				
		try {
			log.debug("Tac Manager WebService START");
			
			if (initGetConfigurationParameters()){
				log.debug("Tac Manager WebService : iniGetConfigurationParameters OK");
				tacManager = new TacMng();
				
				this.processDataTac();
				
			}
			else{
				log.error("Tac Manager WebService : iniGetConfigurationParameters NOK. Please review configuration file " + configurationFile);
				finalize();
			}
			log.debug("Tac Manager WebService END");			
			
		} catch (Exception e) {
			log.error("Error starting Tac WebService");
			throw new WSException("TacManagerWS_CONSTRUCTOR0",
					"Error starting Tac Manager WebService", e);
		}
	}

	private boolean initGetConfigurationParameters(){		
		boolean boolOK = false;
		ConfigFile configFile =null;
		
		try {
			configFile = new ConfigFile(configurationFile);
			
			String stringLoopTime = configFile.getStringParam("tac.looptime");			
			if(stringLoopTime != null){
				loopTime = Long.valueOf(stringLoopTime);
				log.info("loopTime: " + loopTime);
				
				boolOK= true;
			}else{
				loopTime = LOOPTIME_DEFAULT;	
				log.info("loopTime set DEFAULT: " + loopTime);
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("tac.config.properties file not found.", e);
		}
		
		return boolOK;
	}
	
	
	
	@Override
	public void finalize() throws Throwable {
		log.info("Shutting down TAC WS");
		tacManager = null;
		tacMngTimerTask = null;
		super.finalize();
	}

	
	@Override
	public boolean processDataTac() throws WSException{
		log.info("processDataTac START ");
		
		tacManager.processTacData();
		
		/*
		// Instantiate Timer Object
		Timer time = new Timer(); 		
		log.info("processDataTac Starting execute ProcessData TAC every " + loopTime + " milliseconds" );		
		tacMngTimerTask = new TacMngTimerTask(tacManager);		
		// Create Repetitively task for every time defined in loopTime variable milliseconds
		time.schedule(tacMngTimerTask, 0, loopTime); 
		*/
		
		log.info("processDataTac END ");
		
		return true;
	}
	
}

