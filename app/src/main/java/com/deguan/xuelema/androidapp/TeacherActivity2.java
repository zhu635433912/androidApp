package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.SubjectEntity;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.MyPopwindow;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.CourseAdapter;
import modle.Adapter.NewDemandAdapter;
import modle.Adapter.NewTeacherAdapter;
import modle.Adapter.TextAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_teacher2)
public class TeacherActivity2 extends MyBaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, NewTeacherAdapter.OnTopClickListener, TeacherView, Requirdetailed {
    @ViewById(R.id.search_edit)
    EditText searchEidt;
    @ViewById(R.id.search_image)
    ImageView searchImage;
    @ViewById(R.id.choose_ll)
    LinearLayout chooseLl;
    @ViewById(R.id.teacher_back)
    RelativeLayout backRl;
    @ViewById(R.id.teacher_recycler)
    RecyclerView recyclerView;
    @ViewById(R.id.teacher_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.teacher_choose_tv)
    TextView chooseTv;
    @ViewById(R.id.teacher_choose_grade)
    TextView gradeTv;
    @ViewById(R.id.teacher_choose_course)
    TextView genderTv;

    private NewTeacherAdapter teacherAdapter;
    private Teacher_init teacher;
    private int page = 1;
    private int id;
    //需求列表数据源
    private List<TeacherEntity> teachers = new ArrayList<>();
    private boolean isLoading = false;
    private int gradeId = 0;
    private int subjectId = 0;
    private int flagnumber = 0;
    private MyPopwindow choosePop,gradePop,genderPop;
    private int order = 0;
    private int genderId = 0;
    private int order_rank = 3;

    private ListView list1;
    private ListView list2;
    private MyPopwindow coursePop;
    private CourseAdapter courseAdapter;
    private TextAdapter textAdapter;
    private List<String> menus = new ArrayList<>();
    private List<SubjectEntity> courses = new ArrayList<>();



    @Override
    public void before() {
        super.before();
        id=Integer.parseInt(User_id.getUid());
        gradeId = getIntent().getIntExtra("gradeId",0);
        subjectId = getIntent().getIntExtra("subjectId",0);
        order = getIntent().getIntExtra("order",0);
    }

