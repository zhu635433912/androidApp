package modle.MyHttp;

import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.Erre;
import modle.JieYse.User_Modle;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 订单请求接口
 */

public interface Oredr_http {
    /*
    获取订单列表
     */
    @POST("index.php?s=/Service/Order/gets_order")
    Call<ContentModle> getOredrlist(@Query("uid") int uid, @Query("filter_type") int filter_type,
                                    @Query("status") int status, @Query("page") int page,@Query("requirement_id") int requirement_id,@Query("order_rank") int order_rank);

    //    @POST("index.php?s=/Service/Order/gets_order")
//    Call<ContentModle> getOredrlist(@Query("uid") int uid, @Query("filter_type") int filter_type,
//                                    @Query("status") int status, @Query("page") int page,@Query(" ") int requirement_id,@Query("order_rank") int order_rank);
    /*
    获取单一订单
     */
    @POST("index.php?s=/Service/Order/get_order")
    Call<User_Modle> getOredrdanyilist(@Query("uid") int uid,@Query("id") int id);

    /*
    创建订单
     */
    @POST("index.php?s=/Service/Order/create_order")
    Call<Erre> setOredr(@Query("uid") int uid, @Query("teacher_id") int teacher_id, @Query("requirement_id") int requirement_id, @Query("fee") float fee,@Query("duration") int duration,
                        @Query("course_id")int course_id
            ,@Query("grade_id")int grade_id
                       ,@Query("service_type")int service_type
                        ,@Query("address")String address,
                        @Query("province")String province,
                        @Query("city")String city,
                        @Query("district")String district,
                        @Query("desc")String desc
    );
    /*
    删除订单
     */
    @POST("index.php?s=/Service/Order/delete_order")
    Call<Demtest> DeleteOredr(@Query("uid") int uid, @Query("id") int id);
    /*
    更新订单状态------------->有问题无返回
     */
    @POST("index.php?s=/Service/Order/update_orderstatus")
    Call<Erre> UpdateOredr(@Query("uid") int uid, @Query("id") int id,@Query("status") float status ,
                           @Query("safeword") String safewprd,@Query("fee") double fee);
    /*
    更新订单评分
     */
    @POST("index.php?s=/Service/Order/update_orderrank")
    Call<Demtest> Updatepingfen(@Query("uid") int uid, @Query("id") int id,@Query("rank1") int rank1, @Query("rank2") int rank2,@Query("rank3") int rank3
            , @Query("rank4") int rank4,@Query("rank")int rank);
    /*
    更新订单金额
     */

    @POST("index.php?s=/Service/Order/update_orderfee")
    Call<Demtest> UpdateOredrfee(@Query("uid") int uid, @Query("id") int id,@Query("fee") double fee);
    /*
    更新订单课时数  ----------->更新失败
     */
    @POST("index.php?s=/Service/Order/update_orderduration")
    Call<Demtest> Updatekeshishu(@Query("uid") int uid, @Query("id") int id,@Query("fee") float fee);
    /*
    评论订单
     */
    @POST("index.php?s=/Service/Order/comment_order")
    Call<Demtest> PinglunOredr(@Query("uid") int uid, @Query("source_id") int id,
                               @Query("content") String content,@Query("picture") long picture,
                               @Query("rank")int rank ,@Query("rank4")int rank4,
                               @Query("rank1") int rank1,@Query("rank2") int rank2,@Query("rank3") int rank3);
    /*
    订单退款
     */
    @POST("index.php?s=/Service/Order/order_refund")
    Call<Demtest> Oredrtuikun(@Query("uid") int uid, @Query("id") int id,@Query("status") int status,@Query("refund_fee") double refund_fee);
    /*
    创建临时订单
     */
    @POST("index.php?s=/Service/Order/create_temp_order")
    Call<Demtest> Createorder(@Query("uid") int uid,@Query("teacher_id") int eacher_id,
                              @Query("requirement_id") int requirement_id,@Query("fee") float fee,@Query("course_id")int course_Id,
                              @Query("grade_id")int grade_id,@Query("address")String address,@Query("lat")double lat,@Query("lng")double lng);

    @POST("index.php?s=/Service/Order/submit_refund")
    Call<Demtest> submitRefund(@Query("uid") int uid,@Query("id")int id,@Query("status")int status,
                               @Query("refund_fee")String refund_fee,@Query("reason")String reason,@Query("desc")String desc,
                               @Query("imgs1")String imgs1,@Query("imgs2")String imgs2,@Query("imgs3")String imgs3,@Query("imgs4")String imgs4);
}
