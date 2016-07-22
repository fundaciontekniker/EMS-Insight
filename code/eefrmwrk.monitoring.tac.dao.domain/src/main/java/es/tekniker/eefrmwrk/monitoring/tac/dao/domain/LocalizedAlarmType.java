/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Virginia Aranceta
 * License Type: Purchased
 */
package es.tekniker.eefrmwrk.monitoring.tac.dao.domain;

public class LocalizedAlarmType {
	public LocalizedAlarmType() {
	}
	
	private int alarmTypeId;
	
	private String alarmTypeText;
	
	private void setAlarmTypeId(int value) {
		this.alarmTypeId = value;
	}
	
	public int getAlarmTypeId() {
		return alarmTypeId;
	}
	
	public int getORMID() {
		return getAlarmTypeId();
	}
	
	public void setAlarmTypeText(String value) {
		this.alarmTypeText = value;
	}
	
	public String getAlarmTypeText() {
		return alarmTypeText;
	}
	
	public String toString() {
		return String.valueOf(getAlarmTypeId());
	}
	
}
