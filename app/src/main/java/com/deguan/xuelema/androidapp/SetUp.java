package com.deguan.xuelema.androidapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.Set;

import modle.Huanxing.cache.UserCacheManager;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.DemoHelper;
import modle.user_ziliao.User_id;
import view.login.ViewActivity.RevisePsdActivity;
import view.login.ViewActivity.LoginAcitivity;

/**
 * 设置
 */

public class SetUp extends AutoLayoutActivity implements View.OnClickListener {
    private TextView guanyuwomen;
    private TextView xunqiubangzhu;
    private TextView wodetuiguang;
    private RelativeLayout finalsz;
    private TextView xiugaimima;
    private TextView wodezhaop;
    private TextView jiaoshirenzheng;
    private TextView fankuixinxi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);
        User_id.getInstance().addActivity(this);
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
                Intent intentb=new Intent(SetUp.this,Distribution_Activty.class);
                startActivity(intentb);
                break;
            case R.id.finalsz:
                SetUp.this.finish();
                break;
            case R.id.xiugaimima:
                Intent intent1=new Intent(SetUp.this, RevisePsdActivity.class);
                startActivity(intent1);
                break;
            case R.id.wodezhaop:
                new AlertDialog.Builder(SetUp.this).setTitle("学了么提示!").setMessage("确定退出?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //清空数据登录状态数据
                                //从xml里取保存的账号
                                SharedPreferences userxml=getSharedPreferences("userxml",MODE_PRIVATE);
                                userxml.edit().remove("username").remove("password").commit();
                                SharedPreferences settings = getSharedPreferences("userstate", MODE_PRIVATE);
                                SharedPreferences.Editor editor = settings.edit();
                                editor.remove("id");
                                editor.remove("role");
                                editor.remove("username");
                                editor.remove("password");
                                editor.remove("state");
                                editor.commit();
                                logout();
                                Intent intent2=new Intent(SetUp.this, LoginAcitivity.class);
                                startActivity(intent2);

                                Toast.makeText(SetUp.this,"退出成功！",Toast.LENGTH_LONG).show();
                                User_id.getInstance().exit();
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
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(false,new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // show login screen
                        finish();
//                        startActivity(new Intent(SetUp.this, LoginAcitivity.class));
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        Toast.makeText(SetUp.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}
