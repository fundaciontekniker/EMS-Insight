package es.tekniker.eefrmwrk.prediction;

import java.io.*;

import java.util.ArrayList;
import java.util.List;

import org.antlr.grammar.v3.ANTLRv3Parser.throwsSpec_return;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.VariableI;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.evaluation.NumericPrediction;
import weka.classifiers.timeseries.WekaForecaster;
import weka.classifiers.timeseries.core.TSLagMaker;

/**
 * Example of using the time series forecasting API. To compile and run the
 * CLASSPATH will need to contain:
 * 
 * weka.jar (from your weka distribution)
 * pdm-timeseriesforecasting-ce-TRUNK-SNAPSHOT.jar (from the time series
 * package) jcommon-1.0.14.jar (from the time series package lib directory)
 * jfreechart-1.0.13.jar (from the time series package lib directory)
 */
public class TimeSeriesExample {

		public static void main(String[] args) {
		try {
			// path to the Australian wine data included with the time series
			// forecasting
			// package
			// String pathToWineData =
			// weka.core.WekaPackageManager.PACKAGES_DIR.toString()
			// + File.separator + "timeseriesForecasting" + File.separator +
			// "sample-data"
			// + File.separator + "wine.arff";

			String pathFileArff = "descriptores_tekniker_climatizacion.arff";
			// load the data

			Instances consum = new Instances(new BufferedReader(new FileReader(
					pathFileArff)));
			System.out.println("File read");
			// new forecaster
			WekaForecaster forecaster = new WekaForecaster();

			// set the targets we want to forecast. This method calls
			// setFieldsToLag() on the lag maker object for us
			System.out.println("Field to forecast avg");
			forecaster.setFieldsToForecast("avg");

			// default underlying classifier is SMOreg (SVM) - we'll use
			// gaussian processes for regression instead
			// System.out.println("Classifier GuassianProcesses");
			// forecaster.setBaseForecaster(new GaussianProcesses());

			System.out.println("TimeStampField to Fecha");
			forecaster.getTSLagMaker().setTimeStampField("Fecha"); // date time
																	// stamp
			System.out.println("Minlang 1");
			forecaster.getTSLagMaker().setMinLag(1);
			System.out.println("Maxlang 12");
			forecaster.getTSLagMaker().setMaxLag(12); // monthly data

			// add a month of the year indicator field
			forecaster.getTSLagMaker().setAddMonthOfYear(true);
			// forecaster.getTSLagMaker().setAddDayOfWeek(true);

			// add a quarter of the year indicator field
			// forecaster.getTSLagMaker().setAddQuarterOfYear(true);

			// build the model
			System.out.println("Build the model");
			forecaster.buildForecaster(consum, System.out);

			// prime the forecaster with enough recent historical data
			// to cover up to the maximum lag. In our case, we could just supply
			// the 12 most recent historical instances, as this covers our
			// maximum
			// lag period
			System.out.println("prime the forecaster");
			forecaster.primeForecaster(consum);

			// forecast for 12 units (months) beyond the end of the
			// training data
			System.out.println("forecast");
			List<List<NumericPrediction>> forecast = forecaster.forecast(12,
					System.out);

			// output the predictions. Outer list is over the steps; inner list
			// is over
			// the targets
			System.out
					.println("number of items forecasted: " + forecast.size());
			for (int i = 0; i < 12; i++) {
				List<NumericPrediction> predsAtStep = forecast.get(i);
				System.out.println("item forecasted: " + i);
				for (int j = 0; j < 1; j++) {
					NumericPrediction predForTarget = predsAtStep.get(j);
					System.out.print("" + predForTarget.predicted() + " ");
				}
				System.out.println();
			}

			// we can continue to use the trained forecaster for further
			// forecasting
			// by priming with the most recent historical data (as it becomes
			// available).
			// At some stage it becomes prudent to re-build the model using
			// current
			// historical data.

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}