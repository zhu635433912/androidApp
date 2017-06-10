package modle;

import android.util.ArrayMap;
import android.util.Log;

import java.util.Map;

import modle.JieYse.Demtest;
import modle.MyHttp.MyOkhttp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 短信，审核，举报实现类
 */

public class Onteh implements Onteh_init {
    private  Map<String,Object> map;
    private Retrofit retrofit;
    private MyOkhttp myOkhttp;

    //初始化网络访问对象
    public Onteh(){
        map=new ArrayMap<String,Object>();
        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myOkhttp=retrofit.create(MyOkhttp.class);
    }
    @Override
    public Map<String, Object> getyzm(final String mobile, String lei) {
        Call<Demtest> call=myOkhttp.getyzm(mobile,lei);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","成功短信发送到"+mobile);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","短信发送失败"+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","短信异常错误="+t.toString());
            }
        });
        return null;
    }

    @Override
    public Map<String, Object> Toexamine(String url, String user_id) {
        return null;
    }

    /*
    提交举报
     */
    @Override
    public Map<String, Object> Report(int user_id, String content) {
        Call<Demtest> call=myOkhttp.ssetjubao(user_id,content);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","举报成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","举报失败"+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","举报异常错误="+t.toString());
            }
        });
        return null;
    }


    @Override
    public void setuserfeedback(int uid, String content) {
        Call<Demtest> call=myOkhttp.setfeedback(uid,content);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                    String error=response.body().getError();
                    if (error.equals("yse")){
                        Log.e("aa","反馈信息成功");
                    }else {
                        Log.e("aa","反馈信息失败"+response.body().getErrmsg());
                    }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","提交反馈信息异常失败"+t);
            }
        });


    }


}
