package com.deguan.xuelema.androidapp;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.BillAdapter;
import modle.Huanxing.ui.BaseActivity;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;


/**
 * 账单页面
 */
@EActivity(R.layout.activity_bill)
public class BillActivity extends MyBaseActivity implements TurnoverView {

    @ViewById(R.id.bill_back)
    RelativeLayout backImage;
    @ViewById(R.id.bill_list)
    ListView listView;

    private List<Map<String,Object>> data = new ArrayList<>();
    private BillAdapter adapter;

    @Override
    public void initData() {
        User_id.getInstance().addActivity(this);
        new Getdata().getBillList(Integer.parseInt(User_id.getUid()),this);
        adapter  = new BillAdapter(data,this);
        listView.setAdapter(adapter);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void successTurnover(List<Map<String, Object>> list) {
        if (list == null){
            Toast.makeText(this, "无账单", Toast.LENGTH_SHORT).show();
        }else {
            data.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void failTurnover(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
