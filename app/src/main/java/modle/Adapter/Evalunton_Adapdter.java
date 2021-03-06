package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.facebook.drawee.view.SimpleDraweeView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        Viewhod viewhod;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.evalunton_listview_itme,parent,false);
            viewhod=new Viewhod(convertView);
            convertView.setTag(viewhod);
        }
            viewhod= (Viewhod) convertView.getTag();
//        Glide.with(context.getApplicationContext())
//                .load(listmap.get(position).get("headimg").toString())
//                .transform(new GlideCircleTransform(context))
//                .into(viewhod.evalunton_name);
        viewhod.evalunton_name.setImageURI(Uri.parse(listmap.get(position).get("headimg")+""));
        viewhod.evalunton_username.setText(listmap.get(position).get("nickname")+"");
        int rank = Integer.parseInt(listmap.get(position).get("rank")+"");
        viewhod.rankTv.setText(rank+"");
        if (rank == 1){
            viewhod.evaluntion_star.setBackgroundResource(R.drawable.one);
        }else if (rank==2){
            viewhod.evaluntion_star.setBackgroundResource(R.drawable.two);
        }else if (rank == 3){
            viewhod.evaluntion_star.setBackgroundResource(R.drawable.three);
        }else if (rank == 4){
            viewhod.evaluntion_star.setBackgroundResource(R.drawable.four);
        }else if (rank == 5){
            viewhod.evaluntion_star.setBackgroundResource(R.drawable.five);
        }

        Date d = new Date(Long.parseLong(listmap.get(position).get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        viewhod.evalunton_time.setText(sdf.format(d));
//        viewhod.evalunton_time.setText(listmap.get(position).get("created")+"");
        viewhod.evalunton_text.setText(listmap.get(position).get("content")+"");
        return convertView;
    }

    class Viewhod{
        ImageView evaluntion_star;
        SimpleDraweeView evalunton_name;
        TextView evalunton_username;
        TextView evalunton_time;
        TextView evalunton_text,rankTv;
        public Viewhod(View itemView){
            evaluntion_star = (ImageView) itemView.findViewById(R.id.evaluaton_star);
            evalunton_name = (SimpleDraweeView) itemView.findViewById(R.id.evaluaton_name);
            evalunton_username = (TextView) itemView.findViewById(R.id.evalunaton_username);
            evalunton_time = (TextView) itemView.findViewById(R.id.evalunaton_time);
            evalunton_text = (TextView) itemView.findViewById(R.id.evaluaton_text);
            rankTv = (TextView) itemView.findViewById(R.id.evaluaton_rank_tv);

        }
    }

}
