package com.deguan.xuelema.androidapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.deguan.xuelema.androidapp.viewimpl.CashView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.alipay.PayResult;
import modle.getdata.Getdata;
import modle.inter_test;
import modle.user_ziliao.User_id;
import view.fee.Feechongzhi_fagment;
import view.fee.Withdrawals_fagment;

/**
 * 钱包
 */

public class FeeqianbaoActivty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed ,CashView{
    private TextView chongzhi;
    private TextView xianjinjuan;
    private TextView tixian;
    private RelativeLayout qianbaohuitui;
    private TextView yuer;
    private TextView mingofee;
    private TextView textView4;
    private PopupWindow rechargePopwindow,cashPopwindow;
    private int flag = 1;
    private Getdata getdata;
    private int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        User_id.getInstance().addActivity(this);

        yuer= (TextView) findViewById(R.id.yuer);
        xianjinjuan= (TextView) findViewById(R.id.xianjinjuan);
        chongzhi= (TextView) findViewById(R.id.textView4);
        tixian= (TextView) findViewById(R.id.tixian);
        mingofee= (TextView) findViewById(R.id.mingofee);
        qianbaohuitui= (RelativeLayout) findViewById(R.id.qianbaohuitui);
        qianbaohuitui.bringToFront();
        yuer.setOnClickListener(this);
        qianbaohuitui.setOnClickListener(this);
        tixian.setOnClickListener(this);
        xianjinjuan.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        textView4= (TextView) findViewById(R.id.textView4);



        //获取用户余额
        uid = Integer.parseInt(User_id.getUid());
        getdata = new Getdata();
        getdata.getFee(uid,this);

        //获取现金卷
        getdata.getmianfofee(uid,this);
        textView4.setOnClickListener(this);


        initPopwindow();
        initCashPopwindow();
    }

    private int cashFlag = 1;
    private void initCashPopwindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.feelayout,null);
        TextView cashBack = (TextView) view.findViewById(R.id.cash_back);
        TextView cashSure = (TextView) view.findViewById(R.id.cash_sure);
        final EditText cashId = (EditText) view.findViewById(R.id.cash_id);
        final EditText cashFee = (EditText) view.findViewById(R.id.cash_fee);
        final EditText cashNmae = (EditText) view.findViewById(R.id.cash_name);
        final TextView cashType = (TextView) view.findViewById(R.id.cash_type);
        ImageView wechatRl = (ImageView) view.findViewById(R.id.cash_wechat);
        ImageView alpayRl = (ImageView) view.findViewById(R.id.cash_alpay);
        cashPopwindow = new PopupWindow(view);
        cashPopwindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        cashPopwindow.setWidth(width);
        cashPopwindow.setHeight(height/2);
        cashPopwindow.setOutsideTouchable(true);
        cashBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashPopwindow.dismiss();
            }
        });
        cashSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cashNmae.getText().toString())||!TextUtils.isEmpty(cashId.getText().toString())||!TextUtils.isEmpty(cashFee.getText().toString())) {
                    if (Float.parseFloat(cashFee.getText().toString())>myBalance){
                        Toast.makeText(FeeqianbaoActivty.this, "可提现金额不足", Toast.LENGTH_SHORT).show();
                    }else {
                        getdata.getCash(Integer.parseInt(User_id.getUid()), cashNmae.getText().toString(),
                                cashId.getText().toString(), cashFlag, Float.parseFloat(cashFee.getText().toString()), FeeqianbaoActivty.this);
                    }
                }else {
                    Toast.makeText(FeeqianbaoActivty.this, "请输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashType.setText("微信");
                cashFlag = 2;
            }
        });
        alpayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashType.setText("支付宝");
                cashFlag = 1;
            }
        });

    }

    private int rechargeFlag = 1;
    private void initPopwindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.feechongzhi,null);
        TextView rechargeBack = (TextView) view.findViewById(R.id.recharge_back);
        TextView rechargeSure = (TextView) view.findViewById(R.id.feequeding);
        final EditText rechargeFee = (EditText) view.findViewById(R.id.chongzhijine);
        final TextView rechargeType = (TextView) view.findViewById(R.id.feechongzhifee);
        ImageView wechatRl = (ImageView) view.findViewById(R.id.feeweixzhifu);
        ImageView alpayRl = (ImageView) view.findViewById(R.id.feezhifubao);
        rechargePopwindow = new PopupWindow(view);
        rechargePopwindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        rechargePopwindow.setWidth(width);
        rechargePopwindow.setHeight(height/2);
        rechargePopwindow.setOutsideTouchable(true);
        rechargeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(rechargeFee.getText().toString())){
                    Float money = Float.parseFloat(rechargeFee.getText().toString());
                    flag = 2;
                    if (rechargeFlag == 1){
                        getdata.getsizechongzhi(Integer.parseInt(User_id.getUid()),money,1,FeeqianbaoActivty.this);
                    }else {
                        Toast.makeText(FeeqianbaoActivty.this, "暫不可用", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FeeqianbaoActivty.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechargeType.setText("微信");
                rechargeFlag = 2;
            }
        });
        alpayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechargeType.setText("支付宝");
                rechargeFlag = 1;
            }
        });
        rechargeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechargePopwindow.dismiss();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView4:
                //充值
                rechargePopwindow.showAtLocation(textView4, Gravity.CENTER,0,0);
