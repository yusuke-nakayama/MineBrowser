package com.ngigroup;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends Activity {
    /** Called when the activity is first created. */

	private EditText et;
	private Button btn;
	private Button homePageRegist;
	private Button bookmarkRegist;
	private Button bookmarkList;
	private Button historyList;
	private SharedPreferences preferences;
	private String url = "http://www.";
	private Activity con = SettingActivity.this;
	public EditText bookmarkInput;
	private BookmarkDatabase bd;
	private List<Entity> enList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        con = this;
        
        bookmarkInput = new EditText(con);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        et = (EditText)findViewById(R.id.editText1);
        btn = (Button)findViewById(R.id.button1);
        homePageRegist = (Button)findViewById(R.id.homepageregist);
        bookmarkRegist = (Button)findViewById(R.id.bookmarkregist);
        bookmarkList = (Button)findViewById(R.id.bookmarklist);
        historyList = (Button)findViewById(R.id.historylist);
        
		bd = new BookmarkDatabase(con, 1);

        btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				String str = url + et.getEditableText().toString();
				Intent intent = new Intent(con,BrowserActivity.class);
				intent.putExtra("URL", str);
				startActivity(intent);
				finish();
			}
		});

        homePageRegist.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				Bundle extras = getIntent().getExtras();
				SharedPreferences.Editor editor = preferences.edit();
				editor.putString("HomePage", extras.getString("URL"));
				editor.commit();
			}
		});

        bookmarkRegist.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO 自動生成されたメソッド・スタブ
				new AlertDialog.Builder(con)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("ブックマーク登録")
				.setMessage("ブックマーク名")
				.setView(bookmarkInput)
				.setPositiveButton("登録", new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						bd.insert(bookmarkInput.getEditableText().toString(), getIntent().getExtras().getString("URL"));
					}
				}).show();
			}
		});
        
        bookmarkList.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String[] bookmarkList = getList();
				new AlertDialog.Builder(con)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("ブックマーク一覧")
				.setItems(bookmarkList, new DialogInterface.OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(con ,BrowserActivity.class);
						intent.putExtra("URL", enList.get(which).getURL());
						startActivity(intent);
						finish();
					}
				}).show();
			}
		});


    }
    
    public String[] getList(){
    	
    	enList = bd.select();

    	String[] list = new String[enList.size()];
    	int cnt = 0;
    	for(Entity en:enList){
    		list[cnt] = en.getBookmark();
    	}
    	
    	return list;
    }
    
    @Override
    public boolean dispatchKeyEvent(KeyEvent event){
    	if(event.getAction() == KeyEvent.ACTION_DOWN){
    		switch(event.getKeyCode()){
    		case KeyEvent.KEYCODE_BACK :
    			Intent intent = new Intent(con,BrowserActivity.class);
    			intent.putExtra("URL", getIntent().getExtras().getString("URL"));
    			startActivity(intent);
    			finish();
    			return true;
    			}
    		}
    	return super.dispatchKeyEvent(event);
    }
}