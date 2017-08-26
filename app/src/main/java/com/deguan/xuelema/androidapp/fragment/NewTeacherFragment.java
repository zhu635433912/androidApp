package com.deguan.xuelema.androidapp.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SearchActivity_;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.entities.EntityCity;
import com.deguan.xuelema.androidapp.entities.EntityProvince;
import com.deguan.xuelema.androidapp.entities.EntityRegion;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.utils.XmlUits;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Adapter.CityAdapter;
import modle.Adapter.NewDemandAdapter;
import modle.Adapter.NewTeacherAdapter;
import modle.Adapter.ProvinceAdapter;
import modle.Adapter.RegionAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Gaode.Gaode_dinwei;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

import static com.deguan.xuelema.androidapp.R.id.otherTv;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_new_teacher)
public class NewTeacherFragment extends BaseFragment implements Gaodehuidiao_init, View.OnClickListener, XuqiuView, Requirdetailed, NewDemandAdapter.OnTopClickListener, SwipeRefreshLayout.OnRefreshListener, Student_init {

    @ViewById(R.id.teacher_city_btn)
    ImageButton cityBtn;
    @ViewById(R.id.teacher_choose_btn)
    ImageButton chooseBtn;
    @ViewById(R.id.teacher_linear)
    LinearLayout teacherLinear;
    @ViewById(R.id.teacher_recycler)
    RecyclerView recyclerView;
    @ViewById(R.id.teacher_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.teacher_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.teacher_city_tv)
    TextView cityTv;
    @ViewById(R.id.teacher_search_btn)
    ImageButton searchBtn;


    private NewDemandAdapter demandAdapter;
    private List<EntityProvince> provinces = new ArrayList<>();
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private RegionAdapter regionAdapter;
    private List<EntityCity> cities = new ArrayList<>();
    private List<EntityRegion> regions = new ArrayList<>();
    private PopupWindow cityPop,choosePop;
    private Gaode_dinwei gaode_dinwei;
    private Demand_init demand;
    private int id;
    private double lat=131.54915;
    private double lng=24.94213;
    private int page = 1;
    private int latFlag = 1;
    private ListView list1;
    private ListView list2;
    private ListView list3;
    private int genderId = 0;
    private int gradeId = 0;
    private int subjectId = 0;
    private int order_rank = 0;
    private TextView subjectotherTv;
    private User_Realization user_init;
    //需求列表数据源
    private List<XuqiuEntity> datas = new ArrayList<>();
    private boolean isLoading = false;
    private int flagnumber = 0;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
        id=Integer.parseInt(User_id.getUid());
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        gaode_dinwei = new Gaode_dinwei(this,getActivity());
//    }

