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
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.MyPopwindow;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.CourseAdapter;
import modle.Adapter.NewDemandAdapter;
import modle.Adapter.TextAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_demand)
public class  DemandActivity extends MyBaseActivity implements View.OnClickListener, XuqiuView,Student_init, NewDemandAdapter.OnTopClickListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewById(R.id.search_edit)
    EditText searchEidt;
    @ViewById(R.id.search_image)
    ImageView searchImage;
    @ViewById(R.id.choose_ll)
    LinearLayout chooseLl;
    @ViewById(R.id.demand_back)
    RelativeLayout backRl;
    @ViewById(R.id.teacher_recycler)
    RecyclerView recyclerView;
    @ViewById(R.id.teacher_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.demand_choose_tv)
    TextView chooseTv;
    @ViewById(R.id.demand_choose_grade)
    TextView gradeTv;
    @ViewById(R.id.demand_choose_course)
    TextView genderTv;

    private NewDemandAdapter demandAdapter;
    private Demand_init demand;
    private int page = 1;
    private int id;
    //需求列表数据源
    private List<XuqiuEntity> datas = new ArrayList<>();
    private boolean isLoading = false;
    private int gradeId = 0;
    private int subjectId = 0;
    private int flagnumber = 0;
    private MyPopwindow choosePop,gradePop,genderPop;
    private String order = "0";
    private int genderId = 0;

    private ListView list1;
    private ListView list2;
    private MyPopwindow coursePop;
    private CourseAdapter courseAdapter,gradesAdapter;
    private TextAdapter textAdapter,gradeAdapter;
    private List<String> menus = new ArrayList<>();
    private List<SubjectEntity> courses = new ArrayList<>();
    private List<String> grade = new ArrayList<>();
    private List<SubjectEntity> grades = new ArrayList<>();


    @Override
    public void before() {
        super.before();
        id=Integer.parseInt(User_id.getUid());
        gradeId = getIntent().getIntExtra("gradeId",0);
        subjectId = getIntent().getIntExtra("subjectId",0);
    }

    @Override
    public void initData() {
        if (subjectId == 1){
            chooseTv.setText("离我最近");
            order = "2";
            subjectId = 0;
        }
        gradeTv.setOnClickListener(this);
        demandAdapter = new NewDemandAdapter(datas,this);
        demandAdapter.setOnTopClickListener(this);
        recyclerView.setAdapter(demandAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        demand = new Demand(this);
        page = 1;
        demand.getDemand_list(id,
                Integer.parseInt(User_id.getRole()), 0, genderId,
                "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(),
                null, null, DemandActivity.this, gradeId, subjectId, order, ""
        );
    }

    @Override
    public void initView() {
        searchImage.setOnClickListener(this);
        chooseTv.setOnClickListener(this);
        backRl.setOnClickListener(this);
        genderTv.setOnClickListener(this);
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
                            demand.getDemand_list(id,
                                    Integer.parseInt(User_id.getRole()), 0, genderId,
                                    "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(),
                                    null, null, DemandActivity.this, gradeId, subjectId, order, ""
                            );

                        }
                    }
                }
            }
        });
        showChoosePop();
        showGradePop();
        showGenderPop();
        initcityPop();
        searchEidt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!TextUtils.isEmpty(searchEidt.getText().toString())) {
                        startActivity(SearchActivity_.intent(DemandActivity.this).extra("search", searchEidt.getText().toString()).get());
                    }else {
                        Toast.makeText(DemandActivity.this, "请输入搜索条件", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
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
        //防止虚拟软键盘被弹出菜单遮住
        coursePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        coursePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width);
        coursePop.setHeight(height);
        coursePop.setOutsideTouchable(true);
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
                page = 1;
                subjectId = Integer.parseInt(courses.get(position).getSubjectId());

                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursePop.dismiss();
            }
        });

    }

    private void showGenderPop() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.teacher_gender_pop,null);
        TextView buxianTv = (TextView) view.findViewById(R.id.grade0);
        TextView zongheTv = (TextView) view.findViewById(R.id.grade1);
        TextView distanceTv = (TextView) view.findViewById(R.id.grade2);

        genderPop = new MyPopwindow(view);
        genderPop.setFocusable(true);
        genderPop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        genderPop.setWidth(width);
        genderPop.setHeight(height);
        genderPop.setOutsideTouchable(true);
        //防止虚拟软键盘被弹出菜单遮住
        genderPop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        buxianTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderTv.setText("性别不限");
                genderPop.dismiss();
                genderId = 0;
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        zongheTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderTv.setText("男");
                genderPop.dismiss();
                genderId = 1;
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        distanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderTv.setText("女");
                genderPop.dismiss();
                genderId = 2;
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });

    }


    private void showGradePop() {

        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.choose_grade_pop,null);
        list1 = (ListView) view.findViewById(R.id.course_list1);
        list2 = (ListView) view.findViewById(R.id.course_list2);
        View cancelView = view.findViewById(R.id.pop_cancel);

        grade.addAll(SubjectUtil.getGrade());
        grades.addAll(SubjectUtil.getGrades().get(0));

        gradesAdapter = new CourseAdapter(grades,this);
        gradeAdapter = new TextAdapter(grade,this);

        list1.setAdapter(gradeAdapter);
        list2.setAdapter(gradesAdapter);
        gradePop = new MyPopwindow(view);
        gradePop.setFocusable(true);
        //防止虚拟软键盘被弹出菜单遮住
        gradePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        gradePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        gradePop.setWidth(width);
        gradePop.setHeight(height);
        gradePop.setOutsideTouchable(true);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                page = 1;
                grades.clear();
                grades.addAll(SubjectUtil.getGrades().get(position));
                gradesAdapter.notifyDataSetChanged();
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                page = 1;
                gradeTv.setText("        "+grades.get(position).getSubjectName());
                gradePop.dismiss();
                gradeId = Integer.parseInt(grades.get(position).getSubjectId());
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursePop.dismiss();
            }
        });

    }

    private void showChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.demand_choose_pop,null);
        TextView zongheTv = (TextView) view.findViewById(R.id.choose_zonghe);
        TextView distanceTv = (TextView) view.findViewById(R.id.choose_distance);
        TextView timeTv = (TextView) view.findViewById(R.id.choose_time);
        TextView gradeTv = (TextView) view.findViewById(R.id.choose_grade);
        TextView maleTv = (TextView) view.findViewById(R.id.choose_gender_male);
        TextView femaleTv = (TextView) view.findViewById(R.id.choose_gender_female);
        View cancelView = view.findViewById(R.id.pop_cancel);
        choosePop = new MyPopwindow(view);
        choosePop.setFocusable(true);
        choosePop.setBackgroundDrawable(new BitmapDrawable());
        //防止虚拟软键盘被弹出菜单遮住
        choosePop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        choosePop.setWidth(width);
        choosePop.setHeight(height);
        choosePop.setOutsideTouchable(true);
        maleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                chooseTv.setText("选择 男");
                choosePop.dismiss();
                genderId = 1;
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        femaleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                chooseTv.setText("选择 女");
                choosePop.dismiss();
                genderId = 2;
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        zongheTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = "1";
                chooseTv.setText("综合排序");
                choosePop.dismiss();
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        distanceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = "2";
                chooseTv.setText("离我最近");
                choosePop.dismiss();
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = "3";
                chooseTv.setText("发布时间");
                choosePop.dismiss();
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });
        gradeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page = 1;
                genderId = 0;
                order = "4";
                chooseTv.setText("最热需求");
                choosePop.dismiss();
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, DemandActivity.this
                        ,gradeId,subjectId,order,"");
            }
        });

        cancelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePop.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_image:
                if (!TextUtils.isEmpty(searchEidt.getText().toString())) {
                    startActivity(SearchActivity_.intent(DemandActivity.this).extra("search", searchEidt.getText().toString()).get());
                }else {
                    Toast.makeText(DemandActivity.this, "请输入搜索条件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.demand_choose_course:
                coursePop.showAsDropDown(genderTv);
                textAdapter.notifyDataSetChanged();
                courseAdapter.notifyDataSetChanged();
                break;
            case R.id.demand_back:
                finish();
                break;
            case R.id.demand_choose_tv:
                choosePop.showAsDropDown(chooseLl);
                break;
            case R.id.demand_choose_grade:
                gradePop.showAsDropDown(gradeTv);
                break;
        }
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }
    @Override
    public void onTopClick(XuqiuEntity entity) {
        startActivity(DemandDetailActivity_.intent(this)
                .extra("user_id",entity.getId())
                .extra("fee",entity.getFee())
                .extra("publisher_id",entity.getPublisher_id())
                .extra("course_id",entity.getCourse_id())
                .extra("grade_id",entity.getGrade_id())
                .get());
        //进入学生
//        Intent intent = new Intent(this, Xuqiuxiangx.class);
//        intent.putExtra("user_id",entity.getId());
//        intent.putExtra("fee",entity.getFee());
//        intent.putExtra("publisher_id",entity.getPublisher_id());
//        intent.putExtra("course_id",entity.getCourse_id());
//        intent.putExtra("grade_id",entity.getGrade_id());
//        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, genderId, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this
                ,gradeId,subjectId,"created","");

