package modle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;

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
 * 创建时间：2017-10-24 16:05
 * 修改人：zhuyunjian
 * 修改时间：2017-10-24 16:05
 * 修改备注：
 */


public class ExtentAdapter  extends RecyclerView.Adapter<ExtentAdapter.ExtentViewHolder> implements View.OnClickListener {

    private static final String TAG = ExtentAdapter.class.getSimpleName();
    private Context context;
    private List<Map<String,Object>> list;
    private OnTopClickListener listener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public ExtentAdapter(List<Map<String,Object>> list, Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(OnTopClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ExtentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.extent_list_item, parent, false);
        view.setOnClickListener(this);
        return new ExtentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExtentViewHolder holder, final int position) {
        holder.headImage1.setImageURI(Uri.parse(list.get(position).get("user_headimg")+""));
        holder.headImage2.setImageURI(Uri.parse(list.get(position).get("leader_headimg")+""));
        holder.nicknameTv1.setText(list.get(position).get("nickname")+"");
        holder.nicknameTv2.setText(list.get(position).get("leader_nickname")+"");
        holder.moneyTv.setText(list.get(position).get("fee")+"");

        holder.headImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //聊天
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(JGApplication.CONV_TITLE, list.get(position).get("nickname")+"");
                intent.putExtra(JGApplication.TARGET_ID, list.get(position).get("user_name")+"");
                intent.putExtra(JGApplication.TARGET_APP_KEY, JMessageClient.getMyInfo().getAppKey());
                context.startActivity(intent);
            }
        });

        holder.headImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //聊天
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra(JGApplication.CONV_TITLE, list.get(position).get("leader_nickname")+"");
                intent.putExtra(JGApplication.TARGET_ID, list.get(position).get("leader_username")+"");
                intent.putExtra(JGApplication.TARGET_APP_KEY, JMessageClient.getMyInfo().getAppKey());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends Map<String,Object>> collection) {
        addAll(list.size(), collection);
    }

    public void addAll(int position, Collection<? extends Map<String,Object>> collection) {
        list.addAll(position, collection);
        notifyItemRangeInserted(position, collection.size());
    }

    public void clear() {
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
            if (position >= 0) {
                listener.onTopClick(list.get(position));
            }
        }
    }

    public static class ExtentViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView headImage1,headImage2;
        private TextView nicknameTv1,nicknameTv2,moneyTv;


        public ExtentViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            headImage1 = (SimpleDraweeView) itemView.findViewById(R.id.extent_head_image1);
            headImage2 = (SimpleDraweeView) itemView.findViewById(R.id.extent_head_image2);
            nicknameTv1 = (TextView) itemView.findViewById(R.id.extent_user_nickname1);
            nicknameTv2 = (TextView) itemView.findViewById(R.id.extent_user_nickname2);
            moneyTv = (TextView) itemView.findViewById(R.id.extent_money_tv);
        }
    }

    public interface OnTopClickListener {
        void onTopClick(Map<String,Object> entity);
    }
}
