package com.deguan.xuelema.androidapp;



import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.alipay.PayResult;
import modle.getdata.Getdata;
import modle.getdata.PayUtil;
import modle.user_ziliao.User_id;

/**
 * 支付
 */

public class Payment_Activty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed, PayView {
    private Button querenzhifu;
    private TextView zhifufeeTv;
    private TextView ordetbianhao;
    private RelativeLayout querendindanfanhui;
    private RelativeLayout payWeixin,payAlipay,payQianbao;
    private TextView weixinTv,alipayTv,qianbaoTv;

    private int uid;
    private int order_id;
    private int order_fee;
    private double tolFee = 0;
    int durationa;
    //支付宝回调
    private final int SDK_PAY_FLAG = 1;
    private int flag = 3;
    public static String APP_ID = "wx3815ad6bb05c5aca";
    private int mianfee = 0;
    private EditText cashPayEdit;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult=new PayResult((Map<String,String>) msg.obj);

            String resultInfo=payResult.getResult();//同步返回结果
            String resultStatus=payResult.getResultStatus();//同步返回结果

            switch (resultStatus){
                case "9000":
                    Toast.makeText(Payment_Activty.this,"订单支付成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Payment_Activty.this,Payment_tureActivty.class));
                    Payment_Activty.this.finish();
                    break;
                case "8000":
                    Toast.makeText(Payment_Activty.this,"正在处理中，支付结果未知（有可能已经支付成功）!",Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(Payment_Activty.this,"订单支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(Payment_Activty.this,"重复请求",Toast.LENGTH_SHORT).show();
                    break;

                case "6001":
                    Toast.makeText(Payment_Activty.this,"取消支付",Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(Payment_Activty.this,"网络连接出错",Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(Payment_Activty.this,"支付结果未知（有可能已经支付成功）",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private Getdata getdata;
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.makeorder);
        iwxapi = WXAPIFactory.createWXAPI(this,APP_ID);
        iwxapi.registerApp(APP_ID);
        User_id.getInstance().addActivity(this);
        querenzhifu= (Button) findViewById(R.id.querenzhifu);
        zhifufeeTv= (TextView) findViewById(R.id.zhifufee);
        ordetbianhao= (TextView) findViewById(R.id.ordetbianhao);
        querendindanfanhui= (RelativeLayout) findViewById(R.id.querendindanfanhui);
        payWeixin = (RelativeLayout) findViewById(R.id.feeweixzhifurelayout);
        payAlipay = (RelativeLayout) findViewById(R.id.pay_alipay);
        payQianbao = (RelativeLayout) findViewById(R.id.qianbao);
        weixinTv = (TextView) findViewById(R.id.pay_weixin_tv);
        alipayTv = (TextView) findViewById(R.id.pay_alipay_tv);
        cashPayEdit = (EditText) findViewById(R.id.cash_pay_edit);
        qianbaoTv = (TextView) findViewById(R.id.pay_qianbao_tv);

        qianbaoTv.setTextColor(Color.parseColor("#e92c2c"));
        //获取支付信息
        //获取订单id
        String id=getIntent().getStringExtra("id");
        String duration=getIntent().getStringExtra("duration");
        //获取订单金额
        String fee=getIntent().getStringExtra("fee");
        uid=Integer.parseInt(User_id.getUid());
        order_id=Integer.parseInt(id);
        order_fee=Integer.parseInt(fee);
        durationa=Integer.parseInt(duration);
        ordetbianhao.setText(id);
        zhifufeeTv.setText(order_fee * durationa+"");
        getdata = new Getdata();

        payWeixin .setOnClickListener(this);
        payAlipay.setOnClickListener(this);
        payQianbao.setOnClickListener(this);
        querendindanfanhui.setOnClickListener(this);
        querenzhifu.setOnClickListener(this);
        getdata.getFee(uid,this);
        getdata.getmianfofee(uid,this);
        flag = 3;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feeweixzhifurelayout:
                flag = 1;
                weixinTv.setTextColor(Color.parseColor("#e92c2c"));
                alipayTv.setTextColor(Color.parseColor("#8a8686"));
                qianbaoTv.setTextColor(Color.parseColor("#8a8686"));

                break;
            case R.id.pay_alipay:
                flag = 2;
                weixinTv.setTextColor(Color.parseColor("#8a8686"));
                alipayTv.setTextColor(Color.parseColor("#e92c2c"));
                qianbaoTv.setTextColor(Color.parseColor("#8a8686"));

                break;
            case R.id.qianbao:
                flag = 3;
                weixinTv.setTextColor(Color.parseColor("#8a8686"));
                alipayTv.setTextColor(Color.parseColor("#8a8686"));
                qianbaoTv.setTextColor(Color.parseColor("#e92c2c"));

                break;
            case R.id.querenzhifu:

           /*     //更新订单状态
                Order_init order_init=new Order();
                String password=User_id.getPassword();
                order_init.Update_Order(uid,order_id,2,password,durationa*order_fee);
               Intent intent=new Intent(Payment_Activty.this,Payment_tureActivty.class);
                startActivity(intent);
                Toast.makeText(this,"支付成功",Toast.LENGTH_SHORT).show();*/
           int cashPayfee = 0;
                if (!TextUtils.isEmpty(cashPayEdit.getText())){
                    int cashfee = Integer.parseInt(cashPayEdit.getText().toString());
                    if ((mianfee - cashfee )>= cashfee){
                        cashPayfee = cashfee;
                        if (flag == 2) {
                            new PayUtil().getPayDetails(order_id, 0, cashPayfee, this);
//                    getdata.getzfbordet(order_id, 0, this);

                        } else if (flag == 1) {
                            new PayUtil().getPayDetails(order_id, 1, cashPayfee, this);
//                    getdata.getzfbordet(order_id,1,this);

//                    Toast.makeText(this, "暂不可用", Toast.LENGTH_SHORT).show();
                        } else {
                            if (durationa * order_fee <= tolFee) {
                                Order_init order_init = new Order();
                                String password = User_id.getPassword();
                                order_init.Update_Order(uid, order_id, 2, password, durationa * order_fee);
                                Intent intent = new Intent(Payment_Activty.this, Payment_tureActivty.class);
                                startActivity(intent);
                                Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else {
                        Toast.makeText(Payment_Activty.this, "可以现金券不足", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (flag == 2) {
                        new PayUtil().getPayDetails(order_id, 0, cashPayfee, this);
//                    getdata.getzfbordet(order_id, 0, this);

                    } else if (flag == 1) {
                        new PayUtil().getPayDetails(order_id, 1, cashPayfee, this);
//                    getdata.getzfbordet(order_id,1,this);

//                    Toast.makeText(this, "暂不可用", Toast.LENGTH_SHORT).show();
                    } else {
                        if (durationa * order_fee <= tolFee) {
                            Order_init order_init = new Order();
                            String password = User_id.getPassword();
                            order_init.Update_Order(uid, order_id, 2, password, durationa * order_fee);
                            Intent intent = new Intent(Payment_Activty.this, Payment_tureActivty.class);
                            startActivity(intent);
                            Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "余额不足", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
            case R.id.querendindanfanhui:
                finish();
                break;

        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
//        if (flag == 1){
//
////            PayReq payReq = new PayReq();
////            payReq.appId = APP_ID;
////            payReq.partnerId = map.get("partnerid").toString();
////            payReq.nonceStr = map.get("noncestr").toString();
////            payReq.packageValue = map.get("package").toString();
////            payReq.prepayId = map.get("prepayid").toString();
////            payReq.timeStamp = ""+map.get("timestamp").toString();
////            Log.d("aa","timestamp---------"+payReq.timeStamp);
////            payReq.sign = map.get("sign").toString();
////            Log.d("aa",payReq.appId+"----"+payReq.partnerId+"----"+payReq.nonceStr+"----"+payReq.packageValue+"----"+payReq.prepayId+"----"+payReq.sign+"----");
////            iwxapi.sendReq(payReq);
//        }else if (flag == 2) {
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
//            String info = (String) map.get("info");
//            String ordert = info.substring(13);
//            final String orderInfo = ordert;
//
//            Log.e("aa", ordert);
//            Runnable payRunnable = new Runnable() {
//                @Override
//                public void run() {
//                    Log.e("aa", "唤起支付宝");
//                    PayTask alipay = new PayTask(Payment_Activty.this);
//                    Map<String, String> result = alipay.payV2(orderInfo, true);
//                    Message msg = new Message();
//                    msg.what = SDK_PAY_FLAG;
//                    msg.obj = result;
//
//                    //开启回调获取支付结果
//                    mHandler.sendMessage(msg);
//
//                }
//            };
//
//            // 必须异步调用
//            Thread payThread = new Thread(payRunnable);
//            payThread.start();
//        }else {
//
//        }
        if (map.get("fee") != null) {
            tolFee = Double.parseDouble(map.get("fee")+"");
        }else {
            tolFee = 0.0;
        }
        if (map.get("TotalFee")==null){
//            mogint.setText("0");
            mianfee = 0;
        }else {
//            mogint.setText((String)map.get("TotalFee"));
            mianfee = (int) Float.parseFloat((String)map.get("TotalFee"));
        }
    }



    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successPay(Map<String, Object> map) {
        if (flag == 1){

            PayReq payReq = new PayReq();
            payReq.appId = APP_ID;
            payReq.partnerId = map.get("partnerid").toString();
            payReq.nonceStr = map.get("noncestr").toString();
            payReq.packageValue = map.get("package").toString();
            payReq.prepayId = map.get("prepayid").toString();
            payReq.timeStamp = map.get("timestamp").toString();
            Log.d("aa","timestamp---------"+payReq.timeStamp);
            payReq.sign = map.get("sign").toString();
            Log.d("aa",payReq.appId+"----"+payReq.partnerId+"----"+payReq.nonceStr+"----"+payReq.packageValue+"----"+payReq.prepayId+"----"+payReq.sign+"----");
            iwxapi.sendReq(payReq);

        }else if (flag == 2) {
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
            String info = (String) map.get("info");
            String ordert = info.substring(13);
            final String orderInfo = ordert;

            Log.e("aa", ordert);
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("aa", "唤起支付宝");
                    PayTask alipay = new PayTask(Payment_Activty.this);
                    Map<String, String> result = alipay.payV2(orderInfo, true);
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
        }
    }

    @Override
    public void failPay(String msg) {

    }
}
