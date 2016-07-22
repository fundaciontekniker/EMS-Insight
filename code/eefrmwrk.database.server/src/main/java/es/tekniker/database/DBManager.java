package es.tekniker.database;

import java.util.ArrayList;
import java.util.List;

import me.prettyprint.hector.api.beans.HColumn;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.config.DatabaseProperties;
import es.tekniker.eefrmwrk.database.nosql.HectorFactory;
import es.tekniker.eefrmwrk.database.sql.manage.HibernateUtil;
import es.tekniker.eefrmwrk.database.sql.manage.OpcNodeManager;
import es.tekniker.eefrmwrk.database.sql.manage.OpcServerManager;
import es.tekniker.eefrmwrk.database.sql.manage.OpcSubscriptionManager;
import es.tekniker.eefrmwrk.database.sql.manage.ValueVarManager;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.OpcNode;
import es.tekniker.eefrmwrk.database.sql.model.OpcServer;
import es.tekniker.eefrmwrk.database.sql.model.OpcSubscription;
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

public class DBManager {
	
	
	private static final Log log = LogFactory.getLog(DBManager.class);
	
	/**
	 * Lee los valores para la variable "vmd". Primero busca en que base de
	 * datos se guarda: PostgreSQL o Cassandra
	 * 
	 */
	public static List<ValueVar> readVR(String varName, Long start, Long end) throws BaseException{
		List<ValueVar> vrList = new ArrayList<ValueVar>();
		Session session= null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			VarMetadata vmd = VarMetadataManager.findByName(varName,session);
			if (vmd == null) {
				throw new BaseException("DBManager_READVR1","Variable NOT found in DB");
			} else {
				
				int maxResults = 10000;
					try {
						maxResults = new Integer(DatabaseProperties.getProperty("maxResults"));
					} catch (Exception e) {	}
				
				String bd = vmd.getVmdStoredbd();
				if (bd.equals(VarMetadata.Cassandra)) {
					List<HColumn<Long, Object>> l = HectorFactory
							.getInstance()
							.getValues(
									DatabaseProperties.getProperty("NoSQL_VRColFam"),
									vmd.getVmdId(), 
									maxResults, start,end,false);								
									
					for (HColumn<Long, Object> v : l) {
						ValueVar vr = new ValueVar(vmd.getVmdId(),v.getValue() + "", v.getName());
						vrList.add(vr);
					}
				}else if (bd.equals(VarMetadata.PostgreSQL))
					vrList = ValueVarManager.getValues(vmd.getVmdId(), start,end, maxResults, true,session);	
				else 
					throw new BaseException("DBManager_READVR2","Invalid storage DB "+bd);	
				
			}

			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (Exception e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw new BaseException("DBManager_READVR0","Exception reading values", e);
					
		}
		return vrList;
	}
	
	public static ValueVar getLastVR (String varName,Long date) throws BaseException{
		ValueVar vr = null;
		Session session= null;
		try {
			log.debug("Opening session");
			session = HibernateUtil.currentSession();
			session.beginTransaction();

			vr=getLastVR(varName,date,session);
			
			session.getTransaction().commit();
			log.debug("Closing session");
			HibernateUtil.closeSession();
		} catch (Exception e) {
			session.getTransaction().rollback();
			log.debug("Closing session");
			HibernateUtil.closeSession();
			throw new BaseException("DBManager_READVR0","Exception reading values", e);
					
		}
		return vr;
	}
	
	
	public static ValueVar getLastVR (String varName,Long date,Session session) throws BaseException{
	
			VarMetadata vmd = VarMetadataManager.findByName(varName,session);
			if (vmd == null) {
				throw new BaseException("HomeServer_READLASTVARIABLE1","Variable "+ varName +" not found in database");
			}
		
			ValueVar vr=null;			
			String bd = vmd.getVmdStoredbd();
			if (bd.equals(VarMetadata.Cassandra)) {
				try{
				List<HColumn<Long, Object>> l = HectorFactory.getInstance()
						.getValues(
								DatabaseProperties.getProperty("NoSQL_VRColFam"),//"ValueVar",
								vmd.getVmdId(), 
								1,
								Long.MIN_VALUE, 
								date - 1, 
								true);
				if (l.size()>0)
				vr = new ValueVar(vmd.getVmdId(), l.get(l.size() - 1).getValue() + "",
						l.get(l.size() - 1).getName());}
				catch(Exception e){
					throw new BaseException("HomeServer_READLASTVARIABLE2", "Error getting values from Cassandra database", e);				
				}			
			}
			if (bd.equals(VarMetadata.PostgreSQL)) {
				List<ValueVar> vrList = ValueVarManager.getValues(vmd.getVmdId(),new Long(0), date-1,1, false,session);
				if(vrList.size()>0)
					vr = vrList.get(0);
			}	
		return vr;
	} 

