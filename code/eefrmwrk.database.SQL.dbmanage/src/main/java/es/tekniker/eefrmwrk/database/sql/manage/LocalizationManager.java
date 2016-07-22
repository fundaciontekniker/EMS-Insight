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
import es.tekniker.eefrmwrk.database.sql.model.Localization;

/**
 * @author agarcia
 *
 */
public class LocalizationManager {

	private static final Log log = LogFactory.getLog(LocalizationManager.class);
	private static final String tableName = "Localization";
	
	public static long save(Localization instance) throws BaseException{
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
	
	public static long save(Localization instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("LocalizationManagerSAVE0", "Database error saving localization", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static Localization find(long id) throws BaseException {
		Session session = null;
		Localization instance = null;
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
	
	public static Localization find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		Localization instance = null;
		try {
			instance = (Localization)session.get(Localization.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("LocalizationManagerFINDBYID0", "Database error getting localization", re);
		}
		return instance;
	}

	public static Localization findByName(String name) throws BaseException {
		Session session = null;
		Localization loc = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			loc = findByName(name, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return loc;
	}

	public static Localization findByName(String name, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<Localization> lista = null;
		Localization loc=null;
		try {
			Criteria criteria = session.createCriteria(Localization.class);
			criteria.add(Restrictions.eq("locName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 localization with this name");
				throw new BaseException("LocalizationManagerFINDBYNAME1", "More than 1 localization with this name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				loc=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("LocalizationManagerFINDBYNAME0",
					"Database error getting localization by name", re);
		}
		return loc;
	}

	public static List<Localization> findAll() throws BaseException {
		Session session = null;
		List<Localization> list = null;
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
	
	public static List<Localization> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<Localization> lista = new ArrayList<Localization>();
		try {
			Criteria criteria = session.createCriteria(Localization.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("locId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("LocalizationManagerFINDALL0", "Database error getting all localizations", re);
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
			Localization localization = (Localization)session.load(Localization.class, id);
			log.debug("Localization loaded id: " + id);
			if(localization != null){
				localization.setActiv(0);
				session.update(localization);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("LocalizationManagerDELETE0", "Database error deleting localization", re);
		}
	}
	

	public static Localization update(Localization instance) throws BaseException{
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
	
	public static Localization update(Localization instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getLocId();
			Localization localization = (Localization) session.load(Localization.class,id);
			log.debug("Localization to be updated: " + id);
			if (localization.getActiv()==1) {
				localization.setLocName(instance.getLocName());
				localization.setLocAdress(instance.getLocAdress());
				localization.setLocZip(instance.getLocZip());
				localization.setLocCity(instance.getLocCity());
				localization.setLocCountry(instance.getLocCountry());
				localization.setLocGridlocation(instance.getLocGridlocation());
				session.update(localization);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("LocalizationManagerUPDATE1","Localization not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("LocalizationManagerUPDATE0", "Database error updating localization", re);
		}
		return instance;
	}
}
