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

public class TrendLogValueDetachedCriteria extends AbstractORMDetachedCriteria {
	public final TimestampExpression logTime;
	public final ByteExpression valueType;
	public final FloatExpression logValue;
	public final IntegerExpression sequence;
	
	public TrendLogValueDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValueCriteria.class);
		logTime = new TimestampExpression("logTime", this.getDetachedCriteria());
		valueType = new ByteExpression("valueType", this.getDetachedCriteria());
		logValue = new FloatExpression("logValue", this.getDetachedCriteria());
		sequence = new IntegerExpression("sequence", this.getDetachedCriteria());
	}
	
	public TrendLogValueDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValueCriteria.class);
		logTime = new TimestampExpression("logTime", this.getDetachedCriteria());
		valueType = new ByteExpression("valueType", this.getDetachedCriteria());
		logValue = new FloatExpression("logValue", this.getDetachedCriteria());
		sequence = new IntegerExpression("sequence", this.getDetachedCriteria());
	}
	
	public TrendLogDetachedCriteria createTrendLogCriteria() {
		return new TrendLogDetachedCriteria(createCriteria("ORM_TrendLog"));
	}
	
	public TrendLogValue uniqueTrendLogValue(PersistentSession session) {
		return (TrendLogValue) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public TrendLogValue[] listTrendLogValue(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (TrendLogValue[]) list.toArray(new TrendLogValue[list.size()]);
	}
}

