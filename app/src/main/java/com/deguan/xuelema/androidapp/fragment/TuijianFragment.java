package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.presenter.impl.TuijianPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.TuijianAdapter;

@EFragment(R.layout.fragment_tuijian)
public class TuijianFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener, TuijianView{

    @ViewById(R.id.tuijian_listview)
    PullToRefreshListView listView;

    private TuijianAdapter adapter;
    private List<TuijianEntity> list = new ArrayList<>();
    private int courseid =1;
    private int grade_id = 1;
    private String address = "路桥区";
    private String lat = "0";
    private String lng = "0";
    private TuijianPresenterImpl tuijianPresenter;

    @Override
    public void before() {
    }

    @Override
    public void initView() {
        adapter = new TuijianAdapter(list,getContext());
        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        tuijianPresenter =  new TuijianPresenterImpl(this,courseid,grade_id,address,lat,lng);
        tuijianPresenter.getTuijianEntity();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
//                    intent.putExtra("user_id", uid);
                intent.putExtra("head_image",list.get(position-1).getUser_headimg());
                intent.putExtra("user_id",list.get(position-1).getUser_id());
                startActivity(intent);
            }
        });
    }


    @Override
    public void successTuijian(List<Map<String, Object>> maps) {
        listView.onRefreshComplete();
        list.clear();
        for (int i = 0; i < maps.size(); i++) {
            TuijianEntity entity = new TuijianEntity();
            entity.setNickname((String) maps.get(i).get("nickname"));
            entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setSignature((String) maps.get(i).get("signature"));
            entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
            entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
            entity.setUser_id((String) maps.get(i).get("user_id"));
            entity.setGender((String) maps.get(i).get("gender"));
//            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setSpeciality(String.valueOf(maps.get(i).get("speciality")));
            entity.setUsername(String.valueOf(maps.get(i).get("username")));
            entity.setHaoping_num(String.valueOf(maps.get(i).get("haoping_num")));
            list.add(entity);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failTuijian(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new TuijianPresenterImpl(this,courseid,grade_id,address,lat,lng).getTuijianEntity();
    }
}
