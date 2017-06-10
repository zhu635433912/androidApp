package modle.getdata;

import android.util.ArrayMap;
import android.util.Log;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.JieYse.ContentModle;
import modle.JieYse.Courses_Modle;
import modle.JieYse.Grades_Modle;
import modle.JieYse.User_Modle;
import modle.MyHttp.Data;
import modle.MyHttp.Demand_http;
import modle.MyUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取年级 科目信息
 */

public class Getdata {
    private Map<String,Object> map;
    private Retrofit retrofit;
    private Data data;

    //初始化网络访问对象
    public Getdata(){
        map=new ArrayMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data=retrofit.create(Data.class);
    }
    //获取k科目
   public void getGrade(final Requirdetailed requirdetailed){
       Call<Courses_Modle> call=data.getSubject();
       call.enqueue(new Callback<Courses_Modle>() {
           @Override
           public void onResponse(Call<Courses_Modle> call, Response<Courses_Modle> response) {
               String error=response.body().getError();
               if (error.equals("ok")){
                   map = new HashMap<String, Object>();
                   map = response.body().getCourses();
                       Log.e("aa", map.toString() + "6666");
                       requirdetailed.Updatecontent(map);
               }else {

               }
           }

           @Override
           public void onFailure(Call<Courses_Modle> call, Throwable t) {
               Log.e("aa","获取科目异常错误");
           }
       });

   }

    //获取余额
    public void getFee(int uid, final Requirdetailed requirdetailed){
        Call<User_Modle> call=data.getFeea(uid);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map=new HashMap<String, Object>();
                    map=response.body().getContent();
                    Log.e("aa","map"+map.toString());
                    requirdetailed.Updatecontent(map);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {

            }
        });
    }
    //获取现金卷
    public void getmianfofee(int uid, final Requirdetailed requirdetailed){
        final Call<User_Modle> call=data.getminfofee(uid);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map=new HashMap<String, Object>();
                    map=response.body().getContent();
                    requirdetailed.Updatecontent(map);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取现金卷错误"+errmsg);

                }
            }
            @Override
            public void onFailure(Call<User_Modle> call, Throwable t){
                Log.e("aa","获取现金卷异常错误错误");
            }
        });
    }


    //获取一级分销，二级分销
    public void getinfo(int uid, int level, final Requirdetailed requirdetailed){
      Call<User_Modle> call=data.getiniffenx(uid,level);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map=response.body().getContent();
                    requirdetailed.Updatecontent(map);

                }else {
                    String errmog=response.body().getErrmsg();
                    Log.e("aa","获取一级分销，二级分销错误"+errmog);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取一分分销，二级分销异常错误错误");
            }
        });
    }

        //获取订单详细
        public void getzfbordet(int id, int paytype, final Requirdetailed requirdetailed){
            Call<User_Modle> call=data.getordetsiz(id,paytype);

            call.enqueue(new Callback<User_Modle>() {
                @Override
                public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("info",response.body().getContent().toString());
                    requirdetailed.Updatecontent(map);
                }

                @Override
                public void onFailure(Call<User_Modle> call, Throwable t) {
                    Log.e("aa","获取支付宝订单错误");
                }
            });
    }

    //关于我们
    public void getadoutus(final Requirdetailed requirder){
        Call<User_Modle> call=data.getabotuse();
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                Log.e("aa","获取关于我们信息成功"+response.body().getContent());

                Map<String,Object> map=new HashMap<String, Object>();
                map=response.body().getContent();

                requirder.Updatecontent(map);
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取关于我们信息异常错误"+t.toString());
            }
        });

    }

    //获取充值订单
    public void getsizechongzhi(int id, float fee, int paytype, final Requirdetailed requirdetailed){
        Call<User_Modle> call=data.getzisdin(id,fee,paytype);

        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                Log.e("aa","获取支付宝订单成功");
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("info",response.body().getContent().toString());
                requirdetailed.Updatecontent(map);

            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取支付宝订单错误");
            }
        });
    }

    //手机号码获取用户资料
    public void getmobieke(String tel, final Requirdetailed requirdetailed){
        Call<User_Modle> call=data.getmobe(tel);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                     Map<String,Object> map=new HashMap<String, Object>();
                    map=response.body().getContent();
                    Log.e("aa","获取个人资料成功"+map.get("id"));
                    requirdetailed.Updatecontent(map);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
            Log.e("aa","获取个人资料异常错误");
            }
        });

    }

}
