package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
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
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutActivity;

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

public class OrderTeacherActivity extends MyBaseActivity implements Ordercontent_init,View.OnClickListener, ChangeOrderView {
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
    private double fee;
    private TextView keshishufee;
    private TextView kechengjieshu;
    private TextView ordettuikuan;
    private TextView zongjijine;
    private TextView xuqiuneiro;
    private TextView dindan_id;
//    private TextView gender;
    private TextView fuwufan;
    private RelativeLayout ycang;
    private String status;
    private SimpleDraweeView headImage;
    private TextView statuse,telTv,orderDesc;
    private String teacherImage;
    private PopupWindow changePopWindow;
    private Order_init order_init;
    private String telphone;
    private double changeFee ;
    private ImageView teacher_detail_tel,detail_chat;
    private String is_complete;
    private TextView payTv;
    private UserInfo mMyInfo;
    private String lat,lng;
    private LinearLayout addressRl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_teacher);
        User_id.getInstance().addActivity(this);
        mMyInfo = JMessageClient.getMyInfo();

        orderDesc = (TextView) findViewById(R.id.order_pay_desc);
        addressRl = (LinearLayout) findViewById(R.id.order_address_rl);
        payTv = (TextView) findViewById(R.id.textView6);
        teacher_detail_tel = (ImageView) findViewById(R.id.teacher_detail_tel);
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
//        ordettuikuan= (TextView) findViewById(R.id.ordettuikuan);
        dindanxiangxihuitui= (RelativeLayout) findViewById(R.id.dindanxiangxihuitui);
        xuqiufenggexian7= (RelativeLayout) findViewById(R.id.querenwanc);
        zongjijine= (TextView) findViewById(R.id.zongjijine);
        xuqiuneiro= (TextView) findViewById(R.id.xuqiuneiro);
        dindan_id= (TextView) findViewById(R.id.dindan_id);
//        gender= (TextView) findViewById(R.id.gender);
        fuwufan= (TextView) findViewById(R.id.fuwufan);
        ycang= (RelativeLayout) findViewById(R.id.ycang);
        dindanxiangxihuitui.bringToFront();
//        ordettuikuan.setOnClickListener(this);
        xuqiufenggexian7.setOnClickListener(this);
        dindanxiangxihuitui.setOnClickListener(this);
        headImage.setOnClickListener(this);
        name.setOnClickListener(this);
        addressRl.setOnClickListener(this);

//        if(User_id.getRole().equals("2")){
//            ycang.setVisibility(View.GONE);
//            xuqiufenggexian7.setVisibility(View.GONE);
//        }
        //获取订单id与用户id
        String order_ida = getIntent().getStringExtra("oredr_id");
        String durationa=getIntent().getStringExtra("duration");
        teacherImage = getIntent().getStringExtra("teacher_headimg");



        status=getIntent().getStringExtra("status");
        String uida = User_id.getUid();
        duration=Integer.parseInt(durationa);
        order_id = Integer.parseInt(order_ida);
        uid = Integer.parseInt(uida);
        switch (status){
            case "1":
                statuse.setText("修改订单价格");
                orderDesc.setText("您的课程已被接取，请耐心等待对方支付。您也可以与对方联系，建立一个良好的开始。");
                break;
            case "2":
                statuse.setText("结束授课？");
                orderDesc.setText("学生已经支付，请与学生联系，约定授课时间地点。授课结束后记得给予学生建议帮助其成长哦");
                break;
            case "3":
                statuse.setText("待评价");
                orderDesc.setText("学生已经确认支付，感谢您使用学习吧平台，祝您生活愉快");
                break;
            case "4":
//                if(!User_id.getRole().equals("2")) {
//                    ycang.setVisibility(View.GONE);
//                    xuqiufenggexian7.setVisibility(View.GONE);
//                }
//                break;
                statuse.setText("确认or拒绝");
                break;
            case "5":
                statuse.setText("已同意退款");
                break;
            case "6":
                statuse.setText("已拒绝退款");
                break;
            case "8":
                statuse.setText("已完成");
                orderDesc.setText("学生已经确认支付，感谢您使用学习吧平台，祝您生活愉快");
                break;
            case "7":
                statuse.setText("待收款");
                orderDesc.setText("您已完成授课，请等待学生确认支付。若7天内学生未确认，将自动确认并默认五星好评！");
                break;
        }

