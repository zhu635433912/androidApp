package modle.MyHttp;

import com.deguan.xuelema.androidapp.init.Student_init;

import java.util.List;
import java.util.Map;

import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 需求请求接口设置.
 */

public interface Demand_http {
    /*
    获取需求列表
     */
    @POST("index.php?s=/Service/Requirement/gets_requirement")
    Call<ContentModle> getDemandlist(@Query("uid") int uid, @Query("filter_type") int filter_type, @Query("filter_id") int filter_id, @Query("start_time") String start_time,
                                     @Query("end_time") int end_time, @Query("page") int page,@Query("lat")double lat,@Query("lng")double lng);
    /*
    获取单一需求
     */
    @POST("index.php?s=/Service/Requirement/get_requirement")
    Call<User_Modle> getDemanddanyilist(@Query("uid") int uid,@Query("id") int id);

    /*
    发布需求
     */
    @POST("index.php?s=/Service/Requirement/create_requirement")
    Call<Demtest> setDemand(@Query("uid") int uid, @Query("content") String content, @Query("fee") float fee, @Query("grade_id") int grade_id, @Query("course_id") int course_id,
                            @Query("gender") int gender, @Query("age") String age, @Query("education_id") int education_id, @Query("province") String province, @Query("cty") String cty,
                            @Query("state") String state, @Query("service_type") int service_type,
                            @Query("start") String start,@Query("end") String end,@Query("lat") double lat, @Query("lng") double lng);

    /*
    更新需求
     */
    @POST("index.php?s=/Service/Requirement/update_requirement")
    Call<Demtest> Update_Demand(@Query("uid") int uid, @Query("content") String content, @Query("fee") float fee, @Query("grade_id") int grade_id, @Query("course_id") int course_id,
                                @Query("gender") int gender,@Query("education_id") int education_id, @Query("province") String province, @Query("cty") String cty,
                                @Query("state") String state, @Query("service_type") int service_type, @Query("items") Map<String,Object> items);
    /*
    删除需求
     */
    @POST("index.php?s=/Service/Requirement/delete_requirement")
    Call<Demtest> Delete_Demand(@Query("uid") int uid,@Query("id") int id);

    /*
    假删除需求
    */
    @POST("index.php?s=/Service/Requirement/delete_requirement2")
    Call<Demtest> JiaDelete_Demand(@Query("uid") int uid,@Query("id") int id);


    /*
         获取用户自己的需求列表
    */
    @POST("index.php?s=/Service/Requirement/gets_requirement")
    Call<ContentModle> getMyDemandlist(@Query("publisher_id") int publisher_id, @Query("filter_type") int filter_type);


    @POST("index.php?s=/Service/Requirement/recommend_requirement_commend")
    Call<ContentModle> getTuijianDemandList(@Query("course_id") int course_id, @Query("grade_id") int grade_id
    ,@Query("lat") String lat, @Query("lng") String lng,@Query("province") String province, @Query("city") String city,@Query("state") String state);

    @POST("index.php?s=/Service/Requirement/recommend_requirement_commend")
    Call<ContentModle> getTuijianDemandList1(@Query("name") String name);


}
