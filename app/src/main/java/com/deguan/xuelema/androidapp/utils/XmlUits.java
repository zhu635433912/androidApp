package com.deguan.xuelema.androidapp.utils;

import android.content.Context;

import com.deguan.xuelema.androidapp.EntityToos.SAXTooas;
import com.deguan.xuelema.androidapp.entities.EntityProvince;

import java.util.List;


/**
 * Created by Administrator on 2017/6/30.
 */

public class XmlUits {

    private static volatile XmlUits instance = null;

    public static XmlUits getInstance() {
        if (instance == null) {
            synchronized (XmlUits.class) {
                if (instance == null) {
                    instance = new XmlUits();
                }
            }
        }
        return instance;
    }

    //获取省市区所有xml数据方法
    public  List<EntityProvince> getprovince(Context context){
        SAXTooas saxTooas=new SAXTooas(context);
        List<EntityProvince> entityProvinces=saxTooas.initProvinceData();
        return entityProvinces;
    }

}
