package es.tekniker.eefrmwrk.cep.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyDescriptor;

import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

/**
 * Obtiene la información de una variable a partir de un evento CEP y la guarda
 * en la base de datos
 */
public class General_Logger implements AbstractCEP_Listener {
	
	private static final Log log = LogFactory.getLog(General_Logger.class);
	//private String ruleName;
	//private CEP cep;
	private RuleEntity ruleEntity = null;
	
	//public General_Logger(String ruleName, String epl, CEP cep) throws BaseException{
	public General_Logger(RuleEntity rule) throws BaseException{
		log.debug("General_Logger created");
		//his.ruleName=ruleName;
		//this.cep=cep;
		//checkRule(epl);
		this.ruleEntity=rule;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		if(newData!=null)
		for (EventBean eB : newData) {
			try {
				for(EventPropertyDescriptor o :eB.getEventType().getPropertyDescriptors())
					log.info(o.getPropertyName()+":"+eB.get(o.getPropertyName()));
				
			} catch (Exception e) {
				log.error("Error updating with General_Logger");
				//cep.listaReglas.get(ruleName).setStatus("UpdateFail");				
			}
		}
	}

	//@Override
	public boolean checkRule(String epl) throws BaseException {
		String[] arr=epl.split(" [fF][rR][oO][mM] ");
		arr= arr[0].split("[sS][eE][lL][eE][cC][tT] ");
		arr=arr[1].split(",");
		
		String varName="";
		int vars=0;
		boolean timestamp=false;
		for(String s:arr){
				String[] s1=s.split(" [aA][sS] ");
				if (s1.length>1)
					varName=s1[1].trim();
				else
					varName=s.trim();
				if (varName.matches("\\*")){
					throw new BaseException("General_Logger_CHECKRULE1"," '*' can NOT be used in SELECT clause");
				}
				if (varName.equals("timestamp")){
					timestamp=true;
				}else{
				VarMetadata vmd = VarMetadataManager.findByName(varName);
				if (vmd!=null){
					vars++;
				}
				else
					throw new BaseException("General_Logger_CHECKRULE0",varName+" can NOT be found in database");
				}
		}

		if(!timestamp){
			throw new BaseException("General_Logger_CHECKRULE2","'timestamp' has to be included in SELECT clause");
		}
		if(vars==0){
			throw new BaseException("General_Logger_CHECKRULE3","At least 1 variable should be in SELECT clause");
		}
		return true;
	}
}