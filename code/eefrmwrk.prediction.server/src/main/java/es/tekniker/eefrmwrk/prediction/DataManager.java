package es.tekniker.eefrmwrk.prediction;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.neighboursearch.LinearNNSearch;
import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.VarMetadataI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;
//import es.tekniker.eefrmwrk.home.HomeWS;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

/**
 * @author ebikandi
 * */

public class DataManager {
	//private HomeWS homews = null;
	private MathFunctions mathFunctions = null;
	private List < List<Float> > valueList = null;  
 
	private List<Long> timeStamps = null;
	private List<Double> normalizedValues = null;
	private Instances instances = null;
	
	public DataManager(){
		//homews = new HomeWS();
		mathFunctions = new MathFunctions();
	}
	
	/**
	 * Returns a list with the attributes names, except the timestamp
	 * @return List<String> The list that contains the names
	 * */
	public List<String> getVarNames(){
		List<String> nameList = new ArrayList<String>();
		for(int i=1; i<instances.numAttributes();i++){
			nameList.add( instances.attribute(i).name() );
		}
		return nameList;
	}
	
	/**
	 * Initializes the timeStamp list. It will calculate all the periods without considering if they are beyond the last stored date.
	 * Doing this will probably generate a load of instances full of missing values, due to the lack of values depending on the finalDate.
	 * @param Long The initial date
	 * @param Long The final date
	 * @param long The periodicity of the time intervals.
	 * */
	private void initializeTimeStamps(Long initialDate, Long finalDate,  Long periodicity){
		Long rangeDate = initialDate + periodicity;
		while(rangeDate <= finalDate){
			timeStamps.add(rangeDate);
			rangeDate = rangeDate + periodicity;
		}
	}
	/**
	 * Resets the inner lists 
	 * */
	private void resetLists(){
		//Check if the lists have been loaded previously
		if(valueList == null){
			valueList = new  ArrayList < List<Float> >();
			timeStamps = new  ArrayList<Long>();
		}	
		else{
			valueList.clear();
			timeStamps.clear();
		} 
	}
	
	/**
	 * Retrieves the instances that are loaded
	 * @return Instances The instance-set.
	 * */
	public Instances getInstances()
	{
		if(instances != null && instances.size()>0)	return instances;
		else{
			System.out.println("No instances loaded");
			return null;
		}
	}
	
	/**
	 * Creates a new Instances object based on the input instances
	 * @param Instances the new instances
	 * */
	public void setInstances(Instances newInstances){
		this.instances = new Instances(newInstances);
	}
	
	/**
	 * Retrieves the number of instances that are loaded
	 * @return int The number of instances
	 * */
	public int getNumInstances()
	{
		if(instances != null)	return instances.size();
		else{
			System.out.println("No instances loaded");
			return 0;
		}
	}
	
	/**
	 * Prints an output of the Weka instances in ARFF format.
	 * */
	public void printARFF()
	{
		if(instances != null)	
			System.out.println(instances);
		else 
			System.out.println("Cannot print ARFF. No instances loaded");
	}
	
	
	/**
	 * Saves the instances into an ARFF file with the given filename.
	 * @param String The filename to save
	 * */
	public void saveARFF(String filename) throws IOException{
		 ArffSaver saver = new ArffSaver();
		 saver.setInstances(instances);
		 saver.setFile(new File("./"+ filename + ".arff"));
		 //saver.setDestination(new File("./"+ filename + ".arff"));   // **not** necessary in 3.5.4 and later
		 saver.writeBatch();
	}
	
	/**
	 * Get normalized values
	 * @param List<Double> The normalized values
	 * */
	public List<Double> getNormalizationList(){
		return normalizedValues;
	}
	
