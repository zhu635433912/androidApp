package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.deguan.xuelema.androidapp.entities.CourseEntity;
import com.deguan.xuelema.androidapp.entities.DownloadEntity;
import com.deguan.xuelema.androidapp.fragment.CourseFragment;
import com.deguan.xuelema.androidapp.fragment.CourseFragment_;
import com.deguan.xuelema.androidapp.fragment.FabuFragment_;
import com.deguan.xuelema.androidapp.fragment.MyStudentFragment_;
import com.deguan.xuelema.androidapp.fragment.MyTeacherFragment;
import com.deguan.xuelema.androidapp.fragment.MyTeacherFragment_;
import com.deguan.xuelema.androidapp.fragment.NewStudentFragment2_;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment2_;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment_;
import com.deguan.xuelema.androidapp.fragment.StudentFabuFragment;
import com.deguan.xuelema.androidapp.fragment.StudentFabuFragment_;
import com.deguan.xuelema.androidapp.fragment.StudentFragment_;
import com.deguan.xuelema.androidapp.fragment.Teacher_infofragment;
import com.deguan.xuelema.androidapp.fragment.Teacher_infofragment_;
import com.deguan.xuelema.androidapp.fragment.TestFragment;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.APPConfig;
import com.deguan.xuelema.androidapp.utils.FragmentTabUtils;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.SharedPreferencesUtils;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.deguan.xuelema.androidapp.viewimpl.DownloadView;
import com.deguan.xuelema.androidapp.viewimpl.MyPublishView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loveplusplus.update.UpdateChecker;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jiguang.api.JCoreInterface;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import jiguang.chat.activity.fragment.ContactsFragment;
import modle.Order_Modle.Order;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import view.login.ViewActivity.LoginAcitivity;
//import view.index.Teacher_fragment;

@EActivity(R.layout.activity_new_main)
public class NewMainActivity extends MyBaseActivity implements Requirdetailed ,DownloadView, MyPublishView, ChangeOrderView {
    @ViewById(R.id.main_bottom_radiogp)
    RadioGroup radioGroup;
    private ArrayList<Fragment> fragments = new ArrayList<>();
//    @ViewById(R.id.new_main_image)
//    ImageView imageView;
    @ViewById(R.id.main_bottom_name)
    RadioButton radioButton1;
    @ViewById(R.id.main_bottom_mine)
    RadioButton radioButton2;
    @ViewById(R.id.main_bottom_fabu)
    RadioButton radioButton3;
    private String ids;
    private String roles;
    @ViewById(R.id.guide1)
    ImageView guideImage1;
    @ViewById(R.id.guide2)
    ImageView guideImage2;
    @ViewById(R.id.guide3)
    ImageView guideImage3;
    private String myydy;


    TextView baseTv;
    private PopupWindow messageWindow,buycoursePopwindow,buyPopwindow;
    private TextView titleTv;
    private TextView contentTv;
    private String user_id;
    private String user_headimg;
    private String id;
    private String courseId,gradeId;
    private String content;
    private String teacherName;
    private TextView nameTv,signTv,resumeTv,numberTv,distanceTv,scoreTv,nextTv,buyTv;
    private SimpleDraweeView headImage;
    private ImageView starImage;
    private String distance;
    private String fee;
    private String visit_fee;
    private String unvisit_fee;
    private String resume;
    private double order_rank;
    private String teach_count;
    private String grade_name;
    private String signature;
    private String course_remark;
    private String course_name;
    private List<Map<String,Object>> datas = new ArrayList<>();


