package modle.Increase_course;

import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Demand_Modle.Demand;
import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import modle.MyHttp.Data;
import modle.MyHttp.Release_course_http;
import modle.MyUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import view.login.presenter.S_wan_presenter;

/**
 * 老师课程s
 */

public class Increase_course {
    private Map<String,Object> map;
    private Retrofit retrofit;
    private Release_course_http releaseCourseHttp;

    //初始化网络访问对象
    public Increase_course(){
        map=new ArrayMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        releaseCourseHttp=retrofit.create(Release_course_http.class);
    }
    //增加课程
    public void Addcourse(int uid,int course_id,String text,int visit_fee,int unvisit_fee){
        Call<Demtest> call=releaseCourseHttp.Addcourse(uid,course_id,text,visit_fee,unvisit_fee);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","增加课程成功");
                }else {
                    Log.e("aa","增加课程失败"+response.body().getErrmsg());
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","增加课程异常错误");
            }
        });
    }

    //查询课程
    public void selecouse(int uid, final Requirdetailed requirdetailed, final Student_init student_init){
        //new MySele().execute();

      Call<ContentModle> call=releaseCourseHttp.Selcecourse(uid);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
                    listmap=response.body().getContent();
                    Log.e("aa", "查询课程成功"+listmap.toString());
                    if (requirdetailed!=null) {
                        requirdetailed.Updatefee(listmap);
                    }
                    if (student_init!=null){
                        student_init.setListview(listmap);
                    }
                }else {
                    Log.e("aa","查询课程失败"+response.body().getErrmsg());
                }

            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","查询课程异常错误");
            }
        });



    }

    //删除课程
    public void Delect(int uid,int course_id){
       Call<Demtest> call=releaseCourseHttp.delect(uid,course_id);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","删除课程成功");
                }else {
                    Log.e("aa","删除课程失败"+response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","删除课程异常错误");
            }
        });
    }


    class MySele extends AsyncTask<String,Void,Void> {
        private String error;
        private String errmsg;
        private JSONObject jsonb;

        @Override
        protected Void doInBackground(String... params) {
            jsonb = new JSONObject();
            try {
                //教师课程查询
                jsonb.put("uid",560);
                //jsonb.put("course_id",303);
                    URL url = new URL("http://deguanjiaoyu.com/index.php?s=/Service/Teacher/query_course");
                HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("POST");
                String ajs = jsonb.toString();
                OutputStream out = httpconn.getOutputStream();
                out.write(ajs.getBytes());
                BufferedReader reader = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                StringBuffer sbf = new StringBuffer();
                String st;
                while ((st = reader.readLine()) != null) {
                    sbf.append(st);
                }

                JSONObject jsono=new JSONObject(sbf.toString());

                Log.e("aa","服务器返回"+jsono.toString());
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
