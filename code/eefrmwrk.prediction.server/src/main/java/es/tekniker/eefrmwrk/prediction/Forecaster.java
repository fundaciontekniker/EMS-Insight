package es.tekniker.eefrmwrk.prediction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.timeseries.core.TSLagMaker;
import weka.classifiers.timeseries.core.TSLagMaker.Periodicity;
import weka.classifiers.timeseries.eval.TSEvaluation;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author ebikandi
 * */
public class Forecaster extends SupervisedClassifierCreator {

	private WekaForecaster forecaster = null;
	private List<List<NumericPrediction>> forecasting = null;
	//private Instances instanceSet = null;

	// Instances realData;

	/**
	 * Constructor
	 * */
	public Forecaster() {
		forecaster = new WekaForecaster();

	}

	/**
	 * Returns the WekaForecaster that is currently loaded.
	 * 
	 * @return WekaForecaster The WekaForecaster that is currently loaded.
	 * */
	public WekaForecaster getForecaster() {
		return forecaster;
	}

	/**
	 * Returns the prediction that is currently loaded.
	 * 
	 * @return List<List<NumericPrediction>> The prediction that is currently loaded.
	 * */
	public List<List<NumericPrediction>> getPrediction(){
		return forecasting;
	}
	
	/**
	 * Saves the forecaster that is currently loaded in a .model in order to re-use it.
	 * 
	 * @param String
	 *            The filename to use when creating the file (without the .model
	 *            extension)
	 * */
	public void saveForecastingModel(String modelname, Instances structure)throws FileNotFoundException, IOException {
		File sFile = new File(modelname + ".model");
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(sFile));
		oos.writeObject(forecaster);
		oos.writeObject(new Instances(structure, 0));
		oos.close();
	}

	/**
	 * Loads a stored forecaster form a .model file.
	 * 
	 * @param String The name of the .model file (without the .model extension)
	 * @throws Exception 
	 * */
	public void loadForecasterFromModel(String modelname) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream( modelname + ".model"));
		forecaster.reset();
		forecaster = (WekaForecaster) ois.readObject();
		ois.close();
	}

	/**
	 * Builds the forecaster to prepare for the execution.
	 * @param Instances The instances to use. Also they will be stored to remember the data-structure for future use.
	 * @param List<String> The names of the variables to forecast.
	 * @param String The Name of the algorithm to use.
	 * @param Long The time-lapse between the intervals.
	 * @param int The number of steps to set the confidence interval
	 *@throws Exception 
	 * */
