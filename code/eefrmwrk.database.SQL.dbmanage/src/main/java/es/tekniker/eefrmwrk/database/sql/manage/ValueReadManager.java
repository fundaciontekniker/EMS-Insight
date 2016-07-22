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
import es.tekniker.eefrmwrk.database.sql.model.ValueRead;

/**
 * @author agarcia
 *
 */
public class ValueReadManager {

	private static final Log log = LogFactory.getLog(ValueReadManager.class);
	private static final String tableName = "ValueRead";
	
	public static long save(ValueRead instance) throws BaseException{
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
	
	public static long save(ValueRead instance, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("ValueReadManager_SAVE0", "Database error saving valueRead", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static ValueRead find(long id) throws BaseException {
		Session session = null;
		ValueRead instance = null;
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
	
	public static ValueRead find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		ValueRead instance = null;
		try {
			instance = (ValueRead)session.get(ValueRead.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ValueReadManager_FINDBYID0", "Database error getting valueRead", re);
		}
		return instance;
	}

	public static List<ValueRead> findAll() throws BaseException {
		Session session = null;
		List<ValueRead> list = null;
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
	
	public static List<ValueRead> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<ValueRead> lista = new ArrayList<ValueRead>();
		try {
			Criteria criteria = session.createCriteria(ValueRead.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("vrId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ValueReadManager_FINDALL0", "Database error getting all valueReads", re);
		}
		return lista;
	}
	
	public static List<ValueRead> findByVarMetadata(long vmd) throws BaseException {
		return DBInstanceManager.find(ValueRead.class, "vrVmd", vmd);
	}
	
	public static List<ValueRead> findByVarMetadata(long vmd, Session session) throws BaseException {
		return DBInstanceManager.find(ValueRead.class, "vrVmd", vmd, session);
	}
	
	public static List<ValueRead> getValues(long vmdId, Long start, Long end,Integer maxResults, Boolean ord) throws BaseException {
		Session session = null;
		List<ValueRead> list = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = getValues(vmdId,start,end,maxResults,ord,session);
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
	
	public static List<ValueRead> getValues(long vmdId, Long start, Long end,Integer maxResults, Boolean order,Session session) throws BaseException{
		//log.debug("getting " + tableName + "s");
		List<ValueRead> lista = new ArrayList<ValueRead>();
		try {
			Criteria criteria = session.createCriteria(ValueRead.class);
			criteria.add(Restrictions.eq("vrVmd", vmdId));
			criteria.add(Restrictions.between("vrTimestamp", new Date(start), new Date(end)));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			
			if (order)
				criteria.addOrder(Order.asc("vrTimestamp"));
			else
				criteria.addOrder(Order.desc("vrTimestamp"));
			criteria.setMaxResults(maxResults);
			lista = criteria.list();
			
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} 
			else if (lista.size() == maxResults){
				log.debug("get " + tableName + " successful, instance found (MAX_RESULTS)");
				//TODO
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ValueReadManager_GETVALUES0", "Database error getting all varMetadatas", re);
		}
		return lista;
	}
	
	public static void delete(long id) throws BaseException{
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
		log.debug("deleting " + tableName + " instance");
		try {
			ValueRead valueRead = (ValueRead)session.load(ValueRead.class, id);
			log.debug("ValueRead loaded id: " + id);
			if(valueRead != null){
				valueRead.setActiv(0);
				session.update(valueRead);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("ValueReadManager_DELETE0", "Database error deleting valueRead", re);
		}
	}
	

	public static ValueRead update(ValueRead instance) throws BaseException{
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
	
	public static ValueRead update(ValueRead instance, Session session) throws BaseException{
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getVrId();
			ValueRead valueRead = (ValueRead) session.load(ValueRead.class,id);
			log.debug("ValueRead to be updated: " + id);
			if (valueRead.getActiv()==1) {
				valueRead.setVrVmd(instance.getVrVmd());
				valueRead.setVrValue(instance.getVrValue());
				valueRead.setVrTimestamp(instance.getVrTimestamp());
				valueRead.setVrQuality(instance.getVrQuality());
				session.update(valueRead);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("ValueReadManager_UPDATE1","ValueRead not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("ValueReadManager_UPDATE0", "Database error updating valueRead", re);
		}
		return instance;
	}

	public static void deleteByVarMetadata(long vmdId) throws BaseException{
			//log.debug("deleting " + tableName + "s");
			Session session = null;
			try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();
				deleteByVarMetadata(vmdId, session);
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
		
		public static void deleteByVarMetadata(long vmdId, Session session) throws BaseException{
			//log.debug("deleting " + tableName + " by varmetadata");
			try {
				List<ValueRead> lista=findByVarMetadata(vmdId, session);
				for(ValueRead vr: lista){
					vr.setActiv(0);
					session.update(vr);}
				log.debug(tableName + " "+ lista.size()+" instance(s) deleted");
				ChangeLogManager.save(tableName, vmdId,ChangeLogManager.ACTIONDELETE, null, null, null,session);
				
			} catch (RuntimeException re) {
				log.error("delete " + tableName + " failed", re);
				throw new BaseException("ValueReadManager_DELETEBYVMD0", "Database error deleting valueRead bt vmd", re);
			}
		}
	
}
