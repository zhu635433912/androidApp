package com.deguan.xuelema.androidapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.deguan.xuelema.androidapp.fragment.NotFinishFragment;
import com.deguan.xuelema.androidapp.fragment.NotFinishFragment_;
import com.deguan.xuelema.androidapp.fragment.OrderFragment_;
import com.deguan.xuelema.androidapp.fragment.TuijianFragment_;

import java.util.ArrayList;
import java.util.List;

import modle.Adapter.HomeTitleAdapter;

public class MyOrderActivity extends AppCompatActivity {

    private TabLayout tableLayout;
    private ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        tableLayout = (TabLayout) findViewById(R.id.my_order_tablayout);
        viewPager = (ViewPager) findViewById(R.id.my_order_vp);
        initData();
    }

    private void initData() {
        titles.add("未完成");
        titles.add("进行中");
        titles.add("已完成");
        titles.add("待评价");
        fragments.add(NotFinishFragment_.builder().build());
        fragments.add(NotFinishFragment_.builder().build());
        fragments.add(NotFinishFragment_.builder().build());
        fragments.add(NotFinishFragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
