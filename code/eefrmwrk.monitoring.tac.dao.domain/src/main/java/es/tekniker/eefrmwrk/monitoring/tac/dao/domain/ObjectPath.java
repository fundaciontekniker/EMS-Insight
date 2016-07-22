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

public class ObjectPath {
	public ObjectPath() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_OBJECTPATH_EVENT) {
			return ORM_event;
		}
		else if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_OBJECTPATH_EVENT1) {
			return ORM_event1;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private int objectPathId;
	
	private String name;
	
	private java.util.Set ORM_event = new java.util.HashSet();
	
	private java.util.Set ORM_event1 = new java.util.HashSet();
	
	private void setObjectPathId(int value) {
		this.objectPathId = value;
	}
	
	public int getObjectPathId() {
		return objectPathId;
	}
	
	public int getORMID() {
		return getObjectPathId();
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
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection event = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_OBJECTPATH_EVENT, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_OBJECTPATH, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	private void setORM_Event1(java.util.Set value) {
		this.ORM_event1 = value;
	}
	
	private java.util.Set getORM_Event1() {
		return ORM_event1;
	}
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection event1 = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_OBJECTPATH_EVENT1, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_SHORTCUTPATH, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public String toString() {
		return String.valueOf(getObjectPathId());
	}
	
}
