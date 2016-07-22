package es.tekniker.eefrmwrk.gcm.mng.db.test;

import java.io.IOException;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.gcm.mng.dao.domain.Gcmregistration;
import es.tekniker.eefrmwrk.gcm.mng.db.GCMdb;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	private static Log log =LogFactory.getLog(TestCase.class);
	
	String registrationId = "45TR234324FRTRE";
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Test testGetRegistrationId
     */
    public void testGetRegistrationId()
    {
    	/*
    	GCMdb db = new GCMdb();
        
		Gcmregistration instance = db.getRegistrationId(1);
		
		if(instance != null){
			log.debug("testGetRegistrationId is OK " + instance.getORMID());
			
			System.out.println( "testGetRegistrationId TEST OK Id : " + instance.getORMID() + " - Guid: " + instance.getGcmregistration_id() );
			
			assertTrue( true );
			
		}else{
			log.error("testGetRegistrationId is NOK");
			
			System.out.println( "testGetRegistrationId TEST ERROR" );
			
			assertTrue( false );
		}*/
	}
    
    /**
     * Test testGetRegistrationId
     */
    public void testInsertRegistrationId()
    {
    	/*
    	GCMdb db = new GCMdb();
        
		
		boolean boolOK = db.insertRegistrationId(registrationId );
		
		if(boolOK){
			log.debug("testInsertRegistrationId is OK " );
			
			System.out.println( "testInsertRegistrationId TEST OK Id : "  );
			
			assertTrue( true );
			
		}else{
			log.error("testInsertRegistrationId is NOK");
			
			System.out.println( "testInsertRegistrationId TEST ERROR" );
			
			assertTrue( false );
		}
		*/
    }
    
        
}
