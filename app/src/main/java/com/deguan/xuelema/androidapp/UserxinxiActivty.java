package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
//import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import jiguang.chat.database.FriendRecommendEntry;
import jiguang.chat.database.UserEntry;
import jiguang.chat.entity.FriendInvitation;
import jiguang.chat.utils.ToastUtil;
import modle.Adapter.KechengAdapter;
//import modle.Huanxing.ui.ChatActivity;
import modle.Increase_course.Increase_course;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.toos.CircleImageView;
import modle.user_ziliao.User_id;

import static com.deguan.xuelema.androidapp.R.id.teacher_star;

/**
 * 老师个人信息
 */

public class UserxinxiActivty extends MyBaseActivity implements Requirdetailed,View.OnClickListener,Baseinit, TuijianView {
    private TextView Requiname;
    private TextView Requitext;
    private TextView Teachergerjianjie,gerxxjiaoxueanl,gerxxjinl;
    private LinearLayout pingjia;
    private RelativeLayout grfanhui;
    private TextView gerxxxuexiquan;
    private RelativeLayout jiaoyi;
    private ListView gerrxxedintext;
    private ImageButton bohao;
    private String mobile;
    private ImageButton imageButton2;
    private TextView gerxues;
    private TextView dindan;
    private List<Map<String, Object>> listmap;
    private int Requir_id;
    private String username;//教师id
    private ImageButton imageButton;
    private KechengAdapter kechengAdapter;
    private SimpleDraweeView gerxxtoux;
    private AVLoadingIndicatorView jiaoyishuax;
    private String userHeadUrl = "";
    private TextView jubaoTv;
    private ImageView iamgeview;
    private RelativeLayout gerxxTob;
    private PopupWindow buyPopWindow,experPop;
    private TextView courseMoney;
    private TextView zongfee;
    private TextView keshi;
    private Button buyBtn;
    private TextView courseExplain;
    private TextView courseType;
    private TextView courseNametV;
    private String myid;
    private String address;
    private ImageButton backImage;
    private TextView successTv;
    private String content = "";
    private TextView signTv;
    private String xueliUrl ;
    private String exper;
    private String exper_url;

    private TextView diqu;
    private TextView xinjijiaoshi;
    private TextView jiaoling;
    private TextView techaertec;
    private TextView techaerxue;

    private TextView starNumberTv;
    private ImageView starImage;
    private SimpleDraweeView teacherCardImage;
    private TextView educationPassTv,cardPassTv;
    private UserInfo mMyInfo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi);
        User_id.getInstance().addActivity(this);

        educationPassTv = (TextView) findViewById(R.id.user_education_pass);
        cardPassTv = (TextView) findViewById(R.id.user_card_pass);
        gerxxjinl = (TextView) findViewById(R.id.gerxxjinl);
        gerxxjiaoxueanl = (TextView) findViewById(R.id.gerxxjiaoxueanl);
        teacherCardImage = (SimpleDraweeView) findViewById(R.id.teacher_card_pic);
        starNumberTv = (TextView) findViewById(R.id.teacher_star_number);
        starImage = (ImageView) findViewById(teacher_star);
        signTv = (TextView) findViewById(R.id.textView2);
        backImage = (ImageButton) findViewById(R.id.userxinxi_back);
        jubaoTv = (TextView) findViewById(R.id.jiaoyi_jubao);
        imageButton= (ImageButton) findViewById(R.id.imageButton);
        gerxues= (TextView) findViewById(R.id.gerxues);
        dindan= (TextView) findViewById(R.id.dindan);
        Requiname= (TextView) findViewById(R.id.gerxxname);
        Requitext= (TextView) findViewById(R.id.gerxxneirong);
        Teachergerjianjie= (TextView) findViewById(R.id.gerjianjie);
        pingjia= (LinearLayout) findViewById(R.id.teacher_eva);
        grfanhui= (RelativeLayout) findViewById(R.id.grfanhui);
        successTv = (TextView) findViewById(R.id.gerxxxuexiquana);
        gerxxxuexiquan= (TextView) findViewById(R.id.gerxxxuexiquan);
        jiaoyi= (RelativeLayout) findViewById(R.id.jiaoyi);
        gerrxxedintext= (ListView) findViewById(R.id.gerrxxedintext);
        bohao= (ImageButton) findViewById(R.id.bohao);
        imageButton2= (ImageButton) findViewById(R.id.imageButton2);
        gerxxtoux= (SimpleDraweeView) findViewById(R.id.gerxxtoux);
        jiaoyishuax= (AVLoadingIndicatorView) findViewById(R.id.jiaoyishuax);
        iamgeview= (ImageView) findViewById(R.id.curr_backe);
        gerxxTob= (RelativeLayout) findViewById(R.id.gerxxTob);
        techaertec = (TextView) findViewById(R.id.techaertec);
        xinjijiaoshi= (TextView) findViewById(R.id.xinjijiaoshi);
        techaerxue= (TextView) findViewById(R.id.techaerxue);
        jiaoling= (TextView) findViewById(R.id.jiaoling);
        diqu= (TextView) findViewById(R.id.diqu);

        mMyInfo = JMessageClient.getMyInfo();
        gerxxTob.bringToFront();
        jiaoyishuax.bringToFront();
        gerxxxuexiquan.bringToFront();
        jiaoyi.bringToFront();
        grfanhui.bringToFront();

        gerxxjinl.setOnClickListener(this);
        gerxxjiaoxueanl.setOnClickListener(this);
        teacherCardImage.setOnClickListener(this);
        gerxxtoux.setOnClickListener(this);
        successTv.setOnClickListener(this);
        jubaoTv.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        bohao.setOnClickListener(this);
        pingjia.setOnClickListener(this);
        Teachergerjianjie.setOnClickListener(this);
        gerxxxuexiquan.setOnClickListener(this);
        grfanhui.setOnClickListener(this);
        backImage.setOnClickListener(this);
        signTv.setOnClickListener(this);
        if (getIntent().getStringExtra("content")!=null) {
            content = getIntent().getStringExtra("content");
        }
        address = User_id.getAddress();
        myid = getIntent().getStringExtra("myid");
        if (myid == null)
        {
            myid = "0";
        }
        final String user_id=getIntent().getStringExtra("user_id");
