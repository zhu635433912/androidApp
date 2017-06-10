package view.login.ViewActivity;


import java.util.Map;

import view.login.Modle.Modle_user;

/**
 * Created by Administrator on 2017/3/8 0008.
 */

public interface Dei_init  {
    /*
    辨识用户
    ture 老师
    flase 学生
     */
    public boolean Choice();
    /*
    注册成功
     */
    public void Successful(Modle_user user);
    /*
    注册失败
     */
    public void Fail(String yuan);
    /*
    获取手机号码
     */
    public String getusername();
    /*
    获取密码
     */
    public String getpassword();
    /*
    获取验证码
     */
    public String Verification();
    /*
    获取用户级别
     */
    public String gettoogbuton();
    /*
    注册失败
     */
    public void setReatfalse(String a);
    /*
    注册成功
     */
    public void setReatture(Map<String,Object> map);
    /*
    获取邀请码
     */
    public String getyqm();

}