    @Subscriber(tag = "MyReceiver")
    public void getMessage(String msg){
        try {
            JSONObject object = new JSONObject(msg);
            user_id = object.getString("teacher_id");
            user_headimg = object.getString("user_headimg");
            id = object.getString("id");
            content = object.getString("content");
            courseId = object.getString("course_id");
//            courseId = "208";
            teacherName = object.getString("nickname");
            distance = object.getString("distance");
            gradeId = object.getString("grade_id");
            fee = object.getString("fee");
            resume = object.getString("resume");
            course_remark = object.getString("course_remark");
            order_rank = object.getDouble("order_rank");
            teach_count = object.getString("teach_count");
            grade_name = object.getString("grade_name");
            signature = object.getString("signature");
            course_name = object.getString("course_name");
            visit_fee = object.getString("visit_fee");
            unvisit_fee = object.getString("unvisit_fee");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showmyDialog();
        buycoursePopwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
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
//        starImage
    }
    private void showmyDialog() {

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
        buycoursePopwindow = new PopupWindow(view);
        buycoursePopwindow.setFocusable(false);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        buycoursePopwindow.setWidth(width / 10 * 8);
        buycoursePopwindow.setHeight(height / 5 * 3 );
        buycoursePopwindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
        buycoursePopwindow.setOutsideTouchable(false);
        buycoursePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(NewMainActivity.this, 1f);
            }
        });
        nextTv.setText("暂时不要");
        nextTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                buycoursePopwindow.dismiss();
            }
        });
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buycoursePopwindow.dismiss();
            }
        });
        buyTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post();
                CourseEntity entity = new CourseEntity();
                entity.setContent(content);
                entity.setCourse_id(courseId);
                entity.setCourse_name(course_name);
                entity.setCourse_remark(course_remark);
                entity.setGrade_id(gradeId);
                entity.setUnvisit_fee(unvisit_fee);
                entity.setVisit_fee(visit_fee);

                buycoursePopwindow.dismiss();
                EventBus.getDefault().post(entity,"buycourse");
                startActivity(NewTeacherPersonActivity_.intent(NewMainActivity.this)
                        .extra("courseId",courseId)
                        .extra("course_name",course_name)
                        .extra("course_remark",course_remark)
                        .extra("gradeId",gradeId)
                        .extra("unvisit_fee",unvisit_fee)
                        .extra("visit_fee",visit_fee)

                        .extra("myid","0")
                        .extra("content", content)
                        .extra("head_image",user_headimg)
                        .extra("user_id",user_id)
                        .get());
            }
        });
    }

    @Subscriber(tag = "fabusuccess")
    public void successFabu(String msg){
        getSupportFragmentManager().beginTransaction().show(fragments.get(0));
    }
    @Subscriber(tag = "message")
    public void getNotice(String msg){

        showMessagePop();
        messageWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
        titleTv.setText(msg);
        contentTv.setText(msg);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(messageWindow!=null&&messageWindow.isShowing()){
            return false;
        }
        return super.dispatchTouchEvent(event);
    }
    private void showMessagePop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.message_pop, null);
        View backView = view.findViewById(R.id.message_back);
        titleTv = (TextView) view.findViewById(R.id.message_title_tv);
        contentTv = (TextView) view.findViewById(R.id.message_content_tv);
        messageWindow = new PopupWindow(view);
        messageWindow.setFocusable(false);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        messageWindow.setWidth(width / 10 * 8);
        messageWindow.setHeight(height / 3 );
        messageWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
        messageWindow.setOutsideTouchable(false);
        messageWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(NewMainActivity.this, 1f);
            }
        });
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageWindow.dismiss();
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
    public void before() {
        super.before();
        //获取用户是教师还是学生展示相应的参数
        ids=User_id.getUid();
        roles=User_id.getRole();
        EventBus.getDefault().register(this);
        SharedPreferences sp = getSharedPreferences("ydy", MODE_PRIVATE);
        //判断记录是第一次就是"t",不是就是"1"
        myydy = sp.getString("booled", "t");
    }
    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
    }


    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        super.onResume();
    }
    @Override
    public void initView() {
        SDKInitializer.initialize(getApplicationContext());
        User_id.getInstance().addActivity(this);
         User_id.getInstance().setActivity(this);
        //定义底部标签图片大小

//jpush设置id
        setAlias("hly_"+ids);
        new Getdata().getDownloadUrl(this);
        if (User_id.getRole().equals("1")){
            radioButton1.setText("找老师");
//            imageView.setImageResource(R.drawable.hly03);
        }else {
            radioButton1.setText("找学生");
//            imageView.setImageResource(R.drawable.logo);
        }
        fragments.clear();
        if (User_id.getRole().equals("1")) {

//            User_id.setFragment( new ContactsFragment());
            fragments.add(NewStudentFragment2_.builder().build());
//            fragments.add(User_id.getFragment());
            fragments.add(StudentFabuFragment_.builder().build());
            fragments.add(MyStudentFragment_.builder().build());
//            fragments.add(New_StudentFragment_.builder().build());

        }else {
//            User_id.setFragment( new ContactsFragment());
            fragments.add(NewTeacherFragment2_.builder().build());
//            fragments.add(User_id.getFragment());
            fragments.add(CourseFragment_.builder().build());
            fragments.add(MyTeacherFragment_.builder().build());
//            fragments.add(Teacher_infofragment_.builder().build());
        }

        //底部按钮切换fragment的工具类
        new FragmentTabUtils(this,getSupportFragmentManager(),radioGroup,fragments, R.id.main_contaner);

        new Getdata().getmobieke(User_id.getUsername(),this);

    }

    @Subscriber(tag = "updateAddress")
    public void getAddress(String msg){
        new Order().getReceptOrder(Integer.parseInt(User_id.getUid()),User_id.getLat()+"",User_id.getLng()+"",this);
    }

    @Subscriber(tag = "fabu")
    public void setFragment(String msg){
        radioGroup.check(R.id.main_bottom_name);
    }
    private void getsj() {
        if (roles.equals("1")){
            guideImage1.setVisibility(View.VISIBLE);
            guideImage1.setImageResource(R.drawable.student_guide1);
            guideImage2.setImageResource(R.drawable.student_guide2);
            guideImage3.setImageResource(R.drawable.student_guide3);
        }else {
            guideImage1.setVisibility(View.VISIBLE);
            guideImage1.setImageResource(R.drawable.teacher_guide1);
            guideImage2.setImageResource(R.drawable.teacher_guide2);
            guideImage3.setImageResource(R.drawable.teacher_guide3);
        }
        guideImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage1.setVisibility(View.GONE);
                guideImage2.setVisibility(View.VISIBLE);
            }
        });
        guideImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage2.setVisibility(View.GONE);
                guideImage3.setVisibility(View.VISIBLE);
            }
        });
        guideImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideImage3.setVisibility(View.GONE);

            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
