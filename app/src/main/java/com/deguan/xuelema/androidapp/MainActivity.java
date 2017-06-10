package com.deguan.xuelema.androidapp;


import android.*;
import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.zhy.autolayout.AutoLayoutActivity;

import modle.Basequanxian;
import modle.user_ziliao.User_id;
import view.index.Teacher_fragment;
/*
主页列表
 */

public class MainActivity extends AutoLayoutActivity implements View.OnClickListener {
    private RelativeLayout but1;
    private String ids;
    private String roles;
    private RelativeLayout host;
    private TextView bianji;
    private ImageView hostimaview;
    private int i=0;
    private ImageView jiaoshitubiao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //进入动画
     /*   Slide slide=new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(new Explode().setDuration(600));*/


        User_id.getInstance().addActivity(this);
        but1= (RelativeLayout) findViewById(R.id.woder);
        host= (RelativeLayout) findViewById(R.id.host);
        bianji= (TextView) findViewById(R.id.bianji);
        hostimaview= (ImageView) findViewById(R.id.hostimaview);
        jiaoshitubiao= (ImageView) findViewById(R.id.jiaoshitubiao);

        jiaoshitubiao.setBackgroundResource(R.drawable.hly11);
        bianji.setTextColor(Color.parseColor("#f7e61c"));

        bianji.setOnClickListener(this);
        but1.setOnClickListener(this);
        host.setOnClickListener(this);

        //获取用户是教师还是学生展示相应的参数
        ids=User_id.getUid();
        roles=User_id.getRole();
        int id=Integer.parseInt(ids);
        int role=Integer.parseInt(roles);
         if (role==2){
             bianji.setText("学生");
             hostimaview.setBackgroundResource(R.drawable.logo);
              i=1;
         }

        Teacher_fragment teacher_fragment=new Teacher_fragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.hostlineat,teacher_fragment);

        beginTransaction.commit();


        EMClient.getInstance().login(User_id.getUsername(),User_id.getPassword(),new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("aa", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("aa", "登录聊天服务器失败！");
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.host:
                //需求发布
                if (i==0) {
                    Intent intent = new Intent(MainActivity.this, Xuqiufabu.class);
                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                break;
            case R.id.woder:
                if (User_id.getRole().equals("1")){
                    //跳转我的页面
                    Intent intentc = new Intent(MainActivity.this, Student_Activty.class);
                    intentc.putExtra("user_id", ids);
                    intentc.putExtra("user_id", roles);
                    startActivity(intentc,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }else {
                    //跳转老师页面
                    Intent intenta = new Intent(MainActivity.this, indexActivty.class);
                    intenta.putExtra("user_id", ids);
                    intenta.putExtra("user_id", roles);
                    startActivity(intenta, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                break;
        }
    }
}
