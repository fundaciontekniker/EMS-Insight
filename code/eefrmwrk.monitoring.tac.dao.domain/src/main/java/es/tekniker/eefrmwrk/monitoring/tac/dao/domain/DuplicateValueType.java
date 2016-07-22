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

public class DuplicateValueType {
	public DuplicateValueType() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_DUPLICATEVALUETYPE_DUPLICATEVALUE) {
			return ORM_duplicateValue;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private byte duplicateValueTypeId;
	
	private String name;
	
	private java.util.Set ORM_duplicateValue = new java.util.HashSet();
	
	public void setDuplicateValueTypeId(byte value) {
		this.duplicateValueTypeId = value;
	}
	
	public byte getDuplicateValueTypeId() {
		return duplicateValueTypeId;
	}
	
	public byte getORMID() {
		return getDuplicateValueTypeId();
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	private void setORM_DuplicateValue(java.util.Set value) {
		this.ORM_duplicateValue = value;
	}
	
	private java.util.Set getORM_DuplicateValue() {
		return ORM_duplicateValue;
	}
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueSetCollection duplicateValue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_DUPLICATEVALUETYPE_DUPLICATEVALUE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_DUPLICATEVALUE_DUPLICATEVALUETYPE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public String toString() {
		return String.valueOf(getDuplicateValueTypeId());
	}
	
}
