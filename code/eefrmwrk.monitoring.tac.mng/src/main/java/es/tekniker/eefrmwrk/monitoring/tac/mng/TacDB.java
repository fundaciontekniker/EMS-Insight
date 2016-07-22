package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.orm.PersistentException;
import org.orm.PersistentSession;

import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogCriteria;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogDAO;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue;

public class TacDB {
	
	private static Log log =LogFactory.getLog(TacDB.class);
	
	
	public TrendLog getTrendLog(short id){
		TrendLog instance =null;	
		PersistentSession session = null;
		TrendLogCriteria objTrendLogCriteria = null;
				
		try{

			session = TACPersistentManager.instance().getSession();	
			
			objTrendLogCriteria = new TrendLogCriteria();
			objTrendLogCriteria.trendLogId.eq(id);
			
			instance = TrendLogDAO.loadTrendLogByCriteria(objTrendLogCriteria);
			
			if(instance != null)
				log.debug("test is not null " );
			else 
				log.debug("test is null");
			
			
			session.close();
			
		}catch (Exception e) {
			log.error(" getLanguageDefault Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return instance;		
	}	
	
	public TrendLogValue getTrendLogValue (int id){
		TrendLogValue instanceValue = null;
		PersistentSession session = null;
		List<TrendLogValue> list = null; 
		try{

			session = TACPersistentManager.instance().getSession();
			
			Query query = session.createSQLQuery(
					"SELECT TOP 1[TrendLogId],[LogTime], [ValueType],[LogValue], [Sequence]  "
					+ "FROM [TrendLogValue]  "
					+ "WHERE [TrendLogId] = :id  "
					+ "ORDER BY LogTime DESC")
					.addEntity(TrendLogValue.class)
					.setParameter("id", id);
			
			list = query.list();
					
			if(list !=null){
				log.debug("getTrendLogValue value found . Num of items : " + list.size());
				
				if(list.size()>0 ){
					instanceValue= list.get(0);
					log.debug("getTrendLogValue "  + instanceValue.getLogTime() + " - " + instanceValue.getLogValue() + " - "  + instanceValue.getTrendLog().getName());					
				}
			}
			else
				log.debug("getTrendLogValue no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getTrendLogValue Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}
	
	public TrendLogValue getTrendLogValueH (short id){
		TrendLogValue instanceValue = null;
		TrendLog instance = null;
		PersistentSession session = null;
		List<TrendLog> list = null;
		int num =1;
		try{
			//https://docs.jboss.org/hibernate/core/3.3/reference/es-ES/html/querycriteria.html 
			
			session = TACPersistentManager.instance().getSession();
			
			Criteria criteria = session.createCriteria(TrendLog.class);			
			criteria.add(Restrictions.eq("trendLogId", id));			
			criteria.createCriteria("ORM_TrendLogValue").addOrder(Order.desc("logTime"));
			
			
			//Criteria criteriaValue = criteria.createCriteria("ORM_TrendLogValue");
			//criteriaValue.setProjection( Projections.alias( Projections.groupProperty("sequence"), "sequence" ) );
			//criteriaValue.addOrder( Order.desc("sequence") );
			//criteriaValue.setMaxResults(1);
			/*
			ProjectionList projectionList = Projections.projectionList();
			projectionList.add(Projections.groupProperty("ORM_TrendLog"));
			projectionList.add(Projections.groupProperty("sequence"));
			projectionList.add(Projections.count("ORM_TrendLog"), "sequence");
			criteria.setProjection(projectionList);
			criteria.addOrder(Order.desc(("sequence")));
			//return criteria.list();
			*/
			
		
			
			list = criteria.list();
			//list = criteria.list();
			
			
			if(list !=null){
				System.out.println(" getTrendLogValue VALUE FOUND . NUM OF ITEMS " + list.size());
				
				if(list.size()>0 ){
					System.out.println(" getTrendLogValue VALUE FOUND . Item 0 NUM OF Values " + list.size());
					Iterator<TrendLog> it = list.iterator();
					while(it.hasNext()){
						instance = it.next();
						log.debug(" getTrendLogValue " +num + " : " +instance.getMaxSequence() ); 
						
						//Criteria criteriaValue = session.createCriteria(TrendLogValue.class);			
						//criteriaValue.add(Restrictions.eq("sequence", instance.getMaxSequence()));
						
						//List<TrendLogValue> listValue = criteriaValue.list();
						
						//if(listValue !=null && listValue.size()>0){
								
						//	instanceValue = listValue.get(0);
							
						//	log.debug( " - " + instanceValue.getLogTime() + " - " + instanceValue.getLogValue() + " - "  + instanceValue.getTrendLog().getName());
						//}	
						
					}					
				}
			}
			else
				System.out.println("getTrendLogValue no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getTrendLogValue Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}
	
	
	public TrendLogValue getTrendLogValueHCriteria (short id){
		TrendLogValue instanceValue = null;
		PersistentSession session = null;
		TrendLog[] list = null; 
		TrendLogCriteria objTrendLogCriteria = null;
		int num =1;
		try{

			session = TACPersistentManager.instance().getSession();
			
			objTrendLogCriteria = new TrendLogCriteria();
			objTrendLogCriteria.trendLogId.eq(id);			
			objTrendLogCriteria.createTrendLogValueCriteria().addOrder(Order.asc("sequence"));
			
			
			list = TrendLogDAO.listTrendLogByCriteria(objTrendLogCriteria);
			
			if(list !=null){
				System.out.println(" getTrendLogValue VALUE FOUND . NUM OF ITEMS " + list.length);
				
				if(list.length>0 ){
					System.out.println(" getTrendLogValue VALUE FOUND . Item 0 NUM OF Values " + list[0].trendLogValue.size());
					Iterator<TrendLogValue> it = list[0].trendLogValue.getIterator();
					while(it.hasNext()){
						instanceValue = it.next();
						log.debug(" getTrendLogValue " +num + " : " +instanceValue.getSequence() + " - " + instanceValue.getLogTime() + " - " + instanceValue.getLogValue() + " - "  + instanceValue.getTrendLog().getName());
						num = num+1;
					}
					
					
					//instanceValue= list[0].trendLogValue ;
					//log.debug(" getTrendLogValue "  + instanceValue.getLogTime() + " - " + instanceValue.getLogValue() + " - "  + instanceValue.getTrendLog().getName());					
				}
			}
			else
				System.out.println("getTrendLogValue no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getTrendLogValue Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}

	public Event getAirConditiongCalendar(long id, String type) {
		Event instanceValue = null;
		PersistentSession session = null;
		List<Event> list = null; 
		try{

			session = TACPersistentManager.instance().getSession();
			
			Query query = session.createSQLQuery(
					
					//"SELECT TOP 1 [EventId], [EventTime], [EventTypeId], [ObjectType], [UserName], [ObjectOldValue], [ObjectNewValue] "
					"SELECT TOP 1 * "
					+ "FROM [Event]  "
					+ "WHERE [ObjectPathId] = :id "
					+ " AND AttributeType = :type "
					+ "ORDER BY EventTime DESC")
					.addEntity(Event.class)
					.setParameter("id", id)
					.setParameter("type", type);
			
			/*
			
			SELECT TOP 100 [EventTime], [EventTypeId], [ObjectType], [UserName], [ObjectOldValue], [ObjectNewValue]
			  FROM [taclogdata].[dbo].[Event]
			  WHERE [ObjectPathId] = 2016 AND AttributeType = 'WLIST'
			  ORDER BY [EventTime] DESC			
			*/
			
			list = query.list();
					
			if(list !=null){
				log.debug("getAirConditiongCalendar value found . Num of items : " + list.size());
				
				if(list.size()>0 ){
					instanceValue= list.get(0);
					log.debug("getAirConditiongCalendar "  + instanceValue.getObjectOldValue());					
				}
			}
			else
				log.debug("getAirConditiongCalendar no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getAirConditiongCalendar Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}

	public Event getManualActivationDeactivation(long id) {
		Event instanceValue = null;
		PersistentSession session = null;
		List<Event> list = null; 
		try{

			session = TACPersistentManager.instance().getSession();
			
			Query query = session.createSQLQuery(
					
					"SELECT TOP 1 * "
					+ "FROM [Event]  "
					+ "WHERE [ObjectPathId] = :id "
					+ "AND ([ObjectOldValue] = '2' AND [ObjectNewValue] = '0' "
					+ " OR [ObjectOldValue] = '0' AND [ObjectNewValue] = '2')"
					+ "ORDER BY EventTime DESC")
					.addEntity(Event.class)
					.setParameter("id", id);
			
			list = query.list();
					
			if(list !=null){
				log.debug("getManualActivationDeactivation value found . Num of items : " + list.size());
				
				if(list.size()>0 ){
					instanceValue= list.get(0);
					log.debug("getManualActivationDeactivation "  + instanceValue.getObjectOldValue());					
				}
			}
			else
				log.debug("getManualActivationDeactivation no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getManualActivationDeactivation Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}
	
	public Event getEventByState(long id, short state) {
		Event instanceValue = null;
		PersistentSession session = null;
		List<Event> list = null; 
		try{

			session = TACPersistentManager.instance().getSession();
			
			Query query = session.createSQLQuery(
					
					"SELECT TOP 1 * "
					+ "FROM [Event]  "
					+ "WHERE [ObjectPathId] = :id "
					+ "AND [ObjectNewValue] = :state "
					+ "ORDER BY EventTime DESC")
					.addEntity(Event.class)
					.setParameter("id", id)
					.setParameter("state", state);
			
			list = query.list();
					
			if(list !=null){
				log.debug("getEventByState value found . Num of items : " + list.size());
				
				if(list.size()>0 ){
					instanceValue= list.get(0);
					log.debug("getEventByState "  + instanceValue.getObjectOldValue());					
				}
			}
			else
				log.debug("getEventByState no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getEventByState Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}
	
	public Event getEventByIdLast(long id) {
		Event instanceValue = null;
		PersistentSession session = null;
		List<Event> list = null; 
		try{

			session = TACPersistentManager.instance().getSession();
			
			Query query = session.createSQLQuery(					
					"SELECT TOP 1 * "
					+ "FROM [Event]  "
					+ "WHERE [ObjectPathId] = :id "
					+ "ORDER BY EventTime DESC")
					.addEntity(Event.class)
					.setParameter("id", id);
			
			list = query.list();
					
			if(list !=null){
				log.debug("getEventByIdLast value found . Num of items : " + list.size());
				
				if(list.size()>0 ){
					instanceValue= list.get(0);
					log.debug("getEventByIdLast "  + instanceValue.getObjectOldValue());					
				}
			}
			else
				log.debug("getEventByIdLast no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getEventByIdLast Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				e1.printStackTrace();
			}
		}		
		return instanceValue; 
	}

	public List<TrendLogValue> getListTrendLogValueSinceLogTime(int id, Timestamp logTime) {
		List<TrendLogValue> list = null;

		PersistentSession session = null;
		 
		try{
			session = TACPersistentManager.instance().getSession();			
			Query query = session.createSQLQuery(
					"SELECT [TrendLogId],[LogTime], [ValueType],[LogValue], [Sequence]  "
					+ "FROM [TrendLogValue]  "
					+ "WHERE [TrendLogId] = :id  "
					+ "AND [LogTime] > :time  "
					
					+ "ORDER BY LogTime DESC")
					.addEntity(TrendLogValue.class)
					.setParameter("id", id)
					.setParameter("time", logTime);
			
			list = query.list();
					
			if(list !=null){
				log.debug("getListTrendLogValueSinceLogTime value found . Num of items : " + list.size());
			}
			else
				log.debug("getTrendLogValue no data Found for id " + id);
			
			session.close();
			
		}catch (Exception e) {
		
			log.error(" getTrendLogValue Exception " + e.getMessage());			
			e.printStackTrace();		
			try {
				if(session!=null)
					session.close();
			} catch (PersistentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		return list;
	}
	
}
