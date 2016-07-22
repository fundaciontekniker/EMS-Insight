package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.sql.Timestamp;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.cepmngr.client.CepClient;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.home.client.HomeClient;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue;

public class ProcessTemperatureThread extends Thread{

	private static Log log =LogFactory.getLog(ProcessTemperatureThread.class);
	private boolean active =true;
	
	private long loopTime;
	private List<String> listTacVariablesId = null;
	private List<String> listTacVariablesName = null;
	private TreeMap<Integer,TrendLogValue> mapLastLogValueTemperature= null;
	
	private CepClient cep = null;
	private HomeClient home = null;
	
	public ProcessTemperatureThread(CepClient cep, HomeClient home , long loopTimeProcess , List<String> listTacVariablesId , List<String> listTacVariablesName){
		this.loopTime=loopTimeProcess;
		this.cep=cep;
		this.home=home;
		this.listTacVariablesId=listTacVariablesId;
		this.listTacVariablesName=listTacVariablesName;
	
		initMapLastTemperature();
	}
	
	private void initMapLastTemperature(){
		mapLastLogValueTemperature = new TreeMap<Integer,TrendLogValue>();	
		
		for (int i =0 ; i<listTacVariablesId.size();i++){
			mapLastLogValueTemperature.put(Integer.valueOf(listTacVariablesId.get(i)),null);
		}		
	}
	
	@Override
	public void run() {
		log.debug("ProcessTemperatureThread START");
		while(active){			
			//logger.debug("CheckEventsMngrThread while loop inside");
			try {				
				//do work
				processTacDataTemperature();
				
			    Thread.sleep(loopTime);
			    
				
			} catch (InterruptedException e) {
				//no será interrumpido,  excepto por el usuario
			}
		}
		log.debug("ProcessTemperatureThread active is false");	
	}
	
	public boolean processTacDataTemperature(){
		TrendLogValue logValue = null;
		TrendLogValue logValueTemperaturePrevious = null;
		List<TrendLogValue> listLogValue = null;
		boolean boolOK =  false;
		int id =0;
		
		String stringId = null;
		String stringName = null;
		VariableI variableValueHome =  null;
		
		boolean boolProcessPreviousValues = false;
		
		long time4GetListValues = 0;
		
		log.debug("processTacDataTemperature : START ");
		
		try {
			if (this.listTacVariablesId !=null ){			
				log.debug("processTacDataTemperature : listTacVariablesId is not Null Num " + listTacVariablesId.size());
				
				//GET TEMPERATURE VALUES	
				for(int i =0 ; i<listTacVariablesId.size();i++){

					boolProcessPreviousValues = false;
					
					stringId = listTacVariablesId.get(i);
					stringName = listTacVariablesName.get(i);
										
					id= Short.valueOf(stringId);						
					log.debug("processTacDataTemperature : TacVariable id : " + id + " name : " + stringName);
					
					logValue = getLastTrendLogValue(id);			                
			        if (logValue != null ){
	
			        	logValueTemperaturePrevious = this.mapLastLogValueTemperature.get(id);
			        	
			        	if (logValueTemperaturePrevious != null){
			        		log.debug("processTacDataTemperature : logValueTemperaturePrevious exists for id : " + id);
			        		
			        		if(logValueTemperaturePrevious.getSequence()==logValue.getSequence()){
				        		log.debug("processTacDataTemperature : logValue is equal to logValueTemperaturePrevious for id : " + id + " sequence :  " + logValue.getSequence());
				        		//break;
				        	}else{				        		
				        		boolProcessPreviousValues = true;				        		
				        		time4GetListValues = logValueTemperaturePrevious.getLogTime().getTime();				        		
				        	}			        		
			        	}else{
			        		log.debug("processTacDataTemperature : logValueTemperaturePrevious  NOT exists for id : " + id + " first execution of the method or restarted") ;
			        		
			        		variableValueHome = this.getHOMEVariableValue(stringName);
			        		
			        		if(variableValueHome != null){
			        			log.debug("processTacDataTemperature : variableValueHome is NOT null. Name : " + variableValueHome.getName() + " Value : " +variableValueHome.getValue()  + "  timestamp: " + variableValueHome.getTimestamp());

			        			if (variableValueHome.getTimestamp() == logValue.getLogTime().getTime()){
			        				log.debug("processTacDataTemperature : variableValueHome  timestamp is equals to logValue timestamp : " + variableValueHome.getTimestamp());
			        			}else{
			        				log.debug("processTacDataTemperature : variableValueHome  timestamp " + variableValueHome.getTimestamp() + " is NOT equals to logValue timestamp : " + logValue.getLogTime().getTime());
			        				
			        				boolProcessPreviousValues = true;
			        				time4GetListValues = variableValueHome.getTimestamp();			        				
			        			}			        			
			        		}else{
			        			log.debug("processTacDataTemperature : variableValueHome is null. Name : " + stringName);
			        			log.debug("processTacDataTemperature : logValue is NOT null. Name : " +logValue.getTrendLog().getName() + " Value : " +String.valueOf(logValue.getLogValue()));
					        	
				        		try {				        		
					        		if(cep != null){ 
										boolOK = cep.sendEvent(stringName, String.valueOf(logValue.getLogValue()), logValue.getLogTime().getTime());
										
										if(boolOK )
											log.debug("processTacDataTemperature : Data Sent to CEP sucessful ");
										else
											log.error("processTacDataTemperature : Data Sent to CEP ERROR");
					        		}else{
										 log.error("processTacDataTemperature CEP is null : Data NOT sent to CEP");
								    }
								} catch (Exception e) {
									e.printStackTrace();
									log.error("processTacDataTemperature : Data Sent to CEP EXCEPTION " + e.getMessage());
								}	
			        		}
			        	}
			        	
			        	if(boolProcessPreviousValues){
			        		mapLastLogValueTemperature.put(Integer.valueOf(id), logValue);
			        		
			        		log.debug("processTacDataTemperature : mapLastLogValueTemperature updated with last logValue" );
			        		
			        		listLogValue = getListTrendLogValueSinceLogTime(id, new Timestamp(time4GetListValues));
			        		
			        		if(listLogValue != null){
			        			log.debug("processTacDataTemperature : listLogValue is NOT null " + listLogValue.size());
			        		
			        			for  (int j =0 ; j<listLogValue.size() ;j++){
			        			
			        				log.debug("processTacDataTemperature : listLogValue.get(" + j +") is NOT null. Name : " +logValue.getTrendLog().getName() + " Value : " +String.valueOf(logValue.getLogValue()));
						        	
			        				try {				        		
						        		if(cep != null){ 
											boolOK = cep.sendEvent(stringName, String.valueOf(listLogValue.get(j).getLogValue()), listLogValue.get(j).getLogTime().getTime());
											
											if(boolOK )
												log.debug("processTacDataTemperature : Data Sent to CEP sucessful ");
											else
												log.error("processTacDataTemperature : Data Sent to CEP ERROR");
						        		}else{
											 log.error("processTacDataTemperature CEP is null : Data NOT sent to CEP");
									    }
									} catch (Exception e) {
										e.printStackTrace();
										log.error("processTacDataTemperature : Data Sent to CEP EXCEPTION " + e.getMessage());
									}	
			        			}
			        		}else{
			        			log.debug("processTacDataTemperature : listLogValue is null " );
			        		}
			        	}
			        	
			        	
			        }else{
			        	log.error("processTacDataTemperature logValue for variable id " + id +" is null" );
			        }
				}
			}else{
	        	log.error("processTacDataTemperature : listTacVariablesId  is NULL" );
	        }
			        
		} catch (Exception e1) {
	    	log.error("processTacDataTemperature EXCEPTION " + e1.getMessage());
			e1.printStackTrace();
		}	
		
		log.debug("processTacDataTemperature : END ");
		return boolOK;
	}
	
