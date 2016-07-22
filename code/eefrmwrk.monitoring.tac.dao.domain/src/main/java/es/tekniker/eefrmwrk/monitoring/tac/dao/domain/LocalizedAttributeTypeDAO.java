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

public class LocalizedAttributeTypeDAO {
	public static LocalizedAttributeType loadLocalizedAttributeTypeByORMID(String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAttributeTypeByORMID(session, attributeType, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType getLocalizedAttributeTypeByORMID(String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedAttributeTypeByORMID(session, attributeType, objectType);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByORMID(String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAttributeTypeByORMID(session, attributeType, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType getLocalizedAttributeTypeByORMID(String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getLocalizedAttributeTypeByORMID(session, attributeType, objectType, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByORMID(PersistentSession session, String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType) throws PersistentException {
		try {
			LocalizedAttributeType localizedattributetype = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType();
			localizedattributetype.setAttributeType(attributeType);
			localizedattributetype.setORM_ObjectType(objectType);
			
			return (LocalizedAttributeType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType.class, localizedattributetype);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType getLocalizedAttributeTypeByORMID(PersistentSession session, String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType) throws PersistentException {
		try {
			LocalizedAttributeType localizedattributetype = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType();
			localizedattributetype.setAttributeType(attributeType);
			localizedattributetype.setORM_ObjectType(objectType);
			
			return (LocalizedAttributeType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType.class, localizedattributetype);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByORMID(PersistentSession session, String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			LocalizedAttributeType localizedattributetype = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType();
			localizedattributetype.setAttributeType(attributeType);
			localizedattributetype.setORM_ObjectType(objectType);
			
			return (LocalizedAttributeType) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType.class, localizedattributetype, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType getLocalizedAttributeTypeByORMID(PersistentSession session, String attributeType, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			LocalizedAttributeType localizedattributetype = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType();
			localizedattributetype.setAttributeType(attributeType);
			localizedattributetype.setORM_ObjectType(objectType);
			
			return (LocalizedAttributeType) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType.class, localizedattributetype, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAttributeType(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedAttributeType(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAttributeType(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryLocalizedAttributeType(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType[] listLocalizedAttributeTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedAttributeTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType[] listLocalizedAttributeTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listLocalizedAttributeTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryLocalizedAttributeType(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType as LocalizedAttributeType");
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
	
	public static List queryLocalizedAttributeType(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType as LocalizedAttributeType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedAttributeType", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType[] listLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryLocalizedAttributeType(session, condition, orderBy);
			return (LocalizedAttributeType[]) list.toArray(new LocalizedAttributeType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType[] listLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryLocalizedAttributeType(session, condition, orderBy, lockMode);
			return (LocalizedAttributeType[]) list.toArray(new LocalizedAttributeType[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAttributeTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadLocalizedAttributeTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		LocalizedAttributeType[] localizedAttributeTypes = listLocalizedAttributeTypeByQuery(session, condition, orderBy);
		if (localizedAttributeTypes != null && localizedAttributeTypes.length > 0)
			return localizedAttributeTypes[0];
		else
			return null;
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		LocalizedAttributeType[] localizedAttributeTypes = listLocalizedAttributeTypeByQuery(session, condition, orderBy, lockMode);
		if (localizedAttributeTypes != null && localizedAttributeTypes.length > 0)
			return localizedAttributeTypes[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateLocalizedAttributeTypeByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedAttributeTypeByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedAttributeTypeByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateLocalizedAttributeTypeByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType as LocalizedAttributeType");
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
	
	public static java.util.Iterator iterateLocalizedAttributeTypeByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType as LocalizedAttributeType");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("LocalizedAttributeType", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType createLocalizedAttributeType() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(localizedAttributeType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(localizedAttributeType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType = localizedAttributeType.getObjectType();
			if(localizedAttributeType.getObjectType() != null) {
				localizedAttributeType.getObjectType().localizedAttributeType.remove(localizedAttributeType);
			}
			localizedAttributeType.setORM_ObjectType(objectType);
			
			return delete(localizedAttributeType);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedObjectType objectType = localizedAttributeType.getObjectType();
			if(localizedAttributeType.getObjectType() != null) {
				localizedAttributeType.getObjectType().localizedAttributeType.remove(localizedAttributeType);
			}
			localizedAttributeType.setORM_ObjectType(objectType);
			
			try {
				session.delete(localizedAttributeType);
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
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(localizedAttributeType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.LocalizedAttributeType localizedAttributeType) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(localizedAttributeType);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static LocalizedAttributeType loadLocalizedAttributeTypeByCriteria(LocalizedAttributeTypeCriteria localizedAttributeTypeCriteria) {
		LocalizedAttributeType[] localizedAttributeTypes = listLocalizedAttributeTypeByCriteria(localizedAttributeTypeCriteria);
		if(localizedAttributeTypes == null || localizedAttributeTypes.length == 0) {
			return null;
		}
		return localizedAttributeTypes[0];
	}
	
	public static LocalizedAttributeType[] listLocalizedAttributeTypeByCriteria(LocalizedAttributeTypeCriteria localizedAttributeTypeCriteria) {
		return localizedAttributeTypeCriteria.listLocalizedAttributeType();
	}
}
