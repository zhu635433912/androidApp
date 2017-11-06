package com.deguan.xuelema.androidapp.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.deguan.xuelema.androidapp.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.simple.eventbus.EventBus;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.LoginActivity;
import jiguang.chat.activity.MainActivity;
import jiguang.chat.activity.fragment.BaseFragment;
import jiguang.chat.utils.DialogCreator;
import jiguang.chat.utils.FileHelper;
import jiguang.chat.utils.SharePreferenceManager;
import view.login.ViewActivity.LoginAcitivity;


/**
 * fragment基类
 * A simple {@link Fragment} subclass.
 */
@EFragment
public class MyBaseFragment extends BaseFragment {
    private Dialog dialog;

    private UserInfo myInfo;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mWidth;
    protected int mHeight;
    protected float mRatio;
    protected int mAvatarSize;
    private Context mContext;



    @AfterInject
    public void before(){
        EventBus.getDefault().register(this);
            mContext = this.getActivity();
            //订阅接收消息,子类只要重写onEvent就能收到消息
            JMessageClient.registerEventReceiver(this);
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            mDensity = dm.density;
            mDensityDpi = dm.densityDpi;
            mWidth = dm.widthPixels;
            mHeight = dm.heightPixels;
            mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
            mAvatarSize = (int) (50 * mDensity);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
