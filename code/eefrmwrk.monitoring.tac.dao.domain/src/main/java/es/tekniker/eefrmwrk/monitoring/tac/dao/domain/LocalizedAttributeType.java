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

import java.io.Serializable;
public class LocalizedAttributeType implements Serializable {
	public LocalizedAttributeType() {
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof LocalizedAttributeType))
			return false;
		LocalizedAttributeType localizedattributetype = (LocalizedAttributeType)aObj;
		if ((getAttributeType() != null && !getAttributeType().equals(localizedattributetype.getAttributeType())) || (getAttributeType() == null && localizedattributetype.getAttributeType() != null))
			return false;
		if (getObjectType() == null) {
			if (localizedattributetype.getObjectType() != null)
				return false;
		}
		else if (!getObjectType().equals(localizedattributetype.getObjectType()))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getAttributeType() == null ? 0 : getAttributeType().hashCode());
		if (getObjectType() != null) {
			hashcode = hashcode + (getObjectType().getORMID() == null ? 0 : getObjectType().getORMID().hashCode());
		}
		return hashcode;
	}
	
	private void this_setOwner(Object owner, int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_LOCALIZEDATTRIBUTETYPE_OBJECTTYPE) {
			this.objectType = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType) owner;
		}
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public void setOwner(Object owner, int key) {
			this_setOwner(owner, key);
		}
		
	};
	
	private String attributeType;
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType;
	
	private String objectTypeId;
	
	private void setObjectTypeId(String value) {
		this.objectTypeId = value;
	}
	
	public String getObjectTypeId() {
		return objectTypeId;
	}
	
	private String attributeTypeName;
	
	public void setAttributeType(String value) {
		this.attributeType = value;
	}
	
	public String getAttributeType() {
		return attributeType;
	}
	
	public void setAttributeTypeName(String value) {
		this.attributeTypeName = value;
	}
	
	public String getAttributeTypeName() {
		return attributeTypeName;
	}
	
	public void setObjectType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType value) {
		if (objectType != null) {
			objectType.localizedAttributeType.remove(this);
		}
		if (value != null) {
			value.localizedAttributeType.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType getObjectType() {
		return objectType;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_ObjectType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType value) {
		this.objectType = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType getORM_ObjectType() {
		return objectType;
	}
	
	public String toString() {
		return String.valueOf(getAttributeType() + " " + ((getObjectType() == null) ? "" : String.valueOf(getObjectType().getORMID())));
	}
	
}
