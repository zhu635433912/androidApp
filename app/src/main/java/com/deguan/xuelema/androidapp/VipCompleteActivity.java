package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_vip_complete)
public class VipCompleteActivity extends MyBaseActivity {

    @ViewById(R.id.vip_complete_image)
    ImageView completeImage;

    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initView() {
        completeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
