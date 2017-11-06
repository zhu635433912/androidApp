package com.deguan.xuelema.androidapp.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.deguan.xuelema.androidapp.CompleteActivity_;
import com.deguan.xuelema.androidapp.DemandActivity_;
import com.deguan.xuelema.androidapp.Distribution_Activty;
import com.deguan.xuelema.androidapp.FeeqianbaoActivty;
import com.deguan.xuelema.androidapp.IngActivity_;
import com.deguan.xuelema.androidapp.ManagerActivity;
import com.deguan.xuelema.androidapp.ManagerActivity_;
import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.MyPublishActivity_;
import com.deguan.xuelema.androidapp.MyZxingActivity_;
import com.deguan.xuelema.androidapp.MynofinishActivity_;
import com.deguan.xuelema.androidapp.NoevaActivity_;
import com.deguan.xuelema.androidapp.Personal_Activty;
import com.deguan.xuelema.androidapp.PictureZoo;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SetUp;
import com.deguan.xuelema.androidapp.StudentVipActivity_;
import com.deguan.xuelema.androidapp.TeacherActivity2_;
import com.deguan.xuelema.androidapp.TeacherManActivity;
import com.deguan.xuelema.androidapp.TeacherManActivity_;
import com.deguan.xuelema.androidapp.TeacherVipActivity;
import com.deguan.xuelema.androidapp.TeacherVipActivity_;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.GPSUtil;
import com.deguan.xuelema.androidapp.utils.WaveView;
import com.deguan.xuelema.androidapp.viewimpl.FlexibleScrollView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.getdata.PayUtil;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_my_teacher)
public class MyTeacherFragment extends MyBaseFragment implements Requirdetailed, View.OnClickListener, FlexibleScrollView.ISmartScrollChangedListener{


    @ViewById(R.id.my_waveview)
    WaveView waveView;
    @ViewById(R.id.my_look_tv)
    TextView lookTv;
    @ViewById(R.id.my_order_tv)
    TextView orderTv;
    @ViewById(R.id.my_progress_bar)
    ProgressBar myPregressbar;
    @ViewById(R.id.flexible_scroll_vew)
    FlexibleScrollView mScrollView;
    @ViewById(R.id.my_map_view)
    TextureMapView mapView;
    @ViewById(R.id.my_leida_image)
    SimpleDraweeView leidaImage;
    @ViewById(R.id.my_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.my_manager_image)
    ImageView managerImage;
    @ViewById(R.id.my_fee_image)
    ImageView feeImage;
    @ViewById(R.id.my_nickname_tv)
    TextView nicknameTv;
    @ViewById(R.id.my_sign_tv)
    TextView signTv;
    @ViewById(R.id.my_set_tv)
    TextView setTv;
    @ViewById(R.id.my_publish_tv)
    TextView publishTv;
    @ViewById(R.id.nopay_number_tv)
    TextView nopayTv;
    @ViewById(R.id.ing_number_tv)
    TextView ingTv;
    @ViewById(R.id.noeva_number_tv)
    TextView noevaTv;
    @ViewById(R.id.complete_number_tv)
    TextView completeTv;
    @ViewById(R.id.my_share_tv)
    TextView shareTv;
    @ViewById(R.id.my_nopay_number)
    TextView nopayNumberTv;
    @ViewById(R.id.my_progress_number)
    TextView ingNumberTv;
    @ViewById(R.id.my_noeva_number)
    TextView noevaNumberTv;
    @ViewById(R.id.my_complete_number)
    TextView completeNumberTv;
    @ViewById(R.id.my_share_ll)
    LinearLayout shareLl;
    @ViewById(R.id.my_search_tv)
    TextView searchTv;
    @ViewById(R.id.nopay_image)
    ImageView nopayImage;
    @ViewById(R.id.progress_image)
    ImageView progressImage;
    @ViewById(R.id.noeva_image)
    ImageView noevaImage;
    @ViewById(R.id.complete_image)
    ImageView completeImage;
    @ViewById(R.id.vip_image)
    ImageView vipImage;

    private String userHeadUrl;

    private BaiduMap mBaiduMap;
    private String nickname;
    private String level;


    @Override
    public void initData() {
        super.initData();
        new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
//        new User_Realization().User_Data(Integer.parseInt(User_id.getUid()),User_id.getLat()+"",""+User_id.getLng(),this);
    }

