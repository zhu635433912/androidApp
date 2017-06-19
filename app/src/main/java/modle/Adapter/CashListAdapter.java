package modle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
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
 * 创建时间：2017-06-19 14:45
 * 修改人：zhuyunjian
 * 修改时间：2017-06-19 14:45
 * 修改备注：
 */
public class CashListAdapter extends ListBaseAdapter {
    private List<Map<String,Object>> list;

    public CashListAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_cash_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.orderIdTv.setText(list.get(position).get("order_id")+"");
        holder.orderTypeTv.setText(list.get(position).get("remark")+"");
        holder.orderMoneyTv.setText(list.get(position).get("fee")+"");

        Date d = new Date(Long.parseLong((String)list.get(position).get("create_date")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        holder.orderTimeTv.setText(sdf.format(d)+"");

        return convertView;
    }

    static class ViewHolder{
        TextView orderIdTv,orderMoneyTv,orderTimeTv,orderTypeTv;
        ViewHolder(View itemView){
            orderIdTv = (TextView) itemView.findViewById(R.id.order_id_tv);
            orderMoneyTv = (TextView) itemView.findViewById(R.id.order_money_tv);
            orderTimeTv = (TextView) itemView.findViewById(R.id.order_time_tv);
            orderTypeTv = (TextView) itemView.findViewById(R.id.order_type_tv);
        }
    }
}