	/**
	 *Loads the data of a list of variables according to the given time periods.
	 *Calculates the values with a specified function with its name and 
	 *converts them into instance depending on the target, variables (clustering) or time-steps (prediction).
	 * @param String The objects to convert to instances ("vars" or "timeSeries").
	 * @param List<String>  A list of variable-names.
	 * @param Long The initial date
	 * @param Long The final date
	 * @param Long The periodicity of the time intervals.
	 * @param List<String> The list with names of the functions to calculate the values to watch attribute (avg, median, punctual).
	 *@param List<Bolean> A list of booleans to indicate if each value has to be normalized or not            
	 * @throws Exception 
	 * */	
	public void loadInstancesFromDB(String instanceTarget ,List<String> names, Long initialDate, Long finalDate, Long periodicity, List<String> funcNames, List<Boolean> normalize ) throws Exception{
		int i, j; 
		String varName;
		String funcName;
		List<String> filteredNames = new ArrayList<String>();
		List<Boolean> filteredNormalizers = new ArrayList<Boolean>();
		if(names.size()!=funcNames.size() && names.size()!=normalize.size()) 
			throw new Exception("There has to be a function and a normalize-boolean for each variable name!");
		
		if(initialDate<1L)
			throw new Exception("Wrong initial date");
		
		resetLists();
		
		
		for (i=0; i<names.size(); i++){
			varName = names.get(i);
			funcName = funcNames.get(i);
			List<Float> temporalList = new ArrayList<Float>(loadVarData(varName, initialDate, finalDate, periodicity, funcName));
			if(temporalList.size()>0){
				valueList.add(temporalList);
				filteredNames.add(names.get(i));
				filteredNormalizers.add(normalize.get(i)); 
			}
		}

		/*Remove empty variables (when converting timeSeries to instances). Be careful with the indexes, because the list are dynamic and any removing operation will change 
		 * the size() of the list. So if you use a typical for(i=0;i<List.size();i++) for removing attributes sooner or later will throw a NullPointerException or 
		 * an IndexoutOfBoundsException.
		 * 
		 * When converting variables to instances, this iteration will remove empty instances instead of attributes, so it only has to execute when it is specified on 
		 * the cleanOption. To see how to remove attributes when you are converting variables, look a couple of lines below.
		 *  
		 * 

		while(emptyAttrs.size()>0){
			i=0;
			while(i<varNames.size()){
			
				if(varNames.get(i).equals( emptyAttrs.get(0) )){
					varNames.remove(i);
					valueList.remove(i);
					break;
				}
				i++;
			}
			emptyAttrs.remove(0);
		}*/
		
		//InstanceTarget == vars (Aimed to use clustering)
		if(instanceTarget.equals("vars")){
			/*When converting vars into instances, the way to remove empty attributes is different. 
			 */
			List<Integer> missingList = new ArrayList<Integer>();
			
			//Initialize the list of missing values
			for(i=0; i<timeStamps.size(); i++) missingList.add(0);
			
			//Check for missing values. Whenever a missing value is found, it will increment by 1 the value that is on the index of the actual instance.
			for(i=0; i<valueList.size(); i++){
				for(j=0; j<timeStamps.size(); j++){
					if(valueList.get(i).get(j).isNaN())	missingList.set(j, missingList.get(j)+1);	
				}
			}
			
			/*If a value of the missing list is equal to the total of instances, it means that the attribute is "empty", so we have to remove it.
			 * Same as above, be careful with the indexes when you are removing elements of a dynamic list on a loop
			 */
			i=0;
			while(missingList.size() > 0){
				if(missingList.get(0)>=names.size()){
					timeStamps.remove(i);
					for(j=0; j<valueList.size(); j++) valueList.get(j).remove(i);
				}
				else i++;
				missingList.remove(0);
			}

		}

		//If there are variables with values ...
		if(valueList.size()>0){

			if(instanceTarget.equals("vars")) vars2Instances(filteredNames);
			else	timeSeries2Instances(filteredNames);	

			cleanEmptyInstances();
			replaceMissingValues();
			normalizeAttributes(filteredNormalizers);
//			printARFF();

		}
		
		System.out.println("Num. of instances: "+instances.numInstances());
		
//		return varNames;
	}
	
