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

public class LocalizedObjectTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final StringExpression objectType;
	public final StringExpression objectTypeName;
	
	public LocalizedObjectTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectTypeCriteria.class);
		objectType = new StringExpression("objectType", this.getDetachedCriteria());
		objectTypeName = new StringExpression("objectTypeName", this.getDetachedCriteria());
	}
	
	public LocalizedObjectTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectTypeCriteria.class);
		objectType = new StringExpression("objectType", this.getDetachedCriteria());
		objectTypeName = new StringExpression("objectTypeName", this.getDetachedCriteria());
	}
	
	public LocalizedAttributeTypeDetachedCriteria createLocalizedAttributeTypeCriteria() {
		return new LocalizedAttributeTypeDetachedCriteria(createCriteria("ORM_LocalizedAttributeType"));
	}
	
	public LocalizedObjectType uniqueLocalizedObjectType(PersistentSession session) {
		return (LocalizedObjectType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocalizedObjectType[] listLocalizedObjectType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocalizedObjectType[]) list.toArray(new LocalizedObjectType[list.size()]);
	}
}