//        if (requestCode == 100){
//            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
//        }
//        Log.d("aa","------requestCode"+requestCode);
        EventBus.getDefault().post(requestCode,"requsetPermiss");
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        String imageUrl = (String)map.get("headimg");
//        Log.e("aa","头像地址"+imageUrl);
        SharedPreferencesUtils.setParam(this, APPConfig.USER_HEAD_IMG,imageUrl);
        getUser_id().setImageUrl(imageUrl);
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    private PackageManager manager;

    private PackageInfo info = null;
    @Override
    public void successDownload(DownloadEntity entity) {
//        if (entity.getError())
        manager = this.getPackageManager();

        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int  versionCode = info.versionCode;
        String versionname = entity.getVersion();
        String versionName = info.versionName;
//        Log.d("123","versionName"+versionName+"versionname"+versionname);
        if (!versionname.equals(versionName)){
            UpdateChecker.checkForDialog(NewMainActivity.this, entity.getContent());
        }
    }

    private void setAlias(String bieming) {
        String alias =bieming;
        if (TextUtils.isEmpty(alias)) {
//            Toast.makeText(NewMainActivity.this,"111", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (!ExampleUtil.isValidTagAndAlias(alias)) {
//            Toast.makeText(PushSetActivity.this,R.string.error_tag_gs_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
//                    Log.i("aa", logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
//                    Log.i("aa", logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
//                    Log.e("aa", logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };
    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
//                    Log.d("aa", "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
//                    Log.i("aa", "Unhandled msg - " + msg.what);
            }
        }
    };


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
                backgroundAlpha(NewMainActivity.this, 1f);
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
                new Order().cancel_order(Integer.parseInt(User_id.getUid()),Integer.parseInt(id),NewMainActivity.this);
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
                startActivity(NewTeacherPersonActivity_.intent(NewMainActivity.this)
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
//                EventBus.getDefault().post(entity,"buycourse");
            }
        });
    }
    private int flag = 0;
    @Override
    public void successMyPublish(List<Map<String, Object>> maps) {
        if (maps != null) {
            datas = maps;
            if (datas.size()>= 1){
                user_id = ""+datas.get(0).get("teacher_id");
                user_headimg = ""+datas.get(0).get("teacher_headimg");
                id = ""+datas.get(0).get("id");
                content = ""+datas.get(0).get("requirement_content");
                teacherName = ""+datas.get(0).get("teacher_name");
                distance = ""+datas.get(0).get("distance");
                fee = ""+datas.get(0).get("visit_fee");
                courseId = ""+datas.get(0).get("course_id");
                resume = ""+datas.get(0).get("teacher_resume");
                order_rank = Double.parseDouble(datas.get(0).get("order_rank")+"");
                teach_count = ""+datas.get(0).get("teach_count");
                grade_name = ""+datas.get(0).get("grade_name");
                signature = ""+datas.get(0).get("teacher_signature");
                course_name = ""+datas.get(0).get("course_name");
                course_remark = ""+datas.get(0).get("course_remark");
                gradeId  = "" + datas.get(0).get("grade_id");
                visit_fee = "" + datas.get(0).get("visit_fee");
                unvisit_fee = "" + datas.get(0).get("unvisit_fee");

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

    @Override
    public void successOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failOrder(String msg) {

    }
}