    @Override
    public void initView() {
        super.initView();

        mBaiduMap = mapView.getMap(); //获得地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); //设置为普通模式的地图
        LatLng cenpt = new LatLng(GPSUtil.gcj02_To_Bd09(User_id.getLat(),User_id.getLng())[0],GPSUtil.gcj02_To_Bd09(User_id.getLat(),User_id.getLng())[1]);

        //定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(cenpt)
                .zoom(15)
                .build();
        //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化

//        //构建Marker图标
////        BitmapDescriptor bitmap = BitmapDescriptorFactory
////                .fromResource(R.mipmap.ic_launcher);
//        OverlayOptions option = new MarkerOptions()
//                .position(cenpt)
//                ;
//        mBaiduMap.addOverlay(option);
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        //改变地图状态
        mBaiduMap.setMapStatus(mMapStatusUpdate);
        // 不显示地图上比例尺
        mapView.showScaleControl(false);
        mapView.removeViewAt(1);
        // 不显示地图缩放控件（按钮控制栏）
        mapView.showZoomControls(false);
        mapView.getMap().getUiSettings().setAllGesturesEnabled(false);

        mScrollView.bindActionBar(getActivity().findViewById(R.id.custom_action_bar));
        mScrollView.setHeaderView(getActivity().findViewById(R.id.flexible_header_view));

        mScrollView.setScanScrollChangedListener(this);


        leidaImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //定义属性动画操作方式rotationY 在Y方向旋转
                ObjectAnimator oa = ObjectAnimator.ofFloat(leidaImage, "rotationY",
                        new float[] { 90f, 180f, 270f, 360f });
// 一个周期时长
                oa.setDuration(3000);
//         重复次数
                oa.setRepeatCount(5);
//         重复方式
                oa.setRepeatMode(ObjectAnimator.RESTART);
                oa.start();
            }
        });
        searchTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waveView.start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
                //定义属性动画操作方式rotationY 在Y方向旋转
                ObjectAnimator oa = ObjectAnimator.ofFloat(leidaImage, "rotationY",
                        new float[] { 90f, 180f, 270f, 360f });
                // 一个周期时长
                oa.setDuration(4000);
                // 重复次数
//                oa.setRepeatCount(2);
                // 重复方式
                oa.setRepeatMode(ObjectAnimator.REVERSE);
                oa.start();
                oa.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        waveView.stop();
                        startActivity(DemandActivity_.intent(getActivity()).extra("subjectId",1).get());
