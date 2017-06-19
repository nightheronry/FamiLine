package com.example.androidhive;
import info.androidhive.slidingmenu.MainActivity;
import info.androidhive.slidingmenu.GridFragment;
import java.util.Iterator;
import java.util.Set;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class ScrollImageAdapter extends BaseAdapter {

    private int ownposition;
      
 
    public int getOwnposition() {
        return ownposition;
    }
 
    public void setOwnposition(int ownposition) {
        this.ownposition = ownposition;
    }
 
    private Context mContext; 
 
    public ScrollImageAdapter(Context c ) {
        mContext = c;
    }
 
    @Override
    public int getCount() {
        return MainActivity.list.size();
    }
 
    @Override
    public Object getItem(int position) { 
        ownposition=position;
        return position;
    }
 
    @Override
    public long getItemId(int position) {
        ownposition=position; 
        return position;
    }
 
	@Override
	public View getView(int position,  View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ownposition=position;
		Set set =MainActivity.list.get(position).keySet();
        Iterator it = set.iterator();
        String key = (String) it.next();
		//TouchImageView imageview=new TouchImageView(mContext ,(Bitmap)AndroidGridLayoutActivity.list.get(position).get(key) );
		ImageView imageview = new ImageView(mContext);
        imageview.setBackgroundColor(0xFF000000);
        imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageview.setLayoutParams(new GalleryExt.LayoutParams(
               LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        
        imageview.setImageBitmap((Bitmap)MainActivity.list.get(position).get(key));
        
        return imageview;
	}
}