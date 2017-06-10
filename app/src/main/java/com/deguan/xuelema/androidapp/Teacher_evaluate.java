package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

/**
 * 教师评价
 */

public class Teacher_evaluate extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed {
    private TextView gerenjianjie;
    private RelativeLayout pingjiafanhui;
    private int teacher_id;
    private ListView pinlun;
    private ImageView xinyuxinji;
    private TextView pingjiafenshu;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);
        User_id.getInstance().addActivity(this);
        gerenjianjie= (TextView) findViewById(R.id.gerenjianjie);
        pingjiafanhui= (RelativeLayout) findViewById(R.id.pingjiafanhui);
        pinlun= (ListView) findViewById(R.id.pinlun);
        xinyuxinji= (ImageView) findViewById(R.id.xinyuxinji);
        pingjiafenshu= (TextView) findViewById(R.id.pingjiafenshu);
        pingjiafanhui.bringToFront();

        //获取教师id
        String teacher_ida=getIntent().getStringExtra("teacher_id");
        teacher_id=Integer.parseInt(teacher_ida);
        int uid=Integer.parseInt(User_id.getUid());

        Teacher_init teacher_init=new Teacher();
        teacher_init.Get_Teacher_detailed(uid,teacher_id,this,1);

        gerenjianjie.setOnClickListener(this);
        pingjiafanhui.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.gerenjianjie:
                Intent intent=new Intent(Teacher_evaluate.this,Teacher_personal.class);
                intent.putExtra("teacher_id",teacher_id+"");
                startActivity(intent);
                break;
            case R.id.pingjiafanhui:
                Teacher_evaluate.this.finish();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        if (listmap.toString()!=null) {
            SimpleAdapter adapter = new SimpleAdapter(this, listmap, R.layout.pingjia_listview, new String[]{"order_comment"}, new int[]{R.id.textpj});
            pinlun.setAdapter(adapter);
        }
        Map<String,Object> map=listmap.get(0);

        pingjiafenshu.setText(map.get("order_rank").toString());
        switch (map.get("order_rank").toString()){
            case "0.0":
                xinyuxinji.setBackgroundResource(R.drawable.one);
                break;
            case "1.0":
                xinyuxinji.setBackgroundResource(R.drawable.one);
                break;
            case "2.0":
                xinyuxinji.setBackgroundResource(R.drawable.two);
                break;
            case "3.0":
                xinyuxinji.setBackgroundResource(R.drawable.three);
                break;
            case "4.0":
                xinyuxinji.setBackgroundResource(R.drawable.four);
                break;
            case "5.0":
                xinyuxinji.setBackgroundResource(R.drawable.five);
                break;
        }
    }
}
