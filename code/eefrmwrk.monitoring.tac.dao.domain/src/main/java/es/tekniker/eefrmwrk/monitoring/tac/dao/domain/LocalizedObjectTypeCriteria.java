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

public class LocalizedObjectTypeCriteria extends AbstractORMCriteria {
	public final StringExpression objectType;
	public final StringExpression objectTypeName;
	
	public LocalizedObjectTypeCriteria(Criteria criteria) {
		super(criteria);
		objectType = new StringExpression("objectType", this);
		objectTypeName = new StringExpression("objectTypeName", this);
	}
	
	public LocalizedObjectTypeCriteria(PersistentSession session) {
		this(session.createCriteria(LocalizedObjectType.class));
	}
	
	public LocalizedObjectTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public LocalizedAttributeTypeCriteria createLocalizedAttributeTypeCriteria() {
		return new LocalizedAttributeTypeCriteria(createCriteria("ORM_LocalizedAttributeType"));
	}
	
	public LocalizedObjectType uniqueLocalizedObjectType() {
		return (LocalizedObjectType) super.uniqueResult();
	}
	
	public LocalizedObjectType[] listLocalizedObjectType() {
		java.util.List list = super.list();
		return (LocalizedObjectType[]) list.toArray(new LocalizedObjectType[list.size()]);
	}
}

