package com.deguan.xuelema.androidapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.AlertDialogUtil;
import com.deguan.xuelema.androidapp.utils.DeviceUtil;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.utils.SharePreferenceManager;
import modle.MyUrl;
import modle.user_ziliao.User_id;
import view.login.ViewActivity.RevisePsdActivity;
import view.login.ViewActivity.LoginAcitivity;

/**
 * 设置
 */

public class SetUp extends MyBaseActivity implements View.OnClickListener {
    private TextView guanyuwomen;
    private TextView xunqiubangzhu;
    private TextView wodetuiguang;
    private RelativeLayout finalsz;
    private TextView xiugaimima;
    private TextView wodezhaop;
    private TextView jiaoshirenzheng;
    private TextView fankuixinxi;
    private TextView cacheTv;
    private TextView clearCacheTv;
    private AlertDialog clearDialog;
    private ProgressDialog pd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        User_id.getInstance().addActivity(this);
        cacheTv = (TextView) findViewById(R.id.cache_tv);
        clearCacheTv = (TextView) findViewById(R.id.clear_cache_tv);
        wodetuiguang= (TextView) findViewById(R.id.wodetuiguang);
        guanyuwomen = (TextView) findViewById(R.id.guanyuwomen);
        xunqiubangzhu = (TextView) findViewById(R.id.xunqiubangzhu);
        finalsz= (RelativeLayout) findViewById(R.id.finalsz);
        xiugaimima= (TextView) findViewById(R.id.xiugaimima);
        wodezhaop= (TextView) findViewById(R.id.wodezhaop);
        jiaoshirenzheng= (TextView) findViewById(R.id.jiaoshirenzheng);
        fankuixinxi= (TextView) findViewById(R.id.fankuixinxi);

        finalsz.bringToFront();

        if (User_id.getRole().equals("1")){
            jiaoshirenzheng.setVisibility(View.GONE);
        }

        fankuixinxi.setOnClickListener(this);
        wodezhaop.setOnClickListener(this);
        finalsz.setOnClickListener(this);
        wodetuiguang.setOnClickListener(this);
        guanyuwomen.setOnClickListener(this);
        xunqiubangzhu.setOnClickListener(this);
        xiugaimima.setOnClickListener(this);
        jiaoshirenzheng.setOnClickListener(this);

        String s = "当前缓存"+ DeviceUtil.getFormatSize(DeviceUtil.getFolderSize(MyUrl.getCacheFile()));
        cacheTv.setText(s);
        clearDialog = AlertDialogUtil.setClearDialog(this,cacheTv);
        clearCacheTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearDialog.show();
                }
            });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.guanyuwomen:
                //关于我们
                Intent intent=new Intent(SetUp.this,About_us.class);
                startActivity(intent);
                break;
            case R.id.xunqiubangzhu:
                //寻求帮助
                Intent intenta=new Intent(SetUp.this,Hepl.class);
                startActivity(intenta);
                break;
            case R.id.wodetuiguang:
                //我的推广
//                Intent intentb=new Intent(SetUp.this,Distribution_Activty.class);
//                startActivity(intentb);

                startActivity(PayPswActivity_.intent(SetUp.this).extra("type",2).get());
                break;
            case R.id.finalsz:
                SetUp.this.finish();
                break;
            case R.id.xiugaimima:
                Intent intent1=new Intent(SetUp.this, RevisePsdActivity.class);
                startActivity(intent1);
                break;
            case R.id.wodezhaop:
                new AlertDialog.Builder(SetUp.this).setTitle("学习吧提示!").setMessage("确定退出?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
                                //清空数据登录状态数据
                                //从xml里取保存的账号
//                                SharedPreferences userxml=getSharedPreferences("userxml",MODE_PRIVATE);
//                                userxml.edit().remove("username").remove("password").commit();
                                SharedPreferences settings = getSharedPreferences("userstate", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("id");
                                editor.remove("role");
                                editor.remove("username");
                                editor.remove("password");
                                editor.remove("state");
                                editor.remove("nickname");
                                editor.commit();
                                logout();

//                                JMessageClient.logout();
                                Intent intent2=new Intent(SetUp.this, LoginAcitivity.class);
                                startActivity(intent2);
                                pd.dismiss();
                                finish();
                                User_id.deleteActivity();
//                                User_id.getInstance().exit();
                                Toast.makeText(SetUp.this,"退出成功！",Toast.LENGTH_LONG).show();

                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
            case R.id.jiaoshirenzheng:
                //跳转教师管理
                Intent integer=new Intent(SetUp.this,Teacher_management.class);
                startActivity(integer);
                break;
            case R.id.fankuixinxi:
                //跳转到信息反馈
                Intent intent3=new Intent(SetUp.this,Feedback_Activity.class);
                startActivity(intent3);
                break;
        }
    }

    void logout() {
        pd = new ProgressDialog(this);
//        String st = getResources().getString("退出登录");
        pd.setMessage("退出登录");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

//        UserInfo info = JMessageClient.getMyInfo();
//        if (null != info) {
//            SharePreferenceManager.setCachedUsername(info.getUserName());
//            if (info.getAvatarFile() != null) {
//                SharePreferenceManager.setCachedAvatarPath(info.getAvatarFile().getAbsolutePath());
//            }

//        } else {
//            Log.d("aa","退出登录失败");
//        }
    }
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
//                    Log.i("aa", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i("aa", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e("aa", logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d("aa", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
                    Log.i("aa", "Unhandled msg - " + msg.what);
            }
        }
    };
}
