package modle.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

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
 * 创建时间：2017-10-27 09:50
 * 修改人：zhuyunjian
 * 修改时间：2017-10-27 09:50
 * 修改备注：
 */


public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.TeamViewHolder> implements View.OnClickListener {

    private static final String TAG = TeamAdapter.class.getSimpleName();
    private Context context;
    private List<Map<String,Object>> list;
    private OnTopClickListener listener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public TeamAdapter(List<Map<String,Object>> list, Context context) {
        this.context = context;
        this.list = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(OnTopClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_distribution_list, parent, false);
        view.setOnClickListener(this);
        return new TeamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TeamViewHolder holder, final int position) {
        holder.headImage.setImageURI(Uri.parse(list.get(position).get("user_headimg")+""));
        holder.nicknameTv.setText(list.get(position).get("user_name")+"");
        holder.moneyTv.setText(list.get(position).get("fee")+"");
        holder.headImage.setOnClickListener(new View.OnClickListener() {
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

    public static class TeamViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView headImage;
        private TextView nicknameTv,moneyTv;


        public TeamViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            headImage = (SimpleDraweeView) itemView.findViewById(R.id.distribution_head_image);
            nicknameTv = (TextView) itemView.findViewById(R.id.distribution_name);
            moneyTv = (TextView) itemView.findViewById(R.id.distribution_money);
        }
    }

    public interface OnTopClickListener {
        void onTopClick(Map<String,Object> entity);
    }
}
