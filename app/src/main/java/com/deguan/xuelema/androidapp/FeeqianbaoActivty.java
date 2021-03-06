package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.CashView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import modle.alipay.PayResult;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 钱包
 */

public class FeeqianbaoActivty extends MyBaseActivity implements View.OnClickListener,Requirdetailed ,CashView{
    private TextView chongzhi;
    private TextView xianjinjuan;
    private TextView tixian;
    private RelativeLayout qianbaohuitui;
    private TextView yuer;
    private TextView mingofee;
    private TextView textView4,myBillTv,myPushTv;
    private PopupWindow rechargePopwindow,cashPopwindow;
    private int flag = 0;
    private Getdata getdata;
    private int uid;
    private TextView redTv,dongjieTv,text5,xianjinred,vipTv;
    private ImageView helpImage,rechargeImage,rechargeRight;
    private String  isvip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        EventBus.getDefault().register(this);
        User_id.getInstance().addActivity(this);

        vipTv = (TextView) findViewById(R.id.wode_vip_tv);
        rechargeImage = (ImageView) findViewById(R.id.recharge_image);
        rechargeRight = (ImageView) findViewById(R.id.recharge_right);
        text5 = (TextView) findViewById(R.id.textView5);
        helpImage = (ImageView) findViewById(R.id.red_help_image);
        dongjieTv = (TextView) findViewById(R.id.dongjie_money_tv);
        redTv = (TextView) findViewById(R.id.xianjinred_tv);
        myPushTv = (TextView) findViewById(R.id.wodetuiguang);
        myBillTv = (TextView) findViewById(R.id.my_bill);
        yuer= (TextView) findViewById(R.id.yuer);
        xianjinjuan= (TextView) findViewById(R.id.xianjinjuan);
        chongzhi= (TextView) findViewById(R.id.textView4);
        tixian= (TextView) findViewById(R.id.tixian);
        mingofee= (TextView) findViewById(R.id.mingofee);
        xianjinred = (TextView) findViewById(R.id.xianjinred);
        qianbaohuitui= (RelativeLayout) findViewById(R.id.qianbaohuitui);
        qianbaohuitui.bringToFront();
        yuer.setOnClickListener(this);
        qianbaohuitui.setOnClickListener(this);
        tixian.setOnClickListener(this);
//        xianjinjuan.setOnClickListener(this);
        chongzhi.setOnClickListener(this);
        myPushTv.setOnClickListener(this);
        textView4= (TextView) findViewById(R.id.textView4);
        mingofee.setOnClickListener(this);
        yuer.setOnClickListener(this);
        vipTv.setOnClickListener(this);

        //获取用户余额
        uid = Integer.parseInt(User_id.getUid());
        if (User_id.getRole().equals("1")){
            xianjinjuan.setVisibility(View.VISIBLE);
            mingofee.setVisibility(View.VISIBLE);
            redTv.setVisibility(View.VISIBLE);
            xianjinred.setVisibility(View.VISIBLE);
            rechargeImage.setVisibility(View.VISIBLE);
            rechargeRight.setVisibility(View.VISIBLE);
            chongzhi.setVisibility(View.VISIBLE);
        }
        getdata = new Getdata();
        getdata.getFee(uid,this);

        //获取现金卷
        getdata.getmianfofee(uid,this);
        textView4.setOnClickListener(this);
        myBillTv.setOnClickListener(this);

