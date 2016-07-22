package es.tekniker.eefrmwrk.prediction.weather.parser.api;

import es.tekniker.eefrmwrk.commons.WSException;

public interface IPredictionWeatherParserWS {

	boolean processWeatherData() throws WSException;

}
