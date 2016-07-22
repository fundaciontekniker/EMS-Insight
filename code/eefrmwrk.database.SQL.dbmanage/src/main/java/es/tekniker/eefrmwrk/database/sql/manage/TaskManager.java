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
import es.tekniker.eefrmwrk.database.sql.model.Device;
import es.tekniker.eefrmwrk.database.sql.model.Task;
import es.tekniker.eefrmwrk.database.sql.model.TaskStage;
import es.tekniker.eefrmwrk.database.sql.model.UserStage;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadataStage;

/**
 * @author agarcia
 *
 */
public class TaskManager {

	private static final Log log = LogFactory.getLog(TaskManager.class);
	private static final String tableName = "Task";
	
	public static long save(Task instance) throws BaseException{
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
	
	public static long save(Task instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("TaskManagerSAVE0", "Database error saving task", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static Task find(long id) throws BaseException {
		Session session = null;
		Task instance = null;
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
			log.error("findById " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static Task find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		Task instance = null;
		try {
			instance = (Task)session.get(Task.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("TaskManagerFINDBYID0", "Database error getting task", re);
		}
		return instance;
	}

	public static List<Task> findAll() throws BaseException {
		Session session = null;
		List<Task> list = null;
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
	
	public static List<Task> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<Task> lista = new ArrayList<Task>();
		try {
			Criteria criteria = session.createCriteria(Task.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("taskId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("TaskManagerFINDALL0", "Database error getting all tasks", re);
		}
		return lista;
	}
	
	public static void delete(long id) throws BaseException{
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
		}
		catch (BaseException e){
			log.error("delete " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();		
			throw e;
		}
	}
	
	public static void delete(long id, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			Task task = (Task)session.load(Task.class, id);
			log.debug("Task loaded id: " + id);
			if(task != null){
				task.setActiv(0);
				session.update(task);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
			DBInstanceManager.delete(TaskStage.class,"taskId", id,session);
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("TaskManagerDELETE0", "Database error deleting task", re);
		}
	}
	

	public static Task update(Task instance) throws BaseException{
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
			log.error("update " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static Task update(Task instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getTaskId();
			Task task = (Task) session.load(Task.class,id);
			log.debug("Task to be updated: " + id);
			if (task.getActiv()==1) {
				task.setTaskName(instance.getTableName());
				task.setTaskDesc(instance.getTaskDesc());
				task.setTaskPeriod(instance.getTaskPeriod());
				task.setTaskDev(instance.getTaskDev());
				task.setTaskVar(instance.getTaskVar());
				task.setTaskValue(instance.getTaskValue());
				session.update(task);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("TaskManagerUPDATE1","Task not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("TaskManagerUPDATE0", "Database error updating task", re);
		}
		return instance;
	}
	
	
	public static Task findByName(String name) throws BaseException {
		Session session = null;
		Task tsk = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			tsk = findByName(name, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return tsk;
	}
	public static Task findByName(String name, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<Task> lista = null;
		Task tsk=null;
		try {	
			Criteria criteria = session.createCriteria(Task.class);
			criteria.add(Restrictions.eq("taskName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 task with this name");
				throw new BaseException("TaskManagerFINDBYNAME1", "More than 1 task with this name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				tsk=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("TaskManagerFINDBYNAME0",
					"Database error getting task by name", re);
		}
		return tsk;
	}
	

}
