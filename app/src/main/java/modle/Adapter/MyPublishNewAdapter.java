package modle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

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
 * 创建时间：2017-06-23 13:41
 * 修改人：zhuyunjian
 * 修改时间：2017-06-23 13:41
 * 修改备注：
 */
public class MyPublishNewAdapter extends RecyclerView.Adapter<MyPublishNewAdapter.MyPublishNewViewHolder> implements View.OnClickListener {

    private static final String TAG = MyPublishNewAdapter.class.getSimpleName();
    private Context context;
    private List<XuqiuEntity> list;
    private MyPublishNewAdapter.OnTopClickListener listener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public MyPublishNewAdapter( List<XuqiuEntity> list,Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(MyPublishNewAdapter.OnTopClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyPublishNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.listview_itme, parent, false);
        view.setOnClickListener(this);
        return new MyPublishNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyPublishNewViewHolder holder, int position) {
        XuqiuEntity entity = list.get(position);
        holder.nickname.setText(""+list.get(position).getPublisher_name());
        String serviceType = ""+list.get(position).getService_type();
        if (serviceType.equals("1")){
            holder.service_type.setText("老师上门");
        }else if (serviceType.equals("2")){
            holder.service_type.setText("学生上门");
        }else if (serviceType.equals("3")){
            holder.service_type.setText("第三方");
        }else {
            holder.service_type.setText("不限");
        }
//        holder.service_type.setVisibility(View.GONE);
        holder.fee.setText(""
//                +list.get(position).getFee()
        );
//        holder.fee.setVisibility(View.GONE);
        holder.speciality.setText(""+list.get(position).getCourse_name());
        holder.username.setText(""+list.get(position).getContent());
//        holder.user_headimg.setImageURI(Uri.parse(list.get(position).getPublisher_headimg()));
        Glide.with(context).load(list.get(position).getPublisher_headimg()).transform(new GlideCircleTransform(context)).into(holder.user_headimg);
        String dist = list.get(position).getDistance();
        int myDist = 0;
        if (!dist.equals("")){
            myDist = Integer.parseInt(dist)/1000;
        }
//        int lat = myDist/1000;
        holder.distance.setText(myDist+"km");
        holder.haoping_numtext.setText(""+list.get(position).getCreated());
        holder.nianji.setText(""+list.get(position).getGrade_name());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends XuqiuEntity> collection){
        addAll(list.size(), collection);
    }
    public void addAll(int position, Collection<? extends XuqiuEntity> collection){
        list.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }
    public void clear(){
        int size = list.size();
        list.clear();
        notifyItemRangeRemoved(0, size);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = recyclerView.getChildAdapterPosition(v);
            listener.onTopClick(list.get(position));
        }
    }

    public static class MyPublishNewViewHolder extends RecyclerView.ViewHolder{

        private ImageView user_headimg;
        //        private CircleImageView user_headimg;//用户头像
        private TextView nickname;//昵称
        private TextView service_type;//服务类型
        private TextView fee;//课时费
        private TextView speciality;//课程
        private TextView username;//内容
        private TextView distance;//距离
        private ImageView haoping_num;//好评数
        private TextView haoping_numtext;//好评分
        private TextView nianji;

        public MyPublishNewViewHolder(View itemView) {
            super(itemView);
            user_headimg = (ImageView) itemView.findViewById(R.id.lognhost);
            nickname = (TextView) itemView.findViewById(R.id.text1);
            service_type = (TextView) itemView.findViewById(R.id.text9);
            fee = (TextView) itemView.findViewById(R.id.text3);
            speciality = (TextView) itemView.findViewById(R.id.text4);
            username = (TextView) itemView.findViewById(R.id.text6);
            distance = (TextView) itemView.findViewById(R.id.text7);
            haoping_numtext = (TextView) itemView.findViewById(R.id.text8);
            haoping_num = (ImageView) itemView.findViewById(R.id.imagehaop);
            distance = (TextView) itemView.findViewById(R.id.text7);
            nianji = (TextView) itemView.findViewById(R.id.text2);
        }
    }

    public interface OnTopClickListener{
        void onTopClick(XuqiuEntity entity);
    }
}