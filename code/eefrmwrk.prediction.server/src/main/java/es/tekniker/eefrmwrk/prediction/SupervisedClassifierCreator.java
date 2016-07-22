package es.tekniker.eefrmwrk.prediction;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import weka.classifiers.Classifier;
import weka.classifiers.functions.GaussianProcesses;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.functions.SMOreg;
import weka.classifiers.lazy.IBk;
import weka.classifiers.lazy.KStar;
import weka.classifiers.lazy.LWL;
import weka.classifiers.meta.AdditiveRegression;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.classifiers.meta.Bagging;
import weka.classifiers.meta.CVParameterSelection;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.meta.MultiScheme;
import weka.classifiers.meta.RandomCommittee;
import weka.classifiers.meta.RandomSubSpace;
import weka.classifiers.meta.RegressionByDiscretization;
import weka.classifiers.meta.Stacking;
import weka.classifiers.meta.Vote;
import weka.classifiers.misc.InputMappedClassifier;
import weka.classifiers.misc.SerializedClassifier;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.M5Rules;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.M5P;
import weka.classifiers.trees.REPTree;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Instances;

/**
 * @author ebikandi
 * 
 * Class to create Supervised Classifiers
 * */

public abstract class SupervisedClassifierCreator {
	
	/**Returns a classifier depending on the input algorithm name
	 * @param String The classifier name you want to create (IBk, KStar, ...)
	 * @return Classifier The created classsifier 
	 * @throws Exception 
	 * */
	protected Classifier createClassifier(String algorithm) throws Exception{
		/************		Functions	*************************/
		if(algorithm.equals("GaussianProcesses")) return new GaussianProcesses();
		else if(algorithm.equals("LinearRegression")) return new LinearRegression();
		else if(algorithm.equals("MultilayerPerceptron")) return new MultilayerPerceptron();
		else if(algorithm.equals("SMOreg")) return new SMOreg();
		
		/************		Lazy	*************************/
		else if(algorithm.equals("IBk")) return new IBk();
		else if(algorithm.equals("KStar")) return new KStar();
		else if(algorithm.equals("LWL")) return new LWL();
		
		/************		Meta	*************************/
		else if(algorithm.equals("AdditiveRegression")) return new AdditiveRegression();
//		else if(algorithm.equals("AggregateableFilteredClassifier")) return new AggregateableFilteredClassifier();
//		else if(algorithm.equals("AggregateableFilteredClassifierUpdateable")) return new AggregateableFilteredClassifierUpdateable();
		else if(algorithm.equals("AttributeSelectedClassifier")) return new AttributeSelectedClassifier();
		else if(algorithm.equals("Bagging")) return new Bagging();
//		else if(algorithm.equals("BatchPredictorVote")) return new BatchPredictorVote();
		else if(algorithm.equals("CVParameterSelection")) return new CVParameterSelection();
		else if(algorithm.equals("FilteredClassifier")) return new FilteredClassifier();
//		else if(algorithm.equals("FilteredClassifierUpdateable")) return new FilteredClassifierUpdateable();
		else if(algorithm.equals("MultiScheme")) return new MultiScheme();
		else if(algorithm.equals("RandomCommittee")) return new RandomCommittee();
//		else if(algorithm.equals("RandomizableFilteredClassifier")) return new RandomizableFilteredClassifier();
		else if(algorithm.equals("RandomSubSpace")) return new RandomSubSpace();
		else if(algorithm.equals("RegressionByDiscretization")) return new RegressionByDiscretization();
		else if(algorithm.equals("Stacking")) return new Stacking();
		else if(algorithm.equals("Vote")) return new Vote();
		
		/************		Misc	*************************/
		else if(algorithm.equals("InputMappedClassifier")) return new InputMappedClassifier();
		else if(algorithm.equals("SerializedClassifier")) return new SerializedClassifier();
		
		/************		Rules	*************************/
		else if(algorithm.equals("DecisionTable")) return new DecisionTable();
		else if(algorithm.equals("M5Rules")) return new M5Rules();
		else if(algorithm.equals("ZeroR")) return new M5Rules();
		
		/************		Trees	*************************/
		else if(algorithm.equals("DecisionStump")) return new DecisionStump();
		else if(algorithm.equals("M5P")) return new M5P();
		else if(algorithm.equals("RandomForest")) return new RandomForest();
		else if(algorithm.equals("RandomTree")) return new RandomTree();
		else if(algorithm.equals("REPTree")) return new REPTree();
	
		/************		Default	*************************/
		else{
			throw new Exception("Classifier name spelled incorreclty. Check it!");
		}
	}

	
	 

}
