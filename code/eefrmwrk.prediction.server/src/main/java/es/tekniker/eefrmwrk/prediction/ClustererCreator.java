package es.tekniker.eefrmwrk.prediction;

import weka.clusterers.Clusterer;
import weka.clusterers.Cobweb;
import weka.clusterers.EM;
import weka.clusterers.FarthestFirst;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.HierarchicalClusterer;
import weka.clusterers.MakeDensityBasedClusterer;
import weka.clusterers.SimpleKMeans;

/**
 * @author ebikandi
 * 
 * Class to create Clusterers (Unsupervised Classifers)
 * */
public abstract class ClustererCreator {
	
	/**Returns a clusterer depending on the input algorithm name
	 * @param String The clusterer name you want to create (Cobweb, EM, ...)
	 * @return Clusterer The created clusterer 
	 * @throws Exception 
	 * */
	protected Clusterer createClassifier(String algorithm) throws Exception{
//		if(algorithm.equals("Canopy")) return new Canopy();
		if(algorithm.equals("Cobweb")) return new Cobweb();
		else if(algorithm.equals("EM")) return new EM();
		else if(algorithm.equals("FarthestFirst")) return new FarthestFirst();
		else if(algorithm.equals("FilteredClusterer")) return new FilteredClusterer();
		else if(algorithm.equals("HierarchicalClusterer")) return new HierarchicalClusterer();
		else if(algorithm.equals("MakeDensityBasedClusterer")) return new MakeDensityBasedClusterer();
		else if(algorithm.equals("SimpleKMeans")) return new SimpleKMeans();
		
		/************		Default	*************************/
		else throw new Exception("Unkown Clusterer");

	}
}