//        Log.e("aa","UserxinActivyt接收到老师id为"+user_id);
        int id=Integer.parseInt(User_id.getUid());
        Requir_id=Integer.parseInt(user_id);
        teacherId = Requir_id;
        userHeadUrl = getIntent().getStringExtra("head_image");
        if (!userHeadUrl.equals(""))
//            Glide.with(getApplicationContext()).load(userHeadUrl).transform(
//                    new GlideCircleTransform(this)).into(gerxxtoux);
        gerxxtoux.setImageURI(Uri.parse(userHeadUrl));
        //加载数据
        Teacher_init teacher=new Teacher();
        teacher.Get_Teacher_detailed(id,Requir_id,this,0,1);

        //获取成交率
        Getdata getdata=new Getdata();
        getdata.getdelt_info(Requir_id,this);

        //加载课程数据
        Increase_course increaseCourse=new Increase_course();
        increaseCourse.selecouse(Requir_id,this,null);



        gerrxxedintext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showBuyPop();
                buyPopWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                Map<String, Object> map = new HashMap<String, Object>();
                map = listmap.get(position);
                String fee= (String) map.get("visit_fee");
                teacherfee = Integer.parseInt((String)map.get("visit_fee"));
                studentfee = Integer.parseInt((String)map.get("unvisit_fee"));
                coursefee = studentfee;
                courseId = (String) map.get("course_id");
                gradeId = (String)map.get("grade_id");
                courseName =  (String)map.get("course_name") ;
                courseNumber = 1;

                keshi.setText(courseNumber+"");
                courseMoney.setText(coursefee+"元/小时");
                courseNametV.setText(courseName);
//                courseType.setText((String)map.get("service_type_txt"));
                courseExplain.setText((String)map.get("course_remark"));
                zongfee.setText(coursefee*courseNumber+"");
                String fee1= (String) map.get("unvisit_fee");
                String course_id = (String) map.get("course_id");
                String course_name = (String)map.get("course_name") ;
                String grade_id = (String)map.get("grade_id");

                int unvisit_fee=Integer.parseInt(fee1);
                int visit_fee=Integer.parseInt(fee);
//                buyPopWindow.showAtLocation(imageButton2, Gravity.BOTTOM,0,0);

            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2000);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            jiaoyishuax.setVisibility(View.GONE);
