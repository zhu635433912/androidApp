package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhy.autolayout.AutoLayoutActivity;

import modle.user_ziliao.User_id;

/**
 * 帮助
 */

public class Hepl extends AutoLayoutActivity implements View.OnClickListener{
    private RelativeLayout bangzhufanhui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hepl);
        User_id.getInstance().addActivity(this);

        bangzhufanhui= (RelativeLayout) findViewById(R.id.bangzhufanhui);
        bangzhufanhui.bringToFront();

        bangzhufanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bangzhufanhui:
                Hepl.this.finish();
                break;
        }
    }
}
