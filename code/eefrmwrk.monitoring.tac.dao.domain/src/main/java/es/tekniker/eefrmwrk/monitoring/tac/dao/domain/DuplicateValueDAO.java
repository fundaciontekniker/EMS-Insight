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

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

public class DuplicateValueDAO {
	public static DuplicateValue loadDuplicateValueByORMID(int duplicateValueId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueByORMID(session, duplicateValueId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue getDuplicateValueByORMID(int duplicateValueId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getDuplicateValueByORMID(session, duplicateValueId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByORMID(int duplicateValueId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueByORMID(session, duplicateValueId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue getDuplicateValueByORMID(int duplicateValueId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getDuplicateValueByORMID(session, duplicateValueId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByORMID(PersistentSession session, int duplicateValueId) throws PersistentException {
		try {
			return (DuplicateValue) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue.class, new Integer(duplicateValueId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue getDuplicateValueByORMID(PersistentSession session, int duplicateValueId) throws PersistentException {
		try {
			return (DuplicateValue) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue.class, new Integer(duplicateValueId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByORMID(PersistentSession session, int duplicateValueId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DuplicateValue) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue.class, new Integer(duplicateValueId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue getDuplicateValueByORMID(PersistentSession session, int duplicateValueId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DuplicateValue) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue.class, new Integer(duplicateValueId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValue(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryDuplicateValue(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValue(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryDuplicateValue(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue[] listDuplicateValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listDuplicateValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue[] listDuplicateValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listDuplicateValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValue(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue as DuplicateValue");
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
	
	public static List queryDuplicateValue(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue as DuplicateValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("DuplicateValue", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue[] listDuplicateValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryDuplicateValue(session, condition, orderBy);
			return (DuplicateValue[]) list.toArray(new DuplicateValue[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue[] listDuplicateValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryDuplicateValue(session, condition, orderBy, lockMode);
			return (DuplicateValue[]) list.toArray(new DuplicateValue[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DuplicateValue[] duplicateValues = listDuplicateValueByQuery(session, condition, orderBy);
		if (duplicateValues != null && duplicateValues.length > 0)
			return duplicateValues[0];
		else
			return null;
	}
	
	public static DuplicateValue loadDuplicateValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DuplicateValue[] duplicateValues = listDuplicateValueByQuery(session, condition, orderBy, lockMode);
		if (duplicateValues != null && duplicateValues.length > 0)
			return duplicateValues[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDuplicateValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateDuplicateValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDuplicateValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateDuplicateValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDuplicateValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue as DuplicateValue");
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
	
	public static java.util.Iterator iterateDuplicateValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue as DuplicateValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("DuplicateValue", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue createDuplicateValue() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(duplicateValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(duplicateValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue)throws PersistentException {
		try {
			if(duplicateValue.getDuplicateValueType() != null) {
				duplicateValue.getDuplicateValueType().duplicateValue.remove(duplicateValue);
			}
			
			return delete(duplicateValue);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue, org.orm.PersistentSession session)throws PersistentException {
		try {
			if(duplicateValue.getDuplicateValueType() != null) {
				duplicateValue.getDuplicateValueType().duplicateValue.remove(duplicateValue);
			}
			
			try {
				session.delete(duplicateValue);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(duplicateValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue duplicateValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(duplicateValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValue loadDuplicateValueByCriteria(DuplicateValueCriteria duplicateValueCriteria) {
		DuplicateValue[] duplicateValues = listDuplicateValueByCriteria(duplicateValueCriteria);
		if(duplicateValues == null || duplicateValues.length == 0) {
			return null;
		}
		return duplicateValues[0];
	}
	
	public static DuplicateValue[] listDuplicateValueByCriteria(DuplicateValueCriteria duplicateValueCriteria) {
		return duplicateValueCriteria.listDuplicateValue();
	}
}
