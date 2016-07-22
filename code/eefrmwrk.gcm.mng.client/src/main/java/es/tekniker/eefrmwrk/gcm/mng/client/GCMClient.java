package es.tekniker.eefrmwrk.gcm.mng.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import es.tekniker.eefrmwrk.gcm.mng.api.IGCMWS;

public class GCMClient {

	//private static final long TIMEOUT_DEFAULT = 10000;
	
	private static long TIMEOUT = 0; 
	private static String URL = "";

	private IGCMWS gcmInvoker =  null;
	
	private static Log log =LogFactory.getLog(GCMClient.class);
	
	public GCMClient(String url, long timeout) {
		this.URL=url;
		this.TIMEOUT=timeout;
		
		try {
			gcmInvoker =  getIGCMWS();
		} catch (Exception e) {
			log.error("CepClient constructor EXCEPTION at getICepManagerWS " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	private IGCMWS getIGCMWS() throws Exception {
		IGCMWS gcmInvoker =  null;
		try {
			
			log.debug("getIGCMWS START " );
			
			if (URL != null){
			
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				factory.setServiceClass(IGCMWS.class);
				factory.setAddress(URL);
				
				log.debug("getIGCMWS factory created ");
				
				gcmInvoker = (IGCMWS) factory.create();
				
				log.debug("getIGCMWS gcmInvoker created ");
				
				Client client = ClientProxy.getClient(gcmInvoker);
				
				if (client != null) {
					log.debug("getIGCMWS client created ");
					
					HTTPConduit conduit = (HTTPConduit) client.getConduit();
					HTTPClientPolicy policy = new HTTPClientPolicy();
					policy.setConnectionTimeout(TIMEOUT);
					policy.setReceiveTimeout(TIMEOUT);
					conduit.setClient(policy);
					
					log.debug("getIGCMWS policy created ");
				}else
					log.debug("getIGCMWS client is NULL ");
				
				
			}else
				log.error("getIGCMWS URL CEP is null . Event not sent to CEP");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getIGCMWS Exception " + e.getMessage());
		}
		return gcmInvoker;
	}
	
	
	
	public boolean sendMessage( String title, String message, String id) throws Exception {
		
		boolean result = false;
		try {
			
			log.debug("sendMessage START - title:  " +  title+ " message: " + message  + " id: " +id);
	
			
			if(gcmInvoker !=null){
				
				result = gcmInvoker.sendGCMmessage(title, message, id);
				
				if(result )
					log.debug("sendMessage addEvent OK");
				else
					log.error("sendMessage addEvent ERROR - title:  " +  title+ " message: " + message  + " id: " +id);
			}else
				log.error("sendMessage cepInvoker is NULL, no data sent to CEP");
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("sendMessage Exception " + e.getMessage());
		}
		return result;
		
	}
	
}