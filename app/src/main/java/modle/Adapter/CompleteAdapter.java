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
 * 创建时间：2017-06-26 14:39
 * 修改人：zhuyunjian
 * 修改时间：2017-06-26 14:39
 * 修改备注：
 */
public class CompleteAdapter extends BaseAdapter {

    private List<Map<String, Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private Hode hode;
    private String orderid;//订单id
    private int zongfee;//总课时费
    private Map<String,Object> map;
    public CompleteAdapter(List<Map<String, Object>> listmap, Context context) {
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
        Hode hode;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.wodeorder_itme,null);
            hode=new Hode(convertView);
            convertView.setTag(hode);
        }else {
            hode = (Hode) convertView.getTag();
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
                hode.querenshouhuo.setText("已完成");
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

        public Hode(View itemView){
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
