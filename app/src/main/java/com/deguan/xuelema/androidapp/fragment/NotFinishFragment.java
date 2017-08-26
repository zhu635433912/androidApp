package com.deguan.xuelema.androidapp.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.OrderTeacherActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.Order_state;
import com.deguan.xuelema.androidapp.Payment_Activty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.OrderView;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.OrderNewAdapter;
import modle.Adapter.Order_StudionAdabt;
import modle.Demand_Modle.Demand;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * A simple {@link Fragment} subclass.
 * 未完成
 */
@EFragment(R.layout.tuijian_new_fragment)
public class NotFinishFragment extends BaseFragment implements OrderView, SwipeRefreshLayout.OnRefreshListener, OrderNewAdapter.OnTopClickListener, OrderNewAdapter.OnTopLongClickListener, Requirdetailed {

    @ViewById(R.id.tuijian_listview)
    RecyclerView listView;
    @ViewById(R.id.tuijian_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    private OrderNewAdapter adapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private OrderPresenterImpl tuijianPresenter;
    private int page = 1;
    private boolean isLoading = false;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscriber(tag = "changeStatus")
    public void updateList(int msg){
        if (msg == 1){
            tuijianPresenter.getNofinishOrderEntity(1);
        }
    }

    @Override
    public void initView() {
        adapter = new OrderNewAdapter(list,getContext());

        adapter.setOnTopClickListener(this);
        adapter.setOnItemLongClickListener(this);
        listView.setAdapter(adapter);
        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int position = recyclerView.getChildAdapterPosition(childAt);
                    if (adapter1.getItemCount() - position < 5) {
                        isLoading = true;
                        page++;
                        if (User_id.getRole().equals("1")) {
                            new OrderPresenterImpl(NotFinishFragment.this, Integer.parseInt(User_id.getUid()), 0, page).getNofinishOrderEntity(1);
                        }else {
                            new OrderPresenterImpl(NotFinishFragment.this, Integer.parseInt(User_id.getUid()), 1, page).getNofinishOrderEntity(1);
                        }
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        if (User_id.getRole().equals("1")){

            tuijianPresenter =  new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()),0,page);
        }else {
            tuijianPresenter = new OrderPresenterImpl(this,Integer.parseInt(User_id.getUid()),1,page);
        }
        if (list.size() > 0){}
        else {
            tuijianPresenter.getNofinishOrderEntity(1);
        }

    }


    @Override
    public void successOrder(List<Map<String, Object>> maps) {
//        listView.onRefreshComplete();
        if (maps != null) {
            if (page == 1) {
                adapter.clear();
            }
            list.clear();

            for (int i = 0; i < maps.size(); i++) {
                if (maps.get(i).get("status").equals("1")) {
                    list.add(maps.get(i));
                }
            }

            adapter.addAll(list);
            isLoading = false;
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failOrder(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (User_id.getRole().equals("1")) {
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 0, page).getNofinishOrderEntity(1);
        }else {
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 1, page).getNofinishOrderEntity(1);
        }
    }


    @Override
    public void onTopClick(Map<String, Object> map) {
        //跳转我的订单页面
//        Intent intent=new Intent(getActivity(),MyOrderActivity.class);
//        startActivity(intent);
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
    }

    @Override
    public boolean onItemLongClickListener(View view, final Map<String, Object> entity) {
        if (User_id.getRole().equals("1")) {
            new AlertDialog.Builder(getContext()).setTitle("学了么提示!").setMessage("是否确定删除订单?")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Order().Delete_Order(Integer.parseInt(User_id.getUid()), Integer.parseInt(entity.get("id").toString()), NotFinishFragment.this);

                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "取消删除", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
        return true;
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        Toast.makeText(getContext(), "删除失败", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Toast.makeText(getContext(), "已删除请刷新", Toast.LENGTH_SHORT).show();
        if (User_id.getRole().equals("1")) {
            new OrderPresenterImpl(NotFinishFragment.this, Integer.parseInt(User_id.getUid()), 0, 1).getNofinishOrderEntity(1);
        } else {
            new OrderPresenterImpl(NotFinishFragment.this, Integer.parseInt(User_id.getUid()), 1, 1).getNofinishOrderEntity(1);
        }
    }
//    @ViewById(R.id.tuijian_listview)
//    PullToRefreshListView listView;
//    private Order_StudionAdabt studionAdabt;
//    private List<Map<String,Object>> list=new ArrayList<>();
//    private Order_init orderInit;
//    private int uid;
//
//    @Override
//    public void initView() {
//        studionAdabt=new Order_StudionAdabt(list,getContext());
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
//        listView.setAdapter(studionAdabt);
//        uid=Integer.parseInt(User_id.getUid());
//        orderInit=new Order();
//        if (User_id.getRole().equals("1")) {
//            orderInit.getOrder_list(uid, 0, 1, 1, null, null, this, 0, 3);
//        }else {
//            orderInit.getOrder_list(uid, 1, 1, 1, null, null, this, 0, 3);
//        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Map<String,Object> map=studionAdabt.getmap(i-1);
//                String status= (String) map.get("status");
//                String ida = (String) map.get("id");
//                String duration = (String) map.get("duration");
//                String teacherImage = (String) map.get("teacher_headimg");
//                Intent intent = null;
//                if (User_id.getRole().equals("1")) {
//                     intent = new Intent(getActivity(), Order_details.class);
//                }else {
//                     intent = new Intent(getActivity(), OrderTeacherActivity.class);
//                }
//                intent.putExtra("oredr_id", ida);
//                intent.putExtra("duration", duration);
//                intent.putExtra("status", status);
//                intent.putExtra("teacher_headimg",teacherImage);
//                startActivity(intent);
//
//
////                String fee = (String) map.get("fee");
////                Intent intent = new Intent(getActivity(), Payment_Activty.class);
////                intent.putExtra("id", ida);
////                intent.putExtra("fee", fee);
////                intent.putExtra("duration", duration);
////                startActivity(intent);
////                Toast.makeText(getActivity(), "进入支付环节", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    @Override
//    public void onRefresh(PullToRefreshBase refreshView) {
//        if (User_id.getRole().equals("1")) {
//            orderInit.getOrder_list(uid, 0, 1, 1, null, null, this, 0, 3);
//        }else {
//            orderInit.getOrder_list(uid, 1, 1, 1, null, null, this, 0, 3);
//        }
//        //下拉刷新
////        orderInit.getOrder_list(uid,0,1,1,null,null,this,0,3);
//    }
//    @Override
//    public void setListview(List<Map<String, Object>> listmap) {
//        //取消下拉刷新
//        listView.onRefreshComplete();
//        //清空list数据
//        list.clear();
//        for (int i = 0; i < listmap.size(); i++) {
//            if (listmap.get(i).get("status").equals("1")){
//                list.add(listmap.get(i));
//            }
//        }
//        //刷新适配器
//        studionAdabt.notifyDataSetChanged();
//    }
//
//    @Override
//    public void setListview1(List<Map<String, Object>> listmap) {
//
//    }
}
