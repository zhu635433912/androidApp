package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.toos.CircleImageView;
import modle.user_ziliao.User_id;

/**
 * 教师个人简介
 */

public class Teacher_personal extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed {
    private TextView pingjia;
    private RelativeLayout grfanhui;
    private TextView xinjijiaoshi;
    private TextView techartext;
    private Map<String,Object> map;
    private TextView techaertec;
    private TextView techaerxue;
    private TextView jiaoling;
    private TextView teachername;
    private TextView diqu;
    private int teacher_id;
    private CircleImageView teachertoux;
    private ImageButton jianjiebohao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_jieshao);
        User_id.getInstance().addActivity(this);
        //获取教师id
        String teacher_ida=getIntent().getStringExtra("teacher_id");
        teacher_id=Integer.parseInt(teacher_ida);

        jianjiebohao= (ImageButton) findViewById(R.id.jianjiebohao);
        teachertoux= (CircleImageView) findViewById(R.id.teachertoux);
        pingjia= (TextView) findViewById(R.id.pingjia);
        xinjijiaoshi= (TextView) findViewById(R.id.xinjijiaoshi);
        grfanhui= (RelativeLayout) findViewById(R.id.grfanhui);
        techartext= (TextView) findViewById(R.id.techartext);
        techaertec= (TextView) findViewById(R.id.techaertec);
        techaerxue= (TextView) findViewById(R.id.techaerxue);
        jiaoling= (TextView) findViewById(R.id.jiaoling);
        teachername= (TextView) findViewById(R.id.teachername);
        diqu= (TextView) findViewById(R.id.diqu);
        grfanhui.bringToFront();

        jianjiebohao.setOnClickListener(this);
        jiaoling.setOnClickListener(this);
        grfanhui.setOnClickListener(this);
        xinjijiaoshi.setOnClickListener(this);
        pingjia.setOnClickListener(this);

        //获取教师个人资料
        int uid=Integer.parseInt(User_id.getUid());
        Teacher_init teacher_init=new Teacher();
        teacher_init.Get_Teacher(teacher_id,this);

        //获取教师详细资料
        teacher_init.Get_Teacher_detailed(uid,teacher_id,this,1);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pingjia:
                //评价
                Intent i=new Intent(Teacher_personal.this,Teacher_evaluate.class);
                i.putExtra("teacher_id",teacher_id+"");
                startActivity(i);
                break;
            case R.id.xinjijiaoshi:
                //星级教师
                Intent intent=new Intent(Teacher_personal.this,Start_Activty.class);
                intent.putExtra("teacher_id",teacher_id+"");
                startActivity(intent);
                break;
            case R.id.grfanhui:
            Teacher_personal.this.finish();
                break;
            case R.id.jianjiebohao:

                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        teachername.setText(map.get("nickname").toString());
        diqu.setText(map.get("address").toString());


        Log.e("aa","图片地址"+map.get("user_headimg").toString());
        setbitmap(map.get("user_headimg").toString());

        switch (map.get("order_rank").toString()){
            case "0.0":
                xinjijiaoshi.setText("一星级教师");
                break;
            case "1.0":
                xinjijiaoshi.setText("一星级教师");
                break;
            case "2.0":
                xinjijiaoshi.setText("二星级教师");
                break;
            case "3.0":
                xinjijiaoshi.setText("三星级教师");
                break;
            case "4.0":
                xinjijiaoshi.setText("四星级教师");
                break;
            case "5.0":
                xinjijiaoshi.setText("五星级教师");
                break;
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        map = listmap.get(0);

        techartext.setText(map.get("resume").toString());
        techaertec.setText(map.get("speciality_name").toString());
        techaerxue.setText(map.get("graduated_school").toString());
        jiaoling.setText(map.get("years").toString()+"年");



    }

    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                teachertoux.post(new Runnable(){

                    @Override
                    public void run() {
                        teachertoux.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
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
}