        initPopwindow();
        initCashPopwindow();
        helpImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FeeqianbaoActivty.this, "成为会员即可使用", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Getdata().getFee(Integer.parseInt(User_id.getUid()),this);
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
        final TextView weixinTv = (TextView) view.findViewById(R.id.tixian_weixin);
        final TextView alipayTv = (TextView) view.findViewById(R.id.tixian_alipay);
        cashPopwindow = new PopupWindow(view);
        cashPopwindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        cashPopwindow.setWidth(width);
        cashPopwindow.setHeight(height/2);
        cashPopwindow.setOutsideTouchable(true);
        cashFee.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
        cashBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashPopwindow.dismiss();
            }
        });
        cashSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(cashNmae.getText().toString())&&!TextUtils.isEmpty(cashId.getText().toString())&&!TextUtils.isEmpty(cashFee.getText().toString())) {
                    if (Double.parseDouble(cashFee.getText().toString())>myBalance){
                        Toast.makeText(FeeqianbaoActivty.this, "可提现金额不足", Toast.LENGTH_SHORT).show();
                    }else {
                        if (Double.parseDouble(cashFee.getText().toString()) %100 == 0) {
                            getdata.getCash(Integer.parseInt(User_id.getUid()), cashNmae.getText().toString(),
                                    cashId.getText().toString(), cashFlag, Float.parseFloat(cashFee.getText().toString()),"", FeeqianbaoActivty.this);
                            cashFee.setText("");
                            Toast.makeText(FeeqianbaoActivty.this, "已提交提现申请", Toast.LENGTH_SHORT).show();
                            cashPopwindow.dismiss();
                        }else {
                            Toast.makeText(FeeqianbaoActivty.this, "请整百提现", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(FeeqianbaoActivty.this, "请输入完整", Toast.LENGTH_SHORT).show();
                }
            }
        });
        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinTv.setTextColor(Color.parseColor("#e92c2c"));
                alipayTv.setTextColor(Color.parseColor("#8a8686"));
                cashType.setText("微信");
                cashFlag = 2;
            }
        });
        alpayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinTv.setTextColor(Color.parseColor("#8a8686"));
                alipayTv.setTextColor(Color.parseColor("#e92c2c"));
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
        final TextView weixinTv = (TextView) view.findViewById(R.id.chongzhi_weixin_tv);
        final TextView alipayTv = (TextView) view.findViewById(R.id.chongzhi_alipay_tv);
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
                    if (rechargeFlag == 1){
                        flag = 2;
                        getdata.getsizechongzhi(Integer.parseInt(User_id.getUid()),money,1,FeeqianbaoActivty.this);
                    }else {
                        flag = 1;
                        getdata.getsizechongzhi(Integer.parseInt(User_id.getUid()),money,2,FeeqianbaoActivty.this);
//                        Toast.makeText(FeeqianbaoActivty.this, "暫不可用", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(FeeqianbaoActivty.this, "请输入充值金额", Toast.LENGTH_SHORT).show();
                }
            }
        });
        rechargeFee.addTextChangedListener(new TextWatcher()
        {
            public void afterTextChanged(Editable edt)
            {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
        });
        wechatRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinTv.setTextColor(Color.parseColor("#e92c2c"));
                alipayTv.setTextColor(Color.parseColor("#8a8686"));
                rechargeType.setText("微信");
                rechargeFlag = 2;
            }
        });
        alpayRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weixinTv.setTextColor(Color.parseColor("#8a8686"));
                alipayTv.setTextColor(Color.parseColor("#e92c2c"));
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

    private String userHeadUrl;
    private String nickname;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.wode_vip_tv:
                if (User_id.getRole().equals("1")) {
                    startActivity(StudentVipActivity_.intent(this).extra("headurl", userHeadUrl).extra("nickname", nickname).get());
                }else {
                    startActivity(TeacherVipActivity_.intent(this).extra("headurl", userHeadUrl).extra("nickname", nickname).get());
                }

                break;
            case R.id.my_bill:
                if (User_id.getRole().equals("1")) {
                    startActivity(VipDescActivity_.intent(this).extra("type",1).get());
                }else {
                    startActivity(VipDescActivity_.intent(this).extra("type",2).get());
                }
                break;
            case R.id.wodetuiguang:
                //我的推广
//                Intent intentb=new Intent(FeeqianbaoActivty.this,Distribution_Activty.class);
//                startActivity(intentb);
//                startActivity(StudentVipActivity_.intent(this).extra("headurl",userHeadUrl).extra("nickname",nickname).get());
                startActivity(MyExtentActivity_.intent(this).get());
                break;
            case R.id.yuer:
                startActivity(BillActivity_.intent(this).get());
                break;
            case R.id.textView4:
                //充值
//                rechargePopwindow.showAtLocation(textView4, Gravity.CENTER,0,0);
                startActivity(RechargeActivity_.intent(FeeqianbaoActivty.this).get());
                break;
            case R.id.mingofee:
                //跳转现金券
//                Intent intent33=new Intent(FeeqianbaoActivty.this,Cashvolume_Activty.class);
//                startActivity(intent33);
                break;

            case R.id.xianjinjuan:
                //跳转现金券
//                Intent intent=new Intent(FeeqianbaoActivty.this,Cashvolume_Activty.class);
//                startActivity(intent);

                break;
            case R.id.tixian:
                //提现
                startActivity(WithDrawActivity_.intent(FeeqianbaoActivty.this).get());
//                cashPopwindow.showAtLocation(tixian,Gravity.CENTER,0,0);
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
                    getdata.getFee(uid,FeeqianbaoActivty.this);
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
        if (flag == 1){
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
            EventBus.getDefault().post(1,"recharge");
            rechargePopwindow.dismiss();
            flag = 0;
        }else if (flag == 2){
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
            String info=(String) map.get("info");
            String ordert=info.substring(13);
            final String orderInfo = ordert;

//            Log.e("aa",ordert);
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
            flag = 0;
            rechargePopwindow.dismiss();
        }else {
            if (map.get("fee") != null) {
                myBalance = (double) map.get("fee");
                yuer.setText("¥" + myBalance);
            }

            if (map.get("reward") != null) {
                String minfee = (String) map.get("reward");
                redTv.setText(minfee+"");
            }
            if (map.get("credit") != null) {
                String credit = (String) map.get("credit") ;
                mingofee.setText(credit+"");
            }
            if (map.get("brozen_fee") != null) {
                double brozen_fee =  (double)map.get("brozen_fee") ;
                dongjieTv.setText(brozen_fee+"");
            }
            if (map.get("headimg") != null){
                userHeadUrl = map.get("headimg")+"";
            }
            if (map.get("nickname") != null){
                nickname = map.get("nickname")+"";
            }
            if (map.get("level") != null){
                isvip = map.get("level")+"";
                if (isvip.equals("1")){
                    redTv.setVisibility(View.GONE);
                    text5.setVisibility(View.GONE);
                    helpImage.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successCash(String msg) {
        if (cashFlag == 2) {
            Toast.makeText(this, "已提交提现申请,我方将会申请加微信好友,请通过", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "提现将在3个工作日之内到账,请注意查收", Toast.LENGTH_SHORT).show();
        }
        cashPopwindow.dismiss();
        getdata.getFee(uid,this);
    }

    @Override
    public void failCash(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

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
        EventBus.getDefault().register(this);

    }
}
