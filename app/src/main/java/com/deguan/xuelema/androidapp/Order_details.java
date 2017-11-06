package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Ordercontent_init;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.AMapUtil;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 订单详细
 */

public class Order_details extends MyBaseActivity implements Ordercontent_init ,View.OnClickListener {
    private int order_id;
    private int uid;
    private TextView name;
    private TextView order_status;
    private TextView dizhi;
    private TextView closingtime;
    private TextView grade;
    private TextView course;
    private RelativeLayout dindanxiangxihuitui;
    private RelativeLayout xuqiufenggexian7;
    private int duration;
    private int fee;
    private TextView keshishufee;
    private TextView kechengjieshu;
    private TextView ordettuikuan;
    private TextView zongjijine;
    private TextView xuqiuneiro;
    private TextView dindan_id;
    private TextView gender;
    private TextView fuwufan;
    private RelativeLayout ycang,contentRl;
    private String status;
    private SimpleDraweeView headImage;
    private TextView statuse,telTv;
    private String teacherImage;
    private int duration1;
    private String telphone;
    private double tolFee;
    private ImageView detail_tel,detail_chat;
    private SimpleDraweeView exampleImage1,exampleImage2,exampleImage3,exampleImage4;
    private TextView payTv,contentTv,suggestTv,orderDesc,teacherTimeTv;
    private LinearLayout imageLl;
    private UserInfo mMyInfo;
    private String imageurl1,imageurl2,imageurl3,imageurl4;
    private String lat,lng;
    private LinearLayout addressRl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.dindanxxi);
        User_id.getInstance().addActivity(this);
        mMyInfo = JMessageClient.getMyInfo();
        teacherTimeTv = (TextView) findViewById(R.id.order_teacher_time);
        addressRl = (LinearLayout)findViewById(R.id.order_address_rl);
        orderDesc = (TextView) findViewById(R.id.order_pay_desc);
        contentTv = (TextView) findViewById(R.id.order_detail_content);
        suggestTv = (TextView) findViewById(R.id.order_detail_suggest);
        imageLl = (LinearLayout) findViewById(R.id.order_detail_ll);
        contentRl = (RelativeLayout) findViewById(R.id.order_content_rl);
        exampleImage1 = (SimpleDraweeView) findViewById(R.id.order_detail_image1);
        exampleImage2 = (SimpleDraweeView) findViewById(R.id.order_detail_image2);
        exampleImage3 = (SimpleDraweeView) findViewById(R.id.order_detail_image3);
        exampleImage4 = (SimpleDraweeView) findViewById(R.id.order_detail_image4);
//        payTv = (TextView) findViewById(R.id.textView6);
        detail_tel = (ImageView) findViewById(R.id.detail_tel);
        detail_chat = (ImageView) findViewById(R.id.detail_chat);
        headImage = (SimpleDraweeView) findViewById(R.id.order_detail_headimg);
        telTv = (TextView) findViewById(R.id.dianhuahaoma);
        name = (TextView) findViewById(R.id.name);
        statuse= (TextView) findViewById(R.id.statuse);
        order_status = (TextView) findViewById(R.id.kechengzhuangtai);
        dizhi = (TextView) findViewById(R.id.dizhi);
        closingtime = (TextView) findViewById(R.id.closingtime);
        keshishufee= (TextView) findViewById(R.id.keshishufee);
        grade = (TextView) findViewById(R.id.nianji);
        kechengjieshu= (TextView) findViewById(R.id.kechengjieshu);
        course= (TextView) findViewById(R.id.course);
        ordettuikuan= (TextView) findViewById(R.id.ordettuikuan);
        dindanxiangxihuitui= (RelativeLayout) findViewById(R.id.dindanxiangxihuitui);
        xuqiufenggexian7= (RelativeLayout) findViewById(R.id.querenwanc);
        zongjijine= (TextView) findViewById(R.id.zongjijine);
        xuqiuneiro= (TextView) findViewById(R.id.xuqiuneiro);
        dindan_id= (TextView) findViewById(R.id.dindan_id);
        gender= (TextView) findViewById(R.id.gender);
        fuwufan= (TextView) findViewById(R.id.fuwufan);
        ycang= (RelativeLayout) findViewById(R.id.ycang);
        dindanxiangxihuitui.bringToFront();
        ordettuikuan.setOnClickListener(this);
        xuqiufenggexian7.setOnClickListener(this);
        dindanxiangxihuitui.setOnClickListener(this);
        headImage.setOnClickListener(this);
        addressRl.setOnClickListener(this);
        name.setOnClickListener(this);
        detail_chat.setOnClickListener(this);
        exampleImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(Order_details.this, PictureZoo.class);
                intent23.putExtra("hide",imageurl1);
                startActivity(intent23);

            }
        });
        exampleImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(Order_details.this, PictureZoo.class);
                intent23.putExtra("hide",imageurl2);
                startActivity(intent23);

            }
        });
        exampleImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(Order_details.this, PictureZoo.class);
                intent23.putExtra("hide",imageurl3);
                startActivity(intent23);

            }
        });
        exampleImage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent23 = new Intent(Order_details.this, PictureZoo.class);
                intent23.putExtra("hide",imageurl4);
                startActivity(intent23);

            }
        });

        if(User_id.getRole().equals("2")){
            ycang.setVisibility(View.GONE);
            xuqiufenggexian7.setVisibility(View.GONE);
        }
        //获取订单id与用户id
        String order_ida = getIntent().getStringExtra("oredr_id");
        String durationa=getIntent().getStringExtra("duration");
        teacherImage = getIntent().getStringExtra("teacher_headimg");

