package com.deguan.xuelema.androidapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.PhotoBitmapUtils;
import com.deguan.xuelema.androidapp.utils.PhotoUtils1;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.PersonalActivity;
import jiguang.chat.utils.ThreadUtil;
import jiguang.chat.utils.ToastUtil;
import jiguang.chat.utils.photochoose.ChoosePhoto;
import jiguang.chat.utils.photochoose.PhotoUtils;
import kr.co.namee.permissiongen.PermissionGen;
import modle.Gaode.Gaode_dinwei;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.toos.CircleImageView;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;
import okhttp3.Request;
import retrofit2.Call;

/**
 * 个人信息
 */
public class Personal_Activty extends MyBaseActivity implements View.OnClickListener,Requirdetailed,ChangeOrderView {
    private RelativeLayout gerxxhuitui;
    private TextView userdizhi;
    private TextView userage;
    private TextView usershenr;
    private TextView ziyouzhiye;
    private EditText edit1;
    private TextView emage;
    private SimpleDraweeView usertoux;
    private String address;//用户地区
    private String gender;//用户年龄
    private String age;//用户性别
    private String nickname;//用户昵称
    private Map<String,Object> map;
    private TextView biyexuexiao;
    private TextView shurujiaol;
    private User_init user;
    private   User_init user_init;
    private RelativeLayout fuwuleia;
    private RelativeLayout jiaolinlayout;
    private int z=0;
    private int a=0;
    private int uid;
    private List<Map<String,Object>> listmap;
    private android.app.AlertDialog mPickDialog;
    private Button baocun;
    private Teacher_init teacher;
    private String mCurrentPhotoPath;
    private ChoosePhoto mChoosePhoto;


    private static final int REQUEST_IMAGE_GET = 5;
    private static final int REQUEST_IMAGE_CAPTURE = 6;
    private Map<String,Object> mapa;
    private File image;
    private RelativeLayout xueliRl;
    private TextView xueliTv;
    private String idCard;

