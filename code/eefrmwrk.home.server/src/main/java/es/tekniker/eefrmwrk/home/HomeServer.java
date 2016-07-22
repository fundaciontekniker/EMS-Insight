package es.tekniker.eefrmwrk.home;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.DeviceI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.database.sql.manage.DeviceManager;
import es.tekniker.eefrmwrk.database.sql.manage.HibernateUtil;
import es.tekniker.eefrmwrk.database.sql.manage.HomeManager;
import es.tekniker.eefrmwrk.database.sql.manage.LocalizationManager;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.Device;
import es.tekniker.eefrmwrk.database.sql.model.Home;
import es.tekniker.eefrmwrk.database.sql.model.Localization;
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;
//import es.tekniker.eefrmwrk.database.sql.model.ValueRead;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

public class HomeServer {

	private static final Log log = LogFactory.getLog(HomeServer.class);
	
	public static VariableI getLastValue(String varName, long date) throws BaseException{
		Session session= null;
		VariableI var=null;
	
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			
			var= getLastValue(varName,date,session);
			
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;			
		}
		return var;
	} 
	
	public static VariableI getLastValue(String varName,long date,Session session) throws BaseException{

		VariableI var=null;
		
			VarMetadata vmd = VarMetadataManager.findByName(varName,session);
			if (vmd == null) {
				throw new BaseException("HomeServer_READLASTVARIABLE1","Variable "+ varName +" not found in database");
			}
			
			var = new VariableI();
			var.setName(vmd.getVmdName());
			var.setURI(vmd.getVmdUri());
			var.setStoreDB(vmd.getVmdStoredbd());
			var.setAccess(vmd.getVmdAccess());
			var.setDigitalType(vmd.getVmdDigtype());
			var.setPhysicalType(vmd.getVmdPhytype());
			var.setMeasureUnit(vmd.getVmdMeasureunit());
			var.setLocalization(Localization.LOCALIZATION_UNKNOWN);
			var.setDescription(vmd.getVmdDescription());
			Localization loc = LocalizationManager.find(vmd.getVmdLocalization(),session);
			if (loc != null)
				var.setLocalization(loc.getLocName());
			else
				var.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
			var.setValue(ValueVar.VALUE_UNKNOW);

			ValueVar vr= DBManager.getLastVR(varName, date, session);
			
			if (vr == null) {
				var.setValue(ValueVar.VALUE_UNAVALAIBLE);
				var.setQuality(-1);
				var.setTimestamp(-1);
			} else {
				var.setValue(vr.getVrValue());
				var.setQuality(vr.getVrQuality());
				var.setTimestamp(vr.getVrTimestamp());
			}	
		return var;
	} 
	
	public static List<DeviceI> getHomeDevices(String homeName) throws BaseException {
		List<DeviceI> devList = new ArrayList<DeviceI>();
		Session session= null;
		try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();		
				
				log.debug("Getting devices for home " + homeName);
				
					Home h = HomeManager.findByName(homeName,session);
					if (h == null) {
						throw new BaseException("HomeServer_GETHOMEDEVICES1", "Home "+ homeName + " NOT found in database");								
					}
					List<Device> dList = DeviceManager.findByHome(h.getHiId(),session);
					for (Device d : dList) {
						DeviceI devI = new DeviceI();
						devI.setName(d.getDevName());
						devI.setDesc(d.getDevDesc());
						devI.setCapabilities(d.getDevCapabilities());
						devI.setInfo(d.getDevInfo());
						devI.setLocalization(Localization.LOCALIZATION_UNKNOWN);
						
						Localization l = LocalizationManager.find(d.getDevLocalization(),session);
						if (l != null)
							devI.setLocalization(l.getLocName());
						else
							devI.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
						
						devI.setStatus(d.getDevStatus());
						devI.setURI(d.getDevUri());
						devList.add(devI);
					}

				
				session.getTransaction().commit();
				log.debug("Closing session");
				HibernateUtil.closeSession();					
			} 
			catch (BaseException e) {
				session.getTransaction().rollback();
				log.debug("Closing session");
				HibernateUtil.closeSession();
				throw e;
			}
			catch (Exception e){
				session.getTransaction().rollback();					
				log.debug("Closing session");
				HibernateUtil.closeSession();		
				throw new BaseException("HomeServer_GETHOMEDEVICES0","Exception getting home devices", e);
			}
		return devList;
		}

	public static List<VariableI> getDeviceVariables(String devName)throws BaseException {
		List<VariableI> varList = new ArrayList<VariableI>();
		Session session= null;
	
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();
			
			varList= getDeviceVariables(devName,session);
			
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			
		} catch (BaseException e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw e;			
		}
		return varList;
	}
	
	public static List<VariableI> getDeviceVariables(String devName,Session session)throws BaseException {
		List<VariableI> varList = new ArrayList<VariableI>();

		Device d = DeviceManager.findByName(devName,session);
		if (d == null) {
			throw new BaseException("HomeServer_GETDEVICEVARIABLES1", "Device "+devName+ " NOT found in database");
		}

		List<VarMetadata> vmdList = VarMetadataManager.findByDevice(d.getDevId(),session);
		for (VarMetadata vmd : vmdList) {
			
			/**
			 * Variable: (sólo metadatos)
			 * */
			VariableI var = new VariableI();
			var.setName(vmd.getVmdName());
			var.setURI(vmd.getVmdUri());
			var.setStoreDB(vmd.getVmdStoredbd());
			var.setAccess(vmd.getVmdAccess());
			var.setDigitalType(vmd.getVmdDigtype());
			var.setPhysicalType(vmd.getVmdPhytype());
			var.setMeasureUnit(vmd.getVmdMeasureunit());
			var.setLocalization(Localization.LOCALIZATION_UNKNOWN);
			var.setDescription(vmd.getVmdDescription());
			Localization loc = LocalizationManager.find(vmd.getVmdLocalization(),session);
			if (loc != null)
				var.setLocalization(loc.getLocName());
			else
				var.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
			var.setValue(ValueVar.VALUE_UNKNOW);		
			varList.add(var);
			
			
			/**
			 * Variable:  metadatos y último valor
			 * */
			//varList.add(getLastValue(vmd.getVmdName(),System.currentTimeMillis(),session));
		}
		return varList;		
	}	
	
	
	
	
}
