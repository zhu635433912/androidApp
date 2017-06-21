package com.deguan.xuelema.androidapp;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.transition.Fade;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.zhy.autolayout.AutoLayoutActivity;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Gaode.Gaode_dinwei;
import modle.JieYse.Dtu_Modle.ProvinceAreaHelper;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 需求发布
 */

public class  Xuqiufabu extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed ,Gaodehuidiao_init {
    private TextView nan;
    private TextView nv;
    private TextView genderlimited;
    private TextView Subject;//科目
    private TextView grade;//年级
    private int zuigrade;//最终年级
    private int Gender = 66;//最终选择性别
    private TextView agelimitede;//年龄不限
    private TextView Twenty;
    private TextView Thirty;
    private TextView Forty;
    private int age = 66;//最终年龄
    private TextView Education;//学历不限
    private TextView doctor;//博士
    private TextView master;//硕士
    private TextView undergraduat;//本科
    private TextView specialty;//专科
    private int xueli = 66;//最终学历
    private TextView type;//类型
    private TextView Assistant;//助教
    private TextView Student;//学生
    private TextView Thirdparty;//第三方
    private String Servicetype = "";//最终服务类型
    private Button fabu;//发布
    private EditText xuqiuneirong;
    private RelativeLayout xuqiufabufanhui;
    private EditText userweizhi;
    private TextView kaishishijian;
    private int kcid = 206;
    private TextView jieshushijian;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    public AMapLocationClient mLocationClient = null;
    private String provinc;//省
    private String location;//市
    private String caty;//区
    private String qishi;
    private String jieshu;
    private int fuwfangshi = 66;
    private Map<String, Object> map;
    private List<Map<String, Object>> listmap;
    private String start;
    private String end;
    private int id;
    private OptionsPickerView optionsPickerView;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {

                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    userweizhi.setText(aMapLocation.getProvince().toString() + aMapLocation.getCity().toString()
                            + aMapLocation.getDistrict().toString() + aMapLocation.getStreet().toString() +
                            aMapLocation.getStreetNum().toString());

                    provinc = aMapLocation.getProvince().toString();
                    location = aMapLocation.getCity().toString();
                    caty = aMapLocation.getDistrict().toString();


                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xuqiufabu);
        //进入动画
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);

        User_id.getInstance().addActivity(this);
        nan = (TextView) findViewById(R.id.nan);
        nv = (TextView) findViewById(R.id.nv);
        xuqiuneirong = (EditText) findViewById(R.id.xuqiutext);
        genderlimited = (TextView) findViewById(R.id.xinbiebuxian);
        Subject = (TextView) findViewById(R.id.xuanzenianji);
        grade = (TextView) findViewById(R.id.xuanzekemu);
        agelimitede = (TextView) findViewById(R.id.agebux);
        Twenty = (TextView) findViewById(R.id.er_san);
        Thirty = (TextView) findViewById(R.id.san_si);
        Forty = (TextView) findViewById(R.id.sijia);
        Education = (TextView) findViewById(R.id.xuelibuxian);
        doctor = (TextView) findViewById(R.id.boshi);
        master = (TextView) findViewById(R.id.shuoshi);
        undergraduat = (TextView) findViewById(R.id.benke);
        specialty = (TextView) findViewById(R.id.zhuanke);
        type = (TextView) findViewById(R.id.fuwuleix);
        Assistant = (TextView) findViewById(R.id.shangmen);
        Student = (TextView) findViewById(R.id.xuessmen);
        Thirdparty = (TextView) findViewById(R.id.disanf);
        userweizhi = (EditText) findViewById(R.id.userweizhi);
        fabu = (Button) findViewById(R.id.fabu);
        xuqiufabufanhui = (RelativeLayout) findViewById(R.id.xuqiufabufanhui);
        kaishishijian = (TextView) findViewById(R.id.kaishishijian);
        jieshushijian = (TextView) findViewById(R.id.jieshushijian);
        xuqiufabufanhui.bringToFront();

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();


        userweizhi.setOnClickListener(this);
        kaishishijian.setOnClickListener(this);
        jieshushijian.setOnClickListener(this);
        xuqiufabufanhui.setOnClickListener(this);
        fabu.setOnClickListener(this);
        type.setOnClickListener(this);
        Assistant.setOnClickListener(this);
        Student.setOnClickListener(this);
        Thirdparty.setOnClickListener(this);
        Education.setOnClickListener(this);
        doctor.setOnClickListener(this);
        master.setOnClickListener(this);
        undergraduat.setOnClickListener(this);
        specialty.setOnClickListener(this);
        agelimitede.setOnClickListener(this);
        Twenty.setOnClickListener(this);
        Thirty.setOnClickListener(this);
        Forty.setOnClickListener(this);
        Subject.setOnClickListener(this);
        grade.setOnClickListener(this);
        nan.setOnClickListener(this);
        nv.setOnClickListener(this);
        genderlimited.setOnClickListener(this);
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.xuqiufabufanhui:
                Xuqiufabu.this.finish();
                break;
            case R.id.fabu:
                //发布
                if (User_id.getRole().equals("1")) {
                    if (xuqiuneirong.getText().toString() != null) {
                        if (Gender == 66 || age == 66 || xueli == 66 || Servicetype.equals("")) {
                            Toast.makeText(Xuqiufabu.this, "请选择性别年龄要求类型！", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Subject.getText().toString().equals("年级") || grade.getText().toString().equals("科目")) {
                                Toast.makeText(Xuqiufabu.this, "请选择年级和科目！", Toast.LENGTH_SHORT).show();
                            } else {
                                new AlertDialog.Builder(Xuqiufabu.this).setTitle("学了么提示!").setMessage("确定发布需求?")
                                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //得到long类型当前时间
                                                long l = System.currentTimeMillis();
                                                Date date = new Date(l);
                                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                                String sj = dateFormat.format(date);
                                                start = qishi;
                                                end = jieshu;
                                                String user_id = User_id.getUid();
                                                id = Integer.parseInt(user_id);
                                                Log.e("aa", "用户发布内容 id" + id + "内容" + xuqiuneirong.getText().toString() + "课时费" + "年级id" + zuigrade +
                                                        "科目id" + kcid + "性别要求" + Gender + "年龄" + age + "学历" + xueli + "地区" + provinc + location + caty + "服务方式+" + 1 + "时间段" + start + "结束时间段" + end);
                                                //发布需求
                                                Gaode_dinwei gaode_dinwei = new Gaode_dinwei(Xuqiufabu.this, Xuqiufabu.this);

                                                Toast.makeText(Xuqiufabu.this, "发布需求成功！", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Xuqiufabu.this, MainActivity.class);
                                                startActivity(intent);
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                            }
                        }
                    } else {
                        Toast.makeText(Xuqiufabu.this, "需求内容不能为空！", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Xuqiufabu.this, "老师请在我的页面教师管理发布课程！", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.xuanzenianji:
                //年级
                AlertDialog.Builder builder = new AlertDialog.Builder(Xuqiufabu.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("请选择一个年级");
                //    指定下拉列表的显示数据
                final String[] cities = {"一年级", "二年级", "三年级", "四年级", "五年级", "六年级", "初一", "初二", "初三", "高一", "高二", "高三"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Xuqiufabu.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        Subject.setText(cities[which]);
                        zuigrade = which;

                    }
                });
                builder.show();
                break;
            case R.id.xuanzekemu:
                //获取科目数据
                Getdata getdata = new Getdata();
                getdata.getGrade(this);
                break;

            case R.id.kaishishijian:
                //选择开始时间段
                TimePickerDialog timePickerDialog = new TimePickerDialog(Xuqiufabu.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute < 10){
                            kaishishijian.setText("起始时间:" + hourOfDay + ":0" + minute);
                            qishi = hourOfDay + ":0" + minute + ":00";
                        }else {
                            kaishishijian.setText("起始时间:" + hourOfDay + ":" + minute);
                            qishi = hourOfDay + ":" + minute + ":00";
                        }
                    }
                }, hourOfDay, minute, true);
                timePickerDialog.show();

                break;
            case R.id.jieshushijian:
                //选择结束时间段
                TimePickerDialog timePickerDialogz = new TimePickerDialog(Xuqiufabu.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (minute <= 10) {
                            jieshushijian.setText("结束时间:" + hourOfDay + ":0" + minute);
                            jieshu = hourOfDay + ":0" + minute + ":00";
                        }else {
                            jieshushijian.setText("结束时间:" + hourOfDay + ":" + minute);
                            jieshu = hourOfDay + ":" + minute + ":00";
                        }
                    }
                }, hourOfDay, minute, true);
                timePickerDialogz.show();

                break;
            case R.id.nan:
                //男
                nan.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                genderlimited.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Gender = 1;
                break;
            case R.id.nv:
                //女
                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nv.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                genderlimited.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Gender = 2;
                break;
            case R.id.xinbiebuxian:
                //性别不限
                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                genderlimited.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Gender = 0;
                break;
            case R.id.agebux:
                //年龄不限
                agelimitede.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Thirty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Twenty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Forty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                age = 0;
                break;
            case R.id.er_san:
                //20-30
                agelimitede.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Twenty.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Forty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                age = 25;
                break;
            case R.id.san_si:
                //30-40
                agelimitede.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirty.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Twenty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Forty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                age = 35;
                break;
            case R.id.sijia:
                //40+
                agelimitede.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Twenty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Forty.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                age = 40;
                break;
            case R.id.xuelibuxian:
                //学历不限
                Education.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                doctor.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                master.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                undergraduat.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                specialty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli = 0;
                break;
            case R.id.boshi:
                //博士
                Education.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                doctor.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                master.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                undergraduat.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                specialty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli = 4;
                break;
            case R.id.shuoshi:
                //硕士
                Education.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                doctor.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                master.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                undergraduat.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                specialty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli = 3;
                break;
            case R.id.benke:
                //本科
                Education.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                doctor.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                master.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                undergraduat.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                specialty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli = 2;
                break;
            case R.id.zhuanke:
                //专科
                Education.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                doctor.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                master.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                undergraduat.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                specialty.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                xueli = 1;
                break;
            case R.id.fuwuleix:
                //上门类型
                type.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Assistant.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Student.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirdparty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Servicetype = type.getText().toString();
                //------------------------>不限的类型0吗
                fuwfangshi = 1;
                break;
            case R.id.shangmen:
                //助教上门
                type.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Assistant.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Student.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirdparty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Servicetype = Assistant.getText().toString();
                fuwfangshi = 2;
                break;
            case R.id.xuessmen:
                //学生上门
                type.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Assistant.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Student.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Thirdparty.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Servicetype = Student.getText().toString();
                fuwfangshi = 1;
                break;
            case R.id.disanf:
                //第三方
                type.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Assistant.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Student.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                Thirdparty.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                Servicetype = Thirdparty.getText().toString();
                fuwfangshi = 3;
                break;
            case R.id.userweizhi:
                pvoptions();
                break;

        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        //课程种类
        AlertDialog.Builder kemu = new AlertDialog.Builder(Xuqiufabu.this);
        kemu.setIcon(R.drawable.add04);
        kemu.setTitle("请选择一个科目");
        //    指定下拉列表的显示数据
        final String[] cities = new String[79];
        for (int i = 0; i < map.size() + 1; i++) {
            int z = 206 + i;
            String a = (String) map.get(z + "");
            if (z == 285) {
                break;
            }
            cities[i] = a;
        }
        //    设置一个下拉的列表选择项
        kemu.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Xuqiufabu.this, "选择的科目为：" + cities[which], Toast.LENGTH_SHORT).show();
                grade.setText(cities[which]);
                kcid = kcid + which;
            }
        });
        kemu.show();
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    //定位发布成功
    @Override
    public void Updategaode(Map<String, Object> map) {
        double lat = (double) map.get("lat");
        double lng = (double) map.get("lng");
        Demand_init demand_init = new Demand();
        float i = 30;
        demand_init.ReleaseDemand(id, xuqiuneirong.getText().toString(), i, zuigrade + 1, kcid, Gender, age, xueli, provinc, location, caty, fuwfangshi - 1, start, end, lat, lng);


    }

    @Override
    public void Updatecuowu(Map<String, Object> map) {
    }
    public void pvoptions(){
        optionsPickerView= new  OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3 ,View v) {
                //返回的分别是三个级别的选中位置

            }
        })
                .setSubmitText("确定")//确定按钮文字
                .setCancelText("取消")//取消按钮文字
                .setTitleText("城市选择")//标题
                .setSubCalSize(18)//确定和取消文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLUE)//确定按钮文字颜色
                .setCancelColor(Color.BLUE)//取消按钮文字颜色
                .setTitleBgColor(0xFF333333)//标题背景颜色 Night mode
                .setBgColor(0xFF000000)//滚轮背景颜色 Night mode
                .setContentTextSize(18)//滚轮文字大小
                .setLinkage(true)//设置是否联动，默认true
                .setLabels("省", "市", "区")//设置选择的三级单位
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setCyclic(false, false, false)//循环与否
                .setSelectOptions(1, 1, 1)  //设置默认选中项
                .setOutSideCancelable(false)//点击外部dismiss default true
                .isDialog(true)//是否显示为对话框样式
                .build();
        //获取数据
        Map<String, String[]> map=new HashMap<String,String[]>();
        Map<String,String[]> shimap=new HashMap<>();
        ArrayList<String> listmap=new ArrayList<>();
        ProvinceAreaHelper provinceAreaHelper=new ProvinceAreaHelper(this);
        provinceAreaHelper.initProvinceData();
        shimap=provinceAreaHelper.getshi();
        String[] a=shimap.get("台州市");
        for (int i=0;i<a.length;i++){
            listmap.add(a[i]);
        }
        optionsPickerView.setPicker(listmap);
        optionsPickerView.show();
    }

    //收缩软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
