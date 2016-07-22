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

public class LocalizedAttributeTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression attributeType;
	public final StringExpression attributeTypeName;
	
	public LocalizedAttributeTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeTypeCriteria.class);
		attributeType = new StringExpression("attributeType", this.getDetachedCriteria());
		attributeTypeName = new StringExpression("attributeTypeName", this.getDetachedCriteria());
	}
	
	public LocalizedAttributeTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeTypeCriteria.class);
		attributeType = new StringExpression("attributeType", this.getDetachedCriteria());
		attributeTypeName = new StringExpression("attributeTypeName", this.getDetachedCriteria());
	}
	
	public LocalizedObjectTypeDetachedCriteria createObjectTypeCriteria() {
		return new LocalizedObjectTypeDetachedCriteria(createCriteria("ORM_ObjectType"));
	}
	
	public LocalizedAttributeType uniqueLocalizedAttributeType(PersistentSession session) {
		return (LocalizedAttributeType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocalizedAttributeType[] listLocalizedAttributeType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocalizedAttributeType[]) list.toArray(new LocalizedAttributeType[list.size()]);
	}
}

