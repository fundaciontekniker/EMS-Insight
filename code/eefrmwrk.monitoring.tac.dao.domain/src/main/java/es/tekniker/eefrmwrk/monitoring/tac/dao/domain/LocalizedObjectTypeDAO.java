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

public class LocalizedObjectTypeDAO {
	public static LocalizedObjectType loadLocalizedObjectTypeByORMID(String objectType) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedObjectTypeByORMID(session, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType getLocalizedObjectTypeByORMID(String objectType) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedObjectTypeByORMID(session, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByORMID(String objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedObjectTypeByORMID(session, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType getLocalizedObjectTypeByORMID(String objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedObjectTypeByORMID(session, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByORMID(PersistentSession session, String objectType) throws PersistentException {
		try {
			return (LocalizedObjectType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType.class, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType getLocalizedObjectTypeByORMID(PersistentSession session, String objectType) throws PersistentException {
		try {
			return (LocalizedObjectType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType.class, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByORMID(PersistentSession session, String objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedObjectType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType.class, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType getLocalizedObjectTypeByORMID(PersistentSession session, String objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (LocalizedObjectType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType.class, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedObjectType(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedObjectType(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedObjectType(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedObjectType(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType[] listLocalizedObjectTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedObjectTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType[] listLocalizedObjectTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedObjectTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedObjectType(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType as LocalizedObjectType");
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
	
	public static List queryLocalizedObjectType(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType as LocalizedObjectType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedObjectType", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType[] listLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryLocalizedObjectType(session, condition, orderBy);
			return (LocalizedObjectType[]) list.toArray(new LocalizedObjectType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType[] listLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryLocalizedObjectType(session, condition, orderBy, lockMode);
			return (LocalizedObjectType[]) list.toArray(new LocalizedObjectType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedObjectTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedObjectTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocalizedObjectType[] localizedObjectTypes = listLocalizedObjectTypeByQuery(session, condition, orderBy);
		if (localizedObjectTypes != null && localizedObjectTypes.length > 0)
			return localizedObjectTypes[0];
		else
			return null;
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocalizedObjectType[] localizedObjectTypes = listLocalizedObjectTypeByQuery(session, condition, orderBy, lockMode);
		if (localizedObjectTypes != null && localizedObjectTypes.length > 0)
			return localizedObjectTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocalizedObjectTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedObjectTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedObjectTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedObjectTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType as LocalizedObjectType");
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
	
	public static java.util.Iterator iterateLocalizedObjectTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType as LocalizedObjectType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedObjectType", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType createLocalizedObjectType() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(localizedObjectType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(localizedObjectType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType[] lLocalizedAttributeTypes = localizedObjectType.localizedAttributeType.toArray();
			for(int i = 0; i < lLocalizedAttributeTypes.length; i++) {
				lLocalizedAttributeTypes[i].setObjectType(null);
			}
			return delete(localizedObjectType);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType[] lLocalizedAttributeTypes = localizedObjectType.localizedAttributeType.toArray();
			for(int i = 0; i < lLocalizedAttributeTypes.length; i++) {
				lLocalizedAttributeTypes[i].setObjectType(null);
			}
			try {
				session.delete(localizedObjectType);
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
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(localizedObjectType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType localizedObjectType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(localizedObjectType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedObjectType loadLocalizedObjectTypeByCriteria(LocalizedObjectTypeCriteria localizedObjectTypeCriteria) {
		LocalizedObjectType[] localizedObjectTypes = listLocalizedObjectTypeByCriteria(localizedObjectTypeCriteria);
		if(localizedObjectTypes == null || localizedObjectTypes.length == 0) {
			return null;
		}
		return localizedObjectTypes[0];
	}
	
	public static LocalizedObjectType[] listLocalizedObjectTypeByCriteria(LocalizedObjectTypeCriteria localizedObjectTypeCriteria) {
		return localizedObjectTypeCriteria.listLocalizedObjectType();
	}
}
