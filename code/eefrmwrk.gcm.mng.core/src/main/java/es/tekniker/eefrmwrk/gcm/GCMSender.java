package es.tekniker.eefrmwrk.gcm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

/**
 * GCMSender class to manage the Sending of mesages to GCM
 * @author Ignacio Lazaro Llorente IK4-Tekniker 2016
 *
 */
public class GCMSender {
	
	private static Log log =LogFactory.getLog(GCMSender.class);
	
	// Define the Maximun number of devices per message to be sent to GCM
	public static int GCM_NUM_MAX_DEVICES_PER_MESAGE=1000;
		
	//Define the GCM Url to send the GCM messages
    private static String gcmUrl = "https://android.googleapis.com/gcm/send";
    
    //Define the AuthToken asocciated to the google account defined for the Application
    //https://code.google.com/apis/console
    //APIS&Auth / Credentials / Public API access / key for server applications / API KEY
   
    private static String gcmAuthToken = null;
                                            
	public  GCMSender(String authToken) throws Exception{
		this.gcmAuthToken=authToken;
		
		if(gcmAuthToken==null)
			throw new Exception("gcmAuthToken is null");
		
	}
	
	public  MulticastResult doGCMPost(GCMmessage gcmMessage,List<String> devices) {
		return doGCMPost(gcmMessage.getTitle(), gcmMessage.getMessage(),gcmMessage.getId(),devices,gcmMessage.getCollapseKey());
	}
	
	/*
	 * Function to send to GCM the messages based on the received parameters
	 */
	public MulticastResult doGCMPost(String title ,String message, String id, List<String> devices, String collapseKey ) {
		MulticastResult result = null;
	    String senderId =gcmAuthToken;
	    String apiKey = gcmAuthToken;
	    boolean boolDebugMoreInfo=true;
	    
	    if (senderId != null && !"".equalsIgnoreCase(senderId)) {
	                
	        result = sendMessageToDevice(apiKey, devices, title, message, id, gcmUrl, collapseKey);
	        
	        log.info("GCM Message id: " + id + " number of devices:" + devices.size() + " Result is " + result);
	        if(boolDebugMoreInfo){
	        	log.debug("id: " + id);
	        	log.debug("title: " + title);	        
	        	log.debug("message:" + message);
	        	log.debug("url: " + gcmUrl);
	        	log.debug("collapseKey: " + collapseKey);	                
	        	log.debug("Message sent to this number of devices:" + devices.size());
	        	log.debug("This API key was used: " + apiKey);
	        	log.debug("The result is: " + result);
	        }
	    } else {
	    	log.error("Please provide your senderId in the URL parameter. Something like this: http://gcm4public.appspot.com/registeredgcmclients?senderId=716163315987");
	    }
	    
	    return result;
	}

	private static MulticastResult sendMessageToDevice(String apiKey, List<String> devices, String title,
		String message, String id, String url, String collapseKey) {
		Sender sender = new Sender(apiKey);
	    Message gcmMessage = new Message.Builder().addData("id", id)
	    										  .addData("title", title)
	    										  .addData("message", message)
	    										  .addData("url", url)
	    										  .addData("collapse_key",collapseKey)
	    										  .build();
	    MulticastResult result = null;
	    try {
	    	result = sender.send(gcmMessage, devices, 5);
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
	    return result;
	}

	/**
	 * Example Function to send message to GCM
	 */
	public static void doGCMPostExample(){
		String title = "EMS Servicio Notificacion";
	    String message = "Hay una alarma en el dispositivo 1113";
	    String id= "EMS.EEF-1113";
		    
	    //Review this value before test
	    String gcmRegID = "xxRegID";	    
	    String gcmAuthToken ="xxAPIKEY";
	    List<String> devices = new ArrayList<String>();
	    devices.add(gcmRegID);
	    
	    Random rand= new Random();
	    String collapseKey="EM";
	    
	    collapseKey=collapseKey+rand.nextInt();     
    	
	    GCMSender sender;
		try {
			sender = new GCMSender(gcmAuthToken);
			//sender.doGCMPost(title ,message, id, devices , collapseKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public static void main( String[] args ){
		doGCMPostExample();
	}
	
}
