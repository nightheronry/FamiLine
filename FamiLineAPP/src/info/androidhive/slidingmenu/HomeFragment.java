package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Configuration;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HomeFragment extends Fragment {
	WebView webview = null;
	public HomeFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.board_layout, container, false);
        webview = (WebView)rootView.findViewById(R.id.webview);
		 WebSettings websettings = webview.getSettings();  
	     websettings.setSupportZoom(true);  
	     websettings.setBuiltInZoomControls(false);   
	     websettings.setJavaScriptEnabled(true); 
	     webview.setWebChromeClient(mWebChromeClient);
		 //onBackPressed();
		 //webview.setWebViewClient(mWebViewClient);
		 webview.loadUrl("http://203.64.183.224:8080/board.aspx");
		 
        
         
        return rootView;
    }


	@Override    
	public void onConfigurationChanged(Configuration newConfig) { 
		   super.onConfigurationChanged(newConfig);
		    if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){    
		    }
		else{  
		 
		} }
	
	
	WebViewClient mWebViewClient = new WebViewClient() {
		  @Override
		  public boolean shouldOverrideUrlLoading(WebView view, String url) {
		   view.loadUrl(url);
		   return true;
		  }
		 };
	
	 WebChromeClient mWebChromeClient = new WebChromeClient() {
		 
		
		};

}
