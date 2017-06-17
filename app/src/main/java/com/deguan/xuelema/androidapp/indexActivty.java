package com.deguan.xuelema.androidapp;


import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.huanxin.HuihuaList;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import modle.toos.CircleImageView;
import modle.toos.MScrollView;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;
import view.mywode.Dindan_fagment;
import view.mywode.Fabuxuqiu_fagment;


/**
 * 我的(教师)
 */

public class indexActivty extends AutoLayoutActivity implements View.OnClickListener, Xuqiuxiangx_init,MScrollView.MyScrollListener,Requirdetailed {
    private TextView textView;
    private RelativeLayout requir;
    private RelativeLayout host;
    private RelativeLayout qianbao;
    private String ids;
    private String roles;
    FragmentManager fragmentManager;
    FragmentTransaction beginTransaction;
    Dindan_fagment dindan_fagment;
    Fabuxuqiu_fagment fabu;
    private TextView tuijianxuqiu;
    private RelativeLayout setup;
    private CircleImageView circleImageView;
    private RelativeLayout xingxibutton;
    private RelativeLayout wodejiaoshi;
    private int i = 1;
    private TextView huihua;
    private TextView xues;
    private ImageView hostimaview;
    private int z = 1;
    private View view;
    private RelativeLayout move;
    int topDistance;
    int height;
    TextView head;
    private MScrollView scrollView;
    private RelativeLayout relayoutindex;
    private RelativeLayout stop;
    private TextView tuijianxuqiu1;;
     private TextView textView1;
    private TextView usernametext;
    private TextView neirongtext;
    private int uid;
    private ImageView wodegerxx;
    private TextView wod;
    private User_init user_init;
    private LinearLayout index_dindanlinenarla;
    private LinearLayout tuikuanzhong;
    private LinearLayout jinxinzhong;
    private LinearLayout daipingjia;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myindex);
        EventBus.getDefault().register(this);

