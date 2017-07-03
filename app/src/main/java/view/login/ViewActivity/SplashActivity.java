package view.login.ViewActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;


import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.utils.PermissUtil;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import modle.Adapter.My_PagerAdapter;
import modle.user_ziliao.User_id;

/**
 * 首次进入引导页面
 * Created by Administrator on 2017/6/4.
 */

public class SplashActivity extends AutoLayoutActivity implements View.OnClickListener {
    private SharedPreferences sp1 = null;
    private SharedPreferences sp = null;
    private ViewPager viewpager;
    private View view1,view2,view3;
    private List<View> listview;
    private ImageButton jinru;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inde);
        PermissUtil.startPermiss(this);
        //判断用户是否第一次进入
        sp = getSharedPreferences("ydy", MODE_PRIVATE);
        //判断记录是第一次就是"t",不是就是"1"
        String myydy= sp.getString("booled", "t");
        if (myydy.equals("1")){
            getsj();
        }


        viewpager = (ViewPager) findViewById(R.id.viewpager);
        jinru = (ImageButton) findViewById(R.id.jinru);

        jinru.setVisibility(View.GONE);
        jinru.setOnClickListener(this);

        //加载引导页
        LayoutInflater layoutInflater = getLayoutInflater();
        view1 = layoutInflater.inflate(R.layout.index1, null);
        view2 = layoutInflater.inflate(R.layout.index2, null);
        view3 = layoutInflater.inflate(R.layout.index3, null);

        listview = new ArrayList<>();
        listview.add(view1);
        listview.add(view2);
        listview.add(view3);

        viewpager.setAdapter(new My_PagerAdapter(listview));

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position!=2){
                    jinru.setVisibility(View.GONE);
                }
                if (position == 2) {
                    jinru.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jinru:
                SharedPreferences sp = getSharedPreferences("ydy", Context.MODE_PRIVATE);
                SharedPreferences.Editor ddite = sp.edit();
                //第一次进入
                ddite.putString("booled", "1");
                ddite.commit();
                getsj();
                break;
        }
    }

    public void getsj(){
        sp1 = getSharedPreferences("userstate", MODE_PRIVATE);
        String mykey = sp1.getString("id", "t");
        if (!mykey.equals("t")) {
            String zt = sp1.getString("state", "d");
            if (zt.equals("1")) {
                String id = sp1.getString("id", "t");
                String role = sp1.getString("role", "t");
                String username = sp1.getString("username", "t");
                String password = sp1.getString("password", "t");
//                intent.putExtra("id", id);
//                intent.putExtra("role", role);
                Intent intent = NewMainActivity_.intent(this).extra("id",id).extra("role",role).get();
                User_id.setRole(role);
                User_id.setUid(id);
                User_id.setPassword(password);
                User_id.setUsername(username);
                startActivity(intent);
                this.finish();
            }
            //状态改变

        } else {

            //进入登录页面
            Intent intent = new Intent(SplashActivity.this, LoginAcitivity.class);
            startActivity(intent);
            this.finish();

        }

    }
}
