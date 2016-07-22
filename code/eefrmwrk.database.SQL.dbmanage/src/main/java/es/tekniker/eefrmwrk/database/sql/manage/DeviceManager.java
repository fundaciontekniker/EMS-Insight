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
import es.tekniker.eefrmwrk.database.sql.model.Device;
import es.tekniker.eefrmwrk.database.sql.model.DeviceVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.HomeDevice;
import es.tekniker.eefrmwrk.database.sql.model.HomeVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

/**
 * @author agarcia
 */

public class DeviceManager {

	private static final Log log = LogFactory.getLog(DeviceManager.class);
	private static final String tableName = "Device";

	public static long save(Device instance) throws BaseException {
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
	public static long save(Device instance, Session session) throws BaseException {
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
			throw new BaseException("DeviceManagerSAVE0",
					"Database error saving device", re);
		}
		return instanceSaved.longValue();
	}

	public static Device find(long id) throws BaseException {
		Session session = null;
		Device instance = null;
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
	public static Device find(long id, Session session) throws BaseException {
		log.debug("getting " + tableName + " instance with id : "
				+ Long.toString(id));
		Device instance = null;
		try {
			instance = (Device) session.get(Device.class, id);
			if (instance == null || instance.getActiv() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
				instance = null;
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDBYID0",
					"Database error getting device", re);
		}
		return instance;
	}

	public static List<Device> findAll() throws BaseException {
		Session session = null;
		List<Device> list = null;
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
	public static List<Device> findAll(Session session) throws BaseException {
		log.debug("getting " + tableName + " instances ");
		List<Device> lista = new ArrayList<Device>();
		try {
			Criteria criteria = session.createCriteria(Device.class);
			criteria.add(Restrictions.eq("activ", new Long(1)));
			criteria.addOrder(Order.asc("devId"));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDALL0",
					"Database error getting all devices", re);
		}
		return lista;
	}

	/*
	 * public Device findByLogin(String login) throws BaseException { Session
	 * session = null; Device Device = null; try { log.debug("Opening session");
	 * session = HibernateUtil.currentSession(); session.beginTransaction();
	 * Device = findByLogin(login, session); session.getTransaction().commit();
	 * log.debug("Closing session"); HibernateUtil.closeSession(); } catch
	 * (BaseException e){ session.getTransaction().rollback();
	 * log.debug("Closing session"); HibernateUtil.closeSession(); throw e; }
	 * return Device; }
	 * 
	 * public Device findByLogin(String login, Session session) throws
	 * BaseException { log.debug("getting " + tableName +
	 * " instance with login : " + login); List<Device> lista = null; Device
	 * result = null; try { Criteria criteria =
	 * session.createCriteria(Device.class);
	 * criteria.add(Restrictions.eq("Device", login));
	 * criteria.add(Restrictions.eq("activ", new Long(1))); lista =
	 * criteria.list(); if (lista.size() == 0) { log.debug("get " + tableName +
	 * " successful, no instance found"); } else if (lista.size() > 1){
	 * log.debug("More than 1 login found"); throw new
	 * BaseException("DeviceManagerFINDBYLOGIN0",
	 * "Database error getting devices by login"); } else { log.debug("get " +
	 * tableName + " successful, instance found"); result = lista.get(0); } }
	 * catch (RuntimeException re) { log.error("get " + tableName + " failed",
	 * re); re.printStackTrace(); throw new
	 * BaseException("DeviceManagerFINDBYLOGIN1",
	 * "Database error getting devices by login", re); } return result; }
	 */

	public static Device findByName(String name) throws BaseException {
		Session session = null;
		Device dev = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			dev = findByName(name, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return dev;
	}
	public static Device findByName(String name, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with name : " + name);
		List<Device> lista = null;
		Device dev=null;
		try {
			Criteria criteria = session.createCriteria(Device.class);
			criteria.add(Restrictions.eq("devName", name));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else if (lista.size() > 1){
				log.debug("More than 1 variable with this name");
				throw new BaseException("DeviceManagerFINDBYNAME1", "More than 1 device with this name");
			}
			else {
				log.debug("get " + tableName + " successful, instance found");
				dev=lista.get(0);
			}
			
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDBYNAME0",
					"Database error getting device by name", re);
		}
		return dev;
	}

	/*
	 * public List<Device> findByHome(long home) throws BaseException { Session
	 * session = null; List<Device> lista = null; try {
	 * log.debug("Opening session"); session = HibernateUtil.currentSession();
	 * session.beginTransaction(); lista = findByHome(home, session);
	 * session.getTransaction().commit(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); } catch (BaseException e){
	 * session.getTransaction().rollback(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); throw e; } return lista; }
	 * 
	 * public List<Device> findByHome(long home, Session session) throws
	 * BaseException { log.debug("getting " + tableName + " instance in home: "
	 * + home); List<Device> lista = null; try { String sql=
	 * "SELECT eefrmwrk.device.* " +
	 * "FROM eefrmwrk.device JOIN eefrmwrk.home_device " +
	 * "ON eefrmwrk.device.dev_id = eefrmwrk.home_device.dev_id " +
	 * "WHERE eefrmwrk.home_device.hi_id="+home; lista =
	 * session.createSQLQuery(sql).addEntity(Device.class).list(); if
	 * (lista.size() == 0) { log.debug("get " + tableName +
	 * " successful, no instance found"); } else { log.debug("get " + tableName
	 * + " successful, instance found"); } } catch (RuntimeException re) {
	 * log.error("get " + tableName + " failed", re); re.printStackTrace();
	 * throw new BaseException("DeviceManagerFINDBYROLE0",
	 * "Database error getting devices by stage", re); } return lista; }
	 */

	public static List<Device> findByLocalization(long localization)
			throws BaseException {
		Session session = null;
		List<Device> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByLocalization(localization, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return lista;
	}
	public static List<Device> findByLocalization(long localization, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with localization : "
				+ localization);
		List<Device> lista = null;
		try {
			Criteria criteria = session.createCriteria(Device.class);
			criteria.add(Restrictions.eq("devLocalization", localization));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDBYROLE0",
					"Database error getting devices by stage", re);
		}
		return lista;
	}

