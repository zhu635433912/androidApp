package com.deguan.xuelema.androidapp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deguan.xuelema.androidapp.R;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import modle.Adapter.HomeTitleAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_new_order)
public class NewOrderFragment extends BaseFragment {
    @ViewById(R.id.my_order_tablayout)
    TabLayout tableLayout;
    @ViewById(R.id.my_order_vp)
     ViewPager viewPager;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    public void initData() {
        titles.add("未完成");
        titles.add("进行中");
        titles.add("待评价");
        titles.add("已完成");
        fragments.add(NotFinishFragment_.builder().build());
        fragments.add(ConductFragment_.builder().build());
        fragments.add(EvaluationFragment_.builder().build());
        fragments.add(Completefragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
        tableLayout.setTabMode(TabLayout.MODE_FIXED);
//        viewPager.setOffscreenPageLimit(0);
    }

}
