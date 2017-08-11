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
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.R;
import com.zhy.autolayout.AutoLayoutActivity;



import java.util.Map;

import modle.user_ziliao.User_id;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;


/**
 * 修改密码页面
 * Created by Administrator on 2017/3/6 0006.
 */

public class RevisePsdActivity extends AutoLayoutActivity implements Pos_inint,View.OnClickListener {
    private ImageButton pos_loginbutton;
    private EditText pos_username;
    private EditText pos_password;
    private EditText pos_Verfic;//验证码
    private ImageButton pos_toview;//获取验证码
    private ImageButton pos_Verificatgion;//查看
    private  ImageButton pos_extic;//返回
    private login_wan_presenter dwan;
    private TextView textview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_pos);
        User_id.getInstance().addActivity(this);
       // HideIMEUtil.wrap(this);
        init();
    }

    private void init() {
        dwan=new S_wan_presenter(this);
        pos_loginbutton= (ImageButton) findViewById(R.id.pos_login);
        pos_username= (EditText) findViewById(R.id.pos_username);
        pos_password= (EditText) findViewById(R.id.pos_password);
        pos_toview= (ImageButton) findViewById(R.id.pos_vicat);
        pos_Verificatgion= (ImageButton) findViewById(R.id.pos_chakan);
        pos_Verfic= (EditText) findViewById(R.id.pos_editextyansema);
        pos_extic= (ImageButton) findViewById(R.id.pos_imageButton);
        textview= (TextView) findViewById(R.id.pos_yzm);
        pos_extic.setOnClickListener(this);
        pos_toview.setOnClickListener(this);
        pos_password.setOnClickListener(this);
        pos_Verificatgion.setOnClickListener(this);
        pos_loginbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pos_imageButton:
                Log.d("aa","返回");
//                Intent intin=new Intent(RevisePsdActivity.this,LoginAcitivity.class);
//                startActivity(intin);
                finish();
                break;
            case R.id.pos_vicat:
                Log.d("aa","获取验证码");
                dwan.SMSverification(pos_toview,textview,"forget");
                break;
            case R.id.pos_chakan:
                Log.d("aa","查看");
                dwan.hidden(pos_password);
                break;
            case R.id.pos_login:
                dwan.xiugai();

                break;

        }
    }

    @Override
    public String getusername() {
        return pos_username.getText().toString();
    }

    @Override
    public String getpassword() {
        return pos_password.getText().toString();
    }

    @Override
    public String getyzm() {
        return pos_Verfic.getText().toString();
    }

    public String getPayPsd(){return "";}

    @Override
    public void xiuflase(String eseemge) {
        Toast.makeText(RevisePsdActivity.this,eseemge,Toast.LENGTH_LONG).show();
    }

    @Override
    public void xiuture(Map<String,Object> map) {
        Toast.makeText(RevisePsdActivity.this,"修改成功",Toast.LENGTH_LONG).show();
        Intent intin = NewMainActivity_.intent(this).get();
        String role= (String) map.get("role");
        String id= (String) map.get("id");
        intin.putExtra("id",id);
        intin.putExtra("role",role);
        getUser_id().setRole(role);
        getUser_id().setUid(id);
        startActivity(intin);
        finish();
    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }
    @Override
    public void Viewc(String xx) {
        Toast.makeText(RevisePsdActivity.this,xx,Toast.LENGTH_LONG).show();

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
