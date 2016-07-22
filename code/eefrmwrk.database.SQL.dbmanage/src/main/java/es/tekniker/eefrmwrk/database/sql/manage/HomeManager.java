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
import es.tekniker.eefrmwrk.database.sql.model.DeviceVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.Home;
import es.tekniker.eefrmwrk.database.sql.model.HomeDevice;
import es.tekniker.eefrmwrk.database.sql.model.HomeVarMetadata;

/**
 * @author agarcia
 *
 */
public class HomeManager {

	private static final Log log = LogFactory.getLog(HomeManager.class);
	private static final String tableName = "Home";
	
	public static long save(Home instance) throws BaseException{
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
	
	public static long save(Home instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("HomeManagerSAVE0", "Database error saving home", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static Home find(long id) throws BaseException {
		Session session = null;
		Home instance = null;
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
	
	public static Home find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		Home instance = null;
		try {
			instance = (Home)session.get(Home.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("HomeManagerFINDBYID0", "Database error getting home", re);
		}
		return instance;
	}

	public static Home findByName(String name) throws BaseException {
		Session session = null;
		Home h = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			h = findByName(name, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return h;
	}
	public static Home findByName(String name, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<Home> lista = null;
		Home h=null;
		try {
			Criteria criteria = session.createCriteria(Home.class);
			criteria.add(Restrictions.eq("hiName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 home with this name");
				throw new BaseException("HomeManagerFINDBYNAME1", "More than 1 home with this name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				h=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("HomeManagerFINDBYNAME0",
					"Database error getting home by name", re);
		}
		return h;
	}
	
	public static List<Home> findAll() throws BaseException {
		Session session = null;
		List<Home> list = null;
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
	
	public static List<Home> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<Home> lista = new ArrayList<Home>();
		try {
			Criteria criteria = session.createCriteria(Home.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("hiId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("HomeManagerFINDALL0", "Database error getting all homes", re);
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
			Home home = (Home)session.load(Home.class, id);
			log.debug("Home loaded id: " + id);
			if(home != null){
				home.setActiv(0);
				session.update(home);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
			DBInstanceManager.delete(HomeDevice.class,"hiId", id,session);//Borrar HOME/DEVICE
			DBInstanceManager.delete(HomeVarMetadata.class,"hiId", id,session);//Borrar HOME/VARMETADATA

		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("HomeManagerDELETE0", "Database error deleting home", re);
		}
	}
	

	public static Home update(Home instance) throws BaseException{
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
	
	public static Home update(Home instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getHiId();
			Home home = (Home) session.load(Home.class,id);
			log.debug("Home to be updated: " + id);
			if (home.getActiv()==1) {
				home.setHiName(instance.getHiName());
				home.setHiLocalization(instance.getHiLocalization());
				home.setHiEndpoint(instance.getHiEndpoint());
				session.update(home);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("HomeManagerUPDATE1","Home not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("HomeManagerUPDATE0", "Database error updating home", re);
		}
		return instance;
	}

}
