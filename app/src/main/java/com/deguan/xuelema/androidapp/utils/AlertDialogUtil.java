package com.deguan.xuelema.androidapp.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;


import modle.MyUrl;

/**
 * Dialog对话框工具类
 * Created by dell on 2016/4/11.
 */
public class AlertDialogUtil {

    public static AlertDialog setClearDialog(Context context, final TextView nowCacheText){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage("确定要清除缓存吗")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DeviceUtil.deleteFolderFile(MyUrl.IMAGE_CACHE,true);
                        nowCacheText.setText("当前缓存："+DeviceUtil.getFormatSize(DeviceUtil.getFolderSize(MyUrl.getCacheFile())));
                    }
                })
                .setNegativeButton("取消",null);
        AlertDialog clearDialog = builder.create();
        return clearDialog;
    }
}
