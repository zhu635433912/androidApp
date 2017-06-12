package view.index;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import control.Myconteol_init;
import control.Mycontrol;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;
import modle.Adapter.Requirdapter;
import modle.Adapter.StudentAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Gaode.Gaode_dinwei;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.toos.MyListview;
import modle.user_ziliao.User_id;

/**
 * 需求（教师）列表 碎片
 */

public class Teacher_fragment extends Fragment implements View.OnClickListener,MyListview.IReflashListener,
        Requirdetailed,MyListview.RemoveListener,Gaodehuidiao_init,Student_init {
    MyListview listView;
    Myconteol_init myconteol_init;
    ImageButton quyuBtn;
    ImageButton paixuBtn;
    ImageButton chooseBtn;
    Intent intent;
    ListView listview1;
    ListView listview2;
    LinearLayout linearLayout;
    RelativeLayout lineavitint;
    List<Map<String,Object>> listmap;
    RelativeLayout shaixuan;
    private TextView xinjipaixu;
    private TextView jiagepaixu;
    private TextView renqipaixu;
    int id;
    int id_fuzhi;
    int role;
    private RelativeLayout tiaojianshaixuan;

    private TextView xinbiebuxian;
    private TextView nan;
    private TextView nv;
    private int xinbie;

    private TextView kemubuxian;
    private TextView yuwen;
    private TextView suxue;
    private int kemu;

    private TextView nianjibuxian;
    private TextView xiaoxue;
    private TextView chuzhong;
    private TextView gaozhong;
    private TextView daxue;
    private int xueli;

    private RelativeLayout xinbiebuxianr;
    private RelativeLayout nanr;
    private RelativeLayout nvr;

    private RelativeLayout pinfenbuxian;
    private RelativeLayout gaodaodi;
    private RelativeLayout didaogao;
    private TextView pinfenbuxian1;
    private TextView gaodaodi1;
    private TextView didaogao1;
    private int order_rank;


    private RelativeLayout  kemubuxianr;
    private RelativeLayout  yuwenr;
    private RelativeLayout  suxuer;

    private RelativeLayout nianjibuxianr;
    private RelativeLayout xiaoxuer;
    private RelativeLayout chuzhongr;
    private RelativeLayout gaozhongr;
    private RelativeLayout daxuer;
    private Button button;
    private TextView ditutext;
    private Requirdapter requirdapter;
    private StudentAdapter studentAdapter;

    private double lat=131.54915;
    private double lng=24.94213;

    private RelativeLayout indeshuax;
    private TextView indeshuaxtext;

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragemnt_host,null);
        myconteol_init=new Mycontrol();

        button= (Button) view.findViewById(R.id.quedin);
        nianjibuxianr= (RelativeLayout) view.findViewById(R.id.nianjibuxianr);
        xiaoxuer= (RelativeLayout) view.findViewById(R.id.xiaoxuer);
        chuzhongr= (RelativeLayout) view.findViewById(R.id.chuzhongr);
        gaozhongr= (RelativeLayout) view.findViewById(R.id.gaozhongr);
        daxuer= (RelativeLayout) view.findViewById(R.id.daxuer);
        ditutext= (TextView) view.findViewById(R.id.ditutext);
        pinfenbuxian=(RelativeLayout) view.findViewById(R.id.pinfenbuxian);
         gaodaodi= (RelativeLayout) view.findViewById(R.id.gaodaodi);
        didaogao= (RelativeLayout) view.findViewById(R.id.didaogao);
        pinfenbuxian1= (TextView) view.findViewById(R.id.pinfenbuxian1);
        gaodaodi1= (TextView) view.findViewById(R.id.gaodaodi1);
        didaogao1= (TextView) view.findViewById(R.id.didaogao1);
        kemubuxianr= (RelativeLayout) view.findViewById(R.id.kemubuxianr);
        yuwenr= (RelativeLayout) view.findViewById(R.id.yuwenr);
        suxuer= (RelativeLayout) view.findViewById(R.id.suxuer);
        xinbiebuxianr= (RelativeLayout) view.findViewById(R.id.xinbiebuxianr);
        nanr= (RelativeLayout) view.findViewById(R.id.nanr);
        nvr= (RelativeLayout) view.findViewById(R.id.nvr);
        xinbiebuxian= (TextView) view.findViewById(R.id.xinbiebuxian);
        nan= (TextView) view.findViewById(R.id.nan);
        nv= (TextView) view.findViewById(R.id.nv);
        kemubuxian= (TextView) view.findViewById(R.id.kemubuxian);
        yuwen= (TextView) view.findViewById(R.id.yuwen);
        suxue= (TextView) view.findViewById(R.id.suxue);
        nianjibuxian= (TextView) view.findViewById(R.id.nianjibuxian);
        xiaoxue= (TextView) view.findViewById(R.id.xiaoxue);
        chuzhong= (TextView) view.findViewById(R.id.chuzhong);
        gaozhong= (TextView) view.findViewById(R.id.gaozhong);
        daxue= (TextView) view.findViewById(R.id.daxue);
        ImageButton quyuBtn= (ImageButton) view.findViewById(R.id.quyubut1);
        ImageButton paixuBtn= (ImageButton) view.findViewById(R.id.paixubut2);
        ImageButton chooseBtn= (ImageButton) view.findViewById(R.id.shaixuanbut3);
        listview1= (ListView) view.findViewById(R.id.listviewhastva);
        listview2= (ListView) view.findViewById(R.id.listviewhastva2);
        linearLayout= (LinearLayout) view.findViewById(R.id.lincavtilint);
        listView= (MyListview) view.findViewById(R.id.list1);
        shaixuan= (RelativeLayout) view.findViewById(R.id.shaixuan);
        xinjipaixu= (TextView) view.findViewById(R.id.xinjipaixu);
        jiagepaixu= (TextView) view.findViewById(R.id.jiagepaixu);
        renqipaixu= (TextView) view.findViewById(R.id.renqipaixu);
        tiaojianshaixuan= (RelativeLayout) view.findViewById(R.id.tiaojianshaixuan1);
        indeshuax= (RelativeLayout) view.findViewById(R.id.indeshuax);
        indeshuaxtext= (TextView) view.findViewById(R.id.indeshuaxtext);

        //显示控件
       // indeshuax.bringToFront();


        pinfenbuxian.setOnClickListener(this);
        gaodaodi.setOnClickListener(this);
        didaogao.setOnClickListener(this);
        kemubuxianr.setOnClickListener(this);
        yuwenr.setOnClickListener(this);
        suxuer.setOnClickListener(this);
        xinbiebuxianr.setOnClickListener(this);
        nanr.setOnClickListener(this);
        nvr.setOnClickListener(this);
        nianjibuxianr.setOnClickListener(this);
        xiaoxuer.setOnClickListener(this);
        chuzhongr.setOnClickListener(this);
        gaozhongr.setOnClickListener(this);
        daxuer.setOnClickListener(this);
        button.setOnClickListener(this);
        renqipaixu.setOnClickListener(this);
        jiagepaixu.setOnClickListener(this);
        xinjipaixu.setOnClickListener(this);
        quyuBtn.setOnClickListener(this);
        paixuBtn.setOnClickListener(this);
        chooseBtn.setOnClickListener(this);

        //动态申请权限

        if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            PermissionGen.with(getActivity())
                    .addRequestCode(100)
                    .permissions(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE
                            )
                    .request();
