package es.tekniker.eefrmwrk.cep.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;

import es.tekniker.database.DBManager;
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
public class General_Storer implements AbstractCEP_Listener {
	private static final Log log = LogFactory.getLog(General_Storer.class);
	//private String ruleName;
	//private CEP cep;
	
	private RuleEntity  ruleEntity;

	//public General_Storer(String ruleName, CEP cep) {
	public General_Storer(RuleEntity rule) {
		log.debug("General_Storer created");
		this.ruleEntity=rule;
	}

	public void update(EventBean[] newData, EventBean[] oldData) {
		if(newData!=null)
		for (EventBean eB : newData) {
			try {
				long tmsp=(Long)eB.get("timestamp");
				
				String[] propertyNames= eB.getEventType().getPropertyNames();
				if (propertyNames.length<2){
					throw new BaseException("General_Storer_CHECKBEAN1","NOT ENOUGH STUFF");
				}
				for(String property:propertyNames){
					if(!property.equals("timestamp")){
						DBManager.saveVR(property,(String)eB.get(property), tmsp);
					}
				}
				//cep.listaReglas.get(ruleName).setStatus("OK");
			} catch (Exception e) {
				log.error("Error updating with General_Storer");
				//cep.listaReglas.get(ruleName).setStatus("UpdateFail");
			}
		}
	}

	//@Override
	/**
	 * 1. Debe devolver timestamp
	 * 2. Tabla cepEVENTS
	 * 3. Al menos una varible aparte de timestamp
	 * 4. las variables (o sus alias) deben exisir en la BBDD
	 */
	public boolean checkRule(String epl) throws BaseException {
		String[] arr;
		String[] s1;

		System.out.println();
		arr=epl.split("[fF][rR][oO][mM]");
		arr= arr[0].split("[sS][eE][lL][eE][cC][tT]");
		arr=arr[1].split(",");
		
		String varName="";
		for(String s:arr){
			try {
				s1=s.split(" [aA][sS] ");
				if (s1.length>1)
					varName=s1[1];
				else
					varName=s;
			
				VarMetadata vmd = VarMetadataManager.findByName(varName);
				if (vmd!=null)
					System.out.println(varName);
				else
					System.out.println(varName+" NO EXISTE EN BBDD!!");
				
			} catch (Exception e) {//BaseException e) {
				System.err.println("Error:"+varName+"/"+e.getMessage());
			}			
		}	
		return false;
	}
}