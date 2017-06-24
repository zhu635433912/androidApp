package modle.Order_Modle;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Ordercontent_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Myconteol_init;
import control.Mycontrol;
import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.Erre;
import modle.JieYse.User_Modle;
import modle.MyHttp.Oredr_http;
import modle.MyUrl;
import modle.toos.MyListview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 订单实现类
 */

public class Order implements Order_init {
    private Map<String,Object> map;
    private Retrofit retrofit;
    private Oredr_http oredr_http;

    //初始化网络访问对象
    public Order(){
        map=new HashMap<String,Object>();
        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        oredr_http=retrofit.create(Oredr_http.class);
    }

    /*
    获取订单列表
     */
    @Override
    public Map<String, Object> getOrder_list(int id, int filter_type, int status, int page
            , final MyListview listView, final Context context, final Student_init student_init,int requirement_id,int order_rank) {
        Call<ContentModle> call=oredr_http.getOredrlist(id,filter_type,status,page,requirement_id,order_rank);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
               String error=response.body().getError();
              if(error.equals("ok")){
                   List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                   listmap = response.body().getContent();
                   Log.e("aa", "服务器返回订单列表" + listmap.toString());
                   if (student_init==null) {
                       Myconteol_init myconteol_init = new Mycontrol();
                       myconteol_init.getorder_huidiao(listmap, listView, context);
                   }else {
                       Log.e("aa","服务器返回订单列表"+listmap.toString());
                    student_init.setListview(listmap);
                   }
               }else {
                   String errmsg=response.body().getErrmsg();
                   Log.e("aa","获取订单列表失败="+errmsg);
               }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取订单列表异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    获取单一列表
     */
    @Override
    public Map<String, Object> getOrder_danyilist(int uid, int id, final Ordercontent_init ordercontent_init) {
        Call<User_Modle> call=oredr_http.getOredrdanyilist(uid,id);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map=response.body().getContent();
                    Log.e("aa","获取单一订单列表成功"+map.get("teacher_id")+map.get("teacher_name")+map.get("teacher_headimg")+map.get("placer_id")+map.get("placer_name")+
                            map.get("requirement_content")+map.get("requirement_address")+map.get("requirement_fee")+map.get("order_comment")+map.get("order_rank"));
                        ordercontent_init.Updatecontent(map);


                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取单一订单列表失败="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取单一订单列表异常错误="+t.toString());
            }
        });
        return null;
    }


    /*
    创建订单
     */

    @Override
    public Map<String, Object> Establish_Order(int uid, int teacher_id, int requirement_id, float fee,int duration,int course_Id,int grade_Id) {
        Call<Erre> call=oredr_http.setOredr(uid,teacher_id,requirement_id,fee,duration,course_Id,grade_Id);
        call.enqueue(new Callback<Erre>() {
            @Override
            public void onResponse(Call<Erre> call, Response<Erre> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "创建订单成功");
                }else {
                    Log.e("aa", "创建订单失败"+response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<Erre> call, Throwable t) {
                Log.e("aa", "创建订单异常错误");
            }
        });
        return null;
    }

    /*
    删除订单
     */
    @Override
    public Map<String, Object> Delete_Order(int uid, int id, final Requirdetailed requirdetailed) {
        Call<Demtest> call=oredr_http.DeleteOredr(uid,id);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Map<String,Object> stringMap=new HashMap<String, Object>();
                List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
                if (error.equals("ok")){
                    Log.e("aa","删除订单成功");
                    stringMap.put("text","删除订单成功!");
                    listmap.add(stringMap);
                    requirdetailed.Updatefee(listmap);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","删除订单成功失败="+errmsg);

                    stringMap.put("text","删除订单成功失败"+errmsg+"!");
                    requirdetailed.Updatecontent(stringMap);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","删除订单异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    更新订单状态
     */
    @Override
    public Map<String, Object> Update_Order(int id, int dindan, int status,String safeword,float fee) {

        Call<Erre> call=oredr_http.UpdateOredr(id,dindan,status,safeword,fee);
        call.enqueue(new Callback<Erre>() {
            @Override
            public void onResponse(Call<Erre> call, Response<Erre> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新订单状态成功");

                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新订单状态失败="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Erre> call, Throwable t) {
                Log.e("aa","更新订单状态异常失败");
            }
        });
        return null;
    }

    /*
    更新订单评分
     */
    @Override
    public Map<String, Object> UpdateOrder_score(int uid, int id, int rank1, int rank2, int rank3, int rank4) {
        Call<Demtest> call=oredr_http.Updatepingfen(uid,id,rank1,rank2,rank3,rank4);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新评分订单成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新评分订单失败="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新订单评分异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    更新订单金额
     */
    @Override
    public Map<String, Object> UpdateOrder_Amount(int uid, int id, int fee, final ChangeOrderView changeOrderView) {
        Call<Demtest> call=oredr_http.UpdateOredrfee(uid,id,fee);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
            String error=response.body().getError();
            if (error.equals("ok")){
                Log.e("aa","更新金额订单成功");
                changeOrderView.successOrder("更新金额订单成功");
            }else {
                String errmsg=response.body().getErrmsg();
                Log.e("aa","更新订单金额失败="+errmsg);
                changeOrderView.failOrder("更新订单金额失败");
            }
        }
        @Override
        public void onFailure(Call<Demtest> call, Throwable t) {
            Log.e("aa","更新订单金额异常错误="+t.toString());
            changeOrderView.failOrder("更新订单金额异常错误");
         }
         });
        return null;
    }

    /*
    更新订单课时数
     */
    @Override
    public Map<String, Object> UpdateOrder_Classhour(int uid, int id, int fee) {
        Call<Demtest> call=oredr_http.Updatekeshishu(uid,id,fee);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新课时数费用成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新课时数费用失败="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新课时数费用异常错误="+t.toString());
            }
        });
        return null;
    }


    /*
    评论订单
     */
    @Override
    public Map<String, Object> Comment_Order(int uid, int source_id, String content, long picture) {
        Call<Demtest> call=oredr_http.PinglunOredr(uid,source_id,content,picture);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","评论订单成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","评论订单失败="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","评论订单异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    订单退款
     */
    @Override
    public Map<String, Object> Order_refund(int uid, int id, int status, float refund_fee) {
        Call<Demtest> call=oredr_http.Oredrtuikun(uid,id,status,refund_fee);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","订单退款成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","评论退款失败="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","订单退款异常错误="+t.toString());
            }
        });
        return null;
    }

    //创建临时订单
    @Override
    public void  CreateOrder(int uid, int teacher_id , int requirement_id, float fee,int course_id,int grade_id, final Requirdetailed requirdetailed){
        Call<Demtest> call=oredr_http.Createorder(uid,teacher_id,requirement_id,fee,course_id,grade_id);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Map<String,Object> map=new HashMap<String, Object>();
                List<Map<String,Object> > listmap=new ArrayList<Map<String, Object>>();
                if (error.equals("ok")){
                    Log.e("aa","创建订单成功");
                    map.put("tosa","创建订单成功");
                    listmap.add(map);
                    requirdetailed.Updatefee(listmap);

                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","创建订单失败="+errmsg);
                    map.put("tosa","创建订单失败"+errmsg);
                    listmap.add(map);
                    requirdetailed.Updatefee(listmap);

                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {

            }
        });

    }
}
