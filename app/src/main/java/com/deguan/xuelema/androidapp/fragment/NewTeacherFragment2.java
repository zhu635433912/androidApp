package com.deguan.xuelema.androidapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.deguan.xuelema.androidapp.AmapActivity;
import com.deguan.xuelema.androidapp.DemandActivity_;
import com.deguan.xuelema.androidapp.DemandDetailActivity_;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SearchActivity_;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ConversationRefreshEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.MainActivity;
import jiguang.chat.entity.Event;
import jiguang.chat.utils.ThreadUtil;
import kr.co.namee.permissiongen.PermissionGen;
import modle.Adapter.CourseAdapter;
import modle.Demand_Modle.Demand;
import modle.Gaode.Gaode_dinwei;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;
import view.login.Modle.RegisterUtil;

@EFragment(R.layout.fragment_new_teacher_fragment2)
public class NewTeacherFragment2 extends MyBaseFragment implements View.OnClickListener, Gaodehuidiao_init, Requirdetailed, SimilarXuqiuView, TurnoverView, SwipeRefreshLayout.OnRefreshListener {


    @ViewById(R.id.text331)
    TextView priceTv1;
    @ViewById(R.id.text332)
    TextView priceTv2;
    @ViewById(R.id.teacher_address_image)
    ImageView addressImage;
    @ViewById(R.id.teacher_my)
    ImageView chatImage;
    @ViewById(R.id.teacher_message)
    TextView chatTv;
    @ViewById(R.id.teacher_convenientBanner)
    ConvenientBanner convenientBanner;
    @ViewById(R.id.search_image)
    ImageView searchImage;
    @ViewById(R.id.unread_address_number)
    TextView unreadAddressLable;
    @ViewById(R.id.text200)
    Button text200;
    @ViewById(R.id.text208)
    Button text208;
    @ViewById(R.id.text212)
    Button text212;
    @ViewById(R.id.text213)
    Button text213;
    @ViewById(R.id.text217)
    Button text217;
    @ViewById(R.id.text218)
    Button text218;
    @ViewById(R.id.text220)
    Button text220;
    @ViewById(R.id.text221)
    Button text221;
    @ViewById(R.id.teacher_more)
    TextView moreTv;
    @ViewById(R.id.tuijian_demand_rl1)
    RelativeLayout demandRl1;
    @ViewById(R.id.tuijian_demand_rl2)
    RelativeLayout demandRl2;
    @ViewById(R.id.huodong_image)
    ImageView huodongImage;
    @ViewById(R.id.teacher_address_tv)
    TextView cityTv;

    @ViewById(R.id.tuijian_demand_head1)
    SimpleDraweeView headImage1;
    @ViewById(R.id.tuijian_demand_name1)
    TextView nameTv1;
    @ViewById(R.id.tuijian_demand_grade1)
    TextView gradeTv1;
    @ViewById(R.id.tuijian_demand_education_tv)
    TextView educationTv1;
    @ViewById(R.id.tuijian_demand_servicetype1)
    TextView serviceTv1;
    @ViewById(R.id.tuijian_demand_course1)
    TextView courseTv1;
    @ViewById(R.id.tuijian_demand_content1)
    TextView contentTv1;
    @ViewById(R.id.tuijian_demand_haoping1)
    TextView haopingTv1;
    @ViewById(R.id.tuijian_demand_imagehaop1)
    ImageView haopingImage1;
    @ViewById(R.id.tuijian_demand_head2)
    SimpleDraweeView headImage2;
    @ViewById(R.id.tuijian_demand_name2)
    TextView nameTv2;
    @ViewById(R.id.tuijian_demand_grade2)
    TextView gradeTv2;
    @ViewById(R.id.tuijian_demand_education_tv2)
    TextView educationTv2;
    @ViewById(R.id.tuijian_demand_servicetype2)
    TextView serviceTv2;
    @ViewById(R.id.tuijian_demand_course2)
    TextView courseTv2;
    @ViewById(R.id.tuijian_demand_content2)
    TextView contentTv2;
    @ViewById(R.id.tuijian_demand_haoping2)
    TextView haopingTv2;
    @ViewById(R.id.tuijian_demand_imagehaop2)
    ImageView haopingImage2;
    @ViewById(R.id.tuijian_demand_distance2)
    TextView distanceTv2;
    @ViewById(R.id.tuijian_demand_distance1)
    TextView distanceTv1;
    @ViewById(R.id.search_edit)
    EditText searchEdit;

