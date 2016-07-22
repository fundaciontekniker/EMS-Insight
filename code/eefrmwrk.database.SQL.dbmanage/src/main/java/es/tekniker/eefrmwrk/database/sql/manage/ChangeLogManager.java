/**
 * 
 */
package es.tekniker.eefrmwrk.database.sql.manage;

/**
 * @author fdiez
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.model.ChangeLog;

/**
 * @author fdiez
 *
 */
public class ChangeLogManager {

	private static final Log log = LogFactory.getLog(ChangeLogManager.class);
	private static final String tableName = "CHANGELOG";
	public static final String ACTIONINSERT = "ACTIONINSERT";
	public static final String ACTIONDELETE = "ACTIONDELETE";
	public static final String ACTIONUPDATE = "ACTIONUPDATE";
	public static final String UNKNOWN = "UNKNOWN";
	
	public static long save(String changeTable, long changeTargetid, String changeAction, String changeOld, String changeNew, String changeCol, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance by parameters");
		ChangeLog changeLog = new ChangeLog();
		if (changeTable == null || changeTable.isEmpty()){
			changeTable = UNKNOWN;
		}
		changeLog.setChangeTable(changeTable);
		changeLog.setChangeTargetid(changeTargetid);
		if (changeAction == null || changeAction.isEmpty()){
			changeAction = UNKNOWN;
		}
		changeLog.setChangeAction(changeAction);
		changeLog.setChangeTimestamp(new Date());
		if (changeOld != null && changeOld.isEmpty()){
			changeLog.setChangeOld(changeOld);
		}
		if (changeNew != null && changeNew.isEmpty()){
			changeLog.setChangeNew(changeNew);
		}
		if (changeCol != null && changeCol.isEmpty()){
			changeLog.setChangeCol(changeCol);
		}
		long changeLogId = save(changeLog, session);
		return changeLogId;
		
	}
	
	public static long save(ChangeLog instance) throws BaseException{
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
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instanceId;
	}
	
	public static long save(ChangeLog instance, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("ChangeLogManagerSAVE0", "Database error saving userlogin", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static ChangeLog find(long id) throws BaseException {
		Session session = null;
		ChangeLog instance = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = find(id, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			//log.error("find " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static ChangeLog find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		ChangeLog instance = null;
		try {
			instance = (ChangeLog)session.get(ChangeLog.class, id);
			if (instance == null ) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ChangeLogManagerFINDBYID0", "Database error getting userlogin", re);
		}
		return instance;
	}
	
	public static List<ChangeLog> findAll() throws BaseException {
		Session session = null;
		List<ChangeLog> list = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findAll(session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return list;
	}

	public static List<ChangeLog> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<ChangeLog> lista = new ArrayList<ChangeLog>();
		try {
			Criteria criteria = session.createCriteria(ChangeLog.class);
			criteria.addOrder(Order.asc("changeId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ChangeLogManagerFINDALL0", "Database error getting all userlogins", re);
		}
		return lista;
	}

	public static List<ChangeLog> findByTable(String table) throws BaseException {
		Session session = null;
		List<ChangeLog> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByTable(table, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return lista;
	}
	
	public static List<ChangeLog> findByTable(String table, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with table : " + table);
		List<ChangeLog> lista = null;
		try {
			Criteria criteria = session.createCriteria(ChangeLog.class);
			criteria.add(Restrictions.eq("changeTable", table));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ChangeLogManagerFINDBYTABLE0", "Database error getting Logs by table", re);
		}
		return lista;
	}
	
	public static void delete(long id) throws BaseException{
		//log.debug("deleting " + tableName + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			delete(id, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			//log.error("delete " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();		
			throw e;
		}
	}
	
	public static void delete(long id, Session session) throws BaseException{
		//log.debug("deleting " + tableName + " instance");
		try {
			ChangeLog ChangeLog = (ChangeLog)session.load(ChangeLog.class, id);
			//log.debug("ChangeLog loaded id: " + id);
			if(ChangeLog != null){
				session.update(ChangeLog);
				log.debug("deleted " + tableName + "s");
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("ChangeLogManagerDELETE0", "Database error deleting userlogin", re);
		}
	}
	
	public static ChangeLog update(ChangeLog instance) throws BaseException{
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = update(instance, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			//log.error("update " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static ChangeLog update(ChangeLog instance, Session session) throws BaseException{
		//log.debug("updating " + tableName + " instance");
		try {
			session.update(instance);
			log.debug("updated " + tableName + " instance");
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("ChangeLogManagerUPDATE0", "Database error updating userlogin", re);
		}
		return instance;
	}

}