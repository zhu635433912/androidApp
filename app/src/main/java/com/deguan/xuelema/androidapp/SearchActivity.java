package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.DmadAdapter;
import modle.Adapter.TeacherListAdapter;
import modle.Adapter.TuijianNewAdapter;
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_search)
public class SearchActivity extends MyBaseActivity implements SimilarXuqiuView, TeacherView, SwipeRefreshLayout.OnRefreshListener {
    @ViewById
    EditText text_search;

    @ViewById(R.id.naxte)
    ImageView naxte;
    @ViewById(R.id.search_back)
    RelativeLayout backRl;

    @ViewById(R.id.search_swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.searlist)
    RecyclerView searlist;

    private List<Map<String,Object>> list=new ArrayList<>();
    private List<TuijianEntity> datas = new ArrayList<>();

    private int role;
    private Demand_init demand_init;

    private List<XuqiuEntity> listamap;
    private DmadAdapter xuqiuAdapter;
    //教师列表数据
    private List<TeacherEntity> teachers = new ArrayList<>();
    private TuijianNewAdapter teacherAdapter;
    private Teacher_init teacher_init;
    private String search;

    @Override
    public void before() {
        super.before();
        search = getIntent().getStringExtra("search");
    }

    @Override
    public void initView() {
        User_id.getInstance().addActivity(this);
        role=Integer.parseInt(User_id.getRole());
        demand_init=new Demand(this);
        teacher_init = new Teacher(this);
        listamap = new ArrayList<>();
        swipeRefreshLayout.setOnRefreshListener(this);

        if (User_id.getRole().equals("1")) {
            teacherAdapter = new TuijianNewAdapter(datas,this);
            searlist.setAdapter(teacherAdapter);
            teacherAdapter.setOnTopClickListener(new TuijianNewAdapter.OnTopClickListener() {
                @Override
                public void onTopClick(TuijianEntity entity) {
                    startActivity(NewTeacherPersonActivity_.intent(SearchActivity.this)
                            .extra("head_image",entity.getUser_headimg())
                            .extra("user_id",entity.getUser_id()).get());
//                    Intent intent = new Intent(SearchActivity.this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                    intent.putExtra("head_image",entity.getUser_headimg());
//                    intent.putExtra("user_id",entity.getUser_id());
//                    startActivity(intent);
                }
            });
        }else {
            xuqiuAdapter = new DmadAdapter(this, list);
            searlist.setAdapter(xuqiuAdapter);
            xuqiuAdapter.setOnItemClickListener(new DmadAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String publisher_id = list.get(position).get("publisher_id") + "";
                    String user_id = list.get(position).get("id") + "";
                    String fee = list.get(position).get("fee") + "";
//                Toast.makeText(getActivity(),publisher_id,Toast.LENGTH_LONG).show();
//                    Intent intent = new Intent(SearchActivity.this, Xuqiuxiangx.class);
//                    intent.putExtra("publisher_id", publisher_id);
//                    intent.putExtra("user_id", user_id);
//                    intent.putExtra("fee", fee);
//                    intent.putExtra("course_id", list.get(position).get("course_id").toString());
//                    intent.putExtra("grade_id", list.get(position).get("grade_id").toString());
//                    startActivity(intent);

                    startActivity(DemandDetailActivity_.intent(SearchActivity.this)
                            .extra("user_id",user_id)
                            .extra("fee",fee)
                            .extra("publisher_id",publisher_id)
                            .extra("course_id",list.get(position).get("course_id").toString())
                            .extra("grade_id",list.get(position).get("grade_id").toString())
                            .get());

                }
            });
        }
        if (role == 1) {
            teacher_init.gettuijian_Teacher1(search,User_id.getLat()+"",""+User_id.getLng());
        } else {
            demand_init.getTuijianDemand_list1(search,User_id.getLat()+"",""+User_id.getLng());
        }
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        naxte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String aa = text_search.getText().toString();
                if (!aa.equals("")) {
                    if (role == 1) {
                        teacher_init.gettuijian_Teacher1(aa,User_id.getLat()+"",""+User_id.getLng());
                    } else {
                        demand_init.getTuijianDemand_list1(aa,User_id.getLat()+"",""+User_id.getLng());
                    }
                }else {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        if (maps.size() == 0){
            Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
        }
        list.clear();
        for (int i = 0; i < maps.size(); i++) {
            if ((maps.get(i).get("status")).equals("1")||maps.get(i).get("status").equals("2")){
                maps.remove(i);
            }
        }

        list.addAll(maps);
        xuqiuAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg+"!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successTeacher(List<Map<String, Object>> maps) {
        if (maps.size() == 0){
            Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
        }
        teacherAdapter.clear();
            datas.clear();
        if (maps.size() != 0) {
            for (int i = 0; i < maps.size(); i++) {
            TuijianEntity entity = new TuijianEntity();
            entity.setNickname((String) maps.get(i).get("nickname"));
            entity.setSpeciality((String)maps.get(i).get("speciality"));
            entity.setSpeciality_name(maps.get(i).get("city")+"  "+maps.get(i).get("state"));
//            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setSignature((String) maps.get(i).get("resume"));
            entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
            entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
            entity.setUser_id((String) maps.get(i).get("user_id"));
            entity.setGender((String) maps.get(i).get("gender"));
            entity.setClick(maps.get(i).get("click")+"");
//            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setHaoping_num((String)maps.get(i).get("haoping_num"));
            List<Map<String,Object>> listmap = ((List<Map<String,Object>>)maps.get(i).get("information_temp"));
            String course_name = "";
            for (int j = 0; j < listmap.size(); j++) {
                if (listmap.get(j).get("grade_id").equals("1")){
                    course_name = course_name + listmap.get(j).get("course_name")+"(小学)"+"  ";
                }else if (listmap.get(j).get("grade_id").equals("2")){
                    course_name = course_name + listmap.get(j).get("course_name")+"(初中)"+"  ";
                }else if (listmap.get(j).get("grade_id").equals("3")){
                    course_name = course_name + listmap.get(j).get("course_name")+"(高中)"+"  ";
                }else if (listmap.get(j).get("grade_id").equals("4")){
                    course_name = course_name + listmap.get(j).get("course_name")+"(大学)"+"  ";
                }else {
                    course_name = course_name + listmap.get(j).get("course_name")+"(不限)"+"  ";
                }
            }
            entity.setStatus2(course_name);
//            entity.setGrade_name((String)maps.get(i).get("grade_name"));

            datas.add(entity);
        }
        teacherAdapter.addAll(datas);
        swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void failTeacher(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        String aa = text_search.getText().toString();
        if (!aa.equals("")) {
            if (role == 1) {
                teacher_init.gettuijian_Teacher1(aa,User_id.getLat()+"",""+User_id.getLng());
            } else {
                demand_init.getTuijianDemand_list1(aa,User_id.getLat()+"",""+User_id.getLng());
            }
        }else {
            Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }
    }
}
