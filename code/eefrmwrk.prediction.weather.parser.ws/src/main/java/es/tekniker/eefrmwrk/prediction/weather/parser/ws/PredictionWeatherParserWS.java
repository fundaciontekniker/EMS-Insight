package es.tekniker.eefrmwrk.prediction.weather.parser.ws;

import java.io.IOException;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.prediction.weather.parser.WeatherParser;
import es.tekniker.eefrmwrk.prediction.weather.parser.WeatherParserTimerTask;
import es.tekniker.eefrmwrk.prediction.weather.parser.api.IPredictionWeatherParserWS;

public class PredictionWeatherParserWS implements IPredictionWeatherParserWS {
	
	private static final Log log = LogFactory.getLog(PredictionWeatherParserWS.class);
	private static final long LOOPTIME_DEFAULT = 300000;
	private WeatherParser weatherParser;
	private WeatherParserTimerTask weatherParserTimerTask;

	private long loopTime;
	
	private String configurationFile ="weather.prediction.config.properties"; 
	
	public PredictionWeatherParserWS() throws Throwable {
				
		try {
			log.debug("Weather Parser WebService START");
			
			if (initGetConfigurationParameters()){
				log.debug("Weather Parser  WebService : iniGetConfigurationParameters OK");
				weatherParser = new WeatherParser();
				
				this.processWeatherData();
				
			}
			else{
				log.error("Weather Parser  WebService : iniGetConfigurationParameters NOK. Please review configuration file " + configurationFile);
				finalize();
			}
			log.debug("Weather Parser WebService END");			
			
		} catch (Exception e) {
			log.error("Error starting Weather Parser WebService " + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private boolean initGetConfigurationParameters(){		
		boolean boolOK = false;
		ConfigFile configFile =null;
		
		try {
			configFile = new ConfigFile(configurationFile);
			
			String stringLoopTime = configFile.getStringParam("weather.prediction.parser.looptime");			
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
		log.info("Shutting down Weather Parser WS");
		weatherParser  = null;
		super.finalize();
	}

	
	@Override
	public boolean processWeatherData() throws WSException{
		log.info("processWeatherData START ");
		// Instantiate Timer Object
		Timer time = new Timer(); 		
		weatherParserTimerTask = new WeatherParserTimerTask(weatherParser);
		log.info("processWeatherData Starting execute process WeatherData  every " + loopTime + " milliseconds" );
		
		// Create Repetitively task for every time defined in loopTime variable milliseconds
		time.schedule(weatherParserTimerTask, 0, loopTime); 
		
		return true;
	}

	
}

