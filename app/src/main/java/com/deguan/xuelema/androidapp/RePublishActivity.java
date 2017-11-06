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
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.pickerview.OptionsPickerView;
import com.deguan.xuelema.androidapp.entities.SubjectEntity;
import com.deguan.xuelema.androidapp.fragment.StudentFabuFragment;
import com.deguan.xuelema.androidapp.utils.MyBaseActivity;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import modle.Adapter.MyExpandableAdapter;
import modle.Demand_Modle.Demand;
import modle.user_ziliao.User_id;

@EActivity(R.layout.activity_re_publish)
public class RePublishActivity extends MyBaseActivity implements ChangeOrderView, PayView {

    @ViewById(R.id.fabu_head_image)
    SimpleDraweeView headImage;
    @ViewById(R.id.fabu_nickname)
    TextView nikcnameTv;
    @ViewById(R.id.fabu_course_ll)
    LinearLayout courseLl;
    @ViewById(R.id.fabu_course_tv)
    TextView courseTv;
    @ViewById(R.id.fabu_grade_tv)
    TextView gradeTv;
    @ViewById(R.id.fabu_grade_image)
    ImageView gradeImage;
    @ViewById(R.id.fabu_low_price)
    EditText lowPriceEdit;
    @ViewById(R.id.fabu_high_price)
    EditText highPriceEdit;
    @ViewById(R.id.fabu_beizhu_content)
    EditText contentEdit;
    @ViewById(R.id.fabu_grade_ll)
    LinearLayout gradeLl;
    @ViewById(R.id.fabu_version_image)
    ImageView versionImage;
    @ViewById(R.id.fabu_version_tv)
    TextView versionTv;
    @ViewById(R.id.fabu_gender_radiogp)
    RadioGroup genderGroup;
    @ViewById(R.id.fabu_service_radiogp)
    RadioGroup serviceGroup;
    @ViewById(R.id.fabu_address_image)
    ImageView addressImage;
    @ViewById(R.id.fabu_address_edit)
    EditText addressEdit;
    @ViewById(R.id.fabu_address_input)
    ImageView addressInputImage;
    @ViewById(R.id.fabu_sure_btn)
    ImageView sureBtn;
    @ViewById(R.id.republish_back)
    ImageView backImage;


    private String provinc;//省
    private String location;//市
    private String city;//区

    private List<String> menus = SubjectUtil.getMenu();
    private List<List<SubjectEntity>> childDatas = SubjectUtil.getChildList();


