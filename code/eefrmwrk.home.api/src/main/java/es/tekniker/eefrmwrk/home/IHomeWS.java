package es.tekniker.eefrmwrk.home;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import es.tekniker.eefrmwrk.commons.DeviceI;
import es.tekniker.eefrmwrk.commons.GeneralElectricMeasureI;
import es.tekniker.eefrmwrk.commons.HomeI;
import es.tekniker.eefrmwrk.commons.TaskI;
import es.tekniker.eefrmwrk.commons.VarMetadataI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;

@WebService
public interface IHomeWS {

	//----------HOME RELATED----------------------------------
	public @WebResult(name = "home")
	HomeI getHome(
			@WebParam(name = "homeName") String homeName)
			throws WSException;
	
	public @WebResult(name = "device")
	List<DeviceI> getHomeDevices(
			@WebParam(name = "homeName") String homeName)
			throws WSException;
	
	public @WebResult(name = "variable")
	List<VariableI> getHomeVariables(
			@WebParam(name = "homeName") String homeName)
			throws WSException;

	public @WebResult(name = "homeName")
	String addHome(
			@WebParam(name = "homeName") String homeName,
			@WebParam(name = "homeLoc") String homeLoc,
			@WebParam(name = "homeEndpoint") String homeEndpoint)
			throws WSException;
	
	public @WebResult(name = "return")
	String deleteHome(
			@WebParam(name = "homeName") String homeName)
			throws WSException;
	
	//-----------DEVICE RELATED------------------------------
	
	public @WebResult(name = "devName")
	String addDevice(
			@WebParam(name = "homeName") String homeName,
			@WebParam(name = "devName") String devName,
			@WebParam(name = "description") String devDesc,
			@WebParam(name = "URI") String devURI,
			@WebParam(name = "status") String devStatus,
			@WebParam(name = "info") String devInfo,
			@WebParam(name = "localization") String devLoc,
			@WebParam(name = "capabilities") String devCapabilities)
			throws WSException;
	
	public @WebResult(name = "device")
	DeviceI getDevice(
			@WebParam(name = "devName") String deviceName)
			throws WSException;

	public @WebResult(name = "return")
	String editDevice(
			@WebParam(name = "devName") String devName,
			@WebParam(name = "description") String devDesc,
			@WebParam(name = "URI") String devURI,
			@WebParam(name = "status") String devStatus,
			@WebParam(name = "info") String devInfo,
			@WebParam(name = "localization") String devLoc,
			@WebParam(name = "capabilities") String devCapabilities)
			throws WSException;
	
	public @WebResult(name = "return")
	String deleteDevice(
			@WebParam(name = "devName") String devName)
			throws WSException;

	public @WebResult(name = "variable")
	List<VariableI> getDeviceVariables(
			@WebParam(name = "devName") String devName)
			throws WSException;
	
	public @WebResult(name = "return")
	String linkDevice(
			@WebParam(name = "devName") String devName,
			@WebParam(name = "homeName") String homeName)
			throws WSException;
	
	public @WebResult(name = "return")
	String unlinkDevice(
			@WebParam(name = "devName") String devName)
			throws WSException;
	
	
	//----------VARMETADATA RELATED----------------------------------
	
