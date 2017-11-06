package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.CashView;
import com.deguan.xuelema.androidapp.viewimpl.OnPasswordInputFinish;
import com.deguan.xuelema.androidapp.viewimpl.PasswordView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;
import modle.getdata.PayUtil;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_with_draw)
public class WithDrawActivity extends MyBaseActivity implements View.OnClickListener, Requirdetailed, CashView {

    @ViewById(R.id.withdraw_back)
    RelativeLayout backRl;
    @ViewById(R.id.cash_name)
    EditText nameEdit;
    @ViewById(R.id.cash_id)
    EditText idEdit;
    @ViewById(R.id.cash_fee)
    EditText feeEdit;
    @ViewById(R.id.cash_type)
    CardView sureBtn;

    private PopupWindow payPopwindow;
    @ViewById(R.id.withdraw_line)
    View view;

    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initData() {
        new Getdata().getFee(Integer.parseInt(User_id.getUid()),this);
    }

    @Override
    public void initView() {

        sureBtn.setOnClickListener(this);
        backRl.setOnClickListener(this);
        feeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    sureBtn.setCardBackgroundColor(Color.parseColor("#e7d47f"));
                    sureBtn.setClickable(true);
                }else {
                    sureBtn.setCardBackgroundColor(Color.parseColor("#d9d9d9"));
                    sureBtn.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable edt) {
                String temp = edt.toString();
                int posDot = temp.indexOf(".");
                if (posDot <= 0) return;
                if (temp.length() - posDot - 1 > 2)
                {
                    edt.delete(posDot + 3, posDot + 4);
                }
            }
        });
        showPayPop();
    }
    private void showPayPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.pay_psd_pop,null);
        final PasswordView passwordView = (PasswordView) view.findViewById(R.id.pwd_view);
        payPopwindow = new PopupWindow(view);
        payPopwindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        payPopwindow.setWidth(width);
        payPopwindow.setHeight(height);
        payPopwindow.setOutsideTouchable(true);
        payPopwindow.setBackgroundDrawable(new BitmapDrawable());
        passwordView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish() {
                new Getdata().getCash(Integer.parseInt(User_id.getUid()), nameEdit.getText().toString(),
                        idEdit.getText().toString(), 1, Float.parseFloat(feeEdit.getText().toString()), passwordView.getStrPassword(), WithDrawActivity.this);
                feeEdit.setText("");
                passwordView.clearPassword();
                passwordView.setCurrentIndex(-1);
                payPopwindow.dismiss();
            }
        });
        passwordView.getCancelImageView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordView.clearPassword();
                payPopwindow.dismiss();
            }
        });
        passwordView.getForgetTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WithDrawActivity.this, "忘记密码请联系客服", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WithDrawActivity.this,Hepl.class));
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.withdraw_back:
                finish();
                break;
            case R.id.cash_type:
                if (!TextUtils.isEmpty(nameEdit.getText().toString())&&!TextUtils.isEmpty(idEdit.getText().toString())&&!TextUtils.isEmpty(feeEdit.getText().toString())) {
                    if (Double.parseDouble(feeEdit.getText().toString())>myBalance){
                        Toast.makeText(WithDrawActivity.this, "可提现金额不足", Toast.LENGTH_SHORT).show();
                    }else {
                        if (Double.parseDouble(feeEdit.getText().toString()) %100 == 0) {

                            payPopwindow.showAsDropDown(view);
                        }else {
                            Toast.makeText(WithDrawActivity.this, "请整百提现", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else {
                    Toast.makeText(WithDrawActivity.this, "请输入完整", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private double myBalance =0 ;
    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (map.get("fee") != null) {
            myBalance = (double) map.get("fee");
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successCash(String msg) {
        Toast.makeText(this, "提现将在3个工作日之内到账,请注意查收", Toast.LENGTH_SHORT).show();
        new Getdata().getFee(Integer.parseInt(User_id.getUid()),this);
        finish();
    }

    @Override
    public void failCash(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
