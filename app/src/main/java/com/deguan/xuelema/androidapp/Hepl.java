package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import modle.Adapter.BillAdapter;
import modle.Adapter.HelpAdapter;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 帮助
 */

public class Hepl extends MyBaseActivity implements View.OnClickListener, TurnoverView {
    private RelativeLayout bangzhufanhui;
    private TextView xuqiuRl;
    private TextView howToPlaceRl;

    private ListView listView;
    private List<Map<String,Object>> data = new ArrayList<>();
    private HelpAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hepl);
        User_id.getInstance().addActivity(this);

        xuqiuRl = (TextView) findViewById(R.id.xuqiuwenti);
        howToPlaceRl = (TextView) findViewById(R.id.howto_place);
        bangzhufanhui= (RelativeLayout) findViewById(R.id.bangzhufanhui);
        bangzhufanhui.bringToFront();
        listView = (ListView) findViewById(R.id.help_list);
        adapter = new HelpAdapter(data,this);
        listView.setAdapter(adapter);

        bangzhufanhui.setOnClickListener(this);
        xuqiuRl.setOnClickListener(this);
        howToPlaceRl.setOnClickListener(this);

        new Getdata().getServiceList(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //聊天
                Intent intent = new Intent(Hepl.this, ChatActivity.class);
                intent.putExtra(JGApplication.CONV_TITLE, data.get(position).get("nickname")+"");
                intent.putExtra(JGApplication.TARGET_ID, data.get(position).get("tel")+"");
                intent.putExtra(JGApplication.TARGET_APP_KEY, JMessageClient.getMyInfo().getAppKey());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bangzhufanhui:
                Hepl.this.finish();
                break;
            case R.id.xuqiuwenti:
                startActivity(HowToPublishActivity_.intent(this).get());
                break;
            case R.id.howto_place:
                startActivity(PlaceActivity_.intent(this).get());
                break;
        }
    }

    @Override
    public void successTurnover(List<Map<String, Object>> list) {
        if (list != null){
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failTurnover(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
