package com.deguan.xuelema.androidapp;

import android.*;
import android.Manifest;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.hyphenate.util.Utils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;

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

import kr.co.namee.permissiongen.PermissionGen;
import modle.Gaode.Gaode_dinwei;
import modle.Huanxing.ui.UserProfileActivity;
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
public class Personal_Activty extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed {
    private RelativeLayout gerxxhuitui;
    private TextView userdizhi;
    private TextView userage;
    private TextView usershenr;
    private TextView ziyouzhiye;
    private EditText edit1;
    private TextView emage;
    private CircleImageView usertoux;
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
    private static final int REQUEST_IMAGE_GET = 5;
    private static final int REQUEST_IMAGE_CAPTURE = 6;
    private Map<String,Object> mapa;
    private File image;
    private RelativeLayout xueliRl;
    private TextView xueliTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_data);
        EventBus.getDefault().register(this);
        User_id.getInstance().addActivity(this);

        xueliRl = (RelativeLayout) findViewById(R.id.xuelie_rl);
        xueliTv = (TextView) findViewById(R.id.xueli_text);
        gerxxhuitui= (RelativeLayout) findViewById(R.id.gerxxhuitui);
        usertoux= (CircleImageView) findViewById(R.id.usertoux);
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
            fuwuleia.setVisibility(View.GONE);
            jiaolinlayout.setVisibility(View.GONE);
            xueliRl.setVisibility(View.GONE);

        }else {
            teacher=new Teacher();
            teacher.Get_Teacher_detailed(561,uid,this,1);
        }
        //获取用户资料
        user_init=new User_Realization();
        updateuserdata();


        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();


    }
    private int education_id = 0;
    @Override
    public void onClick(View v) {
        final EditText edit = new EditText(this);
        switch (v.getId()) {
            case R.id.xueli_text:
                AlertDialog.Builder xueleTypeDialog = new AlertDialog.Builder(Personal_Activty.this);
                xueleTypeDialog.setIcon(R.drawable.add04);
                xueleTypeDialog.setTitle("请选择学历");
                //    指定下拉列表的显示数据
                final String[] xueliType = {"无","大专", "本科", "硕士" ,"博士"};
                //    设置一个下拉的列表选择项
                xueleTypeDialog.setItems(xueliType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        xueliTv.setText(xueliType[which]);
//                        int years=Integer.parseInt(edit.getText().toString());
//                        teacher.Teacher_years(uid,which+1);
                        education_id = which;
                        user_init.UpdateEducation(uid,which);
                    }
                });
                xueleTypeDialog.show();


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
                rolage.setSingleChoiceItems(sex, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      z=which+1;
                    }
                });
                rolage.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (z!=0||z==1) {
                            user.Updategender(uid, z + "");
                            userage.setText(sex[z - 1]);
                        }else {
                            user.Updategender(uid, 2 + "");
                            userage.setText(sex[1]);
                        }
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
                new  AlertDialog.Builder(this).setTitle("请输入!").setIcon(android.R.drawable.btn_star).setView(edit)
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
                new  AlertDialog.Builder(this).setTitle("请输入!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (!edit.getText().toString().equals("")&&edit.getText().toString().length()<4) {
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
                new  AlertDialog.Builder(this).setTitle("请输入!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&&edit.getText().length()<10) {
                                    user.Updatenickname(uid,edit.getText().toString());
                                    emage.setText(edit.getText().toString());
                                }else {
                                    Toast.makeText(Personal_Activty.this,"昵称不能太短也不能太长哦!!",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
                break;
            case R.id.usertoux:
                mPickDialog.show();
                break;
            //dialog
            case R.id.picture_dialog_pick: {
                selectImage();
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
                    dispatchTakePictureIntent();
                }

                mPickDialog.dismiss();
            }
            break;

            case R.id.baocun:
                if (image != null) {
                    EventBus.getDefault().post(image, "headUrl");
                }
                    EventBus.getDefault().post("update","update");

                finish();
                Toast.makeText(this,"更新资料成功！",Toast.LENGTH_SHORT).show();

                break;
            case R.id.biyexuexiao:
                //个人签名
                new  AlertDialog.Builder(this).setTitle("请输入!").setIcon(android.R.drawable.btn_star).setView(edit)
                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!edit.getText().toString().equals("")&&edit.getText().length()<=20) {
                                    String signature = edit.getText().toString();
                                    teacher.Teacher_signature(uid,signature);
                                    biyexuexiao.setText(signature);
                                }else {
                                    Toast.makeText(Personal_Activty.this, "签名不能太长也不能没有啊", Toast.LENGTH_SHORT).show();
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
                //教龄
//                new  AlertDialog.Builder(this).setTitle("请输入!").setIcon(android.R.drawable.btn_star).setView(edit)
//                        .setPositiveButton("确认",new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                if (!edit.getText().toString().equals("")) {
//                                    int years=Integer.parseInt(edit.getText().toString());
//                                    teacher.Teacher_years(uid,years);
//                                    shurujiaol.setText(years+"年");
//                                }else {
//                                }
//                            }
//                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                }).show();




                break;


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
//        if (requestCode == 100){
//            Gaode_dinwei gaode_dinwei=new Gaode_dinwei(this,getActivity());
//        }
        Log.d("aa","------requestCode"+requestCode);
        dispatchTakePictureIntent();

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

            address = (String) map.get("address");
            gender = (String) map.get("gender");
            age = (String) map.get("age");
            userdizhi.setText((String) map.get("address"));
            if (map.get("gender").equals("1")) {
                userage.setText("男");
            } else {
                userage.setText("女");
            }

            userdizhi.setText((String) map.get("address"));
            emage.setText((String) map.get("nickname"));
            usershenr.setText((String) map.get("age"));
            ziyouzhiye.setText((String) map.get("name"));
            xueliTv.setText((String)map.get("education_name"));
        setbitmap(map.get("headimg").toString());
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESTCODE_PICK:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case REQUESTCODE_CUTTING:
                if (data != null) {
                    setPicToView(data);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
        // 回调成功
        if (resultCode == RESULT_OK) {
            String filePath = null;
            //判断是哪一个的回调
            if (requestCode == REQUEST_IMAGE_GET) {
                //返回的是content://的样式
                filePath = getFilePathFromContentUri(data.getData(), this);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    filePath = mCurrentPhotoPath;
                }
            }
            if (!TextUtils.isEmpty(filePath)) {

                // 自定义大小，防止OOM
                Bitmap bitmap = getSmallBitmap(filePath, 400, 400);
                //获取图片
                usertoux.setImageBitmap(bitmap);
                Log.e("aa","路劲为"+filePath);

                //去服务器更新图片
                if (image!=null) {
                    setuseryoux(image);
                }else {
                    image=new File(filePath);
                    setuseryoux(image);
                }

            }
        }
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
            File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
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
    private static final int REQUESTCODE_PICK = 1;
    private static final int REQUESTCODE_CUTTING = 2;
    /**
     * 从相册中获取
     */
    public void selectImage() {
        Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        //判断系统中是否有处理该Intent的Activity
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_GET);
//        } else {
//            showToast("未找到图片查看器");
//        }

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

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            // 创建文件来保存拍的照片
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // 异常处理
            }
            if (photoFile != null) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, photoFile.getAbsolutePath());
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            showToast("无法启动相机");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param uri     content:// 样式
     * @param context
     * @return real file path
     */
    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
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
        user.setuserbitmap(file,null);
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

    /**
     * 裁剪图片方法实现
     *
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", true);
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 340);
        intent.putExtra("outputY", 340);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent,REQUESTCODE_CUTTING);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
