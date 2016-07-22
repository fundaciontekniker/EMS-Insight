package es.tekniker.eefrmwrk.cep.entity;

public class RuleEntity {
	
	public static String status_OK="OK";
	public static String status_FAIL="FAIL";
	public static String status_STOPPED="STOP";
	public static String status_UP_FAIL="FAIL ON UPDATE";
	
	private long cepId;	
	private long cepEngine;	
	private String cepName;	
	private String cepEpl;	
	private String cepListener;	
	private long cepSeverity;	
	private String cepMessage;	
	private long activ;
	private String status;
	private String info;
	public long getCepId() {
		return cepId;
	}
	public void setCepId(long cepId) {
		this.cepId = cepId;
	}
	public long getCepEngine() {
		return cepEngine;
	}
	public void setCepEngine(long cepEngine) {
		this.cepEngine = cepEngine;
	}
	public String getCepName() {
		return cepName;
	}
	public void setCepName(String cepName) {
		this.cepName = cepName;
	}
	public String getCepEpl() {
		return cepEpl;
	}
	public void setCepEpl(String cepEpl) {
		this.cepEpl = cepEpl;
	}
	public String getCepListener() {
		return cepListener;
	}
	public void setCepListener(String cepListener) {
		this.cepListener = cepListener;
	}
	public long getCepSeverity() {
		return cepSeverity;
	}
	public void setCepSeverity(long cepSeverity) {
		this.cepSeverity = cepSeverity;
	}
	public String getCepMessage() {
		return cepMessage;
	}
	public void setCepMessage(String cepMessage) {
		this.cepMessage = cepMessage;
	}
	public long getActiv() {
		return activ;
	}
	public void setActiv(long activ) {
		this.activ = activ;
	}	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}

	
	
}
