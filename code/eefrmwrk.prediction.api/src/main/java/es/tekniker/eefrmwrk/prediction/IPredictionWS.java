package es.tekniker.eefrmwrk.prediction;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import es.tekniker.eefrmwrk.commons.PredictorI;
import es.tekniker.eefrmwrk.commons.PredictorVariableI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;

@WebService
public interface IPredictionWS {

	public @WebResult(name = "result")
	String addPredictor(
			@WebParam(name = "predictorName")String predictorName,
			@WebParam(name = "varNames")List<String> varNames,
			@WebParam(name = "initialDate") Long initialDate,
			@WebParam(name = "finalDate")Long finalDate,
			@WebParam(name = "interval")Long periodicity,
			@WebParam(name = "funcNames")List<String> funcNames,
			@WebParam(name = "normalize")List<Boolean> normalize,
			@WebParam(name = "algorithm")String algorithm,
			@WebParam(name = "steps")Integer steps
	)throws WSException;
	
	public @WebResult(name = "result")
	String editPredictor(
			@WebParam(name = "predictorName")String predictorName,
			@WebParam(name = "varNames")List<String> varNames,
			@WebParam(name = "initialDate") Long initialDate,
			@WebParam(name = "finalDate")Long finalDate,
			@WebParam(name = "interval")Long periodicity,
			@WebParam(name = "funcNames")List<String> funcNames,
			@WebParam(name = "normalize")List<Boolean> normalize,
			@WebParam(name = "algorithm")String algorithm,
			@WebParam(name = "steps")Integer steps
	)
	throws WSException;
	
	public @WebResult(name = "result")
	String deletePredictor(
			@WebParam(name = "predictorName")String predictorName)
	throws WSException;
		
	public @WebResult(name = "result")
	List<VariableI>predict(
			@WebParam(name = "predictorName")String predictorName,
			@WebParam(name = "steps") Integer steps,
			@WebParam(name = "notifyCEP") Boolean notifyCEP)
	throws WSException;
	
	public @WebResult(name = "result")
	PredictorI getPredictorInfo(
			@WebParam(name = "predictorName")String predictorName)
	throws WSException;
		
	public @WebResult(name = "result")
	List<PredictorVariableI> getPredictorVariables(
			@WebParam(name = "predictorName")String predictorName)
	throws WSException;
	
	public @WebResult(name = "result")
	List<PredictorI> getLoadedPredictors()
	throws WSException;
	
	public @WebResult(name = "result")
	List<VariableI> getSampledData(
			@WebParam(name = "predictorName")String predictorName)
	throws WSException;
	
	public @WebResult(name = "result")
	List<VariableI> getLastPrediction(
			@WebParam(name = "predictorName")String predictorName)
	throws WSException;
	
	//---------------------
	public @WebResult(name="result")
	String startPredictorCycle(
			@WebParam(name = "predictorName")String predictorName,
			@WebParam(name = "repeatInterval") Long repeatInterval) throws WSException;
	
	public @WebResult(name="result")
	String stopPredictorCycle(
			@WebParam(name = "predictorName")String predictorName) throws WSException;
}
