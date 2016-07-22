package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class OpcClientStatusI implements Serializable{

	private static final long serialVersionUID = -8212226580183732125L;

	private String url;
	//private String desc;
	private String status;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	/*public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}*/
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
