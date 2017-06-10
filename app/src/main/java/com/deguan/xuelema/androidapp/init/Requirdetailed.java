package com.deguan.xuelema.androidapp.init;

import java.util.List;
import java.util.Map;

/**
 * 获取教师详细回调接口
 */

public interface Requirdetailed {
    //设置详细信息
    public void Updatecontent(Map<String,Object> map);
    //设置课程信息
    public void Updatefee(List<Map<String, Object>> listmap);
}
