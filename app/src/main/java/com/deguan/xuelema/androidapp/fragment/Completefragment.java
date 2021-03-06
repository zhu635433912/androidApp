package com.deguan.xuelema.androidapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.OrderTeacherActivity;
import com.deguan.xuelema.androidapp.Order_details;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.OrderView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.CompleteAdapter;
import modle.Adapter.OrderNewAdapter;
import modle.Adapter.Order_StudionAdabt;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/17 0017.
 * 已完成
 */
@EFragment(R.layout.tuijian_new_fragment)
public class Completefragment extends MyBaseFragment implements OrderView, SwipeRefreshLayout.OnRefreshListener, OrderNewAdapter.OnTopClickListener, OrderNewAdapter.OnTopLongClickListener, Requirdetailed {

    @ViewById(R.id.tuijian_listview)
    RecyclerView listView;
    @ViewById(R.id.tuijian_swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    private OrderNewAdapter adapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private OrderPresenterImpl tuijianPresenter;
    private int page = 1;
    private boolean isLoading = false;


    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void initView() {
        adapter = new OrderNewAdapter(list,getContext());

        adapter.setOnTopClickListener(this);
//        adapter.setOnItemLongClickListener(this);
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);

        listView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int position = recyclerView.getChildAdapterPosition(childAt);
                    if (adapter1.getItemCount() - position < 5) {
                        isLoading = true;
                        page++;
                        if (User_id.getRole().equals("1")){

                            new OrderPresenterImpl(Completefragment.this, Integer.parseInt(User_id.getUid()),0,page).getEvaluateOrderEntity(3, 0);
                        }else {
                            new OrderPresenterImpl(Completefragment.this,Integer.parseInt(User_id.getUid()),1,page).getEvaluateOrderEntity(3, 0);
                        }
//                        NetworkUtil.getService().getTopList(id, ++page, 20).enqueue(TopListFragment.this);
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);

        if (User_id.getRole().equals("1")){

            tuijianPresenter =  new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()),0,page);
        }else {
            tuijianPresenter = new OrderPresenterImpl(this,Integer.parseInt(User_id.getUid()),1,page);
        }
        if (list.size() > 0){}
        else {
            tuijianPresenter.getEvaluateOrderEntity(3, 0);
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    //跳转我的订单页面
//                    Intent intent=new Intent(getActivity(),MyOrderActivity.class);
//                    startActivity(intent);
//            }
//        });
    }

    @Subscriber(tag = "changeStatus")
    public void updateList(int msg){
        if (msg == 1){
            tuijianPresenter.getEvaluateOrderEntity(3, 0);
        }
    }
    @Override
    public void successOrder(List<Map<String, Object>> maps) {
//        listView.onRefreshComplete();
        if (maps != null) {
            if (page == 1) {
                adapter.clear();
            }
            list.clear();
//            for (int i = 0; i < maps.size(); i++) {
//                if (!maps.get(i).get("status").equals("9")) {
//                    list.add(maps.get(i));
//                }
//            }
            for (int i = 0; i < maps.size(); i++) {
                if (maps.get(i).get("status").equals("3")
//                        ||maps.get(i).get("status").equals("6")
                        ) {
                    maps.get(i).put("status","8");
                    list.add(maps.get(i));
                }
                if (maps.get(i).get("status").equals("5")){
                    list.add(maps.get(i));
                }
            }

            adapter.addAll(list);
            isLoading = false;
        }
        swipeRefreshLayout.setRefreshing(false);
//        list.addAll(maps);
//        for (int i = 0; i < maps.size(); i++) {
//            TuijianEntity entity = new TuijianEntity();
//            entity.setNickname((String) maps.get(i).get("nickname"));
//            entity.setSpeciality_name((String) maps.get(i).get("speciality_name"));
//            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
//            entity.setSignature((String) maps.get(i).get("signature"));
//            entity.setOrder_rank((String.valueOf(maps.get(i).get("order_rank"))));
//            entity.setUser_headimg((String) maps.get(i).get("user_headimg"));
//            entity.setUser_id((String) maps.get(i).get("user_id"));
//            entity.setGender((String) maps.get(i).get("gender"));
////            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
//            entity.setDistance((String) maps.get(i).get("distance"));
//            entity.setFee(String.valueOf(maps.get(i).get("fee")));
//            entity.setSpeciality(String.valueOf(maps.get(i).get("speciality")));
//            entity.setUsername(String.valueOf(maps.get(i).get("username")));
//            entity.setHaoping_num(String.valueOf(maps.get(i).get("haoping_num")));
//            list.add(entity);
//        }
//        adapter.notifyDataSetChanged();
    }
