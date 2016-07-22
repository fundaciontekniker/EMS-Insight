package es.tekniker.database;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.manage.HibernateUtil;
import es.tekniker.eefrmwrk.database.sql.manage.ValueReadManager;
import es.tekniker.eefrmwrk.database.sql.manage.ValueVarManager;
import es.tekniker.eefrmwrk.database.sql.manage.VarMetadataManager;
import es.tekniker.eefrmwrk.database.sql.model.ValueRead;
import es.tekniker.eefrmwrk.database.sql.model.ValueVar;
import es.tekniker.eefrmwrk.database.sql.model.VarMetadata;

public class VR_Transform {
	private static final Log log = LogFactory.getLog(VR_Transform.class);

public static void volcadoDatosPorVariable() throws BaseException{
		

		List<VarMetadata> vmdList = VarMetadataManager.findAll();
		for(VarMetadata vmd: vmdList){
			
	
		List<ValueRead> vL= ValueReadManager.findByVarMetadata(vmd.getVmdId());
		
		log.info("Variable "+ vmd.getVmdName() +":"+vL.size() + " valores");
	
		Session session = null;
		try{
		session =HibernateUtil.currentSession();				
		session.beginTransaction();
			for(ValueRead vr:vL){
				ValueVar vv = new ValueVar();
				vv.setVrId(vr.getVrId());
				vv.setVrTimestamp(vr.getVrTimestamp().getTime());
				vv.setVrValue(vr.getVrValue());
				vv.setVrVmd(vr.getVrVmd());
				vv.setActiv(vr.getActiv());
				ValueVarManager.save(vv,session);
			}
		
		session.getTransaction().commit();
		HibernateUtil.closeSession();
		
		}catch(Exception e){
			session.getTransaction().rollback();					
			log.error(e.getMessage());
			HibernateUtil.closeSession();		
		}
		log.info("Variable "+ vmd.getVmdName() +" volcada");
		}
	}
	
	  public static void main(String[] args) throws Exception { 
	  
		  volcadoDatosPorVariable();
	  }
	
}
