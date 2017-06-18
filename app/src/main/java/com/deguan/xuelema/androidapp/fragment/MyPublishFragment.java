package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.Pick_singleActivty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.presenter.PublishPresenter;
import com.deguan.xuelema.androidapp.presenter.impl.PublishPresenterImpl;
import com.deguan.xuelema.androidapp.presenter.impl.TuijianPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.MyPublishView;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.MfabuAdpter;
import modle.Adapter.TuijianAdapter;
import modle.user_ziliao.User_id;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_tuijian)
public class MyPublishFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, MyPublishView {

    @ViewById(R.id.tuijian_listview)
    PullToRefreshListView listView;

    private MfabuAdpter adapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private int filter_type;
    private PublishPresenter publishPresenter;

    @Override
    public void before() {
    }

    @Override
    public void initView() {
        adapter = new MfabuAdpter(list,getContext());
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        publishPresenter =  new PublishPresenterImpl(this, Integer.parseInt(User_id.getUid()),4);
        publishPresenter.getPublishEntity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转接单列表
//                    String uid=adapter.getuid(position-1);
                    String uid = (String)list.get(position-1).get("id");
                    Intent intent=new Intent(getContext(),Pick_singleActivty.class);
                    intent.putExtra("id",uid);
                    startActivity(intent);
            }
        });
    }



    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new PublishPresenterImpl(this,Integer.parseInt(User_id.getUid()),4).getPublishEntity();
    }

    @Override
    public void successMyPublish(List<Map<String, Object>> maps) {
        listView.onRefreshComplete();
        list.clear();
       list.addAll(maps);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failMyPublish(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
