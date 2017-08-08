package view.login.Modle;

/**
 * 登陆
 */

public interface Modle_wan_login {
    /*
    登陆验证线程逻辑
     */
    public void getlogin(Modle_user user);

    /*
    短信验证线程逻辑
     */
    public void SMSlogin(Modle_user user, String lei);

    /*
    修改 注册用户线程逻辑
     */
    public void Userregistration(String category, String username, String passowrd, String ftype,String yqm);
}

