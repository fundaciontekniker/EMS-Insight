/**
 * 
 */
package es.tekniker.eefrmwrk.database.sql.manage;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.model.UserLogin;

/**
 * @author fdiez
 *
 */
public class UserLoginManager {

	private static final Log log = LogFactory.getLog(UserLoginManager.class);
	private static final String tableName = "USERLOGIN";
	
	public static long save(UserLogin instance) throws BaseException{
		Session session = null;
		long instanceId = -1;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instanceId = save(instance, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException hE){
			session.getTransaction().rollback();					
			log.error("Hibernate Exception adding user", hE);
			throw new BaseException("UserLoginManager_SAVE10", hE.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return instanceId;
	}
	
	public static long save(UserLogin instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("UserLoginManager_SAVE0", "Database error saving userlogin", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static UserLogin find(long id) throws BaseException {
		Session session = null;
		UserLogin instance = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = find(id, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("findById " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_FIND10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return instance;
	}
	
	public static UserLogin find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		UserLogin instance = null;
		try {
			instance = (UserLogin)session.get(UserLogin.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("UserLoginManager_FIND0", "Database error getting userlogin", re);
		}
		return instance;
	}
	
	public static UserLogin findByLogin(String login) throws BaseException {
		Session session = null;
		UserLogin userLogin = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			userLogin = findByLogin(login, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("findByLogin " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_FINDBYLOGIN10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return userLogin;
	}
	
	public static UserLogin findByLogin(String login, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with login : " + login);
		List<UserLogin> lista = null;
		UserLogin result = null;
		try {
			Criteria criteria = session.createCriteria(UserLogin.class);
			criteria.add(Restrictions.eq("userLogin", login));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 login found");
				throw new BaseException("UserLoginManager_FINDBYLOGIN0", "More than 1 user with this login");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("UserLoginManager_FINDBYLOGIN1", "Database error getting userlogins by login", re);
		}
		return result;
	}
	
	public static UserLogin findByName(String name) throws BaseException {
		Session session = null;
		UserLogin user = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			user = findByName(name, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("findByName " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_FINDBYNAME10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return user;
	}
	
	public static UserLogin findByName(String name, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<UserLogin> lista = null;
		UserLogin result=null;
		try {
			Criteria criteria = session.createCriteria(UserLogin.class);
			criteria.add(Restrictions.eq("userName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			}else if (lista.size() > 1){
				log.debug("More than 1 login found");
				throw new BaseException("UserLoginManager_FINDBYNAME1", "More than 1 user with this name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("UserLoginManager_FINDBYNAME0", "Database error getting userlogins by name", re);
		}
		return result;
	}
	
	public static List<UserLogin> findByRole(String role) throws BaseException {
		Session session = null;
		List<UserLogin> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByRole(role, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("findByRole " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_FINDBYROLE10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return lista;
	}
	
	public static List<UserLogin> findByRole(String role, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with role : " + role);
		List<UserLogin> lista = null;
		try {
			Criteria criteria = session.createCriteria(UserLogin.class);
			criteria.add(Restrictions.eq("userRole", role));
			criteria.add(Restrictions.eq("activ", new Long(1)));
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
			throw new BaseException("UserLoginManager_FINDBYROLE0", "Database error getting userlogins by ROLE", re);
		}
		return lista;
	}
	
	public static List<UserLogin> findAll() throws BaseException {
		Session session = null;
		List<UserLogin> list = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findAll(session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("findAll " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_FINDALL10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return list;
	}
	
	public static List<UserLogin> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<UserLogin> lista = new ArrayList<UserLogin>();
		try {
			Criteria criteria = session.createCriteria(UserLogin.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("userId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("UserLoginManager_FINDALL0", "Database error getting all userlogins", re);
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
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("delete " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_DELETE10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
	} 
	
	public static void delete(long id, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			UserLogin UserLogin = (UserLogin)session.load(UserLogin.class, id);
			log.debug("UserLogin loaded id: " + id);
			if(UserLogin != null){
				UserLogin.setActiv(0);
				session.update(UserLogin);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("UserLoginManager_DELETE0", "Database error deleting userlogin", re);
		}
	}
		
	public static void deleteByLogin(String login) throws BaseException{
		log.debug("deleting " + tableName + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			deleteByLogin(login, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("deleteByLogin " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_DELETEBYLOGIN10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
	}
	
	public static void deleteByLogin(String login, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			UserLogin userLogin = findByLogin(login, session);
			if (userLogin != null){
				session.update(userLogin);
				log.debug(tableName + " instance deleted");
				ChangeLogManager.save(tableName, userLogin.getUserId(), ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
			else {
				throw new BaseException("UserLoginManager_DELETEBYLOGIN1", "Login not found " + login);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("UserLoginManager_DELETEBYLOGIN0", "Database error deleting userlogins by login", re);
		}
	}
	
	public static void deleteByName(String name) throws BaseException{
		log.debug("deleting " + tableName + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			deleteByName(name, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException e){
			session.getTransaction().rollback();					
			log.error("deleteByName " + tableName + " failed", e);
			throw new BaseException("UserLoginManager_DELETEBYNAME10", e.getCause().getLocalizedMessage());
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
	}
	
	public static void deleteByName(String login, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			UserLogin user = findByName(login, session);
			if (user==null){
				log.error("Replicated name");
				throw new BaseException("UserLoginManager_DELETEBYNAME1", "User not found");
			}
			else
			{
				user.setActiv(0);
				session.update(user);
				log.debug(tableName + " instance deleted");
				ChangeLogManager.save(tableName, user.getUserId(), ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("UserLoginManager_DELETEBYNAME0", "Database error deleting userlogins by name", re);
		}
	}
	
	public static UserLogin update(UserLogin instance) throws BaseException{
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = update(instance, session);
			session.getTransaction().commit();
		}
		catch (BaseException e){
			session.getTransaction().rollback();					
			throw e;
		}
		catch (HibernateException hE){
			log.error("Hibernate Exception updating user", hE);
			session.getTransaction().rollback();					
			Throwable ex = hE.getCause();
			BatchUpdateException batchE = null;
			try {
				if (Class.forName("java.sql.BatchUpdateException").isInstance(ex)){
					batchE = (BatchUpdateException) ex;
					throw new BaseException("UserLoginManager_UPDATE10", batchE.getNextException().getLocalizedMessage());				
				}
				else {
					throw new BaseException("UserLoginManager_UPDATE11", hE.getCause().getLocalizedMessage());
				}
			}
			catch(ClassNotFoundException ce){
				throw new BaseException("UserLoginManager_UPDATE11", hE.getCause().getLocalizedMessage());				
			}
		}
		finally {
			log.debug("Closing session");
			HibernateUtil.closeSession();	
		}
		return instance;
	}
	
	public static UserLogin update(UserLogin instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getUserId();
			UserLogin userLogin = (UserLogin)session.load(UserLogin.class, id);
			log.debug("UserLogin to be updated: " + id);
			if (userLogin.getActiv()==1) {
				userLogin.setUserLogin(instance.getUserLogin());
				userLogin.setUserName(instance.getUserName());
				userLogin.setUserPassword(instance.getUserPassword());
				userLogin.setUserRole(instance.getUserRole());
				session.update(userLogin);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("UserLoginManager_UPDATE1","UserLogin not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("UserLoginManager_UPDATE0", "Database error updating userlogin", re);
		}
		
		return instance;
	}

}
