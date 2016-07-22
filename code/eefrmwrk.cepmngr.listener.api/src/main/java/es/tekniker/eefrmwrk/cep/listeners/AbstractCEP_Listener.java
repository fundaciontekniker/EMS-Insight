package es.tekniker.eefrmwrk.cep.listeners;

import com.espertech.esper.client.UpdateListener;
import es.tekniker.eefrmwrk.commons.BaseException;
public interface AbstractCEP_Listener extends UpdateListener {
	
/**
	 * This function checks if a given EPL is good to use with the Listener
	 */
	public boolean checkRule(String epl) throws BaseException;

}
