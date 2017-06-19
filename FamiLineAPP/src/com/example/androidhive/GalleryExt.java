package com.example.androidhive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;
import android.widget.Toast;

public class GalleryExt extends Gallery {
    boolean is_first=false;
    boolean is_last=false;
    public GalleryExt(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }
     
    public GalleryExt(Context context,AttributeSet paramAttributeSet)
     {
           super(context,paramAttributeSet);
 
     }
 
    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2)
       {   
        return e2.getX() > e1.getX(); 
       }
 
     @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float distanceX,
                    float distanceY) { 
          
    	 ScrollImageAdapter ia=(ScrollImageAdapter)this.getAdapter();
        
         int p=ia.getOwnposition();
         
         int count=ia.getCount(); 
         int kEvent;  
          if(isScrollingLeft(e1, e2)){ 
           //Check if scrolling left  
              if(p==0&&is_first){
                  
                  Toast.makeText(this.getContext(), "The top ", Toast.LENGTH_SHORT).show();
              }else if(p==0){
                  
                  is_first=true;
              }else{
                  is_last=false;
              }
               
           kEvent = KeyEvent.KEYCODE_DPAD_LEFT;  
           }  else{ 
            //Otherwise scrolling right    
               if(p==count-1&&is_last){
                      Toast.makeText(this.getContext(), "The bottom", Toast.LENGTH_SHORT).show();
                  }else if( p==count-1){
                      is_last=true;
                  }else{
                      is_first=false;
                  }
                   
            kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;   
            }  
          onKeyDown(kEvent, null);  
          return true;  
    }
}