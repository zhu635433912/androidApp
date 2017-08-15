package view.login.Modle;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import modle.JieYse.User_Modle;
import modle.MyHttp.MyOkhttp;
import modle.MyUrl;
import modle.user_ziliao.User_id;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import view.login.ViewActivity.Dei_init;
import view.login.ViewActivity.Pos_inint;
import view.login.ViewActivity.wan_inint;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;


/**
 * Created by Administrator on 2017/3/7 0007.
 */

public class S_wan_login implements Modle_wan_login {
    private JSONObject jsonb;//json数据
    private String username;
    private String password;

    private URL url;
    private HttpURLConnection httpconn;
    private login_wan_presenter swan;
    private wan_inint wanview;
    private Modle_user user;
    private Dei_init dwan;
    private String category;
    private String SMSma;
    private int role=0;
    private Pos_inint pos;

    public S_wan_login(Pos_inint pos){this.pos=pos;}
    public S_wan_login(wan_inint wanview){
    this.wanview=wanview;
    }
    public S_wan_login(Dei_init dwan){this.dwan=dwan;}
    //登陆
    @Override
    public void getlogin(Modle_user user) {
        this.user=user;
        username=user.getUsername();
        password=user.getPassword();
        new MyAysnta().execute(Backj_URL.USER_URL);
    }
    //发送验证
    @Override
    public void SMSlogin(Modle_user user, String lei){
        Log.d("aa","往"+user.getUsername()+"验证类型"+lei);
        this.user=user;
        username=user.getUsername();
        new MySMS().execute(Backj_URL.SMS_URL,lei,username);
    }
    //注册,修改
    @Override
    public void Userregistration(String category, String username, String passowrd,String ftype,String yqm) {
        String url;
        if (!category.equals("z")){
            if (category.equals("学生")){
                role=1;
                Log.d("aa","学生用户");
            }else {
                role=2;
                Log.d("aa","教师用户");
            }
            this.category=category;
            url= Backj_URL.REGISTER_URL;
            Log.d("aa",username+"注册用户"+"验证码为"+ftype);
        }else {
            Log.d("aa",username+"修改密码:"+"验证码为"+ftype);
            url= Backj_URL.CZ_URL;
        }
        this.username=username;
        this.password=passowrd;
       new Myregister().execute(url,ftype,yqm);
    }


