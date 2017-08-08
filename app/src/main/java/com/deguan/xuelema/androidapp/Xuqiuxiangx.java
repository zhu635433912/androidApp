package com.deguan.xuelema.androidapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.huanxin.HuihuaActivity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
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

import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Huanxing.ui.*;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.getdata.Getdata;
import modle.toos.CircleImageView;
import modle.user_ziliao.User_id;


/**
 * 需求详细
 */

public class Xuqiuxiangx extends AutoLayoutActivity implements Xuqiuxiangx_init,View.OnClickListener,Requirdetailed,PullToRefreshBase.OnRefreshListener, Student_init, SimilarXuqiuView, PayView {
    private PullToRefreshListView listview;
    private List<Map<String, Object>> listamap;
    private TextView textView;
    private TextView Requirname;
    private TextView Demandcontent;
    private TextView Relasetime;
    private TextView userage;
    private ImageButton xuqiudianh;
    private RelativeLayout xuqiufanhui;
    ImageButton xuqiuweix;
    private TextView xingbie;
    private  TextView fuwufang;
    private TextView shijianduan;
    private String username;
    private TextView diqu;
    private ImageView xuqiuimage;
    private AVLoadingIndicatorView xuqiushuax;
    private int fee;
    private int id;
    private int dindan;
    private int user_id;
    private TextView kemuleibie;
    private TextView nianjia;
    private XuqiuAdapter xuqiuAdapter;
    private List<XuqiuEntity> datas = new ArrayList<>();
    private Demand_init demand_init;
    private int filter_type = 0;
    private int filter_id = 0;
    private int course_id ;
    private int grade_id  ;
    private TextView xueliTv;
    private TextView phoneTv;
    private ImageButton addFriend;
    private ImageButton bohaoImage;
    private boolean ispass = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xuqiulayout);
        User_id.getInstance().addActivity(this);

        bohaoImage = (ImageButton) findViewById(R.id.bohao);
        phoneTv = (TextView) findViewById(R.id.xuqiu_tel_tv);
        addFriend = (ImageButton) findViewById(R.id.add_friend);
        xueliTv = (TextView) findViewById(R.id.xuqiu_xueli_tv);
        kemuleibie= (TextView) findViewById(R.id.kemuleibie);
        nianjia= (TextView) findViewById(R.id.nianjia);
        listview = (PullToRefreshListView) findViewById(R.id.xiangsixuqiulist);
        textView = (TextView) findViewById(R.id.jiequxuqiu);
        Requirname= (TextView) findViewById(R.id.xuqiuname);
        Demandcontent= (TextView) findViewById(R.id.xuqiuneirong);
        Relasetime= (TextView) findViewById(R.id.fabushijian);
        userage= (TextView) findViewById(R.id.nianling);
        xuqiudianh= (ImageButton) findViewById(R.id.xuqiudianh);
        xuqiufanhui= (RelativeLayout) findViewById(R.id.xuqiufanhui);
        xuqiuweix= (ImageButton) findViewById(R.id.xuqiuweix);
        xingbie= (TextView) findViewById(R.id.xingbie);
        fuwufang= (TextView) findViewById(R.id.fuwufang);
        shijianduan= (TextView) findViewById(R.id.shijianduan);
        diqu= (TextView) findViewById(R.id.diqu);
        xuqiuimage= (ImageView) findViewById(R.id.xuqiuimage);
        xuqiushuax= (AVLoadingIndicatorView) findViewById(R.id.xuqiushuax);
        Demandcontent.setMovementMethod(ScrollingMovementMethod.getInstance());

        xuqiuimage.setOnClickListener(this);
        xuqiufanhui.bringToFront();
        xuqiuweix.setOnClickListener(this);
        xuqiufanhui.setOnClickListener(this);
        addFriend.setOnClickListener(this);
        bohaoImage.setOnClickListener(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            xuqiushuax.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        //获取需求id与用户id
        String  publisher_id=getIntent().getStringExtra("publisher_id");
        String uds = getIntent().getStringExtra("user_id");
        String feeva= getIntent().getStringExtra("fee");
//        Integer i
                fee = Integer.valueOf((Math.round(Float.parseFloat(feeva))));
//        fee=Integer.parseInt(feeva);
        id = Integer.parseInt(User_id.getUid());
        dindan = Integer.parseInt(uds);
        user_id=Integer.parseInt(publisher_id);
        course_id = Integer.parseInt(getIntent().getStringExtra("course_id"));
        grade_id = Integer.parseInt(getIntent().getStringExtra("grade_id"));

//        Log.e("aa", "需求详细接收到需求编号为" + dindan + "发布者id为"+user_id+"金额为"+feeva);

        //相似需求
        listview.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listamap = new ArrayList<Map<String, Object>>();
        xuqiuAdapter = new XuqiuAdapter(datas,this);
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, Data(), R.layout.myindex_list, new String[]{"text"}, new int[]{R.id.myindeusername});
        listview.setAdapter(xuqiuAdapter);

        //需求详细信息展示
        demand_init=new Demand(this);
        demand_init.getDemand_danyi(id,dindan,this);
