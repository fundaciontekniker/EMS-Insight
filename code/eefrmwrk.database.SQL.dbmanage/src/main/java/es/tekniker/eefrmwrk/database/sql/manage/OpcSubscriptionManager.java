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
import es.tekniker.eefrmwrk.database.sql.model.OpcNode;
import es.tekniker.eefrmwrk.database.sql.model.OpcSubscription;

public class OpcSubscriptionManager {

	private static final Log log = LogFactory.getLog(OpcSubscriptionManager.class);
	private static final String tableName = "OpcSubscription";
	
	public static long save(OpcSubscription instance) throws BaseException{
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
	
	public static long save(OpcSubscription instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("OpcSubscriptionManager_SAVE0", "Database error saving OpcSubscription", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static OpcSubscription find(long id) throws BaseException {
		Session session = null;
		OpcSubscription instance = null;
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
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static OpcSubscription find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		OpcSubscription instance = null;
		try {
			instance = (OpcSubscription)session.get(OpcSubscription.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("find " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcSubscriptionManager_FINDBYID0", "Database error getting OpcSubscription", re);
		}
		return instance;
	}

	public static OpcSubscription findByVarMetaData(long vmd)throws BaseException{
		OpcSubscription sub =null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			sub = findByVarMetaData(vmd, session);
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
		return sub;
	}

	public static OpcSubscription findByVarMetaData(long vmd, Session session)throws BaseException{
		log.debug("getting " + tableName + " instance with varmetadata : " + vmd);
		List<OpcSubscription> lista = null;
		OpcSubscription result = null;
		try {
			Criteria criteria = session.createCriteria(OpcSubscription.class);
			criteria.add(Restrictions.eq("opcVmd", vmd));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 OpcSubscription with same VarMetadata found");
				throw new BaseException("OpcSubscriptionManager_FINDBYVARMETADATA0", "More than 1 OpcSubscription with same VarMetadata found");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcSubscriptionManager_FINDBYVARMETADATA1", "Database error getting OpcSubscription by VarMetadata", re);
		}
		return result;	
	}

	public static OpcSubscription findByNode(long nodeId)throws BaseException{
		OpcSubscription sub =null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			sub = findByNode(nodeId, session);
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
		return sub;
	}

	public static OpcSubscription findByNode(long nodeId, Session session)throws BaseException{
		log.debug("getting " + tableName + " instance with nodeId: " + nodeId);
		List<OpcSubscription> lista = null;
		OpcSubscription result = null;
		try {
			Criteria criteria = session.createCriteria(OpcSubscription.class);
			criteria.add(Restrictions.eq("opcNodeId", nodeId));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 OpcSubscription with same nodeId found");
				throw new BaseException("OpcSubscriptionManager_FINDBYNODE0", "More than 1 OpcSubscription with same nodeId found");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcSubscriptionManager_FINDBYNODE1", "Database error getting OpcSubscription by node", re);
		}
		return result;	
	}

		
	public static List<OpcSubscription> findAll() throws BaseException {
		Session session = null;
		List<OpcSubscription> list = null;
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

	public static List<OpcSubscription> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<OpcSubscription> lista = new ArrayList<OpcSubscription>();
		try {
			Criteria criteria = session.createCriteria(OpcSubscription.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("opcSubId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcSubscriptionManager_FINDALL0", "Database error getting all OpcSubscriptions", re);
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
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();		
			throw e;
		}
	}
	
	public static void delete(long id, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			OpcSubscription opcNode = (OpcSubscription)session.load(OpcSubscription.class, id);
			log.debug("OpcSubscription loaded id: " + id);
			if(opcNode != null){
				opcNode.setActiv(0);
				session.update(opcNode);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("OpcSubscriptionManager_DELETE0", "Database error deleting OpcSubscription", re);
		}
	}
	

	public static OpcSubscription update(OpcSubscription instance) throws BaseException{
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
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();	
			throw e;
		}
		return instance;
	}
	
	public static OpcSubscription update(OpcSubscription instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getOpcNodeId();
			OpcSubscription opcSub = (OpcSubscription) session.load(OpcSubscription.class,id);
			log.debug("OpcSubscription to be updated: " + id);
			if (opcSub.getActiv()==1) {
				opcSub.setOpcNodeId(instance.getOpcNodeId());
				opcSub.setOpcVmd(instance.getOpcVmd());
				opcSub.setOpcDatachange(instance.getOpcDatachange());
				opcSub.setOpcPubint(instance.getOpcPubint());
				session.update(instance);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("OpcSubscriptionManager_UPDATE1","OpcSubscription not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("OpcSubscriptionManager_UPDATE0", "Database error updating OpcSubscription", re);
		}
		return instance;
	}
	
	
}
