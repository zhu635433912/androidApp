package com.deguan.xuelema.androidapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_tuijian)
public class NotFinishFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, TuijianView {

    @ViewById(R.id.tuijian_listview)
    PullToRefreshListView listView;

    @Override
    public void initView() {

    }

    @Override
    public void successTuijian(List<Map<String, Object>> maps) {

    }

    @Override
    public void failTuijian(String msg) {

    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {

    }
}
