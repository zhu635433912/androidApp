package com.deguan.xuelema.androidapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.fragment.MyBaseFragment;
import com.deguan.xuelema.androidapp.fragment.MyPublishFragment_;
import com.deguan.xuelema.androidapp.fragment.NewOrderFragment_;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.HomeTitleAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
//import modle.Huanxing.db.InviteMessgeDao;
//import modle.Huanxing.ui.GroupsActivity;
//import modle.Huanxing.ui.NewHuanxinMainActivity;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

@EFragment(R.layout.activity_new__student)
public class New_StudentFragment extends MyBaseFragment implements View.OnClickListener,Student_init,
        Requirdetailed {

    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private int id;
    private int role;
    Demand_init demand_init;
    private User_init user_init;
    private int TAGE_IRONG=0;
    private String userHeadUrl;


    @ViewById(R.id.student_manager_image)
    ImageView managerImage;
    @ViewById(R.id.unread_message_number)
    TextView unreadAddressLable;
    @ViewById(R.id.unread_address_number)
    TextView unreadLabel;
    @ViewById(R.id.new_student_viewpage)
    ViewPager viewPager;
    @ViewById(R.id.new_student_tablayout)
    TabLayout tabLayout;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.chat_icon)
    ImageView chatImage;
    @ViewById(R.id.huihua)
    TextView huihua;
    @ViewById(R.id.stidentshezhiimabt)
    TextView stidentshezhiimabt;
    @ViewById(R.id.studentwodeqianbao)
    ImageView studentwodeqianbao;
    @ViewById(R.id.studenttouxiangimg)
    ImageView studenttouxiangimg;
    @ViewById(R.id.studentusernametext)
    TextView studentusernametext;
    @ViewById(R.id.studentneirongtext)
    TextView studentneirongtext;
    @ViewById(R.id.student_chat)
    ImageView studentChatImage;
    @ViewById(R.id.student_my)
    ImageView studentMychatImage;

    @Override
    public void before() {
        //获取用户id与角色
        String ida= User_id.getUid();
        String rolea=User_id.getRole();
        id=Integer.parseInt(ida);
        role=Integer.parseInt(rolea);
        EventBus.getDefault().register(this);
        //获取用户资料
        user_init=new User_Realization();

        //获取用户自己的需求 这里开始
//        wodefabulist.setInterface(this);
        demand_init=new Demand();
//        DemoHelper sdkHelper = DemoHelper.getInstance();
//        sdkHelper.pushActivity(getActivity());

//        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void initView() {

        studentwodeqianbao.bringToFront();
        stidentshezhiimabt.bringToFront();

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        huihua.setOnClickListener(this);
//        wodejiaoshi.setOnClickListener(this);
//        host.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        studenttouxiangimg.setOnClickListener(this);
        studentwodeqianbao.setOnClickListener(this);
        stidentshezhiimabt.setOnClickListener(this);
        studentChatImage.setOnClickListener(this);
        studentMychatImage.setOnClickListener(this);
        managerImage.setOnClickListener(this);
    }

    @Override
    public void initData() {
        titles.add("我的发布");
//        titles.add("推荐教师");
        titles.add("我的订单");
//        titles.add("推荐教师");
        fragments.add(MyPublishFragment_.builder().build());
        fragments.add(NewOrderFragment_.builder().build());
//        fragments.add(TuijianFragment_.builder().build());
//        fragments.add(OrderFragment_.builder().build());
//        fragments.add(TuijianFragment_.builder().build());
//        fragments.add(new TestFragment());
//        fragments.add(new TestFragment());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getFragmentManager(),fragments,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        viewPager.setOffscreenPageLimit(0);

        demand_init.getMyDemand_list(id,4,this,1);
        user_init.User_Data(id,User_id.getLat()+"",User_id.getLng()+"",this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i > 0; i++) {
                    try {
                        Thread.sleep(100000);
//                        updateUnreadLabel();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
//        inviteMessgeDao = new InviteMessgeDao(getContext());
//        registerBroadcastReceiver();
//        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
    }

    @Override
    public void onClick(View v) {
        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
        switch (v.getId()){
            case R.id.student_manager_image:
                //个人信息
                Intent intentb=new Intent(getActivity(),Personal_Activty.class);
                startActivity(intentb);
                break;
            case R.id.student_chat:
                //会话列表
//                Intent intent1 = new Intent(getActivity(), modle.Huanxing.ui.NewHuanxinMainActivity.class);
//                startActivity(intent1);
                break;
            case R.id.student_my:
                //会话列表
//                Intent intent11 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
//                startActivity(intent11);
                break;
            case R.id.studentwodeqianbao:
                //钱包
                startActivity(new Intent(getActivity(),FeeqianbaoActivty.class));
                if(version > 5 ){
                    getActivity().overridePendingTransition(R.anim.animo_no, R.anim.slide_in_bottom);
                }

                break;
            case R.id.stidentshezhiimabt:
                //设置
                startActivity(new Intent(getActivity(), SetUp.class));
                if(version > 5 ){
                    getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                }
                break;
            case R.id.studenttouxiangimg:
                if (userHeadUrl != null) {
                    Intent intent2 = new Intent(getActivity(), PictureZoo.class);
                    intent2.putExtra("hide",userHeadUrl);
                    startActivity(intent2);
                }
                break;
//
            case R.id.studenthost:
                Intent intentt=new Intent(getActivity(),Xuqiufabu.class);
                startActivity(intentt);
                break;
//
            case R.id.chat_icon:
                //会话列表
//                Intent intent2 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
//                startActivity(intent2);

                break;
            case R.id.huihua:
                //会话列表
//                Intent intent21 = new Intent(getActivity(), modle.Huanxing.ui.MainActivity.class);
//                startActivity(intent21);
                break;
        }
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
    }
    @Subscriber(tag = "headUrl")
    public void updateHead(File msg){
//        Glide.with(getContext().getApplicationContext()).load(msg).
//                transform(new GlideCircleTransform(getActivity())).into(studenttouxiangimg);
        studenttouxiangimg.setImageURI(Uri.fromFile(msg));
    }

    @Subscriber(tag = "update")
    public void updateMsg(String msg){
        user_init.User_Data(id,User_id.getLat()+"",User_id.getLng()+"",this);
    }
    @Override
    public void Updatecontent(Map<String, Object> map) {
        if(map.get("nickname")!=null) {
            studentusernametext.setText(map.get("nickname")+"");
            if (!TextUtils.isEmpty(map.get("signature")+"")) {
                studentneirongtext.setText(map.get("signature") + "");
            }
//            Glide.with(getContext().getApplicationContext()).load(map.get("headimg").toString())
//                    .transform(new GlideCircleTransform(getActivity())).into(studenttouxiangimg);
            studenttouxiangimg.setImageURI(Uri.parse(""+map.get("headimg")));
            userHeadUrl = map.get("headimg")+"";
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=new HashMap<String,Object>();
        map=listmap.get(0);
//        Toast.makeText(getActivity(),map.get("text").toString(),Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
//    public int getUnreadMsgCountTotal() {
//        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
//    }

    @Override
    public void onResume() {
        super.onResume();
//        updateUnreadLabel();
//        updateUnreadAddressLable();
    }

//    @Subscriber(tag = "refresh")
//    public void getrefresh(String msg){
//        final int count = getUnreadMsgCountTotal();
//        if (count > 0) {
//            unreadLabel.setText(count +"");
//            unreadLabel.setVisibility(View.VISIBLE);
//        } else {
//            unreadLabel.setVisibility(View.INVISIBLE);
//        }
//    }
//    @Subscriber(tag = "refresh1")
//    public void getrefresh1(String msg){
//        final int count = getUnreadAddressCountTotal();
//        if (count > 0) {
//            unreadAddressLable.setText(count +"");
//            unreadAddressLable.setVisibility(View.VISIBLE);
//        } else {
//            unreadAddressLable.setVisibility(View.INVISIBLE);
//        }
//    }
////    private InviteMessgeDao inviteMessgeDao;
//    /**
//     * get unread event notification count, including application, accepted, etc
//     *
//     * @return
//     */
//    public int getUnreadAddressCountTotal() {
//        int unreadAddressCountTotal = 0;
////        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
//        return unreadAddressCountTotal;
//    }
//    /**
//     * update the total unread count
//     */
//    public void updateUnreadAddressLable() {
//        EventBus.getDefault().post("123","refresh1");
//    }
//    public class MyContactListener implements EMContactListener {
//        @Override
//        public void onContactAdded(String username) {}
//        @Override
//        public void onContactDeleted(final String username) {
//            updateUnreadAddressLable();
//        }
//        @Override
//        public void onContactInvited(String username, String reason) {}
//        @Override
//        public void onFriendRequestAccepted(String username) {}
//        @Override
//        public void onFriendRequestDeclined(String username) {}
//    }
//    public void updateUnreadLabel() {
//        EventBus.getDefault().post("123","refresh");
//    }
//    private BroadcastReceiver broadcastReceiver;
//    private LocalBroadcastManager broadcastManager;
//    private void registerBroadcastReceiver() {
//        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        IntentFilter intentFilter = new IntentFilter();
////        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
////        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
////		intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
//        broadcastReceiver = new BroadcastReceiver() {
//
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                updateUnreadLabel();
//                updateUnreadAddressLable();
//
//                String action = intent.getAction();
////                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
////                    if (EaseCommonUtils.getTopActivity(getActivity()).equals(GroupsActivity.class.getName())) {
////                        GroupsActivity.instance.onResume();
////                    }
////                }
//                //red packet code : 处理红包回执透传消息
////				if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
////					if (conversationListFragment != null){
////						conversationListFragment.refresh();
////					}
////				}
//                //end of red packet code
//            }
//        };
//        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
//    }
//
//
//
//    private void refreshUIWithMessage() {
//        getActivity().runOnUiThread(new Runnable() {
//            public void run() {
//                // refresh unread count
//                updateUnreadLabel();
//
//            }
//        });
//    }
//    EMMessageListener messageListener = new EMMessageListener() {
//
//        @Override
//        public void onMessageReceived(List<EMMessage> messages) {
//            // notify new message
//            for (EMMessage message : messages) {
////                DemoHelper.getInstance().getNotifier().onNewMsg(message);
//            }
//            refreshUIWithMessage();
//        }
//
//        @Override
//        public void onCmdMessageReceived(List<EMMessage> messages) {
//            //red packet code : 处理红包回执透传消息
//            for (EMMessage message : messages) {
//                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
//            }
//            refreshUIWithMessage();
//        }
//
//        @Override
//        public void onMessageRead(List<EMMessage> messages) {
//        }
//
//        @Override
//        public void onMessageDelivered(List<EMMessage> message) {
//        }
//
//        @Override
//        public void onMessageChanged(EMMessage message, Object change) {}
//    };

}
