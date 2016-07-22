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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class EventCriteria extends AbstractORMCriteria {
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
	
	public EventCriteria(Criteria criteria) {
		super(criteria);
		eventId = new IntegerExpression("eventId", this);
		eventTime = new TimestampExpression("eventTime", this);
		alarmTypeId = new IntegerExpression("alarmTypeId", this);
		commandTypeId = new IntegerExpression("commandTypeId", this);
		objectType = new StringExpression("objectType", this);
		attributeType = new StringExpression("attributeType", this);
		operatorUnit = new StringExpression("operatorUnit", this);
		objectKey = new StringExpression("objectKey", this);
		userName = new StringExpression("userName", this);
		comment = new StringExpression("comment", this);
		alarmPriority = new ShortExpression("alarmPriority", this);
		objectOldValue = new StringExpression("objectOldValue", this);
		objectNewValue = new StringExpression("objectNewValue", this);
		trendLogGuid = new StringExpression("trendLogGuid", this);
		trendLogSequence = new IntegerExpression("trendLogSequence", this);
		trendLogOldValue = new FloatExpression("trendLogOldValue", this);
		trendLogNewValue = new FloatExpression("trendLogNewValue", this);
		trendLogTime = new TimestampExpression("trendLogTime", this);
		signatureAction = new StringExpression("signatureAction", this);
		signatureReason = new StringExpression("signatureReason", this);
		signatureComment = new StringExpression("signatureComment", this);
		firstName = new StringExpression("firstName", this);
		lastName = new StringExpression("lastName", this);
		groupName = new StringExpression("groupName", this);
		uniqueField = new StringExpression("uniqueField", this);
		tenant = new IntegerExpression("tenant", this);
		individual = new IntegerExpression("individual", this);
		linkName = new StringExpression("linkName", this);
		stationName = new StringExpression("stationName", this);
		deviceName = new StringExpression("deviceName", this);
		zone = new IntegerExpression("zone", this);
		site = new IntegerExpression("site", this);
	}
	
	public EventCriteria(PersistentSession session) {
		this(session.createCriteria(Event.class));
	}
	
	public EventCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public EventTypeCriteria createEventTypeCriteria() {
		return new EventTypeCriteria(createCriteria("eventType"));
	}
	
	public ObjectPathCriteria createObjectPathCriteria() {
		return new ObjectPathCriteria(createCriteria("objectPath"));
	}
	
	public ObjectPathCriteria createShortcutPathCriteria() {
		return new ObjectPathCriteria(createCriteria("shortcutPath"));
	}
	
	public Event uniqueEvent() {
		return (Event) super.uniqueResult();
	}
	
	public Event[] listEvent() {
		java.util.List list = super.list();
		return (Event[]) list.toArray(new Event[list.size()]);
	}
}