    class MyAysnta extends AsyncTask<String,Void,Void>{
        private Map<String,Object> map;
        private Retrofit  retrofit;
        private MyOkhttp myOkhttp;
     private int user_id;
    private String error;
     private String errmsg;
        @Override
        protected Void doInBackground(String... params) {
            swan=new S_wan_presenter(wanview);
            Log.d("aa","异步线程：逻辑启动接受到username=="+username+"!   password===="+password);
            if (jsonb==null){
                jsonb=new JSONObject();
            }
            try {
                jsonb.put("username",username);
                jsonb.put("password",password);
                Log.d("aa","异步线程：逻辑：用户转换成json=="+jsonb.toString());
                String jsonString=jsonb.toString();
                url=new URL(params[0]);
                httpconn= (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("POST");
                OutputStream out=httpconn.getOutputStream();
                out.write(jsonString.getBytes());
                BufferedReader reader=new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                StringBuffer sbf=new StringBuffer();
                String st;
                while ((st=reader.readLine())!=null){
                    sbf.append(st);
                }
                JSONObject jas=new JSONObject(sbf.toString());
                Log.d("aa","异步线程逻辑：服务器返回的josn="+sbf);
                error=jas.getString("error");
                if (error.equals("no")) {
                    errmsg = jas.getString("errmsg");
                }else {
                    User_id.setUsername(username);
                    User_id.setPassword(password);
                    //判断用户是否为角色
                    user_id=jas.getInt("user_id");
                    Log.e("aa","用户id为"+user_id);
                    retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    myOkhttp=retrofit.create(MyOkhttp.class);
                    Call<User_Modle> call=myOkhttp.getuserziliao(user_id,"","");
                    call.enqueue(new Callback<User_Modle>() {
                        @Override
                        public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                            String error=response.body().getError();
                            if (error.equals("ok")) {
                                map = response.body().getContent();
                                Log.e("aa","用户id为"+map.get("id")+"用角色为"+ map.get("role")+map.get("has_paypassword")+"");
                                map.put("username",username);
                                map.put("password",password);
                                swan.loginchenggong(map);
                            }else {
                                String errmsg=response.body().getErrmsg();
                                Log.e("aa","获取用户资料错误="+errmsg);
                            }
                        }

                        @Override
                        public void onFailure(Call<User_Modle> call, Throwable t) {
                            Log.e("aa","获取用户资料异常错误="+t.toString());
                        }
                    });
                }
                reader.close();
                out.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (error.equals("no")){
                //失败回调
                swan.loginshiba(errmsg);
            }
        }
    }
    //短信验证
    class MySMS extends AsyncTask<String,Void,Void>{
        private String error;
        private String errmsg;
        @Override
        protected Void doInBackground(String... params) {
            if (dwan!=null) {
                swan = new S_wan_presenter(dwan);
            }else {
                swan=new S_wan_presenter(pos);
            }
           /*  try {  //自动发送验证逻辑 不需后台
           OkHttpClient http=new OkHttpClient();
            Request.Builder request=new Request.Builder();
            String url="http://api.smsbao.com/sms?u=deguan&p=afdd0b4ad2ec172c586e2150770fbf9e&m="+params[0]+"&c=(学了吗)注册验证码_"+params[1]+"请在十分钟之内注册";
            Request re=request.get().url(url).build();
            Call call=http.newCall(re);
                call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            jsonb=new JSONObject();
            try {
                Log.d("aa",params[2]);
                jsonb.put("mobile",params[2]);
                jsonb.put("ftype",params[1]);
                url=new URL(params[0]);
                httpconn= (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("POST");
                String ajs=jsonb.toString();
                OutputStream out=httpconn.getOutputStream();
                out.write(ajs.getBytes());
                BufferedReader reader=new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                StringBuffer sbf=new StringBuffer();
                String st;
                while ((st=reader.readLine())!=null){
                    sbf.append(st);
                }
                JSONObject jsono=new JSONObject(sbf.toString());
                 error=jsono.getString("error");
                if (error.equals("no")){
                    errmsg=jsono.getString("errmsg");
                }
                Log.d("aa'", "短信发送"+error);
                out.close();
                reader.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dwan!=null) {
                if (error.equals("ok")) {
                    Log.d("aa'", "短信发送成功");
                    swan.SMStrue(user);
                } else {
                    Log.d("aa", "发送失败失败原因" + errmsg + "");
                    swan.SMSfalse(errmsg);
                }
            }else {
                if (error.equals("ok")) {
                    Log.d("aa'", "短信发送成功");
                    swan.xiugaitflase("发送成功");
                } else {
                    Log.d("aa", "发送失败失败原因" + errmsg + "");
                    swan.xiugaitflase(errmsg);
                }
            }

        }
    }
    class Myregister extends AsyncTask<String,Void,Void>{
        private String error;
        private String errmsg;
        private int user_id;
        private Retrofit  retrofit;
        private MyOkhttp myOkhttp;
        private int i=0;
        private Map<String,Object> map;
        @Override
        protected Void doInBackground(String... params) {
            if (dwan!=null) {
                i=1;
                swan = new S_wan_presenter(dwan);
            }else {
                i=2;
                swan=new S_wan_presenter(pos);
            }
            jsonb=new JSONObject();
            try {
                jsonb.put("yzm",params[1]);
                if (role!=0){
                    jsonb.put("role",role);
                }
                jsonb.put("username",username);
                jsonb.put("password",password);
                jsonb.put("inv_code",params[2]);
//                jsonb.put("pay_password",params[3]);
                url=new URL(params[0]);
                httpconn= (HttpURLConnection) url.openConnection();
                httpconn.setRequestMethod("POST");
                String ajs=jsonb.toString();
                OutputStream out=httpconn.getOutputStream();
                out.write(ajs.getBytes());
                BufferedReader reader=new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
                StringBuffer sbf=new StringBuffer();
                String st;
                while ((st=reader.readLine())!=null){
                    sbf.append(st);
                }
                JSONObject jsono=new JSONObject(sbf.toString());
                error=jsono.getString("error");
                Log.d("aa","服务器返回"+error);
                if (error.equals("no")){
                    errmsg=jsono.getString("errmsg");
                    Log.d("aa","错误信息"+errmsg);
                }else {
                    Log.e("aa","注册成功开始注环信账号");
                    //注册环信账号
                    //注册失败会抛出HyphenateException
                    if (i == 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EMClient.getInstance().createAccount(username,
    //                            password
                                            "123456"
                                    );//同步方法
                                } catch (HyphenateException e) {
                                    e.printStackTrace();
                                }
                                Log.e("aa", "环信注册成功");
                            }
                        }).start();

                    }
                    User_id.setUsername(username);
                    User_id.setPassword(password);
                    user_id=jsono.getInt("user_id");
                    Log.e("aa","用户id为"+user_id);

                    retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    myOkhttp=retrofit.create(MyOkhttp.class);
                    Call<User_Modle> call=myOkhttp.getuserziliao(user_id,"","");
                    call.enqueue(new Callback<User_Modle>() {
                        @Override
                        public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                            String error=response.body().getError();
                            if (error.equals("ok")) {
                                map = response.body().getContent();
                                Log.e("aa","用户id为"+map.get("id")+"用角色为"+ map.get("role"));
                                if (i==1){
                                    //成功回调
                                    swan.Registereduserture(map);
                                }else {
                                    //成功回调
                                    swan.xiugaiture(map);
                                }
                                Log.e("aa","环信以及用户账号也注册成功");
                            }else {
                                String errmsg=response.body().getErrmsg();
                                Log.e("aa","注册获取用户资料失败"+errmsg);
                            }
                        }

                        @Override
                        public void onFailure(Call<User_Modle> call, Throwable t) {
                            Log.e("aa","获取用户资料异常错误="+t.toString());
                        }
                    });
                }


                reader.close();
                out.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dwan!=null) {
                if (error.equals("no")) {
                    //失败回调
                    swan.Registereduserflase(errmsg);
                }
            }else {
                if (error.equals("no")) {
                    //失败回调
                    swan.xiugaitflase(errmsg);
                }
            }
        }
    }

}