    private double lat;
    private double lng;
    private float i;
    private int gradeId = 0;//最终年级
    private int genderId = 0;
    private String version = "";
    private int service_type = 2;
    private int courseId;
    private PopupWindow coursePop;
    private MyExpandableAdapter adapter;
    private String content;
    private String address;
    private String lowPrice;
    private String highPrice;
    private String headurl;
    private String courseName;
    private String gradeName;
    private String grade_id;
    private String course_id;
    private String serviceType;
    private String gender_id;


    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
        content = getIntent().getStringExtra("content");
        grade_id = getIntent().getStringExtra("gradeId");
        course_id = getIntent().getStringExtra("courseId");
        provinc = getIntent().getStringExtra("province");
        location = getIntent().getStringExtra("location");
        city = getIntent().getStringExtra("city");
        serviceType = getIntent().getStringExtra("service_type");
        lat = getIntent().getDoubleExtra("lat",21);
        lng = getIntent().getDoubleExtra("lng",121);
        address = getIntent().getStringExtra("address");
        lowPrice = getIntent().getStringExtra("lowPrice");
        highPrice = getIntent().getStringExtra("highPrice");
        version = getIntent().getStringExtra("version");
        headurl = getIntent().getStringExtra("headurl");
        courseName = getIntent().getStringExtra("courseName");
        gradeName = getIntent().getStringExtra("gradeName");
        gender_id = getIntent().getStringExtra("genderId");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        adapter = new MyExpandableAdapter(this,menus,childDatas);
        nikcnameTv.setText(User_id.getNickName());
        headImage.setImageURI(Uri.parse(headurl));
        gradeTv.setText(gradeName);
        gradeId = Integer.parseInt(grade_id);
        versionTv.setText(version);
        lowPriceEdit.setText(lowPrice);
        highPriceEdit.setText(highPrice);
        courseTv.setText(courseName);
        courseId = Integer.parseInt(course_id);
        contentEdit.setText(content);
        versionTv.setText(version);
        addressEdit.setText(address);
        service_type = Integer.parseInt(serviceType);
        if (service_type == 3){
            serviceGroup.check(R.id.fabu_service_student);
        }else {
            serviceGroup.check(R.id.fabu_service_teacher);
        }
        genderId = Integer.parseInt(gender_id);
        if (genderId == 0){
            genderGroup.check(R.id.fabu_gender_no);
        }else if (genderId == 2){
            genderGroup.check(R.id.fabu_gender_female);
        }else{
            genderGroup.check(R.id.fabu_gender_male);
        }

//        new Demand().getLastDemand(Integer.parseInt(User_id.getUid()),this);
    }

    @Override
    public void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
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
        versionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //科教版本
                AlertDialog.Builder builder = new AlertDialog.Builder(RePublishActivity.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("选择科教版本");
                //    指定下拉列表的显示数据
                final String[] cities = {"人教版","语文版","北师版","苏教版","翼教版","华师版",
                        "鲁教版","沪教版","京教版","鄂教版","粤教版","川教版","浙教版" ,
                        "湘教版","外研版","北京仁爱","其它"
                };
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RePublishActivity.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        versionTv.setText(cities[which]);
                        version = cities[which];

                    }
                });
                builder.show();
            }
        });
        versionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //科教版本
                AlertDialog.Builder builder = new AlertDialog.Builder(RePublishActivity.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("选择科技版本");
                //    指定下拉列表的显示数据
                final String[] cities = {"人教版","语文版","北师版","苏教版","翼教版","华师版",
                        "鲁教版","沪教版","京教版","鄂教版","粤教版","川教版","浙教版" ,
                        "湘教版","外研版","北京仁爱","其它"
                };
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RePublishActivity.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        versionTv.setText(cities[which]);
                        version = cities[which];

                    }
                });
                builder.show();
            }
        });
        gradeLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        gradeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //年级
                AlertDialog.Builder builder = new AlertDialog.Builder(RePublishActivity.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("请选择一个年级");
                //    指定下拉列表的显示数据
                final String[] cities = {"小学 一年级","小学 二年级","小学 三年级","小学 四年级"
                        , "小学 五年级", "小学 六年级", "初中 七年级", "初中 八年级", "初中 九年级",
                        "高中 高一", "高中 高二", "高中 高三","大学"
                };
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RePublishActivity.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        gradeTv.setText(cities[which]);
                        gradeId = which + 30;

                    }
                });
                builder.show();
            }
        });
        gradeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //年级
                AlertDialog.Builder builder = new AlertDialog.Builder(RePublishActivity.this);
                builder.setIcon(R.drawable.add04);
                builder.setTitle("请选择一个年级");
                //    指定下拉列表的显示数据
                final String[] cities = {"小学 一年级","小学 二年级","小学 三年级","小学 四年级"
                        , "小学 五年级", "小学 六年级", "初中 七年级", "初中 八年级", "初中 九年级",
                        "高中 高一", "高中 高二", "高中 高三","大学"
                };
                //    设置一个下拉的列表选择项
                builder.setItems(cities, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(RePublishActivity.this, "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
                        gradeTv.setText(cities[which]);
                        gradeId = which + 30;

                    }
                });
                builder.show();
            }
        });
        addressImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RePublishActivity.this, AmapActivity.class), REQUEST_CODE_MAP);
            }
        });
        addressInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(RePublishActivity.this, AmapActivity.class), REQUEST_CODE_MAP);
            }
        });
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.fabu_gender_no:
                        genderId = 0;
                        break;
                    case R.id.fabu_gender_male:
                        genderId = 1;
                        break;
                    case R.id.fabu_gender_female:
                        genderId = 2;
                        break;
                }

            }
        });
        serviceGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.fabu_service_teacher:
                        service_type = 2;
                        break;
                    case R.id.fabu_service_student:
                        service_type = 3;
                        break;
                }
            }
        });

        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int low = Integer.parseInt(lowPriceEdit.getText().toString());
                int high = Integer.parseInt(highPriceEdit.getText().toString());
                if (TextUtils.isEmpty(lowPriceEdit.getText().toString())
                        ||Integer.parseInt(lowPriceEdit.getText().toString())<30){
                    Toast.makeText(RePublishActivity.this, "起步价不能低于30", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(highPriceEdit.getText().toString())){
                    Toast.makeText(RePublishActivity.this, "请设置价格", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(contentEdit.getText().toString())){
                    Toast.makeText(RePublishActivity.this, "请填写需求内容", Toast.LENGTH_SHORT).show();
                }else if (courseId == 0){
                    Toast.makeText(RePublishActivity.this, "请选择科目", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(version)){
                    Toast.makeText(RePublishActivity.this, "请选择你的科教版本", Toast.LENGTH_SHORT).show();
                }else if (gradeId == 0){
                    Toast.makeText(RePublishActivity.this, "请选择年级", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(addressEdit.getText().toString())){
                    Toast.makeText(RePublishActivity.this, "你的位置呢？？？", Toast.LENGTH_SHORT).show();
                }else {
                    new Demand().publishDemand(RePublishActivity.this,Integer.parseInt(User_id.getUid()),contentEdit.getText().toString(),gradeId,courseId,genderId,provinc,
                            location,city,service_type,lat,lng,addressEdit.getText().toString(),lowPriceEdit.getText().toString(),
                            highPriceEdit.getText().toString(),version);
                }
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
        backgroundAlpha(RePublishActivity.this, 0.5f);//0.0-1.0  ;
        coursePop.setOutsideTouchable(true);
        coursePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(RePublishActivity.this, 1f);
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

    protected static final int REQUEST_CODE_MAP = 1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_MAP) { // location
                String latitude = data.getStringExtra("latitude");
                String longitude = data.getStringExtra("longitude");
                provinc = data.getStringExtra("province");
                location = data.getStringExtra("location");
                city = data.getStringExtra("city");
                lat = Double.parseDouble(latitude);
                lng =  Double.parseDouble(longitude);
                String locationAddress = data.getStringExtra("address");
                if (locationAddress != null && !locationAddress.equals("")) {
                    addressEdit.setText(locationAddress);
                }

            }
        }
    }

    @Override
    public void successOrder(String msg) {
        EventBus.getDefault().post("fabu","fabu");
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successPay(Map<String, Object> map) {

    }

    @Override
    public void failPay(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



}
