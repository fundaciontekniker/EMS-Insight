package es.tekniker.eefrmwrk.prediction;

import java.util.Date;
import java.util.List;

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
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;

/**
 *  25/09/2014
 * Esta clase tiene doble proposito. Actua como modelo de los parametros a utilizar por AdvancedPredictor y gestiona el ciclo de vida de las repeticiones.
 * 
 * @author Alvaro Garcia
 *
 */
public class PredictorWorker implements Runnable {


	private static final Log log = LogFactory.getLog(PredictorWorker.class);
	
	public final static String Status_IDLE = "IDLE";
	public final static String Status_BUSY = "BUSY";

	private static final boolean INTERRUPT_ON_CEP_ERROR = false;  //TODO cargar de propiedades
	public long repeatInterval = 0; 
	
	public String status = Status_IDLE;
	
	
	//--
	public String predictorName;		
	
	//Parámetros carga de datos
	public List<String> varNames;	//Nombre de las variables a cargar
	public List<String> functions;	//Forma de obtener los valores por cada variable -> (avg, punctual, median)
	public Long initialDate;
	public Long finalDate;		
	public long periodicity;
	
	//Parámetros forecasting
	public String algorithm="LinearRegression";		//Default: LinearRegression
	public int steps=10;  			//Default: 10
	//--------------------------
	
	public List<Boolean> normalize;	//Lista de booleanos, indicando se se deben normalizar y renormalizar el rango de los valores.
									//Actualmente, simplemente divide entre el mayor valor, obteniendo valores entre 1 y 0.
									//Al Renormalizar, vuelve a multiplicar por el mayor valor. TODO Diferentes metodos de normalización
	//--
	private AdvancedPredictor aPred= new AdvancedPredictor();
	
	
	@Override
	public void run() {
		log.debug("PredictorWorker "+ predictorName +" cycle started");
		while (repeatInterval>0) {
			try{
				loopTask();
			}catch(Exception e){
				//TODO Tratamiento interrupción
				//    Interrumpido durante el bucle: Puede ser mientras espera o mientras trabaja
				e.printStackTrace();
				log.debug("Interrupted:"+e);
				repeatInterval=0;
				if (status.equals(Status_BUSY)){
					log.error("Predictor Interrupted abruptly");
					status=Status_IDLE;
				}
			}
		}
	}
 
	public void stop()  {
		log.debug("Stopping PredictorWorker "+ predictorName + " cycle");
		repeatInterval=0;
		if(status.equals(Status_IDLE)){ //Está esperando en el loop
			//TODO Thread.currentThread ().interrupt ();  //Concurencia/Bloqueo y otros
		}else
			log.debug("Waiting to finish task");
	};

	/**
	 * Looping task. After finished it will wait for timer. If tasks takes more
	 * than timer, it will wait for finishing  //TODO Timeout management
	 */
	private void loopTask() throws Exception{
		long startTime = System.currentTimeMillis();
		log.debug("["+ predictorName+ "] loop task: " + new Date(startTime));
		timeTask();
		long endTime = System.currentTimeMillis();
		if ((endTime - startTime) < repeatInterval) {
			Thread.sleep(repeatInterval - (endTime - startTime));
		}
	}

	private void timeTask() throws Exception{
		/*//CODIGO PRUEBA, espera entre 1  10 segundos
		int ki = (int) (Math.random() * (10000 - 1000) + 1000);
		try {Thread.sleep(ki);} catch (Exception e) {}
		System.out.println("["+ advPred.instanceName+"]Task finished in " + ki + "miliseconds");*/
		status=Status_BUSY;
		
		//CREATE MODEL wit new finalDate
		finalDate=System.currentTimeMillis();
		aPred.createModel(predictorName,varNames, initialDate, finalDate, periodicity, algorithm, functions, normalize);

		// PREDICT MODEL 
		List<VariableI> predList = aPred.predict(steps);
		
		// NOTIFY CEP
		try{
			long predictTime=System.currentTimeMillis();
			for(VariableI vI:predList){
				sendEvent(vI.getName(),vI.getValue(),vI.getTimestamp(),predictTime);
			}
		}
		catch(Exception e){
			throw new Exception("Error notifying CEP");
		}
		status=Status_IDLE;
	}
	
	
	
	private static long TIMEOUT =60000;											//TODO Cargar de propiedades
	private static String url="http://CepLivingLab/CepManagerWS/CepManagerWS?wsdl";  //TODO Cargar de propiedades
	private static boolean sendEvent(String varName, String value,long timestamp,long predictDate) throws BaseException {
			
		boolean result = true;
		try {
			JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
			factory.getInInterceptors().add(new LoggingInInterceptor());
			factory.getOutInterceptors().add(new LoggingOutInterceptor());
			factory.setServiceClass(ICepManagerWS.class);
			factory.setAddress(url);
			ICepManagerWS cepInvoker = (ICepManagerWS) factory.create();
			Client client = ClientProxy.getClient(cepInvoker);
			if (client != null) {
				HTTPConduit conduit = (HTTPConduit) client.getConduit();
				HTTPClientPolicy policy = new HTTPClientPolicy();
				policy.setConnectionTimeout(TIMEOUT);
				policy.setReceiveTimeout(TIMEOUT);
				conduit.setClient(policy);
			}
			result = cepInvoker.addPredictionEvent(varName, value, timestamp,predictDate);
		} catch (Exception e) {
			if(INTERRUPT_ON_CEP_ERROR)
				throw new BaseException("CEPListener_SENDEVENT0","Error sending event to CEP:" + e.getMessage(), e);	
			else
				log.error("Error notifying CEP",e);
					
		}
		return result;
	}

	/**
	 * Includes getting data interpolated and normalized and building the arff file.
	 */
	public void createModel()throws Exception {
		status=Status_BUSY;
		aPred.createModel(predictorName,varNames, initialDate, finalDate, periodicity, algorithm, functions, normalize);
		status=Status_IDLE;
	}


	/**
	 * Makes a prediction for the instances that are loaded currently on the
	 * model, notifies CEP and returns it.
	 * @param notifyCEP 
	 * 
	 * @param Integer Time-steps to predict
	 * @return List<VariableI> the complete instance list as variables
	 * @throws Exception
	 */
	public List<VariableI> predict(Boolean notifyCEP) throws Exception  {
		status=Status_BUSY;
		List<VariableI> pList=aPred.predict(steps);
		
		if(notifyCEP)
			try{
			long predictTime=System.currentTimeMillis();
			for(VariableI vI:pList){
				sendEvent(vI.getName(),vI.getValue(),vI.getTimestamp(),predictTime);
			}
			}
			catch(Exception e){
				if(INTERRUPT_ON_CEP_ERROR)
					throw new Exception("Error notifying CEP",e);
				else
					log.error("Error notifying CEP",e);
		}
		
		status=Status_IDLE;
		return pList;
	}


	/**
	 * Returns the prediction that is currently loaded on the forecaster.
	 * 
	 * @param List<String> Names of the variables to return. If empty, ALL variables are returned
	 * @return List<<VariableI> The predicted values
	 * @throws WSException 
	 */
	public List<VariableI> getPrediction()  throws Exception {
		return aPred.getPrediction();
	}

	public List<VariableI> getModelVariables() throws Exception {
		return aPred.getSampledData();
	}	
	public String getArffModel() throws Exception {
		return aPred.getArffModel();
	}

}
