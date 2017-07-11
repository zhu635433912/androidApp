package modle.user_Modle;

import android.graphics.Bitmap;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 用户接口
 */

public interface User_init {
    /**
    注册用户
    role     用户角色 1:普通用户 2:教师 3:运营 4:管理员
    username 用户名
    password 密码
    yzm      短信验证码
     */
    public Map<String,Object> RegisteredUser( int role, String username, String password, String yzm);

    /**
     用户登录
    username 用户名
    password 密码
     */
    public Map<String,Object> Registerlogin( String username, String password);

    /**
    修改密码
     user_id          用户id
     Jiu_password 旧密码
     password 新密码
     */
    public Map<String,Object> Modifypassword(int user_id, String Jiupassword, String password);

    /**
     重置密码
    username 用户名
    password 密码
    yzm      短信验证码
     */
    public Map<String,Object> Resetpassword(String username, String password, String yzm);

    /**
     获取用户资料
     user_id    用户id
     */
    public Map<String,Object> User_Data(int user_id,String lat,String lng, Requirdetailed requirdetailed);


    /**
     用户资料更新
     user_id    用户id
     */
    public Map<String,Object> Updatedata(int user_id, Map<String, Object> map);

    public void Updatenickname(int user_id, String nickname);

    public void Updateheadimg(int user_id, String headimg);

    public void Updatename(int user_id, String name);

    public void Updategender(int user_id, String gender);

    public void Updateage(int user_id, String age);

    public void Updateaddress(int user_id, String address);

    public void setuserbitmap(File file,Student_init student_init);

    public void setlan_lng(int uid,double lat,double lng);

    public void UpdateEducation(int user_id,int education_id);

}