//For now, we will not fix the periodicity
//	public void buildForecaster(Instances instances, List<String> varNames, String algorithm, Long periodicity) throws Exception {
public void buildForecaster(Instances instances, List<String> varNames, String algorithm) throws Exception {
		if (varNames.size() > 0) {
			System.out.println("Building the forecast");
//			instanceSet = new Instances(instances);
			// Set the attributes we want to make the forecasting
			String varString = "";
			for (String f : varNames) {
				varString += f + ",";
			}

			varString = varString.substring(0, varString.length() - 1);
			forecaster.setFieldsToForecast(varString);

			forecaster.setBaseForecaster(createClassifier(algorithm));
			
			forecaster.getTSLagMaker().setTimeStampField("date"); // date  timestamp
//			forecaster.getTSLagMaker().setAdjustForVariance(true);

//FIXME dynamic periodicity, depending on the input data
//			// Set the periodicity 
//			if (periodicity == 86400000L)
//				forecaster.getTSLagMaker().setPeriodicity(Periodicity.DAILY); 
//			else
//				forecaster.getTSLagMaker().setPeriodicity(Periodicity.HOURLY); //Default option
			// Assign the model
//			forecaster.setConfidenceLevel((double)0.99);
//			forecaster.setCalculateConfIntervalsForForecasts(steps);
			forecaster.buildForecaster(instances, System.out);
		}
	}

	/**
	 * Runs the forecaster. It has to be built previously with buildForecaster().
	 * @param The instance-set to prime the forecaster
	 * @param int The number of time-steps to predict beyond the last data.
	 *@throws Exception 
	 * */
	public void runForecaster(Instances instanceSet,int steps) throws Exception { 
	
		forecaster.primeForecaster(instanceSet);
		forecasting = forecaster.forecast(steps, System.out);
//		this.printPrediction(false, false);
	}

	/**
	 * Evaluates the forecaster that is currently built loaded.
	 * @param The instance-set to prime the forecaster
	 * @param int The number of time-steps to predict beyond the last data.
	 * @return String The summary of the evaluation.
	 *@throws Exception 
	 * */
	public String evaluateForecaster(Instances instanceSet, int steps) throws Exception { 
		TSEvaluation eval = new TSEvaluation(instanceSet, 0.3);
		// Set the evaluation horizon
		if (forecaster.getTSLagMaker().getPeriodicity() == Periodicity.DAILY)
			eval.setHorizon(7);// One week
		else
			eval.setHorizon(6); 
		eval.setHorizon(steps);
		eval.setEvaluateOnTestData(true);
		eval.setEvaluateOnTrainingData(false);
		eval.evaluateForecaster(forecaster, System.out);
		System.out.println(eval.toSummaryString());
		return eval.toSummaryString();
	}

	/**
	 * Prints the last forecasting that has been done.
	 * @param String The name of the algorithm that it has been used.
	 * @param Boolean To save the forecasting in a log. (True to store)
	 * @param Boolean To append (true) the forecasting to the content of the file, or erase (false) it.
	 * @throws Exception
	 * */
	public void printPrediction(boolean save2Log, boolean append) throws Exception {
		if (forecasting != null) {
			int i, j;
			double lastDate, firstDate, delta;
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			StringBuilder logContent = new StringBuilder(
					"********************************\n********	" + forecaster.getAlgorithmName()
							+ "\n********************************\n\n");
			TSLagMaker tslMaker = forecaster.getTSLagMaker();
			lastDate = tslMaker.getCurrentTimeStampValue();
			delta = tslMaker.getDeltaTime();
			firstDate = lastDate - (delta * forecasting.size());
			for (i = 0; i < forecasting.size(); i++) { // Number of steps
				logContent.append(sdf.format(firstDate + (delta * (i + 1)))
						+ ": ");
				for (j = 0; j < forecasting.get(i).size(); j++) { // Number of attributes (variables)
					logContent.append(forecasting.get(i).get(j).predicted()
							+ " ");
				}
				logContent.append("\n");
			}
			System.out.println(logContent);
			if (save2Log)
				saveLoging(logContent.toString(), append);

		}
	}

	/**Saves a string into a log file.
	 * @param String The string to write into the file.
	 * @param Boolean Boolean To append (true) the forcasting to the content of the file, or erase (false) it.
	 * */
	public void saveLoging(String logContent, boolean append)
			throws IOException {
		File file = new File("forecastingLog.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		// true = append file
		FileWriter fileWritter = new FileWriter(file.getName(), append);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		bufferWritter.write(logContent);
		bufferWritter.close();

		System.out.println("Done");

	}

	/**
	 * Builds, runs and evaluates one or more forecaster/s, and saves the evaluation into a log.
	 *@param Instances The instances to use.
	 *@param List<String> The name of the variables.
	 *@param int The number of time-steps to predict.
	 *@param List<String> The names of the algorithms to use.
	 *@param Long The time-lapse between the intervals.
	 * @param int The number of steps to set the confidence interval
	 * @throws Exception
	 */
	public void evaluateForecasters(Instances instances, List<String> varNames,
			int steps, List<String> algorithms, Long periodicity)
			throws Exception {
		if (varNames.size() > 0) {
			int i;
			Long start, end;
			String output = "";
			System.out.println("Starting the evaluation of the forecasting algorithms");

			for (i = 0; i < algorithms.size(); i++) {
				output ="***********************\n" + algorithms.get(i) +"\n***********************\n\n";
				start = System.currentTimeMillis();
				buildForecaster(instances, varNames, algorithms.get(i));
//				buildForecaster(instances, varNames, algorithms.get(i), periodicity);
				runForecaster(instances, steps);
//				printForecasting(algorithms.get(i), true, true);
				output += evaluateForecaster(instances,steps);
				end = System.currentTimeMillis();
				output += "\n\n ExecTime: "
						+ (end - start)
						+ "ms \n\n----------------------------------------------------------------------\n\n";
				saveLoging(output, true);// FIXME always appends
				output = "";
			}
		}
	}

	
	/**Adds a new instance to the model and runs again the forecaster.
	 * @param Instance The new instance
	 * @param int The steps to do the forecast.
	 * @throws Exception
	 * */
	public void incrementalForecasting(DataManager dm, Instance newInstance, int steps) throws Exception{
		Instances newInstances = new Instances(dm.getInstances());
		newInstances.add(newInstance);
		dm.setInstances(newInstances);
		forecaster.buildForecaster(newInstances, System.out);
		forecaster.primeForecaster(newInstances);
	//	forecaster.primeForecasterIncremental(instance);
		forecasting.clear();
		forecasting = forecaster.forecast(steps, System.out);
	}

	/**Gets the prediction for the first time-step.
	 * @return List<Double> List with the predicted values.
	 * */
	public List<Double> getSoonestPrediction() throws Exception{
		int i, j;
		double lastDate, delta;
		List<Double> dataList = new ArrayList<Double>();
		TSLagMaker tslMaker = forecaster.getTSLagMaker();
		lastDate = tslMaker.getCurrentTimeStampValue();
		delta = tslMaker.getDeltaTime();
		dataList.add( lastDate - (delta * forecasting.size()) );

			for (j = 0; j < forecasting.get(0).size(); j++) { // Number of  attributes (variables)
				dataList.add(forecasting.get(0).get(j).predicted() );
			}
		dataList.remove(0);
		return dataList;
	}

}
