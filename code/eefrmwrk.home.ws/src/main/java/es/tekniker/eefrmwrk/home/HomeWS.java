package es.tekniker.eefrmwrk.home;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import es.tekniker.database.DBManager;
import es.tekniker.database.DBManager;
import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.commons.DeviceI;
import es.tekniker.eefrmwrk.commons.HomeI;
import es.tekniker.eefrmwrk.commons.TaskI;
import es.tekniker.eefrmwrk.commons.VarMetadataI;
import es.tekniker.eefrmwrk.commons.VariableI;
import es.tekniker.eefrmwrk.commons.WSException;
import es.tekniker.eefrmwrk.config.DatabaseProperties;
import es.tekniker.eefrmwrk.database.nosql.HectorFactory;
import es.tekniker.eefrmwrk.database.sql.manage.DBInstanceManager;
import es.tekniker.eefrmwrk.database.sql.manage.DeviceManager;
import es.tekniker.eefrmwrk.database.sql.manage.HomeManager;
import es.tekniker.eefrmwrk.database.sql.manage.LocalizationManager;
import es.tekniker.eefrmwrk.database.sql.manage.OpcSubscriptionManager;
import es.tekniker.eefrmwrk.database.sql.manage.TaskManager;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.Device;
import es.tekniker.eefrmwrk.database.sql.model.DeviceVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.Home;
import es.tekniker.eefrmwrk.database.sql.model.HomeDevice;
import es.tekniker.eefrmwrk.database.sql.model.HomeVarMetadata;
import es.tekniker.eefrmwrk.database.sql.model.Localization;
import es.tekniker.eefrmwrk.database.sql.model.OpcSubscription;
import es.tekniker.eefrmwrk.database.sql.model.Task;
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

public class HomeWS implements IHomeWS {
	
	private static final Log log = LogFactory.getLog(HomeWS.class);
	private static final String deviceEntity = "device";
	private static final String homeEntity = "home";
	private static final String globalEntity = "global";
	private static final String RETURN_OK = "RETURN_OK";
	private static final Object ABSOLUT = "absolut";
	private static final Object INCREMENTAL = "incremental";

