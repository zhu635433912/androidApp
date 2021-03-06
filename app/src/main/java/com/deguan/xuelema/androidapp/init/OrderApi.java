package com.deguan.xuelema.androidapp.init;

import modle.JieYse.ContentModle;
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
 * 创建时间：2017-06-17 09:10
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 09:10
 * 修改备注：
 */
public interface OrderApi {
    /*
   获取订单列表
    */
    @POST("index.php?s=/Service/Order/gets_order")
    Call<ContentModle> getOredrlist(@Query("uid") int uid, @Query("filter_type") int filter_type,
                                     @Query("page") int page,@Query("status")int status);

    //
    /*
   获取未完成 已完成 进行中订单列表
    */
    @POST("index.php?s=/Service/Order/gets_order")
    Call<ContentModle> getNofinishOredrlist(@Query("uid") int uid, @Query("filter_type") int filter_type,
                                    @Query("page") int page,@Query("status")int status);

    //
    //
    /*
   获取未完成 已完成 进行中订单列表
    */
    @POST("index.php?s=/Service/Order/gets_order")
    Call<ContentModle> getEvaluateOredrlist(@Query("uid") int uid, @Query("filter_type") int filter_type,
                                            @Query("page") int page,@Query("status")int status,@Query("order_rank")int order_rank);

    //教师完成授课
    @POST("index.php?s=/Service/Order/gets_order_uncomplete")
    Call<ContentModle> getTeacherEvaOrderlist(@Query("id")int uid,@Query("page")int page);
}
