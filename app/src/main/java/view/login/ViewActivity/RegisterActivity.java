package view.login.ViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.deguan.xuelema.androidapp.NewMainActivity_;
import com.deguan.xuelema.androidapp.PayPswActivity_;
import com.deguan.xuelema.androidapp.R;
import com.zhy.autolayout.AutoLayoutActivity;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.FinishRegisterActivity;
import jiguang.chat.activity.MainActivity;
import jiguang.chat.database.UserEntry;
import jiguang.chat.utils.SharePreferenceManager;
import jiguang.chat.utils.ToastUtil;
import modle.user_ziliao.User_id;
import view.login.Modle.MobileView;
import view.login.Modle.Modle_user;
import view.login.Modle.RegisterEntity;
import view.login.Modle.RegisterUtil;
import view.login.Modle.RegisterView;
import view.login.presenter.S_wan_presenter;
import view.login.presenter.login_wan_presenter;


/**
 * 注册页面
 */

public class RegisterActivity extends AutoLayoutActivity implements Dei_init,View.OnClickListener, RegisterView, MobileView {
    private ImageButton dei_loginbutton;//登陆
    private RadioButton Rdbutton;//学生
    private RadioButton Rdbutton1;//老师
    private ImageButton dei_exit;//返回
    private EditText dei_editext;//手机号码
    private EditText dei_editext1;//密码
    private EditText dei_Verfic;//验证码
    private Button dei_toview;//获取验证码
    private ImageButton dei_Verificatgion;//查看
    private EditText dei_yqm;//邀请码
    private login_wan_presenter dwan;
    private TextView text;
    private EditText payPsdEdit;
    private int role = 1;
    private String username;
    private String password;
    private PopupWindow popupWindow,registerPop;
    private TextView descTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_dei);
        User_id.getInstance().addActivity(this);
        descTv = (TextView) findViewById(R.id.register_user);
        descTv.setOnClickListener(this);
        inint();
        showPop();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showChoosePop();
                            registerPop.showAtLocation(descTv, Gravity.CENTER,0,0);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void showChoosePop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.register_pop,null);
        ImageView studentImage = (ImageView) view.findViewById(R.id.register_student);
        ImageView teacherImage = (ImageView) view.findViewById(R.id.register_teacher);
        LinearLayout studentLl = (LinearLayout) view.findViewById(R.id.register_student_ll);
        LinearLayout teacherLl = (LinearLayout) view.findViewById(R.id.register_teacher_ll);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        registerPop = new PopupWindow(view);
