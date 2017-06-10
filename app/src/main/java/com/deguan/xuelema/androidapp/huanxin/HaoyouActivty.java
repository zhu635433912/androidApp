package com.deguan.xuelema.androidapp.huanxin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.deguan.xuelema.androidapp.R;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseBaseActivity;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/26 0026.
 */

public class HaoyouActivty extends  EaseBaseActivity implements View.OnClickListener{
    private Button rong_huihua;
    private EaseContactListFragment contactListFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ronghuihua);

        rong_huihua= (Button) findViewById(R.id.rong_huihua);
        rong_huihua.setOnClickListener(this);


        //初始化好友列表
        contactListFragment=new EaseContactListFragment();
        //去获取好友列表
        new Myadbet().execute("");

        contactListFragment.setContactListItemClickListener(new EaseContactListFragment.EaseContactListItemClickListener() {
            @Override
            public void onListItemClicked(EaseUser user) {

                Intent intent = new Intent(HaoyouActivty.this, HuihuaActivity.class);
                intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                intent.putExtra(EaseConstant.EXTRA_USER_ID,user.getUsername());
                startActivity(intent);

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rong_huihua:
                this.finish();
                break;


        }

    }

    class Myadbet extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... params) {
            try {
                List<String> usernames=EMClient.getInstance().contactManager().getAllContactsFromServer();
                Log.e("aa","从服务器返回好友列表"+usernames);
                //获取好友列表参数
                contactListFragment.setContactsMap(getContacts(usernames));
                //初始化事务管理
                getSupportFragmentManager().beginTransaction().add(R.id.huihuahuanx,contactListFragment).commit();
            } catch (HyphenateException e) {
                e.printStackTrace();
            }
            return null;
        }
    }



    private Map<String, EaseUser> getContacts(List<String> list){

        Map<String, EaseUser> contacts = new HashMap<String, EaseUser>();

        for (int i=0;i<list.size();i++) {
            EaseUser user = new EaseUser(list.get(i));
            contacts.put("easeuitest"+i,user);
        }
        return contacts;
    }

}
