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

import org.hibernate.Criteria;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.criteria.*;

public class GcmregistrationCriteria extends AbstractORMCriteria {
	public final LongExpression gcmregistration_id;
	public final StringExpression registration_id;
	public final IntegerExpression enabled;
	
	public GcmregistrationCriteria(Criteria criteria) {
		super(criteria);
		gcmregistration_id = new LongExpression("gcmregistration_id", this);
		registration_id = new StringExpression("registration_id", this);
		enabled = new IntegerExpression("enabled", this);
	}
	
	public GcmregistrationCriteria(PersistentSession session) {
		this(session.createCriteria(Gcmregistration.class));
	}
	
	public GcmregistrationCriteria() throws PersistentException {
		this(es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession());
	}
	
	public Gcmregistration uniqueGcmregistration() {
		return (Gcmregistration) super.uniqueResult();
	}
	
	public Gcmregistration[] listGcmregistration() {
		java.util.List list = super.list();
		return (Gcmregistration[]) list.toArray(new Gcmregistration[list.size()]);
	}
}

