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

/**
 * @author agarcia
 */

public class AlarmManager {

	private static final Log log = LogFactory.getLog(AlarmManager.class);
	private static final String tableName = "Alarm";

	public static long save(Alarm instance) throws BaseException {
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
	public static long save(Alarm instance, Session session) throws BaseException {
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
			throw new BaseException("AlarmManager_SAVE0",
					"Database error saving alarm", re);
		}
		return instanceSaved.longValue();
	}

	public static Alarm find(long id) throws BaseException {
		Session session = null;
		Alarm instance = null;
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
	public static Alarm find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : "+ Long.toString(id));				
		Alarm instance = null;
		try {
			instance = (Alarm) session.get(Alarm.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlarmManager_FINDBYID0","Database error getting alarm", re);					
		}
		return instance;
	}

	public static List<Alarm> findAll() throws BaseException {
		Session session = null;
		List<Alarm> list = null;
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
	public static List<Alarm> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<Alarm> lista = new ArrayList<Alarm>();
		try {
			Criteria criteria = session.createCriteria(Alarm.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("alarmId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlarmManager_FINDALL0",	"Database error getting all alarms", re);		
		}
		return lista;
	}

	public static Alarm findByCode(String code) throws BaseException {
		Session session = null;
		Alarm dev = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			dev = findByCode(code, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return dev;
	}
	public static Alarm findByCode(String code, Session session)
			throws BaseException {
		//log.debug("getting " + tableName + " instance with code : " + code);
		List<Alarm> lista = null;
		Alarm dev=null;
		try {
			Criteria criteria = session.createCriteria(Alarm.class);
			criteria.add(Restrictions.eq("alarmCode", code));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 variable with this code");
				throw new BaseException("AlarmManager_FINDBYCODE1", "More than 1 alarm with this code");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				dev=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("AlarmManagerFINDBYCODE0",
					"Database error getting alarm by code", re);
		}
		return dev;
	}

	public static List<Alarm> findByStatus(String status, Integer limit) throws BaseException {
		Session session = null;
		List<Alarm> dev = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			dev = findByStatus(status, limit, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return dev;
	}
	public static List<Alarm> findByStatus(String status, Integer limit, Session session)
			throws BaseException {
		//log.debug("getting " + tableName + " instance with code : " + code);
		List<Alarm> lista = null;
		try {
			Criteria criteria = session.createCriteria(Alarm.class);
			if(status!=null)
				criteria.add(Restrictions.eq("alarmState", status));
			criteria.addOrder(Order.desc("alarmTimespan"));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			if(limit!=null && limit>0)
				criteria.setMaxResults(limit);
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			}else {
				log.debug("get " + tableName + " successful, instance found");
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			throw new BaseException("AlarmManagerFINDBYSTATUS0","Database error getting alarm by status", re);
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
			Alarm alarm = (Alarm) session.load(Alarm.class, id);
			//log.debug("alarm loaded id: " + id);
			if (alarm != null) {
				alarm.setActiv(0);
				session.update(alarm);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONDELETE, null, null, null,session);
			}
			} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("AlarmManager_DELETE0","Database error deleting alarm", re);
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
	 * List<Alarm> lista = (List<Alarm>) findByLogin(login, session); for(int
	 * i=0; i<lista.size(); i++){ lista.get(i).setActiv(0);
	 * session.update(lista.get(i)); log.debug(tableName + " instance deleted");
	 * ChangeLogManager.save(tableName, lista.get(i).getalarmId(),
	 * ChangeLogManager.ACTIONDELETE, null, null, null, session); } } catch
	 * (RuntimeException re) { log.error("delete " + tableName + " failed", re);
	 * throw new BaseException("AlarmManagerDELETEBYLOGIN0",
	 * "Database error deleting Alarms by login", re); } }
	 */
	
	public static Alarm update(Alarm instance) throws BaseException {
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
	public static Alarm update(Alarm instance, Session session) throws BaseException {
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getAlarmId();
			Alarm alarm = (Alarm) session.load(Alarm.class,id);
			//log.debug("Alarm to be updated: " + id);
			if (alarm.getActiv()==1) {
				alarm.setAlarmCode(instance.getAlarmCode());
				alarm.setAlarmDesc(instance.getAlarmDesc());
				alarm.setAlarmType(instance.getAlarmType());
				alarm.setAlarmMessage(instance.getAlarmMessage());
				alarm.setAlarmDuedate(instance.getAlarmDuedate());
				alarm.setAlarmSeverity(instance.getAlarmSeverity());
				alarm.setAlarmState(instance.getAlarmState());
				alarm.setAlarmTimespan(instance.getAlarmTimespan());
				alarm.setAlarmRule(instance.getAlarmRule());
				alarm.setAlarmUser(instance.getAlarmUser());
				session.update(alarm);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("AlarmManager_UPDATE1","Alarm not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("AlarmManager_UPDATE0","Database error updating alarm", re);
		}
		return instance;
	}
	
	//------------------------------------
	
	
	
	
}
