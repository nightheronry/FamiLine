package com.example.androidhive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageViewExt extends ImageView {

    private int color;
 
    public ImageViewExt(Context context) {
        super(context);
       // TODO Auto-generated constructor stub
        color=Color.GRAY;
  }
     
   public ImageViewExt(Context context, AttributeSet attrs) {
         super(context, attrs);
        // TODO Auto-generated constructor stub
         color=Color.GRAY;
   }
 
     
    @Override
     protected void onDraw(Canvas canvas) {
         // TODO Auto-generated method stub    
        
        super.onDraw(canvas);    
        
         Rect rec=canvas.getClipBounds();
        rec.bottom--;
        rec.right--;
        Paint paint=new Paint();
        paint.setColor(color); 
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rec, paint);
    }

}
