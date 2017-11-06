package com.deguan.xuelema.androidapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.ZoomImageView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import jiguang.chat.pickerimage.utils.AttachmentStore;
import jiguang.chat.pickerimage.utils.StorageUtil;
import jiguang.chat.utils.DialogCreator;

/**
 * Created by Administrator on 2017/6/29.
 */

public class PictureZoo extends MyBaseActivity {
    private ZoomImageView himagr;
    private RelativeLayout picture_rl;
    private PopupWindow popupWindow;
    private Bitmap bmp;
    private TextView saveTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picturezoo);
        saveTv = (TextView) findViewById(R.id.save_tv);
        himagr= (ZoomImageView) findViewById(R.id.himagr);
        showPop();
        String hide=getIntent().getStringExtra("hide");
        Bundle b=getIntent().getExtras();

        if (!hide.equals("")){
            String SBM=hide.substring(0,1);
            if (SBM.equals("h")){
//                Glide.with(getApplicationContext())
//                        .load(hide)
//                        .into(himagr);
//                himagr.setImageURI(Uri.parse(hide));
                Picasso.with(this).load(hide).into(himagr);
            }else {
                himagr.setImageResource(Integer.parseInt(hide));
            }
        }else {
            if (b.getParcelable("bitmap") != null) {
                bmp = (Bitmap) b.getParcelable("bitmap");
                himagr.setImageBitmap(bmp);
                saveTv.setVisibility(View.VISIBLE);
                himagr.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        saveBitmap(PictureZoo.this,bmp);
                        return true;
                    }
                });
            } else{
                finish();
                saveTv.setVisibility(View.GONE);
         }
        }
        himagr.setOnCliLcontent(new ZoomImageView.OncliLcontent() {
            @Override
            public void setcontent(boolean status) {
                if (status) {
                    finish();
                    saveTv.setVisibility(View.GONE);
                }
            }
        });
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBitmap(PictureZoo.this,bmp);
            }
        });

    }
    private void showPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.erweima_pop, null);
        TextView saveTv = (TextView) view.findViewById(R.id.save_tv);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        popupWindow.setWidth(width );
        popupWindow.setHeight(height / 10 );
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        saveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               saveBitmap(PictureZoo.this,bmp);
                popupWindow.dismiss();
            }
        });

    }
    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 保存bitmap到本地
     *
     * @param context
     * @param mBitmap
     * @return
     */
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            Toast.makeText(context, filePic.getAbsolutePath(), Toast.LENGTH_SHORT).show();
            // 其次把文件插入到系统图库

                MediaStore.Images.Media.insertImage(context.getContentResolver(),
                        filePic.getAbsolutePath(), savePath + generateFileName() + ".jpg", null);
            // 最后通知图库更新
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + savePath)));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }
    /**
     * 随机生产文件名
     *
     * @return
     */
    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }
}
