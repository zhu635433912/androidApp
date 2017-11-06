package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.entities.CourseEntity;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment_;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.MyPublishView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Order_Modle.Order;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_my_publish)
public class MyPublishActivity extends MyBaseActivity implements MyPublishView {

    @ViewById(R.id.back_image)
    ImageView backImage;
    @ViewById(R.id.layout)
    FrameLayout layout;
    @ViewById(R.id.title_tv)
    TextView titleTv;

    TextView baseTv;
    private PopupWindow messageWindow,buycoursePopwindow,buyPopwindow;
    private TextView contentTv;
    private String user_id;
    private String user_headimg;
    private String id;
    private String content;
    private String teacherName;
    private TextView nameTv,signTv,resumeTv,numberTv,distanceTv,scoreTv,nextTv,buyTv;
    private SimpleDraweeView headImage;
    private ImageView starImage;
    private String distance;
    private String fee;
    private String resume;
    private double order_rank;
    private String teach_count;
    private String grade_name;
    private String signature;
    private String course_name;
    private List<Map<String,Object>> datas = new ArrayList<>();


    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public void initView() {
        super.initView();
        titleTv.setText("我的发布");
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyPublishFragment fragment = MyPublishFragment_.builder().build();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager. beginTransaction();
        transaction.add(R.id.layout, MyPublishFragment_.builder().build());
        transaction.commit();
    }

    @Subscriber(tag = "demandId")
    public void getDemandId(String demandId){
        new Order().getDemandOrder(Integer.parseInt(User_id.getUid()),demandId,User_id.getLat()+"",User_id.getLng()+"",this);
    }
    private void showbuyDialog() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.student_buy_pop, null);
//        View backView = view.findViewById(R.id.message_back);
        ImageView closeImage = (ImageView) view.findViewById(R.id.buy_pop_close);
        nameTv = (TextView) view.findViewById(R.id.buy_nickname_tv);
        signTv = (TextView) view.findViewById(R.id.buy_sign_tv);
        resumeTv = (TextView) view.findViewById(R.id.buy_resume_tv);
        numberTv = (TextView) view.findViewById(R.id.buy_number_tv);
        distanceTv = (TextView) view.findViewById(R.id.buy_distance_tv);
        scoreTv = (TextView) view.findViewById(R.id.buy_score_tv);
        nextTv = (TextView) view.findViewById(R.id.buy_next_tv);
        buyTv = (TextView) view.findViewById(R.id.buy_buy_tv);
        headImage = (SimpleDraweeView) view.findViewById(R.id.buy_head_image);
        starImage = (ImageView) view.findViewById(R.id.buy_star_image);
        buyPopwindow = new PopupWindow(view);
        buyPopwindow.setFocusable(false);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        buyPopwindow.setWidth(width / 10 * 8);
        buyPopwindow.setHeight(height / 5 * 3 );
        buyPopwindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
        buyPopwindow.setOutsideTouchable(false);
        buyPopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(MyPublishActivity.this, 1f);
            }
        });
//        backView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageWindow.dismiss();
//            }
//        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPopwindow.dismiss();
            }
        });
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag ++;
                if (flag < datas.size()) {
                    user_id = ""+datas.get(flag).get("teacher_id");
                    user_headimg = ""+datas.get(flag).get("teacher_headimg");
                    id = ""+datas.get(flag).get("id");
                    content = ""+datas.get(flag).get("content");
                    teacherName = ""+datas.get(flag).get("teacher_name");
                    distance = ""+datas.get(flag).get("distance");
                    fee = ""+datas.get(flag).get("visit_fee");
                    resume = ""+datas.get(flag).get("teacher_resume");
                    order_rank = Double.parseDouble(datas.get(flag).get("order_rank")+"");
                    teach_count = ""+datas.get(flag).get("teach_count");
                    grade_name = ""+datas.get(flag).get("grade_name");
                    signature = ""+datas.get(flag).get("teacher_signature");
                    course_name = ""+datas.get(flag).get("course_name");
                    courseId = "" + datas.get(flag).get("course_id");
                    course_remark = ""+datas.get(flag).get("course_remark");
                    gradeId  = "" + datas.get(flag).get("grade_id");
                    visit_fee = "" + datas.get(flag).get("visit_fee");
                    unvisit_fee = "" + datas.get(flag).get("unvisit_fee");

                    nameTv.setText(teacherName);
                    signTv.setText(signature);
                    resumeTv.setText(resume);
                    numberTv.setText(teach_count);


                    if (order_rank<1.5){
                        starImage.setImageResource(R.drawable.one);
                    }else if (order_rank >= 1.5 && order_rank <2.5){
                        starImage.setImageResource(R.drawable.two);
                    }else if (order_rank >= 2.5 && order_rank <3.5){
                        starImage.setImageResource(R.drawable.three);
                    }else if (order_rank >= 3.5 && order_rank <4.5){
                        starImage.setImageResource(R.drawable.four);
                    }else if (order_rank >= 4.5 ){
                        starImage.setImageResource(R.drawable.five);
                    }

                    double myDist1 = 0;
                    if (!distance.equals("")){
                        myDist1 = Double.parseDouble(distance)/1000;
                    }
                    DecimalFormat df = new DecimalFormat("#0.0");
                    distanceTv.setText("    距我"+df.format(myDist1) + "km");
                    scoreTv.setText(order_rank+"");
                    headImage.setImageURI(Uri.parse(user_headimg));

                }else {
                    buyPopwindow.dismiss();
                }
            }
        });

        buyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CourseEntity entity = new CourseEntity();
                entity.setContent(content);
                entity.setCourse_id(courseId);
                entity.setCourse_name(course_name);
                entity.setCourse_remark(course_remark);
                entity.setGrade_id(gradeId);
                entity.setUnvisit_fee(unvisit_fee);
                entity.setVisit_fee(visit_fee);
                buyPopwindow.dismiss();

