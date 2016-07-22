package es.tekniker.eefrmwrk.cep.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.GeneralElectricMeasure;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;

/**
 * Obtiene la información de una medida electrica a partir de un evento
 * GeneralElectricMeasure y la guarda en la base da datos
 */
public class GEM_Storer implements AbstractCEP_Listener {
	
	private static final Log log = LogFactory.getLog(GEM_Storer.class);
	//private String ruleName; 
	//private CEP cep;
	private RuleEntity ruleEntity = null;
	
	//public GEM_Storer(String ruleName, CEP cep) {
	public GEM_Storer(RuleEntity rule) {
		log.debug("GEM_Storer created");
		this.ruleEntity=rule;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		log.debug("Updating with GEM_Storer");
		if (newData != null)
			for (EventBean eb : newData) {
				try {
					GeneralElectricMeasure gme = (GeneralElectricMeasure) eb.getUnderlying();		
					DBManager.saveGEM(gme.getDeviceId(), gme.getTimestamp(), gme);
					log.info("Measure updated for device " + gme.getDeviceId() + ":["+ gme.getTimestamp() + "]");						
				} catch (Exception e) {
					log.error("RULEUPADTEERROR:"+e.getMessage());
					//cep.listaReglas.get(ruleName).setStatus("UpdateFail");
		}
	}}

	//@Override
	public boolean checkRule(String epl) throws BaseException {
		// TODO Auto-generated method stub
		return false;
	}
}