	/**
	 *Loads the data of a variable according to the given time periods. If needed, calls the function to initialize the timeStamps.
	 *Finally, calculates the values for each period.
	 *@param String A variable name.
	 * @param Long The initial date
	 * @param Long The final date
	 * @param Long The periodicity of the time intervals.
	 * @param String The name of the function to calculate the values (avg, median, punctual).
	 * @param String The option to deal with missing values (ignore, last, guess)
	 * @return List<Float> The list of the calculated values for the periods.
	 * @throws Exception 
	 * */
	private List<Float> loadVarData(String varName, Long initialDate, Long finalDate,  Long periodicity, String funcName) throws Exception{ 
		List<ValueVar> results = new ArrayList<ValueVar>();
		List<ValueVar> partialVarList = new ArrayList<ValueVar>();
		List<Float> localValueList = new ArrayList<Float>();
		int i=0;
		Long rangeDate = initialDate + periodicity;
		if(periodicity<300000L)
			throw new Exception("The minimum periodicity accepted is 300.000ms (5 mins)");
		
		if(timeStamps.size()<1) initializeTimeStamps(initialDate, finalDate, periodicity); //The TimeStamp list is uninitialized
						
		//The configurations for accessing the dataBase is on hibernate.cfg.xml (package eefrmwrk.database.SQL.dbmodel)
		results =  DBManager.readVR(varName, initialDate, finalDate);	
	
		
		if(results.size()<1)
		{
			/*Maybe it is no worth to deal with an empty attributes missing value. 
			 * Imagine that having 100 instances and only knowing the first value of the attribute, it is counterproductive to invent the other.
			 * Because surely it would bring noise more than knowledge.
			 * 
			 * Float previousValue = dealWithMissingValues(missingOption, varName, initialDate, previuosRangeChecked);
			
			if(previousValue.isNaN()) */
			return new ArrayList<Float>();
			
			
		}
		else
		{
			
			while(rangeDate <= finalDate ){ 
					
				while(i<results.size() && results.get(i).getVrTimestamp() <= rangeDate){
					partialVarList.add(results.get(i));
					i++;
				}
				if(partialVarList.size()<1) //No values for this timeInterval
				{ 
//					if(valueList.size()>0 && missingOption.equals("last")){
//						/*If the politic with the missing values is to replace them with the last known value of the attribute, 
//						 * and if we know that it isn't the first interval, it is enough to replace the missing value with last value of the list
//						 * instead of calling the function to deal with missing values.
//						 */
//						valueList.add( valueList.get( valueList.size()-1  ) );
//					}
//					else
//					{
//						valueList.add( dealWithMissingValues(missingOption, varName, rangeDate, previuosRangeChecked) ); 
//						if(!previuosRangeChecked)  previuosRangeChecked=true;	
//					}
					localValueList.add(Float.NaN);
				}
				else{
					//If we only need a punctual value, we only need the last value of the partialList, as we do not need to calculate any mathematical function
					if(funcName.equals("punctual")) 
						localValueList.add( Float.valueOf( partialVarList.get( partialVarList.size()-1 ).getVrValue()));
					else{
						List<Float> floatList = new ArrayList<Float>();
						for(ValueVar varI : partialVarList) floatList.add( Float.valueOf( varI.getVrValue() ) );
						localValueList.add( mathFunctions.calculateFunction(floatList, funcName) );
					}
				}
				partialVarList.clear();
				
				rangeDate = rangeDate + periodicity;
			}
		}
		return localValueList;
	}
	

	
	
