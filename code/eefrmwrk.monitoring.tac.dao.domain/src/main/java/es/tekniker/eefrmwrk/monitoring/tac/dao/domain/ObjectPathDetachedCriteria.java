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

public class ObjectPathDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression objectPathId;
	public final StringExpression name;
	
	public ObjectPathDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPathCriteria.class);
		objectPathId = new IntegerExpression("objectPathId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public ObjectPathDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPathCriteria.class);
		objectPathId = new IntegerExpression("objectPathId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public EventDetachedCriteria createEventCriteria() {
		return new EventDetachedCriteria(createCriteria("ORM_Event"));
	}
	
	public EventDetachedCriteria createEvent1Criteria() {
		return new EventDetachedCriteria(createCriteria("ORM_Event1"));
	}
	
	public ObjectPath uniqueObjectPath(PersistentSession session) {
		return (ObjectPath) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public ObjectPath[] listObjectPath(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (ObjectPath[]) list.toArray(new ObjectPath[list.size()]);
	}
}

