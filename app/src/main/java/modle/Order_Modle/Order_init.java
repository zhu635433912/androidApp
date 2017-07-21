package modle.Order_Modle;

import android.content.Context;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Ordercontent_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.ChangeOrderView;

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
    public Map<String,Object> Establish_Order(int uid,int teacher_id,int requirement_id,float fee,int duration,int course_Id,int grade_id,int service_type,String address);
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
    public Map<String,Object> Update_Order(int uid,int id,int status,String safeword,float fee );
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
    public Map<String,Object> UpdateOrder_Amount(int uid,int id,int fee,ChangeOrderView changeOrderView);
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
    public Map<String,Object> Comment_Order(int uid,int source_id,String content,long picture,int rank,int rank4);
    /**
     * 订单退款
     * uid            用户id
     * id             订单id
     * status         订单状态 1:未付款 2:进行中 3:交易完成 4:申请退款 5:同意退款 6:拒绝退款
     * refund_fee     退款金额
     */
    public Map<String,Object> Order_refund(int uid,int id,int status,float refund_fee);

    //创建订单
    public void CreateOrder(int uid,int teacher_id,int requirement_id,float fee,int course_id,int grade_id,Requirdetailed requirdetailed,String address,double lat,double lng);
}
