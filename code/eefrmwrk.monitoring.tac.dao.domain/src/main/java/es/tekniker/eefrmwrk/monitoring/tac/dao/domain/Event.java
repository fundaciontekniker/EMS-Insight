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

public class Event {
	public Event() {
	}
	
	private void this_setOwner(Object owner, int key) {
		if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_EVENTTYPE) {
			this.eventType = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType) owner;
		}
		
		else if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_OBJECTPATH) {
			this.objectPath = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath) owner;
		}
		
		else if (key == es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ORMConstants.KEY_EVENT_SHORTCUTPATH) {
			this.shortcutPath = (es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath) owner;
		}
	}
	
	org.orm.util.ORMAdapter _ormAdapter = new org.orm.util.AbstractORMAdapter() {
		public void setOwner(Object owner, int key) {
			this_setOwner(owner, key);
		}
		
	};
	
	private int eventId;
	
	private java.sql.Timestamp eventTime;
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType eventType;
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath;
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath shortcutPath;
	
	private Integer alarmTypeId;
	
	private Integer commandTypeId;
	
	private String objectType;
	
	private String attributeType;
	
	private String operatorUnit;
	
	private String objectKey;
	
	private String userName;
	
	private String comment;
	
	private Short alarmPriority;
	
	private String objectOldValue;
	
	private String objectNewValue;
	
	private String trendLogGuid;
	
	private Integer trendLogSequence;
	
	private Float trendLogOldValue;
	
	private Float trendLogNewValue;
	
	private java.sql.Timestamp trendLogTime;
	
	private String signatureAction;
	
	private String signatureReason;
	
	private String signatureComment;
	
	private String firstName;
	
	private String lastName;
	
	private String groupName;
	
	private String uniqueField;
	
	private Integer tenant;
	
	private Integer individual;
	
	private String linkName;
	
	private String stationName;
	
	private String deviceName;
	
	private Integer zone;
	
	private Integer site;
	
	private void setEventId(int value) {
		this.eventId = value;
	}
	
	public int getEventId() {
		return eventId;
	}
	
	public int getORMID() {
		return getEventId();
	}
	
	public void setEventTime(java.sql.Timestamp value) {
		this.eventTime = value;
	}
	
	public java.sql.Timestamp getEventTime() {
		return eventTime;
	}
	
	public void setAlarmTypeId(int value) {
		setAlarmTypeId(new Integer(value));
	}
	
	public void setAlarmTypeId(Integer value) {
		this.alarmTypeId = value;
	}
	
	public Integer getAlarmTypeId() {
		return alarmTypeId;
	}
	
	public void setCommandTypeId(int value) {
		setCommandTypeId(new Integer(value));
	}
	
	public void setCommandTypeId(Integer value) {
		this.commandTypeId = value;
	}
	
	public Integer getCommandTypeId() {
		return commandTypeId;
	}
	
	public void setObjectType(String value) {
		this.objectType = value;
	}
	
	public String getObjectType() {
		return objectType;
	}
	
	public void setAttributeType(String value) {
		this.attributeType = value;
	}
	
	public String getAttributeType() {
		return attributeType;
	}
	
	public void setOperatorUnit(String value) {
		this.operatorUnit = value;
	}
	
	public String getOperatorUnit() {
		return operatorUnit;
	}
	
	public void setObjectKey(String value) {
		this.objectKey = value;
	}
	
	public String getObjectKey() {
		return objectKey;
	}
	
	public void setUserName(String value) {
		this.userName = value;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setComment(String value) {
		this.comment = value;
	}
	
	public String getComment() {
		return comment;
	}
	
	public void setAlarmPriority(short value) {
		setAlarmPriority(new Short(value));
	}
	
	public void setAlarmPriority(Short value) {
		this.alarmPriority = value;
	}
	
	public Short getAlarmPriority() {
		return alarmPriority;
	}
	
	public void setObjectOldValue(String value) {
		this.objectOldValue = value;
	}
	
	public String getObjectOldValue() {
		return objectOldValue;
	}
	
	public void setObjectNewValue(String value) {
		this.objectNewValue = value;
	}
	
	public String getObjectNewValue() {
		return objectNewValue;
	}
	
	public void setTrendLogGuid(String value) {
		this.trendLogGuid = value;
	}
	
	public String getTrendLogGuid() {
		return trendLogGuid;
	}
	
	public void setTrendLogSequence(int value) {
		setTrendLogSequence(new Integer(value));
	}
	
	public void setTrendLogSequence(Integer value) {
		this.trendLogSequence = value;
	}
	
	public Integer getTrendLogSequence() {
		return trendLogSequence;
	}
	
	public void setTrendLogOldValue(float value) {
		setTrendLogOldValue(new Float(value));
	}
	
	public void setTrendLogOldValue(Float value) {
		this.trendLogOldValue = value;
	}
	
	public Float getTrendLogOldValue() {
		return trendLogOldValue;
	}
	
	public void setTrendLogNewValue(float value) {
		setTrendLogNewValue(new Float(value));
	}
	
	public void setTrendLogNewValue(Float value) {
		this.trendLogNewValue = value;
	}
	
	public Float getTrendLogNewValue() {
		return trendLogNewValue;
	}
	
	public void setTrendLogTime(java.sql.Timestamp value) {
		this.trendLogTime = value;
	}
	
	public java.sql.Timestamp getTrendLogTime() {
		return trendLogTime;
	}
	
	public void setSignatureAction(String value) {
		this.signatureAction = value;
	}
	
	public String getSignatureAction() {
		return signatureAction;
	}
	
	public void setSignatureReason(String value) {
		this.signatureReason = value;
	}
	
	public String getSignatureReason() {
		return signatureReason;
	}
	
	public void setSignatureComment(String value) {
		this.signatureComment = value;
	}
	
	public String getSignatureComment() {
		return signatureComment;
	}
	
	public void setFirstName(String value) {
		this.firstName = value;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setLastName(String value) {
		this.lastName = value;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setGroupName(String value) {
		this.groupName = value;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setUniqueField(String value) {
		this.uniqueField = value;
	}
	
	public String getUniqueField() {
		return uniqueField;
	}
	
	public void setTenant(int value) {
		setTenant(new Integer(value));
	}
	
	public void setTenant(Integer value) {
		this.tenant = value;
	}
	
	public Integer getTenant() {
		return tenant;
	}
	
	public void setIndividual(int value) {
		setIndividual(new Integer(value));
	}
	
	public void setIndividual(Integer value) {
		this.individual = value;
	}
	
	public Integer getIndividual() {
		return individual;
	}
	
	public void setLinkName(String value) {
		this.linkName = value;
	}
	
	public String getLinkName() {
		return linkName;
	}
	
	public void setStationName(String value) {
		this.stationName = value;
	}
	
	public String getStationName() {
		return stationName;
	}
	
	public void setDeviceName(String value) {
		this.deviceName = value;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setZone(int value) {
		setZone(new Integer(value));
	}
	
	public void setZone(Integer value) {
		this.zone = value;
	}
	
	public Integer getZone() {
		return zone;
	}
	
	public void setSite(int value) {
		setSite(new Integer(value));
	}
	
	public void setSite(Integer value) {
		this.site = value;
	}
	
	public Integer getSite() {
		return site;
	}
	
	public void setEventType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType value) {
		if (eventType != null) {
			eventType.event.remove(this);
		}
		if (value != null) {
			value.event.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType getEventType() {
		return eventType;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_EventType(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType value) {
		this.eventType = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType getORM_EventType() {
		return eventType;
	}
	
	public void setObjectPath(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath value) {
		if (objectPath != null) {
			objectPath.event.remove(this);
		}
		if (value != null) {
			value.event.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath getObjectPath() {
		return objectPath;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_ObjectPath(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath value) {
		this.objectPath = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath getORM_ObjectPath() {
		return objectPath;
	}
	
	public void setShortcutPath(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath value) {
		if (shortcutPath != null) {
			shortcutPath.event1.remove(this);
		}
		if (value != null) {
			value.event1.add(this);
		}
	}
	
	public es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath getShortcutPath() {
		return shortcutPath;
	}
	
	/**
	 * This method is for internal use only.
	 */
	public void setORM_ShortcutPath(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath value) {
		this.shortcutPath = value;
	}
	
	private es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath getORM_ShortcutPath() {
		return shortcutPath;
	}
	
	public String toString() {
		return String.valueOf(getEventId());
	}
	
}
