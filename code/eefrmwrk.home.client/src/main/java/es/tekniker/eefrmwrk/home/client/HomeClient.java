package es.tekniker.eefrmwrk.home.client;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.home.IHomeWS;

public class HomeClient {
	
	private static long TIMEOUT = 0; 
	private static String URL = "";

	private IHomeWS homeInvoker =  null;
	
	private static Log log =LogFactory.getLog(HomeClient.class);
	
	public HomeClient(String url, long timeout) throws Exception {
		this.URL=url;
		this.TIMEOUT=timeout;
		
		homeInvoker =  getIHomeWS();
		
	}

	public IHomeWS getIHomeWS() throws Exception {
		IHomeWS homeInvoker =  null;
		try {
			
			log.debug("getIHomeWS START " );
			
			if (URL != null){
			
				JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
				factory.getInInterceptors().add(new LoggingInInterceptor());
				factory.getOutInterceptors().add(new LoggingOutInterceptor());
				factory.setServiceClass(IHomeWS.class);
				factory.setAddress(URL);
				
				log.debug("getIHomeWS factory created ");
				
				homeInvoker = (IHomeWS) factory.create();
				
				log.debug("getIHomeWS homeInvoker created ");
				
				Client client = ClientProxy.getClient(homeInvoker);
				
				if (client != null) {
					log.debug("getIHomeWS client created ");
					
					HTTPConduit conduit = (HTTPConduit) client.getConduit();
					HTTPClientPolicy policy = new HTTPClientPolicy();
					policy.setConnectionTimeout(TIMEOUT);
					policy.setReceiveTimeout(TIMEOUT);
					conduit.setClient(policy);
					
					log.debug("getIHomeWS policy created ");
				}else
					log.debug("getIHomeWS client is NULL ");
				
				
			}else
				log.error("getIHomeWS URL HOME is null .  not sent to Home");
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getIHomeWS Exception " + e.getMessage());
		}
		return homeInvoker;
	}
	
		
	public VariableI getVariableValue( String varName) throws Exception {
		
		VariableI result = null;
		try {
			
			log.debug("getVariableValue START - varName: " +varName);
			if(homeInvoker != null){
				result = homeInvoker.getVariableValue(varName, null);
				
				if(result != null)
					log.debug("getVariableValue OK");
				else
					log.error("getVariableValue ERROR - varName: " +varName);
			}else{
				log.error("addValue homeInvoker is null" );
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error("getVariableValue Exception " + e.getMessage());
		}
		
		log.debug("getVariableValue END - varName: " +varName);
		return result;
		
	}	
	
	public boolean addValue(String varName, String value, long timestamp){
		boolean boolOK =false;
		String sValue = null;
		try {
			
			log.debug("addValue START - varName: " +varName + " value: " + value + " timestamp: " +timestamp );			
	
			if(homeInvoker != null){						
				sValue = homeInvoker.addValue(varName, value, timestamp);			
				if(sValue != null){
					log.debug("addValue Variable is not null" + sValue);
					boolOK =true;
				}
				else{ 
					log.error("addValue Variable is null" );
					boolOK =false;
				}
			}else{
				log.error("addValue homeInvoker is null" );
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("addValue Exception " + e.getMessage());
		}

		log.debug("addValue END - varName: " +varName + " value: " + value + " timestamp: " +timestamp );
		
		return boolOK;
	}
	
}