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

public class DuplicateValue {
	public DuplicateValue() {
	}
	
	private void this_setOwner(Object owner, int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_DUPLICATEVALUE_DUPLICATEVALUETYPE) {
			this.duplicateValueType = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType) owner;
		}
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public void setOwner(Object owner, int key) {
			this_setOwner(owner, key);
		}
		
	};
	
	private int duplicateValueId;
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType;
	
	private short trendLogId;
	
	private String trendLogGuid;
	
	private float logValue;
	
	private java.sql.Timestamp logTime;
	
	private int sequence;
	
	private java.sql.Timestamp insertTimeUTC;
	
	private void setDuplicateValueId(int value) {
		this.duplicateValueId = value;
	}
	
	public int getDuplicateValueId() {
		return duplicateValueId;
	}
	
	public int getORMID() {
		return getDuplicateValueId();
	}
	
	public void setTrendLogId(short value) {
		this.trendLogId = value;
	}
	
	public short getTrendLogId() {
		return trendLogId;
	}
	
	public void setTrendLogGuid(String value) {
		this.trendLogGuid = value;
	}
	
	public String getTrendLogGuid() {
		return trendLogGuid;
	}
	
	public void setLogValue(float value) {
		this.logValue = value;
	}
	
	public float getLogValue() {
		return logValue;
	}
	
	public void setLogTime(java.sql.Timestamp value) {
		this.logTime = value;
	}
	
	public java.sql.Timestamp getLogTime() {
		return logTime;
	}
	
	public void setSequence(int value) {
		this.sequence = value;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setInsertTimeUTC(java.sql.Timestamp value) {
		this.insertTimeUTC = value;
	}
	
	public java.sql.Timestamp getInsertTimeUTC() {
		return insertTimeUTC;
	}
	
	public void setDuplicateValueType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType value) {
		if (duplicateValueType != null) {
			duplicateValueType.duplicateValue.remove(this);
		}
		if (value != null) {
			value.duplicateValue.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType getDuplicateValueType() {
		return duplicateValueType;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_DuplicateValueType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType value) {
		this.duplicateValueType = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType getORM_DuplicateValueType() {
		return duplicateValueType;
	}
	
	public String toString() {
		return String.valueOf(getDuplicateValueId());
	}
	
}
