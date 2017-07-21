package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.fragment.BaseFragment;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment_;
import com.deguan.xuelema.androidapp.fragment.NewOrderFragment;
import com.deguan.xuelema.androidapp.fragment.NewOrderFragment_;
import com.deguan.xuelema.androidapp.fragment.OrderFragment_;
import com.deguan.xuelema.androidapp.fragment.TestFragment;
import com.deguan.xuelema.androidapp.fragment.TuijianFragment_;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.hyphenate.chat.EMClient;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.HomeTitleAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.toos.CircleImageView;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

@EFragment(R.layout.activity_new__student)
public class New_StudentFragment extends BaseFragment implements View.OnClickListener,Student_init,
        Requirdetailed {

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int id;
    private int role;
    Demand_init demand_init;
    private User_init user_init;
    private int TAGE_IRONG=0;

    @ViewById(R.id.unread_address_number)
    TextView unreadLabel;
    @ViewById(R.id.new_student_viewpage)
    ViewPager viewPager;
    @ViewById(R.id.new_student_tablayout)
    TabLayout tabLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.chat_icon)
    ImageView chatImage;
    @ViewById(R.id.huihua)
    TextView huihua;
    @ViewById(R.id.stidentshezhiimabt)
    RelativeLayout stidentshezhiimabt;
    @ViewById(R.id.studentwodeqianbao)
    RelativeLayout studentwodeqianbao;
    @ViewById(R.id.studenttouxiangimg)
    ImageView studenttouxiangimg;
    @ViewById(R.id.studentusernametext)
    TextView studentusernametext;
    @ViewById(R.id.studentneirongtext)
    TextView studentneirongtext;
    @ViewById(R.id.student_chat)
    ImageView studentChatImage;
    @ViewById(R.id.student_my)
    ImageView studentMychatImage;

    @Override
    public void before() {
        //获取用户id与角色
        String ida= User_id.getUid();
        String rolea=User_id.getRole();
        id=Integer.parseInt(ida);
        role=Integer.parseInt(rolea);
        EventBus.getDefault().register(this);
        //获取用户资料
        user_init=new User_Realization();

        //获取用户自己的需求 这里开始
//        wodefabulist.setInterface(this);
        demand_init=new Demand();

    }

    @Override
    public void initView() {

//        wodegerxxstudent.setBackgroundResource(R.drawable.hly48);
//        wodegerxxstudenttext.setTextColor(Color.parseColor("#f7e61c"));
        studentwodeqianbao.bringToFront();
        stidentshezhiimabt.bringToFront();

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        huihua.setOnClickListener(this);
//        wodejiaoshi.setOnClickListener(this);
//        host.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        studenttouxiangimg.setOnClickListener(this);
        studentwodeqianbao.setOnClickListener(this);
        stidentshezhiimabt.setOnClickListener(this);
        studentChatImage.setOnClickListener(this);
        studentMychatImage.setOnClickListener(this);
    }

    @Override
    public void initData() {
        titles.add("我的发布");
//        titles.add("推荐教师");
        titles.add("我的订单");
        titles.add("推荐教师");
        fragments.add(MyPublishFragment_.builder().build());
        fragments.add(NewOrderFragment_.builder().build());
        fragments.add(TuijianFragment_.builder().build());
//        fragments.add(OrderFragment_.builder().build());
//        fragments.add(TuijianFragment_.builder().build());
//        fragments.add(new TestFragment());
//        fragments.add(new TestFragment());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        viewPager.setOffscreenPageLimit(0);

        demand_init.getMyDemand_list(id,4,this);
        user_init.User_Data(id,User_id.getLat()+"",User_id.getLng()+"",this);
    }

    @Override
    public void onClick(View v) {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        switch (v.getId()){
            case R.id.student_chat:
                //会话列表
                Intent intent1 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.student_my:
                //会话列表
                Intent intent11 = new Intent(getActivity(), modle.Huanxing.ui.NewHuanxinMainActivity.class);
                startActivity(intent11);
                break;
            case R.id.studentwodeqianbao:
                //设置
                startActivity(new Intent(getActivity(), SetUp.class));
                if(version > 5 ){
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
                break;
            case R.id.stidentshezhiimabt:
                //钱包
                startActivity(new Intent(getActivity(),FeeqianbaoActivty.class));
                if(version > 5 ){
                    getActivity().overridePendingTransition(R.anim.animo_no, R.anim.slide_in_bottom);
                }

                break;
            case R.id.studenttouxiangimg:
                //个人信息
                Intent intentb=new Intent(getActivity(),Personal_Activty.class);
                startActivity(intentb);
                break;
//
            case R.id.studenthost:
                Intent intentt=new Intent(getActivity(),Xuqiufabu.class);
                startActivity(intentt);
                break;
//
            case R.id.chat_icon:
                //会话列表
                Intent intent2 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent2);

                break;
            case R.id.huihua:
                //会话列表
                Intent intent21 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent21);
                break;
        }
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
    }
    @Subscriber(tag = "headUrl")
    public void updateHead(File msg){
        Glide.with(this).load(msg).transform(new GlideCircleTransform(getActivity())).into(studenttouxiangimg);
    }

    @Subscriber(tag = "update")
    public void updateMsg(String msg){
        user_init.User_Data(id,User_id.getLat()+"",User_id.getLng()+"",this);
    }
    @Override
    public void Updatecontent(Map<String, Object> map) {
        if(map.get("nickname").toString()!=null) {
            studentusernametext.setText(map.get("nickname")+"");
            studentneirongtext.setText(map.get("signature")+"");
            Glide.with(this).load(map.get("headimg").toString()).transform(new GlideCircleTransform(getActivity())).into(studenttouxiangimg);
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=new HashMap<String,Object>();
        map=listmap.get(0);
        Toast.makeText(getActivity(),map.get("text").toString(),Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
////        user_init.User_Data(id,User_id.getLat()+"",User_id.getLng()+"",this);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUnreadLabel();
    }

    public void updateUnreadLabel() {
        int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(count +"");
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }
}
