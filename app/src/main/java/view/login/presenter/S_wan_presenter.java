package view.login.presenter;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

import view.login.Modle.Modle_user;
import view.login.Modle.Modle_wan_login;
import view.login.Modle.S_wan_login;
import view.login.ViewActivity.Dei_init;
import view.login.ViewActivity.Pos_inint;
import view.login.ViewActivity.wan_inint;


/**
 * 登陆控制层
 */

public class S_wan_presenter implements login_wan_presenter {
    //登录接口
    private wan_inint wan;
    private Modle_wan_login modlewan;
    private Modle_user modleuser;
    private Dei_init dei;
    private boolean flag=true;
    private ImageButton but2;
    private TextView text;
    private String Myyzm;
    private String username;
    private String password;
    private String payPsd;
    private Pos_inint pos;
    public S_wan_presenter(wan_inint wan){
        this.wan=wan;
    }
    public S_wan_presenter(Dei_init dei){this.dei=dei;}
    public S_wan_presenter(Pos_inint pos){this.pos=pos;}


    //登陆
    @Override
    public boolean getlogin() {
        String username=wan.getusername();
        String password=wan.getpassword();

        if (username.equals("")){
            loginshiba("账号不能为空");
            return false;
        }
        if (password.equals("")){
            loginshiba("密码不能为空");
            return false;
        }
        Log.d("aa","控制层收到值为username==="+username+"!!!!!password==="+password);
        if (modleuser==null){
            modleuser=new Modle_user();
        }
        if (modlewan==null){
            modlewan=new S_wan_login(wan);
        }
        modleuser.setUsername(username);
        modleuser.setPassword(password);
        //执行登陆逻辑方法
        modlewan.getlogin(modleuser);
        return false;
    }

    @Override
    public  void loginshiba(String errmsg) {
        wan.loginflase(errmsg);
    }

    @Override
    public void loginchenggong(Map<String,Object> map) {
        wan.loginture(map);
    }

    @Override
    public void hidden(EditText psdEdit) {
        if (flag==false){
            //设置EditText文本为隐藏的
            psdEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }else {
            //设置EditText文本为可见的
            psdEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        flag=!flag;
        psdEdit.postInvalidate();
        //最后面获得光标
        CharSequence charSequence = psdEdit.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    @Override
    public void RememberPssword() {
        wan.getusername();
        wan.getpassword();
    }
    //发送手机验证码
    @Override
    public void SMSverification(final ImageButton but2, final TextView text,String yz) {
        this.but2=but2;
        this.text=text;
        //timer.start();
        Log.d("aa","控制层");
        if (dei!=null) {
            modlewan=new S_wan_login(dei);
            username = dei.getusername();
        }else {
            modlewan=new S_wan_login(pos);
            username = pos.getusername();
        }
        Log.d("aa","长度为"+username.length());
        if (username.length()==11){
            Handler handler=new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    CountDownTimer timer = new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            but2.setClickable(false);
                            text.setText((millisUntilFinished / 1000) + "s");
                        }

                        @Override
                        public void onFinish() {
                            but2.setClickable(true);
                            text.setText("");
                        }
                    }.start();
                }
            });
            modleuser =new Modle_user();
            modleuser.setUsername(username);
            //int yzm=((int)((Math.random()*9+1)*100000)); 自动生成验证码
            //String yz=yzm+"";
            modleuser.setPassword(yz);
            modlewan.SMSlogin(modleuser,yz);
        }else {
            if (dei!=null) {
                //短信发送成功
                dei.Fail("请输入正确的手机号码");
            }
            if (pos!=null){
                pos.Viewc("请输入正确的手机号码");
            }
        }
    }
    @Override
    public void SMStrue(Modle_user user) {
        if (dei!=null) {
            //短信发送成功
            dei.Successful(user);
        }
        if (pos!=null){
            pos.Viewc("发送成功");
        }

    }

    @Override
    public void SMSfalse(String shibai) {
        if (dei!=null) {
            //注册发送失败
            dei.Fail(shibai);
        }
        if (pos!=null){
            pos.Viewc(shibai);
        }
    }
    //注册
    @Override
    public void Registereduser() {
        String username=dei.getusername();
        String password=dei.getpassword();
        String Verif=dei.Verification();
        String cla=dei.gettoogbuton();
        String yqm=dei.getyqm();
        String payPsd = dei.getPayPsd();
        if (username.length()>=11&&!password.equals("")&&!Verif.equals("")&&!cla.equals("")){
            if (password.length()<8){
                dei.Fail("密码不能小于八位");
            }else {

                    modlewan = new S_wan_login(dei);
                    Log.d("aa", "传递进类型=" + cla + "---账号=" + username + "---密码=" + password + "验证码" + Verif);
                    modlewan.Userregistration(cla, username, password, Verif, yqm,payPsd);

            }
        }else{
            dei.Fail("填写格式不正确");
        }
    }

    //注册成功
    @Override
    public void Registereduserture(Map<String,Object>  map) {
       dei.setReatture(map);
    }
    //注册失败
    @Override
    public void Registereduserflase(String xx) {
        dei.setReatfalse(xx);
    }


    //修改密码
    @Override
    public void xiugai(){
        username = pos.getusername();
        password = pos.getpassword();
        Myyzm = pos.getyzm();
        payPsd = pos.getPayPsd();
        String yqm="";
        if (username.length() >= 11 && !password.equals("") && !Myyzm.equals("")) {
            if (password.length() > 8 && password.length() <16) {
                modlewan = new S_wan_login(pos);
                modlewan.Userregistration("z", username, password, Myyzm,yqm,"");
            } else {
                pos.xiuflase("密码必须超过8位小于16位");
            }
        }else{
            pos.xiuflase("填写格式不正确");
        }
    }

    @Override
    public void xiugaiture(Map<String,Object>  map) {
    pos.xiuture(map);
    }

    @Override
    public void xiugaitflase(String cw) {
    pos.xiuflase(cw);
    }


}
