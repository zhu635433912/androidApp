package view.login.ViewActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.deguan.xuelema.androidapp.MainActivity;
import com.deguan.xuelema.androidapp.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;


import java.util.Map;

import modle.user_ziliao.User_id;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;

import static modle.user_ziliao.User_id.getUsername;

/**
 * 登陆页面
 * 登录问题 点击登录的时候要判断是否记住密码 此时再去做存储记录
 */
public class LoginAcitivity extends AutoLayoutActivity implements wan_inint,View.OnClickListener {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logion_wan);

        //过渡动画
//        getWindow().setExitTransition(TransitionInflater.from(this).inflateTransition(R.transition.slide));
        inint();

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

                Log.d("aa","登陆");
                if (loginLoading.getVisibility()==View.GONE) {
                    loginLoading.setVisibility(View.VISIBLE);
                    loginLoadingTv.setVisibility(View.VISIBLE);
                }
                    swan.getlogin();
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

    @Override
    public void loginture(Map<String,Object> map) {
        intent=new Intent(LoginAcitivity.this,MainActivity.class);
        String role= (String) map.get("role");
        String id= (String) map.get("id");
        intent.putExtra("id",id);
        intent.putExtra("role",role);
        getUser_id().setRole(role);
        getUser_id().setUid(id);
        if (rememberBtn.isChecked()){
            rememberPssword();
        }

            SharedPreferences sp = getSharedPreferences("userstate", Context.MODE_PRIVATE);
            SharedPreferences.Editor ddite = sp.edit();
            ddite.putString("state", "1"); //1以登录
            ddite.putString("id", id);
            ddite.putString("role", role);
            ddite.putString("username", map.get("username") + "");
            ddite.putString("password", map.get("password") + "");
            ddite.commit();



        //进入动画
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this);
        if (loginLoading.getVisibility()==View.VISIBLE) {
            loginLoading.setVisibility(View.GONE);
            loginLoadingTv.setVisibility(View.GONE);
        }
        startActivity(intent,activityOptionsCompat.toBundle());
      //  LoginAcitivity.this.finish();
    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }


    //登录失败
    @Override
    public void loginflase(String user) {
        Log.d("aa","登陆状态="+user);
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
}