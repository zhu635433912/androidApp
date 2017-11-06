package com.deguan.xuelema.androidapp.entities;

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
 * 创建时间：2017-09-23 15:29
 * 修改人：zhuyunjian
 * 修改时间：2017-09-23 15:29
 * 修改备注：
 */


public class MyXuqiuEntity {

    /**
     * error : ok
     * content : {"publisher_id":"74","publisher_name":"聪哥  大佬","publisher_headimg":"http://deguanjiaoyu.com/Uploads/AppImg/2017-08-05/598539ab57ec2.png","id":"40","content":"语文","grade_id":"38","grade_name":"初中 九年级","course_id":"208","course_name":"语文","gender":"3","age":"不限","education_id":"","address":"浙江省台州市路桥区横街镇新横大道90号","service_type":"4","service_type_txt":"不限","created":"2017-08-05 11:20","lng":"121.4184190000","lat":"28.5421400000","desc":"","start":"","end":"","duration":"","state":"路桥区","click":"8","low_price":"0.00","high_price":"0.00","teacher_version":""}
     */

    private String error;
    private ContentBean content;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * publisher_id : 74
         * publisher_name : 聪哥  大佬
         * publisher_headimg : http://deguanjiaoyu.com/Uploads/AppImg/2017-08-05/598539ab57ec2.png
         * id : 40
         * content : 语文
         * grade_id : 38
         * grade_name : 初中 九年级
         * course_id : 208
         * course_name : 语文
         * gender : 3
         * age : 不限
         * education_id :
         * address : 浙江省台州市路桥区横街镇新横大道90号
         * service_type : 4
         * service_type_txt : 不限
         * created : 2017-08-05 11:20
         * lng : 121.4184190000
         * lat : 28.5421400000
         * desc :
         * start :
         * end :
         * duration :
         * state : 路桥区
         * click : 8
         * low_price : 0.00
         * high_price : 0.00
         * teacher_version :
         */

        private String publisher_id;
        private String publisher_name;
        private String publisher_headimg;
        private String id;
        private String content;
        private String grade_id;
        private String grade_name;
        private String course_id;
        private String course_name;
        private String gender;
        private String age;
        private String education_id;
        private String address;
        private String service_type;
        private String service_type_txt;
        private String created;
        private String lng;
        private String lat;
        private String desc;
        private String start;
        private String end;
        private String duration;
        private String state;
        private String click;
        private String low_price;
        private String high_price;
        private String teacher_version;

        public String getPublisher_id() {
            return publisher_id;
        }

        public void setPublisher_id(String publisher_id) {
            this.publisher_id = publisher_id;
        }

        public String getPublisher_name() {
            return publisher_name;
        }

        public void setPublisher_name(String publisher_name) {
            this.publisher_name = publisher_name;
        }

        public String getPublisher_headimg() {
            return publisher_headimg;
        }

        public void setPublisher_headimg(String publisher_headimg) {
            this.publisher_headimg = publisher_headimg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getGrade_id() {
            return grade_id;
        }

        public void setGrade_id(String grade_id) {
            this.grade_id = grade_id;
        }

        public String getGrade_name() {
            return grade_name;
        }

        public void setGrade_name(String grade_name) {
            this.grade_name = grade_name;
        }

        public String getCourse_id() {
            return course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getEducation_id() {
            return education_id;
        }

        public void setEducation_id(String education_id) {
            this.education_id = education_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public String getService_type_txt() {
            return service_type_txt;
        }

        public void setService_type_txt(String service_type_txt) {
            this.service_type_txt = service_type_txt;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getClick() {
            return click;
        }

        public void setClick(String click) {
            this.click = click;
        }

        public String getLow_price() {
            return low_price;
        }

        public void setLow_price(String low_price) {
            this.low_price = low_price;
        }

        public String getHigh_price() {
            return high_price;
        }

        public void setHigh_price(String high_price) {
            this.high_price = high_price;
        }

        public String getTeacher_version() {
            return teacher_version;
        }

        public void setTeacher_version(String teacher_version) {
            this.teacher_version = teacher_version;
        }
    }
}
