package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.zhy.autolayout.AutoLayoutActivity;

import modle.Onteh;
import modle.Onteh_init;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/6.
 */

public class Feedback_Activity extends AutoLayoutActivity implements View.OnClickListener{
    private EditText editText;
    private RelativeLayout relativeLayout;
    private Button imageButton;
    private int uid;
    private ImageButton backBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback);

        editText= (EditText) findViewById(R.id.fankuineirong);
        relativeLayout= (RelativeLayout) findViewById(R.id.tianjaitupian);
        imageButton= (Button) findViewById(R.id.tijiaofankui);
        backBtn = (ImageButton) findViewById(R.id.fankui_back);
        relativeLayout.setVisibility(View.GONE);
        //获取用户id
        uid=Integer.parseInt(User_id.getUid());


        backBtn.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        editText.setOnClickListener(this);
        relativeLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fankui_back:
                this.finish();
                break;
            case R.id.fankuineirong:


                break;
            case R.id.tianjaitupian:


                break;
            case R.id.tijiaofankui:
                String content=editText.getText().toString();
                if (!content.equals("")){
                    Onteh_init onteh_init=new Onteh();
                    onteh_init.setuserfeedback(uid,content);
                    Toast.makeText(Feedback_Activity.this,"反馈信息成功~",Toast.LENGTH_LONG).show();
                    this.finish();
                }else {
                    Toast.makeText(Feedback_Activity.this,"内容不能为空~",Toast.LENGTH_LONG).show();
                }
                break;

        }

    }
    //收缩软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
}
