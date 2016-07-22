package es.tekniker.eefrmwrk.cep.listeners;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;

import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.Rule;
import es.tekniker.eefrmwrk.cep.entity.CepEvent;
import es.tekniker.eefrmwrk.cep.entity.CepPrediction;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;

/**
 * Obtiene la información de una variable a partir de un evento CEP y la
 * registra en el log
 */
public class VR_Logger implements AbstractCEP_Listener {
	//private String ruleName;
	//private CEP cep;
	private RuleEntity ruleEntity = null;
	private static final Log log = LogFactory.getLog(VR_Logger.class);
	
	//public VR_Logger(String ruleName, CEP cep) {
	public VR_Logger(RuleEntity rule) {
		log.debug("VR_Logger created");		
		//this.cep=cep;
		//this.ruleName=ruleName;
		this.ruleEntity=rule;
		
	}

	public void update(EventBean[] newData, EventBean[] oldData) {	
		log.debug("Updating with VR_Logger ["+ruleEntity.getCepName()+"]");
		if (newData != null)
			for (EventBean eb : newData) {
				for(String s:eb.getEventType().getPropertyNames()){
					log.debug(s+" : "+eb.get(s));
				}
			//String message=cep.listaReglas.get(ruleName).getCepRule().getCepMessage();
			String message=ruleEntity.getCepMessage();
			if(message!=null && !message.isEmpty())
				log.info(message);
				
			if(eb.getUnderlying() instanceof CepEvent){
				CepEvent cE = (CepEvent) eb.getUnderlying();
				log.info("LOG:"+cE.getVarName()+":"+ cE.getValue()+" "+new Date(cE.getTimestamp()));}
			else if(eb.getUnderlying() instanceof CepPrediction){
				CepPrediction cE = (CepPrediction) eb.getUnderlying();
				log.info("LOG_Predicted:"+cE.getVarName()+":"+ cE.getValue()+" "+new Date(cE.getTimestamp()));}
			else {
				try{
					CepEvent cE =(CepEvent)eb.get("event");
					log.info(cE.getVarName()+":"+ cE.getValue()+" "+new Date(cE.getTimestamp()));}
				catch(Exception e){
					log.error("Error updating with VR_Logger:"+e.getMessage());
					//cep.listaReglas.get(ruleName).setStatus(Rule.status_UP_FAIL);
					//cep.listaReglas.get(ruleName).setInfo(e.getMessage());
				}}
			}
	}

	//@Override
	public boolean checkRule(String epl) throws BaseException {
			return true;
	}
}