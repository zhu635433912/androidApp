package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import view.login.Modle.MobileView;
import view.login.Modle.RegisterEntity;
import view.login.Modle.RegisterUtil;

@EActivity(R.layout.activity_vip_desc)
public class VipDescActivity extends MyBaseActivity implements MobileView {

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
        //钱包学生1  老师2  vip学生3 老师4 用户说明5
        new RegisterUtil().getDescPic(flag,this);
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void successRegister(String msg) {
        descImage.setImageURI(Uri.parse(msg));
    }

    @Override
    public void failRegister(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

    }
}
