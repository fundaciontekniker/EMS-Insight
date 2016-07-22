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

public class LocalizedCommandTypeDAO {
	public static LocalizedCommandType loadLocalizedCommandTypeByORMID(int commandTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedCommandTypeByORMID(session, commandTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType getLocalizedCommandTypeByORMID(int commandTypeId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedCommandTypeByORMID(session, commandTypeId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByORMID(int commandTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedCommandTypeByORMID(session, commandTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType getLocalizedCommandTypeByORMID(int commandTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedCommandTypeByORMID(session, commandTypeId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByORMID(PersistentSession session, int commandTypeId) throws PersistentException {
		try {
			return (LocalizedCommandType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType.class, new Integer(commandTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType getLocalizedCommandTypeByORMID(PersistentSession session, int commandTypeId) throws PersistentException {
		try {
			return (LocalizedCommandType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType.class, new Integer(commandTypeId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByORMID(PersistentSession session, int commandTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedCommandType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType.class, new Integer(commandTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType getLocalizedCommandTypeByORMID(PersistentSession session, int commandTypeId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedCommandType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType.class, new Integer(commandTypeId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedCommandType(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedCommandType(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedCommandType(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedCommandType(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType[] listLocalizedCommandTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedCommandTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType[] listLocalizedCommandTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedCommandTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedCommandType(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType as LocalizedCommandType");
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
	
	public static List queryLocalizedCommandType(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType as LocalizedCommandType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedCommandType", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType[] listLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryLocalizedCommandType(session, condition, orderBy);
			return (LocalizedCommandType[]) list.toArray(new LocalizedCommandType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType[] listLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryLocalizedCommandType(session, condition, orderBy, lockMode);
			return (LocalizedCommandType[]) list.toArray(new LocalizedCommandType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedCommandTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedCommandTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocalizedCommandType[] localizedCommandTypes = listLocalizedCommandTypeByQuery(session, condition, orderBy);
		if (localizedCommandTypes != null && localizedCommandTypes.length > 0)
			return localizedCommandTypes[0];
		else
			return null;
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocalizedCommandType[] localizedCommandTypes = listLocalizedCommandTypeByQuery(session, condition, orderBy, lockMode);
		if (localizedCommandTypes != null && localizedCommandTypes.length > 0)
			return localizedCommandTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocalizedCommandTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedCommandTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedCommandTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedCommandTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType as LocalizedCommandType");
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
	
	public static java.util.Iterator iterateLocalizedCommandTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType as LocalizedCommandType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedCommandType", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType createLocalizedCommandType() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType localizedCommandType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(localizedCommandType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType localizedCommandType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(localizedCommandType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType localizedCommandType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(localizedCommandType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedCommandType localizedCommandType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(localizedCommandType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedCommandType loadLocalizedCommandTypeByCriteria(LocalizedCommandTypeCriteria localizedCommandTypeCriteria) {
		LocalizedCommandType[] localizedCommandTypes = listLocalizedCommandTypeByCriteria(localizedCommandTypeCriteria);
		if(localizedCommandTypes == null || localizedCommandTypes.length == 0) {
			return null;
		}
		return localizedCommandTypes[0];
	}
	
	public static LocalizedCommandType[] listLocalizedCommandTypeByCriteria(LocalizedCommandTypeCriteria localizedCommandTypeCriteria) {
		return localizedCommandTypeCriteria.listLocalizedCommandType();
	}
}
