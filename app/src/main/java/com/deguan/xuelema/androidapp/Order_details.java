package com.deguan.xuelema.androidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Ordercontent_init;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Map;

import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * 订单详细
 */

public class Order_details extends AutoLayoutActivity implements Ordercontent_init ,View.OnClickListener {
    private int order_id;
    private int uid;
    private TextView name;
    private TextView order_status;
    private TextView dizhi;
    private TextView closingtime;
    private TextView grade;
    private TextView course;
    private RelativeLayout dindanxiangxihuitui;
    private RelativeLayout xuqiufenggexian7;
    private int duration;
    private int fee;
    private TextView keshishufee;
    private TextView kechengjieshu;
    private TextView ordettuikuan;
    private TextView zongjijine;
    private TextView xuqiuneiro;
    private TextView dindan_id;
    private TextView gender;
    private TextView fuwufan;
    private RelativeLayout ycang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dindanxxi);
        User_id.getInstance().addActivity(this);
        name = (TextView) findViewById(R.id.name);
        order_status = (TextView) findViewById(R.id.kechengzhuangtai);
        dizhi = (TextView) findViewById(R.id.dizhi);
        closingtime = (TextView) findViewById(R.id.closingtime);
        keshishufee= (TextView) findViewById(R.id.keshishufee);
        grade = (TextView) findViewById(R.id.nianji);
        kechengjieshu= (TextView) findViewById(R.id.kechengjieshu);
        course= (TextView) findViewById(R.id.course);
        ordettuikuan= (TextView) findViewById(R.id.ordettuikuan);
        dindanxiangxihuitui= (RelativeLayout) findViewById(R.id.dindanxiangxihuitui);
        xuqiufenggexian7= (RelativeLayout) findViewById(R.id.querenwanc);
        zongjijine= (TextView) findViewById(R.id.zongjijine);
        xuqiuneiro= (TextView) findViewById(R.id.xuqiuneiro);
        dindan_id= (TextView) findViewById(R.id.dindan_id);
        gender= (TextView) findViewById(R.id.gender);
        fuwufan= (TextView) findViewById(R.id. fuwufan);
        ycang= (RelativeLayout) findViewById(R.id.ycang);
        dindanxiangxihuitui.bringToFront();

        if(User_id.getRole().equals("2")){
            ycang.setVisibility(View.GONE);
            xuqiufenggexian7.setVisibility(View.GONE);
        }

        ordettuikuan.setOnClickListener(this);
        xuqiufenggexian7.setOnClickListener(this);
        dindanxiangxihuitui.setOnClickListener(this);

        //获取订单id与用户id
        String order_ida = getIntent().getStringExtra("oredr_id");
        String durationa=getIntent().getStringExtra("duration");
        String uida = User_id.getUid();
        duration=Integer.parseInt(durationa);

        order_id = Integer.parseInt(order_ida);
        uid = Integer.parseInt(uida);

        Log.e("aa", "订单详细收到的订单id为" + order_id + "与用户id为" + uida);

        //根据订单号用户id去后台获取订单详细信息
        Order_init order_init = new Order();
        order_init.getOrder_danyilist(uid, order_id, this);


    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        String status = (String) map.get("status");
        String requirement_address = (String) map.get("requirement_address");
        String created = (String) map.get("created");
        String requirement_grade = (String) map.get("requirement_grade");
        String requirement_course = (String) map.get("requirement_course");
        String feae= (String) map.get("fee");


        fee=Integer.parseInt(feae);
       int duration=Integer.parseInt(map.get("duration").toString());
        kechengjieshu.setText("x"+duration+"节");
        keshishufee.setText("￥"+fee+"/节");

        course.setText(requirement_course);
        grade.setText(requirement_grade);
        closingtime.setText(created);
        dizhi.setText(requirement_address);
        zongjijine.setText("￥"+(fee*duration));
        switch (status){
            case "1":
                order_status.setText("未付款");
                break;
            case "2":
                order_status.setText("进行中");
                break;
            case "3":
                order_status.setText("交易完成");
                break;
        }

        if (User_id.getRole().equals("1")) {
            name.setText(""+map.get("teacher_name"));
        }else {
            name.setText(""+map.get("placer_name"));
        }
        dizhi.setText(""+map.get("requirement_address"));
        xuqiuneiro.setText(""+map.get("requirement_content"));
        dindan_id.setText(""+map.get("id"));

        String ger=map.get("requirement_gender")+"";
        if (ger.equals("1")){
            gender.setText("男");
        }else {
            gender.setText("女");
        }
        String stuts=map.get("requirement_service_type")+"";
        switch (stuts){
            case "1":
                fuwufan.setText("教师上门");
                break;
            case "2":
                fuwufan.setText("学生上门");
                break;
            case "3":
                fuwufan.setText("第三方");
                break;
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.dindanxiangxihuitui:
            Order_details.this.finish();
            break;
        case R.id.querenwanc:
            new AlertDialog.Builder(Order_details.this).setTitle("学了么提示!").setMessage("确定完成交易?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Order_init order_init=new Order();
                            String password=User_id.getPassword();
                            order_init.Update_Order(uid,order_id,3,password,duration*fee);
                            Intent intent=new Intent(Order_details.this,Student_Activty.class);
                            startActivity(intent);
                            Toast.makeText(Order_details.this,"赶快去评价这位老师吧~",Toast.LENGTH_LONG).show();

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            break;
        case R.id.ordettuikuan:
          //订单退款
            new AlertDialog.Builder(Order_details.this).setTitle("学了么提示!").setMessage("确定退款吗?")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Order_init order_init=new Order();
                            String password=User_id.getPassword();
                            order_init.Update_Order(uid,order_id,4,password,duration*fee);
                            Intent intent=new Intent(Order_details.this,Student_Activty.class);
                            startActivity(intent);
                            Toast.makeText(Order_details.this,"退款成功!~",Toast.LENGTH_LONG).show();

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
            break;
    }
    }
}
