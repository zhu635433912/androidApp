package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.ShareUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.toos.Erweima;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

/**
 * 我的分销
 */

public class Distribution_Activty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed{
    private RelativeLayout jiantou;
    private RelativeLayout wodeffenxiao;
    private ImageView erweima;
    Getdata getdata;
    int uid;
    private int zongfee=0;
    private TextView leijifee;
    private TextView yijifee;
    private TextView erjifee;
    private TextView yqm;
    private int z=1;
    private ImageView weichatImage,xinlangImage,weichatCoreImage,qqImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.distribution);
        User_id.getInstance().addActivity(this);


        weichatImage = (ImageView) findViewById(R.id.share_to_weixin_image);
        xinlangImage = (ImageView) findViewById(R.id.share_to_xinlang_image);
        weichatCoreImage = (ImageView) findViewById(R.id.share_to_weixincore_image);
        qqImage = (ImageView) findViewById(R.id.share_to_qq_image);
        jiantou= (RelativeLayout) findViewById(R.id.jiantou);
        wodeffenxiao= (RelativeLayout) findViewById(R.id.wodeffenxiao);
        erweima= (ImageView) findViewById(R.id.erweima);
        wodeffenxiao.bringToFront();
        jiantou.bringToFront();
        yqm= (TextView) findViewById(R.id.yqm);
        leijifee= (TextView) findViewById(R.id.leijifee);
        yijifee= (TextView) findViewById(R.id.yijifee);
        erjifee= (TextView) findViewById(R.id.erjifee);

        uid=Integer.parseInt(User_id.getUid());
        //获取推广金额
        getdata=new Getdata();
        //获取不到啊!
        getdata.getinfo(uid,1,this);

        //设置生成二维码
        User_init user=new User_Realization();
        user.User_Data(uid,User_id.getLat()+"",User_id.getLng()+"",this);


        weichatImage.setOnClickListener(this);
        xinlangImage.setOnClickListener(this);
        weichatCoreImage.setOnClickListener(this);
        qqImage.setOnClickListener(this);


        wodeffenxiao.setOnClickListener(this);
        jiantou.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_to_weixin_image:
                ShareUtil.getInstance().share(this);
//                ShareUtil.getInstance().shareQQ(this);
                break;
            case R.id.share_to_weixincore_image:
                ShareUtil.getInstance().share(this);

                break;
            case R.id.share_to_qq_image:
                ShareUtil.getInstance().share(this);

                break;
            case R.id.share_to_xinlang_image:

                ShareUtil.getInstance().share(this);
                break;
            case R.id.jiantou:
                //推广金额
                Intent intent=new Intent(Distribution_Activty.this,Promote_Acitvty.class);
                startActivity(intent);
                break;
            case R.id.wodeffenxiao:
                Distribution_Activty.this.finish();
                break;
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (map.get("TotalUser")!=null){
            if (map.get("TotalFee")!=null){
                zongfee+= (int) map.get("TotalFee");
            }
        if (yijifee.getText().toString().equals("")){
            z=2;
            if (map.get("TotalFee")==null) {
                yijifee.setText("0.00");
            }else {
                yijifee.setText((String) map.get("TotalFee"));
                zongfee+=(int)map.get("TotalFee");
            }
        }
         if (z==2){
             if (erjifee.getText().toString().equals("")){
                 if (map.get("TotalFee")==null) {
                     erjifee.setText("0.00");
                 }else {
                     erjifee.setText((String) map.get("TotalFee"));
                     zongfee+=(int)map.get("TotalFee");
                 }
             }
         }
        leijifee.setText(""+zongfee);
        }

        if (map.get("mobile")!=null) {
            Erweima erweimac = new Erweima();
            Bitmap bitmap = erweimac.generateBitmap("邀请码为:" + map.get("mobile"), 440, 440);
            erweima.setImageBitmap(bitmap);
            yqm.setText((String) map.get("mobile"));
        }



    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
