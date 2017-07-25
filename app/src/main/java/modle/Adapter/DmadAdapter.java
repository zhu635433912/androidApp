package modle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/6/23 0023.
 */


public class DmadAdapter extends RecyclerView.Adapter<DmadAdapter.MyViewHolder> implements View.OnClickListener{
    private Context context;
    private List<Map<String, Object>> listmap;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    //定义一个接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public DmadAdapter(Context context, List<Map<String, Object>> listmap) {
        this.context = context;
        this.listmap = listmap;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.listview_itme,parent,false);
//        MyViewHolder holder=new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (listmap.get(position).get("publisher_name") != null) {

            holder.text1.setText(listmap.get(position).get("publisher_name") + "");
        }
        if (listmap.get(position).get("service_type") != null) {
            String service_type = listmap.get(position).get("service_type") + "";

            switch (service_type){
                case "1":
                    holder.text9.setText("教师上门");
                    break;
                case "2":
                    holder.text9.setText("学生上门");
                    break;
                case "3":
                    holder.text9.setText("第三方");
                    break;
                case "4":
                    holder.text9.setText("不限");
                    break;
            }
        }
        if (listmap.get(position).get("publisher_headimg") != null) {
            Glide.with(context).load(listmap.get(position).get("publisher_headimg").toString())
                    .transform(new GlideCircleTransform(context)).into(holder.lognhost);
        }
        if (listmap.get(position).get("grade_name")!=null) {
            holder.text2.setText(listmap.get(position).get("grade_name").toString() + "");
        }
        holder.text4.setText(listmap.get(position).get("course_name").toString()+"");
        holder.text6.setText(listmap.get(position).get("content").toString()+"");
//        holder.text7.setText(listmap.get(position).get("distance").toString());
        String dist = listmap.get(position).get("distance").toString();
        double myDist = 0;
        if (!dist.equals("")){
            myDist = Double.parseDouble(dist)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");

//        int myDist = 0;
//        if (!dist.equals("")){
//            myDist = Integer.parseInt(dist)/1000;
//        }
//        int lat = myDist/1000;
        holder.text7.setText(df.format(myDist)+"km       "+((String)listmap.get(position).get("address")).substring(0, 7) + "......");
        holder.text8.setText(""+listmap.get(position).get("created"));
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return listmap.size();
    }
    //监听方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView lognhost;
        TextView text1;//name
        TextView text9;//服务类型
        TextView text2;//年级
        TextView text4;//课程
        TextView text6;//内容
        TextView text7;//距离
        TextView text8;//评分
        ImageView imagehaop;//星级
        public MyViewHolder(View view) {
            super(view);
            AutoUtils.autoSize(itemView);
            lognhost = (ImageView) view.findViewById(R.id.lognhost);
            text1= (TextView) view.findViewById(R.id.text1);
            text9= (TextView) view.findViewById(R.id.text9);
            text2= (TextView) view.findViewById(R.id.text2);
            text4= (TextView) view.findViewById(R.id.text4);
            text6= (TextView) view.findViewById(R.id.text6);
            text7= (TextView) view.findViewById(R.id.text7);
            text8= (TextView) view.findViewById(R.id.text8);
            imagehaop= (ImageView) view.findViewById(R.id.imagehaop);
            lognhost= (ImageView) view.findViewById(R.id.lognhost);
        }
    }
}

