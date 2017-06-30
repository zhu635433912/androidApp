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
import com.deguan.xuelema.androidapp.Payment_tureActivty;
import com.deguan.xuelema.androidapp.R;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLayoutActivity;


public class WXPayEntryActivity extends AutoLayoutActivity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	private Button wancheng;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
				Intent intent=new Intent(WXPayEntryActivity.this,MyOrderActivity.class);
				startActivity(intent);
				Toast.makeText(WXPayEntryActivity.this,"赶快去学习吧~",Toast.LENGTH_SHORT).show();
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

	@Override
	public void onResp(BaseResp resp) {
		if (resp.errCode==-1){
			Toast.makeText(this, "支付失败", Toast.LENGTH_SHORT).show();
			finish();
		}else if (resp.errCode== -2){
			Toast.makeText(this, "取消支付", Toast.LENGTH_SHORT).show();
			finish();
		}
//		Log.d("aa", "onPayFinish, errCode = " + resp.errCode);

//		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle("提示");
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
//		}
	}
}