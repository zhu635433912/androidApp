//package view.index;
//
//import android.Manifest;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.graphics.Region;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.PopupWindow;
//import android.widget.RelativeLayout;
//import android.widget.SearchView;
//import android.widget.SimpleAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.deguan.xuelema.androidapp.R;
//import com.deguan.xuelema.androidapp.SearchActivity_;
//import com.deguan.xuelema.androidapp.UserxinxiActivty;
//import com.deguan.xuelema.androidapp.Xuqiufabu;
//import com.deguan.xuelema.androidapp.Xuqiuxiangx;
//import com.deguan.xuelema.androidapp.entities.EntityCity;
//import com.deguan.xuelema.androidapp.entities.EntityProvince;
//import com.deguan.xuelema.androidapp.entities.EntityRegion;
//import com.deguan.xuelema.androidapp.entities.TeacherEntity;
//import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
//import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
//import com.deguan.xuelema.androidapp.init.Requirdetailed;
//import com.deguan.xuelema.androidapp.init.Student_init;
//import com.deguan.xuelema.androidapp.utils.DbUtil;
//import com.deguan.xuelema.androidapp.utils.SubjectUtil;
//import com.deguan.xuelema.androidapp.utils.XmlUits;
//import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
//import com.deguan.xuelema.androidapp.viewimpl.XuqiuView;
//import com.handmark.pulltorefresh.library.PullToRefreshBase;
//import com.handmark.pulltorefresh.library.PullToRefreshListView;
//
//import org.simple.eventbus.EventBus;
//import org.simple.eventbus.Subscriber;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import control.Myconteol_init;
//import control.Mycontrol;
//import de.greenrobot.dao.DbUtils;
//import kr.co.namee.permissiongen.PermissionGen;
//import kr.co.namee.permissiongen.PermissionSuccess;
//import modle.Adapter.CityAdapter;
//import modle.Adapter.ProvinceAdapter;
//import modle.Adapter.RegionAdapter;
//import modle.Adapter.Requirdapter;
//import modle.Adapter.StudentAdapter;
//import modle.Adapter.TeacherListAdapter;
//import modle.Adapter.XuqiuAdapter;
//import modle.Demand_Modle.Demand;
//import modle.Demand_Modle.Demand_init;
//import modle.Gaode.Gaode_dinwei;
//import modle.Teacher_Modle.Teacher;
//import modle.Teacher_Modle.Teacher_init;
//import modle.toos.MyListview;
//import modle.user_Modle.User_Realization;
//import modle.user_ziliao.User_id;
//
///**
// * 需求（教师）列表 碎片
// */
//
//public class Teacher_fragment extends Fragment implements View.OnClickListener,
////        MyListview.IReflashListener,
//        Requirdetailed,
//        PullToRefreshBase.OnRefreshListener2,
//        XuqiuView,
////        MyListview.RemoveListener,
//        Gaodehuidiao_init,Student_init, TeacherView {
//    PullToRefreshListView listView;
//    Myconteol_init myconteol_init;
//    ImageButton quyuBtn;
//    ImageButton paixuBtn;
//    ImageButton chooseBtn;
//    Intent intent;
//    ListView listview1;
//    ListView listview2;
//    ListView listView3;
//    LinearLayout linearLayout;
//    RelativeLayout lineavitint;
//    List<Map<String,Object>> listmap;
//    RelativeLayout shaixuan;
//    private TextView xinjipaixu;
//    private TextView jiagepaixu;
//    private TextView renqipaixu;
//    int id;
//    int id_fuzhi;
//    int role;
//    private RelativeLayout tiaojianshaixuan;
//
//    private TextView xinbiebuxian;
//    private TextView nan;
//    private TextView nv;
//    private int xinbie;
//
//
//    private TextView kemubuxian;
//    private TextView yuwen;
//    private TextView suxue;
//    private int kemu;
//
//    private TextView nianjibuxian;
//    private TextView xiaoxue;
//    private TextView chuzhong;
//    private TextView gaozhong;
//    private TextView daxue;
//    private int xueli;
//
//    private RelativeLayout xinbiebuxianr;
//    private RelativeLayout nanr;
//    private RelativeLayout nvr;
//
//    private RelativeLayout pinfenbuxian;
//    private RelativeLayout gaodaodi;
//    private RelativeLayout didaogao;
//    private TextView pinfenbuxian1;
//    private TextView gaodaodi1;
//    private TextView didaogao1;
//    private int order_rank;
//
//
//    private RelativeLayout  kemubuxianr;
//    private RelativeLayout  yuwenr;
//    private RelativeLayout  suxuer;
//
//    private RelativeLayout nianjibuxianr;
//    private RelativeLayout xiaoxuer;
//    private RelativeLayout chuzhongr;
//    private RelativeLayout gaozhongr;
//    private RelativeLayout daxuer;
//    private Button button;
//    private TextView ditutext;
//    private Requirdapter requirdapter;
//    private StudentAdapter studentAdapter;
//
//    private double lat=131.54915;
//    private double lng=24.94213;
//
//    private RelativeLayout indeshuax;
//    private TextView indeshuaxtext;
//    private int page = 1;
//    //需求列表数据源
//    private List<XuqiuEntity> datas = new ArrayList<>();
//    private XuqiuAdapter xuqiuAdapter ;
//    //教师列表数据
//    private List<TeacherEntity> teachers = new ArrayList<>();
//    private TeacherListAdapter teacherAdapter;
//    private Demand_init demand_init;
//    private Teacher_init t;
//    private TextView otherTv;
//    private ImageButton searchBtn;
//    private User_Realization user_init;
//    private Gaode_dinwei gaode_dinwei;
//    private List<EntityProvince> provinces ;
//    private ProvinceAdapter provinceAdapter;
//    private CityAdapter cityAdapter;
//    private RegionAdapter regionAdapter;
//    private List<EntityCity> cities = new ArrayList<>();
//    private List<EntityRegion> regions = new ArrayList<>();
//    private String loadMore;
//    private int latFlag = 1;
//    private TextView textView8;
//
////    =new SimpleAdapter(context,listmap,R.layout.text_itme,new String[]{"diqu"},new int[]{R.id.textsuan});
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        gaode_dinwei = new Gaode_dinwei(this,getActivity());
//    }
//    private PopupWindow textPopwindow;
//
//    @Nullable
//    @Override
//    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragemnt_host,null);
//        Toolbar toolbar = (Toolbar) view.findViewById(R.id.main_toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
////        myconteol_init=new Mycontrol(this);
////        listView.setAdapter();
//        textView8 = (TextView) view.findViewById(R.id.textView8);
//        searchBtn = (ImageButton) view.findViewById(R.id.main_search_btn);
//        otherTv = (TextView) view.findViewById(R.id.otherTv);
//        button= (Button) view.findViewById(R.id.quedin);
//        nianjibuxianr= (RelativeLayout) view.findViewById(R.id.nianjibuxianr);
//        xiaoxuer= (RelativeLayout) view.findViewById(R.id.xiaoxuer);
//        chuzhongr= (RelativeLayout) view.findViewById(R.id.chuzhongr);
//        gaozhongr= (RelativeLayout) view.findViewById(R.id.gaozhongr);
//        daxuer= (RelativeLayout) view.findViewById(R.id.daxuer);
//        ditutext= (TextView) view.findViewById(R.id.ditutext);
//        pinfenbuxian=(RelativeLayout) view.findViewById(R.id.pinfenbuxian);
//         gaodaodi= (RelativeLayout) view.findViewById(R.id.gaodaodi);
//        didaogao= (RelativeLayout) view.findViewById(R.id.didaogao);
//        pinfenbuxian1= (TextView) view.findViewById(R.id.pinfenbuxian1);
//        gaodaodi1= (TextView) view.findViewById(R.id.gaodaodi1);
//        didaogao1= (TextView) view.findViewById(R.id.didaogao1);
//        kemubuxianr= (RelativeLayout) view.findViewById(R.id.kemubuxianr);
//        yuwenr= (RelativeLayout) view.findViewById(R.id.yuwenr);
//        suxuer= (RelativeLayout) view.findViewById(R.id.suxuer);
//        xinbiebuxianr= (RelativeLayout) view.findViewById(R.id.xinbiebuxianr);
//        nanr= (RelativeLayout) view.findViewById(R.id.nanr);
//        nvr= (RelativeLayout) view.findViewById(R.id.nvr);
//        xinbiebuxian= (TextView) view.findViewById(R.id.xinbiebuxian);
//        nan= (TextView) view.findViewById(R.id.nan);
//        nv= (TextView) view.findViewById(R.id.nv);
//        kemubuxian= (TextView) view.findViewById(R.id.kemubuxian);
//        yuwen= (TextView) view.findViewById(R.id.yuwen);
//        suxue= (TextView) view.findViewById(R.id.suxue);
//        nianjibuxian= (TextView) view.findViewById(R.id.nianjibuxian);
//        xiaoxue= (TextView) view.findViewById(R.id.xiaoxue);
//        chuzhong= (TextView) view.findViewById(R.id.chuzhong);
//        gaozhong= (TextView) view.findViewById(R.id.gaozhong);
//        daxue= (TextView) view.findViewById(R.id.daxue);
//        ImageButton quyuBtn= (ImageButton) view.findViewById(R.id.quyubut1);
//        ImageButton paixuBtn= (ImageButton) view.findViewById(R.id.paixubut2);
//        ImageButton chooseBtn= (ImageButton) view.findViewById(R.id.shaixuanbut3);
//        listview1= (ListView) view.findViewById(R.id.listviewhastva);
//        listview2= (ListView) view.findViewById(R.id.listviewhastva2);
//        listView3 = (ListView) view.findViewById(R.id.listviewhastva3);
//        linearLayout= (LinearLayout) view.findViewById(R.id.lincavtilint);
//        listView= (PullToRefreshListView) view.findViewById(R.id.list1);
//        shaixuan= (RelativeLayout) view.findViewById(R.id.shaixuan);
//        xinjipaixu= (TextView) view.findViewById(R.id.xinjipaixu);
//        jiagepaixu= (TextView) view.findViewById(R.id.jiagepaixu);
//        renqipaixu= (TextView) view.findViewById(R.id.renqipaixu);
//        tiaojianshaixuan= (RelativeLayout) view.findViewById(R.id.tiaojianshaixuan1);
//        indeshuax= (RelativeLayout) view.findViewById(R.id.indeshuax);
//        indeshuaxtext= (TextView) view.findViewById(R.id.indeshuaxtext);
//
//        //显示控件
//       // indeshuax.bringToFront();
//
//        searchBtn.setOnClickListener(this);
//        otherTv.setOnClickListener(this);
//        pinfenbuxian.setOnClickListener(this);
//        gaodaodi.setOnClickListener(this);
//        didaogao.setOnClickListener(this);
//        kemubuxianr.setOnClickListener(this);
//        yuwenr.setOnClickListener(this);
//        suxuer.setOnClickListener(this);
//        xinbiebuxianr.setOnClickListener(this);
//        nanr.setOnClickListener(this);
//        nvr.setOnClickListener(this);
//        nianjibuxianr.setOnClickListener(this);
//        xiaoxuer.setOnClickListener(this);
//        chuzhongr.setOnClickListener(this);
//        gaozhongr.setOnClickListener(this);
//        daxuer.setOnClickListener(this);
//        button.setOnClickListener(this);
//        renqipaixu.setOnClickListener(this);
//        jiagepaixu.setOnClickListener(this);
//        xinjipaixu.setOnClickListener(this);
//        quyuBtn.setOnClickListener(this);
//        paixuBtn.setOnClickListener(this);
//        chooseBtn.setOnClickListener(this);
//        ditutext.setOnClickListener(this);
//
//        textPop();
//        textView8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                textPopwindow.showAtLocation(textView8,500,800,0);
////                textPopwindow.showAsDropDown(textView8);
//            }
//        });
//        provinces = XmlUits.getInstance().getprovince(getContext());
//
//        //下拉刷新
////        listView.setInterface(this);
////        listView.setRemoveListener(this);
//        listView.setMode(PullToRefreshBase.Mode.BOTH);
//        listView.setOnRefreshListener(this);
//
//
//        //筛选布局在最上面
//        linearLayout.bringToFront();
//        xinbiebuxianr.bringToFront();
//        nanr.bringToFront();
//        nvr.bringToFront();
//        kemubuxianr.bringToFront();
//        yuwenr.bringToFront();
//        suxuer.bringToFront();
//
//        //取消listview下拉上啦默认
////        listView.setOverScrollMode(view.OVER_SCROLL_NEVER);
////        listView.setVerticalScrollBarEnabled(false);
//        listview1.setOverScrollMode(view.OVER_SCROLL_NEVER);
//        listview1.setVerticalScrollBarEnabled(false);
//        listview2.setOverScrollMode(view.OVER_SCROLL_NEVER);
//        listview2.setVerticalScrollBarEnabled(false);
//        listView3.setOverScrollMode(view.OVER_SCROLL_NEVER);
//        listView3.setVerticalScrollBarEnabled(false);
//
////        for (int i = 0; i < provinces.size(); i++) {
////
////        }
//        //隐藏条件布局
//        lineavitint= (RelativeLayout) view.findViewById(R.id.relativelayout);
//        lineavitint.setVisibility(View.GONE);
//        tiaojianshaixuan.setVisibility(View.GONE);
//        shaixuan.setVisibility(View.GONE);
//
//        //view监听 点击屏幕 隐藏筛选控件
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lineavitint.setVisibility(View.GONE);
//                tiaojianshaixuan.setVisibility(View.GONE);
//
//            }
//        });
//
//        //加载教师列表
//        id=Integer.parseInt(User_id.getUid());
//        role=Integer.parseInt(User_id.getRole());
//        id_fuzhi=id;
//
//
//        if (role == 2){
//            linearLayout.setBackgroundResource(R.drawable.tiaojian_quyu);
//            paixuBtn.setVisibility(View.GONE);
//        }
//        provinceAdapter = new ProvinceAdapter(provinces,getContext());
//        listview1.setAdapter(provinceAdapter);
//        cityAdapter = new CityAdapter(cities,getContext());
//        listview2.setAdapter(cityAdapter);
//        regionAdapter = new RegionAdapter(regions,getContext());
//        listView3.setAdapter(regionAdapter);
//
//        latFlag = latFlag;
//        latFlag = 0 + latFlag;
//
//        //城市列表
//        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Map<String,Object> map= (Map<String, Object>) listview1.getItemAtPosition(position);
////                String aa= (String) map.get("text");
////                myconteol_init.setlist_dqiandao(aa,listview2,getActivity());
//                cities.clear();
//                cities.addAll(provinces.get(position).getCity());
//                cityAdapter.notifyDataSetChanged();
//                listview2.setVisibility(View.VISIBLE);
//            }
//        });
//
//        //区域列表
//        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                Map<String,Object> map= (Map<String, Object>) listview2.getItemAtPosition(position);
////                String aa= (String) map.get("diqu");
////
////                myconteol_init.setlist_a(id_fuzhi,role,lat, lng, listView, getActivity(),0,aa,0,0,0,3,page);
////                lineavitint.setVisibility(View.GONE);
//                regions.clear();
//                regions.addAll(cities.get(position).getList());
//                regionAdapter.notifyDataSetChanged();
//                listView3.setVisibility(View.VISIBLE);
//            }
//        });
//        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long longid) {
//                lat = Double.parseDouble(regions.get(position).getLat());
//                lng = Double.parseDouble(regions.get(position).getLng());
//                latFlag = 0;
//                User_id.setLat(lat);
//                User_id.setLng(lng);
//                if (role == 2){
//                    demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,page,Double.parseDouble(regions.get(position).getLat()),
//                            Double.parseDouble(regions.get(position).getLng()),null,null,Teacher_fragment.this);
//                }else if (role == 1){
//                    t.Get_Teacher_list(id, role, regions.get(position).getLat(),
//                            regions.get(position).getLng(),
//                            listView, getActivity(), 0, "", 0, 0, 0, 3, Teacher_fragment.this, page);                }
//
//
//            }
//        });
//        //listview监听事件
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (role==1) {
//                    //进入老师
////                    String uid = requirdapter.geiuiserid(position-1);
//                    Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                    intent.putExtra("head_image",teachers.get(position-1).getUser_headimg());
//                    intent.putExtra("user_id",teachers.get(position-1).getUser_id());
//                    intent.putExtra("myid","1");
//                    startActivity(intent);
//                }else {
//                    //进入学生
//
////                    Map<String,Object> map=new HashMap<String, Object>();
////                    map=studentAdapter.geiuiserid(position-1);
//
//                    Intent intent = new Intent(getActivity(), Xuqiuxiangx.class);
//                    intent.putExtra("user_id",datas.get(position-1).getId());
//                    intent.putExtra("fee",datas.get(position-1).getFee());
//                    intent.putExtra("publisher_id",datas.get(position-1).getPublisher_id());
//                    intent.putExtra("course_id",datas.get(position-1).getCourse_id());
//                    intent.putExtra("grade_id",datas.get(position-1).getGrade_id());
////                    intent.putExtra("user_id",(String)map.get("user_id"));
////                    intent.putExtra("fee",(String)map.get("fee"));
////                    intent.putExtra("publisher_id",(String)map.get("publisher_id"));
//                    startActivity(intent);
//                }
//
//            }
//        });
//        //动态申请权限
//
//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//             ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
//                ) {
//            PermissionGen.with(getActivity())
//                    .addRequestCode(100)
//                    .permissions(
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_PHONE_STATE,
//                            Manifest.permission.CAMERA
//                    )
//                    .request();
////
//        }else{
////  定位
////            if (gaode_dinwei == null)
////                gaode_dinwei = new Gaode_dinwei(this,getActivity());
//        }
//
//        //动态申请权限
//
//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
//                ) {
//            PermissionGen.with(getActivity())
//                    .addRequestCode(100)
//                    .permissions(
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_PHONE_STATE,
//                            Manifest.permission.CAMERA
//                    )
//                    .request();
////
//        }else{
////  定位
////            if (gaode_dinwei == null)
////                gaode_dinwei = new Gaode_dinwei(this,getActivity());
//        }
//        //动态申请权限
//
//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
//                ) {
//            PermissionGen.with(getActivity())
//                    .addRequestCode(100)
//                    .permissions(
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_PHONE_STATE,
//                            Manifest.permission.CAMERA
//                    )
//                    .request();
////
//        }else{
////  定位
////            if (gaode_dinwei == null)
////                gaode_dinwei = new Gaode_dinwei(this,getActivity());
//        }
//        //动态申请权限
//
//        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED
//                ||   ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED
//                ) {
//            PermissionGen.with(getActivity())
//                    .addRequestCode(100)
//                    .permissions(
//                            Manifest.permission.ACCESS_COARSE_LOCATION,
//                            Manifest.permission.ACCESS_FINE_LOCATION,
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            Manifest.permission.READ_PHONE_STATE,
//                            Manifest.permission.CAMERA
//                    )
//                    .request();
////
//        }else{
////  定位
//            if (gaode_dinwei == null)
//            gaode_dinwei = new Gaode_dinwei(this,getActivity());
//        }
//        initData();
////        if (role == 2){
////            paixuBtn.setVisibility(View.GONE);
////        }
//        myconteol_init=new Mycontrol(this);
//        return view;
//    }
//
//    private void textPop() {
//        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.cash_ticket_layout,null);
//        TextView cashBack = (TextView) view.findViewById(R.id.cash_back);
//        TextView cashSure = (TextView) view.findViewById(R.id.cash_sure);
//        final EditText cashId = (EditText) view.findViewById(R.id.cash_name);
//        textPopwindow = new PopupWindow(view);
//        textPopwindow.setFocusable(true);
//        textPopwindow.setBackgroundDrawable(new BitmapDrawable());
//        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        int width = wm.getDefaultDisplay().getWidth();
//        textPopwindow.setWidth(width/4*3);
//        textPopwindow.setHeight(height/2);
//        textPopwindow.setOutsideTouchable(true);
//        cashBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "dianwoganam", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    //初始化数据
//    private void initData() {
//        if (role == 2){
//            List<XuqiuEntity> listEntities = DbUtil.getSession()
//                    .getXuqiuEntityDao()
//                    .queryBuilder()
//                    .limit(19)
//                    .list();
//            datas.addAll(listEntities);
//            xuqiuAdapter = new XuqiuAdapter(datas, getActivity());
//            listView.setAdapter(xuqiuAdapter);
//        }else if (role == 1){
//            List<TeacherEntity> dbLists = DbUtil.getSession()
//                    .getTeacherEntityDao()
//                    .queryBuilder()
//                    .limit(19)
//                    .list();
//            teachers.addAll(dbLists);
//            teacherAdapter = new TeacherListAdapter(teachers,getActivity());
//            listView.setAdapter(teacherAdapter);
//        }
//
////        adapter
//        demand_init = new Demand(this);
//        t = new Teacher(this);
//    }
//
//
////    //权限申请成功
////    @PermissionSuccess(requestCode = 100)
////    public void successPermission(){
//////        Log.d("123","deviceId"+ DeviceUtil.getDeviceId(this));
////        Log.d("aa","申请成功");
////        Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
//////        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
////    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()){
//            case R.id.ditutext:
//                latFlag = 1;
//                User_id.setLat(0);
//                User_id.setLng(0);
//                gaode_dinwei = new Gaode_dinwei(this,getActivity());
//                break;
//            case R.id.main_search_btn:
//                startActivity(SearchActivity_.intent(this).get());
//
//                break;
//            case R.id.quyubut1:
//                Log.e("aa","点击的是地区");
////                listView.reflashComplet();
//                listView.onRefreshComplete();
//                //显示筛选布局控件
//                lineavitint.setVisibility(View.VISIBLE);
//                //隐藏排序
//                shaixuan.setVisibility(View.GONE);
//                tiaojianshaixuan.setVisibility(View.GONE);
//                //显示地区筛选
//                listview1.setVisibility(View.VISIBLE);
////                listview2.setVisibility(View.VISIBLE);
//                if (role == 2){
//                    linearLayout.setBackgroundResource(R.drawable.tiaojian_quyu);
//                }else {
//                    linearLayout.setBackgroundResource(R.drawable.list01);
//                }
////                myconteol_init.setlist_d("浙江省",listview1,getActivity());
//
//                break;
//            case R.id.paixubut2:
//                Log.e("aa","点击的是排序");
//                page = 1;
//                linearLayout.setBackgroundResource(R.drawable.list02);
//                //显示筛选布局控件
//                lineavitint.setVisibility(View.VISIBLE);
//                //隐藏地区筛选
//                listview1.setVisibility(View.GONE);
//                listview2.setVisibility(View.GONE);
//                listView3.setVisibility(View.GONE);
//                tiaojianshaixuan.setVisibility(View.GONE);
//                //显示排序
//                shaixuan.setVisibility(View.VISIBLE);
//                break;
//            case R.id.xinjipaixu:
//                //星级排序
//                Log.e("aa","点击的是星级");
//                myconteol_init.setlist_a(id,role,lat+"", lng+"", listView, getActivity(),1,"",0,0,0,3,page);
//                lineavitint.setVisibility(View.GONE);
//                break;
//            case R.id.jiagepaixu:
//                //价格排序
//                Log.e("aa","点击的是价格");
//                myconteol_init.setlist_a(id,role,lat+"", lng+"", listView, getActivity(),2,"",0,0,0,3,page);
//                lineavitint.setVisibility(View.GONE);
//                break;
//            case R.id.renqipaixu:
//                //人气排序
//                Log.e("aa","点击的是人气");
//                myconteol_init.setlist_a(id,role,lat+"", lng+"", listView, getActivity(),3,"",0,0,0,3,page);
//                lineavitint.setVisibility(View.GONE);
//                break;
//            case R.id.shaixuanbut3:
//                Log.e("aa","点击的是筛选");
//                if (role == 2){
//                    linearLayout.setBackgroundResource(R.drawable.tiaojian_shaixuan);
//                }else {
//                    linearLayout.setBackgroundResource(R.drawable.list03);
//                }
//                lineavitint.setVisibility(View.GONE);
//                tiaojianshaixuan.setVisibility(View.VISIBLE);
//                break;
//            case R.id.xinbiebuxianr:
//                //性别不限
//                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                xinbie=0;
//                break;
//            case R.id.nanr:
//                //男
//                nan.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xinbie=1;
//                break;
//            case R.id.nvr:
//                //女
//                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nv.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xinbie=2;
//                break;
//            case R.id.kemubuxianr:
//                //科目不限
//                otherTv.setText("其他");
//                otherTv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                yuwen.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                suxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                kemubuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                kemu=0;
//                break;
//            case R.id.otherTv:
//                yuwen.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                suxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                kemubuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                otherTv.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                showSubject();
//
//
//
//                break;
//            case R.id.yuwenr:
//                //语文
//                otherTv.setText("其他");
//                otherTv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                yuwen.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                suxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                kemubuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                kemu=208;
//                break;
//            case R.id.suxuer:
//                //数学
//                otherTv.setText("其他");
//                otherTv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                yuwen.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                suxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                kemubuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                kemu=212;
//                break;
//            case R.id.nianjibuxianr:
//                //年级不限
//                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                xueli=0;
//                break;
//            case R.id.xiaoxuer:
//                //小学
//                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xiaoxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xueli=1;
//                break;
//            case R.id.chuzhongr:
//                //初中
//                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                chuzhong.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xueli=2;
//                break;
//            case R.id.gaozhongr:
//                //高中
//                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaozhong.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xueli=3;
//                break;
//            case R.id.daxuer:
//                daxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                xueli=4;
//                break;
//            case R.id.pinfenbuxian:
//                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                didaogao1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                order_rank=0;
//                break;
//            case R.id.gaodaodi:
//                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                didaogao1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                order_rank=1;
//                break;
//
//            case R.id.didaogao:
//                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
//                didaogao1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
//                order_rank=2;
//                break;
//
//            case R.id.quedin:
//                page = 1;
////                if (role == 1){
////                    t.Get_Teacher_list(id, role, lat, lng, listView, getActivity(),0, "", 0, 0, 0, 3, this,page);
////                }else if (role == 2){
////                    demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,page,null,null,this);
////                }
//                myconteol_init.setlist_a(id,role,""+lat,""+ lng, listView, getActivity(),1,"",xinbie,kemu,xueli,order_rank,page);
//                tiaojianshaixuan.setVisibility(View.GONE);
//
//                break;
//        }
//    }
//
//    private void showSubject() {
//        //课程种类
//        final AlertDialog.Builder subjectDialog = new AlertDialog.Builder(getActivity());
//        subjectDialog.setIcon(R.drawable.add04);
//        subjectDialog.setTitle("请选择一个科目");
//        //    指定下拉列表的显示数据
//        final String[] subjects = new String[SubjectUtil.getSubjectList().size()];
//        for (int i = 0; i < subjects.length ; i++) {
//            subjects[i] = SubjectUtil.getSubjectList().get(i).getSubjectName();
//        }
//        //    设置一个下拉的列表选择项
//        subjectDialog.setItems(subjects, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getActivity(), "选择的科目为：" + SubjectUtil.getSubjectList().get(which).getSubjectName(), Toast.LENGTH_SHORT).show();
//                otherTv.setText(SubjectUtil.getSubjectList().get(which).getSubjectName());
//                kemu = Integer.parseInt(SubjectUtil.getSubjectList().get(which).getSubjectId());
//            }
//        });
//        subjectDialog.show();
//        subjectDialog.setCancelable(true);
//    }
//
//
//    //刷新数据接口
////    @Override
////    public void onReflash() {
////        myconteol_init.setlist_a(id,role,lat,lng, listView, getActivity(),0,"",0,0,0,0);
////    }
//
//
//    @Override
//    public void Updatecontent(Map<String, Object> map) {
//
//    }
//
//    @Override
//    public void Updatefee(List<Map<String, Object>> listmap) {
////        if (role==1) {
////            requirdapter = new Requirdapter(listmap, getActivity());
////            listView.setAdapter(requirdapter);
////            //隐藏加载数据提示
////            indeshuax.setVisibility(View.GONE);
////        }
//    }
//
////    //滑动删除之后回调方法
////    @Override
////    public void removeItem(MyListview.RemoveDirection direction, int position) {
////
////    }
//
//
//    //高德成功回调
//    @Override
//    public void Updategaode(Map<String, Object> map) {
//        if (User_id.getLat() != 0){
//            latFlag = 0;
//        }
//        if (latFlag == 1 ){
//            lat = (double) map.get("lat");
//            lng = (double) map.get("lng");
//            User_id.setLat(lat);
//            User_id.setLng(lng);
//        }else {
//            ditutext.setText(map.get("District") + "");
//            User_id.setStatus(map.get("District") + "");
//            User_id.setAddress(map.get("address") + "");
//            if (user_init != null) {
//                user_init.setlan_lng(id, User_id.getLat(), User_id.getLng());
//                user_init.User_Data(id, User_id.getLat() + "", User_id.getLng() + "", this);
//            } else {
//                user_init = new User_Realization();
//                user_init.setlan_lng(id, User_id.getLat(), User_id.getLng());
//                user_init.User_Data(id, User_id.getLat() + "", User_id.getLng() + "", this);
//            }
//
//            EventBus.getDefault().post(map.get("address") + "", "status");
//            if (role == 1) {
//
//                t.Get_Teacher_list(id, role, User_id.getLat() + "", "" + User_id.getLng(), listView, getActivity(), 0, "", 0, 0, 0, 3, this, page);
//            } else {
//                demand_init.getDemand_list(id, role, 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this);
//            }
//        }
//
//    }
//    //高德失败回调
//    @Override
//    public void Updatecuowu(Map<String, Object> map) {
//        Toast.makeText(getActivity(),"定位失败!!请开启手机定位!",Toast.LENGTH_LONG).show();
//        if (role==1) {
//
//            t.Get_Teacher_list(id, role, "","", listView, getActivity(),0, "", 0, 0, 0, 3, this,page);
//        }else {
//            demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,page,0,0,null,null,this);
//        }
////        lat= 125.61750;
////        lng= 25.65471;
//      /*
//      lat= 125.61750;
//        lng= 25.65471;
//        if (role==1) {
//            Teacher_init t = new Teacher();
//            t.Get_Teacher_list(id, role, lat, lng, listView, getActivity(), 0, "", 0, 0, 0, 3, this);
//        }else {
//            Demand_init demand_init=new Demand();
//            demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,1,null,null,this);
//
//        }*/
//
//    }
//
//
//
//    @Override
//    public void setListview(List<Map<String, Object>> listmap) {
//
//    }
//
//    @Override
//    public void setListview1(List<Map<String, Object>> listmap) {
//        if (role==2) {
//            studentAdapter = new StudentAdapter(listmap, getActivity());
//            for (int i = 0; i < listmap.size(); i++) {
//                listmap.get(i).get("publisher_headimg");
//            }
//            listView.setAdapter(studentAdapter);
//            //隐藏加载数据提示
//            indeshuax.setVisibility(View.GONE);
//        }
//    }
//
//    @Subscriber(tag = "requsetPermiss")
//    public void requestSuccess(int requestCode){
//        initData();
//        if (requestCode == 100){
//            if (gaode_dinwei == null)
//                gaode_dinwei = new Gaode_dinwei(this,getActivity());
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Override
//    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//        page = 1;
//
//            if (role == 2) {
//                demand_init.getDemand_list(id, role, 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this);
//            } else if (role == 1) {
//                t.Get_Teacher_list(id, role, User_id.getLat() + "", "" + User_id.getLng(), listView, getActivity(), 0, "", 0, 0, 0, 3, this, page);
//            }
//
//    }
//
//    @Override
//    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
//        page++;
//
//            if (role == 2) {
//                demand_init.getDemand_list(id, role, 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this);
//            } else if (role == 1) {
////            if (teachers.size() < 19){
////                        new Thread(new Runnable() {
////                            @Override
////                            public void run() {
////                                try {
////                                    Thread.sleep(5000);
////                                    listView.onRefreshComplete();
////                                } catch (InterruptedException e) {
////                                    e.printStackTrace();
////                                }
////                            }
////                        }).start();
////            }else {
//                t.Get_Teacher_list(id, role, "" + User_id.getLat(), "" + User_id.getLng(), listView, getActivity(), 0, "", 0, 0, 0, 3, this, page);
////            }
//            }
//        listView.onRefreshComplete();
//    }
//
//
//    @Override
//    public void successXuqiu(List<Map<String, Object>> maps) {
//        listview2.setVisibility(View.GONE);
//        listView3.setVisibility(View.GONE);
//        lineavitint.setVisibility(View.GONE);
//        listView.onRefreshComplete();
//        if (page == 1){
//            datas.clear();
//        }
//        List<XuqiuEntity> lists = new ArrayList<>();
//        for (int i = 0; i < maps.size(); i++) {
//            XuqiuEntity entity = new XuqiuEntity();
//            entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
//            entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
//            entity.setService_type((String)maps.get(i).get("service_type"));
//            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
//            entity.setCourse_name((String) maps.get(i).get("course_name"));
//            entity.setContent((String) maps.get(i).get("content"));
//            entity.setCreated((String) maps.get(i).get("created"));
//            entity.setId((String) maps.get(i).get("id"));
//            entity.setCourse_id((String)maps.get(i).get("course_id"));
//            entity.setGrade_id((String)maps.get(i).get("grade_id"));
//            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
//            entity.setDistance((String) maps.get(i).get("distance"));
//            entity.setFee(String.valueOf(maps.get(i).get("fee")));
//            entity.setGrade_name((String)maps.get(i).get("grade_name"));
//            if ((maps.get(i).get("address")+"").length()>7) {
//                entity.setAddress(((String) maps.get(i).get("address")).substring(0, 7) + "......");
//            }else {
//                entity.setAddress((String) maps.get(i).get("address"));
//            }
//            if ((maps.get(i).get("status")).equals("1")||maps.get(i).get("status").equals("2")){
//                continue;
//            }
//            lists.add(entity);
//        }
//        DbUtil.getSession().getXuqiuEntityDao().insertOrReplaceInTx(lists);
//        datas.addAll(lists);
//        xuqiuAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void failXuqiu(String msg) {
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void successTeacher(List<Map<String, Object>> maps) {
//        listview2.setVisibility(View.GONE);
//        listView3.setVisibility(View.GONE);
//        lineavitint.setVisibility(View.GONE);
//        listView.onRefreshComplete();
//        if (page == 1){
//            teachers.clear();
//        }
//        if (maps.size() != 0) {
//            List<TeacherEntity> lists = new ArrayList<>();
//            for (int i = 0; i < maps.size(); i++) {
//                TeacherEntity entity = new TeacherEntity();
//                entity.setNickname((String) maps.get(i).get("nickname"));
//                entity.setSpeciality((String)maps.get(i).get("speciality"));
//                entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
////                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
//
//
//                entity.setSignature((String) maps.get(i).get("signature"));
//                entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
//                entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
//                entity.setUser_id((String) maps.get(i).get("user_id"));
//                entity.setGender((String) maps.get(i).get("gender"));
////            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
//                entity.setDistance((String) maps.get(i).get("distance"));
//                entity.setFee(String.valueOf(maps.get(i).get("fee")));
//                entity.setHaoping_num((String)maps.get(i).get("haoping_num"));
//                List<Map<String,Object>> listmap = ((List<Map<String,Object>>)maps.get(i).get("information_temp"));
//                if (listmap .size()>0){
//                    entity.setService_type_txt((String) listmap.get(0).get("course_remark"));
//                }else {
//                    entity.setService_type_txt("不限");
//                }
//                String course_name = "";
//                for (int j = 0; j < listmap.size(); j++) {
//                    course_name = course_name + listmap.get(j).get("course_name")+"  ";
//                }
//                entity.setStatus2(course_name);
//                entity.setEducation((String)maps.get(i).get("education"));
////            entity.setGrade_name((String)maps.get(i).get("grade_name"));
//                lists.add(entity);
//            }
//            DbUtil.getSession().getTeacherEntityDao().insertOrReplaceInTx(lists);
//            teachers.addAll(lists);
//            teacherAdapter.notifyDataSetChanged();
//        }
//    }
//
//    @Override
//    public void failTeacher(String msg) {
//        Toast.makeText(getActivity(), "网络错误", Toast.LENGTH_SHORT).show();
//    }
//}
