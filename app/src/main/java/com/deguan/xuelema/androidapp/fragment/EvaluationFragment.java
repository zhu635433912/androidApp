package com.deguan.xuelema.androidapp.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.OrderTeacherActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.Student_assessment;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.OrderView;
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
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 评价
 */
@EFragment(R.layout.tuijian_new_fragment)
public class EvaluationFragment extends MyBaseFragment implements OrderView, SwipeRefreshLayout.OnRefreshListener, OrderNewAdapter.OnTopClickListener {

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

    @Override
    public void initView() {
        adapter = new OrderNewAdapter(list,getContext());

        adapter.setOnTopClickListener(this);
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
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
                            new OrderPresenterImpl(EvaluationFragment.this, Integer.parseInt(User_id.getUid()), 0, page).getEvaluateOrderEntity(3, 1);
                        }else {
                            new OrderPresenterImpl(EvaluationFragment.this, Integer.parseInt(User_id.getUid()), 1, page).getTeacherEvaOrderEntity();
                        }
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        if (User_id.getRole().equals("1")){
            tuijianPresenter =  new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()),0,page);
            tuijianPresenter.getEvaluateOrderEntity(3, 1);
        }else {
            tuijianPresenter = new OrderPresenterImpl(this,Integer.parseInt(User_id.getUid()),1,page);
            tuijianPresenter.getTeacherEvaOrderEntity();
        }
    }

    @Subscriber(tag = "changeStatus")
    public void updateList(int msg){
        if (msg == 1){
            if (User_id.getRole().equals("1")) {
                tuijianPresenter.getEvaluateOrderEntity(3, 1);
            }
            else {
                tuijianPresenter.getTeacherEvaOrderEntity();
            }
        }
    }
    @Override
    public void successOrder(List<Map<String, Object>> maps) {
//        listView.onRefreshComplete();

        if (maps != null) {
            if (page == 1) {
                adapter.clear();
                list.clear();
            }
//            for (int i = 0; i < maps.size(); i++) {
//                if (!maps.get(i).get("status").equals("9")) {
//                    list.add(maps.get(i));
//                }
//            }
            List<Map<String, Object>> lists = new ArrayList<>();
            if (User_id.getRole().equals("1")) {
                for (int i = 0; i < maps.size(); i++) {
                    if (maps.get(i).get("status").equals("3")) {
                        lists.add(maps.get(i));
                    }
                }
            }else {
                for (int i = 0; i < maps.size(); i++) {
                    if (maps.get(i).get("status").equals("7")) {
                        lists.add(maps.get(i));
                    }
                }
            }
            adapter.addAll(lists);
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
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 0, page).getEvaluateOrderEntity(3,1);
        }else {
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 1, page).getOrderEntity(7);
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


}
