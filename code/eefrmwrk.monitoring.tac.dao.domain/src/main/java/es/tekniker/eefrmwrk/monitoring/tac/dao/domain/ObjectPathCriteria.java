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

public class ObjectPathCriteria extends AbstractORMCriteria {
	public final IntegerExpression objectPathId;
	public final StringExpression name;
	
	public ObjectPathCriteria(Criteria criteria) {
		super(criteria);
		objectPathId = new IntegerExpression("objectPathId", this);
		name = new StringExpression("name", this);
	}
	
	public ObjectPathCriteria(PersistentSession session) {
		this(session.createCriteria(ObjectPath.class));
	}
	
	public ObjectPathCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public EventCriteria createEventCriteria() {
		return new EventCriteria(createCriteria("ORM_Event"));
	}
	
	public EventCriteria createEvent1Criteria() {
		return new EventCriteria(createCriteria("ORM_Event1"));
	}
	
	public ObjectPath uniqueObjectPath() {
		return (ObjectPath) super.uniqueResult();
	}
	
	public ObjectPath[] listObjectPath() {
		java.util.List list = super.list();
		return (ObjectPath[]) list.toArray(new ObjectPath[list.size()]);
	}
}

