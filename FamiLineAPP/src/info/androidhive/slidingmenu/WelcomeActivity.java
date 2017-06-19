package info.androidhive.slidingmenu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.androidhive.Constant;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.view.Window;
import android.view.WindowManager;



public class WelcomeActivity extends Activity {
	private SharePreferenceUtil util;
	private Handler mHandler;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.welcome);
		/*util = new SharePreferenceUtil(this, Constant.SAVE_USER);
		if (util.getisFirst()) {*/
			//createShut();
			moveSound();
		
		initView();
	}

	public void initView() {
		/*if (util.getIsStart()) {
			goFriendListActivity();
		} else {*/
			//goLoginActivity();
			
			mHandler = new Handler();
			mHandler.postDelayed(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					goLoginActivity();
				}
			}, 4000);
			
		
	}

	
	public void goLoginActivity() {
		Intent intent = new Intent();
		intent.setClass(this, RegistActivity.class);
		startActivity(intent);
		finish();
	}


	public void goFriendListActivity() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		util.setIsStart(false);
		finish();
	}

	
	public void createShut() {
		
		Intent addIntent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		String title = getResources().getString(R.string.app_name);
			
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				WelcomeActivity.this, R.drawable.ic_launcher);
		Intent myIntent = new Intent(WelcomeActivity.this,
				WelcomeActivity.class);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);
		
		sendBroadcast(addIntent);
		util.setIsFirst(false);
	}


	public void moveSound() {
		InputStream is = getResources().openRawResource(R.raw.msg);
		File file = new File(getFilesDir(), "msg.mp3");
		try {
			OutputStream os = new FileOutputStream(file);
			int len = -1;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				os.write(buffer, 0, len);
			}
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}