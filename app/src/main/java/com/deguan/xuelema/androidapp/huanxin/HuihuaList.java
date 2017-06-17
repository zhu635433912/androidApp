package com.deguan.xuelema.androidapp.huanxin;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.SetUp;
import com.deguan.xuelema.androidapp.entities.UserMessage;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.SharedPreferencesUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.zhy.autolayout.AutoLayoutActivity;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;
import java.util.Map;

import modle.getdata.Getdata;

import static com.hyphenate.easeui.utils.EaseUserUtils.getUserInfo;


/**
 * Created by Administrator on 2017/5/26 0026.
 * 聊天会话列表
 */

public class HuihuaList extends AutoLayoutActivity implements View.OnClickListener ,Requirdetailed{
    private Button chat_haoyou;
    private EaseConversationListFragment listFragment;
    private EaseUI easeUI;
    private EaseUser easeUser;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EventBus.getDefault().register(this);
        chat_haoyou = (Button) findViewById(R.id.chat_haoyou);
        chat_haoyou.setOnClickListener(this);
        easeUI = EaseUI.getInstance();
        //初始化会话列表
        listFragment = new EaseConversationListFragment();

        //初始化事务管理
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, listFragment).commit();

        //点击事件进入单聊
        listFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Log.e("aa", EaseConstant.EXTRA_USER_ID + "111 " + conversation.conversationId());
                Intent intent = new Intent(HuihuaList.this, HuihuaActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                startActivity(intent);

            }
        });

        //获取当前页面的聊天id
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                Log.e("aa","username="+username);
                easeUser=new EaseUser(username);
//                String imageUrl = (String) SharedPreferencesUtils.getParam(HuihuaList.this,username,"");
//                if (!imageUrl.equals("")){
//                    easeUser.setAvatar(imageUrl);
//                }
                return easeUser;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_haoyou:
                //好友管理
                 Intent i = new Intent(HuihuaList.this, HaoyouActivty.class);
                startActivity(i);
                break;
        }
    }

    @Subscriber(tag = "image")
    public void setImageHead(final UserMessage entity){
        Log.d("aa",entity.getImageUrl());
        //获取当前页面的聊天id
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                Log.e("aa","username=Event"+username);
                if (entity.getUserId().equals(username)){
                    easeUser=new EaseUser(username);
                    easeUser.setAvatar(entity.getImageUrl());
                }
                return easeUser;
            }
        });
    }
    @Override
    public void Updatecontent(final Map<String, Object> map) {
        String imageUrl = (String)map.get("headimg");
        String userId = (String)map.get("mobile");
        UserMessage entity = new UserMessage();
        entity.setImageUrl(imageUrl);
        entity.setUserId(userId);
        EventBus.getDefault().post(entity,"image");
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
