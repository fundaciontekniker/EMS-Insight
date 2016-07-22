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

public class TrendLogValueCriteria extends AbstractORMCriteria {
	public final TimestampExpression logTime;
	public final ByteExpression valueType;
	public final FloatExpression logValue;
	public final IntegerExpression sequence;
	
	public TrendLogValueCriteria(Criteria criteria) {
		super(criteria);
		logTime = new TimestampExpression("logTime", this);
		valueType = new ByteExpression("valueType", this);
		logValue = new FloatExpression("logValue", this);
		sequence = new IntegerExpression("sequence", this);
	}
	
	public TrendLogValueCriteria(PersistentSession session) {
		this(session.createCriteria(TrendLogValue.class));
	}
	
	public TrendLogValueCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public TrendLogCriteria createTrendLogCriteria() {
		return new TrendLogCriteria(createCriteria("ORM_TrendLog"));
	}
	
	public TrendLogValue uniqueTrendLogValue() {
		return (TrendLogValue) super.uniqueResult();
	}
	
	public TrendLogValue[] listTrendLogValue() {
		java.util.List list = super.list();
		return (TrendLogValue[]) list.toArray(new TrendLogValue[list.size()]);
	}
}