    protected File cameraFile;
    private static final int REQUESTCODE_PICK = 1;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    private static final int REQUESTCODE_CUTTING = 4;
    private UserInfo mMyInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data);
        EventBus.getDefault().register(this);
        User_id.getInstance().addActivity(this);

        xueliRl = (RelativeLayout) findViewById(R.id.xuelie_rl);
        xueliTv = (TextView) findViewById(R.id.xueli_text);
        gerxxhuitui= (RelativeLayout) findViewById(R.id.gerxxhuitui);
        usertoux= (SimpleDraweeView) findViewById(R.id.usertoux);
        userdizhi= (TextView) findViewById(R.id.userdizhi);
        userage= (TextView) findViewById(R.id.userage);
        usershenr= (TextView) findViewById(R.id.usershenr);
        ziyouzhiye= (TextView) findViewById(R.id.ziyouzhiye);
        emage= (TextView) findViewById(R.id.emage);
        fuwuleia= (RelativeLayout) findViewById(R.id.fuwuleia);
        jiaolinlayout= (RelativeLayout) findViewById(R.id.jiaolinlayout);
        biyexuexiao= (TextView) findViewById(R.id.biyexuexiao);
        shurujiaol= (TextView) findViewById(R.id.shurujiaol);

        xueliTv.setOnClickListener(this);
        biyexuexiao.setOnClickListener(this);
        shurujiaol.setOnClickListener(this);
        userage.setOnClickListener(this);
        usershenr.setOnClickListener(this);
        ziyouzhiye.setOnClickListener(this);
        emage.setOnClickListener(this);
        baocun= (Button) findViewById(R.id.baocun);
        baocun.setOnClickListener(this);

        map=new HashMap<String,Object>();
        listmap=new ArrayList<Map<String,Object>>();

        user=new User_Realization();
        emage= (TextView) findViewById(R.id.emage);
        gerxxhuitui.bringToFront();

        uid=Integer.parseInt(User_id.getUid());
        int role=Integer.parseInt(User_id.getRole());
        if (role==1){
            jiaolinlayout.setVisibility(View.GONE);
        }else {
            teacher=new Teacher();
            teacher.Get_Teacher_detailed(561,uid,this,1,0);
        }
        //获取用户资料
        user_init=new User_Realization();
        updateuserdata();


        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();
        mMyInfo = JMessageClient.getMyInfo();
        mChoosePhoto = new ChoosePhoto();
        mChoosePhoto.setPortraitChangeListener(this, usertoux, 1);

    }
    private int education_id = 0;
    @Override
    public void onClick(View v) {
        final EditText edit = new EditText(this);
        switch (v.getId()) {
            case R.id.xueli_text:

                //所在地
                new  AlertDialog.Builder(this).setTitle("身份证号码(无法修改,将作为找回支付密码的依据)").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&&edit.getText().length()==18) {
                                    user.UpdateIdcard(uid, edit.getText().toString());
                                    xueliTv.setText(edit.getText().toString().substring(0,4)+"********");
                                }else {
                                    Toast.makeText(Personal_Activty.this,"请输入正确的身份证号码",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.gerxxhuitui:
                finish();
                break;
            case R.id.userdizhi:
                //所在地
                new  AlertDialog.Builder(this).setTitle("请输入").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")) {
                                    user.Updateaddress(uid, edit.getText().toString());
                                    userdizhi.setText(edit.getText().toString());
                                    ThreadUtil.runInThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMyInfo.setAddress( edit.getText().toString());
                                            JMessageClient.updateMyInfo(UserInfo.Field.address, mMyInfo, new BasicCallback() {
                                                @Override
                                                public void gotResult(int responseCode, String responseMessage) {
                                                    if (responseCode == 0) {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                                    } else {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }else {
                                    Toast.makeText(Personal_Activty.this,"所在地不能为空",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.userage:
                //性别
                final String[] sex={"男","女"};
                AlertDialog.Builder rolage=new AlertDialog.Builder(Personal_Activty.this);
                rolage.setIcon(android.R.drawable.btn_star);
                rolage.setTitle("选择您的性别!");
                rolage.setSingleChoiceItems(sex, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      z=which+1;
                    }
                });
                rolage.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (z==0||z==1) {
                            user.Updategender(uid, "1");
                            userage.setText("男");
                            mMyInfo.setGender(UserInfo.Gender.male);
                        }else {
                            user.Updategender(uid, "2");
                            userage.setText("女");
                            mMyInfo.setGender(UserInfo.Gender.female);
                        }
                        ThreadUtil.runInThread(new Runnable() {
                            @Override
                            public void run() {

                                JMessageClient.updateMyInfo(UserInfo.Field.gender, mMyInfo, new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {
                                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                        } else {
                                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                                        }
                                    }
                                });
                            }
                        });
                        Toast.makeText(Personal_Activty.this,"修改性别成功",Toast.LENGTH_LONG).show();
                    }
                });
                rolage.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                rolage.show();

                break;
            case R.id.usershenr:
                //年龄
                new  AlertDialog.Builder(this).setTitle("请输入年龄!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&edit.getText().length()<4) {
                                    user.Updateage(uid,edit.getText().toString());
                                    usershenr.setText(edit.getText().toString());
                                }else {
                                    Toast.makeText(Personal_Activty.this,"年龄输入有误!!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.ziyouzhiye:
                //姓名
                new  AlertDialog.Builder(this).setTitle("请输入姓名!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!edit.getText().toString().equals("")&&edit.getText().toString().length()<=4) {
                                    user.Updatename(uid,edit.getText().toString());
                                    ziyouzhiye.setText(edit.getText().toString());

                                }else {
                                    Toast.makeText(Personal_Activty.this,"姓名输入有误!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.emage:
                //昵称
                new  AlertDialog.Builder(this).setTitle("请输入昵称!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&&edit.getText().length()<10) {
                                    user.Updatenickname(uid,edit.getText().toString());
                                    emage.setText(edit.getText().toString());
                                    ThreadUtil.runInThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMyInfo.setNickname(edit.getText().toString());
                                            JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                                                @Override
                                                public void gotResult(int responseCode, String responseMessage) {
                                                    if (responseCode == 0) {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                                    } else {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新失败,请正确输入");
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }else {
                                    Toast.makeText(Personal_Activty.this,"昵称不能超过10个字!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.usertoux:
                mChoosePhoto.setInfo(this, true);
                mChoosePhoto.showPhotoDialog(this);
//                mPickDialog.show();
                break;
            //dialog
            case R.id.picture_dialog_pick: {
                selectPicFromLocal();
//                selectImage();
                mPickDialog.dismiss();
            }
            break;
            case R.id.camera_dialog_pick: {
                //动态申请权限

                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    PermissionGen.with(this)
                            .addRequestCode(200)
                            .permissions(
                                    Manifest.permission.CAMERA
                            )
                            .request();
//
                }else{
//                    selectPicFromCamera();
//                    dispatchTakePictureIntent();
                }
                mPickDialog.dismiss();
            }
            break;

            case R.id.baocun:
                if (image != null) {
                    EventBus.getDefault().post(image, "headUrl");
                }
                    EventBus.getDefault().post("update","update");
                if (TextUtils.isEmpty(ziyouzhiye.getText())) {
                    Toast.makeText(this, "请完善姓名", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(emage.getText())){
                    Toast.makeText(this, "请填写昵称", Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }
                if (User_id.getRole().equals("1")) {
                    Toast.makeText(this, "更新资料成功,快去发布需求找老师吧", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "更新资料成功", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.biyexuexiao:
                //个人签名
                new  AlertDialog.Builder(this).setTitle("请输入个人签名!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&&edit.getText().length()<=20) {
                                    String signature = edit.getText().toString();
                                    user_init.Upsignature(uid,signature);
                                    biyexuexiao.setText(signature);
                                    ThreadUtil.runInThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            mMyInfo.setNickname(edit.getText().toString());
                                            JMessageClient.updateMyInfo(UserInfo.Field.signature, mMyInfo, new BasicCallback() {
                                                @Override
                                                public void gotResult(int responseCode, String responseMessage) {
                                                    if (responseCode == 0) {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                                                    } else {
                                                        ToastUtil.shortToast(Personal_Activty.this, "更新失败,请正确输入");
                                                    }
                                                }
                                            });
                                        }
                                    });
                                }else {
                                    Toast.makeText(Personal_Activty.this, "签名不能超过20个字哦", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();


                break;
            case R.id.shurujiaol:

                AlertDialog.Builder serviceTypeDialog = new AlertDialog.Builder(Personal_Activty.this);
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
                        shurujiaol.setText(fuwuType[which]);
//                        int years=Integer.parseInt(edit.getText().toString());
                        teacher.Teacher_years(uid,which+1);
                    }
                });
                serviceTypeDialog.show();

                break;


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
//        selectPicFromCamera();
    }
    public void updateuserdata(){
        //获取用户昵称与资料
        user_init.User_Data(uid,User_id.getLat()+"",User_id.getLng()+"",this);
        usertoux.setOnClickListener(this);
        userdizhi.setOnClickListener(this);
        gerxxhuitui.setOnClickListener(this);
    }

    public void refresh() {
        onCreate(null);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

//        UserCacheManager.save(User_id.getUsername(), map.get("nickname")+"", map.get("headimg")+"");
//        UserCacheManager.updateMyNick( map.get("nickname")+"");
//        UserCacheManager.updateMyAvatar(map.get("headimg")+"");
            address = (String) map.get("address");
            gender = (String) map.get("gender");
            age = (String) map.get("age");
            userdizhi.setText((String) map.get("address"));

        if (map.get("gender") != null) {
            if (map.get("gender").equals("1")||map.get("gender").equals("男")) {
                userage.setText("男");
                mMyInfo.setGender(UserInfo.Gender.male);
            } else {
                userage.setText("女");
                mMyInfo.setGender(UserInfo.Gender.female);
            }
        }

        ThreadUtil.runInThread(new Runnable() {
            @Override
            public void run() {
                JMessageClient.updateMyInfo(UserInfo.Field.gender, mMyInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                        } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                        }
                    }
                });
            }
        });
        if (map.get("signature") != null) {
            biyexuexiao.setText(map.get("signature") +"");
            mMyInfo.setSignature(map.get("signature")+"");
            ThreadUtil.runInThread(new Runnable() {
                @Override
                public void run() {
                    JMessageClient.updateMyInfo(UserInfo.Field.signature, mMyInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                            } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                            }
                        }
                    });
                }
            });
        }
            userdizhi.setText((String) map.get("address"));
        if (map.get("mobile").equals(map.get("nickname"))){
            emage.setText("");
        }else {
            emage.setText((String) map.get("nickname"));
            mMyInfo.setNickname(map.get("nickname")+"");
            ThreadUtil.runInThread(new Runnable() {
                @Override
                public void run() {
                    JMessageClient.updateMyInfo(UserInfo.Field.nickname, mMyInfo, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新成功");
                            } else {
//                            ToastUtil.shortToast(Personal_Activty.this, "更新失败" + responseMessage);
                            }
                        }
                    });
                }
            });

        }
            usershenr.setText((String) map.get("age"));
            ziyouzhiye.setText((String) map.get("name"));
        if (!TextUtils.isEmpty(map.get("idcard")+"")) {
            xueliTv.setText(((String) map.get("idcard")).substring(0, 4) + "********");
        }
        if (!TextUtils.isEmpty(map.get("idcard")+"")){
            idCard = map.get("idcard")+"";
            xueliRl.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(idCard)){
            xueliRl.setVisibility(View.VISIBLE);
        }
        usertoux.setImageURI(Uri.parse(map.get("headimg")+""));
