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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SearchActivity_;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.entities.EntityCity;
import com.deguan.xuelema.androidapp.entities.EntityProvince;
import com.deguan.xuelema.androidapp.entities.EntityRegion;
import com.deguan.xuelema.androidapp.entities.TeacherEntity;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.utils.XmlUits;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Adapter.CityAdapter;
import modle.Adapter.NewTeacherAdapter;
import modle.Adapter.ProvinceAdapter;
import modle.Adapter.RegionAdapter;
import modle.Adapter.TeacherListAdapter;
import modle.Gaode.Gaode_dinwei;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

import static com.deguan.xuelema.androidapp.R.id.otherTv;

/**
 * * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-06-23 13:15
 * 修改人：zhuyunjian
 * 修改时间：2017-06-23 13:15
 * 修改备注：
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_student)
public class StudentFragment extends BaseFragment implements Gaodehuidiao_init, View.OnClickListener, TeacherView, Requirdetailed, NewTeacherAdapter.OnTopClickListener, SwipeRefreshLayout.OnRefreshListener {

    @ViewById(R.id.student_city_btn)
    ImageButton cityBtn;
    @ViewById(R.id.student_list_btn)
    ImageButton listBtn;
    @ViewById(R.id.student_choose_btn)
    ImageButton chooseBtn;
    @ViewById(R.id.student_linear)
    LinearLayout studentLinear;
    @ViewById(R.id.student_recycler)
    RecyclerView recyclerView;
    @ViewById(R.id.student_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.student_toolbar)
    Toolbar toolbar;
    @ViewById(R.id.student_city_tv)
    TextView cityTv;
    @ViewById(R.id.student_search_btn)
    ImageButton searchBtn;


    private NewTeacherAdapter teacherAdapter;
    private List<EntityProvince> provinces = new ArrayList<>();
    private ProvinceAdapter provinceAdapter;
    private CityAdapter cityAdapter;
    private RegionAdapter regionAdapter;
    private List<EntityCity> cities = new ArrayList<>();
    private List<EntityRegion> regions = new ArrayList<>();
    private PopupWindow cityPop,listPop,choosePop;
    private Gaode_dinwei gaode_dinwei;
    private Teacher_init teacher;
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
    private int order_rank = 3;
    private int order = 0;
    private TextView subjectotherTv;
    private User_Realization user_init;
    private List<TeacherEntity> teachers = new ArrayList<>();
    private boolean isLoading = false;
    private int flagnumber =0 ;

    @Override
    public void before() {
        EventBus.getDefault().register(this);
        id=Integer.parseInt(User_id.getUid());
    }

    @Override
    public void onStart() {
        super.onStart();
        gaode_dinwei = new Gaode_dinwei(this,getActivity());
    }

    @Override
    public void initView() {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        provinces.addAll(XmlUits.getInstance().getprovince(getActivity()));
        provinceAdapter = new ProvinceAdapter(provinces,getActivity());
        cityAdapter = new CityAdapter(cities,getActivity());
        regionAdapter = new RegionAdapter(regions,getActivity());
        initcityPop();
        initListPop();
        initChoosePop();
        chooseBtn.setOnClickListener(this);
        cityBtn.setOnClickListener(this);
        listBtn.setOnClickListener(this);
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
                    if (flagnumber >= 19) {
                        if (adapter1.getItemCount() - position < 5) {
                            isLoading = true;
                            page++;
                            teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page, subjectId);
                        }
                    }
                }
            }
        });
    }

    private void initChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.student_choose_pop,null);
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
        choosePop.setHeight(height/2);
        choosePop.setOutsideTouchable(true);
        gendernochooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderId = 0;
                gendernochooseTv.setTextColor(Color.parseColor("#f7e61c"));
                genderboyTv.setTextColor(Color.parseColor("#8b8b8b"));
                gendergirlTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        genderboyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderId = 1;
                gendernochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                genderboyTv.setTextColor(Color.parseColor("#f7e61c"));
                gendergirlTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        gendergirlTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderId = 2;
                gendernochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                genderboyTv.setTextColor(Color.parseColor("#8b8b8b"));
                gendergirlTv.setTextColor(Color.parseColor("#f7e61c"));
            }
        });
        scorenochooseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_rank = 0;
                scorenochooseTv.setTextColor(Color.parseColor("#f7e61c"));
                scorehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                scorelowTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        scorehighTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_rank = 1;
                scorenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                scorehighTv.setTextColor(Color.parseColor("#f7e61c"));
                scorelowTv.setTextColor(Color.parseColor("#8b8b8b"));
            }
        });
        scorelowTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_rank = 2;
                scorenochooseTv.setTextColor(Color.parseColor("#8b8b8b"));
                scorehighTv.setTextColor(Color.parseColor("#8b8b8b"));
                scorelowTv.setTextColor(Color.parseColor("#f7e61c"));
            }
        });
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
                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), 0, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);

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

    private void initListPop() {
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.student_list_pop,null);
        TextView starTv = (TextView) view.findViewById(R.id.student_star);
        TextView moneyTv = (TextView) view.findViewById(R.id.student_money);
        TextView likeTv = (TextView) view.findViewById(R.id.student_like);
        listPop = new PopupWindow(view);
        listPop.setFocusable(true);
        listPop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        listPop.setWidth(width/10*7);
        listPop.setHeight(height/5);
        listPop.setOutsideTouchable(true);
        starTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = 1;
                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);
                listPop.dismiss();
            }
        });
        moneyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order = 2;
                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);

                listPop.dismiss();
            }
        });
        likeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order =3;
                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);

                listPop.dismiss();
            }
        });
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
                teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()),
                        User_id.getLat() + "", "" + User_id.getLng(), recyclerView,
                        getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);
            }
        });

    }

    @Override
    public void initData() {
        List<TeacherEntity> dbLists = DbUtil.getSession()
                    .getTeacherEntityDao()
                    .queryBuilder()
                    .limit(19)
                    .list();
            teachers.addAll(dbLists);
            teacherAdapter = new NewTeacherAdapter(teachers,getActivity());
        teacherAdapter.setOnTopClickListener(this);
        recyclerView.setAdapter(teacherAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        teacher = new Teacher(this);
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
            } else {
                user_init = new User_Realization();
                user_init.setlan_lng(id, User_id.getLat(), User_id.getLng());
                user_init.User_Data(id, User_id.getLat() + "", User_id.getLng() + "", this);
            }

            EventBus.getDefault().post(map.get("address") + "", "status");
//
            teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "",  genderId, 0, gradeId, order_rank, this, page,subjectId);


    }

    @Override
    public void Updatecuowu(Map<String, Object> map) {
        Toast.makeText(getActivity(),"定位失败!!请开启手机定位!",Toast.LENGTH_LONG).show();

        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()),  "", "" , recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.student_city_tv:
                latFlag = 1;
                order = 0;
                User_id.setLat(0);
                User_id.setLng(0);
                gaode_dinwei = new Gaode_dinwei(this,getActivity());
                break;
            case R.id.student_search_btn:
                startActivity(SearchActivity_.intent(this).get());
                break;
            case R.id.student_city_btn:
                studentLinear.setBackground(getActivity().getResources().getDrawable(R.drawable.list01));
                cityPop.showAsDropDown(studentLinear);
                provinceAdapter.notifyDataSetChanged();
                listPop.dismiss();
                choosePop.dismiss();
                break;
            case R.id.student_list_btn:
                studentLinear.setBackground(getActivity().getResources().getDrawable(R.drawable.list02));
                listPop.showAsDropDown(studentLinear);
                cityPop.dismiss();
                choosePop.dismiss();
                break;
            case R.id.student_choose_btn:
                studentLinear.setBackground(getActivity().getResources().getDrawable(R.drawable.list03));
                choosePop.showAsDropDown(studentLinear);
                cityPop.dismiss();
                listPop.dismiss();
                break;
        }
    }

    @Override
    public void successTeacher(List<Map<String, Object>> maps) {
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
                entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
//                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));

                entity.setClick(maps.get(i).get("click")+"");
                entity.setSignature((String) maps.get(i).get("signature"));
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
                    entity.setService_type_txt((String) listmap.get(0).get("course_remark"));
                }else {
                    entity.setService_type_txt("不限");
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
            DbUtil.getSession().getTeacherEntityDao().insertOrReplaceInTx(lists);
            teachers.addAll(lists);
            isLoading = false;
            teacherAdapter.addAll(lists);
            swipeRefreshLayout.setRefreshing(false);

        }
    }

    @Override
    public void failTeacher(String msg) {
        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onTopClick(TeacherEntity entity) {
        //进入老师
//                    String uid = requirdapter.geiuiserid(position-1);
                    Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
//                    intent.putExtra("user_id", uid);
                    intent.putExtra("head_image",entity.getUser_headimg());
                    intent.putExtra("user_id",entity.getUser_id());
                    intent.putExtra("myid","1");
                    startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        teacher.Get_Teacher_list(id, Integer.parseInt(User_id.getRole()), User_id.getLat() + "", "" + User_id.getLng(), recyclerView, getActivity(), order, "", genderId, 0, gradeId, order_rank, StudentFragment.this, page,subjectId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
