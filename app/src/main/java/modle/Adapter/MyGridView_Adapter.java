package modle.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.deguan.xuelema.androidapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/6.
 */

public class MyGridView_Adapter extends BaseAdapter {
    private List<Map<String,Bitmap>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private Myadbt myadbt;

    public MyGridView_Adapter(List<Map<String,Bitmap>> listmap, Context context){
        this.listmap=listmap;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return listmap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            myadbt=new Myadbt();
            convertView=layoutInflater.inflate(R.layout.gridview_itme,null);
            myadbt.imageView= (ImageView) convertView.findViewById(R.id.grideview_imageview);
            convertView.setTag(myadbt);
        }else {
            myadbt= (Myadbt) convertView.getTag();
        }

        myadbt.imageView.setImageBitmap((Bitmap) listmap.get(position).get("bitmap"));


        return convertView;
    }



    class Myadbt {
       ImageView imageView;


    }
}
