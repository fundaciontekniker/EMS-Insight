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
import es.tekniker.eefrmwrk.database.sql.model.Stage;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadataStage;

/**
 * @author agarcia
 *
 */
public class OpcNodeManager {

	private static final Log log = LogFactory.getLog(OpcNodeManager.class);
	private static final String tableName = "OpcNode";
	
	public static long save(OpcNode instance) throws BaseException{
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
	
	public static long save(OpcNode instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("OpcNodeManagerSAVE0", "Database error saving opcNode", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static OpcNode find(long id) throws BaseException {
		Session session = null;
		OpcNode instance = null;
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
	
	public static OpcNode find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		OpcNode instance = null;
		try {
			instance = (OpcNode)session.get(OpcNode.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("find " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcNodeManagerFINDBYID0", "Database error getting opcNode", re);
		}
		return instance;
	}

	/*public static OpcNode findByName(String name)throws BaseException{
		OpcNode node =null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			node = findByName(name, session);
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
		return node;
	}

	public static OpcNode findByName(String name, Session session)throws BaseException{
		log.debug("getting " + tableName + " instance with name: " + name);
		List<OpcNode> lista = null;
		OpcNode result = null;
		try {
			Criteria criteria = session.createCriteria(OpcNode.class);
			criteria.add(Restrictions.eq("opcNodename", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 OpcNode with same name found");
				throw new BaseException("OpcNodeFINDBYNAME0", "Database error getting OpcNodes by name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcNodeFINDBYNAME1", "Database error getting OpcNodes by name", re);
		}
		return result;	
	}*/

	
	public static List<OpcNode> findByServer(long server) throws BaseException {
		Session session = null;
		List<OpcNode> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByServer(server, session);
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
	
	public static List<OpcNode> findByServer(long server, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with server : " + server);
		List<OpcNode> lista = null;
		try {
			Criteria criteria = session.createCriteria(OpcNode.class);
			criteria.add(Restrictions.eq("opcServerId", server));
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
			throw new BaseException("OpcNodeManagerFINDBYSERVER0", "Database error getting opcNodes by server", re);
		}
		return lista;
	}
	
	
	public static List<OpcNode> findAll() throws BaseException {
		Session session = null;
		List<OpcNode> list = null;
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

	public static List<OpcNode> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<OpcNode> lista = new ArrayList<OpcNode>();
		try {
			Criteria criteria = session.createCriteria(OpcNode.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("opcNodeId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcNodeManagerFINDALL0", "Database error getting all opcNodes", re);
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
			OpcNode opcNode = (OpcNode)session.load(OpcNode.class, id);
			log.debug("OpcNode loaded id: " + id);
			if(opcNode != null){
				opcNode.setActiv(0);
				session.update(opcNode);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("OpcNodeManagerDELETE0", "Database error deleting opcNode", re);
		}
	}
	

	public static OpcNode update(OpcNode instance) throws BaseException{
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
	
	public static OpcNode update(OpcNode instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getOpcNodeId();
			OpcNode opcNode = (OpcNode) session.load(OpcNode.class,id);
			log.debug("OpcNode to be updated: " + id);
			if (opcNode.getActiv()==1) {
				opcNode.setOpcServerId(instance.getOpcServerId());
				opcNode.setOpcNodename(instance.getOpcNodename());
				opcNode.setOpcNmspc(instance.getOpcNmspc());
				session.update(instance);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("OpcNodeManagerUPDATE1","OpcNode not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("OpcNodeManagerUPDATE0", "Database error updating opcNode", re);
		}
		return instance;
	}

	
	
	public static OpcNode findInServer(long opcServerId, String name,String namespace )throws BaseException{
		OpcNode node =null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			node = findInServer(opcServerId,name,namespace,session);
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
		return node;
	}

	public static OpcNode findInServer(long opcServerId,String name,String namespace, Session session)throws BaseException{
		log.debug("getting " + tableName + " instance with name: " + name);
		List<OpcNode> lista = null;
		OpcNode result = null;
		try {
			Criteria criteria = session.createCriteria(OpcNode.class);
			criteria.add(Restrictions.eq("opcNodename", name));
			criteria.add(Restrictions.eq("opcServerId", opcServerId));
			criteria.add(Restrictions.eq("opcNmspc",namespace));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 opcNode with same name found in server");
				throw new BaseException("OpcNodeFINDINSERVER0", "More than 1 opcNode with same name found in server");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("OpcNodeFINDBYNAME1", "Database error getting OpcNodes by name", re);
		}
		return result;	
	}
	

}
