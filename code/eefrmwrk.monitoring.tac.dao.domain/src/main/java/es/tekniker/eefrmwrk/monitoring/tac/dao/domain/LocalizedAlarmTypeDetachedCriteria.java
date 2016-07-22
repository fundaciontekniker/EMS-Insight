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

public class LocalizedAlarmTypeDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression alarmTypeId;
	public final StringExpression alarmTypeText;
	
	public LocalizedAlarmTypeDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmTypeCriteria.class);
		alarmTypeId = new IntegerExpression("alarmTypeId", this.getDetachedCriteria());
		alarmTypeText = new StringExpression("alarmTypeText", this.getDetachedCriteria());
	}
	
	public LocalizedAlarmTypeDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmTypeCriteria.class);
		alarmTypeId = new IntegerExpression("alarmTypeId", this.getDetachedCriteria());
		alarmTypeText = new StringExpression("alarmTypeText", this.getDetachedCriteria());
	}
	
	public LocalizedAlarmType uniqueLocalizedAlarmType(PersistentSession session) {
		return (LocalizedAlarmType) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public LocalizedAlarmType[] listLocalizedAlarmType(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (LocalizedAlarmType[]) list.toArray(new LocalizedAlarmType[list.size()]);
	}
}

