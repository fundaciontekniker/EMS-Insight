/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: Virginia Aranceta
 * License Type: Purchased
 */
package es.tekniker.eefrmwrk.monitoring.tac.dao.domain;

import org.orm.*;
import org.hibernate.Query;
import org.hibernate.LockMode;
import java.util.List;

public class TrendLogValueDAO {
	public static TrendLogValue loadTrendLogValueByORMID(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogValueByORMID(session, trendLog, logTime);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue getTrendLogValueByORMID(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getTrendLogValueByORMID(session, trendLog, logTime);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByORMID(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogValueByORMID(session, trendLog, logTime, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue getTrendLogValueByORMID(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getTrendLogValueByORMID(session, trendLog, logTime, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByORMID(PersistentSession session, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime) throws PersistentException {
		try {
			TrendLogValue trendlogvalue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue();
			trendlogvalue.setORM_TrendLog(trendLog);
			trendlogvalue.setLogTime(logTime);
			
			return (TrendLogValue) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue.class, trendlogvalue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue getTrendLogValueByORMID(PersistentSession session, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime) throws PersistentException {
		try {
			TrendLogValue trendlogvalue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue();
			trendlogvalue.setORM_TrendLog(trendLog);
			trendlogvalue.setLogTime(logTime);
			
			return (TrendLogValue) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue.class, trendlogvalue);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByORMID(PersistentSession session, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			TrendLogValue trendlogvalue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue();
			trendlogvalue.setORM_TrendLog(trendLog);
			trendlogvalue.setLogTime(logTime);
			
			return (TrendLogValue) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue.class, trendlogvalue, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue getTrendLogValueByORMID(PersistentSession session, es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, java.sql.Timestamp logTime, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			TrendLogValue trendlogvalue = new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue();
			trendlogvalue.setORM_TrendLog(trendLog);
			trendlogvalue.setLogTime(logTime);
			
			return (TrendLogValue) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue.class, trendlogvalue, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLogValue(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryTrendLogValue(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLogValue(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryTrendLogValue(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue[] listTrendLogValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listTrendLogValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue[] listTrendLogValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listTrendLogValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLogValue(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue as TrendLogValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLogValue(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue as TrendLogValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("TrendLogValue", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue[] listTrendLogValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryTrendLogValue(session, condition, orderBy);
			return (TrendLogValue[]) list.toArray(new TrendLogValue[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue[] listTrendLogValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryTrendLogValue(session, condition, orderBy, lockMode);
			return (TrendLogValue[]) list.toArray(new TrendLogValue[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		TrendLogValue[] trendLogValues = listTrendLogValueByQuery(session, condition, orderBy);
		if (trendLogValues != null && trendLogValues.length > 0)
			return trendLogValues[0];
		else
			return null;
	}
	
	public static TrendLogValue loadTrendLogValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		TrendLogValue[] trendLogValues = listTrendLogValueByQuery(session, condition, orderBy, lockMode);
		if (trendLogValues != null && trendLogValues.length > 0)
			return trendLogValues[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateTrendLogValueByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateTrendLogValueByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTrendLogValueByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateTrendLogValueByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTrendLogValueByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue as TrendLogValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTrendLogValueByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue as TrendLogValue");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("TrendLogValue", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue createTrendLogValue() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(trendLogValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(trendLogValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog = trendLogValue.getTrendLog();
			if(trendLogValue.getTrendLog() != null) {
				trendLogValue.getTrendLog().trendLogValue.remove(trendLogValue);
			}
			trendLogValue.setORM_TrendLog(trendLog);
			
			return delete(trendLogValue);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog = trendLogValue.getTrendLog();
			if(trendLogValue.getTrendLog() != null) {
				trendLogValue.getTrendLog().trendLogValue.remove(trendLogValue);
			}
			trendLogValue.setORM_TrendLog(trendLog);
			
			try {
				session.delete(trendLogValue);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(trendLogValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue trendLogValue) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(trendLogValue);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLogValue loadTrendLogValueByCriteria(TrendLogValueCriteria trendLogValueCriteria) {
		TrendLogValue[] trendLogValues = listTrendLogValueByCriteria(trendLogValueCriteria);
		if(trendLogValues == null || trendLogValues.length == 0) {
			return null;
		}
		return trendLogValues[0];
	}
	
	public static TrendLogValue[] listTrendLogValueByCriteria(TrendLogValueCriteria trendLogValueCriteria) {
		return trendLogValueCriteria.listTrendLogValue();
	}
}
