package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.ScrollListview;
import com.deguan.xuelema.androidapp.viewimpl.FlexibleScrollView;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Order_Modle.Order;
import modle.Order_Modle.Order_init;
import modle.Teacher_Modle.Teacher;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

import static com.deguan.xuelema.androidapp.R.id.imageButton;

@EActivity(R.layout.activity_demand_detail)
public class DemandDetailActivity extends MyBaseActivity implements View.OnClickListener, PayView, Xuqiuxiangx_init, Requirdetailed , SimilarXuqiuView {

    @ViewById(R.id.demand_person_back)
    TextView backTv;
    @ViewById(R.id.flexible_scroll_vew)
    FlexibleScrollView mScrollView;
    @ViewById(R.id.demand_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.demand_gender_image)
    ImageView genderImage;
    @ViewById(R.id.demand_nickname_tv)
    TextView nicknameTv;
    @ViewById(R.id.demand_sign_tv)
    TextView signTv;
    @ViewById(R.id.demand_course_tv)
    TextView courseTv;
    @ViewById(R.id.demand_grade_tv)
    TextView gradeTv;
    @ViewById(R.id.demand_fee_tv)
    TextView feeTv;
    @ViewById(R.id.demand_stats_tv)
    TextView statsTv;
    @ViewById(R.id.demand_address_tv)
    TextView addressTv;
    @ViewById(R.id.demand_distance_tv)
    TextView distanceTv;
    @ViewById(R.id.demand_gender_tv)
    TextView genderTv;
    @ViewById(R.id.demand_service_tv)
    TextView serviceTv;
    @ViewById(R.id.demand_time_tv)
    TextView timeTv;
    @ViewById(R.id.demand_content_tv)
    TextView contentTv;
    @ViewById(R.id.demand_list)
    ScrollListview listview;
    @ViewById(R.id.xuqiudianh)
    ImageButton receptBtn;
    @ViewById(R.id.xuqiuweix)
    ImageButton  imageButton;
    @ViewById(R.id.bohao)
    ImageButton  bohao;
    @ViewById(R.id.add_friend)
    ImageButton  imageButton2;

    private int fee;
    private int id;
    private int dindan;
    private int user_id;
    private int course_id ;
    private int grade_id  ;
    private String username;

    private boolean ispass = false;
    private UserInfo mMyInfo;
    private List<Map<String, Object>> listamap = new ArrayList<>();
    private XuqiuAdapter xuqiuAdapter;
    private List<XuqiuEntity> datas = new ArrayList<>();
    private String publisher_name;

    @Override
    public void before() {
        super.before();
        String  publisher_id=getIntent().getStringExtra("publisher_id");
        String uds = getIntent().getStringExtra("user_id");
        String feeva= getIntent().getStringExtra("fee");
//        Integer i
        fee = 1;
//                Integer.valueOf((Math.round(Float.parseFloat(feeva))));
//        fee=Integer.parseInt(feeva);
        id = Integer.parseInt(User_id.getUid());
        dindan = Integer.parseInt(uds);
        user_id=Integer.parseInt(publisher_id);
        course_id = Integer.parseInt(getIntent().getStringExtra("course_id"));
        grade_id = Integer.parseInt(getIntent().getStringExtra("grade_id"));

    }

