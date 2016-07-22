package es.tekniker.eefrmwrk.cepmngr.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import es.tekniker.eefrmwrk.cepmngr.ICepManagerWS;

public class CepClient {
	
	private static long TIMEOUT = 0; 
	private static String URL = "";
	
	private ICepManagerWS cepInvoker =  null;
	
	private static Log log =LogFactory.getLog(CepClient.class);
	
	public CepClient(String url, long timeout) {
		CepClient.URL=url;
		CepClient.TIMEOUT=timeout;
		
		try {
			cepInvoker =  getICepManagerWS();
		} catch (Exception e) {
			log.error("CepClient constructor EXCEPTION at getICepManagerWS " + e.getMessage());
			e.printStackTrace();
		}
	}	
	
	private ICepManagerWS getICepManagerWS() throws Exception {
		ICepManagerWS cepInvoker =  null;
		try {
			
			log.debug("getICepManagerWS START " );
			
			if (URL != null){
			
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				factory.setServiceClass(ICepManagerWS.class);
				factory.setAddress(URL);
				
				log.debug("getICepManagerWS factory created ");
				
				cepInvoker = (ICepManagerWS) factory.create();
				
				log.debug("getICepManagerWS cepInvoker created ");
				
				Client client = ClientProxy.getClient(cepInvoker);
				
				if (client != null) {
					log.debug("getICepManagerWS client created ");
					
					HTTPConduit conduit = (HTTPConduit) client.getConduit();
					HTTPClientPolicy policy = new HTTPClientPolicy();
					policy.setConnectionTimeout(TIMEOUT);
					policy.setReceiveTimeout(TIMEOUT);
					conduit.setClient(policy);
					
					log.debug("getICepManagerWS policy created ");
				}else
					log.debug("getICepManagerWS client is NULL ");
				
				
			}else
				log.error("getICepManagerWS URL CEP is null . Event not sent to CEP");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getICepManagerWS Exception " + e.getMessage());
		}
		return cepInvoker;
	}
	
	
	public boolean sendPredictionEvent(String varName, String value, long timestamp, long predictDate) throws Exception {
		//value: Temp predicha, timestamp: fecha para la cual se predice el value, predictDate: Fecha de creacion de predicciones
		boolean result = false;
		try {
			
			log.debug("sendEvent START - value:  " + value + " timestamp: " + timestamp + " predictDate: " +predictDate);
						
			if (varName != null){
				if(cepInvoker !=null){
					result = cepInvoker.addPredictionEvent(varName, value, timestamp, predictDate);
					
					if(result )
						log.debug("sendEvent addPredictionEvent OK");
					else
						log.error("sendEvent addPredictionEvent ERROR - value:  " + value + " timestamp: " + timestamp + " predictDate: " +predictDate);
				}else
					log.error("sendEvent cepInvoker is NULL, no data sent to CEP");
			}
			else
				log.error("sendEvent varName is null . Event not sent to CEP");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("sendEvent Exception " + e.getMessage());
		}
		return result;
	}	
	
	public boolean sendEvent( String varName, String value, long timestamp) throws Exception {
		boolean result = false;
		try {
			
			log.debug("sendEvent START - value:  " + value + " timestamp: " + timestamp + " varName: " +varName);
	
			if(cepInvoker !=null){
				result = cepInvoker.addEvent(varName, value, timestamp);
				
				if(result )
					log.debug("sendEvent addEvent OK");
				else
					log.error("sendEvent addEvent ERROR - value:  " + value + " timestamp: " + timestamp + " varName: " +varName);
			}else
				log.error("sendEvent cepInvoker is NULL, no data sent to CEP");
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("sendEvent Exception " + e.getMessage());
		}
		return result;
		
	}
	
}