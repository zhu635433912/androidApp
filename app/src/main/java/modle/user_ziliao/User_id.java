package modle.user_ziliao;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.deguan.xuelema.androidapp.entities.AddFriendEntity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.TeacherDbUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import jiguang.chat.activity.fragment.ContactsFragment;
import jiguang.chat.application.JGApplication;
import jiguang.chat.database.UserEntry;
import jiguang.chat.entity.NotificationClickEventReceiver;
import jiguang.chat.location.service.LocationService;
import jiguang.chat.pickerimage.utils.StorageUtil;
import jiguang.chat.utils.SharePreferenceManager;
import jiguang.chat.utils.imagepicker.GlideImageLoader;
import jiguang.chat.utils.imagepicker.ImagePicker;
import jiguang.chat.utils.imagepicker.view.CropImageView;
import modle.getdata.Getdata;


/**
 * 登陆成功用户的全局资料
 */

public class User_id extends JGApplication {

    public static final String CONV_TITLE = "conv_title";

    private static final String JCHAT_CONFIGS = "JChat_configs";

    private static String uid;
    private static String role;
    private static String password;
    private static String username;
    private static String imageUrl;
    private static Context applicationContent;
    private static double lat,lng;
    private static String status;
    public static String APP_URL = "http://deguanjiaoyu.com/index.php?s=/Service/Public/downUrl";
    private static String address;
    private static String nickName;
    private static String province;
    private static String city;
    private static ContactsFragment fragment;
    private static List<AddFriendEntity> datas = new ArrayList<>();

    public static ContactsFragment getFragment() {
        return fragment;
    }

    public static void setFragment(ContactsFragment fragment) {
        User_id.fragment = fragment;
    }

    public static String getProvince() {
        return province;
    }

    public static void setProvince(String province) {
        User_id.province = province;
    }

    public static String getCity() {
        return city;
    }

    public static void setCity(String city) {
        User_id.city = city;
    }

    public static String getNickName() {
        return nickName;
    }

    public static void setNickName(String nickName) {
        User_id.nickName = nickName;
    }

    private Context appContext;
    private List<Activity> activityList = new LinkedList<Activity>();

    public static void deleteActivity() {
        activity.finish();
    }

    public static void setActivity(Activity activity) {
        User_id.activity = activity;
    }

    private static Activity activity;
    private static User_id instance;
    private static Map<String, Object> map;
    public static Map<String, Object> getMap() {
        return map;
    }

    public static void setMap(Map<String, Object> map) {
        User_id.map = map;
    }

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContent = this;
        instance = this;
        Fresco.initialize(getApplicationContext());
        //初始化Fresco
//        FrescoHelper.getInstance().init(this);
//        Fresco.initialize(this);
        //初始化数据库类
        DbUtil.init(this);
        TeacherDbUtil.init(this);
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this,"wx3815ad6bb05c5aca");
        iwxapi.registerApp("wx3815ad6bb05c5aca");
        appContext = this;

        //初始化极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        context = getApplicationContext();
        StorageUtil.init(context, null);

        Fresco.initialize(getApplicationContext());
        SDKInitializer.initialize(getApplicationContext());
        locationService = new LocationService(getApplicationContext());

        JMessageClient.init(getApplicationContext(), true);
        JMessageClient.setDebugMode(true);
        SharePreferenceManager.init(getApplicationContext(), JCHAT_CONFIGS);
        //设置Notification的模式
        JMessageClient.setNotificationFlag(JMessageClient.FLAG_NOTIFY_WITH_SOUND | JMessageClient.FLAG_NOTIFY_WITH_LED | JMessageClient.FLAG_NOTIFY_WITH_VIBRATE);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static User_id getInstance() {
//        if (null == instance) {
//            instance = new User_id();
//        }
        return instance;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void addFriend(AddFriendEntity entity){
        datas.add(entity);
    }

    public void deleteFriend(){
        datas.clear();
    }

    public List<AddFriendEntity> getFriends(){
        return datas;
    }


    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
//        System.exit(0);
    }

    public static String getAddress() {
        return address;
    }

    public static void setAddress(String address) {
        User_id.address = address;
    }

    public static double getLat(){return lat;}

    public static void setLat(double lat){User_id.lat = lat;}

    public static double getLng(){return lng;};

    public static void setLng(double lng){User_id.lng = lng;}

    public static String getStatus(){return status;}

    public static void setStatus(String status){
        User_id.status = status;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User_id.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        User_id.password = password;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        User_id.uid = uid;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        User_id.role = role;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        User_id.imageUrl = imageUrl;
    }
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}