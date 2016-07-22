package es.tekniker.eefrmwrk.cep.listeners;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.classic.Session;

import com.espertech.esper.client.EventBean;

import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.Rule;
import es.tekniker.eefrmwrk.cep.entity.CepPrediction;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;
/**
 * Stores the values of a prediction variable, erasing other previously predicted values with bigger timestamps
 */
public class VR_Predict implements AbstractCEP_Listener {	
	//private String ruleName;
	//private CEP cep;
	private RuleEntity  ruleEntity;
	private static final Log log = LogFactory.getLog(VR_Predict.class);
	
	//public VR_Predict(String ruleName, CEP cep) {
	public VR_Predict(RuleEntity rule) {
		log.debug("VR_Predict created");
		//this.cep=cep;
		//this.ruleName=ruleName;
		this.ruleEntity=rule;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		log.debug("Updating with VR_Predict ["+ruleEntity.getCepName()+"]");
		if (newData != null)
			for (EventBean eb : newData) {
				try {
					CepPrediction cE = (CepPrediction) eb.getUnderlying();
					DBManager.deleteVariableValues(cE.getVarName(),cE.getTimestamp(),Long.MAX_VALUE);
					DBManager.saveVR(cE.getVarName(), cE.getValue()+"",cE.getTimestamp());
					log.info("Updated "+cE.getVarName()+":"+ cE.getValue()+" "+new Date(cE.getTimestamp()));
					//cep.listaReglas.get(ruleName).setStatus(Rule.status_OK);
					//cep.listaReglas.get(ruleName).setInfo("");
				} catch (Exception e) {
					log.error("Error updating with VR_Predict:"+e.getMessage());
					//cep.listaReglas.get(ruleName).setStatus(Rule.status_UP_FAIL);
					//cep.listaReglas.get(ruleName).setInfo(e.getMessage());
				}
			}
	}

	//@Override
	public boolean checkRule(String epl) throws BaseException {
		return true;
	}
}