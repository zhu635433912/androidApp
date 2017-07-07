package com.deguan.xuelema.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.GlideCircleTransform;
import com.deguan.xuelema.androidapp.viewimpl.Baseinit;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
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

import modle.Adapter.KechengAdapter;
import modle.Huanxing.ui.ChatActivity;
import modle.Increase_course.Increase_course;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.toos.CircleImageView;
import modle.user_ziliao.User_id;

/**
 * 老师个人信息
 */

public class UserxinxiActivty extends AutoLayoutActivity implements Requirdetailed,View.OnClickListener,Baseinit {
    private TextView Requiname;
    private TextView Requitext;
    private TextView Teachergerjianjie;
    private TextView pingjia;
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
    private CircleImageView gerxxtoux;
    private AVLoadingIndicatorView jiaoyishuax;
    private String userHeadUrl = "";
    private TextView jubaoTv;
    private ImageView iamgeview;
    private RelativeLayout gerxxTob;
    private PopupWindow buyPopWindow;
    private TextView courseMoney;
    private TextView zongfee;
    private TextView keshi;
    private Button buyBtn;
    private TextView courseExplain;
    private TextView courseType;
    private TextView courseNametV;
    private String myid;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiaoyi);
        User_id.getInstance().addActivity(this);
        jubaoTv = (TextView) findViewById(R.id.jiaoyi_jubao);
        imageButton= (ImageButton) findViewById(R.id.imageButton);
        gerxues= (TextView) findViewById(R.id.gerxues);
        dindan= (TextView) findViewById(R.id.dindan);
        Requiname= (TextView) findViewById(R.id.gerxxname);
        Requitext= (TextView) findViewById(R.id.gerxxneirong);
        Teachergerjianjie= (TextView) findViewById(R.id.gerjianjie);
        pingjia= (TextView) findViewById(R.id.pingjia);
        grfanhui= (RelativeLayout) findViewById(R.id.grfanhui);
        gerxxxuexiquan= (TextView) findViewById(R.id.gerxxxuexiquan);
        jiaoyi= (RelativeLayout) findViewById(R.id.jiaoyi);
        gerrxxedintext= (ListView) findViewById(R.id.gerrxxedintext);
        bohao= (ImageButton) findViewById(R.id.bohao);
        imageButton2= (ImageButton) findViewById(R.id.imageButton2);
        gerxxtoux= (CircleImageView) findViewById(R.id.gerxxtoux);
        jiaoyishuax= (AVLoadingIndicatorView) findViewById(R.id.jiaoyishuax);
        iamgeview= (ImageView) findViewById(R.id.curr_backe);
        gerxxTob= (RelativeLayout) findViewById(R.id.gerxxTob);

        gerxxTob.bringToFront();
        jiaoyishuax.bringToFront();
        gerxxxuexiquan.bringToFront();
        jiaoyi.bringToFront();
        grfanhui.bringToFront();

        jubaoTv.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        bohao.setOnClickListener(this);
        pingjia.setOnClickListener(this);
        Teachergerjianjie.setOnClickListener(this);
        gerxxxuexiquan.setOnClickListener(this);
        grfanhui.setOnClickListener(this);

        address = User_id.getAddress();
        myid = getIntent().getStringExtra("myid");
        final String user_id=getIntent().getStringExtra("user_id");
        Log.e("aa","UserxinActivyt接收到老师id为"+user_id);
        int id=Integer.parseInt(User_id.getUid());
        Requir_id=Integer.parseInt(user_id);
        teacherId = Requir_id;
        userHeadUrl = getIntent().getStringExtra("head_image");
        if (!userHeadUrl.equals(""))
            Glide.with(this).load(userHeadUrl).transform(new GlideCircleTransform(this)).into(gerxxtoux);

        //加载数据
        Teacher_init teacher=new Teacher();
        teacher.Get_Teacher_detailed(id,Requir_id,this,0);

        //获取成交率
        Getdata getdata=new Getdata();
        getdata.getdelt_info(Requir_id,this);

        //加载课程数据
        Increase_course increaseCourse=new Increase_course();
        increaseCourse.selecouse(Requir_id,this,null);

        showBuyPop();

        gerrxxedintext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                courseMoney.setText(coursefee+"元/节");
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
                buyPopWindow.showAtLocation(imageButton2, Gravity.BOTTOM,0,0);

            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            jiaoyishuax.setVisibility(View.GONE);
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private TextView studentShangmen,teacherShangmen;
    private int coursefee,teacherId,courseNumber,servicetype,studentfee,teacherfee;
    private String courseId,gradeId,serviceType,courseName;
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
        studentShangmen.setTextColor(Color.parseColor("#fd1245"));
        buyPopWindow = new PopupWindow(view);
        buyPopWindow.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        buyPopWindow.setWidth(width);
        buyPopWindow.setHeight(height/3);
        buyPopWindow.setBackgroundDrawable(new BitmapDrawable());
        buyPopWindow.setOutsideTouchable(true);
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
                courseNumber --;
                keshi.setText(courseNumber+"");
                zongfee.setText(coursefee*courseNumber+"元");
            }
        });
        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(UserxinxiActivty.this).setTitle("学了么提示!").setMessage("是否确定下单?")
                        .setPositiveButton("是", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int uid=Integer.parseInt(User_id.getUid());
                                Order_init order_init=new Order();
                                //创建订单
                                order_init.Establish_Order(uid,teacherId,Integer.parseInt(myid),coursefee,courseNumber,Integer.parseInt(courseId),Integer.parseInt(gradeId),servicetype,User_id.getAddress());
                                Toast.makeText(UserxinxiActivty.this,"购买课程成功",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(UserxinxiActivty.this,MyOrderActivity.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(UserxinxiActivty.this,"在看看别的老师吧~",Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });

    }

    //更新教师详细资料
    @Override
    public void Updatecontent(Map<String, Object> map) {
        String nickname = (String) map.get("nickname");
        String resume = (String) map.get("resume");
        username = (String) map.get("username");

        address = map.get("address")+"";
            mobile= (String) map.get("mobile");
            String order_finish= (String) map.get("order_finish");
            String order_working= (String) map.get("order_working");
//        Glide.with(this).load((String)map.get("class_img")).into(iamgeview);
        gerxues.setText(order_working);
        dindan.setText(order_finish);
        Requitext.setText(resume);
        Requiname.setText(nickname);

        Log.e("aa","头像地址为"+map.get("user_headimg").toString());
//        setbitmap(map.get("user_headimg").toString());

    }

    //更新listview
    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        this.listmap=listmap;
        kechengAdapter = new KechengAdapter(listmap, this);
        gerrxxedintext.setAdapter(kechengAdapter);
        setListViewHeightBasedOnChildren(gerrxxedintext);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jiaoyi_jubao:
                startActivity(JubaoActivity_.intent(this).extra("teacher_id","612").get());

                break;
            case R.id.grfanhui:
                UserxinxiActivty.this.finish();
                break;
            case R.id.gerxxxuexiquan:
                //跳转成交率
                Intent intent = new Intent(UserxinxiActivty.this, Closing.class);
                intent.putExtra("Requir_id",Requir_id+"");
                startActivity(intent);
                break;
            case R.id.gerjianjie:
                //跳转个人简介
                Intent i = new Intent(UserxinxiActivty.this, Teacher_personal.class);
                i.putExtra("teacher_id", Requir_id + "");
                i.putExtra("teacher_image",userHeadUrl);
                startActivity(i);
                break;
            case R.id.pingjia:
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
//                Intent intent1 = new Intent(UserxinxiActivty.this, HuihuaActivity.class);
                Intent intent1 = new Intent(UserxinxiActivty.this, ChatActivity.class);
                intent1.putExtra(EaseConstant.EXTRA_USER_ID, username);
                intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
                startActivity(intent1);
                break;
            case R.id.imageButton:
                Toast.makeText(this, "已发送好友申请", Toast.LENGTH_SHORT).show();
                new Thread(new Runnable() {
                    public void run() {
                    try {
                        //demo use a hardcode reason here, you need let user to input if you like
                        String s = getResources().getString(R.string.Add_a_friend);
                        EMClient.getInstance().contactManager().addContact(username, s);
                    } catch (final Exception e) {

                    }
                    }
                }).start();
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



    public void setbitmap(final String pate){
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                //从网络上获取图片
                final Bitmap bitmap=getPicture(pate);
                //发送一个Runnable对象
                gerxxtoux.post(new Runnable(){

                    @Override
                    public void run() {
                        gerxxtoux.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                        jiaoyishuax.setVisibility(View.GONE);
                    }

                });

            }
        }).start();//开启线程
    }
    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param path
     * @return Bitmap
     * */
    public Bitmap getPicture(String path){
        Bitmap bm=null;
        URL url;
        try {
            url = new URL(path);//创建URL对象
            URLConnection conn=url.openConnection();//获取URL对象对应的连接
            conn.connect();//打开连接
            InputStream is=conn.getInputStream();//获取输入流对象
            bm= BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
        } catch (MalformedURLException e1) {
            e1.printStackTrace();//输出异常信息
        }catch (IOException e) {
            e.printStackTrace();//输出异常信息
        }
        return bm;
    }

    @Override
    public void upcontent(Map<String,Object> map) {
        gerxxxuexiquan.setText(map.get("comp_rate")+"");
    }
}
