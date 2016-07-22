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

public class EventTypeCriteria extends AbstractORMCriteria {
	public final ShortExpression eventTypeId;
	public final StringExpression name;
	
	public EventTypeCriteria(Criteria criteria) {
		super(criteria);
		eventTypeId = new ShortExpression("eventTypeId", this);
		name = new StringExpression("name", this);
	}
	
	public EventTypeCriteria(PersistentSession session) {
		this(session.createCriteria(EventType.class));
	}
	
	public EventTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public EventCriteria createEventCriteria() {
		return new EventCriteria(createCriteria("ORM_Event"));
	}
	
	public EventType uniqueEventType() {
		return (EventType) super.uniqueResult();
	}
	
	public EventType[] listEventType() {
		java.util.List list = super.list();
		return (EventType[]) list.toArray(new EventType[list.size()]);
	}
}

