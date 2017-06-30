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
    private RelativeLayout xuqiuRl;
    private RelativeLayout howToPlaceRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hepl);
        User_id.getInstance().addActivity(this);

        xuqiuRl = (RelativeLayout) findViewById(R.id.xuqiuwenti);
        howToPlaceRl = (RelativeLayout) findViewById(R.id.howto_place);
        bangzhufanhui= (RelativeLayout) findViewById(R.id.bangzhufanhui);
        bangzhufanhui.bringToFront();

        bangzhufanhui.setOnClickListener(this);
        xuqiuRl.setOnClickListener(this);
        howToPlaceRl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bangzhufanhui:
                Hepl.this.finish();
                break;
            case R.id.xuqiuwenti:
                startActivity(HowToPublishActivity_.intent(this).get());
                break;
            case R.id.howto_place:
                startActivity(PlaceActivity_.intent(this).get());
                break;

        }
    }
}
