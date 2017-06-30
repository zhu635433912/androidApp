package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;

/**
 * Created by Administrator on 2017/6/29.
 */

public class PictureZoo extends AppCompatActivity {
    private PhotoView photoView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturezoo);
        String hiemag =
//                "http://photocdn.sohu.com/20140311/Img396390334.jpg";
                getIntent().getStringExtra("hiemag");
        photoView = (PhotoView) findViewById(R.id.img);
        if (!hiemag.equals("")) {
            String SBM = hiemag.substring(0, 1);
            if (SBM.equals("h")) {
                Glide.with(this)
                        .load(hiemag)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(photoView);
            } else {
//                Bitmap bm = BitmapFactory.decodeFile(hiemag);
//                photoView.setImageBitmap(bm);
                photoView.setImageResource(Integer.parseInt(hiemag));
            }
        } else {
            Toast.makeText(this,"图片出现异常",Toast.LENGTH_LONG).show();
            finish();
        }

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // 启用图片缩放功能
        photoView.enable();
        Info info = photoView.getInfo();
        photoView.animaFrom(info);
        // 获取/设置 动画持续时间
        photoView.setAnimaDuring(20);
        photoView.setMaxScale(5);


    }
}
