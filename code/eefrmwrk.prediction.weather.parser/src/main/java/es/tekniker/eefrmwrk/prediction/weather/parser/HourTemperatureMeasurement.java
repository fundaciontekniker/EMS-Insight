package es.tekniker.eefrmwrk.prediction.weather.parser;


public class HourTemperatureMeasurement{

	private String time;
	private String temperature;
	private String description;

	public HourTemperatureMeasurement() {
	}
	
	public HourTemperatureMeasurement(String time_value,
			String temperature_value, String description_value) {
		
		this.setTime(time_value);
		this.setTemperature(temperature_value);
		this.description = description_value;
		
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getDescription() {
		return description;
	}
	
}