    @Override
    public void initView() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        provinces.addAll(XmlUits.getInstance().getprovince(getActivity()));
        provinceAdapter = new ProvinceAdapter(provinces,getActivity());
        cityAdapter = new CityAdapter(cities,getActivity());
        regionAdapter = new RegionAdapter(regions,getActivity());
        initcityPop();
//        initListPop();
        initChoosePop();
        chooseBtn.setOnClickListener(this);
        cityBtn.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        cityTv.setOnClickListener(this);
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
                                    Integer.parseInt(User_id.getRole()), 0, 0,
                                    "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(),
                                    null, null, NewTeacherFragment.this, gradeId, subjectId, "created", ""
                            );

                        }
                    }
                }
            }
        });
    }

    private void initChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.teacher_choose_pop,null);
        final TextView gendernochooseTv = (TextView) view.findViewById(R.id.xinbiebuxian);
        final TextView genderboyTv = (TextView) view.findViewById(R.id.nan);
        final TextView gendergirlTv = (TextView) view.findViewById(R.id.nv);
        final TextView scorenochooseTv = (TextView) view.findViewById(R.id.pinfenbuxian1);
        final TextView scorehighTv = (TextView) view.findViewById(R.id.gaodaodi1);
        final TextView scorelowTv = (TextView) view.findViewById(R.id.didaogao1);
        final TextView subjectnochooseTv = (TextView) view.findViewById(R.id.kemubuxian);
        final TextView subjectchineseTv = (TextView) view.findViewById(R.id.yuwen);
        final TextView subjectmathTv = (TextView) view.findViewById(R.id.suxue);
        subjectotherTv = (TextView) view.findViewById(otherTv);
        final TextView gradenochooseTv = (TextView) view.findViewById(R.id.nianjibuxian);
        final TextView gradeprimaryTv = (TextView) view.findViewById(R.id.xiaoxue);
        final TextView gradejuniorTv = (TextView) view.findViewById(R.id.chuzhong);
        final TextView gradehighTv = (TextView) view.findViewById(R.id.gaozhong);
        final TextView gradeuniversityTv = (TextView) view.findViewById(R.id.daxue);
        Button sureBtn = (Button)view.findViewById(R.id.quedin);

        choosePop = new PopupWindow(view);
        choosePop.setFocusable(true);
        choosePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        choosePop.setWidth(width/10*7);
        choosePop.setHeight(height/4);
        choosePop.setOutsideTouchable(true);

        subjectnochooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectId = 0;
                subjectotherTv.setText("其他");
                subjectnochooseTv.setTextColor(Color.parseColor("#f7e61c"));
                subjectchineseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectmathTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectotherTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        subjectchineseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectId = 208;
                subjectotherTv.setText("其他");
                subjectnochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectchineseTv.setTextColor(Color.parseColor("#f7e61c"));
                subjectmathTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectotherTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        subjectmathTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectId = 212;
                subjectotherTv.setText("其他");
                subjectnochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectchineseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectmathTv.setTextColor(Color.parseColor("#f7e61c"));
                subjectotherTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        subjectotherTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectnochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectchineseTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectmathTv.setTextColor(Color.parseColor("#8b8b8b"));
                subjectotherTv.setTextColor(Color.parseColor("#f7e61c"));
                showSubject();
            }
        });
        gradenochooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradenochooseTv.setTextColor(Color.parseColor("#f7e61c"));
                gradeprimaryTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradejuniorTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeuniversityTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeId = 0;
            }
        });
        gradeprimaryTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeprimaryTv.setTextColor(Color.parseColor("#f7e61c"));
                gradejuniorTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeuniversityTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeId = 1;
            }
        });
        gradejuniorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeprimaryTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradejuniorTv.setTextColor(Color.parseColor("#f7e61c"));
                gradehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeuniversityTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeId = 2;
            }
        });
        gradehighTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeprimaryTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradejuniorTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradehighTv.setTextColor(Color.parseColor("#f7e61c"));
                gradeuniversityTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeId = 3;
            }
        });
        gradeuniversityTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gradenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeprimaryTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradejuniorTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                gradeuniversityTv.setTextColor(Color.parseColor("#f7e61c"));
                gradeId = 4;
            }
        });
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page  = 1;
//                teacher.Get_Teacher_list()
                ////                    t.Get_Teacher_list(id, role, lat, lng, listView, getActivity(),0, "", 0, 0, 0, 3, this,page);
                demand.getDemand_list(id,
                        Integer.parseInt(User_id.getRole()), 0, 0,
                        "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(),
                        null, null, NewTeacherFragment.this,gradeId,subjectId,"created",""
                );
                choosePop.dismiss();
            }
        });


    }
    private void showSubject() {
        //课程种类
        final AlertDialog.Builder subjectDialog = new AlertDialog.Builder(getActivity());
        subjectDialog.setIcon(R.drawable.add04);
        subjectDialog.setTitle("请选择一个科目");
        //    指定下拉列表的显示数据
        final String[] subjects = new String[SubjectUtil.getSubjectList().size()];
        for (int i = 0; i < subjects.length ; i++) {
            subjects[i] = SubjectUtil.getSubjectList().get(i).getSubjectName();
        }
        //    设置一个下拉的列表选择项
        subjectDialog.setItems(subjects, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "选择的科目为：" + SubjectUtil.getSubjectList().get(which).getSubjectName(), Toast.LENGTH_SHORT).show();
                subjectotherTv.setText(SubjectUtil.getSubjectList().get(which).getSubjectName());
                subjectId = Integer.parseInt(SubjectUtil.getSubjectList().get(which).getSubjectId());
            }
        });
        subjectDialog.show();
        subjectDialog.setCancelable(true);
    }


    private void initcityPop() {
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.student_city_pop,null);
        list1 = (ListView) view.findViewById(R.id.student_list1);
        list2 = (ListView) view.findViewById(R.id.student_list2);
        list3 = (ListView) view.findViewById(R.id.student_list3);
        list1.setAdapter(provinceAdapter);
        list2.setAdapter(cityAdapter);
        list3.setAdapter(regionAdapter);
        cityPop = new PopupWindow(view);
        cityPop.setFocusable(true);
        cityPop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        cityPop.setWidth(width/10*7);
        cityPop.setHeight(height/3);
        cityPop.setOutsideTouchable(true);
        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                cities.clear();
                cities.addAll(provinces.get(position).getCity());
                cityAdapter.notifyDataSetChanged();
                list2.setVisibility(View.VISIBLE);
            }
        });
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                regions.clear();
                regions.addAll(cities.get(position).getList());
                regionAdapter.notifyDataSetChanged();
                list3.setVisibility(View.VISIBLE);
            }
        });

        list3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id1) {
                lat = Double.parseDouble(regions.get(position).getLat());
                lng = Double.parseDouble(regions.get(position).getLng());
                latFlag = 0;
                User_id.setLat(lat);
                User_id.setLng(lng);
//                Toast.makeText(getActivity(), lat+"  "+lng, Toast.LENGTH_SHORT).show();
                list2.setVisibility(View.GONE);
                list3.setVisibility(View.GONE);
                cityPop.dismiss();
                cityTv.setText(regions.get(position).getName());
                demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, NewTeacherFragment.this
                        ,gradeId,subjectId,"created","");
