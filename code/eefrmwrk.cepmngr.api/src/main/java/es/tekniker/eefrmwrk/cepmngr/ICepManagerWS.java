package es.tekniker.eefrmwrk.cepmngr;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import es.tekniker.eefrmwrk.commons.AlarmI;
import es.tekniker.eefrmwrk.commons.GeneralElectricMeasureI;
import es.tekniker.eefrmwrk.commons.RuleI;
import es.tekniker.eefrmwrk.commons.CepEventI;
import es.tekniker.eefrmwrk.commons.WSException;

@WebService
public interface ICepManagerWS {

	public @WebResult(name = "result")
	boolean addEvent(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "value") String value,
			@WebParam(name = "timestamp") Long timestamp)
			throws WSException;
	
	public @WebResult(name = "result")
	boolean addPredictionEvent(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "value") String value,
			@WebParam(name = "timestamp") Long timestamp,
			@WebParam(name = "predictDate") Long predictDate)
			throws WSException;
	
	/*public @WebResult(name = "result")
	boolean addElectricEvent(
			@WebParam(name = "event") GeneralElectricMeasureI event)
			throws WSException;*/
	
	/*public @WebResult(name = "result")
	boolean addElectricEvent(
			@WebParam(name = "devName") String devName,
			@WebParam(name = "CUPid") String CUPid,
			@WebParam(name = "timestamp") long timestamp,
			@WebParam(name = "activeIn") String activeIn,
			@WebParam(name = "activeOut") String activeOut,
			@WebParam(name = "qActiveIn") String qActiveIn,
			@WebParam(name = "qActiveOut") String qActiveOut,
			@WebParam(name = "reactive1") String reactive1,
			@WebParam(name = "reactive2") String reactive2,
			@WebParam(name = "reactive3") String reactive3,
			@WebParam(name = "reactive4") String reactive4,
			@WebParam(name = "qReactive1") String qReactive1,
			@WebParam(name = "qReactive2") String qReactive2,
			@WebParam(name = "qReactive3") String qReactive3,
			@WebParam(name = "qReactive4") String qReactive4,
			@WebParam(name = "gotten") String gotten)
			throws WSException;*/
	
	public @WebResult(name = "result")
	String addRule(
			@WebParam(name = "ruleName") String ruleName,
			@WebParam(name = "ruleEPL") String rule,
			@WebParam(name = "ruleListener") String listener,
			@WebParam(name = "ruleSeverity") Long severity,
			@WebParam(name = "ruleMessage") String message)
			throws WSException;

	public @WebResult(name = "result")
	String deleteRule(
			@WebParam(name = "ruleName") String ruleName)
			throws WSException;
	
	public @WebResult(name = "result")
	String updateRule(
			@WebParam(name = "ruleName") String ruleName,
			@WebParam(name = "ruleEPL") String rule,
			@WebParam(name = "ruleListener") String listener,
			@WebParam(name = "ruleSeverity") Long severity,
			@WebParam(name = "ruleMessage") String message)
			throws WSException;
	
	 public @WebResult(name="rule") 
	 List<RuleI> getRules() throws WSException;
	 
	 public @WebResult(name = "status")
		String getStatus() throws WSException;
		
	//---------------------------------------------	
	 public @WebResult(name="alarm") 
	 List<AlarmI> getAlarms(@WebParam(name = "status") String status) throws WSException;
	 
		//---------------------------------------------	
	 public @WebResult(name="alarm") 
	 List<AlarmI> getVariableAlarms(@WebParam(name = "varName") String varName) throws WSException;
	 
	 public @WebResult(name="result") 
	 String addAlarm(
			 @WebParam(name = "alarmCode") String alarmCode,
			 @WebParam(name = "alarmType") String alarmType,
			 @WebParam(name = "alarmDesc") String alarmDesc,
			 @WebParam(name = "alarmMessage") String alarmMessage,
			 @WebParam(name = "alarmTimespan") long alarmTimespan,
			 @WebParam(name = "alarmDuedate") long alarmDuedate,
			 /*@WebParam(name = "alarmRule") String alarmRule,*/
			 @WebParam(name = "alarmSeverity") long alarmSeverity
			 ) throws WSException;
	 
	 public @WebResult(name="result") 
	 String updateAlarm(
			 @WebParam(name = "alarmCode") String alarmCode,
			 @WebParam(name = "alarmType") String alarmType,
			 @WebParam(name = "alarmDesc") String alarmDesc,
			 @WebParam(name = "alarmMessage") String alarmMessage,
			 @WebParam(name = "alarmTimespan") long alarmTimespan,
			 @WebParam(name = "alarmDuedate") long alarmDuedate,
			 /*@WebParam(name = "alarmRule") String alarmRule,*/
			 @WebParam(name = "alarmSeverity") long alarmSeverity,
			 @WebParam(name = "alarmState") String alarmState
			 ) throws WSException;
	 
	 public @WebResult(name="result") 
	 String deleteAlarm(
			 @WebParam(name = "alarmCode") String alarmCode
			 ) throws WSException;
}