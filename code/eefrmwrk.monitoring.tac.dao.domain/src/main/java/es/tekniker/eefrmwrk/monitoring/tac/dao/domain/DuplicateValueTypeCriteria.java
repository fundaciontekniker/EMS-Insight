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

public class DuplicateValueTypeCriteria extends AbstractORMCriteria {
	public final ByteExpression duplicateValueTypeId;
	public final StringExpression name;
	
	public DuplicateValueTypeCriteria(Criteria criteria) {
		super(criteria);
		duplicateValueTypeId = new ByteExpression("duplicateValueTypeId", this);
		name = new StringExpression("name", this);
	}
	
	public DuplicateValueTypeCriteria(PersistentSession session) {
		this(session.createCriteria(DuplicateValueType.class));
	}
	
	public DuplicateValueTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public DuplicateValueCriteria createDuplicateValueCriteria() {
		return new DuplicateValueCriteria(createCriteria("ORM_DuplicateValue"));
	}
	
	public DuplicateValueType uniqueDuplicateValueType() {
		return (DuplicateValueType) super.uniqueResult();
	}
	
	public DuplicateValueType[] listDuplicateValueType() {
		java.util.List list = super.list();
		return (DuplicateValueType[]) list.toArray(new DuplicateValueType[list.size()]);
	}
}

