package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 现金卷
 */

public class Cashvolume_Activty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed{
    private Button xinjinjuantixian;
    private RelativeLayout xianjinjbufanhui;
    private TextView mogint;
    private TextView rogsi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashvolume);
        User_id.getInstance().addActivity(this);

        xinjinjuantixian= (Button) findViewById(R.id.xinjinjuantixian);
        xianjinjbufanhui= (RelativeLayout) findViewById(R.id.xianjinjbufanhui);
        mogint= (TextView) findViewById(R.id.tuiguangjine);
        rogsi= (TextView) findViewById(R.id.rogasi);

        xianjinjbufanhui.bringToFront();
        xianjinjbufanhui.setOnClickListener(this);
        xinjinjuantixian.setOnClickListener(this);


        //获取用户余额
        int uid=Integer.parseInt(User_id.getUid());
        Getdata getdata=new Getdata();
        getdata.getmianfofee(uid,this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xinjinjuantixian:
                Toast.makeText(this,"提现成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.xianjinjbufanhui:
                Cashvolume_Activty.this.finish();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

        if (map.get("RecomCount")==null) {
            rogsi.setText("共推广了0人");
        }else {
            rogsi.setText("共推广了" + map.get("RecomCount") + "人");
        }


        if (map.get("TotalFee")==null){
            mogint.setText("0");
        }else {
            mogint.setText((String)map.get("TotalFee"));
        }


    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