	public static void saveVR(String varName, String varValue, long varTimestamp,Session session) throws Exception {
		
				VarMetadata vmd = VarMetadataManager.findByName(varName,session);
				if(vmd==null){
					throw new BaseException("DBManager_SAVEVR1","Variable NOT found in DB");
				}
				
				CheckType(vmd.getVmdDigtype(),varValue);
				
				ValueVar vr = new ValueVar(vmd.getId(), varValue, varTimestamp);
				String bd = vmd.getVmdStoredbd().trim();
				if (bd.equals(VarMetadata.Cassandra)) {
					HectorFactory.getInstance().updateCol(
							DatabaseProperties.getProperty("NoSQL_VRColFam"),
							vmd.getVmdId(), vr.getVrTimestamp(),
							vr.getVrValue());
				} else if (bd.equals(VarMetadata.PostgreSQL)) {
					ValueVarManager.save(vr,session);
				} else {
					throw new BaseException("DBManager_SAVEVR2","Unknown storage DB "+bd);	
				}	
	}	
	
	public static void saveVR(String varName, String varValue, long varTimestamp) throws BaseException {
		Session session= null;
		try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();
				
				saveVR(varName, varValue, varTimestamp,session);
				
				session.getTransaction().commit();
				log.debug("Closing session");
				HibernateUtil.closeSession();					
			}
			catch (Exception e){
				session.getTransaction().rollback();					
				log.debug("Closing session");
				HibernateUtil.closeSession();		
				throw new BaseException("DBManager_SAVEVR0","Exception saving value", e);
					
			}
	}	
	
	public static long saveOPCNode(String url, String nodeName, String nodeNmspc, String value, long timestamp) throws BaseException {
		Session session= null;
		Long subId=(long)-1;
		try {
				log.debug("Opening session");
				session = HibernateUtil.currentSession();
				session.beginTransaction();
				
				OpcServer opS = OpcServerManager.findByUrl(url,session);
				if(opS==null){
					throw new BaseException("DBManager_SAVEOPCNODE1","OPC-UA Url NOT found in DB");
				}
				OpcNode opn = OpcNodeManager.findInServer(opS.getOpcServerId(),nodeName, nodeNmspc,session);
				if(opn==null){
					throw new BaseException("DBManager_SAVEOPCNODE2","OPC-UA Node NOT found in DB");
				}		
				OpcSubscription sub = OpcSubscriptionManager.findByNode(opn.getOpcNodeId(),session);
				if(sub==null){
					throw new BaseException("DBManager_SAVEOPCNODE3","OPC-UA Subscription NOT found in DB");
				}
				subId=sub.getOpcSubId();
				
				VarMetadata vmd = VarMetadataManager.find(sub.getOpcVmd(),session);
				if(vmd==null){
					throw new BaseException("DBManager_SAVEOPCNODE4","Variable NOT found in DB");
				}

				ValueVar vr = new ValueVar(vmd.getVmdId(),value,timestamp);
						
				CheckType(vmd.getVmdDigtype(),value);
				
				String bd = vmd.getVmdStoredbd().trim();
				if (bd.equals(VarMetadata.Cassandra)) {
					HectorFactory.getInstance().updateCol(
							DatabaseProperties.getProperty("NoSQL_VRColFam"),
							vmd.getVmdId(), vr.getVrTimestamp(),
							vr.getVrValue());
					
					
				} else if (bd.equals(VarMetadata.PostgreSQL)) {
					ValueVarManager.save(vr,session);
				} else {
					throw new BaseException("DBManager_SAVEOPCNODE5","Unknown storage DB");	
				}	
						
				session.getTransaction().commit();
				log.debug("Closing session");
				HibernateUtil.closeSession();
				
				return sub.getOpcSubId();
			}
			catch (Exception e){
				session.getTransaction().rollback();					
				log.debug("Closing session");
				HibernateUtil.closeSession();		
				throw new BaseException("DBManager_SAVEOPCNODE0","Exception saving value", e,subId);					
			}
		}	
	
	/**
	 * Coge los valores antiguos y los archiva en otra familia
	 */
	public static String deleteVariableValues(String varName,Long minDate,Long maxDate) {
		try {
			VarMetadata vmd = VarMetadataManager.findByName(varName);
			if (vmd == null) {
				log.error("Variable not found");
			}

			if (vmd.getVmdStoredbd().equals(VarMetadata.PostgreSQL)) {
				// BORRAMOS VALORES
				ValueVarManager.deleteByVarMetadata(vmd.getId(),minDate,maxDate);
			}
			if (vmd.getVmdStoredbd().equals(VarMetadata.Cassandra)) {
				// OBTENEMOS VALORES
				List<HColumn<Long, Object>> l = HectorFactory.getInstance()
						.getValues(
								DatabaseProperties.getProperty("NoSQL_VRColFam"),
								vmd.getVmdId(), Integer.MAX_VALUE,
								minDate, maxDate, false);
				// INTRODUCIMOS VALORES
				for (HColumn<Long, Object> v : l) {
					HectorFactory.getInstance().updateCol("H_ValueVar",vmd.getVmdId(), v.getName(), v.getValue());
							
				}
				// BORRAMOS VALORES
				HectorFactory.getInstance().deleteCol("ValueVar",vmd.getVmdId());
			}

		} catch (Exception e) {
			log.error("Error deleting values for variable " + varName);
		}
		return "";
	}

	/*
	 * public static List<GeneralElectricMeasureI> readGEM(String devName, Long
	 * start, Long end)throws BaseException {
	 * 
	 * Device dev= DeviceManager.findByName(devName); HashMap<Long, ValueVar>
	 * varMap = new HashMap<Long, ValueVar>(); HashMap<Long, VarMetadata>
	 * vmdMap = new HashMap<Long, VarMetadata>(); List<ValueVar> vrList = new
	 * ArrayList<ValueVar>();
	 * 
	 * for (VarMetadata v : VarMetadataManager.findByDevice(dev.getDevId())) {
	 * vmdMap.put(v.getVmdId(),v); List<ValueVar> l = read(v.getVmdId(), start,
	 * end); varMap.put(v.getVmdId(), l.get(0)); vrList.addAll(l); }
	 * 
	 * GeneralElectricMeasureI GEM = new GeneralElectricMeasureI();
	 * GEM.setDeviceId(devName); Date last = new Date(Long.MIN_VALUE); for
	 * (VarMetadata v : vmdMap.values()) {
	 * System.out.println(v.getVmdName()+":"+
	 * varMap.get(v.getVmdId()).getVrTimestamp());
	 * GEM.setProperty(v.getVmdPhytype(),Id
	 * varMap.get(v.getVmdId()).getVrValue()); if
	 * (last.compareTo(varMap.get(v.getVmdId()).getVrTimestamp()) < 0) last =
	 * varMap.get(v.getVmdId()).getVrTimestamp(); }
	 * GEM.setTimestamp(last.getTime());
	 * System.out.println("GEM completo a fecha: "+last);
	 * 
	 * ArrayList<GeneralElectricMeasureI> gemList = new
	 * ArrayList<GeneralElectricMeasureI>(); gemList.add(GEM);
	 * 
	 * Collections.sort(vrList, new ValueVar.ValueVar_DateComparator()); for
	 * (ValueVar vr : vrList) {
	 * //System.out.println(vmdMap.get(vr.getVrVmd()).getVmdName
	 * ()+"["+vr.getVrValue()+"]:"+vr.getVrTimestamp());
	 * if(vr.getVrTimestamp().compareTo(last)<=0){ //TODO NO SE INCLUYEN ESTAS
	 * MEDIDAS PORQUE NO SE HA PODIDO COMPLETAR UN GEM COMPLETO }else{
	 * VarMetadata vmd = vmdMap.get(vr.getVrVmd()); GeneralElectricMeasureI g
	 * =gemList.get(gemList.size()-1).copyOf();
	 * 
	 * if(g.getTimestamp()==vr.getVrTimestamp().getTime())
	 * gemList.remove(gemList.size()-1); else
	 * g.setTimestamp(vr.getVrTimestamp().getTime());
	 * 
	 * if(g.getProperty(vmd.getVmdPhytype()).equals(vr.getVrValue()))
	 * gemList.remove(gemList.size()-1); else g.setProperty(vmd.getVmdPhytype(),
	 * vr.getVrValue()); gemList.add(g); } } System.out.println(gemList.size());
	 * return gemList;
	 * 
	 * }
	 */

	public static void saveGEM(long id, long timestamp, Object value)
			throws Exception {
		// System.out.println("GUARDANDO medida de "+gem.getDeviceId()+"["+gem.getTimestamp()+"]");
		HectorFactory.getInstance().updateCol("GeneralElectric",// DatabaseProperties.getProperty("NoSQL_GEMColFam"),
				id, timestamp, value);

	}

	
	  public static void main(String[] args) throws Exception { 
	  
		// DBManager.deleteVariableValues("Tibucon_1_Temp_1124682048",1421623400244L, 1421627300562L);
		for( ValueVar v: DBManager.readVR("Tibucon_4_Lum_1124683841", Long.MIN_VALUE, Long.MAX_VALUE)){
			 System.out.println(v.getVrValue()+":"+v.getVrTimestamp());
		 }
		 
		 HectorFactory.exit();
	  }
	 
	
	//--------------------------------
	private static Object CheckType(String type, String value) throws BaseException {
		Object rVal;
		if(type==null||type.isEmpty()){
			type=VarMetadata.DigType_string;}	
		else{
			type=type.trim();}
			if (type.equals(VarMetadata.DigType_boolean)) {
				rVal = new Boolean(value);
				log.debug("Converting value to boolean:" + value);
			} else if (type.equals(VarMetadata.DigType_integer)) {
				rVal = new Integer(value);
				log.debug("Converting value to integer:" + value);
			} else if (type.equals(VarMetadata.DigType_double)) {
				rVal = new Double(value);
				log.debug("Converting value to double:" + value);
			} else if (type.equals(VarMetadata.DigType_float)) {
				rVal = new Float(value);
				log.debug("Converting value to float:" + value);
			} else if (type.equals(VarMetadata.DigType_long)) {
				rVal = new Long(value);
				log.debug("Converting value to long:" + value);
			} else if (type.equals(VarMetadata.DigType_string)) {
				rVal = value;
				log.debug("Converting value to string:" + value);
			}else{
				log.error("Unknown type:"+type);
				throw new BaseException("DBManager_TOVMDTYPE1","Unknown type:"+type);
			}
		return rVal;
	}
}
