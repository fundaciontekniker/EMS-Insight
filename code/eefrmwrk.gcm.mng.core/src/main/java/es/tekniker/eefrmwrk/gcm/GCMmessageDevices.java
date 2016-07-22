package es.tekniker.eefrmwrk.gcm;

import java.util.List;

/**
 * GCMmessageDevices class to associate devices with users by lang
 * @author Ignacio Lazaro Llorente IK4-Tekniker 2016
 *
 */
public class GCMmessageDevices {

	private String locale;
	private List<String> devices=null;
	private List<Integer> users=null;
	
	public String getLocale() {
		return locale;
	}
	
	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	public List<String> getDevices() {
		return devices;
	}
	
	public void setDevices(List<String> devices) {
		this.devices = devices;
	}
	
	public List<Integer> getUsers() {
		return users;
	}
	
	public void setUsers(List<Integer> users) {
		this.users = users;
	}
	
}
