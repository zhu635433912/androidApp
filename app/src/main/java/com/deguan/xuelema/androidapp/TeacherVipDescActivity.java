package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
@EActivity(R.layout.activity_vip_desc)
public class TeacherVipDescActivity extends MyBaseActivity {

    @ViewById(R.id.vip_desc_image)
    SimpleDraweeView descImage;
    @ViewById(R.id.back_rl)
    RelativeLayout backRl;
    private int flag = 0;

    @Override
    public void before() {
        super.before();
        flag = getIntent().getIntExtra("type",0);
    }

    @Override
    public void initView() {

        descImage.setImageURI(Uri.parse("http://deguan.tpddns.cn:82/des.png"));
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}