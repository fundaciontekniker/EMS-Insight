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

public class TrendLog {
	public TrendLog() {
	}
	
	private java.util.Set this_getSet (int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_TRENDLOG_TRENDLOGVALUE) {
			return ORM_trendLogValue;
		}
		
		return null;
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public java.util.Set getSet(int key) {
			return this_getSet(key);
		}
		
	};
	
	private short trendLogId;
	
	private String trendLogGuid;
	
	private String name;
	
	private int maxSequence;
	
	private Integer capacity;
	
	private Integer intervalSeconds;
	
	private Integer intervalMonths;
	
	private Integer intervalYears;
	
	private java.util.Set ORM_trendLogValue = new java.util.HashSet();
	
	private void setTrendLogId(short value) {
		this.trendLogId = value;
	}
	
	public short getTrendLogId() {
		return trendLogId;
	}
	
	public short getORMID() {
		return getTrendLogId();
	}
	
	public void setTrendLogGuid(String value) {
		this.trendLogGuid = value;
	}
	
	public String getTrendLogGuid() {
		return trendLogGuid;
	}
	
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return name;
	}
	
	public void setMaxSequence(int value) {
		this.maxSequence = value;
	}
	
	public int getMaxSequence() {
		return maxSequence;
	}
	
	public void setCapacity(int value) {
		setCapacity(new Integer(value));
	}
	
	public void setCapacity(Integer value) {
		this.capacity = value;
	}
	
	public Integer getCapacity() {
		return capacity;
	}
	
	public void setIntervalSeconds(int value) {
		setIntervalSeconds(new Integer(value));
	}
	
	public void setIntervalSeconds(Integer value) {
		this.intervalSeconds = value;
	}
	
	public Integer getIntervalSeconds() {
		return intervalSeconds;
	}
	
	public void setIntervalMonths(int value) {
		setIntervalMonths(new Integer(value));
	}
	
	public void setIntervalMonths(Integer value) {
		this.intervalMonths = value;
	}
	
	public Integer getIntervalMonths() {
		return intervalMonths;
	}
	
	public void setIntervalYears(int value) {
		setIntervalYears(new Integer(value));
	}
	
	public void setIntervalYears(Integer value) {
		this.intervalYears = value;
	}
	
	public Integer getIntervalYears() {
		return intervalYears;
	}
	
	private void setORM_TrendLogValue(java.util.Set value) {
		this.ORM_trendLogValue = value;
	}
	
	private java.util.Set getORM_TrendLogValue() {
		return ORM_trendLogValue;
	}
	
	public final es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValueSetCollection trendLogValue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValueSetCollection(this, _ormAdapter, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_TRENDLOG_TRENDLOGVALUE, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_TRENDLOGVALUE_TRENDLOG, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_MUL_ONE_TO_MANY);
	
	public String toString() {
		return String.valueOf(getTrendLogId());
	}
	
}
