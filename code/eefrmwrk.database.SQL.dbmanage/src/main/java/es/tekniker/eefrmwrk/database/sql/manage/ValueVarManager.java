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
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;

/**
 * @author agarcia
 *
 */
public class ValueVarManager {

	private static final Log log = LogFactory.getLog(ValueVarManager.class);
	private static final String tableName = "ValueVar";
	
	public static long save(ValueVar instance) throws BaseException{
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
	
	public static long save(ValueVar instance, Session session) throws BaseException{
		//log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("ValueVarManager_SAVE0", "Database error saving valueVar", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static ValueVar find(long id) throws BaseException {
		Session session = null;
		ValueVar instance = null;
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
	
	public static ValueVar find(long id, Session session) throws BaseException {
		//log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		ValueVar instance = null;
		try {
			instance = (ValueVar)session.get(ValueVar.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("ValueVarManager_FINDBYID0", "Database error getting valueVar", re);
		}
		return instance;
	}

	public static List<ValueVar> findAll() throws BaseException {
		Session session = null;
		List<ValueVar> list = null;
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
	
	public static List<ValueVar> findAll(Session session) throws BaseException {
		//log.debug("getting " + tableName + " instances ");
		List<ValueVar> lista = new ArrayList<ValueVar>();
		try {
			Criteria criteria = session.createCriteria(ValueVar.class);
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
			throw new BaseException("ValueVarManager_FINDALL0", "Database error getting all valueVars", re);
		}
		return lista;
	}
	
	public static List<ValueVar> findByVarMetadata(long vmd) throws BaseException {
		return DBInstanceManager.find(ValueVar.class, "vrVmd", vmd);
	}
	
	public static List<ValueVar> findByVarMetadata(long vmd, Session session) throws BaseException {
		return DBInstanceManager.find(ValueVar.class, "vrVmd", vmd, session);
	}
	
	public static List<ValueVar> getValues(long vmdId, Long start, Long end,Integer maxResults, Boolean ord) throws BaseException {
		Session session = null;
		List<ValueVar> list = null;
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
	
	public static List<ValueVar> getValues(long vmdId, Long start, Long end,Integer maxResults, Boolean order,Session session) throws BaseException{
		//log.debug("getting " + tableName + "s");
		List<ValueVar> lista = new ArrayList<ValueVar>();
		try {
			Criteria criteria = session.createCriteria(ValueVar.class);
			criteria.add(Restrictions.eq("vrVmd", vmdId));
			criteria.add(Restrictions.between("vrTimestamp",start,end));
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
			throw new BaseException("ValueVarManager_GETVALUES0", "Database error getting all varMetadatas", re);
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
			ValueVar valueVar = (ValueVar)session.load(ValueVar.class, id);
			log.debug("ValueVar loaded id: " + id);
			if(valueVar != null){
				valueVar.setActiv(0);
				session.update(valueVar);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("ValueVarManager_DELETE0", "Database error deleting valueVar", re);
		}
	}
	

	public static ValueVar update(ValueVar instance) throws BaseException{
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
	
	public static ValueVar update(ValueVar instance, Session session) throws BaseException{
		//log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getVrId();
			ValueVar valueVar = (ValueVar) session.load(ValueVar.class,id);
			log.debug("ValueVar to be updated: " + id);
			if (valueVar.getActiv()==1) {
				valueVar.setVrVmd(instance.getVrVmd());
				valueVar.setVrValue(instance.getVrValue());
				valueVar.setVrTimestamp(instance.getVrTimestamp());
				valueVar.setVrQuality(instance.getVrQuality());
				session.update(valueVar);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("ValueVarManager_UPDATE1","ValueVar not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("ValueVarManager_UPDATE0", "Database error updating valueVar", re);
		}
		return instance;
	}

	public static void deleteByVarMetadata(long vmdId,long minDate,long maxDate) throws BaseException{
			//log.debug("deleting " + tableName + "s");
			Session session = null;
			try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();
				deleteByVarMetadata(vmdId,minDate,maxDate, session);
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
		
		public static void deleteByVarMetadata(long vmdId,long minDate,long maxDate, Session session) throws BaseException{
			//log.debug("deleting " + tableName + " by varmetadata");
			try {
				List<ValueVar> lista=getValues(vmdId, minDate, maxDate, Integer.MAX_VALUE, true, session);
				for(ValueVar vr: lista){
					vr.setActiv(0);
					session.update(vr);}
				log.debug(tableName + " "+ lista.size()+" instance(s) deleted");
				ChangeLogManager.save(tableName, vmdId,ChangeLogManager.ACTIONDELETE, null, null, null,session);
				
			} catch (RuntimeException re) {
				log.error("delete " + tableName + " failed", re);
				throw new BaseException("ValueVarManager_DELETEBYVMD0", "Database error deleting valueVar bt vmd", re);
			}
		}
	
}