//
        }else{
//  定位
            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
        }


        //下拉刷新
        listView.setInterface(this);
        listView.setRemoveListener(this);




        //筛选布局在最上面
        linearLayout.bringToFront();
        xinbiebuxianr.bringToFront();
        nanr.bringToFront();
        nvr.bringToFront();
        kemubuxianr.bringToFront();
        yuwenr.bringToFront();
        suxuer.bringToFront();

        //取消listview下拉上啦默认
        listView.setOverScrollMode(view.OVER_SCROLL_NEVER);
        listView.setVerticalScrollBarEnabled(false);
        listview1.setOverScrollMode(view.OVER_SCROLL_NEVER);
        listview1.setVerticalScrollBarEnabled(false);
        listview2.setOverScrollMode(view.OVER_SCROLL_NEVER);
        listview2.setVerticalScrollBarEnabled(false);

        //隐藏条件布局
        lineavitint= (RelativeLayout) view.findViewById(R.id.relativelayout);
        lineavitint.setVisibility(View.GONE);
        tiaojianshaixuan.setVisibility(View.GONE);
        shaixuan.setVisibility(View.GONE);

        //view监听 点击屏幕 隐藏筛选控件
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineavitint.setVisibility(View.GONE);
                tiaojianshaixuan.setVisibility(View.GONE);

            }
        });

        //加载教师列表
        id=Integer.parseInt(User_id.getUid());
        role=Integer.parseInt(User_id.getRole());
        id_fuzhi=id;

        //城市列表
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) listview1.getItemAtPosition(position);
                String aa= (String) map.get("text");
                myconteol_init.setlist_dqiandao(aa,listview2,getActivity());

            }
        });

        //区域列表
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map= (Map<String, Object>) listview2.getItemAtPosition(position);
                String aa= (String) map.get("diqu");

                myconteol_init.setlist_a(id_fuzhi,role,lat, lng, listView, getActivity(),0,aa,0,0,0,3);
                lineavitint.setVisibility(View.GONE);
            }
        });

        //listview监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (role==1) {
                    //进入老师
                    String uid = requirdapter.geiuiserid(position-1);
                    Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
                    intent.putExtra("user_id", uid);
                    startActivity(intent);
                }else {
                    //进入学生

                    Map<String,Object> map=new HashMap<String, Object>();
                    map=studentAdapter.geiuiserid(position-1);
                    Intent intent = new Intent(getActivity(), Xuqiuxiangx.class);

                    intent.putExtra("user_id",(String)map.get("user_id"));
                    intent.putExtra("fee",(String)map.get("fee"));
                    intent.putExtra("publisher_id",(String)map.get("publisher_id"));
                    startActivity(intent);
                }

            }
        });


        return view;
    }


