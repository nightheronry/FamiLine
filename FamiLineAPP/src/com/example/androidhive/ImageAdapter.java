package com.example.androidhive;

import info.androidhive.slidingmenu.GridFragment;
import java.io.Console;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	public List<HashMap<String, Bitmap>> mThumbIds=new ArrayList<HashMap<String, Bitmap>>();
	
	// Constructor
	public ImageAdapter(Context c,List<HashMap<String, Bitmap>>  listItems){
		mContext = c;
		this.mThumbIds=listItems;
	}
	
	@Override
	public int getCount() {
		System.out.println(mThumbIds.size()+"getCount");
		return mThumbIds.size();
		
	}

	@Override
	public Object getItem(int position) {
		
        return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	//
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {	
		ImageViewExt imageView;
		imageView = new ImageViewExt(mContext);
        /*if (convertView == null) {
            
            */
            if (GridFragment.imageCol == 5) {
                imageView.setLayoutParams(new GridView.LayoutParams(
                		(GridFragment.dm.widthPixels / GridFragment.imageCol) - 6, (GridFragment.dm.widthPixels
                                / GridFragment.imageCol) - 6));
            } else {
                imageView.setLayoutParams(new GridView.LayoutParams(
                		(GridFragment.dm.heightPixels / GridFragment.imageCol) - 6, (GridFragment.dm.heightPixels
                                / GridFragment.imageCol) - 6));
            }
            imageView.setAdjustViewBounds(true);
            
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); 
            Set set =mThumbIds.get(position).keySet();
            Iterator it = set.iterator();
            String key = (String) it.next();
            imageView.setImageBitmap((Bitmap)mThumbIds.get(position).get(key));
            
            /*} else {
            imageView = (ImageViewExt) convertView;}*/
        
        
        return imageView;
        	
    /*     
	/*	ImageView imageView = new ImageView(mContext);
	/*	/*if (convertView == null) { */
	/*	imageView.setAdjustViewBounds(true); 
		//imageView=(ImageView)convertView.getTag();
		System.out.println(mThumbIds.get(position)+"getmap");
		
        imageView.setImageBitmap((Bitmap)mThumbIds.get(position).get(0));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
        imageView.setPadding(3, 3, 3, 3);
		/*}else{imageView = (ImageView) convertView;}*/
        //return imageView;
	}

}