//        Glide.with(getApplicationContext()).load(teacherImage).
//                transform(new GlideCircleTransform(this)).into(headImage);
        headImage.setImageURI(Uri.parse(teacherImage));

        status=getIntent().getStringExtra("status");
        String uida = User_id.getUid();
        duration=Integer.parseInt(durationa);
        order_id = Integer.parseInt(order_ida);
        uid = Integer.parseInt(uida);
        switch (status){
            case "1":
                statuse.setText("支付到平台");
                ycang.setVisibility(View.GONE);
                orderDesc.setText("您已成功下单，请支付课程费用到平台");
                break;
            case "7":
                statuse.setText("确认支付");
                orderDesc.setText("老师已经对你作出了建议,请您确认支付并对老师作出评价。7天之后将会自动确认并默认五星好评哦");
                break;
            case "2":
                statuse.setText("等待老师授课");
                orderDesc.setText("您已成功支付，请与老师联系，建议一个良好的开始，并等待老师授课");
                break;
            case "3":
                statuse.setText("去评价");
                if(!User_id.getRole().equals("2")) {
                    ycang.setVisibility(View.GONE);
                }
                orderDesc.setText("请您对老师作出评价，7天之后将会自动默认五星好评哦");
                break;
            case "4":
                if(!User_id.getRole().equals("2")) {
                    ycang.setVisibility(View.GONE);
                    xuqiufenggexian7.setVisibility(View.GONE);
                }
                break;
            case "5":
                statuse.setText("已同意退款");
                ycang.setVisibility(View.GONE);
                if(!User_id.getRole().equals("2")) {
                    ycang.setVisibility(View.GONE);
                    xuqiufenggexian7.setVisibility(View.GONE);
                }
                break;
            case "6":
                statuse.setText("已拒绝退款");
                if(!User_id.getRole().equals("2")) {
                    ycang.setVisibility(View.GONE);
                }
                break;
            case "8":
                statuse.setText("授课完成");
                ycang.setVisibility(View.GONE);
                if(!User_id.getRole().equals("2")) {
                    ycang.setVisibility(View.GONE);
                    xuqiufenggexian7.setVisibility(View.GONE);
                }
                orderDesc.setText("您已对老师作出了评价，感谢您使用学习吧平台，祝您生活愉快！");
                break;
        }

//        Log.e("aa", "订单详细收到的订单id为" + order_id + "与用户id为" + uida);

        //根据订单号用户id去后台获取订单详细信息
        Order_init order_init = new Order();
        order_init.getOrder_danyilist(uid, order_id, this);

        telTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拨号
//                Log.e("aa", "拨号成功");
                Intent inte = new Intent(Intent.ACTION_DIAL);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.setData(Uri.parse("tel:" + telTv.getText().toString()));
                startActivity(inte);
            }
        });
        detail_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //拨号
