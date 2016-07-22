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

public class TrendLogDAO {
	public static TrendLog loadTrendLogByORMID(short trendLogId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogByORMID(session, trendLogId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog getTrendLogByORMID(short trendLogId) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getTrendLogByORMID(session, trendLogId);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByORMID(short trendLogId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogByORMID(session, trendLogId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog getTrendLogByORMID(short trendLogId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return getTrendLogByORMID(session, trendLogId, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByORMID(PersistentSession session, short trendLogId) throws PersistentException {
		try {
			return (TrendLog) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog.class, new Short(trendLogId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog getTrendLogByORMID(PersistentSession session, short trendLogId) throws PersistentException {
		try {
			return (TrendLog) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog.class, new Short(trendLogId));
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByORMID(PersistentSession session, short trendLogId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (TrendLog) session.load(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog.class, new Short(trendLogId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog getTrendLogByORMID(PersistentSession session, short trendLogId, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			return (TrendLog) session.get(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog.class, new Short(trendLogId), lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLog(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryTrendLog(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLog(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return queryTrendLog(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog[] listTrendLogByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listTrendLogByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog[] listTrendLogByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return listTrendLogByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static List queryTrendLog(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog as TrendLog");
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
	
	public static List queryTrendLog(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog as TrendLog");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("TrendLog", lockMode);
			return query.list();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog[] listTrendLogByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		try {
			List list = queryTrendLog(session, condition, orderBy);
			return (TrendLog[]) list.toArray(new TrendLog[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog[] listTrendLogByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			List list = queryTrendLog(session, condition, orderBy, lockMode);
			return (TrendLog[]) list.toArray(new TrendLog[list.size()]);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return loadTrendLogByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		TrendLog[] trendLogs = listTrendLogByQuery(session, condition, orderBy);
		if (trendLogs != null && trendLogs.length > 0)
			return trendLogs[0];
		else
			return null;
	}
	
	public static TrendLog loadTrendLogByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		TrendLog[] trendLogs = listTrendLogByQuery(session, condition, orderBy, lockMode);
		if (trendLogs != null && trendLogs.length > 0)
			return trendLogs[0];
		else
			return null;
	}
	
	public static java.util.Iterator iterateTrendLogByQuery(String condition, String orderBy) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateTrendLogByQuery(session, condition, orderBy);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTrendLogByQuery(String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		try {
			PersistentSession session = es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession();
			return iterateTrendLogByQuery(session, condition, orderBy, lockMode);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static java.util.Iterator iterateTrendLogByQuery(PersistentSession session, String condition, String orderBy) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog as TrendLog");
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
	
	public static java.util.Iterator iterateTrendLogByQuery(PersistentSession session, String condition, String orderBy, org.hibernate.LockMode lockMode) throws PersistentException {
		StringBuffer sb = new StringBuffer("From es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog as TrendLog");
		if (condition != null)
			sb.append(" Where ").append(condition);
		if (orderBy != null)
			sb.append(" Order By ").append(orderBy);
		try {
			Query query = session.createQuery(sb.toString());
			query.setLockMode("TrendLog", lockMode);
			return query.iterate();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog createTrendLog() {
		return new es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog();
	}
	
	public static boolean save(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().saveObject(trendLog);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean delete(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().deleteObject(trendLog);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue[] lTrendLogValues = trendLog.trendLogValue.toArray();
			for(int i = 0; i < lTrendLogValues.length; i++) {
				lTrendLogValues[i].setTrendLog(null);
			}
			return delete(trendLog);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean deleteAndDissociate(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog, org.orm.PersistentSession session)throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue[] lTrendLogValues = trendLog.trendLogValue.toArray();
			for(int i = 0; i < lTrendLogValues.length; i++) {
				lTrendLogValues[i].setTrendLog(null);
			}
			try {
				session.delete(trendLog);
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
	
	public static boolean refresh(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().refresh(trendLog);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static boolean evict(es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog trendLog) throws PersistentException {
		try {
			es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TACPersistentManager.instance().getSession().evict(trendLog);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new PersistentException(e);
		}
	}
	
	public static TrendLog loadTrendLogByCriteria(TrendLogCriteria trendLogCriteria) {
		TrendLog[] trendLogs = listTrendLogByCriteria(trendLogCriteria);
		if(trendLogs == null || trendLogs.length == 0) {
			return null;
		}
		return trendLogs[0];
	}
	
	public static TrendLog[] listTrendLogByCriteria(TrendLogCriteria trendLogCriteria) {
		return trendLogCriteria.listTrendLog();
	}
}
