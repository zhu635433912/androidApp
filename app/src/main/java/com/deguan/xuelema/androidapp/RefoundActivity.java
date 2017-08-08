package com.deguan.xuelema.androidapp;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.fragment.StudentFragment;
import com.deguan.xuelema.androidapp.init.Ordercontent_init;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Huanxing.ui.BaseActivity;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.getdata.Getdata;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

/**
 * 退款页面
 */
@EActivity(R.layout.activity_refound)
public class RefoundActivity extends MyBaseActivity implements View.OnClickListener, Ordercontent_init, Student_init, ChangeOrderView {
    @ViewById(R.id.refund_back)
    RelativeLayout backRelative;
    @ViewById(R.id.refund_nianji)
    TextView gradeTv;
    @ViewById(R.id.refund_course)
    TextView courseTv;
    @ViewById(R.id.refund_stats)
    TextView statsTv;
    @ViewById(R.id.refund_number)
    TextView numberTv;
    @ViewById(R.id.refund_fee)
    TextView feeTv;
    @ViewById(R.id.orderfee)
    TextView orderfeeTv;
    @ViewById(R.id.refund_orderfee)
    TextView refundOrderfeeTv;
    @ViewById(R.id.refund_choose_reason)
    TextView chooseReasonTv;
    @ViewById(R.id.refund_desc)
    TextView descTv;
    @ViewById(R.id.refund_reason_edit)
    EditText reasonEdit;
    @ViewById(R.id.refund_picture1)
    ImageView image1;
    @ViewById(R.id.refund_picture2)
    ImageView image2;
    @ViewById(R.id.refund_picture3)
    ImageView image3;
    @ViewById(R.id.refund_picture4)
    ImageView image4;
    @ViewById(R.id.refund_sure_btn)
    RelativeLayout sureBtn;
    private PopupWindow reasonPop;
    private int reasonFlag = 1;
    private int orderId ;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private int TAGE_ISRONT;
    private File image;
    private String imageurl1 = "";
    private String imageurl2 = "";
    private String imageurl3 = "";
    private String imageurl4 = "";
    private Order_init order_init;
    private String refund_fee,reason,desc;

    @Override
    public void before() {
        User_id.getInstance().addActivity(this);
        orderId = getIntent().getIntExtra("orderId",0);
        EventBus.getDefault().register(this);
    }

    @Override
    public void initData() {
        showChoosePop();
        chooseReasonTv.setOnClickListener(this);
        backRelative.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        sureBtn.setOnClickListener(this);
    }

