package com.example.androidhive;

import info.androidhive.slidingmenu.R;
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

import com.example.androidhive.GalleryExt;
public class TouchModeActivity  extends Activity {
	public int i_position = 0;
    private DisplayMetrics dm;
    protected static final int MENU_MODE = Menu.FIRST;
    GalleryExt  g;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.full_image);
        
          
        // remove the activity title to make space for tabs  
        
        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
         
        GalleryExt  g = (GalleryExt) findViewById(R.id.gallerext);
        
        Intent intent = getIntent();
        i_position = intent.getIntExtra("id",0);  
        
        TouchModeAdapter ia=new TouchModeAdapter(this);     
        g.setAdapter(ia);
        g.setSelection(i_position);     

    } 
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_MODE, 0, "GO BACK GALLERY");
        return super.onCreateOptionsMenu(menu);
     }
    @Override
     public boolean onOptionsItemSelected(MenuItem item)
     {
         super.onOptionsItemSelected(item);
         switch(item.getItemId()){
         case MENU_MODE:
        	 Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
				// passing array index
				i.putExtra("id", i_position);
				startActivity(i);
         }
         return super.onOptionsItemSelected(item);
     }    
	
    }

