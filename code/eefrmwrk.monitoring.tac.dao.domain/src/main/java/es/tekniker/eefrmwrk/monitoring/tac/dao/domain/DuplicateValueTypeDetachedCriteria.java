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

public class DuplicateValueTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final ByteExpression duplicateValueTypeId;
	public final StringExpression name;
	
	public DuplicateValueTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueTypeCriteria.class);
		duplicateValueTypeId = new ByteExpression("duplicateValueTypeId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public DuplicateValueTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueTypeCriteria.class);
		duplicateValueTypeId = new ByteExpression("duplicateValueTypeId", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
	}
	
	public DuplicateValueDetachedCriteria createDuplicateValueCriteria() {
		return new DuplicateValueDetachedCriteria(createCriteria("ORM_DuplicateValue"));
	}
	
	public DuplicateValueType uniqueDuplicateValueType(PersistentSession session) {
		return (DuplicateValueType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public DuplicateValueType[] listDuplicateValueType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (DuplicateValueType[]) list.toArray(new DuplicateValueType[list.size()]);
	}
}

