package modle.getdata;

import android.util.ArrayMap;
import android.util.Log;

import com.deguan.xuelema.androidapp.entities.DownloadEntity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.deguan.xuelema.androidapp.viewimpl.CashListView;
import com.deguan.xuelema.androidapp.viewimpl.CashView;
import com.deguan.xuelema.androidapp.viewimpl.DownloadView;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.deguan.xuelema.androidapp.viewimpl.UpReportView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        map=new HashMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data=retrofit.create(Data.class);
    }

    public void getDownloadUrl(final DownloadView downloadView){
        Call<DownloadEntity> call = data.getDownload("andriod");
        call.enqueue(new Callback<DownloadEntity>() {
            @Override
            public void onResponse(Call<DownloadEntity> call, Response<DownloadEntity> response) {
                downloadView.successDownload(response.body());
            }

            @Override
            public void onFailure(Call<DownloadEntity> call, Throwable t) {

            }
        });
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
        public void getzfbordet(int id, final int paytype,int reward_fee , final Requirdetailed requirdetailed){
            Call<User_Modle> call=data.getordetsiz(id,paytype,reward_fee);

            call.enqueue(new Callback<User_Modle>() {
                @Override
                public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                    Map<String,Object> maps=new HashMap<String, Object>();
                    if (paytype == 1) {
                        maps = response.body().getContent();
                    }else if (paytype == 0){
                        maps.put("info", response.body().getContent().toString());
                    }
                    requirdetailed.Updatecontent(maps);
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
    public void getsizechongzhi(int id, float fee, final int paytype, final Requirdetailed requirdetailed){
        Call<User_Modle> call=data.getzisdin(id,fee,paytype);

        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                Log.e("aa","获取支付宝订单成功");
                Map<String,Object> maps=new HashMap<String, Object>();
                if (paytype == 2) {
                    maps = response.body().getContent();
                }else if (paytype == 1){
                    maps.put("info", response.body().getContent().toString());
                }
                requirdetailed.Updatecontent(maps);
//                Map<String,Object> map=new HashMap<String, Object>();
//                map.put("info",response.body().getContent().toString());
//                requirdetailed.Updatecontent(map);

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
                    Log.e("aa",map.get("mobile")+"获取个人资料成功"+map.get("id"));
                    requirdetailed.Updatecontent(map);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
            Log.e("aa","获取个人资料异常错误");
            }
        });

    }
    //提现
    public void getCash(int uid , String bank_name, String back_number, int type, float fee, final CashView cashView){
        Call<User_Modle> call = data.getcash(uid,bank_name,back_number,type,fee);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Map<String,Object> map=new HashMap<String, Object>();
//                    map=response.body().getContent();
//                    Log.e("aa",map.get("mobile")+"获取个人资料成功"+map.get("id"));
//                    requirdetailed.Updatecontent(map);
                    cashView.successCash(response.body().getErrmsg());
                }else {
                    cashView.failCash(response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                cashView.failCash("网络错误");
            }
        });
    }

    public void getCashList(int uid, int page, final CashListView cashListView ){
        Call<ContentModle> call = data.getcashList(uid,page);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    List<Map<String,Object>> map=new ArrayList<Map<String, Object>>();
                    map=response.body().getContent();
                    Log.e("aa","获取个人资料成功");
                    cashListView.successCashList(map);
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取个人资料异常错误");
                cashListView.failCashList();
            }
        });
    }
    public void upReport(int uid, String content, final UpReportView upReportView){
        Call<User_Modle> call  = data.upReport(uid,content);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取个人资料成功");
                    upReportView.successUpReport("举报成功");
                }else {
                    upReportView.successUpReport("举报失败");
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                upReportView.failUpReport("网络错误");
            }
        });
    }


    //获取成交率
    public void getdelt_info(int id, final Baseinit baseinit){
        Call<User_Modle> call=data.getdea_info(id);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                if (response.body().getError().equals("ok")){
                    Map<String,Object> map=new HashMap<String, Object>();
                    map=response.body().getContent();
                    baseinit.upcontent(map);
                }else {
                    Log.e("aa","获取成交率失败"+response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取成交率异常错误");
            }
        });
    }


    //获取成交率
    public void getTurnoverData(int teacher_id, int page, final TurnoverView turnoverView){
        Call<ContentModle> call = data.getTurnover(teacher_id,page);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                if (response.body().getError().equals("ok")){
                    turnoverView.successTurnover(response.body().getContent());
                }else {
                    turnoverView.failTurnover("获取失败");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                turnoverView.failTurnover("网络错误");
            }
        });
    }
}
