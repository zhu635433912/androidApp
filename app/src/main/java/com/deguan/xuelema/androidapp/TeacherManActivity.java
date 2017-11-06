package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.baidu.platform.comapi.map.E;
//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
//import com.deguan.xuelema.androidapp.utils.GlideRoundTransform;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_teacher_man)
public class TeacherManActivity extends MyBaseActivity implements Requirdetailed {

    @ViewById(R.id.teacher_man_person)
    LinearLayout personLl;
    @ViewById(R.id.teacher_man_xueli)
    LinearLayout educationLl;
    @ViewById(R.id.teacher_man_rongyu)
    LinearLayout rongyuLl;
    @ViewById(R.id.teacher_idcard)
    LinearLayout idCardLl;
    @ViewById(R.id.teacher_man_example)
    LinearLayout exampleLl;
    @ViewById(R.id.teacher_man_exper)
    LinearLayout experLl;
    @ViewById(R.id.man_head)
    SimpleDraweeView headImage;
    @ViewById(R.id.teacher_man_sign)
    TextView signTv;
    @ViewById(R.id.teacher_man_back)
    RelativeLayout backRl;
    @ViewById(R.id.teacher_special)
    LinearLayout specialLl;
    @ViewById(R.id.teacher_teach_year)
    LinearLayout teachYearLl;
    @ViewById(R.id.teacher_simple)
    LinearLayout simpleDescLl;
    @ViewById(R.id.teacher_year_tv)
    TextView yearTv;
    @ViewById(R.id.teacher_honor_image)
    ImageView honorImage;
    @ViewById(R.id.teacher_edu_image)
    ImageView eduImage;
    @ViewById(R.id.teacher_name_image)
    ImageView nameImage;
    @ViewById(R.id.teacher_special_tv)
    TextView specialTv;

    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        new Teacher().Get_Teacher_detailed(0,Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void initView() {
        teachYearLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder serviceTypeDialog = new AlertDialog.Builder(TeacherManActivity.this);
                serviceTypeDialog.setIcon(R.drawable.add04);
                serviceTypeDialog.setTitle("请选择服务类型");
                //    指定下拉列表的显示数据
                final String[] fuwuType = {"1年", "2年", "3年" ,"4年", "5年","6年","7年" ,"8年", "9年","10年",
                        "11年", "12年", "13年" ,"14年", "15年","16年","17年" ,"18年", "19年","20年",
                        "21年", "22年", "23年" ,"24年", "25年","26年","27年" ,"28年", "29年","30年",
                        "31年", "32年", "33年" ,"34年", "35年","36年","37年" ,"38年", "39年","40年",
                        "41年", "42年", "43年" ,"44年", "45年","46年","47年" ,"48年", "49年","50年"

                };
                //    设置一个下拉的列表选择项
                serviceTypeDialog.setItems(fuwuType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yearTv.setText(fuwuType[which]);
//                        int years=Integer.parseInt(edit.getText().toString());
                        new Teacher().Teacher_years(Integer.parseInt(User_id.getUid()),which+1);
                    }
                });
                serviceTypeDialog.show();
            }
        });
        simpleDescLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SimpleDescActivity_.intent(TeacherManActivity.this).get());
            }
        });
        specialLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SpecialActivity_.intent(TeacherManActivity.this).get());
            }
        });
        personLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherManActivity.this,Personal_Activty.class));
            }
        });
        educationLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EducationActivity_.intent(TeacherManActivity.this).get());
            }
        });
        rongyuLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HonorActivity_.intent(TeacherManActivity.this).get());
            }
        });
        idCardLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TeacherActivity_.intent(TeacherManActivity.this).get());
            }
        });
        exampleLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExampleActivity_.intent(TeacherManActivity.this).get());
            }
        });
        experLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(ExerActivity_.intent(TeacherManActivity.this).get());
            }
        });
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void initData() {
        new Teacher().Get_Teacher_detailed(0,Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("user_headimg")+""))
//        Glide.with(getApplicationContext()).load(map.get("user_headimg")).
//                transform(new GlideRoundTransform(this,12)).into(headImage);
        headImage.setImageURI(Uri.parse(map.get("user_headimg")+""));
        if (!TextUtils.isEmpty(map.get("signature")+""))
            signTv.setText(map.get("signature")+"");

        if (!TextUtils.isEmpty(map.get("is_passed")+"")){
            if (map.get("is_passed").equals("1")){
                if (!TextUtils.isEmpty(map.get("others_5")+"")||!TextUtils.isEmpty(map.get("others_6")+"")) {
                    nameImage.setImageResource(R.mipmap.teacher_pass_name);
                }
                if (!TextUtils.isEmpty(map.get("others_1")+"")||!TextUtils.isEmpty(map.get("others_2")+"")) {
                    eduImage.setImageResource(R.mipmap.teacher_pass_edu);
                }
                if (!TextUtils.isEmpty(map.get("others_3")+"")||!TextUtils.isEmpty(map.get("others_4")+"")) {
                    honorImage.setImageResource(R.mipmap.teacher_pass_honor);
                }
            }
        }
        if (!TextUtils.isEmpty(map.get("years")+"")){
            yearTv.setText(map.get("years")+"年");
        }
        if (!TextUtils.isEmpty(map.get("speciality")+"")){
            specialTv.setText(map.get("speciality")+"");
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
