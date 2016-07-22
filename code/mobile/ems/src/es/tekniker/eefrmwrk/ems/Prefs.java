package es.tekniker.eefrmwrk.ems;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Class to manage Preferences 
 */
public class Prefs {
	  
    public static String PREF_APP_VERSION="APP_VERSION";	
    public static String PREF_USER_REG_ID="REGID";    
    public static String PREF_GCM_API_URL="GCMAPIURL";    
    public static String PREF_WEB_URL="WEBURL";
    
	
	public static boolean getPreferenceBoolean(Context context, String key, boolean defaultValue){
		boolean boolOK= false;
		
		boolOK= PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key,defaultValue);

		return boolOK;
		
	}
	
	public static String getPreferenceString(Context context, String key, String defaultValue){
		String sValue= null;
		
		sValue= PreferenceManager.getDefaultSharedPreferences(context).getString(key,defaultValue);

		return sValue;
		
	}
	
	public static void setPreferenceString(Context context, String key, String value){
		SharedPreferences.Editor sped = PreferenceManager.getDefaultSharedPreferences(context).edit();
	    sped.putString(key, value);
	    sped.commit();
	}
	
	public static void setPreferenceInteger(Context context, String key, int value){
		SharedPreferences.Editor sped = PreferenceManager.getDefaultSharedPreferences(context).edit();
	    sped.putInt(key, value);
	    sped.commit();
	}
	
	public static int getPreferenceInteger(Context context, String key, int defaultValue){
		int iValue= 0 ;
		
		iValue= PreferenceManager.getDefaultSharedPreferences(context).getInt(key,defaultValue);

		return iValue;
		
	}
	
	}
