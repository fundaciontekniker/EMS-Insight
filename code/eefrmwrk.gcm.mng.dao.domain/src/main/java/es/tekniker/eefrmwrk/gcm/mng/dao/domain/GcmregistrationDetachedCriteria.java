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
package es.tekniker.eefrmwrk.gcm.mng.dao.domain;

import java.util.List;
import org.hibernate.criterion.DetachedCriteria;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class GcmregistrationDetachedCriteria extends AbstractORMDetachedCriteria {
	public final LongExpression gcmregistration_id;
	public final StringExpression registration_id;
	public final IntegerExpression enabled;
	
	public GcmregistrationDetachedCriteria() {
		super(es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration.class, es.tekniker.eefrmwrk.gcm.mng.dao.domain.GcmregistrationCriteria.class);
		gcmregistration_id = new LongExpression("gcmregistration_id", this.getDetachedCriteria());
		registration_id = new StringExpression("registration_id", this.getDetachedCriteria());
		enabled = new IntegerExpression("enabled", this.getDetachedCriteria());
	}
	
	public GcmregistrationDetachedCriteria(DetachedCriteria aDetachedCriteria) {
		super(aDetachedCriteria, es.tekniker.eefrmwrk.gcm.mng.dao.domain.GcmregistrationCriteria.class);
		gcmregistration_id = new LongExpression("gcmregistration_id", this.getDetachedCriteria());
		registration_id = new StringExpression("registration_id", this.getDetachedCriteria());
		enabled = new IntegerExpression("enabled", this.getDetachedCriteria());
	}
	
	public Gcmregistration uniqueGcmregistration(PersistentSession session) {
		return (Gcmregistration) super.createExecutableCriteria(session).uniqueResult();
	}
	
	public Gcmregistration[] listGcmregistration(PersistentSession session) {
		List list = super.createExecutableCriteria(session).list();
		return (Gcmregistration[]) list.toArray(new Gcmregistration[list.size()]);
	}
}

