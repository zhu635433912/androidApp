package modle.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.deguan.xuelema.androidapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by jash
 * Date: 16-2-26
 * Time: 下午2:10
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TopViewHolder> implements View.OnClickListener {
    private static final String TAG = TestAdapter.class.getSimpleName();
    private Context context;
    private List<String> list;
    private OnTopClickListener listener;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss", Locale.CHINA);
    private RecyclerView recyclerView;

    public TestAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = new ArrayList<>();
        this.addAll(list);
    }

    public void setOnTopClickListener(OnTopClickListener listener) {
        this.listener = listener;
    }

    @Override
    public TopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        view.setOnClickListener(this);
        return new TopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopViewHolder holder, int position) {
        String top = list.get(position);
        holder.time.setText(top);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(Collection<? extends String> collection){
        addAll(list.size(), collection);
    }
    public void addAll(int position, Collection<? extends String> collection){
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

    public static class TopViewHolder extends RecyclerView.ViewHolder{

        private TextView time;

        public TopViewHolder(View itemView) {
            super(itemView);
            time = ((TextView) itemView.findViewById(android.R.id.text1));
        }
    }

    public interface OnTopClickListener{
        void onTopClick(String top);
    }
}
