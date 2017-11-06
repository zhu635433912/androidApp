package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.fragment.MyBaseFragment;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.KechengAdapter;
import modle.Increase_course.Increase_course;
import modle.user_ziliao.User_id;
import view.login.Modle.MobileView;
import view.login.Modle.RegisterEntity;

@EActivity(R.layout.activity_manager)
public class ManagerActivity extends MyBaseActivity implements View.OnClickListener, TurnoverView, Student_init, MobileView {

    @ViewById(R.id.manager_back)
    RelativeLayout backR;
    @ViewById(R.id.manager_list)
    ListView listView;

    private TextView chooseSubjetTv,chooseGradeTv,chooseDescTv;
    @ViewById(R.id.manager_fabu)
    TextView fabuTv;
    private EditText visitEdit,unvisitEdit;
    private int grade_id;
    private int descFlag;
    private int kcid;
    private boolean ispass;
    private Increase_course increase_course;
    private KechengAdapter kechengAdapter;
    private List<Map<String, Object>> lists = new ArrayList<>();


    @Override
    public void before() {
        super.before();
    }


    @Override
    public void initView() {
        View view = getLayoutInflater().inflate(R.layout.manager_list_head,null);
        chooseSubjetTv = (TextView) view.findViewById(R.id.manager_choose_subject);
        chooseGradeTv = (TextView) view.findViewById(R.id.manager_choose_grade);
        visitEdit = (EditText) view.findViewById(R.id.manager_visit_fee);
        unvisitEdit = (EditText) view.findViewById(R.id.manager_unvisit_fee);
        chooseDescTv = (TextView) view.findViewById(R.id.manager_choose_desc);
        chooseSubjetTv.setOnClickListener(this);
        chooseGradeTv .setOnClickListener(this);
        visitEdit .setOnClickListener(this);
        unvisitEdit .setOnClickListener(this);
        chooseDescTv .setOnClickListener(this);
        fabuTv.setOnClickListener(this);
        backR.setOnClickListener(this);
        kechengAdapter = new KechengAdapter(lists,this);
        listView.setAdapter(kechengAdapter);
//        listView.addHeaderView(view);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int course_id=kechengAdapter.getcourse_id(position);
//                Log.e("aa","删除"+course_id);
                new AlertDialog.Builder(ManagerActivity.this).setTitle("学了么提示!").setMessage("确定删除课程吗?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                increase_course.deleteCourse(Integer.parseInt(User_id.getUid()),course_id,ManagerActivity.this);
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(CourseActivity_.intent(ManagerActivity.this)
                        .extra("uid",User_id.getUid())
                        .extra("id",lists.get(position).get("id")+"")
                        .extra("course_id",lists.get(position).get("course_id")+"")
                        .extra("course_name",lists.get(position).get("course_name")+"")
                        .extra("grade_id",lists.get(position).get("grade_id")+"")
                        .extra("grade_name",lists.get(position).get("grade_name")+"")
                        .extra("course_remark",lists.get(position).get("course_remark")+"")
                        .extra("visit_fee",lists.get(position).get("visit_fee")+"")
                        .extra("unvisit_fee",lists.get(position).get("unvisit_fee")+"")
                        .extra("service_type",lists.get(position).get("service_type")+"")
                        .extra("address",lists.get(position).get("address")+"")
                        .extra("lat",lists.get(position).get("lat")+"")
                        .extra("lng",lists.get(position).get("lng")+"")
                        .get());
            }
        });
    }

    @Override
    public void initData() {
        //获取课程
        getmCourse();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manager_back:
                finish();
                break;
            case R.id.manager_choose_subject:
//获取数据
//                Getdata getdata=new Getdata(); //课程种类
                final AlertDialog.Builder subjectDialog = new AlertDialog.Builder(this);
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
                        Toast.makeText(ManagerActivity.this, "选择的科目为：" + SubjectUtil.getSubjectList().get(which).getSubjectName(), Toast.LENGTH_SHORT).show();
                        chooseSubjetTv.setText(SubjectUtil.getSubjectList().get(which).getSubjectName());
                        kcid = Integer.parseInt(SubjectUtil.getSubjectList().get(which).getSubjectId());
                    }
                });
                subjectDialog.show();
                break;
            case R.id.manager_choose_grade:
//                科目
                AlertDialog.Builder kemuza = new AlertDialog.Builder(this);
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
                        Toast.makeText(ManagerActivity.this, "选择的科目为：" + kemucities[which], Toast.LENGTH_SHORT).show();
                        chooseGradeTv.setText(kemucities[which]);
                        grade_id = which + 1;
                    }
                });
                kemuza.show();
                break;
            case R.id.manager_choose_desc:
//服务类型

                final String[] sex1={"一对一","一对多","一对一/一对多"};
                AlertDialog.Builder rolage1=new AlertDialog.Builder(ManagerActivity.this);
                rolage1.setIcon(android.R.drawable.btn_star);
                rolage1.setTitle("选择类型!");
                rolage1.setSingleChoiceItems(sex1, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        descFlag = which + 1;
                    }
                });
                rolage1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if ( descFlag == 0 || descFlag == 2) {
                            chooseDescTv.setText(sex1[1]);
                        }else if (descFlag == 1){
                            chooseDescTv.setText(sex1[0]);
                        }else {
                            chooseDescTv.setText(sex1[2]);
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
            case R.id.manager_fabu:
                //增加课程
                if (chooseSubjetTv.getText().toString().equals("选择课程")||chooseGradeTv.getText().toString().equals("选择年级")
                        ||chooseDescTv.getText().toString().equals("选择课程说明")
//                        ||serviceTv.getText().toString().equals("服务类型")
                        ||unvisitEdit.getText().toString().equals("")
                        ||visitEdit.getText().toString().equals("")){
                    Toast.makeText(ManagerActivity.this,"请填写正确的课程资料",Toast.LENGTH_SHORT).show();

                }else {
                    new AlertDialog.Builder(ManagerActivity.this).setTitle("学了么提示!").setMessage("确定发布课程吗?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //增加课程
                                    int uid=Integer.parseInt(User_id.getUid());
                                    Increase_course inc=new Increase_course();
                                    int laoshifee=Integer.parseInt(visitEdit.getText().toString());
                                    int xuesfee=Integer.parseInt(unvisitEdit.getText().toString());
//                                         int xuesfee = 0;

                                        inc.Addcourse(uid, kcid, chooseDescTv.getText().toString(), laoshifee, xuesfee, 6, grade_id, ManagerActivity.this);
//                                        Toast.makeText(ManagerActivity.this, "增加课程成功", Toast.LENGTH_SHORT).show();

                                    //刷新课程
//                                    getmCourse();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show();
                }
                break;
        }
    }

    @Override
    public void successTurnover(List<Map<String, Object>> list) {
        getmCourse();
    }
    public void getmCourse(){
        //获取教师自己课程
        increase_course=new Increase_course();
        increase_course.selecouse(Integer.parseInt(User_id.getUid()),null,this);
    }
    @Override
    public void failTurnover(String msg) {
        if (msg.equals("发布课程成功")){
            getmCourse();
        }
        Toast.makeText(ManagerActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListview(List<Map<String, Object>> listmap) {
        lists.clear();
        lists.addAll(listmap);
        kechengAdapter.notifyDataSetChanged();
    }

    @Override
    public void setListview1(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successRegister(String msg) {
        Toast.makeText(ManagerActivity.this, msg, Toast.LENGTH_SHORT).show();
        getmCourse();
    }

    @Override
    public void failRegister(String msg) {
        Toast.makeText(ManagerActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successLogin(RegisterEntity entity) {

    }
}
