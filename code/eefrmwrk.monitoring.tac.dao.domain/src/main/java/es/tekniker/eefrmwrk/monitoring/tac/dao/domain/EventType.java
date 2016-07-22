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

public class EventType {
	public EventType() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENTTYPE_EVENT) {
			return ORM_event;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private short eventTypeId;
	
	private String name;
	
	private java.util.Set ORM_event = new java.util.HashSet();
	
	private void setEventTypeId(short value) {
		this.eventTypeId = value;
	}
	
	public short getEventTypeId() {
		return eventTypeId;
	}
	
	public short getORMID() {
		return getEventTypeId();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	private void setORM_Event(java.util.Set value) {
		this.ORM_event = value;
	}
	
	private java.util.Set getORM_Event() {
		return ORM_event;
	}
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection event = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENTTYPE_EVENT, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_EVENTTYPE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public String toString() {
		return String.valueOf(getEventTypeId());
	}
	
}
