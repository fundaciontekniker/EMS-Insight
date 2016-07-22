package es.tekniker.eefrmwrk.prediction;

import java.util.ArrayList;
import java.util.List;

import es.tekniker.eefrmwrk.commons.VariableI;

/**
 * @author ebikandi
 * */

public class MathFunctions {
	
	public MathFunctions(){}
	
	/**
	 * Functions to sort the list by executing the MergeSort algorithm. 
	 * See --> http://es.wikipedia.org/wiki/Ordenamiento_por_mezcla
	 * @param List<VariableI> Variable list to sort
	 * @return List<VariableI> A list sorted from the MIN value to the MAX
	 * ********************************************************************************************************************/
	public List<Float> mergeSort(List<Float> vars, int i){
		List<Float> left = null;
		List<Float> right = null;
		List<Float> left_loc = null; //helper lists initialize the halves of the list
		List<Float> right_loc = null;
		List<Float> result = null;
		
		if(vars.size()<=1){
			return vars;
		}
		else
		{	

  
			left_loc = new ArrayList<Float>(vars.subList(0, vars.size()/2) );
			right_loc = new ArrayList<Float>( vars.subList( vars.size()/2, vars.size()) );
			
			left = mergeSort(left_loc, i+1);
			right = mergeSort(right_loc, i+1);
			
			Float lastLeft = left.get(left.size()-1);
			Float lastRight = right.get(right.size()-1);
			
			if(lastLeft < lastRight ){ 
				left.addAll(right);
				return left;
			}
			
			result =  new ArrayList<Float>(merge(left, right));
			
			return result;
		}	
	}

	private List<Float> merge(List<Float> left, List<Float> right){
		List<Float> result = new ArrayList<Float>();
		
		while(left.size()>0 && right.size()>0){
			if( left.get(0) < right.get(0) ){
				result.add(left.get(0));
				left.remove(0);
			}
			else{
				result.add(right.get(0));
				right.remove(0);
			}
		}
		
		if( left.size() > 0) result.addAll(left);
		if( right.size() > 0) result.addAll(right);

		return result;
	}
/****************************************************************************************************************************/
	
	/**
	 * Function that calls the mathematical method to calculate the value depending on the given value
	 * @param List<VariableI> The variable list with the values to calculate.
	 * @param String The name of the function it is wanted to use.
	 * @return Float The value of the function applied to the list's values.
	 * */
	public Float calculateFunction(List<Float> varList, String funcName){

		Float value = 0.0f;
		if(funcName.equals("avg")){
			value = getAVG(varList);
		}
		else if(funcName.equals("median")){
			value = getMedian(varList);
		}
		else{
			System.out.println("Invalid function. Returning NaN");
			value = Float.NaN;
		}
		
		return value;
	}
	
	
	/**
	 * Calculates the average value of a variable list
	 * @param List<VariableI> List of variables.
	 * @return Float the calculated average
	 * */
	public Float getAVG(List<Float> vars){
		Float avg = (float) 0.0;
		int i;
		
		for(i=0; i<vars.size();i++){
			avg = avg + vars.get(i);
		}
		avg = avg / vars.size();
		return avg;
	}
	
	/**
	 * Calculates the median value of the list. It takes these steps, for a list with length=N:
	 * 1- If the list has only 1 element, returns the only value it has.
	 * 2-Else sorts the list by applying MergeSort.
	 * 3-If the N (the list length) is odd, it returns the value that is on the position (N+1)/2;
	 * 4-If N is pair, it returns the average value between the values of the position N/2 and (N/2)+1;
	 * @param List<VariableI> The list to calculate the media.n
	 * @return Float The median between the list's values.
	 * */
	public Float getMedian(List<Float> vars){
		Float median = 0.0f;
		float middleValue1;
		float middleValue2;
		
		if (vars.size() == 0) return Float.NaN ;
		if (vars.size() == 1) return vars.get(0);
		if (vars.size() == 2){
			middleValue1 = vars.get(0); 
			middleValue2 = vars.get(1); 
			median = (middleValue1 + middleValue2)/2;
			return median;
		}

		List<Float> sortedList = new ArrayList<Float>( mergeSort(vars,0) );
		
				
		if(sortedList.size()%2 != 0 ) median = sortedList.get( (sortedList.size()+1)/2 ); 
		else{
			
			middleValue1 = sortedList.get( sortedList.size()/2 ); 
			middleValue2 = sortedList.get( (sortedList.size()/2) + 1 ); 
			
			median = (middleValue1 + middleValue2)/2;
			
		}
		
		return median;
	}
	
	//TODO Implementar mas funciones matematicas
}
