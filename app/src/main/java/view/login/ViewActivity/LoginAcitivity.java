package view.login.ViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.deguan.xuelema.androidapp.NewMainActivity_;
//import com.deguan.xuelema.androidapp.New_StudentActivity_;
import com.deguan.xuelema.androidapp.PayPswActivity_;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.APPConfig;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.deguan.xuelema.androidapp.utils.SharedPreferencesUtils;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;


import java.io.File;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.LoginActivity;
import jiguang.chat.activity.MainActivity;
import jiguang.chat.database.UserEntry;
import jiguang.chat.utils.SharePreferenceManager;
import jiguang.chat.utils.ToastUtil;
import modle.user_ziliao.User_id;
import view.login.Modle.MobileView;
import view.login.Modle.RegisterEntity;
import view.login.Modle.RegisterUtil;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;

/**
 * 登陆页面
 * 登录问题 点击登录的时候要判断是否记住密码 此时再去做存储记录
 */
public class  LoginAcitivity extends AutoLayoutActivity implements wan_inint,View.OnClickListener, MobileView {
    private ImageButton loginBtn;//登陆
    private ImageButton returnBtn;//返回
    private TextView loginTv;//注册
    private EditText phoneEdit;//手机号码
    private EditText psdEdit;//密码
    private ImageButton lookPsd;//查看
    private ToggleButton rememberBtn;//记住密码
    private TextView forgetPsdTv;//忘记密码
    private Intent intent;//意图
    private login_wan_presenter swan;//具体业务逻辑实现类
    private SharedPreferences sharedPreferences = null;
    private AVLoadingIndicatorView loginLoading;
    private TextView loginLoadingTv;
    private ImageView guideImage1,guideImage2,guideImage3;
    private ProgressBar progressBar;

    private String myydy;
    private ActivityOptionsCompat activityOptionsCompat;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logion_wan);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        guideImage1  = (ImageView) findViewById(R.id.guide1);
        guideImage2 = (ImageView) findViewById(R.id.guide2);
        guideImage3 = (ImageView) findViewById(R.id.guide3);
        //过渡动画
//        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide));
        inint();
        SharedPreferences sp = getSharedPreferences("ydy", MODE_PRIVATE);
        //判断记录是第一次就是"t",不是就是"1"
        myydy = sp.getString("booled", "t");
