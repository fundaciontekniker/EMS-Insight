package es.tekniker.eefrmwrk.commons;

import java.io.Serializable;
/**
 * @author agarcia
 */
public class RuleI implements Serializable{

	private static final long serialVersionUID = 3697603847558085109L;
	private String ruleName;
	private String ruleEPL;
	private String ruleListener;
	private Long   ruleSeverity;
	private String ruleMessage;
	private String ruleStatus;
	private String ruleInfo;
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleEPL() {
		return ruleEPL;
	}
	public void setRuleEPL(String ruleEPL) {
		this.ruleEPL = ruleEPL;
	}
	public String getRuleListener() {
		return ruleListener;
	}
	public void setRuleListener(String ruleListener) {
		this.ruleListener = ruleListener;
	}
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public Long getRuleSeverity() {
		return ruleSeverity;
	}
	public void setRuleSeverity(Long ruleSeverity) {
		this.ruleSeverity = ruleSeverity;
	}
	public String getRuleMessage() {
		return ruleMessage;
	}
	public void setRuleMessage(String ruleMessage) {
		this.ruleMessage = ruleMessage;
	}
	public String getRuleInfo() {
		return ruleInfo;
	}
	public void setRuleInfo(String ruleInfo) {
		this.ruleInfo = ruleInfo;
	}
	
	
}
