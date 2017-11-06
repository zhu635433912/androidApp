package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.TurnoverAdapter;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 成交率
 */

public class Closing extends MyBaseActivity implements View.OnClickListener,Baseinit,PullToRefreshListView.OnRefreshListener2, TurnoverView {
    private RelativeLayout chengjiaolvfanhui;
    private TextView chengjiaolv;
    private TextView size_imte;
    private TextView ron_itme;
    private PullToRefreshListView cjiaolv;
    private int requir_id;
    private int page = 1;
    private TurnoverAdapter adapter;
    private List<Map<String,Object>> list = new ArrayList<>();
    private Getdata getdata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closing);
        User_id.getInstance().addActivity(this);
        chengjiaolvfanhui= (RelativeLayout) findViewById(R.id.chengjiaolvfanhui);
        chengjiaolv= (TextView) findViewById(R.id.chengjiaolv);
        size_imte= (TextView) findViewById(R.id.size_imte);
        ron_itme= (TextView) findViewById(R.id.ron_itme);
        cjiaolv= (PullToRefreshListView) findViewById(R.id.cjiaolv);
        chengjiaolvfanhui.bringToFront();
        chengjiaolvfanhui.setOnClickListener(this);
        String teqid=getIntent().getStringExtra("Requir_id");
        requir_id = Integer.parseInt(teqid);

        //获取成交率
        getdata = new Getdata();
        getdata.getdelt_info(requir_id,this);
        getdata.getTurnoverData(requir_id, page,this);
        //下拉刷新
//        listView.setInterface(this);
//        listView.setRemoveListener(this);
        cjiaolv.setMode(PullToRefreshBase.Mode.BOTH);
        cjiaolv.setOnRefreshListener(this);
        adapter = new TurnoverAdapter(list,this);
        cjiaolv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chengjiaolvfanhui:
                Closing.this.finish();
                break;
        }

    }


    @Override
    public void upcontent(Map<String, Object> map) {
        chengjiaolv.setText((int)(Double.parseDouble(map.get("comp_rate")+"")*100)+"");
        size_imte.setText("完成"+map.get("comp_case_num")+"单");
        ron_itme.setText("共接取"+map.get("gets_case_num")+"单");
    }



    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getdata.getTurnoverData(requir_id,page,this);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page ++;
        getdata.getTurnoverData(requir_id,page,this);
    }

    @Override
    public void successTurnover(List<Map<String, Object>> data) {
        cjiaolv.onRefreshComplete();
        if (page == 1) {
            list.clear();
            if (data == null){
                Toast.makeText(this, "暂无成交", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        list.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failTurnover(String msg) {
        cjiaolv.onRefreshComplete();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
