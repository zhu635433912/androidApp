package com.deguan.xuelema.androidapp.utils;

import com.deguan.xuelema.androidapp.entities.SubjectEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 创建时间：2017-06-17 08:19
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 08:19
 * 修改备注：
 */
public class SubjectUtil {

    public static List<List<SubjectEntity>> getGrades(){
        List<List<SubjectEntity>> lists = new ArrayList<>();
        List<SubjectEntity> list = new ArrayList<>();
        List<SubjectEntity> list1 = new ArrayList<>();
        List<SubjectEntity> list2 = new ArrayList<>();
        List<SubjectEntity> list3 = new ArrayList<>();
        List<SubjectEntity> list4 = new ArrayList<>();

        list.add(new SubjectEntity("1" , "全年级"));
        list.add(new SubjectEntity("30" , "一年级"));
        list.add(new SubjectEntity("31" , "二年级"));
        list.add(new SubjectEntity("32" , "三年级"));
        list.add(new SubjectEntity("33" , "四年级"));
        list.add(new SubjectEntity("34" , "五年级"));
        list.add(new SubjectEntity("35" , "六年级"));


        list1.add(new SubjectEntity("2" , "全年级"));
        list1.add(new SubjectEntity("36" , "七年级"));
        list1.add(new SubjectEntity("37" , "八年级"));
        list1.add(new SubjectEntity("38" , "九年级"));


        list2.add(new SubjectEntity("3" , "全年级"));
        list2.add(new SubjectEntity("39" , "高一"));
        list2.add(new SubjectEntity("40" , "高二"));
        list2.add(new SubjectEntity("41" , "高三"));


        list3.add(new SubjectEntity("4" , "全年级"));

        list4.add(new SubjectEntity("5" , "不限"));

        lists.add(list);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);


