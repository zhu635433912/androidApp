package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 推广金额
 */

public class Promote_Acitvty  extends MyBaseActivity implements View.OnClickListener,Requirdetailed{
    private ImageButton xuelematuiguangshuoming;
    private RelativeLayout yiji;
    private RelativeLayout qianbaofanhui;
    private RelativeLayout erji;
    private int zongfee=0;//总金额
    private double yijifee;
    private double erjifee;
    private TextView tuiguanjine;
    private TextView erjifenxiao;
    private TextView zongjine;
    private int i=1;
    Getdata getdata;
    private TextView leijifeejin;
    int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promote_fee);
        User_id.getInstance().addActivity(this);
        xuelematuiguangshuoming= (ImageButton) findViewById(R.id.xuelematuiguangshuoming);
        yiji= (RelativeLayout) findViewById(R.id.yiji);
        qianbaofanhui= (RelativeLayout) findViewById(R.id.qianbaofanhui);
        erji= (RelativeLayout) findViewById(R.id.erji);
        yiji= (RelativeLayout) findViewById(R.id.yiji);
        tuiguanjine= (TextView) findViewById(R.id.tuiguanjine);
        erjifenxiao= (TextView) findViewById(R.id.erjifenxiao1);
        zongjine= (TextView) findViewById(R.id.zongjine);
        leijifeejin= (TextView) findViewById(R.id.leijifeejin);

        uid=Integer.parseInt(User_id.getUid());
        getdata=new Getdata();
        getdata.getinfo(uid,1,this);




        erji.bringToFront();
        yiji.bringToFront();
        qianbaofanhui.bringToFront();

        erji.setOnClickListener(this);
        qianbaofanhui.setOnClickListener(this);
        yiji.setOnClickListener(this);
        xuelematuiguangshuoming.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xuelematuiguangshuoming:
                //学了吗推广说明
                Intent intenta=new Intent(Promote_Acitvty.this,Extended_Activty.class);
                startActivity(intenta);
                break;
            case R.id.yiji:
                //一二级分销
                Intent intent=new Intent(Promote_Acitvty.this,MyDistribution_Actvity.class);
                intent.putExtra("level","1");
                startActivity(intent);

                break;
            case R.id.qianbaofanhui:
                Promote_Acitvty.this.finish();
                break;
            case R.id.erji:

                Intent intentc=new Intent(Promote_Acitvty.this,MyDistribution_Actvity.class);
                intentc.putExtra("level","2");
                startActivity(intentc);
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (map.get("TotalFee")!=null){
//            zongfee+= (int) map.get("TotalFee");
            zongjine.setText( map.get("TotalFee")+"");
            leijifeejin.setText( map.get("TotalFee")+"");
        }
        if (map.get("TotalFee1") != null){
            tuiguanjine.setText(map.get("TotalFee1")+"元");
        }
        if (map.get("TotalFee2") != null){
            erjifenxiao.setText(map.get("TotalFee2")+"元");
        }


//        if (tuiguanjine.getText().toString().equals("")){
//            i=2;
//            if (map.get("TotalFee")==null) {
//                tuiguanjine.setText("0.00");
//                getdata.getinfo(uid,2,this);
//            }else {
//
//                tuiguanjine.setText((String)map.get("TotalFee"));
//                getdata.getinfo(uid,2,this);
//
//            }
//        }else {
//            if (i==2) {
//                if (erjifenxiao.getText().toString().equals("")) {
//                    if (map.get("TotalFee") == null) {
//                        erjifenxiao.setText("0.00");
//                    } else {
//                        erjifenxiao.setText((String) map.get("TotalFee"));
//                    }
//                }
//            }
//        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
