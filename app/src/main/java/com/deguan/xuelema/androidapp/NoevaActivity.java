package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.fragment.EvaluationFragment_;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment_;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import modle.user_ziliao.User_id;


@EActivity(R.layout.activity_mynofinish)
public class NoevaActivity extends MyBaseActivity {

    @ViewById(R.id.back_image)
    ImageView backImage;
    @ViewById(R.id.layout)
    FrameLayout layout;
    @ViewById(R.id.title_tv)
    TextView titleTv;

    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView() {
        super.initView();
        if (User_id.getRole().equals("1")) {
            titleTv.setText("待评价订单");
        }else {
            titleTv.setText("已授课订单");
        }
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyPublishFragment fragment = MyPublishFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.add(R.id.layout, EvaluationFragment_.builder().build());
        transaction.commit();
    }
}
