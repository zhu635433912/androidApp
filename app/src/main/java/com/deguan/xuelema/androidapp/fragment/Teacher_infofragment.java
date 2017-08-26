package com.deguan.xuelema.androidapp.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.FeeqianbaoActivty;
import com.deguan.xuelema.androidapp.NewMainActivity;
import com.deguan.xuelema.androidapp.Personal_Activty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SetUp;
import com.deguan.xuelema.androidapp.TeacherManActivity;
import com.deguan.xuelema.androidapp.TeacherManActivity_;
import com.deguan.xuelema.androidapp.Teacher_management;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.utils.EaseCommonUtils;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.HomeTitleAdapter;
import modle.Huanxing.db.InviteMessgeDao;
import modle.Huanxing.ui.ChatActivity;
import modle.Huanxing.ui.GroupsActivity;
import modle.Huanxing.ui.MainActivity;
import modle.Huanxing.ui.NewHuanxinMainActivity;
import modle.toos.CircleImageView;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.Constant;
import modle.user_ziliao.DemoHelper;
import modle.user_ziliao.User_id;

/**
 * Created by Administrator on 2017/6/23 0023.
 */
@EFragment(R.layout.teacher_fragmengt)
public class Teacher_infofragment extends BaseFragment implements Requirdetailed ,View.OnClickListener{
    @ViewById(R.id.techaer_massge)
    ImageView techaer_massge;
    @ViewById(R.id.teacher_setup)
    ImageView teacher_setup;
    @ViewById(R.id.myfee)
    ImageView myfee;
    @ViewById(R.id.teacher_loc)
    ImageView teacher_loc;
    @ViewById(R.id.huanxin_but)
    ImageView huanxin_but;
    @ViewById(R.id.teacher_name)
    TextView teacher_name;
    @ViewById(R.id.teacher_autograph)
    TextView teacher_autograph;
    @ViewById(R.id.new_teacher_tablayout)
    TabLayout new_teacher_tablayout;
    @ViewById(R.id.new_techaer_viewpage)
    ViewPager new_techaer_viewpage;
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.unread_message_number)
    TextView unreadAddressLable;
    @ViewById(R.id.unread_address_number)
    TextView unreadLabel;
    @ViewById(R.id.teacher_chat)
    ImageView chatImage;
    @ViewById(R.id.teacher_my)
    ImageView mychatImage;
    @ViewById(R.id.teacher_manager_image)
    ImageView teahcerManagerImage;

    private int uid;
    private int role;

    private List<String> tlebat=new ArrayList<>();
    private List<Fragment> fragments=new ArrayList<>();
    private User_init user_init;

    @Override
    public void before() {
        uid = Integer.parseInt(User_id.getUid());
        role = Integer.parseInt(User_id.getRole());
        EventBus.getDefault().register(this);
        DemoHelper sdkHelper = DemoHelper.getInstance();
        sdkHelper.pushActivity(getActivity());

        EMClient.getInstance().chatManager().addMessageListener(messageListener);
    }

    @Override
    public void initView() {

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        teacher_loc.setOnClickListener(this);
        techaer_massge.setOnClickListener(this);
        teacher_setup.setOnClickListener(this);
        myfee.setOnClickListener(this);
        huanxin_but.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        mychatImage.setOnClickListener(this);
        teahcerManagerImage.setOnClickListener(this);

    }

    @Override
    public void initData() {
        user_init = new User_Realization();
        user_init.User_Data(uid,User_id.getLat()+"",User_id.getLng()+"", this);

        tlebat.add("推荐需求");
        tlebat.add("我的订单");
        tlebat.add("我的接取");
        fragments.add(DmadFragmengt_.builder().build());
//        fragments.add(OrderFragment_.builder().build());
        fragments.add(NewOrderFragment_.builder().build());
        fragments.add(MyReceptFragment_.builder().build());
        HomeTitleAdapter adapter = new HomeTitleAdapter(getFragmentManager(),fragments,tlebat);
        new_techaer_viewpage.setAdapter(adapter);
        new_teacher_tablayout.setupWithViewPager(new_techaer_viewpage);
        new_teacher_tablayout.setTabMode(TabLayout.MODE_FIXED);

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i > 0; i++) {
                    try {
                        Thread.sleep(100000);
                        updateUnreadLabel();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        inviteMessgeDao = new InviteMessgeDao(getContext());
        registerBroadcastReceiver();
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
//        new_techaer_viewpage.setOffscreenPageLimit(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.teacher_manager_image:
                startActivity(TeacherManActivity_.intent(getContext()).get());
                break;
            case R.id.teacher_chat:
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), modle.Huanxing.ui.NewHuanxinMainActivity.class);
                startActivity(intent3);
                break;
            case R.id.teacher_my:
                Intent intent13 = new Intent();
                intent13.setClass(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent13);
                break;
            case R.id.techaer_massge:
                Intent intent = new Intent();
                intent.setClass(getActivity(), Teacher_management.class);
                startActivity(intent);
                break;
            case R.id.teacher_setup:
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), SetUp.class);
                startActivity(intent1);
                break;
            case R.id.myfee:
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), FeeqianbaoActivty.class);
                startActivity(intent2);
                break;
            case R.id.huanxin_but:
                Intent intent23 = new Intent();
                intent23.setClass(getActivity(), modle.Huanxing.ui.MainActivity.class);
                startActivity(intent23);
                break;
            case R.id.teacher_loc:
                Intent intent4 = new Intent(getActivity(), Personal_Activty.class);
                startActivity(intent4);
        }
    }

    @Subscriber(tag = "headUrl")
    private void updateHead(File msg){
//        Glide.with(this).load(msg).into(teacher_loc);
        Glide.with(getContext().getApplicationContext()).load(msg).transform(new GlideCircleTransform(getContext())).into(teacher_loc);
    }
    @Subscriber(tag = "update")
    public void updateMsg(String msg){
        user_init.User_Data(uid,User_id.getLat()+"",User_id.getLng()+"",this);
    }
    /**
     * 获取用户资料成功回调并设置
     * @param map
     */
    @Override
    public void Updatecontent(Map<String, Object> map) {
        Glide.with(getContext().getApplicationContext()).load(map.get("headimg").toString()).transform(new GlideCircleTransform(getContext())).into(teacher_loc);
        teacher_name.setText(map.get("nickname")+"");
        teacher_autograph.setText(map.get("signature")+"");
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMsgsCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUnreadLabel();
        updateUnreadAddressLable();
    }

    @Subscriber(tag = "refresh")
    public void getrefresh(String msg){
        final int count = getUnreadMsgCountTotal();
        if (count > 0) {
            unreadLabel.setText(count +"");
            unreadLabel.setVisibility(View.VISIBLE);
        } else {
            unreadLabel.setVisibility(View.INVISIBLE);
        }
    }
    @Subscriber(tag = "refresh1")
    public void getrefresh1(String msg){
        final int count = getUnreadAddressCountTotal();
        if (count > 0) {
            unreadAddressLable.setText(count +"");
            unreadAddressLable.setVisibility(View.VISIBLE);
        } else {
            unreadAddressLable.setVisibility(View.INVISIBLE);
        }
    }
    private InviteMessgeDao inviteMessgeDao;
    /**
     * get unread event notification count, including application, accepted, etc
     *
     * @return
     */
    public int getUnreadAddressCountTotal() {
        int unreadAddressCountTotal = 0;
        unreadAddressCountTotal = inviteMessgeDao.getUnreadMessagesCount();
        return unreadAddressCountTotal;
    }
    /**
     * update the total unread count
     */
    public void updateUnreadAddressLable() {
        EventBus.getDefault().post("123","refresh1");
    }
    public class MyContactListener implements EMContactListener {
        @Override
        public void onContactAdded(String username) {}
        @Override
        public void onContactDeleted(final String username) {
            updateUnreadAddressLable();
        }
        @Override
        public void onContactInvited(String username, String reason) {}
        @Override
        public void onFriendRequestAccepted(String username) {}
        @Override
        public void onFriendRequestDeclined(String username) {}
    }
    public void updateUnreadLabel() {
        EventBus.getDefault().post("123","refresh");
    }
    private BroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager broadcastManager;
    private void registerBroadcastReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_CONTACT_CHANAGED);
        intentFilter.addAction(Constant.ACTION_GROUP_CHANAGED);
//		intentFilter.addAction(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION);
        broadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                updateUnreadLabel();
                updateUnreadAddressLable();

                String action = intent.getAction();
                if(action.equals(Constant.ACTION_GROUP_CHANAGED)){
                    if (EaseCommonUtils.getTopActivity(getActivity()).equals(GroupsActivity.class.getName())) {
                        GroupsActivity.instance.onResume();
                    }
                }
                //red packet code : 处理红包回执透传消息
//				if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
//					if (conversationListFragment != null){
//						conversationListFragment.refresh();
//					}
//				}
                //end of red packet code
            }
        };
        broadcastManager.registerReceiver(broadcastReceiver, intentFilter);
    }
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                DemoHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息
            for (EMMessage message : messages) {
                EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            }
            refreshUIWithMessage();
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    private void refreshUIWithMessage() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count
                updateUnreadLabel();
            }
        });
    }

}