//                Log.e("aa", "拨号成功");
                Intent inte = new Intent(Intent.ACTION_DIAL);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.setData(Uri.parse("tel:" + telTv.getText().toString()));
                startActivity(inte);
            }
        });
    }

    private String teacherId;

    @Override
    public void Updatecontent(Map<String, Object> map) {
        String status = (String) map.get("status");
        String requirement_address = (String) map.get("address");
        Date d = new Date(Long.parseLong(map.get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String created = sdf.format(d);
        String requirement_grade = (String) map.get("grade_name");
        String requirement_course = (String) map.get("course_name");
        String feae= (String) map.get("fee");

//        teacherImage = (String) map.get("teacher_headimg");
//            Glide.with(getApplicationContext()).load((String) map.get("teacher_headimg")).
//                    transform(new GlideCircleTransform(this)).into(headImage);
        headImage.setImageURI(Uri.parse(""+ map.get("teacher_headimg")));
        teacherId = (String) map.get("teacher_id");

        telTv.setText(Html.fromHtml("<u>"+(String)map.get("teacher_mobile")+""+"</u>"));
        telphone = map.get("teacher_mobile")+"";
        fee=Integer.parseInt(feae);
        duration1 = Integer.parseInt(map.get("duration").toString());
        kechengjieshu.setText("x"+ duration1 +"节");
        keshishufee.setText("¥"+fee+"/节");

        lat = map.get("lat")+"";
        lng = map.get("lng")+"";

//        payTv.setText(map.get("pay_desc")+"");
        course.setText(requirement_course);
        grade.setText(requirement_grade);

        closingtime.setText(created);
        dizhi.setText(requirement_address);
        if (Double.parseDouble(map.get("credit")+"")!=0){
            zongjijine.setText("¥"+map.get("order_price")+"代金券"+map.get("credit"));
        }else if (Double.parseDouble(map.get("reward_fee")+"") != 0){
            zongjijine.setText("¥"+map.get("order_price")+"红包"+map.get("reward_fee"));
        }else {
            zongjijine.setText("¥" + map.get("order_price"));
        }
//        zongjijine.setText("¥"+map.get("order_price"));
        tolFee = Double.parseDouble(map.get("order_fee")+"");
        switch (status){
            case "1":
                order_status.setText("未付款");
                break;
            case "2":
                order_status.setText("进行中");
                break;
            case "7":
                order_status.setText("老师已授课");
                break;
            case "3":
                order_status.setText("交易完成");
                break;
            case "4":
                order_status.setText("申请退款中");
                break;
            case "5":
                order_status.setText("退款成功");
                break;
            case "6":
                order_status.setText("退款失败,请联系客服");
                break;
            case "8":
                order_status.setText("授课完成");
        }

        if (User_id.getRole().equals("1")) {
            name.setText(""+map.get("teacher_name"));
        }else {
            name.setText(""+map.get("placer_name"));
        }
//        dizhi.setText(""+map.get("address"));
        xuqiuneiro.setText(""+map.get("desc"));
//        xuqiuneiro.setText("德冠网络科技公司");
        dindan_id.setText(""+map.get("id"));

        String ger=map.get("teacher_gender")+"";
        if (ger.equals("1")||ger.equals("男")){
            gender.setText("男");
        }else {
            gender.setText("女");
        }
        String stuts=map.get("service_type")+"";
        switch (stuts){
            case "1":
                fuwufan.setText("教师上门");
                break;
            case "2":
                fuwufan.setText("学生上门");
                break;
            case "3":
                fuwufan.setText("第三方");
                break;
        }
        if (map.get("is_complete").equals("1")){
            Map<String,String>  datas = (Map<String, String>) map.get("teacher_comment");
            contentRl.setVisibility(View.VISIBLE);
            suggestTv.setVisibility(View.VISIBLE);
            contentTv.setVisibility(View.VISIBLE);
            teacherTimeTv.setText(map.get("comfirmtime")+"");
            contentTv.setText("教学内容：   "+datas.get("content"));
            suggestTv.setText(datas.get("教师寄语：   "+"evaluate"));
            if (TextUtils.isEmpty(datas.get("img1")+"")&&TextUtils.isEmpty(datas.get("img2")+"")&&TextUtils.isEmpty(datas.get("img3")+"")&&TextUtils.isEmpty(datas.get("img4")+"")){
                imageLl.setVisibility(View.GONE);
            }else if (!TextUtils.isEmpty(datas.get("img1")+"")){
                imageurl1 = datas.get("img1")+"";
                imageLl.setVisibility(View.VISIBLE);
//                Glide.with(getApplicationContext()).load(datas.get("img1")+"").into(exampleImage1);
                exampleImage1.setImageURI(Uri.parse(datas.get("img1")+""));
                if (!TextUtils.isEmpty(datas.get("img2")+"")){
                    imageLl.setVisibility(View.VISIBLE);
                    exampleImage2.setImageURI(Uri.parse(datas.get("img2")+""));
                    imageurl2 = datas.get("img2")+"";
//                    Glide.with(getApplicationContext()).load(datas.get("img2")+"").into(exampleImage2);
                    if (!TextUtils.isEmpty(datas.get("img3")+"")){
                        imageLl.setVisibility(View.VISIBLE);
                        exampleImage3.setImageURI(Uri.parse(datas.get("img3")+""));
                        imageurl3 = datas.get("img3")+"";
//                        Glide.with(getApplicationContext()).load(datas.get("img3")+"").into(exampleImage3);
                        if (!TextUtils.isEmpty(datas.get("img4")+"")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage4.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl4 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4")+"").into(exampleImage4);
                        }
                    }else {
                        if (!TextUtils.isEmpty(datas.get("img4")+"")){
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage3.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl3 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4")+"").into(exampleImage3);
                        }
                    }
                } else  {
                    if (!TextUtils.isEmpty(datas.get("img3")+"")) {
                        imageLl.setVisibility(View.VISIBLE);
                        exampleImage2.setImageURI(Uri.parse(datas.get("img3")+""));
                        imageurl2 = datas.get("img3")+"";
//                        Glide.with(getApplicationContext()).load(datas.get("img3")+"").into(exampleImage2);
                        if (!TextUtils.isEmpty(datas.get("img4")+"")){
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage3.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl3 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4")+"").into(exampleImage3);
                        }
                    }else {
                        if (!TextUtils.isEmpty(datas.get("img4")+"")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage2.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl2 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4")+"").into(exampleImage2);
                        }
                    }
                }
            }else {
                if (!TextUtils.isEmpty(datas.get("img2") + "")) {
                    imageLl.setVisibility(View.VISIBLE);
                    exampleImage1.setImageURI(Uri.parse(datas.get("img2")+""));
                    imageurl1 = datas.get("img2")+"";
//                    Glide.with(getApplicationContext()).load(datas.get("img2") + "").into(exampleImage1);
                    if (!TextUtils.isEmpty(datas.get("img3") + "")) {
                        imageLl.setVisibility(View.VISIBLE);
                        exampleImage2.setImageURI(Uri.parse(datas.get("img3")+""));
                        imageurl2 = datas.get("img3")+"";
//                        Glide.with(getApplicationContext()).load(datas.get("img3") + "").into(exampleImage2);
                        if (!TextUtils.isEmpty(datas.get("img4") + "")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage3.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl3 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4") + "").into(exampleImage3);
                        }
                    } else {
                        if (!TextUtils.isEmpty(datas.get("img4") + "")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage2.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl2 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4") + "").into(exampleImage2);
                        }
                    }
                } else {
                    if (!TextUtils.isEmpty(datas.get("img3") + "")) {
                        imageLl.setVisibility(View.VISIBLE);
                        exampleImage1.setImageURI(Uri.parse(datas.get("img3")+""));
                        imageurl1 = datas.get("img3")+"";
//                        Glide.with(getApplicationContext()).load(datas.get("img3") + "").into(exampleImage1);
                        if (!TextUtils.isEmpty(datas.get("img4") + "")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage2.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl2 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4") + "").into(exampleImage2);
                        }
                    } else {
                        if (!TextUtils.isEmpty(datas.get("img4") + "")) {
                            imageLl.setVisibility(View.VISIBLE);
                            exampleImage1.setImageURI(Uri.parse(datas.get("img4")+""));
                            imageurl1 = datas.get("img4")+"";
//                            Glide.with(getApplicationContext()).load(datas.get("img4") + "").into(exampleImage1);
                        }
                    }
                }
            }
        }

    }
    private PopupWindow mapPopWindow;
    private void showMapPop() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.map_choose_pop,null);

        TextView gaodeTv= (TextView) view.findViewById(R.id.choose_gaode_tv);
        TextView baiduTv= (TextView) view.findViewById(R.id.choose_baidu_tv);

        mapPopWindow = new PopupWindow(view);
        mapPopWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        mapPopWindow.setWidth(width/10*8);
        mapPopWindow.setHeight(height/6);
        mapPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
        mapPopWindow.setOutsideTouchable(true);
        mapPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(Order_details.this, 1f);
            }
        });

        gaodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AMapUtil.isInstallByRead(Order_details.this,"com.autonavi.minimap")){
                    AMapUtil.goToNaviActivity(Order_details.this,"共享老师","",lat,lng,"0","0");
                }else {
                    Toast.makeText(Order_details.this, "请先下载高德地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
        baiduTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AMapUtil.isInstallByRead(Order_details.this,"com.baidu.BaiduMap")){
                    AMapUtil.goToBaiduActivity(Order_details.this,lat,lng);
                }else {
                    Toast.makeText(Order_details.this, "请先下载百度地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.order_address_rl:
            if (status.equals("3")||status.equals("8")) {

            }else {
                showMapPop();
                mapPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);

            }
            break;
        case R.id.detail_chat:

            Intent intent11 = new Intent(this, ChatActivity.class);
            intent11.putExtra(JGApplication.CONV_TITLE, telphone);
            intent11.putExtra(JGApplication.TARGET_ID, telphone);
            intent11.putExtra(JGApplication.TARGET_APP_KEY, mMyInfo.getAppKey());
            startActivity(intent11);

            break;
        case R.id.name :
            //跳转老师详情
            startActivity(NewTeacherPersonActivity_.intent(this).extra("head_image",teacherImage)
                    .extra("user_id",teacherId).get());
//            Intent intentTeacher = new Intent(this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//            intentTeacher.putExtra("head_image",teacherImage);
//            intentTeacher.putExtra("user_id",teacherId);
//            startActivity(intentTeacher);
            finish();

            break;
        case R.id.order_detail_headimg:
            startActivity(NewTeacherPersonActivity_.intent(this).extra("head_image",teacherImage)
                    .extra("user_id",teacherId).get());
            //跳转老师详情
//            Intent intentTeacher2 = new Intent(this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//            intentTeacher2.putExtra("head_image",teacherImage);
//            intentTeacher2.putExtra("user_id",teacherId);
//            startActivity(intentTeacher2);
            finish();
            break;
        case R.id.dindanxiangxihuitui:
            Order_details.this.finish();
            break;
        case R.id.querenwanc:
            if (status.equals("1")) {
                //未完成
                Intent intent = new Intent(Order_details.this, Payment_Activty.class);
                intent.putExtra("id", order_id+"");
                intent.putExtra("fee", tolFee+"");
                intent.putExtra("duration", duration+"");
                intent.putExtra("telphone",telphone);
                startActivity(intent);
                Toast.makeText(Order_details.this, "进入支付环节", Toast.LENGTH_SHORT).show();
                finish();
            }
            if (status.equals("7")){
                //进行中
                new AlertDialog.Builder(Order_details.this).setTitle("学了么提示!").setMessage("确定完成交易?（一旦确认，钱将直接打入教师钱包）")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Order_init order_init = new Order();
                                String password = User_id.getPassword();
                                order_init.Update_Order(uid, order_id, 3, password, tolFee);
//                                Intent intent = new Intent(Order_details.this, Student_Activty.class);
                                Intent intent = new Intent(Order_details.this,Student_assessment.class);
                                intent.putExtra("oredr_id", order_id+"");
                                intent.putExtra("teacher_id",teacherId);
                                new Getdata().sendMessage(User_id.getNickName()+"已经确认授课完成了哦",telphone);
                                EventBus.getDefault().post(1,"changeStatus");
                                startActivity(intent);
                                Toast.makeText(Order_details.this, "赶快去评价这位老师吧", Toast.LENGTH_LONG).show();
                                finish();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
            if (status.equals("3")){
                //待评价
                Intent intent2=new Intent(Order_details.this,Student_assessment.class);
                intent2.putExtra("oredr_id", order_id+"");
                intent2.putExtra("teacher_id",teacherId);
                startActivity(intent2);
                finish();
            }
            if (status.equals("6")){
                Toast.makeText(Order_details.this, "有问题请联系客服", Toast.LENGTH_SHORT).show();
            }
            if (statuse.equals("2")){
                Toast.makeText(this, "请等待老师确认授课", Toast.LENGTH_SHORT).show();
            }
            break;
        case R.id.ordettuikuan:

            startActivity(RefoundActivity_.intent(this).extra("orderId",order_id).get());
            finish();
//          //订单退款
//            new AlertDialog.Builder(Order_details.this).setTitle("学了么提示!").setMessage("确定退款吗?")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Order_init order_init=new Order();
//                            String password=User_id.getPassword();
//                            order_init.Update_Order(uid,order_id,4,password,tolFee);
//                            new Getdata().sendMessage(User_id.getNickName()+"已提交退款申请",telphone);
//                            EventBus.getDefault().post(1,"changeStatus");
//                            Intent intent= new Intent(Order_details.this,MyOrderActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(Order_details.this,"已提交退款申请!~",Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            }).show();

            break;
    }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
