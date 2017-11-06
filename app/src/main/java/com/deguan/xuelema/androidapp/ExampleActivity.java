package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.google.zxing.oned.rss.RSSUtils;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.ExampleAdapter;
import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_example)
public class ExampleActivity extends MyBaseActivity implements SwipeRefreshLayout.OnRefreshListener, TeacherView {
    @ViewById(R.id.example_back)
    RelativeLayout backRl;
    @ViewById(R.id.example_swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewById(R.id.example_recycler)
    RecyclerView recyclerView;

    private List<Map<String,Object>> list=new ArrayList<>();
    private ExampleAdapter adapter ;
    private boolean isLoading = false;
    private int page = 1;
    private int id;

    @Override
    public void before() {
        super.before();
        id = getIntent().getIntExtra("teacherId",Integer.parseInt(User_id.getUid()));
    }

    @Override
    public void initData() {
        adapter = new ExampleAdapter(this,list);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        new Teacher(ExampleActivity.this).getExampleList(id,page);
                    }
                }
            }
        });
        new Teacher(this).getExampleList(id,page);
    }
    @Override
    public void initView() {
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onRefresh() {
        page = 1;
        new Teacher(this).getExampleList(id,page);
    }

    @Override
    public void successTeacher(List<Map<String, Object>> maps) {
        if (maps != null && maps.size()>0){
            if (page == 1){
                list.clear();
            }
            List<Map<String,Object>> lists = new ArrayList<>();
            for (int i = 0; i < maps.size(); i++) {
                lists.add(maps.get(i));
            }
            list.addAll(lists);
            adapter.notifyDataSetChanged();
            isLoading = false;
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void failTeacher(String msg) {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