//                        }
//                    });
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void showExperPop() {
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.exper_pop, null);
        TextView experTv = (TextView) view.findViewById(R.id.pop_exper_tv);
        SimpleDraweeView experImage = (SimpleDraweeView) view.findViewById(R.id.pop_exper_image);
        experPop = new PopupWindow(view);
        experPop.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        if (TextUtils.isEmpty(exper)){
            experTv.setText("暂无个人经历介绍");
        }else {
            experTv.setText("" + exper);
        }
        if (!TextUtils.isEmpty(exper_url)) {
//        Glide.with(getApplicationContext()).load(exper_url).into(experImage);
            experImage.setVisibility(View.VISIBLE);
            experImage.setImageURI(Uri.parse(exper_url));
        }
        experPop.setWidth(width / 10 * 8);
        experPop.setHeight(height / 5 *4 );
        experPop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        experPop.setOutsideTouchable(true);
        experPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(UserxinxiActivty.this, 1f);
            }
        });
        experTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experPop.dismiss();
            }
        });
        experImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                experPop.dismiss();
            }
        });
    }
    private int coursefee,teacherId,courseNumber,servicetype,studentfee,teacherfee;
    private String courseId,gradeId,serviceType,courseName;
    private TextView studentShangmen,teacherShangmen;
    private void showBuyPop() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.purchase_course,null);
        courseMoney = (TextView) view.findViewById(R.id.jieshufee);
        zongfee = (TextView) view.findViewById(R.id.zongfee);
        TextView jia= (TextView) view.findViewById(R.id.jia);
        TextView jian= (TextView) view.findViewById(R.id.jian);
        keshi = (TextView) view.findViewById(R.id.fee);
        buyBtn = (Button) view.findViewById(R.id.goumai);
        courseExplain = (TextView) view.findViewById(R.id.course_explain);
        courseType = (TextView) view.findViewById(R.id.xuessm);
        courseNametV = (TextView) view.findViewById(R.id.kechengname);
        studentShangmen  = (TextView) view.findViewById(R.id.xuessm);
        teacherShangmen = (TextView) view.findViewById(R.id.laoshism);
        final EditText descEdit = (EditText) view.findViewById(R.id.buy_course_desc);
        studentShangmen.setTextColor(Color.parseColor("#fd1245"));
        buyPopWindow = new PopupWindow(view);
        buyPopWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        buyPopWindow.setWidth(width/10*9);
        buyPopWindow.setHeight(height/5*2);
        buyPopWindow.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        buyPopWindow.setOutsideTouchable(true);
        buyPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(UserxinxiActivty.this, 1f);
            }
        });
        servicetype = 2;

        studentShangmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentShangmen.setTextColor(0xfffd1245);
                teacherShangmen.setTextColor(0xff8b8b8b);
                servicetype = 2;
                coursefee = studentfee;
