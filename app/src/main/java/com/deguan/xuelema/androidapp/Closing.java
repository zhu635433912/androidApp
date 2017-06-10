package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.autolayout.AutoLayoutActivity;

import modle.user_ziliao.User_id;

/**
 * 成交率
 */

public class Closing extends AutoLayoutActivity implements View.OnClickListener {
    private RelativeLayout chengjiaolvfanhui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing);
        User_id.getInstance().addActivity(this);

        chengjiaolvfanhui= (RelativeLayout) findViewById(R.id.chengjiaolvfanhui);
        chengjiaolvfanhui.bringToFront();

        chengjiaolvfanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chengjiaolvfanhui:
                Closing.this.finish();
                break;
        }

    }
}
