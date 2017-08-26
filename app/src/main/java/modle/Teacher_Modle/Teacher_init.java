package modle.Teacher_Modle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.PayView;
import com.deguan.xuelema.androidapp.viewimpl.TeacherView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Map;

import modle.toos.MyListview;

/**
 * 教师接口
 */

public interface Teacher_init {
    /**
    获取教师个人资料
     */
    public Map<String,Object> Get_Teacher(int uid, Requirdetailed requirdetailed);

    public Map<String,Object> Get_Teacher1(int uid, PayView teacherView);
    /**
    获取教师详细资料
     uid    用户id
     id     操作教师id
     */
    public Map<String,Object> Get_Teacher_detailed(int uid, int id, Requirdetailed requirdetailed, int ztm,int number);
    /**
     * 获取教师列表
     * uid    用户id
     * lat    纬度
     * lng    经度
     */
    public List<Map<String,Object>> Get_Teacher_list(int uid, int role, String lat, String lng, RecyclerView listView, Context context, int order, String state,
                                                     int gender, int speciality, int grade_type, int order_rank, Requirdetailed requirdetailed, int page,
                                                     int course_id);
    /**
     *教师资料更新
     */
    public Map<String,Object> Teacher_update(int uid, String others);
    public Map<String,Object> Teacher_update2(int uid, String others);
    public Map<String,Object> Teacher_update3(int uid, String others);
    public Map<String,Object> Teacher_update4(int uid, String others);
    public void  Teacher_update5(int uid, String others);
    public void  Teacher_update6(int uid, String others);
    public Map<String,Object> Teacher_updateSubjectBg(int uid, String class_img);

    /**
     * 教师列表显示状态更改
     * uid    用户id
     * status 显示状态
     */
    public Map<String,Object> Teacher_Change(int uid, int status);
    /**
     * 招聘列表显示状态更改
     *uid    用户id
     * status 显示状态
     */
    public Map<String,Object> Teacher_recruit(int uid, int status);

    /**
     *教师资料更新服务类型
     */
    public Map<String,Object> Teacher_service_type(int uid, String service_type);
    /**
     *教师资料更新教龄
     */
    public Map<String,Object> Teacher_years(int uid, int years);
    //更新备注
    public void TeacherUpdateRemark(int uid,String remark);

    /**
     * 更新教师个人经历
     * @param uid
     * @param exper
     * @param exper_url
     */
    public void Teacher_exper(int uid,String exper,String exper_url);
    /**
     *教师资料更新个人简介
     */
    public void Teacher_resume(int uid, String resume);
    /**
     *教师资料更新特长
     */
    public void Teacher_speciality(int uid, String speciality);
    /**
     *教师资料更新毕业学校
     */
    public void Teacher_graduated_school(int uid, String graduated_school);
    /*
        更新教师个人签名
     */
    public void Teacher_signature(int uid, String signature);
    /*
    获取推荐教师
     */
    public void gettuijian_Teacher(int course_id, int grade_id, String address, Requirdetailed requirdetailed);


    /*
   获取对教师的评价接口
    */
    public void setEvaluation_Teacher(int uid, Student_init student_init,int page);


    //更新教师课程封面
//    public void setSubjectBackgroud(int uid,)


    /*
    获取推荐教师
     */
    public void gettuijian_Teacher1(String name, String lat, String lng);


    /*教学案例*/
    public void getExampleList(int id,int page);
}
