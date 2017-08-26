package com.example;

import java.io.IOException;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class DbEntity {
    public static void main(String[] args) {
//        roomid: "80000",
//        roomname: "80000",
//        roompic: "",
//        roomrs: "300",
//        roompwd: "",
//        rscount: "201",
//        gateway:""
        Schema schema = new Schema(2,"com.deguan.xuelema.androidapp.entities");
        schema.setDefaultJavaPackageDao("com.deguan.xuelema.androidapp.dao");
        Entity xuqiuEntity = schema.addEntity("XuqiuEntity");
        xuqiuEntity.addStringProperty("publisher_id");
        xuqiuEntity.addStringProperty("publisher_name");
        xuqiuEntity.addStringProperty("publisher_headimg");
        xuqiuEntity.addStringProperty("publisher_gender");
        xuqiuEntity.addStringProperty("id");
        xuqiuEntity.addStringProperty("content");
        xuqiuEntity.addStringProperty("address");
        xuqiuEntity.addStringProperty("service_type");
        xuqiuEntity.addStringProperty("service_type_txt");
        xuqiuEntity.addStringProperty("grade_id");
        xuqiuEntity.addStringProperty("grade_name");
        xuqiuEntity.addStringProperty("course_id");
        xuqiuEntity.addStringProperty("course_name");
        xuqiuEntity.addStringProperty("education_id");
        xuqiuEntity.addStringProperty("education_name");
        xuqiuEntity.addStringProperty("fee");
        xuqiuEntity.addStringProperty("duration");
        xuqiuEntity.addStringProperty("gender");
        xuqiuEntity.addStringProperty("age");
        xuqiuEntity.addStringProperty("created");
        xuqiuEntity.addStringProperty("lng");
        xuqiuEntity.addStringProperty("lat");
        xuqiuEntity.addStringProperty("distance");
        xuqiuEntity.addStringProperty("status");


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
        teacherEntity.addStringProperty("click");
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
