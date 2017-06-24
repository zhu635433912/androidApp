package com.deguan.xuelema.androidapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;

import java.util.ArrayList;
import java.util.List;

import modle.Adapter.TestAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TestAdapter.OnTopClickListener {


    private long id;
    private SwipeRefreshLayout swipe;
    private TestAdapter adapter;
    private int page = 0;
    private boolean isLoading = false;

    public static TestFragment newInstance(long id) {

        Bundle args = new Bundle();

        TestFragment fragment = new TestFragment();
        args.putLong("id", id);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_test, container, false);
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        id = getArguments().getLong("id");
        swipe = ((SwipeRefreshLayout) view.findViewById(R.id.top_list_swipe));
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.fragment_list);
        List<String> list = new ArrayList<>();
        adapter = new TestAdapter(getContext(), list);
        adapter.setOnTopClickListener(this);
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        List<String> tngou = new ArrayList<>();
                        for (int i = 0; i < 44; i++) {
                                tngou.add("test-----"+i);
//        DbUtil.getSession().getTopDao().insertOrReplaceInTx(tngou);
                        }
                        adapter.addAll(tngou);
                        swipe.setRefreshing(false);
//                        NetworkUtil.getService().getTopList(id, ++page, 20).enqueue(TopListFragment.this);
                    }
                }
            }
        });
        swipe.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        page = 1;
        if (page == 1) {
            adapter.clear();
        }
        List<String> tngou = new ArrayList<>();
        for (int i = 0; i < 44; i++) {
            tngou.add("test-----"+i);
        }
//        DbUtil.getSession().getTopDao().insertOrReplaceInTx(tngou);
        adapter.addAll(tngou);
        swipe.setRefreshing(false);
        isLoading = false;
//        NetworkUtil.getService().getTopList(id, page, 20).enqueue(this);
    }

//    @Override
//    public void onResponse(Call<ResponseEntity<Top>> call, Response<ResponseEntity<Top>> response) {
//        if (page == 1) {
//            adapter.clear();
//        }
//        List<String> tngou = response.body().getTngou();
////        DbUtil.getSession().getTopDao().insertOrReplaceInTx(tngou);
//        adapter.addAll(tngou);
//        swipe.setRefreshing(false);
//        isLoading = false;
////        swipe.setRefreshing(true);
//    }


    @Override
    public void onTopClick(String top) {
//        Intent intent = new Intent(getContext(), DetailActivity.class);
//        intent.putExtra("id", top.getId());
//        startActivity(intent);
        Toast.makeText(getContext(), "点我干嘛", Toast.LENGTH_SHORT).show();
    }



    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        // Inflate the layout for this fragment
//        RecyclerView listView = (RecyclerView) view.findViewById(R.id.fragment_list);
//        List<String> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            list.add("test-----"+i);
//        }
//        BarAdapter adapter = new BarAdapter(getActivity(),list);
//        listView.setAdapter(adapter);

        return view;
    }

}