	// -------------HOME RELATED--------------------------------------------
	public String addHome(String homeName, String homeLoc, String homeEndpoint)
			throws WSException {
		log.debug("Adding new home");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_ADDHOME1",
					"Home name can NOT be empty");

		}
		try {
			Home h = HomeManager.findByName(homeName);
			if (h != null) {
				log.error("Home name " + homeName + " already exists");
				throw new WSException("HomeWS_ADDHOME2", "Home name "
						+ homeName + " already exists");
			}

			h = new Home();
			h.setHiName(homeName);
			h.setHiEndpoint(homeEndpoint);
			if (homeLoc != null && !homeLoc.isEmpty()) {
				Localization l = LocalizationManager.findByName(homeLoc);
				if (l == null) {
					log.debug("Localization " + homeLoc + " not found");
					throw new WSException("HomeWS_ADDHOME3", "Localization "
							+ homeLoc + " NOT found in database");
				} else
					h.setHiLocalization(l.getLocId());
			} else {
				h.setHiLocalization(0);
			}

			HomeManager.save(h);
			return homeName;

		} catch (BaseException e) {
			log.error("Exception adding home");
			throw new WSException("HomeWS_ADDHOME0", "Exception adding home", e);
		}

	}

	public HomeI getHome(String homeName) throws WSException {
		HomeI hI = new HomeI();
		log.debug("Getting Home Info");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_GETHOME1",
					"Home name can NOT be empty");
		}
		log.debug("Getting Home Info for " + homeName);
		try {
			Home h = HomeManager.findByName(homeName);
			if (h == null) {
				log.error("Home " + homeName + " not found");
				throw new WSException("HomeWS_GETHOME2", "Home " + homeName
						+ " NOT found in database");
			}
			hI.setName(h.getHiName());

			hI.setLocalization(Localization.LOCALIZATION_UNKNOWN);
			Localization l = LocalizationManager.find(h.getHiLocalization());
			if (l != null)
				hI.setLocalization(l.getLocName());
			else
				hI.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
			hI.setEndpoint(h.getHiEndpoint());
			return hI;
		} catch (BaseException e) {
			log.error("Exception getting home info");
			throw new WSException("HomeWS_GETHOME0",
					"Exception getting home info", e);
		}

	}

	public String editHome(String homeName, String homeLoc, String homeEndpoint)
			throws WSException {
		log.debug("Editing Home Info");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_EDITHOME1",
					"Home name can NOT be empty");
		}
		log.debug("Editing Home Info for " + homeName);
		try {
			Home h = HomeManager.findByName(homeName);
			if (h == null) {
				log.error("Home " + homeName + " NOT found in database");
				throw new WSException("HomeWS_EDITHOME2", "Home " + homeName
						+ " NOT found in database");
			}
			h.setHiName(homeName);

			if (homeLoc != null && !homeLoc.isEmpty()) {
				Localization l = LocalizationManager.findByName(homeLoc);
				if (l == null) {
					log.debug("Localization " + homeLoc + " not found");
					throw new WSException("HomeWS_EDITHOME3", "Localization "
							+ homeLoc + " NOT found in database");
				} else
					h.setHiLocalization(l.getLocId());
			} else {
				h.setHiLocalization(0);
			}

			h.setHiEndpoint(homeEndpoint);

			HomeManager.update(h);

			return homeName;

		} catch (BaseException e) {
			log.error("Exception editing home");
			throw new WSException("HomeWS_EDITHOME0", "Exception editing home",
					e);
		}
	}

	public String deleteHome(String homeName) throws WSException {
		log.debug("Editing Home Info");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_DELETEHOME1",
					"Home name can NOT be empty");
		}
		log.debug("Editing Home Info for " + homeName);
		try {
			Home h = HomeManager.findByName(homeName);
			if (h == null) {
				log.error("Home " + homeName + " NOT found in database");
				throw new WSException("HomeWS_DELETEHOME2", "Home " + homeName
						+ " NOT found in database");
			}
			List hdList = DBInstanceManager.find(HomeDevice.class,"hiId", h.getHiId());
			if (!hdList.isEmpty()) {
				log.debug("Home still has "+hdList.size()+" linked device(s). Unlink first");
				throw new WSException("HomeWS_DELETEHOME3","Home still has "+hdList.size()+" linked device(s). Unlink first");
			}
			
			List hvList = DBInstanceManager.find(HomeVarMetadata.class,"hiId", h.getHiId());
			if (!hvList.isEmpty()) {
				log.debug("Home still has "+hvList.size()+" linked variable(s). Unlink first");
				throw new WSException("HomeWS_DELETEHOME4","Home still has "+hvList.size()+" linked variable(s). Unlink first");
			}
			
			HomeManager.delete(h.getHiId());
			return RETURN_OK;

		} catch (BaseException e) {
			log.error("Exception deleting home");
			throw new WSException("HomeWS_DELETEHOME0",
					"Exception deleting home", e);
		}
	}

	public List<VariableI> getHomeVariables(String homeName) throws WSException {
		List<VariableI> varList = new ArrayList<VariableI>();
		log.debug("Getting variables for home ");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_GETHOMEVARIABLES1",
					"Home name can NOT be empty");
		}
		log.debug("Getting variables for home " + homeName);
		try {
			Home h = HomeManager.findByName(homeName);
			if (h == null) {
				log.error("Home " + homeName + " NOT found in database");
				throw new WSException("HomeWS_GETHOMEVARIABLES2", "Home "
						+ homeName + " NOT found in database");
			}

			List<VarMetadata> vmdList = VarMetadataManager.findByHome(h
					.getHiId());
			for (VarMetadata vmd : vmdList) {
				// for (VariableI vI : getVariableValues(vmd.getVmdName())) {
				varList.add(getVariableValue(vmd.getVmdName(),null));
				// }
			}

			log.debug("Getting variables for devices in home " + homeName);
			List<Device> devList = DeviceManager.findByHome(h.getHiId());
			for (Device d : devList) {
				for (VariableI vI : getDeviceVariables(d.getDevName())) {
					varList.add(vI);
				}
			}
			return varList;
		} catch (BaseException e) {
			log.error("Exception getting home variables");
			throw new WSException("HomeWS_GETHOMEVARIABLES0",
					"Exception getting home variables", e);
		}

	}

	public List<DeviceI> getHomeDevices(String homeName) throws WSException {

		log.debug("Getting devices for home");
		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name can NOT be empty");
			throw new WSException("HomeWS_GETHOMEDEVICES1",
					"Home name can NOT be empty");
		}
		try{
		return HomeServer.getHomeDevices(homeName);}
		catch(BaseException e){
			throw new WSException("HomeWS_GETHOMEDEVICES0","Exception getting home devices",e);
		}
	}

	// ---------------DEVICE RELATED------------------------------------------
	public String addDevice(String homeName, String devName, String devDesc,
			String devURI, String devStatus, String devInfo, String devLoc,
			String devCapabilities)
			throws WSException {
		log.debug("Adding new device");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_ADDDEVICE1",
					"Device name can NOT be empty");
		}
		if (devURI == null || devURI.isEmpty()) {
			log.error("Device URI empty");
			throw new WSException("HomeWS_ADDDEVICE2",
					"Device URI can NOT be empty");
		}

		try {
			Device d = DeviceManager.findByName(devName);
			if (d != null) {
				log.debug("Device name " + devName + " already exists");
				throw new WSException("HomeWS_ADDDEVICE3", "Device name "
						+ devName + " already exists");
			}
			
			Home h=null;
			if (homeName != null && !homeName.isEmpty()) {
				log.debug("Adding device " + devName + " to " + homeName);
				h = HomeManager.findByName(homeName);
				if (h == null) {
					log.warn("Home " + homeName + " not found");
					throw new WSException("HomeWS_ADDDEVICE4", "Home "+ homeName + " NOT found in database");
				}
			}
			
			d = new Device(devName, devURI);
			if (devLoc != null && !devLoc.isEmpty()) {
				Localization l = LocalizationManager.findByName(devLoc);
				if (l == null) {
					log.debug("Localization NOT found in database");
					throw new WSException("HomeWS_ADDDEVICE5", "Localization NOT found in database");
				} else
					d.setDevLocalization(l.getLocId());
			} else {
				d.setDevLocalization(0);
			}
			d.setDevDesc(devDesc);
			d.setDevStatus(devStatus);
			d.setDevInfo(devInfo);
			d.setDevCapabilities(devCapabilities);
			Long devId = DeviceManager.save(d);
			if (h!=null) {
				HomeDevice hd = new HomeDevice(h.getHiId(), devId);
				DBInstanceManager.save(hd);
			}
			return devName;
		} catch (BaseException e) {
			log.error("Exception adding device");
			throw new WSException("HomeWS_ADDDEVICE0",
					"Exception adding device", e);
		}

	}

	public DeviceI getDevice(String devName) throws WSException {
		log.debug("Getting device " + devName);
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_GETDEVICE1","Device name can NOT be empty");
		}
		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " NOT found in database");
				throw new WSException("HomeWS_GETDEVICE2", "Device" + devName+ " NOT found in database");
			} else {
				DeviceI dI = new DeviceI();
				dI.setName(d.getDevName());
				dI.setDesc(d.getDevDesc());
				dI.setInfo(d.getDevInfo());
				dI.setCapabilities(d.getDevCapabilities());
				dI.setURI(d.getDevStatus());
				dI.setStatus(d.getDevStatus());
				dI.setLocalization(Localization.LOCALIZATION_UNKNOWN);
				Localization l = LocalizationManager.find(d.getDevLocalization());
				if (l != null)
					dI.setLocalization(l.getLocName());
				else
					dI.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
				return dI;
			}
		} catch (BaseException e) {
			log.error("Exception getting device");
			throw new WSException("HomeWS_GETDEVICE0","Exception getting device", e);					
		}
	}

	public String editDevice(String devName, String devDesc, String devURI,
			String devStatus, String devInfo, String devLoc,
			String devCapabilities) throws WSException {

		log.debug("Editing device ");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_EDITDEVICE1",	"Device name can NOT be empty");
		}
		if (devURI == null || devURI.isEmpty()) {
			log.error("Device URI empty");
			throw new WSException("HomeWS_EDITDEVICE2","Device URI can NOT be empty");
		}

		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " not found");
				throw new WSException("HomeWS_EDITDEVICE3",
						"Device NOT found in database");
			}
			d.setDevName(devName);
			d.setDevDesc(devDesc);
			d.setDevUri(devURI);
			d.setDevStatus(devStatus);
			d.setDevInfo(devInfo);
			if (devLoc != null && !devLoc.isEmpty()) {
				Localization l = LocalizationManager.findByName(devLoc);
				if (l == null) {
					log.debug("Localization NOT found in database");
					throw new WSException("HomeWS_EDITDEVICE4", "Localization NOT found in database");
				} else
					d.setDevLocalization(l.getLocId());
			} else {
				d.setDevLocalization(0);
			}
			d.setDevCapabilities(devCapabilities);
			DeviceManager.update(d);
			return devName;
		} catch (BaseException e) {
			log.error("Exception editing device");
			throw new WSException("HomeWS_EDITDEVICE0",
					"Exception editing device", e);
		}
	}

	public String deleteDevice(String devName) throws WSException {
		log.debug("Deleting device");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_DELETEDEVICE1","Device name can NOT be empty");
		}
		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " not found");
				throw new WSException("HomeWS_DELETEDEVICE2", "Device "	+ devName + " NOT found in database");
			}
			
			List dvList = DBInstanceManager.find(DeviceVarMetadata.class,"devId", d.getDevId());
			if (!dvList.isEmpty()) {
				log.debug("Device still has "+dvList.size()+" linked variable(s). Unlink first");
				throw new WSException("HomeWS_DELETEDEVICE3","Device still has "+dvList.size()+" linked variable(s). Unlink first");
			}
			
			DeviceManager.delete(d.getDevId());
			return RETURN_OK;
			
			} catch (BaseException e) {
			log.error("Exception deleting device");
			throw new WSException("HomeWS_DELETEDEVICE0",
					"Exception deleting device", e);
		}
	}

	public List<VariableI> getDeviceVariables(String devName)throws WSException {				
		log.debug("Getting device variables");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_GETDEVICEVARIABLES1","Device name can NOT be empty");
		}
		try {
			return HomeServer.getDeviceVariables(devName);

		} catch (BaseException e) {
			log.error("Exception getting device variables");
			throw new WSException("HomeWS_GETDEVICEVARIABLES0",
					"Exception getting device variables", e);
		}
	}

	public String linkDevice(String devName, String homeName)
			throws WSException {
		log.debug("Linking device");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_LINKDEVICE1",
					"Device name can NOT be empty");
		}

		if (homeName == null || homeName.isEmpty()) {
			log.error("Home name empty");
			throw new WSException("HomeWS_LINKDEVICE2",
					"Home name can NOT be empty");
		}

		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " NOT  found in database");
				throw new WSException("HomeWS_LINKDEVICE3", "Device " + devName
						+ " NOT  found in database");
			}
			
			Home h = HomeManager.findByName(homeName);
			if (h == null) {
				log.debug("Home " + homeName + " NOT  found in database");
				throw new WSException("HomeWS_LINKDEVICE4", "Home " + homeName
						+ " NOT found in database");
			}
			
			List l = DBInstanceManager.find(HomeDevice.class, "devId",
					d.getDevId());
			if (!l.isEmpty()) {
				log.debug("Device " + devName + " is already linked to a home");
				throw new WSException("HomeWS_LINKDEVICE5", "Device " + devName
						+ " is already linked to a home. Unlink first");
			}
			DBInstanceManager.save(new HomeDevice(h.getHiId(), d.getDevId()));
			return RETURN_OK;

		} catch (BaseException e) {
			log.error("Exception linking device ");
			throw new WSException("HomeWS_LINKDEVICE0","Exception linking device", e);		
		}
	}
	
	public String unlinkDevice(String devName) throws WSException {
		log.debug("Unlinking device");
		if (devName == null || devName.isEmpty()) {
			log.error("Device name empty");
			throw new WSException("HomeWS_UNLINKDEVICE1",
					"Device name can NOT be empty");
		}
		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " NOT  found in database");
				throw new WSException("HomeWS_UNLINKDEVICE2", "Device "
						+ devName + " NOT  found in database");
			}
			List l = DBInstanceManager.find(HomeDevice.class, "devId",
					d.getDevId());
			if (l.isEmpty()) {
				log.debug("Device " + devName + " is NOT  linked to any home");
				throw new WSException("HomeWS_UNLINKDEVICE3", "Device "
						+ devName + " NOT linked to any home");
			}
			DBInstanceManager.delete(HomeDevice.class, "devId", d.getDevId());
			return RETURN_OK;

		} catch (BaseException e) {
			log.error("Exception unlinking device ");
			throw new WSException("HomeWS_UNLINKDEVICE0",
					"Exception unlinking device", e);
		}
	}


	// ----------------VARIABLE RELATED----------------------------
	/*
	 * entityId y entyType se utilizan para, en caso de quererlo, asociar la
	 * variable a un device o a home. entytyType tendrá los valores "device" y
	 * "home". En caso de ser otra cosa, la variable se creará pero no se
	 * asociará a nada. entytyId es el nombre del device o home a asociar.
	 */
	public String addVariable(String varName, String entityId, String entityType,
			String localization, String physicalType,
			String digitalType, 
			String measureUnit, 
			String access,
			String description,
			String store) throws WSException {
		log.debug("Adding new variable");

		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_ADDVARIABLE1",
					"Variable name can NOT be empty");
		}

		if (store == null || store.isEmpty()) {
			log.debug("Storage can NOT be empty");
			throw new WSException("HomeWS_ADDVARIABLE2",
					"Variable storage can NOT be empty");
		}
		if (!VarMetadata.validStorage(store)) {
			log.debug("Invalid variable storage");
			throw new WSException("HomeWS_ADDVARIABLE3",
					"Invalid variable storage");
		}
		if (!VarMetadata.validDigType(digitalType)) {
			log.debug("Invalid digital type");
			throw new WSException("HomeWS_ADDVARIABLE10",
					"Invalid digital type");
		}
		
		if (entityId == null || entityId.isEmpty()) {
			log.debug("entityId can NOT be empty");
			throw new WSException("HomeWS_ADDVARIABLE4",
					"entityId can NOT be empty");
		}
		if (entityType == null || entityType.isEmpty()) {
			log.debug("entityType can NOT be empty");
			throw new WSException("HomeWS_ADDVARIABLE5",
					"entityType can NOT be empty");
		}
		if (!entityType.equals(deviceEntity) && !entityType.equals(homeEntity)
				&& !entityType.equals(globalEntity)) {
			log.debug("EntityType can only be home, device or global");
			throw new WSException("HomeWS_ADDVARIABLE6",
					"EntityType can only be home, device or global");
		}

		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd != null) {
				log.debug("Variable name " + varName + " already in use");
				throw new WSException("HomeWS_ADDVARIABLE7", "Variable name "
						+ varName + " already in use");
			}

			Device d = null;
			if (entityType.equals(deviceEntity)) {
				d = DeviceManager.findByName(entityId);
				if (d == null)
					throw new WSException("HomeWS_ADDVARIABLE8", "Device "
							+ entityId + " NOT found in database");
			}

			Home h = null;
			if (entityType.equals(homeEntity)) {
				h = HomeManager.findByName(entityId);
				if (h == null)
					throw new WSException("HomeWS_ADDVARIABLE9", "Home "
							+ entityId + " NOT found in database");
			}

			vmd = new VarMetadata();
			vmd.setVmdName(varName);
			Localization l = LocalizationManager.findByName(localization);
			if (l != null)
				vmd.setVmdLocalization(l.getLocId());
			else
				vmd.setVmdLocalization(new Long(0));
			vmd.setVmdPhytype(physicalType);
			vmd.setVmdDigtype(digitalType);
			vmd.setVmdMeasureunit(measureUnit);
			vmd.setVmdUri(createURI(varName,store));
			vmd.setVmdAccess(access);
			vmd.setVmdDescription(description);
			vmd.setVmdStoredbd(store);

			long vmdId = VarMetadataManager.save(vmd);

			if (entityType != null) {
				if (entityType.equals(deviceEntity)) {
					DBInstanceManager.save(new DeviceVarMetadata(d.getDevId(),	vmdId));
				}
				if (entityType.equals(homeEntity)) {
					DBInstanceManager.save(new HomeVarMetadata(h.getHiId(),vmdId));
				}
			}
			return varName;

		} catch (BaseException e) {
			log.error("Exception adding variable");
			throw new WSException("HomeWS_ADDVARIABLE0",
					"Exception adding variable", e);
		}
	}

	public VarMetadataI getVariable(String varName) throws WSException {
		log.debug("Getting variable data");

		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_GETVARIABLE1",
					"Variable name can NOT be empty");
		}
		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd == null) {
				log.debug("Variable " + varName + " NOT found in database");
				throw new WSException("HomeWS_GETVARIABLE2", "Variable "
						+ varName + " NOT found in database");
			}
			VarMetadataI vI = new VarMetadataI();
			vI.setName(vmd.getVmdName());
			vI.setLocalization(Localization.LOCALIZATION_UNKNOWN);
			Localization l = LocalizationManager.find(vmd.getVmdLocalization());
			if (l != null)
				vI.setLocalization(l.getLocName());
			else
				vI.setLocalization(Localization.LOCALIZATION_UNAVALAIBLE);
			vI.setPhysicalType(vmd.getVmdPhytype());
			vI.setDigitalType(vmd.getVmdDigtype());
			vI.setMeasureUnit(vmd.getVmdMeasureunit());
			vI.setURI(vmd.getVmdUri());
			vI.setAccess(vmd.getVmdAccess());
			vI.setDescription(vmd.getVmdDescription());
			vI.setStoreDB(vmd.getVmdStoredbd());
			return vI;

		} catch (BaseException e) {
			log.error("Exception getting variable");
			throw new WSException("HomeWS_GETVARIABLE0",
					"Exception getting variable", e);
		}
	}

	public String editVariable(String varName,
			String localization, String physicalType,
			String digitalType, 
			String measureUnit, 
			String access,
			String description,
			String storeDB) throws WSException {
		log.debug("Editing variable");
		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_EDITVARIABLE1",
					"Variable name can NOT be empty");
		}

		if (storeDB == null || storeDB.isEmpty()) {
			log.debug("Storage can NOT be empty");
			throw new WSException("HomeWS_EDITVARIABLE2",
					"Variable storage can NOT be empty");
		}

		try {
			VarMetadata v = VarMetadataManager.findByName(varName);
			if (v == null) {
				log.debug("Variable " + varName + " NOT found in database");
				throw new WSException("HomeWS_EDITVARIABLE3", 
						"Variable " + varName + " NOT found in database");
			}

			if (localization != null && !localization.isEmpty()) {
				Localization l = LocalizationManager.findByName(localization);
				if (l == null) {
					log.debug("Localization " + localization + " not found");
					throw new WSException("HomeWS_EDITVARIABLE4",
							"Localization " + localization+ " NOT found in database");			
				} else
					v.setVmdLocalization(l.getLocId());
			} else {
				v.setVmdLocalization(0);
			}

			v.setVmdPhytype(physicalType);
			v.setVmdDigtype(digitalType);
			v.setVmdMeasureunit(measureUnit);
			v.setVmdAccess(access);
			v.setVmdUri(createURI(varName,storeDB));
			v.setVmdDescription(description);
			v.setVmdStoredbd(storeDB);

			VarMetadataManager.update(v);
			return varName;

		} catch (BaseException e) {
			log.error("Exception editing variable");
			throw new WSException("HomeWS_EDITVARIABLE0","Exception editing variable", e);	
		}
	}

	public String deleteVariable(String varName) throws WSException {
		log.debug("Deleting variable");
		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_DELETEVARIABLE1",	"Variable name can NOT be empty");
		}

		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd == null) {
				log.debug("Variable " + varName + " NOT found in database");
				throw new WSException("HomeWS_DELETEVARIABLE2", 
						"Variable "+ varName + " NOT found in database");	
			}

			OpcSubscription opcSub = OpcSubscriptionManager.findByVarMetaData(vmd.getVmdId());
			if (opcSub != null) {
				log.debug("Variable " + varName+ " still has subscriptions. Delete them first");
				throw new WSException("HomeWS_DELETEVARIABLE3", "Variable "
						+ varName+ " still has subscriptions. Delete them first");
			}

			DBManager.deleteVariableValues(vmd.getVmdName(),Long.MIN_VALUE,Long.MAX_VALUE);
			VarMetadataManager.delete(vmd.getVmdId());
			return RETURN_OK;
		} catch (BaseException e) {
			log.error("Exception deleting variable");
			throw new WSException("HomeWS_DELETEVARIABLE0",
					"Exception deleting variable", e);
		}
	}
	@Override
	public VariableI getVariableValue(String varName, Long date) throws WSException {
		log.debug("Getting variable values");
		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_GETVARIABLEVALUE1",
					"Variable name can NOT be empty");
		}
		if (date == null) {
			date=System.currentTimeMillis();
		}
		try {
			VariableI vr = HomeServer.getLastValue(varName,date);
			return vr;
		} catch (BaseException e) {
			log.error("Exception getting variable values");
			throw new WSException("HomeWS_GETVARIABLEVALUE0",
					"Exception getting variable values", e);
		}
	}
	
	@Override
	public List<VariableI> getVariableValues(
			String varName, 
			Long initialDate,
			Long finalDate, 
			Long duration, 
			String absolut) throws WSException {
		
		List<VariableI> varList = new ArrayList<VariableI>();
			try {
				log.debug("Getting variable values");
			
				if(varName==null||varName.isEmpty()){
					log.debug("Variable name can NOT be empty");
					throw new WSException("HomeWS_GETVARIABLEVALUES1","Variable name can NOT be empty");}
							
				if (!validMode(absolut)) {
					log.debug("Invalid absolut value");
					throw new WSException("HomeWS_GETVARIABLEVALUES3", "Invalid absolut value. Can ONLY be blank, 'absolut' OR 'incremental'");
				} 
				
				boolean acumul=false;
				if(absolut!=null&&absolut.equals(HomeWS.INCREMENTAL)){
					acumul=true;
				}
				
				if (finalDate == null) {
					finalDate = System.currentTimeMillis(); // Por defecto se le asigna
															// la fecha actual
				}

				if (initialDate == null) {
					if (duration != null && duration < finalDate)
						initialDate = finalDate - duration;
					else
						initialDate = (long) 0;
				} else {
					if (initialDate > finalDate) {
						throw new BaseException("HomeWS_GETVARIABLEVALUES4",
								"finalDate is later than initialDate, should be previous");
					}
				}
				
				List<ValueVar> l = DBManager.readVR(varName, initialDate,finalDate);
				
				if(acumul){					
					if (l.size() < 2) {
						throw new BaseException("HomeWS_GETVARIABLEVALUES5",
								"Not enough values to calculate incremental");
					}
					ValueVar last =l.get(0);
					
					try{
						new Double(last.getVrValue());
					}catch(Exception e){
						throw new BaseException("HomeWS_GETVARIABLEVALUES6",	"Values are not numerical");}	
					
					for (int i=1;i<l.size();i++){
						ValueVar vr= l.get(i);
						VariableI var = new VariableI();	
						var.setName(varName);
						//var.setPhysicalType(vmd.getVmdPhytype());
						//var.setMeasureUnit(vmd.getVmdMeasureunit());
						var.setTimestamp(vr.getVrTimestamp());
						
						Double value =new Double(vr.getVrValue())-new Double(last.getVrValue());
						var.setValue(value.toString());
						varList.add(var);						
						last=vr;
					}
					
					
				}else{
				for (ValueVar vr:l){
					VariableI var = new VariableI();	
					var.setName(varName);
					//var.setPhysicalType(vmd.getVmdPhytype());
					//var.setMeasureUnit(vmd.getVmdMeasureunit());
					var.setTimestamp(vr.getVrTimestamp());
					var.setValue(vr.getVrValue());
					varList.add(var);
				}}
				
				return varList;
				
			} catch (BaseException e) {
				log.error("Error getting variable values");
				throw new WSException("HomeWS_GETVARIABLEVALUES0",e.getMessage(), e);		
			}
		}
	
	public String linkVariable(String varName, String entityId,
			String entityType) throws WSException {
		log.debug("Linking variables");
		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_LINKVARIABLE1",
					"Variable name can NOT be empty");
		}

		if (entityId == null || entityId.isEmpty()) {
			log.debug("EntityId can NOT be empty");
			throw new WSException("HomeWS_LINKVARIABLE2",
					"EntityId can NOT be empty");
		}

		if (entityType == null || entityType.isEmpty()) {
			log.debug("EntityType can NOT be empty");
			throw new WSException("HomeWS_LINKVARIABLE3",
					"EntityType can NOT be empty");
		}

		if (!entityType.equals(deviceEntity) && !entityType.equals(homeEntity)) {
			log.debug("EntityType can only be home or device");
			throw new WSException("HomeWS_LINKVARIABLE4",
					"EntityType can only be home or device");
		}

		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd == null) {
				log.debug("Variable " + varName + " NOT found in database");
				throw new WSException("HomeWS_LINKVARIABLE5", "Variable "
						+ varName + " NOT found in database");
			}

			Device d = null;
			if (entityType.equals(deviceEntity)) {
				d = DeviceManager.findByName(entityId);
				if (d == null)
					throw new WSException("HomeWS_LINKVARIABLE6", "Device "
							+ entityId + " NOT found in database");
			}
			
			List dvList = DBInstanceManager.find(DeviceVarMetadata.class,
					"vmdId", vmd.getVmdId());
			if (!dvList.isEmpty()) {
				log.debug("Variable " + varName+ " is already linked to a device. Unlink first");
				throw new WSException("HomeWS_LINKVARIABLE7", 
						"Variable "+ varName + " is already linked to a device. Unlink first");
			}
			
			Home h = null;
			if (entityType.equals(homeEntity)) {
				h = HomeManager.findByName(entityId);
				if (h == null)
					throw new WSException("HomeWS_LINKVARIABLE8", "Home "
							+ entityId + " NOT found in database");
			}

			List hvList = DBInstanceManager.find(HomeVarMetadata.class,
					"vmdId", vmd.getVmdId());
			if (!hvList.isEmpty()) {
				log.debug("Variable " + varName
						+ " is already linked to a home. Unlink first");
				throw new WSException("HomeWS_LINKVARIABLE9", "Variable "
						+ varName + " is already linked to a home. Unlink first");
			}
			
			// SE BORRAN TODAS LAS POSIBLES RELACIONES
			DBInstanceManager.delete(DeviceVarMetadata.class,"vmdId",vmd.getVmdId());
			DBInstanceManager.delete(HomeVarMetadata.class,"vmdId",vmd.getVmdId());				

			if (entityType != null) {
				if (entityType.equals(deviceEntity)) {
					DBInstanceManager.save(new DeviceVarMetadata(d.getDevId(),
							vmd.getVmdId()));
				}
				if (entityType.equals(homeEntity)) {
					DBInstanceManager.save(new HomeVarMetadata(h.getHiId(), vmd
							.getVmdId()));
				}
			}
			return RETURN_OK;
		} catch (BaseException e) {
			log.error("Exception moving variable");
			throw new WSException("HomeWS_LINKVARIABLE0","Exception moving variable", e);			
		}
	}

	public String unlinkVariable(String varName) throws WSException {
		log.debug("Unlinking variables");
		if (varName == null || varName.isEmpty()) {
			log.debug("Variable name can NOT be empty");
			throw new WSException("HomeWS_UNLINKVARIABLE1",
					"Variable name can NOT be empty");
		}
		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd == null) {
				log.debug("Variable " + varName + " NOT found in database");
				throw new WSException("HomeWS_UNLINKVARIABLE2", "Variable "
						+ varName + " NOT found in database");
			}
			List dvList = DBInstanceManager.find(DeviceVarMetadata.class,
					"vmdId", vmd.getVmdId());
			List hvList = DBInstanceManager.find(HomeVarMetadata.class,
					"vmdId", vmd.getVmdId());

			if (dvList.isEmpty() && hvList.isEmpty()) {
				log.debug("Variable " + varName
						+ " is NOT linked to any device or home");
				throw new WSException("HomeWS_UNLINKVARIABLE3", "Variable "
						+ varName + " is NOT linked to any device or home");
			}
			if (!dvList.isEmpty())
				DBInstanceManager.delete(DeviceVarMetadata.class, "vmdId",
						vmd.getVmdId());
			if (!hvList.isEmpty())
				DBInstanceManager.delete(HomeVarMetadata.class, "vmdId",
						vmd.getVmdId());

			return RETURN_OK;
		} catch (BaseException e) {
			log.error("Exception unlinking variable");
			throw new WSException("HomeWS_UNLINKVARIABLE0",
					"Exception unlinking variable", e);
		}
	}

	// ----------------------TASK RELATED------------------------------
	public String addScheduledTask(String taskName, String desc, String period,
			String devName, String varName, String value) throws WSException {

		if (taskName == null || taskName.isEmpty()) {
			log.debug("Task name can NOT be empty");
			throw new WSException("HomeWS_ADDSCHEDULEDTASK1",
					"Task name can NOT be empty");
		}
		if (period == null || period.isEmpty()) {
			log.debug("Period can NOT be empty");
			throw new WSException("HomeWS_ADDSCHEDULEDTASK2",
					"Period can NOT be empty");
		}

		try {
			Task t = TaskManager.findByName(taskName);
			if (t != null) {
				log.debug("Task name " + taskName + " already in use");
				throw new WSException("HomeWS_ADDSCHEDULEDTASK3", "Task name "
						+ taskName + " already in use");
			}

			/*
			 * Device d = DeviceManager.findByName(devName); if (d == null) {
			 * log.debug("Device " + devName + " NOT found in database"); throw
			 * new WSException("HomeWS_ADDSCHEDULEDTASK4", "Device "+ devName +
			 * " NOT found in database"); }
			 * 
			 * VarMetadata vmd = VarMetadataManager.findByName(varName); if (vmd
			 * == null) { log.debug("Variable " + varName +
			 * " NOT found in database"); throw new
			 * WSException("HomeWS_ADDSCHEDULEDTASK5", "Variable "+ varName +
			 * " NOT found in database"); }
			 */

			t = new Task();
			t.setTaskName(taskName);
			t.setTaskDesc(desc);
			t.setTaskPeriod(period);
			t.setTaskValue(value);
			t.setTaskDev(devName);
			t.setTaskVar(varName);

			TaskManager.save(t);
			return taskName;

		} catch (BaseException e) {
			log.error("Exception adding task");
			throw new WSException("HomeWS_ADDSCHEDULEDTASK0",
					"Exception adding scheduled task", e);
		}
	}

	public String editScheduledTask(String taskName, String desc,
			String period, String devName, String varName, String value)
			throws WSException {
		if (taskName == null || taskName.isEmpty()) {
			log.debug("Task name can NOT be empty");
			throw new WSException("HomeWS_EDITSCHEDULEDTASK1",
					"Task name can NOT be empty");
		}
		if (period == null || period.isEmpty()) {
			log.debug("Period can NOT be empty");
			throw new WSException("HomeWS_EDITSCHEDULEDTASK2",
					"Period can NOT be empty");
		}

		try {
			Task t = TaskManager.findByName(taskName);
			if (t == null) {
				log.debug("Task name " + taskName + " NOT found in database");
				throw new WSException("HomeWS_EDITSCHEDULEDTASK3",
						"Task NOT found in database");
			}

			/*
			 * Device d = DeviceManager.findByName(devName); if (d == null) {
			 * log.debug("Device " + devName + " NOT found in database"); throw
			 * new WSException("HomeWS_EDITSCHEDULEDTASK4", "Device "+ devName +
			 * " NOT found in database"); }
			 * 
			 * VarMetadata vmd = VarMetadataManager.findByName(varName); if (vmd
			 * == null) { log.debug("Variable " + varName +
			 * " NOT found in database"); throw new
			 * WSException("HomeWS_EDITSCHEDULEDTASK5", "Variable "+ varName +
			 * " NOT found in database"); }
			 */

			t.setTaskDesc(desc);
			t.setTaskPeriod(period);
			t.setTaskValue(value);
			t.setTaskDev(devName);
			t.setTaskVar(varName);

			TaskManager.update(t);
			return RETURN_OK;

		} catch (BaseException e) {
			log.error("Exception editing scheduled task");
			throw new WSException("HomeWS_EDITSCHEDULEDTASK0",
					"Exception editing scheduled task", e);
		}
	}

	public String deleteScheduledTask(String taskName) throws WSException {
		if (taskName == null || taskName.isEmpty()) {
			log.debug("Task name can NOT be empty");
			throw new WSException("HomeWS_DELETESCHEDULEDTASK1",
					"Task name can NOT be empty");
		}
		try {
			Task t = TaskManager.findByName(taskName);
			if (t == null) {
				log.debug("Task " + taskName + " NOT found in database");
				throw new WSException("HomeWS_DELETESCHEDULEDTASK2",
						"Task NOT found in database");
			}

			TaskManager.delete(t.getTaskId());
			return RETURN_OK;

		} catch (BaseException e) {
			log.error("Exception editing scheduled task");
			throw new WSException("HomeWS_DELETESCHEDULEDTASK0",
					"Exception deleting scheduled task", e);
		}
	}

	public List<TaskI> getDeviceScheduledTasks(String devName)
			throws WSException {
		if (devName == null || devName.isEmpty()) {
			log.debug("Device name can NOT be empty");
			throw new WSException("HomeWS_GETSCHEDULEDTASKS1",
					"Device name can NOT be empty");
		}
		try {
			Device d = DeviceManager.findByName(devName);
			if (d == null) {
				log.debug("Device " + devName + " NOT found in database");
				throw new WSException("HomeWS_GETSCHEDULEDTASKS2", "Device "
						+ devName + " NOT found in database");
			}

			List<TaskI> taskList = new ArrayList<TaskI>();
			for (Task t : TaskManager.findAll()) {
				if (t.getTaskDev().equals(devName)) {
					TaskI tI = new TaskI();
					tI.setName(t.getTaskName());
					tI.setDesc(t.getTaskDesc());
					tI.setPeriod(t.getTaskPeriod());
					tI.setDevName(t.getTaskDev());
					tI.setVarName(t.getTaskVar());
					tI.setValue(t.getTaskValue());
					taskList.add(tI);
				}
			}
			return taskList;

		} catch (BaseException e) {
			log.error("Exception adding task");
			throw new WSException("HomeWS_GETSCHEDULEDTASKS0",
					"Exception getting device tasks", e);
		}
	}
