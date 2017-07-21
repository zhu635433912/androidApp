package com.deguan.xuelema.androidapp.model.impl;

import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.entities.TuijianListEntity;
import com.deguan.xuelema.androidapp.model.BaseModel;
import com.deguan.xuelema.androidapp.model.TuijianModel;

import modle.JieYse.ContentModle;
import retrofit2.Callback;

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
 * 创建时间：2017-06-16 14:57
 * 修改人：zhuyunjian
 * 修改时间：2017-06-16 14:57
 * 修改备注：
 */
public class TuijianModelImpl extends BaseModel implements TuijianModel {

    private static volatile TuijianModelImpl instance = null;

    public static TuijianModelImpl getInstance() {
        if (instance == null) {
            synchronized (TuijianModelImpl.class) {
                if (instance == null) {
                    instance = new TuijianModelImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void getTuijianData(Callback<TuijianListEntity> callback, int uid, int grade_id, String address, String lat, String lng) {
//        service.gettuijianjiaoshi(course_id,grade_id,address,lat,lng);
        service.gettuijianjiaoshi(
                uid,
//                course_id,grade_id,
//                address,
                lat,lng).enqueue(callback);
    }
}
