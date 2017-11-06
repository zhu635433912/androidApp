package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.CashListView;
import com.deguan.xuelema.androidapp.viewimpl.CashView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.CashListAdapter;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 现金卷
 */

public class Cashvolume_Activty extends MyBaseActivity implements PullToRefreshBase.OnRefreshListener2,View.OnClickListener,Requirdetailed,CashView,CashListView{
    private Button xinjinjuantixian;
    private RelativeLayout xianjinjbufanhui;
    private TextView mogint;
    private TextView rogsi;
    private PopupWindow cashPopwindow;
    private Getdata getdata;
    private double myBalance =0 ;
    private int uid;
    private int page = 1;
    private PullToRefreshListView listView;
    private CashListAdapter adapter;
    private List<Map<String,Object>> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cashvolume);
        User_id.getInstance().addActivity(this);

        listView = (PullToRefreshListView) findViewById(R.id.cash_ticket_list);
        xinjinjuantixian= (Button) findViewById(R.id.xinjinjuantixian);
        xianjinjbufanhui= (RelativeLayout) findViewById(R.id.xianjinjbufanhui);
        mogint= (TextView) findViewById(R.id.tuiguangjine);
        rogsi= (TextView) findViewById(R.id.rogasi);

        xianjinjbufanhui.bringToFront();
        xianjinjbufanhui.setOnClickListener(this);
        xinjinjuantixian.setOnClickListener(this);
        //应用宝  版本  已上架  下载分享

        adapter = new CashListAdapter(list,this);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listView.setOnRefreshListener(this);
        listView.setAdapter(adapter);
        if (User_id.getRole().equals("1")) {
            xinjinjuantixian.setVisibility(View.GONE);
        }
        //获取用户余额
        uid = Integer.parseInt(User_id.getUid());
        getdata = new Getdata();
        getdata.getmianfofee(uid,this);
        getdata.getCashList(uid,page,this);
        initCashPopwindow();
    }

//    private int cashFee = 0;
    private int cashFlag = 1;
    private void initCashPopwindow() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.cash_ticket_layout,null);
        TextView cashBack = (TextView) view.findViewById(R.id.cash_back);
        TextView cashSure = (TextView) view.findViewById(R.id.cash_sure);
        final EditText cashId = (EditText) view.findViewById(R.id.cash_name);
        cashPopwindow = new PopupWindow(view);
        cashPopwindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        cashPopwindow.setWidth(width);
        cashPopwindow.setHeight(height/2);
        cashPopwindow.setOutsideTouchable(true);
        cashBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cashPopwindow.dismiss();
            }
        });
        cashSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cashFee = (int)Double.parseDouble(cashId.getText().toString());
                    if (Double.parseDouble(cashId.getText().toString())>myBalance){
                        Toast.makeText(Cashvolume_Activty.this, "可提现金额不足", Toast.LENGTH_SHORT).show();
                    }else if(cashFee % 100 == 0){
                        getdata.getCash(Integer.parseInt(User_id.getUid()), User_id.getUsername(), "现金券提现", 3, Float.parseFloat(cashId.getText().toString()), "",Cashvolume_Activty.this);
                                        Toast.makeText(Cashvolume_Activty.this,"已提交提现申请",Toast.LENGTH_LONG).show();

                    }else {
                        Toast.makeText(Cashvolume_Activty.this, "请输入合适的金额", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xinjinjuantixian:
                if (User_id.getRole().equals("1")) {
                    Toast.makeText(this,"对不起!学生不能提现",Toast.LENGTH_LONG).show();
                }else {
                    cashPopwindow.showAtLocation(xinjinjuantixian, Gravity.BOTTOM, 0, 0);
                }
//                Toast.makeText(this,"提现成功",Toast.LENGTH_LONG).show();
                break;
            case R.id.xianjinjbufanhui:
                Cashvolume_Activty.this.finish();
                break;
        }
    }

    private int cashTicket;
    @Override
    public void Updatecontent(Map<String, Object> map) {

        if (map.get("RecomCount")==null) {
            rogsi.setText("共推广了0人");
        }else {
            rogsi.setText("共推广了" + map.get("RecomCount") + "人");
        }


        if (map.get("TotalFee")==null){
            mogint.setText("0");
            myBalance = 0;
        }else {
            mogint.setText((String)map.get("TotalFee"));
            myBalance = Double.parseDouble((String)map.get("TotalFee"));
        }


    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successCash(String msg) {
        Toast.makeText(this, "已提交提现申请", Toast.LENGTH_SHORT).show();
        cashPopwindow.dismiss();
        getdata.getmianfofee(uid,this);
    }

    @Override
    public void failCash(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successCashList(List<Map<String,Object>> map) {
        listView.onRefreshComplete();

        if (page == 1){
            list.clear();
            if (map == null){
                Toast.makeText(this, "无记录", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        list.addAll(map);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failCashList() {
        listView.onRefreshComplete();
        Toast.makeText(this, "获取现金券流水失败", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        page = 1;
        getdata.getCashList(uid,page,this);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        page++;
        getdata.getCashList(uid,page,this);
    }
}
