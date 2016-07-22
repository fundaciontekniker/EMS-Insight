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

public class LocalizedAlarmTypeDAO {
	public static LocalizedAlarmType loadLocalizedAlarmTypeByORMID(int alarmTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAlarmTypeByORMID(session, alarmTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType getLocalizedAlarmTypeByORMID(int alarmTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedAlarmTypeByORMID(session, alarmTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByORMID(int alarmTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAlarmTypeByORMID(session, alarmTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType getLocalizedAlarmTypeByORMID(int alarmTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedAlarmTypeByORMID(session, alarmTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByORMID(PersistentSession session, int alarmTypeId) throws PersistentException {
		try {
			return (LocalizedAlarmType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType.class, new Integer(alarmTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType getLocalizedAlarmTypeByORMID(PersistentSession session, int alarmTypeId) throws PersistentException {
		try {
			return (LocalizedAlarmType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType.class, new Integer(alarmTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByORMID(PersistentSession session, int alarmTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedAlarmType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType.class, new Integer(alarmTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType getLocalizedAlarmTypeByORMID(PersistentSession session, int alarmTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedAlarmType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType.class, new Integer(alarmTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAlarmType(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedAlarmType(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAlarmType(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedAlarmType(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType[] listLocalizedAlarmTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedAlarmTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType[] listLocalizedAlarmTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedAlarmTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAlarmType(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType as LocalizedAlarmType");
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
	
	public static List queryLocalizedAlarmType(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType as LocalizedAlarmType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedAlarmType", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType[] listLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryLocalizedAlarmType(session, condition, orderBy);
			return (LocalizedAlarmType[]) list.toArray(new LocalizedAlarmType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType[] listLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryLocalizedAlarmType(session, condition, orderBy, lockMode);
			return (LocalizedAlarmType[]) list.toArray(new LocalizedAlarmType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAlarmTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAlarmTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocalizedAlarmType[] localizedAlarmTypes = listLocalizedAlarmTypeByQuery(session, condition, orderBy);
		if (localizedAlarmTypes != null && localizedAlarmTypes.length > 0)
			return localizedAlarmTypes[0];
		else
			return null;
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocalizedAlarmType[] localizedAlarmTypes = listLocalizedAlarmTypeByQuery(session, condition, orderBy, lockMode);
		if (localizedAlarmTypes != null && localizedAlarmTypes.length > 0)
			return localizedAlarmTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocalizedAlarmTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedAlarmTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedAlarmTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedAlarmTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType as LocalizedAlarmType");
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
	
	public static java.util.Iterator iterateLocalizedAlarmTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType as LocalizedAlarmType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedAlarmType", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType createLocalizedAlarmType() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType localizedAlarmType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(localizedAlarmType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType localizedAlarmType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(localizedAlarmType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType localizedAlarmType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(localizedAlarmType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAlarmType localizedAlarmType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(localizedAlarmType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAlarmType loadLocalizedAlarmTypeByCriteria(LocalizedAlarmTypeCriteria localizedAlarmTypeCriteria) {
		LocalizedAlarmType[] localizedAlarmTypes = listLocalizedAlarmTypeByCriteria(localizedAlarmTypeCriteria);
		if(localizedAlarmTypes == null || localizedAlarmTypes.length == 0) {
			return null;
		}
		return localizedAlarmTypes[0];
	}
	
	public static LocalizedAlarmType[] listLocalizedAlarmTypeByCriteria(LocalizedAlarmTypeCriteria localizedAlarmTypeCriteria) {
		return localizedAlarmTypeCriteria.listLocalizedAlarmType();
	}
}
