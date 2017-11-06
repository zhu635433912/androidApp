package com.deguan.xuelema.androidapp.presenter.impl;

import android.util.Log;

import com.deguan.xuelema.androidapp.model.ModelFactory;
import com.deguan.xuelema.androidapp.presenter.PublishPresenter;
import com.deguan.xuelema.androidapp.viewimpl.MyPublishView;

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
 * 创建时间：2017-06-17 13:53
 * 修改人：zhuyunjian
 * 修改时间：2017-06-17 13:53
 * 修改备注：
 */
public class PublishPresenterImpl implements PublishPresenter {
    private MyPublishView myPublishView;
    private int uid;
    private int filter_type;
    private int page;

    public PublishPresenterImpl(MyPublishView myPublishView, int uid, int filter_type,int page) {
        this.myPublishView = myPublishView;
        this.uid = uid;
        this.filter_type = filter_type;
        this.page = page;
    }

    @Override
    public void getPublishEntity() {
        ModelFactory.getInstance().getPublishMdelImpl().getPublishData(new Callback<ContentModle>() {
            @Override
            public void onResponse(Call<ContentModle> call, Response<ContentModle> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    Log.e("aa","获取推荐教师成功");
                    if (response.body().getContent() .size() == 0) {
//                        myPublishView.failMyPublish("您还未发布");
                    }
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        list = response.body().getContent();
                        myPublishView.successMyPublish(list);

                }else {
                    myPublishView.failMyPublish("您还未发布");
                }
            }

            @Override
            public void onFailure(Call<ContentModle> call, Throwable t) {
                myPublishView.failMyPublish("网络错误");
            }
        },uid,filter_type,page);
    }
}
