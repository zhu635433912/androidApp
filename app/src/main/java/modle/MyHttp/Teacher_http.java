package modle.MyHttp;

import com.deguan.xuelema.androidapp.entities.TuijianListEntity;

import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 教师请求接口设置
 */

public interface Teacher_http {
    /*
    获取教师个人资料
    */
    @POST("index.php?s=/Service/Teacher/get_information")
    Call<User_Modle> getTeacherziliao(@Query("uid") int id);
    /*
    获取教师详细个人资料
     */
    @POST("index.php?s=/Service/Teacher/get_teacher")
    Call<User_Modle> getTeacherxiangxizl(@Query("uid") int uid,@Query("id") int id);
    /*
    获取教师列表
     */
    @POST("index.php?s=/Service/Teacher/gets_teacher")
    Call<ContentModle> getTeacherlist(@Query("uid") int uid, @Query("lat") double lat, @Query("lng") double lng,
                                      @Query("order") int order,@Query("state") String state,@Query("gender") int gender,@Query("speciality") int speciality,
                                      @Query("grade_type") int grade_type,@Query("order_rank") int order_rank);
    /*
    教师资料更新荣誉证书
     */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getTeachergenxin(@Query("uid") int uid,@Query("others_1") String others_1);
    /*
   教师资料更新荣誉证书
    */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getTeachergenxin2(@Query("uid") int uid,@Query("others_2") String others_2);
    /*
  教师资料更新荣誉证书
   */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getTeachergenxin3(@Query("uid") int uid,@Query("others_3") String others_3);
    /*
  教师资料更新荣誉证书
   */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getTeachergenxin4(@Query("uid") int uid,@Query("others_4") String others_4);

    /*
    教师列表显示状态更改
     */
    @POST("index.php?s=/Service/Teacher/update_status1")
    Call<Demtest> getTeacherzhuangtai(@Query("uid") int uid,@Query("status") int status);
    /*
    招聘列表显示状态
     */
    @POST("index.php?s=/Service/Teacher/update_status2")
    Call<Demtest> getTeachezhaopzhuangt(@Query("uid") int uid,@Query("status") int status);

    /*
教师资料更新服务类型
 */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getservice_type(@Query("uid") int uid,@Query("service_type") String service_type);

    /*
教师资料更新教龄
*/
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getyears(@Query("uid") int uid,@Query("years") int years);

    /*
    教师资料更新个人简介
    */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getresume(@Query("uid") int uid,@Query("resume") String resume);
    /*
    教师资料更新个人特长
    */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getspeciality(@Query("uid") int uid,@Query("speciality") String speciality);
    /*
         教师资料更新个人签名
   */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getsignature(@Query("uid") int uid,@Query("signature") String signature);
    /*
        教师资料更新个人毕业学校
    */
    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getgraduated_school(@Query("uid") int uid,@Query("graduated_school") String graduated_school);
//    /*
//        获取推荐教师
//    */
//    @POST("index.php?s=/Service/Teacher/recommend_teacher")
//    Call<ContentModle> gettuijianjiaoshi(@Query("course_id") int course_id,@Query("grade_id") int grade_id,@Query("address") String address,@Query("lat") String lat,@Query("lng") String lng);
      /*
        获取推荐教师
    */
@POST("index.php?s=/Service/Teacher/recommend_teacher")
Call<TuijianListEntity> gettuijianjiaoshi(
//        @Query("course_id") int course_id,@Query("grade_id") int grade_id,
//        @Query("address") String address,
        @Query("lat") String lat,@Query("lng") String lng);

    @POST("index.php?s=/Service/Teacher/get_teacher_comment")
    Call<ContentModle> getEvluation(@Query("teacher_id") int teacher_id);

    @POST("index.php?s=/Service/Teacher/update_information")
    Call<Demtest> getsubjectBackgroud(@Query("uid") int uid,@Query("class_img") String class_img);
}
