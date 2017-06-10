package modle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by 罗超 on 2017/5/23.
 * 老师我的 listview适配器
 */

public class IndexAdapter extends BaseAdapter {
    private List<Map<String,Object>> listmap;
    private Context context;
    private MyViewHode myViewHolder;
    private LayoutInflater layoutInflater;

     public IndexAdapter(List<Map<String, Object>> listmap, Context context){
        this.listmap=listmap;
        this.context=context;
         if (context!=null) {
             layoutInflater = LayoutInflater.from(context);
         }
    }

    @Override
    public int getCount() {
        return listmap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            myViewHolder=new MyViewHode();
            convertView=layoutInflater.inflate(R.layout.mydindan_list,null);
            myViewHolder.dindanbianhao= (TextView) convertView.findViewById(R.id.dindanbianhao);
            myViewHolder.xiadansjtext= (TextView) convertView.findViewById(R.id.xiadansjtext);
            myViewHolder.feejiner= (TextView) convertView.findViewById(R.id.feejiner);
            myViewHolder.dindannairon= (TextView) convertView.findViewById(R.id.dindannairon);
            myViewHolder.kemu= (TextView) convertView.findViewById(R.id.kemu);
            myViewHolder.fukuan= (TextView) convertView.findViewById(R.id.fukuan);
            myViewHolder.weifukuan= (TextView) convertView.findViewById(R.id.weifukuan);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHode) convertView.getTag();
        }

        myViewHolder.dindanbianhao.setText(listmap.get(position).get("id")+"");
        myViewHolder.xiadansjtext.setText(listmap.get(position).get("created")+"");
        myViewHolder.feejiner.setText(listmap.get(position).get("fee")+"");
        myViewHolder.dindannairon.setText(listmap.get(position).get("requirement_grade")+"");
        myViewHolder.kemu.setText(listmap.get(position).get("requirement_course")+"");


        return convertView;
    }


    class MyViewHode{
        private TextView dindanbianhao;//订单id号
        private TextView xiadansjtext;//下单时间
        private TextView feejiner;//订单金额
        private TextView dindannairon;//需求年纪
        private TextView kemu;//需求科目
        private TextView fukuan;//付款
        private TextView weifukuan;//未付款
    }


    public String geiuiserid(int pioos){

        String uida=listmap.get(pioos).get("id").toString();
        int uid=Integer.parseInt(uida);
        return uid+"";
    }
}
