package es.tekniker.eefrmwrk.gcm;

import java.util.List;

/**
 * GCMmessage class to define message for GCM Google Cloud Messaging
 * @author Ignacio Lazaro Llorente IK4-Tekniker 2016
 *
 */
public class GCMmessage {
	
	//GCM data
	private String title=null, message=null , id=null, collapseKey=null;
	private List<String> devices=null;
	private String locale=null;

	//GCM result data
	private String sendingResult=null;
	private int state=0;
	private String GCMMessageId=null;

	//Incidence Data
	private int incidenceId=0;
	private List<Integer> usersId=null;
		
	public GCMmessage (){
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollapseKey() {
		return collapseKey;
	}

	public void setCollapseKey(String collapseKey) {
		this.collapseKey = collapseKey;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public String getSendingResult() {
		return sendingResult;
	}

	public void setSendingResult(String sendingResult) {
		this.sendingResult= sendingResult;
	}
	
	public int getState()	{
		return state;
	}
	
	public void setState(int state) {
		this.state=state;
	}

	public int getIncidenceId()	{
		return incidenceId;
	}
	
	public void setIncidenceId(int incidenceId) {
		this.incidenceId=incidenceId;
	}
	
	public String getGCMMessageId()	{
		return GCMMessageId;
	}
	
	public void setGCMMessageId(String GCMMessageId) {
		this.GCMMessageId=GCMMessageId;
	}	
	
	public List<String> getDevices() {
		return devices;
	}

	public void setDevices(List<String> devices) {
		this.devices = devices;
	}
	
	public List<Integer> getUsersId() {
		return usersId;
	}

	public void setUsersId(List<Integer> usersId) {
		this.usersId = usersId;
	}
	
	
	
	
}