    @Override
    public void initView() {
        super.initView();
//        mScrollView = (FlexibleScrollView) findViewById(R.id.flexible_scroll_vew);
        mScrollView.bindActionBar(findViewById(R.id.custom_action_bar));
        mScrollView.setHeaderView(findViewById(R.id.flexible_header_view));
        backTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        headImage.setOnClickListener(this);
        mScrollView.smoothScrollTo(0,0);
        imageButton.setOnClickListener(this);
        bohao.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        xuqiuAdapter = new XuqiuAdapter(datas,this);
        listview.setAdapter(xuqiuAdapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                startActivity(DemandDetailActivity_.intent(DemandDetailActivity.this)
                        .extra("user_id",datas.get(position).getId())
                        .extra("fee",datas.get(position).getFee())
                        .extra("publisher_id",datas.get(position).getPublisher_id())
                        .extra("course_id",datas.get(position).getCourse_id())
                        .extra("grade_id",datas.get(position).getGrade_id())
                        .get());
                finish();
//                Intent intent = new Intent(DemandDetailActivity.this, Xuqiuxiangx.class);
//                intent.putExtra("user_id",datas.get(position-1).getId());
//                intent.putExtra("fee",datas.get(position-1).getFee());
//                intent.putExtra("publisher_id",datas.get(position-1).getPublisher_id());
//                intent.putExtra("course_id",datas.get(position-1).getCourse_id());
//                intent.putExtra("grade_id",datas.get(position-1).getGrade_id());
//
//                startActivity(intent);
            }
        });
        receptBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        super.initData();
        mMyInfo = JMessageClient.getMyInfo();
        new Demand().getDemand_danyi(id,dindan,this);
        new Teacher().Get_Teacher1(Integer.parseInt(User_id.getUid()),this);
    }

    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        datas.clear();
        List<XuqiuEntity> lists = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            XuqiuEntity entity = new XuqiuEntity();
            entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
            entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setCourse_name((String) maps.get(i).get("course_name"));
            entity.setContent((String) maps.get(i).get("content"));
            entity.setCreated((String) maps.get(i).get("created"));
            entity.setId((String) maps.get(i).get("id"));
            if (maps.get(i).get("id").equals(dindan+"")) {
                continue;
            }
            entity.setProvince(maps.get(i).get("province")+"");
            entity.setCity(maps.get(i).get("city")+"");
            entity.setVersion(maps.get(i).get("teacher_version")+"");
            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setGrade_name((String)maps.get(i).get("grade_name"));
            entity.setCourse_id((String)maps.get(i).get("course_id"));
            entity.setGrade_id((String)maps.get(i).get("grade_id"));
//            entity.setAddress((String)maps.get(i).get("address"));
            if ((maps.get(i).get("address")+"").length()>7) {
                entity.setAddress(((String) maps.get(i).get("address")).substring(0, 7) + "......");
            }else {
                entity.setAddress((String) maps.get(i).get("address"));
            }
            lists.add(entity);
        }
        datas.addAll(lists);
        xuqiuAdapter.notifyDataSetChanged();
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        publisher_name = (String) map.get("publisher_name");
        String content = (String) map.get("content");
        String created = (String) map.get("created");
        String age = (String) map.get("age");
        String gender = (String) map.get("gender");
        String service_type = (String) map.get("service_type");
        String start = (String) map.get("start");
        String end = (String) map.get("end");

        String dist = map.get("distance")+"";
        double myDist = 0;
        if (!dist.equals("")){
            myDist = Double.parseDouble(dist)/1000;
        }
        DecimalFormat df = new DecimalFormat("#0.0");

        distanceTv.setText("距我"+df.format(myDist)+"km");

        course_id = Integer.parseInt((String)map.get("course_id"));
        grade_id = Integer.parseInt((String)map.get("grade_id"));
//        demand_init.getTuijianDemand_list(course_id,User_id.getUid(),User_id.getLat()+"",""+User_id.getLng(),null,null,null,listview,this,null);
        String state = "";
        if ((map.get("address")+"").length()>7) {
            state = ((String) map.get("address")).substring(0,7)+"......";
        }else {
            state = ((String) map.get("address"));
        }

        if (!TextUtils.isEmpty(map.get("ordernum").toString())){
            String ordernum =  map.get("ordernum").toString();
            statsTv.setText("已有"+ordernum+"人接取");
        }
        addressTv.setText(state+"");
        username = (String) map.get("publisher_mobile");

        headImage.setImageURI(Uri.parse(map.get("publisher_headimg")+""));

        String desc=start+" - "+end;
        if (map.get("publisher_gender").equals("2")||map.get("publisher_gender").equals("女")){
            genderImage.setImageResource(R.mipmap.gender_female_icon);
        }else {
            genderImage.setImageResource(R.mipmap.teacher_male);
        }
        signTv.setText(map.get("publisher_signature")+"");

        if (gender.equals("2")||gender.equals("女")) {
            genderTv.setText("教师性别： 女");
        }else {
            genderTv.setText("教师性别： 男");
        }

