package es.tekniker.eefrmwrk.prediction;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import weka.classifiers.evaluation.NumericPrediction;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.VarMetadataI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;


//clases accesibles

/**
 * 
 * Las llamadas se utilizan mediante la clase
 * PredictorWS.(effrmwrk.prediction.ws)
 * 
 * 
 * 
 * 1 -Integrar analyzer, sampleo, carga de instancias ; -añadir parametro para
 * método de agregación: puntual, mediana... 2 -Comprobar funcionamiento del
 * TSLAG y probar diferentes configuraciones 3- Comprobar número de instancias
 * que puede soportar, y tiempos (crear el modelo no suele dar problemas, la
 * función forecaster.forecast si) 4- SOA4AMI-UI es la apliación web (está en
 * subversive). Utiliza html y javascript para gráficar. -Echar un vistazo para
 * familiarizarse cómo se utilizan las llamadas SOAP (en los scripts, para
 * entender cómo se utiliza)
 */



public class AdvancedPredictor {

	// Instances model;
	private DataManager dataManager;
	private Forecaster forecaster;
	private static final Log log = LogFactory.getLog(AdvancedPredictor.class);

//	TODO 26/09/2014    getPrediction y getSampledData tienen un tiempo de ejecución relativamente alto debido a que 
//	acceden a la base de datos cada vez que se ejecutan. Se han añadido dos colecciones de variablesI para reducir el numero de accesos a BBDD 
//  SampledData se carga con createModel() y LastPrediction con predict().  Aqui o mover dentro de dataManager

	private List<VariableI> sampledData = new ArrayList<VariableI>();
	private List<VariableI> lastPrediction = new ArrayList<VariableI>();
	
	/**
	 * Constructor.
	 * */
	public AdvancedPredictor() {
		dataManager = new DataManager();
		forecaster = new Forecaster();
	}

	/**
	 * Creates a model of the named variables' values between the given
	 * initial-date and the final-date. Timesteps depend on the input
	 * periodicity. It uses the median of a variable's values in a time-period.
	 * Builds the model using the bagging algorithm, which uses the RepTree on
	 * the inside.
	 * @param String The object to convert into instances (timeSeries or vars)
	 * @param List
	 *            <String> A list containing the names of the variables to use
	 *            as attributes.
	 * @param Long
	 *            The initial-date
	 * @param Long
	 *            The final-date
	 * @param Long
	 *            The periodicity that it will be used to divide the total time
	 *            into time-intervals
	 *@param String The name of the algorithm to use
	 *@param List<String> A list containgin the functions to calculate the interval-values
	 *@param List<Bolean> A list of booleans to indicate if each value has to be normalized or not            
	 * @return String OK/Error message
	 * */
	public void createModel(String instanceTarget,List<String> varNames, Long initialDate,Long finalDate, Long periodicity,String algorithm, List<String> functions, List<Boolean> normalize)throws Exception {
		dataManager.loadInstancesFromDB(instanceTarget, varNames, initialDate,finalDate, periodicity, functions, normalize);
		//dataManager.printARFF();
		buildModel(algorithm);
		sampledData=loadSampledData();
	}
	/**
	 * Retrieves the model that is currently loaded as an ARFF file.
	 * @return String The model in ARFF format
	 */
	public String getArffModel() {
		return dataManager.getInstances().toString();
	}

	/**
	 * Builds the model. This function its implemented apart because of the need
	 * of calling it periodically, not every time
	 * 
	 * @throws BaseException
	 * @param String  The name of the algorithm to use
	 * 	 * */
	public void buildModel(String algorithm) throws Exception {
		//try {
			// forecaster.buildForecaster(dataManager.getInstances(),dataManager.getVarNames(), "Bagging");
			//forecaster.buildForecaster(dataManager.getInstances(),dataManager.getVarNames(), "LinearRegression");
			forecaster.buildForecaster(dataManager.getInstances(),dataManager.getVarNames(), algorithm);
		/*} catch (Exception e) {
			throw new BaseException("Prediction", "Exception building", e);
		}*/
	}

	/**
	 * Makes a prediction for the instances that are loaded currently on the
	 * model, and returns it. CreateModel or BuildModel methods have to be
	 * called before executing this method.
	 * 
	 * @param Integer Time-steps to predict
	 * @return List<VariableI> the complete instance list as variables
	 * @throws Exception
	 */
	public List<VariableI> predict(Integer steps) throws Exception  {
		forecaster.runForecaster(dataManager.getInstances(), steps);
//		System.out.println("\n\nSize: " + forecaster.getPrediction().size() + " "+forecaster.getPrediction().get(0).size()+ "\n\n");
		lastPrediction=loadPrediction();
		return lastPrediction;
	}


