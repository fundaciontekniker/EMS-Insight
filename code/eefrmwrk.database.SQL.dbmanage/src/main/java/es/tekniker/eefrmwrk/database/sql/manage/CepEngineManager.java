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
import es.tekniker.eefrmwrk.database.sql.model.CepEngine;
import es.tekniker.eefrmwrk.database.sql.model.CepRule;
import es.tekniker.eefrmwrk.database.sql.model.UserLogin;

public class CepEngineManager {
	private static final Log log = LogFactory.getLog(CepEngineManager.class);
	private static final String tableName = "CepEngine";
	
	public static long save(CepEngine instance) throws BaseException{
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
	
	public static long save(CepEngine instance, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("CepEngineManagerSAVE0", "Database error saving cepEngine", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static CepEngine find(long id) throws BaseException {
		Session session = null;
		CepEngine instance = null;
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
			//log.error("findById " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static CepEngine find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		CepEngine instance = null;
		try {
			instance = (CepEngine)session.get(CepEngine.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("CepEngineManagerFINDBYID0", "Database error getting cepEngine", re);
		}
		return instance;
	}
	
	public static List<CepEngine> findAll() throws BaseException {
		Session session = null;
		List<CepEngine> list = null;
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
	
	public static List<CepEngine> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<CepEngine> lista = new ArrayList<CepEngine>();
		try {
			Criteria criteria = session.createCriteria(CepEngine.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("cengId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("CepEngineManagerFINDALL0", "Database error getting all cepEngines", re);
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
		//	log.error("delete " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();		
			throw e;
		}
	}
	
	public static void delete(long id, Session session) throws BaseException{
		//log.debug("deleting " + tableName + " instance");
		try {
			CepEngine cepEngine = (CepEngine)session.load(CepEngine.class, id);
			//log.debug("cepEngine loaded id: " + id);
			if(cepEngine != null){
				cepEngine.setActiv(0);
				session.update(cepEngine);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("CepEngineManagerDELETE0", "Database error deleting cepEngine", re);
		}
	}
	
	
	public static CepEngine update(CepEngine instance) throws BaseException{
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
	
	public static CepEngine update(CepEngine instance, Session session) throws BaseException{
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getId();
			CepEngine cepEngine = (CepEngine) session.load(CepEngine.class,id);
			//log.debug("cepEngine to be updated: " + id);
			if (cepEngine.getActiv()==1) {
				cepEngine.setCengDesc(instance.getCengDesc());
				cepEngine.setCengUrl(instance.getCengUrl());
				session.update(cepEngine);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("CepEngineManagerUPDATE1","CepEngine not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("CepEngineManagerUPDATE0", "Database error updating cepEngine", re);
		}
		return instance;
	}

	public static CepEngine findByUrl(String url) throws BaseException {
		Session session = null;
		CepEngine ceng = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			ceng = findByUrl(url, session);
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
		return ceng;
	}

	public static CepEngine findByUrl(String url, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with url : " + url);
		List<CepEngine> lista = null;
		CepEngine result = null;
		try {
			Criteria criteria = session.createCriteria(CepEngine.class);
			criteria.add(Restrictions.eq("cengUrl", url));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 login found");
				throw new BaseException(tableName+"ManagerFINDBYURL0", "More than 1 cepEngines with this url");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException(tableName+"ManagerFINDBYURL1", "Database error getting cepEngine by url", re);
		}
		return result;
	}
	
}
