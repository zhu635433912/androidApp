package modle.MyHttp;

import android.graphics.Bitmap;

import java.util.List;
import java.util.Map;

import modle.JieYse.Demtest;
import modle.JieYse.UploadEntity;
import modle.JieYse.User_Modle;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 用户请求接口设置
 */

public interface MyOkhttp {
    /*
    登陆
     */
    @POST("index.php?s=/Service/Accounts/signin")
    Call<Demtest> getDemtest(@Query("username") String username, @Query("password") String password);

    /*
    获取验证码
     */
    @POST("index.php?s=/Service/Accounts/check_mobile")
    Call<Demtest> getyzm(@Query("mobile") String mobile, @Query("ftype") String lei);

    /*
    注册用户
     */
    @POST("index.php?s=/Service/Accounts/signup")
    Call<Demtest> getzhuce(@Query("role") int role, @Query("username") String username, @Query("password") String password, @Query("yzm") String yzm);

    /*
    重置密码
     */
    @POST("index.php?s=/Service/Accounts/reset")
    Call<Demtest> getchongzhi(@Query("username") String username, @Query("password") String password, @Query("yzm") String yzm);

    /*
    修改密码
     */
    @POST("index.php?s=/Service/Accounts/modify_password")
    Call<Demtest> getxiugaimima(@Query("uid") int id, @Query("old_password") String oldpassword, @Query("new_password") String password);

    /*
    获取用户资料
     */
    @POST("index.php?s=/Service/Accounts/get_profile")
    Call<User_Modle> getuserziliao(@Query("id") int id);

    /*
    用户资料更新
     */
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setuserziliao(@Query("id") int id, @Query("content") Map<String, Object> listmap);

    /*
    举报------>http://deguanjiaoyu.com/index.php?s=/Service/Accounts/create_tip
     */
    @POST("index.php?s=/Service/Accounts/create_tip")
    Call<Demtest> ssetjubao(@Query("uid") int uid, @Query("content") String content);

    /*
      用户资料更新昵称
       */
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setnickname(@Query("id") int id, @Query("nickname") String nickname);

    /*
   用户资料真实姓名
   */
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setname(@Query("id") int id, @Query("name") String name);

    /*
用户资料年龄
*/
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setage(@Query("id") int id, @Query("age") String age);

    /*
用户资料个性签名
*/
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setsignature(@Query("id") int id, @Query("signature") String signature);

    /*
用户资料地区
*/
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setaddress(@Query("id") int id, @Query("address") String address);
    /*
用户资性别
*/
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setgender(@Query("id") int id, @Query("gender") String gender);

    /*
用户头像资料更新
*/
    @POST("index.php?s=/Service/Accounts/update_profile")
    Call<Demtest> setheadimg(@Query("id") int id, @Query("headimg") String headimg);


    /**
     * 上传一张图片
     * @param imgs
     * @return
     */
    @Multipart
    @POST("index.php?s=/Service/Public/uploadImg")
    Call<UploadEntity> uploadImage(
            @Part("file\"; filename=\"image.png\"")RequestBody imgs);

    /*
    用户信息反馈
     */
    @POST("index.php?s=/Service/Public/feedback")
    Call<Demtest> setfeedback(@Query("uid") int uid,@Query("content") String content);

}
