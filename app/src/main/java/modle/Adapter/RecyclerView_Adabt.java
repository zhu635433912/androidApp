package modle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/9.
 */

public class RecyclerView_Adabt extends RecyclerView.Adapter<MyViewHolder>{

    private LayoutInflater layoutInflater;
    private Context context;
    private List<Map<String,Object>> listmapl;
    private OnItemClickListener onItemClickListener;
    private Map<String,Object> map;
    public interface OnItemClickListener{
        void onItemClik(View view,int position);
    }

    public RecyclerView_Adabt(Context context,List<Map<String,Object>> listmapl){
        map=new HashMap<>();
        this.context=context;
        this.listmapl=listmapl;
        layoutInflater=LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        MyViewHolder holder=new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.pick_singleitme,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int i) {
        map=listmapl.get(i);
        String aa= (String) map.get("teacher_id");
        myViewHolder.tv.setText(aa);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     if (onItemClickListener!=null){
                         onItemClickListener.onItemClik(myViewHolder.itemView,i);
                     }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listmapl.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv;
    TextView tacherxinji;


    public MyViewHolder(View itemView) {
        super(itemView);
        tv= (TextView) itemView.findViewById(R.id.listname);
    }
}