//        DemoHelper.getInstance().logout(true,new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
////                runOnUiThread(new Runnable() {
////                    public void run() {
////                        pd.dismiss();
////                        // show login screen
////                        finish();
//////                        startActivity(new Intent(SetUp.this, LoginAcitivity.class));
////                    }
////                });
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
////                runOnUiThread(new Runnable() {
////
////                    @Override
////                    public void run() {
////                        // TODO Auto-generated method stub
////                        pd.dismiss();
////                        Toast.makeText(SetUp.this, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
////                    }
////                });
//            }
//        });
        JMessageClient.logout();
        PermissUtil.startPermiss(this);
        //从xml里取保存的账号
        sharedPreferences=getSharedPreferences("userxml",MODE_PRIVATE);
        String ksy=sharedPreferences.getString("username","no");
        if (!ksy.equals("no")){
            rememberBtn.setChecked(true);
            String name=sharedPreferences.getString("username","");
            String pass=sharedPreferences.getString("password","");
            phoneEdit.setText(name);
            psdEdit.setText(pass);
            phoneEdit.setSelection(phoneEdit.getText().length());
        }
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

    //初始化
    private void inint() {
        swan=new S_wan_presenter(this);
        //进度指示器

        loginLoading= (AVLoadingIndicatorView) findViewById(R.id.denglulogo);
        loginLoadingTv= (TextView) findViewById(R.id.denglulogotext);
        loginLoading.bringToFront();
        loginLoadingTv.bringToFront();
        loginLoading.setVisibility(View.GONE);
        loginLoadingTv.setVisibility(View.GONE);

        loginBtn= (ImageButton) findViewById(R.id.wan_login);
        returnBtn= (ImageButton) findViewById(R.id.wan_imagebutton);
        loginTv= (TextView) findViewById(R.id.wan_textviewzhu);
        phoneEdit= (EditText) findViewById(R.id.wan_username);
        psdEdit= (EditText) findViewById(R.id.wan_password);
        lookPsd= (ImageButton) findViewById(R.id.wan_imagetoview);
        rememberBtn= (ToggleButton) findViewById(R.id.wan_togglebutton);
        forgetPsdTv= (TextView) findViewById(R.id.wan_wangjimima);


        loginBtn.setOnClickListener(this);
        returnBtn.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        lookPsd.setOnClickListener(this);
        rememberBtn.setOnClickListener(this);
        forgetPsdTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //登录button
            case R.id.wan_login:
//                JMessageClient.register(get
                Log.d("aa","登陆");

                //判断网络连接
                if (isNetworkAvailable(this))
                {
//                    swan.getlogin();
                    new RegisterUtil().getLogin(getusername(),getpassword(),this);
                    progressBar.setVisibility(View.VISIBLE);
                    loginBtn.setEnabled(false);
                    loginBtn.setClickable(false);
                }
                else
                {
                    Toast.makeText(this, "当前没有可用网络！", Toast.LENGTH_LONG).show();
                }

                break;
            //返回button
            case  R.id.wan_imagebutton:
                Log.d("aa","返回");
                break;
            //lookPsd查看密码
            case  R.id.wan_imagetoview:

                Log.d("aa","查看");
                swan.hidden(psdEdit);
                break;
            //loginTv 注册
            case  R.id.wan_textviewzhu:
                Log.d("aa","注册");
                 intent=new Intent(LoginAcitivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            //rememberPsd记住密码
            case  R.id.wan_togglebutton:
                Log.d("aa","记住密码");
//                if (rememberBtn.isChecked()){
//                    SharedPreferences preferences = getSharedPreferences("userxml",MODE_PRIVATE);
//
//                }
                break;
            //forgetPsdTv 忘记密码Tv
            case  R.id.wan_wangjimima:
                Log.d("aa","忘记密码");
                 intent=new Intent(LoginAcitivity.this,RevisePsdActivity.class);
                startActivity(intent);
                break;
        }
    }

    private double isSetup;

    @Override
    public void loginture(Map<String,Object> map) {

        isSetup = Double.parseDouble(map.get("has_paypassword")+"");
        final String role= (String) map.get("role");
        final String id= (String) map.get("id");
//        intent.putExtra("id",id);
//        intent.putExtra("role",role);
        intent= NewMainActivity_.intent(this).extra("id",id).extra("role",role).get();
        getUser_id().setRole(role);
        getUser_id().setUid(id);
        User_id.setNickName(map.get("nickname")+"");

        if (rememberBtn.isChecked()){
            rememberPssword();
        }else {
            SharedPreferences userxml=getSharedPreferences("userxml",MODE_PRIVATE);
            userxml.edit().remove("username").remove("password").commit();
        }
        SharedPreferencesUtils.setParam(this, APPConfig.USRE_PHONE,getusername());
            SharedPreferences sp = getSharedPreferences("userstate", Context.MODE_PRIVATE);
            SharedPreferences.Editor ddite = sp.edit();
            ddite.putString("state", "1"); //1以登录
            ddite.putString("id", id);
            ddite.putString("role", role);
            ddite.putString("username", map.get("username") + "");
            ddite.putString("password", map.get("password") + "");
            ddite.putString("nickname", map.get("nickname")+  "");
            ddite.commit();



        final String name = map.get("username")+"";
        User_id.setUsername(name);
        final long start = System.currentTimeMillis();

//        JMessageClient.login(name, "123456", new BasicCallback() {
//            @Override
//            public void gotResult(int responseCode, String responseMessage) {
//                if (responseCode == 0) {
//                    SharePreferenceManager.setCachedPsw("123456");
//                    UserInfo myInfo = JMessageClient.getMyInfo();
//                    File avatarFile = myInfo.getAvatarFile();
//                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
//                    if (avatarFile != null) {
//                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
//                    } else {
//                        SharePreferenceManager.setCachedAvatarPath(null);
//                    }
//                    String username = myInfo.getUserName();
//                    String appKey = myInfo.getAppKey();
//                    UserEntry user = UserEntry.getUser(username, appKey);
//                    if (null == user) {
//                        user = new UserEntry(username, appKey);
//                        user.save();
//                    }
////                    mContext.goToActivity(mContext, MainActivity.class);
//                    Log.d("aa", "登陆成功");
////                    mContext.finish();
//                } else {
//                    Log.d("aa", "登陆失败"+responseMessage);
////                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
//                }
//            }
//        });


        if (myydy.equals("1")){
            progressBar.setVisibility(View.GONE);
            getsj(role);
            SharedPreferences sharepre = getSharedPreferences("ydy", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharepre.edit();
            //第一次进入
            editor.putString("booled", "2");
            editor.commit();
            myydy = "2";
        }else {
            JMessageClient.login(name, "123456", new BasicCallback() {
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
//                    mContext.goToActivity(mContext, MainActivity.class);
                        if (isSetup == 0) {
                            startActivity(PayPswActivity_.intent(LoginAcitivity.this).get());
                        } else{
                            startActivity(intent);
                            finish();
                        }
                        loginBtn.setEnabled(true);
                        loginBtn.setClickable(true);
                        progressBar.setVisibility(View.GONE);
                        Log.d("aa", "登陆成功");
//                    mContext.finish();
                    } else {
                        Log.d("aa", "登陆失败"+responseMessage);
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                    }
                }
            });

        }



        //进入动画
        activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        if (loginLoading.getVisibility()==View.VISIBLE) {
            loginLoading.setVisibility(View.GONE);
            loginLoadingTv.setVisibility(View.GONE);
        }
