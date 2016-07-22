package es.tekniker.eefrmwrk.prediction.weather.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;


public abstract class DateTimeTemperatureMeasurement extends HourTemperatureMeasurement{

	
	private String date;
	

	public DateTimeTemperatureMeasurement(String date_value, String hour_value,
			String temperature_value, String description_value) {
		
		super(hour_value, temperature_value, description_value);
		this.setDate(date_value);
	}


	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	

	public long getPredictedTimestamp(){
		long predictedTimestamp =0;
		try {
			predictedTimestamp = new SimpleDateFormat("yyyyMMdd'T'HH:mm").parse(getDate() + "T" + getTime()).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return predictedTimestamp;		
	}
	
}
