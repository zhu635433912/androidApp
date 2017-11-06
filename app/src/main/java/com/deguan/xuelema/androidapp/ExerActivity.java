package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.EaseCommonUtils;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.PathUtil;
import com.deguan.xuelema.androidapp.utils.PhotoBitmapUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.File;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Teacher_Modle.Teacher;
import modle.user_Modle.User_Realization;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_exer)
public class ExerActivity extends MyBaseActivity implements Requirdetailed, View.OnClickListener, Student_init {

    @ViewById(R.id.exer_desc)
    EditText descEdit;
    @ViewById(R.id.exer_save)
    TextView saveTv;
    @ViewById(R.id.exer_back)
    RelativeLayout backRl;
    @ViewById(R.id.exer_image)
    SimpleDraweeView exerImage;
    @ViewById(R.id.exer_add)
    ImageView addImage;

    private String image_url = "";
    protected File cameraFile;
    protected static final int REQUEST_CODE_CAMERA = 2;
    protected static final int REQUEST_CODE_LOCAL = 3;
    private android.app.AlertDialog mPickDialog;

    @Override
    public void before() {
        super.before();
    }

    @Override
    public void initData() {
        new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),
                Integer.parseInt(User_id.getUid()),this,2,0);
    }

    @Override
    public void initView() {
        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();
        backRl.setOnClickListener(this);
        addImage.setOnClickListener(this);
        saveTv.setOnClickListener(this);


    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("exper")+"")){
            descEdit.setText(""+map.get("exper"));
        }
        if (!TextUtils.isEmpty(map.get("img1")+"")){
            image_url = map.get("img1")+"";
//            Glide.with(getApplicationContext()).load(image_url).into(exerImage);
            exerImage.setImageURI(Uri.parse(image_url));
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.exer_add:
                mPickDialog.show();
                break;
            case R.id.exer_back:
                finish();
                break;
            case R.id.exer_save:
                if (!TextUtils.isEmpty(descEdit.getText())) {
                    new Teacher().Teacher_exper(Integer.parseInt(User_id.getUid()), ""+descEdit.getText() + "",image_url);
                    finish();
                }else {
                    Toast.makeText(ExerActivity.this, "请说下你兴趣爱好吧", Toast.LENGTH_SHORT).show();
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
    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
            image_url = listmap.get(0).get("imageurl")+"";
//            Glide.with(getApplicationContext()).load(listmap.get(0).get("imageurl")+"").
//                    into(exerImage);
        exerImage.setImageURI(Uri.parse(listmap.get(0).get("imageurl")+""));
        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
    }



}
