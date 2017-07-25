package com.deguan.xuelema.androidapp;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.zhy.autolayout.AutoLayoutActivity;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.co.namee.permissiongen.PermissionGen;
import modle.Adapter.KechengAdapter;
import modle.Adapter.MyGridView_Adapter;
import modle.Increase_course.Increase_course;
import modle.Teacher_Modle.Teacher;
import modle.Teacher_Modle.Teacher_init;
import modle.getdata.Getdata;
import modle.user_Modle.User_Realization;
import modle.user_Modle.User_init;
import modle.user_ziliao.User_id;

/**
 * 教师管理
 */

public class Teacher_management extends AutoLayoutActivity implements View.OnClickListener,Requirdetailed,Student_init, TurnoverView {
    private TextView zhonglei;
    private RelativeLayout jiaoshiguanlifanhui;
    private TextView kemuzhonglei;
    private TextView editText2;
    private EditText shanggmenfee;
    private EditText xueshengfee;
    private Button naxt,educationBtn,bookBtn;
    private int kcid=206;
    private EditText gerjianjietext_edi;
    private TextView techang;
    private EditText biyexuex;
    private Button baocunjiaoshi;
    private Teacher_init teacher_init;
    private RelativeLayout tianjiaxueli1;
    private int uid;
    private android.app.AlertDialog mPickDialog;
    private AlertDialog zhengshuDialog,shenfenDialog;
    private String mCurrentPhotoPath;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private RelativeLayout tianjiazhengshu;
    private RelativeLayout viw;
    private RelativeLayout tianjiaxueli;
    private ListView kechengitme;
    private LinearLayout xuelimage;
    private String others_1;
    private String others_2;
    private String others_3;
    private String others_4;
    private String others_5;
    private String others_6;
    private LinearLayout rongyuimage;
    private int TAGE_ISRONT;
    private File image;
    private KechengAdapter kechengAdapter;
    private Increase_course increase_course;
    private TextView serviceTv;
    private int kechengType = 1;
//    private EditText signEdit;
    private int flag = 1;
//    private ImageView classImage;
//    private TextView classImageChange;
    private ImageView xueliImage1,xueliImage2,rongyuImage1,rongyuImage2;
    private int grade_id;
    private EditText educationEdit;
    private TextView teacherYearEdit;
    private RelativeLayout tianjiasfenz;
    private ImageView postuserimage1;
    private ImageView postuserimage2;
    private TextView kechenfengmian;
    private boolean ispass = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teachers_management);
        User_id.getInstance().addActivity(this);

        educationBtn = (Button) findViewById(R.id.tianjiaxuelitubiao);
        bookBtn = (Button) findViewById(R.id.tianjiarongyuztubiao);
        educationEdit = (EditText) findViewById(R.id.education_et);
        teacherYearEdit = (TextView) findViewById(R.id.teacher_year_et);
        xueliImage1 = (ImageView) findViewById(R.id.xueli_image1);
        xueliImage2 = (ImageView) findViewById(R.id.xueli_image2);
        rongyuImage1 = (ImageView) findViewById(R.id.rongyu_image1);
        rongyuImage2 = (ImageView) findViewById(R.id.rongyu_image2);
