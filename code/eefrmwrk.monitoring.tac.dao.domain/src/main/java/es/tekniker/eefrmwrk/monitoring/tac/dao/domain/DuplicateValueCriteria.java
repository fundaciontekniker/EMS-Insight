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

public class DuplicateValueCriteria extends AbstractORMCriteria {
	public final IntegerExpression duplicateValueId;
	public final ShortExpression trendLogId;
	public final StringExpression trendLogGuid;
	public final FloatExpression logValue;
	public final TimestampExpression logTime;
	public final IntegerExpression sequence;
	public final TimestampExpression insertTimeUTC;
	
	public DuplicateValueCriteria(Criteria criteria) {
		super(criteria);
		duplicateValueId = new IntegerExpression("duplicateValueId", this);
		trendLogId = new ShortExpression("trendLogId", this);
		trendLogGuid = new StringExpression("trendLogGuid", this);
		logValue = new FloatExpression("logValue", this);
		logTime = new TimestampExpression("logTime", this);
		sequence = new IntegerExpression("sequence", this);
		insertTimeUTC = new TimestampExpression("insertTimeUTC", this);
	}
	
	public DuplicateValueCriteria(PersistentSession session) {
		this(session.createCriteria(DuplicateValue.class));
	}
	
	public DuplicateValueCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession());
	}
	
	public DuplicateValueTypeCriteria createDuplicateValueTypeCriteria() {
		return new DuplicateValueTypeCriteria(createCriteria("duplicateValueType"));
	}
	
	public DuplicateValue uniqueDuplicateValue() {
		return (DuplicateValue) super.uniqueResult();
	}
	
	public DuplicateValue[] listDuplicateValue() {
		java.util.List list = super.list();
		return (DuplicateValue[]) list.toArray(new DuplicateValue[list.size()]);
	}
}

