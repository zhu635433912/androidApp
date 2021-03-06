package view.login.Modle;

import modle.JieYse.ContentModle;
import modle.JieYse.User_Modle;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-08-08 10:58
 * 修改人：zhuyunjian
 * 修改时间：2017-08-08 10:58
 * 修改备注：
 */


public interface RegisterApi {

    @POST("index.php?s=/Service/Accounts/check_mobile")
    Call<User_Modle> getMobileNumber(@Query("mobile")String mobile,@Query("ftype")String ftype);


    @POST("index.php?s=/Service/Accounts/signup")
    Call<RegisterEntity> getRegisterEntity(@Query("role")int role,@Query("username")String username,@Query("password")String password,
                                           @Query("yzm")String yzm,@Query("inv_code")String inv_code);

    @POST("index.php?s=/Service/Accounts/signin")
    Call<RegisterEntity> getLoginEntity(@Query("username")String username,@Query("password")String password);

    @POST("index.php?s=/Service/Accounts/update_paypassword")
    Call<User_Modle> getPayPwd(@Query("id")int id,@Query("pay_password")String pay_password,@Query("oldpay_password")String oldpay_password,@Query("type")int type);

    @POST("index.php?s=/Service/Accounts/ad")
    Call<ContentModle> getAd();
    //学生钱包说明
    @POST("index.php?s=/Service/Accounts/s_wallet_des")
    Call<PicEntity> getsWalletPic();

    //老师钱包说明
    @POST("index.php?s=/Service/Accounts/t_wallet_des")
    Call<PicEntity> gettWalletPic();

    //学生vip说明
    @POST("index.php?s=/Service/Accounts/s_vip_des")
    Call<PicEntity> getsVipPic();


    //教师vip说明
    @POST("index.php?s=/Service/Accounts/t_vip_des")
    Call<PicEntity> gettVipPic();


    //用户说明
    @POST("index.php?s=/Service/Accounts/user_des")
    Call<PicEntity> getUserPic();

}