//                studentShangmen.setTextColor(Color.parseColor("#fd1245"));
//                teacherShangmen.setTextColor(Color.parseColor("#8b8b8b"));
                courseMoney.setText(coursefee+"元/节");
                zongfee.setText(coursefee*courseNumber+"元");

            }
        });
        teacherShangmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coursefee = teacherfee;
                teacherShangmen.setTextColor(0xfffd1245);
                studentShangmen.setTextColor(0xff8b8b8b);
                servicetype = 1;
                courseMoney.setText(coursefee+"元/节");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                courseNumber ++;
                keshi.setText(courseNumber+"");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (courseNumber > 1){
                    courseNumber --;
                }
                keshi.setText(courseNumber+"");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        descEdit.setText(content);
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserxinxiActivty.this).setTitle("学了吗提示!").setMessage("是否确定下单?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                buyPopWindow.dismiss();
                                int uid=Integer.parseInt(User_id.getUid());
                                Order_init order_init=new Order();
                                //创建订单
                                if (!TextUtils.isEmpty(descEdit.getText())) {
                                    order_init.Establish_Order(UserxinxiActivty.this,uid, teacherId, Integer.parseInt(myid), coursefee, courseNumber, Integer.parseInt(courseId), Integer.parseInt(gradeId), servicetype,
                                            User_id.getAddress(), User_id.getProvince(), User_id.getCity(), User_id.getStatus(),descEdit.getText().toString() );
                                }else {
                                    order_init.Establish_Order(UserxinxiActivty.this,uid, teacherId, Integer.parseInt(myid), coursefee, courseNumber, Integer.parseInt(courseId), Integer.parseInt(gradeId), servicetype,
                                            User_id.getAddress(), User_id.getProvince(), User_id.getCity(), User_id.getStatus(),"" );
                                }

                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserxinxiActivty.this,"再看看别的老师吧~",Toast.LENGTH_SHORT).show();
                    }
                }).show();
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

    //更新教师详细资料
    @Override
    public void Updatecontent(Map<String, Object> map) {
        jiaoyishuax.setVisibility(View.GONE);
        exper = map.get("exper")+"";
        exper_url = map.get("exper_img")+"";
        String nickname = (String) map.get("nickname");
        String resume = (String) map.get("resume");
        username = (String) map.get("username");
        signTv.setText(map.get("signature")+"");
        address = map.get("address")+"";
        diqu.setText(address);
            mobile= (String) map.get("mobile");
            String order_finish= (String) map.get("order_finish");
            String order_working= (String) map.get("order_working");
//        Glide.with(this).load((String)map.get("class_img")).into(iamgeview);
        gerxues.setText(order_working);
        dindan.setText(order_finish);
        Requitext.setText(resume);
        Requiname.setText(nickname);
        if (map.get("years") != null)
            jiaoling.setText(map.get("years")+"年");
        if (map.get("graduated_school") != null)
            techaerxue.setText(map.get("graduated_school")+"");
        techaertec.setText(map.get("speciality")+"");
//        Log.e("aa","头像地址为"+map.get("user_headimg").toString());
//        setbitmap(map.get("user_headimg").toString());
        starNumberTv.setText(map.get("order_rank").toString());
        double orderrank = Double.parseDouble(map.get("order_rank")+"");
        if (orderrank<1.5){
            starImage.setImageResource(R.drawable.one);
        }else if (orderrank >= 1.5 && orderrank <2.5){
            starImage.setImageResource(R.drawable.two);
        }else if (orderrank >= 2.5 && orderrank <3.5){
            starImage.setImageResource(R.drawable.three);
        }else if (orderrank >= 3.5 && orderrank <4.5){
            starImage.setImageResource(R.drawable.four);
        }else if (orderrank >= 4.5 ){
            starImage.setImageResource(R.drawable.five);
        }

        if (map.get("others_1")!=null) {
            xueliUrl = map.get("others_1").toString() + "";
//            Glide.with(getApplicationContext()).load(xueliUrl).into(teacherCardImage);
            teacherCardImage.setImageURI(Uri.parse(xueliUrl));
        }
        if (map.get("is_passed").equals("1")){
            educationPassTv.setTextColor(Color.parseColor("#f76d1d"));
            cardPassTv.setTextColor(Color.parseColor("#f76d1d"));
        }
    }

    //更新listview
    @Override
    public void Updatefee(List<Map<String, Object>> listmaps) {
        listmap=listmaps;
        kechengAdapter = new KechengAdapter(listmap, this);
        gerrxxedintext.setAdapter(kechengAdapter);
        setListViewHeightBasedOnChildren(gerrxxedintext);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gerxxjinl:
                if (TextUtils.isEmpty(exper)&&TextUtils.isEmpty(exper_url)) {
                    Toast.makeText(this, "暂无", Toast.LENGTH_SHORT).show();
                }else {
                    showExperPop();
                    experPop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.gerxxjiaoxueanl:
                startActivity(ExampleActivity_.intent(UserxinxiActivty.this).extra("teacherId",teacherId).get());
//                Toast.makeText(this, "暂无", Toast.LENGTH_SHORT).show();
                break;
            case R.id.teacher_card_pic:

//                if (xueliUrl != null) {
//                    Intent intent = new Intent(UserxinxiActivty.this, PictureZoo.class);
//                    intent.putExtra("hide",xueliUrl);
//                    startActivity(intent);
//                }

                break;
            case R.id.gerxxtoux:
                if (userHeadUrl != null) {
                    Intent intent2 = new Intent(UserxinxiActivty.this, PictureZoo.class);
                    intent2.putExtra("hide",userHeadUrl);
                    startActivity(intent2);
                }

                break;
            case R.id.gerxxxuexiquana:
                //跳转成交率
//                Intent intent1 = new Intent(UserxinxiActivty.this, Closing.class);
//                intent1.putExtra("Requir_id",Requir_id+"");
//                startActivity(intent1);
                break;
            case R.id.userxinxi_back:
                finish();
                break;
            case R.id.jiaoyi_jubao:
                startActivity(JubaoActivity_.intent(this).extra("teacher_id",teacherId).get());

                break;
            case R.id.grfanhui:
                UserxinxiActivty.this.finish();
                break;
            case R.id.gerxxxuexiquan:
                //跳转成交率
//                Intent intent = new Intent(UserxinxiActivty.this, Closing.class);
//                intent.putExtra("Requir_id",Requir_id+"");
//                startActivity(intent);
                break;
//            case R.id.gerjianjie:
//                //跳转个人简介
//                Intent i = new Intent(UserxinxiActivty.this, Teacher_personal.class);
//                i.putExtra("teacher_id", Requir_id + "");
//                i.putExtra("teacher_image",userHeadUrl);
//                startActivity(i);
//                break;
            case R.id.teacher_eva:
                //评价
                Intent pj = new Intent(UserxinxiActivty.this, Teacher_evaluate.class);
                pj.putExtra("teacher_id", Requir_id + "");
                startActivity(pj);
                break;
            case R.id.bohao:
                //拨号
                Log.e("aa", "拨号成功");
                Intent inte = new Intent(Intent.ACTION_DIAL);
                inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                inte.setData(Uri.parse("tel:" + mobile));
                startActivity(inte);
                break;
            case R.id.imageButton2:
                //聊天
                Intent intent = new Intent(this, ChatActivity.class);
                intent.putExtra(JGApplication.CONV_TITLE, mobile);
                intent.putExtra(JGApplication.TARGET_ID, mobile);
                intent.putExtra(JGApplication.TARGET_APP_KEY, mMyInfo.getAppKey());
                startActivity(intent);
//                Intent intent1 = new Intent(UserxinxiActivty.this, HuihuaActivity.class);
//                Intent intent2 = new Intent(UserxinxiActivty.this, ChatActivity.class);
//                intent2.putExtra(EaseConstant.EXTRA_USER_ID, mobile);
//                intent2.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
//                startActivity(intent2);
                break;
            case R.id.imageButton:
                ContactManager.sendInvitationRequest(username, null, "老师加个好友吧", new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
                            UserEntry userEntry = UserEntry.getUser(mMyInfo.getUserName(), mMyInfo.getAppKey());
                            FriendRecommendEntry entry = FriendRecommendEntry.getEntry(userEntry,
                                    username, mMyInfo.getAppKey());
                            if (null == entry) {
                                entry = new FriendRecommendEntry(username, "", "", mMyInfo.getAppKey(),
                                        "", "", "老师加个好友吧", FriendInvitation.INVITING.getValue(), userEntry, 100);
                            } else {
                                entry.state = FriendInvitation.INVITING.getValue();
                                entry.reason =  "老师加个好友吧";
                            }
                            entry.save();
                            ToastUtil.shortToast(UserxinxiActivty.this, "申请成功");
                            finish();
                        } else if (responseCode == 871317) {
                            ToastUtil.shortToast(UserxinxiActivty.this, "不能添加自己为好友");
                        } else {
                            ToastUtil.shortToast(UserxinxiActivty.this, "申请失败");
                        }
                    }
                });//                new Thread(new Runnable() {
//                    public void run() {
//                    try {
//                        //demo use a hardcode reason here, you need let user to input if you like
//                        String s = getResources().getString(R.string.Add_a_friend);
//                        EMClient.getInstance().contactManager().addContact(username, s);
//                    } catch (final Exception e) {
//
//                    }
//                    }
//                }).start();
                break;
        }
    }

        /**
         * 动态设置ListView的高度
         */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null)
            return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        if (listAdapter.getCount()!=0) {
            for (int i = 0; i < listAdapter.getCount(); i++) {

                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }


    @Override
    public void upcontent(Map<String,Object> map) {
        gerxxxuexiquan.setText((int)(Double.parseDouble(map.get("comp_rate")+"")*100)+"%");
    }

    @Override
    public void successTuijian(List<Map<String, Object>> maps) {

    }

    @Override
    public void failTuijian(String msg) {
        if (msg.equals("ok")){
            Toast.makeText(UserxinxiActivty.this,"购买课程成功",Toast.LENGTH_SHORT).show();
            new Getdata().sendMessage("有人购买了你的课程哦,快去看看吧",mobile);
            Intent intent=new Intent(UserxinxiActivty.this,MyOrderActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
