package es.tekniker.eefrmwrk.prediction.weather.parser;

import java.util.Calendar;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class WeatherParserTimerTask extends TimerTask{
	

	
	private static Log log =LogFactory.getLog(WeatherParserTimerTask.class);
	
	private WeatherParser weatherParser =null;
	
	public WeatherParserTimerTask (WeatherParser weatherParser){
		this.weatherParser=weatherParser;	
	}
	
	@Override
	public void run() {
	    log.debug("run START at " + Calendar.getInstance().getTimeInMillis() );
	    
	    log.debug("run parseWeatherData START at " + Calendar.getInstance().getTimeInMillis() );
	    weatherParser.parseWeatherData();
	    log.debug("run parseWeatherData END at " + Calendar.getInstance().getTimeInMillis() );

	    log.debug("run parseWeatherDataAEMET START at " + Calendar.getInstance().getTimeInMillis() );
	    weatherParser.parseWeatherDataAEMET();
	    log.debug("run parseWeatherDataAEMET END at " + Calendar.getInstance().getTimeInMillis() );
	    
		log.debug("run END at " + Calendar.getInstance().getTimeInMillis() );
	}
	
}
