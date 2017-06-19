package com.deguan.xuelema.androidapp;


import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.transition.Explode;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.fragment.MyPublishFragment_;
import com.deguan.xuelema.androidapp.fragment.OrderFragment;
import com.deguan.xuelema.androidapp.fragment.OrderFragment_;
import com.deguan.xuelema.androidapp.fragment.TuijianFragment_;
import com.deguan.xuelema.androidapp.huanxin.HuihuaList;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.MyPublishView;
import com.hyphenate.chat.EMClient;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.androidannotations.annotations.EActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Myconteol_init;
import modle.Adapter.HomeTitleAdapter;
import modle.Adapter.MfabuAdpter;
import modle.Adapter.Requirdapter;
import modle.Adapter.UserStudenAdpabt;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.JieYse.User_Modle;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.toos.CircleImageView;
import modle.toos.MyListview;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

/**
 * 我的（学生）
 */
public class Student_Activty extends AutoLayoutActivity implements View.OnClickListener,Student_init,
//        MyListview.IReflashListener,MyListview.RemoveListener,
        Requirdetailed{
    private RelativeLayout studentwodeqianbao;
    private RelativeLayout stidentshezhiimabt;
    private CircleImageView studenttouxiangimg;
    private TextView studentusernametext;
    private TextView studentneirongtext;
    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
//    private TextView myfabu;
//    private MyListview wodefabulist;
    private SimpleAdapter adapter;
//    private TextView mydindan;
    private int id;
    private int role;
    private int i=0;
    private Fragment mConversationList=null;
    private RelativeLayout wodejiaoshi;
    private RelativeLayout host;
    Myconteol_init myconteol_init;
    private TextView huihua;
    Demand_init demand_init;
    Order_init order_init;
    private UserStudenAdpabt userStudenAdpabt;
    private List<Map<String, Object>> listmap;
    private MfabuAdpter mfabuAdpter;
    private String xuqiuid;//需求id
    private AVLoadingIndicatorView xuesshuax;
    private ImageView wodegerxxstudent;
    private TextView wodegerxxstudenttext;
    private User_init user_init;
//    private TextView tuijianjiaoshi;
    private int TAGE_IRONG=0;
    private ImageView chatImage;
    private int number = 0;

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        User_id.getInstance().addActivity(this);
        //进入动画
     /*   Slide slide=new Slide();
        slide.setDuration(500);
        slide.setSlideEdge(Gravity.LEFT);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(new Explode().setDuration(600));*/

        tableLayout = (TabLayout) findViewById(R.id.student_tablayout);
        viewPager = (ViewPager) findViewById(R.id.student_viewpage);
//        tuijianjiaoshi= (TextView) findViewById(R.id.tuijianjiaoshi);
        chatImage = (ImageView) findViewById(R.id.chat_icon);
        huihua= (TextView) findViewById(R.id.huihua);
        studentwodeqianbao= (RelativeLayout) findViewById(R.id.studentwodeqianbao);
        stidentshezhiimabt= (RelativeLayout) findViewById(R.id.stidentshezhiimabt);
        studenttouxiangimg= (CircleImageView) findViewById(R.id.studenttouxiangimg);
        studentusernametext= (TextView) findViewById(R.id.studentusernametext);
        studentneirongtext= (TextView) findViewById(R.id.studentneirongtext);
        xuesshuax= (AVLoadingIndicatorView) findViewById(R.id.xuesshuax);
//        wodefabulist= (MyListview) findViewById(R.id.wodefabulist);
//        myfabu= (TextView) findViewById(R.id.myfabu);
//        mydindan= (TextView) findViewById(R.id.mydindan);
        wodejiaoshi= (RelativeLayout) findViewById(R.id.studentwodejiaoshi);
        host= (RelativeLayout) findViewById(R.id.studenthost);
        wodegerxxstudent= (ImageView) findViewById(R.id.wodegerxxstudent);
        wodegerxxstudenttext= (TextView) findViewById(R.id.wodegerxxstudenttext);

        wodegerxxstudent.setBackgroundResource(R.drawable.hly48);
        wodegerxxstudenttext.setTextColor(Color.parseColor("#f7e61c"));
//        number = EMClient.getInstance().chatManager().getConversation(User_id.getUsername()).getUnreadMsgCount();

        if (number != 0){
            chatImage.setImageResource(R.mipmap.chat_icon_new);
        }else {
            chatImage.setImageResource(R.mipmap.chat_icon);
        }
        xuesshuax.bringToFront();
        studentwodeqianbao.bringToFront();
        stidentshezhiimabt.bringToFront();
//        myfabu.setTextColor(android.graphics.Color.parseColor("#fd1245"));
        huihua.setOnClickListener(this);
        wodejiaoshi.setOnClickListener(this);
        host.setOnClickListener(this);
//        mydindan.setOnClickListener(this);
//        myfabu.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        studenttouxiangimg.setOnClickListener(this);
        studentwodeqianbao.setOnClickListener(this);
        stidentshezhiimabt.setOnClickListener(this);
//        wodefabulist.setRemoveListener(this);
//        tuijianjiaoshi.setOnClickListener(this);
        //获取用户id与角色
        String ida= User_id.getUid();
        String rolea=User_id.getRole();
        id=Integer.parseInt(ida);
        role=Integer.parseInt(rolea);

        //获取用户资料
        user_init=new User_Realization();
        user_init.User_Data(id,this);


        //获取用户自己的需求 这里开始
//        wodefabulist.setInterface(this);
        demand_init=new Demand();
        demand_init.getMyDemand_list(id,4,this);
        initData();

        //取消listview下拉上啦默认
//        wodefabulist.setOverScrollMode(View .OVER_SCROLL_NEVER);
//        wodefabulist.setVerticalScrollBarEnabled(false);

       /*
        myconteol_init=new Mycontrol();
        myconteol_init.getorderconlist(id,role+1,9,1,wodefabulist,getApplicationContext());*/

//        wodefabulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (i==0){
//                    //跳转接单列表
//                    String uid=mfabuAdpter.getuid(position-1);
//                    Intent intent=new Intent(Student_Activty.this,Pick_singleActivty.class);
//                    intent.putExtra("id",uid);
//                    startActivity(intent);
//                }else {
//                    //跳转我的订单页面
//                    Intent intent=new Intent(Student_Activty.this,Order_state.class);
//                    startActivity(intent);
//                }
//            }
//        });
    }

    private void initData() {

        titles.add("推荐教师");
        titles.add("我的发布");
        titles.add("我的订单");
        fragments.add(TuijianFragment_.builder().build());
        fragments.add(MyPublishFragment_.builder().build());
        fragments.add(OrderFragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    public void onClick(View v) {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        switch (v.getId()){
            case R.id.studentwodeqianbao:
                //设置
                startActivity(new Intent(Student_Activty.this, SetUp.class));
                if(version > 5 ){
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
                break;
            case R.id.stidentshezhiimabt:
                //钱包
                startActivity(new Intent(Student_Activty.this,FeeqianbaoActivty.class));
                if(version > 5 ){
                    overridePendingTransition(R.anim.animo_no, R.anim.slide_in_bottom);
                }

                break;
            case R.id.studenttouxiangimg:
                //个人信息
                Intent intentb=new Intent(Student_Activty.this,Personal_Activty.class);
                startActivity(intentb);
                break;
//            case R.id.myfabu:
//                //我的发布
//                TAGE_IRONG=0;
//                tuijianjiaoshi.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                mydindan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                myfabu.setTextColor(android.graphics.Color.parseColor("#fd1245"));
//                demand_init.getMyDemand_list(id,4,this);
//                i=0;
//                break;
//            case R.id.mydindan:
                //我的订单
//                tuijianjiaoshi.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                mydindan.setTextColor(android.graphics.Color.parseColor("#fd1245"));
//                myfabu.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                order_init=new Order();
//                order_init.getOrder_list(id,role-1,0,1,null,this,Student_Activty.this,0,3);
//                i=1;
//                break;
            case R.id.studenthost:
                Intent intentt=new Intent(Student_Activty.this,Xuqiufabu.class);
//                startActivity(intentt,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                startActivity(intentt);
                break;
            case R.id.studentwodejiaoshi:
                Intent intentz=new Intent(Student_Activty.this,MainActivity.class);
                startActivity(intentz);
                break;
            case R.id.chat_icon:
                //会话列表
//                Intent intent1=new Intent(Student_Activty.this,HuihuaList.class);
                Intent intent2 = new Intent(Student_Activty.this, modle.Huanxing.ui.MainActivity.class);
                startActivity(intent2);

                break;
            case R.id.huihua:
                //会话列表
//                Intent intent1=new Intent(Student_Activty.this,HuihuaList.class);
                Intent intent1 = new Intent(Student_Activty.this, modle.Huanxing.ui.MainActivity.class);
                startActivity(intent1);
                break;
//            case R.id.tuijianjiaoshi:
                //推荐教师
//                tuijianjiaoshi.setTextColor(android.graphics.Color.parseColor("#fd1245"));
//                mydindan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                myfabu.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                TAGE_IRONG=1;
//                Map<String,Object> map=mfabuAdpter.getmap(0);
//                Teacher_init teacher_init=new Teacher();
//                int course_id=Integer.parseInt(map.get("course_id")+"");
//                int grade_id=Integer.parseInt(map.get("grade_id")+"");
//                teacher_init.gettuijian_Teacher(course_id,grade_id,"",this);
//                break;
        }
    }



    //回调接口
    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        //订单详细
        userStudenAdpabt=new UserStudenAdpabt(listmap,this);
//        wodefabulist.setAdapter(userStudenAdpabt);
//        wodefabulist.reflashComplet();
    }


    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        if (TAGE_IRONG==0) {
            this.listmap = listmap;
            //我的需求回调  这里回调设置我的需求
            mfabuAdpter = new MfabuAdpter(listmap, this);
//            wodefabulist.setAdapter(mfabuAdpter);
//            wodefabulist.reflashComplet();
        }else {
            if (listmap!=null){
                Requirdapter requirdapter=new Requirdapter(listmap,this);
//                wodefabulist.setAdapter(requirdapter);
//                wodefabulist.reflashComplet();

            }
        }

    }

//    @Override
//    public void onReflash() {
//        if (i==1) {
//            order_init.getOrder_list(id,role-1,1,1,null,this,Student_Activty.this,0,3);
//        }else {
//            demand_init.getMyDemand_list(id,4,this);
//        }
//
//    }

//    @Override
//    public void removeItem(MyListview.RemoveDirection direction, final int position) {
//        switch (direction) {
//            case RIGHT:
//                new AlertDialog.Builder(Student_Activty.this).setTitle("学了吗提示!").setMessage("你确定删除当前订单吗?").
//                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    if (i==1) {
//                        xuqiuid=userStudenAdpabt.getuid(position-1);
//                    }else {
//                        xuqiuid=mfabuAdpter.getuid(position-1);
//                    }
//                    int uqid=Integer.parseInt(xuqiuid);
//                    if (i==1){
//                        Order_init order_init=new Order();
//                        order_init.Delete_Order(id,uqid,Student_Activty.this);
//                    }else {
//                        Demand_init demand_init = new Demand();
//                        demand_init.Delete_Demand(id, uqid);
//                        Toast.makeText(Student_Activty.this, "删除需求成功!", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            }).show();
//                break;
//            case LEFT:
//                new AlertDialog.Builder(Student_Activty.this).setTitle("学了吗提示!").setMessage("你确定删除吗?").
//                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (i==1) {
//                                    xuqiuid=userStudenAdpabt.getuid(position-1);
//                                }else {
//                                    xuqiuid=mfabuAdpter.getuid(position-1);
//                                }
//                                int uqid=Integer.parseInt(xuqiuid);
//                                if (i==1){
//                                    Order_init order_init=new Order();
//                                    order_init.Delete_Order(id,uqid,Student_Activty.this);
//                                }else {
//                                    demand_init.Delete_Demand(id,uqid);
//                                    Toast.makeText(Student_Activty.this, "删除需求成功!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//
//                    }
//                }).show();
//                break;
//
//            default:
//
//                break;
//        }
//    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

        if(map.get("nickname").toString()!=null) {
            studentusernametext.setText(map.get("nickname")+"");
            studentneirongtext.setText(map.get("signature")+"");

            setbitmap(map.get("headimg").toString());
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=new HashMap<String,Object>();
        map=listmap.get(0);
            Toast.makeText(this,map.get("text").toString(),Toast.LENGTH_SHORT).show();

    }


    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                studenttouxiangimg.post(new Runnable(){

                    @Override
                    public void run() {
                        studenttouxiangimg.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                        xuesshuax.setVisibility(View.GONE);
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
            conn.connect();//打开连`接
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
        user_init.User_Data(id,this);
    }
}