//                Feechongzhi_fagment dindan_fagment=new Feechongzhi_fagment();
//                FragmentManager fragmentManager=getFragmentManager();
//                FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
//                beginTransaction.add(R.id.feechongzhi,dindan_fagment);
//                beginTransaction.commit();
                break;
            case R.id.xianjinjuan:
                //跳转现金券
                Intent intent=new Intent(FeeqianbaoActivty.this,Cashvolume_Activty.class);
                startActivity(intent);

                break;
            case R.id.tixian:
                //提现
                cashPopwindow.showAtLocation(tixian,Gravity.CENTER,0,0);
//                Withdrawals_fagment withdrawalsFagment=new Withdrawals_fagment();
//                FragmentManager fr=getFragmentManager();
//                FragmentTransaction bt=fr.beginTransaction();
//                bt.add(R.id.feechongzhi,withdrawalsFagment);
//                bt.commit();
                break;
            case R.id.qianbaohuitui:
                //返回
                FeeqianbaoActivty.this.finish();
                break;
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult=new PayResult((Map<String,String>) msg.obj);


            String resultInfo=payResult.getResult();//同步返回结果
            String resultStatus=payResult.getResultStatus();//同步返回结果

            switch (resultStatus){
                case "9000":
                    Toast.makeText(FeeqianbaoActivty.this,"订单支付成功",Toast.LENGTH_SHORT).show();
                    break;
                case "8000":
                    Toast.makeText(FeeqianbaoActivty.this,"正在处理中，支付结果未知（有可能已经支付成功）!",Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(FeeqianbaoActivty.this,"订单支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(FeeqianbaoActivty.this,"重复请求",Toast.LENGTH_SHORT).show();
                    break;

                case "6001":
                    Toast.makeText(FeeqianbaoActivty.this,"取消支付",Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(FeeqianbaoActivty.this,"网络连接出错",Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(FeeqianbaoActivty.this,"支付结果未知（有可能已经支付成功）",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private final int SDK_PAY_FLAG = 1;
    private double myBalance =0 ;
    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (flag == 2){
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
            String info=(String) map.get("info");
            String ordert=info.substring(13);
            final String orderInfo = ordert;

            Log.e("aa",ordert);
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("aa","唤起支付宝");
                    PayTask alipay = new PayTask(FeeqianbaoActivty.this);
                    Map<String, String> result = alipay.payV2(orderInfo,true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;

                    //开启回调获取支付结果
                    mHandler.sendMessage(msg);

                }
            };

            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
            flag = 1;
        }else {
            if (map.get("fee") != null) {
                myBalance = (double) map.get("fee");
                yuer.setText("¥" + myBalance);
            }

            if (map.get("TotalFee") == null) {
                mingofee.setText("0");
            } else {
                String minfee = (String) map.get("TotalFee");
                mingofee.setText(minfee);
            }
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successCash(String msg) {
        Toast.makeText(this, "已提交提现申请", Toast.LENGTH_SHORT).show();
        cashPopwindow.dismiss();
        getdata.getFee(uid,this);
    }

    @Override
    public void failCash(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
