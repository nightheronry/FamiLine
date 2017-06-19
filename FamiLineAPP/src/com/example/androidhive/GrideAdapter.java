package com.example.androidhive;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.GridFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class GrideAdapter extends BaseAdapter {
	private Context mContext;
	
	// Keep all Images in array
	public List<HashMap<String, Bitmap>> mThumbIds=new ArrayList<HashMap<String, Bitmap>>();
	
	// Constructor
	public GrideAdapter(Context c,List<HashMap<String, Bitmap>>  listItems){
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
		
		View view = View.inflate(mContext, R.layout.grid_item, null);
        RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.relaGrid);
        ImageViewExt imageView= new ImageViewExt(mContext);
        imageView = (ImageViewExt) rl.findViewById(R.id.gridImage);
        Set set =mThumbIds.get(position).keySet();
        Iterator it = set.iterator();
        String key = (String) it.next();
        imageView.setImageBitmap((Bitmap)mThumbIds.get(position).get(key));
        TextView text = (TextView) rl.findViewById(R.id.gridText);
		
        /*if (convertView == null) {
            
            */
            if (GridFragment.imageCol == 5) {
            	rl.setLayoutParams(new GridView.LayoutParams(
                		(GridFragment.dm.widthPixels / GridFragment.imageCol) - 6, (GridFragment.dm.widthPixels
                                / GridFragment.imageCol) - 6));
            } else {
            	rl.setLayoutParams(new GridView.LayoutParams(
                		(GridFragment.dm.heightPixels / GridFragment.imageCol) - 6, (GridFragment.dm.heightPixels
                                / GridFragment.imageCol) - 6));
            }
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); 
            String[] ablbumKeyString = key.split("@");
            text.setText(ablbumKeyString[1]);
            /*} else {
            imageView = (ImageViewExt) convertView;}*/
        
        
        return rl;
        	

	}

}
