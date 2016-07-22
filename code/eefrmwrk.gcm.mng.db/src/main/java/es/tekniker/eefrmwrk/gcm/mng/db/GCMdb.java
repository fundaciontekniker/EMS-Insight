package es.tekniker.eefrmwrk.gcm.mng.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.orm.PersistentException;
import org.orm.PersistentSession;
import org.orm.PersistentTransaction;

import es.tekniker.eefrmwrk.gcm.mng.dao.domain.GCMPersistentManager;
import es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration;
import es.tekniker.eefrmwrk.gcm.mng.dao.domain.GcmregistrationCriteria;

public class GCMdb {
	private static Log log =LogFactory.getLog(GCMdb.class);
	
	public static int GCMREGISTRATION_ID_ENABLED_FALSE=0;
	public static int GCMREGISTRATION_ID_ENABLED_TRUE=1;
	
	
	public Gcmregistration[] getListRegistrationEnabled(){
		PersistentSession session = null;
		GcmregistrationCriteria gcmregistrationCriteria = null;
		Gcmregistration[] array = null;
		
		try{

			session = GCMPersistentManager.instance().getSession();	
			
			gcmregistrationCriteria = new GcmregistrationCriteria();
			gcmregistrationCriteria.enabled.eq(GCMREGISTRATION_ID_ENABLED_TRUE);			
			
			array= Gcmregistration.listGcmregistrationByCriteria(gcmregistrationCriteria);
			
			if(array != null)
				log.debug("getListRegistrationEnabled array is not null " );
			else 
				log.debug("getListRegistrationEnabled array is null");
			
			
			session.close();
			
		}catch (Exception e) {
			log.error(" getListRegistrationEnabled Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return array;
	}
	
	public Gcmregistration getRegistrationId(long gcmregistration_id){
		PersistentSession session = null;		
		Gcmregistration instance = null;
		
		try{
			session = GCMPersistentManager.instance().getSession();
			instance = Gcmregistration.getGcmregistrationByORMID(gcmregistration_id);
			
			if(instance != null)
				log.debug("getRegistrationId instance is not null " );
			else 
				log.debug("getRegistrationId instance is null");
			
			session.close();
			
		}catch (Exception e) {
			log.error(" getRegistrationId Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instance;
	}
	
	public Gcmregistration getRegistrationIdByRegId(String regId){
		PersistentSession session = null;		
		Gcmregistration instance = null;
		GcmregistrationCriteria gcmregistrationCriteria =null;
		try{

			session = GCMPersistentManager.instance().getSession();				
			gcmregistrationCriteria = new GcmregistrationCriteria ();
			gcmregistrationCriteria.registration_id.eq(regId);
			instance = Gcmregistration.loadGcmregistrationByCriteria(gcmregistrationCriteria);
			
			if(instance != null)
				log.debug("getRegistrationIdByRegId instance is not null " );
			else 
				log.debug("getRegistrationIdByRegId instance is null");
			
			session.close();
			
		}catch (Exception e) {
			log.error(" getRegistrationIdByRegId Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instance;
	}
	
	public boolean insertRegistrationId(String registrationId){
		boolean boolOK = false;
		PersistentSession session = null;
		PersistentTransaction tr = null;
		Gcmregistration instance = null;
		
		if(registrationId != null){
		
			try{
	
				instance = new Gcmregistration();
				instance.setRegistration_id(registrationId);
				instance.setEnabled(GCMdb.GCMREGISTRATION_ID_ENABLED_TRUE);
								
				session = GCMPersistentManager.instance().getSession();
				tr = session.beginTransaction();
								
				session.save(instance);
				
				if(tr.wasCommitted()==false)
					tr.commit();
				
				session.close();
				
				boolOK =true;
			
			}catch (Exception e) {
				log.error(" insertRegistrationId Exception " + e.getMessage());			
				e.printStackTrace();		
				try {
					if(tr!=null)
						tr.rollback();
					
					if(session!=null)
						session.close();
				} catch (PersistentException e1) {
					e1.printStackTrace();
				}
			}	
		}else{
			log.error(" insertRegistrationId registrationId is NULL");
		}
		
		if(boolOK)
			log.debug(" insertRegistrationId TRUE ");
		else
			log.debug(" insertRegistrationId FALSE");
					
		return boolOK;
	}
	
}
