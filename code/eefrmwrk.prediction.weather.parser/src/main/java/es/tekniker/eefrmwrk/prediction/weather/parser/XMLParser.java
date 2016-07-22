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

public class XMLParser {

	private static ArrayList<DateTimeTemperatureMeasurement> myMeasurements;
	
	private static String TEMP_TAG = "temp";
	private static String TEMP_VALUE_TAG = "value";
	private static String DESC_TAG = "symbol";
	private static String DESC_VALUE_TAG = "desc";
	
	private static String XML_TAG_DAY= "day";
	private static String XML_TAG_HOUR= "hour";	
	private static String XML_TAG_DAY_VALUE ="value";
	
	//private static String WEATHER_URL = "http://api.tiempo.com/index.php?api_lang=es&localidad=3390&affiliate_id=1o12fzp3zesa&v=2&h=1";
	//La URL a la que hay que acceder es siempre la misma (CONSTANTE)
	
	private static Log log =LogFactory.getLog(XMLParser.class);
	
	public static ArrayList<DateTimeTemperatureMeasurement> getData(String WEATHER_URL){
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
				parseDocument(dom);
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
		//return filename;
		return myMeasurements;
	}
	
	private static void parseDocument(Document dom) throws IOException{
		int NUMBER_OF_DAYS_NODES = 0;
		//Root Element
		
		log.debug("parseDocument START");
		
		Element docEle = dom.getDocumentElement();
		
		NodeList nl_days = docEle.getElementsByTagName(XMLParser.XML_TAG_DAY);
		if (nl_days!=null){
			NUMBER_OF_DAYS_NODES = nl_days.getLength();
			
			log.debug("parseDocument dom NUMBER_OF_DAYS_NODES " + NUMBER_OF_DAYS_NODES);
		
			if (NUMBER_OF_DAYS_NODES>0){
				for (int i = 0; i < NUMBER_OF_DAYS_NODES; i++) {
					log.debug("parseDocument day " + i);					
					getTemperatureMeasurements(nl_days, i);
				}
			}
		}else{
			log.error("parseDocument dom nl_days is NULL ");
		}
		log.debug("parseDocument END");
	}
	
	private static void getTemperatureMeasurements(NodeList nl_days, int index) {
		int NUMBER_OF_HOURS_NODES = 0;
		String hour_value = "00:00:00";
		String temp_value = "Sin temperatura";
		String desc_value = "Sin descripción";
		String date_value = "Fecha no valida";
		DateTimeTemperatureMeasurement tm = null;
		
		log.debug("getTemperatureMeasurements START");
		
		Element day_element = (Element)nl_days.item(index);
		date_value = getDayValue(day_element);
		
		
		NodeList nl_hours = day_element.getElementsByTagName(XMLParser.XML_TAG_HOUR);
		if (nl_hours!=null){
			NUMBER_OF_HOURS_NODES = nl_hours.getLength();
			
			log.debug("getTemperatureMeasurements NUMBER_OF_HOURS_NODES " + NUMBER_OF_HOURS_NODES);
			
			if (NUMBER_OF_HOURS_NODES>0){
				for (int i = 0; i < NUMBER_OF_HOURS_NODES; i++) {					
					hour_value = getHourValue(nl_hours, i);
					temp_value = getTempValue(nl_hours, i);
					desc_value = getDescValue(nl_hours, i);
					
					log.debug("getTemperatureMeasurements date_value: " + date_value + " hour_value: " + hour_value + " temp_value: " + temp_value );
					
					tm = new DateTimeTemperatureMeasurement(date_value, hour_value,temp_value, desc_value) {}; 
					myMeasurements.add(tm);
				}
			}	
		}else
			log.error("getTemperatureMeasurements nl_hours is NULL ");
			
		log.debug("getTemperatureMeasurements END");
	}

	private static String getDescValue(NodeList nl_hours, int index) {
		String desc_value = getTextValue(nl_hours, index, DESC_TAG, DESC_VALUE_TAG);
		return desc_value;
	}

	private static String getTempValue(NodeList nl_hours, int index) {
		String temp_value = getTextValue(nl_hours, index, TEMP_TAG, TEMP_VALUE_TAG);
		return temp_value;
	}

	private static String getTextValue(NodeList nl_hours, int index,String Tag, String ValueTag) {
		String value = "";
		int NUMBER_OF_NODES = 0;
		
		Element el = (Element)nl_hours.item(index);
		NodeList nl = el.getElementsByTagName(Tag);
		
		if (nl!=null){
			NUMBER_OF_NODES = nl.getLength();
		}
		
		if (NUMBER_OF_NODES > 0){
			Element ele = (Element)nl.item(0);
			value = ele.getAttribute(ValueTag);
		}
		return value;
	}

	private static String getHourValue(NodeList nl_hours, int index) {
		Element hour_element = (Element)nl_hours.item(index);
		String hour_value = getDayValue(hour_element);
		return hour_value;
	}

	private static String getDayValue(Element dayD1){
		String date = dayD1.getAttribute(XML_TAG_DAY_VALUE);
		return date;
	}
	
}
