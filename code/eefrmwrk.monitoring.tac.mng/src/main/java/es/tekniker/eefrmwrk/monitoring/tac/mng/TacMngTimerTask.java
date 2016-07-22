package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.util.Calendar;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class TacMngTimerTask extends TimerTask{
	
	private static Log log =LogFactory.getLog(TacMngTimerTask.class);
	
	private TacMng tacMng =null;
	
	public TacMngTimerTask (TacMng tacMng){
		this.tacMng=tacMng;	
	}
	
	@Override
	public void run() {
	    log.debug("run START at " + Calendar.getInstance().getTimeInMillis() );
	    tacMng.processTacData();
		log.debug("run END at " + Calendar.getInstance().getTimeInMillis() );
	}
	
}
