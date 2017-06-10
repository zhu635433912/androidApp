package view.login.ViewActivity;


import java.util.Map;

import view.login.Modle.Modle_user;

/**
 * Created by Administrator on 2017/3/7 0007.
 */

public interface wan_inint {
    /*
    登陆成功
     */
    public void loginture(Map<String,Object> map);
    /*
    登陆失败
     */
    public void loginflase(String user);
    /*
    获取账username password
     */
    public String getusername();
    public String getpassword();
    /*
    保存
     */
    public void rememberPssword();
}
