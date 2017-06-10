package modle.MyHttp;

import modle.JieYse.ContentModle;
import modle.JieYse.Courses_Modle;
import modle.JieYse.Demtest;
import modle.JieYse.Grades_Modle;
import modle.JieYse.User_Modle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 年级 科目
 */

public interface Data {
    //年级
    @POST("index.php?s=/Service/Setup/grades")
    Call<Grades_Modle> getgrade();
    //科目
    @POST("index.php?s=/Service/Setup/courses")
    Call<Courses_Modle> getSubject();
    //获取用户余额
    @POST("index.php?s=/Service/Finance/get_balance")
    Call<User_Modle> getFeea(@Query("uid") int uid);


    //获取融云 token
   // @POST("rongyun-sdk-php/API/rongyun_api.php")

    //获取现金卷
    @POST("index.php?s=/Service/Finance/userrecominfo")
    Call<User_Modle> getminfofee(@Query("uid") int uid);

    //一级分销，二级分销接口
    @POST("index.php?s=/Service/Finance/fenxiaoinfo")
    Call<User_Modle> getiniffenx(@Query("uid") int uid,@Query("level") int level);


    //获取支付宝订单
    @GET("index.php?s=/Service/Order/dopay")
    Call<User_Modle> getordetsiz(@Query("id") int id,@Query("paytype") int paytype);


    //关于我们
    @POST("index.php?s=/Service/Setup/aboutus")
    Call<User_Modle> getabotuse();

    //获取充值订单
    @POST("index.php?s=/Service/Accounts/recharge")
    Call<User_Modle> getzisdin(@Query("uid") int uid,@Query("fee") float fee,@Query("channel") int channel);

    //通过手机号码获取用户个人信息
    @POST("index.php?s=/Service/Accounts/get_profile")
    Call<User_Modle> getmobe(@Query("tel") String tle);
}
