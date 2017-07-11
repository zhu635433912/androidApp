package modle.MyHttp;

import android.content.Context;

import com.deguan.xuelema.androidapp.entities.DownloadEntity;
import com.deguan.xuelema.androidapp.entities.PayEntity;

import modle.JieYse.ContentModle;
import modle.JieYse.Courses_Modle;
import modle.JieYse.Demtest;
import modle.JieYse.Grades_Modle;
import modle.JieYse.User_Modle;
import modle.user_ziliao.User_id;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 年级 科目
 */

public interface Data {
    //更新 下载地址
    @POST("index.php?s=/Service/Public/downUrl")
    Call<DownloadEntity> getDownload(@Query("type")String type);
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
    @POST("index.php?s=/Service/Order/dopay")
    Call<User_Modle> getordetsiz(@Query("id") int id,@Query("paytype") int paytype
            ,@Query("reward_fee")int reward_fee);

    //获取支付宝订单
    @POST("index.php?s=/Service/Order/dopay")
    Call<PayEntity> getpayMsg(@Query("id") int id, @Query("paytype") int paytype
            ,@Query("reward_fee")int reward_fee);
    //关于我们
    @POST("index.php?s=/Service/Setup/aboutus")
    Call<User_Modle> getabotuse();

    //获取充值订单
    @POST("index.php?s=/Service/Accounts/recharge")
    Call<User_Modle> getzisdin(@Query("uid") int uid,@Query("fee") float fee,@Query("channel") int channel);

    //通过手机号码获取用户个人信息
    @POST("index.php?s=/Service/Accounts/get_profile")
    Call<User_Modle> getmobe(@Query("tel") String tle);

    //提现
    @POST("index.php?s=/Service/Finance/withdraw")
    Call<User_Modle>  getcash(@Query("uid")int uid,@Query("bank_name")String bank_name,@Query("bank_account")String bank_account,@Query("type")int type,@Query("fee")float fee);

    //现金券提现列表
    @POST("index.php?s=/Service/Finance/reward_list")
    Call<ContentModle> getcashList(@Query("uid")int uid,@Query("page")int page);

    //举报
    @POST("index.php?s=/Service/Accounts/create_tip")
    Call<User_Modle> upReport(@Query("uid")int uid,@Query("content")String content);

    //获取成交率
    @POST("/index.php?s=/Service/Teacher/query_deal_info")
    Call<User_Modle> getdea_info(@Query("teacher_id") int teacher_id);

    //成交率列表
    @POST("index.php?s=/Service/Order/query_deal_list")
    Call<ContentModle> getTurnover(@Query("teacher_id")int teacher_id,@Query("page")int page);

    //账单列表
    @POST("index.php?s=/Service/Finance/gets_billing")
    Call<ContentModle> getBillList(@Query("uid")int uid);
}
