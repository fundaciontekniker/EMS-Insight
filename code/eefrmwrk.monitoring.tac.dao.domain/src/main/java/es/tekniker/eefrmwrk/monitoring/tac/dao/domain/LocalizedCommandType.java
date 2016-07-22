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

public class LocalizedCommandType {
	public LocalizedCommandType() {
	}
	
	private int commandTypeId;
	
	private String commandText;
	
	private void setCommandTypeId(int value) {
		this.commandTypeId = value;
	}
	
	public int getCommandTypeId() {
		return commandTypeId;
	}
	
	public int getORMID() {
		return getCommandTypeId();
	}
	
	public void setCommandText(String value) {
		this.commandText = value;
	}
	
	public String getCommandText() {
		return commandText;
	}
	
	public String toString() {
		return String.valueOf(getCommandTypeId());
	}
	
}
