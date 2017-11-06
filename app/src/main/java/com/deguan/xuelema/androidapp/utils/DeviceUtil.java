package com.deguan.xuelema.androidapp.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dell on 2016/3/14.
 */
public class DeviceUtil {

    /**
     2      * 格式化单位
     3      * @param size
     4      * @return
     5      */
     public static String getFormatSize(double size) {
         double kiloByte = size/1024;
         if(kiloByte < 1) {
             return size + "B";
         }

         double megaByte = kiloByte/1024;
         if(megaByte < 1) {
             BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
             return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
         }
         double gigaByte = megaByte/1024;
         if(gigaByte < 1) {
             BigDecimal result2  = new BigDecimal(Double.toString(megaByte));
             return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
         }
         double teraBytes = gigaByte/1024;
         if(teraBytes < 1) {
             BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
             return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
         }
         BigDecimal result4 = new BigDecimal(teraBytes);
         return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
     }

    /**
     * 删除指定目录下文件及目录
     * @param deleteThisPath
     * @param filePath
     * @return
     */
     public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
           try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                        }
                    }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                        } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                            }
                        }
                   }
               } catch (Exception e) {
               // TODO Auto-generated catch block
                 e.printStackTrace();
               }
            }
         }

    /**
     * 获得文件夹的大小
     * @param file
     * @return
     */
    public static long getFolderSize(File file){
        long size = 0;
        try {
            File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++)
                {
                        if (fileList[i].isDirectory())
                        {
                            size = size + getFolderSize(fileList[i]);

                        }else{
                            size = size + fileList[i].length();
                        }
                }
           } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
                //return size/1048576;
               return size;
    }


    /**
     * 得到当前app的name
     * @param context
     * @param processId
     * @return
     */
    public static String getAppName(Context context, int processId) {
        String processName = null;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        // 获取所有运行App的进程集合
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = context.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == processId) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                Log.e(DeviceUtil.class.getName(), e.getMessage(), e);
            }
        }
        return processName;
    }

    /**
     * 当前app的id
     * @return
     */
    public static int getAppProcessId() {
        return android.os.Process.myPid();
    }

    /**
     * 系统android版本
     * @return
     */
    public static String getBuildVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机Android API等级（22、23 ...）
     *
     * @return
     */
    public static int getBuildLevel() {
        return android.os.Build.VERSION.SDK_INT;
    }
    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }
    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }

    public static String getVersionName(Context context){
        PackageManager manager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo =  manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }
    //获取这周第一天
    public static String getWeekAndDay() {

        Calendar calendar = Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int year = calendar.get(Calendar.YEAR);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(month+1).append("-").append(days - day+1);
        String time = sb.toString();
        return time;
    }
    //获取这月第一天
    public static String getMonth() {

        Calendar calendar = Calendar.getInstance();
        //获取当前时间为本月的第几周
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        //获取当前时间为本周的第几天
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        int year = calendar.get(Calendar.YEAR);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(year).append("-").append(month+1).append("-").append(1);
        String time = sb.toString();
        return time;
    }
}
