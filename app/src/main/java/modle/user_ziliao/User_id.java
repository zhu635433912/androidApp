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

import com.deguan.xuelema.androidapp.huanxin.HuihuaActivity;
import com.deguan.xuelema.androidapp.huanxin.HuihuaList;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.DbUtil;
import com.deguan.xuelema.androidapp.utils.TeacherDbUtil;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import modle.getdata.Getdata;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;

/**
 * 登陆成功用户的全局资料
 */

public class User_id extends Application {
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


    private Context appContext;
    EMMessageListener messageListener = null;
    private List<Activity> activityList = new LinkedList<Activity>();
    private static User_id instance;
    private static Map<String, Object> map;
    private EaseUI easeUI;
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
        DemoHelper.getInstance().init(applicationContent);
        //初始化Fresco
//        FrescoHelper.getInstance().init(this);
//        Fresco.initialize(this);
        //初始化数据库类
        DbUtil.init(this);
        TeacherDbUtil.init(this);
        IWXAPI iwxapi = WXAPIFactory.createWXAPI(this,"wx3815ad6bb05c5aca");
        iwxapi.registerApp("wx3815ad6bb05c5aca");
        appContext = this;
//        EMOptions options = new EMOptions();
//        // 默认添加好友时，是不需要验证的，改成需要验证
//        options.setAcceptInvitationAlways(true);
//        int pid = android.os.Process.myPid();
//        String processAppName = getAppName(pid);
//        // 如果APP启用了远程的service，此application:onCreate会被调用2次
//        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
//        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
//        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
//            Log.e("aa", "enter the service process!");
//
//            // 则此application::onCreate 是被service 调用的，直接返回
//            return;
//        }
//        //初始化环形
//        EaseUI.getInstance().init(appContext, options);
//        EMClient.getInstance().setDebugMode(false);
//        easeUI = EaseUI.getInstance();

        //初始化极光推送
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
//
//        //注册聊天监听器
//        registerMessageListener();
//        //聊天提示
//        liaotiantishi();


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

    // 遍历所有Activity并finish
    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        System.exit(0);
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

    //环信注册聊天提示监听器
    protected void registerMessageListener() {
        messageListener = new EMMessageListener() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                for (EMMessage message : list) {
                    Log.e("aa", "onMessageReceived id : " + message.getMsgId());
                    // in background, do not refresh UI, notify it in notification bar
                    if (!easeUI.hasForegroundActivies()) {
                        easeUI.getNotifier().onNewMsg(message);
                        Log.e("aa","环信聊天监听器已设置");
                    }
                }
            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> list) {
                for (EMMessage message : list) {
                    Log.e("aa", "receive command message");
                    //get message body
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    final String action = cmdMsgBody.action();//获取自定义action
                    //red packet code : 处理红包回执透传消息
                    if (!easeUI.hasForegroundActivies()) {

                    }

                    if (action.equals("__Call_ReqP2P_ConferencePattern")) {
                        String title = message.getStringAttribute("em_apns_ext", "conference call");
                        Toast.makeText(appContext, title, Toast.LENGTH_LONG).show();
                    }
                    //end of red packet code
                    //获取扩展属性 此处省略
                    //maybe you need get extension of your message
                    //message.getStringAttribute("");
                    Log.e("aa", String.format("Command：action:%s,message:%s", action, message.toString()));
                }
            }
            @Override
            public void onMessageRead(List<EMMessage> list) {

            }
            @Override
            public void onMessageDelivered(List<EMMessage> list) {

            }

            @Override
            public void onMessageChanged(EMMessage emMessage, Object o) {
                Log.e("aa", "change:");
                Log.e("aa", "change:" + o);
            }

        };
    }

    public void liaotiantishi(){
        Log.e("aa","环信聊天监听器准备就绪");
        easeUI.getNotifier().setNotificationInfoProvider(new EaseNotifier.EaseNotificationInfoProvider() {
            @Override
            public String getDisplayedText(EMMessage message) {
                // 设置状态栏的消息提示，可以根据message的类型做相应提示
                String ticker = EaseCommonUtils.getMessageDigest(message,appContext);
                Log.e("aa","收到一条短信"+ticker);

                if(message.getType() == EMMessage.Type.TXT){
                    ticker = ticker.replaceAll("\\[.{2,3}\\]", "[表情]");
                }
                EaseUser user = new EaseUser(message.getFrom());
                if(user != null){
                    return getUserInfo(message.getFrom()).getNick() + ": " + ticker;
                }else{
                    return message.getFrom() + ": " + ticker;
                }
            }

            @Override
            public String getLatestText(EMMessage message, int fromUsersNum, int messageNum) {
                Log.e("aa","收到"+fromUsersNum+"消息");
                return "有个基友给你发来了一条短信";
            }

            @Override
            public String getTitle(EMMessage message) {
                Log.e("aa","getTitle");
                return null;
            }

            @Override
            public int getSmallIcon(EMMessage message) {
                Log.e("aa","message");
                return 0;
            }

            @Override
            public Intent getLaunchIntent(EMMessage message) {
                Log.e("aa","getLaunchIntent");
                //设置点击通知栏跳转事件
                Intent intent = new Intent(appContext, HuihuaActivity.class);
                //进入单聊界面
                EMMessage.ChatType chatType = message.getChatType();
                if (chatType == EMMessage.ChatType.Chat) { // 单聊信息
                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, message.getFrom());
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, EaseConstant.CHATTYPE_SINGLE);
                }
                return intent;
            }
        });
    }
}