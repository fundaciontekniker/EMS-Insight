package es.tekniker.eefrmwrk.ws;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.net.UnknownHostException;


import android.util.Log;

public class WebServiceCall {
	private static final String TAG = "WebServiceCall";
	private static boolean isDebug = true;

	private static String InvokeWS(String sRequest, String targetURL,String soapAction)
	{
		StringBuffer response = new StringBuffer(); 
		URL url;
		HttpURLConnection connection = null;
		
		try {
			  url = new URL(targetURL);
			  connection = (HttpURLConnection)url.openConnection();
			
		    
		      connection.setRequestMethod("POST");
		    		    
		      connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
				
			    if(soapAction!="")
			    	connection.setRequestProperty("SOAP-Action",soapAction);
			    
			    connection.setRequestProperty("Content-Length", "" + Integer.toString(sRequest.getBytes().length));
			    connection.setRequestProperty("Content-Language", "en-US");  
					
			    connection.setUseCaches (false);
			    connection.setDoInput(true);
			    connection.setDoOutput(true);
		
			    //Send request
			    DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
			    wr.writeBytes (sRequest);
			    wr.flush ();
			    wr.close ();
		
			    //Get Response	
			    InputStream is = connection.getInputStream();
			    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			    String line;
			    
			    while((line = rd.readLine()) != null) {
			        response.append(line);
			        response.append('\r');
			    }
			    rd.close();
		} catch (UnknownHostException e) {
			//e.printStackTrace();
			Log.d("InvokeWS", "Invoke UnknownHostException " + e.getMessage());	
			System.out.println("Invoke UnknownHostException " + e.getMessage());
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			Log.d("InvokeWS", "Invoke MalformedURLException " + e.getMessage());
			System.out.println("Invoke MalformedURLException " + e.getMessage());
		 } catch (IOException e) {
			//e.printStackTrace();
			Log.d("InvokeWS", "Invoke IOException " + e.getMessage());
			System.out.println("Invoke IOException " + e.getMessage());
		 
		} catch (Exception e) {
			//e.printStackTrace();
			Log.d("InvokeWS", "Invoke Exception " + e.getMessage());
			System.out.println("Invoke Exception " + e.getMessage());
		 }
		 
		return response.toString();
	}
	
	public static String setRegId(String targetURL ,String sRegId) {
		
		String response = null; 
		String sRequest = null;
		String soapAction = null;
		
		try{
			Log.d("setRegId","START");
			
			//targetURL= "http://10.1.22.124/GCMWS/GCMWS";
			soapAction ="setRegID"; 
			sRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:mng=\"http://mng.gcm.eefrmwrk.tekniker.es/\">";
			sRequest = sRequest  + "<soapenv:Header/>";
			sRequest = sRequest  + "<soapenv:Body>";
			sRequest = sRequest  + "<mng:setRegID>";
			sRequest = sRequest  + "<RegID>"+sRegId+"</RegID>";
			sRequest = sRequest  + "</mng:setRegID>";
			sRequest = sRequest  + "</soapenv:Body>";
			sRequest = sRequest  + "</soapenv:Envelope>" ;
			
			response = InvokeWS(sRequest, targetURL, soapAction);
			
			Log.d("setRegId",response);
			
			/*
		}catch(SocketTimeoutException eTimeout){
			throw eTimeout;*/
		}catch(Exception e){
			String s=e.getMessage();
			Log.e(TAG, "Web Servic setRegId Exception " + e.getMessage());
			
		}
		Log.d("setRegId","END");
		
		return response;
	}

	public static boolean RegIdParse(String result) {
		
		boolean boolOK = false;
		
		boolOK = result.contains("<result>true</result>");
				
		return boolOK;
	}

}