    private void showChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.refund_reason_pop,null);
        LinearLayout firstReason = (LinearLayout) view.findViewById(R.id.reason_one);
        final LinearLayout secondReason = (LinearLayout) view.findViewById(R.id.reason_two);
        final LinearLayout thirdReason = (LinearLayout) view.findViewById(R.id.reason_three);
        final LinearLayout fourReason = (LinearLayout) view.findViewById(R.id.reason_four);
        final ImageView firsrImage = (ImageView) view.findViewById(R.id.choose_image1);
        final ImageView secondImage = (ImageView) view.findViewById(R.id.choose_image2);
        final ImageView thirdImage = (ImageView) view.findViewById(R.id.choose_image3);
        final ImageView fourImage = (ImageView) view.findViewById(R.id.choose_image4);
        reasonPop = new PopupWindow(view);
        reasonPop.setFocusable(true);
        reasonPop.setBackgroundDrawable(new BitmapDrawable());
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        reasonPop.setWidth(width);
        reasonPop.setHeight(height/5*2);
        reasonPop.setOutsideTouchable(true);
        firstReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firsrImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_choose));
                secondImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                thirdImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                fourImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                reasonFlag = 1;
                descTv.setText("退款说明:  教师未授课");
                reason = "退款说明:  教师未授课";
                reasonPop.dismiss();
            }
        });
        secondReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firsrImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                secondImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_choose));
                thirdImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                fourImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                reasonFlag = 2;
                reason = "退款说明:  教师态度极差,并有辱骂现象";
                descTv.setText("退款说明:  教师态度极差,并有辱骂现象");
                reasonPop.dismiss();
            }
        });
        thirdReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firsrImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                secondImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                thirdImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_choose));
                fourImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                reasonFlag = 3;
                reasonPop.dismiss();
                reason = "退款说明:  有事未去听课";
                descTv.setText("退款说明:  有事未去听课");
            }
        });
        fourReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firsrImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                secondImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                thirdImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_normal));
                fourImage.setImageDrawable(getResources().getDrawable(R.drawable.refund_choose));
                reasonFlag = 4;
                reasonPop.dismiss();
                reason = "退款说明:  其他";
                descTv.setText("退款说明:  其他");
            }
        });
    }

    @Override
    public void initView() {
        //根据订单号用户id去后台获取订单详细信息
        order_init = new Order();
        order_init.getOrder_danyilist(Integer.parseInt(User_id.getUid()), orderId, this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refund_choose_reason:
                reasonPop.showAsDropDown(chooseReasonTv);
                break;
            case R.id.refund_back:
                finish();
                break;
            case R.id.refund_picture1:
                if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    PermissionGen.with(this)
                            .addRequestCode(100)
                            .permissions(
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    android.Manifest.permission.READ_PHONE_STATE,
                                    android.Manifest.permission.CAMERA
                            )
                            .request();
                }else{
                    dispatchTakePictureIntent();
                }
                TAGE_ISRONT = 1;
                break;
            case R.id.refund_picture2:
                selectImage();
                TAGE_ISRONT = 2;
                break;
            case R.id.refund_picture3:
                selectImage();
                TAGE_ISRONT = 3;
                break;
            case R.id.refund_picture4:
                selectImage();
                TAGE_ISRONT = 4;
                break;
            case R.id.refund_sure_btn:
                if (TextUtils.isEmpty(reasonEdit.getText())){
                    desc = reason;
                }else {
                    desc = reasonEdit.getText().toString();
                }
                order_init.submit_refund(Integer.parseInt(User_id.getUid()),orderId,4,refund_fee,reason,desc
                ,imageurl1,imageurl2,imageurl3,imageurl4,this);

                break;
        }

    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        gradeTv.setText( map.get("grade_name")+"");
        courseTv.setText(map.get("course_name")+"");
        statsTv.setText("进行中");
        numberTv.setText("x"+map.get("duration")+"节");
        feeTv.setText( "￥"+map.get("fee")+"/节");
        orderfeeTv.setText("￥"+map.get("order_price"));
        refundOrderfeeTv.setText("￥"+map.get("order_price"));
//        refund_fee = map.get("order_fee")+"";
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
            //图片设置问题
            if (!TextUtils.isEmpty(filePath)) {
                //文件路劲是有的
                // 自定义大小，防止OOM
                Bitmap bitmap = getSmallBitmap(filePath, 600, 600);
                //获取图片
                //image空
//                Log.e("aa","路劲为"+filePath);
                User_init user_init=new User_Realization();
                image=new File(filePath);
//                if (TAGE_ISRONT==1){
//                     user_init.setuserbitmap(image,this);
//                }else if(TAGE_ISRONT == 3){
//                    user_init.setuserbitmap(image,this);
//                }else if (TAGE_ISRONT == 2){
//                    user_init.setuserbitmap(image,this);
//                }else if (TAGE_ISRONT==4){
                    user_init.setuserbitmap(image,this);
//                }
            }
        }
    }

    /**
     * 从相册中获取
     */
    public void selectImage() {
//        Intent pickIntent = new Intent(Intent.ACTION_PICK,null);
//        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(pickIntent, REQUEST_IMAGE_GET);
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_IMAGE_GET);

    }


    /**
     * 创建新文件
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
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
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


    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        //更新图片
        Map<String,Object> map=listmap.get(0);
        if (TAGE_ISRONT == 1) {
            imageurl1 = map.get("imageurl")+"";
            Glide.with(this).load(imageurl1).into(image1);
        }else if (TAGE_ISRONT == 2){
            imageurl2 = map.get("imageurl")+"";
            Glide.with(this).load(imageurl2).into(image2);
        }else if (TAGE_ISRONT == 3){
            imageurl3 = map.get("imageurl")+"";
            Glide.with(this).load(imageurl3).into(image3);
        }else if (TAGE_ISRONT == 4){
            imageurl4 = map.get("imageurl")+"";
            Glide.with(this).load(imageurl4).into(image4);
        }

        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
    }
    private String telphone;

    @Override
    public void successOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        new Getdata().sendMessage(User_id.getNickName()+"已提交退款申请",telphone);
        EventBus.getDefault().post(1,"changeStatus");
        Intent intent= new Intent(RefoundActivity.this,MyOrderActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
