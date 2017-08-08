package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.GlideRoundTransform;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_teacher_man)
public class TeacherManActivity extends MyBaseActivity implements Requirdetailed {

    @ViewById(R.id.teacher_man_person)
    LinearLayout personLl;
    @ViewById(R.id.teacher_man_xueli)
    LinearLayout educationLl;
    @ViewById(R.id.teacher_man_rongyu)
    LinearLayout rongyuLl;
    @ViewById(R.id.teacher_man_manager)
    LinearLayout managerLl;
    @ViewById(R.id.teacher_man_example)
    LinearLayout exampleLl;
    @ViewById(R.id.teacher_man_exper)
    LinearLayout experLl;
    @ViewById(R.id.man_head)
    ImageView headImage;
    @ViewById(R.id.teacher_man_sign)
    TextView signTv;
    @ViewById(R.id.teacher_man_back)
    RelativeLayout backRl;




    @Override
    public void before() {
        super.before();
    }


    @Override
    public void initView() {
        personLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherManActivity.this,Personal_Activty.class));
            }
        });
        educationLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EducationActivity_.intent(TeacherManActivity.this).get());
            }
        });
        rongyuLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HonorActivity_.intent(TeacherManActivity.this).get());
            }
        });
        managerLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ManagerActivity_.intent(TeacherManActivity.this).get());
            }
        });
        exampleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExampleActivity_.intent(TeacherManActivity.this).get());
            }
        });
        experLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExerActivity_.intent(TeacherManActivity.this).get());
            }
        });
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        new Teacher().Get_Teacher_detailed(0,Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("user_headimg")+""))
        Glide.with(this).load(map.get("user_headimg")).transform(new GlideRoundTransform(this,12)).into(headImage);
        if (!TextUtils.isEmpty(map.get("signature")+""))
            signTv.setText(map.get("signature")+"");

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
