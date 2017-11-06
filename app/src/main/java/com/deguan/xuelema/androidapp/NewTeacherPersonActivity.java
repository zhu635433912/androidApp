package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.CourseEntity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.deguan.xuelema.androidapp.viewimpl.FlexibleScrollView;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import jiguang.chat.database.FriendRecommendEntry;
import jiguang.chat.database.UserEntry;
import jiguang.chat.entity.Event;
import jiguang.chat.entity.FriendInvitation;
import jiguang.chat.utils.ToastUtil;
import modle.Adapter.CoursePopAdapter;
import modle.Increase_course.Increase_course;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_new_teacher_person)
public class NewTeacherPersonActivity extends MyBaseActivity implements Requirdetailed, Baseinit, View.OnClickListener, TuijianView {

    @ViewById(R.id.course_line1)
    View line1;
    @ViewById(R.id.course_line2)
    View line2;
    @ViewById(R.id.course_line3)
    View line3;
    @ViewById(R.id.teacher_person_back)
    TextView backTv;
    @ViewById(R.id.flexible_scroll_vew)
    FlexibleScrollView mScrollView;
    @ViewById(R.id.teacher_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.teacher_gender_image)
    ImageView genderImage;
    @ViewById(R.id.teacher_address_tv)
    TextView addressTv;
    @ViewById(R.id.teacher_nickname_tv)
    TextView nicknameTv;
    @ViewById(R.id.teacher_sign_tv)
    TextView signTv;
    @ViewById(R.id.teacher_student_number)
    TextView studentNumberTv;
    @ViewById(R.id.teacher_order_number)
    TextView orderNumberTv;
    @ViewById(R.id.teacher_complete_number)
    TextView completeNumberTv;
    @ViewById(R.id.teacher_jubao)
    TextView jubaoTv;
    @ViewById(R.id.teacher_time_tv)
    TextView timeTv;
    @ViewById(R.id.teacher_course_more)
    TextView courseMoreTv;
    @ViewById(R.id.teacher_one_course)
    TextView oneCourseTv;
    @ViewById(R.id.teacher_one_fee)
    TextView onefeerTv;
    @ViewById(R.id.teacher_one_ll)
    LinearLayout oneLl;
    @ViewById(R.id.teacher_two_course)
    TextView twoCourseTv;
    @ViewById(R.id.teacher_two_fee)
    TextView twofeerTv;
    @ViewById(R.id.teacher_two_ll)
    LinearLayout twoLl;
    @ViewById(R.id.teacher_three_course)
    TextView threeCourseTv;
    @ViewById(R.id.teacher_three_fee)
    TextView threefeerTv;
    @ViewById(R.id.teacher_three_ll)
    LinearLayout threeLl;
    @ViewById(R.id.teacher_year_tv)
    TextView yearTv;
    @ViewById(R.id.user_card_pass)
    TextView idPassTv;
    @ViewById(R.id.user_education_pass)
    TextView educationPassTv;
    @ViewById(R.id.teacher_school_tv)
    TextView schoolTv;
    @ViewById(R.id.teacher_like_tv)
    TextView likeTv;
    @ViewById(R.id.teacher_star_image)
    ImageView starImage;
    @ViewById(R.id.teacher_star_tv)
    TextView starTv;
    @ViewById(R.id.teacher_simple_tv)
    TextView simpleTv;
    @ViewById(R.id.teacher_zone_image1)
    SimpleDraweeView zoneImage1;
    @ViewById(R.id.teacher_zone_image2)
    SimpleDraweeView zoneImage2;
    @ViewById(R.id.teacher_zone_image3)
    SimpleDraweeView zoneImage3;
    @ViewById(R.id.teacher_example_course)
    TextView exampleCourseTv;
    @ViewById(R.id.teacher_example_more)
    TextView exampleMoreTv;
    @ViewById(R.id.teacher_example_content)
    TextView exampleContentTv;
    @ViewById
    ImageButton  imageButton;
    @ViewById
    ImageButton  bohao;
    @ViewById
    ImageButton  imageButton2;
    @ViewById(R.id.teacher_star_ll)
    LinearLayout starLl;