//                EventBus.getDefault().post("id","BUYCOURSE");
                startActivity(NewTeacherPersonActivity_.intent(MyPublishActivity.this)
                        .extra("courseId",courseId)
                        .extra("course_name",course_name)
                        .extra("course_remark",course_remark)
                        .extra("gradeId",gradeId)
                        .extra("unvisit_fee",unvisit_fee)
                        .extra("visit_fee",visit_fee)


                        .extra("myid","0")
                        .extra("content", content)
                        .extra("head_image",user_headimg)
                        .extra("user_id",user_id).get());
            }
        });
    }
    private int flag = 0;
    private String courseId;
    private String course_remark,gradeId;
    private String visit_fee;
    private String unvisit_fee;

    @Override
    public void successMyPublish(List<Map<String, Object>> maps) {
        if (maps != null) {
            datas = maps;
            if (datas.size()>= 1){
                user_id = ""+datas.get(0).get("teacher_id");
                user_headimg = ""+datas.get(0).get("teacher_headimg");
                id = ""+datas.get(0).get("id");
                content = ""+datas.get(0).get("content");
                teacherName = ""+datas.get(0).get("teacher_name");
                distance = ""+datas.get(0).get("distance");
                fee = ""+datas.get(0).get("visit_fee");
                resume = ""+datas.get(0).get("teacher_resume");
                order_rank = Double.parseDouble(datas.get(0).get("order_rank")+"");
                teach_count = ""+datas.get(0).get("teach_count");
                grade_name = ""+datas.get(0).get("grade_name");
                signature = ""+datas.get(0).get("teacher_signature");
                course_name = ""+datas.get(0).get("course_name");
                courseId = "" + datas.get(0).get("course_id");
                course_remark = ""+datas.get(0).get("course_remark");
                gradeId  = "" + datas.get(0).get("grade_id");
                visit_fee = "" + datas.get(0).get("visit_fee");
                unvisit_fee = "" + datas.get(0).get("unvisit_fee");
                flag = 0 ;
                showbuyDialog();
                buyPopwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
                nameTv.setText(teacherName);
                signTv.setText(signature);
                resumeTv.setText(resume);
                numberTv.setText(teach_count);


                if (order_rank<1.5){
                    starImage.setImageResource(R.drawable.one);
                }else if (order_rank >= 1.5 && order_rank <2.5){
                    starImage.setImageResource(R.drawable.two);
                }else if (order_rank >= 2.5 && order_rank <3.5){
                    starImage.setImageResource(R.drawable.three);
                }else if (order_rank >= 3.5 && order_rank <4.5){
                    starImage.setImageResource(R.drawable.four);
                }else if (order_rank >= 4.5 ){
                    starImage.setImageResource(R.drawable.five);
                }

                double myDist1 = 0;
                if (!distance.equals("")){
                    myDist1 = Double.parseDouble(distance)/1000;
                }
                DecimalFormat df = new DecimalFormat("#0.0");
                distanceTv.setText("    距我"+df.format(myDist1) + "km");
                scoreTv.setText(order_rank+"");

                headImage.setImageURI(Uri.parse(user_headimg));
            }
        }
    }

    @Override
    public void failMyPublish(String msg) {

    }

}