//--------------------------------------
	
	
	public String addValue(String varName, String value, long timestamp)throws WSException {
		log.debug("Adding variable value");
		if (varName == null || varName.isEmpty()) {
			log.error("Variable name can NOT be empty");
			throw new WSException("HomeWS_ADDVALUE1","Variable name can NOT be empty");
		}

		//TODO SE PERMITEN VALORES EN BLANCO, PERO NO NULOS
		if (value == null) {
			log.error("Value can NOT be empty");
			throw new WSException("HomeWS_ADDVALUE2","Value can NOT be null");
		}
		
		try {		
			DBManager.saveVR(varName, value, timestamp);
			
			return RETURN_OK;
		} catch (BaseException e) {
			log.error("Error adding variable value");
			throw new WSException("HomeWS_ADDVALUE0",
					"Error adding variable value", e);
		}
	}

	
	// ----------------------------------------------------------------

	public Double getHomeEmissions(String homeId, long startDate, long endDate)
			throws WSException {
		throw new NotImplementedException();
	}

	public String sendNotification(String alarmId, String notificationId,
			long timeSpan, String message) throws WSException {
		throw new NotImplementedException();
	}
	public String configureAlarmNotification(String homeName,
			String localization, String category, String priority,
			String endpoint, String condition) throws WSException {
		throw new NotImplementedException();
	}

	/*public boolean sendTargetValue(String varId, String targetValue)
			throws WSException {
		throw new NotImplementedException();
	}*/

	// -----------------------------------------
	
	public String getMaxResults() throws WSException {
		return DatabaseProperties.getProperty("maxResults");
	}

	public String setMaxResults(String maxResults) throws WSException {
		try{
			new Integer(maxResults);
		}catch(Exception e){
			throw new WSException("HomeWS_SETMAXRESULTS0","Given value is not a integer");
		}
		
		DatabaseProperties.setProperty("maxResults", maxResults);
		return RETURN_OK;
	}

