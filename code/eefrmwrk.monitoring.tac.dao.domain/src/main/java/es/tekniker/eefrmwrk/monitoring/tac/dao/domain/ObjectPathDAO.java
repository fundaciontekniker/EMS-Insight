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

public class ObjectPathDAO {
	public static ObjectPath loadObjectPathByORMID(int objectPathId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadObjectPathByORMID(session, objectPathId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath getObjectPathByORMID(int objectPathId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getObjectPathByORMID(session, objectPathId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByORMID(int objectPathId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadObjectPathByORMID(session, objectPathId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath getObjectPathByORMID(int objectPathId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getObjectPathByORMID(session, objectPathId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByORMID(PersistentSession session, int objectPathId) throws PersistentException {
		try {
			return (ObjectPath) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath.class, new Integer(objectPathId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath getObjectPathByORMID(PersistentSession session, int objectPathId) throws PersistentException {
		try {
			return (ObjectPath) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath.class, new Integer(objectPathId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByORMID(PersistentSession session, int objectPathId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ObjectPath) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath.class, new Integer(objectPathId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath getObjectPathByORMID(PersistentSession session, int objectPathId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (ObjectPath) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath.class, new Integer(objectPathId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryObjectPath(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryObjectPath(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryObjectPath(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryObjectPath(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath[] listObjectPathByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listObjectPathByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath[] listObjectPathByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listObjectPathByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryObjectPath(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath as ObjectPath");
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
	
	public static List queryObjectPath(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath as ObjectPath");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("ObjectPath", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath[] listObjectPathByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryObjectPath(session, condition, orderBy);
			return (ObjectPath[]) list.toArray(new ObjectPath[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath[] listObjectPathByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryObjectPath(session, condition, orderBy, lockMode);
			return (ObjectPath[]) list.toArray(new ObjectPath[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadObjectPathByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadObjectPathByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		ObjectPath[] objectPaths = listObjectPathByQuery(session, condition, orderBy);
		if (objectPaths != null && objectPaths.length > 0)
			return objectPaths[0];
		else
			return null;
	}
	
	public static ObjectPath loadObjectPathByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		ObjectPath[] objectPaths = listObjectPathByQuery(session, condition, orderBy, lockMode);
		if (objectPaths != null && objectPaths.length > 0)
			return objectPaths[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateObjectPathByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateObjectPathByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateObjectPathByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateObjectPathByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateObjectPathByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath as ObjectPath");
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
	
	public static java.util.Iterator iterateObjectPathByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath as ObjectPath");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("ObjectPath", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath createObjectPath() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(objectPath);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(objectPath);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event[] lEvents = objectPath.event.toArray();
			for(int i = 0; i < lEvents.length; i++) {
				lEvents[i].setObjectPath(null);
			}
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event[] lEvent1s = objectPath.event1.toArray();
			for(int i = 0; i < lEvent1s.length; i++) {
				lEvent1s[i].setShortcutPath(null);
			}
			return delete(objectPath);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event[] lEvents = objectPath.event.toArray();
			for(int i = 0; i < lEvents.length; i++) {
				lEvents[i].setObjectPath(null);
			}
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event[] lEvent1s = objectPath.event1.toArray();
			for(int i = 0; i < lEvent1s.length; i++) {
				lEvent1s[i].setShortcutPath(null);
			}
			try {
				session.delete(objectPath);
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
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(objectPath);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.ObjectPath objectPath) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(objectPath);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static ObjectPath loadObjectPathByCriteria(ObjectPathCriteria objectPathCriteria) {
		ObjectPath[] objectPaths = listObjectPathByCriteria(objectPathCriteria);
		if(objectPaths == null || objectPaths.length == 0) {
			return null;
		}
		return objectPaths[0];
	}
	
	public static ObjectPath[] listObjectPathByCriteria(ObjectPathCriteria objectPathCriteria) {
		return objectPathCriteria.listObjectPath();
	}
}
