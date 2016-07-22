package es.tekniker.eefrmwrk.commons;

import java.util.Date;

public class AlarmI  implements java.io.Serializable {

private static final long serialVersionUID = -6523776980592294203L;

private String alarmCode;
private String alarmDesc;
private String alarmMessage;
private long alarmSeverity;
private String alarmType;
private long alarmTimespan;
private long alarmDuedate;
private String alarmState;
private String alarmRule;


public String getAlarmCode() {
	return alarmCode;
}
public void setAlarmCode(String alarmCode) {
	this.alarmCode = alarmCode;
}
public String getAlarmDesc() {
	return alarmDesc;
}
public void setAlarmDesc(String alarmDesc) {
	this.alarmDesc = alarmDesc;
}
public String getAlarmMessage() {
	return alarmMessage;
}
public void setAlarmMessage(String alarmMessage) {
	this.alarmMessage = alarmMessage;
}
public long getAlarmSeverity() {
	return alarmSeverity;
}
public void setAlarmSeverity(long alarmSeverity) {
	this.alarmSeverity = alarmSeverity;
}
public String getAlarmType() {
	return alarmType;
}
public void setAlarmType(String alarmType) {
	this.alarmType = alarmType;
}
public long getAlarmTimespan() {
	return alarmTimespan;
}
public void setAlarmTimespan(long alarmTimespan) {
	this.alarmTimespan = alarmTimespan;
}
public String getAlarmState() {
	return alarmState;
}
public void setAlarmState(String alarmState) {
	this.alarmState = alarmState;
}
public long getAlarmDuedate() {
	return alarmDuedate;
}
public void setAlarmDuedate(long alarmDuedate) {
	this.alarmDuedate = alarmDuedate;
}
public String getAlarmRule() {
	return alarmRule;
}
public void setAlarmRule(String alarmRule) {
	this.alarmRule = alarmRule;
}


	
}