//        classImageChange = (TextView) findViewById(R.id.class_image_change);
//        classImage = (ImageView) findViewById(R.id.class_image);
//        signEdit = (EditText) findViewById(R.id.person_sign);
        rongyuimage= (LinearLayout) findViewById(R.id.rongyuimage);
        xuelimage= (LinearLayout) findViewById(R.id.xueliimage);
        jiaoshiguanlifanhui= (RelativeLayout) findViewById(R.id.jiaoshiguanlifanhui);
        zhonglei= (TextView) findViewById(R.id.kechengzhonglei);
        kemuzhonglei= (TextView) findViewById(R.id.textView7);
        editText2= (TextView) findViewById(R.id.editText2);
        shanggmenfee= (EditText) findViewById(R.id.shanggmenfee);
        xueshengfee= (EditText) findViewById(R.id.xueshengfee);
        tianjiaxueli1= (RelativeLayout) findViewById(R.id.tianjiaxueli1);
        tianjiazhengshu= (RelativeLayout) findViewById(R.id.tianjiazhengshu);
        gerjianjietext_edi= (EditText) findViewById(R.id.gerjianjietext_edi);
        naxt= (Button) findViewById(R.id.naxt);
        techang= (TextView) findViewById(R.id.techang);
        biyexuex= (EditText) findViewById(R.id.biyexuex);
        viw= (RelativeLayout) findViewById(R.id.viw);
        baocunjiaoshi= (Button) findViewById(R.id.baocunjiaoshi);
        tianjiaxueli= (RelativeLayout) findViewById(R.id.tianjiaxueli);
        kechengitme= (ListView) findViewById(R.id.kechengitme);
        tianjiasfenz= (RelativeLayout) findViewById(R.id.tianjiasfenz);
        postuserimage1= (ImageView) findViewById(R.id.postuserimage1);
        postuserimage2= (ImageView) findViewById(R.id.postuserimage2);
        jiaoshiguanlifanhui.bringToFront();
//        serviceTv = (TextView) findViewById(R.id.kecheng_service_type);
        tianjiasfenz.setOnClickListener(this);
//        classImageChange.setOnClickListener(this);
//        serviceTv.setOnClickListener(this);
        tianjiaxueli.setOnClickListener(this);
        tianjiazhengshu.setOnClickListener(this);
        tianjiaxueli1.setOnClickListener(this);
        baocunjiaoshi.setOnClickListener(this);
        naxt.setOnClickListener(this);
        xueshengfee.setOnClickListener(this);
        shanggmenfee.setOnClickListener(this);
        editText2.setOnClickListener(this);
        kemuzhonglei.setOnClickListener(this);
        zhonglei.setOnClickListener(this);
        jiaoshiguanlifanhui.setOnClickListener(this);
        educationEdit.setOnClickListener(this);
        teacherYearEdit.setOnClickListener(this);
        techang.setOnClickListener(this);
        educationBtn.setOnClickListener(this);
        bookBtn.setOnClickListener(this);

        //去获取教师详细资料
        uid=Integer.parseInt(User_id.getUid());
        teacher_init=new Teacher();
        teacher_init.Get_Teacher_detailed(628,uid,this,1);

        View view = getLayoutInflater().inflate(R.layout.layout_dialog_pick, null);
        mPickDialog = new android.app.AlertDialog.Builder(this).setView(view).create();

        View view2 = getLayoutInflater().inflate(R.layout.dialog_zhengshu,null);
        zhengshuDialog = new AlertDialog.Builder(this).setView(view2).create();

        View view5 = getLayoutInflater().inflate(R.layout.dialog_shenfen,null);
        shenfenDialog = new AlertDialog.Builder(this).setView(view5).create();

        //获取课程
        getmCourse();

        //删除课程
        kechengitme.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int course_id=kechengAdapter.getcourse_id(i);
                Log.e("aa","删除"+course_id);
                new AlertDialog.Builder(Teacher_management.this).setTitle("学了么提示!").setMessage("确定删除课程吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                increase_course.Delect(uid,course_id,Teacher_management.this);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });


    }
    public void getmCourse(){
        //获取教师自己课程
        increase_course=new Increase_course();
        increase_course.selecouse(uid,null,this);
    }
    private int a = 0;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.teacher_year_et:

                AlertDialog.Builder serviceTypeDialog = new AlertDialog.Builder(Teacher_management.this);
                serviceTypeDialog.setIcon(R.drawable.add04);
                serviceTypeDialog.setTitle("请选择服务类型");
                //    指定下拉列表的显示数据
                final String[] fuwuType = {"1年", "2年", "3年" ,"4年", "5年","6年","7年" ,"8年", "9年","10年",
                        "11年", "12年", "13年" ,"14年", "15年","16年","17年" ,"18年", "19年","20年",
                        "21年", "22年", "23年" ,"24年", "25年","26年","27年" ,"28年", "29年","30年",
                        "31年", "32年", "33年" ,"34年", "35年","36年","37年" ,"38年", "39年","40年",
                        "41年", "42年", "43年" ,"44年", "45年","46年","47年" ,"48年", "49年","50年"

                };
                //    设置一个下拉的列表选择项
                serviceTypeDialog.setItems(fuwuType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        teacherYearEdit.setText(fuwuType[which]);
//                        int years=Integer.parseInt(edit.getText().toString());
                        teacher_init.Teacher_years(uid,which+1);
                    }
                });
                serviceTypeDialog.show();
                break;
            case R.id.techang:
                AlertDialog.Builder xueleTypeDialog = new AlertDialog.Builder(Teacher_management.this);
                xueleTypeDialog.setIcon(R.drawable.add04);
                xueleTypeDialog.setTitle("请选择学历");
                //    指定下拉列表的显示数据
                final String[] xueliType = {"无","大专", "本科","研究生", "硕士" ,"博士"};
                //    设置一个下拉的列表选择项
                xueleTypeDialog.setItems(xueliType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        techang.setText(xueliType[which]);
//                        int years=Integer.parseInt(edit.getText().toString());
//                        teacher.Teacher_years(uid,which+1);
//                        education_id = which;
                        new User_Realization().UpdateEducation(uid,which);
                    }
                });
                xueleTypeDialog.show();


                break;
