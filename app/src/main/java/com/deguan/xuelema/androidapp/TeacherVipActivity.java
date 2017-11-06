package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.Map;

import modle.alipay.PayResult;
import modle.getdata.PayUtil;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_teacher_vip)
public class TeacherVipActivity extends MyBaseActivity implements PayView {

    @ViewById(R.id.vip_desc_tv)
    TextView descTv;
    @ViewById(R.id.back_rl)
    RelativeLayout backRl;
    @ViewById(R.id.vip_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.vip_nickname_tv)
    TextView nicknameTv;
    @ViewById(R.id.two_yuan_ll)
    LinearLayout twoLl;
    @ViewById(R.id.choose_two_image)
    ImageView twoImage;
    @ViewById(R.id.alipay_ll)
    LinearLayout alipayLl;
    @ViewById(R.id.alipay_choose_image)
    ImageView alipayImage;
    @ViewById(R.id.weixinpay_ll)
    LinearLayout wxpayLl;
    @ViewById(R.id.wxpay_choose_image)
    ImageView wxpayImage;
    @ViewById(R.id.sure_btn)
    ImageView sureBtn;

    //支付宝回调
    private final int SDK_PAY_FLAG = 1;
    public static String APP_ID = "wx3815ad6bb05c5aca";

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult=new PayResult((Map<String,String>) msg.obj);

            String resultInfo=payResult.getResult();//同步返回结果
            String resultStatus=payResult.getResultStatus();//同步返回结果

            switch (resultStatus){
                case "9000":
                    Toast.makeText(TeacherVipActivity.this,"订单支付成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TeacherVipActivity.this,Payment_tureActivty.class));
                    TeacherVipActivity.this.finish();
                    break;
                case "8000":
                    Toast.makeText(TeacherVipActivity.this,"正在处理中，支付结果未知（有可能已经支付成功）!",Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(TeacherVipActivity.this,"订单支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(TeacherVipActivity.this,"重复请求",Toast.LENGTH_SHORT).show();
                    break;

                case "6001":
                    Toast.makeText(TeacherVipActivity.this,"取消支付",Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(TeacherVipActivity.this,"网络连接出错",Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(TeacherVipActivity.this,"支付结果未知（有可能已经支付成功）",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    private IWXAPI iwxapi;
    private PayUtil payUtil;
    private String headurl;
    private String nickname;

    @Override
    public void before() {
        super.before();
        headurl = getIntent().getStringExtra("headurl");
        nickname = getIntent().getStringExtra("nickname");
        iwxapi = WXAPIFactory.createWXAPI(this,APP_ID);
        iwxapi.registerApp(APP_ID);
    }

    @Override
    public void initData() {
        payUtil = new PayUtil();
    }

    private int channl = 1;
    private int flag = 1;
    @Override
    public void initView() {
        headImage.setImageURI(Uri.parse(headurl));
        nicknameTv.setText(nickname);
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        alipayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channl = 1;
                alipayImage.setImageResource(R.mipmap.choose_ok_gou);
                wxpayImage.setImageDrawable(null);
            }
        });
        wxpayLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channl = 2;
                wxpayImage.setImageResource(R.mipmap.choose_ok_gou);
                alipayImage.setImageDrawable(null);
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showmyDialog();
                codePopwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
            }
        });
        descTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(VipDescActivity_.intent(TeacherVipActivity.this).extra("type",4).get());
            }
        });
    }

    private PopupWindow codePopwindow;

    private void showmyDialog() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.vip_code_pop, null);
        TextView nextTv = (TextView) view.findViewById(R.id.vip_next_tv);
        TextView buyTv = (TextView) view.findViewById(R.id.vip_sure_tv);
        final EditText codeEdit = (EditText) view.findViewById(R.id.vip_code_edit);
        codePopwindow = new PopupWindow(view);
        codePopwindow.setFocusable(true);
        codePopwindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        codePopwindow.setWidth(width / 10 * 8);
        codePopwindow.setHeight(height / 3 );
        codePopwindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
        codePopwindow.setOutsideTouchable(false);
        codePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(TeacherVipActivity.this, 1f);
            }
        });
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payUtil.getVipPay(Integer.parseInt(User_id.getUid()),flag,channl,"",TeacherVipActivity.this);
                codePopwindow.dismiss();
            }
        });
        buyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codeEdit.getText().length()==11){
                    payUtil.getVipPay(Integer.parseInt(User_id.getUid()),flag,channl,codeEdit.getText().toString(),TeacherVipActivity.this);
                }else {
                    Toast.makeText(TeacherVipActivity.this, "请输入正确的邀请码", Toast.LENGTH_SHORT).show();
                }
                codePopwindow.dismiss();
            }
        });
    }



    @Override
    public void successPay(Map<String, Object> map) {
        if (channl == 2){

            PayReq payReq = new PayReq();
            payReq.appId = APP_ID;
            payReq.partnerId = map.get("partnerid").toString();
            payReq.nonceStr = map.get("noncestr").toString();
            payReq.packageValue = map.get("package").toString();
            payReq.prepayId = map.get("prepayid").toString();
            payReq.timeStamp = map.get("timestamp").toString();
//            Log.d("aa","timestamp---------"+payReq.t1366686imeStamp);
            payReq.sign = map.get("sign").toString();
//            Log.d("aa",payReq.appId+"----"+payReq.partnerId+"----"+payReq.nonceStr+"----"+payReq.packageValue+"----"+payReq.prepayId+"----"+payReq.sign+"----");
            iwxapi.sendReq(payReq);
//            EventBus.getDefault().post(telphone,"telphone");
//            EventBus.getDefault().post(order_id,"weiorderId");

        }else if (channl == 1) {
            //获取去服务器返回的支付宝订单信息再去唤起支付宝
            String info = (String) map.get("info");
            String ordert = info.substring(13);
            final String orderInfo = ordert;

//            Log.e("aa", ordert);
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
//                    Log.e("aa", "唤起支付宝");
                    PayTask alipay = new PayTask(TeacherVipActivity.this);
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
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
