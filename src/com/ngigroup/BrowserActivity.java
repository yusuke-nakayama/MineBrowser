package com.ngigroup;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BrowserActivity extends Activity {
    /** Called when the activity is first created. */

	private WebView wv;
	private String homePage;
	private Activity con = BrowserActivity.this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.main);

        wv = (WebView)findViewById(R.id.webview);

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        homePage = shared.getString("HomePage", "http://www.google.co.jp");
        if(getIntent().getExtras()!= null){
        	homePage = getIntent().getExtras().getString("URL");
        }
        wv.loadUrl(homePage);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new CustomWebViewClient());

    } 

    @Override
    public void onResume(){
    	super.onResume();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
        	wv.loadUrl(bundle.getString("URL"));
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	super.onCreateOptionsMenu(menu);
    	MenuInflater inflater = new MenuInflater(BrowserActivity.this);
    	inflater.inflate(R.menu.webviewmenu, menu);
    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch(item.getItemId()){
	    	case R.id.settingbutton:
	    		Intent intent = new Intent(con, SettingActivity.class);
	    		intent.putExtra("URL", wv.getUrl());
	    		startActivity(intent);
	    		finish();
	    		break;
	    	case R.id.reloadbutton:
	    		wv.reload();
	    		break;
	    	default:
	   			return super.onOptionsItemSelected(item);
    	}
    	return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
    	if(event.getAction() == KeyEvent.ACTION_DOWN){
    		switch(event.getKeyCode()){
    		case KeyEvent.KEYCODE_BACK :
    			if(wv.canGoBack()){
    				wv.goBack();
    				return true;
    			}
    		}
    	}
    	return super.dispatchKeyEvent(event);
    }


    class CustomWebViewClient extends WebViewClient{
    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		Log.i("PageLoadStart", "start");
            setProgressBarVisibility(true);
            setProgressBarIndeterminate(true);
    	}
    	@Override
    	public void onPageFinished(WebView view, String url) {
    		con.setProgressBarVisibility(false);
    	}
    }

}