package es.tekniker.eefrmwrk.prediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
import es.tekniker.eefrmwrk.commons.PredictorI;
import es.tekniker.eefrmwrk.commons.PredictorVariableI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.database.nosql.HectorClass;
import es.tekniker.eefrmwrk.database.nosql.HectorFactory;

public class PredictionWS implements IPredictionWS {

	private static final Log log = LogFactory.getLog(PredictionWS.class);
	private final String RETURN_OK = "RETURN_OK";

	PredictorManager pre_man = new PredictorManager();
	
	public PredictionWS(){
		//Leer fichero configuracion
		
		//añadir predictores pre configurados (UNO DE LOS PREDICTORES DEBERÁ SER Actual_Variables) 	
		
		Properties prop = new Properties();
		InputStream input = null;
		try {
			prop.load(getClass().getClassLoader().getResourceAsStream("predictors.conf"));

			for(Object s:prop.keySet()){
				try{
					String[] args =prop.get(s).toString().split(",");
					String predictorName =s.toString();
					Long initialDate=Long.parseLong(args[0]);
					Long finalDate=Long.parseLong(args[1]);
					Long periodicity=Long.parseLong(args[2]);
					String algorithm=args[3].trim();
					Integer steps=Integer.parseInt(args[4]);
					
					List<String> varNames= new ArrayList<String>();
					//List<String> funcNames= new ArrayList<String>();
					//ist<Boolean> normalize= new ArrayList<Boolean>();
					for(int i=5;i<args.length;i++){
						varNames.add(args[i]);
						//funcNames.add("medians");
						//normalize.add(false);
					}
					//addPredictor(predictorName, varNames, initialDate, finalDate, periodicity, funcNames, normalize, algorithm, steps);
					addPredictor(predictorName, varNames, initialDate, finalDate, periodicity, null, null, algorithm, steps);
				}catch(Exception e){
					log.error("Could not load predictor "+ s ,e);
				}
				}
				} catch (IOException ex) {
					log.error("Exception loading predictors:",ex);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					log.error("Exception loading predictors",e);
				}
			}
		}
	}
	
	
	
		
	@Override
	public String addPredictor(String predictorName, List<String> varNames,
			Long initialDate, Long finalDate, Long periodicity,
			List<String> funcNames, List<Boolean> normalize, String algorithm,
			Integer steps) throws WSException {

		log.debug("Create model");
		if (predictorName == null || predictorName.equals(""))
			throw new WSException("PredictionWS_CREATEPREDICTOR1",
					"PredictorName can't be null");

		if (varNames == null || varNames.isEmpty())
			throw new WSException("PredictionWS_CREATEPREDICTOR2",
					"No variables selected");

		if (initialDate == null) {
			initialDate = 1000L;
		}
		if (finalDate == null) {
			finalDate = System.currentTimeMillis();
		}
		if (initialDate > finalDate) {
			throw new WSException("PredictionWS_CREATEPREDICTOR3",
					"finalDate is later than initialDate, should be previous");
		}

		// TODO Inicializadores aqui o en PredictorManager?
		if (periodicity == null)
			periodicity = 3600000L; // DEFAULT: 1 hora
		if (periodicity < 1)
			throw new WSException("PredictionWS_CREATEPREDICTOR4",
					"Periodicity needs to be a positive number");

		if (funcNames == null) {
			funcNames = new ArrayList<String>();
			for (String s : varNames)
				funcNames.add("median"); // DEFAULT: medians
		}
		// TODO: Comprobar funciones interpolacion validas?

		if (varNames.size() != funcNames.size())
			throw new WSException("PredictionWS_CREATEPREDICTOR5",
					"There has to be a interpolation function for each variable");

		if (normalize == null) {
			normalize = new ArrayList<Boolean>();
			for (String s : varNames)
				normalize.add(false); // DEFAULT: sin normalizar
		}
		if (varNames.size() != normalize.size())
			throw new WSException("PredictionWS_CREATEPREDICTOR5",
					"There has to be a normalization flag for each variable");

		if (algorithm == null)
			algorithm = "LinearRegression";
		// TODO: Comprobar algoritmos validos?

		if (steps == null || steps < 1)
			throw new WSException("PredictionWS_CREATEPREDICTOR6",
					"Steps to predict must be a positive integer");

		try {
			pre_man.addPredictor(predictorName, varNames, initialDate,
					finalDate, periodicity, funcNames, normalize, algorithm,
					steps);
			return RETURN_OK;
		} catch (BaseException e) {
			throw new WSException("PredictionWS_CREATEPREDICTOR0",
					"Exception creating model", e);
		}
	}

	@Override
	public String editPredictor(String predictorName, List<String> varNames,
			Long initialDate, Long finalDate, Long periodicity,
			List<String> funcNames, List<Boolean> normalize, String algorithm,
			Integer steps) throws WSException {
		log.debug("Create model");
		if (predictorName == null || predictorName.equals(""))
			throw new WSException("PredictionWS_EDITPREDICTOR1",
					"PredictorName can't be null");

		if (varNames == null || varNames.isEmpty())
			throw new WSException("PredictionWS_EDITPREDICTOR2",
					"No variables selected");

		if (initialDate == null) {
			initialDate = (long) 0;
		}
		if (finalDate == null) {
			finalDate = System.currentTimeMillis();
		}
		if (initialDate > finalDate) {
			throw new WSException("PredictionWS_EDITPREDICTOR3",
					"finalDate is later than initialDate, should be previous");
		}

		// TODO Inicializadores aqui o en PredictorManager?
		if (periodicity == null)
			periodicity = 3600000L; // DEFAULT: 1 hora
		if (periodicity < 1)
			throw new WSException("PredictionWS_EDITPREDICTOR4",
					"Periodicity needs to be a positive number");

		if (funcNames == null) {
			funcNames = new ArrayList<String>();
			for (String s : varNames)
				funcNames.add("median"); // DEFAULT: medians
		}
		// TODO: Comprobar funciones interpolacion validas?

		if (varNames.size() != funcNames.size())
			throw new WSException("PredictionWS_EDITPREDICTOR5",
					"There has to be a interpolation function for each variable");

		if (normalize == null) {
			normalize = new ArrayList<Boolean>();
			for (String s : varNames)
				normalize.add(false); // DEFAULT: sin normalizar
		}
		if (varNames.size() != normalize.size())
			throw new WSException("PredictionWS_EDITPREDICTOR5",
					"There has to be a normalization flag for each variable");

		if (algorithm == null)
			algorithm = "LinearRegression";
		// TODO: Comprobar algoritmos validas?

		if (steps == null || steps < 1)
			throw new WSException("PredictionWS_EDITPREDICTOR6",
					"Steps to predict must be a positive integer");
		try {
			pre_man.editPredictor(predictorName, varNames, initialDate,
					finalDate, periodicity, funcNames, normalize, algorithm,
					steps);
			return RETURN_OK;
		} catch (BaseException e) {
			throw new WSException("PredictionWS_EDITPREDICTOR0",
					"Exception creating model", e);
		}
	}

	@Override
	public String deletePredictor(String predictorName) throws WSException {
		if (predictorName == null || predictorName.equals(""))
			throw new WSException("PredictionWS_DELETEPREDICTOR1",
					"PredictorName can't be null");
		try {
			pre_man.deletePredictor(predictorName);
			return RETURN_OK;
		} catch (BaseException e) {
			throw new WSException("PredictionWS_DELETEPREDICTOR0",
					"Exception creating model", e);
		}
	}

	@Override
	public List<VariableI> predict(String predictorName, Integer steps,
			Boolean notifyCEP) throws WSException {
		log.info("Predict");
		if (steps == null)
			steps = 1;
		if (steps < 1)
			throw new WSException("PredictionWS_PREDICT1",
					"Steps needs to be more a positive Integer 1");
		if (notifyCEP == null)
			notifyCEP = false;
		try {
			return pre_man.predict(predictorName, steps, notifyCEP);
		} catch (Exception e) {
			throw new WSException("PredictionWS_PREDICT0",
					"Exception doing the prediction", e);
		}
	}

	@Override
	public List<VariableI> getSampledData(String predictorName)
			throws WSException {
		log.debug("Get sampled data");
		try {
			return pre_man.getSampledData(predictorName);
		} catch (Exception e) {
			throw new WSException("PredictionWS_GETSAMPLEDDATA0",
					"Exception getting sampled data", e);
		}
	}

	@Override
	public List<VariableI> getLastPrediction(String predictorName)
			throws WSException {

		try {
			return pre_man.getPrediction(predictorName);
		} catch (Exception e) {
			throw new WSException("PredictionWS_GETPREDICTION0",
					"Exception getting the prediction", e);
		}
	}

	// ---------------------------
	@Override
	public String startPredictorCycle(String predictorName, Long repeatInterval)
			throws WSException {
		if (predictorName == null || predictorName.equals(""))
			throw new WSException("PredictionWS_STARTPREDICTORCYCLE1",
					"PredictorName can't be null");
		if (repeatInterval == null || repeatInterval < 0)
			throw new WSException("PredictionWS_STARTPREDICTORCYCLE2",
					"Interval must be a positive number (in miliseconds)");
		try {
			pre_man.startCycle(predictorName, repeatInterval);
			return RETURN_OK;
		} catch (BaseException e) {
			throw new WSException("PredictionWS_STARTPREDICTORCYCLE0",
					"Exception starting prediction cycle", e);
		}
	}

	@Override
	public String stopPredictorCycle(String predictorName) throws WSException {
		if (predictorName == null || predictorName.equals(""))
			throw new WSException("PredictionWS_STOPPREDICTORCYCLE1",
					"PredictorName can't be null");
		try {
			pre_man.stopCycle(predictorName);
			return RETURN_OK;
		} catch (BaseException e) {
			throw new WSException("PredictionWS_STOPPREDICTORCYCLE0",
					"Exception stopping prediction cycle", e);
		}
	}

	// -------------------

	@Override
	public List<PredictorI> getLoadedPredictors() throws WSException {
		try {
			return pre_man.getLoadedPredictors();
		} catch (Exception e) {
			throw new WSException("PredictionWS_GETLOADEDPREDICTORS0",
					"Exception getting predictors", e);
		}
	}

	@Override
	public PredictorI getPredictorInfo(String predictorName) throws WSException {
		try {
			return pre_man.getPredictorInfo(predictorName);
		} catch (Exception e) {
			throw new WSException("PredictionWS_GETPREDICTORINFO0",
					"Exception getting predictor info", e);
		}
	}

	@Override
	public List<PredictorVariableI> getPredictorVariables(String predictorName)
			throws WSException {
		try {
			return pre_man.getPredictorVariables(predictorName);
		} catch (Exception e) {
			throw new WSException("PredictionWS_GETPREDICTORVARIABLES0",
					"Exception getting predictor variables", e);
		}
	}

	// ---------------------------------------------------

	private static String readInput() {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String s = null;
		do
			try {
				s = stdin.readLine();
			} catch (IOException e) {
				System.err.println(e);
			}
		while ((s == null) || (s.length() == 0));
		return s;
	}

	public static void main(String[] args) {
		PredictionWS pm = new PredictionWS();

		do {
			System.out.println("-------------------------------------");
			System.out.println("-  Pulsa x para salir               -");
			System.out.println("-------------------------------------");
			String input = readInput();
			if (input.equals("x"))
				break;
			else {
				try {
					String[] commands = input.split(" ");
					if (commands[0].equals("1")) {

						String predictorName = commands[1];
						ArrayList<String> varNames = new ArrayList<String>();
						varNames.add("Var1");
						ArrayList<String> functions = new ArrayList<String>();
						functions.add("avg");
						long initialDate = System.currentTimeMillis()
								- (5 * 24 * 60 * 60 * 1000);
						long finalDate = System.currentTimeMillis();
						long periodicity = 60 * 60 * 1000;
						ArrayList<Boolean> normalize = new ArrayList<Boolean>();
						normalize.add(false);
						String algorithm = "LinearRegression";
						int steps = 5;

						pm.addPredictor(predictorName, varNames, null, null,
								null, functions, normalize, null, steps);

					} else if (commands[0].equals("2")) {
						pm.predict(commands[1], 5, false);

						for (VariableI vI : pm.getLastPrediction(commands[1])) {
							System.out.println(vI.getName() + ":"
									+ vI.getValue() + "["
									+ new Date(vI.getTimestamp()) + "]");
						}

					} else if (commands[0].equals("3")) {
						pm.startPredictorCycle(commands[1], 30000L);
					} else if (commands[0].equals("4")) {
						pm.stopPredictorCycle(commands[1]);
					} else if (commands[0].equals("i")) {
						PredictorI pI = pm.getPredictorInfo(commands[1]);
						System.out.println(pI.getPredictorName() + " Status:"
								+ pI.getStatus() + " Cycle:"
								+ pI.getRepeatInterval());
					}
				} catch (Exception e) {
					e.printStackTrace();

				}
			}

		} while (true);

	}
}
