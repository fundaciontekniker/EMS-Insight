package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

/**
 * @author agarcia
 */
public class CepEventI implements Serializable{

	private static final long serialVersionUID = -7913255052624254190L;
	private String var;
	private String value;
	private long timestamp;
	
	
	public String getVar() {
		return var;
	}
	public void setVar(String var) {
		this.var = var;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String o) {
		this.value = o;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
