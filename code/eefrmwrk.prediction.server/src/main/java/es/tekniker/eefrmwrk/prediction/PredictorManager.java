package es.tekniker.eefrmwrk.prediction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.PredictorI;
import es.tekniker.eefrmwrk.commons.PredictorVariableI;
import es.tekniker.eefrmwrk.commons.VariableI;

/**
 * Clase creada para manejar diferentes predictores.
 *  
 * @author agarcia 12/09/2014
 * 
 */
public class PredictorManager {
	
	ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);
	HashMap<String,PredictorWorker> predictorMap = new HashMap<String,PredictorWorker>();
	
	//--PREDICTOR MODELING-
	public void addPredictor(String predictorName, List<String> varNames,
			Long initialDate, Long finalDate, Long periodicity,
			List<String> funcNames, List<Boolean> normalize, String algorithm,
			Integer steps)throws BaseException {
		
		if (predictorName.equals("vars")) // Revisar AdvancedPredictor. Se utilizaba para clustering por variables...
			throw new BaseException("PredictorManager_CREATEMODEL1","Predictor can't be named vars. Is a reserved word");
		
		if (predictorMap.containsKey(predictorName)){
			throw new BaseException("PredictorManager_CREATEMODEL2","Duplicated predictor name");
		}else{
			PredictorWorker ap = new PredictorWorker();
			ap.predictorName=predictorName;
			ap.varNames =varNames;
			ap.functions =funcNames;
			ap.initialDate=initialDate;
			ap.finalDate=finalDate;
			ap.periodicity=periodicity;
			ap.normalize =normalize;
			ap.algorithm=algorithm;
			ap.steps=steps;	
			predictorMap.put(predictorName,ap);
		}
		//TODO Guardar en base de datos
		
		//Construir modelo la primera vez
		try {
			predictorMap.get(predictorName).createModel();
		} catch (Exception e) {
			predictorMap.remove(predictorName);
			throw new BaseException("PredictorManager_CREATEMODEL0","Exception creating model",e);
		}	
	}
	
	public void editPredictor(String predictorName, List<String> varNames,Long initialDate, Long finalDate, Long periodicity,List<String> funcNames, List<Boolean> normalize, String algorithm,Integer steps)throws BaseException {
				
			if (!predictorMap.containsKey(predictorName)){
				throw new BaseException("PredictorManager_EDITMODEL1","No predictor with that name found");
			}else{
				//TODO Comprobar si esta en marcha (puntual o ciclo) ANTES de modificar cualquier cosas
				if(predictorMap.get(predictorName).repeatInterval>0)
					throw new BaseException("PredictorManager_EDITPREDICTOR2", "Predictor is cycle. Stop before editing.");
				if(predictorMap.get(predictorName).status==PredictorWorker.Status_BUSY)
					throw new BaseException("PredictorManager_EDITPREDICTOR3", "Predictor is busy wit some task. Please wait.");
				
				PredictorWorker old_pred=predictorMap.get(predictorName); //En caso de fallo, recupera el antiguo
				
				predictorMap.get(predictorName).varNames =varNames;
				predictorMap.get(predictorName).functions =funcNames;
				predictorMap.get(predictorName).initialDate=initialDate;
				predictorMap.get(predictorName).finalDate=finalDate;
				predictorMap.get(predictorName).periodicity=periodicity;
				predictorMap.get(predictorName).normalize =normalize;
				predictorMap.get(predictorName).algorithm=algorithm;
				predictorMap.get(predictorName).steps=steps;	
				
				//TODO Reconstruir modela/Cambiar en modelos en funcionamiento del motor modelo
				//Si sólo cambia steps, no es necesario ningun cambio
				//Si sólo cambia algorithm, solo ha que reconstruir el modelo
				//Si hay cambios en cualquier otro parámetro requiere cargar datos+reconstruir modelo
				
				
				try {
					predictorMap.get(predictorName).createModel();  // 29/09/2014 DE MOMENTO RECONSTRUYE COMPLETO SIEMPRE
				} catch (Exception e) {
					predictorMap.put(predictorName,old_pred);	//En caso de fallo, recupera el antiguo. Este ya estará construido
					throw new BaseException("PredictorManager_CREATEMODEL0","Exception creating model",e);
				}	
			}
			
			//TODO Actualizar base de datos
	}
	
	public void deletePredictor(String predictorName)throws BaseException {
		
		if (!predictorMap.containsKey(predictorName)){
			throw new BaseException("PredictorManager_DELETEPREDICTOR1","No predictor with that name found");
		}else{
			if(predictorMap.get(predictorName).repeatInterval>0)
				throw new BaseException("PredictorManager_DELETEPREDICTOR2", "Predictor is in cycle. Stop before deleting.");
			if(predictorMap.get(predictorName).status==PredictorWorker.Status_BUSY)
				throw new BaseException("PredictorManager_DELETEPREDICTOR3", "Predictor is busy wit some task. Please wait.");
			predictorMap.remove(predictorName);
		}
}
	
	public void startCycle(String predictorName, Long repeatInterval) throws BaseException{
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_STARTCYCLE1","No predictor with that name found");
		else
		{	if(predictorMap.get(predictorName).repeatInterval>0)
				throw new BaseException("PredictorManager_STARTCYCLE2","Predictor is already on a cycle");
			else if (predictorMap.get(predictorName).status.equals(PredictorWorker.Status_BUSY))
				throw new BaseException("PredictorManager_STARTCYCLE3","Predictor is busy with some task. Plase wait");
			else {
				predictorMap.get(predictorName).repeatInterval=repeatInterval;
				scheduledThreadPool.schedule(predictorMap.get(predictorName), 0, TimeUnit.SECONDS);
				}
		} 	
	}
	
	public void stopCycle(String predictorName) throws BaseException{
		if(predictorMap.containsKey(predictorName))
			predictorMap.get(predictorName).stop();
		else
			throw new BaseException("PredictorManager_STOPCYCLE1","No predictor with that name found"); 	
	}
	
	
	public String getStatus(String predictorName) throws BaseException{
		if(predictorMap.containsKey(predictorName))
			return predictorMap.get(predictorName).status;
		else
			throw new BaseException("PredictorManager_GETSTATUS1","No predictor with that name found");
	}
	
	
	public void shutdown(){
		scheduledThreadPool.shutdownNow();
		for(PredictorWorker p:predictorMap.values()){
			p.stop();
		}
	
	    while(!scheduledThreadPool.isTerminated()){   }
	}

	
	//---------------------------------------
	

	public void createModel(String predictorName, String algorithm)throws BaseException {
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_CREATEMODEL1","No predictor with that name found");
		else
			try {
				predictorMap.get(predictorName).algorithm=algorithm;
				predictorMap.get(predictorName).createModel();
			} catch (Exception e) {
				throw new BaseException("PredictorManager_CREATEMODEL0","Exception creaing model",e);
			}
	}

	public void buildModel(String predictorName, String algorithm)throws BaseException {
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_BUILDMODEL1","No predictor with that name found");
		else
			try {
				predictorMap.get(predictorName).algorithm=algorithm;
				predictorMap.get(predictorName).createModel();
			} catch (Exception e) {
				throw new BaseException("PredictorManager_BUILDMODEL0","Exception building model",e);
			}
	}

	public List<VariableI> predict(String predictorName, Integer steps, Boolean notifyCEP)throws BaseException {
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_PREDICT1","No predictor with that name found");
		else
			try {
				predictorMap.get(predictorName).steps=steps;
				return predictorMap.get(predictorName).predict(notifyCEP);
			} catch (Exception e) {
				throw new BaseException("PredictorManager_PREDICT0","Exception predicting",e);
			}
	}


	public List<VariableI> getSampledData(String predictorName) throws BaseException {
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_GETSAMPLEDDATA1","No predictor with that name found");
		else
			try {
				return predictorMap.get(predictorName).getModelVariables();
			} catch (Exception e) {
				throw new BaseException("PredictorManager_GETSAMPLEDDATA0","Exception getting sampled variables",e);
			}
	}


	public List<VariableI> getPrediction(String predictorName) throws BaseException {
		if(!predictorMap.containsKey(predictorName))
			throw new BaseException("PredictorManager_GETPREDICTION1","No predictor with that name found");
		else
			try {
				return predictorMap.get(predictorName).getPrediction();
			} catch (Exception e) {
				throw new BaseException("PredictorManager_GETPREDICTION0","Exception retrieving last prediction",e);
			}
	}

	

	public List<PredictorI> getLoadedPredictors() {
		List<PredictorI> pList = new ArrayList<PredictorI>();
		for (PredictorWorker pw:predictorMap.values()){
			PredictorI pI = new PredictorI();
			pI.setPredictorName(pw.predictorName);
			pI.setAlgorithm(pw.algorithm);
			pI.setInitialDate(pw.initialDate);
			pI.setFinalDate(pw.finalDate);
			pI.setPeriodicity(pw.periodicity);
			pI.setStatus(pw.status);
			pI.setRepeatInterval(pw.repeatInterval);
			pI.setSteps(pw.steps);
			pList.add(pI);
		}
		return pList;
	}
	
	public PredictorI getPredictorInfo(String predictorName)
			throws BaseException {
		if (predictorMap.containsKey(predictorName)) {
			PredictorWorker pw = predictorMap.get(predictorName);
			PredictorI pI = new PredictorI();
			pI.setPredictorName(pw.predictorName);
			pI.setAlgorithm(pw.algorithm);
			pI.setInitialDate(pw.initialDate);
			pI.setFinalDate(pw.finalDate);
			pI.setPeriodicity(pw.periodicity);
			pI.setStatus(pw.status);
			pI.setRepeatInterval(pw.repeatInterval);
			pI.setSteps(pw.steps);
			return pI;
		} else
			throw new BaseException("PredictorManager_GETPREDICTORINFO1","No predictor with that name found");
	}

	public List<PredictorVariableI> getPredictorVariables(String predictorName) throws BaseException {
		List<PredictorVariableI> pVList= new ArrayList<PredictorVariableI>();
		if(predictorMap.containsKey(predictorName)){
		PredictorWorker pw=predictorMap.get(predictorName);
		for(int i=0;i<pw.varNames.size();i++){
			PredictorVariableI pVI = new PredictorVariableI();
			pVI.setVarName(pw.varNames.get(i));
			pVI.setNorm(pw.normalize.get(i));
			pVI.setItpl_f(pw.functions.get(i));
			pVList.add(pVI);
		}
		return pVList;}else
			throw new BaseException("PredictorManager_GETPREDICTORVARIABLES1","No predictor with that name found"); 	
	}
}