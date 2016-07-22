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

public class LocalizedObjectType {
	public LocalizedObjectType() {
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof LocalizedObjectType))
			return false;
		LocalizedObjectType localizedobjecttype = (LocalizedObjectType)aObj;
		if ((getObjectType() != null && !getObjectType().equals(localizedobjecttype.getObjectType())) || (getObjectType() == null && localizedobjecttype.getObjectType() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		hashcode = hashcode + (getObjectType() == null ? 0 : getObjectType().hashCode());
		return hashcode;
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_LOCALIZEDOBJECTTYPE_LOCALIZEDATTRIBUTETYPE) {
			return ORM_localizedAttributeType;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private String objectType;
	
	private String objectTypeName;
	
	private java.util.Set ORM_localizedAttributeType = new java.util.HashSet();
	
	public void setObjectType(String value) {
		this.objectType = value;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public String getORMID() {
		return getObjectType();
	}
	
	public void setObjectTypeName(String value) {
		this.objectTypeName = value;
	}
	
	public String getObjectTypeName() {
		return objectTypeName;
	}
	
	private void setORM_LocalizedAttributeType(java.util.Set value) {
		this.ORM_localizedAttributeType = value;
	}
	
	private java.util.Set getORM_LocalizedAttributeType() {
		return ORM_localizedAttributeType;
	}
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeTypeSetCollection localizedAttributeType = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeTypeSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_LOCALIZEDOBJECTTYPE_LOCALIZEDATTRIBUTETYPE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_LOCALIZEDATTRIBUTETYPE_OBJECTTYPE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public String toString() {
		return String.valueOf(getObjectType());
	}
	
}
