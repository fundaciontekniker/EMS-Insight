package es.tekniker.eefrmwrk.database.sql.manage;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.model.DBInstance;

public class DBInstanceManager {
private static final Log log = LogFactory.getLog(DBInstanceManager.class);

/*
	private Class class1; //First class
	private Class class2; //Second class
	private Class class3; //Intermediate class
	private String table1;
	private String table2;	
	
	//OPTION 1: "Raw" classes and the necessary attributes to use them (attribute names and table names) <--
	//OPTION 2: Abstract classes with the necessary functions to access them. Requires 
	public GenericRelationManager(Class class1, Class class2, Class class3, String table1, String table2) {
		this.class1 = class1;
		this.class2 = class2;
		this.class3 = class3;
		this.table1 = table1;
		this.table2 = table2;
	}*/

	public static long save(Object ins) throws BaseException{
		Session session = null;
		long instanceId = -1;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instanceId = save(ins, session);
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
	
	public static long save(Object ins, Session session) throws BaseException{
		//ins MUST BE a not null valid instance
		log.debug("saving " + ins.getClass().getSimpleName() + " instance");
		Long instanceSaved = null;
		try {		
			instanceSaved = (Long)session.save(ins);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(ins.getClass().getSimpleName() , instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + ins.getClass().getSimpleName()  + " failed", re);
			throw new BaseException(ins.getClass().getSimpleName()+"ManagerSAVE0", "Database error saving "+ ins.getClass().getSimpleName(), re);
		}
		return instanceSaved.longValue();		
	}
	

	public static List find(Class cls, String attribute, Object value) throws BaseException {
		Session session = null;
		List lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = find(cls,attribute,value, session);
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

	public static List find(Class cls, String attribute, Object value,  Session session) throws BaseException {
		log.debug("getting " + cls.getSimpleName()  + " instance with "+attribute+" = " + value);
		List  lista = null;
		try {
			Criteria criteria = session.createCriteria(cls);
			criteria.add(Restrictions.eq(attribute, value));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " +  cls.getSimpleName()  + " successful, no instance found");
			}
			else {
				log.debug("get " +  cls.getSimpleName()  + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " +  cls.getSimpleName()  + " failed", re);
			re.printStackTrace();
			throw new BaseException( cls.getSimpleName()+"ManagerFINDBY"+attribute+"0", "Database error getting "+ cls.getSimpleName()+" by "+attribute, re);
		}
		return lista;
	}
	
	public static void delete(Class cls, String attribute, Object value) throws BaseException{
		log.debug("deleting " +  cls.getSimpleName()  + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			delete(cls,attribute,value, session);
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
	
	public static void delete(Class cls, String attribute, Object value, Session session) throws BaseException{
		log.debug("deleting " + cls.getSimpleName() + " instance");
		try {
			List<DBInstance> lista = find(cls,attribute,value,session);
			for(DBInstance dbi:lista){
				//for(int i=0; i<lista.size(); i++){
				session.delete(dbi);
				log.debug(cls.getSimpleName() + " instance deleted");
				ChangeLogManager.save(cls.getSimpleName(),dbi.getId(), ChangeLogManager.ACTIONDELETE, null, null, attribute, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + cls.getSimpleName() + " failed", re);
			throw new BaseException(cls.getSimpleName()+"ManagerDELETEBY"+attribute+"0", "Database error deleting "+ cls.getSimpleName()+" by "+attribute, re);
		}
	}
	

}
