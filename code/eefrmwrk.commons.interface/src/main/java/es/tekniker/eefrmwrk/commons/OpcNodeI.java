package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;

public class OpcNodeI implements Serializable{

	private String nodeName;
	private String nodeNmsp;
	private String url;
	
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private static final long serialVersionUID = 186091147134915371L;
}
