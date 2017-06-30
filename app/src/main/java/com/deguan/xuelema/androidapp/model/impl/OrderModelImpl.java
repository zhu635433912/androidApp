package com.deguan.xuelema.androidapp.model.impl;

import com.deguan.xuelema.androidapp.model.BaseOrderModel;
import com.deguan.xuelema.androidapp.model.OrderModel;

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
 * 创建时间：2017-06-17 09:04
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 09:04
 * 修改备注：
 */
public class OrderModelImpl extends BaseOrderModel implements OrderModel {
    private static volatile OrderModelImpl instance = null;

    public static OrderModelImpl getInstance() {
        if (instance == null) {
            synchronized (OrderModelImpl.class) {
                if (instance == null) {
                    instance = new OrderModelImpl();
                }
            }
        }
        return instance;
    }
    @Override
    public void getOrderData(Callback<ContentModle> callback, int uid, int filter_type, int page) {
        service.getOredrlist(uid,filter_type,page).enqueue(callback);
    }
    @Override
    public void getNoFinishOrderData(Callback<ContentModle> callback, int uid, int filter_type, int page,int status) {
        service.getNofinishOredrlist(uid,filter_type,page,status).enqueue(callback);
    }
    @Override
    public void getEvaluateOrderData(Callback<ContentModle> callback, int uid, int filter_type, int page,int status,int order_rank) {
        service.getEvaluateOredrlist(uid,filter_type,page,status,order_rank).enqueue(callback);
    }
}
