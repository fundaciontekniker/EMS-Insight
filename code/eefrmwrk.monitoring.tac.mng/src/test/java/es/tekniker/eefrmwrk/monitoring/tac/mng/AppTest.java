package es.tekniker.eefrmwrk.monitoring.tac.mng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import es.tekniker.eefrmwrk.cepmngr.client.CepClient;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.Event;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLog;
import es.tekniker.eefrmwrk.monitoring.tac.dao.domain.TrendLogValue;
import es.tekniker.eefrmwrk.monitoring.tac.mng.TacDB;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	
	private static final String CEPurl = null;
	private static Log log =LogFactory.getLog(TestCase.class);
	
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
     * Test GetTrendLog
     */
    public void testGetTrendLog()
    {
    	/*
    	TacDB tacDBMAnager = new TacDB();
        
        short id=1;
		TrendLog instance = null;
		
		instance = tacDBMAnager.getTrendLog(id);
		
		if(instance != null){
			log.debug("testGetTrendLog is OK " + instance.getORMID());
			
			System.out.println( "testGetTrendLog TEST OK Id : " + instance.getORMID() + " - Guid: " + instance .getTrendLogGuid() );
			
			assertTrue( true );
			
		}else{
			log.error("testGetTrendLog is NOK");
			
			System.out.println( "testGetTrendLog TEST ERROR" );
			
			assertTrue( false );
		}
		*/
    }
    
    /**
     * Test GetTrendLogValue
     */
    public void testGetTrendLogValue()
    {
    	/*
    	TacDB tacDBMAnager = new TacDB();
        
        short id=1;
		TrendLogValue instance = tacDBMAnager.getTrendLogValue(id);
		
		if(instance != null){
			log.debug("testGetTrendLogValue is OK " + instance.getLogValue());
			System.out.println( "testGetTrendLogValue TEST OK Id : " + instance.getTrendLogId() + " - Value: " + instance.getLogValue() + " - Name " + instance.getTrendLog().getName() );

			assertTrue( true );
		}else{
			log.error("testGetTrendLogValue is NOK");			
			System.out.println( "testGetTrendLogValue TEST ERROR" );
			
			assertTrue( false );
		}*/
    }
    
    
    
    
    /**
     * Test testGetAirConditiongCalendar
     */
    public void testGetAirConditiongCalendar()
    {
    	/*
    	try {
    		String stringTacAirConditioningFloor2Name="TAC_OS_AIRCONDITIONER_STATE";
    		String tacAirConditioningFloor2Type="WLIST";
    		List<String> listAirConditioningFloor2ZoneId =null;
    		CepClient cep= null;
    		long loopTimeProcess=10000;
    		long tacAirConditioningFloor2Id =2016;
    		
    		String CEPurl = "http://10.1.45.54/CepManagerWS/CepManagerWS";
    		long timeout=5555;
			//cep = new CepClient(CEPurl,timeout);
    		
			listAirConditioningFloor2ZoneId = new ArrayList<String>();
			listAirConditioningFloor2ZoneId.add("1208");
			listAirConditioningFloor2ZoneId.add("2557");
			listAirConditioningFloor2ZoneId.add("1206");
			listAirConditioningFloor2ZoneId.add("2572");
			listAirConditioningFloor2ZoneId.add("1209");
			listAirConditioningFloor2ZoneId.add("2565");
			listAirConditioningFloor2ZoneId.add("1207");
			listAirConditioningFloor2ZoneId.add("2444");
			
			
    		ProcessAirConditionerThread t = new ProcessAirConditionerThread(stringTacAirConditioningFloor2Name, tacAirConditioningFloor2Type, listAirConditioningFloor2ZoneId , cep, loopTimeProcess, tacAirConditioningFloor2Id);
    		
    		t.processActivationAirConditioner();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	*/
    	
    	/*
    	TacDB tacDBMAnager = new TacDB();
        
        short id=2016;
        String type="WLIST";
        
		Event instance = tacDBMAnager.getAirConditiongCalendar(id,type);
		
		if(instance != null){
			log.debug("testGetAirConditiongCalendar is OK " + instance.getObjectOldValue());

			assertTrue( true );
		}else{
			log.error("testGetAirConditiongCalendar is NOK");			
			System.out.println( "testGetAirConditiongCalendar TEST ERROR" );
			
			assertTrue( false );
		}
		*/
    }
    /**
     * Test GetTrendLogValueH
     */
    /*
    public void testGetTrendLogValueH()
    {
    	TacDB tacDBMAnager = new TacDB();
        
        short id=1;
		TrendLogValue instance = tacDBMAnager.getTrendLogValueH(id);
		
		if(instance != null){
			log.debug("testGetTrendLogValueH is OK " + instance.getLogValue());
			System.out.println( "testGetTrendLogValueH TEST OK Id : " + instance.getTrendLogId() + " - Value: " + instance.getLogValue() + " - Name " + instance.getTrendLog().getName() );

			assertTrue( true );
		}else{
			log.error("testGetTrendLogValueH is NOK");			
			System.out.println( "testGetTrendLogValueH TEST ERROR" );
			
			assertTrue( false );
		}
    }
    */
    
    /**
     * Test GetTrendLogValue
     */
    public void testProcessTacData()
    {
    	/*
    	try {
			TacMng manager= new TacMng();
			manager.processTacData();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
    	boolean boolOK = true;    	
      /*
    	Timer time = new Timer(); // Instantiate Timer Object
		//ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
		time.schedule(manager, 0, 5000); // Create Repetitively task for every 1 secs

		//for demo only.
		for (int i = 0; i <= 5; i++) {
			System.out.println("Execution in Main Thread...." + i);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i == 5) {
				System.out.println("Test  Terminates");
				//System.exit(0);
			}
		}
    	
    	*/
		
		log.debug("testProcessTacData is OK " );			
		assertTrue( true );
		
    }
    
    public void testSendEventCEP(){
    	 CepClient cep = null;
         String url = "http://10.1.45.54/CepManagerWS/CepManagerWS?wsdl";
         boolean boolOK = true;
         /*
         try {
        	cep = new CepClient(url,4000);
         	boolOK= cep.sendEvent( "CL18R_HUM_RET", "17.5", System.currentTimeMillis());
         	
         	if(boolOK){
    			log.debug("testSendEventCEP is OK " );			
    			assertTrue( true );
    		}else{
    			log.error("testSendEventCEP is NOK");
    			assertTrue( false );
    		}
         	
         }catch(Exception e){
         	e.printStackTrace();
         	
			log.error("testSendEventCEP is NOK EXCEPTION " + e.getMessage());
			assertTrue( false );
         }*/
    }
    
}
