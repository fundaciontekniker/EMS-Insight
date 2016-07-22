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

public class TrendLogDetachedCriteria extends AbstractORMDetachedCriteria {
	public final ShortExpression trendLogId;
	public final StringExpression trendLogGuid;
	public final StringExpression name;
	public final IntegerExpression maxSequence;
	public final IntegerExpression capacity;
	public final IntegerExpression intervalSeconds;
	public final IntegerExpression intervalMonths;
	public final IntegerExpression intervalYears;
	
	public TrendLogDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogCriteria.class);
		trendLogId = new ShortExpression("trendLogId", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		maxSequence = new IntegerExpression("maxSequence", this.getDetachedCriteria());
		capacity = new IntegerExpression("capacity", this.getDetachedCriteria());
		intervalSeconds = new IntegerExpression("intervalSeconds", this.getDetachedCriteria());
		intervalMonths = new IntegerExpression("intervalMonths", this.getDetachedCriteria());
		intervalYears = new IntegerExpression("intervalYears", this.getDetachedCriteria());
	}
	
	public TrendLogDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogCriteria.class);
		trendLogId = new ShortExpression("trendLogId", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		name = new StringExpression("name", this.getDetachedCriteria());
		maxSequence = new IntegerExpression("maxSequence", this.getDetachedCriteria());
		capacity = new IntegerExpression("capacity", this.getDetachedCriteria());
		intervalSeconds = new IntegerExpression("intervalSeconds", this.getDetachedCriteria());
		intervalMonths = new IntegerExpression("intervalMonths", this.getDetachedCriteria());
		intervalYears = new IntegerExpression("intervalYears", this.getDetachedCriteria());
	}
	
	public TrendLogValueDetachedCriteria createTrendLogValueCriteria() {
		return new TrendLogValueDetachedCriteria(createCriteria("ORM_TrendLogValue"));
	}
	
	public TrendLog uniqueTrendLog(PersistentSession session) {
		return (TrendLog) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public TrendLog[] listTrendLog(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (TrendLog[]) list.toArray(new TrendLog[list.size()]);
	}
}

