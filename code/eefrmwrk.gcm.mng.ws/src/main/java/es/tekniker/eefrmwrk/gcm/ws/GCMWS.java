package es.tekniker.eefrmwrk.gcm.ws;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.gcm.mng.api.IGCMWS;
import es.tekniker.eefrmwrk.gcm.mng.server.GCMserver;

public class GCMWS implements IGCMWS {
	
	private static final Log log = LogFactory.getLog(GCMWS.class);
	
	private String configurationFile = "gcm.config.properties";
	private String gcmAuthToken = null;
	
	public GCMWS() throws Throwable {				
		initGetConfigurationParameters();	
	}
	
	public boolean setRegID(String regID) throws WSException{
		boolean boolOK = false;
		log.info("setRegID START ");						
				
		if (regID !=null){
			log.info("setRegID RegID value : " +regID);
			
			GCMserver server = new GCMserver(gcmAuthToken);
			boolOK = server.insertRegistrationId(regID );
			
			if(boolOK){
				log.debug("insertRegistrationId is OK " );
			}else{
				log.error("insertRegistrationId is NOK");
			}
		}else
			log.error("setRegID RegID value is null ");
		
		log.info("setRegID END ");
		boolOK = true;
		return boolOK;
	}

	public boolean sendGCMmessage(String title, String message, String id ){
		
		boolean boolOK = false;
		log.info("sendGCMmessage START ");
		
		if (title !=null && message != null && id != null){
		
			GCMserver server = new GCMserver(gcmAuthToken);		
			boolOK= server.sendGCMmessage(title, message, id);
			
			if(boolOK){
				log.debug("sendGCMmessage is OK " );
			}else{
				log.error("sendGCMmessage is NOK");
			}
		}else{
			log.error("sendGCMmessage required parameters not defined");
		}
		
		log.info("sendGCMmessage END ");
		boolOK = true;
		return boolOK;
	}


	private boolean initGetConfigurationParameters() throws Exception{		
		boolean boolOK = false;
		ConfigFile configFile =null;
		
		try {
			configFile = new ConfigFile(configurationFile);
			
			gcmAuthToken = configFile.getStringParam("gcm.authtoken");			
			if(gcmAuthToken != null){
				log.info("gcmAuthToken: " + gcmAuthToken);
				boolOK= true;
			}else{					
				log.error("gcmAuthToken is NULL define the required value");
				
				throw new Exception("gcm.authtoken is NULL define the required value");
			}
		} catch (IOException e) {
			e.printStackTrace();
			log.error("gcm.properties file not found.", e);
		}
		
		return boolOK;
	}
	
}

