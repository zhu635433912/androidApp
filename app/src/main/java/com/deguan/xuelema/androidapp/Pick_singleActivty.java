package com.deguan.xuelema.androidapp;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Picksinge_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.GrideViewadbut;
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
    private int id;
    private List<Map<String,Object>> listmap =new ArrayList<>();
    private GridView gridView;
    private String content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_single);
        User_id.getInstance().addActivity(this);
        jiedanliebiaofanhui= (RelativeLayout) findViewById(R.id.jiedanliebiaofanhui);
        gridView= (GridView) findViewById(R.id.girdeviewlist);
        jiedanliebiaofanhui.bringToFront();
        jiedanliebiaofanhui.setOnClickListener(this);

        final String myid=getIntent().getStringExtra("id");
//        Log.e("aa","接收到的参数为"+myid);
        id=Integer.parseInt(myid);
        content = getIntent().getStringExtra("content");
        jiedanliebiaofanhui.bringToFront();
        jiedanliebiaofanhui.setOnClickListener(this);

        //获取临时订单数据
        int uid=Integer.parseInt(User_id.getUid());
        int role=Integer.parseInt(User_id.getRole());
        Order_init order_init=new Order();
        order_init.getOrder_list(uid,role-1,9,1,null,this,this,id,0);
        map=new HashMap<String, Object>();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,Object> map= listmap.get(i);
                String teacher_id= (String) map.get("teacher_id");
                String head_image= (String) map.get("teacher_headimg");
                Intent intent=new Intent(Pick_singleActivty.this,UserxinxiActivty.class);
                intent.putExtra("user_id",teacher_id);
                intent.putExtra("head_image",head_image);
                intent.putExtra("content",content);
                intent.putExtra("myid",myid);
                startActivity(intent);

            }
        });

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
    public void setListview( final List<Map<String, Object>> listmap) {
        this.listmap.clear();
        this.listmap.addAll(listmap);
        GrideViewadbut grideViewadbut=new GrideViewadbut(this,listmap);
        gridView.setAdapter(grideViewadbut);
        grideViewadbut.notifyDataSetChanged();
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
    }
}
