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

public class LocalizedAttributeTypeCriteria extends AbstractORMCriteria {
	public final StringExpression attributeType;
	public final StringExpression attributeTypeName;
	
	public LocalizedAttributeTypeCriteria(Criteria criteria) {
		super(criteria);
		attributeType = new StringExpression("attributeType", this);
		attributeTypeName = new StringExpression("attributeTypeName", this);
	}
	
	public LocalizedAttributeTypeCriteria(PersistentSession session) {
		this(session.createCriteria(LocalizedAttributeType.class));
	}
	
	public LocalizedAttributeTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public LocalizedObjectTypeCriteria createObjectTypeCriteria() {
		return new LocalizedObjectTypeCriteria(createCriteria("ORM_ObjectType"));
	}
	
	public LocalizedAttributeType uniqueLocalizedAttributeType() {
		return (LocalizedAttributeType) super.uniqueResult();
	}
	
	public LocalizedAttributeType[] listLocalizedAttributeType() {
		java.util.List list = super.list();
		return (LocalizedAttributeType[]) list.toArray(new LocalizedAttributeType[list.size()]);
	}
}

