package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
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
 * 创建时间：2017-06-23 13:15
 * 修改人：zhuyunjian
 * 修改时间：2017-06-23 13:15
 * 修改备注：
 */
public class TuijianNewAdapter extends RecyclerView.Adapter<TuijianNewAdapter.TuijianNewViewHolder> implements View.OnClickListener {

    private static final String TAG = TuijianNewAdapter.class.getSimpleName();
    private Context context;
    private List<TuijianEntity> list;
    private TuijianNewAdapter.OnTopClickListener listener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public TuijianNewAdapter( List<TuijianEntity> list,Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(TuijianNewAdapter.OnTopClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TuijianNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_list_item, parent, false);
        view.setOnClickListener(this);
        return new TuijianNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TuijianNewViewHolder holder, int position) {
        TuijianEntity entity = list.get(position);
        holder.nickname.setText("" + list.get(position).getNickname());
        holder.service_type.setText("" + list.get(position).getService_type_txt());
//        holder.fee.setText("" + list.get(position).getFee());
        holder.fee.setText("");
        holder.speciality.setVisibility(View.GONE);
        holder.speciality.setText("" + list.get(position).getSpeciality());
        holder.username.setText("" + list.get(position).getSignature());
//        holder.user_headimg.setImageURI(Uri.parse(list.get(position).getPublisher_headimg()));
//        Glide.with(context.getApplicationContext()).load(list.get(position).getUser_headimg()).transform(new GlideCircleTransform(context)).into(holder.user_headimg);
        holder.user_headimg.setImageURI(Uri.parse(list.get(position).getUser_headimg()));
        String dist = list.get(position).getDistance();
        holder.stats.setText(""+list.get(position).getStatus2());
        double myDist = 0;
        if (!dist.equals("")){
            myDist = Double.parseDouble(dist)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");

//        int myDist = 0;
//        if (!dist.equals("")) {
//            myDist = Integer.parseInt(dist) ;
//        }
//        int lat = myDist / 1000;
        holder.distance.setText(list.get(position).getSpeciality_name()+"    距我"+df.format(myDist) + "km");

//        holder.distance.setText(df.format(myDist) + "km");
        if (!TextUtils.isEmpty(list.get(position).getGender())){
            String gender = list.get(position).getGender();
            if (gender.equals("1")) {
                holder.nianji.setText("男");
            } else if (gender.equals("2")) {
                holder.nianji.setText("女");
            }
        }else {
            holder.nianji.setText("未知");
        }
        holder.haoping_numtext.setText(list.get(position).getClick()+"人看过  好评率: "+list.get(position).getHaoping_num());

//        holder.haoping_numtext.setText(list.get(position).getClick()+"  好评:"+list.get(position).getHaoping_num());
//        holder.haoping_numtext.setText("");
//        holder.haoping_numtext.setVisibility(View.GONE);
        double rank = Double.parseDouble(list.get(position).getOrder_rank());
        if (0 <= rank && rank <1.5){
            holder.haoping_num.setBackgroundResource(R.drawable.one);
        }else if (rank>= 1.5 && rank < 2.5){
            holder.haoping_num.setBackgroundResource(R.drawable.two);
        }else if (rank>= 2.5 && rank < 3.5){
            holder.haoping_num.setBackgroundResource(R.drawable.three);
        }else if (rank>= 3.5 && rank < 4.5){
            holder.haoping_num.setBackgroundResource(R.drawable.four);
        }else if (rank >= 4.5){
            holder.haoping_num.setBackgroundResource(R.drawable.five);
        }

//        switch (list.get(position).getOrder_rank().toString()){
//            case "0.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.five);
//                break;
//            case "1.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.one);
//                break;
//            case "2.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.two);
//                break;
//            case "3.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.three);
//                break;
//            case "4.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.four);
//                break;
//            case "5.0":
//                holder.haoping_num.setBackgroundResource(R.drawable.five);
//                break;
//        }

        holder.education.setText(list.get(position).getEducation());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends TuijianEntity> collection){
        addAll(list.size(), collection);
    }
    public void addAll(int position, Collection<? extends TuijianEntity> collection){
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

    public static class TuijianNewViewHolder extends RecyclerView.ViewHolder{

        private SimpleDraweeView user_headimg;
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
        private TextView stats;
        private TextView education;

        public TuijianNewViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            education = (TextView) itemView.findViewById(R.id.education_tv);
            user_headimg = (SimpleDraweeView) itemView.findViewById(R.id.lognhost);
            nickname = (TextView) itemView.findViewById(R.id.text1);
            service_type = (TextView) itemView.findViewById(R.id.text9);
            fee = (TextView) itemView.findViewById(R.id.text3);
            stats = (TextView) itemView.findViewById(R.id.text5);
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
        void onTopClick(TuijianEntity entity);
    }
}
