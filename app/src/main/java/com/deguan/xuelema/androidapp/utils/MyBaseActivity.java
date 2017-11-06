package com.deguan.xuelema.androidapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.NewMainActivity;
import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.NewTeacherPersonActivity_;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.entities.AddFriendEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.BaseActivity;
import jiguang.chat.activity.MainActivity;
import jiguang.chat.database.FriendEntry;
import jiguang.chat.database.FriendRecommendEntry;
import jiguang.chat.entity.Event;
import jiguang.chat.entity.EventType;
import jiguang.chat.utils.DialogCreator;
import jiguang.chat.utils.FileHelper;
import jiguang.chat.utils.SharePreferenceManager;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import view.login.ViewActivity.LoginAcitivity;

@EActivity(R.layout.activity_base)
public class MyBaseActivity extends AutoLayoutActivity {

    TextView baseTv;
    private PopupWindow messageWindow,buycoursePopwindow;
    private TextView titleTv;
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
    private String order_rank;
    private String teach_count;
    private String grade_name;
    private String signature;
    private String course_name;

    @AfterInject
    public void before(){

        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
    }
    @AfterViews
    public final void init(){
        initView();
        initData();
        initListener();
    }

//{"teacher_id":"72",
// "distance":1345656,
// "fee":"0",
// "user_id":"72",
// "resume":"学习成绩优异,yyjc"
// ,"user_headimg":"http:\/\/deguan.tpddns.cn:88\/Uploads\/AppImg\/2017-09-19\/59c0bb674d178.png"
// ,"order_rank":5,
// "teach_count":"51",
// "content":"22",
// "grade_name":"小学 二年级",
// "nickname ":" 大头",
// "signature":"学海无涯苦作舟",
// "course_name":"语文",
// "id":"191"}

//    @Subscriber(tag = "MyReceiver")
//    public void getMessage(String msg){
//        try {
//            JSONObject object = new JSONObject(msg);
//            user_id = object.getString("teacher_id");
//            user_headimg = object.getString("user_headimg");
//            id = object.getString("id");
//            content = object.getString("content");
//            teacherName = object.getString("nickname");
//            distance = object.getString("distance");
//            fee = object.getString("fee");
//            resume = object.getString("resume");
//            order_rank = object.getInt("order_rank")+"";
//            teach_count = object.getString("teach_count");
//            grade_name = object.getString("grade_name");
//            signature = object.getString("signature");
//            course_name = object.getString("course_name");
//             /* @setIcon 设置对话框图标
//         * @setTitle 设置对话框标题
//         * @setMessage 设置对话框消息提示
//         * setXXX方法返回Dialog对象，因此可以链式设置属性
//         */
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        showmyDialog();
//        buycoursePopwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
//        nameTv.setText(teacherName);
//        signTv.setText(signature);
//        resumeTv.setText(resume);
//        numberTv.setText(teach_count);
//        distanceTv.setText(distance);
////        scoreTv.setText(score);
//        headImage.setImageURI(Uri.parse(user_headimg));
////        starImage
//    }

    @Override
    public void onBackPressed() {
        finish();
    }

//    private void showmyDialog() {
//
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.student_buy_pop, null);
////        View backView = view.findViewById(R.id.message_back);
//        nameTv = (TextView) view.findViewById(R.id.buy_nickname_tv);
//        signTv = (TextView) view.findViewById(R.id.buy_sign_tv);
//        resumeTv = (TextView) view.findViewById(R.id.buy_resume_tv);
//        numberTv = (TextView) view.findViewById(R.id.buy_number_tv);
//        distanceTv = (TextView) view.findViewById(R.id.buy_distance_tv);
//        scoreTv = (TextView) view.findViewById(R.id.buy_score_tv);
//        nextTv = (TextView) view.findViewById(R.id.buy_next_tv);
//        buyTv = (TextView) view.findViewById(R.id.buy_buy_tv);
//        headImage = (SimpleDraweeView) view.findViewById(R.id.buy_head_image);
//        starImage = (ImageView) view.findViewById(R.id.buy_star_image);
//        buycoursePopwindow = new PopupWindow(view);
//        buycoursePopwindow.setFocusable(false);
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        int width = wm.getDefaultDisplay().getWidth();
//        buycoursePopwindow.setWidth(width / 10 * 8);
//        buycoursePopwindow.setHeight(height / 5 * 3 );
//        buycoursePopwindow.setBackgroundDrawable(new BitmapDrawable());
//        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
//        buycoursePopwindow.setOutsideTouchable(false);
//        buycoursePopwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(MyBaseActivity.this, 1f);
//            }
//        });
////        backView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                messageWindow.dismiss();
////            }
////        });
//
//        nextTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                buycoursePopwindow.dismiss();
//            }
//        });
//
//        buyTv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(NewTeacherPersonActivity_.intent(MyBaseActivity.this).
//                        extra("myid","0").extra("content", content)
//                        .extra("head_image",user_headimg)
//                        .extra("user_id",user_id).get());
//            }
//        });
//        new AlertDialog.Builder(this).setTitle("学了吗提示!").setMessage("有老师接取了你的需求，是否去购买课程？")
//                .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
////                        Intent intent = new Intent(MyBaseActivity.this,UserxinxiActivty.class);
////                        intent.putExtra("content", content);
////                        intent.putExtra("myid", id);
////                        intent.putExtra("user_id", user_id);
////                        intent.putExtra("head_image", user_headimg);
////                        startActivity(intent);
//                    }
//                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
////                    new Getdata().sendMessage("学生");
//            }
//        }).show();
//
//    }

