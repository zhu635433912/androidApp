package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃　　　　　　　┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 * ━━━━━━神兽出没━━━━━━
 * <p>
 * 项目名称：androidApp
 * 类描述：
 * 创建人：zhuyunjian
 * 创建时间：2017-06-14 13:32
 * 修改人：zhuyunjian
 * 修改时间：2017-06-14 13:32
 * 修改备注：
 */
public class TeacherDbEntity {
    public static void main(String[] args) {
//        roomid: "80000",
//        roomname: "80000",
//        roompic: "",
//        roomrs: "300",
//        roompwd: "",
//        rscount: "201",
//        gateway:""
        Schema schema = new Schema(1,"com.deguan.xuelema.androidapp.entities");
        schema.setDefaultJavaPackageDao("com.deguan.xuelema.androidapp.dao");
        Entity teacherEntity = schema.addEntity("TeacherEntity");
        teacherEntity.addStringProperty("user_id");
        teacherEntity.addStringProperty("username");
        teacherEntity.addStringProperty("nickname");
        teacherEntity.addStringProperty("gender");
        teacherEntity.addStringProperty("education");
        teacherEntity.addStringProperty("user_headimg");
        teacherEntity.addStringProperty("date_joined");
        teacherEntity.addStringProperty("speciality");
        teacherEntity.addStringProperty("speciality_name");
        teacherEntity.addStringProperty("fee");
        teacherEntity.addStringProperty("years");
        teacherEntity.addStringProperty("apply_job");
        teacherEntity.addStringProperty("demand_fee");
        teacherEntity.addStringProperty("service_type");
        teacherEntity.addStringProperty("service_type_txt");
        teacherEntity.addStringProperty("grade_type_txt");
        teacherEntity.addStringProperty("is_passed");
        teacherEntity.addStringProperty("haoping_num");
        teacherEntity.addStringProperty("order_rank");
        teacherEntity.addStringProperty("distance");
        teacherEntity.addStringProperty("status1");
        teacherEntity.addStringProperty("status2");
        teacherEntity.addStringProperty("signature");
        try {
            DaoGenerator generator = new DaoGenerator();
            generator.generateAll(schema,"app/src/main/java");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
