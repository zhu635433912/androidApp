package com.deguan.xuelema.androidapp.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.FeeqianbaoActivty;
import com.deguan.xuelema.androidapp.Personal_Activty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SetUp;
import com.deguan.xuelema.androidapp.Teacher_management;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.HomeTitleAdapter;
import modle.toos.CircleImageView;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
@EFragment(R.layout.teacher_fragmengt)
public class Teacher_infofragment extends BaseFragment implements Requirdetailed ,View.OnClickListener{
    @ViewById
    ImageView techaer_massge;
    @ViewById
    ImageView teacher_setup;
    @ViewById
    ImageView myfee;
    @ViewById
    CircleImageView teacher_loc;
    @ViewById
    ImageView huanxin_but;
    @ViewById
    TextView teacher_name;
    @ViewById
    TextView teacher_autograph;
    @ViewById
    TabLayout new_teacher_tablayout;
    @ViewById
    ViewPager new_techaer_viewpage;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;

    private int uid;
    private int role;

    private List<String> tlebat=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();

    @Override
    public void before() {
        uid = Integer.parseInt(User_id.getUid());
        role = Integer.parseInt(User_id.getRole());
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        teacher_loc.setOnClickListener(this);
        techaer_massge.setOnClickListener(this);
        teacher_setup.setOnClickListener(this);
        myfee.setOnClickListener(this);
        huanxin_but.setOnClickListener(this);
    }

    @Override
    public void initData() {
        User_init user_init = new User_Realization();
        user_init.User_Data(uid,User_id.getLat()+"",User_id.getLng()+"", this);

        tlebat.add("推荐需求");
        tlebat.add("我的订单");
        fragments.add(DmadFragmengt_.builder().build());
        fragments.add(OrderFragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getFragmentManager(),fragments,tlebat);
        new_techaer_viewpage.setAdapter(adapter);
        new_teacher_tablayout.setupWithViewPager(new_techaer_viewpage);
        new_teacher_tablayout.setTabMode(TabLayout.MODE_FIXED);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.techaer_massge:
                Intent intent = new Intent();
                intent.setClass(getActivity(), Teacher_management.class);
                startActivity(intent);
                break;
            case R.id.teacher_setup:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), SetUp.class);
                startActivity(intent1);
                break;
            case R.id.myfee:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), FeeqianbaoActivty.class);
                startActivity(intent2);
                break;
            case R.id.huanxin_but:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent3);
                break;
            case R.id.teacher_loc:
                Intent intent4 = new Intent(getActivity(), Personal_Activty.class);
                startActivity(intent4);
        }
    }

    @Subscriber(tag = "headUrl")
    private void updateHead(File msg){
//        Glide.with(this).load(msg).into(teacher_loc);
        Glide.with(this).load(msg).into(teacher_loc);
    }

    /**
     * 获取用户资料成功回调并设置
     * @param map
     */
    @Override
    public void Updatecontent(Map<String, Object> map) {
        Glide.with(this).load(map.get("headimg").toString()).into(teacher_loc);
        teacher_name.setText(map.get("nickname")+"");
        teacher_autograph.setText(map.get("signature")+"");
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