//    //权限申请成功
//    @PermissionSuccess(requestCode = 100)
//    public void successPermission(){
////        Log.d("123","deviceId"+ DeviceUtil.getDeviceId(this));
//        Log.d("aa","申请成功");
//        Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
////        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.quyubut1:
                Log.e("aa","点击的是地区");
                listView.reflashComplet();
                //显示筛选布局控件
                lineavitint.setVisibility(View.VISIBLE);
                //隐藏排序
                shaixuan.setVisibility(View.GONE);
                tiaojianshaixuan.setVisibility(View.GONE);
                //显示地区筛选
                listview1.setVisibility(View.VISIBLE);
                listview2.setVisibility(View.VISIBLE);

                linearLayout.setBackgroundResource(R.drawable.list01);
                myconteol_init.setlist_d("浙江省",listview1,getActivity());

                break;
            case R.id.paixubut2:
                Log.e("aa","点击的是排序");
                linearLayout.setBackgroundResource(R.drawable.list02);
                //显示筛选布局控件
                lineavitint.setVisibility(View.VISIBLE);
                //隐藏地区筛选
                listview1.setVisibility(View.GONE);
                listview2.setVisibility(View.GONE);
                tiaojianshaixuan.setVisibility(View.GONE);
                //显示排序
                shaixuan.setVisibility(View.VISIBLE);
                break;
            case R.id.xinjipaixu:
                //星级排序
                Log.e("aa","点击的是星级");
                myconteol_init.setlist_a(id,role,lat, lng, listView, getActivity(),1,"",0,0,0,3);
                lineavitint.setVisibility(View.GONE);
                break;
            case R.id.jiagepaixu:
                //价格排序
                Log.e("aa","点击的是价格");
                myconteol_init.setlist_a(id,role,lat, lng, listView, getActivity(),2,"",0,0,0,3);
                lineavitint.setVisibility(View.GONE);
                break;
            case R.id.renqipaixu:
                //人气排序
                Log.e("aa","点击的是人气");
                myconteol_init.setlist_a(id,role,lat, lng, listView, getActivity(),3,"",0,0,0,3);
                lineavitint.setVisibility(View.GONE);
                break;
            case R.id.shaixuanbut3:
                Log.e("aa","点击的是筛选");
                linearLayout.setBackgroundResource(R.drawable.list03);
                lineavitint.setVisibility(View.GONE);
                tiaojianshaixuan.setVisibility(View.VISIBLE);
                break;
            case R.id.xinbiebuxianr:
                //性别不限
                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                xinbie=0;
                break;
            case R.id.nanr:
                //男
                nan.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                nv.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xinbie=1;
                break;
            case R.id.nvr:
                //女
                nan.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nv.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                xinbiebuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xinbie=2;
                break;
            case R.id.kemubuxianr:
                //科目不限
                yuwen.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                suxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                kemubuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                kemu=0;
                break;
            case R.id.yuwenr:
                //语文
                yuwen.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                suxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                kemubuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                kemu=208;
                break;
            case R.id.suxuer:
                //数学
                yuwen.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                suxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                kemubuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                kemu=212;
                break;
            case R.id.nianjibuxianr:
                //年级不限
                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                xueli=0;
                break;
            case R.id.xiaoxuer:
                //小学
                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xiaoxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli=1;
                break;
            case R.id.chuzhongr:
                //初中
                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                chuzhong.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli=2;
                break;
            case R.id.gaozhongr:
                //高中
                daxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaozhong.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli=3;
                break;
            case R.id.daxuer:
                daxue.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                gaozhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                chuzhong.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xiaoxue.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                nianjibuxian.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                xueli=4;
                break;
            case R.id.pinfenbuxian:
                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                didaogao1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                order_rank=0;
                break;
            case R.id.gaodaodi:
                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                didaogao1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                order_rank=1;
                break;

            case R.id.didaogao:
                pinfenbuxian1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                gaodaodi1.setTextColor(android.graphics.Color.parseColor("#8b8b8b"));
                didaogao1.setTextColor(android.graphics.Color.parseColor("#f7e61c"));
                order_rank=2;
                break;

            case R.id.quedin:
                myconteol_init.setlist_a(id,role,lat, lng, listView, getActivity(),1,"",xinbie,kemu,xueli,order_rank);
                tiaojianshaixuan.setVisibility(View.GONE);

                break;
        }
    }


    //刷新数据接口
    @Override
    public void onReflash() {
        myconteol_init.setlist_a(id,role,lat,lng, listView, getActivity(),0,"",0,0,0,0);
    }


    @Override
    public void Updatecontent(Map<String, Object> map) {

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        if (role==1) {
            requirdapter = new Requirdapter(listmap, getActivity());
            listView.setAdapter(requirdapter);
            //隐藏加载数据提示
            indeshuax.setVisibility(View.GONE);
        }
    }

    //滑动删除之后回调方法
    @Override
    public void removeItem(MyListview.RemoveDirection direction, int position) {

    }


    //高德成功回调
    @Override
    public void Updategaode(Map<String, Object> map) {
        lat= (double) map.get("lat");
        lng= (double) map.get("lng");
        ditutext.setText(map.get("District")+"");
        if (role==1) {
            Teacher_init t = new Teacher();
            t.Get_Teacher_list(id, role, lat, lng, listView, getActivity(),0, "", 0, 0, 0, 3, this);
        }else {
            Demand_init demand_init=new Demand();
            demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,1,null,null,this);

        }

    }
    //高德失败回调
    @Override
    public void Updatecuowu(Map<String, Object> map) {
        Toast.makeText(getActivity(),"请开启手机定位!",Toast.LENGTH_LONG).show();

      /*  lat= 125.61750;
        lng= 25.65471;
        if (role==1) {
            Teacher_init t = new Teacher();
            t.Get_Teacher_list(id, role, lat, lng, listView, getActivity(), 0, "", 0, 0, 0, 3, this);
        }else {
            Demand_init demand_init=new Demand();
            demand_init.getDemand_list(id,role,0,0,"2016-08-10",0,1,null,null,this);

        }*/

    }



    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        if (role==2) {
            studentAdapter = new StudentAdapter(listmap, getActivity());
            listView.setAdapter(studentAdapter);
            //隐藏加载数据提示
            indeshuax.setVisibility(View.GONE);
        }
    }
    @Subscriber(tag = "requsetPermiss")
    public void requestSuccess(int requestCode){
        if (requestCode == 100){
            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
