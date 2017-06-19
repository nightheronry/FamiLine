package info.androidhive.slidingmenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter {

	private LayoutInflater myInflater;
    CharSequence[] title = null;
    CharSequence[] info = null;
    Integer [] pic =null;
     
    public ListViewAdapter(Context ctxt, CharSequence[] title, CharSequence[] info ,Integer [] pic){
        myInflater = LayoutInflater.from(ctxt);
        this.title = title;
        this.info = info;
        this.pic=pic;
    }
     
    @Override
    public int getCount() {
         return title.length;
    }
 
    @Override
    public Object getItem(int position) {
         return title[position];
    }
 
    @Override
    public long getItemId(int position) {
         return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
        //自訂類別，表達個別listItem中的view物件集合。
        ViewTag viewTag;
 
        if(convertView == null){
            //取得listItem容器 view
            convertView = myInflater.inflate(R.layout.adapter, null);
             
            //建構listItem內容view
            viewTag = new ViewTag(
            (ImageView)convertView.findViewById(R.id.MyAdapter_ImageView_icon),
            (TextView) convertView.findViewById(R.id.MyAdapter_TextView_title),
            (TextView) convertView.findViewById(R.id.MyAdapter_TextView_info)
             );
             
            //設置容器內容
            convertView.setTag(viewTag);
        }
        else{
            viewTag = (ViewTag) convertView.getTag();
        }
         
        //設定內容圖案
        /*switch(position){
            case 0:
                viewTag.icon.setBackgroundResource(R.drawable.f01);
                break;
            case 1:
                viewTag.icon.setBackgroundResource(R.drawable.img02);
                break;
            case 2:
                viewTag.icon.setBackgroundResource(R.drawable.img03);
                break;
            case 3:
                viewTag.icon.setBackgroundResource(R.drawable.img01);
                break;
            case 4:
                viewTag.icon.setBackgroundResource(R.drawable.img02);
                break;
            case 5:
                viewTag.icon.setBackgroundResource(R.drawable.img02);
                break;
            case 6:
                viewTag.icon.setBackgroundResource(R.drawable.img03);
                break;
            case 7:
                viewTag.icon.setBackgroundResource(R.drawable.img03);
                break;
        }*/
        viewTag.icon.setBackgroundResource(pic[position]);
        //設定標題文字
        viewTag.title.setText(title[position]);
        //設定內容文字
        viewTag.info.setText(info[position]);
         
        return convertView;
    }
 
    //自訂類別，表達個別listItem中的view物件集合。
    class ViewTag{
        ImageView icon;
        TextView title;
        TextView info;
     
        public ViewTag(ImageView icon, TextView title, TextView info){
            this.icon = icon;
            this.title = title;
            this.info = info;
        }
    }

}
