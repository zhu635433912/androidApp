package com.deguan.xuelema.androidapp;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.deguan.xuelema.androidapp.entities.SubjectEntity;
import com.deguan.xuelema.androidapp.fragment.CourseFragment;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.TurnoverView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.Map;

import modle.Adapter.MyExpandableAdapter;
import modle.Increase_course.Increase_course;
import modle.Teacher_Modle.Teacher;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_course)
public class CourseActivity extends MyBaseActivity implements Requirdetailed, TurnoverView {

    @ViewById(R.id.manager_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.manager_nickname)
    TextView nicknameTv;
    @ViewById(R.id.manager_course_tv)
    TextView courseTv;
    @ViewById(R.id.manager_course_ll)
    LinearLayout courseLl;
    @ViewById(R.id.manager_grade_ll)
    LinearLayout gradeLl;
    @ViewById(R.id.manager_grade_tv)
    TextView gradeTv;
    @ViewById(R.id.manager_service_radiogp)
    RadioGroup serviceGroup;
    @ViewById(R.id.manager_address_image)
    ImageView addressImage;
    @ViewById(R.id.manager_address_edit)
    EditText addressEdit;
    @ViewById(R.id.manager_address_input)
    ImageView addreInputImage;
    @ViewById(R.id.manager_time_edit)
    EditText timeEdit;
    @ViewById(R.id.manager_sure_btn)
    TextView sureBtn;
    @ViewById(R.id.manager_student_price)
    EditText studentEdit;
    @ViewById(R.id.manager_teacher_price)
    EditText teacherEdit;
    @ViewById(R.id.manager_list)
    TextView listTv;
    @ViewById(R.id.dindanxiangxihuitui)
    RelativeLayout backRl;



    private List<String> menus = SubjectUtil.getMenu();
    private List<List<SubjectEntity>> childDatas = SubjectUtil.getChildList();
    private int gradeId = 0;//最终年级
    private int courseId = 0;
    private String remark = "一对一";
    private PopupWindow coursePop;
    private MyExpandableAdapter adapter;
    private String lat,lng;

