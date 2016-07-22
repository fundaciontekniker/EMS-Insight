package es.tekniker.eefrmwrk.prediction.weather.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



import es.tekniker.eefrmwrk.cepmngr.client.CepClient;
import es.tekniker.eefrmwrk.config.ConfigFile;


public class WeatherParser {

	private static Log log =LogFactory.getLog(WeatherParser.class);

	private String configurationFile ="weather.prediction.config.properties";
	
	private String WEATHER_URL = null;
	private String WEATHER_AEMET_URL = null;
	private String WEATHER_AEMET_HOURS_URL = null;
	
	private static final long TIMEOUT_DEFAULT = 10000;
	
	private static long TIMEOUT = 0; 
	private static String CEP_URL = "";
	private String CEP_WEATHER_VARNAME = null;
	private String CEP_WEATHER_VARNAME_AEMET = null;
		
	
	public WeatherParser() throws IOException{
		iniGetConfigurationParameters();
	}
	
	public void iniGetConfigurationParameters() throws IOException{	
	
		ConfigFile configFile = null;
		String stringTimeout = null;
		try {
			configFile = new ConfigFile(configurationFile);
			
			WEATHER_URL = configFile.getStringParam("weather.url");			
			if(WEATHER_URL != null){				
				log.info("WEATHER_URL: " + WEATHER_URL);
			}else{					
				log.error("WEATHER_URL is null. Please review configuration file");
			}
			
			WEATHER_AEMET_URL = configFile.getStringParam("weather.url.aemet");			
			if(WEATHER_AEMET_URL != null){				
				log.info("WEATHER_AEMET_URL: " + WEATHER_AEMET_URL);
			}else{					
				log.error("WEATHER_AEMET_URL is null. Please review configuration file");
			}
			
			WEATHER_AEMET_HOURS_URL = configFile.getStringParam("weather.url.aemet.hours");			
			if(WEATHER_AEMET_HOURS_URL != null){				
				log.info("WEATHER_AEMET_HOURS_URL: " + WEATHER_AEMET_HOURS_URL);
			}else{					
				log.error("WEATHER_AEMET_HOURS_URL is null. Please review configuration file");
			}
						
			stringTimeout = configFile.getStringParam("cep.client.timeout");			
			if(stringTimeout != null){
				TIMEOUT = Long.valueOf(stringTimeout);
				log.info("TIMEOUT: " + TIMEOUT);
			}else{
				TIMEOUT = TIMEOUT_DEFAULT;	
				log.info("TIMEOUT set DEFAULT: " + TIMEOUT);
			}
			
			CEP_URL = configFile.getStringParam("cep.client.url");			
			if(CEP_URL != null){				
				log.info("URL: " + CEP_URL);
			}else{					
				log.error("URL is null. Please review configuration file");
			}
			
			CEP_WEATHER_VARNAME = configFile.getStringParam("cep.variable.weather.eibar.temp.predict");			
			if(CEP_WEATHER_VARNAME != null){				
				log.info("CEP_WEATHER_VARNAME: " + CEP_WEATHER_VARNAME);
			}else{					
				log.error("CEP_WEATHER_VARNAME is null. Please review configuration file");
			}
						
			CEP_WEATHER_VARNAME_AEMET = configFile.getStringParam("cep.variable.weather.eibar.temp.predict.aemet");			
			if(CEP_WEATHER_VARNAME_AEMET != null){				
				log.info("CEP_WEATHER_VARNAME_AEMET: " + CEP_WEATHER_VARNAME_AEMET);
			}else{					
				log.error("CEP_WEATHER_VARNAME_AEMET is null. Please review configuration file");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("iniGetConfigurationParameters : Configuration file " + configurationFile+ " not found.", e);
			throw e;			
		}
	}
	
	
	public boolean parseWeatherData(){
		boolean boolOK = false;
		
		String predictedTemp = "";
		long currentTimestamp = 0;
		long predictedTimestamp = 0;
		ArrayList<DateTimeTemperatureMeasurement> predictionList = null;
		boolean boolCEP = false;
		CepClient cepClient = null;
		currentTimestamp= new java.util.Date().getTime();
		
		log.debug("parseWeatherData START  at " + currentTimestamp);
		
		predictionList = XMLParser.getData(WEATHER_URL);
		
		try {

			if (predictionList != null){
				
				cepClient = new CepClient(CEP_URL,TIMEOUT);
				
				log.debug("parseWeatherData predictionList is not null " + predictionList.size());
				
				for (int i = 0; i < predictionList.size(); i++) {
				
					predictedTimestamp =  predictionList.get(i).getPredictedTimestamp();					
					predictedTemp = predictionList.get(i).getTemperature();
				
					log.debug("parseWeatherData prediction Data " + i + " predictedTimestamp " +  predictedTimestamp + " predictedTemp: " + predictedTemp  + " currentTimestamp: " +currentTimestamp);
					
					boolCEP = cepClient.sendPredictionEvent(CEP_WEATHER_VARNAME, predictedTemp, predictedTimestamp, currentTimestamp);
					 
					if (boolCEP )
						log.debug("parseWeatherData sendEvent to CEP OK ");
					else
						log.error("parseWeatherData sendEvent to CEP with ERROR ");

				}
			}else
				log.debug("parseWeatherData predictionList is null " );
			
		} catch (Exception e) {
			log.error("parseWeatherData EXCEPTION " + e.getMessage());
		}

		
		currentTimestamp= new java.util.Date().getTime();
		log.debug("parseWeatherData END  at " + currentTimestamp);
		
		return boolOK;
	}
	
	public boolean parseWeatherDataAEMET(){
		boolean boolOK = false;
		
		String predictedTemp = "";
		long currentTimestamp = 0;
		long predictedTimestamp = 0;
		ArrayList<DateTimeTemperatureMeasurement> predictionList = null;
		ArrayList<DateTimeTemperatureMeasurement> predictionListHours = null;
		boolean boolCEP = false;
		CepClient cepClient = null;
		currentTimestamp= new java.util.Date().getTime();
		
		log.debug("parseWeatherDataAEMET START  at " + currentTimestamp);
		
		//predictionList = XMLParserAemet.getData(WEATHER_AEMET_URL,false);		
		predictionList = XMLParserAemet.getData(WEATHER_AEMET_HOURS_URL,true);
				
		try {

			/*
			if (predictionList != null){
				if (predictionListHours != null){
					for(int i =0 ; i <predictionListHours.size() ; i++){
						predictionList.add(predictionListHours.get(i));
					}
				}
			}
			*/
			
			if (predictionList != null){
				
				cepClient = new CepClient(CEP_URL,TIMEOUT);
				
				log.debug("parseWeatherDataAEMET predictionList is not null " + predictionList.size());
				
				for (int i = 0; i < predictionList.size(); i++) {
				
					predictedTimestamp =  predictionList.get(i).getPredictedTimestamp();					
					predictedTemp = predictionList.get(i).getTemperature();
				
					log.debug("parseWeatherDataAEMET prediction Data " + i + " predictedTimestamp " +  predictedTimestamp + " predictedTemp: " + predictedTemp  + " currentTimestamp: " +currentTimestamp);
					
					boolCEP = cepClient.sendPredictionEvent(CEP_WEATHER_VARNAME_AEMET, predictedTemp, predictedTimestamp, currentTimestamp);
					 
					if (boolCEP )
						log.debug("parseWeatherDataAEMET sendEvent to CEP OK ");
					else
						log.error("parseWeatherDataAEMET sendEvent to CEP with ERROR ");

				}
			}else
				log.debug("parseWeatherDataAEMET predictionList is null " );
			
		} catch (Exception e) {
			log.error("parseWeatherDataAEMET EXCEPTION " + e.getMessage());
		}
		
		currentTimestamp= new java.util.Date().getTime();
		log.debug("parseWeatherDataAEMET END  at " + currentTimestamp);
		
		return boolOK;
	}
	
}
