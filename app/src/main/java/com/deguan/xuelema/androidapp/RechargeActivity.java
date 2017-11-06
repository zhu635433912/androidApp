package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import modle.alipay.PayResult;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_recharge)
public class RechargeActivity extends MyBaseActivity implements View.OnClickListener, Requirdetailed {
    @ViewById(R.id.recharge_back)
    RelativeLayout backRl;
    @ViewById(R.id.recharge_sure)
    CardView sureBtn;
    @ViewById(R.id.recharge_money)
    EditText moneyEdit;
    @ViewById(R.id.feeweixzhifu)
    ImageView weixinImage;
    @ViewById(R.id.feezhifubao)
    ImageView alpayImage;
    @ViewById(R.id.chongzhi_weixin_tv)
    TextView weixinTv;
    @ViewById(R.id.chongzhi_alipay_tv)
    TextView alipayTv;

    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        weixinImage.setOnClickListener(this);
        alpayImage.setOnClickListener(this);
        backRl.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
        moneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    sureBtn.setCardBackgroundColor(Color.parseColor("#e7d47f"));
                    sureBtn.setClickable(true);
                }else {
                    sureBtn.setCardBackgroundColor(Color.parseColor("#d9d9d9"));
                    sureBtn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }
        });
    }

    private int rechargeFlag = 1;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feeweixzhifu:
                weixinTv.setTextColor(Color.parseColor("#e92c2c"));
                alipayTv.setTextColor(Color.parseColor("#8a8686"));
                rechargeFlag = 2;
                break;
            case R.id.feezhifubao:
                weixinTv.setTextColor(Color.parseColor("#8a8686"));
                alipayTv.setTextColor(Color.parseColor("#e92c2c"));
                rechargeFlag = 1;
                break;
            case R.id.recharge_back:
                finish();
                break;
            case R.id.recharge_sure:
                sureBtn.setCardBackgroundColor(Color.parseColor("#d9d9d9"));
                sureBtn.setClickable(false);

                if (!TextUtils.isEmpty(moneyEdit.getText().toString())){
                    Float money = Float.parseFloat(moneyEdit.getText().toString());
                    if (rechargeFlag == 1){
                        new Getdata().getsizechongzhi(Integer.parseInt(User_id.getUid()),money,1,RechargeActivity.this);
                    }else {
                        new Getdata().getsizechongzhi(Integer.parseInt(User_id.getUid()),money,2,RechargeActivity.this);
                    }
                }else {
                    Toast.makeText(RechargeActivity.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                }
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
                    Toast.makeText(RechargeActivity.this,"订单支付成功",Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case "8000":
                    Toast.makeText(RechargeActivity.this,"正在处理中，支付结果未知（有可能已经支付成功）!",Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(RechargeActivity.this,"订单支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(RechargeActivity.this,"重复请求",Toast.LENGTH_SHORT).show();
                    break;
                case "6001":
                    Toast.makeText(RechargeActivity.this,"取消支付",Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(RechargeActivity.this,"网络连接出错",Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(RechargeActivity.this,"支付结果未知（有可能已经支付成功）",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private final int SDK_PAY_FLAG = 1;
    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (rechargeFlag == 2){
            IWXAPI iwxapi = WXAPIFactory.createWXAPI(this,Payment_Activty.APP_ID);
            iwxapi.registerApp(Payment_Activty.APP_ID);
            PayReq payReq = new PayReq();
            payReq.appId = Payment_Activty.APP_ID;
            payReq.partnerId = map.get("partnerid").toString();
            payReq.nonceStr = map.get("noncestr").toString();
            payReq.packageValue = map.get("package").toString();
            payReq.prepayId = map.get("prepayid").toString();
            payReq.timeStamp = ""+map.get("timestamp").toString();
//            Log.d("aa","timestamp---------"+payReq.timeStamp);
            payReq.sign = map.get("sign").toString();
//            Log.d("aa",payReq.appId+"----"+payReq.partnerId+"----"+payReq.nonceStr+"----"+payReq.packageValue+"----"+payReq.prepayId+"----"+payReq.sign+"----");
            iwxapi.sendReq(payReq);
            sureBtn.setCardBackgroundColor(Color.parseColor("#e7d47f"));
            sureBtn.setClickable(true);
            EventBus.getDefault().post(1,"recharge");
        }else if (rechargeFlag == 1){
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
            String info=(String) map.get("info");
            String ordert=info.substring(13);
            final String orderInfo = ordert;

//            Log.e("aa",ordert);
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    Log.e("aa","唤起支付宝");
                    PayTask alipay = new PayTask(RechargeActivity.this);
                    Map<String, String> result = alipay.payV2(orderInfo,true);
                    Message msg = new Message();
                    msg.what = SDK_PAY_FLAG;
                    msg.obj = result;

                    //开启回调获取支付结果
                    mHandler.sendMessage(msg);

                }
            };
            sureBtn.setCardBackgroundColor(Color.parseColor("#e7d47f"));
            sureBtn.setClickable(true);
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
    //处理事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //处理按下事件
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
