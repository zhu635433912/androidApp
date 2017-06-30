package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_place)
public class PlaceActivity extends MyBaseActivity {

    @ViewById(R.id.how_to_publish_back)
    RelativeLayout backRl;
    @ViewById(R.id.how_to_publish_image)
    ImageView imageView;

    @Override
    public void initView() {
//        imageView.setImage(ImageSource.resource(R.mipmap.how_to_place));
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this,PictureZoo.class);
                intent.putExtra("hiemag",R.mipmap.how_to_place+"");
                startActivity(intent);
            }
        });
    }

}
