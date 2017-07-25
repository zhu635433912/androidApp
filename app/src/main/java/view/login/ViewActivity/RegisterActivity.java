package view.login.ViewActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.R;
import com.zhy.autolayout.AutoLayoutActivity;



import java.util.Map;

import modle.user_ziliao.User_id;
import view.login.Modle.Modle_user;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;


/**
 * 注册页面
 */

public class RegisterActivity extends AutoLayoutActivity implements Dei_init,View.OnClickListener {
    private ImageButton dei_loginbutton;//登陆
    private RadioButton Rdbutton;//学生
    private RadioButton Rdbutton1;//老师
    private ImageButton dei_exit;//返回
    private EditText dei_editext;//手机号码
    private EditText dei_editext1;//密码
    private EditText dei_Verfic;//验证码
    private ImageButton dei_toview;//获取验证码
    private ImageButton dei_Verificatgion;//查看
    private EditText dei_yqm;//邀请码
    private login_wan_presenter dwan;
    private TextView text;
    private EditText payPsdEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_dei);
        User_id.getInstance().addActivity(this);
        inint();
    }
    private void inint() {
        dwan=new S_wan_presenter(this);
        dei_yqm= (EditText) findViewById(R.id.dei_tuijianma);
        dei_loginbutton= (ImageButton) findViewById(R.id.dei_login);
        dei_exit= (ImageButton) findViewById(R.id.dei_exti);
        Rdbutton= (RadioButton) findViewById(R.id.dei_RadioGrooup);
        Rdbutton1= (RadioButton) findViewById(R.id.dei_radiobutton1);
        dei_editext= (EditText) findViewById(R.id.dei_username);
        dei_editext1= (EditText) findViewById(R.id.dei_password);
        dei_Verfic= (EditText) findViewById(R.id.dei_editextyansema);
        payPsdEdit = (EditText) findViewById(R.id.dei_pay_password);
        dei_toview= (ImageButton) findViewById(R.id.dei_VictButton);
        dei_Verificatgion= (ImageButton) findViewById(R.id.dei_imagSess);
        text= (TextView) findViewById(R.id.dei_yzm);
        dei_toview.setOnClickListener(this);
        dei_Verificatgion.setOnClickListener(this);
        Rdbutton.setOnClickListener(this);
        Rdbutton1.setOnClickListener(this);
        dei_loginbutton.setOnClickListener(this);
        dei_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dei_VictButton:
                Log.d("aa","获取验证码");
                dwan.SMSverification(dei_toview,text,"signup");
                break;
            case R.id.dei_imagSess:
                Log.d("aa","查看密码");
                dwan.hidden(dei_editext1);
                break;
            case R.id.dei_RadioGrooup:
                Rdbutton1.setChecked(false);
                break;
            case R.id.dei_radiobutton1:
                Rdbutton.setChecked(false);
                break;
            case R.id.dei_login:
                Log.d("aa","注册");
                if (dei_editext1.getText().length() > 8 && dei_editext1.getText().length() <= 16 && payPsdEdit.getText().length()==6) {
                    dwan.Registereduser();
                }else if (dei_editext1.getText().length() > 8 && dei_editext1.getText().length() <= 16 && payPsdEdit.getText().length()<6){
                    Toast.makeText(this, "请输入6位支付密码", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "密码必须超过8位小于16位", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.dei_exti:
                Log.d("aa","返回上一步");
                Intent intent=new Intent(RegisterActivity.this,LoginAcitivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean Choice() {return false;}

    @Override
    public void Successful(Modle_user user) {Toast.makeText(this,"以发送验证码到"+user.getUsername(),Toast.LENGTH_LONG).show();}

    @Override
    public void Fail(String yuan) {Toast.makeText(this,yuan,Toast.LENGTH_LONG).show();}

    @Override
    public String getusername() {
        return dei_editext.getText().toString();
    }

    @Override
    public String getpassword() {
        return dei_editext1.getText().toString();
    }

    public String getPayPsd(){return payPsdEdit.getText().toString();}
    @Override
    public String Verification() {
        return dei_Verfic.getText().toString();
    }

    @Override
    public String gettoogbuton() {
        if (Rdbutton.isChecked()){
            return "学生";
        }else {
            return "教师";
        }
    }

    @Override
    public void setReatfalse(String a) {
        Toast.makeText(this,a,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setReatture(Map<String,Object> map) {
        Toast.makeText(this,"注册成功",Toast.LENGTH_LONG).show();
        Intent intent = NewMainActivity_.intent(this).get();
        String role= (String) map.get("role");
        String id= (String) map.get("id");
        intent.putExtra("id",id);
        intent.putExtra("role",role);
        getUser_id().setRole(role);
        getUser_id().setUid(id);
        startActivity(intent);
        finish();
    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }

    @Override
    public String getyqm() {
        return dei_yqm.getText().toString();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
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