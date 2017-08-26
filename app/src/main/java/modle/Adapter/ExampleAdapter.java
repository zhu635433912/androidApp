package modle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-08-26 10:48
 * 修改人：zhuyunjian
 * 修改时间：2017-08-26 10:48
 * 修改备注：
 */


public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<Map<String, Object>> listmap;
    private LayoutInflater layoutInflater;
    private OnItemClickListener mOnItemClickListener = null;

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view, (int) view.getTag());
        }
    }

    //定义一个接口
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ExampleAdapter(Context context, List<Map<String, Object>> listmap) {
        this.context = context;
        this.listmap = listmap;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.example_list_item, parent, false);
//        MyViewHolder holder=new MyViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String url1 = listmap.get(position).get("img1")+"";
        String url2 = listmap.get(position).get("img2")+"";
        String url3 = listmap.get(position).get("img3")+"";
        String url4 = listmap.get(position).get("img4")+"";

        holder.descTv.setText(listmap.get(position).get("content_tostu")+"");
        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("headimg")+"").transform(new GlideCircleTransform(context)).into(holder.headImage);
        holder.nicknameTv.setText(listmap.get(position).get("nickname")+"");
        holder.courseTv.setText(listmap.get(position).get("completetime")+"    "+listmap.get(position).get("course_name")+"    "
        +listmap.get(position).get("course_name")+"    "+listmap.get(position).get("service_type_text"));
        if (TextUtils.isEmpty(listmap.get(position).get("img1")+"")&&TextUtils.isEmpty(listmap.get(position).get("img2")+"")&&TextUtils.isEmpty(listmap.get(position).get("img3")+"")&&TextUtils.isEmpty(listmap.get(position).get("img4")+"")){
            holder.imageLl.setVisibility(View.GONE);
        }else if (!TextUtils.isEmpty(listmap.get(position).get("img1")+"")){
            Glide.with(context.getApplicationContext()).load(url1).into(holder.exampleImage1);
            if (!TextUtils.isEmpty(listmap.get(position).get("img2")+"")){
                Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img2")+"").into(holder.exampleImage2);
                if (!TextUtils.isEmpty(listmap.get(position).get("img3")+"")){
                    Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img3")+"").into(holder.exampleImage3);
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")) {
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage4);
                    }
                }else {
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")){
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage3);
                    }
                }
            } else  {
                if (!TextUtils.isEmpty(listmap.get(position).get("img3")+"")) {
                    Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img3")+"").into(holder.exampleImage2);
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")){
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage3);
                    }
                }else {
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")) {
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage2);
                    }
                }
            }
        }else {
            if (!TextUtils.isEmpty(listmap.get(position).get("img2")+"")){
                Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img2")+"").into(holder.exampleImage1);
                if (!TextUtils.isEmpty(listmap.get(position).get("img3")+"")){
                    Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img3")+"").into(holder.exampleImage2);
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")) {
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage3);
                    }
                }else {
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")){
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage2);
                    }
                }
            }else {
                if (!TextUtils.isEmpty(listmap.get(position).get("img3")+"")){
                    Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img3")+"").into(holder.exampleImage1);
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")) {
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage2);
                    }
                }else {
                    if (!TextUtils.isEmpty(listmap.get(position).get("img4")+"")){
                        Glide.with(context.getApplicationContext()).load(listmap.get(position).get("img4")+"").into(holder.exampleImage1);
                    }
                }
            }
        }

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
        ImageView headImage;
        TextView nicknameTv;//name
        TextView courseTv;
        TextView descTv;
        LinearLayout imageLl;
        ImageView exampleImage1;
        ImageView exampleImage2;
        ImageView exampleImage3;
        ImageView exampleImage4;


        public MyViewHolder(View view) {
            super(view);
            AutoUtils.autoSize(itemView);
            headImage = (ImageView) view.findViewById(R.id.example_head_image);
            nicknameTv = (TextView) view.findViewById(R.id.example_nickname_tv);
            courseTv = (TextView) view.findViewById(R.id.example_course_tv);
            descTv = (TextView) view.findViewById(R.id.example_desc_tv);
            imageLl = (LinearLayout) view.findViewById(R.id.example_image_ll);
            exampleImage1 = (ImageView) view.findViewById(R.id.example_image1);
            exampleImage2 = (ImageView) view.findViewById(R.id.example_image2);
            exampleImage3 = (ImageView) view.findViewById(R.id.example_image3);
            exampleImage4 = (ImageView) view.findViewById(R.id.example_image4);
        }
    }
}