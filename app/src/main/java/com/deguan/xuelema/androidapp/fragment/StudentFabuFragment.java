package com.deguan.xuelema.androidapp.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
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
import com.deguan.xuelema.androidapp.AmapActivity;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.entities.SubjectEntity;
import com.deguan.xuelema.androidapp.utils.FragmentTabUtils;
import com.deguan.xuelema.androidapp.utils.SubjectUtil;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.simple.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import modle.Adapter.MyExpandableAdapter;
import modle.Demand_Modle.Demand;
import modle.user_ziliao.User_id;

import static android.content.Context.WINDOW_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
@EFragment(R.layout.fragment_student_fabu)
public class StudentFabuFragment extends MyBaseFragment implements ChangeOrderView, PayView {

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


    private String provinc;//省
    private String location;//市
    private String city;//区

    private List<String> menus = SubjectUtil.getMenu();
    private List<List<SubjectEntity>> childDatas = SubjectUtil.getChildList();

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
                    lat = aMapLocation.getLatitude();
                    lng = aMapLocation.getLongitude();
                    addressEdit.setText(
                            aMapLocation.getProvince().toString() +
                            aMapLocation.getCity().toString()
                                    + aMapLocation.getDistrict().toString() +
                                    aMapLocation.getStreet().toString() +
                                    aMapLocation.getStreetNum().toString());

                    provinc = aMapLocation.getProvince().toString();
                    location = aMapLocation.getCity().toString();
                    city = aMapLocation.getDistrict().toString();


                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
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

    @Override
    public void before() {
        super.before();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void initData() {
        adapter = new MyExpandableAdapter(getActivity(),menus,childDatas);
        new Demand().getLastDemand(Integer.parseInt(User_id.getUid()),this);
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
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
        mLocationClient.startLocation();
    }

    @Override
    public void initView() {

        courseLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCoursePop();
                coursePop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
            }
        });
        versionTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //科教版本
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                        Toast.makeText(getActivity(), "选择的年级为：" + cities[which], Toast.LENGTH_SHORT).show();
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
                startActivityForResult(new Intent(getActivity(), AmapActivity.class), REQUEST_CODE_MAP);
            }
        });
        addressInputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), AmapActivity.class), REQUEST_CODE_MAP);
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

                if (TextUtils.isEmpty(lowPriceEdit.getText().toString())
                        ||Integer.parseInt(lowPriceEdit.getText().toString())<30){
                    Toast.makeText(getActivity(), "起步价不能低于30", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(highPriceEdit.getText().toString())){
                    Toast.makeText(getActivity(), "请设置价格", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(contentEdit.getText().toString())){
                    Toast.makeText(getActivity(), "请填写需求内容", Toast.LENGTH_SHORT).show();
                }else if (courseId == 0){
                    Toast.makeText(getActivity(), "请选择科目", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(version)){
                    Toast.makeText(getActivity(), "请选择你的科教版本", Toast.LENGTH_SHORT).show();
                }else if (gradeId == 0){
                    Toast.makeText(getActivity(), "请选择年级", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(addressEdit.getText().toString())){
                    Toast.makeText(getActivity(), "你的位置呢？？？", Toast.LENGTH_SHORT).show();
                }else {
//                    int low = Integer.parseInt(lowPriceEdit.getText().toString());
//                    int high = Integer.parseInt(highPriceEdit.getText().toString());
                    new Demand().publishDemand(StudentFabuFragment.this,Integer.parseInt(User_id.getUid()),contentEdit.getText().toString(),gradeId,courseId,genderId,provinc,
                            location,city,service_type,lat,lng,addressEdit.getText().toString(),lowPriceEdit.getText().toString(),
                            highPriceEdit.getText().toString(),version);
                }
            }
        });
    }

    private void showCoursePop() {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fabu_course_pop,null);
        ExpandableListView listView= (ExpandableListView) view.findViewById(R.id.choose_course_eplist);
        listView.setAdapter(adapter);
        coursePop = new PopupWindow(view);
        coursePop.setFocusable(true);
        WindowManager wm = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        int width = wm.getDefaultDisplay().getWidth();
        coursePop.setWidth(width/10*9);
        coursePop.setHeight(height/10 * 9);
        coursePop.setBackgroundDrawable(new BitmapDrawable());
        backgroundAlpha(getActivity(), 0.5f);//0.0-1.0  ;
        coursePop.setOutsideTouchable(true);
        coursePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(getActivity(), 1f);
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
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        showNormalDialog();
    }

    @Override
    public void failOrder(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successPay(Map<String, Object> map) {
        if (!TextUtils.isEmpty(map.get("publisher_name")+"")){
            nikcnameTv.setText(map.get("publisher_name")+"");
        }
        if (!TextUtils.isEmpty(map.get("publisher_headimg")+"")){
            headImage.setImageURI(Uri.parse(map.get("publisher_headimg")+""));
        }
        if (!TextUtils.isEmpty(map.get("grade_name")+"")){
            gradeTv.setText(map.get("grade_name")+"");
            gradeId = Integer.parseInt(map.get("grade_id")+"");
        }

        if (!TextUtils.isEmpty(map.get("teacher_version")+"")){
            versionTv.setText(map.get("teacher_version")+"");
            version = map.get("teacher_version")+"";
        }
        if (!TextUtils.isEmpty(map.get("publisher_name")+"")){
            nikcnameTv.setText(map.get("publisher_name")+"");
        }
        if (!TextUtils.isEmpty(map.get("service_type")+"")){
            service_type = Integer.parseInt( map.get("service_type")+"");
            if (service_type == 3){
                serviceGroup.check(R.id.fabu_service_student);
            }else {
                serviceGroup.check(R.id.fabu_service_teacher);
            }
        }
        if (!TextUtils.isEmpty(map.get("gender")+"")){
            genderId = Integer.parseInt( map.get("gender")+"");
            if (genderId == 0){
                genderGroup.check(R.id.fabu_gender_no);
            }else if (genderId == 2){
                genderGroup.check(R.id.fabu_gender_female);
            }else{
                genderGroup.check(R.id.fabu_gender_male);
            }
        }
    }

    @Override
    public void failPay(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    private void showNormalDialog(){
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setIcon(android.R.drawable.btn_star);
        normalDialog.setTitle("共享老师提示！");
        normalDialog.setMessage("发布成功");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                    }
                });
        // 显示
        normalDialog.show();
    }


}
