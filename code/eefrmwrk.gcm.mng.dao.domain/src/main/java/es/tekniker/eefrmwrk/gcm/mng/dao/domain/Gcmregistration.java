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

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

public class Gcmregistration {
	public Gcmregistration() {
	}
	
	public static Gcmregistration loadGcmregistrationByORMID(long gcmregistration_id) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return loadGcmregistrationByORMID(session, gcmregistration_id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration getGcmregistrationByORMID(long gcmregistration_id) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return getGcmregistrationByORMID(session, gcmregistration_id);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByORMID(long gcmregistration_id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return loadGcmregistrationByORMID(session, gcmregistration_id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration getGcmregistrationByORMID(long gcmregistration_id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return getGcmregistrationByORMID(session, gcmregistration_id, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByORMID(PersistentSession session, long gcmregistration_id) throws PersistentException {
		try {
			return (Gcmregistration) session.load(es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration.class, new Long(gcmregistration_id));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration getGcmregistrationByORMID(PersistentSession session, long gcmregistration_id) throws PersistentException {
		try {
			return (Gcmregistration) session.get(es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration.class, new Long(gcmregistration_id));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByORMID(PersistentSession session, long gcmregistration_id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Gcmregistration) session.load(es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration.class, new Long(gcmregistration_id), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration getGcmregistrationByORMID(PersistentSession session, long gcmregistration_id, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (Gcmregistration) session.get(es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration.class, new Long(gcmregistration_id), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGcmregistration(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return queryGcmregistration(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGcmregistration(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return queryGcmregistration(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration[] listGcmregistrationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return listGcmregistrationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration[] listGcmregistrationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return listGcmregistrationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGcmregistration(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.gcm.mng.dao.Gcmregistration as Gcmregistration");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryGcmregistration(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.gcm.mng.dao.Gcmregistration as Gcmregistration");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Gcmregistration", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration[] listGcmregistrationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryGcmregistration(session, condition, orderBy);
			return (Gcmregistration[]) list.toArray(new Gcmregistration[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration[] listGcmregistrationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryGcmregistration(session, condition, orderBy, lockMode);
			return (Gcmregistration[]) list.toArray(new Gcmregistration[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return loadGcmregistrationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return loadGcmregistrationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		Gcmregistration[] gcmregistrations = listGcmregistrationByQuery(session, condition, orderBy);
		if (gcmregistrations != null && gcmregistrations.length > 0)
			return gcmregistrations[0];
		else
			return null;
	}
	
	public static Gcmregistration loadGcmregistrationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		Gcmregistration[] gcmregistrations = listGcmregistrationByQuery(session, condition, orderBy, lockMode);
		if (gcmregistrations != null && gcmregistrations.length > 0)
			return gcmregistrations[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateGcmregistrationByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return iterateGcmregistrationByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGcmregistrationByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession();
			return iterateGcmregistrationByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGcmregistrationByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.gcm.mng.dao.Gcmregistration as Gcmregistration");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateGcmregistrationByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.gcm.mng.dao.Gcmregistration as Gcmregistration");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("Gcmregistration", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static Gcmregistration loadGcmregistrationByCriteria(GcmregistrationCriteria gcmregistrationCriteria) {
		Gcmregistration[] gcmregistrations = listGcmregistrationByCriteria(gcmregistrationCriteria);
		if(gcmregistrations == null || gcmregistrations.length == 0) {
			return null;
		}
		return gcmregistrations[0];
	}
	
	public static Gcmregistration[] listGcmregistrationByCriteria(GcmregistrationCriteria gcmregistrationCriteria) {
		return gcmregistrationCriteria.listGcmregistration();
	}
	
	public static Gcmregistration createGcmregistration() {
		return new es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration();
	}
	
	public boolean save() throws PersistentException {
		try {
			es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().saveObject(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean delete() throws PersistentException {
		try {
			es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().deleteObject(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean refresh() throws PersistentException {
		try {
			es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession().refresh(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public boolean evict() throws PersistentException {
		try {
			es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager.instance().getSession().evict(this);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	private long gcmregistration_id;
	
	private String registration_id;
	
	private Integer enabled;
	
	private void setGcmregistration_id(long value) {
		this.gcmregistration_id = value;
	}
	
	public long getGcmregistration_id() {
		return gcmregistration_id;
	}
	
	public long getORMID() {
		return getGcmregistration_id();
	}
	
	public void setRegistration_id(String value) {
		this.registration_id = value;
	}
	
	public String getRegistration_id() {
		return registration_id;
	}
	
	public void setEnabled(int value) {
		setEnabled(new Integer(value));
	}
	
	public void setEnabled(Integer value) {
		this.enabled = value;
	}
	
	public Integer getEnabled() {
		return enabled;
	}
	
	public String toString() {
		return String.valueOf(getGcmregistration_id());
	}
	
}
