package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class PredictorVariableI implements Serializable{

	private static final long serialVersionUID = 3422011902135750510L;
	private String varName;
	private String itpl_f;
	private Boolean norm;
	
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getItpl_f() {
		return itpl_f;
	}
	public void setItpl_f(String itpl_f) {
		this.itpl_f = itpl_f;
	}
	public Boolean getNorm() {
		return norm;
	}
	public void setNorm(Boolean norm) {
		this.norm = norm;
	}
}
