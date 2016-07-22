package es.tekniker.eefrmwrk.gcm.mng.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.android.gcm.server.MulticastResult;

import es.tekniker.eefrmwrk.gcm.GCMSender;
import es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration;
import es.tekniker.eefrmwrk.gcm.mng.db.GCMdb;

public class GCMserver {
	private static Log log =LogFactory.getLog(GCMserver.class);
	
	private static String GCM_COLLAPSE_KEY_DEFAULT ="EMS"; 
	private String gcmAuthToken = null;
	
	public GCMserver(String authToken){
		gcmAuthToken= authToken;
	}
	
	public boolean sendGCMmessage(String title, String message, String id){
		
		boolean boolOK = false;
		log.info("sendGCMmessage START ");
		
		GCMSender sender = null;
		String collapseKey = null;		
		GCMdb db = null;
		List<String> devices = null; 
		Gcmregistration[] regIds = null;
		MulticastResult result = null;		
		
		db = new GCMdb();
		regIds = db.getListRegistrationEnabled();
		
		if(regIds!= null){
			 		
			devices = new ArrayList<String>();			
			for (int i=0; i<regIds.length;i++){			
				devices.add(regIds[i].getRegistration_id());
				log.info("sendGCMmessage GCM regid " + regIds[i].getRegistration_id() );
			}		
		
			try {
				sender = new GCMSender(gcmAuthToken);
				collapseKey =GCMserver.GCM_COLLAPSE_KEY_DEFAULT;
				result = sender.doGCMPost(title ,message, id, devices , collapseKey);
				
				if(result !=null){
					log.info("sendGCMmessage GCM result is not null. result.getSuccess() value " + result.getSuccess());
					if (result.getSuccess()==1){				
						log.info("sendGCMmessage GCM result is not null. result.getSuccess() value is 1");
						boolOK =true;
					}
				}else{
					log.error("sendGCMmessage GCM result is null " );
				}
				
				if(boolOK)
					log.info("sendGCMmessage GCM sent OK ");
				else
					log.error("sendGCMmessage GCM sent ERROR ");
				
			} catch (Exception e) {
				log.error("sendGCMmessage Exception ", e );
			}
		}else
			log.info("sendGCMmessage no regIds");
				
		log.info("sendGCMmessage END ");
		
		return boolOK;
	}

	public boolean insertRegistrationId(String regID) {
		boolean boolOK = false;
		log.info("insertRegistrationId START ");
		
		GCMdb db = new GCMdb();
		
		Gcmregistration instance = db.getRegistrationIdByRegId(regID);
		
		if(instance != null){
			log.info("insertRegistrationId regID already exists ");
			boolOK =true;
		}else{
			log.info("insertRegistrationId regID not exists " + regID);
			boolOK = db.insertRegistrationId(regID );
			
			if(boolOK)
				log.info("insertRegistrationId GCM sent OK ");
			else
				log.error("insertRegistrationId GCM sent ERROR ");
			
			log.info("insertRegistrationId END ");
		}
		return boolOK;
		
	}
	
	
}
