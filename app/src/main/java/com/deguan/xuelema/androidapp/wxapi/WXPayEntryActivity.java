package com.deguan.xuelema.androidapp.wxapi;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.Payment_tureActivty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;


public class WXPayEntryActivity extends AutoLayoutActivity implements IWXAPIEventHandler, ChangeOrderView {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	private Button wancheng;
	private int flag = 1;
	private int recharge = 0;
	private int orderId;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		EventBus.getDefault().register(this);
        setContentView(R.layout.paycomplete);
        
    	api = WXAPIFactory.createWXAPI(this, "wx3815ad6bb05c5aca");
        api.handleIntent(getIntent(), this);
		ImageButton backBtn = (ImageButton) findViewById(R.id.wancheng_back);
		backBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		wancheng = (Button) findViewById(R.id.wancheng);
		wancheng.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (recharge == 1){
					finish();
				}else {
					finish();
					Intent intent = new Intent(WXPayEntryActivity.this, MyOrderActivity.class);
					startActivity(intent);
					Toast.makeText(WXPayEntryActivity.this, "赶快去学习吧~", Toast.LENGTH_SHORT).show();
				}
			}
		});
    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Subscriber(tag = "telphone")
	public void getTelPhone(String telphone){
		if (flag == 0){
			new Getdata().sendMessage(User_id.getNickName()+"已支付订单",telphone);
		}
	}

	@Subscriber(tag = "recharge")
	public void getRecharge(int msg){
		if (msg == 1){
			recharge = 1;
		}

	}

	@Subscriber(tag = "weiorderId")
	public void getOrderId(int msg){
		orderId = msg;

	}


	@Override
	public void onResp(BaseResp resp) {
		if (resp.errCode==-1){
			Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
			new Getdata().cancalPay(orderId,this);
			finish();
		}else if (resp.errCode== -2){
			Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
			new Getdata().cancalPay(orderId,this);
			finish();
		}
		flag = resp.errCode;
//		Log.d("aa", "onPayFinish, errCode = " + resp.errCode);

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void successOrder(String msg) {

	}

	@Override
	public void failOrder(String msg) {

	}
}