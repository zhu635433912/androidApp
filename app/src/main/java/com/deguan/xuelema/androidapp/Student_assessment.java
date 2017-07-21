package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;


import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

/**
 * 学生评价老师
 */

public class Student_assessment extends AutoLayoutActivity implements View.OnClickListener, Requirdetailed {
    private RelativeLayout pingjiafanhui;
    private int oredr_id;
    private Button jieinteun;
    private int uid;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private Button but5;
    private LinearLayout pingfenxinji;
    private int rantoke=2;//评分
    private EditText pingjiatext;
    private TextView pingfenTv;
    private TextView goodTv;
    private TextView midTv;
    private int rank = 4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation_sutuder);
        User_id.getInstance().addActivity(this);
     //   HideIMEUtil.wrap(this);


        midTv = (TextView) findViewById(R.id.mid_percent);
        goodTv = (TextView) findViewById(R.id.good_percent);
        pingfenTv = (TextView) findViewById(R.id.pingfen_tv);
        pingjiafanhui= (RelativeLayout) findViewById(R.id.pingjiafanhui);
        but1= (Button) findViewById(R.id.but1);
        but2= (Button) findViewById(R.id.but2);
        but3= (Button) findViewById(R.id.but3);
        but4= (Button) findViewById(R.id.but4);
        but5= (Button) findViewById(R.id.but5);
        pingfenxinji= (LinearLayout) findViewById(R.id.pingfenxinji);
        pingjiatext= (EditText) findViewById(R.id.pingjiatext);

        String oredr_ida=getIntent().getStringExtra("oredr_id");
        String teacherId = getIntent().getStringExtra("teacher_id");
        uid=Integer.parseInt(User_id.getUid());
        oredr_id=Integer.parseInt(oredr_ida);
        new Teacher().Get_Teacher(Integer.parseInt(teacherId),this);
        jieinteun= (Button) findViewById(R.id.jieinteun);

        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        pingjiafanhui.bringToFront();
        jieinteun.setOnClickListener(this);
        pingjiafanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pingjiafanhui:
                Student_assessment.this.finish();
                break;
            case R.id.jieinteun:
                new AlertDialog.Builder(Student_assessment.this).setTitle("学了么提示!").setMessage("确定提交评论?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Order_init order_init=new Order();
                                long ac=11;
                                order_init.Comment_Order(uid,oredr_id,pingjiatext.getText().toString(),ac,rank,rantoke);
//                                order_init.UpdateOrder_score(uid,oredr_id,1,1,1,rantoke,rank);
                                Intent intent=NewMainActivity_.intent(Student_assessment.this).get();
                                startActivity(intent);
                                Toast.makeText(Student_assessment.this,"评论订单成功!",Toast.LENGTH_LONG).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.but1:
                pingfenxinji.setBackgroundResource(R.drawable.one);
                rantoke=3;
                rank = 1;
                pingfenTv.setText("1.0");
                break;
            case R.id.but2:
                pingfenxinji.setBackgroundResource(R.drawable.two);
                rantoke=3;
                rank = 2;
                pingfenTv.setText("2.0");
                break;
            case R.id.but3:
                pingfenxinji.setBackgroundResource(R.drawable.three);
                rantoke=2;
                rank = 3;
                pingfenTv.setText("3.0");
                break;
            case R.id.but4:
                pingfenxinji.setBackgroundResource(R.drawable.four);
                rantoke=2;
                rank =4;
                pingfenTv.setText("4.0");
                break;
            case R.id.but5:
                pingfenxinji.setBackgroundResource(R.drawable.five);
                rantoke=1;
                rank = 5;
                pingfenTv.setText("5.0");
                break;
        }
    }

    //收缩软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        DecimalFormat df   = new DecimalFormat("######0.00");
        double haopingNum = Double.parseDouble((String)map.get("haoping_num"));
        double order_rank = Double.parseDouble((String)map.get("order_rank"));

        goodTv.setText(df.format(haopingNum/order_rank)+"%");
        midTv.setText(df.format(1-haopingNum/order_rank)+"%");
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
