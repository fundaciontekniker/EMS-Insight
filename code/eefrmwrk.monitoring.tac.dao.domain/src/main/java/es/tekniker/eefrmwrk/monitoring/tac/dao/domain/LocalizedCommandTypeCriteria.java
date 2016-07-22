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

public class LocalizedCommandTypeCriteria extends AbstractORMCriteria {
	public final IntegerExpression commandTypeId;
	public final StringExpression commandText;
	
	public LocalizedCommandTypeCriteria(Criteria criteria) {
		super(criteria);
		commandTypeId = new IntegerExpression("commandTypeId", this);
		commandText = new StringExpression("commandText", this);
	}
	
	public LocalizedCommandTypeCriteria(PersistentSession session) {
		this(session.createCriteria(LocalizedCommandType.class));
	}
	
	public LocalizedCommandTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public LocalizedCommandType uniqueLocalizedCommandType() {
		return (LocalizedCommandType) super.uniqueResult();
	}
	
	public LocalizedCommandType[] listLocalizedCommandType() {
		java.util.List list = super.list();
		return (LocalizedCommandType[]) list.toArray(new LocalizedCommandType[list.size()]);
	}
}