//                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()),
//                        User_id.getLat() + "", "" + User_id.getLng(), recyclerView,
//                        getActivity(), 0, "", genderId, subjectId, gradeId, order_rank, NewTeacherFragment.this, page);
            }
        });

    }

    @Override
    public void initData() {
        List<XuqiuEntity> dbLists = DbUtil.getSession()
                .getXuqiuEntityDao()
                .queryBuilder()
                .limit(19)
                .list();
        datas.addAll(dbLists);
        demandAdapter = new NewDemandAdapter(datas,getActivity());
        demandAdapter.setOnTopClickListener(this);
        recyclerView.setAdapter(demandAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        demand = new Demand(this);
        PermissUtil.startPermiss(getActivity());
        PermissUtil.startPermiss(getActivity());
        PermissUtil.startPermiss(getActivity());
        PermissUtil.startPermiss(getActivity());
        //动态申请权限

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
                ) {
            PermissionGen.with(getActivity())
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.CAMERA
                    )
                    .request();
//
        }else{
//  定位
            if (gaode_dinwei == null)
                gaode_dinwei = new Gaode_dinwei(this,getActivity());
        }
    }

    @Override
    public void Updategaode(Map<String, Object> map) {
        if (User_id.getLat() != 0){
            latFlag = 0;
        }
        if (latFlag == 1){
            lat = (double) map.get("lat");
            lng = (double) map.get("lng");
            User_id.setLat(lat);
            User_id.setLng(lng);
        }
            User_id.setProvince(map.get("Province")+"");
            User_id.setCity(map.get("City")+"");
            cityTv.setText(map.get("District")+"");
            User_id.setStatus(map.get("District") + "");
            User_id.setAddress(map.get("address") + "");
            if (user_init != null) {
                user_init.setlan_lng(id, User_id.getLat(), User_id.getLng());
                user_init.User_Data(id, User_id.getLat() + "", User_id.getLng() + "", this);
                user_init.setAddress(id,User_id.getProvince(),User_id.getCity(),User_id.getStatus());
            } else {
                user_init = new User_Realization();
                user_init.setlan_lng(id, User_id.getLat(), User_id.getLng());
                user_init.User_Data(id, User_id.getLat() + "", User_id.getLng() + "", this);
                user_init.setAddress(id,User_id.getProvince(),User_id.getCity(),User_id.getStatus());
            }

            EventBus.getDefault().post(map.get("address") + "", "status");
//
            demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this
            ,gradeId,subjectId,"created","");

//            teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), 0, "", 0, 0, 0, 3, this, page);


    }

    @Override
    public void Updatecuowu(Map<String, Object> map) {
        Toast.makeText(getActivity(),"定位失败!!请开启手机定位!",Toast.LENGTH_LONG).show();
        demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this
        ,gradeId,subjectId,"created","");

//        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()),  "", "" , recyclerView, getActivity(), 0, "", genderId, subjectId, gradeId, order_rank, NewTeacherFragment.this, page);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teacher_city_tv:
                latFlag = 1;

                User_id.setLat(0);
                User_id.setLng(0);
                gaode_dinwei = new Gaode_dinwei(this,getActivity());
                break;
            case R.id.teacher_search_btn:
                startActivity(SearchActivity_.intent(this).get());
                break;
            case R.id.teacher_city_btn:
                teacherLinear.setBackground(getActivity().getResources().getDrawable(R.drawable.tiaojian_quyu));
                cityPop.showAsDropDown(teacherLinear);
                provinceAdapter.notifyDataSetChanged();
                choosePop.dismiss();
                break;
            case R.id.teacher_choose_btn:
                teacherLinear.setBackground(getActivity().getResources().getDrawable(R.drawable.tiaojian_shaixuan));
                choosePop.showAsDropDown(teacherLinear);
                cityPop.dismiss();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onTopClick(XuqiuEntity entity) {
        //进入学生
        Intent intent = new Intent(getActivity(), Xuqiuxiangx.class);
        intent.putExtra("user_id",entity.getId());
        intent.putExtra("fee",entity.getFee());
        intent.putExtra("publisher_id",entity.getPublisher_id());
        intent.putExtra("course_id",entity.getCourse_id());
        intent.putExtra("grade_id",entity.getGrade_id());
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this
        ,gradeId,subjectId,"created","");

//        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), 0, "", genderId, subjectId, gradeId, order_rank, NewTeacherFragment.this, page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successXuqiu(List<Map<String, Object>> maps) {
        if (maps != null) {
            if (page == 1) {
                demandAdapter.clear();
                datas.clear();
            }
            List<XuqiuEntity> lists = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            XuqiuEntity entity = new XuqiuEntity();
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
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
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
        DbUtil.getSession().getXuqiuEntityDao().insertOrReplaceInTx(lists);
        datas.addAll(lists);
        isLoading = false;
        demandAdapter.addAll(lists);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failXuqiu(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
