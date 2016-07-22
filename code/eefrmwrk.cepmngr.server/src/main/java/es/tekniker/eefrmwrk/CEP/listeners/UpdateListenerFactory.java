package es.tekniker.eefrmwrk.cep.listeners;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

import es.tekniker.eefrmwrk.cep.CEP;
import es.tekniker.eefrmwrk.cep.entity.RuleEntity;
import es.tekniker.eefrmwrk.cep.listeners.AbstractCEP_Listener;
import es.tekniker.eefrmwrk.commons.BaseException;

public class UpdateListenerFactory {
	private static final Log log = LogFactory.getLog(UpdateListenerFactory.class);
	
	private static final String VR_LOG="VR_LOG";
	private static final String VR_STORE="VR_STORE";
	//private static final String GEM_LOG="GEM_LOG";
	//private static final String GEM_STORE="GEM_STORE";
	//private static final String GENERAL_LOGGER="GENERAL_LOG";
	private static final String VR_PREDICT="VR_PREDICT";
	private static final String ALARM="ALARM";
	private static final String NONE="NONE";
	/**
	 * Crea listeners con las acciones a tomar al activarse una regla CEP. El listener se asociará a dicha regla.
	 *
	 * @param action String de configuración
	 * @param epl 
	 * @return
	 * @throws Exception
	 */
	//public static AbstractCEP_Listener create(String action, String ruleName, String epl, CEP cep) throws BaseException {
	public static AbstractCEP_Listener create(RuleEntity rule) throws BaseException {
		
		ClassLoader classLoader = null;
		Class listenerClass = null;
		Class[] parameter =  null;
		Object[] values = null;
		Object newObject = null;
        Constructor cons = null;
        String exceptionMessage =null;
        
        /*
		log.debug("Creating Listener:" + action);
		if (action.equals(VR_STORE))
			return new VR_Storer(ruleName,cep);
		if (action.equals(VR_LOG))
			return new VR_Logger(ruleName,cep);
		if (action.equals(VR_PREDICT))
				return new VR_Predict(ruleName,cep);
		//if (action.equals(GEM_STORE))
		//return new GEM_Storer(ruleName,cep);
		//if (action.equals(GEM_LOG))
		//	return new GEM_Logger(ruleName,cep);
		//if (action.equals(GENERAL_LOGGER))
		//	return new General_Logger(ruleName,epl,cep);
		if (action.matches(ALARM))
			return new Alarm_Listener(ruleName,cep);
		*/
		
		 try {
		    	
		    	classLoader = AbstractCEP_Listener.class.getClassLoader();
		    	//listenerClass = classLoader.loadClass("es.tekniker.eefrmwrk.cep.listeners.SentiloListener");
		    	listenerClass = classLoader.loadClass(rule.getCepListener());
		        System.out.println("listenerClass.getName() = " + listenerClass.getName());
		        
		        parameter = new Class[]{RuleEntity.class};
		        values = new RuleEntity[]{rule};		        
		        cons = listenerClass.getConstructor(parameter);
		        newObject = cons.newInstance(values);
		         
		        return (AbstractCEP_Listener) newObject;
		        
		    } catch (ClassNotFoundException e) {
		        e.printStackTrace();
		        exceptionMessage ="ClassNotFoundException " + e.getMessage(); 
		    } catch (InstantiationException e) {
				e.printStackTrace();
				exceptionMessage ="InstantiationException " + e.getMessage();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				exceptionMessage ="IllegalAccessException " + e.getMessage();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				exceptionMessage ="NoSuchMethodException " + e.getMessage();
			} catch (SecurityException e) {
				e.printStackTrace();
				exceptionMessage ="SecurityException " + e.getMessage();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				exceptionMessage ="IllegalArgumentException " + e.getMessage();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				exceptionMessage ="InvocationTargetException " + e.getMessage();
			}catch(Exception e){
				e.printStackTrace();
				exceptionMessage ="Exception " + e.getMessage();
			}
		
		
		/*
		if (action.matches(NONE)){
			log.debug("Alarm_Listener created");
			return null;
		}
		*/
		/* new AbstractCEP_Listener() {
				@Override
				public void update(EventBean[] newEvents, EventBean[] oldEvents) {
				}
				@Override
				public boolean checkRule(String epl) throws BaseException {
					return true;
				}
			};*/
		/*if (action.matches(CHECK))
			return new CheckLastValue_Listener(ruleName,epl,action,cep);*/
		//throw new BaseException("UpdateListenerFactory_CREATE0","Could NOT recognize valid listener");
		throw new BaseException("UpdateListenerFactory_CREATE0","Could NOT recognize valid listener " + rule.getCepListener() + " Exception : " + exceptionMessage);
	}
}
