package view.login.ViewActivity;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/11 0011.
 */

public interface Pos_inint  {
    /*
    获取username  password yzm
     */
    public String getusername();
    public String getpassword();
    public String getyzm();
    /*
    修改成功修改失败
     */
    public void xiuflase(String eseemge);
    public void xiuture(Map<String,Object> map);
    /*
    获取验证码返回信息
     */
    public void Viewc(String xx);

}