	public List<VariableI> getPrediction() throws Exception {
		return lastPrediction;
	}
	/**
	 * Returns the prediction that is currently loaded on the forecaster.
	 * 
	 * @param List<String> Names of the variables to return. If empty, ALL variables are returned
	 * @return List<<VariableI> The predicted values
	 * @throws WSException 
	 */
	public List<VariableI> loadPrediction() throws Exception {
		List<VariableI> vList = new ArrayList<VariableI>();
		List<VarMetadata> metaList = new ArrayList<VarMetadata>();
		List<Double> normalizationList = new ArrayList<Double>(dataManager.getNormalizationList());
		Long lastDate = (long) dataManager.getInstances().lastInstance().value(0);
		Long penultiDate = (long) dataManager.getInstances().get(dataManager.getInstances().size() - 2).value(0);
		Long interval = lastDate - penultiDate;
		for(int i=1; i<dataManager.getInstances().numAttributes();i++)
			metaList.add( VarMetadataManager.findByName(dataManager.getInstances().attribute(i).name() ) );

		for (int i = 0; i < forecaster.getPrediction().size(); i++) { // number of steps

			lastDate = lastDate + (interval);
			List<NumericPrediction> predsAtStep = forecaster.getPrediction().get(i);
			for (int j = 0; j < predsAtStep.size(); j++) { // Number of variables
				NumericPrediction predForTarget = predsAtStep.get(j);
				VariableI vI = new VariableI();
				vI.setTimestamp(lastDate);
				vI.setName(dataManager.getInstances().attribute(j + 1).name());
				vI.setValue(predForTarget.predicted()*normalizationList.get(j) + "");
				vI.setQuality((long) 1);
				vI.setDigitalType(metaList.get(j).getVmdDigtype());
				//vI.setLocalization( metaList.get(j).getVmdLocalization() );
				vI.setPhysicalType( metaList.get(j).getVmdPhytype() );
				vI.setMeasureUnit( metaList.get(j).getVmdMeasureunit() );
				vI.setURI( metaList.get(j).getVmdUri() );
				vI.setAccess( metaList.get(j).getVmdAccess() );
				vI.setDescription( metaList.get(j).getVmdDescription() );
				vI.setStoreDB( metaList.get(j).getVmdStoredbd() );
				vList.add(vI);
			}
		}
		return vList;
	}

	/**
	 * Gets a valueList, converts it into an instance and adds it to the current
	 * instance-set
	 * @param List<VariableI> The list of values (variableI).
	 * @throws Exception
	 * 	 
	 * * ALVARO 12/09/2014:  La idea es buena, pero se asumen muchas cosas:
	 * 	- Todo los valores de la lista tiene el mismo timestamp
	 * 	- La lista incluye TODAS las variables del modelo, y estas estan ordenadas de la misma forma que el modelo
	 * 
	 * */
	/*public void addInstance2Model(List<VariableI> vList) throws Exception {
		try {
			dataManager.addInstance(vList);
		} catch (Exception e) {
			throw new BaseException("Prediction",
					"Exception adding a new instance", e);
		}
	}*/

	public List<VariableI> getSampledData() throws Exception {
		return sampledData;
	}
	/**
	 * Retrieves the model that is currently loaded as variables
	 * @param List<String> Names of the variables to return. If empty, ALL variables are returned
	 * @return List<VariableI> The model-values.
	 * @throws WSException 
	 */
	private List<VariableI> loadSampledData() throws Exception {
		List<VariableI> model = new ArrayList<VariableI>();
		List<VarMetadata> metaList = new ArrayList<VarMetadata>();
		for(int i=1; i<dataManager.getInstances().numAttributes();i++)
			metaList.add( VarMetadataManager.findByName( dataManager.getInstances().attribute(i).name() ) );
		for (int i = 0; i < dataManager.getNumInstances(); i++) { // number of steps
			for (int j = 1; j < dataManager.getInstances().numAttributes(); j++) { // Number of variables
				VariableI vI = new VariableI();
				vI.setTimestamp((long) dataManager.getInstances().get(i).value(0));
				vI.setName(dataManager.getInstances().attribute(j).name());
				vI.setValue(dataManager.getInstances().get(i).value(j) + "");
				vI.setQuality((long) 2);
				vI.setDigitalType(metaList.get(j-1).getVmdDigtype());
				//vI.setLocalization( metaList.get(j-1).getVmdLocalization() );
				vI.setPhysicalType( metaList.get(j-1).getVmdPhytype() );
				vI.setMeasureUnit( metaList.get(j-1).getVmdMeasureunit() );
				vI.setURI( metaList.get(j-1).getVmdUri() );
				vI.setAccess( metaList.get(j-1).getVmdAccess() );
				vI.setDescription( metaList.get(j-1).getVmdDescription() );
				vI.setStoreDB( metaList.get(j-1).getVmdStoredbd() );
				model.add(vI);
			}
		}
		return model;
	}

	/**
	 * Retrieves the name of the atts of the current instance-set (except the
	 * timeStamp field)
	 * 
	 * @return List<String> The list with the attributes' names.
	 * */
	/*public List<String> getAttNames() {
		return dataManager.getVarNames();
	}*/

	/**
	 * Saves the current forecasting model on a .model file.
	 * @param String The filename to create
	 * @throws Exception
	 * */
	public void saveModel(String name) throws Exception {
		try {
			forecaster.saveForecastingModel(name,dataManager.getInstances());
		} catch (Exception e) {
			throw new BaseException("Prediction",
					"Exception saving the model into a file", e);
		}
	}

	/**
	 * Loads the forecasting model from a .model file.
	 * @param String The filename to load
	 * @throws Exception
	 * */
	public void loadModel(String name) throws Exception {
		try {
			forecaster.loadForecasterFromModel(name);
		} catch (Exception e) {
			throw new BaseException("Prediction",
					"Exception loading the model from file", e);
		}
	}
}
