package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Picksinge_init;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.RecyclerView_Adabt;
import modle.Increase_course.Increase_course;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.toos.DividerGridItemDecoration;
import modle.toos.DividerItemDecoration;
import modle.user_ziliao.User_id;

/**
 * 接单列表
 */

public class Pick_singleActivty  extends AutoLayoutActivity implements View.OnClickListener,Student_init{
    private RecyclerView mRecyclerView;
    private List<Map<String,Object>> mDatas;
    private RelativeLayout jiedanliebiaofanhui;
    Map<String,Object> map;
    RecyclerView_Adabt recyclerView_adabt;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_single);
        User_id.getInstance().addActivity(this);

        String myid=getIntent().getStringExtra("id");
        Log.e("aa","接收到的参数为"+myid);
        id=Integer.parseInt(myid);

        mRecyclerView= (RecyclerView) findViewById(R.id.id_recyclerview);
        jiedanliebiaofanhui= (RelativeLayout) findViewById(R.id.jiedanliebiaofanhui);
        jiedanliebiaofanhui.bringToFront();
        jiedanliebiaofanhui.setOnClickListener(this);

        //设置布局管理器
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        //获取临时订单数据
        int uid=Integer.parseInt(User_id.getUid());
        int role=Integer.parseInt(User_id.getRole());
        Order_init order_init=new Order();
        order_init.getOrder_list(uid,role-1,9,1,null,this,this,id,0);
        map=new HashMap<String, Object>();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jiedanliebiaofanhui:
                Pick_singleActivty.this.finish();
                break;
        }

    }



    @Override
    public void setListview(final List<Map<String, Object>> listmap) {
        //设置adapter
        recyclerView_adabt=new RecyclerView_Adabt(this,listmap);
        mRecyclerView.setAdapter(recyclerView_adabt);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.HORIZONTAL_LIST));

        //监听事件
        recyclerView_adabt.setOnItemClickListener(new RecyclerView_Adabt.OnItemClickListener() {
            @Override
            public void onItemClik(View view, int position) {

                map=listmap.get(position);
                String aa= (String) map.get("teacher_id");
                Intent intent=new Intent(Pick_singleActivty.this,UserxinxiActivty.class);
                intent.putExtra("user_id",aa);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
    }
}
