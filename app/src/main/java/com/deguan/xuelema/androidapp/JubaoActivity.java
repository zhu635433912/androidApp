package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.UpReportView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import retrofit2.Retrofit;

/**
 * 举报
 */
@EActivity(R.layout.activity_jubao)
public class JubaoActivity extends MyBaseActivity implements UpReportView {

    private int teacherId;
    @ViewById(R.id.jubaoneirong)
    EditText jubaoTv;
    @ViewById(R.id.jubao_back)
    ImageButton jubaoBack;
    @ViewById(R.id.tijiaojubao)
    Button jubaoBtn;
    @ViewById(R.id.jubaoclos)
    RelativeLayout relativeLayout;

    @Override
    public void before() {
        super.before();
        teacherId = getIntent().getIntExtra("teacher_id",411);
    }


    @Override
    public void initView() {
        jubaoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        jubaoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(jubaoTv.getText().toString())){
                    new Getdata().upReport(teacherId,jubaoTv.getText().toString(),JubaoActivity.this,Integer.parseInt(User_id.getUid()));
                }else {
                    Toast.makeText(JubaoActivity.this, "请输入举报内容", Toast.LENGTH_SHORT).show();
                }
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void successUpReport(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void failUpReport(String msg) {
        Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show();
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
