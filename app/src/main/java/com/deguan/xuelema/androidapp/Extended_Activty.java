package com.deguan.xuelema.androidapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;

import com.zhy.autolayout.AutoLayoutActivity;

import modle.user_ziliao.User_id;

/**
 * 学了吗推广说明
 */

public class Extended_Activty extends AutoLayoutActivity implements View.OnClickListener{
    private ImageButton tuiguangshuoming;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.promote_that);
        User_id.getInstance().addActivity(this);

        tuiguangshuoming= (ImageButton) findViewById(R.id.tuiguangshuoming);

        tuiguangshuoming.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tuiguangshuoming:
                Extended_Activty.this.finish();
                break;
        }

    }
}
