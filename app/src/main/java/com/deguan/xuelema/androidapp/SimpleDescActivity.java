package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.Huanxing.ui.BaseActivity;
import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_simple_desc)
public class SimpleDescActivity extends MyBaseActivity implements Requirdetailed {

    @ViewById(R.id.teacher_desc)
    EditText descEdit;
    @ViewById(R.id.teacher_save)
    TextView saveTv;
    @ViewById(R.id.teacher_back)
    RelativeLayout backRl;

    @Override
    public void initData() {
        new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),
                Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void initView() {
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(descEdit.getText())) {
                    new Teacher().Teacher_resume(Integer.parseInt(User_id.getUid()), descEdit.getText() + "");
                    finish();
                }else {
                    Toast.makeText(SimpleDescActivity.this, "请介绍一下自己！", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("resume")+"")){
            descEdit.setText(map.get("resume")+"");
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