//            Glide.with(getApplicationContext()).load(map.get("headimg")+"").transform(new GlideCircleTransform(this)).into(usertoux);
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        mapa=listmap.get(0);

        if (mapa.get("years").toString().equals("0.0")) {

        }else if (mapa.get("signature") != null){
            biyexuexiao.setText(mapa.get("signature").toString());
        }
//        else {
//            biyexuexiao.setText(mapa.get("signature").toString());
//        }
        shurujiaol.setText(mapa.get("years").toString()+"年");

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PhotoUtils.INTENT_CROP:
            case PhotoUtils.INTENT_TAKE:
            case PhotoUtils.INTENT_SELECT:
                mChoosePhoto.photoUtils.onActivityResult(this, requestCode, resultCode, data);
                break;
        }
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        super.onActivityResult(requestCode, resultCode, data);
//        // 回调成功
//        if (resultCode == Activity.RESULT_OK) {
//            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
//                if (cameraFile != null && cameraFile.exists()) {
//                    String filepath = PhotoUtils1.amendRotatePhoto(cameraFile.getAbsolutePath(),this);
//                    setuseryoux(new File(filepath));
////                    setuseryoux(cameraFile);
//                    Bitmap bitmap
////                            = setImage(data.getData()) ;
//                            = getSmallBitmap(cameraFile.getAbsolutePath(), 600, 600);
//
//                    usertoux.setImageBitmap(bitmap);
//
////                    sendImageMessage(cameraFile.getAbsolutePath());
//                }
//
//            }else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
//                if (data != null) {
//                    sendPicByUri(data.getData());
////     startPhotoZoom(data.getData());
//                }
//            }else if (requestCode == REQUESTCODE_CUTTING){
//                if (data != null) {
//                    setPicToView(data);
////                    sendPicByUri(data.getData());
//                }
//            }
//
//        }
//
//    }

    private Bitmap setImage(Uri mImageCaptureUri) {

        // 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
        // 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看

        ContentResolver cr = this.getContentResolver();
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
        if (cursor != null) {
            cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
            String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路
            String orientation = cursor.getString(cursor
                    .getColumnIndex("orientation"));// 获取旋转的角度
            cursor.close();
            if (filePath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);//根据Path读取资源图片
                int angle = 0;
                if (orientation != null && !"".equals(orientation)) {
                    angle = Integer.parseInt(orientation);
                }
                if (angle != 0) {
                    // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
                    Matrix m = new Matrix();
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    m.setRotate(angle); // 旋转angle度
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                            m, true);// 从新生成图片

                }
//                photo.setImageBitmap(bitmap);
                return  bitmap;
            }
        }
        return null;
    }
    @Subscriber(tag = "headUrl")
    public void updateHeadImage(String msg){
        Log.d("aa","headUrl---------->"+msg);
        setuseryoux(new File(msg));
    }
    /**
     * send image
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
//            String orientation = cursor.getString(cursor
//                    .getColumnIndex("orientation"));// 获取旋转的角度
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                return;
            }
            String filepath = PhotoUtils1.amendRotatePhoto(picturePath,this);
            setuseryoux(new File(filepath));
//            setuseryoux(new File(picturePath));
            Bitmap bitmap = getSmallBitmap(picturePath, 600, 600);
            usertoux.setImageBitmap(bitmap);
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                return;

            }
            String filepath = PhotoUtils1.amendRotatePhoto(selectedImage.getPath(),this);
            setuseryoux(new File(filepath));
//            setuseryoux(file);
            Bitmap bitmap = getSmallBitmap(file.getAbsolutePath(), 600, 600);
            usertoux.setImageBitmap(bitmap);
        }
    }
    /**
     * select local image
     */
    protected void selectPicFromLocal() {
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_LOCAL);
    }

    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(getResources(), photo);
            usertoux.setImageDrawable(drawable);
