package com.deguan.xuelema.androidapp.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.deguan.xuelema.androidapp.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_base)
public class MyBaseActivity extends AppCompatActivity {

    @AfterInject
    public void before(){

    }
    @AfterViews
    public final void init(){
        initView();
        initData();
        initListener();
    }

    public void initListener() {

    }

    public void initData() {

    }

    public void initView() {

    }
}
