package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.util.PathUtil;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Teacher_Modle.Teacher;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_honor)
public class HonorActivity extends MyBaseActivity implements View.OnClickListener, Requirdetailed, Student_init {

    @ViewById(R.id.honor_back)
    RelativeLayout backRl;
    @ViewById(R.id.honor_add1)
    ImageView addImage1;
    @ViewById(R.id.honor_add2)
    ImageView addImage2;
    @ViewById(R.id.honor_delete1)
    ImageView delImage1;
    @ViewById(R.id.honor_delete2)
    ImageView delImage2;
    @ViewById(R.id.honor_image1)
    ImageView honorImage1;
    @ViewById(R.id.honor_image2)
    ImageView honorImage2;
    @ViewById(R.id.honor_save)
    TextView saveTv;


    protected File cameraFile;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    private static final int REQUESTCODE_CUTTING = 4;
    private android.app.AlertDialog mPickDialog;
    private int flag = 1;
    private Teacher teacher;
    private String other_1;
    private String other_2;


    @Override
    public void before() {
        teacher = new Teacher();
    }

    @Override
    public void initData() {
        teacher.Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),
                Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void initView() {
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();
        backRl.setOnClickListener(this);
        addImage1.setOnClickListener(this);
        addImage2.setOnClickListener(this);
        delImage1.setOnClickListener(this);
        delImage2.setOnClickListener(this);
        honorImage1.setOnClickListener(this);
        honorImage2.setOnClickListener(this);
        saveTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.honor_save:
                if (TextUtils.isEmpty(other_1)&&TextUtils.isEmpty(other_2)){
                    Toast.makeText(this, "请上传荣誉证书", Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }
                break;
            case R.id.honor_back:
                finish();
                break;
            case R.id.honor_add1:
                flag = 1;
                mPickDialog.show();
                break;
            case R.id.honor_add2:
                flag = 2;
                mPickDialog.show();
                break;
            case R.id.honor_delete1:
                new AlertDialog.Builder(HonorActivity.this).setTitle("学了么提示!").setMessage("确认删除证书吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacher.Teacher_update3(Integer.parseInt(User_id.getUid()),"");
                                honorImage1.setImageResource(R.drawable.education_edit_bg);
                                addImage1.setVisibility(View.VISIBLE);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.honor_delete2:
                new AlertDialog.Builder(HonorActivity.this).setTitle("学了么提示!").setMessage("确认删除证书吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacher.Teacher_update4(Integer.parseInt(User_id.getUid()),"");
                                honorImage2.setImageResource(R.drawable.education_edit_bg);
                                addImage2.setVisibility(View.VISIBLE);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
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
            break;
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

    /**
     * capture new image
     */
    protected void selectPicFromCamera() {
        if (!EaseCommonUtils.isSdcardExist()) {
            Toast.makeText(this, com.hyphenate.easeui.R.string.sd_card_does_not_exist, Toast.LENGTH_SHORT).show();
            return;
        }

        cameraFile = new File(PathUtil.getInstance().getImagePath(), EMClient.getInstance().getCurrentUser()
                + System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
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
                    setuseryoux(cameraFile);
                }

            }else if (requestCode == REQUEST_CODE_LOCAL) { // send local image
                Uri selectedImage = data.getData();
                if (selectedImage != null) {
                    sendPicByUri(selectedImage);
                }

            }

        }
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
                Toast toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;
            }
            setuseryoux(new File(picturePath));
        } else {
            File file = new File(selectedImage.getPath());
            if (!file.exists()) {
                Toast toast = Toast.makeText(this, com.hyphenate.easeui.R.string.cant_find_pictures, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return;

            }
            setuseryoux(file);
        }

    }
    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        if (flag == 1){
            teacher.Teacher_update3(Integer.parseInt(User_id.getUid()), listmap.get(0).get("imageurl")+"");
            other_1 = listmap.get(0).get("imageurl")+"";
            Glide.with(getApplicationContext()).load(listmap.get(0).get("imageurl")+"").into(honorImage1);
            addImage1.setVisibility(View.GONE);
        }else {
            other_2 = listmap.get(0).get("imageurl")+"";
            teacher.Teacher_update4(Integer.parseInt(User_id.getUid()), listmap.get(0).get("imageurl")+"");
            Glide.with(getApplicationContext()).load(listmap.get(0).get("imageurl")).into(honorImage2);
            addImage2.setVisibility(View.GONE);
        }
        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
    }


    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("others_3")+"")){
            other_1 = map.get("others_3")+"";
            Glide.with(getApplicationContext()).load(map.get("others_3")+"").into(honorImage1);
            addImage1.setVisibility(View.GONE);
            if (map.get("is_passed").equals("1")){
                delImage1.setVisibility(View.GONE);
            }else {
                delImage1.setVisibility(View.VISIBLE);
            }
        }else {
            addImage1.setVisibility(View.VISIBLE);
            delImage1.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(map.get("others_4")+"")){
            other_2 = map.get("others_4")+"";
            Glide.with(getApplicationContext()).load(map.get("others_4")+"").into(honorImage2);
            addImage2.setVisibility(View.GONE);
            if (map.get("is_passed").equals("1")){
                delImage2.setVisibility(View.GONE);
            }else {
                delImage2.setVisibility(View.VISIBLE);
            }
        }else {
            addImage2.setVisibility(View.VISIBLE);
            delImage2.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }
}