        return lists;
    }

    public static List<String> getGrade(){
        List<String> list = new ArrayList<>();
        list.add("小学");
        list.add("初中");
        list.add("高中");
        list.add("大学");
        list.add("不限");

        return list;
    }

    public static List<String> getMenu(){
        List<String> list = new ArrayList<>();
        list.add("基础学科");
        list.add("书法美术");
        list.add("乐器");
        list.add("体育运动");
        list.add("舞蹈");
        list.add("武术");
        list.add("棋类");
        list.add("培训咨询 ");
        list.add("手工");
        list.add("表演声乐");
        return list;
    }

    public static List<List<SubjectEntity>> getChildList(){
        List<List<SubjectEntity>> lists = new ArrayList<>();
        List<SubjectEntity> list = new ArrayList<>();
        List<SubjectEntity> list1 = new ArrayList<>();
        List<SubjectEntity> list2 = new ArrayList<>();
        List<SubjectEntity> list3 = new ArrayList<>();
        List<SubjectEntity> list4 = new ArrayList<>();
        List<SubjectEntity> list5 = new ArrayList<>();
        List<SubjectEntity> list6 = new ArrayList<>();
        List<SubjectEntity> list7 = new ArrayList<>();
        List<SubjectEntity> list8 = new ArrayList<>();
        List<SubjectEntity> list9 = new ArrayList<>();

        list.add(new SubjectEntity("208" , "语文"));
        list.add(new SubjectEntity("212" , "数学"));
        list.add(new SubjectEntity("213" , "英语"));
        list.add(new SubjectEntity("215" , "科学"));
        list.add(new SubjectEntity("214" , "社会"));
        list.add(new SubjectEntity("221" , "政治"));
        list.add(new SubjectEntity("220" , "历史"));
        list.add(new SubjectEntity("216" , "地理"));
        list.add(new SubjectEntity("219" , "生物"));
        list.add(new SubjectEntity("217" , "物理"));
        list.add(new SubjectEntity("218" , "化学"));
        list.add(new SubjectEntity("222" , "奥数"));
        list.add(new SubjectEntity("223" , "作文"));
        list.add(new SubjectEntity("224" , "计算机"));
        list.add(new SubjectEntity("327" , "文综"));
        list.add(new SubjectEntity("328" , "理综"));

        list1.add(new SubjectEntity("239" , "软笔书法"));
        list1.add(new SubjectEntity("240" , "硬笔书法"));
        list1.add(new SubjectEntity("241" , "篆刻"));
        list1.add(new SubjectEntity("242" , "素描"));
        list1.add(new SubjectEntity("243" , "儿童画"));
        list1.add(new SubjectEntity("244" , "国画"));
        list1.add(new SubjectEntity("245" , "油画"));
        list1.add(new SubjectEntity("246" , "涂鸦"));
        list1.add(new SubjectEntity("247" , "漫画"));
        list1.add(new SubjectEntity("248" , "速写"));
        list1.add(new SubjectEntity("249" , "水粉"));

        list2.add(new SubjectEntity("209" , "钢琴"));
        list2.add(new SubjectEntity("225" , "吉他"));
        list2.add(new SubjectEntity("226" , "架子鼓"));
        list2.add(new SubjectEntity("230" , "古筝"));
        list2.add(new SubjectEntity("227" , "小提琴"));
        list2.add(new SubjectEntity("228" , "古琴"));
        list2.add(new SubjectEntity("229" , "萨克斯"));
        list2.add(new SubjectEntity("298" , "贝斯"));
        list2.add(new SubjectEntity("299" , "尤可里"));
        list2.add(new SubjectEntity("300" , "中提琴"));
        list2.add(new SubjectEntity("301" , "大提琴"));
        list2.add(new SubjectEntity("302" , "黑管"));
        list2.add(new SubjectEntity("303" , "小号"));
        list2.add(new SubjectEntity("304" , "长笛"));
        list2.add(new SubjectEntity("305" , "双簧管"));
        list2.add(new SubjectEntity("306" , "单簧管"));
        list2.add(new SubjectEntity("307" , "圆号"));
        list2.add(new SubjectEntity("308" , "口琴"));
        list2.add(new SubjectEntity("309" , "二胡"));
        list2.add(new SubjectEntity("310" , "电子琴"));
        list2.add(new SubjectEntity("311" , "双排琴"));
        list2.add(new SubjectEntity("312" , "手风琴"));
        list2.add(new SubjectEntity("313" , "脚踏琴"));
        list2.add(new SubjectEntity("314" , "琵琶"));
        list2.add(new SubjectEntity("315" , "扬琴"));
        list2.add(new SubjectEntity("316" , "葫芦丝"));
        list2.add(new SubjectEntity("317" , "萧"));
        list2.add(new SubjectEntity("318" , "竹笛"));

        list3.add(new SubjectEntity("232" , "篮球"));
        list3.add(new SubjectEntity("233" , "轮滑"));
        list3.add(new SubjectEntity("234" , "羽毛球"));
        list3.add(new SubjectEntity("274" , "游泳"));
        list3.add(new SubjectEntity("275" , "跑酷"));
        list3.add(new SubjectEntity("276" , "滑冰"));
        list3.add(new SubjectEntity("277" , "滑雪"));
        list3.add(new SubjectEntity("278" , "滑板"));
        list3.add(new SubjectEntity("279" , "足球"));
        list3.add(new SubjectEntity("280" , "网球"));
        list3.add(new SubjectEntity("281" , "乒乓球"));
        list3.add(new SubjectEntity("282" , "高尔夫"));
        list3.add(new SubjectEntity("283" , "桌球"));
        list3.add(new SubjectEntity("284" , "攀岩"));

        list4.add(new SubjectEntity("211" , "肚皮舞"));
        list4.add(new SubjectEntity("235" , "街舞"));
        list4.add(new SubjectEntity("236" , "爵士舞"));
        list4.add(new SubjectEntity("237" , "芭蕾"));
        list4.add(new SubjectEntity("285" , "健美操"));
        list4.add(new SubjectEntity("288" , "拉丁舞"));
        list4.add(new SubjectEntity("289" , "民族舞"));
        list4.add(new SubjectEntity("290" , "风情舞"));
        list4.add(new SubjectEntity("291" , "古典舞"));
        list4.add(new SubjectEntity("292" , "国际舞"));
        list4.add(new SubjectEntity("273" , "瑜伽"));

        list5.add(new SubjectEntity("255" , "跆拳道"));
        list5.add(new SubjectEntity("257" , "空手道"));
        list5.add(new SubjectEntity("258" , "太极"));
        list5.add(new SubjectEntity("259" , "散打"));
        list5.add(new SubjectEntity("260" , "拳击"));
        list5.add(new SubjectEntity("261" , "咏春"));
        list5.add(new SubjectEntity("262" , "柔道"));

        list6.add(new SubjectEntity("263" , "象棋"));
        list6.add(new SubjectEntity("264" , "国际象棋"));
        list6.add(new SubjectEntity("256" , "围棋"));

        list7.add(new SubjectEntity("252" , "培训咨询"));
        list7.add(new SubjectEntity("253" , "心理咨询"));
        list7.add(new SubjectEntity("254" , "拓展培训"));


        list8.add(new SubjectEntity("266" , "沙画"));
        list8.add(new SubjectEntity("267" , "航模"));
        list8.add(new SubjectEntity("268" , "木艺"));
        list8.add(new SubjectEntity("269" , "陶艺"));
        list8.add(new SubjectEntity("270" , "剪纸"));
        list8.add(new SubjectEntity("271" , "茶艺"));
        list8.add(new SubjectEntity("272" , "手工编织"));

        list9.add(new SubjectEntity("294" , "美声唱法"));
        list9.add(new SubjectEntity("295" , "民族唱法"));
        list9.add(new SubjectEntity("296" , "流行唱法"));
        list9.add(new SubjectEntity("297" , "少儿声乐"));
        list9.add(new SubjectEntity("321" , "演讲"));
        list9.add(new SubjectEntity("322" , "朗诵"));
        list9.add(new SubjectEntity("323" , "快板"));
        list9.add(new SubjectEntity("324" , "相声"));
        list9.add(new SubjectEntity("325" , "戏剧"));

        lists.add(list);
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);
        lists.add(list4);
        lists.add(list5);
        lists.add(list6);
        lists.add(list7);
        lists.add(list8);
        lists.add(list9);

        return lists;
    }

    public static List<SubjectEntity> getSubjectList(){
        List<SubjectEntity> list = new ArrayList<>();
//            list.add(new SubjectEntity("206" , "基础知识"));
            list.add(new SubjectEntity("208" , "语文"));
            list.add(new SubjectEntity("212" , "数学"));
            list.add(new SubjectEntity("213" , "英语"));
            list.add(new SubjectEntity("215" , "科学"));
            list.add(new SubjectEntity("214" , "社会"));
            list.add(new SubjectEntity("221" , "政治"));
            list.add(new SubjectEntity("220" , "历史"));
            list.add(new SubjectEntity("216" , "地理"));
            list.add(new SubjectEntity("219" , "生物"));
            list.add(new SubjectEntity("217" , "物理"));
            list.add(new SubjectEntity("218" , "化学"));
            list.add(new SubjectEntity("222" , "奥数"));
            list.add(new SubjectEntity("223" , "作文"));
            list.add(new SubjectEntity("224" , "计算机"));
            list.add(new SubjectEntity("327" , "文综"));
            list.add(new SubjectEntity("328" , "理综"));

            list.add(new SubjectEntity("239" , "软笔书法"));
            list.add(new SubjectEntity("240" , "硬笔书法"));
            list.add(new SubjectEntity("241" , "篆刻"));
            list.add(new SubjectEntity("242" , "素描"));
            list.add(new SubjectEntity("243" , "儿童画"));
            list.add(new SubjectEntity("244" , "国画"));
            list.add(new SubjectEntity("245" , "油画"));
            list.add(new SubjectEntity("246" , "涂鸦"));
            list.add(new SubjectEntity("247" , "漫画"));
            list.add(new SubjectEntity("248" , "速写"));
            list.add(new SubjectEntity("249" , "水粉"));



            list.add(new SubjectEntity("209" , "钢琴"));
            list.add(new SubjectEntity("225" , "吉他"));
            list.add(new SubjectEntity("226" , "架子鼓"));
            list.add(new SubjectEntity("230" , "古筝"));
            list.add(new SubjectEntity("227" , "小提琴"));
            list.add(new SubjectEntity("228" , "古琴"));
            list.add(new SubjectEntity("229" , "萨克斯"));
            list.add(new SubjectEntity("298" , "贝斯"));
            list.add(new SubjectEntity("299" , "尤可里"));
            list.add(new SubjectEntity("300" , "中提琴"));
            list.add(new SubjectEntity("301" , "大提琴"));
            list.add(new SubjectEntity("302" , "黑管"));
            list.add(new SubjectEntity("303" , "小号"));
            list.add(new SubjectEntity("304" , "长笛"));
            list.add(new SubjectEntity("305" , "双簧管"));
            list.add(new SubjectEntity("306" , "单簧管"));
            list.add(new SubjectEntity("307" , "圆号"));
            list.add(new SubjectEntity("308" , "口琴"));
            list.add(new SubjectEntity("309" , "二胡"));
            list.add(new SubjectEntity("310" , "电子琴"));
            list.add(new SubjectEntity("311" , "双排琴"));
            list.add(new SubjectEntity("312" , "手风琴"));
            list.add(new SubjectEntity("313" , "脚踏琴"));
            list.add(new SubjectEntity("314" , "琵琶"));
            list.add(new SubjectEntity("315" , "扬琴"));
            list.add(new SubjectEntity("316" , "葫芦丝"));
            list.add(new SubjectEntity("317" , "萧"));
            list.add(new SubjectEntity("318" , "竹笛"));

            list.add(new SubjectEntity("232" , "篮球"));
            list.add(new SubjectEntity("233" , "轮滑"));
            list.add(new SubjectEntity("234" , "羽毛球"));
            list.add(new SubjectEntity("274" , "游泳"));
            list.add(new SubjectEntity("275" , "跑酷"));
            list.add(new SubjectEntity("276" , "滑冰"));
            list.add(new SubjectEntity("277" , "滑雪"));
            list.add(new SubjectEntity("278" , "滑板"));
            list.add(new SubjectEntity("279" , "足球"));
            list.add(new SubjectEntity("280" , "网球"));
            list.add(new SubjectEntity("281" , "乒乓球"));
            list.add(new SubjectEntity("282" , "高尔夫"));
            list.add(new SubjectEntity("283" , "桌球"));
            list.add(new SubjectEntity("284" , "攀岩"));

            list.add(new SubjectEntity("211" , "肚皮舞"));
            list.add(new SubjectEntity("235" , "街舞"));
            list.add(new SubjectEntity("236" , "爵士舞"));
            list.add(new SubjectEntity("237" , "芭蕾"));
            list.add(new SubjectEntity("285" , "健美操"));
            list.add(new SubjectEntity("288" , "拉丁舞"));
            list.add(new SubjectEntity("289" , "民族舞"));
            list.add(new SubjectEntity("290" , "风情舞"));
            list.add(new SubjectEntity("291" , "古典舞"));
            list.add(new SubjectEntity("292" , "国际舞"));
            list.add(new SubjectEntity("273" , "瑜伽"));

            list.add(new SubjectEntity("255" , "跆拳道"));
            list.add(new SubjectEntity("257" , "空手道"));
            list.add(new SubjectEntity("258" , "太极"));
            list.add(new SubjectEntity("259" , "散打"));
            list.add(new SubjectEntity("260" , "拳击"));
            list.add(new SubjectEntity("261" , "咏春"));
            list.add(new SubjectEntity("262" , "柔道"));

            list.add(new SubjectEntity("263" , "象棋"));
            list.add(new SubjectEntity("264" , "国际象棋"));
            list.add(new SubjectEntity("256" , "围棋"));

            list.add(new SubjectEntity("252" , "培训咨询"));
            list.add(new SubjectEntity("253" , "心理咨询"));
            list.add(new SubjectEntity("254" , "拓展培训"));


            list.add(new SubjectEntity("266" , "沙画"));
            list.add(new SubjectEntity("267" , "航模"));
            list.add(new SubjectEntity("268" , "木艺"));
            list.add(new SubjectEntity("269" , "陶艺"));
            list.add(new SubjectEntity("270" , "剪纸"));
            list.add(new SubjectEntity("271" , "茶艺"));
            list.add(new SubjectEntity("272" , "手工编织"));

            list.add(new SubjectEntity("294" , "美声唱法"));
            list.add(new SubjectEntity("295" , "民族唱法"));
            list.add(new SubjectEntity("296" , "流行唱法"));
            list.add(new SubjectEntity("297" , "少儿声乐"));
            list.add(new SubjectEntity("321" , "演讲"));
            list.add(new SubjectEntity("322" , "朗诵"));
            list.add(new SubjectEntity("323" , "快板"));
            list.add(new SubjectEntity("324" , "相声"));
            list.add(new SubjectEntity("325" , "戏剧"));


            return list;
    }

    public static Map<String,String> getSubjects(){
        Map<String,String> map = new HashMap<>();
                map.put("206" , "基础知识");
                map.put("207" , "乐器");
                map.put("208" , "语文");
                map.put("209" , "钢琴");
                map.put("210" , "舞蹈");
                map.put("211" , "肚皮舞");
                map.put("212" , "数学");
                map.put("213" , "英语");
                map.put("214" , "社会");
                map.put("215" , "科学");
                map.put("216" , "地理");
                map.put("217" , "物理");
                map.put("218" , "化学");
                map.put("219" , "生物");
                map.put("220" , "历史");
                map.put("221" , "政治");
                map.put("222" , "奥数");
                map.put("223" , "作文");
                map.put("224" , "计算机");
                map.put("225" , "吉他");
                map.put("226" , "架子鼓");
                map.put("227" , "小提琴");
                map.put("228" , "古琴");
                map.put("229" , "萨克斯");
                map.put("230" , "古筝");
                map.put("231" , "体育运动");
                map.put("232" , "篮球");
                map.put("233" , "轮滑");
                map.put("234" , "羽毛球");
                map.put("235" , "街舞");
                map.put("236" , "爵士舞");
                map.put("237" , "芭蕾");
                map.put("238" , "书法美术");
                map.put("239" , "软笔书法");
                map.put("240" , "硬笔书法");
                map.put("241" , "篆刻");
                map.put("242" , "素描");
                map.put("243" , "儿童画");
                map.put("244" , "国画");
                map.put("245" , "油画");
                map.put("246" , "涂鸦");
                map.put("247" , "漫画");
                map.put("248" , "速写");
                map.put("249" , "水粉");
                map.put("250" , "武术");
                map.put("251" , "棋类");
                map.put("252" , "培训咨询");
                map.put("253" , "心理咨询");
                map.put("254" , "拓展培训");
                map.put("255" , "跆拳道");
                map.put("256" , "围棋");
                map.put("257" , "空手道");
                map.put("258" , "太极");
                map.put("259" , "散打");
                map.put("260" , "拳击");
                map.put("261" , "咏春");
                map.put("262" , "柔道");
                map.put("263" , "象棋");
                map.put("264" , "国际象棋");
                map.put("265" , "手工");
                map.put("266" , "沙画");
                map.put("267" , "航模");
                map.put("268" , "木艺");
                map.put("269" , "陶艺");
                map.put("270" , "剪纸");
                map.put("271" , "茶艺");
                map.put("272" , "手工编织");
                map.put("273" , "瑜伽");
                map.put("274" , "游泳");
                map.put("275" , "跑酷");
                map.put("276" , "滑冰");
                map.put("277" , "滑雪");
                map.put("278" , "滑板");
                map.put("279" , "足球");
                map.put("280" , "网球");
                map.put("281" , "乒乓球");
                map.put("282" , "高尔夫");
                map.put("283" , "桌球");
                map.put("284" , "攀岩");
                map.put("285" , "健美操");
                map.put("288" , "拉丁舞");
                map.put("289" , "民族舞");
                map.put("290" , "风情舞");
                map.put("291" , "古典舞");
                map.put("292" , "国际舞");
                map.put("293" , "表演声乐");
                map.put("294" , "美声唱法");
                map.put("295" , "民族唱法");
                map.put("296" , "流行唱法");
                map.put("297" , "少儿声乐");
                map.put("298" , "贝斯");
                map.put("299" , "尤可里");
                map.put("300" , "中提琴");
                map.put("301" , "大提琴");
                map.put("302" , "黑管");
                map.put("303" , "小号");
                map.put("304" , "长笛");
                map.put("305" , "双簧管");
                map.put("306" , "单簧管");
                map.put("307" , "圆号");
                map.put("308" , "口琴");
                map.put("309" , "二胡");
                map.put("310" , "电子琴");
                map.put("311" , "双排琴");
                map.put("312" , "手风琴");
                map.put("313" , "脚踏琴");
                map.put("314" , "琵琶");
                map.put("315" , "扬琴");
                map.put("316" , "葫芦丝");
                map.put("317" , "萧");
                map.put("318" , "竹笛");
                map.put("320" , "主持人");
                map.put("321" , "演讲");
                map.put("322" , "朗诵");
                map.put("323" , "快板");
                map.put("324" , "相声");
                map.put("325" , "戏剧");
                map.put("327" , "文综");
                map.put("328" , "理综");
        return map;
    }
}
