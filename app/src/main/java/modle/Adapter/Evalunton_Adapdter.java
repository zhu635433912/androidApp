package modle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;

import java.util.List;
import java.util.Map;

import modle.toos.CircleImageView;

/**
 * Created by Administrator on 2017/6/19 0019.
 */


public class Evalunton_Adapdter extends ListBaseAdapter {

    private final List<Map<String, Object>> listmap;
    private final Context context;
    private LayoutInflater layoutInflater;
    private Viewhod viewhod;

    public Evalunton_Adapdter(List<Map<String,Object>> listmap, Context context){
        super(listmap,context);
        this.listmap = listmap;
        this.context = context;
        layoutInflater=LayoutInflater.from(context);
    }

    public Evalunton_Adapdter(List list, Context context, List<Map<String, Object>> listmap, Context context1) {
        super(list, context);
        this.listmap = listmap;
        this.context = context1;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            viewhod=new Viewhod();
            convertView=layoutInflater.inflate(R.layout.evalunton_listview_itme,null);
            viewhod.evalunton_name= (CircleImageView) convertView.findViewById(R.id.evaluaton_name);
            viewhod.evalunton_username= (TextView) convertView.findViewById(R.id.evalunaton_username);
            viewhod.evalunton_time= (TextView) convertView.findViewById(R.id.evalunaton_time);
            viewhod.evalunton_text= (TextView) convertView.findViewById(R.id.evaluaton_text);
            convertView.setTag(viewhod);
        }else {
            viewhod= (Viewhod) convertView.getTag();
        }
        Glide.with(context)
                .load(listmap.get(position).get("headimg").toString())
                .into(viewhod.evalunton_name);
        viewhod.evalunton_username.setText(listmap.get(position).get("nickname")+"");
        viewhod.evalunton_time.setText(listmap.get(position).get("created")+"");
        viewhod.evalunton_text.setText(listmap.get(position).get("content")+"");
        return convertView;
    }

    class Viewhod{
        CircleImageView evalunton_name;
        TextView evalunton_username;
        TextView evalunton_time;
        TextView evalunton_text;
    }

}
