package es.tekniker.eefrmwrk.database.sql.manage;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.model.Alarm;
import es.tekniker.eefrmwrk.database.sql.model.Alert;

/**
 * @author agarcia
 */

public class AlertManager {

	private static final Log log = LogFactory.getLog(AlertManager.class);
	private static final String tableName = "Alert";

	public static long save(Alert instance) throws BaseException {
		Session session = null;
		long instanceId = -1;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instanceId = save(instance, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instanceId;
	}
	public static long save(Alert instance, Session session) throws BaseException {
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long) session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(),
					ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("AlertManager_SAVE0",
					"Database error saving alert", re);
		}
		return instanceSaved.longValue();
	}

	public static Alert find(long id) throws BaseException {
		Session session = null;
		Alert instance = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = find(id, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instance;
	}
	public static Alert find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : "+ Long.toString(id));				
		Alert instance = null;
		try {
			instance = (Alert) session.get(Alert.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlertManager_FINDBYID0","Database error getting alert", re);					
		}
		return instance;
	}

	public static List<Alert> findAll() throws BaseException {
		Session session = null;
		List<Alert> list = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findAll(session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return list;
	}
	public static List<Alert> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<Alert> lista = new ArrayList<Alert>();
		try {
			Criteria criteria = session.createCriteria(Alert.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("alertId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlertManager_FINDALL0",
					"Database error getting all alerts", re);
		}
		return lista;
	}

	public static List<Alert> findByAlarm(long alarmId) throws BaseException {
		Session session = null;
		List<Alert> al = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			al = findByAlarm(alarmId, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return al;
	}
	public static List<Alert> findByAlarm(long alarmId, Session session)
			throws BaseException {
		//log.debug("getting " + tableName + " instance with code : " + code);
		List<Alert> lista =  new ArrayList<Alert>();
		try {
			Criteria criteria = session.createCriteria(Alert.class);
			criteria.add(Restrictions.eq("alertAlarm", alarmId));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			}else {
				log.debug("get " + tableName + " successful, instance found");
			}			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlertManagerFINDBYALARM0",
					"Database error getting alert by code", re);
		}
		return lista;
	}

	public static void delete(long id) throws BaseException {
		log.debug("deleting " + tableName + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			delete(id, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
	}
	public static void delete(long id, Session session) throws BaseException {
		//log.debug("deleting " + tableName + " instance");
		try {
			Alert alert = (Alert) session.load(Alert.class, id);
			//log.debug("alert loaded id: " + id);
			if (alert != null) {
				alert.setActiv(0);
				session.update(alert);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONDELETE, null, null, null,session);
			}
			} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("AlertManager_DELETE0","Database error deleting alert", re);
		}
	}

	/*
	 * public void deleteByLogin(String login) throws BaseException{
	 * log.debug("deleting " + tableName + "s"); Session session = null; try {
	 * log.debug("Opening session"); session = HibernateUtil.currentSession();
	 * session.beginTransaction(); deleteByLogin(login, session);
	 * session.getTransaction().commit(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); } catch (BaseException e){
	 * log.error("deleteBySearch " + tableName + " failed", e);
	 * session.getTransaction().rollback(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); throw e; } }
	 * 
	 * public void deleteByLogin(String login, Session session) throws
	 * BaseException{ log.debug("deleting " + tableName + " instance"); try {
	 * List<Alert> lista = (List<Alert>) findByLogin(login, session); for(int
	 * i=0; i<lista.size(); i++){ lista.get(i).setActiv(0);
	 * session.update(lista.get(i)); log.debug(tableName + " instance deleted");
	 * ChangeLogManager.save(tableName, lista.get(i).getalertId(),
	 * ChangeLogManager.ACTIONDELETE, null, null, null, session); } } catch
	 * (RuntimeException re) { log.error("delete " + tableName + " failed", re);
	 * throw new BaseException("AlertManagerDELETEBYLOGIN0",
	 * "Database error deleting Alerts by login", re); } }
	 */
	
	public static Alert update(Alert instance) throws BaseException {
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = update(instance, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instance;
	}
	public static Alert update(Alert instance, Session session) throws BaseException {
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getAlertId();
			Alert alert = (Alert) session.load(Alert.class,id);
			//log.debug("Alert to be updated: " + id);
			if (alert.getActiv()==1) {
				alert.setAlertMessage(instance.getAlertMessage());
				alert.setAlertStatus(instance.getAlertStatus());
				alert.setAlertAlarm(instance.getAlertAlarm());
				alert.setAlertTimestamp(instance.getAlertTimestamp());
				session.update(alert);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("AlertManager_UPDATE1","Alert not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("AlertManager_UPDATE0","Database error updating alert", re);
		}
		return instance;
	}
	
	//------------------------------------
	
}
