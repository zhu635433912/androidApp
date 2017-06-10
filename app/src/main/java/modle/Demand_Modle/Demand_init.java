package modle.Demand_Modle;

import android.content.Context;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.init.Xuqiuxiangx_init;

import java.util.List;
import java.util.Map;

import modle.toos.MyListview;

/**
 * 需求接口映射
 */

public interface Demand_init {
    /**获取需求列表
     *uid              用户id
     *filter_type      筛选条件, 0:不筛选; 1:年级; 2:时间段; 3:学历要求; 4:发布者
     * filter_id        对应的id, 如果是学历的话, 做"》"筛选
     * start_time       如果筛选条件是2, 传入开始时间, 如2016-08-10
     *end_time         如果筛选条件是2, 传入结束时间
     * page             页面数, 做分页处理, 默认填1
     */
    public List<Map<String,Object>> getDemand_list(int uid, int role, int filter_type, int filter_id, String start_time, int end_time, int page, MyListview listView, Context context, Student_init requirdetailed);

    /**获取单一需求
     *uid    用户id
     * id     需求id
     */
    public Map<String,Object> getDemand_danyi(int uid, int id, Xuqiuxiangx_init xuqiuxiangx_init);

    /**发布需求
     * uid            用户id
     * content        需求内容
     * fee            课时费
     * grade_id       年级id
     * course_id      科目id
     * gender         性别要求
     * age            年龄要求
     * education_id   学 历id
     * province       省
     * cty            市
     * state          区
     * service_type   服务方式
     * items          时间段
     */
    public Map<String,Object> ReleaseDemand(int id,String content,float fee,int grade_id,int course_id,int gender,int age,int education_id,String province,String
                                            cty,String state,int serice_type,String start,String ent,double lng,double lat);
    /**更新需求
     * uid            用户id
     * content        需求内容
     * fee            课时费
     * grade_id       年级id
     * course_id      科目id
     * gender         性别要求
     * education_id   学历id
     * province       省
     * cty            市
     * state          区
     * service_type   服务方式
     * items          时间段
     */
    public Map<String,Object> Update_Demand(int uid,String content,float fee,int grade_id,int course_id,int gender,int education_id,String province,String cty,String state,
                                             int service_type,Map<String,Object> items);
    /**删除需求
     *uid   用户id
     * id    需求id
     */
    public Map<String,Object> Delete_Demand(int uid,int id);

    /**假删除需求
     *uid   用户id
     * id    需求id
     */
    public Map<String,Object> JiaDelete_Demand(int uid,int id);

    /*
    获取用户自己的需求
     */
    public void getMyDemand_list(int publisher_iint,int filter_type, Student_init student_init);
}