    private UserInfo mMyInfo;
    private int Requir_id;
    private String username;//教师id
    private String mobile;
    private String userHeadUrl = "";
    private String myid;
    private String address;
    private String content = "";
    private int coursefee,teacherId,courseNumber,servicetype,studentfee,teacherfee;
    private String courseId,gradeId,serviceType,courseName;
    private int id;
    private String zoneUrl1="";
    private String zoneUrl2="";
    private String zoneUrl3="";
    private List<Map<String,Object>> courseDatas = new ArrayList<>();
    private CoursePopAdapter adapter;
    private EditText descEdit;

    private CourseEntity entity = new CourseEntity();
    private String course_remark ;
    private String nickname;


    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
        if (getIntent().getStringExtra("content")!=null) {
            content = getIntent().getStringExtra("content");
        }
        address = User_id.getAddress();
        myid = getIntent().getStringExtra("myid");
        if (myid == null)
        {
            myid = "0";
        }
        final String user_id=getIntent().getStringExtra("user_id");
//        Log.e("aa","UserxinActivyt接收到老师id为"+user_id);
        id = Integer.parseInt(User_id.getUid());
        Requir_id=Integer.parseInt(user_id);
        teacherId = Requir_id;
        userHeadUrl = getIntent().getStringExtra("head_image");
        if (getIntent().getStringExtra("courseId") != null){
            course_remark = getIntent().getStringExtra("course_remark");
            entity.setContent(getIntent().getStringExtra("content"));
            entity.setCourse_id(getIntent().getStringExtra("courseId"));
            entity.setCourse_name(getIntent().getStringExtra("course_name"));
            entity.setCourse_remark(getIntent().getStringExtra("course_remark"));
            entity.setGrade_id(getIntent().getStringExtra("gradeId"));
            entity.setUnvisit_fee(getIntent().getStringExtra("unvisit_fee"));
            entity.setVisit_fee(getIntent().getStringExtra("visit_fee"));
        }


    }

    @Subscriber(tag = "buycourse")
    public void getBuyCourse(final CourseEntity entity){
        Log.d("aa","buycourse-----------1");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        super.initData();
        mMyInfo = JMessageClient.getMyInfo();
        if (!userHeadUrl.equals(""))
//            Glide.with(getApplicationContext()).load(userHeadUrl).transform(
//                    new GlideCircleTransform(this)).into(gerxxtoux);
            headImage.setImageURI(Uri.parse(userHeadUrl));
        //加载数据
        Teacher_init teacher=new Teacher();
        teacher.getTeacherDetailed(User_id.getLat()+"",User_id.getLng()+"",id,Requir_id,this,0,1);

        //获取成交率
        Getdata getdata=new Getdata();
        getdata.getdelt_info(Requir_id,this);

        //加载课程数据
        Increase_course increaseCourse=new Increase_course();
        increaseCourse.selecouse(Requir_id,this,null);
    }

    @Override
    public void initView() {
        super.initView();
//        mScrollView = (FlexibleScrollView) findViewById(R.id.flexible_scroll_vew);
        mScrollView.bindActionBar(findViewById(R.id.custom_action_bar));
        mScrollView.setHeaderView(findViewById(R.id.flexible_header_view));
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headImage.setOnClickListener(this);
        studentNumberTv.setOnClickListener(this);
        orderNumberTv.setOnClickListener(this);
        completeNumberTv.setOnClickListener(this);
        jubaoTv.setOnClickListener(this);
        courseMoreTv.setOnClickListener(this);
         oneLl.setOnClickListener(this);
         twoLl.setOnClickListener(this);
         threeLl.setOnClickListener(this);
         starImage.setOnClickListener(this);
         starTv.setOnClickListener(this);
         zoneImage1.setOnClickListener(this);
         zoneImage2.setOnClickListener(this);
         zoneImage3.setOnClickListener(this);
         exampleMoreTv.setOnClickListener(this);
        imageButton.setOnClickListener(this);
          bohao.setOnClickListener(this);
          imageButton2.setOnClickListener(this);
        starLl.setOnClickListener(this);


    }


    private void showCoursePop(){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.teacher_course_pop,null);
        ListView courseList = (ListView) view.findViewById(R.id.teacher_course_list);
        coursePop = new PopupWindow(view);
        coursePop.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width/10*9);
        coursePop.setHeight(height/5*2);
        coursePop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        coursePop.setOutsideTouchable(true);
        coursePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(NewTeacherPersonActivity.this, 1f);
            }
        });
        courseList.setAdapter(adapter);
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                coursePop.dismiss();
                showBuyPop();
                buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                Map<String, Object> map = new HashMap<String, Object>();
                map = courseDatas.get(position);
                teacherfee = Integer.parseInt((String)map.get("visit_fee"));
                studentfee = Integer.parseInt((String)map.get("unvisit_fee"));
                coursefee = studentfee;
                courseId = (String) map.get("course_id");
                gradeId = (String)map.get("grade_id");
                courseName =  (String)map.get("course_name") ;
                courseNumber = 1;

                keshi.setText(courseNumber+"");
                courseMoney.setText(coursefee+"元/小时");
                courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
                courseExplain.setText((String)map.get("course_remark"));
                zongfee.setText(coursefee*courseNumber+"");
            }
        });
    }

    private TextView courseMoney;
    private TextView zongfee;
    private TextView keshi;
    private Button buyBtn;
    private TextView courseExplain;
    private TextView courseType;
    private TextView courseNametV;
    private PopupWindow buyPopWindow,coursePop;
    private TextView studentShangmen,teacherShangmen;
    private void showBuyPop() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.purchase_course,null);
        courseMoney = (TextView) view.findViewById(R.id.jieshufee);
        zongfee = (TextView) view.findViewById(R.id.zongfee);
        TextView jia= (TextView) view.findViewById(R.id.jia);
        TextView jian= (TextView) view.findViewById(R.id.jian);
        keshi = (TextView) view.findViewById(R.id.fee);
        buyBtn = (Button) view.findViewById(R.id.goumai);
        courseExplain = (TextView) view.findViewById(R.id.course_explain);
        courseType = (TextView) view.findViewById(R.id.xuessm);
        courseNametV = (TextView) view.findViewById(R.id.kechengname);
        studentShangmen  = (TextView) view.findViewById(R.id.xuessm);
        teacherShangmen = (TextView) view.findViewById(R.id.laoshism);
        descEdit = (EditText) view.findViewById(R.id.buy_course_desc);
        studentShangmen.setTextColor(Color.parseColor("#fd1245"));
        buyPopWindow = new PopupWindow(view);
        buyPopWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        buyPopWindow.setWidth(width/10*9);
        buyPopWindow.setHeight(height/5*2);
        buyPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        buyPopWindow.setOutsideTouchable(true);
        buyPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(NewTeacherPersonActivity.this, 1f);
            }
        });
        servicetype = 2;

        studentShangmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentShangmen.setTextColor(0xfffd1245);
                teacherShangmen.setTextColor(0xff8b8b8b);
                servicetype = 2;
                coursefee = studentfee;