	public TrendLogValue getLastTrendLogValue(int id){
		TacDB db = null;
		TrendLogValue value = null;
		
		try{
			db = new TacDB();			
			value = db.getTrendLogValue(id);
			
			if(value != null){
				log.debug("getLastTacData TrendLogValue is not null for id: " + id + " value: " + value);
			}
			else{
				log.error("getLastTacData TrendLogValue is null ");
			}
		}catch(Exception e){
			log.error("getLastTacData TrendLogValue Exception " + e.getMessage());
		}
		return value;
	}		
	
	private List<TrendLogValue> getListTrendLogValueSinceLogTime(int id, Timestamp logTime) {
		TacDB db = null;
		List<TrendLogValue> list = null;

		log.debug("getListTrendLogValueSinceLogTime START id: " + id  );
		
		if(logTime != null)
			log.debug("getListTrendLogValueSinceLogTime logTime:" + logTime );
		
		
		try{
			db = new TacDB();			
			list = db.getListTrendLogValueSinceLogTime(id,  logTime);
			
			if(list != null){
				log.debug("getListTrendLogValueSinceLogTime list is not null for id: " + id + list.size());
			}
			else{
				log.error("getListTrendLogValueSinceLogTime list is null ");
			}
		}catch(Exception e){
			log.error("getListTrendLogValueSinceLogTime Exception " + e.getMessage());
		}
		return list;
	}
	

	private VariableI getHOMEVariableValue(String varName){
		VariableI instance = null;
		try {
			
			log.debug("getHOMEVariableValue START");
			
			if(home != null){
				instance = home.getVariableValue(varName);
				
				if(instance !=null )
					log.debug("getHOMEVariableValue : getVariableValue OK value for varName: " + varName + " is : "  + instance.getValue());
				else
					log.error("getHOMEVariableValue : getVariableValue ERROR");
			}else{
				 log.error("getHOMEVariableValue home is null : Home not invoked");
		    }
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getHOMEVariableValue : EXCEPTION " + e.getMessage());
		}	
		return instance;
	}
	
	
	public void setActive(boolean activebool) {
		this.active = activebool;
	}


	public boolean getActive() {
		return this.active;
	}
	
}
