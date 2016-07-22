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

public class LocalizedAlarmTypeCriteria extends AbstractORMCriteria {
	public final IntegerExpression alarmTypeId;
	public final StringExpression alarmTypeText;
	
	public LocalizedAlarmTypeCriteria(Criteria criteria) {
		super(criteria);
		alarmTypeId = new IntegerExpression("alarmTypeId", this);
		alarmTypeText = new StringExpression("alarmTypeText", this);
	}
	
	public LocalizedAlarmTypeCriteria(PersistentSession session) {
		this(session.createCriteria(LocalizedAlarmType.class));
	}
	
	public LocalizedAlarmTypeCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public LocalizedAlarmType uniqueLocalizedAlarmType() {
		return (LocalizedAlarmType) super.uniqueResult();
	}
	
	public LocalizedAlarmType[] listLocalizedAlarmType() {
		java.util.List list = super.list();
		return (LocalizedAlarmType[]) list.toArray(new LocalizedAlarmType[list.size()]);
	}
}