    @Override
    public void initData() {
        searchImage.setOnClickListener(this);
        genderTv.setOnClickListener(this);
        gradeTv.setOnClickListener(this);

        teacherAdapter = new NewTeacherAdapter(teachers,this);
        teacherAdapter.setOnTopClickListener(this);
        recyclerView.setAdapter(teacherAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        teacher = new Teacher(this);
        if (subjectId == 1){
            order = 4;
            chooseTv.setText("离我最近");
            subjectId = 0;
        }
        page = 1;
            teacher.Get_Teacher_list(id,
                    Integer.parseInt(User_id.getRole()),
                    User_id.getLat() + "", "" + User_id.getLng(),
                    recyclerView, this, order, "",
                    genderId, 0, gradeId,
                    order_rank, this, page, subjectId);
    }

    @Override
    public void initView() {
        chooseTv.setOnClickListener(this);
        backRl.setOnClickListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading){
                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int position = recyclerView.getChildAdapterPosition(childAt);
                    if ( flagnumber >= 19 ) {
                        if (adapter1.getItemCount() - position < 5) {
                            isLoading = true;
                            page++;
                            teacher.Get_Teacher_list(id,
                                    Integer.parseInt(User_id.getRole()),
                                    User_id.getLat() + "", "" + User_id.getLng(),
                                    recyclerView, TeacherActivity2.this, order, "",
                                    genderId, 0, gradeId,
                                    order_rank, TeacherActivity2.this, page,subjectId);

                        }
                    }
                }
            }
        });
        showChoosePop();
        showGradePop();
        initcityPop();
        searchEidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!TextUtils.isEmpty(searchEidt.getText().toString())) {
                        startActivity(SearchActivity_.intent(TeacherActivity2.this).extra("search", searchEidt.getText().toString()).get());
                    }else {
                        Toast.makeText(TeacherActivity2.this, "请输入搜索条件", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

    }

//    private void showGenderPop() {
//        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.teacher_gender_pop,null);
//        TextView buxianTv = (TextView) view.findViewById(R.id.grade0);
//        TextView zongheTv = (TextView) view.findViewById(R.id.grade1);
//        TextView distanceTv = (TextView) view.findViewById(R.id.grade2);
//        View cancelView = view.findViewById(R.id.pop_cancel);
//        genderPop = new PopupWindow(view);
//        genderPop.setFocusable(true);
//        genderPop.setBackgroundDrawable(new BitmapDrawable());
//        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        int width = wm.getDefaultDisplay().getWidth();
//        genderPop.setWidth(width);
//        genderPop.setHeight(height);
//        genderPop.setOutsideTouchable(true);
//        cancelView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                genderPop.dismiss();
//            }
//        });
//        buxianTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                genderTv.setText("不限");
//                genderPop.dismiss();
//                genderId = 0;
//                teacher.Get_Teacher_list(id,
//                        Integer.parseInt(User_id.getRole()),
//                        User_id.getLat() + "", "" + User_id.getLng(),
//                        recyclerView, TeacherActivity2.this, order, "",
//                        genderId, 0, gradeId,
//                        order_rank, TeacherActivity2.this, page,subjectId);
//            }
//        });
//        zongheTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                genderTv.setText("男");
//                genderPop.dismiss();
//                genderId = 1;
//                teacher.Get_Teacher_list(id,
//                        Integer.parseInt(User_id.getRole()),
//                        User_id.getLat() + "", "" + User_id.getLng(),
//                        recyclerView, TeacherActivity2.this, order, "",
//                        genderId, 0, gradeId,
//                        order_rank, TeacherActivity2.this, page,subjectId);
//            }
//        });
//        distanceTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                genderTv.setText("女");
//                genderPop.dismiss();
//                genderId = 2;
//                teacher.Get_Teacher_list(id,
//                        Integer.parseInt(User_id.getRole()),
//                        User_id.getLat() + "", "" + User_id.getLng(),
//                        recyclerView, TeacherActivity2.this, order, "",
//                        genderId, 0, gradeId,
//                        order_rank, TeacherActivity2.this, page,subjectId);
//            }
//        });
//
//        }



    private void showGradePop() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.teacher_grade_pop,null);
        TextView buxianTv = (TextView) view.findViewById(R.id.grade0);
        TextView zongheTv = (TextView) view.findViewById(R.id.grade1);
        TextView distanceTv = (TextView) view.findViewById(R.id.grade2);
        TextView timeTv = (TextView) view.findViewById(R.id.grade3);
        final TextView grade1Tv = (TextView) view.findViewById(R.id.grade4);
        View cancelView = view.findViewById(R.id.pop_cancel);
        gradePop = new MyPopwindow(view);
        gradePop.setFocusable(true);
        gradePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        gradePop.setWidth(width);
        gradePop.setHeight(height);
        gradePop.setOutsideTouchable(true);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradePop.dismiss();
            }
        });
        buxianTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                gradeTv.setText("不限");
                gradePop.dismiss();
                gradeId = 0;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        zongheTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                gradeTv.setText("小学");
                gradePop.dismiss();
                gradeId = 1;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        distanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                gradeTv.setText("初中");
                gradePop.dismiss();
                gradeId = 2;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                gradeTv.setText("高中");
                gradePop.dismiss();
                gradeId = 3;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        grade1Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                gradeTv.setText("大学");
                gradePop.dismiss();
                gradeId = 4;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });

    }
    private void initcityPop() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.choose_course_pop,null);
        list1 = (ListView) view.findViewById(R.id.course_list1);
        list2 = (ListView) view.findViewById(R.id.course_list2);
        View cancelView = view.findViewById(R.id.pop_cancel);

        menus.addAll(SubjectUtil.getMenu());
        courses.addAll(SubjectUtil.getChildList().get(0));
        courseAdapter = new CourseAdapter(courses,this);
        textAdapter = new TextAdapter(menus,this);

        list1.setAdapter(textAdapter);
        list2.setAdapter(courseAdapter);
        coursePop = new MyPopwindow(view);
        coursePop.setFocusable(true);
        coursePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width);
        coursePop.setHeight(height);
        coursePop.setOutsideTouchable(true);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursePop.dismiss();
            }
        });
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                courses.clear();
                courses.addAll(SubjectUtil.getChildList().get(position));
                courseAdapter.notifyDataSetChanged();
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                subjectId = Integer.parseInt(courses.get(position).getSubjectId());
//                coursePop.dismiss();
                page = 1;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);


            }
        });


    }
    private void showChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.teacher_choose__pop,null);
        TextView zongheTv = (TextView) view.findViewById(R.id.choose_zonghe);
        TextView distanceTv = (TextView) view.findViewById(R.id.choose_distance);
        TextView timeTv = (TextView) view.findViewById(R.id.choose_time);
        TextView gradeTv = (TextView) view.findViewById(R.id.choose_grade);
        TextView renqiTv = (TextView) view.findViewById(R.id.choose_renqi);

        TextView maleTv = (TextView) view.findViewById(R.id.choose_gender_male);
        TextView femaleTv = (TextView) view.findViewById(R.id.choose_gender_female);
        View cancelView = view.findViewById(R.id.pop_cancel);
        choosePop = new MyPopwindow(view);
        choosePop.setFocusable(true);
        choosePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        choosePop.setWidth(width);
        choosePop.setHeight(height);
        choosePop.setOutsideTouchable(true);
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePop.dismiss();
            }
        });

        maleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                chooseTv.setText("选择 男");
                choosePop.dismiss();
                genderId = 1;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        femaleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                chooseTv.setText("选择 女");
                choosePop.dismiss();
                genderId = 2;
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });

        zongheTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = 0;
                chooseTv.setText("综合排序");
                choosePop.dismiss();
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        distanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = 1;
                chooseTv.setText("教师星级");
                choosePop.dismiss();
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = 2;
                chooseTv.setText("价格从低到高");
                choosePop.dismiss();
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        gradeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = 3;
                chooseTv.setText("人气教师");
                choosePop.dismiss();
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
        renqiTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = 4;
                chooseTv.setText("离我最近");
                choosePop.dismiss();
                teacher.Get_Teacher_list(id,
                        Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(),
                        recyclerView, TeacherActivity2.this, order, "",
                        genderId, 0, gradeId,
                        order_rank, TeacherActivity2.this, page,subjectId);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_image:
                if (!TextUtils.isEmpty(searchEidt.getText().toString())) {
                    startActivity(SearchActivity_.intent(TeacherActivity2.this).extra("search", searchEidt.getText().toString()).get());
                }else {
                    Toast.makeText(TeacherActivity2.this, "请输入搜索条件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.teacher_back:
                finish();
                break;
            case R.id.teacher_choose_tv:
                choosePop.showAsDropDown(chooseLl);

                break;
            case R.id.teacher_choose_grade:
                gradePop.showAsDropDown(gradeTv);
                break;
            case R.id.teacher_choose_course:
                coursePop.showAsDropDown(genderTv);
                textAdapter.notifyDataSetChanged();
                courseAdapter.notifyDataSetChanged();
                break;
        }
    }



    @Override
    public void onRefresh() {
        page = 1;
        teacher.Get_Teacher_list(id,
                Integer.parseInt(User_id.getRole()),
                User_id.getLat() + "", "" + User_id.getLng(),
                recyclerView, TeacherActivity2.this, order, "",
                genderId, 0, gradeId,
                order_rank, TeacherActivity2.this, page,subjectId);

//        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), 0, "", genderId, subjectId, gradeId, order_rank, NewTeacherFragment.this, page);
    }

    @Override
    public void onTopClick(TeacherEntity entity) {
        //进入老师
        startActivity(NewTeacherPersonActivity_.intent(this).
                extra("myid","0")
                .extra("head_image",entity.getUser_headimg())
                .extra("user_id",entity.getUser_id()).get());
//                    String uid = requirdapter.geiuiserid(position-1);
//        Intent intent = new Intent(this, UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//        intent.putExtra("head_image",entity.getUser_headimg());
//        intent.putExtra("user_id",entity.getUser_id());
//        intent.putExtra("myid","0");
//        startActivity(intent);
    }

    @Override
    public void successTeacher(List<Map<String, Object>> maps) {
        coursePop.dismiss();
        if (maps != null){
            if (page == 1){
                teacherAdapter.clear();
                teachers.clear();
            }
            List<TeacherEntity> lists = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                TeacherEntity entity = new TeacherEntity();
                entity.setNickname((String) maps.get(i).get("nickname"));
                entity.setSpeciality((String)maps.get(i).get("speciality"));
                entity.setSpeciality_name(maps.get(i).get("city")+"  "+maps.get(i).get("state"));
//                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));

                entity.setClick(maps.get(i).get("click")+"");
                entity.setSignature((String) maps.get(i).get("resume"));
                entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
                entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
                entity.setUser_id((String) maps.get(i).get("user_id"));
                entity.setGender((String) maps.get(i).get("gender"));
//            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
                entity.setDistance((String) maps.get(i).get("distance"));
                entity.setFee(String.valueOf(maps.get(i).get("fee")));
                entity.setHaoping_num((String)maps.get(i).get("haoping_num"));
                List<Map<String,Object>> listmap = ((List<Map<String,Object>>)maps.get(i).get("information_temp"));
                if (listmap .size()>0){
                    entity.setService_type_txt(
//                            (String) listmap.get(0).get("course_remark")
                            "");
                }else {
                    entity.setService_type_txt("");
                }
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
                entity.setEducation((String)maps.get(i).get("education"));
//            entity.setGrade_name((String)maps.get(i).get("grade_name"));
                lists.add(entity);
            }
            flagnumber = lists.size();
//            DbUtil.getSession().getTeacherEntityDao().insertOrReplaceInTx(lists);
            teachers.addAll(lists);
            isLoading = false;
            teacherAdapter.addAll(lists);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failTeacher(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}