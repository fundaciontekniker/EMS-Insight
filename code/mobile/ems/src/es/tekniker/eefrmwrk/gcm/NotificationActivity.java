package es.tekniker.eefrmwrk.gcm;

import java.util.Locale;
import java.util.Random;

import es.tekniker.eefrmwrk.ems.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.speech.tts.TextToSpeech.OnInitListener;

public class NotificationActivity extends Activity implements OnInitListener {

	TextToSpeech ttobj = null;
	String textToSpeech = null;

	String sNotifyId = null;
	String sNotifyMsgId = null;
	TextView tvnotificationMainMessage = null;
	TextView tvnotificationSecondaryMessage = null;
	
	boolean boolTTSEnabled=false;
	
	
	private static int NOTIFICATION_ACTIVITY_SHOW_TIME=15000;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_notification);

		setTitle("");
		
		Bundle extras = getIntent().getExtras();
        if (extras != null) {
        	Integer intIncidenceId = extras.getInt("id");	        
    		sNotifyId = String.valueOf(intIncidenceId);
    		
    		String intIncidenceMsgId = extras.getString("msgid");	        
    		//sNotifyMsgId = String.valueOf(intIncidenceMsgId);
    		
        }        
        
        Button buttonOK = (Button) findViewById(R.id.notification_close_button);        
        buttonOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  
            	CancelNotification();
            	finish();            		
            }
        }); 
        
        Button buttonDelete = (Button) findViewById(R.id.notification_delete_button);        
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  
            	DeleteStoredNotifications();            	
            	tvnotificationMainMessage.setText(getResources().getString(R.string.strNotificationNo));
            }
        }); 
        
        textToSpeech =  getResources().getString(R.string.strNotificationReceived);
        
        tvnotificationMainMessage = (TextView) findViewById(R.id.notificationMainMessage);
        		      
		ttobj = new TextToSpeech(this, this);
		
		//closeAfterTime();
		
	}
	
	private void parseNotifications(){
		  
        final SharedPreferences prefs = getSharedPreferences(GcmMainActivity.class.getSimpleName(),Context.MODE_PRIVATE);  
        int num = prefs.getInt(GcmMainActivity.PROPERTY_NOTIFICATION_NUMBER, 0);		
        String notificationString = prefs.getString(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, "");
        
        String[] splitNotificationString = notificationString.split("##");                
        sNotifyMsgId="";
        if(splitNotificationString!=null){
        	for (int i =splitNotificationString.length-1 ; i >-1;i--){
            	if(sNotifyMsgId.equals("")==false ){
            		sNotifyMsgId = sNotifyMsgId + "\n" + "     ----------    " + "\n" ;  
            	}
            	sNotifyMsgId = sNotifyMsgId + splitNotificationString[i] ; 
            }
        }       
				
		if(num >1){
			textToSpeech = textToSpeech +  " " + num +  " "  + getResources().getString(R.string.strNotificationNotifications);
		}else if(num ==1){
			textToSpeech =  textToSpeech +  getResources().getString(R.string.strNotificationOneNotification);
		}else{
			textToSpeech = "";
		}
		
		System.out.println("Id on Create extras " + sNotifyId);        
			    
	    tvnotificationMainMessage.setText(sNotifyMsgId);
	}
	
	private void closeAfterTime() {
		 
		// TODO Auto-generated method stub
		//CLOSE INCIDENCE ACTIVITY AFTER X MILLISECONDS
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
		   @Override
		   public void run() {			   
			   CancelNotification();				
		       finish();
		   }
		 },NotificationActivity.NOTIFICATION_ACTIVITY_SHOW_TIME);
	}
	
	@Override
	protected void onResume() {
		parseNotifications();
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		if(ttobj!=null){
			ttobj.shutdown();
		}
		super.onDestroy();		
	}	
	
	@Override
	protected void onStop() {
		if(ttobj!=null){
			ttobj.stop();
		}
		super.onStop();		
	}
	/*
	private void speakText(String toSpeak ){
		//Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
		if(ttobj !=null)
			ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
		else
			System.out.println("TTS obj instance is null");
			
	}
	*/
	
	public  void CancelNotification(){
		if(sNotifyId!= null){
			int notifyId= Integer.valueOf(sNotifyId);		
			CancelNotification(getApplicationContext(),notifyId);
		}
		//DeleteStoredNotifications();
		this.finish();
		
	}
	
	@SuppressLint("NewApi")
	private void DeleteStoredNotifications(){
		final SharedPreferences prefs = getSharedPreferences(GcmMainActivity.class.getSimpleName(),Context.MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(GcmMainActivity.PROPERTY_NOTIFICATION_NUMBER, 0);
        editor.putString(GcmMainActivity.PROPERTY_NOTIFICATION_LIST, new String());        
        editor.commit();
	}
	
	public static void CancelNotification(Context ctx, int notifyId) {
	    String ns = Context.NOTIFICATION_SERVICE;
	    NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
	    nMgr.cancel(notifyId);
	}

	@SuppressLint("NewApi")
	@Override
	public void onInit(int status) {
		Log.d("onInit", "TextToSpeech status " + status );
		Log.d("onInit","TextToSpeech textToSpeech: " + textToSpeech );
		
		
		if (boolTTSEnabled){
		
			if(status == TextToSpeech.SUCCESS){
				Log.d("onInit", "TextToSpeech status is SUCCESS " + status );
	            ttobj.setLanguage(Locale.getDefault());
	            
	            String utteranceId = null;
				if(textToSpeech !=null && textToSpeech.equals("")==false){
					utteranceId = String.valueOf( new Random().nextLong());
					try{
						//ttobj.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
						
						
						if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) { 
							ttobj.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null, utteranceId);
						} else {
							ttobj.speak(textToSpeech, TextToSpeech.QUEUE_FLUSH, null);
						}
					}catch(Exception e){
						Log.e("onInit", "speak method Exception" +e.getMessage());
					}
				}
	        }else{
	        	Log.d("onInit", "TextToSpeech status is not SUCCESS " + status );
	        }
		}
	}
	
}
