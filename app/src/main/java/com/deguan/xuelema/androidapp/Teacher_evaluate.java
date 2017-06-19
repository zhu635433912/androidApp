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
import com.deguan.xuelema.androidapp.init.Student_init;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.Adapter.Evalunton_Adapdter;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

/**
 * 教师评价
 */

public class Teacher_evaluate extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed,PullToRefreshBase.OnRefreshListener2,Student_init {
    private TextView gerenjianjie;
    private RelativeLayout pingjiafanhui;
    private int teacher_id;
    private ImageView xinyuxinji;
    private TextView pingjiafenshu;
    private PullToRefreshListView pullToRefreshListView;
    private  Teacher_init teacher_init;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluation);
        User_id.getInstance().addActivity(this);
        gerenjianjie= (TextView) findViewById(R.id.gerenjianjie);
        pingjiafanhui= (RelativeLayout) findViewById(R.id.pingjiafanhui);
        xinyuxinji= (ImageView) findViewById(R.id.xinyuxinji);
        pingjiafenshu= (TextView) findViewById(R.id.pingjiafenshu);
        pullToRefreshListView= (PullToRefreshListView) findViewById(R.id.pinlun);

        pingjiafanhui.bringToFront();

        //下拉刷新
//        listView.setInterface(this);
//        listView.setRemoveListener(this);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);

        //获取教师id
        String teacher_ida=getIntent().getStringExtra("teacher_id");
        teacher_id=Integer.parseInt(teacher_ida);
        int uid=Integer.parseInt(User_id.getUid());

        Teacher_init teacher_init=new Teacher();
        teacher_init.Get_Teacher_detailed(uid,teacher_id,this,1);

        teacher_init.setEvaluation_Teacher(teacher_id,this);

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

        Map<String,Object> map=listmap.get(0);
        pingjiafenshu.setText(map.get("order_rank").toString());
        switch (map.get("order_rank").toString()){
            case "0.0":
                xinyuxinji.setBackgroundResource(R.drawable.one);
                pingjiafenshu.setText("0.0");
                break;
            case "1.0":
                xinyuxinji.setBackgroundResource(R.drawable.one);
                pingjiafenshu.setText("1.0");
                break;
            case "2.0":
                xinyuxinji.setBackgroundResource(R.drawable.two);
                pingjiafenshu.setText("2.0");
                break;
            case "3.0":
                xinyuxinji.setBackgroundResource(R.drawable.three);
                pingjiafenshu.setText("3.0");
                break;
            case "4.0":
                xinyuxinji.setBackgroundResource(R.drawable.four);
                pingjiafenshu.setText("4.0");
                break;
            case "5.0":
                xinyuxinji.setBackgroundResource(R.drawable.five);
                pingjiafenshu.setText("5.0");
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
    //加载更多

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        //下拉刷新
        teacher_init.setEvaluation_Teacher(teacher_id,this);
    }


    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        Evalunton_Adapdter evalunton_adapdter=new Evalunton_Adapdter(listmap,this);
        pullToRefreshListView.setAdapter(evalunton_adapdter);

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }
}