//        if (service_type.equals("1")) {
//            serviceTv.setText("教师上门");
//        }else {
//            serviceTv.setText("学生上门");
//        }
        feeTv.setText(map.get("low_price")+"-"+map.get("high_price")+"元/小时");
        serviceTv.setText("服务方式："+(String)map.get("service_type_txt"));
        timeTv.setText(created);
        contentTv.setText(content);
        nicknameTv.setText(publisher_name);
        courseTv.setText(map.get("course_name")+"");
        gradeTv.setText(map.get("teacher_version")+"    "+map.get("grade_name")+"");

        new Demand(this).getTuijianDemand_list(course_id,User_id.getUid(),User_id.getLat()+"",""+User_id.getLng(),null,null,null,null,this,null);

    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=listmap.get(0);

        if (map.get("tosa") .equals("创建订单成功")){
            new Getdata().sendMessage("您的需求已被"+User_id.getNickName()+"接取",username);
        }
        Toast.makeText(DemandDetailActivity.this,(String)map.get("tosa"),Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xuqiuimage:


                break;
            case  R.id.bohao:
                if (ispass) {
                    //拨号
                    Log.e("aa", "拨号成功");
                    Intent inte = new Intent(Intent.ACTION_DIAL);
                    inte.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    inte.setData(Uri.parse("tel:" + username));
                    startActivity(inte);
                }else {
                    Toast.makeText(this, "请完善教师管理信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.add_friend:

                if (ispass) {
                    ContactManager.sendInvitationRequest(username, null, "我对你的需求有兴趣，加个好友吧", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                UserEntry userEntry = UserEntry.getUser(mMyInfo.getUserName(), mMyInfo.getAppKey());
                                FriendRecommendEntry entry = FriendRecommendEntry.getEntry(userEntry,
                                        username, mMyInfo.getAppKey());
                                if (null == entry) {
                                    entry = new FriendRecommendEntry(username, "", "", mMyInfo.getAppKey(),
                                            "", "", "我对你的需求有兴趣，加个好友吧", FriendInvitation.INVITING.getValue(), userEntry, 100);
                                } else {
                                    entry.state = FriendInvitation.INVITING.getValue();
                                    entry.reason =  "我对你的需求有兴趣，加个好友吧";
                                }
                                entry.save();
                                ToastUtil.shortToast(DemandDetailActivity.this, "申请成功");
                                finish();
                            } else if (responseCode == 871317) {
                                ToastUtil.shortToast(DemandDetailActivity.this, "不能添加自己为好友");
                            } else {
                                ToastUtil.shortToast(DemandDetailActivity.this, "申请失败");
                            }
                        }
                    });
//                    Toast.makeText(this, "已发送好友申请", Toast.LENGTH_SHORT).show();
//                    new Thread(new Runnable() {
//                        public void run() {
//                            try {
//                                //demo use a hardcode reason here, you need let user to input if you like
//                                String s = getResources().getString(R.string.Add_a_friend);
//                                EMClient.getInstance().contactManager().addContact(username, s);
//                            } catch (final Exception e) {
//
//                            }
//                        }
//                    }).start();
                }else {
                    Toast.makeText(this, "请完善教师管理信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.xuqiufanhui:
                finish();
                break;
            case R.id.xuqiuweix:
                if (ispass) {
                    Intent intent = new Intent(this, ChatActivity.class);
                    intent.putExtra(JGApplication.CONV_TITLE, publisher_name);
                    intent.putExtra(JGApplication.TARGET_ID, username);
                    intent.putExtra(JGApplication.TARGET_APP_KEY, mMyInfo.getAppKey());
                    startActivity(intent);
                    //聊天
//                Intent intent1=new Intent(Xuqiuxiangx.this, HuihuaActivity.class);
//                    Intent intent1 = new Intent(Xuqiuxiangx.this, ChatActivity.class);
//                    intent1.putExtra(EaseConstant.EXTRA_USER_ID, username);
//                    intent1.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
//                    startActivity(intent1);
                }else {
                    Toast.makeText(this, "请完善教师管理信息等待审核通过", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.xuqiudianh:
                if (ispass) {
                    new AlertDialog.Builder(DemandDetailActivity.this).setTitle("学习吧提示!").setMessage("是否确定接取需求?")
                            .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Order_init order_init = new Order();
                                    order_init.CreateOrder(user_id, id, dindan, fee, course_id, grade_id, DemandDetailActivity.this, User_id.getAddress(), User_id.getLat(), User_id.getLng());
                                    Intent intent = NewMainActivity_.intent(DemandDetailActivity.this).get();
                                    startActivity(intent);
                                }
                            }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(DemandDetailActivity.this, "再去看看别的需求吧~", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                }else {
                    Toast.makeText(this, "请完善教师管理信息等待审核通过,并发布相应的课程才能接单哦", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void successPay(Map<String, Object> map) {
        if (map.get("is_passed").equals("1")){
            ispass = true;
        }
    }

    @Override
    public void failPay(String msg) {

    }


}
