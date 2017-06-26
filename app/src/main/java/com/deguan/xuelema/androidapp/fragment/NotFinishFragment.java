package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.OrderTeacherActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.Order_state;
import com.deguan.xuelema.androidapp.Payment_Activty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.Order_StudionAdabt;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * A simple {@link Fragment} subclass.
 * 未完成
 */
@EFragment(R.layout.fragment_tuijian)
public class NotFinishFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener,Student_init {

    @ViewById(R.id.tuijian_listview)
    PullToRefreshListView listView;
    private Order_StudionAdabt studionAdabt;
    private List<Map<String,Object>> list=new ArrayList<>();
    private Order_init orderInit;
    private int uid;

    @Override
    public void initView() {
        studionAdabt=new Order_StudionAdabt(list,getContext());
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(studionAdabt);
        uid=Integer.parseInt(User_id.getUid());
        orderInit=new Order();
        if (User_id.getRole().equals("1")) {
            orderInit.getOrder_list(uid, 0, 1, 1, null, null, this, 0, 3);
        }else {
            orderInit.getOrder_list(uid, 1, 1, 1, null, null, this, 0, 3);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map=studionAdabt.getmap(i-1);
                String status= (String) map.get("status");
                String ida = (String) map.get("id");
                String duration = (String) map.get("duration");
                String teacherImage = (String) map.get("teacher_headimg");
                Intent intent = null;
                if (User_id.getRole().equals("1")) {
                     intent = new Intent(getActivity(), Order_details.class);
                }else {
                     intent = new Intent(getActivity(), OrderTeacherActivity.class);
                }
                intent.putExtra("oredr_id", ida);
                intent.putExtra("duration", duration);
                intent.putExtra("status", status);
                intent.putExtra("teacher_headimg",teacherImage);
                startActivity(intent);


//                String fee = (String) map.get("fee");
//                Intent intent = new Intent(getActivity(), Payment_Activty.class);
//                intent.putExtra("id", ida);
//                intent.putExtra("fee", fee);
//                intent.putExtra("duration", duration);
//                startActivity(intent);
//                Toast.makeText(getActivity(), "进入支付环节", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        if (User_id.getRole().equals("1")) {
            orderInit.getOrder_list(uid, 0, 1, 1, null, null, this, 0, 3);
        }else {
            orderInit.getOrder_list(uid, 1, 1, 1, null, null, this, 0, 3);
        }
        //下拉刷新
//        orderInit.getOrder_list(uid,0,1,1,null,null,this,0,3);
    }
    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        //取消下拉刷新
        listView.onRefreshComplete();
        //清空list数据
        list.clear();
        for (int i = 0; i < listmap.size(); i++) {
            if (listmap.get(i).get("status").equals("1")){
                list.add(listmap.get(i));
            }
        }
        //刷新适配器
        studionAdabt.notifyDataSetChanged();
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }
}
