package com.deguan.xuelema.androidapp.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.MyOrderActivity;
import com.deguan.xuelema.androidapp.Pick_singleActivty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
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
import modle.Adapter.MyPublishNewAdapter;
import modle.Adapter.TuijianAdapter;
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.user_ziliao.User_id;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.tuijian_new_fragment)
public class MyPublishFragment extends BaseFragment implements  MyPublishView, MyPublishNewAdapter.OnTopClickListener, SwipeRefreshLayout.OnRefreshListener, MyPublishNewAdapter.OnTopLongClickListener {

    @ViewById(R.id.tuijian_listview)
    RecyclerView listView;
    @ViewById(R.id.tuijian_swipe)
    SwipeRefreshLayout swipeRefreshLayout;


    private MyPublishNewAdapter adapter;
    private List<XuqiuEntity> list = new ArrayList<>();
    private int filter_type;
    private PublishPresenter publishPresenter;
    private boolean isLoading = false;
    private int page = 1;

    @Override
    public void before() {

    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void initView() {
        adapter = new MyPublishNewAdapter(list,getContext());
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
//        listView.setAdapter(adapter);
        adapter.setOnTopClickListener(this);
        adapter.setOnItemLongClickListener(this);
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
                        new PublishPresenterImpl(MyPublishFragment.this, Integer.parseInt(User_id.getUid()),4,page).getPublishEntity();
                    }
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        publishPresenter =  new PublishPresenterImpl(this, Integer.parseInt(User_id.getUid()),4,page);
        if (list.size() > 0){

        }else {
            publishPresenter.getPublishEntity();
        }
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                //跳转接单列表
////                    String uid=adapter.getuid(position-1);
//                    String uid = (String)list.get(position-1).getId();
//                    String headUrl = (String) list.get(position-1).getPublisher_headimg();
//                    Intent intent=new Intent(getContext(),Pick_singleActivty.class);
//                    intent.putExtra("id",uid);
//                    intent.putExtra("teacher_headimg",headUrl);
//                    startActivity(intent);
//            }
//        });

    }



//    @Override
//    public void onRefresh() {
//        new PublishPresenterImpl(this,Integer.parseInt(User_id.getUid()),4).getPublishEntity();
//    }

    @Override
    public void successMyPublish(List<Map<String, Object>> maps) {
        if (maps != null) {
            if (page == 1) {
                adapter.clear();
                list.clear();
            }
            List<XuqiuEntity> lists = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                XuqiuEntity entity = new XuqiuEntity();
                entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
                entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
                entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
                entity.setService_type(maps.get(i).get("service_type")+"");
                entity.setCourse_name((String) maps.get(i).get("course_name"));
                entity.setContent((String) maps.get(i).get("content"));
                entity.setCreated((String) maps.get(i).get("created"));
                entity.setId((String) maps.get(i).get("id"));
                entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
                entity.setDistance((String) maps.get(i).get("distance"));
                entity.setFee(String.valueOf(maps.get(i).get("fee")));
                entity.setGrade_name((String) maps.get(i).get("grade_name"));
                
                if ((maps.get(i).get("status")).equals("1")) {
                    continue;
                }
                lists.add(entity);
            }
            list.addAll(lists);
            adapter.addAll(lists);
//        adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            isLoading = false;
        }
    }

    @Override
    public void failMyPublish(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTopClick(XuqiuEntity entity) {
//跳转接单列表
//                    String uid=adapter.getuid(position-1);
                    String uid = entity.getId();
                    String headUrl = entity.getPublisher_headimg();
        String content;
        if (!TextUtils.isEmpty(entity.getContent())) {
             content = entity.getContent();
        }else {
             content = "";}
                    Intent intent=new Intent(getContext(),Pick_singleActivty.class);
                    intent.putExtra("id",uid);

                    intent.putExtra("content",content);
                    intent.putExtra("teacher_headimg",headUrl);

                    startActivity(intent);
    }

    @Override
    public void onRefresh() {
        page = 1;
        publishPresenter.getPublishEntity();
//        new PublishPresenterImpl(this, Integer.parseInt(User_id.getUid()),4).getPublishEntity();
    }

    @Override
    public boolean onItemLongClickListener(View view, final XuqiuEntity entity) {
        new AlertDialog.Builder(getContext()).setTitle("学了么提示!").setMessage("是否确定删除需求?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Demand().Delete_Demand(Integer.parseInt(entity.getPublisher_id()),Integer.parseInt(entity.getId()));
                        Toast.makeText(getContext(), "已删除请刷新", Toast.LENGTH_SHORT).show();
                        publishPresenter.getPublishEntity();
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "取消删除", Toast.LENGTH_SHORT).show();
            }
        }).show();


        return true;
    }
}