    public AMapLocationClient mLocationClient = null;
    private OptionsPickerView optionsPickerView;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {

                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
//                    lat = aMapLocation.getLatitude();
//                    lng = aMapLocation.getLongitude();
                    addressEdit.setText(
                            aMapLocation.getProvince().toString() +
                                    aMapLocation.getCity().toString()
                                    + aMapLocation.getDistrict().toString() +
                                    aMapLocation.getStreet().toString() +
                                    aMapLocation.getStreetNum().toString());

//                    provinc = aMapLocation.getProvince().toString();
//                    location = aMapLocation.getCity().toString();
//                    city = aMapLocation.getDistrict().toString();


                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    private String id;
    private String course_id,course_name;
    private String grade_id;
    private String course_remark;
    private String visit_fee;
    private String unvisit_fee;
    private String service_type;
    private String address;
    private String grade_name;

    @Override
    public void before() {
        super.before();
        id = getIntent().getStringExtra("id");
        course_id = getIntent().getStringExtra("course_id");
        courseId = Integer.parseInt(course_id);

        course_name = getIntent().getStringExtra("course_name");
        grade_id = getIntent().getStringExtra("grade_id");
        gradeId = Integer.parseInt(grade_id);
        grade_name = getIntent().getStringExtra("grade_name");
        course_remark = getIntent().getStringExtra("course_remark");
        visit_fee = getIntent().getStringExtra("visit_fee");
        unvisit_fee = getIntent().getStringExtra("unvisit_fee");
        service_type = getIntent().getStringExtra("service_type");
        address = getIntent().getStringExtra("address");
    }

    @Override
    public void initView() {
        courseTv.setText(course_name);
        gradeTv.setText(grade_name);
        if (course_remark.equals("一对一"))
        {
            serviceGroup.check(R.id.manager_service_teacher);
        }else {
            serviceGroup.check(R.id.manager_service_student);
        }
        addressEdit.setText(address);
        studentEdit.setText(unvisit_fee);
        teacherEdit.setText(visit_fee);


        backRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        courseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCoursePop();
                coursePop.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
        gradeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //年级
                AlertDialog.Builder builder = new AlertDialog.Builder(CourseActivity.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("请选择一个年级");
                //    指定下拉列表的显示数据
                final String[] cities = {"小学","初中", "高中","大学"};
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(CourseActivity.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        gradeTv.setText(cities[which]);
                        gradeId = which + 1;

                    }
                });
                builder.show();
            }
        });

        serviceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.fabu_service_teacher:
                        remark = "一对一";
                        break;
                    case R.id.fabu_service_student:
                        remark = "一对多";
                        break;
                }
            }
        });
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(studentEdit.getText().toString())
                        ||Integer.parseInt(studentEdit.getText().toString())<10){
                    Toast.makeText(CourseActivity.this, "起步价不能低于10元", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(teacherEdit.getText().toString())
                        ||Integer.parseInt(teacherEdit.getText().toString())<10){
                    Toast.makeText(CourseActivity.this, "起步价不能低于10元", Toast.LENGTH_SHORT).show();
                }else if (courseId == 0){
                    Toast.makeText(CourseActivity.this, "请选择科目", Toast.LENGTH_SHORT).show();
                }else if (gradeId == 0){
                    Toast.makeText(CourseActivity.this, "请选择年级", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(addressEdit.getText().toString())){
                    Toast.makeText(CourseActivity.this, "你的位置呢？？？", Toast.LENGTH_SHORT).show();
                }else {
                    int studentPrice = Integer.parseInt(studentEdit.getText().toString());
                    int teacherPrice = Integer.parseInt(teacherEdit.getText().toString());
                    if (studentPrice <= teacherPrice) {
                    new Increase_course().changeCourse(User_id.getUid(),id,courseId,gradeId,remark,teacherPrice,studentPrice,
                            6,addressEdit.getText().toString(),CourseActivity.this,lat,lng);
                    }else {
                        Toast.makeText(CourseActivity.this, "老师上门价格不能低于学生上门", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        addressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CourseActivity.this, AmapActivity.class), REQUEST_CODE_MAP);
            }
        });
        addreInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(CourseActivity.this, AmapActivity.class), REQUEST_CODE_MAP);
            }
        });
    }
    private void showCoursePop() {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fabu_course_pop,null);
        ExpandableListView listView= (ExpandableListView) view.findViewById(R.id.choose_course_eplist);
        listView.setAdapter(adapter);
        coursePop = new PopupWindow(view);
        coursePop.setFocusable(true);
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width/10*9);
        coursePop.setHeight(height/10 * 9);
        coursePop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(this, 0.5f);//0.0-1.0  ;
        coursePop.setOutsideTouchable(true);
        coursePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(CourseActivity.this, 1f);
            }
        });
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                SubjectEntity entity = childDatas.get(groupPosition).get(childPosition);
                courseId = Integer.parseInt(entity.getSubjectId());
                courseTv.setText(entity.getSubjectName());
                coursePop.dismiss();
                return true;
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


    @Override
    public void initData() {
        adapter = new MyExpandableAdapter(CourseActivity.this,menus,childDatas);
//        new Teacher().Get_Teacher_detailed(Integer.parseInt(User_id.getUid()),Integer.parseInt(User_id.getUid()),this,2,0);
        //初始化定位
        mLocationClient = new AMapLocationClient(CourseActivity.this.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
//        mLocationClient.startLocation();
    }

    protected static final int REQUEST_CODE_MAP = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAP) { // location
                String latitude = data.getStringExtra("latitude");
                String longitude = data.getStringExtra("longitude");
//                provinc = data.getStringExtra("province");
//                location = data.getStringExtra("location");
//                city = data.getStringExtra("city");
//                lat = Double.parseDouble(latitude);
//                lng =  Double.parseDouble(longitude);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    addressEdit.setText(locationAddress);
                }

            }
        }
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("nickname")+"")){
            nicknameTv.setText(map.get("nickname")+"");
        }
        if (!TextUtils.isEmpty(map.get("teach_time")+"")){
            timeEdit.setText(map.get("teach_time")+"");
        }else {
            timeEdit.setText("默认时间 ：       周一到周五16:00-20:00          " +
                    "             周末9:00-19:00");
        }
        if (!TextUtils.isEmpty(map.get("user_headimg")+"")){
            headImage.setImageURI(Uri.parse(map.get("user_headimg")+""));
        }
        if (!TextUtils.isEmpty(map.get("teach_address")+"")){
            addressEdit.setText(map.get("teach_address")+"");
        }else {
            //启动定位
            mLocationClient.startLocation();
        }
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void successTurnover(List<Map<String, Object>> list) {

    }

    @Override
    public void failTurnover(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        if (msg.equals("修改课程成功")){
//            getmCourse();
            finish();
        }
    }
}
