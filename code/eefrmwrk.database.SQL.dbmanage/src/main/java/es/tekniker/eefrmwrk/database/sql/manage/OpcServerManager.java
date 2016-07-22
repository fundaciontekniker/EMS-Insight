package es.tekniker.eefrmwrk.database.sql.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.model.OpcNode;
import es.tekniker.eefrmwrk.database.sql.model.OpcServer;

/**
 * @author agarcia
 *
 */
public class OpcServerManager {

	private static final Log log = LogFactory.getLog(OpcServerManager.class);
	private static final String tableName = "OpcServer";
	
	public static long save(OpcServer instance) throws BaseException{
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
	
	public static long save(OpcServer instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("OpcServerManagerSAVE0", "Database error saving opcServer", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static OpcServer find(long id) throws BaseException {
		Session session = null;
		OpcServer instance = null;
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
	
	public static OpcServer find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		OpcServer instance = null;
		try {
			instance = (OpcServer)session.get(OpcServer.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			throw new BaseException("OpcServerManagerFINDBYID0", "Database error getting opcServer", re);
		}
		return instance;
	}

	public static List<OpcServer> findAll() throws BaseException {
		Session session = null;
		List<OpcServer> list = null;
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
	
	public static List<OpcServer> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<OpcServer> lista = new ArrayList<OpcServer>();
		try {
			Criteria criteria = session.createCriteria(OpcServer.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("opcServerId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcServerManagerFINDALL0", "Database error getting all opcServers", re);
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
			OpcServer opcServer = (OpcServer)session.load(OpcServer.class, id);
			log.debug("OpcServer loaded id: " + id);
			if(opcServer != null){
				opcServer.setActiv(0);
				session.update(opcServer);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("OpcServerManagerDELETE0", "Database error deleting opcServer", re);
		}
	}
	

	public static OpcServer update(OpcServer instance) throws BaseException{
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
	
	public static OpcServer update(OpcServer instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getOpcServerId();
			OpcServer opcServer = (OpcServer) session.load(OpcServer.class,id);
			log.debug("OpcServer to be updated: " + id);
			if (opcServer.getActiv()==1) {
				opcServer.setOpcDesc(opcServer.getOpcDesc());
				opcServer.setOpcUrl(instance.getOpcUrl());
				session.update(opcServer);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("OpcServerManagerUPDATE1","OpcServer not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("OpcServerManagerUPDATE0", "Database error updating opcServer", re);
		}
		return instance;
	}
	//--------------------------------------------------
	
		public static List<OpcNode> getNodes(long serv) throws BaseException {
			return OpcNodeManager.findByServer(serv);
		}
		
		public static List<OpcNode> getNodes(long serv, Session session) throws BaseException {
			return OpcNodeManager.findByServer(serv,session);
		}

		public static OpcServer findByUrl(String url)throws BaseException{
			OpcServer serv =null;
			Session session = null;
			try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();
				serv = findByUrl(url, session);
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
			return serv;
		}

		public static OpcServer findByUrl(String url, Session session)throws BaseException{
			log.debug("getting " + tableName + " instance with url: " + url);
			List<OpcServer> lista = null;
			OpcServer result = null;
			try {
				Criteria criteria = session.createCriteria(OpcServer.class);
				criteria.add(Restrictions.eq("opcUrl", url));
				criteria.add(Restrictions.eq("activ", new Long(1)));
				lista = criteria.list();
				if (lista.size() == 0) {
					log.debug("get " + tableName + " successful, no instance found");
				} else if (lista.size() > 1){
					log.debug("More than 1 OpcServer with same name found");
					throw new BaseException("OpcServerFINDBYURL0", "More than 1 OpcServer with same name found");
				}
				else {
					log.debug("get " + tableName + " successful, instance found");
					result = lista.get(0);
				}
			} catch (RuntimeException re) {
				log.error("get " + tableName + " failed", re);
				throw new BaseException("OpcServerFINDBYURL0", "Database error getting OpcServer by url", re);
			}
			return result;	
		}
		
}
