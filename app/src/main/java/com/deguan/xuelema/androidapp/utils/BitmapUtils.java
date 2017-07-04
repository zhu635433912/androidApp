package com.deguan.xuelema.androidapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;

/**
 * Created by Jimmy on 16/1/14.
 */
public class BitmapUtils {
    //当改变图片的默认像素点的时候,也可以降低图片在内存中的大小

    /**
     * 对图片进行压缩
     * @param file      图片源,文件的File路径
     * @param newWidth  压缩后的的宽度
     * @param newHeight 压缩后的的高度
     * @return bitmap 实际的图像
     */
    public static Bitmap bitmapThumbnail(File file, int newWidth, int newHeight) {
        //直接通过BitmapFactory解析图片的过程,如果图片过大,会遇到OOM(Out Of Memory)的错误
//        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//        return bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //不分配内存空间,但可以获取的图片的宽度和高度
        options.inJustDecodeBounds = true;
        //这时候获取的bitmap是没有任何意义的
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
        //这时候获取的图片是null
        Log.d("BitmapUtils", "使用options第一次加载图片内存" + bitmap);

        //1.获取边框信息
        int orginWidth = options.outWidth; //原始图片的宽度
        int orginHeight = options.outHeight; //原始图片的高度

        //2.通过算法计算出压缩比

        //算法一:
        //1.实际需要压缩后的宽度高度,和原始图片的宽度高度进行对比
//        int scaleWidth = orginWidth / newWidth;   // 1024/200  = 5
//        int scaleHeight = orginHeight / newHeight; // 960 /200 = 4
//        //选取压缩比率小的作为压缩比
//        int scale = scaleWidth < scaleHeight ? scaleWidth : scaleHeight;
//        options.inSampleSize = scale;
        //算法二:直接调用封装的方法
        options.inSampleSize = calculateScale(orginWidth, orginHeight, newWidth, newHeight);
        //默认的
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //放弃了透明度,但也推荐使用,可以降低一半的内存消耗
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //修改参数(inJustDecodeBounds为false,表示将图片加入到内存中)
        options.inJustDecodeBounds = false;
        //重新加载的图片就是压缩后的图片
        return BitmapFactory.decodeFile(file.getAbsolutePath(), options);
    }


    /**
     * 官方推荐的计算sample的公式 (希望的压缩是2的倍数,提高压缩图片的速度)
     * 比如
     * 原始:srcWidth 1024 srcHeight 960
     * 目标:targetWidth 1000 targetHeight 200
     */
    private static int calculateScale(int srcWidth, int srcHeight, int targetWidth, int targetHeight) {
        int sample = 1;
        while (srcWidth / 2 > targetWidth && srcHeight / 2 > targetHeight) { // 128 > 200 && 125 > 200
            srcWidth /= 2;  //256
            srcHeight /= 2; //200
            sample *= 2;    //4
        }
        return sample;
    }


}
