package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.Order_state;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.presenter.OrderPresenter;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.OrderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.Order_StudionAdabt;
import modle.Adapter.StudentAdapter;
import modle.user_ziliao.User_id;

/**
 * 我的订单
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_tuijian)
public class OrderFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener,OrderView{

    @ViewById(R.id.tuijian_listview)
    PullToRefreshListView listView;

    private Order_StudionAdabt adapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private int courseid =1;
    private int grade_id = 1;
    private String address = "路桥区";
    private String lat = "0";
    private String lng = "0";
    private OrderPresenterImpl tuijianPresenter;
    private int page = 1;
    @Override
    public void before() {
    }

    @Override
    public void initView() {
        adapter = new Order_StudionAdabt(list,getContext());
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        tuijianPresenter =  new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()),0,page);
        tuijianPresenter.getOrderEntity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //跳转我的订单页面
                    Intent intent=new Intent(getActivity(),MyOrderActivity.class);
                    startActivity(intent);
            }
        });
    }


    @Override
    public void successOrder(List<Map<String, Object>> maps) {
        listView.onRefreshComplete();
        list.clear();
        for (int i = 0; i < maps.size(); i++) {
            if (!maps.get(i).get("status").equals("9")){
                list.add(maps.get(i));
            }
        }
//        list.addAll(maps);
//        for (int i = 0; i < maps.size(); i++) {
//            TuijianEntity entity = new TuijianEntity();
//            entity.setNickname((String) maps.get(i).get("nickname"));
//            entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
//            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
//            entity.setSignature((String) maps.get(i).get("signature"));
//            entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
//            entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
//            entity.setUser_id((String) maps.get(i).get("user_id"));
//            entity.setGender((String) maps.get(i).get("gender"));
////            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
//            entity.setDistance((String) maps.get(i).get("distance"));
//            entity.setFee(String.valueOf(maps.get(i).get("fee")));
//            entity.setSpeciality(String.valueOf(maps.get(i).get("speciality")));
//            entity.setUsername(String.valueOf(maps.get(i).get("username")));
//            entity.setHaoping_num(String.valueOf(maps.get(i).get("haoping_num")));
//            list.add(entity);
//        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new OrderPresenterImpl(this,Integer.parseInt(User_id.getUid()),0,1).getOrderEntity();
    }


}