//    @Subscriber(tag = "message")
//    public void getNotice(String msg){
//
//        showMessagePop();
//        messageWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
//        titleTv.setText(msg);
//        contentTv.setText(msg);
//    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        //注册sdk的event用于接收各种event事件
        JMessageClient.registerEventReceiver(this);
        baseTv = (TextView) findViewById(R.id.base_tv);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDensity = dm.density;
        mDensityDpi = dm.densityDpi;
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        mRatio = Math.min((float) mWidth / 720, (float) mHeight / 1280);
        mAvatarSize = (int) (50 * mDensity);
//        showMessagePop();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(messageWindow!=null&&messageWindow.isShowing()){
            return false;
        }
        return super.dispatchTouchEvent(event);
    }
//    private void showMessagePop() {
//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = layoutInflater.inflate(R.layout.message_pop, null);
//        View backView = view.findViewById(R.id.message_back);
//        titleTv = (TextView) view.findViewById(R.id.message_title_tv);
//        contentTv = (TextView) view.findViewById(R.id.message_content_tv);
//        messageWindow = new PopupWindow(view);
//        messageWindow.setFocusable(false);
//        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//        int height = wm.getDefaultDisplay().getHeight();
//        int width = wm.getDefaultDisplay().getWidth();
//        messageWindow.setWidth(width / 10 * 8);
//        messageWindow.setHeight(height / 3 );
//        messageWindow.setBackgroundDrawable(new BitmapDrawable());
//        backgroundAlpha(this, 0.4f);//0.0-1.0  ;
//        messageWindow.setOutsideTouchable(false);
//        messageWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//            @Override
//            public void onDismiss() {
//                backgroundAlpha(MyBaseActivity.this, 1f);
//            }
//        });
//        backView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                messageWindow.dismiss();
//            }
//        });
//    }
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

    public void initListener() {

    }

    public void initData() {

    }

    public void initView() {

    }
    protected int mWidth;
    protected int mHeight;
    protected float mDensity;
    protected int mDensityDpi;
    protected int mAvatarSize;
    protected float mRatio;
    private Dialog dialog;


    //接收到好友事件
    public void onEvent(ContactNotifyEvent event) {
        Log.d("aa",event.getType()+"------------------>>>");
        if (event.getType() == ContactNotifyEvent.Type.invite_received) {
            final String reason = event.getReason();
            final String username = event.getFromUsername();
            final String appKey = event.getfromUserAppKey();
            AddFriendEntity entity = new AddFriendEntity();
            entity.setReason(event.getReason());
            entity.setAppKey(event.getfromUserAppKey());
            entity.setUsername(event.getFromUsername());
            User_id.getInstance().addFriend(entity);
//            EventBus.getDefault().post(entity, "friend");
        }
    }
    public void onEventMainThread(LoginStateChangeEvent event) {
        final LoginStateChangeEvent.Reason reason = event.getReason();
        UserInfo myInfo = event.getMyInfo();
        if (myInfo != null) {
            String path;
            File avatar = myInfo.getAvatarFile();
            if (avatar != null && avatar.exists()) {
                path = avatar.getAbsolutePath();
            } else {
                path = FileHelper.getUserAvatarPath(myInfo.getUserName());
            }
            SharePreferenceManager.setCachedUsername(myInfo.getUserName());
            SharePreferenceManager.setCachedAvatarPath(path);
            JMessageClient.logout();
        }
        switch (reason) {
            case user_logout:
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.jmui_cancel_btn:
                                Intent intent = new Intent(MyBaseActivity.this, LoginAcitivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            case R.id.jmui_commit_btn:
                                Intent intent1 = new Intent(MyBaseActivity.this, LoginAcitivity.class);
                                startActivity(intent1);
                                finish();
//                                JMessageClient.login(SharePreferenceManager.getCachedUsername(), SharePreferenceManager.getCachedPsw(), new BasicCallback() {
//                                    @Override
//                                    public void gotResult(int responseCode, String responseMessage) {
//                                        if (responseCode == 0) {
//                                            Intent intent = new Intent(MyBaseActivity.this, MainActivity.class);
//                                            startActivity(intent);
//                                        }
//                                    }
//                                });
                                break;
                        }
                    }
                };
                dialog = DialogCreator.createLogoutStatusDialog(MyBaseActivity.this, "您的账号在其他设备上登陆", listener);
                dialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        if (dialog != null) {
            dialog.dismiss();
        }
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