//        demand_init.getDemand_list(user_id,Integer.parseInt(User_id.getRole()),filter_type,filter_id,"2016-08-10",0,1,null,null,this);
        xuqiudianh.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Xuqiuxiangx.this, Xuqiuxiangx.class);
                intent.putExtra("user_id",datas.get(position-1).getId());
                intent.putExtra("fee",datas.get(position-1).getFee());
                intent.putExtra("publisher_id",datas.get(position-1).getPublisher_id());
                intent.putExtra("course_id",datas.get(position-1).getCourse_id());
                intent.putExtra("grade_id",datas.get(position-1).getGrade_id());

//                Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                intent.putExtra("head_image",listmap.get(position).getPublisher_headimg());
//                intent.putExtra("user_id",listmap.get(position).getPublisher_id());
                startActivity(intent);
            }
        });

        new Teacher().Get_Teacher1(Integer.parseInt(User_id.getUid()),this);
    }


    private List<Map<String, Object>> Data() {
        for (int i = 0; i < 10; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", i + "个");
            listamap.add(map);
        }
        return listamap;
    }

    //更新内容
    @Override
    public void Updatecontent(Map<String, Object> map) {

        String publisher_name = (String) map.get("publisher_name");
        String content = (String) map.get("content");
        String created = (String) map.get("created");
        String age = (String) map.get("age");
        String gender = (String) map.get("gender");
        String service_type = (String) map.get("service_type");
        String start = (String) map.get("start");
        String end = (String) map.get("end");
        if (map.get("education_id").equals("0")){
            xueliTv.setText("学历不限");
        }else if (map.get("education_id").equals("1")){
            xueliTv.setText("专科");
        }else if (map.get("education_id").equals("2")){
            xueliTv.setText("本科");
        }else if (map.get("education_id").equals("3")){
            xueliTv.setText("硕士");
        }else if (map.get("education_id").equals("4")){
            xueliTv.setText("博士");
        }
            course_id = Integer.parseInt((String)map.get("course_id"));
        grade_id = Integer.parseInt((String)map.get("grade_id"));
        demand_init.getTuijianDemand_list(course_id,User_id.getUid(),User_id.getLat()+"",""+User_id.getLng(),null,null,null,listview,this,null);
        String state = "";
        if ((map.get("address")+"").length()>7) {
             state = ((String) map.get("address")).substring(0,7)+"......";
        }else {
            state = ((String) map.get("address"));
        }

        if (!TextUtils.isEmpty(map.get("ordernum").toString())){
            String ordernum =  map.get("ordernum").toString();
            textView.setText("已有"+ordernum+"人接取");
        }
        diqu.setText(state+"");
        username = (String) map.get("publisher_mobile");

        Glide.with(this).load(map.get("publisher_headimg")+"").transform(new GlideCircleTransform(this)).into(xuqiuimage);
        phoneTv.setText(username);
        String desc=start+" - "+end;

        if (gender.equals("1")) {
            xingbie.setText("男性");
        }else {
            xingbie.setText("女性");
        }

//        if (service_type.equals("1")) {
//            fuwufang.setText("教师上门");
//        }else {
//            fuwufang.setText("学生上门");
//        }
//        if (!TextUtils.isEmpty(map.get("education_name").toString())){
//            xueliTv.setText((String)map.get("education_name"));
//        }
        fuwufang.setText((String)map.get("service_type_txt"));
        shijianduan.setText(desc);
        if (age.equals("不限")) {
            userage.setText(age + "年龄");
        }else {
            userage.setText(age + "岁");
        }
        Relasetime.setText(created);
        Demandcontent.setText(content);
        Requirname.setText(publisher_name);
//        kemuleibie.setText(map.get("course_name")+"");
        nianjia.setText(map.get("course_name")+" "+map.get("grade_name")+"");
//        setbitmap(map.get("publisher_headimg")+"");

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=listmap.get(0);

        if (map.get("tosa") .equals("创建订单成功")){
            new Getdata().sendMessage("您的需求已被"+User_id.getNickName()+"接取",username);
//            new Getdata().sendMessage("有老师接取了你的需求快去看看我的发布里吧!",username);
        }
        Toast.makeText(Xuqiuxiangx.this,(String)map.get("tosa"),Toast.LENGTH_SHORT).show();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xuqiuimage:


                break;
            case  R.id.bohao:
                if (ispass) {
                    //拨号
                    Log.e("aa", "拨号成功");
                    Intent inte = new Intent(Intent.ACTION_DIAL);
                    inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inte.setData(Uri.parse("tel:" + username));
                    startActivity(inte);
                }else {
                    Toast.makeText(this, "请完善信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_friend:
                if (ispass) {
                    Toast.makeText(this, "已发送好友申请", Toast.LENGTH_SHORT).show();
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                //demo use a hardcode reason here, you need let user to input if you like
                                String s = getResources().getString(R.string.Add_a_friend);
                                EMClient.getInstance().contactManager().addContact(username, s);
                            } catch (final Exception e) {

                            }
                        }
                    }).start();
                }else {
                    Toast.makeText(this, "请完善信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.xuqiufanhui:
                Xuqiuxiangx.this.finish();
                break;
            case R.id.xuqiuweix:
                if (ispass) {
                    //聊天
//                Intent intent1=new Intent(Xuqiuxiangx.this, HuihuaActivity.class);
                    Intent intent1 = new Intent(Xuqiuxiangx.this, ChatActivity.class);
                    intent1.putExtra(EaseConstant.EXTRA_USER_ID, username);
                    intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                    startActivity(intent1);
                }else {
                    Toast.makeText(this, "请完善信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.xuqiudianh:
                if (ispass) {
                    new AlertDialog.Builder(Xuqiuxiangx.this).setTitle("学了么提示!").setMessage("是否确定接取需求?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Order_init order_init = new Order();
                                    order_init.CreateOrder(user_id, id, dindan, fee, course_id, grade_id, Xuqiuxiangx.this, User_id.getAddress(), User_id.getLat(), User_id.getLng());
                                    Intent intent = NewMainActivity_.intent(Xuqiuxiangx.this).get();
                                    startActivity(intent);
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Xuqiuxiangx.this, "再去看看别的需求吧~", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }else {
                    Toast.makeText(this, "请完善信息等待审核通过,并发布相应的课程才能接单哦", Toast.LENGTH_SHORT).show();
                }
                break;
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
                xuqiuimage.post(new Runnable(){

                    @Override
                    public void run() {
                        xuqiuimage.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                        xuqiushuax.setVisibility(View.GONE);
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
    public void onRefresh(PullToRefreshBase refreshView) {
        demand_init.getTuijianDemand_list(course_id,User_id.getUid(),""+User_id.getLat(),""+User_id.getLng(),null,null,null,listview,this,null);
//        demand_init.getDemand_list(user_id,Integer.parseInt(User_id.getRole()),filter_type,filter_id,"2016-08-10",0,1,null,null,this);
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        listview.onRefreshComplete();
            datas.clear();
        List<XuqiuEntity> lists = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            XuqiuEntity entity = new XuqiuEntity();
            entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
            entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setCourse_name((String) maps.get(i).get("course_name"));
            entity.setContent((String) maps.get(i).get("content"));
            entity.setCreated((String) maps.get(i).get("created"));
            entity.setId((String) maps.get(i).get("id"));
            if (maps.get(i).get("id").equals(dindan+"")) {
                continue;
            }
            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setGrade_name((String)maps.get(i).get("grade_name"));
            entity.setCourse_id((String)maps.get(i).get("course_id"));
            entity.setGrade_id((String)maps.get(i).get("grade_id"));
//            entity.setAddress((String)maps.get(i).get("address"));
            if ((maps.get(i).get("address")+"").length()>7) {
                entity.setAddress(((String) maps.get(i).get("address")).substring(0, 7) + "......");
            }else {
                entity.setAddress((String) maps.get(i).get("address"));
            }
            lists.add(entity);
        }
        datas.addAll(lists);
        xuqiuAdapter.notifyDataSetChanged();
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        listview.onRefreshComplete();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successPay(Map<String, Object> map) {
        if (map.get("is_passed").equals("1")){
            ispass = true;
        }
    }

    @Override
    public void failPay(String msg) {

    }
}
