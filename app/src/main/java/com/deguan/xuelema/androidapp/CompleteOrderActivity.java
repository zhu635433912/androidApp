package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.EaseCommonUtils;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.PathUtil;
import com.deguan.xuelema.androidapp.utils.PhotoBitmapUtils;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.getdata.Getdata;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_complete_order)
public class CompleteOrderActivity extends MyBaseActivity implements View.OnClickListener, Student_init, ChangeOrderView {

    @ViewById(R.id.complete_back)
    RelativeLayout backRelative;
    @ViewById(R.id.complete_desc_edit)
    EditText descEdit;
    @ViewById(R.id.complete_talk_edit)
    EditText talkEdit;
    @ViewById(R.id.complete_picture1)
    SimpleDraweeView image1;
    @ViewById(R.id.complete_picture2)
    SimpleDraweeView image2;
    @ViewById(R.id.complete_picture3)
    SimpleDraweeView image3;
    @ViewById(R.id.complete_picture4)
    SimpleDraweeView image4;
    @ViewById(R.id.complete_sure_btn)
    RelativeLayout sureBtn;
    private int orderId ;
    protected File cameraFile;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    private int flag = 1;

    private String imageurl1 = "";
    private String imageurl2 = "";
    private String imageurl3 = "";
    private String imageurl4 = "";
    private Order_init order_init;
    private String talk = "";
    private String desc = "";
    private android.app.AlertDialog mPickDialog;

    @Override
    public void before() {
        super.before();
        User_id.getInstance().addActivity(this);
        orderId = getIntent().getIntExtra("orderId",0);
        telphone = getIntent().getStringExtra("telPhone");
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        backRelative.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
    }

    @Override
    public void initView() {
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();
        //根据订单号用户id去后台获取订单详细信息
        order_init = new Order();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.complete_back:
                finish();
                break;
            case R.id.complete_picture1:
                mPickDialog.show();
                flag = 1;
                break;
            case R.id.complete_picture2:
                mPickDialog.show();
                flag = 2;
                break;
            case R.id.complete_picture3:
                mPickDialog.show();
                flag = 3;
                break;
            case R.id.complete_picture4:
                mPickDialog.show();
                flag = 4;
                break;
            case R.id.complete_sure_btn:
                if (TextUtils.isEmpty(descEdit.getText())){
                    Toast.makeText(this, "请填写授课内容", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(talkEdit.getText())){
                    Toast.makeText(this, "对学生说点什么吧", Toast.LENGTH_SHORT).show();
                }else {
                    talk = talkEdit.getText().toString();
                    desc = descEdit.getText().toString();
                    order_init.complete_order(orderId, desc, talk, imageurl1, imageurl2, imageurl3, imageurl4, this);
                }
                break;
            case R.id.picture_dialog_pick: {
                selectPicFromLocal();
                mPickDialog.dismiss();
            }
            break;
            case R.id.camera_dialog_pick: {
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    PermissionGen.with(this)
                            .addRequestCode(100)
                            .permissions(
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                                    android.Manifest.permission.READ_PHONE_STATE,
                                    android.Manifest.permission.CAMERA
                            )
                            .request();
//
                }else{
//  定位
                    selectPicFromCamera();
                }
                mPickDialog.dismiss();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        selectPicFromCamera();
    }

    private void setuseryoux(File file){
        new User_Realization().setuserbitmap(file,this,null);
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

    String mFilePath;
    /**
     * capture new image
     */
    public void selectPicFromCamera() {
        mFilePath = Environment.getExternalStorageDirectory().getPath();// 获取SD卡路径
        mFilePath = mFilePath + "/"+ User_id.getUid()
                + System.currentTimeMillis() + ".jpg";// 指定路径
        if (!EaseCommonUtils.isSdcardExist()) {
            return;
        }
        cameraFile = new File( mFilePath);
        startActivityForResult(
                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
                REQUEST_CODE_CAMERA);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) { // capture new image
                if (cameraFile != null && cameraFile.exists()) {
                    String filepath = PhotoBitmapUtils.amendRotatePhoto(cameraFile.getAbsolutePath(),this);
                    setuseryoux(new File(filepath));
//                    setuseryoux(cameraFile);
                }

            }else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    sendPicByUri(selectedImage);
                }
            }

        }
    }


    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        //更新图片
        Map<String,Object> map=listmap.get(0);
        if (flag == 1) {
            imageurl1 = map.get("imageurl")+"";
            image1.setImageURI(Uri.parse(imageurl1));
//            Glide.with(getApplicationContext()).load(imageurl1).into(image1);
        }else if (flag == 2){
            imageurl2 = map.get("imageurl")+"";
            image2.setImageURI(Uri.parse(imageurl2));
//            Glide.with(getApplicationContext()).load(imageurl2).into(image2);
        }else if (flag == 3){
            imageurl3 = map.get("imageurl")+"";
            image3.setImageURI(Uri.parse(imageurl3));
//            Glide.with(getApplicationContext()).load(imageurl3).into(image3);
        }else if (flag == 4){
            imageurl4 = map.get("imageurl")+"";
            image4.setImageURI(Uri.parse(imageurl4));
//            Glide.with(getApplicationContext()).load(imageurl4).into(image4);
        }

        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
    }
    private String telphone;

    @Override
    public void successOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        new Getdata().sendMessage(User_id.getNickName()+"有给你的建议哦，快去看看吧",telphone);
        EventBus.getDefault().post(1,"changeStatus");
        Intent intent= new Intent(CompleteOrderActivity.this,MyOrderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * send image
     *
     * @param selectedImage
     */
    protected void sendPicByUri(Uri selectedImage) {
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            cursor = null;

            if (picturePath == null || picturePath.equals("null")) {
                return;
            }
            String filepath = PhotoBitmapUtils.amendRotatePhoto(picturePath,this);
            setuseryoux(new File(filepath));
//            setuseryoux(new File(picturePath));
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                return;

            }
            String filepath = PhotoBitmapUtils.amendRotatePhoto(selectedImage.getPath(),this);
            setuseryoux(new File(filepath));
//            setuseryoux(file);
        }

    }

}
