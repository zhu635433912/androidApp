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

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.APPConfig;
import com.deguan.xuelema.androidapp.utils.SharedPreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import modle.Basequanxian;
import modle.Gaode.Gaode_dinwei;
import modle.Huanxing.cache.UserCacheManager;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import view.index.Teacher_fragment;
/*
主页列表
 */

public class MainActivity extends AutoLayoutActivity implements View.OnClickListener, Requirdetailed {
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
        EventBus.getDefault().register(this);
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
        new Getdata().getmobieke(User_id.getUsername(),this);
        Teacher_fragment teacher_fragment=new Teacher_fragment();
        FragmentManager fragmentManager=getFragmentManager();
        FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
        beginTransaction.add(R.id.hostlineat,teacher_fragment);

        beginTransaction.commit();


//        EMClient.getInstance().login(User_id.getUsername(),User_id.getPassword(),new EMCallBack() {//回调
//            @Override
//            public void onSuccess() {
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
//                Log.e("aa", "登录聊天服务器成功！");
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                Log.e("aa", code+"登录聊天服务器失败！"+message);
//            }
//        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.host:
                //需求发布
                if (i==0) {
                    Intent intent = new Intent(MainActivity.this, Xuqiufabu.class);
//                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    startActivity(intent);
                }
                break;
            case R.id.woder:
                if (User_id.getRole().equals("1")){
                    //跳转我的页面
                    Intent intentc = new Intent(MainActivity.this, Student_Activty.class);
                    intentc.putExtra("user_id", ids);
                    intentc.putExtra("user_id", roles);
//                    startActivity(intentc,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    startActivity(intentc);
                }else {
                    //跳转老师页面
                    Intent intenta = new Intent(MainActivity.this, indexActivty.class);
                    intenta.putExtra("user_id", ids);
                    intenta.putExtra("user_id", roles);
                    startActivity(intenta);
//                    startActivity(intenta, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                break;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
//        if (requestCode == 100){
//            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
//        }
        Log.d("aa","------requestCode"+requestCode);
        EventBus.getDefault().post(requestCode,"requsetPermiss");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        String imageUrl = (String)map.get("headimg");
        Log.e("aa","头像地址"+imageUrl);
        SharedPreferencesUtils.setParam(this, APPConfig.USER_HEAD_IMG,imageUrl);
        getUser_id().setImageUrl(imageUrl);
        // 登录成功，将用户的环信ID、昵称和头像缓存在本地
        UserCacheManager.save(User_id.getUsername(), User_id.getUsername(), imageUrl);
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
                Log.e("aa", code+"登录聊天服务器失败！"+message);
            }
        });
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }
}
