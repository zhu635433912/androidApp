package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.fragment.EvaluationFragment;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.DistributionView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.ExtentAdapter;
import modle.Adapter.OrderNewAdapter;
import modle.Adapter.TeamAdapter;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

import static com.deguan.xuelema.androidapp.R.id.listView;

@EActivity(R.layout.activity_my_extent)
public class MyExtentActivity extends MyBaseActivity implements DistributionView, ExtentAdapter.OnTopClickListener {

    @ViewById(R.id.back_rl)
    RelativeLayout backRl;
    @ViewById(R.id.extent_total_tv)
    TextView totalTv;
    @ViewById(R.id.two_money_tv)
    TextView twoMoneyTv;
    @ViewById(R.id.one_money_tv)
    TextView oneMoneyTv;
    @ViewById(R.id.extent_recycler_view1)
    RecyclerView recyclerView;
    @ViewById(R.id.extent_recycler_view2)
    RecyclerView recyclerView2;
    @ViewById(R.id.two_card_view)
    CardView twoCard;
    @ViewById(R.id.one_card_view)
    CardView oneCard;

    @ViewById(R.id.two_money_ll)
    LinearLayout twoLl;
    @ViewById(R.id.one_money_ll)
    LinearLayout oneLl;


    private ExtentAdapter adapter;
    private TeamAdapter teamAdapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private List<Map<String,Object>> list1 = new ArrayList<>();
    private int page = 1;
    private int page1 = 1;
    private boolean isLoading = false;
    private boolean isLoading1 = false;
    private int flag = 1;
    private String level = "0";


    @Override
    public void before() {
        super.before();
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
    public void initData() {
        adapter = new ExtentAdapter(list,this);
        teamAdapter = new TeamAdapter(list1,this);

        adapter.setOnTopClickListener(this);
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView2.setAdapter(teamAdapter);
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading1) {
                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int position = recyclerView.getChildAdapterPosition(childAt);
                    if (adapter1.getItemCount() - position < 5) {
                        isLoading1 = true;
                        page1++;
                        new Getdata().getDistribution(Integer.parseInt(User_id.getUid()),2,page1,MyExtentActivity.this);
                    }
                }
            }
        });
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
                        new Getdata().getDistribution(Integer.parseInt(User_id.getUid()),1,page,MyExtentActivity.this);
                    }
                }
            }
        });
        new Getdata().getDistribution(Integer.parseInt(User_id.getUid()),1,page,this);
        oneLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 1;
                oneCard.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                twoCard.setVisibility(View.GONE);
                recyclerView2.setVisibility(View.GONE);
            }
        });
        twoLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (level.equals("0")){
                    Toast.makeText(MyExtentActivity.this, "仅会员可见", Toast.LENGTH_SHORT).show();
                }else {
                    flag = 2;
                    twoCard.setVisibility(View.VISIBLE);
                    recyclerView2.setVisibility(View.VISIBLE);
                    oneCard.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void successDistribution(List<Map<String, Object>> maps) {
        if (flag == 1) {
            if (maps != null) {
                if (page == 1) {
                    adapter.clear();
                    list.clear();
                }
//            List<Map<String, Object>> lists = new ArrayList<>();
                adapter.addAll(maps);
                isLoading = false;
            }
        }else {
            if (maps != null) {
                if (page1 == 1) {
                    teamAdapter.clear();
                    list1.clear();
                }
//            List<Map<String, Object>> lists = new ArrayList<>();
                teamAdapter.addAll(maps);
                isLoading1 = false;
            }
        }
    }

    @Override
    public void failDistribution(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successMoney(Map<String, Object> map) {
        double totalfee1 = Double.parseDouble(map.get("TotalFee1")+"");
        double totalfee2 = Double.parseDouble(map.get("TotalFee2")+"");
        double totalfee = totalfee1 + totalfee2;
        totalTv.setText(totalfee+"(元)");
        if (Double.parseDouble(map.get("Totaluser1")+"")==0){
            oneMoneyTv.setText(map.get("TotalFee1")+"(元)0(人)");
        }else{
            oneMoneyTv.setText(map.get("TotalFee1")+"(元)"+map.get("Totaluser1")+"(人)");
        }

        if (Double.parseDouble(map.get("Totaluser2")+"")==0){
            twoMoneyTv.setText(map.get("TotalFee2")+"(元)0(人)");
        }else {
            twoMoneyTv.setText(map.get("TotalFee2")+"(元)"+map.get("Totaluser2")+"(人)");
        }
        level = map.get("level")+"";
    }

    @Override
    public void onTopClick(Map<String, Object> entity) {

    }
}
