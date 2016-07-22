package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.util.Calendar;

public class TimeInterval {
	
	private Calendar from = null;
	private Calendar to = null;
	
	public TimeInterval(Calendar from, Calendar to) {
		super();
		this.from = from;
		this.to = to;
	}
	public Calendar getFrom() {
		return from;
	}
	public void setFrom(Calendar from) {
		this.from = from;
	}
	public Calendar getTo() {
		return to;
	}
	public void setTo(Calendar to) {
		this.to = to;
	}
	
	// FJD 01/03/2016 Test to check the translation between Calendar and Array index
	public static void main(String[] args) {
		System.out.println("Calendar DAY_OF_THE_WEEK Monday" + Calendar.MONDAY);
		Calendar now = Calendar.getInstance();
		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);
		System.out.println("now day of the week: " + dayOfWeek );
		dayOfWeek = (2+5) %7;
		System.out.println("lunes : " + dayOfWeek );
		dayOfWeek = (3+5) %7;
		System.out.println("martes : " + dayOfWeek );
		dayOfWeek = (4+5) %7;
		System.out.println("miercoles : " + dayOfWeek );
		dayOfWeek = (5+5) %7;
		System.out.println("jueves : " + dayOfWeek );
		dayOfWeek = (6+5) %7;
		System.out.println("viernes : " + dayOfWeek );
		dayOfWeek = (7+5) %7;
		System.out.println("sabado : " + dayOfWeek );
		dayOfWeek = (1+5) %7;
		System.out.println("domingo : " + dayOfWeek );
		
		
	}
	

}
