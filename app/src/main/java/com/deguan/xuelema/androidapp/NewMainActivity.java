package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.DownloadEntity;
import com.deguan.xuelema.androidapp.fragment.BaseFragment_;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment;
import com.deguan.xuelema.androidapp.fragment.NewTeacherFragment_;
import com.deguan.xuelema.androidapp.fragment.StudentFragment_;
import com.deguan.xuelema.androidapp.fragment.Teacher_infofragment;
import com.deguan.xuelema.androidapp.fragment.Teacher_infofragment_;
import com.deguan.xuelema.androidapp.fragment.TestFragment;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.APPConfig;
import com.deguan.xuelema.androidapp.utils.FragmentTabUtils;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.SharedPreferencesUtils;
import com.deguan.xuelema.androidapp.viewimpl.DownloadView;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.loveplusplus.update.UpdateChecker;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import modle.Huanxing.cache.UserCacheManager;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import view.login.ViewActivity.LoginAcitivity;
//import view.index.Teacher_fragment;

@EActivity(R.layout.activity_new_main)
public class NewMainActivity extends MyBaseActivity implements Requirdetailed ,DownloadView{
    @ViewById(R.id.main_bottom_radiogp)
    RadioGroup radioGroup;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    @ViewById(R.id.new_main_image)
    ImageView imageView;
    @ViewById(R.id.main_bottom_name)
    RadioButton radioButton1;
    @ViewById(R.id.main_bottom_mine)
    RadioButton radioButton2;
    private String ids;
    private String roles;
    @ViewById(R.id.guide1)
    ImageView guideImage1;
    @ViewById(R.id.guide2)
    ImageView guideImage2;
    @ViewById(R.id.guide3)
    ImageView guideImage3;

    @Override
    public void before() {
        //获取用户是教师还是学生展示相应的参数
        ids=User_id.getUid();
        roles=User_id.getRole();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
        User_id.getInstance().addActivity(this);
        //定义底部标签图片大小
//        Drawable drawableFirst = getResources().getDrawable(R.drawable.bottom_home_icon);
//        drawableFirst.setBounds(0, 0, 60, 60);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        radioButton1.setCompoundDrawables(null, drawableFirst, null, null);//只放上面
//        radioButton1.setPadding(0,0,0,0);
//
//        Drawable drawableSearch = getResources().getDrawable(R.drawable.bottom_follow_icon);
//        drawableSearch.setBounds(0, 0, 60, 60);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
//        radioButton2.setCompoundDrawables(null, drawableSearch, null, null);//只放上面
//        radioButton2.setPadding(0,0,0,0);

//jpush设置id
        setAlias("hly_"+ids);
        new Getdata().getDownloadUrl(this);
        if (User_id.getRole().equals("1")){
            radioButton1.setText("找老师");
            imageView.setImageResource(R.drawable.hly03);
        }else {
            radioButton1.setText("找学生");
            imageView.setImageResource(R.drawable.logo);
        }
        fragments.clear();
        if (User_id.getRole().equals("1")) {
            fragments.add(StudentFragment_.builder().build());
            fragments.add(BaseFragment_.builder().build());
            fragments.add(New_StudentFragment_.builder().build());
        }else {
            fragments.add(NewTeacherFragment_.builder().build());
            fragments.add(BaseFragment_.builder().build());
            fragments.add(Teacher_infofragment_.builder().build());
        }
        //底部按钮切换fragment的工具类
        new FragmentTabUtils(this,getSupportFragmentManager(),radioGroup,fragments, R.id.main_contaner);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //需求发布
                if (User_id.getRole().equals("1")){
                    Intent intent = new Intent(NewMainActivity.this, Xuqiufabu.class);
//                    startActivity(intent,ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                    startActivity(intent);
                }else {
                    Intent intentTeacher = new Intent(NewMainActivity.this,Teacher_management.class);
                    startActivity(intentTeacher);
                }
            }
        });

        new Getdata().getmobieke(User_id.getUsername(),this);
        SharedPreferences sp = getSharedPreferences("ydy", MODE_PRIVATE);
        //判断记录是第一次就是"t",不是就是"1"
        String myydy= sp.getString("booled", "t");
        if (myydy.equals("1")){
            getsj();
        }
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
                SharedPreferences sp = getSharedPreferences("ydy", Context.MODE_PRIVATE);
                SharedPreferences.Editor ddite = sp.edit();
                //第一次进入
                ddite.putString("booled", "2");
                ddite.commit();
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
        // 登录成功，将用户的环信ID、昵称和头像缓存在本地
        UserCacheManager.save(User_id.getUsername(), map.get("nickname")+"", imageUrl);
        EMClient.getInstance().login(User_id.getUsername(),
//                User_id.getPassword()
                "123456"

                ,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//                Log.e("aa", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
//                Log.e("aa", code+"登录聊天服务器失败！"+message);
            }
        });
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }

    @Override
    protected void onDestroy() {
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
}
