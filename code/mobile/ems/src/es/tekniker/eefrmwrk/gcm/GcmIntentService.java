/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package es.tekniker.eefrmwrk.gcm;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

//import org.ksoap2.serialization.SoapObject;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import es.tekniker.eefrmwrk.ems.Prefs;
import es.tekniker.eefrmwrk.ems.R;


import android.annotation.SuppressLint;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    public static int NOTIFICATION_ID = 1;
    public static String NOTIFICATION_MSG_ID =null;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;

    private int numNotifications =0;
    
    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "EMS";

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification("Send error: " + extras.toString(),"1");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString(),"2");
            // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                
                // Post notification of received message.
                
                //Parsed data from Notification
                String msgid= extras.getString("id");
                String msgtitle= extras.getString("title");
                String msgmessage= extras.getString("message");
                
                Log.i(TAG,"GCM id : " +  msgid);
                Log.i(TAG,"GCM Title " + msgtitle);
                Log.i(TAG,"GCM Message : "  + msgmessage);
                
                Log.i(TAG, "Received: " + extras.toString());
                
                //sendNotification("Received: " + extras.toString());
                sendNotification(msgmessage,msgid);
                
                
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    @SuppressLint("NewApi")
	private void sendNotification(String msg, String msgid) {
    	
    	
    	final SharedPreferences prefs = getSharedPreferences(GcmMainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	
    	numNotifications = prefs.getInt(GcmMainActivity.PROPERTY_NOTIFICATION_NUMBER, 0);
    	
    	
    	String notificationString = prefs.getString(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, "");
    	if(notificationString != null)
        {
    		if(notificationString.equals("")==false){
    			notificationString = notificationString + "##" ;
    		}
    		
    		notificationString = notificationString + msg;
    		editor = prefs.edit();
            editor.putString(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, notificationString);
            editor.commit();
            
            numNotifications = numNotifications+1;
            editor.putInt(GcmMainActivity.PROPERTY_NOTIFICATION_NUMBER, numNotifications);
            editor.commit();
            
        }
    	
    	/*
        Set<String> notificationSet = prefs.getStringSet(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, new HashSet<String>());        
        if(notificationSet != null)
        {        	
        	//List<String> notificationList = new ArrayList<String>(notificationSet);            
        	//notificationList.add(msg);        	
        	//notificationSet =new HashSet<String>(notificationList);
        	//numNotifications = notificationList.size();
        	
        	notificationSet.add(msg);
        	numNotifications =notificationSet.size();
        	
        	editor = prefs.edit();
            editor.putStringSet(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, notificationSet);
            editor.commit();        
            
            editor.putInt(GcmMainActivity.PROPERTY_NOTIFICATION_NUMBER, numNotifications);
            editor.commit();
        }
        */
    	
        //COMPOSE NOTIFICATION TITLE FROM RESOURCES TEXT 
        String title=null;
        Resources res = getResources();
        title= res.getString(R.string.strGCMNotificationTitle);
        //title= "GCM Notification Title";
        String NOTIFICATION_DATA = "NOTIFICATION_DATA";
        long when =  Calendar.getInstance().getTimeInMillis(); 
        
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
		int smalIcon =R.drawable.ic_launcher;
		String notificationData="This is data : "+msg;
		
		/*create intent for show notification details when user clicks notification*/
		Intent intent =new Intent(getApplicationContext(), NotificationActivity.class);
		intent.putExtra(NOTIFICATION_DATA, notificationData);
		
		/*create unique this intent from  other intent using setData */
		intent.setData(Uri.parse("content://"+when));
		/*create new task for each notification with pending intent so we set Intent.FLAG_ACTIVITY_NEW_TASK */
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		/*get the system service that manage notification NotificationManager*/
		NotificationManager notificationManager =(NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE); 
				
		String notificationText = null;
		String bigText = null;
		
		
		//int num = notificationList.size();		
		
		notificationText =  getResources().getString(R.string.strNotificationReceived)  ;
		
		if(numNotifications >1){
			notificationText = notificationText +  " " + numNotifications +  " " + getResources().getString(R.string.strNotificationNotifications);
		}else{
			notificationText =  notificationText  +  " "  + numNotifications +  " " + getResources().getString(R.string.strNotificationNotification);
		}
		/*
		for(int i=0; i< numNotifications; i++){
			if(bigText !=null){
				bigText = bigText +  notificationList.get(i) + "\n";
			}else{
				bigText = notificationList.get(i) + "\n";
			}
		}
		*/
				
		/*build the notification*/
		mBuilder = new NotificationCompat.Builder(
				getApplicationContext())
				.setWhen(when)
				.setContentText(notificationText )//.setNumber(numNotifications)
				.setContentTitle(title)
				.setSmallIcon(smalIcon)
				.setAutoCancel(true)
				.setTicker(title)
				.setLargeIcon(largeIcon)
				.setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_VIBRATE| Notification.DEFAULT_SOUND)
				.setContentIntent(pendingIntent);
				//.setStyle(new NotificationCompat.BigTextStyle().bigText(bigText));
		
		
		Intent intentIncidence=new Intent(this,NotificationActivity.class);
		intentIncidence.putExtra("id", NOTIFICATION_ID);	
		intentIncidence.putExtra("msgid", msg);	
		intentIncidence.setData(Uri.parse("content://"+when));
		
		PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,intentIncidence, PendingIntent.FLAG_UPDATE_CURRENT );		  
	    mBuilder.setContentIntent(contentIntent);

	    System.out.println(" Send to Notification " + NOTIFICATION_ID);

	    mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
	    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	    
		/*Create notification with builder*/
		//Notification notification=notificationBuilder.build();
		
		/*sending notification to system.Here we use unique id (when)for making different each notification
		 * if we use same id,then first notification replace by the last notification*/
		//notificationManager.notify((int) when, notification);
        
        /*
       
        	mBuilder =
            		new NotificationCompat.Builder(this)
            		.setSmallIcon(R.drawable.ic_launcher)
    		        .setContentTitle(title)
    		        .setStyle(new NotificationCompat.BigTextStyle()
    		        .bigText(msg))
    		        .setContentText(msg);	
       
        
        //if (msgid.contains("-")){
            
	        //StringTokenizer tokens = new StringTokenizer(msgid, "-");
	        //String sAlarmId = tokens.nextToken();
	        //String sMsgId = tokens.nextToken();
	                   
	        NOTIFICATION_ID= Integer.valueOf( msgid );
	        //NOTIFICATION_MSG_ID= Integer.valueOf( sMsgId);
	        NOTIFICATION_MSG_ID=msg;
        
	        //GetIncidence(NOTIFICATION_ID,sMsgId);
	        
	        AlarmSound sound = new AlarmSound(getApplicationContext());
			//sound.play();
			
			Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);					
			v.vibrate(2000);
			
			Intent intentIncidence=new Intent(this,NotificationActivity.class);
			intentIncidence.putExtra("id", NOTIFICATION_ID);	
			intentIncidence.putExtra("msgid", msg);	
			
		    
			//intentIncidence.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
	
			
		    PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,intentIncidence, PendingIntent.FLAG_UPDATE_CURRENT );		  
		    mBuilder.setContentIntent(contentIntent);
	
		    System.out.println(" Send to Notification " + NOTIFICATION_ID);
	
		    mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		    
		    */
		    /*
		    Handler mHandler = new Handler(getMainLooper());
		    mHandler.post(new Runnable() {
		        @Override
		        public void run() {
		        	
		        	openNotification();
		            
		        }
		    });
		    */
        //}
        
        /*
        if (msgid.contains("#")){
        
	        StringTokenizer tokens = new StringTokenizer(msgid, "#");
	        String sIncidenceId = tokens.nextToken();
	        String sMsgId = tokens.nextToken();
	                   
	        NOTIFICATION_ID= Integer.valueOf( sIncidenceId );
	        NOTIFICATION_MSG_ID= Integer.valueOf( sMsgId );
	        
	        //GetIncidence(NOTIFICATION_ID,sMsgId);
	        
	        AlarmSound sound = new AlarmSound(getApplicationContext());
			sound.play();
			
			Vibrator v = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);					
			v.vibrate(2000);
			
			//PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, IncidenceActivity.class), 0);
		        
			Intent intentIncidence=new Intent(this,NotificationActivity.class);
			intentIncidence.putExtra("id", NOTIFICATION_ID);	
			intentIncidence.putExtra("msgid", sMsgId);	
			
		    
			//intentIncidence.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK | android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK);
	
			
		    PendingIntent contentIntent = PendingIntent.getActivity(this, NOTIFICATION_ID,intentIncidence, PendingIntent.FLAG_UPDATE_CURRENT );		  
		    mBuilder.setContentIntent(contentIntent);
	
		    System.out.println(" Send to Notification " + NOTIFICATION_ID);
	
		    mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
		    mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		    
		    Handler mHandler = new Handler(getMainLooper());
		    mHandler.post(new Runnable() {
		        @Override
		        public void run() {
		        	
		        	openIncidence();
		            
		        }
		    });
		    
        }
	    */
    }
    
    public void openNotification(){
    	Intent myIntent = new Intent(getApplicationContext(), NotificationActivity.class);
    	myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	myIntent.putExtra("id", NOTIFICATION_ID);
    	myIntent.putExtra("msgid", NOTIFICATION_MSG_ID);
    	
    	startActivity(myIntent);
    }
    
    
        /*
    private void GetIncidencedummy(int idIncidence, String msgId){
    	
		Incidence incidence= null;

		//Prefs prefs = new Prefs(this);
	    String token=null;
		int userId=0;
		
		
		token = Prefs.getPreferenceString(getApplicationContext(), Prefs.PREF_USER_TOKEN, "");
    	userId = Prefs.getPreferenceInteger(getApplicationContext(), Prefs.PREF_USER_USERID, 0);
    	
		String incidenceId=String.valueOf(idIncidence);
		    
        
        //GET DATA FROM INCIDENCE
        SoapObject result;
		try {
			result = ServiceCallEaglemobile.Wso2GetIncidence(token , userId, Integer.valueOf(incidenceId), Integer.valueOf(msgId), getApplicationContext() );
			
			if (result!=null)
			{
				incidence = ServiceCallEaglemobile.Wso2GetIncidenceParse(result);					
				
				if(incidence!=null)
				{
					Prefs.setIncidence(getApplicationContext(), incidenceId, incidence.getText(), incidence.getIncidenceCode(), incidence.getImage1(),String.valueOf(incidence.getLatitude()), String.valueOf(incidence.getLongitude()),msgId);
					
					System.out.println("INCIDENCE SAVED ON PREFS " + incidenceId);
					
					openIncidence();
				}
			}	else{
				//strMessage= getResources().getString( R.string.strWebServiceRegIdInvokedNoValid);
			}
			
			
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}							
		
        
        
    }*/
    
    
    
}
