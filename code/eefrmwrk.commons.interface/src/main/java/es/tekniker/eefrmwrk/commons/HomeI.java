package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class HomeI implements Serializable {

	private static final long serialVersionUID = -1034820487467872745L;

	private String name;
	private String endpoint;
	private String localization;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getLocalization() {
		return localization;
	}
	public void setLocalization(String localization) {
		this.localization = localization;
	}
	

	
}
