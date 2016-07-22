package es.tekniker.eefrmwrk.prediction.weather.parser;



public class DateTimeConverter {

	
	public static String ConvertToXSDDateTime(String date, String time)
	{
		
		
		//opcionB
		//return date.concat("T").concat(time);
		
		//opcionC
		StringBuilder sb = new StringBuilder();
		return sb.append(date).append("T").append(time).toString();
		
		
		/*v2
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		String dateInString = date;
		try {
			java.util.Date date2 = formatter.parse(dateInString);
			System.out.println(date2);
			
			String a = formatter.format(date2);
			System.out.println(formatter.format(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		
		/*v1
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		try {
			java.util.Date date2 = sdf.parse("2015-05-04");
			sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
			StringBuffer sb = new StringBuffer(sdf.format(date2));
			return sb.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;*/
	
	}
	
}

