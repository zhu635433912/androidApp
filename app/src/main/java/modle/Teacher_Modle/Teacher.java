package modle.Teacher_Modle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import control.Myconteol_init;
import control.Mycontrol;
import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import modle.MyHttp.Teacher_http;
import modle.MyUrl;
import modle.toos.MyListview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 教师接口实现类
 */

public class Teacher implements Teacher_init {
    private Map<String, Object> map;
    private Retrofit retrofit;
    private Teacher_http teacher_http;
    private List<Map<String, Object>> listmap;
    private TeacherView teacherView;

    //初始化网络访问对象
    public Teacher() {
        map = new HashMap<String, Object>();
        retrofit = new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        teacher_http = retrofit.create(Teacher_http.class);
    }

    //初始化网络访问对象
    public Teacher(TeacherView teacherView) {
        this.teacherView = teacherView;
        map = new HashMap<String, Object>();
        retrofit = new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        teacher_http = retrofit.create(Teacher_http.class);
    }


    /*
    获取教师个人资料
     */
    @Override
    public Map<String, Object> Get_Teacher1(int uid, final PayView requirdetailed) {
        Call<User_Modle> call = teacher_http.getTeacherziliao(uid);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error = response.body().getError();
                if (error.equals("ok")) {
                    Map<String,Object> maps = response.body().getContent();
                    requirdetailed.successPay(maps);
                } else {
                    String errmsg = response.body().getErrmsg();
                    Log.e("aa", "获取教师个人资料错误=" + errmsg);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa", "获取教师个人资料异常错误=" + t.toString());
            }
        });
        return null;
    }

    /*
    获取教师个人资料
     */
    @Override
    public Map<String, Object> Get_Teacher(int uid, final Requirdetailed requirdetailed) {
       Call<User_Modle> call=teacher_http.getTeacherziliao(uid);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map=response.body().getContent();
                    List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
                    listmap.add(map);
                 requirdetailed.Updatefee(listmap);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取教师个人资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取教师个人资料异常错误="+t.toString());
            }
        });

        return null;
    }


    //获取教师详细资料
    @Override
    public Map<String, Object> getTeacherDetailed(String lat,String lng,int uid, int id, final Requirdetailed requirdetailed, final int ztm,int number) {
        Call<User_Modle> call=teacher_http.getTeacherDetail(lat,lng,uid,id,number);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map = response.body().getContent();
                    Log.e("aa", "获取教师个人信息map" + map.toString());
                    if (ztm!=1) {
                        requirdetailed.Updatecontent(map);
                    }else {
                        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                        listmap.add(map);
                        requirdetailed.Updatefee(listmap);
                    }
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取教师详细资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取教师详细资料异常错误="+t.toString());
            }
        });
        return null;
    }



        /*
       获取教师详细资料  ztm 1启动listview
       */


    @Override
    public Map<String, Object> Get_Teacher_detailed(int uid, int id, final Requirdetailed requirdetailed, final int ztm,int number) {
        Call<User_Modle> call=teacher_http.getTeacherxiangxizl(uid,id,number);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    map = response.body().getContent();
                    Log.e("aa", "获取教师个人信息map" + map.toString());
                    if (ztm!=1) {
                        requirdetailed.Updatecontent(map);
                    }else {
                        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                        listmap.add(map);
                        requirdetailed.Updatefee(listmap);
                    }
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取教师详细资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取教师详细资料异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    获取教师列表
     */
    @Override
    public List<Map<String, Object>> Get_Teacher_list(int uid, final int role, String lat, String lng, final RecyclerView listView, final Context context, int order, String
            state, int gender, int speciality, int grade_type, int order_rank, final Requirdetailed requirdetailed, int page,int course_id) {
        Call<ContentModle> call=teacher_http.getTeacherlist(uid,lat,lng,order,state,gender,speciality,grade_type,order_rank,page,course_id);
        listmap=new ArrayList<Map<String, Object>>();
                        call.enqueue(new Callback<ContentModle>() {
                            @Override
                            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                                String error=response.body().getError();
                                if (error.equals("ok")){
                                    if (response.body().getContent() .size() == 0){
                                        teacherView.failTeacher("无数据");
                                    }
                        listmap = response.body().getContent();
                        teacherView.successTeacher(listmap);

                }else {
//                    String errmsg=response.body().getErrmsg();
//                    Log.e("aa","获取教师列表错误"+errmsg);
                    teacherView.failTeacher("无数据");
                }
            }
            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取教师列表异常错误="+t.toString());
                teacherView.failTeacher("网络错误");
            }
        });
        return null;
    }

    /*
       教师课程封面更新
        */
    @Override
    public Map<String, Object> Teacher_updateSubjectBg(int uid,String others) {
        Call<Demtest> call=teacher_http.getsubjectBackgroud(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
        return null;
    }
    /*
      教师资料更新
       */
    @Override
    public Map<String, Object> Teacher_update4(int uid,String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin4(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
        return null;
    }
    //身份证正面
    @Override
    public void Teacher_update5(int uid, String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin5(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
    }
    //身份证反面
    @Override
    public void Teacher_update6(int uid, String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin6(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
    }

    /*
  教师资料更新
   */
    @Override
    public Map<String, Object> Teacher_update3(int uid,String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin3(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
        return null;
    }
    /*
       教师资料更新
        */
    @Override
    public Map<String, Object> Teacher_update2(int uid,String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin2(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
        return null;
    }
    /*
    教师资料更新
     */
    @Override
    public Map<String, Object> Teacher_update(int uid,String others) {
        Call<Demtest> call=teacher_http.getTeachergenxin(uid,others);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                Log.e("aa","error"+error);
                if (error.equals("ok")){
                    Log.e("aa","更新教师资料成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教师资料错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师资料异常错误="+t.toString());
            }
        });
        return null;
    }

    /*
    教师列表显示状态更改
     */
    @Override
    public Map<String, Object> Teacher_Change(int uid, int status) {
        Call<Demtest> call=teacher_http.getTeacherzhuangtai(uid,status);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","教师列表显示状态更改成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","教师列表显示状态更改错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","教师列表显示状态更改异常错误="+t.toString());
            }
        });
        return null;
    }
    /*
    招聘列表显示状态更改
     */
    @Override
    public Map<String, Object> Teacher_recruit(int uid, int status) {
        Call<Demtest> call=teacher_http.getTeachezhaopzhuangt(uid,status);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","招聘列表显示状态更改成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","招聘列表显示状态更改错误="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","招聘列表显示状态更改异常错误="+t.toString());
            }
        });
        return null;
    }


    //更新服务类型
    @Override
    public Map<String, Object> Teacher_service_type(int uid, String service_type) {
        Call<Demtest> call=teacher_http.getservice_type(uid,service_type);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","教师列表显示状态更改成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","教师列表显示状态更改错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","教师列表显示状态更改异常错误="+t.toString());
            }
        });
        return null;
    }


    //更新教龄
    @Override
    public Map<String, Object> Teacher_years(int uid, int years) {
        Call<Demtest> call=teacher_http.getyears(uid,years);
        call.enqueue(new Callback<Demtest>(){
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教龄成功");
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","更新教龄失败="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教龄异常错误="+t.toString());
            }
        });
        return null;
    }


    //更新教师个人空间
    public void Teacher_exper(int uid,String exper,String exper_url){
        Call<Demtest> call = teacher_http.setExper(uid,exper,exper_url);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教师个人简介成功");
                }else {
                    Log.e("aa","更新教师个人简介失败");
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {

            }
        });

    }

    //更新教师个人简介
    @Override
    public void Teacher_resume(int uid, String resume) {
        Call<Demtest> call=teacher_http.getresume(uid,resume);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教师个人简介成功");
                }else {
                    Log.e("aa","更新教师个人简介失败");
                }

            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师个人简介异常失败"+t.toString());
            }
        });


    }
    //更新教师个人图片
    public void UpdatePic(int uid, String img1,String img2,String img3) {
        Call<Demtest> call=teacher_http.updatePic(uid,img1,img2,img3);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教师个人照片成功");
                }else {
                    Log.e("aa","更新教师个人照片失败");
                }

            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师个人简介异常失败"+t.toString());
            }
        });


    }


    //更新教师特长
    @Override
    public void Teacher_speciality(int uid, String speciality) {
        Call<Demtest> call=teacher_http.getspeciality(uid,speciality);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教师个人特长成功");
                }else {
                    Log.e("aa","更新教师个人特长失败");
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师个人特长异常失败"+t.toString());
            }
        });
    }

    //更新在校时间
    public void TeacherUpdateTime(int uid,String starttime,String endtime){
        Call<Demtest> call=teacher_http.UpdateTime(uid,starttime,endtime);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新在校时间成功");
                }else {
                    Log.e("aa","更新在校时间失败");
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新在校时间异常失败"+t.toString());
            }
        });
    }

    //更新备注
    public void TeacherUpdateRemark(int uid,String remark){
        Call<Demtest> call=teacher_http.UpdateRemark(uid,remark);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新在校时间成功");
                }else {
                    Log.e("aa","更新在校时间失败");
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新在校时间异常失败"+t.toString());
            }
        });
    }

    //更新教师毕业学校
    @Override
    public void Teacher_graduated_school(int uid, String graduated_school) {
        Call<Demtest> call=teacher_http.getgraduated_school(uid,graduated_school);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","更新教师个人毕业学校成功");
                }else {
                    Log.e("aa","更新教师个人毕业学校失败");
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师个人毕业学校异常失败"+t.toString());
            }
        });
    }
    //教师签名
    @Override
    public void Teacher_signature(int uid, String signature) {
        Call<Demtest> call=teacher_http.getsignature(uid,signature);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("no")){
                    Log.e("aa","更新教师个人签名成功");
                }else {
                    Log.e("aa","更新教师个人签名失败");
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa","更新教师个人签名异常失败"+t.toString());
            }
        });
    }

    /*
    获取推荐教师
     */
    @Override
    public void gettuijian_Teacher(int course_id, int grade_id, String address, final Requirdetailed requirdetailed) {
//        Call<ContentModle> call=teacher_http.gettuijianjiaoshi(course_id,grade_id,address);
//        call.enqueue(new Callback<ContentModle>() {
//            @Override
//            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
//                String error=response.body().getError();
//                if (error.equals("ok")){
//                    Log.e("aa","获取推荐教师成功");
//                    List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();
//                    list=response.body().getContent();
//                    requirdetailed.Updatefee(list);
//                }else {
//                    Log.e("aa","获取推荐教师失败");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ContentModle> call, Throwable t) {
//                Log.e("aa","获取推荐教师异常错误");
//            }
//        });
    }

    //获取教师评价接口
    @Override
    public void setEvaluation_Teacher(int uid, final Student_init student_init,int page) {
        Call<ContentModle> call=teacher_http.getEvluation(uid,page);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                if (response.body().getError().equals("ok")){
                    List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
                    listmap.addAll(response.body().getContent());
                    student_init.setListview(listmap);
                }else {
                    Log.e("aa","获取教师列表错误"+response.body().getErrmsg());
                }

            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取教师评价异常错误");
            }
        });
    }

    /*
  获取推荐教师
   */
    @Override
    public void gettuijian_Teacher1(String name,String lat,String lng) {
        Call<ContentModle> call=teacher_http.gettuijianjiaoshi1(name,lat,lng);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取推荐教师成功");
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        list = response.body().getContent();
                        teacherView.successTeacher(list);

                }else {
                    Log.e("aa","获取推荐教师失败");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取推荐教师异常错误");
                teacherView.failTeacher("网络异常");
            }
        });
    }

    //获取教学案例
    public void getExampleList(int id,int page){
        Call<ContentModle> call=teacher_http.getExample(id,page);
        listmap=new ArrayList<Map<String, Object>>();
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    if (response.body().getContent()  == null){
//                        teacherView.failTeacher("无数据");
                    }
                    listmap = response.body().getContent();
                    teacherView.successTeacher(listmap);
                }else {
//                    String errmsg=response.body().getErrmsg();
//                    Log.e("aa","获取教师列表错误"+errmsg);
                    teacherView.failTeacher("无数据");
                }
            }
            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取教师列表异常错误="+t.toString());
                teacherView.failTeacher("网络错误");
            }
        });
    }

}
