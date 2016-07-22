package es.tekniker.eefrmwrk.cep.listeners;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;

import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.Rule;
import es.tekniker.eefrmwrk.cep.entity.CepEvent;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;
/**
 * Obtiene la información de una variable a partir de un evento CEP y la guarda
 * en la base de datos
 */
public class VR_Storer implements AbstractCEP_Listener {	
	//private String ruleName;
	//private CEP cep;
	private RuleEntity  ruleEntity;
	private static final Log log = LogFactory.getLog(VR_Storer.class);
	
	//public VR_Storer(String ruleName, CEP cep) {
	public VR_Storer(RuleEntity rule) {
		log.debug("VR_Storer created");
		//this.cep=cep;
		//this.ruleName=ruleName;
		this.ruleEntity=rule;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		log.debug("Updating with VR_Storer ["+ruleEntity.getCepName()+"]");
		if (newData != null)
			for (EventBean eb : newData) {
				try {
					CepEvent cE = (CepEvent) eb.getUnderlying();
					DBManager.saveVR(cE.getVarName(), cE.getValue()+"",cE.getTimestamp());
					log.info("Stored "+cE.getVarName()+":"+ cE.getValue()+" "+new Date(cE.getTimestamp()));
					//cep.listaReglas.get(ruleName).setStatus(Rule.status_OK);
					//cep.listaReglas.get(ruleName).setInfo("");
				} catch (Exception e) {
					log.error("Error updating with VR_Storer:"+e.getMessage());
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