//}

    @Override
    public void failOrder(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (User_id.getRole().equals("1")) {
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 0, page).getEvaluateOrderEntity(3,0);
        }else {
            new OrderPresenterImpl(this, Integer.parseInt(User_id.getUid()), 1, page).getEvaluateOrderEntity(3,0);
        }
    }


    @Override
    public void onTopClick(Map<String, Object> map) {
        //跳转我的订单页面
//        Intent intent=new Intent(getActivity(),MyOrderActivity.class);
//        startActivity(intent);
        String status= (String) map.get("status");
        String ida = (String) map.get("id");
        String duration = (String) map.get("duration");
        String teacherImage = (String) map.get("teacher_headimg");

        Intent intent = null;
        if (User_id.getRole().equals("1")) {
            intent = new Intent(getActivity(), Order_details.class);
//            intent.putExtra("telphone",map.get("teacher_mobile")+"");
        }else {
            intent = new Intent(getActivity(), OrderTeacherActivity.class);
//            intent.putExtra("telphone",map.get("placer_mobile")+"");
        }
        intent.putExtra("oredr_id", ida);
        intent.putExtra("duration", duration);
//        intent.putExtra("status", status);
        intent.putExtra("status", 8+"");
        intent.putExtra("teacher_headimg",teacherImage);
        startActivity(intent);
    }


    @Override
    public boolean onItemLongClickListener(View view, final Map<String, Object> entity) {
        if (User_id.getRole().equals("1")) {
            new AlertDialog.Builder(getContext()).setTitle("学了么提示!").setMessage("是否确定删除订单?")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new Order().Delete_Order(Integer.parseInt(User_id.getUid()), Integer.parseInt(entity.get("id").toString()), Completefragment.this);

                        }
                    }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getContext(), "取消删除", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
        return true;
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), "已删除请刷新", Toast.LENGTH_SHORT).show();
        if (User_id.getRole().equals("1")) {
            new OrderPresenterImpl(Completefragment.this, Integer.parseInt(User_id.getUid()), 0, 1).getNofinishOrderEntity(1);
        } else {
            new OrderPresenterImpl(Completefragment.this, Integer.parseInt(User_id.getUid()), 1, 1).getNofinishOrderEntity(1);
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        swipeRefreshLayout.setRefreshing(false);
    }


//
//        extends BaseFragment implements PullToRefreshBase.OnRefreshListener,Student_init {
//
//    @ViewById(R.id.tuijian_listview)
//    PullToRefreshListView listView;
//    private CompleteAdapter studionAdabt;
//    private List<Map<String,Object>> list=new ArrayList<>();
//    private Order_init orderInit;
//    private int uid;
//
//
//    @Override
//    public void initView() {
//        studionAdabt=new CompleteAdapter(list,getContext());
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
//        listView.setAdapter(studionAdabt);
//        uid=Integer.parseInt(User_id.getUid());
//        orderInit=new Order();
//        if (User_id.getRole().equals("1")){
//
//            orderInit.getOrder_list(uid,0,3,1,null,null,this,0,3);
//        }else {
//            orderInit.getOrder_list(uid,1,3,1,null,null,this,0,3);
//        }
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Map<String,Object> map=studionAdabt.getmap(i-1);
//                String status= (String) map.get("status");
//                String ida = (String) map.get("id");
//                String duration = (String) map.get("duration");
//                Intent intent = null;
//                if (User_id.getRole().equals("1")) {
//
//                    intent = new Intent(getActivity(), Order_details.class);
//                }else {
//                    intent = new Intent(getActivity(), OrderTeacherActivity.class);
//                }
////                Intent intent = new Intent(getActivity(), Order_details.class);
//                intent.putExtra("oredr_id", ida);
//                intent.putExtra("duration", duration);
//                intent.putExtra("status", 7+"");
//                startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public void onRefresh(PullToRefreshBase refreshView) {
//        orderInit.getOrder_list(uid,0,3,1,null,null,this,0,3);
//    }
//
//    @Override
//    public void setListview(List<Map<String, Object>> listmap) {
//        //取消下拉刷新
//        listView.onRefreshComplete();
//        //清空list数据
//        list.clear();
//        for (int i = 0; i < listmap.size(); i++) {
//            if (listmap.get(i).get("status").equals("3")
//                    ||listmap.get(i).get("status").equals("5")
//                    ||listmap.get(i).get("status").equals("6")
//                    ){
//                list.add(listmap.get(i));
//            }
//        }
//        //刷新适配器
//        studionAdabt.notifyDataSetChanged();
//    }
//
//    @Override
//    public void setListview1(List<Map<String, Object>> listmap) {
//
//    }

}
