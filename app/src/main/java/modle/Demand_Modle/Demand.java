package modle.Demand_Modle;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import control.Myconteol_init;
import control.Mycontrol;
import modle.JieYse.ContentModle;
import modle.JieYse.Demtest;
import modle.JieYse.User_Modle;
import modle.MyHttp.Demand_http;
import modle.MyUrl;
import modle.toos.MyListview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 需求接口实现类
 */

public class Demand implements Demand_init {
    private Map<String,Object> map;
    private Retrofit retrofit;
    private Demand_http demand_http;
    private XuqiuView xuqiuView;

    //初始化网络访问对象
    public Demand(){
        map=new HashMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        demand_http=retrofit.create(Demand_http.class);
    }
    //初始化网络访问对象
    public Demand(XuqiuView xuqiuView){
        this.xuqiuView = xuqiuView;
        map=new HashMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        demand_http=retrofit.create(Demand_http.class);
    }
    /*
    获取需求列表
     */
    @Override
    public List<Map<String, Object>> getDemand_list(int uid, final int role, int filter_type, int filter_id, final String start_time, int end_time, int page, final PullToRefreshListView listView, final Context context, final Student_init student_init) {
        Call<ContentModle> call=demand_http.getDemandlist(uid,filter_type,filter_id,start_time,end_time,page);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String errpr=response.body().getError();
                if (errpr.equals("ok")) {
                    List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                    listmap = response.body().getContent();
                    Log.e("aa", "listmap=" + listmap);
//                    if (student_init == null) {
//                        //回调设置listview
//                        Myconteol_init myconteol_init = new Mycontrol();
//                        myconteol_init.huidiao(listmap, role, listView, context);
//                    }else {
//                        student_init.setListview1(listmap);
//                    }
                    xuqiuView.successXuqiu(listmap);

                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取需求列表失败错误="+errmsg);
                    xuqiuView.failXuqiu(errmsg);
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取需求列表异常错误"+t.toString());
                xuqiuView.failXuqiu("网络错误");
            }
        });

        return null;
    }

    /*
    获取单一需求
     */
    @Override
    public Map<String, Object> getDemand_danyi(int uid, int id, final Xuqiuxiangx_init xuqiuxiangx_init) {
        Call<User_Modle> call=demand_http.getDemanddanyilist(uid,id);
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                String errpr=response.body().getError();
                if (errpr.equals("ok")){
                    map=response.body().getContent();
                    Log.e("aa","获取单一需求列表成功="+map.get("publisher_id")+map.get("publisher_name")+map.get("id")+map.get("content")+map.get("service_type")+
                            map.get("grade_id")+map.get("lng")+map.get("lat"));
                    xuqiuxiangx_init.Updatecontent(map);
                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取单一需求列表失败错误="+errmsg);
                }
            }
            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                Log.e("aa","获取单一需求列表异常错误"+t.toString());
            }
        });
        return null;
    }
     /*
       发布需求
       */
    @Override
    public Map<String, Object> ReleaseDemand(int id, String content, float fee, int grade_id, int course_id, int gender, int age, int education_id,
                                             String province, String cty, String state, int serice_type,String start,String ent,double lat,double lng) {
     Call<Demtest> call=demand_http.setDemand(id,content,fee,grade_id,course_id,gender,age,education_id,province,cty,state,serice_type,start,ent,lat,lng);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String errpr = response.body().getError();
                if (errpr.equals("ok")) {
                    Log.e("aa", "发布需求成功");
                } else {
                    String errmsg = response.body().getErrmsg();
                    Log.e("aa", "发布需求错误=" + errmsg);
                }
            }
            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {
                Log.e("aa", "发布需求异常错误=" + t.toString());
            }
            });
        return null;
    }

    /*
    更新需求
     */
    @Override
    public Map<String, Object> Update_Demand(int uid, String content, float fee, int grade_id, int course_id, int gender, int education_id, String province, String cty, String state, int service_type, Map<String, Object> items) {
        Call<Demtest> call=demand_http.Update_Demand(uid,content,fee,grade_id,course_id,gender,education_id,province,cty,state,service_type,items);

        return null;
    }

    /*
    删除需求
     */
    @Override
    public Map<String, Object> Delete_Demand(int uid, int id) {
        Call<Demtest> call=demand_http.Delete_Demand(uid,id);
        call.enqueue(new Callback<Demtest>() {
            @Override
            public void onResponse(Call<Demtest> call, Response<Demtest> response) {
                String error=response.body().getError();
                if (error.equals("ok")) {
                    Log.e("aa", "删除需求成功");
                } else {
                    String errmsg = response.body().getErrmsg();
                    Log.e("aa", "删除需求失败=" + errmsg);
                }
            }

            @Override
            public void onFailure(Call<Demtest> call, Throwable t) {

            }
        });

        return null;
    }

    @Override
    public Map<String, Object> JiaDelete_Demand(int uid, int id) {
        return null;
    }

    @Override
    public void getMyDemand_list(int publisher_iint,int filter_type, final Student_init student_init) {
        Call<ContentModle> call=demand_http.getMyDemandlist(publisher_iint,filter_type);
        call.enqueue(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String errpr=response.body().getError();
                if (errpr.equals("ok")) {
                    List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
                    listmap = response.body().getContent();
                    Log.e("aa", "listmap=" + listmap);
                    student_init.setListview1(listmap);

                }else {
                    String errmsg=response.body().getErrmsg();
                    Log.e("aa","获取需求列表失败错误="+errmsg);
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                Log.e("aa","获取需求列表异常错误"+t.toString());
            }
        });
    }


}
