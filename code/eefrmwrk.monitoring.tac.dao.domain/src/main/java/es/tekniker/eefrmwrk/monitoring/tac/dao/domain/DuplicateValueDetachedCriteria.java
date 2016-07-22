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

public class DuplicateValueDetachedCriteria extends AbstractORMDetachedCriteria {
	public final IntegerExpression duplicateValueId;
	public final ShortExpression trendLogId;
	public final StringExpression trendLogGuid;
	public final FloatExpression logValue;
	public final TimestampExpression logTime;
	public final IntegerExpression sequence;
	public final TimestampExpression insertTimeUTC;
	
	public DuplicateValueDetachedCriteria() {
		super(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue.class, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueCriteria.class);
		duplicateValueId = new IntegerExpression("duplicateValueId", this.getDetachedCriteria());
		trendLogId = new ShortExpression("trendLogId", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		logValue = new FloatExpression("logValue", this.getDetachedCriteria());
		logTime = new TimestampExpression("logTime", this.getDetachedCriteria());
		sequence = new IntegerExpression("sequence", this.getDetachedCriteria());
		insertTimeUTC = new TimestampExpression("insertTimeUTC", this.getDetachedCriteria());
	}
	
	public DuplicateValueDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueCriteria.class);
		duplicateValueId = new IntegerExpression("duplicateValueId", this.getDetachedCriteria());
		trendLogId = new ShortExpression("trendLogId", this.getDetachedCriteria());
		trendLogGuid = new StringExpression("trendLogGuid", this.getDetachedCriteria());
		logValue = new FloatExpression("logValue", this.getDetachedCriteria());
		logTime = new TimestampExpression("logTime", this.getDetachedCriteria());
		sequence = new IntegerExpression("sequence", this.getDetachedCriteria());
		insertTimeUTC = new TimestampExpression("insertTimeUTC", this.getDetachedCriteria());
	}
	
	public DuplicateValueTypeDetachedCriteria createDuplicateValueTypeCriteria() {
		return new DuplicateValueTypeDetachedCriteria(createCriteria("duplicateValueType"));
	}
	
	public DuplicateValue uniqueDuplicateValue(PersistentSession session) {
		return (DuplicateValue) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public DuplicateValue[] listDuplicateValue(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (DuplicateValue[]) list.toArray(new DuplicateValue[list.size()]);
	}
}