	/**
	 * Converts the list of values into Weka instances.
	 * @param List<String> The names of the variables
	 * @throws ParseException
	 * @throws WSException 
	 * */
	private void timeSeries2Instances(List<String> varNames) throws ParseException, WSException{
			int i,j;
			ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
			attributeList.add(new Attribute("date", "yyyy-MM-dd HH:mm:ss.SSS"));
			for(i=0; i<varNames.size(); i++){
				attributeList.add(new Attribute(varNames.get(i)));
			}
			instances = new Instances("timeSeriesAsInstances", attributeList, timeStamps.size()); //timestamp + variables
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			String strDate;
			
			//Load values
			for(i=0; i<timeStamps.size(); i++){
				DenseInstance denseInstance = new DenseInstance(1 + varNames.size()); //timestamp + variables
				denseInstance.setDataset(instances);
				strDate = sdf.format(timeStamps.get(i));

				denseInstance.setValue(0, denseInstance.attribute(0).parseDate(strDate));
				for(j=0; j<valueList.size(); j++){
					//All instances have the same amount of attributes.				
					if( valueList.get(j).get(i).isNaN())		denseInstance.setMissing(j+1);//AttributeNumber + timestamp
					else denseInstance.setValue(j+1, valueList.get(j).get(i));
				}
				instances.add(denseInstance);
			}
			
	}
	
	
	/**Normalizes the attributes according to the input list of booleans
	 *@param List<Bolean> A list of booleans to indicate if each value has to be normalized or not    
	 * @throws InterruptedException
	 * @throws WSException        
	 * @throws BaseException 
	 * */
	public void normalizeAttributes(List<Boolean> normalizers) throws WSException, InterruptedException, BaseException{
		normalizedValues = new ArrayList<Double>();
		List<String> varNames = new ArrayList<String>(getVarNames());
		for(int i=0; i<varNames.size();i++){
			normalizedValues.add(1.0);
			if(normalizers.get(i)){
				System.out.println("Normalizing " + varNames.get(i));
				//VarMetadataI metadata = homews.getVariable(varNames.get(i));
				VarMetadata metadata = VarMetadataManager.findByName(varNames.get(i));
				System.out.println("\n\n******************************\n\n");	
				Thread.sleep(1000);
				if(metadata.getVmdMeasureunit().equals("%")){
					for(Instance instance: instances) 
						instance.setValue(i+1, instance.value(i+1)/100);
						normalizedValues.set(normalizedValues.size()-1, 100.0);
				}
				else{
					double maxValue = 0f;
					for(Instance instance: instances)
						if(instance.value(i+1) > maxValue) maxValue = instance.value(i+1);
					for(Instance instance: instances)
						instance.setValue(i+1, instance.value(i+1)/maxValue);
					 normalizedValues.set(normalizedValues.size()-1, maxValue);
				}
			}
		}
	}
	
