package modle.Order_Modle;

import android.content.Context;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Ordercontent_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;

import java.util.Map;

import modle.toos.MyListview;

/**
 * 订单接口映射
 */

public interface Order_init {
    /**
     * 获取订单列表
     *uid              用户id
     *filter_type      筛选条件 0:下单人 1:教师
     * status           订单状态  1:未付款 2:进行中 3:交易完成 4:申请退款 5:同意退款 6:拒绝退款
     * page             页面数, 做分页处理, 默认填1
     */
    public Map<String,Object> getOrder_list(int id, int filter_type, int status , int page, MyListview listView
            , Context context, Student_init student_init,int requirement_id,int order_rank);
    /**
     * 获取单一订单
     * uid              用户id
     * id               订单id
     */
    public Map<String,Object> getOrder_danyilist(int uid, int id, Ordercontent_init ordercontent_init);
    /**
     *创建订单
     *uid              用户id
     * teacher_id       教师id
     * requirement_id   需求id
     * fee              金额
     */
    public Map<String,Object> Establish_Order(TuijianView tuijianView,int uid, int teacher_id, int requirement_id, float fee, int duration, int course_Id, int grade_id, int service_type, String address, String province, String city, String district, String desc);
    /**
     * 删除订单
     * uid      用户id
     * id       订单id
     */
    public Map<String,Object> Delete_Order(int uid, int id, Requirdetailed requirdetailed);
    /**
     * 更新订单状态
     * uid      用户id
     * id       订单id
     * status   订单状态  1:未付款 2:进行中 3:交易完成 4:申请退款 5:同意退款 6:拒绝退款
     */
    public Map<String,Object> Update_Order(int uid,int id,int status,String safeword,double fee );
    /**
     * 更新订单评分
     * uid      用户id
     * id       订单id
     * rank1    教学质量
     * rank2    备课评分
     * rank3    教学范围
     * rank4    评价 1:好评 2:中评 3:差评
     */
    public Map<String,Object> UpdateOrder_score(int uid,int id,int rank1,int rank2,int rank3,int rank4,int rank);
    /**
     * 更新订单金额
     * uid      用户id
     * id       订单id
     * fee      金额
     */
    public Map<String,Object> UpdateOrder_Amount(int uid,int id,double fee,ChangeOrderView changeOrderView);
    /**
     * 更新订单课时数费用
     * uid      用户id
     * id       订单id
     * fee      金额
     */
    public Map<String,Object> UpdateOrder_Classhour(int uid,int id,int fee);
    /**
     * 评论订单
     * uid            用户id
     * source_id      订单id
     * content        评论内容
     * picture        评论图片
     */
    public Map<String,Object> Comment_Order(int uid,int source_id,String content,long picture,int rank,int rank4,int rank1,int rank2,int rank3);
    /**
     * 订单退款
     * uid            用户id
     * id             订单id
     * status         订单状态 1:未付款 2:进行中 3:交易完成 4:申请退款 5:同意退款 6:拒绝退款
     * refund_fee     退款金额
     */
    public Map<String,Object> Order_refund(int uid,int id,int status,double refund_fee);

    //创建订单
    public void CreateOrder(int uid,int teacher_id,int requirement_id,float fee,int course_id,int grade_id,Requirdetailed requirdetailed,String address,double lat,double lng);

    //退款
    public void submit_refund(int uid,int id,int status,String refund_fee,String reason,String desc,String imgs1,String imgs2,String imgs3,String imgs4,ChangeOrderView changeOrderView);

    //教师完成授课
    public void complete_order(int order_id,String content,String evaluate,String img1,String img2,String img3,String img4,ChangeOrderView changeOrderView);
}
