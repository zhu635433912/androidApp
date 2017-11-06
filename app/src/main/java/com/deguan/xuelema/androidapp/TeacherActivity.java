package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.EaseCommonUtils;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.PathUtil;
import com.deguan.xuelema.androidapp.utils.PhotoBitmapUtils;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Adapter.KechengAdapter;
import modle.Increase_course.Increase_course;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_teacher)
public class TeacherActivity extends MyBaseActivity implements View.OnClickListener, Student_init, Requirdetailed {

    @ViewById(R.id.teacher_add1)
    ImageView imageAdd1;
    @ViewById(R.id.teacher_add2)
    ImageView imageAdd2;
    @ViewById(R.id.teacher_save)
    TextView saveTv;
    @ViewById(R.id.teacher_back)
    RelativeLayout backRl;

    @ViewById(R.id.teacher_del1)
    ImageView imageDel1;
    @ViewById(R.id.teacher_del2)
    ImageView imageDel2;
    @ViewById(R.id.teacher_pic1)
    SimpleDraweeView educationImage1;
    @ViewById(R.id.teacher_pic2)
    SimpleDraweeView educationImage2;


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
        super.before();
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
        imageAdd1.setOnClickListener(this);
        imageAdd2.setOnClickListener(this);
        backRl.setOnClickListener(this);
        imageDel1.setOnClickListener(this);
        imageDel2.setOnClickListener(this);
        saveTv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teacher_save:
                if (TextUtils.isEmpty(other_1)&&TextUtils.isEmpty(other_2)){
                    Toast.makeText(this, "请上传身份证", Toast.LENGTH_SHORT).show();
                }else {
                    finish();
                }
                break;
            case R.id.teacher_del1:
                new AlertDialog.Builder(TeacherActivity.this).setTitle("学了么提示!").setMessage("确认删除身份证吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacher.Teacher_update5(Integer.parseInt(User_id.getUid()),"");
                                educationImage1.setImageResource(R.mipmap.name_example1);
                                imageAdd1.setVisibility(View.VISIBLE);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.teacher_del2:
                new AlertDialog.Builder(TeacherActivity.this).setTitle("学了么提示!").setMessage("确认删除身份证吗")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                teacher.Teacher_update6(Integer.parseInt(User_id.getUid()),"");
                                educationImage2.setImageResource(R.mipmap.name_example2);
                                imageAdd2.setVisibility(View.VISIBLE);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.teacher_back:
                finish();
                break;
            case R.id.teacher_add1:
                flag = 1;
                mPickDialog.show();
                break;
            case R.id.teacher_add2:
                flag = 2;
                mPickDialog.show();
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
        if (flag == 1){
            teacher.Teacher_update5(Integer.parseInt(User_id.getUid()), listmap.get(0).get("imageurl")+"");
            other_1 = listmap.get(0).get("imageurl")+"";
//            Glide.with(getApplicationContext()).load(listmap.get(0).get("imageurl")+"").
//                    into(educationImage1);
            educationImage1.setImageURI(Uri.parse(listmap.get(0).get("imageurl")+""));
            imageAdd1.setVisibility(View.GONE);
        }else {
            other_2 = listmap.get(0).get("imageurl")+"";
            teacher.Teacher_update6(Integer.parseInt(User_id.getUid()), listmap.get(0).get("imageurl")+"");
//            Glide.with(getApplicationContext()).load(listmap.get(0).get
//                    ("imageurl")).into(educationImage2);
            educationImage2.setImageURI(Uri.parse(listmap.get(0).get("imageurl")+""));
            imageAdd2.setVisibility(View.GONE);
        }
        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
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

    @Override
    public void Updatecontent(Map<String, Object> map) {

        if (!TextUtils.isEmpty(map.get("others_5")+"")){
            other_1 = map.get("others_5")+"";
//            Glide.with(getApplicationContext()).load(map.get("others_5")+"").
//                    into(educationImage1);
            educationImage1.setImageURI(Uri.parse(map.get("others_5")+""));
            imageAdd1.setVisibility(View.GONE);
            if (map.get("is_passed").equals("1")){
                imageDel1.setVisibility(View.GONE);
            }else {
                imageDel1.setVisibility(View.VISIBLE);
            }
        }else {
            imageAdd1.setVisibility(View.VISIBLE);
            imageDel1.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(map.get("others_6")+"")){
            other_2 = map.get("others_6")+"";
            educationImage2.setImageURI(Uri.parse(map.get("others_6")+""));
//            Glide.with(getApplicationContext()).load(map.get("others_6")+"").into(educationImage2);
            imageAdd2.setVisibility(View.GONE);
            if (map.get("is_passed").equals("1")){
                imageDel2.setVisibility(View.GONE);
            }else {
                imageDel2.setVisibility(View.VISIBLE);
            }
        }else {
            imageAdd2.setVisibility(View.VISIBLE);
            imageDel2.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

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
