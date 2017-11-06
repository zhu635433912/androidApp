package modle;

import java.io.File;

import modle.user_ziliao.User_id;

/**
 * 后台地址
 */

public class MyUrl {
//    public static final String URL="http://deguanjiaoyu.com/";
//    public static final String URL="http://1754q21l80.51mypc.cn:88/";

    public static final String URL="http://deguan.tpddns.cn:88/";

    //设置图片缓存地址
    public static final String IMAGE_CACHE = User_id.getInstance().getCacheDir().getAbsolutePath()+"";
    public static File getCacheFile(){
        File file = new File(IMAGE_CACHE);
        return file;
    }

}
