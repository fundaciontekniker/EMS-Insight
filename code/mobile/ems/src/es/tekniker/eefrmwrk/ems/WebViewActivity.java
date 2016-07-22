package es.tekniker.eefrmwrk.ems;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;

public class WebViewActivity extends Activity {
    /** Called when the activity is first created. */
	
	 WebView mainWebView =null;
	 
	 // file upload
	 private ValueCallback<Uri> mUploadMessage;  
	 private final static int FILECHOOSER_RESULTCODE=1;  

	 
    @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        
        mainWebView = (WebView) findViewById(R.id.mainWebView);
        
        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
        CustomWebViewClient mywvClient=new CustomWebViewClient();                
        
        mainWebView.setWebViewClient(mywvClient);
        
        
        //mainWebView.setWebChromeClient(new WebChromeClient());
        mainWebView.setWebChromeClient(new WebChromeClient()  
        {             
        	
        	
        	
        });  

        
        
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebView.getSettings().setDomStorageEnabled(true);
        mainWebView.getSettings().setPluginState(PluginState.ON);
        mainWebView.getSettings().setAllowFileAccess(true);

        mainWebView.getSettings().setAppCacheMaxSize(1024 * 8);
        mainWebView.getSettings().setAppCacheEnabled(true);
        
        JavaScriptInterface JSInterface = new JavaScriptInterface(this);
        mainWebView.addJavascriptInterface(JSInterface, "JSInterface"); 
        if (savedInstanceState == null){
        	loadPage();
        }
    }  
    
    private void loadPage(){
    	
    	String webUrl=Prefs.getPreferenceString(getApplicationContext(), Prefs.PREF_WEB_URL, "");
    	mainWebView.loadUrl(webUrl);
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
      super.onSaveInstanceState(outState);
   
      // Save the state of the WebView
      mainWebView.saveState(outState);
    }
     
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
      super.onRestoreInstanceState(savedInstanceState);
   
      // Restore the state of the WebView
      mainWebView.restoreState(savedInstanceState);
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

    	mUploadMessage.onReceiveValue(intent.getData());

    	mUploadMessage = null;

    }
    
    class JavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        public void loadFunction(String name)
        {
            System.out.print("function " +  name );            
            String url="javascript:" + name+"()";            
            mainWebView.loadUrl(url);
        }
    }
    
    class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	
        	if (url.startsWith("tel:")) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
            } else if (url.startsWith("mailto:")) {
                url = url.replaceFirst("mailto:", "");
                url = url.trim();
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text").putExtra(Intent.EXTRA_EMAIL,
                        new String[] { url });
                startActivity(i);

            } else if (url.startsWith("geo:")) {
                try {
                } catch (Exception e) {
                    System.out.println(e);
                }

            } else if (url.startsWith("data:application/pdf;base64,")) {

                try {

                	String pdfData=url.replace("data:application/pdf;base64,", "");
                	
                	//String sFile = FilePDF.SaveFile(pdfData);
                	String sFile = null;
                	
                	File file = new File(sFile);
                	Intent intent = new Intent(Intent.ACTION_VIEW);
                	intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                	intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                	startActivity(intent);
                	
                	String s="";
                	
                	s="DD";

                } catch (Exception e) {
                    System.out.println(e);
                }

            }

            else {
                view.loadUrl(url);
            }
            return true;
            // then it is not handled by default action
        }
        	
         
         
        
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }
      

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

            //progressBar.setVisibility(View.GONE);
        }
        
       
       
        
    }
   
}