//                        startActivity(TeacherActivity2_.intent(getActivity()).extra("subjectId",0).get());

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }
        });
        headImage.setOnClickListener(this);
        setTv.setOnClickListener(this);
        managerImage.setOnClickListener(this);
        feeImage.setOnClickListener(this);
        orderTv.setOnClickListener(this);
        shareLl.setOnClickListener(this);
        publishTv.setOnClickListener(this);
        nopayImage.setOnClickListener(this);
        progressImage.setOnClickListener(this);
        noevaImage.setOnClickListener(this);
        completeImage.setOnClickListener(this);

        waveView.setDuration(4000);
        waveView.setStyle(Paint.Style.STROKE);
        waveView.setSpeed(700);
        waveView.setMaxRadius(500f);
        waveView.setColor(Color.parseColor("#3D84F5"));
        waveView.setInterpolator(new AccelerateInterpolator(1.2f));

        vipImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("1")){
                    Toast.makeText(getActivity(), "你已经是会员了", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(TeacherVipActivity_.intent(getActivity()).extra("headurl",userHeadUrl).extra("nickname",nickname).get());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
//        myPregressbar.setVisibility(View.GONE);
//        isRefresh = false;
//        if (getActivity()!= null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(500);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                myPregressbar.setVisibility(View.GONE);
//                                isRefresh = false;
//                            }
//                        });
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }
        myPregressbar.setVisibility(View.GONE);
        isRefresh = false;
        if (map.get("level") !=null){
            level = map.get("level") + "";
            if (map.get("level").equals("1")) {
                vipImage.setImageResource(R.mipmap.vip_icon);
            }else {
                vipImage.setImageResource(R.mipmap.isvip_icon);
            }
        }
        if(map.get("nickname")!=null) {
            nickname = map.get("nickname")+"";
            nicknameTv.setText(map.get("nickname")+"");
            if (!TextUtils.isEmpty(map.get("signature")+"")) {
                signTv.setText(map.get("signature") + "");
            }
//            Glide.with(getContext().getApplicationContext()).load(map.get("headimg").toString())
//                    .transform(new GlideCircleTransform(getActivity())).into(studenttouxiangimg);
            headImage.setImageURI(Uri.parse(""+map.get("user_headimg")));
            leidaImage.setImageURI(Uri.parse(""+map.get("user_headimg")));
            userHeadUrl = map.get("user_headimg")+"";
            publishTv.setText("我的课程（"+map.get("course_count")+"）");
            if (!map.get("order_nopay").equals("0")){
                nopayTv.setText("待付款 "+map.get("order_nopay")+"");
                nopayNumberTv.setVisibility(View.VISIBLE);
                nopayNumberTv.setText(map.get("order_nopay")+"");
            }else {
                nopayNumberTv.setVisibility(View.GONE);
            }
            if (!map.get("order_working").equals("0")){
                ingTv.setText("待授课 "+map.get("order_working")+"");
                ingNumberTv.setVisibility(View.VISIBLE);
                ingNumberTv.setText(map.get("order_working")+"");
            }else {
                ingNumberTv.setVisibility(View.GONE);
            }
            if (!map.get("order_tconfirm").equals("0")){
                noevaTv.setText("已授课 "+map.get("order_tconfirm")+"");
                noevaNumberTv.setVisibility(View.VISIBLE);
                noevaNumberTv.setText(map.get("order_tconfirm")+"");
            }else {
                noevaNumberTv.setVisibility(View.GONE);
            }
            if (!map.get("order_finish").equals("0")){
                completeTv.setText("已完成 "+map.get("order_finish")+"");
                completeNumberTv.setVisibility(View.GONE);
                completeNumberTv.setText(map.get("order_finish")+"");
            }else {
                completeNumberTv.setVisibility(View.GONE);
            }
            if ((double)map.get("totaluser")!=0){
                shareTv.setText(BigDecimal.valueOf((Double) map.get("totaluser")).stripTrailingZeros().toPlainString() + "人");
            }
            if (!map.get("click").equals("0")){
                lookTv.setText(map.get("click")+"人看过我");
            }
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Subscriber(tag = "headUrl")
    public void updateHead(File msg){
        headImage.setImageURI(Uri.fromFile(msg));
        leidaImage.setImageURI(Uri.fromFile(msg));
    }

    @Subscriber(tag = "update")
    public void updateMsg(String msg){
        new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){


            case R.id.nopay_image:
                new PayUtil().setRead(Integer.parseInt(User_id.getUid()),1);
                new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
                startActivity(MynofinishActivity_.intent(getActivity()).get());
                break;
            case R.id.progress_image:
                new PayUtil().setRead(Integer.parseInt(User_id.getUid()),2);
                new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
                startActivity(IngActivity_.intent(getActivity()).get());
                break;
            case R.id.noeva_image:
                new PayUtil().setRead(Integer.parseInt(User_id.getUid()),3);
                new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
                startActivity(NoevaActivity_.intent(getActivity()).get());
                break;
            case R.id.complete_image:
                startActivity(CompleteActivity_.intent(getActivity()).get());
                break;

            case R.id.my_publish_tv:
                startActivity(ManagerActivity_.intent(getActivity()).get());
//                startActivity(MyPublishActivity_.intent(getActivity()).get());
                break;
            case R.id.my_share_ll:

//                startActivity(TeacherVipActivity_.intent(getActivity()).extra("headurl",userHeadUrl).extra("nickname",nickname).get());

                //我的推广
//                Intent intent1=new Intent(getActivity(),Distribution_Activty.class);
//                startActivity(intent1);
                startActivity(MyZxingActivity_.intent(getActivity()).get());
//                startActivity(StudentVipActivity_.intent(getActivity()).get());
                break;
            case R.id.my_order_tv:
                startActivity(new Intent(getActivity(),MyOrderActivity.class));
                break;
            case R.id.my_manager_image:
                //个人信息
                startActivity(TeacherManActivity_.intent(getActivity()).get());
                break;
            case R.id.my_fee_image:
                //钱包
                startActivity(new Intent(getActivity(),FeeqianbaoActivty.class));

                break;
            case R.id.my_set_tv:
                //设置
                startActivity(new Intent(getActivity(), SetUp.class));
                break;
            case R.id.my_head_image:
                if (userHeadUrl != null) {
                    Intent intent2 = new Intent(getActivity(), PictureZoo.class);
                    intent2.putExtra("hide", userHeadUrl);
                    startActivity(intent2);
                }
                break;

        }
    }

    private boolean isRefresh;

    @Override
    public void onScrolledToTop() {
        if (isRefresh) {

        }else {
            isRefresh = true;
            myPregressbar.setVisibility(View.VISIBLE);
            new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
//            Toast.makeText(getActivity(), "大顶部了", Toast.LENGTH_SHORT).show();
        }
    }



}
