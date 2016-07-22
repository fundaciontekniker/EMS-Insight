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

public class TrendLogCriteria extends AbstractORMCriteria {
	public final ShortExpression trendLogId;
	public final StringExpression trendLogGuid;
	public final StringExpression name;
	public final IntegerExpression maxSequence;
	public final IntegerExpression capacity;
	public final IntegerExpression intervalSeconds;
	public final IntegerExpression intervalMonths;
	public final IntegerExpression intervalYears;
	
	public TrendLogCriteria(Criteria criteria) {
		super(criteria);
		trendLogId = new ShortExpression("trendLogId", this);
		trendLogGuid = new StringExpression("trendLogGuid", this);
		name = new StringExpression("name", this);
		maxSequence = new IntegerExpression("maxSequence", this);
		capacity = new IntegerExpression("capacity", this);
		intervalSeconds = new IntegerExpression("intervalSeconds", this);
		intervalMonths = new IntegerExpression("intervalMonths", this);
		intervalYears = new IntegerExpression("intervalYears", this);
	}
	
	public TrendLogCriteria(PersistentSession session) {
		this(session.createCriteria(TrendLog.class));
	}
	
	public TrendLogCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public TrendLogValueCriteria createTrendLogValueCriteria() {
		return new TrendLogValueCriteria(createCriteria("ORM_TrendLogValue"));
	}
	
	public TrendLog uniqueTrendLog() {
		return (TrendLog) super.uniqueResult();
	}
	
	public TrendLog[] listTrendLog() {
		java.util.List list = super.list();
		return (TrendLog[]) list.toArray(new TrendLog[list.size()]);
	}
}

