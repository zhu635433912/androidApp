package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
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
import modle.Adapter.TuijianNewAdapter;
import modle.user_ziliao.User_id;

/**
 * 推荐
 */
@EFragment(R.layout.tuijian_new_fragment)
public class TuijianFragment extends BaseFragment implements  TuijianView, SwipeRefreshLayout.OnRefreshListener, TuijianNewAdapter.OnTopClickListener {

    @ViewById(R.id.tuijian_listview)
    RecyclerView listView;
    @ViewById(R.id.tuijian_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    private TuijianNewAdapter adapter;
    private List<TuijianEntity> list = new ArrayList<>();
    private int courseid = 1;
    private int grade_id = 1;
    private String address = "路桥区";
    private String lat = "0";
    private String lng = "0";
    private TuijianPresenterImpl tuijianPresenter;
    private boolean isLoading = false;
    private int page = 0;

    @Override
    public void before() {

    }

    @Override
    public void initView() {
        adapter = new TuijianNewAdapter(list,getContext());
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);

        adapter.setOnTopClickListener(this);
        listView.setAdapter(adapter);

//        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!isLoading) {
//                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
//                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
//                    int position = recyclerView.getChildAdapterPosition(childAt);
//                    if (adapter1.getItemCount() - position < 5) {
//                        isLoading = true;
//                        page++;
////                        NetworkUtil.getService().getTopList(id, ++page, 20).enqueue(TopListFragment.this);
//                    }
//                }
//            }
//        });
        swipeRefreshLayout.setOnRefreshListener(this);

        tuijianPresenter =  new TuijianPresenterImpl(this,Integer.parseInt(User_id.getUid()),grade_id,User_id.getStatus(), User_id.getLat()+"",User_id.getLng()+"");
        if (list.size() > 0){

        }else {
            tuijianPresenter.getTuijianEntity();
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                intent.putExtra("head_image",list.get(position-1).getUser_headimg());
//                intent.putExtra("user_id",list.get(position-1).getUser_id());
//                startActivity(intent);
//            }
//        });
    }


    @Override
    public void successTuijian(List<Map<String, Object>> maps) {
        if (maps != null) {
            if (page == 1) {
                adapter.clear();
                list.clear();
            }
            for (int i = 0; i < maps.size(); i++) {
                TuijianEntity entity = new TuijianEntity();
                entity.setNickname((String) maps.get(i).get("nickname"));
                entity.setSpeciality((String)maps.get(i).get("speciality"));
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
                entity.setHaoping_num((String)maps.get(i).get("haoping_num"));
                List<Map<String,Object>> listmap = ((List<Map<String,Object>>)maps.get(i).get("information_temp"));
                String course_name = "";
                for (int j = 0; j < listmap.size(); j++) {
                    if (listmap.get(j).get("grade_id").equals("1")){
                        course_name = course_name + listmap.get(j).get("course_name")+"(小学)"+"  ";
                    }else if (listmap.get(j).get("grade_id").equals("2")){
                        course_name = course_name + listmap.get(j).get("course_name")+"(初中)"+"  ";
                    }else if (listmap.get(j).get("grade_id").equals("3")){
                        course_name = course_name + listmap.get(j).get("course_name")+"(高中)"+"  ";
                    }else if (listmap.get(j).get("grade_id").equals("4")){
                        course_name = course_name + listmap.get(j).get("course_name")+"(大学)"+"  ";
                    }else {
                        course_name = course_name + listmap.get(j).get("course_name")+"(不限)"+"  ";
                    }
                }
                entity.setStatus2(course_name);
//            entity.setGrade_name((String)maps.get(i).get("grade_name"));

                list.add(entity);
            }
            adapter.addAll(list);
            swipeRefreshLayout.setRefreshing(false);
            isLoading = false;
//        adapter.notifyDataSetChanged();
//        adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failTuijian(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        page = 1;
        new TuijianPresenterImpl(this,Integer.parseInt(User_id.getUid()),grade_id,User_id.getStatus(),User_id.getLat()+"",User_id.getLng()+"").getTuijianEntity();
    }

    @Override
    public void onTopClick(TuijianEntity entity) {
        Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
//                    intent.putExtra("user_id", uid);
        intent.putExtra("head_image",entity.getUser_headimg());
        intent.putExtra("user_id",entity.getUser_id());
        intent.putExtra("myid","1");
        startActivity(intent);
    }

}
