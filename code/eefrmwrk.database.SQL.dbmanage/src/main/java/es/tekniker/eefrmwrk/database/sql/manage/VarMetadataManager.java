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
import es.tekniker.eefrmwrk.database.sql.model.DeviceVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.HomeDevice;
import es.tekniker.eefrmwrk.database.sql.model.HomeVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadataStage;

/**
 * @author agarcia
 * 
 */
public class VarMetadataManager {

	private static final Log log = LogFactory.getLog(VarMetadataManager.class);
	private static final String tableName = "VarMetadata";

	public static long save(VarMetadata instance) throws BaseException {
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
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instanceId;
	}

	public static long save(VarMetadata instance, Session session)
			throws BaseException {
		log.debug("saving " + tableName + " instance");
		Long instanceSaved = null;
		try {
			instance.setActiv(1);
			instanceSaved = (Long) session.save(instance);
			log.debug("Instance saved: " + instanceSaved);
			ChangeLogManager.save(tableName, instanceSaved.longValue(),
					ChangeLogManager.ACTIONINSERT, null, null, null, session);
		} catch (RuntimeException re) {
			log.error("save " + tableName + " failed", re);
			throw new BaseException("VarMetadataManagerSAVE0",
					"Database error saving varMetadata", re);
		}
		return instanceSaved.longValue();
	}

	public static VarMetadata find(long id) throws BaseException {
		Session session = null;
		VarMetadata instance = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = find(id, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instance;
	}

	public static VarMetadata find(long id, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with id : "
				+ Long.toString(id));
		VarMetadata instance = null;
		try {
			instance = (VarMetadata) session.get(VarMetadata.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDBYID0",
					"Database error getting varMetadata", re);
		}
		return instance;
	}

	public static List<VarMetadata> findAll() throws BaseException {
		Session session = null;
		List<VarMetadata> list = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findAll(session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return list;
	}

	public static List<VarMetadata> findAll(Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<VarMetadata> lista = new ArrayList<VarMetadata>();
		try {
			Criteria criteria = session.createCriteria(VarMetadata.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("vmdId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDALL0",
					"Database error getting all varMetadatas", re);
		}
		return lista;
	}

	public static VarMetadata findByName(String name) throws BaseException {
		Session session = null;
		VarMetadata vmd = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			vmd = findByName(name, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return vmd;
	}

	public static VarMetadata findByName(String name, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		VarMetadata vmd = null;
		try {
			Criteria criteria = session.createCriteria(VarMetadata.class);
			criteria.add(Restrictions.eq("vmdName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			List<VarMetadata> lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1) {
				log.debug("More than 1 variable with this name");
				throw new BaseException("VarMetadataManagerFINDBYNAME1",
						"More than 1 variable with this name");
			} else {
				log.debug("get " + tableName + " successful, instance found");
				vmd = lista.get(0);
			}

		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDBYNAME0",
					"Database error getting variable by name", re);
		}
		return vmd;
	}

	public static void delete(long id) throws BaseException {
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
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
	}

	public static void delete(long id, Session session) throws BaseException {
		log.debug("deleting " + tableName + " instance");
		try {
			VarMetadata varMetadata = (VarMetadata) session.load(
					VarMetadata.class, id);
			log.debug("VarMetadata loaded id: " + id);
			if (varMetadata != null) {
				varMetadata.setActiv(0);
				session.update(varMetadata);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id,
						ChangeLogManager.ACTIONDELETE, null, null, null,
						session);
			}
			DBInstanceManager.delete(DeviceVarMetadata.class, "vmdId", id,
					session);// Borrar VARMETADATA/HOME
			DBInstanceManager.delete(HomeVarMetadata.class, "vmdId", id,
					session);// Borrar VARMETADATA/DEVICE
			DBInstanceManager.delete(VarMetadataStage.class, "vmdId", id,
					session);// Borrar stage
			// OPC NODES
			// VALUE_READ

		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("VarMetadataManagerDELETE0",
					"Database error deleting varMetadata", re);
		}
	}

	public static VarMetadata update(VarMetadata instance) throws BaseException {
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			instance = update(instance, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return instance;
	}

	public static VarMetadata update(VarMetadata instance, Session session)
			throws BaseException {
		log.debug("updating " + tableName + " instance");
		try {
			long id = instance.getVmdId();
			VarMetadata varMetadata = (VarMetadata) session.load(
					VarMetadata.class, id);
			log.debug("VarMetadata to be updated: " + id);
			if (varMetadata.getActiv() == 1) {
				varMetadata.setVmdName(instance.getVmdName());
				varMetadata.setVmdUri(instance.getVmdUri());
				varMetadata.setVmdLocalization(instance.getVmdLocalization());
				varMetadata.setVmdMeasureunit(instance.getVmdMeasureunit());
				varMetadata.setVmdDigtype(instance.getVmdDigtype());
				varMetadata.setVmdPhytype(instance.getVmdPhytype());
				varMetadata.setVmdDescription(instance.getVmdDescription());
				varMetadata.setVmdStoredbd(instance.getVmdStoredbd());
				session.update(varMetadata);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,
						ChangeLogManager.ACTIONUPDATE, null, null, null,
						session);
			} else {
				log.error("update " + tableName + " failed");
				throw new BaseException("VarMetadataManagerUPDATE1",
						"VarMetadata not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("VarMetadataManagerUPDATE0",
					"Database error updating varMetadata", re);
		}
		return instance;
	}

	public static List<VarMetadata> findByDevice(long device)
			throws BaseException {
		List<VarMetadata> list = null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findByDevice(device, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return list;
	}

	public static List<VarMetadata> findByDevice(long device, Session session)
			throws BaseException {

		log.debug("getting " + tableName + " instance with device : " + device);
		List<VarMetadata> lista = new ArrayList<VarMetadata>();
		try {
			List<DeviceVarMetadata> sList = DBInstanceManager.find(
					DeviceVarMetadata.class, "devId", device, session);
			for (DeviceVarMetadata dvm : sList) {
				lista.add(find(dvm.getVmdId(), session));
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDBYDEVICE0",
					"Database error getting varMetadata by device", re);
		}
		return lista;

	}

	public static List<VarMetadata> findByHome(long home) throws BaseException {
		List<VarMetadata> list = null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findByHome(home, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return list;
	}

	public static List<VarMetadata> findByHome(long home, Session session)
			throws BaseException {

		log.debug("getting " + tableName + " instance with home : " + home);
		List<VarMetadata> lista = new ArrayList<VarMetadata>();
		try {
			List<HomeVarMetadata> sList = DBInstanceManager.find(HomeVarMetadata.class, "hiId", home, session);
			for (HomeVarMetadata hvm : sList) {
				VarMetadata vmd = find(hvm.getVmdId(), session);
				lista.add(vmd);
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDBYHOME0","Database error getting varMetadata by home", re);
				
		}
		return lista;

	}

	public static List<VarMetadata> findByStage(long stage)
			throws BaseException {
		List<VarMetadata> list = null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findByStage(stage, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return list;
	}

	public static List<VarMetadata> findByStage(long stage, Session session)
			throws BaseException {

		log.debug("getting " + tableName + " instance with stage : " + stage);
		List<VarMetadata> lista = new ArrayList<VarMetadata>();
		try {
			List<VarMetadataStage> sList = DBInstanceManager.find(
					VarMetadataStage.class, "stageId", stage, session);
			for (VarMetadataStage svm : sList) {
				lista.add(find(svm.getVmdId(), session));
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("VarMetadataManagerFINDBYSTAGE0",
					"Database error getting varMetadata by stage", re);
		}
		return lista;
	}
}