//            case R.id.class_image_change:
//                //添加课程图片
//                TAGE_ISRONT=3;
//                mPickDialog.show();
//
//                break;
            case R.id.jiaoshiguanlifanhui:
                Teacher_management.this.finish();
                break;
            case R.id.kechengzhonglei:
                //获取数据
//                Getdata getdata=new Getdata(); //课程种类
                final AlertDialog.Builder subjectDialog = new AlertDialog.Builder(Teacher_management.this);
                subjectDialog.setIcon(R.drawable.add04);
                subjectDialog.setTitle("请选择一个科目");
                //    指定下拉列表的显示数据
                final String[] subjects = new String[SubjectUtil.getSubjectList().size()];
                for (int i = 0; i < subjects.length ; i++) {
                    subjects[i] = SubjectUtil.getSubjectList().get(i).getSubjectName();
                }
                //    设置一个下拉的列表选择项
                subjectDialog.setItems(subjects, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Teacher_management.this, "选择的科目为：" + SubjectUtil.getSubjectList().get(which).getSubjectName(), Toast.LENGTH_SHORT).show();
                        zhonglei.setText(SubjectUtil.getSubjectList().get(which).getSubjectName());
                        kcid = Integer.parseInt(SubjectUtil.getSubjectList().get(which).getSubjectId());
                    }
                });
                subjectDialog.show();
//                getdata.getGrade(this);
                break;
            case R.id.editText2:
                //服务类型

                final String[] sex1={"一对一","一对多","一对一/一对多"};
                AlertDialog.Builder rolage1=new AlertDialog.Builder(Teacher_management.this);
                rolage1.setIcon(android.R.drawable.btn_star);
                rolage1.setTitle("选择类型!");
                rolage1.setSingleChoiceItems(sex1, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        a = which + 1;
                    }
                });
                rolage1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( a == 0 || a == 2) {
                            editText2.setText(sex1[1]);
                        }else if (a == 1){
                            editText2.setText(sex1[0]);
                        }else {
                            editText2.setText(sex1[2]);
                        }
                    }
                });
                rolage1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                rolage1.show();
                break;
//            case R.id.kecheng_service_type:
//
//                AlertDialog.Builder serviceTypeDialog = new AlertDialog.Builder(Teacher_management.this);
//                serviceTypeDialog.setIcon(R.drawable.add04);
//                serviceTypeDialog.setTitle("请选择服务类型");
//                //    指定下拉列表的显示数据
//                final String[] fuwuType = {"一对一", "一对多", "学生上门" ,"老师上门", "第三方","不限"};
//                //    设置一个下拉的列表选择项
//                serviceTypeDialog.setItems(fuwuType, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(Teacher_management.this, "选择的科目为：" + fuwuType[which], Toast.LENGTH_SHORT).show();
//                        serviceTv.setText(fuwuType[which]);
//                        kechengType = which + 1;
//                    }
//                });
//                serviceTypeDialog.show();
//
//                break;
            case R.id.textView7:

