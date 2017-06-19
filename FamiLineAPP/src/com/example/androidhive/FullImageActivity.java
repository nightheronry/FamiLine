package com.example.androidhive;

import com.example.androidhive.ScrollImageAdapter;
import info.androidhive.slidingmenu.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

import com.example.androidhive.GalleryExt;
public class FullImageActivity extends Activity implements ActionBar.OnNavigationListener{
	public int i_position = 0;
    private DisplayMetrics dm;
    ScrollImageAdapter ia;
    ActionBar actionBar;
    protected static final int MENU_MODE = Menu.FIRST;
    GalleryExt  g;
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); /*if set this the action  will be cut */
        
        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33daa520")));
        actionBar.setSplitBackgroundDrawable(new ColorDrawable(Color.parseColor("#33daa520")));
        actionBar.setTitle(R.string.app_name);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        setContentView(R.layout.full_image);
        // remove the activity title to make space for tabs        
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
         
        g = (GalleryExt) findViewById(R.id.gallerext);
        
        Intent intent = getIntent();
        i_position = intent.getIntExtra("id",0);  
        
        ia=new ScrollImageAdapter(this);     
        g.setAdapter(ia);
        g.setSelection(i_position);     
        
        
        /*Animation an= AnimationUtils.loadAnimation(this,R.anim.scale);
        g.setAnimation(an); */
        
    } 
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_MODE, 0, "GO TO OTHR MODE");
        return super.onCreateOptionsMenu(menu);
     }*/
    @SuppressWarnings("deprecation")
	@Override
    public boolean onTouchEvent(MotionEvent event){  
        ActionBar bar = getActionBar();  
        switch(event.getAction()){  
            case MotionEvent.ACTION_UP:  
                if(bar.isShowing()) bar.hide();  
                else bar.show();  
                break;  
            default:  
                    break;  
        }  
        return true;  
    }  
    /*
    @Override
    public void onAttachedToWindow() { this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD); super.onAttachedToWindow(); } 
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        
	        case R.id.the_other:
	        	 Intent i = new Intent(getApplicationContext(), TouchModeActivity.class);
					// passing array index
					i.putExtra("id", ia.getOwnposition());
					startActivity(i);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
    /*@Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
         switch(item.getItemId()){
         case MENU_MODE:
        	 
        	 Intent i = new Intent(getApplicationContext(), TouchModeActivity.class);
				// passing array index
				i.putExtra("id", ia.getOwnposition());
				startActivity(i);
            
         }
         return super.onOptionsItemSelected(item);
     }  */
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
    
	/*private GestureDetector      mGestureDetector;
	
	@Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        g.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);*/
    }
	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.full_image);
		
		// get intent data
		Intent i = getIntent();
		
		// Selected image id
		int position = i.getExtras().getInt("id");
		
		
		ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
		imageView.setImageBitmap((Bitmap)AndroidGridLayoutActivity.list.get(position).get(0));
	}*/
    

