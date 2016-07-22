package es.tekniker.eefrmwrk.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

public class DateUtil {
	
	public static GregorianCalendar getGregorianDate(Date dateDate){
		GregorianCalendar dateGregorian = new GregorianCalendar();
		try {
			dateGregorian.setTime(dateDate);
		}
		catch(Exception e){
			//log.fatal("Date not valid publication: " + publication.getPublicationID());	
			e.printStackTrace();
		}
		return dateGregorian;
	}

	public static XMLGregorianCalendar getXmlGregorianDate(GregorianCalendar gregorianDate){
		
		DatatypeFactory dataTypeFactory;
		XMLGregorianCalendar xmlGregorianDate = new XMLGregorianCalendarImpl();
		try {
			dataTypeFactory = DatatypeFactory.newInstance();
			xmlGregorianDate = dataTypeFactory.newXMLGregorianCalendar(gregorianDate);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlGregorianDate;
	}
	
	public static XMLGregorianCalendar getXmlGregorianDate(Date dateDate){
		return DateUtil.getXmlGregorianDate(DateUtil.getGregorianDate(dateDate));
	}
	
	public static Date getDate(String stringDate){
		DateFormat df = new SimpleDateFormat("yyyy MMM dd");	
		Date dateDate = new Date();
		try {
			dateDate = df.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		return dateDate;
	}
	
	public static String formatDate(Date d){
		SimpleDateFormat df= new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss SSS");
		return df.format(d);
	}
	public static String formatDate(long d){
		SimpleDateFormat df= new SimpleDateFormat("yyyy/MMM/dd HH:mm:ss SSS");
		return df.format(new Date(d));
	}


}
