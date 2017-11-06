package view.login.ViewActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.PayPswActivity_;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.database.UserEntry;
import jiguang.chat.utils.SharePreferenceManager;
import modle.Adapter.My_PagerAdapter;
import modle.user_ziliao.User_id;
import view.login.Modle.MobileView;
import view.login.Modle.RegisterEntity;
import view.login.Modle.RegisterUtil;

/**
 * 首次进入引导页面
 * Created by Administrator on 2017/6/4.
 */

public class SplashActivity extends AutoLayoutActivity implements View.OnClickListener, MobileView {
    private SharedPreferences sp1 = null;
    private SharedPreferences sp = null;
    private ViewPager viewpager;
    private View view1,view2,view3;
    private List<View> listview;
    private ImageButton jinru;
    private Intent intent;
    private String username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inde);
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, ""));
        PermissUtil.startPermiss(this);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        //判断用户是否第一次进入
        sp = getSharedPreferences("ydy", MODE_PRIVATE);
        //判断记录是第一次就是"t",不是就是"1"
        String myydy= sp.getString("booled", "t");
        if (myydy.equals("2")){
            getsj();
            viewpager.setVisibility(View.GONE);
        }


        jinru = (ImageButton) findViewById(R.id.jinru);

//        jinru.setVisibility(View.GONE);
//        jinru.setOnClickListener(this);

        //加载引导页
        LayoutInflater layoutInflater = getLayoutInflater();
        view1 = layoutInflater.inflate(R.layout.index1, null);
        view2 = layoutInflater.inflate(R.layout.index2, null);
        view3 = layoutInflater.inflate(R.layout.index3, null);

        listview = new ArrayList<>();
        listview.add(view1);
        listview.add(view2);
        listview.add(view3);
        view3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("ydy", Context.MODE_PRIVATE);
                SharedPreferences.Editor ddite = sp.edit();
                //第一次进入
                ddite.putString("booled", "1");
                ddite.commit();
                getsj();
            }
        });
        viewpager.setAdapter(new My_PagerAdapter(listview));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if (position!=2){
//                    jinru.setVisibility(View.GONE);
//                }
//                if (position == 2) {
//                    jinru.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jinru:
                SharedPreferences sp = getSharedPreferences("ydy", Context.MODE_PRIVATE);
                SharedPreferences.Editor ddite = sp.edit();
                //第一次进入
                ddite.putString("booled", "1");
                ddite.commit();
                getsj();
                break;
        }
    }

    public void getsj(){
        sp1 = getSharedPreferences("userstate", MODE_PRIVATE);
        String mykey = sp1.getString("id", "t");
        if (!mykey.equals("t")) {
            String zt = sp1.getString("state", "d");
            if (zt.equals("1")) {
                String id = sp1.getString("id", "t");
                String role = sp1.getString("role", "t");
                username = sp1.getString("username", "t");
                String password = sp1.getString("password", "t");
                String nickname = sp1.getString("nickname","t");
//                intent.putExtra("id", id);
//                intent.putExtra("role", role);
                intent = NewMainActivity_.intent(this).extra("id",id).extra("role",role).get();
                User_id.setRole(role);
                User_id.setUid(id);
                User_id.setPassword(password);
                User_id.setUsername(username);
                User_id.setNickName(nickname);
//                try {
//                    EMClient.getInstance().createAccount(username,
//                            //                            password
//                            "123456"
//                    );//同步方法
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
                new RegisterUtil().getLogin(username,password,this);
            }
            //状态改变

        } else {

            //进入登录页面
            Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
            startActivity(intent);
            this.finish();

        }

    }

    @Override
    public void successRegister(final String msg) {
        JMessageClient.login(username, "123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw("123456");
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    if (msg.equals("0")){
                        startActivity(PayPswActivity_.intent(SplashActivity.this).get());
                    }else {
                        startActivity(intent);
                        finish();
                    }
//                    mContext.goToActivity(mContext, MainActivity.class);
                    Log.d("aa", "登陆成功");
//                    mContext.finish();
                } else {
                    Log.d("aa", "登陆失败"+responseMessage);
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                }
            }
        });


    }

    @Override
    public void failRegister(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        //进入登录页面
        Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i("aa", logs);
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
