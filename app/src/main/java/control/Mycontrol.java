package control;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.IndexAdapter;
import modle.Adapter.Requirdapter;
import modle.Adapter.StudentAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.JieYse.Dtu_Modle.ProvinceAreaHelper;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.toos.MyListview;
import modle.user_ziliao.User_id;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 控制器1
 */

public class Mycontrol implements Myconteol_init  {
    Teacher_init teacher_init;
    List<Map<String, Object>> listmap;
    String[] jz;
    XuqiuView xuqiuView;
    TeacherView teacherView;

    public Mycontrol() {

    }

    public Mycontrol( TeacherView teacherView) {
        this.teacherView = teacherView;
    }
    //获取教师列表
    @Override
    public void setlist_a(final int uid, final int role, final String lat, final String ing, final RecyclerView listView, final Context context, int order, String state
            , int gender, int speciality, int grade_type, int order_rank, int page) {
        Log.e("aa","用户身份为"+role);
        if (role==1) {
            //判断用户是否为学生 为学生展示老师信息
            teacher_init = new Teacher(teacherView);
            listmap = teacher_init.Get_Teacher_list(uid,role, lat, ing, listView, context,order,state,gender,speciality,grade_type,order_rank,null,page,0);

        }else {
            //判断用户是否为老师，为老师展示学生信息
//            Demand_init demand_init=new Demand(xuqiuView);
//            demand_init.getDemand_list(uid,role,0,0,"2016-08-10",0,page,listView,context,null);
        }
    }

    //教师列表回调
    public void huidiao(final List<Map<String, Object>> listmap, final int role, final PullToRefreshListView listView, final Context context){
        this.listmap=listmap;
        Observable.create(new Observable.OnSubscribe<List<Map<String, Object>>>() {
            @Override
            public void call(Subscriber<? super List<Map<String, Object>>> subscriber) {
                subscriber.onNext(listmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Map<String, Object>>>(){
                    @Override
                    public void call(List<Map<String, Object>> maps) {
                        if (role==1){
                            Requirdapter listAdapter=new Requirdapter(listmap,context);
                            listView.setAdapter(listAdapter);
                            //自定义适配器
                            listView.onRefreshComplete();
//                            listView.reflashComplet();
                        }else {
                            //SimpleAdapter simpleAdapter = new SimpleAdapter(context, maps, R.layout.listview_itme, new String[]{"publisher_id","fee","content","course_name","service_type"}, new int[]{R.id.text1,R.id.text3, R.id.text6, R.id.text4, R.id.text9});

                            StudentAdapter simpleAdapte=new StudentAdapter(listmap,context);
                            listView.setAdapter(simpleAdapte);
//                            listView.reflashComplet();
                            listView.onRefreshComplete();
                        }


                    }
                });
    }

    //获取地区listview
    @Override
    public void setlist_d(final String shen, final ListView listView, final Context context) {
        Observable.create(new Observable.OnSubscribe<List<Map<String,Object>>>() {
            @Override
            public void call(Subscriber<? super List<Map<String, Object>>> subscriber) {

                ProvinceAreaHelper provinceAreaHelper=new ProvinceAreaHelper(context);
                provinceAreaHelper.initProvinceData();
                Map<String,String[]> map=provinceAreaHelper.getsheng();
                jz=map.get(shen);
                listmap=new ArrayList<Map<String,Object>>();
                for (int i=0;i<jz.length;i++){
                    Map<String,Object> map1=new HashMap<String,Object>();
                    map1.put("text",jz[i]);
                    listmap.add(map1);
                }
                subscriber.onNext(listmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Map<String, Object>>>() {
            @Override
            public void call(List<Map<String, Object>> maps) {

                SimpleAdapter simpleAdapter=new SimpleAdapter(context,listmap,R.layout.text_itme,new String[]{"text"},new int[]{R.id.textsuan});
                listView.setAdapter(simpleAdapter);
            }
        });



    }

    //获取市
    @Override
    public void setlist_dqiandao(final String shi, final ListView listView, final Context context) {
        Observable.create(new Observable.OnSubscribe<List<Map<String,Object>>>() {
            @Override
            public void call(Subscriber<? super List<Map<String, Object>>> subscriber) {
                ProvinceAreaHelper provinceAreaHelper=new ProvinceAreaHelper(context);
                provinceAreaHelper.initProvinceData();
                Map<String,String[]> map=provinceAreaHelper.getshi();
                jz=map.get(shi);
                listmap=new ArrayList<Map<String,Object>>();
                for (int i=0;i<jz.length;i++){
                    Map<String,Object> map1=new HashMap<String,Object>();
                    map1.put("diqu",jz[i]);
                    listmap.add(map1);
                }
                subscriber.onNext(listmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Map<String, Object>>>() {
                    @Override
                    public void call(List<Map<String, Object>> maps) {
                        SimpleAdapter simpleAdapter=new SimpleAdapter(context,listmap,R.layout.text_itme,new String[]{"diqu"},new int[]{R.id.textsuan});
                        listView.setAdapter(simpleAdapter);
                    }
                });
    }

    //获取订单listview
    @Override
    public void getorderconlist(int uid, int tiaojian, int startu, int page,final MyListview listView, final Context context) {

        Order_init order_init=new Order();
        order_init.getOrder_list(uid,tiaojian,startu,page,listView,context,null,0,3);

    }
    //订单回调
    @Override
    public void getorder_huidiao(final List<Map<String, Object>> listmap, final MyListview listView, final Context context) {
        this.listmap=listmap;
        Observable.create(new Observable.OnSubscribe<List<Map<String, Object>>>() {
            @Override
            public void call(Subscriber<? super List<Map<String, Object>>> subscriber) {
                subscriber.onNext(listmap);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Map<String, Object>>>(){
                    @Override
                    public void call(List<Map<String, Object>> maps) {
                            Log.e("aa","主线接收到的参数是"+maps);
                            if (User_id.getRole().equals("2")) {
                               // SimpleAdapter simpleAdapter = new SimpleAdapter(context, maps, R.layout.mydindan_list, new String[]{"id", "requirement_grade", "fee", "created","requirement_course"}, new int[]{R.id.dindanbianhao, R.id.dindannairon, R.id.feejiner, R.id.xiadansjtext,R.id.kemu});
                                IndexAdapter indexAdapter=new IndexAdapter(maps,context);
                                listView.setAdapter(indexAdapter);
                                listView.reflashComplet();
                            } else {
                                SimpleAdapter simpleAdapter = new SimpleAdapter(context, maps, R.layout.myindex_list, new String[]{"teacher_id"}, new int[]{R.id.myindeusername});
                                listView.setAdapter(simpleAdapter);
                                listView.reflashComplet();
                            }
                    }
                });
    }

//983
}
