package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class SubscriptionI implements Serializable{

	private static final long serialVersionUID = 526075493897040918L;
	
	private String url;
	private String nodeName;
	private String nodeNmsp;
	private String varName;
	private String dataChange;
	private Integer pubInt;
	
	private String status;
	private String info;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeNmsp() {
		return nodeNmsp;
	}
	public void setNodeNmsp(String nodeNmsp) {
		this.nodeNmsp = nodeNmsp;
	}
	public String getVarName() {
		return varName;
	}
	public void setVarName(String varName) {
		this.varName = varName;
	}
	public String getDataChange() {
		return dataChange;
	}
	public void setDataChange(String dataChange) {
		this.dataChange = dataChange;
	}
	public Integer getPubInt() {
		return pubInt;
	}
	public void setPubInt(Integer pubInt) {
		this.pubInt = pubInt;
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
	
	public String getStatusInfo(){
		String s= url+":["+nodeName+","+nodeNmsp+"] STATUS:"+status;
		if (!info.isEmpty())
			s=s+"["+info+"]";
		return s;
	}
	}
