package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.ShareUtil;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.toos.Erweima;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_my_zxing)
public class MyZxingActivity extends MyBaseActivity implements Requirdetailed {

    @ViewById(R.id.back_rl)
    RelativeLayout backRl;
    @ViewById(R.id.my_share_tv)
    TextView shareTv;
    @ViewById(R.id.zxing_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.zxing_nickname_tv)
    TextView nicknameTv;
    @ViewById(R.id.zxing_gender_image)
    ImageView genderImage;
    @ViewById(R.id.zxing_code_tv)
    TextView codeTv;
    @ViewById(R.id.zxing_image)
    ImageView zxingImage;
    @ViewById(R.id.zxing_share_ll)
    LinearLayout shareLl;
    @ViewById(R.id.zxing_right_image)
    ImageView rightImage;




    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initView() {
        rightImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareTv.setVisibility(View.VISIBLE);
                rightImage.setVisibility(View.GONE);
            }
        });
        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //我的推广
                startActivity(MyExtentActivity_.intent(MyZxingActivity.this).get());
            }
        });
        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shareLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtil.getInstance().share(MyZxingActivity.this);
            }
        });

        zxingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res=getResources();
//                Bitmap bmp= BitmapFactory.decodeResource(res, R.drawable.ic_launcher);

                Bundle b = new Bundle();
                b.putParcelable("bitmap", bitmap);

                Intent intent9 = new Intent(MyZxingActivity.this, PictureZoo.class);
                intent9.putExtras(b);
                intent9.putExtra("hide","");
                startActivity(intent9);
            }
        });
    }

    @Override
    public void initData() {
        new User_Realization().User_Data(Integer.parseInt(User_id.getUid()),"","",this);
    }

    private Bitmap bitmap;
    @Override
    public void Updatecontent(Map<String, Object> map) {
        nicknameTv.setText(map.get("nickname")+"");
        if (map.get("gender").equals("1")||map.get("gender").equals("男")){
            genderImage.setImageResource(R.mipmap.teacher_male);
        }else {
            genderImage.setImageResource(R.mipmap.gender_female_icon);
        }
        headImage.setImageURI(Uri.parse(map.get("headimg")+""));

        if (map.get("mobile")!=null) {
            Erweima erweimac = new Erweima();
            bitmap = erweimac.generateBitmap("http://deguanjiaoyu.com/index.php?s=/Home/users/index/tel/"+ User_id.getUsername(), 440, 440);
//            http://deguanjiaoyu.com/index.php?s=/Home/users/index/tel/"+User_id.getUsername()
//            Bitmap bitmap = erweimac.generateBitmap("邀请码为:" + map.get("mobile"), 440, 440);
            zxingImage.setImageBitmap(bitmap);
            codeTv.setText((String) map.get("mobile"));
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
