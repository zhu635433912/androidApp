package com.deguan.xuelema.androidapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.deguan.xuelema.androidapp.entities.DownloadEntity;
import com.deguan.xuelema.androidapp.fragment.BaseFragment_;
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

import modle.Huanxing.cache.UserCacheManager;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;
import view.index.Teacher_fragment;

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

    @Override
    public void before() {
        //获取用户是教师还是学生展示相应的参数
        ids=User_id.getUid();
        roles=User_id.getRole();
        EventBus.getDefault().register(this);
    }

    @Override
    public void initView() {
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


        new Getdata().getDownloadUrl(this);
        if (User_id.getRole().equals("1")){
            radioButton1.setText("老师");
            imageView.setImageResource(R.drawable.hly03);
        }else {
            radioButton1.setText("学生");
            imageView.setImageResource(R.drawable.logo);
        }
        fragments.add(new Teacher_fragment());
        fragments.add(BaseFragment_.builder().build());
//        fragments.add(new Teacher_fragment());
        if (User_id.getRole().equals("1")) {
            fragments.add(New_StudentFragment_.builder().build());
        }else {
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
//        if (requestCode == 100){
//            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
//        }
        Log.d("aa","------requestCode"+requestCode);
        EventBus.getDefault().post(requestCode,"requsetPermiss");
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        String imageUrl = (String)map.get("headimg");
        Log.e("aa","头像地址"+imageUrl);
        SharedPreferencesUtils.setParam(this, APPConfig.USER_HEAD_IMG,imageUrl);
        getUser_id().setImageUrl(imageUrl);
        // 登录成功，将用户的环信ID、昵称和头像缓存在本地
        UserCacheManager.save(User_id.getUsername(), User_id.getUsername(), imageUrl);
        EMClient.getInstance().login(User_id.getUsername(),User_id.getPassword(),new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.e("aa", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.e("aa", code+"登录聊天服务器失败！"+message);
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
        Log.d("123","versionName"+versionName+"versionname"+versionname);
        if (!versionname.equals(versionName)){
            UpdateChecker.checkForDialog(NewMainActivity.this, entity.getContent());
        }
    }

}
