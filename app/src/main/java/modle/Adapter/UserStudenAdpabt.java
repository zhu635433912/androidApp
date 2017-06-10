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
 * 学生我的订单适配器
 */

public class UserStudenAdpabt extends BaseAdapter {
    private List<Map<String,Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private MviewHode mviewHode;

    public UserStudenAdpabt(List<Map<String,Object>> listmap,Context context){
        this.listmap=listmap;
        this.context=context;
         layoutInflater=LayoutInflater.from(context);

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
                mviewHode=new MviewHode();
                convertView=layoutInflater.inflate(R.layout.student_itmeordet,null);
                mviewHode.dindanbianhao= (TextView) convertView.findViewById(R.id.dindanbianhao);
                mviewHode.xiadansjtext= (TextView) convertView.findViewById(R.id.xiadansjtext);
                mviewHode.ordefee= (TextView) convertView.findViewById(R.id.ordefee);
                mviewHode.dindannairon= (TextView) convertView.findViewById(R.id.dindannairon);
                mviewHode.jieshu= (TextView) convertView.findViewById(R.id.jieshu);
                mviewHode.status= (TextView) convertView.findViewById(R.id.status);
                convertView.setTag(mviewHode);
            }else {
                convertView.getTag();
            }

            mviewHode.dindanbianhao.setText(""+listmap.get(position).get("id"));
            mviewHode.xiadansjtext.setText(""+listmap.get(position).get("created"));
            mviewHode.ordefee.setText(""+listmap.get(position).get("fee"));
            mviewHode.dindannairon.setText(""+listmap.get(position).get("requirement_course"));
            mviewHode.jieshu.setText("x"+listmap.get(position).get("duration")+"节");

            switch (listmap.get(position).get("status").toString()){
                case "1":
                mviewHode.status.setText("未付款");
                    break;
                case "2":
                    mviewHode.status.setText("进行中");
                    break;
                case "3":
                    mviewHode.status.setText("交易完成");
                    break;
                case "4":
                    mviewHode.status.setText("申请退款");
                    break;


            }


        return convertView;
    }

    class MviewHode{
       private TextView dindanbianhao;//订单编号
       private TextView xiadansjtext;//下单时间
        private TextView ordefee;//金额
        private TextView dindannairon;//订单科目
        private TextView jieshu;//课时数
        private TextView status;
    }

    public String getuid(int piont){
        String uida=listmap.get(piont).get("id").toString();
        int uid=Integer.parseInt(uida);

        return uid+"";
    }


}
