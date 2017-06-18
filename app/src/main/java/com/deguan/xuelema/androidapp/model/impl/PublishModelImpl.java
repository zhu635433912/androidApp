package com.deguan.xuelema.androidapp.model.impl;

import com.deguan.xuelema.androidapp.model.BasePublishModel;
import com.deguan.xuelema.androidapp.model.PublishModel;

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
 * 创建时间：2017-06-17 13:51
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 13:51
 * 修改备注：
 */
public class PublishModelImpl extends BasePublishModel implements PublishModel {

    private static volatile PublishModelImpl instance = null;

    public static PublishModelImpl getInstance() {
        if (instance == null) {
            synchronized (PublishModelImpl.class) {
                if (instance == null) {
                    instance = new PublishModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getPublishData(Callback<ContentModle> callback, int uid, int filter_type) {
        service.getMyDemandlist(uid,filter_type).enqueue(callback);
    }
}