//            if(!TextUtils.isEmpty(user.getAvatar())){
//                Glide.with(UserProfileActivity.this).load(user.getAvatar()).placeholder(R.drawable.em_default_avatar).into(headAvatar);
//            }else{
//                Glide.with(UserProfileActivity.this).load(R.drawable.em_default_avatar).into(headAvatar);
//            }

            String timeStamp = new SimpleDateFormat("yyyy").format(new Date());
            String imageFileName = "" + timeStamp + "_";
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            setuseryoux(byte2File(Bitmap2Bytes(photo),storageDir.getAbsolutePath(),imageFileName));
        }

    }
    public static File byte2File(byte[] buf, String filePath, String fileName)
    {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try
        {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory())
            {
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(buf);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (bos != null)
            {
                try
                {
                    bos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (fos != null)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
    //bitmap转字节流
    public byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    /**
     * 创建新文件
     *
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy").format(new Date());
        String imageFileName = "" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(imageFileName,  /* 文件名 */
                ".jpg",         /* 后缀 */
                storageDir      /* 路径 */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }
    /**
     * capture new image
     */
//    protected void selectPicFromCamera() {
//        if (!EaseCommonUtils.isSdcardExist()) {
//            Toast.makeText(this, com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
//                + System.currentTimeMillis() + ".jpg");
//        //noinspection ResultOfMethodCallIgnored
//        cameraFile.getParentFile().mkdirs();
//        startActivityForResult(
//                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
//                REQUEST_CODE_CAMERA);
//    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 获取小图片，防止OOM
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private void setuseryoux(File file){
        user.setuserbitmap(file,null,this);
    }

    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                usertoux.post(new Runnable(){
                    @Override
                    public void run() {
                        usertoux.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                    }

                });

            }
        }).start();//开启线程
    }
    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param path
     * @return Bitmap
     * */
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }
    private File fileCut;//裁切后图片
    /**
     * 裁剪图片方法实现
     *
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
        // 设置裁剪
//        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);//设置为不返回数据，true返回bitMap
//        intent.putExtra("noFaceDetection", true);
//        startActivityForResult(intent,REQUESTCODE_CUTTING);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void successOrder(String msg) {
//        Glide.with(getApplicationContext()).load(msg).transform(new GlideCircleTransform(this)).into(usertoux);
    }

    @Override
    public void failOrder(String msg) {

    }
}
