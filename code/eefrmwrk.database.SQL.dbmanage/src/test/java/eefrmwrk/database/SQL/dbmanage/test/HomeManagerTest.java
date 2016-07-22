package eefrmwrk.database.SQL.dbmanage.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import es.tekniker.eefrmwrk.commons.BaseException;
import es.tekniker.eefrmwrk.database.sql.manage.HomeManager;
import es.tekniker.eefrmwrk.database.sql.model.Home;

public class HomeManagerTest {

	private static Log log = LogFactory.getLog(HomeManagerTest.class);
	
	public static String RESULT_OK="OK";
	public static String RESULT_ERROR="ERROR";
	public static String RESULT_EXCEPTION="EXCEPTION";
	
	
	@BeforeClass
	public static void doSomethingBefore(){

	}
	
	@AfterClass
	public static void doSomethingBeforeAfter(){
		
	}	
	
	//@org.junit.Test	
	public void testGetHomeByName(){
		log.info("*************************************************************" );		
		log.info("testGetHomeByName: START " );
		String  result= RESULT_OK;
		String name = "Tekniker_LivingLab";
		
		Home instance;
		try {
			instance = HomeManager.findByName(name);
			
			if(instance!=null)
			{
				log.info("testGetHomeByName: instance is not null " + instance.getHiName() );
				Assert.assertTrue(true);
			}
			else
			{
				log.error("testGetHomeByName: instance is NULL " );
				Assert.assertTrue(false);
				result= RESULT_ERROR;
			}
			
		} catch (BaseException e) {
			log.error("testGetHomeByName: Exception " + e.getMessage() );
			Assert.assertTrue(false);
			result= RESULT_ERROR;
		}		
		
		log.info("testGetHomeByName: RESULT "  + result );
		log.info("testGetHomeByName: END " );
		log.info("*************************************************************" );
		log.info("");
		
	}	
	
}