//        Log.e("aa", "订单详细收到的订单id为" + order_id + "与用户id为" + uida);

        //根据订单号用户id去后台获取订单详细信息
        order_init = new Order();
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
        initPopwindow();
        teacher_detail_tel.setOnClickListener(new View.OnClickListener() {
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
        detail_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent11 = new Intent(OrderTeacherActivity.this, ChatActivity.class);
                intent11.putExtra(JGApplication.CONV_TITLE, telphone);
                intent11.putExtra(JGApplication.TARGET_ID, telphone);
                intent11.putExtra(JGApplication.TARGET_APP_KEY, mMyInfo.getAppKey());
                startActivity(intent11);
            }
        });
    }

    private void initPopwindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.change_money_pop,null);
        TextView changeBack = (TextView) view.findViewById(R.id.change_back);
        TextView changeSure = (TextView) view.findViewById(R.id.change_sure);
        final EditText changeMoney = (EditText) view.findViewById(R.id.change_edittv);
        changePopWindow = new PopupWindow(view);
        changePopWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        changePopWindow.setWidth(width);
        changePopWindow.setHeight(height/3);
        changePopWindow.setOutsideTouchable(true);
        changeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePopWindow.dismiss();
            }
        });
        changeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(changeMoney.getText())){

                    changeFee = (Double.parseDouble(changeMoney.getText().toString()));
                    if (changeFee > 0) {
                        order_init.UpdateOrder_Amount(Integer.parseInt(User_id.getUid()), order_id, changeFee, OrderTeacherActivity.this);
                    }else {
                        Toast.makeText(OrderTeacherActivity.this, "订单价格不能小于0", Toast.LENGTH_SHORT).show();
                    }
//                    order_init.getOrder_danyilist(uid, order_id, OrderTeacherActivity.this);

                }else {
                    Toast.makeText(OrderTeacherActivity.this, "请输入修改价格", Toast.LENGTH_SHORT).show();
                }
                changePopWindow.dismiss();
            }
        });




    }

    private String teacherId;
    private double tolFee;
    @Override
    public void Updatecontent(Map<String, Object> map) {
        String status = (String) map.get("status");
        String requirement_address = (String) map.get("address");
        Date d = new Date(Long.parseLong(map.get("created")+"")*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String created = sdf.format(d);
//        String created = (String) map.get("created");
        String requirement_grade = (String) map.get("grade_name");
        String requirement_course = (String) map.get("course_name");
        String feae= (String) map.get("fee");
        teacherImage = (String) map.get("placer_headimg");
//        Glide.with(getApplicationContext()).load(teacherImage).transform
//                (new GlideCircleTransform(this)).into(headImage);
        headImage.setImageURI(Uri.parse(teacherImage));
        teacherId = (String) map.get("teacher_id");
        telphone = map.get("placer_mobile")+"";
        telTv.setText(Html.fromHtml("<u>"+(String)map.get("placer_mobile")+""+"</u>"));
        fee=Double.parseDouble(feae);
        int duration=Integer.parseInt(map.get("duration").toString());
        kechengjieshu.setText("x"+duration+"节");
        keshishufee.setText("¥"+fee+"/节");
        tolFee = Double.parseDouble(map.get("order_fee")+"");
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
        is_complete = map.get("is_complete")+"";
//        payTv.setText(map.get("pay_desc")+"");



        switch (status){
            case "1":
                statuse.setText("修改订单价格");
                order_status.setText("未付款");
                break;
            case "2":
//                if (is_complete.equals("1")){
//                    statuse.setText("已结束授课");
//                }else {
                    statuse.setText("结束授课？");
//                }
                order_status.setText("进行中");
                break;
            case "3":
//                statuse.setText("待评价");
//                if (is_complete.equals("1")){
                    statuse.setText("已结束授课");
//                }else {
//                    statuse.setText("结束授课？");
//                }
                order_status.setText("交易完成");
                break;
            case "4":
//                if(!User_id.getRole().equals("2")) {
//                    ycang.setVisibility(View.GONE);
//                    xuqiufenggexian7.setVisibility(View.GONE);
//                }
//                break;
                order_status.setText("待确认");
                statuse.setText("确认or拒绝");
                break;
            case "5":
                order_status.setText("已同意退款");
                statuse.setText("已同意退款");
                break;
            case "6":
                order_status.setText("已拒绝退款");
                statuse.setText("已拒绝退款");
                break;
            case "8":
                order_status.setText("交易完成");
                break;
            case "7":
                order_status.setText("待收款");
                break;
        }

        if (User_id.getRole().equals("1")) {
            name.setText(""+map.get("teacher_name"));
        }else {
            name.setText(""+map.get("placer_name"));
        }
//        dizhi.setText(""+map.get("requirement_address"));
        xuqiuneiro.setText(""+map.get("desc"));
        dindan_id.setText(""+map.get("id"));

        String ger=map.get("teacher_gender")+"";
//        if (ger.equals("1")){
//            gender.setText("男");
//        }else {
//            gender.setText("女");
//        }
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
                backgroundAlpha(OrderTeacherActivity.this, 1f);
            }
        });

        gaodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AMapUtil.isInstallByRead(OrderTeacherActivity.this,"com.autonavi.minimap")){
                    AMapUtil.goToNaviActivity(OrderTeacherActivity.this,"共享老师","",lat,lng,"0","0");
                }else {
                    Toast.makeText(OrderTeacherActivity.this, "请先下载高德地图", Toast.LENGTH_SHORT).show();
                }
            }
        });
        baiduTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AMapUtil.isInstallByRead(OrderTeacherActivity.this,"com.baidu.BaiduMap")){
                    AMapUtil.goToBaiduActivity(OrderTeacherActivity.this,lat,lng);
                }else {
                    Toast.makeText(OrderTeacherActivity.this, "请先下载百度地图", Toast.LENGTH_SHORT).show();
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
            case R.id.name :
                //跳转老师详情
//                Intent intentTeacher = new Intent(this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                intentTeacher.putExtra("head_image",teacherImage);
//                intentTeacher.putExtra("user_id",teacherId);
//                startActivity(intentTeacher);


                break;
            case R.id.order_detail_headimg:
                //跳转老师详情
//                Intent intentTeacher2 = new Intent(this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                intentTeacher2.putExtra("head_image",teacherImage);
//                intentTeacher2.putExtra("user_id",teacherId);
//                startActivity(intentTeacher2);
                break;
            case R.id.dindanxiangxihuitui:
                this.finish();
                break;
            case R.id.querenwanc:
                if (status.equals("1")) {
                    changePopWindow.showAtLocation(xuqiufenggexian7, Gravity.CENTER,0,0);
                    //未完成
//                    Intent intent = new Intent(OrderTeacherActivity.this, Payment_Activty.class);
//                    intent.putExtra("id", order_id+"");
//                    intent.putExtra("fee", fee+"");
//                    intent.putExtra("duration", duration+"");
//                    startActivity(intent);
//                    Toast.makeText(OrderTeacherActivity.this, "进入支付环节", Toast.LENGTH_SHORT).show();
                }else if (status.equals("4")){
                    new AlertDialog.Builder(OrderTeacherActivity.this).setTitle("学了么提示!").setMessage("确认退款吗")
                            .setPositiveButton("确认退款", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //创建订单
                                    order_init.Order_refund(uid,order_id,5,tolFee);
                                    new Getdata().sendMessage(User_id.getNickName()+"已同意退款",telphone);
                                    finish();
                                    Toast.makeText(OrderTeacherActivity.this,"已确认退款",Toast.LENGTH_SHORT).show();
                                }
                            }).setNegativeButton("拒绝退款", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            order_init.Order_refund(uid,order_id,6,tolFee);
                            new Getdata().sendMessage(User_id.getNickName()+"拒绝退款",telphone);
                            Toast.makeText(OrderTeacherActivity.this,"已拒绝退款~",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).show();


                }
                if (status.equals("2")){
//                    if (is_complete.equals("1")){
//                        Toast.makeText(this, "已确认授课", Toast.LENGTH_SHORT).show();
//                    }else {
                        startActivity(CompleteOrderActivity_.intent(OrderTeacherActivity.this).extra("orderId",order_id).extra("telPhone",telphone).get());
                        finish();
//                    }
                }
                if (status.equals("3")){
//                    if (is_complete.equals("1")){
                        Toast.makeText(this, "已完成授课", Toast.LENGTH_SHORT).show();
//                    }else {
//                        startActivity(CompleteOrderActivity_.intent(OrderTeacherActivity.this).extra("orderId",order_id).extra("telPhone",telphone).get());
//                        finish();
//                    }
                }
                break;
            case R.id.ordettuikuan:
                //订单退款
//                new AlertDialog.Builder(OrderTeacherActivity.this).setTitle("学了么提示!").setMessage("确定退款吗?")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Order_init order_init=new Order();
//                                String password=User_id.getPassword();
//                                order_init.Update_Order(uid,order_id,4,password,duration*fee);
//                                Intent intent=new Intent(OrderTeacherActivity.this,Student_Activty.class);
//                                startActivity(intent);
//                                Toast.makeText(OrderTeacherActivity.this,"退款成功!~",Toast.LENGTH_LONG).show();
//
//                            }
//                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();
                break;
        }
    }

    @Override
    public void successOrder(String msg) {
        new Getdata().sendMessage(User_id.getNickName()+"已修改订单价格为"+changeFee,telphone);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        order_init.getOrder_danyilist(uid, order_id, this);
        finish();
    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
