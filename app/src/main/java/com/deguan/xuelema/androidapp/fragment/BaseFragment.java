package com.deguan.xuelema.androidapp.fragment;


import android.support.v4.app.Fragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;


/**
 * fragment基类
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class BaseFragment extends Fragment {

    @AfterInject
    public void before(){

    }

    @AfterViews
    public final void init(){
        initView();
        initData();
    }

    public void initData() {

    }

    public void initView() {

    }

}
