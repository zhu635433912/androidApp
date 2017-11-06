package com.deguan.xuelema.androidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import modle.Adapter.Order_StudionAdabt;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * 学生订单状态
 */
public class Order_state extends MyBaseActivity implements View.OnClickListener,Student_init{
    private List<Map<String,Object>> listmap;
    private TextView stuentweiwanc;
    private TextView stuentyiwanc;
    private TextView studentdaipingjia;
    private RelativeLayout stuentordentfanhui;
    private int uid;
    private int role;
    private TextView jinxzhong;
    private Order_init order_init;
    private int i=1;
    private ListView mystudentlist;
    private SimpleAdapter simpleAdapter;
    private TextView kechengjieshu;
    private String text="确认付款";
    private Order_StudionAdabt order_studionAdabt;
    private AVLoadingIndicatorView mydindanjiazai;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wdorder);
        User_id.getInstance().addActivity(this);
        studentdaipingjia= (TextView) findViewById(R.id.studentdaipingjia);
        stuentweiwanc= (TextView) findViewById(R.id.stuentweiwanc);
        stuentyiwanc= (TextView) findViewById(R.id.stuentyiwanc);
        stuentordentfanhui= (RelativeLayout) findViewById(R.id.stuentordentfanhui);
        mystudentlist= (ListView) findViewById(R.id.mystudentlist);
        kechengjieshu= (TextView) findViewById(R.id.kechengjieshu);
        jinxzhong= (TextView) findViewById(R.id.jinxzhong);
        mydindanjiazai= (AVLoadingIndicatorView) findViewById(R.id.mydindanjiazai);

        mydindanjiazai.bringToFront();

        jinxzhong.setOnClickListener(this);
        stuentordentfanhui.setOnClickListener(this);
        studentdaipingjia.setOnClickListener(this);
        stuentyiwanc.setOnClickListener(this);
        stuentweiwanc.setOnClickListener(this);

        //取消listview下拉上啦默认
        mystudentlist.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mystudentlist.setVerticalScrollBarEnabled(false);

        //加载订单
        uid=Integer.parseInt(User_id.getUid());
        role=Integer.parseInt(User_id.getRole());

        order_init=new Order();
        order_init.getOrder_list(uid,role-1,0,1,null,null,this,0,3);




        mystudentlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = new HashMap<String, Object>();
                map = order_studionAdabt.getmap(position);
                //获取到点击的listview的itme用户id
                String status= (String) map.get("status");
                String ida = (String) map.get("id");
                String duration = (String) map.get("duration");
                String fee = (String) map.get("fee");
                switch (status) {
                    case "1":
                    Intent intent = new Intent(Order_state.this, Payment_Activty.class);
                    intent.putExtra("id", ida);
                    intent.putExtra("fee", fee);
                    intent.putExtra("duration", duration);
                    startActivity(intent);
                    Toast.makeText(Order_state.this, "进入支付环节", Toast.LENGTH_SHORT).show();
                        break;
                    case "2":
                        Intent intent1=new Intent(Order_state.this,Order_details.class);
                        intent1.putExtra("oredr_id", ida);
                        intent1.putExtra("duration", duration);
                        startActivity(intent1);
                        break;
                    case "3":
                       Intent intent2=new Intent(Order_state.this,Student_assessment.class);
                        intent2.putExtra("oredr_id", ida);
                        startActivity(intent2);
                        break;
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
           case  R.id.studentdaipingjia:
                //待评价
               studentdaipingjia.setTextColor(android.graphics.Color.parseColor("#fd1245"));
               stuentyiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
               stuentweiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
               jinxzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
               order_init.getOrder_list(uid,role-1,3,1,null,null,this,0,1);
            break;
            case R.id.stuentyiwanc:
                //已完成
                studentdaipingjia.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                stuentyiwanc.setTextColor(android.graphics.Color.parseColor("#fd1245"));
                stuentweiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                jinxzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                order_init.getOrder_list(uid,role-1,3,1,null,null,this,0,3);
                break;
            case R.id.stuentweiwanc:
                //进行中
                studentdaipingjia.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                stuentyiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                stuentweiwanc.setTextColor(android.graphics.Color.parseColor("#fd1245"));
                jinxzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                order_init.getOrder_list(uid,role-1,2,1,null,null,this,0,3);
                break;
            case R.id.stuentordentfanhui:
                //返回
                Order_state.this.finish();
                break;
            case R.id.jinxzhong:
                //未完成
                studentdaipingjia.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                stuentyiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                stuentweiwanc.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                jinxzhong.setTextColor(android.graphics.Color.parseColor("#fd1245"));
                order_init.getOrder_list(uid,role-1,1,1,null,null,this,0,3);
                break;

        }
    }
    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        order_studionAdabt=new Order_StudionAdabt(listmap,this);
        mystudentlist.setAdapter(order_studionAdabt);
        mydindanjiazai.setVisibility(View.GONE);
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }

}
