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
import es.tekniker.eefrmwrk.database.sql.model.CepRuleStage;
import es.tekniker.eefrmwrk.database.sql.model.HomeDevice;
import es.tekniker.eefrmwrk.database.sql.model.HomeVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.Stage;
import es.tekniker.eefrmwrk.database.sql.model.TaskStage;
import es.tekniker.eefrmwrk.database.sql.model.UserStage;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadataStage;

/**
 * @author agarcia
 *
 */
public class StageManager {

	private static final Log log = LogFactory.getLog(StageManager.class);
	private static final String tableName = "Stage";
	
	public static long save(Stage instance) throws BaseException{
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
	
	public static long save(Stage instance, Session session) throws BaseException{
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long)session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(), ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("StageManagerSAVE0", "Database error saving stage", re);
		}
		return instanceSaved.longValue();		
	}
	
	public static Stage find(long id) throws BaseException {
		Session session = null;
		Stage instance = null;
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
	
	public static Stage find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : " + Long.toString(id));
		Stage instance = null;
		try {
			instance = (Stage)session.get(Stage.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("StageManagerFINDBYID0", "Database error getting stage", re);
		}
		return instance;
	}
	
	
	
	/*
	public List<Stage> findByName(String name) throws BaseException {
		Session session = null;
		List<Stage> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByName(name, session);
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
	
	public List<Stage> findByName(String name, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<Stage> lista = null;
		try {
			Criteria criteria = session.createCriteria(Stage.class);
			criteria.add(Restrictions.eq("stageName", name));
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
			throw new BaseException("StageManagerFINDBYNAME0", "Database error getting Stages by name", re);
		}
		return lista;
	}
	
	public List<Stage> findByRole(String role) throws BaseException {
		Session session = null;
		List<Stage> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByRole(role, session);
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
	
	public List<Stage> findByRole(String role, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with role : " + role);
		List<Stage> lista = null;
		try {
			Criteria criteria = session.createCriteria(Stage.class);
			criteria.add(Restrictions.eq("stageRole", role));
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
			throw new BaseException("StageManagerFINDBYROLE0", "Database error getting Stages by ROLE", re);
		}
		return lista;
	}
	*/
	public static List<Stage> findAll() throws BaseException {
		Session session = null;
		List<Stage> list = null;
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
	
	public static List<Stage> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<Stage> lista = new ArrayList<Stage>();
		try {
			Criteria criteria = session.createCriteria(Stage.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("stageId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("StageManagerFINDALL0", "Database error getting all stages", re);
		}
		return lista;
	}
	
	public static Stage findByOwner(long owner) throws BaseException {
		Session session = null;
		Stage Stage = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			Stage = findByOwner(owner, session);
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
		return Stage;
	}

	public static Stage findByOwner(long owner, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with owner : " + owner);
		List<Stage> lista = null;
		Stage result = null;
		try {
			Criteria criteria = session.createCriteria(Stage.class);
			criteria.add(Restrictions.eq("stageOwner", owner));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 login found");
				throw new BaseException("StageManagerFINDBYOWNER0", "Database error getting stages by owner");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				result = lista.get(0);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("StageManagerFINDBYOWNER1", "Database error getting stages by owner", re);
		}
		return result;
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
			Stage stage = (Stage)session.load(Stage.class, id);
			log.debug("Stage loaded id: " + id);
			if(stage != null){
				stage.setActiv(0);
				session.update(stage);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id, ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
			DBInstanceManager.delete(TaskStage.class,"stageId", id,session);
			DBInstanceManager.delete(UserStage.class,"stageId", id,session);
			DBInstanceManager.delete(VarMetadataStage.class,"stageId", id,session);
			DBInstanceManager.delete(CepRuleStage.class,"stageId",id,session);

		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("StageManagerDELETE0", "Database error deleting stage", re);
		}
	}
	
	/*
	public void deleteByLogin(String login) throws BaseException{
		log.debug("deleting " + tableName + "s");
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			deleteByLogin(login, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();					
		}
		catch (BaseException e){
			log.error("deleteBySearch " + tableName + " failed", e);
			session.getTransaction().rollback();					
			log.debug("Closing session");
			HibernateUtil.closeSession();		
			throw e;
		}
	}
	
	public void deleteByLogin(String login, Session session) throws BaseException{
		log.debug("deleting " + tableName + " instance");
		try {
			List<Stage> lista = (List<Stage>) findByLogin(login, session);
			for(int i=0; i<lista.size(); i++){
				lista.get(i).setActiv(0);
				session.update(lista.get(i));
				log.debug(tableName + " instance deleted");
				ChangeLogManager.save(tableName, lista.get(i).getstageId(), ChangeLogManager.ACTIONDELETE, null, null, null, session);
			}
		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("StageManagerDELETEBYLOGIN0", "Database error deleting Stages by login", re);
		}
	}
	*/
	public static Stage update(Stage instance) throws BaseException{
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
	
	public static Stage update(Stage instance, Session session) throws BaseException{
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getStageId();
			Stage stage = (Stage) session.load(Stage.class,id);
			log.debug("Stage to be updated: " + id);
			if (stage.getActiv()==1) {
				stage.setStageOwner(stage.getStageOwner());
				stage.setStageDesc(stage.getStageDesc());	
				session.saveOrUpdate(stage);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("StageManagerUPDATE1","Stage not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("StageManagerUPDATE0", "Database error updating stage", re);
		}
		return instance;
	}

}
