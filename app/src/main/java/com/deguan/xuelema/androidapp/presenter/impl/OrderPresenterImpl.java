package com.deguan.xuelema.androidapp.presenter.impl;

import android.util.Log;

import com.deguan.xuelema.androidapp.model.ModelFactory;
import com.deguan.xuelema.androidapp.presenter.OrderPresenter;
import com.deguan.xuelema.androidapp.viewimpl.OrderView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.JieYse.ContentModle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
 * 创建时间：2017-06-17 09:14
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 09:14
 * 修改备注：
 */
public class OrderPresenterImpl implements OrderPresenter {
    private OrderView orderView;
    private int uid;
    private int filter_type;
    private int page;

    public OrderPresenterImpl(OrderView orderView, int uid, int filter_type, int page) {
        this.orderView = orderView;
        this.uid = uid;
        this.filter_type = filter_type;
        this.page = page;
    }

    @Override
    public void getOrderEntity() {
        ModelFactory.getInstance().getOrderModelImpl().getOrderData(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取推荐教师成功");
                    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                    list=response.body().getContent();
                    orderView.successOrder(list);
                }else {
                    orderView.failOrder("获取推荐教师失败");
                    Log.e("aa","获取推荐教师失败");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                orderView.failOrder("网络错误");
            }
        },uid,filter_type,page);
    }
    @Override
    public void getNofinishOrderEntity(int status) {
        ModelFactory.getInstance().getOrderModelImpl().getNoFinishOrderData(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取推荐教师成功");
                    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                    list=response.body().getContent();
                    orderView.successOrder(list);
                }else {
                    orderView.failOrder("获取推荐教师失败");
                    Log.e("aa","获取推荐教师失败");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                orderView.failOrder("网络错误");
            }
        },uid,filter_type,page,status);
    }

    @Override
    public void getEvaluateOrderEntity(int status, int order_rank) {
        ModelFactory.getInstance().getOrderModelImpl().getEvaluateOrderData(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取推荐教师成功");
                    List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
                    list=response.body().getContent();
                    orderView.successOrder(list);
                }else {
                    orderView.failOrder("获取推荐教师失败");
                    Log.e("aa","获取推荐教师失败");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                orderView.failOrder("网络错误");
            }
        },uid,filter_type,page,status,order_rank);
    }
}
