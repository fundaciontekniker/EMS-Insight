package es.tekniker.eefrmwrk.cep.entity;

import java.util.Date;

public class CepEvent {

	private String varName;
	private Object value;
	private long timestamp;
	private boolean predict;

	public CepEvent() {
	}

	public CepEvent(String vN, Object v) {
		varName = vN;
		value = v;
		timestamp = System.currentTimeMillis();
		predict=false;
	}
	public CepEvent(String vN, Object v,boolean p) {
		varName = vN;
		value = v;
		timestamp = System.currentTimeMillis();
		predict=p;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public Object getValue() {
		return value;
	}
	
	public boolean getPredict() {
		return predict;
	}


	public void setValue(Object value) {
		this.value = value;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Double getNumValue() {
		return new Double(value.toString());
	}
	
	@Override
	public String toString(){return varName+":"+value+"["+ new Date(timestamp)+"]";}
}
