package com.deguan.xuelema.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_jubao)
public class JubaoActivity extends MyBaseActivity {

    private String teacherId;

    @Override
    public void before() {
        teacherId = getIntent().getStringExtra("teacher_id");

    }

    @Override
    public void initView() {

    }
}
