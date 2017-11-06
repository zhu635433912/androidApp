package com.deguan.xuelema.androidapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.deguan.xuelema.androidapp.R;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import modle.MyUrl;
import modle.user_ziliao.User_id;
import okhttp3.internal.Util;
import onekeyshare.OnekeyShare;

/**
 * 第三方分享工具类
 * Created by dell on 2016/4/11.
 */
public class ShareUtil {

    private static volatile ShareUtil instance = null;

    private ShareUtil(){
    }
    public static ShareUtil getInstance() {
        if (instance == null) {
            synchronized (ShareUtil.class) {
                if (instance == null) {
                    instance = new ShareUtil();
                }
            }
        }
        return instance;
    }

    public void share(Context context){
        OnekeyShare onekeyShare = new OnekeyShare();
        //关闭sso授权
        onekeyShare.disableSSOWhenAuthorize();
        onekeyShare.setTitle("学习吧");
        onekeyShare.setText("精英领航 筑梦飞翔 \n快速对接周边老师上门服务，看得见的实惠");

        //网络图片的url：所有平台
//        onekeyShare.setImageUrl("http://deguanjiaoyu.com/Public\\static\\register1\\image\\title1.png" );//网络图片rul
//
//        onekeyShare.setImagePath("http://deguanjiaoyu.com/Public\\static\\register1\\image\\title1.png");//确保SDcard下面存在此张图片
//        onekeyShare.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        onekeyShare.setImageUrl("http://f1.sdk.mob.com/web/images/e79/bd1/6359340c97e8819de16ab2fde6.png");
        // url：仅在微信（包括好友和朋友圈）中使用
        onekeyShare.setUrl(MyUrl.URL+"index.php?s=/Home/users/index/tel/"+User_id.getUsername());   //网友点进链接后，可以看到分享的详情
//        onekeyShare.setUrl("http://sharesdk.cn");
        // Url：仅在QQ空间使用
//        onekeyShare.setTitleUrl("http://sharesdk.cn");

        onekeyShare.setTitleUrl(MyUrl.URL+"index.php?s=/Home/users/index/tel/"+User_id.getUsername());  //网友点进链接后，可以看到分享的详情
        onekeyShare.show(context);
    }
    public void shareXinlang(Context context){
//        ShareSDK.initSDK(context);
        SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setText("邀请码: "+User_id.getUsername());
//        sp.setImagePath(R.mipmap.ic_launcher);

        Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//        weibo.setPlatformActionListener(paListener); // 设置分享事件回调
        // 执行图文分享
        weibo.share(sp);
    }

    public void shareWechatCore(Context context){
//        ShareSDK.initSDK(context);
    }

    public void shareWechat(Context context){
//        ShareSDK.initSDK(context);
//        WXWebpageObject webpageObject = new WXWebpageObject();
//        webpageObject.webpageUrl = "http://v.xiumi.us/board/v5/2Z7Fo/48857694";
//
//        WXMediaMessage msg = new WXMediaMessage(webpageObject);
//        msg.title = "学了吗";
//        msg.description = "邀请码: "+ User_id.getUsername();
//        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher);
//        msg.thumbData = com.deguan.xuelema.androidapp.utils.Util.bmpToByteArray(thumb,true);
//
//        //构造一个Req
//        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("webpage");



    }

    public void shareQQ(Context context){
//        ShareSDK.initSDK(context);
        QQ.ShareParams sp = new QQ.ShareParams();
        sp.setTitle("学了吗");
        sp.setTitleUrl("http://v.xiumi.us/board/v5/2Z7Fo/48857694"); // 标题的超链接
        sp.setText("邀请码: "+User_id.getUsername());
        sp.setImageUrl("http://img.xiumi.us/xmi/ua/14iWN/i/d89eb61c05946f1b84710fc2b86b7fd6-sz_80016.png?x-oss-process=style/xm");
        sp.setSite("快来下载学了吗");
        sp.setSiteUrl("http://v.xiumi.us/board/v5/2Z7Fo/48857694");

        Platform qzone = ShareSDK.getPlatform (QQ.NAME);
// 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        qzone.setPlatformActionListener (new PlatformActionListener() {
            public void onError(Platform arg0, int arg1, Throwable arg2) {
                //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
            }
            public void onComplete(Platform arg0, int arg1, HashMap arg2) {
                //分享成功的回调
            }
            public void onCancel(Platform arg0, int arg1) {
                //取消分享的回调
            }
        });
// 执行图文分享
        qzone.share(sp);
    }
}