	public @WebResult(name = "varName")
	String addVariable(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "entityName") String entityName,	//nombre de la entidad a asociar
			@WebParam(name = "entityType") String entityType,  //HOME o DEVICE
			@WebParam(name = "localization") String localization,
			@WebParam(name = "physicalType") String physicalType,
			@WebParam(name = "digitalType") String digitalType,
			@WebParam(name = "measureUnit") String measureUnit,
			@WebParam(name = "access") String access,
			@WebParam(name = "description") String description,
			@WebParam(name = "store") String store) 
			throws WSException;

	
	public @WebResult(name = "variable")
	VarMetadataI getVariable(
			@WebParam(name = "varName") String varName)
			throws WSException;

	public @WebResult(name = "return")
	String editVariable(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "localization") String localization,
			@WebParam(name = "physicalType") String physicalType,
			@WebParam(name = "digitalType") String digitalType,
			@WebParam(name = "measureUnit") String measureUnit,
			@WebParam(name = "access") String access,
			@WebParam(name = "description") String description,
			@WebParam(name = "store") String store) 
			throws WSException;

	public @WebResult(name = "return")
	String deleteVariable(
			@WebParam(name = "varName") String varName)
			throws WSException;
	
	public @WebResult(name = "variable")
	VariableI getVariableValue(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "date") Long date)
			throws WSException;
	
	public @WebResult(name = "variable") 
	List<VariableI> getVariableValues(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "initialDate") Long initialDate,
			@WebParam(name = "finalDate") Long finalDate,
			@WebParam(name = "duration") Long duration,
			@WebParam(name = "absolut") String absolut)
	throws WSException;
	
	public @WebResult(name = "return")
	String linkVariable(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "entityName") String entityName,	//Id de la entidad a asociar
			@WebParam(name = "entityType") String entityType)  //HOME o DEVICE)
			throws WSException;
	
	public @WebResult(name = "return")
	String unlinkVariable(
			@WebParam(name = "varName") String varName)
			throws WSException;

	//------------TASK RELATED--------------------------------------
	
	public @WebResult(name = "taskName")
	String addScheduledTask(
			@WebParam(name = "taskName")  String taskName,
			@WebParam(name = "description")  String description,
			@WebParam(name = "period")  String period,
			@WebParam(name = "devName")  String devName,
			@WebParam(name = "varName")  String varName,
			@WebParam(name = "value")  String value)
			throws WSException;

	public @WebResult(name = "return")
	String editScheduledTask(
			@WebParam(name = "taskName") String taskName,
			@WebParam(name = "description")  String description,
			@WebParam(name = "period")  String period,
			@WebParam(name = "devName")  String devName,
			@WebParam(name = "varName")  String varName,
			@WebParam(name = "value")  String value) 
			throws WSException;

	public @WebResult(name = "return")
	String deleteScheduledTask(
			@WebParam(name = "taskName") String taskName)
			throws WSException;

	public @WebResult(name = "task")
	List<TaskI> getDeviceScheduledTasks(
			@WebParam(name = "devName") String devName) 
			throws WSException;

//-------------------------------------------------	
	public @WebResult(name = "co2")
	Double getHomeEmissions(
			@WebParam(name = "homeName") String homeName,
			@WebParam(name = "startDate") long startDate,
			@WebParam(name = "endDate") long endDate) 
			throws WSException;

	public @WebResult(name = "result")
	String sendNotification(
			@WebParam(name = "alarmId") String alarmId,
			@WebParam(name = "notificationId") String notificationId,
			@WebParam(name = "timeSpan") long timeSpan,
			@WebParam(name = "message") String message) 
			throws WSException;
	
	public @WebResult(name = "alarmId")
	String configureAlarmNotification(
			@WebParam(name = "homeName") String homeName,
			@WebParam(name = "localization") String localization,
			@WebParam(name = "category") String category,
			@WebParam(name = "priority") String priority,
			@WebParam(name = "endpoint") String endpoint,
			@WebParam(name = "condition") String condition) 
			throws WSException;
	/*public @WebResult(name = "returnValue")
	boolean sendTargetValue(
			@WebParam(name = "varName") String varName,
			@WebParam(name = "targetValue") String targetValue)
			throws WSException;*/
	//-------------------------------------------------
	public @WebResult (name="return")
	String addValue(
			@WebParam(name = "varName") String varName, 
			@WebParam(name = "value") String value, 
			@WebParam(name = "timestamp") long timestamp
			)throws WSException;
	
	//----maxResults--------
	public @WebResult(name = "maxResult")
	String getMaxResults() throws WSException;

	public @WebResult(name = "return")
	String setMaxResults(
			@WebParam(name = "maxResults") String maxResults)
			throws WSException;
}