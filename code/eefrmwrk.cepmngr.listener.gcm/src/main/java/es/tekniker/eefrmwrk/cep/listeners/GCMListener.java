package es.tekniker.eefrmwrk.cep.listeners;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;

import es.tekniker.eefrmwrk.cep.entity.CepEvent;
import es.tekniker.eefrmwrk.cep.entity.CepPrediction;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.config.ConfigFile;
import es.tekniker.eefrmwrk.gcm.mng.client.GCMClient;

/**
 * Obtiene la informacion de una variable a partir de un evento CEP y la
 * envia como mensaje GCM
 */
public class GCMListener implements AbstractCEP_Listener {
	private RuleEntity rule;
	private String GCMMessageTitle="GCMListener";
	private String GCMMessageId;
	
	private String GCMurl = null;
	private long TIMEOUT;
	private long TIMEOUT_DEFAULT=5000;
	
	private String configurationFile ="gcm.config.properties";
	private GCMClient client = null; 
	
	private static final Log log = LogFactory.getLog(GCMListener.class);
	
	public GCMListener(RuleEntity rule) throws IOException {
		log.debug("GCMListener created");
		this.rule=rule;
		
		initConfigurationData();
	}

	private boolean initConfigurationData() throws IOException{
		boolean boolOK= false;
		
		ConfigFile configFile =null;
		String stringTimeout = null;
		
		try {
			configFile = new ConfigFile(configurationFile);
						
			GCMurl = configFile.getStringParam("gcm.client.url");			
			if(GCMurl != null){				
				log.info("GCMurl: " + GCMurl);
			}else{					
				log.error("GCMurl is NULL, please review configuration file" );
			}
			
			stringTimeout = configFile.getStringParam("gcm.client.timeout");
			
			if(stringTimeout != null){
				TIMEOUT = Long.valueOf(stringTimeout);
				log.info("timeout: " + TIMEOUT);
			}else{
				TIMEOUT = TIMEOUT_DEFAULT;	
				log.info("timeout set DEFAULT: " + TIMEOUT);
			}
			
			boolOK = true;
			
		} catch (IOException e) {
			e.printStackTrace();
			log.error("iniGetConfigurationParameters : Configuration file " + configurationFile+ " not found.", e);
			throw e;			
		}
		
		return boolOK;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {	
		boolean boolSend  = false;
		String value=null, varName=null;
		String timeString = null;
		String message = null;
		
		log.debug("Updating with GCMListener ["+rule.getCepName()+"]");
		if (newData != null){
			
			try{
				for (EventBean eb : newData) {
					for(String s:eb.getEventType().getPropertyNames()){
						log.debug(s+" : "+eb.get(s));
					}
					
					if(newData[0].getUnderlying() instanceof CepEvent){					
						CepEvent cE = (CepEvent) newData[0].getUnderlying();
						
						Date dateTime = new Date( cE.getTimestamp());
						timeString = dateTime.toGMTString();
						
						value=cE.getValue()+"";
						varName=cE.getVarName();
					}
					if(newData[0].getUnderlying() instanceof CepPrediction){					
						CepPrediction cE = (CepPrediction) newData[0].getUnderlying();
						
						Date dateTime = new Date( cE.getTimestamp());
						timeString = dateTime.toGMTString();
						
						value=cE.getValue()+"";
						varName=cE.getVarName();
					}
					
					if(timeString != null){
						message = timeString;
					}else{							
						timeString = new Date().toGMTString();							
						message = timeString;
					}
					if(message != null){
						message= message + " - " +rule.getCepMessage();
					}else{
						message= rule.getCepMessage();
					}
					
					if(message!=null && !message.isEmpty()){
						
						if(varName != null)
							message = message + " " + varName;
						
						if(value != null)
							message = message + " : " + value;
						
						
						
						log.info(message);
						GCMMessageId =  String.valueOf(new Random().nextDouble());
						
						boolSend = sendDataToGCM(GCMMessageTitle, message,GCMMessageId);
						
						if(boolSend)
							log.debug("GCMListener update Message sent ok . Message " +message + " id : " +GCMMessageId);
						else
							log.error("GCMListener update Message sent ERROR . Message " +message + " id : " +GCMMessageId);
						
					}			
				}
			}catch (Exception e){
				rule.setStatus(RuleEntity.status_UP_FAIL);
				rule.setInfo(e.getMessage());
			}
		}
	}
	
	//Interval methods
	private boolean sendDataToGCM(String title, String message, String id){
		boolean boolOK = false;		
		
        try {
        	if(client == null)
        		client = new GCMClient (GCMurl, TIMEOUT);
        	boolOK =client.sendMessage(title, message, id);
        	
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        
        return boolOK;
	}

	public boolean checkRule(String epl) throws BaseException {
		// TODO Auto-generated method stub
		return true;
	}	
	
}