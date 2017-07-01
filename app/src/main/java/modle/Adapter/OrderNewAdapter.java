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
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import modle.user_ziliao.User_id;

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
 * 创建时间：2017-06-23 14:41
 * 修改人：zhuyunjian
 * 修改时间：2017-06-23 14:41
 * 修改备注：
 */
public class OrderNewAdapter extends RecyclerView.Adapter<OrderNewAdapter.OrderNewViewHolder> implements View.OnClickListener ,View.OnLongClickListener{

    private static final String TAG = OrderNewAdapter.class.getSimpleName();
    private Context context;
    private List<Map<String,Object>> listmap;
    private OrderNewAdapter.OnTopClickListener listener;
    private OrderNewAdapter.OnTopLongClickListener longListener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public OrderNewAdapter( List<Map<String,Object>> list,Context context) {
        this.context = context;
        this.listmap = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(OrderNewAdapter.OnTopClickListener listener) {
        this.listener = listener;
    }
    /*设置长按事件*/
    public void setOnItemLongClickListener(OnTopLongClickListener onItemLongClickListener) {
        this.longListener = onItemLongClickListener;
    }
    @Override
    public OrderNewAdapter.OrderNewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.wodeorder_itme, parent, false);
        view.setOnClickListener(this);
        view.setOnLongClickListener(this);
        return new OrderNewAdapter.OrderNewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderNewAdapter.OrderNewViewHolder hode, int position) {
        Map<String,Object> entity = listmap.get(position);
        //课时费
        int keshifee=Integer.parseInt(listmap.get(position).get("fee").toString());
        int duration=Integer.parseInt(listmap.get(position).get("duration").toString());
        //总课时费
        int zongfee=keshifee*duration;
        //订单id
        String  orderid=listmap.get(position).get("id").toString();
        if (User_id.getRole().equals("1")) {
            hode.studentlistname.setText(listmap.get(position).get("teacher_name") + "");
            Glide.with(context).load(listmap.get(position).get("teacher_headimg")).transform(new GlideCircleTransform(context)).into(hode.headImage);
        }else {
            hode.studentlistname.setText(listmap.get(position).get("placer_name")+"");
            Glide.with(context).load(listmap.get(position).get("placer_headimg")).transform(new GlideCircleTransform(context)).into(hode.headImage);

        }
//        hode.xdsj.setText(listmap.get(position).get("created")+"");
        Date d = new Date(Long.parseLong(listmap.get(position).get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        hode.xdsj.setText(sdf.format(d));

        hode.nianji.setText(listmap.get(position).get("grade_name")+"");
        hode.yaoqiukemu.setText(listmap.get(position).get("course_name")+"");
        hode.keshifei.setText("￥"+keshifee+"/节");

        switch (listmap.get(position).get("status").toString()){
            case "1":
                hode.studentkechengzhuangtai.setText("未付款");
                hode.querenshouhuo.setText("确认付款");
                break;
            case "2":
                hode.studentkechengzhuangtai.setText("进行中");
                hode.querenshouhuo.setText("确认收货");
                break;
            case "3":
                hode.studentkechengzhuangtai.setText("交易完成");
                hode.querenshouhuo.setText("去评价");
                break;
            case "4":
                hode.studentkechengzhuangtai.setText("申请退款中");
                hode.querenshouhuo.setText("申请退款");
                break;
            case "5":
                hode.studentkechengzhuangtai.setText("同意退款");
                hode.querenshouhuo.setText("退款成功");
                break;
            case "6":
                hode.studentkechengzhuangtai.setText("拒绝退款");
                hode.querenshouhuo.setText("退款失败");
                break;
            case "7":
                hode.studentkechengzhuangtai.setText("交易完成");
                hode.querenshouhuo.setText("完成评价");
                break;

        }
        hode.kechengfee.setText(zongfee+"");
        hode.kechengjieshua.setText("x"+duration+"节");
        hode.orde_id.setText(orderid);
    }

    @Override
    public int getItemCount() {
        return listmap.size();
    }

    public void addAll(Collection<? extends Map<String,Object>> collection){
        addAll(listmap.size(), collection);
    }
    public void addAll(int position, Collection<? extends Map<String,Object>> collection){
        listmap.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }
    public void clear(){
        int size = listmap.size();
        listmap.clear();
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
            listener.onTopClick(listmap.get(position));
        }
    }
    @Override
    public boolean onLongClick(View v) {
        int position = recyclerView.getChildAdapterPosition(v);
        return longListener != null && longListener.onItemLongClickListener(v, listmap.get(position));
    }

    public static class OrderNewViewHolder extends RecyclerView.ViewHolder{

        private TextView studentlistname;//订单教师昵称
        private TextView xdsj;//下单时间
        private TextView nianji;//年级
        private TextView yaoqiukemu;//课程
        private TextView keshifei;//课时费
        private TextView studentkechengzhuangtai;//订单状态
        private TextView kechengfee;//总课时费
        private TextView kechengjieshua;//课程节数
        private TextView orde_id;//订单id
        private TextView querenshouhuo;//付款状态
        private ImageView headImage;//头像图片

        public OrderNewViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            headImage = (ImageView) itemView.findViewById(R.id.dianputubiao1);
            studentlistname= (TextView) itemView.findViewById(R.id.studentlistname);
            xdsj= (TextView) itemView.findViewById(R.id.xdsj);
            nianji= (TextView) itemView.findViewById(R.id.nianji);
            yaoqiukemu= (TextView) itemView.findViewById(R.id.yaoqiukemu);
            keshifei= (TextView) itemView.findViewById(R.id.keshifei);
            studentkechengzhuangtai= (TextView) itemView.findViewById(R.id.studentkechengzhuangtai);
            kechengfee= (TextView) itemView.findViewById(R.id.kechengfee);
            kechengjieshua= (TextView) itemView.findViewById(R.id.kechengjieshua);
            orde_id= (TextView) itemView.findViewById(R.id.orde_id);
            querenshouhuo= (TextView) itemView.findViewById(R.id.querenshouhuo);
        }
    }

    public interface OnTopClickListener{
        void onTopClick(Map<String,Object> entity);
    }
    public interface OnTopLongClickListener{
        boolean onItemLongClickListener(View view,Map<String,Object> entity);
    }
}