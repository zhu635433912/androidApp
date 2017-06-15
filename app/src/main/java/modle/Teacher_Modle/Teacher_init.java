package modle.Teacher_Modle;

import android.content.Context;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
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
    public Map<String,Object> Get_Teacher(int uid,Requirdetailed requirdetailed);
    /**
    获取教师详细资料
     uid    用户id
     id     操作教师id
     */
    public Map<String,Object> Get_Teacher_detailed(int uid, int id, Requirdetailed requirdetailed,int ztm);
    /**
     * 获取教师列表
     * uid    用户id
     * lat    纬度
     * lng    经度
     */
    public List<Map<String,Object>> Get_Teacher_list(int uid, int role, double lat, double lng, PullToRefreshListView listView, Context context, int order, String state,
                                                     int gender, int speciality, int grade_type, int order_rank, Requirdetailed requirdetailed,int page);
    /**
     *教师资料更新
     */
    public Map<String,Object> Teacher_update(int uid,String others);
    /**
     * 教师列表显示状态更改
     * uid    用户id
     * status 显示状态
     */
    public Map<String,Object> Teacher_Change(int uid,int status);
    /**
     * 招聘列表显示状态更改
     *uid    用户id
     * status 显示状态
     */
    public Map<String,Object> Teacher_recruit(int uid,int status);

    /**
     *教师资料更新服务类型
     */
    public Map<String,Object> Teacher_service_type(int uid,String service_type);
    /**
     *教师资料更新教龄
     */
    public Map<String,Object> Teacher_years(int uid,int years);
    /**
     *教师资料更新个人简介
     */
    public void Teacher_resume(int uid,String resume);
    /**
     *教师资料更新特长
     */
    public void Teacher_speciality(int uid,String speciality);
    /**
     *教师资料更新毕业学校
     */
    public void Teacher_graduated_school(int uid,String graduated_school);
    /*
    获取推荐教师
     */
    public void gettuijian_Teacher(int course_id,int grade_id,String address,Requirdetailed requirdetailed);
}
