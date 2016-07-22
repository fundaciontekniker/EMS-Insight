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

public class DuplicateValueTypeDAO {
	public static DuplicateValueType loadDuplicateValueTypeByORMID(byte duplicateValueTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueTypeByORMID(session, duplicateValueTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType getDuplicateValueTypeByORMID(byte duplicateValueTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getDuplicateValueTypeByORMID(session, duplicateValueTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByORMID(byte duplicateValueTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueTypeByORMID(session, duplicateValueTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType getDuplicateValueTypeByORMID(byte duplicateValueTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getDuplicateValueTypeByORMID(session, duplicateValueTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByORMID(PersistentSession session, byte duplicateValueTypeId) throws PersistentException {
		try {
			return (DuplicateValueType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType.class, new Byte(duplicateValueTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType getDuplicateValueTypeByORMID(PersistentSession session, byte duplicateValueTypeId) throws PersistentException {
		try {
			return (DuplicateValueType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType.class, new Byte(duplicateValueTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByORMID(PersistentSession session, byte duplicateValueTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DuplicateValueType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType.class, new Byte(duplicateValueTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType getDuplicateValueTypeByORMID(PersistentSession session, byte duplicateValueTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (DuplicateValueType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType.class, new Byte(duplicateValueTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValueType(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryDuplicateValueType(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValueType(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryDuplicateValueType(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType[] listDuplicateValueTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listDuplicateValueTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType[] listDuplicateValueTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listDuplicateValueTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryDuplicateValueType(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType as DuplicateValueType");
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
	
	public static List queryDuplicateValueType(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType as DuplicateValueType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("DuplicateValueType", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType[] listDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryDuplicateValueType(session, condition, orderBy);
			return (DuplicateValueType[]) list.toArray(new DuplicateValueType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType[] listDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryDuplicateValueType(session, condition, orderBy, lockMode);
			return (DuplicateValueType[]) list.toArray(new DuplicateValueType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadDuplicateValueTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		DuplicateValueType[] duplicateValueTypes = listDuplicateValueTypeByQuery(session, condition, orderBy);
		if (duplicateValueTypes != null && duplicateValueTypes.length > 0)
			return duplicateValueTypes[0];
		else
			return null;
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		DuplicateValueType[] duplicateValueTypes = listDuplicateValueTypeByQuery(session, condition, orderBy, lockMode);
		if (duplicateValueTypes != null && duplicateValueTypes.length > 0)
			return duplicateValueTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateDuplicateValueTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateDuplicateValueTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDuplicateValueTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateDuplicateValueTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType as DuplicateValueType");
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
	
	public static java.util.Iterator iterateDuplicateValueTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType as DuplicateValueType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("DuplicateValueType", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType createDuplicateValueType() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(duplicateValueType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(duplicateValueType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue[] lDuplicateValues = duplicateValueType.duplicateValue.toArray();
			for(int i = 0; i < lDuplicateValues.length; i++) {
				lDuplicateValues[i].setDuplicateValueType(null);
			}
			return delete(duplicateValueType);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValue[] lDuplicateValues = duplicateValueType.duplicateValue.toArray();
			for(int i = 0; i < lDuplicateValues.length; i++) {
				lDuplicateValues[i].setDuplicateValueType(null);
			}
			try {
				session.delete(duplicateValueType);
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
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(duplicateValueType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.DuplicateValueType duplicateValueType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(duplicateValueType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static DuplicateValueType loadDuplicateValueTypeByCriteria(DuplicateValueTypeCriteria duplicateValueTypeCriteria) {
		DuplicateValueType[] duplicateValueTypes = listDuplicateValueTypeByCriteria(duplicateValueTypeCriteria);
		if(duplicateValueTypes == null || duplicateValueTypes.length == 0) {
			return null;
		}
		return duplicateValueTypes[0];
	}
	
	public static DuplicateValueType[] listDuplicateValueTypeByCriteria(DuplicateValueTypeCriteria duplicateValueTypeCriteria) {
		return duplicateValueTypeCriteria.listDuplicateValueType();
	}
}
