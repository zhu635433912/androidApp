package modle.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.zhy.autolayout.utils.AutoUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/6/19 0019.
 */

public class TurnoverAdapter extends ListBaseAdapter {
    private List<Map<String,Object>> list;

    public TurnoverAdapter(List list, Context context) {
        super(list, context);
        this.list = list;
    }

    @Override
    public View getItemView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_turnover_list, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
            //对于listview，注意添加这一行，即可在item上使用高度
            AutoUtils.autoSize(convertView);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.turnIdTv.setText(list.get(position).get("id")+"");
        holder.turnNameTv.setText(list.get(position).get("nickname")+"");
        Date d = new Date(Long.parseLong((String)list.get(position).get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.turnTimeTv.setText(sdf.format(d)+"");
        String type = list.get(position).get("status")+"";
        if (type.equals("1")){
            holder.turnStatsTv.setText("未付款");
        }else if (type.equals("2")){
            holder.turnStatsTv.setText("进行中");
        }
        else if (type.equals("3")){
            holder.turnStatsTv.setText("交易完成");
        }
        else if (type.equals("4")){
            holder.turnStatsTv.setText("申请退款");
        }
        else if (type.equals("5")){
            holder.turnStatsTv.setText("同意退款");
        }
        else if (type.equals("6")){
            holder.turnStatsTv.setText("拒绝退款");
        }else {
            holder.turnStatsTv.setText(" ");
        }
        Glide.with(context).load(list.get(position).get("headimg")+"").transform(new GlideCircleTransform(context)).into(holder.turnHeadImage);


        return convertView;
    }


    static class ViewHolder{
        TextView turnIdTv,turnTimeTv,turnNameTv,turnStatsTv;
        ImageView turnHeadImage;

         ViewHolder(View itemview) {
            turnIdTv = (TextView) itemview.findViewById(R.id.text1);
             turnTimeTv = (TextView) itemview.findViewById(R.id.turn_time_tv);
             turnNameTv = (TextView) itemview.findViewById(R.id.turn_name_tv);
             turnStatsTv = (TextView) itemview.findViewById(R.id.turn_status_tv);
             turnHeadImage = (ImageView) itemview.findViewById(R.id.turn_head_image);
         }
    }
}
