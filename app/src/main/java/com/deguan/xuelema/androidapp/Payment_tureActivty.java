package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;

import modle.user_ziliao.User_id;

/**
 * 支付完成
 */

public class Payment_tureActivty extends AutoLayoutActivity implements View.OnClickListener {
    private Button wancheng;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.paycomplete);
        User_id.getInstance().addActivity(this);
        wancheng= (Button) findViewById(R.id.wancheng);

        wancheng.setOnClickListener(this);
        ImageButton backBtn = (ImageButton) findViewById(R.id.wancheng_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wancheng:
                EventBus.getDefault().post(1,"changeStatus");
                Intent intent= new Intent(this,MyOrderActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(this,"赶快去学习吧~",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
