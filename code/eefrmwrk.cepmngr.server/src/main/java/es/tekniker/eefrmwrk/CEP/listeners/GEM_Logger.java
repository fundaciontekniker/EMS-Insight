package es.tekniker.eefrmwrk.cep.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.GeneralElectricMeasure;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.manage.DeviceManager;
import es.tekniker.eefrmwrk.database.sql.model.Device;

/**
 * Obtiene la información de una variable a partir de un evento CEP y la
 * registra en el log
 */
public class GEM_Logger implements AbstractCEP_Listener {
	//private String ruleName;
	//private CEP cep;
	private RuleEntity ruleEntity = null;
	private static final Log log = LogFactory.getLog(GEM_Logger.class);
	//public GEM_Logger(String ruleName, CEP cep) {
	public GEM_Logger(RuleEntity rule) {
		log.debug("GEM_Logger created");
		//this.ruleName=ruleName;
		this.ruleEntity=rule;
		//this.cep=cep;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		log.debug("Updating with GEM_Logger");
		if (newData != null)
			for (EventBean eb : newData) {
		
		GeneralElectricMeasure cE = (GeneralElectricMeasure)eb.getUnderlying();
		try {
			Device dev = DeviceManager.find(cE.getDeviceId());
			log.info(dev.getDevName() + ":["	+ cE.getTimestamp() + "]");
		} catch (Exception e) {
			log.error("Error updating with GEM_Logger");
			//cep.listaReglas.get(ruleName).setStatus("UpdateFail");
		}
	}}

	//@Override
	public boolean checkRule(String epl) throws BaseException {
		return false;
	}
}