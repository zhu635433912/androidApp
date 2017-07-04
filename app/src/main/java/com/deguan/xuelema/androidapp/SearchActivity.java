package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.TeacherEntity;
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

import modle.Adapter.TeacherListAdapter;
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_search)
public class SearchActivity extends MyBaseActivity implements SimilarXuqiuView, TeacherView {
    @ViewById
    EditText text_search;

    @ViewById(R.id.naxte)
    ImageView naxte;
    @ViewById(R.id.search_back)
    RelativeLayout backRl;

    @ViewById
    ListView searlist;

    private int role;
    private Demand_init demand_init;

    private List<XuqiuEntity> listamap;
    private XuqiuAdapter xuqiuAdapter;
    //教师列表数据
    private List<TeacherEntity> teachers = new ArrayList<>();
    private TeacherListAdapter teacherAdapter;
    private Teacher_init teacher_init;

    @Override
    public void before() {
    }

    @Override
    public void initView() {
        role=Integer.parseInt(User_id.getRole());
        demand_init=new Demand(this);
        teacher_init = new Teacher(this);
        listamap = new ArrayList<>();
        if (User_id.getRole().equals("1")) {
            teacherAdapter = new TeacherListAdapter(teachers,this);
            searlist.setAdapter(teacherAdapter);
        }else {
            xuqiuAdapter = new XuqiuAdapter(listamap, this);
            searlist.setAdapter(xuqiuAdapter);
        }
//        simpleAdapter=new SimpleAdapter(this,listamap,R.layout.listview_itme,new String[]{"publisher_name"},new int[]{R.id.text1});
//        searlist.setAdapter(simpleAdapter);
        searlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (role==1) {
                    //进入老师
//                    String uid = requirdapter.geiuiserid(position-1);
                    Intent intent = new Intent(SearchActivity.this, UserxinxiActivty.class);
//                    intent.putExtra("user_id", uid);
                    intent.putExtra("head_image",teachers.get(position-1).getUser_headimg());
                    intent.putExtra("user_id",teachers.get(position-1).getUser_id());
                    startActivity(intent);
                }else {
                    //进入学生

//                    Map<String,Object> map=new HashMap<String, Object>();
//                    map=studentAdapter.geiuiserid(position-1);

                    Intent intent = new Intent(SearchActivity.this, Xuqiuxiangx.class);
                    intent.putExtra("user_id",listamap.get(position-1).getId());
                    intent.putExtra("fee",listamap.get(position-1).getFee());
                    intent.putExtra("publisher_id",listamap.get(position-1).getPublisher_id());
                    intent.putExtra("course_id",listamap.get(position-1).getCourse_id());
                    intent.putExtra("grade_id",listamap.get(position-1).getGrade_id());
//                    intent.putExtra("user_id",(String)map.get("user_id"));
//                    intent.putExtra("fee",(String)map.get("fee"));
//                    intent.putExtra("publisher_id",(String)map.get("publisher_id"));
                    startActivity(intent);
                }
            }
        });
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
//        listamap.clear();
//        listamap.addAll(maps);
//        xuqiuAdapter.notifyDataSetChanged();
//        SimpleAdapter simpleAdapter=new SimpleAdapter(this,listamap,R.layout.listview_itme,new String[]{"publisher_name"},new int[]{R.id.text1});
//        searlist.setAdapter(simpleAdapter);
        if (maps==null){
            Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
        }
        listamap.clear();
        List<XuqiuEntity> lists = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            XuqiuEntity entity = new XuqiuEntity();
            entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
            entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setCourse_name((String) maps.get(i).get("course_name"));
            entity.setContent((String) maps.get(i).get("content"));
            entity.setCreated((String) maps.get(i).get("created"));
            entity.setId((String) maps.get(i).get("id"));
            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setGrade_name((String)maps.get(i).get("grade_name"));
            entity.setCourse_id((String)maps.get(i).get("course_id"));
            entity.setGrade_id((String)maps.get(i).get("grade_id"));
            entity.setAddress((String)maps.get(i).get("address"));

            lists.add(entity);
        }
        listamap.addAll(lists);
        xuqiuAdapter.notifyDataSetChanged();
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successTeacher(List<Map<String, Object>> maps) {
        if (maps==null){
            Toast.makeText(this, "无搜索结果", Toast.LENGTH_SHORT).show();
        }
            teachers.clear();
        if (maps.size() != 0) {
            List<TeacherEntity> lists = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                TeacherEntity entity = new TeacherEntity();
                entity.setNickname((String) maps.get(i).get("nickname"));
                entity.setSpeciality((String)maps.get(i).get("speciality"));
                entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
                entity.setSignature((String) maps.get(i).get("signature"));
                entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
                entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
                entity.setUser_id((String) maps.get(i).get("user_id"));
                entity.setGender((String) maps.get(i).get("gender"));
                entity.setHaoping_num((String)maps.get(i).get("haoping_num"));
//            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
                entity.setDistance((String) maps.get(i).get("distance"));
                entity.setFee(String.valueOf(maps.get(i).get("fee")));
                List<Map<String,Object>> listmap = ((List<Map<String,Object>>)maps.get(i).get("information_temp"));
                String course_name = "";
//                for (int j = 0; j < listmap.size(); j++) {
//                    course_name = course_name + listmap.get(j).get("course_name")+"  ";
//                }
                entity.setStatus2(course_name);
//            entity.setGrade_name((String)maps.get(i).get("grade_name"));
                lists.add(entity);
            }
            teachers.addAll(lists);
            teacherAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failTeacher(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
