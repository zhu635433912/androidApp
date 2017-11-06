package view.login.presenter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Map;

import view.login.Modle.Modle_user;


/**
 * Created by Administrator on 2017/3/7 0007.
 */

public interface login_wan_presenter {
    /*
    登陆
     */
    public boolean getlogin();
    /*
    登陆失败
     */
    public void loginshiba(String errmsg);
    /*
    登陆成功
     */
    public void loginchenggong(Map<String,Object> map);
    /*
    隐藏密码
     */
    public void hidden(EditText editText);
    /*
    记住密码
     */
    public void RememberPssword();
    /*
    短信验证
     */
    public void SMSverification(Button but2, TextView text, String yz);
    /*
    短信验证成功
     */
    public void SMStrue(Modle_user user);
    /*
    短信验证失败
     */
    public void SMSfalse(String shibai);
    /*
    注册用户
     */
    public void Registereduser();
    /*
    注册成功
     */
    public void Registereduserture(Map<String,Object>  map);
    /*
    注册失败
     */
    public void Registereduserflase(String xx);
    /*
    修改密码
     */
    public void xiugai();
    /*
    修改成功
     */
    public void xiugaiture(Map<String,Object>  map );
    /*
   修改失败
    */
    public void xiugaitflase(String cw);
}
