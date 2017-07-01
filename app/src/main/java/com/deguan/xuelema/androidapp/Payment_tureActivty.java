package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import modle.user_ziliao.User_id;

/**
 * 支付完成
 */

public class Payment_tureActivty extends AutoLayoutActivity implements View.OnClickListener {
    private Button wancheng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                Intent intent=NewMainActivity_.intent(this).get();
                startActivity(intent);
                Toast.makeText(this,"赶快去学习吧~",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
