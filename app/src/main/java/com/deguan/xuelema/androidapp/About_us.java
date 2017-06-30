package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 关于我们
 */

public class About_us extends AutoLayoutActivity implements View.OnClickListener ,Requirdetailed{
    private RelativeLayout guanyuwmenfanhui;
    private User_id application;
    private TextView webo;//微博
    private TextView weixiang;//微信
    private TextView kefuxinxi;//客服电话
    private TextView webwangzhi;//官方网址
    private TextView dizhi;//官方网址
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutus);
        User_id.getInstance().addActivity(this);
        guanyuwmenfanhui= (RelativeLayout) findViewById(R.id.guanyuwmenfanhui);
        webo= (TextView) findViewById(R.id.webo);
        weixiang= (TextView) findViewById(R.id.weixiang);
        kefuxinxi= (TextView) findViewById(R.id.kefuxinxi);
        webwangzhi= (TextView) findViewById(R.id.webwangzhi);
        dizhi= (TextView) findViewById(R.id.dizhi);

        //获取配置信息
        Getdata getdata=new Getdata();
        getdata.getadoutus(this);

        dizhi.setOnClickListener(this);
        kefuxinxi.setOnClickListener(this);
        guanyuwmenfanhui.bringToFront();
        weixiang.setOnClickListener(this);
        guanyuwmenfanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guanyuwmenfanhui:
                About_us.this.finish();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

//        webo.setText(map.get("weibo")+"");
//        kefuxinxi.setText(map.get("tel")+"");
//        webwangzhi.setText(map.get("web")+"");
//        dizhi.setText(map.get("email")+"");

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