//        Fade fade = (Fade) TransitionInflater.from(this).inflateTransition(R.transition.activity_hold);
//        getWindow().setEnterTransition(fade);
//
//        //进入动画
//        Slide slide=new Slide();
//        slide.setDuration(500);
//        slide.setSlideEdge(Gravity.LEFT);
//        getWindow().setEnterTransition(slide);
//        getWindow().setReenterTransition(new Explode().setDuration(600));


        //获取用户是教师还是学生展示相应的参数
        User_id.getInstance().addActivity(this);
        LayoutInflater inflater = LayoutInflater.from(indexActivty.this);
        view = inflater.inflate(R.layout.myindex, null);

        daipingjia= (LinearLayout) findViewById(R.id.daipingjia);
        jinxinzhong= (LinearLayout) findViewById(R.id.jinxinzhong);
        tuikuanzhong= (LinearLayout) findViewById(R.id.tuikuanzhong);
        index_dindanlinenarla= (LinearLayout) findViewById(R.id.index_dindanlinenarla);
        neirongtext= (TextView) findViewById(R.id.neirongtext);
        usernametext= (TextView) findViewById(R.id.usernametext);
        stop= (RelativeLayout) findViewById(R.id.sopt);
        head= (TextView) findViewById(R.id.head);
        move= (RelativeLayout) findViewById(R.id.fenggexiantext1);
        scrollView= (MScrollView) findViewById(R.id.scv);
        scrollView.requestDisallowInterceptTouchEvent(false);
        circleImageView = (CircleImageView) findViewById(R.id.touxiangimg);
        tuijianxuqiu = (TextView) findViewById(R.id.tuijianxuqiutext);
        textView = (TextView) findViewById(R.id.wodedingdan);
        tuijianxuqiu1 = (TextView) findViewById(R.id.tuijianxuqiutext1);
        textView1 = (TextView) findViewById(R.id.wodedingdan1);
        qianbao = (RelativeLayout) findViewById(R.id.wodeqianbao);
        wodejiaoshi = (RelativeLayout) findViewById(R.id.wodejiaoshi);
        requir = (RelativeLayout) findViewById(R.id.wode);
        host = (RelativeLayout) findViewById(R.id.host);
        setup = (RelativeLayout) findViewById(R.id.shezhiimabt);
        xingxibutton = (RelativeLayout) findViewById(R.id.xingxibutton);
        usernametext = (TextView) findViewById(R.id.usernametext);
        huihua = (TextView) findViewById(R.id.huihua);
        xues = (TextView) findViewById(R.id.xues);
        hostimaview = (ImageView) findViewById(R.id.hostimaview);
        wodegerxx= (ImageView) findViewById(R.id.wodegerxx);
        wod= (TextView) findViewById(R.id.wod);

        wodegerxx.setBackgroundResource(R.drawable.hly48);
        wod.setTextColor(Color.parseColor("#f7e61c"));

        index_dindanlinenarla.setVisibility(View.GONE);
        xingxibutton.bringToFront();
        setup.bringToFront();
        qianbao.bringToFront();
        scrollView.setMyScrollListener(this);
        if (!TextUtils.isEmpty(User_id.getUid())) {
            uid = Integer.parseInt(User_id.getUid());
        }
        int role = Integer.parseInt(User_id.getRole());
        if (role == 2) {
            xues.setText("学生");
            hostimaview.setBackgroundResource(R.drawable.logo);
            z = 2;
        }

        jinxinzhong.setOnClickListener(this);
        tuikuanzhong.setOnClickListener(this);
        index_dindanlinenarla.setOnClickListener(this);
        huihua.setOnClickListener(this);
        usernametext.setOnClickListener(this);
        wodejiaoshi.setOnClickListener(this);
        xingxibutton.setOnClickListener(this);
        circleImageView.setOnClickListener(this);
        setup.setOnClickListener(this);
        host.setOnClickListener(this);
        tuijianxuqiu.setOnClickListener(this);
        tuijianxuqiu1.setOnClickListener(this);
        qianbao.setOnClickListener(this);
        textView.setOnClickListener(this);
        textView1.setOnClickListener(this);
        requir.setOnClickListener(this);


        //获取用户昵称与资料
        user_init=new User_Realization();
        user_init.User_Data(uid,this);

        //加载我的订单
        dindan_fagment = new Dindan_fagment();
        fragmentManager = getSupportFragmentManager();
        beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(R.id.myindexfagment, dindan_fagment);
        beginTransaction.commit();

        textView.setTextColor(Color.parseColor("#fd1245"));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wodeqianbao:
                //钱包
                Intent intent = new Intent(indexActivty.this, FeeqianbaoActivty.class);
                startActivity(intent);
                break;
            case R.id.wodejiaoshi:
                //教师
                Intent intenta = new Intent(indexActivty.this, MainActivity.class);
                //进入动画
                startActivity(intenta);
                break;
            case R.id.wodedingdan:
                //我的订单
                if (i != 1) {
                    i = 1;
                    textView.setTextColor(Color.parseColor("#fd1245"));
                    tuijianxuqiu.setTextColor(Color.parseColor("#8b8b8b"));

                    dindan_fagment = new Dindan_fagment();
                    fragmentManager = getSupportFragmentManager();
                    beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add(R.id.myindexfagment, dindan_fagment);
                    beginTransaction.hide(fabu);
                    beginTransaction.show(dindan_fagment);
                    beginTransaction.commit();
                }
                break;
            case R.id.tuijianxuqiutext:
                //推荐需求
                if (i != 2) {
                    i = 2;
                    textView.setTextColor(Color.parseColor("#8b8b8b"));
                    tuijianxuqiu.setTextColor(Color.parseColor("#fd1245"));

                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    fabu = new Fabuxuqiu_fagment();
                    transaction.add(R.id.myindexfagment, fabu);
                    transaction.hide(dindan_fagment);
                    transaction.show(fabu);
                    transaction.commit();
                }
                break;
            case R.id.wodedingdan1:
                //我的订单
                if (i != 1) {
                    i = 1;
                    dindan_fagment = new Dindan_fagment();
                    fragmentManager = getSupportFragmentManager();
                    beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add(R.id.myindexfagment, dindan_fagment);
                    beginTransaction.hide(fabu);
                    beginTransaction.show(dindan_fagment);
                    beginTransaction.commit();
                }
                break;
            case R.id.tuijianxuqiutext1:
                //推荐需求
                if (i != 2) {
                    i = 2;
                    index_dindanlinenarla.setVisibility(View.GONE);
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction transaction = fm.beginTransaction();
                    fabu = new Fabuxuqiu_fagment();
                    transaction.add(R.id.myindexfagment, fabu);
                    transaction.hide(dindan_fagment);
                    transaction.show(fabu);
                    transaction.commit();
                }
                break;
            case R.id.host:
                //需求发布
                if (z != 2) {
                    Intent intentb = new Intent(indexActivty.this, Xuqiufabu.class);
                    startActivity(intentb);
//                    startActivity(intentb, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                break;
            case R.id.shezhiimabt:
                Intent intentc = new Intent(indexActivty.this, SetUp.class);
                startActivity(intentc);
                break;
            case R.id.touxiangimg:
                Log.e("aa", "点击头像");
                Intent inten = new Intent(indexActivty.this, Personal_Activty.class);
                startActivity(inten);
                break;
            case R.id.xingxibutton:
                Intent intena = new Intent(indexActivty.this, Teacher_management.class);
                startActivity(intena);
                break;
            case R.id.huihua:
                //会话列表
                Intent intent1=new Intent(indexActivty.this, modle.Huanxing.ui.MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.jinxinzhong:
                //进行中
                EventBus.getDefault().post(1,"Orderrefresh");
                break;
            case R.id.tuikuanzhong:
                //退款
                EventBus.getDefault().post(2,"Orderrefresh");
                break;
            case R.id.daipingjia:
                //待评价
                EventBus.getDefault().post(3,"Orderrefresh");
                break;
        }
    }


    @Override
    public void Updatecontent(Map<String, Object> map) {
        usernametext.setText((String)map.get("nickname"));
        neirongtext.setText((String)map.get("signature"));

        setbitmap(map.get("headimg").toString());
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            topDistance = move.getTop();  //获取位置3，即内部绿色栏的顶部到布局顶部的距离
            height = head.getMeasuredHeight();  //获取位置2，不就是搜索栏的高度么，啊哈哈哈，是不是很机智，当然你也可以用getButtom，一样的，看你自己
        }
    }

    @Override
    public void sendDistanceY(int scrollY) {
        Log.d("aa","crolIY="+scrollY+"---"+(topDistance - height));
        if(scrollY >= topDistance - height){  //如果滑动的距离大于或等于位置3到位置2的距离，那么说明内部绿色的顶部在位置2上面了，我们需要显示外部绿色栏了
            stop.setVisibility(View.VISIBLE);
            if (i==1) {
                index_dindanlinenarla.setVisibility(View.VISIBLE);
            }
        }else {  //反之隐藏
            stop.setVisibility(View.GONE);
            if (i==1) {
                index_dindanlinenarla.setVisibility(View.GONE);
            }
        }
    }

    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                circleImageView.post(new Runnable(){
                    @Override
                    public void run() {
                        circleImageView.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片

                    }

                });

            }
        }).start();//开启线程
    }

    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param path
     * @return Bitmap
     * */
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        user_init.User_Data(uid,this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}