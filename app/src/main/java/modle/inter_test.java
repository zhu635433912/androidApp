package modle;



import java.util.Map;

import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.Teacher_Modle.Teacher_init;
import modle.user_Modle.User_init;

/**
 * 测试类
 */

public class inter_test {
    private Map<String,Object> map;
    private User_init user;
    private Teacher_init teacher_init;

    public void testLogin(){
        /**
         * 登陆
        user=new User_Realization();
        user.Registerlogin("15157269365","1205615021");
         */


        /**发送验证
         * signup:注册用户 forget:忘记密码
        Onteh_init onteh_init=new Onteh();
        onteh_init.getyzm("15157269367","signup");
         */

        /**注册用户
         * 1:普通用户 2:教师 3:运营 4:管理员
        user=new User_Realization();
        user.RegisteredUser(1,"15157269366","123456","6666");
         */

        /**重置密码
        user=new User_Realization();
        user.Resetpassword("15157269366","123456","6666");
         */

        /**修改密码
        user=new User_Realization();
        user.Modifypassword(556,"123456","1205615021");
         */

        /**获取用户资料
        user=new User_Realization();
        user.User_Data(556);
         */

        /**更新用户资料 资料无法更新有问题待测试
         user=new User_Realization();
         map=new ArrayMap<String,Object>();
         map.put("signature","一个爱搞事的男孩");
         List<Map<String,Object>> listmap=new ArrayList<Map<String, Object>>();
         user.Updatedata(524,listmap);
         */


        //------------------------------------------分割线

        /**获取教师个人资料
        teacher_init=new Teacher();
        teacher_init.Get_Teacher(365);
         */

        /**获取教师详细资料
        teacher_init=new Teacher();
        teacher_init.Get_Teacher_detailed(524,365);
         */

        /**获取教师列表
         * 有问题 服务器是接受类型int
          teacher_init=new Teacher();
         teacher_init.Get_Teacher_list(364,121.381298,28.597213);
         */

        /**更新教师资料
        teacher_init=new Teacher();
        teacher_init.Teacher_update(369);
         */

        /**教师列表显示状态更改
        teacher_init=new Teacher();
        teacher_init.Teacher_Change(524,1);
         */

        /**招聘列表显示状态更改
        teacher_init=new Teacher();
        teacher_init.Teacher_recruit(524,1);
         */

        //-----------------------------------------分割线

        /**获取订单列表
        Order_init order_init=new Order();
        order_init.getOrder_list(524,0,1,1);
        */

        /**获取单一列表
        Order_init order_init=new Order();
        order_init.getOrder_danyilist(556,474);
         */

        /**
         * 创建订单
        Order_init order_init=new Order();
        order_init.Establish_Order(524,364,680,80);
         */

        /**删除订单
        Order_init order_init=new Order();
        order_init.Delete_Order(364,680);
         */

       /**更新订单状态
        Order_init order_init=new Order();
        order_init.Update_Order(364,680,2);
        */

        /**更新订单评分
        Order_init order_init=new Order();
        order_init.UpdateOrder_score(364,680,1,2,3,3);
         */

        /**更新订单金额
        Order_init order_init=new Order();
        order_init.UpdateOrder_Amount(364,680,600);
         */

        /**更新课时数费用
         Order_init order_init=new Order();
         order_init.UpdateOrder_Classhour(364,680,600);
         */

        /**评论订单
        Order_init order_init=new Order();
        long ac=789;
        order_init.Comment_Order(524,672,aa,ac);
         */

        /**订单退款
        Order_init order_init=new Order();
        float aa=1111;
        order_init.Order_refund(364,672,5,aa);
         */
        //----------------------------------分割线


        /** 获取想
         * Demand_init demand_init=new Demand();
         demand_init.getDemand_list(524,0,0,"2016-08-10",0,1);
         */

        /**获取单一需求
        Demand_init demand_init=new Demand();
        demand_init.getDemand_danysi(524,680);
         */

        /**发布需求
        Demand_init demand_init=new Demand();
        float i=60;
        map=new ArrayMap<String, Object>();
        map.put("start","2016-08-10 12:00:00");
        map.put("end","2017-02-10 20:00:00");
        demand_init.ReleaseDemand(556,"学编程",i,3,2,1,18,2,"浙江省","台州市","路桥区",1,map);
         */

    }

}
