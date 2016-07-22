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

public class EventTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final ShortExpression eventTypeId;
	public final StringExpression name;
	
	public EventTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventTypeCriteria.class);
		eventTypeId = new ShortExpression("eventTypeId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public EventTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.EventTypeCriteria.class);
		eventTypeId = new ShortExpression("eventTypeId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public EventDetachedCriteria createEventCriteria() {
		return new EventDetachedCriteria(createCriteria("ORM_Event"));
	}
	
	public EventType uniqueEventType(PersistentSession session) {
		return (EventType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public EventType[] listEventType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (EventType[]) list.toArray(new EventType[list.size()]);
	}
}

