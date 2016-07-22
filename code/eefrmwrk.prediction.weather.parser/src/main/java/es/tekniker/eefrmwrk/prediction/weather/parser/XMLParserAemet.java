package es.tekniker.eefrmwrk.prediction.weather.parser;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParserAemet {

	private static ArrayList<DateTimeTemperatureMeasurement> myMeasurements;
		
	private static String XML_TAG_DAY = "dia";
	private static String XML_TAG_TEMPERATURE = "temperatura";
	private static String XML_TAG_DATA = "dato";
	private static String XML_TAG_HOUR = "hora";
	private static String XML_TAG_DATE = "fecha";
	
	private static String XML_TAG_PERIOD = "periodo";
	
	
	private static Log log =LogFactory.getLog(XMLParserAemet.class);
	
	public static ArrayList<DateTimeTemperatureMeasurement> getData(String WEATHER_URL, boolean boolTemperatureByHours){
		log.debug("getData START");

		myMeasurements = new ArrayList<DateTimeTemperatureMeasurement>();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		if(dbf != null)
			log.debug("getData dbf is not null");
		else
			log.error("getData dbf is NULL");
			
		try{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document dom = db.parse(WEATHER_URL);
			if (dom !=null){
				log.debug("getData dom instance is  not null");
				parseDocument(dom, boolTemperatureByHours);
			}else
				log.error("getData dom instance is  NULL");
		}
		catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
		catch (SAXException se) {
			se.printStackTrace();
		}
		catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		log.debug("getData myMeasurements size " + myMeasurements.size());
		log.debug("getData END ");
		return myMeasurements;
	}
	
	private static void parseDocument(Document dom, Boolean boolTemperatureByHours) throws IOException{
		int NUMBER_OF_DAYS_NODES = 0;
		//Root Element
		
		log.debug("parseDocument START");		
		Element docEle = dom.getDocumentElement();		
		NodeList nl_days = docEle.getElementsByTagName(XMLParserAemet.XML_TAG_DAY);
		if (nl_days!=null){
			NUMBER_OF_DAYS_NODES = nl_days.getLength();
			
			log.debug("parseDocument dom NUMBER_OF_DAYS_NODES " + NUMBER_OF_DAYS_NODES);
		
			if (NUMBER_OF_DAYS_NODES>0){
				for (int i = 0; i < NUMBER_OF_DAYS_NODES; i++) {
					log.debug("parseDocument day " + i);
					if(boolTemperatureByHours)
						getTemperatureMeasurementsByHours(nl_days, i);
					else
						getTemperatureMeasurements(nl_days, i);
				}
			}
		}else{
			log.error("parseDocument dom nl_days is NULL ");
		}
		log.debug("parseDocument END");
	}

	private static void getTemperatureMeasurementsByHours(NodeList nl_days, int index) {
		int NUMBER_OF_TEMPERATURE_NODES = 0;
		String hour_value = "";
		String temp_value = "";
		String desc_value = "";
		String date_value = "";
		DateTimeTemperatureMeasurement tm = null;
		
		log.debug("getTemperatureMeasurementsByHours START");
		
		Element day_element = (Element)nl_days.item(index);
		date_value = getDateValue(day_element);
		
		
		NodeList nl_temp = day_element.getElementsByTagName(XMLParserAemet.XML_TAG_TEMPERATURE);
		if (nl_temp!=null){
			NUMBER_OF_TEMPERATURE_NODES = nl_temp.getLength();
			
			log.debug("getTemperatureMeasurementsByHours NUMBER_OF_TEMPERATURE_NODES " + NUMBER_OF_TEMPERATURE_NODES);
			for (int i = 0; i < NUMBER_OF_TEMPERATURE_NODES; i++) {
			
				Element temperature_element = (Element)nl_temp.item(i);				
				
				hour_value = temperature_element.getAttribute(XMLParserAemet.XML_TAG_PERIOD);
				
				if(hour_value.contains(":")==false )
					hour_value = hour_value + ":00";
										
				temp_value = temperature_element.getTextContent();
				
				log.debug("getTemperatureMeasurementsByHours date_value: " + date_value + " hour_value: " + hour_value + " temp_value: " + temp_value );
				
				tm = new DateTimeTemperatureMeasurement(date_value, hour_value,temp_value, desc_value) {}; 
				myMeasurements.add(tm);
			}
		}else
			log.error("getTemperatureMeasurementsByHours nl_hours is NULL ");
				
		log.debug("getTemperatureMeasurements END");		
	}

	private static void getTemperatureMeasurements(NodeList nl_days, int index) {
		int NUMBER_OF_TEMPERATURE_NODES = 0;
		int NUMBER_OF_DATA_NODES = 0;
		String hour_value = "";
		String temp_value = "";
		String desc_value = "";
		String date_value = "";
		DateTimeTemperatureMeasurement tm = null;
		
		log.debug("getTemperatureMeasurements START");
		
		Element day_element = (Element)nl_days.item(index);
		date_value = getDateValue(day_element);
		
		
		NodeList nl_temp = day_element.getElementsByTagName(XMLParserAemet.XML_TAG_TEMPERATURE);
		if (nl_temp!=null){
			NUMBER_OF_TEMPERATURE_NODES = nl_temp.getLength();
			
			log.debug("getTemperatureMeasurements NUMBER_OF_TEMPERATURE_NODES " + NUMBER_OF_TEMPERATURE_NODES);
			for (int i = 0; i < NUMBER_OF_TEMPERATURE_NODES; i++) {
			
				Element temperature_element = (Element)nl_temp.item(i);
				
				NodeList nl_data = temperature_element.getElementsByTagName(XMLParserAemet.XML_TAG_DATA);
				
				NUMBER_OF_DATA_NODES = nl_data.getLength();
				
				if (NUMBER_OF_DATA_NODES>0){
					for (int j = 0; j < NUMBER_OF_DATA_NODES; j++) {					
						hour_value = getHourValue(nl_data, j);
						temp_value = getTempValue(nl_data,j);
						
						log.debug("getTemperatureMeasurements date_value: " + date_value + " hour_value: " + hour_value + " temp_value: " + temp_value );
						
						tm = new DateTimeTemperatureMeasurement(date_value, hour_value,temp_value, desc_value) {}; 
						myMeasurements.add(tm);
						
					}
				}	
			}
		}else
			log.error("getTemperatureMeasurements nl_hours is NULL ");
			
	
		log.debug("getTemperatureMeasurements END");		
	}	

	private static String getDateValue(Element dayD1){
		String date = dayD1.getAttribute(XMLParserAemet.XML_TAG_DATE);
		
		date = date.replace("-", "");
		
		return date;
	}
	
	private static String getHourValue(NodeList nl_data, int index) {
		Element data_element = (Element)nl_data.item(index);
		String hour_value = data_element.getAttribute(XMLParserAemet.XML_TAG_HOUR);
		
		if(hour_value.contains(":") == false)
			hour_value = hour_value +":00";
		
		return hour_value;
	}
	
	private static String getTempValue(NodeList nl_data, int index) {
		Element data_element = (Element)nl_data.item(index);
		String temp_value = data_element.getTextContent();
				
		if(temp_value==null)
			temp_value="";
		
		return temp_value;
	}
	

	
	
}