//        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), 0, "", genderId, subjectId, gradeId, order_rank, NewTeacherFragment.this, page);
    }
    @Override
    public void successXuqiu(List<Map<String, Object>> maps) {
        coursePop.dismiss();
        if (maps != null) {
            if (page == 1) {
                demandAdapter.clear();
                datas.clear();
                if (maps.size()==0){
                    Toast.makeText(this, "暂无此需求", Toast.LENGTH_SHORT).show();
                }
            }
            List<XuqiuEntity> lists = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                XuqiuEntity entity = new XuqiuEntity();
                entity.setVersion(maps.get(i).get("teacher_version")+"");
                entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
                entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
                entity.setService_type((String)maps.get(i).get("service_type"));
                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
                entity.setCourse_name((String) maps.get(i).get("course_name"));
                entity.setContent((String) maps.get(i).get("content"));
                entity.setCreated((String) maps.get(i).get("created"));
                entity.setId((String) maps.get(i).get("id"));
                entity.setCourse_id((String)maps.get(i).get("course_id"));
                entity.setGrade_id((String)maps.get(i).get("grade_id"));
                entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
                entity.setDistance((String) maps.get(i).get("distance"));
                entity.setFee(maps.get(i).get("low_price")+"--"+maps.get(i).get("high_price")+"元");
                entity.setGrade_name((String)maps.get(i).get("grade_name"));
                if ((maps.get(i).get("address")+"").length()>7) {
                    entity.setAddress(((String) maps.get(i).get("address")).substring(0, 7) + "......");
                }else {
                    entity.setAddress((String) maps.get(i).get("address"));
                }
                if ((maps.get(i).get("status")).equals("1")||maps.get(i).get("status").equals("2")){
                    continue;
                }
                lists.add(entity);
            }
            flagnumber = lists.size();
//            DbUtil.getSession().getXuqiuEntityDao().insertOrReplaceInTx(lists);
            datas.addAll(lists);
            isLoading = false;
            demandAdapter.addAll(lists);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failXuqiu(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
