package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.utils.MyBaseActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import modle.user_ziliao.User_id;
import view.login.Modle.MobileView;
import view.login.Modle.RegisterEntity;
import view.login.Modle.RegisterUtil;
import view.login.ViewActivity.LoginAcitivity;

@EActivity(R.layout.activity_pay_psw)
public class PayPswActivity extends MyBaseActivity implements MobileView {

    @ViewById(R.id.back_image)
    ImageView backImage;
    @ViewById(R.id.psd_new)
    EditText newPwdEdit;
    @ViewById(R.id.psd_sure)
    EditText surdPwdEdit;
    @ViewById(R.id.psd_btn)
    Button nextBtn;
    @ViewById(R.id.sure_paypwd_tv)
    TextView sureTv;
    @ViewById(R.id.new_paypwd_tv)
    TextView newTv;
    @ViewById(R.id.psd_two)
    EditText twoEdit;
    @ViewById(R.id.two_paypwd_tv)
    TextView twoTv;
    private String newPwd;
    private String oldPwd;
    private int type;
    private String twoPwd;

    @Override
    public void before() {
        super.before();
        type = getIntent().getIntExtra("type",0);
    }

    @Override
    public void initView() {
        if (type == 2){
            sureTv.setText("新密码");
            newTv.setText("旧密码");
            twoEdit.setVisibility(View.VISIBLE);
            twoTv.setVisibility(View.VISIBLE);
            backImage.setVisibility(View.VISIBLE);
        }else {
            sureTv.setText("确认密码");
            newTv.setText("新密码");
            twoEdit.setVisibility(View.GONE);
            backImage.setVisibility(View.GONE);
            twoTv.setVisibility(View.GONE);
        }

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(newPwdEdit.getText())||newPwdEdit.getText().length()!= 6) {
                    Toast.makeText(PayPswActivity.this, "请输入6位支付密码", Toast.LENGTH_SHORT).show();
                        return;
                }else {
                    newPwd = newPwdEdit.getText().toString();
                }
                if (TextUtils.isEmpty(surdPwdEdit.getText())||surdPwdEdit.getText().length()!= 6) {
                    Toast.makeText(PayPswActivity.this, "请确认6位支付密码", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    oldPwd = surdPwdEdit.getText().toString();
                }
                if (type == 2){
                    if (TextUtils.isEmpty(twoEdit.getText())||twoEdit.getText().length()!= 6){
                        Toast.makeText(PayPswActivity.this, "请确认新密码", Toast.LENGTH_SHORT).show();
                    }else {
                        twoPwd = twoEdit.getText().toString();
                        if (!newPwd.equals(oldPwd)) {
                            if (oldPwd.equals(twoPwd)){
                                new RegisterUtil().setPaypsd(Integer.parseInt(User_id.getUid()), oldPwd, newPwd, 2, PayPswActivity.this);
                            }else {
                                Toast.makeText(PayPswActivity.this, "新密码2次输入不一致", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PayPswActivity.this, "两次输入密码一致", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else {
                    if (newPwd.equals(oldPwd)) {
                        new RegisterUtil().setPaypsd(Integer.parseInt(User_id.getUid()), newPwd, "", 1, PayPswActivity.this);
                    } else {
                        Toast.makeText(PayPswActivity.this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void successRegister(String msg) {
        Toast.makeText(this, "设置成功", Toast.LENGTH_SHORT).show();
        startActivity(NewMainActivity_.intent(this).get());
    }

    @Override
    public void failRegister(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

    }

    @Override
    public void onBackPressed() {
        if (type == 2){
            finish();
        }else {
            startActivity(new Intent(this, LoginAcitivity.class));
            finish();
        }
    }
}