	/**
	 * Gets a list of values, converts them into an instance and adds the new instance to the currently loaded InstanceSet
	 * @param List<VariableI> The list of values (VariableI)
	 * @throws Exception  
	 * 	 * 	 
	 * * ALVARO 12/09/2014:  La idea es buena, pero se asumen muchas cosas:
	 * 	- Todo los valores de la lista tiene el mismo timestamp
	 * 	- La lista incluye TODAS las variables del modelo, y estas estan ordenadas de la misma forma que el modelo     
	 *       
	 * */
	public void addInstance(List<VariableI> vList) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strDate;
		DenseInstance denseInstance = new DenseInstance(1 + vList.size()); //timestamp + variables
		denseInstance.setDataset(instances);
		strDate = sdf.format( vList.get(0).getTimestamp() );
		denseInstance.setValue(0, strDate);
		for(int i=0; i<vList.size(); i++){
			denseInstance.setValue(i+1, Float.valueOf( vList.get(i).getValue() ));
		}
		instances.add(denseInstance);
	}
	
	
	/**Converts a list of values into instance. One instance per sensor, and the attributes will be the timeStamps of the values.
	 * @param List<String> The list of sensor names;
	 * @throws ParseESception
	 * */
	private void vars2Instances(List<String> varNames) throws ParseException{
		if(valueList.size() > 0){
			int i,j;
			// Create/clear the model			
			if(instances != null) instances.delete();
			else{
				ArrayList<Attribute> attributeList = new ArrayList<Attribute>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				attributeList.add(new Attribute("varName", varNames));
				for(i=0; i<timeStamps.size(); i++){
					attributeList.add(new Attribute(sdf.format(timeStamps.get(i))));
				}
				instances = new Instances("varsAsInstances", attributeList, varNames.size());
			}
	
			//Load values
			for(j=0; j<valueList.size(); j++){
				
				DenseInstance denseInstance = new DenseInstance(1 + timeStamps.size()); //varName + timestamps
				denseInstance.setDataset(instances);
				String name = varNames.get(j);
				denseInstance.setValue(0, name);
				
				for(i=0; i<valueList.get(j).size(); i++){
					//All instances have the same amount of attributes.				
					if( valueList.get(j).get(i).isNaN()) denseInstance.setMissing(i+1);//AttributeNumber + timestamp
					else		denseInstance.setValue(i+1, valueList.get(j).get(i) );
				}
				instances.add(denseInstance);
			}	
		}
		else{
			System.out.println("No variables to convert");
		}
	}

	
		
	/**
	 * 
	 * Replaces the missing values in two steps:
	 * 	1- Applies the 4-NN imputation technique to replace a missing value with the median of it's 4 nearest neighbours' values.
	 *	2- Looks for remaining missing values and replaces them with the previous value of the attribute
	 * @throws Exception 
	 * */
	private void replaceMissingValues() throws Exception{
		int i,j;
		boolean neighbourSearched;
		LinearNNSearch knn;

		Instances nearestInstances = instances;
		List<Float> neighbourList = new ArrayList<Float>();
		Float newValue;
		
		System.out.println("\nTrying to guess the missing values using 4-NN Imputation\n");

		for(i=0; i<instances.numInstances();i++){
			neighbourSearched = false;
	
			for(j=0; j<instances.numAttributes();j++){
				if(instances.get(i).isMissing(j)){
					if(!neighbourSearched){
						Instances localInstances = new Instances(instances);
						localInstances.delete(i); //We delete the instance the actual instance to avoid overfitting
						knn = new LinearNNSearch(localInstances); 
						nearestInstances = new Instances(knn.kNearestNeighbours(instances.get(i), 8)); //The k value has to be pair. With odd values the function goes incorrectly.
						neighbourSearched = true;		
					}
				
					while(nearestInstances.numInstances()>0){
						VariableI localVar = new VariableI();
						localVar.setValue( String.valueOf( nearestInstances.get(0).value(j) ) );
						nearestInstances.delete(0);
						neighbourList.add(Float.valueOf( localVar.getValue() ));
					}
					
					//The returned float can have any value depending on it's neighbours, including NaN.
					newValue =  mathFunctions.calculateFunction(neighbourList, "median");
					if(newValue.isNaN())
					{
						if(i==0){
							instances.get(i).setValue(j, instances.get(i+1).value(j));
						}
						else instances.get(i).setValue(j, instances.get(i-1).value(j));
					}
					else{ 
						instances.get(i).setValue(j, newValue);
					}
					neighbourList.clear();					
				}
			}
		}

	}

	/**Function to remove the empty instances of the dataset. It is no use having an instance with all its attributes as missing values
	 * */
	private void cleanEmptyInstances(){
		int i=0;
		int j;
		System.out.println("\nRemoving empty instances\n");
		while(i<instances.numInstances()){
			Instance localIns = instances.get(i);
			//The search has to start from the second attribute (index=1) because the first is the timeStamp. 
			j=1;
			while(j<localIns.numAttributes()){
				if(!localIns.isMissing(j)) break;
				j++;
			}
			//ikusi j eta numAttrs. iguala bada ta holan bada borra ein
			if(j>=localIns.numAttributes())	instances.remove(i);
			else i++;	
		}
	}
	
	/**Gets a variable's metadata from the database
	 * @param String the name of the variable to get the metadata
	 * @return VarMetadataI The object that encapsulates the metadata
	 * */
	/*public VarMetadataI getVarMetadata(String varName) throws WSException{
		
		return homews.getVariable(varName);
	}/*
	
//	public void parse(String input) throws Exception{
//		String modelName = null;
//		String algorithm = null;
//		String vars = null;
//		String target = null;
//		boolean clean = false;
//		boolean missingValues = false;
//		StringTokenizer tokenizer = new StringTokenizer(input);
//		 while (tokenizer.hasMoreTokens()) {
//			 switch(tokenizer.nextToken()){
//			 	case "-N":
//			 		modelName = tokenizer.nextToken();	
//			 		break;
//			 	case "-A":
//			 		algorithm = tokenizer.nextToken();
//			 		break;
//			 	case "-V":
//			 		vars = tokenizer.nextToken();
//			 		break;
//			 	case "-T":
//			 		target = tokenizer.nextToken();
//			 		break;
//			 	case "-C":
//			 		clean = true;
//			 		break;
//			 	case "-M":
//			 		missingValues = true;
//			 		break;
//			 	default: 
//			 		throw new Exception("invalid input");
//			 }
//	     }
////		 if(modelName == null || algorithm == null || vars == null || target == null)	throw new Exception("invalid input");
//		 parseVars(vars);
//		 System.out.println(vars);
//	}
//	
//	
//	private void parseVars(String input){
//		StringTokenizer tokenizer = new StringTokenizer(input,",");
//		String avgVars="";
//		String medianVars="";
//		String simpleVars="";
//		String token="";
//		String recursiveInput;
//		String[] subStrings;
//		boolean onAVG = false;
//		boolean onMEDIAN = false;
//		
//		while (tokenizer.hasMoreTokens()) {
//			token = tokenizer.nextToken();
//			System.out.println("tokenizer_0 : " + token);
//			
//			if(token.contains("avg(") || onAVG){
//				onAVG = true;
//				System.out.println("------------	onAVG_true_0 ------");
//				subStrings = token.split("[(]");
////				if(subStrings[1].equals("avg") || subStrings[1].equals("median") ){ //The first variable is another function
//				if(token.contains("avg(avg") || token.contains("avg(median") ){ //The first variable is another function
//					recursiveInput = subStrings[1] + "(" + subStrings[2];
//					while(tokenizer.hasMoreTokens() && !token.contains(")")){ //continue looking for the right parentheses
//						token = tokenizer.nextToken();
//						System.out.println("tokenizer_1 : " + token);
//						recursiveInput +="," + token;
//						if( token.contains(")") )
//								break;
//					}
//					System.out.println("\n\n******	Recursive	*******\n\n");
//					parseVars(recursiveInput);
//					System.out.println("\n\n******	END Recursive	*******\n\n");
//				}
//				else if(token.contains(")")){ 
//					subStrings = token.split("avg[(]");
//					subStrings = subStrings[subStrings.length-1].split("[)]");
//					avgVars+=subStrings[0];
//					onAVG = false;
//					System.out.println("------------	onAVG_false_0 ------");
//
//				}
//				else{
//					avgVars+=subStrings[subStrings.length-1];
//					while(tokenizer.hasMoreTokens()){ //continue looking for the right parentheses
//						token = tokenizer.nextToken();
//						System.out.println("tokenizer_2 : " + token);
//						if(token.contains("avg") || token.contains("median")){
//							recursiveInput = token;
//							while(tokenizer.hasMoreTokens() && !token.contains(")")){
//								token = tokenizer.nextToken();
//								System.out.println("tokenizer_3 : " + token);
//								recursiveInput +="," + token;
//								if(token.contains("))")){
//									onAVG = false;
//									System.out.println("------------	onAVG_false_1 ------");
//									break;
//								}
//								if( token.contains(")") )
//										break;
//							}
//							System.out.println("\n\n******	Recursive	*******\n\n");
//							parseVars(recursiveInput);
//							System.out.println("\n\n******	END Recursive	*******\n\n");
//							System.out.println("******"+token+"*******");
//							if(token.contains("))"))
//									break; //Upper while (A function has been the last variable)
//						}
//						else if(token.contains(")")){
//
//							subStrings = token.split("[)]");
//							avgVars+=" "+subStrings[0];
//							onAVG = false;
//							System.out.println("------------	onAVG_false_2 ------");
//							break;
//						}
//						else avgVars+=token;
//					}
//				}
//			
//			}
//			else if(token.contains("median(") || onMEDIAN){
//				onMEDIAN = true;
////				System.out.println("------------	onMEDIAN_true_0 ------");
//				subStrings = token.split("[(]");
////				if(subStrings[1].equals("avg") || subStrings[1].equals("median") ){ //The first variable is another function
//				if(token.contains("median(avg") || token.contains("median(median") ){ //The first variable is another function
//					recursiveInput = subStrings[1] + "(" + subStrings[2];
//					while(tokenizer.hasMoreTokens() && !token.contains(")")){ //continue looking for the right parentheses
//						token = tokenizer.nextToken();
////						System.out.println("tokenizer_1 : " + token);
//						recursiveInput +="," + token;
//						if( token.contains(")") )
//								break;
//					}
//					System.out.println("\n\n******	Recursive	*******\n\n");
//					parseVars(recursiveInput);
//					System.out.println("\n\n******	END Recursive	*******\n\n");
//				}
////				else if(subStrings[1].contains(")")){ 
//				else if(token.contains(")")){ 
//					subStrings = token.split("median[(]");
//					subStrings = subStrings[subStrings.length-1].split("[)]");
//					medianVars+=subStrings[0];
//					onMEDIAN = false;
////					System.out.println("------------	onMEDIAN_false_0 ------");
//
//				}
//				else{
//					medianVars+=subStrings[subStrings.length-1];
//					while(tokenizer.hasMoreTokens()){ //continue looking for the right parentheses
//						token = tokenizer.nextToken();
////						System.out.println("tokenizer_2 : " + token);
//						if(token.contains("avg") || token.contains("median")){
//							recursiveInput = token;
//							while(tokenizer.hasMoreTokens() && !token.contains(")")){
//								token = tokenizer.nextToken();
////								System.out.println("tokenizer_3 : " + token);
//								recursiveInput +="," + token;
//								if(token.contains("))")){
//									onMEDIAN = false;
////									System.out.println("------------	onAVG_false_1 ------");
//									break;
//								}
//								if( token.contains(")") )
//										break;
//							}
//							System.out.println("\n\n******	Recursive	*******\n\n");
//							parseVars(recursiveInput);
//							System.out.println("\n\n******	END Recursive	*******\n\n");
////							System.out.println("******"+token+"*******");
//							if(token.contains("))"))
//									break; //Upper while (A function has been the last variable)
//						}
//						else if(token.contains(")")){
//
//							subStrings = token.split("[)]");
//							medianVars+=" "+subStrings[0];
//							onMEDIAN = false;
////							System.out.println("------------	onAVG_false_2 ------");
//							break;
//						}
//						else medianVars+=token;
//					}
//				}
//			}
//			else simpleVars += " " + token;
//
//		}
//		System.out.println("avg : " +avgVars );
//		System.out.println("med : " +medianVars );
//		System.out.println("sim : " +simpleVars );
//
//	}
	
//	/**Normalizes an attribute dividing the value by attribute's max value, converts it to a value between [0,1]  
//	 * */
//	public void normalizeAttribute(String attName){
//		double maxValue = 0f;
//		for(Instance instance: instances)
//			if(instance.value( new Attribute(attName)) >maxValue) maxValue = instance.value( new Attribute(attName));
//		for(Instance instance: instances)
//			instance.setValue( new Attribute(attName) , instance.value( new Attribute(attName))/maxValue);
//	}
	
}