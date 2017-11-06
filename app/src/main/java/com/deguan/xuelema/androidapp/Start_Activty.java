package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

/**
 * 星级教师
 */

public class Start_Activty extends MyBaseActivity implements View.OnClickListener,Requirdetailed{
    private RelativeLayout xinjijiaoshihuitui;
    private TextView jjiaoshi;
    private int teacher_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.star_teachers);
        User_id.getInstance().addActivity(this);
        xinjijiaoshihuitui= (RelativeLayout) findViewById(R.id.xinjijiaoshihuitui);
        jjiaoshi= (TextView) findViewById(R.id.jjiaoshi);

        //获取教师id
        String teacher_ida=getIntent().getStringExtra("teacher_id");
        teacher_id=Integer.parseInt(teacher_ida);
        //获取教师个人资料
        Teacher_init teacher_init=new Teacher();
        teacher_init.Get_Teacher(teacher_id,this);

        xinjijiaoshihuitui.bringToFront();
        xinjijiaoshihuitui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xinjijiaoshihuitui:
                Start_Activty.this.finish();
                break;
        }
    }


    @Override
    public void Updatecontent(Map<String, Object> map) {

        switch (map.get("order_rank").toString()){
            case "0.0":
                jjiaoshi.setText("一级教师");
                break;
            case "1.0":
                jjiaoshi.setText("一级教师");
                break;
            case "2.0":
                jjiaoshi.setText("二级教师");
                break;
            case "3.0":
                jjiaoshi.setText("三级教师");
                break;
            case "4.0":
                jjiaoshi.setText("四级教师");
                break;
            case "5.0":
                jjiaoshi.setText("五级教师");
                break;
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
