package view.mywode;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.deguan.xuelema.androidapp.FeeqianbaoActivty;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.indexActivty;
import com.deguan.xuelema.androidapp.init.Student_init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Myconteol_init;
import control.Mycontrol;
import modle.Adapter.IndexAdapter;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.toos.MyListview;
import modle.user_ziliao.User_id;

/**
 * 我的订单碎片
 */

public class Dindan_fagment extends Fragment implements View.OnClickListener,MyListview.IReflashListener,MyListview.RemoveListener,Student_init {
    private LinearLayout jinxinzhong;//进行中
    private LinearLayout tuikuanzhong;//退款中
    private LinearLayout daipingjia;//待评价
    private MyListview listView;
    private Map<String,Object> map;
    private IndexAdapter indexAdapter;
    private int id;
    private int role;
    private Order_init order_init;
    List<Map<String,Object>> listmap;
    private int i=1;
    private boolean chushihua=false;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.mydindan,null);
        jinxinzhong= (LinearLayout) view.findViewById(R.id.jinxinzhong);
        tuikuanzhong= (LinearLayout) view.findViewById(R.id.tuikuanzhong);
        daipingjia= (LinearLayout) view.findViewById(R.id.daipingjia);
        listView= (MyListview) view.findViewById(R.id.dindanlistview);

        listView.setInterface(this);
        listView.setRemoveListener(this);


        jinxinzhong.setOnClickListener(this);
        tuikuanzhong.setOnClickListener(this);
        daipingjia.setOnClickListener(this);
        map = new HashMap<String, Object>();

        //获取用户id与角色
        String ida=User_id.getUid();
        String rolea=User_id.getRole();
        id=Integer.parseInt(ida);
        role=Integer.parseInt(rolea);

        //取消listview下拉上啦默认
        listView.setOverScrollMode(view.OVER_SCROLL_NEVER);
        listView.setVerticalScrollBarEnabled(false);

        //加载listview
        order_init=new Order();
        order_init.getOrder_list(id,role-1,2,1,listView,getActivity(),this,0,3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String order_id=indexAdapter.geiuiserid(position);
                Intent intent=new Intent(getActivity(),Order_details.class);
                intent.putExtra("oredr_id",order_id);
                intent.putExtra("duration","1");
                startActivity(intent);
            }
        });

        return view;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.jinxinzhong:
                //进行中
                order_init.getOrder_list(id,role-1,2,1,listView,getActivity(),this,0,3);
                break;
            case R.id.tuikuanzhong:
                //退款中
                order_init.getOrder_list(id,role-1,4,1,listView,getActivity(),this,0,3);
                break;
            case R.id.daipingjia:
                order_init.getOrder_list(id,role-1,6,1,listView,getActivity(),this,0,3);
                break;

        }
    }





    @Override
    public void onReflash() {//有个很牛逼的bug 先放着 等以后在处理
        Log.e("aa","i="+i);
        if (chushihua){
            order_init.getOrder_list(id,role-1,i,1,listView,getActivity(),this,0,3);
            chushihua=false;
        }else {
            order_init.getOrder_list(id,role-1,i,1,listView,getActivity(),this,0,3);
        }
    }


    @Override
    public void removeItem(MyListview.RemoveDirection direction, int position) {

    }



    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        indexAdapter=new IndexAdapter(listmap,getActivity());
        listView.setAdapter(indexAdapter);
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }
}