    @ViewById(R.id.student_swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.student_scroll)
    ScrollView scrollView;

    private double lat=131.54915;
    private double lng=24.94213;
    private int latFlag = 1;
    private Gaode_dinwei gaode_dinwei;
    private ArrayList<String> localImages = new ArrayList<String>();
    private User_Realization user_init;
    private int id ;
    private PopupWindow coursePop;
    private UserInfo mMyInfo;

    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
        id = Integer.parseInt(User_id.getUid());
    }

    @Override
    public void initView() {
        new RegisterUtil().getAdpicture(this);
        cityTv.setOnClickListener(this);
        chatTv.setOnClickListener(this);
        addressImage.setOnClickListener(this);
        searchImage.setOnClickListener(this);
        huodongImage.setOnClickListener(this);
        demandRl1.setOnClickListener(this);
        demandRl2.setOnClickListener(this);
        text208.setOnClickListener(this);
        text212.setOnClickListener(this);
        text213.setOnClickListener(this);
        text217.setOnClickListener(this);
        text218.setOnClickListener(this);
        text220.setOnClickListener(this);
        text221.setOnClickListener(this);
        moreTv.setOnClickListener(this);
        text200.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new  ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                swipeRefreshLayout.setEnabled(scrollView.getScrollY()==0);
            }
        });
//        localImages.add(R.mipmap.banner1);
//        localImages.add(R.mipmap.banner1);
//        localImages.add(R.mipmap.banner1);
//        localImages.add(getResId("banner1", R.mipmap.class));
//        localImages.add(getResId("banner1", R.mipmap.class));
////        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/1.jpg");
////        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/2.jpg");
////        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/3.jpg");
//        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
//        convenientBanner.setPages(
//                new CBViewHolderCreator<LocalImageHolderView>() {
//                    @Override
//                    public LocalImageHolderView createHolder() {
//                        return new LocalImageHolderView();
//                    }
//                }, localImages)
//                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
//        convenientBanner.startTurning(2000);
        showCourse();
        convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                EventBus.getDefault().post("特大通知 特大通知","message");
            }
        });
    }

    private void showCourse() {
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.course_pop,null);
        ListView courseList = (ListView) view.findViewById(R.id.course_pop_list);
        CourseAdapter courseAdapter = new CourseAdapter(SubjectUtil.getSubjectList(),getActivity());
        courseList.setAdapter(courseAdapter);
        coursePop = new PopupWindow(view);
        coursePop.setFocusable(true);
