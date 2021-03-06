package modle.MyHttp;


import java.util.Map;

import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 课程访问请求参数设置
 */

public interface Release_course_http  {
    //增加教师课程
    @POST("index.php?s=/Service/Teacher/add_course")
    Call<Demtest> Addcourse(@Query("uid") int uid, @Query("course_id") int course_id, @Query("course_remark") String course_remark, @Query("visit_fee") int visit_fee
    , @Query("unvisit_fee") int unvisit_fee,@Query("service_type")int service_type,@Query("grade_id")int grade_id);

    //查询教师课程
    @POST("index.php?s=/Service/Teacher/query_course")
    Call<ContentModle> Selcecourse(@Query("uid") int uid);

    //删除教师课程
    @POST("index.php?s=/Service/Teacher/del_course")
    Call<Demtest> delect(@Query("uid") int uid, @Query("id") int course_id);

    @POST("index.php?s=/Service/Teacher/add_course")
    Call<Demtest> publishCourse(@Query("uid") String uid, @Query("course_id") int course_id,@Query("grade_id")int grade_id,
                                @Query("course_remark") String course_remark,
                                @Query("visit_fee") int visit_fee, @Query("unvisit_fee") int unvisit_fee,
                                @Query("service_type")int service_type,
                                @Query("address")String address,
                                @Query("lat")String lat,
                                @Query("lng")String lng
                                );
    //修改课程
    @POST("index.php?s=/Service/Teacher/edit_course")
    Call<Demtest> changeCourse(@Query("uid") String uid,
                               @Query("id") String id,
                               @Query("course_id") int course_id,
                               @Query("grade_id")int grade_id,
                               @Query("course_remark") String course_remark,
                               @Query("visit_fee") int visit_fee,
                               @Query("unvisit_fee") int unvisit_fee,
                               @Query("service_type")int service_type,
                               @Query("address")String address,
                               @Query("lat")String lat,
                               @Query("lng")String lng);
}