//                studentShangmen.setTextColor(Color.parseColor("#fd1245"));
//                teacherShangmen.setTextColor(Color.parseColor("#8b8b8b"));
                courseMoney.setText(coursefee+"元/小时");
                zongfee.setText(coursefee*courseNumber+"元");

            }
        });
        teacherShangmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursefee = teacherfee;
                teacherShangmen.setTextColor(0xfffd1245);
                studentShangmen.setTextColor(0xff8b8b8b);
                servicetype = 1;
                courseMoney.setText(coursefee+"元/小时");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseNumber ++;
                keshi.setText(courseNumber+"");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseNumber > 1){
                    courseNumber --;
                }
                keshi.setText(courseNumber+"");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        descEdit.setText(content);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NewTeacherPersonActivity.this).setTitle("学习吧提示!").setMessage("是否确定下单?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buyPopWindow.dismiss();
                                int uid=Integer.parseInt(User_id.getUid());
                                Order_init order_init=new Order();
                                //创建订单
                                if (!TextUtils.isEmpty(descEdit.getText())) {
                                    order_init.Establish_Order(NewTeacherPersonActivity.this,uid, teacherId, Integer.parseInt(myid), coursefee, courseNumber, Integer.parseInt(courseId), Integer.parseInt(gradeId), servicetype,
                                            User_id.getAddress(), User_id.getProvince(), User_id.getCity(), User_id.getStatus(), descEdit.getText().toString() );
                                }else {
                                    order_init.Establish_Order(NewTeacherPersonActivity.this,uid, teacherId, Integer.parseInt(myid), coursefee, courseNumber, Integer.parseInt(courseId), Integer.parseInt(gradeId), servicetype,
                                            User_id.getAddress(), User_id.getProvince(), User_id.getCity(), User_id.getStatus(),"" );
                                }

                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(NewTeacherPersonActivity.this,"再看看别的老师吧~",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        nickname = "" + map.get("nickname");
        String resume = map.get("resume") + "";
        username = map.get("username") + "";
        signTv.setText(map.get("signature")+"");
        address = map.get("address")+"";
        addressTv.setText(address);
        mobile=  map.get("mobile")+ "";
        String order_finish=  map.get("order_finish")+ "";
        String order_working=  map.get("order_working")+ "";
        studentNumberTv.setText("学生  "+order_working);
        orderNumberTv.setText("订单  "+order_finish);
        simpleTv.setText(resume);
        nicknameTv.setText(nickname);
        String gender = map.get("gender") + "";
        if (gender.equals("2")){
            genderImage.setImageResource(R.mipmap.gender_female_icon);
        }
        if (!TextUtils.isEmpty(map.get("teach_time")+"")){
            timeTv.setText(map.get("teach_time")+"");
        }
        if (map.get("years") != null)
            yearTv.setText(map.get("years")+"年");
        if (map.get("graduated_school") != null)
            schoolTv.setText("毕业学校：         "+map.get("graduated_school")+"      "+map.get("education"));
        if (map.get("speciality")!=null){
            likeTv.setText("兴趣爱好：   "+map.get("speciality")+"");
        }
        starTv.setText(map.get("order_rank")+"  >>");
        double orderrank = Double.parseDouble(map.get("order_rank")+"");
        if (orderrank<1.5){
            starImage.setImageResource(R.drawable.one);
        }else if (orderrank >= 1.5 && orderrank <2.5){
            starImage.setImageResource(R.drawable.two);
        }else if (orderrank >= 2.5 && orderrank <3.5){
            starImage.setImageResource(R.drawable.three);
        }else if (orderrank >= 3.5 && orderrank <4.5){
            starImage.setImageResource(R.drawable.four);
        }else if (orderrank >= 4.5 ){
            starImage.setImageResource(R.drawable.five);
        }
        if (!TextUtils.isEmpty(map.get("img1")+"")) {
            zoneUrl1 = map.get("img1")+"";
            zoneImage1.setVisibility(View.VISIBLE);
            zoneImage1.setImageURI(Uri.parse(map.get("img1") + ""));
        }
        if (!TextUtils.isEmpty(map.get("img2")+"")) {
            zoneUrl2 = map.get("img2")+"";
            zoneImage2.setVisibility(View.VISIBLE);
            zoneImage2.setImageURI(Uri.parse(map.get("img2") + ""));
        }
        if (!TextUtils.isEmpty(map.get("img3")+"")) {
            zoneUrl3 = map.get("img3")+"";
            zoneImage3.setVisibility(View.VISIBLE);
            zoneImage3.setImageURI(Uri.parse(map.get("img3") + ""));
        }
        if (map.get("is_passed").equals("1")){
            educationPassTv.setTextColor(Color.parseColor("#f76d1d"));
            idPassTv.setTextColor(Color.parseColor("#f76d1d"));
        }
        if (TextUtils.isEmpty(map.get("teach_course")+"")){
            exampleMoreTv.setVisibility(View.GONE);
        }else {
            exampleCourseTv.setText(map.get("teach_completetime") + "     " + map.get("teach_course"));
            exampleContentTv.setText(map.get("teach_content") + "");
        }
        String dist1 = map.get("distance")+"";
        double myDist1 = 0;
        if (!dist1.equals("")){
            myDist1 = Double.parseDouble(dist1)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");
        if (!TextUtils.isEmpty(map.get("teach_address")+"")){
            addressTv.setText(map.get("teach_address")+"");
        }else {
            addressTv.setText("地址：  " + map.get("city") + "  " + map.get("state") + "    距我" + df.format(myDist1) + "km");
        }

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

        courseDatas.addAll(listmap);
        adapter = new CoursePopAdapter(courseDatas,this);
        if (listmap.size()==0){

        }else if (listmap.size()==1){
            line1.setVisibility(View.VISIBLE);
            oneLl.setVisibility(View.VISIBLE);
            oneCourseTv.setText(listmap.get(0).get("course_name")+"     "+listmap.get(0).get("grade_name"));
            onefeerTv.setText(listmap.get(0).get("unvisit_fee")+"元/小时");
        }else if (listmap.size()==2){
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            oneLl.setVisibility(View.VISIBLE);
            twoLl.setVisibility(View.VISIBLE);
            oneCourseTv.setText(listmap.get(0).get("course_name")+"     "+listmap.get(0).get("grade_name"));
            onefeerTv.setText(listmap.get(0).get("unvisit_fee")+"元/小时");
            twoCourseTv.setText(listmap.get(1).get("course_name")+"     "+listmap.get(1).get("grade_name"));
            twofeerTv.setText(listmap.get(1).get("unvisit_fee")+"元/小时");
        }else {
            line1.setVisibility(View.VISIBLE);
            line2.setVisibility(View.VISIBLE);
            line3.setVisibility(View.VISIBLE);
            courseMoreTv.setVisibility(View.VISIBLE);
            oneLl.setVisibility(View.VISIBLE);
            twoLl.setVisibility(View.VISIBLE);
            threeLl.setVisibility(View.VISIBLE);
            oneCourseTv.setText(listmap.get(0).get("course_name")+"     "+listmap.get(0).get("grade_name"));
            onefeerTv.setText(listmap.get(0).get("unvisit_fee")+"元/小时");
            twoCourseTv.setText(listmap.get(1).get("course_name")+"     "+listmap.get(1).get("grade_name"));
            twofeerTv.setText(listmap.get(1).get("unvisit_fee")+"元/小时");
            threeCourseTv.setText(listmap.get(2).get("course_name")+"     "+listmap.get(2).get("grade_name"));
            threefeerTv.setText(listmap.get(2).get("unvisit_fee")+"元/小时");
        }
        if (!TextUtils.isEmpty(course_remark)) {
            showBuyPop();
            buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            Map<String, Object> map = new HashMap<String, Object>();
//        map = courseDatas.get(position);
            teacherfee = Integer.parseInt(entity.getVisit_fee());
            studentfee = Integer.parseInt(entity.getUnvisit_fee());
            coursefee = studentfee;
            courseId = entity.getCourse_id();
            gradeId = entity.getGrade_id();
            courseName = entity.getCourse_name();
            courseNumber = 1;

            keshi.setText(courseNumber + "");
            courseMoney.setText(coursefee + "元/小时");
            courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
            courseExplain.setText(entity.getCourse_remark());
            zongfee.setText(coursefee * courseNumber + "");
            descEdit.setText(entity.getContent());
        }
    }

    @Override
    public void upcontent(Map<String, Object> map) {
        completeNumberTv.setText("成交率  "+(int)(Double.parseDouble(map.get("comp_rate")+"")*100)+"%");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.teacher_course_more:
                showCoursePop();
                coursePop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.teacher_one_ll:
                showBuyPop();
                buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                Map<String, Object> map = new HashMap<String, Object>();
                map = courseDatas.get(0);
                teacherfee = Integer.parseInt((String)map.get("visit_fee"));
                studentfee = Integer.parseInt((String)map.get("unvisit_fee"));
                coursefee = studentfee;
                courseId = (String) map.get("course_id");
                gradeId = (String)map.get("grade_id");
                courseName =  (String)map.get("course_name") ;
                courseNumber = 1;

                keshi.setText(courseNumber+"");
                courseMoney.setText(coursefee+"元/小时");
                courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
                courseExplain.setText((String)map.get("course_remark"));
                zongfee.setText(coursefee*courseNumber+"");
                break;
            case R.id.teacher_two_ll:
                showBuyPop();
                buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
//                Map<String, Object> map = new HashMap<String, Object>();
                map = courseDatas.get(1);
                teacherfee = Integer.parseInt((String)map.get("visit_fee"));
                studentfee = Integer.parseInt((String)map.get("unvisit_fee"));
                coursefee = studentfee;
                courseId = (String) map.get("course_id");
                gradeId = (String)map.get("grade_id");
                courseName =  (String)map.get("course_name") ;
                courseNumber = 1;

                keshi.setText(courseNumber+"");
                courseMoney.setText(coursefee+"元/小时");
                courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
                courseExplain.setText((String)map.get("course_remark"));
                zongfee.setText(coursefee*courseNumber+"");
                break;
            case R.id.teacher_three_ll:
                showBuyPop();
                buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
//                Map<String, Object> map = new HashMap<String, Object>();
                map = courseDatas.get(2);
                teacherfee = Integer.parseInt((String)map.get("visit_fee"));
                studentfee = Integer.parseInt((String)map.get("unvisit_fee"));
                coursefee = studentfee;
                courseId = (String) map.get("course_id");
                gradeId = (String)map.get("grade_id");
                courseName =  (String)map.get("course_name") ;
                courseNumber = 1;

                keshi.setText(courseNumber+"");
                courseMoney.setText(coursefee+"元/小时");
                courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
                courseExplain.setText((String)map.get("course_remark"));
                zongfee.setText(coursefee*courseNumber+"");
                break;
            case R.id.teacher_student_number:
                Toast.makeText(this, "这是老师正在上课的学生个数！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.teacher_order_number:
                Toast.makeText(this, "这个老师有这么多订单！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.teacher_complete_number:
                Toast.makeText(this, "这是老师订单的成交率！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.teacher_example_more:
                startActivity(ExampleActivity_.intent(NewTeacherPersonActivity.this).extra("teacherId",teacherId).get());
//                Toast.makeText(this, "暂无", Toast.LENGTH_SHORT).show();
                break;
            case R.id.teacher_zone_image1:
                    Intent intent21 = new Intent(NewTeacherPersonActivity.this, PictureZoo.class);
                    intent21.putExtra("hide",zoneUrl1);
                    startActivity(intent21);
                break;
            case R.id.teacher_zone_image2:
                Intent intent22 = new Intent(NewTeacherPersonActivity.this, PictureZoo.class);
                intent22.putExtra("hide",zoneUrl2);
                startActivity(intent22);
                break;
            case R.id.teacher_zone_image3:
                Intent intent23 = new Intent(NewTeacherPersonActivity.this, PictureZoo.class);
                intent23.putExtra("hide",zoneUrl3);
                startActivity(intent23);
                break;
            case R.id.teacher_head_image:
                if (userHeadUrl != null) {
                    Intent intent2 = new Intent(NewTeacherPersonActivity.this, PictureZoo.class);
                    intent2.putExtra("hide",userHeadUrl);
                    startActivity(intent2);
                }

                break;
            case R.id.gerxxxuexiquana:
                //跳转成交率
//                Intent intent1 = new Intent(UserxinxiActivty.this, Closing.class);
//                intent1.putExtra("Requir_id",Requir_id+"");
//                startActivity(intent1);
                break;
            case R.id.teacher_jubao:
                startActivity(JubaoActivity_.intent(this).extra("teacher_id",teacherId).get());
                break;
            case R.id.gerxxxuexiquan:
                //跳转成交率
//                Intent intent = new Intent(UserxinxiActivty.this, Closing.class);
//                intent.putExtra("Requir_id",Requir_id+"");
//                startActivity(intent);
                break;
            case R.id.teacher_star_image:
                //评价
                Intent pj = new Intent(NewTeacherPersonActivity.this, Teacher_evaluate.class);
                pj.putExtra("teacher_id", Requir_id + "");
                startActivity(pj);
                break;
            case R.id.teacher_star_ll:
                //评价
                Intent pj1 = new Intent(NewTeacherPersonActivity.this, Teacher_evaluate.class);
                pj1.putExtra("teacher_id", Requir_id + "");
                startActivity(pj1);
                break;
            case R.id.bohao:
                //拨号
                Log.e("aa", "拨号成功");
                Intent inte = new Intent(Intent.ACTION_DIAL);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.setData(Uri.parse("tel:" + mobile));
                startActivity(inte);
                break;
            case R.id.imageButton2:
                //聊天
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(JGApplication.CONV_TITLE, nickname);
                intent.putExtra(JGApplication.TARGET_ID, mobile);
                intent.putExtra(JGApplication.TARGET_APP_KEY, mMyInfo.getAppKey());
                startActivity(intent);
                break;
            case R.id.imageButton:
                ContactManager.sendInvitationRequest(username, null, "老师加个好友吧", new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
                            UserEntry userEntry = UserEntry.getUser(mMyInfo.getUserName(), mMyInfo.getAppKey());
                            FriendRecommendEntry entry = FriendRecommendEntry.getEntry(userEntry,
                                    username, mMyInfo.getAppKey());
                            if (null == entry) {
                                entry = new FriendRecommendEntry(username, "", "", mMyInfo.getAppKey(),
                                        "", "", "老师加个好友吧", FriendInvitation.INVITING.getValue(), userEntry, 100);
                            } else {
                                entry.state = FriendInvitation.INVITING.getValue();
                                entry.reason =  "老师加个好友吧";
                            }
                            entry.save();
                            ToastUtil.shortToast(NewTeacherPersonActivity.this, "申请成功");
                            finish();
                        } else if (responseCode == 871317) {
                            ToastUtil.shortToast(NewTeacherPersonActivity.this, "不能添加自己为好友");
                        } else {
                            ToastUtil.shortToast(NewTeacherPersonActivity.this, "申请失败");
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void successTuijian(List<Map<String, Object>> maps) {

    }

    @Override
    public void failTuijian(String msg) {
        if (msg.equals("ok")){
            Toast.makeText(NewTeacherPersonActivity.this,"购买课程成功",Toast.LENGTH_SHORT).show();
            new Getdata().sendMessage("有人购买了你的课程哦,快去看看吧",mobile);
            Intent intent=new Intent(NewTeacherPersonActivity.this,MyOrderActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
