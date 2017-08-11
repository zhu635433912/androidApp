package modle.user_Modle;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.ArrayMap;
import android.util.Log;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.hyphenate.util.FileUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.JieYse.Demtest;
import modle.JieYse.UploadEntity;
import modle.JieYse.User_Modle;
import modle.MyHttp.MyOkhttp;
import modle.MyUrl;
import modle.user_ziliao.User_id;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.http.Multipart;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 用户接口实现类
 */

public class User_Realization implements User_init {
    private  Map<String,Object> map;
    private Retrofit  retrofit;
    private MyOkhttp myOkhttp;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpg");

    //初始化网络访问对象
    public User_Realization(){
        map=new HashMap<String,Object>();
        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myOkhttp=retrofit.create(MyOkhttp.class);
    }

    /*
    注册
     */
    @Override
    public Map<String, Object> RegisteredUser( int role, String username, String password, String yzm){
        Call<Demtest> call=myOkhttp.getzhuce(role,username,password,yzm);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
            String error=response.body().getError();
            if (error.equals("ok")){
                String user_id=response.body().getUser_id();
                Log.e("aa", "注册成功返回用户id为=" + user_id);
            }else {
                String errmsg=response.body().getErrmsg();
                Log.e("aa","注册错误="+errmsg);
            }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","注册异常错误="+t.toString());
            }
        });
        return map;
    }

    /*
    登陆
     */
    @Override
    public Map<String, Object> Registerlogin( String username, String password) {
        Call<Demtest> call=myOkhttp.getDemtest(username,password);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    String user_id=response.body().getUser_id();
                    Log.e("aa", "登陆成功返回用户id为=" + user_id);
                    map.put("user_id",user_id);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","登陆错误="+errmsg);
                    map.put("errmsg",errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","登陆异常错误="+t.toString());
            }
        });
        return map;
    }

    /*
    修改密码
     */
    @Override
    public Map<String, Object> Modifypassword(int user_id, String Jiupassword, String password) {
        Call<Demtest> call=myOkhttp.getxiugaimima(user_id,Jiupassword,password);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "修改密码成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","修改错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","修改异常错误="+t.toString());
            }
        });
        return map;
    }

    /*
    重置密码
     */
    @Override
    public Map<String, Object> Resetpassword(String username, String password, String yzm) {
        Call<Demtest> call=myOkhttp.getchongzhi(username,password,yzm);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    String user_id=response.body().getUser_id();
                    Log.e("aa", "重置密码成功返回用户id为=" + user_id);
                    map.put("user_id",user_id);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","重置密码错误="+errmsg);
                    map.put("errmsg",errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","重置密码异常错误="+t.toString());
            }
        });
        return map;
    }


    /*
    获取用户资料
     */
    @Override
    public Map<String, Object> User_Data(int user_id,String lat,String lng, final Requirdetailed requirdetailed) {
        Call<User_Modle> call=myOkhttp.getuserziliao(user_id,lat,lng);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    map = response.body().getContent();
                   if (requirdetailed!=null){
                       requirdetailed.Updatecontent(map);
                   }
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取用户资料错误="+errmsg);
                }
              }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","根性用户资料异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    更新用户资料
     */
    @Override
    public Map<String, Object> Updatedata(int user_id, Map<String, Object>listmap) {
        Call<Demtest> call=myOkhttp.setuserziliao(user_id,listmap);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    更新真实姓名
     */
    @Override
    public void Updatenickname(int user_id, String nickname) {
        Call<Demtest> call=myOkhttp.setnickname(user_id,nickname);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });

    }
    //上传身份证id
    public void UpdateIdcard(int user_id,String idCard){
        Call<Demtest> call=myOkhttp.setIdcard(user_id,idCard);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });

    }

    @Override
    public void Updateheadimg(int user_id, String headimg) {
            Call<Demtest> call=myOkhttp.setheadimg(user_id,headimg);
            call.enqueue(new Callback<Demtest>() {
                @Override
                public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                    String error=response.body().getError();
                    if (error.equals("ok")){
                        Log.e("aa","上传图片地址成功");
                    }else {
                    Log.e("aa","上传图片失败"+response.body().getErrmsg());
                    }
                }

                @Override
                public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","上传图片异常失败"+t.toString());
                }
            });
    }

    @Override
    public void Updatename(int user_id, String name) {
        Call<Demtest> call=myOkhttp.setname(user_id,name);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }

    @Override
    public void Updategender(int user_id, String gender) {
        Call<Demtest> call=myOkhttp.setgender(user_id,gender);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }

    @Override
    public void Upsignature(int user_id, String signature) {
        Call<Demtest> call=myOkhttp.setsignature(user_id,signature);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }

    @Override
    public void Updateage(int user_id, String age) {
        Call<Demtest> call=myOkhttp.setage(user_id,age);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");

                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }

    @Override
    public void Updateaddress(int user_id, String address) {
        Call<Demtest> call=myOkhttp.setaddress(user_id,address);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }
    //更新经纬度
    @Override
    public void setlan_lng(int uid, double lat, double lng) {
        Call<Demtest> call=myOkhttp.setlat_lng(uid,lat,lng);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料位置更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户位置资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户位置资料异常错误="+t.toString());
            }
        });
    }

    public void setAddress(int uid,String province,String city,String status){
        Call<Demtest> call=myOkhttp.setAddress(uid,province,city,status);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料位置更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户位置资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户位置资料异常错误="+t.toString());
            }
        });
    }

    @Override
    public void UpdateEducation(int user_id, int education_id) {
        Call<Demtest> call=myOkhttp.setEducation(user_id,education_id);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "用户资料更新成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新用户资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","获取用户资料异常错误="+t.toString());
            }
        });
    }

    //上传图片
    @Override
    public void setuserbitmap(File file, final Student_init student_init, final ChangeOrderView changeOrderView) {
        Bitmap bitmap=compressImageFromFile(file.getPath());
        File f = new File("/sdcard/namecard/");
        if (f.exists()) {
            f.delete();
        }
        //空
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
            //取出压缩后图片
            FileInputStream fis=new FileInputStream(f);
            Bitmap bit = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        RequestBody reboy=RequestBody.create(MediaType.parse("multipart/form-data"),f);
       Call<UploadEntity> call=myOkhttp.uploadImage(reboy);
        call.enqueue(new Callback<UploadEntity>() {
            @Override
            public void onResponse(Call<UploadEntity> call, Response<UploadEntity> response) {
                String  error=response.body().getError();
                if (error.equals("yes")){
                    Log.e("aa","上传图片成功"+response.body().getContent());
                    String hemaign=response.body().getContent();
                    String uid= User_id.getUid();
                    int id=Integer.parseInt(uid);
                    if (student_init==null) {
                        //更新用户头像
                        Updateheadimg(id, hemaign);
                        changeOrderView.successOrder(hemaign);
                    }else {
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("imageurl",hemaign);
                        map.put("uid",uid);
                        List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
                        listmap.add(map);
                        student_init.setListview1(listmap);
                    }
                }else {

                 Log.e("aa","上传图片错误"+response.body().getErrmsg());

                }
            }
            @Override
            public void onFailure(Call<UploadEntity> call, Throwable t) {
                Log.e("aa","上传图片异常错误"+t.toString());
            }
        });
    }


    private Bitmap compressImageFromFile(String srcPath) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 900f;//
        float ww = 720f;//
        int be = 1;
        if (w > h && w > ww) {
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
//      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;
    }
}
