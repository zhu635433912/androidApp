package com.deguan.xuelema.androidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.inter_test;
import modle.user_ziliao.User_id;
import view.fee.Feechongzhi_fagment;
import view.fee.Withdrawals_fagment;

/**
 * 钱包
 */

public class FeeqianbaoActivty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed {
    private TextView chongzhi;
    private TextView xianjinjuan;
    private TextView tixian;
    private RelativeLayout qianbaohuitui;
    private TextView yuer;
    private TextView mingofee;
    private TextView textView4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        User_id.getInstance().addActivity(this);

        yuer= (TextView) findViewById(R.id.yuer);
        xianjinjuan= (TextView) findViewById(R.id.xianjinjuan);
        chongzhi= (TextView) findViewById(R.id.textView4);
        tixian= (TextView) findViewById(R.id.tixian);
        mingofee= (TextView) findViewById(R.id.mingofee);
        qianbaohuitui= (RelativeLayout) findViewById(R.id.qianbaohuitui);
        qianbaohuitui.bringToFront();
        yuer.setOnClickListener(this);
        qianbaohuitui.setOnClickListener(this);
        tixian.setOnClickListener(this);
        xianjinjuan.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        textView4= (TextView) findViewById(R.id.textView4);



        //获取用户余额
        int uid=Integer.parseInt(User_id.getUid());
        Getdata getdata=new Getdata();
        getdata.getFee(uid,this);

        //获取现金卷
        getdata.getmianfofee(uid,this);
        textView4.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView4:
                //充值
                Feechongzhi_fagment dindan_fagment=new Feechongzhi_fagment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
                beginTransaction.add(R.id.feechongzhi,dindan_fagment);
                beginTransaction.commit();
                break;
            case R.id.xianjinjuan:
                //跳转现金券
                Intent intent=new Intent(FeeqianbaoActivty.this,Cashvolume_Activty.class);
                startActivity(intent);

                break;
            case R.id.tixian:
                //提现
                Withdrawals_fagment withdrawalsFagment=new Withdrawals_fagment();
                FragmentManager fr=getFragmentManager();
                FragmentTransaction bt=fr.beginTransaction();
                bt.add(R.id.feechongzhi,withdrawalsFagment);
                bt.commit();
                break;
            case R.id.qianbaohuitui:
                //返回
                FeeqianbaoActivty.this.finish();
                break;
        }
    }


    @Override
    public void Updatecontent(Map<String, Object> map) {

            if (map.get("fee") != null) {
                double integer = (double) map.get("fee");
                yuer.setText("$"+integer);
            }

        if (map.get("TotalFee")==null){
            mingofee.setText("0");
        }else {
            String minfee= (String) map.get("TotalFee");
            mingofee.setText(minfee);
        }

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
