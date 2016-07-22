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
public class TrendLogValue implements Serializable {
	public TrendLogValue() {
	}
	
	public boolean equals(Object aObj) {
		if (aObj == this)
			return true;
		if (!(aObj instanceof TrendLogValue))
			return false;
		TrendLogValue trendlogvalue = (TrendLogValue)aObj;
		if (getTrendLog() == null) {
			if (trendlogvalue.getTrendLog() != null)
				return false;
		}
		else if (!getTrendLog().equals(trendlogvalue.getTrendLog()))
			return false;
		if ((getLogTime() != null && !getLogTime().equals(trendlogvalue.getLogTime())) || (getLogTime() == null && trendlogvalue.getLogTime() != null))
			return false;
		return true;
	}
	
	public int hashCode() {
		int hashcode = 0;
		if (getTrendLog() != null) {
			hashcode = hashcode + (int) getTrendLog().getORMID();
		}
		hashcode = hashcode + (getLogTime() == null ? 0 : getLogTime().hashCode());
		return hashcode;
	}
	
	private void this_setOwner(Object owner, int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_TRENDLOGVALUE_TRENDLOG) {
			this.trendLog = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog) owner;
		}
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public void setOwner(Object owner, int key) {
			this_setOwner(owner, key);
		}
		
	};
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog;
	
	private short trendLogId;
	
	private void setTrendLogId(short value) {
		this.trendLogId = value;
	}
	
	public short getTrendLogId() {
		return trendLogId;
	}
	
	private java.sql.Timestamp logTime;
	
	private byte valueType;
	
	private float logValue;
	
	private int sequence;
	
	public void setLogTime(java.sql.Timestamp value) {
		this.logTime = value;
	}
	
	public java.sql.Timestamp getLogTime() {
		return logTime;
	}
	
	public void setValueType(byte value) {
		this.valueType = value;
	}
	
	public byte getValueType() {
		return valueType;
	}
	
	public void setLogValue(float value) {
		this.logValue = value;
	}
	
	public float getLogValue() {
		return logValue;
	}
	
	public void setSequence(int value) {
		this.sequence = value;
	}
	
	public int getSequence() {
		return sequence;
	}
	
	public void setTrendLog(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog value) {
		if (trendLog != null) {
			trendLog.trendLogValue.remove(this);
		}
		if (value != null) {
			value.trendLogValue.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog getTrendLog() {
		return trendLog;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_TrendLog(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog value) {
		this.trendLog = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog getORM_TrendLog() {
		return trendLog;
	}
	
	public String toString() {
		return String.valueOf(((getTrendLog() == null) ? "" : String.valueOf(getTrendLog().getORMID())) + " " + getLogTime());
	}
	
}
