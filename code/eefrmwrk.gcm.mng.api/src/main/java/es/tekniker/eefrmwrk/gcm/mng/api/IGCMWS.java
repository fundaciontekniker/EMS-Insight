package es.tekniker.eefrmwrk.gcm.mng.api;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import es.tekniker.eefrmwrk.commons.WSException;

@WebService
public interface IGCMWS {

	public @WebResult(name = "result")
	boolean setRegID(
			@WebParam(name = "RegID") String RegID)
			throws WSException;
	
	public @WebResult(name = "result")
	boolean sendGCMmessage(@WebParam(name = "title") String title, @WebParam(name = "message") String message, @WebParam(name = "id") String id );
	
}