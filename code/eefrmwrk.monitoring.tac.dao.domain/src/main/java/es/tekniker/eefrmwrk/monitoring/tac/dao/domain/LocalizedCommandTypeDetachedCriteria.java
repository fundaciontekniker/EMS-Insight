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

public class LocalizedCommandTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression commandTypeId;
	public final StringExpression commandText;
	
	public LocalizedCommandTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandTypeCriteria.class);
		commandTypeId = new IntegerExpression("commandTypeId", this.getDetachedCriteria());
		commandText = new StringExpression("commandText", this.getDetachedCriteria());
	}
	
	public LocalizedCommandTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandTypeCriteria.class);
		commandTypeId = new IntegerExpression("commandTypeId", this.getDetachedCriteria());
		commandText = new StringExpression("commandText", this.getDetachedCriteria());
	}
	
	public LocalizedCommandType uniqueLocalizedCommandType(PersistentSession session) {
		return (LocalizedCommandType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocalizedCommandType[] listLocalizedCommandType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocalizedCommandType[]) list.toArray(new LocalizedCommandType[list.size()]);
	}
}

