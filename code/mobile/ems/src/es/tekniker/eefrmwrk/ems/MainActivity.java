package es.tekniker.eefrmwrk.ems;

import java.io.IOException;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import es.tekniker.eefrmwrk.gcm.Defines;
import es.tekniker.eefrmwrk.gcm.NotificationActivity;
import es.tekniker.eefrmwrk.ws.OnSynchronizationStoreRegistrationIdCompleted;
import es.tekniker.eefrmwrk.ws.WebServiceCall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnSynchronizationStoreRegistrationIdCompleted {

	private boolean boolRegIdAlreadySent = false;
	private GoogleCloudMessaging gcm;
	private String regid;
	private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	public static Context context = null;
	
	private static final String TAG = "MainActivity";
	public static final Object AsyncTaskRegIdNoSucess = "AsyncTaskRegIdNoSucess";
	

	private String strMessageRegIdResult= null;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		 		
		context = this;
		
		Button buttonWeb= (Button) findViewById(R.id.open_web_button);
        
		buttonWeb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  
            	openWeb();            		
            }
        }); 
		
		
		Button buttonNotification= (Button) findViewById(R.id.notification_button);
        
		buttonNotification.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {  
            	openNotification();            		
            }
        }); 
		
		getGCMApiData();
		getWebURL();
		
		if (boolRegIdAlreadySent==false)
			getGCMRegID();		
	}
	
	private void openWeb(){
		 Intent intent = new Intent(this, WebViewActivity.class);		    
		 startActivity(intent);
	}
	
	private void openNotification(){
		 Intent intent = new Intent(this, NotificationActivity.class);		    
		 startActivity(intent);
	}
	
	private void getGCMApiData(){
		Bundle data = null;
		String GCMApiUrl = null;
		try {
			  data = context.getPackageManager().getApplicationInfo(
			        context.getPackageName(),
			        PackageManager.GET_META_DATA).metaData;
		
			  GCMApiUrl = data.getString("gcm.api.url");			  
			  Prefs.setPreferenceString(getApplicationContext(), Prefs.PREF_GCM_API_URL, GCMApiUrl);
			  
			  Log.d(TAG, "getGCMApiData : " + GCMApiUrl);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	     
	}
	
	private void getWebURL(){
		Bundle data = null;
		String webUrl = null;
		try {
			  data = context.getPackageManager().getApplicationInfo(
			        context.getPackageName(),
			        PackageManager.GET_META_DATA).metaData;
		
			  webUrl = data.getString("web.url");			  
			  Prefs.setPreferenceString(getApplicationContext(), Prefs.PREF_WEB_URL, webUrl);
			  
			  Log.d(TAG, "getWebURL : " + webUrl);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	     
	}
	
	
	private void getGCMRegID() {
		context = getApplicationContext();

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            regid = getRegistrationId(context);

            System.out.println("Exist REGID " + regid);                        
            //regid.isEmpty()  API 9 or higger
            if (regid == null || regid.equals("")) {
            	GCMRegisterInBackground();
            }
            else{
            	sendRegistrationIdToBackend();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
	}

	/**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
	
    /**
     * Gets the current registration ID for application on GCM service, if there is one.
     * <p>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     *         registration ID.
     */
    private String getRegistrationId(Context context) {
        String registrationId = Prefs.getPreferenceString(getApplicationContext(), Prefs.PREF_USER_REG_ID, "");
        return registrationId;
    }

    /**
     * Registers the application with GCM servers asynchronously.
     * <p>
     * Stores the registration ID and the app versionCode in the application's
     * shared preferences.
     */
    private void GCMRegisterInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(context);
                    }
                    regid = gcm.register(Defines.GCM_SENDER_ID);
                    msg = "Device registered, registration ID=" + regid;
                    
                    if(regid != null && regid.equals("")==false){
	                    // You should send the registration ID to your server over HTTP, so it
	                    // can use GCM/HTTP or CCS to send messages to your app.
	                    sendRegistrationIdToBackend();
	
	                    // For this demo: we don't need to send it because the device will send
	                    // upstream messages to a server that echo back the message using the
	                    // 'from' address in the message.
	
	                    // Persist the regID - no need to register again.
	                    storeRegistrationId(context, regid);
                    }
                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    // If there is an error, don't just keep trying to register.
                    // Require the user to click a button again, or perform
                    // exponential back-off.
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                //mDisplay.append(msg + "\n");
                System.out.println(msg);
                //sendRegistrationIdToBackend();
            }
        }.execute(null, null, null);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP or CCS to send
     * messages to your app. Not needed for this demo since the device sends upstream messages
     * to a server that echoes back the message using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
    	System.out.println("sendRegistrationIdToBackend");
    	
    	String regId=null;
    	String GCMApiUrl = null;

    	regId=Prefs.getPreferenceString(getApplicationContext(), Prefs.PREF_USER_REG_ID, "");
    	GCMApiUrl=Prefs.getPreferenceString(getApplicationContext(), Prefs.PREF_GCM_API_URL, "");
    	
    	if(regId != null && regId.equals("")==false){
    		
    		Log.d("sendRegistrationIdToBackend : regId",regId);
    		
    		if(GCMApiUrl != null && GCMApiUrl.equals("")==false){
    			Log.d("sendRegistrationIdToBackend : GCMApiUrl",GCMApiUrl);
		    	StoreRegistrationIdAsyncTask objStoreRegistrationIdAsyncTask= new StoreRegistrationIdAsyncTask(this);				
			    objStoreRegistrationIdAsyncTask.execute(GCMApiUrl, regId);
    		}
    	}
    }
    
    /**
     * Stores the registration ID and the app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param context application's context.
     * @param regId registration ID
     */
    private void storeRegistrationId(Context context, String regId) {
    	
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion + " RegID:  " + regId);
               
        Prefs.setPreferenceInteger(getApplicationContext(), Prefs.PREF_APP_VERSION, appVersion);        
    	Prefs.setPreferenceString(getApplicationContext(), Prefs.PREF_USER_REG_ID, regId);
        
    }
    
    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    
    private class StoreRegistrationIdAsyncTask extends AsyncTask<String, String, String> {
		
		private OnSynchronizationStoreRegistrationIdCompleted listener;

			 
		public StoreRegistrationIdAsyncTask(OnSynchronizationStoreRegistrationIdCompleted listener){
		      this.listener=listener;
		}
		
		protected void onPreExecute() {
				
		}

		protected String doInBackground(String... params) {
		  	///Get params
		  	
			String sGCMApiUrl=params[0];
		    String sRegId=params[1];
		    
		    //Define general variables
		  	String result=null;
		      
			try
			{
				
				//CHECK NETWORK CONNECTIVITY
				boolean boolNetwork=true;
				
				//boolNetwork= isOnline();
				
				if(boolNetwork){
				
					result=WebServiceCall.setRegId(sGCMApiUrl, sRegId);							
					
					if (result!=null)
					{
						
						boolean boolOK= WebServiceCall.RegIdParse(result);
				    
						if(boolOK==false)
						{
							//publishProgress(MainActivity.AsyncTaskRegIdNoSucess); //update progress
							
							strMessageRegIdResult= getResources().getString( R.string.strWebServiceRegIdInvokedError);
							
						}
						else{
						    //OK NOTHING TO DO 
						}
					}	
				}else{
					strMessageRegIdResult= getResources().getString( R.string.strNoNetworkConnectivity);
				}
			}catch(Exception e){
				System.out.println(e.getMessage());
				
				
			}
		  
		    return "OK";
		  }
			
		  protected void onProgressUpdate(String... progress) {
			  if(progress[0].equals(MainActivity.AsyncTaskRegIdNoSucess))
				  Toast.makeText(MainActivity.this, strMessageRegIdResult, Toast.LENGTH_LONG).show();
			  
		  }

		  protected void onPostExecute(String sessionID) {			 
		  	listener.onSynchronizationStoreRegistrationIdCompleted();			 
		  }
	 }


	@Override
	public void onSynchronizationStoreRegistrationIdCompleted() {

		Log.d(TAG, "onSynchronizationStoreRegistrationIdCompleted DONE");
		
		
	}

    
}
