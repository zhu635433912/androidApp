package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.CashListView;
import com.deguan.xuelema.androidapp.viewimpl.DistributionView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.DistributionAdapter;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 一级二级分销
 */

public class MyDistribution_Actvity extends MyBaseActivity implements View.OnClickListener,Requirdetailed,PullToRefreshListView.OnRefreshListener2 , DistributionView {
    private RelativeLayout fenxiaofanhui;
    Getdata getdata;
    private TextView zongordet;
    private TextView zongrogin;
    int uid;
    private TextView yijifenxiaofee;
    private TextView myleve_name;
    private PullToRefreshListView pullToRefreshListView;
    private int level;
    private int page=1;
    private List<Map<String,Object>> listmap=new ArrayList<>();
    private SimpleAdapter simpleAdapter;
    private DistributionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelsales);
        User_id.getInstance().addActivity(this);
        fenxiaofanhui= (RelativeLayout) findViewById(R.id.fenxiaofanhui);
        yijifenxiaofee= (TextView) findViewById(R.id.yijifenxiaofee);
        zongordet= (TextView) findViewById(R.id.zongordet);
        zongrogin= (TextView) findViewById(R.id.zongrogin);
        myleve_name= (TextView) findViewById(R.id.myleve_name);
        pullToRefreshListView= (PullToRefreshListView) findViewById(R.id.myfenxiaolistview);
        level=Integer.parseInt(getIntent().getStringExtra("level"));
        uid=Integer.parseInt(User_id.getUid());

        if (level==1){
            myleve_name.setText("推广奖励");
        }else {
            myleve_name.setText("团队奖励");
        }
        adapter = new DistributionAdapter(listmap,this);
        simpleAdapter=new SimpleAdapter(this,listmap,R.layout.distibution_itme,new String[]{"level","fee"},new int[]{R.id.fenxiaostatus,R.id.fenxiaofee});
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(this);
        pullToRefreshListView.setAdapter(adapter);

        getdata=new Getdata();
        getdata.getinfo(uid,level,this);
//        getdata.getCashList(uid,page,this);
        getdata.getDistribution(uid,level,page,this);

        fenxiaofanhui.bringToFront();
        fenxiaofanhui.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fenxiaofanhui:
                MyDistribution_Actvity.this.finish();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

        if (!map.get("TotalUser").equals("0")){
            if (level == 1) {
                if ((double)map.get("TotalUser1") != 0) {
                    zongrogin.setText(BigDecimal.valueOf((Double) map.get("TotalUser1")).stripTrailingZeros().toPlainString() + "人");
                }else {
                    zongrogin.setText("0人");
                }if ((double)map.get("TotalBill1") != 0) {
                    zongordet.setText("共交易了" + BigDecimal.valueOf((Double) map.get("TotalBill1")).stripTrailingZeros().toPlainString() + "单");
                }else {
                    zongordet.setText("共交易了0单");

                }
                yijifenxiaofee.setText(map.get("TotalFee1")+"");
            }
            if (level == 2) {
                if ((double)map.get("TotalUser2") != 0) {
                    zongrogin.setText(BigDecimal.valueOf((Double) map.get("TotalUser2")).stripTrailingZeros().toPlainString() + "人");
                }else{
                    zongrogin.setText("0人");
                }
                if ((double)map.get("TotalBill2") != 0) {
                    zongordet.setText("共交易了" + BigDecimal.valueOf((Double) map.get("TotalBill2")).stripTrailingZeros().toPlainString() + "单");
                }else{
                    zongordet.setText("共交易了0单");
                }
                yijifenxiaofee.setText(map.get("TotalFee2")+"");
            }
        }else {
            yijifenxiaofee.setText("0.00");
            zongrogin.setText("0人");
            zongordet.setText("共交易了0"+"单");
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page=1;
        getdata.getDistribution(uid,level,page,this);

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getdata.getDistribution(uid,level,page,this);
    }


    @Override
    public void successDistribution(List<Map<String, Object>> list) {
        pullToRefreshListView.onRefreshComplete();
        if (page==1) {
            listmap.clear();
        }
        if (list != null)
            listmap.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failDistribution(String msg) {
        pullToRefreshListView.onRefreshComplete();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successMoney(Map<String, Object> map) {

    }
}
