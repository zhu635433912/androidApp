package com.deguan.xuelema.androidapp.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.List;

/**
 * Created by dell on 2016/3/4.
 */
public class FragmentTabUtils implements RadioGroup.OnCheckedChangeListener {
    private FragmentManager manager;
    private List<Fragment> fragments;
    private int containerId;
    private int index;
    private OnTabCheckListener listener;
    private Context context;

    public FragmentTabUtils(Context context, FragmentManager manager, RadioGroup radioGroup, List<Fragment> fragments, int containerId) {
        this(context,manager, radioGroup, fragments, containerId,null);
    }

    public FragmentTabUtils(Context context, FragmentManager manager, RadioGroup radioGroup, List<Fragment> fragments, int containerId, OnTabCheckListener listener) {
        this.manager = manager;
        this.fragments = fragments;
        this.containerId = containerId;
        this.listener = listener;
        radioGroup.setOnCheckedChangeListener(this);
        RadioButton radioButton = (RadioButton) radioGroup.getChildAt(0);
        radioButton.setChecked(true);
    }

    public FragmentTransaction getFragmentTransaction(){
        FragmentTransaction transaction = manager.beginTransaction();
        return transaction;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        for (int i = 0; i < group.getChildCount(); i++) {
            Fragment fragment = fragments.get(i);
            if (group.getChildAt(i).getId() == checkedId) {
                if (!fragment.isAdded()){
                    getFragmentTransaction().add(containerId,fragment).commit();
                }
                index = i;
                getFragmentTransaction().show(fragment).commit();
//                ((RadioButton)group.getChildAt(i)).setTextColor(context.getResources().getColor(R.color.title_backgroud));
                //第一次加载显示第一张fragment
                if (listener != null){
                    listener.onTabCheckListener(group,checkedId,index);
                }
            }else {
                getFragmentTransaction().hide(fragment).commit();
//                ((RadioButton)group.getChildAt(i)).setTextColor(context.getResources().getColor(R.color.radiobtn_text));
            }
        }
    }

    public void setListener(OnTabCheckListener listener) {
        this.listener = listener;
    }

    public interface OnTabCheckListener{
        public void onTabCheckListener(RadioGroup group, int checkedId, int index);
    }
}
