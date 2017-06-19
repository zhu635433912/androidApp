package modle.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 罗超 on 2017/5/24.
 * 学生   我的订单适配器
 */

public class Order_StudionAdabt extends BaseAdapter {

    private List<Map<String, Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private Hode hode;
    private String orderid;//订单id
    private int zongfee;//总课时费
    private Map<String,Object> map;
    public Order_StudionAdabt(List<Map<String, Object>> listmap, Context context) {
        this.listmap = listmap;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
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
            hode=new Hode();
            convertView=layoutInflater.inflate(R.layout.wodeorder_itme,null);
            hode.studentlistname= (TextView) convertView.findViewById(R.id.studentlistname);
            hode.xdsj= (TextView) convertView.findViewById(R.id.xdsj);
            hode.nianji= (TextView) convertView.findViewById(R.id.nianji);
            hode.yaoqiukemu= (TextView) convertView.findViewById(R.id.yaoqiukemu);
            hode.keshifei= (TextView) convertView.findViewById(R.id.keshifei);
            hode.studentkechengzhuangtai= (TextView) convertView.findViewById(R.id.studentkechengzhuangtai);
            hode.kechengfee= (TextView) convertView.findViewById(R.id.kechengfee);
            hode.kechengjieshua= (TextView) convertView.findViewById(R.id.kechengjieshua);
            hode.orde_id= (TextView) convertView.findViewById(R.id.orde_id);
            hode.querenshouhuo= (TextView) convertView.findViewById(R.id.querenshouhuo);
            convertView.setTag(hode);
        }else {
            convertView.getTag();
        }

        //课时费
       int keshifee=Integer.parseInt(listmap.get(position).get("fee").toString());
        int duration=Integer.parseInt(listmap.get(position).get("duration").toString());
        //总课时费
        zongfee=keshifee*duration;
        //订单id
        orderid=listmap.get(position).get("id").toString();

        hode.studentlistname.setText(listmap.get(position).get("teacher_name")+"");
        hode.xdsj.setText(listmap.get(position).get("created")+"");
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

        }
        hode.kechengfee.setText(zongfee+"");
        hode.kechengjieshua.setText("x"+duration+"节");
        hode.orde_id.setText(orderid);
        return convertView;
    }
    class Hode {
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
}


    public Map<String,Object> getmap(int position){
        if (map==null) {
            map = new HashMap<>();
        }
        map.put("duration",listmap.get(position).get("duration").toString());
        map.put("status",listmap.get(position).get("status").toString());
        map.put("id",listmap.get(position).get("id").toString());

        int keshifee=Integer.parseInt(listmap.get(position).get("fee").toString());
        int duration=Integer.parseInt(listmap.get(position).get("duration").toString());
        int fee=keshifee*duration;
        map.put("fee",fee+"");
        return map;
    }
}
