package com.deguan.xuelema.androidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.huanxin.HuihuaActivity;
import com.deguan.xuelema.androidapp.init.Bitmap_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.exceptions.HyphenateException;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.KechengAdapter;
import modle.GetBitmap_Url;
import modle.Huanxing.ui.ChatActivity;
import modle.Increase_course.Increase_course;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.toos.CircleImageView;
import modle.user_ziliao.User_id;
import view.fee.Purchase_figment;

import static android.R.attr.alpha;

/**
 * 老师个人信息
 */

public class UserxinxiActivty extends AutoLayoutActivity implements Requirdetailed,View.OnClickListener{
    private TextView Requiname;
    private TextView Requitext;
    private TextView Teachergerjianjie;
    private TextView pingjia;
    private RelativeLayout grfanhui;
    private TextView gerxxxuexiquan;
    private RelativeLayout jiaoyi;
    private ListView gerrxxedintext;
    private ImageButton bohao;
    private String mobile;
    private ImageButton imageButton2;
    private TextView gerxues;
    private TextView dindan;
    private List<Map<String, Object>> listmap;
    private int Requir_id;
    private String username;//教师id
    private ImageButton imageButton;
    private KechengAdapter kechengAdapter;
    private CircleImageView gerxxtoux;
    private AVLoadingIndicatorView jiaoyishuax;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi);
        User_id.getInstance().addActivity(this);
        imageButton= (ImageButton) findViewById(R.id.imageButton);
        gerxues= (TextView) findViewById(R.id.gerxues);
        dindan= (TextView) findViewById(R.id.dindan);
        Requiname= (TextView) findViewById(R.id.gerxxname);
        Requitext= (TextView) findViewById(R.id.gerxxneirong);
        Teachergerjianjie= (TextView) findViewById(R.id.gerjianjie);
        pingjia= (TextView) findViewById(R.id.pingjia);
        grfanhui= (RelativeLayout) findViewById(R.id.grfanhui);
        gerxxxuexiquan= (TextView) findViewById(R.id.gerxxxuexiquan);
        jiaoyi= (RelativeLayout) findViewById(R.id.jiaoyi);
        gerrxxedintext= (ListView) findViewById(R.id.gerrxxedintext);
        bohao= (ImageButton) findViewById(R.id.bohao);
        imageButton2= (ImageButton) findViewById(R.id.imageButton2);
        gerxxtoux= (CircleImageView) findViewById(R.id.gerxxtoux);
        jiaoyishuax= (AVLoadingIndicatorView) findViewById(R.id.jiaoyishuax);

        jiaoyishuax.bringToFront();
        gerxxxuexiquan.bringToFront();
        jiaoyi.bringToFront();
        grfanhui.bringToFront();

        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        bohao.setOnClickListener(this);
        pingjia.setOnClickListener(this);
        Teachergerjianjie.setOnClickListener(this);
        gerxxxuexiquan.setOnClickListener(this);
        grfanhui.setOnClickListener(this);

        final String user_id=getIntent().getStringExtra("user_id");
        Log.e("aa","UserxinActivyt接收到老师id为"+user_id);
        int id=Integer.parseInt(User_id.getUid());
        Requir_id=Integer.parseInt(user_id);



        //加载数据
        Teacher_init teacher=new Teacher();
        teacher.Get_Teacher_detailed(id,Requir_id,this,0);

        //加载课程数据
        Increase_course increaseCourse=new Increase_course();
        increaseCourse.selecouse(Requir_id,this,null);



        gerrxxedintext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map = new HashMap<String, Object>();
                map = listmap.get(position);
                String fee= (String) map.get("visit_fee");
                String fee1= (String) map.get("unvisit_fee");

                int unvisit_fee=Integer.parseInt(fee1);
                int visit_fee=Integer.parseInt(fee);
                //购买课程
                Purchase_figment purchase_fagment=new Purchase_figment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
                beginTransaction.add(R.id.jiaoyi,purchase_fagment);
                Bundle bundle = new Bundle();
                bundle.putInt("unvisit_fee",unvisit_fee);
                bundle.putInt("visit_fee",visit_fee);
                bundle.putInt("Requir_id",Requir_id);
                purchase_fagment.setArguments(bundle);
                beginTransaction.commit();
            }
        });

    }

    //更新教师详细资料
    @Override
    public void Updatecontent(Map<String, Object> map) {
    String nickname= (String) map.get("nickname");
    String resume= (String) map.get("resume");
    username= (String) map.get("username");


        mobile= (String) map.get("mobile");
        String order_finish= (String) map.get("order_finish");
        String order_working= (String) map.get("order_working");
        gerxues.setText(order_working);
     dindan.setText(order_finish);
    Requitext.setText(resume);
    Requiname.setText(nickname);

        Log.e("aa","头像地址为"+map.get("user_headimg").toString());
        setbitmap(map.get("user_headimg").toString());

    }

    //更新listview
    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        this.listmap=listmap;
        kechengAdapter = new KechengAdapter(listmap, this);
        gerrxxedintext.setAdapter(kechengAdapter);
        setListViewHeightBasedOnChildren(gerrxxedintext);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grfanhui:
                UserxinxiActivty.this.finish();
                break;
            case R.id.gerxxxuexiquan:
                //跳转成交率
                Intent intent = new Intent(UserxinxiActivty.this, Closing.class);
                startActivity(intent);
                break;
            case R.id.gerjianjie:
                //跳转个人简介
                Intent i = new Intent(UserxinxiActivty.this, Teacher_personal.class);
                i.putExtra("teacher_id", Requir_id + "");
                startActivity(i);
                break;
            case R.id.pingjia:
                //评价
                Intent pj = new Intent(UserxinxiActivty.this, Teacher_evaluate.class);
                pj.putExtra("teacher_id", Requir_id + "");
                startActivity(pj);
                break;
            case R.id.bohao:
                //拨号
                Log.e("aa", "拨号成功");
                Intent inte = new Intent(Intent.ACTION_DIAL);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.setData(Uri.parse("tel:" + mobile));
                startActivity(inte);
                break;
            case R.id.imageButton2:
                //聊天
//                Intent intent1 = new Intent(UserxinxiActivty.this, HuihuaActivity.class);
                Intent intent1 = new Intent(UserxinxiActivty.this, ChatActivity.class);
                intent1.putExtra(EaseConstant.EXTRA_USER_ID, username);
                intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(intent1);
                break;
            case R.id.imageButton:
                try {
                    EMClient.getInstance().contactManager().addContact(username, "添加好友");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

        /**
         * 动态设置ListView的高度
         */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }



    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                gerxxtoux.post(new Runnable(){

                    @Override
                    public void run() {
                        gerxxtoux.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                        jiaoyishuax.setVisibility(View.GONE);
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
