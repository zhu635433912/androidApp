package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.JieYse.Dtu_Modle.ProvinceAreaHelper;

@EActivity(R.layout.content_how_to_publish)
public class HowToPublishActivity extends MyBaseActivity {

    @ViewById(R.id.how_to_publish_back)
    RelativeLayout backRl;
//    @ViewById(R.id.how_to_publish_image)
//    SubsamplingScaleImageView imageView;
    @ViewById(R.id.how_to_publish_iv)
    ImageView imageView;

    @Override
    public void initView() {
        ProvinceAreaHelper provinceAreaHelper = new ProvinceAreaHelper(this);
        provinceAreaHelper.initProvinceData();
        Map<String,String[]> list = provinceAreaHelper.getsheng();
        Map<String,String[]> list1 = provinceAreaHelper.getshi();
        Map<String,String> list2 = provinceAreaHelper.getqu();
//        imageView.setImage(ImageSource.resource(R.mipmap.how_to_publish));
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HowToPublishActivity.this,PictureZoo.class);
                intent.putExtra("hide",R.mipmap.how_to_publish+"");
                startActivity(intent);
            }
        });
    }
}