//                科目
                AlertDialog.Builder kemuza = new AlertDialog.Builder(Teacher_management.this);
                kemuza.setIcon(R.drawable.add04);
                kemuza.setTitle("请选择一个年级");
                //    指定下拉列表的显示数据
                final String[] kemucities = {"小学", "初中", "高中","大学"
//                        ,                        "五年级", "六年级","初一", "初二", "初三"
                };
                //    设置一个下拉的列表选择项
                kemuza.setItems(kemucities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Teacher_management.this, "选择的科目为：" + kemucities[which], Toast.LENGTH_SHORT).show();
                        kemuzhonglei.setText(kemucities[which]);
                        grade_id = which + 1;
                    }
                });
                kemuza.show();
                break;
            case R.id.naxt:
                //增加课程
                if (zhonglei.getText().toString().equals("课程种类")||kemuzhonglei.getText().toString().equals("选择年级")
                        ||editText2.getText().toString().equals("课程说明")||shanggmenfee.getText().toString().equals("课时费")
//                        ||serviceTv.getText().toString().equals("服务类型")
                        ||xueshengfee.getText().toString().equals("")
                        ||shanggmenfee.getText().toString().equals("")){
                    Toast.makeText(Teacher_management.this,"请填写正确的课程资料",Toast.LENGTH_SHORT).show();

                }else {
                    new AlertDialog.Builder(Teacher_management.this).setTitle("学了么提示!").setMessage("确定发布课程吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //增加课程
                                    int uid=Integer.parseInt(User_id.getUid());
                                    Increase_course inc=new Increase_course();
                                    int laoshifee=Integer.parseInt(shanggmenfee.getText().toString());
                                    int xuesfee=Integer.parseInt(xueshengfee.getText().toString());
//                                         int xuesfee = 0;
                                    if (ispass) {
                                        inc.Addcourse(uid, kcid, editText2.getText().toString(), laoshifee, xuesfee, 6, grade_id, Teacher_management.this);

                                        Toast.makeText(Teacher_management.this, "增加课程成功", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Teacher_management.this, "请完善信息等待审核通过", Toast.LENGTH_SHORT).show();
                                    }
                                    //刷新课程
//                                    getmCourse();
                                    viw.setVisibility(View.GONE);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                break;
            case R.id.baocunjiaoshi:
                String dei=gerjianjietext_edi.getText().toString();
                String tec=educationEdit.getText().toString();
                String biy=biyexuex.getText().toString();
//                String signText = signEdit.getText().toString()+"";

                if (dei.equals("")||tec.equals("")||biy.equals("")){
                    Toast.makeText(Teacher_management.this,"个人介绍学历毕业学校不能为空!",Toast.LENGTH_LONG).show();
                }else {
                    teacher_init.Teacher_resume(uid,dei);
                    teacher_init.Teacher_speciality(uid,tec);
                    teacher_init.Teacher_graduated_school(uid,biy);
//                    teacher_init.Teacher_signature(uid,signText);
                    Toast.makeText(Teacher_management.this,"更新个人信息成功!",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.tianjiarongyuztubiao:
                //添加荣誉证书图片
                TAGE_ISRONT=2;
//                Toast.makeText(this, "暂不用", Toast.LENGTH_SHORT).show();
                mPickDialog.show();
                break;
            case R.id.tianjiazhengshu:
                //添加荣誉证书图片
                TAGE_ISRONT=2;
//                Toast.makeText(this, "暂不用", Toast.LENGTH_SHORT).show();
                mPickDialog.show();
                break;
            case R.id.tianjiaxuelitubiao:
                //添加学历图片
                TAGE_ISRONT=1;
                mPickDialog.show();
                break;
            case R.id.tianjiaxueli1:
                //添加学历图片
                TAGE_ISRONT=1;
                mPickDialog.show();
                break;
            case R.id.zhengshu_dialog_pick:

                flag = 1 ;
                new User_Realization().setuserbitmap(image,this);
                zhengshuDialog.dismiss();
                break;
            case R.id.zhengshu2_dialog_pick:
                flag = 2;
                new User_Realization().setuserbitmap(image,this);
                zhengshuDialog.dismiss();
                break;
            case R.id.shenfen_dialog_pick:
                flag = 1;
                new User_Realization().setuserbitmap(image,this);
                shenfenDialog.dismiss();
                break;
            case R.id.shenfen2_dialog_pick:
                flag = 2;
                new User_Realization().setuserbitmap(image,this);
                shenfenDialog.dismiss();
                break;
            case R.id.picture_dialog_pick: {
                selectImage();
                mPickDialog.dismiss();
            }
            break;
            case R.id.camera_dialog_pick: {
                if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                    PermissionGen.with(this)
                            .addRequestCode(100)
                            .permissions(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.CAMERA
                            )
                            .request();
//
                }else{
//  定位
                    dispatchTakePictureIntent();
                }
//                dispatchTakePictureIntent();
                mPickDialog.dismiss();
            }
            break;
            case R.id.tianjiaxueli:

                if (viw.getVisibility()==View.GONE) {
                    viw.setVisibility(View.VISIBLE);
                }else {
                    viw.setVisibility(View.GONE);
                }
                break;
            case R.id.tianjiasfenz:
                TAGE_ISRONT=4;
                mPickDialog.show();
                break;
            case R.id.xueli_image1:
                setIntentimage(others_1);
                break;
            case R.id.xueli_image2:
                setIntentimage(others_2);
                break;
            case R.id.rongyu_image1:
                setIntentimage(others_3);
                break;
            case R.id.rongyu_image2:
                setIntentimage(others_4);
                break;
            case R.id.postuserimage1:
                setIntentimage(others_5);
                break;
            case R.id.postuserimage2:
                setIntentimage(others_6);
                break;
        }
    }
    public void setIntentimage(String hdie){
        Intent intent=new Intent(this,PictureZoo.class);
        intent.putExtra("hide",hdie);
        startActivity(intent);
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        //课程种类
        AlertDialog.Builder kemu = new AlertDialog.Builder(Teacher_management.this);
        kemu.setIcon(R.drawable.add04);
        kemu.setTitle("请选择一个年级");
        //    指定下拉列表的显示数据
         final String[] cities = new String[79];
        for (int i=0;i<map.size()+1;i++) {
            int z=206+i;
           String a= (String) map.get(z+"");
            if (z==285){
                break;
            }
            cities[i]=a;
        }
        //    设置一个下拉的列表选择项
     kemu.setItems(cities, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(Teacher_management.this, "选择的科目为：" + cities[which], Toast.LENGTH_SHORT).show();
                zhonglei.setText(cities[which]);
                kcid=kcid+which;
            }
        });
        kemu.show();
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {
        Map<String,Object> map=new HashMap<String,Object>();
        map=listmap.get(0);
//        signEdit.setText(map.get("signature")+"");
        educationEdit.setText(map.get("speciality")+"");
        if (map.get("is_passed").equals("1")){
            ispass = true;
        }
        if (map.get("graduated_school")!=null)
            biyexuex.setText(map.get("graduated_school")+"");
        if (map.get("resume") != null)
            gerjianjietext_edi.setText(map.get("resume")+"");
        others_1=map.get("others_1")+"";
        others_2=map.get("others_2")+"";
        others_3=map.get("others_3")+"";
        others_4=map.get("others_4")+"";
        others_5=map.get("others_5")+"";
        others_6=map.get("others_6")+"";
        if (map.get("education")!=null)
            techang.setText(map.get("education")+"");
        if (map.get("years") != null)
            teacherYearEdit.setText(map.get("years")+"年");
        String classimage = map.get("class_img")+"";
        if (classimage != null){
//            Glide.with(this).load(classimage).into(classImage);
        }
        Glide.with(this).load(map.get("others_5")).into(postuserimage1);
        Glide.with(this).load(map.get("others_6")).into(postuserimage2);

        //判断服务器返回是否由照片
       if (others_1!=null&&others_2!=null){
//        String[] ot1={others_1,others_2};
//           setbitmap(ot1);/
           Glide.with(this).load(others_1).into(xueliImage1);
           Glide.with(this).load(others_2).into(xueliImage2);
       }else if (others_1!=null){
//           String[] ot1={others_1};
//           setbitmap(ot1);
           Glide.with(this).load(others_1).into(xueliImage1);
       }else {
//           String[] ot1={others_2};
//           setbitmap(ot1);
           Glide.with(this).load(others_2).into(xueliImage2);
       }
//        判断服务器返回是否有照片
        if (others_3!=null&&others_4!=null){
            Glide.with(this).load(others_3).into(rongyuImage1);
            Glide.with(this).load(others_4).into(rongyuImage2);
        }else if (others_3!=null){
            Glide.with(this).load(others_3).into(rongyuImage1);
        }else {
            Glide.with(this).load(others_4).into(rongyuImage2);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            String filePath = null;
            //判断是哪一个的回调
            if (requestCode == REQUEST_IMAGE_GET) {
                //返回的是content://的样式
                filePath = getFilePathFromContentUri(data.getData(), this);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    filePath = mCurrentPhotoPath;
                }
            }
            //图片设置问题
            if (!TextUtils.isEmpty(filePath)) {
                //文件路劲是有的
                // 自定义大小，防止OOM
                Bitmap bitmap = getSmallBitmap(filePath, 600, 600);
                //获取图片
                //image空
                Log.e("aa","路劲为"+filePath);
                User_init user_init=new User_Realization();
                image=new File(filePath);
                if (TAGE_ISRONT==1){
                    zhengshuDialog.show();
//                    user_init.setuserbitmap(image,this);
                }else if(TAGE_ISRONT == 3){
                    new User_Realization().setuserbitmap(image,this);
                }else if (TAGE_ISRONT == 2){
                    zhengshuDialog.show();
                }else if (TAGE_ISRONT==4){
                    shenfenDialog.show();
                }



            }
        }
    }

    /**
     * 从相册中获取
     */
    public void selectImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        //判断系统中是否有处理该Intent的Activity
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(intent, REQUEST_IMAGE_GET);
//        } else {
//            showToast("未找到图片查看器");
//        }
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

        } else {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_IMAGE_GET);

    }


    /**
     * 创建新文件
     * @return
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyy").format(new Date());
        String imageFileName = "" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(imageFileName,  /* 文件名 */
                ".jpg",         /* 后缀 */
                storageDir      /* 路径 */

        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;

    }

    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断系统中是否有处理该Intent的Activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            // 创建文件来保存拍的照片
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // 异常处理
            }
            if (photoFile != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
            }
        } else {
            showToast("无法启动相机");
        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param uri     content:// 样式
     * @param context
     * @return real file path
     */
    public static String getFilePathFromContentUri(Uri uri, Context context) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    /**
     * 获取小图片，防止OOM
     *
     * @param filePath
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeFile(filePath, options);
        } catch (Exception e) {
            e.printStackTrace();
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片缩放比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {

        kechengAdapter = new KechengAdapter(listmap, this);
        kechengitme.setAdapter(kechengAdapter);
        //动态设置高度
        setListViewHeightBasedOnChildren(kechengitme);

        kemuzhonglei.setOverScrollMode(View.OVER_SCROLL_NEVER);
        kemuzhonglei.setVerticalScrollBarEnabled(false);


    }



    @Override
    public void setListview1(List<Map<String, Object>> listmap) {
        //更新用户证书
        Map<String,Object> map=listmap.get(0);
        if (TAGE_ISRONT == 1) {
            if (flag == 1) {
                teacher_init.Teacher_update(uid, map.get("imageurl").toString());
                Glide.with(this).load(map.get("imageurl")).into(xueliImage1);
//                Glide.with(this).load(map.get("imageurl")).into(tianjiaxueli);
            } else if (flag == 2) {
                teacher_init.Teacher_update2(uid, map.get("imageurl").toString());
//                Glide.with(this).load(map.get("imageurl")).into(classImage);
                Glide.with(this).load(map.get("imageurl")).into(xueliImage2);
            }
        }else if (TAGE_ISRONT == 3){
            teacher_init.Teacher_updateSubjectBg(uid, map.get("imageurl").toString());
//            Glide.with(this).load(map.get("imageurl")).into(classImage);
        }else if (TAGE_ISRONT == 2){
            if (flag == 1) {
                teacher_init.Teacher_update3(uid, map.get("imageurl").toString());
                Glide.with(this).load(map.get("imageurl")).into(rongyuImage1);
//                Glide.with(this).load(map.get("imageurl")).into(tianjiaxueli);
            } else if (flag == 2) {
                teacher_init.Teacher_update4(uid, map.get("imageurl").toString());
//                Glide.with(this).load(map.get("imageurl")).into(classImage);
                Glide.with(this).load(map.get("imageurl")).into(rongyuImage2);
            }
        }else if (TAGE_ISRONT == 4){
            if (flag == 1) {
                teacher_init.Teacher_update5(uid, map.get("imageurl").toString());
                Glide.with(this).load(map.get("imageurl")).into(postuserimage1);
            }else  if (flag == 2){
                teacher_init.Teacher_update6(uid, map.get("imageurl").toString());
                Glide.with(this).load(map.get("imageurl")).into(postuserimage2);
            }

        }

        Toast.makeText(this,"更新成功",Toast.LENGTH_LONG).show();
    }


    /**
     * 动态设置ListView的高度
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //收缩软键盘
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
//    MyGridView_Adapter simpleAdapter;
//    public void setbitmap(final String[] pate){
//        //创建一个新线程，用于从网络上获取图片
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //从网络上获取图片
//                final List<Map<String,Bitmap>> bitmap=getPicture(pate);
//                //发送一个Runnable对象
//                xuelimage.post(new Runnable(){
//                    @Override
//                    public void run() {
//                         simpleAdapter=new MyGridView_Adapter(bitmap,Teacher_management.this);
//                         xuelimage.setAdapter(simpleAdapter);
//                    }
//
//                });
//
//            }
//        }).start();//开启线程
//    }

//    public void setbitmap1(final String[] pate){
//        //创建一个新线程，用于从网络上获取图片
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //从网络上获取图片
//                final List<Map<String,Bitmap>> bitmap=getPicture(pate);
//                //发送一个Runnable对象
//                rongyuimage.post(new Runnable(){
//                    @Override
//                    public void run() {
//                        MyGridView_Adapter simpleAdapte=new MyGridView_Adapter(bitmap,Teacher_management.this);
//                        rongyuimage.setAdapter(simpleAdapte);
//                    }
//
//                });
//
//            }
//        }).start();//开启线程
//    }

    /*
     * 功能:根据网址获取图片对应的Bitmap对象
     * @param path
     * @return Bitmap
     * */
    public List<Map<String,Bitmap>> getPicture(String path[]){
        Bitmap bm=null;
        URL url;
        List<Map<String,Bitmap>> listbimap=new ArrayList<>();
        for (int i=0;i<path.length;i++) {
            try {
                url = new URL(path[i]);//创建URL对象
                URLConnection conn = url.openConnection();//获取URL对象对应的连接
                conn.connect();//打开连接
                InputStream is = conn.getInputStream();//获取输入流对象
                bm = BitmapFactory.decodeStream(is);//根据输入流对象创建Bitmap对象
                Map<String,Bitmap> mapbitmap=new HashMap<>();
                mapbitmap.put("bitmap",bm);
                listbimap.add(mapbitmap);
            } catch (MalformedURLException e1) {
                e1.printStackTrace();//输出异常信息
            } catch (IOException e) {
                e.printStackTrace();//输出异常信息
            }
        }
        return listbimap;
    }

    @Override
    public void successTurnover(List<Map<String, Object>> list) {
        getmCourse();
    }

    @Override
    public void failTurnover(String msg) {

    }
}
