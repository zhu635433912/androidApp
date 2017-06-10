package modle.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.init.Requirdetailed;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/2.
 */

public class KechengAdapter extends BaseAdapter implements Requirdetailed {
    private List<Map<String,Object>> listmap;
    private Context context;
    private LayoutInflater layoutInflater;
    private Madbt madbt;
    private Getdata getdata;
    private String[] cities;
    private Map<String,Object> map;

    public KechengAdapter(List<Map<String, Object>> listmap, Context context){
        this.listmap=listmap;
        this.context=context;
        layoutInflater=LayoutInflater.from(context);
        map=User_id.getMap();
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
            madbt=new Madbt();
            convertView=layoutInflater.inflate(R.layout.purchase_coures_itme,null);
            madbt.gerxxyiduiyi= (TextView) convertView.findViewById(R.id.gerxxyiduiyi);
            madbt.kechengzhongle= (TextView) convertView.findViewById(R.id.kechengzhongle);
            madbt.kechengfeee= (TextView) convertView.findViewById(R.id.kechengfeee);
            convertView.setTag(madbt);
        }else {
            madbt= (Madbt) convertView.getTag();
        }
                String course_id=listmap.get(position).get("course_id").toString();
                if (course_id==null) {
                    String kemu=map.get(course_id)+"250";
                    madbt.kechengzhongle.setText(kemu + "");
                }
                madbt.gerxxyiduiyi.setText("一对一");
                madbt.kechengfeee.setText(listmap.get(position).get("visit_fee")+"￥/课时费");
        return convertView;
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {


    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }


    class Madbt {
        private TextView gerxxyiduiyi;//服务方式
       private TextView kechengzhongle;//课程种类
       private TextView kechengfeee;//服务金额
   }

}