//        startActivity(intent, activityOptionsCompat.toBundle());
//        finish();
    }

    private void getsj(String roles) {
        if (roles.equals("1")){
            guideImage1.setVisibility(View.VISIBLE);
            guideImage1.setImageResource(R.drawable.student_guide1);
            guideImage2.setImageResource(R.drawable.student_guide2);
            guideImage3.setImageResource(R.drawable.student_guide3);
        }else {
            guideImage1.setVisibility(View.VISIBLE);
            guideImage1.setImageResource(R.drawable.teacher_guide1);
            guideImage2.setImageResource(R.drawable.teacher_guide2);
            guideImage3.setImageResource(R.drawable.teacher_guide3);
        }
        guideImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage1.setVisibility(View.GONE);
                guideImage2.setVisibility(View.VISIBLE);
            }
        });
        guideImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage2.setVisibility(View.GONE);
                guideImage3.setVisibility(View.VISIBLE);
            }
        });
        guideImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage3.setVisibility(View.GONE);
                JMessageClient.login(getusername(), "123456", new BasicCallback() {
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
                            if (isSetup == 0){
                                startActivity(PayPswActivity_.intent(LoginAcitivity.this).get());
                            }else {
                                startActivity(intent);
                                finish();
                            }
                            loginBtn.setEnabled(true);
                            loginBtn.setClickable(true);
                            progressBar.setVisibility(View.GONE);
                            Log.d("aa", "登陆成功");
                        } else {
                            Log.d("aa", "登陆失败"+responseMessage);
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                        }
                    }
                });


            }
        });
    }


    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }


    //登录失败
    @Override
    public void loginflase(String user) {
        loginBtn.setEnabled(true);
        loginBtn.setClickable(true);
        progressBar.setVisibility(View.GONE);
//        Log.d("aa","登陆状态="+user);
        if (loginLoading.getVisibility()==View.VISIBLE) {
            loginLoading.setVisibility(View.GONE);
            loginLoadingTv.setVisibility(View.GONE);
        }
        Toast.makeText(this,user,Toast.LENGTH_LONG).show();
    }
    //获取用户id
    @Override
    public String getusername() {
        return phoneEdit.getText().toString();
    }
    //获取用户密码
    @Override
    public String getpassword() {
        return psdEdit.getText().toString();
    }

    //记住密码
    @Override
    public void rememberPssword() {
        SharedPreferences.Editor edi=sharedPreferences.edit();
        edi.putString("username",phoneEdit.getText().toString());
        edi.putString("password",psdEdit.getText().toString());
        edi.commit();
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
    /**
     * 检查当前网络是否可用
     * @return
     */

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    public void onBackPressed() {
        if(!mBackKeyPressed){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mBackKeyPressed = true;
            new Timer().schedule(new TimerTask() {
                //延时两秒，如果超出则擦错第一次按键记录
                // @Override
                public void run() {
                    mBackKeyPressed = false;
                }
             }, 2000);
        }
        else{
        //退出程序
            this.finish();
            System.exit(0);

        }
    }

    @Override
    public void successRegister(String msg) {

    }

    @Override
    public void failRegister(String msg) {
        loginBtn.setEnabled(true);
        loginBtn.setClickable(true);
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

        if (loginLoading.getVisibility()==View.GONE) {
            loginLoading.setVisibility(View.VISIBLE);
            loginLoadingTv.setVisibility(View.VISIBLE);
        }

        isSetup = Double.parseDouble(entity.getHas_paypassword());
        final String role= entity.getRole();
        final String id= entity.getUser_id();
//        intent.putExtra("id",id);
//        intent.putExtra("role",role);
        intent= NewMainActivity_.intent(this).extra("id",id).extra("role",role).get();
        getUser_id().setRole(role);
        getUser_id().setUid(id);
        User_id.setNickName(entity.getNickname());

        if (rememberBtn.isChecked()){
            rememberPssword();
        }else {
            SharedPreferences userxml=getSharedPreferences("userxml",MODE_PRIVATE);
            userxml.edit().remove("username").remove("password").commit();
        }
        SharedPreferencesUtils.setParam(this, APPConfig.USRE_PHONE,getusername());
        SharedPreferences sp = getSharedPreferences("userstate", Context.MODE_PRIVATE);
        SharedPreferences.Editor ddite = sp.edit();
        ddite.putString("state", "1"); //1以登录
        ddite.putString("id", id);
        ddite.putString("role", role);
        ddite.putString("username",getusername());
        ddite.putString("password", getpassword());
        ddite.putString("nickname", entity.getNickname());
        ddite.commit();



        final String name = getusername();
        User_id.setUsername(name);

        if (myydy.equals("1")){
            getsj(role);
            SharedPreferences sharepre = getSharedPreferences("ydy", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharepre.edit();
            //第一次进入
            editor.putString("booled", "2");
            editor.commit();
            myydy = "2";
        }else {
            JMessageClient.login(name, "123456", new BasicCallback() {
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
//                    mContext.goToActivity(mContext, MainActivity.class);
                        if (isSetup == 0) {
                            startActivity(PayPswActivity_.intent(LoginAcitivity.this).get());
                        } else{
                            startActivity(intent);
                            finish();
                        }
                        Log.d("aa", "登陆成功");
//                    mContext.finish();
                    } else {
                        Log.d("aa", "登陆失败"+responseMessage);
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                    }
                }
            });

        }



        //进入动画
        activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        if (loginLoading.getVisibility()==View.VISIBLE) {
            loginLoading.setVisibility(View.GONE);
            loginLoadingTv.setVisibility(View.GONE);
        }
//        startActivity(intent, activityOptionsCompat.toBundle());
//        finish();
    }
}