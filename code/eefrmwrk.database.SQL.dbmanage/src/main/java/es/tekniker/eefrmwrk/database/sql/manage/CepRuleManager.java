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
import es.tekniker.eefrmwrk.database.sql.model.CepRule;

/**
 * @author agarcia
 *
 */
public class CepRuleManager {

	private static final Log log = LogFactory.getLog(CepRuleManager.class);
	private static final String tableName = "CepRule";
	
	public static long save(CepRule instance) throws BaseException{
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
	
	public static long save(CepRule instance, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("CepRuleManager_SAVE0", "Database error saving cepRule", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static CepRule find(long id) throws BaseException {
		Session session = null;
		CepRule instance = null;
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
	
	public static CepRule find(long id, Session session) throws BaseException {
		///log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		CepRule instance = null;
		try {
			instance = (CepRule)session.get(CepRule.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("CepRuleManager_FINDBYID0", "Database error getting cepRule", re);
		}
		return instance;
	}
	
	public static List<CepRule> findAll() throws BaseException {
		Session session = null;
		List<CepRule> list = null;
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
	
	public static List<CepRule> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<CepRule> lista = new ArrayList<CepRule>();
		try {
			Criteria criteria = session.createCriteria(CepRule.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("cepId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("CepRuleManager_FINDALL0", "Database error getting all cepRules", re);
		}
		return lista;
	}
	
	public static CepRule findByName(String name) throws BaseException {
		Session session = null;
		CepRule cR = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			cR = findByName(name, session);
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
		return cR;
	}

	public static CepRule findByName(String name, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with name : " + name);
		CepRule cR= null;
		try {
			Criteria criteria = session.createCriteria(CepRule.class);
			criteria.add(Restrictions.eq("cepName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			List<CepRule >lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			}else if (lista.size() > 1) {
				log.debug("More than 1 cepRule with this name");
				throw new BaseException("CepRuleManager_FINDBYNAME1", "More than 1 cepRule with this name");
			}else {
				log.debug("get " + tableName + " successful, instance found");
				cR=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("CepRuleManager_FINDBYNAME0", "Database error getting cepRule by name", re);
		}
		return cR;
	}

	public static List<CepRule> findByCepEngine(long engine) throws BaseException {
		Session session = null;
		List<CepRule> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByCepEngine(engine, session);
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

	public static List<CepRule> findByCepEngine(long engine, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with cepEngine: " + engine);
		List<CepRule> lista = null;
		try {
			Criteria criteria = session.createCriteria(CepRule.class);
			criteria.add(Restrictions.eq("cepEngine", engine));
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
			throw new BaseException("CepRuleManager_FINDBYENGINE0", "Database error getting cepRules by engine", re);
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
			CepRule cepRule = (CepRule)session.load(CepRule.class, id);
			//log.debug("cepRule loaded id: " + id);
			if(cepRule != null){
				cepRule.setActiv(0);
				session.update(cepRule);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("CepRuleManager_DELETE0", "Database error deleting cepRule", re);
		}
	}
	
	
	public static CepRule update(CepRule instance) throws BaseException{
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
	
	public static CepRule update(CepRule instance, Session session) throws BaseException{
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getCepId();
			CepRule cepRule = (CepRule)session.load(CepRule.class, id);
			//log.debug("CEP rule to be updated: " + id);
			if (cepRule.getActiv()==1) {
				cepRule.setCepName(instance.getCepName());
				cepRule.setCepEpl(instance.getCepEpl());
				cepRule.setCepEngine(instance.getCepEngine());
				cepRule.setCepListener(instance.getCepListener());
				cepRule.setCepMessage(instance.getCepMessage());
				cepRule.setCepSeverity(instance.getCepSeverity());
				session.update(cepRule);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("CepRuleManager_UPDATE1","Cep Rule not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("CepRuleManager_UPDATE0", "Database error updating CEP rule", re);
		}
		return instance;
	}

}