//        coursePop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width);
        coursePop.setHeight(height/15 * 14);
        coursePop.setOutsideTouchable(true);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coursePop.dismiss();
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",Integer.parseInt(SubjectUtil.getSubjectList().get(position).getSubjectId())).get());
            }
        });
    }

    @Override
    public void initData() {
//        mMyInfo = JMessageClient.getMyInfo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i > 0; i++) {
                    try {
                        Thread.sleep(2000);
                        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
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
        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());


        searchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    if (!TextUtils.isEmpty(searchEdit.getText().toString())) {
                        startActivity(SearchActivity_.intent(getActivity()).extra("search", searchEdit.getText().toString()).get());
                    }else {
                        Toast.makeText(getActivity(), "请输入搜索条件", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.student_address_tv:
                new Demand().getBestDemand(Integer.parseInt(User_id.getUid()),
                        User_id.getLat()+"",""+User_id.getLng()
                        ,User_id.getProvince(),
                        User_id.getCity(),User_id.getStatus(),this);
                break;
            case R.id.search_image:
//                startActivity(SearchActivity_.intent(getActivity()).get());
                if (!TextUtils.isEmpty(searchEdit.getText().toString())) {
                    startActivity(SearchActivity_.intent(getActivity()).extra("search", searchEdit.getText().toString()).get());
                }else {
                    Toast.makeText(getActivity(), "请输入搜索条件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.teacher_address_image:
                startActivityForResult(new Intent(getActivity(), AmapActivity.class), REQUEST_CODE_MAP);
                break;
            case R.id.teacher_message:
                //聊天
                Intent intent14 = new Intent();
                intent14.setClass(getActivity(), MainActivity.class);
                startActivity(intent14);
                break;
            case R.id.teacher_my:
                //聊天
                Intent intent13 = new Intent();
                intent13.setClass(getActivity(), MainActivity.class);
                startActivity(intent13);
                break;
            case R.id.text208:
               startActivity(DemandActivity_.intent(getContext()).extra("subjectId",208).get());
                break;
            case R.id.text212:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",212).get());
                break;
            case R.id.text213:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",213).get());
                break;
            case R.id.text217:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",217).get());
                break;
            case R.id.text218:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",218).get());
                break;
            case R.id.text221:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",221).get());
                break;
            case R.id.text220:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",220).get());
                break;
            case R.id.text200:
                showSubject();
//                coursePop.showAtLocation(searchImage, Gravity.CENTER,0,0);
//                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",0).get());
                break;
            case R.id.teacher_more:
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",0).get());
                break;
            case R.id.tuijian_demand_rl1:
                //进入学生

                startActivity(DemandDetailActivity_.intent(getActivity())
                        .extra("user_id",userid1)
                        .extra("fee",fee1)
                        .extra("publisher_id",publisherId1)
                        .extra("course_id",courseId1)
                        .extra("grade_id",gradeId1)
                        .get());

//                Intent intent = new Intent(getActivity(), Xuqiuxiangx.class);
//                intent.putExtra("user_id",userid1);
//                intent.putExtra("fee",fee1);
//                intent.putExtra("publisher_id",publisherId1);
//                intent.putExtra("course_id",courseId1);
//                intent.putExtra("grade_id",gradeId1);
//                startActivity(intent);
                break;
            case R.id.tuijian_demand_rl2:
                //进入学生
                startActivity(DemandDetailActivity_.intent(getActivity())
                        .extra("user_id",userid2)
                        .extra("fee",fee2)
                        .extra("publisher_id",publisherId2)
                        .extra("course_id",courseId2)
                        .extra("grade_id",gradeId2)
                        .get());
//                Intent intent1 = new Intent(getActivity(), Xuqiuxiangx.class);
//                intent1.putExtra("user_id",userid2);
//                intent1.putExtra("fee",fee2);
//                intent1.putExtra("publisher_id",publisherId2);
//                intent1.putExtra("course_id",courseId2);
//                intent1.putExtra("grade_id",gradeId2);
//                startActivity(intent1);
                break;
            case R.id.huodong_image:
                Toast.makeText(getContext(), "huodong", Toast.LENGTH_SHORT).show();
                break;
        }
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
                int subjectId = Integer.parseInt(SubjectUtil.getSubjectList().get(which).getSubjectId());
                startActivity(DemandActivity_.intent(getContext()).extra("subjectId",subjectId).get());

            }
        });
        subjectDialog.show();
        subjectDialog.setCancelable(true);
    }

    private String userid1;
    private String fee1;
    private String publisherId1;
    private String courseId1;
    private String gradeId1;
    private String userid2;
    private String fee2;
    private String publisherId2;
    private String courseId2;
    private String gradeId2;
    protected static final int REQUEST_CODE_MAP = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAP) { // location
                String latitude = data.getStringExtra("latitude");
                String longitude = data.getStringExtra("longitude");
                String locationAddress = data.getStringExtra("address");
                String cityAddress = data.getStringExtra("city");
                if (locationAddress != null && !locationAddress.equals("")) {
                    cityTv.setText(cityAddress);
                }
                new Demand().getBestDemand(Integer.parseInt(User_id.getUid()),
                        latitude,longitude,User_id.getProvince(),
                        User_id.getCity(),cityAddress,this);
                User_id.setLat(Double.parseDouble(latitude));
                User_id.setLng(Double.parseDouble(longitude));
                User_id.setStatus(cityAddress);
                User_id.setAddress(locationAddress);
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

        }
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
        new Demand().getBestDemand(Integer.parseInt(User_id.getUid()),User_id.getLat()+"",""+User_id.getLng(),User_id.getProvince(),User_id.getCity(),User_id.getStatus(),this);

