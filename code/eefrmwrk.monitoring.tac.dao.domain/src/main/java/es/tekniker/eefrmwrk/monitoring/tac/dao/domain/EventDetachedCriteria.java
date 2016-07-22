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

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class EventDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression eventId;
	public final TimestampExpression eventTime;
	public final IntegerExpression alarmTypeId;
	public final IntegerExpression commandTypeId;
	public final StringExpression objectType;
	public final StringExpression attributeType;
	public final StringExpression operatorUnit;
	public final StringExpression objectKey;
	public final StringExpression userName;
	public final StringExpression comment;
	public final ShortExpression alarmPriority;
	public final StringExpression objectOldValue;
	public final StringExpression objectNewValue;
	public final StringExpression trendLogGuid;
	public final IntegerExpression trendLogSequence;
	public final FloatExpression trendLogOldValue;
	public final FloatExpression trendLogNewValue;
	public final TimestampExpression trendLogTime;
	public final StringExpression signatureAction;
	public final StringExpression signatureReason;
	public final StringExpression signatureComment;
	public final StringExpression firstName;
	public final StringExpression lastName;
	public final StringExpression groupName;
	public final StringExpression uniqueField;
	public final IntegerExpression tenant;
	public final IntegerExpression individual;
	public final StringExpression linkName;
	public final StringExpression stationName;
	public final StringExpression deviceName;
	public final IntegerExpression zone;
	public final IntegerExpression site;
	
	public EventDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventCriteria.class);
		eventId = new IntegerExpression("eventId", this.getDetachedCriteria());
		eventTime = new TimestampExpression("eventTime", this.getDetachedCriteria());
		alarmTypeId = new IntegerExpression("alarmTypeId", this.getDetachedCriteria());
		commandTypeId = new IntegerExpression("commandTypeId", this.getDetachedCriteria());
		objectType = new StringExpression("objectType", this.getDetachedCriteria());
		attributeType = new StringExpression("attributeType", this.getDetachedCriteria());
		operatorUnit = new StringExpression("operatorUnit", this.getDetachedCriteria());
		objectKey = new StringExpression("objectKey", this.getDetachedCriteria());
		userName = new StringExpression("userName", this.getDetachedCriteria());
		comment = new StringExpression("comment", this.getDetachedCriteria());
		alarmPriority = new ShortExpression("alarmPriority", this.getDetachedCriteria());
		objectOldValue = new StringExpression("objectOldValue", this.getDetachedCriteria());
		objectNewValue = new StringExpression("objectNewValue", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		trendLogSequence = new IntegerExpression("trendLogSequence", this.getDetachedCriteria());
		trendLogOldValue = new FloatExpression("trendLogOldValue", this.getDetachedCriteria());
		trendLogNewValue = new FloatExpression("trendLogNewValue", this.getDetachedCriteria());
		trendLogTime = new TimestampExpression("trendLogTime", this.getDetachedCriteria());
		signatureAction = new StringExpression("signatureAction", this.getDetachedCriteria());
		signatureReason = new StringExpression("signatureReason", this.getDetachedCriteria());
		signatureComment = new StringExpression("signatureComment", this.getDetachedCriteria());
		firstName = new StringExpression("firstName", this.getDetachedCriteria());
		lastName = new StringExpression("lastName", this.getDetachedCriteria());
		groupName = new StringExpression("groupName", this.getDetachedCriteria());
		uniqueField = new StringExpression("uniqueField", this.getDetachedCriteria());
		tenant = new IntegerExpression("tenant", this.getDetachedCriteria());
		individual = new IntegerExpression("individual", this.getDetachedCriteria());
		linkName = new StringExpression("linkName", this.getDetachedCriteria());
		stationName = new StringExpression("stationName", this.getDetachedCriteria());
		deviceName = new StringExpression("deviceName", this.getDetachedCriteria());
		zone = new IntegerExpression("zone", this.getDetachedCriteria());
		site = new IntegerExpression("site", this.getDetachedCriteria());
	}
	
	public EventDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventCriteria.class);
		eventId = new IntegerExpression("eventId", this.getDetachedCriteria());
		eventTime = new TimestampExpression("eventTime", this.getDetachedCriteria());
		alarmTypeId = new IntegerExpression("alarmTypeId", this.getDetachedCriteria());
		commandTypeId = new IntegerExpression("commandTypeId", this.getDetachedCriteria());
		objectType = new StringExpression("objectType", this.getDetachedCriteria());
		attributeType = new StringExpression("attributeType", this.getDetachedCriteria());
		operatorUnit = new StringExpression("operatorUnit", this.getDetachedCriteria());
		objectKey = new StringExpression("objectKey", this.getDetachedCriteria());
		userName = new StringExpression("userName", this.getDetachedCriteria());
		comment = new StringExpression("comment", this.getDetachedCriteria());
		alarmPriority = new ShortExpression("alarmPriority", this.getDetachedCriteria());
		objectOldValue = new StringExpression("objectOldValue", this.getDetachedCriteria());
		objectNewValue = new StringExpression("objectNewValue", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		trendLogSequence = new IntegerExpression("trendLogSequence", this.getDetachedCriteria());
		trendLogOldValue = new FloatExpression("trendLogOldValue", this.getDetachedCriteria());
		trendLogNewValue = new FloatExpression("trendLogNewValue", this.getDetachedCriteria());
		trendLogTime = new TimestampExpression("trendLogTime", this.getDetachedCriteria());
		signatureAction = new StringExpression("signatureAction", this.getDetachedCriteria());
		signatureReason = new StringExpression("signatureReason", this.getDetachedCriteria());
		signatureComment = new StringExpression("signatureComment", this.getDetachedCriteria());
		firstName = new StringExpression("firstName", this.getDetachedCriteria());
		lastName = new StringExpression("lastName", this.getDetachedCriteria());
		groupName = new StringExpression("groupName", this.getDetachedCriteria());
		uniqueField = new StringExpression("uniqueField", this.getDetachedCriteria());
		tenant = new IntegerExpression("tenant", this.getDetachedCriteria());
		individual = new IntegerExpression("individual", this.getDetachedCriteria());
		linkName = new StringExpression("linkName", this.getDetachedCriteria());
		stationName = new StringExpression("stationName", this.getDetachedCriteria());
		deviceName = new StringExpression("deviceName", this.getDetachedCriteria());
		zone = new IntegerExpression("zone", this.getDetachedCriteria());
		site = new IntegerExpression("site", this.getDetachedCriteria());
	}
	
	public EventTypeDetachedCriteria createEventTypeCriteria() {
		return new EventTypeDetachedCriteria(createCriteria("eventType"));
	}
	
	public ObjectPathDetachedCriteria createObjectPathCriteria() {
		return new ObjectPathDetachedCriteria(createCriteria("objectPath"));
	}
	
	public ObjectPathDetachedCriteria createShortcutPathCriteria() {
		return new ObjectPathDetachedCriteria(createCriteria("shortcutPath"));
	}
	
	public Event uniqueEvent(PersistentSession session) {
		return (Event) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Event[] listEvent(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Event[]) list.toArray(new Event[list.size()]);
	}
}

