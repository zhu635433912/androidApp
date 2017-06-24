package com.deguan.xuelema.androidapp.fragment;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.deguan.xuelema.androidapp.OrderTeacherActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.Student_assessment;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.Order_StudionAdabt;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 评价
 */
@EFragment(R.layout.fragment_tuijian)
public class EvaluationFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener,Student_init {

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
        if (User_id.getRole().equals("1")){

            orderInit.getOrder_list(uid,0,3,1,null,null,this,0,1);
        }else {
            orderInit.getOrder_list(uid,1,3,1,null,null,this,0,1);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.e("aa","aa点击了");
//                Map<String,Object> map=studionAdabt.getmap(i-1);
//                String ida = (String) map.get("id");
//                Intent intent2=new Intent(getActivity(),Student_assessment.class);
//                intent2.putExtra("oredr_id", ida);
//                startActivity(intent2);
                Map<String,Object> map=studionAdabt.getmap(i-1);
                String status= (String) map.get("status");
                String ida = (String) map.get("id");
                String duration = (String) map.get("duration");
                Intent intent = null;
                if (User_id.getRole().equals("1")) {
                    intent = new Intent(getActivity(), Order_details.class);
                }else {
                    intent = new Intent(getActivity(), OrderTeacherActivity.class);
                }
//                Intent intent = new Intent(getActivity(), Order_details.class);
                intent.putExtra("oredr_id", ida);
                intent.putExtra("duration", duration);
                intent.putExtra("status", status);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        //下拉刷新
        orderInit.getOrder_list(uid,0,3,1,null,null,this,0,1);
    }
    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        //取消下拉刷新
        listView.onRefreshComplete();
        //清空list数据
        list.clear();
        for (int i = 0; i < listmap.size(); i++) {
            if (!listmap.get(i).get("status").equals("9")){
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