//        registerPop.setFocusable(true);
//        registerPop.setOutsideTouchable(true);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        registerPop.setWidth(width/10*8);
        registerPop.setHeight(height / 3 * 2);
        registerPop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        registerPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(RegisterActivity.this, 1f);
            }
        });
        studentLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 1;
                Rdbutton1.setChecked(false);
                registerPop.dismiss();
            }
        });
        teacherLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 2;
                Rdbutton1.setChecked(true);
                Rdbutton.setChecked(false);
                registerPop.dismiss();
            }
        });
        studentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 1;
                Rdbutton.setChecked(true);
                Rdbutton1.setChecked(false);
                registerPop.dismiss();
            }
        });
        teacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                role = 2;
                Rdbutton1.setChecked(true);
                Rdbutton.setChecked(false);
                registerPop.dismiss();
            }
        });

    }
    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
    private void showPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.user_shuoming_pop,null);
        TextView textView = (TextView) view.findViewById(R.id.pop_shuoming);
        popupWindow = new PopupWindow(view);
        popupWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        popupWindow.setWidth(width);
        popupWindow.setHeight(height);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    private void inint() {
        dwan=new S_wan_presenter(this);
        dei_yqm= (EditText) findViewById(R.id.dei_tuijianma);
        dei_loginbutton= (ImageButton) findViewById(R.id.dei_login);
        dei_exit= (ImageButton) findViewById(R.id.dei_exti);
        Rdbutton= (RadioButton) findViewById(R.id.dei_RadioGrooup);
        Rdbutton1= (RadioButton) findViewById(R.id.dei_radiobutton1);
        dei_editext= (EditText) findViewById(R.id.dei_username);
        dei_editext1= (EditText) findViewById(R.id.dei_password);
        dei_Verfic= (EditText) findViewById(R.id.dei_editextyansema);
        payPsdEdit = (EditText) findViewById(R.id.dei_pay_password);
        dei_toview= (Button) findViewById(R.id.dei_VictButton);
        dei_Verificatgion= (ImageButton) findViewById(R.id.dei_imagSess);
        text= (TextView) findViewById(R.id.dei_yzm);
        dei_toview.setOnClickListener(this);
        dei_Verificatgion.setOnClickListener(this);
        Rdbutton.setOnClickListener(this);
        Rdbutton1.setOnClickListener(this);
        dei_loginbutton.setOnClickListener(this);
        dei_exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_user:
                popupWindow.showAtLocation(descTv, Gravity.CENTER,0,0);
                break;
            case R.id.dei_VictButton:
                Log.d("aa","获取验证码");

//                dwan.SMSverification(dei_toview,text,"signup");
                if (dei_editext.getText().length()==11) {
                    Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            CountDownTimer timer = new CountDownTimer(60000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    dei_toview.setClickable(false);
                                    dei_toview.setBackground(getResources().getDrawable(R.mipmap.register_un_yzm));
                                    dei_toview.setText("重新发送"+(millisUntilFinished / 1000) + "s");
                                }

                                @Override
                                public void onFinish() {
                                     dei_toview.setClickable(true);
                                    dei_toview.setText("重新发送");
                                }
                            }.start();
                        }
                    });
                    new RegisterUtil().getMobileNum(dei_editext.getText().toString(),this);
                }else {
                    Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.dei_imagSess:
                Log.d("aa","查看密码");
                dwan.hidden(dei_editext1);
                break;
            case R.id.dei_RadioGrooup:
                role = 1;
                Rdbutton1.setChecked(false);
                break;
            case R.id.dei_radiobutton1:
                role = 2;
                Rdbutton.setChecked(false);
                break;
            case R.id.dei_login:
                Log.d("aa","注册");
//                if (dei_editext1.getText().length() > 5 && dei_editext1.getText().length() <= 16 ) {
//                    dwan.Registereduser();
////                }else if (dei_editext1.getText().length() > 5 && dei_editext1.getText().length() <= 16 && payPsdEdit.getText().length()!=6){
////                    Toast.makeText(this, "请输入6位支付密码", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(this, "密码必须超过5位小于16位", Toast.LENGTH_SHORT).show();
//                }

                if (dei_editext.getText().length() != 11){
                    Toast.makeText(this, "请输入11位手机号码", Toast.LENGTH_SHORT).show();
                }else if (dei_editext1.getText().length() <= 5 || dei_editext1.getText().length() >16){
                    Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
                }else if (dei_Verfic.getText().length() != 4){
                    Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                }else {
                    JMessageClient.register(username, "123456", new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {

                        }
                    });
                    final String uri = "https://api.im.jpush.cn/v1" + "/users/" + username;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String httpGet = executeHttpPost(uri);
                            if (httpGet == null) {
                                SharePreferenceManager.setRegisterName(username);
                                SharePreferenceManager.setRegistePass("123456");
//                    mContext.startActivity(new Intent(mContext, FinishRegisterActivity.class));
                            } else {
//                    mContext.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ToastUtil.shortToast(mContext, "该用户已经存在");
//                        }
//                    });
                            }

                        }
                    }).start();
                    username = dei_editext.getText().toString();
                    password = dei_editext1.getText().toString();
                    new RegisterUtil().getRegisterEntity(role,dei_editext.getText().toString(),dei_editext1.getText().toString()
                    ,dei_Verfic.getText().toString(),dei_yqm.getText().toString(),this);
                }
                break;
            case R.id.dei_exti:
                Log.d("aa","返回上一步");
                Intent intent=new Intent(RegisterActivity.this,LoginAcitivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean Choice() {return false;}

    @Override
    public void Successful(Modle_user user) {Toast.makeText(this,"以发送验证码到"+user.getUsername(),Toast.LENGTH_LONG).show();}

    @Override
    public void Fail(String yuan) {Toast.makeText(this,yuan,Toast.LENGTH_LONG).show();}

    @Override
    public String getusername() {
        return dei_editext.getText().toString();
    }

    @Override
    public String getpassword() {
        return dei_editext1.getText().toString();
    }

    public String getPayPsd(){return payPsdEdit.getText().toString();}
    @Override
    public String Verification() {
        return dei_Verfic.getText().toString();
    }

    @Override
    public String gettoogbuton() {
        if (Rdbutton.isChecked()){
            return "学生";
        }else {
            return "教师";
        }
    }

    @Override
    public void setReatfalse(String a) {
        Toast.makeText(this,a,Toast.LENGTH_LONG).show();
    }

    @Override
    public void setReatture(final Map<String,Object> map) {
        JMessageClient.login(getusername(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw("123456");
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
//                    mContext.goToActivity(mContext, MainActivity.class);
                    Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_LONG).show();
                    Intent intent = NewMainActivity_.intent(RegisterActivity.this).get();
                    String role= (String) map.get("role");
                    String id= (String) map.get("id");
                    intent.putExtra("id",id);
                    intent.putExtra("role",role);
                    getUser_id().setRole(role);
                    getUser_id().setUid(id);
                    startActivity(intent);
                    finish();
                    Log.d("aa", "登陆成功");
//                    mContext.finish();
                } else {
                    Log.d("aa", "登陆失败"+responseMessage);
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                }
            }
        });


    }

    @Override
    public void successMobile(Map<String, Object> map) {
        Toast.makeText(this, "获取验证码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failMobile(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successRegister(String msg) {
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    EMClient.getInstance().createAccount(username,
//                            //                            password
//                            "123456"
//                    );//同步方法
//                } catch (HyphenateException e) {
//                    e.printStackTrace();
//                }
//                Log.e("aa", "环信注册成功");
//            }
//        }).start();

        JMessageClient.login(username, "123456", new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    SharePreferenceManager.setCachedPsw(password);
                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    Log.d("aa","登陆成功");
//                    ToastUtil.shortToast(, "登陆成功");
//                    mContext.finish();
                } else {
                    Log.d("aa","登陆失败");
//                    ToastUtil.shortToast(mContext, "登陆失败" + responseMessage);
                }
            }
        });
        User_id.setUsername(username);
        User_id.setPassword(password);
        Intent intent = NewMainActivity_.intent(this).get();
        intent.putExtra("id",msg);
        intent.putExtra("role",role);
        getUser_id().setRole(role+"");
        getUser_id().setUid(msg);
        startActivity(intent);
        finish();
    }
    public String executeHttpPost(final String uid) {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            //GET请求直接在链接后面拼上请求参数
            URL url = new URL(uid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "Basic NGY3YWVmMzRmYjM2MTI5MmM1NjZhMWNkOjA1NGQ2MTAzODIzYTcyNmZjMTJkMDQ2Ng==");
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            return null;
        }
        //关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
    @Override
    public void failRegister(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

    }


    //获取用户参数
    public User_id getUser_id() {
        return ((User_id)getApplicationContext());
    }

    @Override
    public String getyqm() {
        return dei_yqm.getText().toString();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}