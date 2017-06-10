package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 一级二级分销
 */

public class MyDistribution_Actvity extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed {
    private RelativeLayout fenxiaofanhui;
    Getdata getdata;
    private TextView zongordet;
    private TextView zongrogin;
    int uid;
    private TextView yijifenxiaofee;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelsales);
        User_id.getInstance().addActivity(this);
        fenxiaofanhui= (RelativeLayout) findViewById(R.id.fenxiaofanhui);
        yijifenxiaofee= (TextView) findViewById(R.id.yijifenxiaofee);
        zongordet= (TextView) findViewById(R.id.zongordet);
        zongrogin= (TextView) findViewById(R.id.zongrogin);

        int level=Integer.parseInt(getIntent().getStringExtra("level"));
        uid=Integer.parseInt(User_id.getUid());
        getdata=new Getdata();
        getdata.getinfo(uid,level,this);


        fenxiaofanhui.bringToFront();
        fenxiaofanhui.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fenxiaofanhui:
                MyDistribution_Actvity.this.finish();
                break;
        }

    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        zongordet.setText("共交易了"+map.get("TotalBill")+"单");
        zongrogin.setText(map.get("TotalUser")+"人");
        if (map.get("TotalFee")!=null){
            yijifenxiaofee.setText((String)map.get("TotalFee"));
        }else {
            yijifenxiaofee.setText("0.00");
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