//------------------------------	
	
	public boolean validMode(String mode){
		return (mode==null||mode.isEmpty()||mode.equals(HomeWS.ABSOLUT)||mode.equals(HomeWS.INCREMENTAL));
	}
	
	public String createURI(String varName, String store){
		return "/"+store+"/"+varName;
	}

	// --
	public static void main(String[] args) {
		try {
			HomeWS hs= new HomeWS();
			System.out.println("-------------------");
			
			/*List<VariableI> l =hs.getVariableValues("EACO:CLIMA REST",null,null,null,null);
			for(VariableI v:l){
				System.out.println(v.getValue()+"["+new Date(v.getTimestamp())+"]");
			}
			System.out.println(l.size()+" medidas");
			
			VariableI c =hs.getVariableValue("EACO:CLIMA REST",null);
			System.out.println(c.getValue()+"["+new Date(c.getTimestamp())+"]");*/
			cargarCSV(hs);
			
			HectorFactory.exit();
		} catch (Exception e) {
			System.err.println("ERROR_WS:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void cargarCSV(HomeWS hs) throws WSException, ParseException{
		//CREAR VARIABLES TIBUCON
		
		//hs.addHome("BEC_OFICINAS",null,null); //ODO
		//for (int i=26;i<52;i++){
		//	hs.addDevice("BEC_OFICINAS", "Tibucon_"+i,"Tibucon_"+i,"/BEC/Oficinas/Tibucon"+i, null, null, null, null); //TODO
		//	hs.addVariable("Tibucon_"+i+"_TEMP", "Tibucon_"+i, "device",null, "Temperature", "double", "ºC", null, "Temperature (Tibucon "+i+")", "PostgreSQL");// TODO
		//	hs.addVariable("Tibucon_"+i+"_HUM", "Tibucon_"+i, "device",null, "Hunidity", "double", "%", null, "Humidity (Tibucon "+i+")", "PostgreSQL");///TODO 
		//	hs.addVariable("Tibucon_"+i+"_LUM", "Tibucon_"+i, "device",null, "Temperature", "double", "Lum", null, "Luminosity (Tibucon "+i+")", "PostgreSQL");//TODO
		//}
		
		
		
		
		//CARGAR CSV
		String csvFile = "BEC-EXPORT-20141015_1530.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<String> errorLines= new ArrayList<String>();
		try {
	 
			br = new BufferedReader(new FileReader(csvFile));
			br.readLine();//Omitir cabecera
			while ((line = br.readLine()) != null) {
	 		  
				try{
				// use comma as separator
				String[] arr = line.split(cvsSplitBy);
				String date =arr[1].replace("\"","");
				String varId =arr[3];
				String temp =arr[4];
				String hum =arr[5];
				String lum =arr[6];
				Date d = sdf.parse(date);
				hs.addValue("Tibucon_"+varId+"_TEMP", temp, d.getTime());//TODO
				hs.addValue("Tibucon_"+varId+"_HUM", hum, d.getTime());//TODO
				hs.addValue("Tibucon_"+varId+"_LUM", lum, d.getTime());}//TODO
				catch(Exception e){
					errorLines.add(line);
				}
			}
	 
		} catch (FileNotFoundException e) {
			System.err.println("ERROR1:"+e);e.printStackTrace();
		} catch (IOException e) {

			System.err.println("ERROR2:"+e);e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					System.err.println("ERROR3:"+e);e.printStackTrace();
				}
			}
		}
	 
		System.out.println("Done");
		System.out.println("Errors detected in "+errorLines.size() +" lines:");
		for(String s :errorLines){
			System.out.println(s);
		}
	  }
	
}