	public static List<Device> findByUri(String uri) throws BaseException {
		Session session = null;
		List<Device> lista = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			lista = findByUri(uri, session);
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;
		}
		return lista;
	}
	public static List<Device> findByUri(String uri, Session session)
			throws BaseException {
		log.debug("getting " + tableName + " instance with URI : " + uri);
		List<Device> lista = null;
		try {
			Criteria criteria = session.createCriteria(Device.class);
			criteria.add(Restrictions.eq("devUri", uri));
			criteria.add(Restrictions.eq("activ", new Long(1)));
			lista = criteria.list();
			if (lista.size() == 0) {
				log.debug("get " + tableName + " successful, no instance found");
			} else {
				log.debug("get " + tableName + " successful, instance found");
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDBYROLE0",
					"Database error getting devices by stage", re);
		}
		return lista;
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
			Device device = (Device) session.load(Device.class, id);
			log.debug("device loaded id: " + id);
			if (device != null) {
				device.setActiv(0);
				session.update(device);
				log.debug("deleted " + tableName + "s");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONDELETE, null, null, null,session);
			}
				DBInstanceManager.delete(HomeDevice.class,"devId", id,session);//Borrar HOME/DEVICE
				DBInstanceManager.delete(DeviceVarMetadata.class,"devId", id,session);//Borrar VARMETADATA/DEVICE

		} catch (RuntimeException re) {
			log.error("delete " + tableName + " failed", re);
			throw new BaseException("DeviceManagerDELETE0","Database error deleting device", re);
		}
	}

	/*
	 * public void deleteByLogin(String login) throws BaseException{
	 * log.debug("deleting " + tableName + "s"); Session session = null; try {
	 * log.debug("Opening session"); session = HibernateUtil.currentSession();
	 * session.beginTransaction(); deleteByLogin(login, session);
	 * session.getTransaction().commit(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); } catch (BaseException e){
	 * log.error("deleteBySearch " + tableName + " failed", e);
	 * session.getTransaction().rollback(); log.debug("Closing session");
	 * HibernateUtil.closeSession(); throw e; } }
	 * 
	 * public void deleteByLogin(String login, Session session) throws
	 * BaseException{ log.debug("deleting " + tableName + " instance"); try {
	 * List<Device> lista = (List<Device>) findByLogin(login, session); for(int
	 * i=0; i<lista.size(); i++){ lista.get(i).setActiv(0);
	 * session.update(lista.get(i)); log.debug(tableName + " instance deleted");
	 * ChangeLogManager.save(tableName, lista.get(i).getdeviceId(),
	 * ChangeLogManager.ACTIONDELETE, null, null, null, session); } } catch
	 * (RuntimeException re) { log.error("delete " + tableName + " failed", re);
	 * throw new BaseException("DeviceManagerDELETEBYLOGIN0",
	 * "Database error deleting Devices by login", re); } }
	 */
	
	public static Device update(Device instance) throws BaseException {
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
	public static Device update(Device instance, Session session) throws BaseException {
		log.debug("updating " + tableName + " instance");
		try {
			long id =instance.getDevId();
			Device device = (Device) session.load(Device.class,id);
			log.debug("Device to be updated: " + id);
			if (device.getActiv()==1) {
				device.setDevName(instance.getDevName());
				device.setDevDesc(instance.getDevDesc());
				device.setDevUri(instance.getDevUri());
				device.setDevCapabilities(instance.getDevCapabilities());
				device.setDevInfo(instance.getDevInfo());
				device.setDevLocalization(instance.getDevLocalization());
				device.setDevStatus(instance.getDevStatus());
				session.update(device);
				log.debug(tableName + " instance updated");
				ChangeLogManager.save(tableName, id,ChangeLogManager.ACTIONUPDATE, null, null, null,session);
			}else {
				log.error("update " + tableName + " failed");
				throw new BaseException("DeviceManagerUPDATE1","Device not found in database");
			}
		} catch (RuntimeException re) {
			log.error("update " + tableName + " failed", re);
			throw new BaseException("DeviceManagerUPDATE0","Database error updating device", re);
		}
		return instance;
	}
	
	public static List<Device> findByHome(long home)throws BaseException{
		List<Device> list= null;
		Session session = null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			list = findByHome(home, session);
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
	public static List<Device> findByHome(long home,Session session)throws BaseException{
	
		log.debug("getting " + tableName + " instance with home : " + home);
		List<Device> lista = new ArrayList<Device>();
		try {
			List<HomeDevice> sList=DBInstanceManager.find(HomeDevice.class,"hiId",home,session);
			for(HomeDevice hd: sList){
				lista.add(find(hd.getDevId(),session));
			}
		} catch (RuntimeException re) {
			log.error("get " + tableName + " failed", re);
			re.printStackTrace();
			throw new BaseException("DeviceManagerFINDBYHOME0", "Database error getting devices by home", re);
		}
		return lista;
		
	}
}
