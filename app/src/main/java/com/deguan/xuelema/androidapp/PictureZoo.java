package com.deguan.xuelema.androidapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.viewimpl.ZoomImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/29.
 */

public class PictureZoo extends AppCompatActivity {
    private ZoomImageView himagr;
    private RelativeLayout picture_rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturezoo);
//        picture_rl = (RelativeLayout) findViewById(R.id.picture_fanhui);
//        picture_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        himagr= (ZoomImageView) findViewById(R.id.himagr);
        String hide=getIntent().getStringExtra("hide");
        if (!hide.equals("")){
            String SBM=hide.substring(0,1);
            if (SBM.equals("h")){
                Glide.with(this)
                        .load(hide)
                        .into(himagr);
            }else {
                himagr.setImageResource(Integer.parseInt(hide));
            }
        }else {
            finish();
        }
        himagr.setOnCliLcontent(new ZoomImageView.OncliLcontent() {
            @Override
            public void setcontent(boolean status) {
                if (status)
                    finish();
            }
        });

    }

}