//      demand.getDemand_list(id, Integer.parseInt(User_id.getRole()), 0, 0, "2016-08-10", 0, page, User_id.getLat(), User_id.getLng(), null, null, this
//                ,gradeId,subjectId,"created","");

    }

    @Override
    public void Updatecuowu(Map<String, Object> map) {
        Toast.makeText(getActivity(),"定位失败!!请开启手机定位!",Toast.LENGTH_LONG).show();
        new Demand().getBestDemand(Integer.parseInt(User_id.getUid()),lat+"",""+lng,"","","",this);

    }

    @Override
    public void Updatecontent(final Map<String, Object> map) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                    try {
                        Thread.sleep(2000);
                        mMyInfo = JMessageClient.getMyInfo();
                        if (map.get("gender") != null) {
                            if (map.get("gender").equals("1")||map.get("gender").equals("男")) {
                                mMyInfo.setGender(UserInfo.Gender.male);
                            } else {
                                mMyInfo.setGender(UserInfo.Gender.female);
                            }
                        }

                        ThreadUtil.runInThread(new Runnable() {
                            @Override
                            public void run() {
                                JMessageClient.updateMyInfo(UserInfo.Field.gender, mMyInfo, new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                        } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                                        }
                                    }
                                });
                            }
                        });
                        if (map.get("signature") != null) {
                            mMyInfo.setSignature(map.get("signature")+"");
                            ThreadUtil.runInThread(new Runnable() {
                                @Override
                                public void run() {
                                    JMessageClient.updateMyInfo(UserInfo.Field.signature, mMyInfo, new BasicCallback() {
                                        @Override
                                        public void gotResult(int responseCode, String responseMessage) {
                                            if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                            } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                        if (map.get("mobile").equals(map.get("nickname"))){
                        }else {
                            mMyInfo.setNickname(map.get("nickname")+"");
                            ThreadUtil.runInThread(new Runnable() {
                                @Override
                                public void run() {
                                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                                        @Override
                                        public void gotResult(int responseCode, String responseMessage) {
                                            if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                            } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                                            }
                                        }
                                    });
                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }).start();

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        swipeRefreshLayout.setRefreshing(false);
        userid1 = maps.get(0).get("id") + "";
        fee1 = maps.get(0).get("fee") + "";
        publisherId1 = maps.get(0).get("publisher_id") +"";
        courseId1 = maps.get(0).get("course_id") + "";
        gradeId1 = maps.get(0).get("grade_id" ) + "";
        userid2 = maps.get(1).get("id") + "";
        fee2 = maps.get(1).get("fee" )+ "";
        publisherId2 = maps.get(1).get("publisher_id") + "";
        courseId2 = maps.get(1).get("course_id") + "";
        gradeId2 = maps.get(1).get("grade_id") + "";
        headImage1.setImageURI(Uri.parse(""+maps.get(0).get("publisher_headimg")));
        headImage2.setImageURI(Uri.parse(""+maps.get(1).get("publisher_headimg")));
//        Glide.with(this).load(maps.get(0).get("publisher_headimg")).transform(
//                new GlideCircleTransform(getActivity().getApplicationContext())).into(headImage1);
//        Glide.with(this).load(maps.get(1).get("publisher_headimg")).transform(
//                new GlideCircleTransform(getActivity().getApplicationContext())).into(headImage2);
        nameTv1.setText(maps.get(0).get("publisher_name")+"");
        nameTv2.setText(maps.get(1).get("publisher_name")+"");
        serviceTv1.setText(""+maps.get(0).get("service_type_txt"));
        serviceTv2.setText(""+maps.get(1).get("service_type_txt"));
        courseTv1.setText(""+maps.get(0).get("course_name") +"    "+maps.get(0).get("teacher_version")+"");
        courseTv2.setText(""+maps.get(1).get("course_name") +"    "+maps.get(1).get("teacher_version")+"");
        contentTv1.setText(""+maps.get(0).get("content"));
        contentTv2.setText(""+maps.get(1).get("content"));
        priceTv1.setText(maps.get(0).get("low_price")+"--"+maps.get(0).get("high_price")+"元");
        priceTv2.setText(maps.get(1).get("low_price")+"--"+maps.get(1).get("high_price")+"元");
//        entity.setFee(maps.get(i).get("low_price")+"--"+maps.get(i).get("high_price")+"元");
        gradeTv1.setText(maps.get(0).get("grade_name")+"");
        gradeTv2.setText(maps.get(1).get("grade_name")+"");
        String dist1 = maps.get(0).get("distance")+"";
        String dist2 = maps.get(1).get("distance")+"";
        double myDist1 = 0;
        double myDist2 = 0;
        if (!dist1.equals("")){
            myDist1 = Double.parseDouble(dist1)/1000;
        }
        if (!dist2.equals("")){
            myDist2 = Double.parseDouble(dist2)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");

        if (!TextUtils.isEmpty(maps.get(0).get("city")+"")){
            distanceTv1.setText(maps.get(0).get("city")+"  "+maps.get(0).get("state")+"   "+"距我"+df.format(myDist1)+"km");
        }
        if (!TextUtils.isEmpty(maps.get(1).get("city")+"")){
            distanceTv2.setText(maps.get(1).get("city")+"  "+maps.get(1).get("state")+"   "+"距我"+df.format(myDist2)+"km");
        }

        haopingTv1.setText(maps.get(0).get("created")+"");
        haopingTv2.setText(""+maps.get(1).get("created"));

//        if ((maps.get(0).get("address")+"").length()>7) {
//            distanceTv1.setText(df.format(myDist1)+"km      "+((String) maps.get(0).get("address")).substring(0, 7) + "......");
//        }else {
//            distanceTv1.setText(df.format(myDist1)+"km      "+((String) maps.get(0).get("address")));
//        }
//        if ((maps.get(1).get("address")+"").length()>7) {
//            distanceTv2.setText(df.format(myDist2)+"km      "+((String) maps.get(1).get("address")).substring(0, 7) + "......");
//        }else {
//            distanceTv2.setText(df.format(myDist2)+"km      "+((String) maps.get(1).get("address")));
//        }




    }

    @Override
    public void failSimilarXuqiu(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void successTurnover(List<Map<String, Object>> list) {
        for (int i = 0; i < list.size(); i++) {
            localImages.add(list.get(i).get("img")+"");
        }
//        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/1.jpg");
////        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/2.jpg");
////        localImages.add("http://61.153.111.94:9418/home_hjktv/Tpl/default/Room/ad/3.jpg");
//        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(2000);
    }

    @Override
    public void failTurnover(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        new Demand().getBestDemand(Integer.parseInt(User_id.getUid()),
                User_id.getLat()+"",""+User_id.getLng()
                ,User_id.getProvince(),
                User_id.getCity(),User_id.getStatus(),this);
    }


    public class LocalImageHolderView implements Holder<String> {
        private SimpleDraweeView imageView;

        @Override
        public View createView(Context context) {
            imageView = new SimpleDraweeView(context);
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getResources());
            GenericDraweeHierarchy hierarchy = builder
                    .setActualImageScaleType(ScalingUtils.ScaleType.FIT_XY)
                    .setPlaceholderImage(getContext().getResources().getDrawable(R.mipmap.banner1))
                    .build();
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setHierarchy(hierarchy);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
//            imageView.setImageResource(data);
            imageView.setImageURI(Uri.parse(data));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     * 收到消息
     */
    public void onEvent(MessageEvent event) {
        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
    }
    /**
            * 接收离线消息
     *
             * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
    }

    public void onEvent(MessageRetractEvent event) {
        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
    }

    /**
     * 消息漫游完成事件
     *
     * @param event 漫游完成后， 刷新会话事件
     */
    public void onEvent(ConversationRefreshEvent event) {
        setUnReadMsg(JMessageClient.getAllUnReadMsgCount());
    }





    public void setUnReadMsg(final int count) {
        ThreadUtil.runInUiThread(new Runnable() {
            @Override
            public void run() {
                if (unreadAddressLable != null) {
                    if (count > 0) {
                        unreadAddressLable.setVisibility(View.VISIBLE);
                        if (count < 100) {
                            unreadAddressLable.setText(count + "");
                        } else {
                            unreadAddressLable.setText("99+");
                        }
                    } else {
                        unreadAddressLable.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
