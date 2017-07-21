package modle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

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
 * 创建时间：2017-07-10 15:10
 * 修改人：zhuyunjian
 * 修改时间：2017-07-10 15:10
 * 修改备注：
 */


public class BillAdapter extends ListBaseAdapter {
    private List<Map<String,Object>> list;

    public BillAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_bill_list,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.idTv.setText(list.get(position).get("id")+"");
        holder.moneyTv.setText(list.get(position).get("fee")+"元");
        String type = list.get(position).get("financetype")+"";
        if (type.equals("1")){
            holder.typeTv.setText("充值");
        }else if (type.equals("2")){
            holder.typeTv.setText("消费");
        }else if (type.equals("3")){
            holder.typeTv.setText("收入");
        }
        else if (type.equals("4")){
            holder.typeTv.setText("提现");
        }
        else if (type.equals("5")){
            holder.typeTv.setText("退款");
        }else if (type.equals("6")){
            holder.typeTv.setText("手续费");
        }else if (type.equals("7")){
            if (list.get(position).get("level").equals("1")){
                holder.typeTv.setText("一级返");
                holder.idTv.setText(list.get(position).get("order_id")+"");
            }else {
                holder.typeTv.setText("二级返");
                holder.idTv.setText(list.get(position).get("order_id")+"");
            }
        }
        String time = list.get(position).get("created")+"";
        holder.timeTv.setText(time);
        return convertView;
    }

    static class ViewHolder{
        TextView idTv,typeTv,moneyTv,timeTv;
        ViewHolder(View itemView){
            idTv = (TextView) itemView.findViewById(R.id.bill_id_tv);
            typeTv = (TextView) itemView.findViewById(R.id.bill_type_tv);
            moneyTv = (TextView) itemView.findViewById(R.id.bill_money_tv);
            timeTv = (TextView) itemView.findViewById(R.id.bill_time_tv);
        }
    }
}
