package com.deguan.xuelema.androidapp.huanxin;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.deguan.xuelema.androidapp.R;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * 环信单聊界面
 */

public class HuihuaActivity extends AutoLayoutActivity {

    // 当前聊天的 ID
    private String mChatId;
    private EaseChatFragment chatFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danliao);

        // 初始化单聊页面
        chatFragment = new EaseChatFragment();
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.ec_layout_container, chatFragment).commit();

        initView();
    }
    /**
     * 初始化界面
     */
    private void initView() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
