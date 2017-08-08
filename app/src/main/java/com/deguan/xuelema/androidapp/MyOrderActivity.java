package com.deguan.xuelema.androidapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.deguan.xuelema.androidapp.fragment.Completefragment_;
import com.deguan.xuelema.androidapp.fragment.ConductFragment_;
import com.deguan.xuelema.androidapp.fragment.EvaluationFragment;
import com.deguan.xuelema.androidapp.fragment.EvaluationFragment_;
import com.deguan.xuelema.androidapp.fragment.NotFinishFragment_;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import modle.Adapter.HomeTitleAdapter;
import modle.user_ziliao.User_id;

public class MyOrderActivity extends AutoLayoutActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private ImageButton backImage;
    private RelativeLayout stuentordentfanhui;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User_id.getInstance().addActivity(this);
        setContentView(R.layout.activity_my_order);
        stuentordentfanhui = (RelativeLayout) findViewById(R.id.stuentordentfanhui);
        tableLayout = (TabLayout) findViewById(R.id.my_order_tablayout);
        viewPager = (ViewPager) findViewById(R.id.my_order_vp);
        backImage = (ImageButton) findViewById(R.id.my_order_back);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(NewMainActivity_.intent(MyOrderActivity.this).get());
                finish();
            }
        });
        stuentordentfanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initData();
    }

    private void initData() {
        titles.add("未完成");
        titles.add("进行中");
        titles.add("待评价");
        titles.add("已完成");
        fragments.add(NotFinishFragment_.builder().build());
        fragments.add(ConductFragment_.builder().build());
        fragments.add(EvaluationFragment_.builder().build());
        fragments.add(Completefragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
