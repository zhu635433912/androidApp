package com.deguan.xuelema.androidapp.presenter.impl;

import android.util.Log;

import com.deguan.xuelema.androidapp.entities.TuijianListEntity;
import com.deguan.xuelema.androidapp.model.ModelFactory;
import com.deguan.xuelema.androidapp.presenter.TuijianPresenter;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;

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
 * 创建时间：2017-06-16 15:15
 * 修改人：zhuyunjian
 * 修改时间：2017-06-16 15:15
 * 修改备注：
 */
public class TuijianPresenterImpl implements TuijianPresenter {
    private TuijianView tuijianView;
    private int uid;
    private int grade_id;
    private String address;
    private String lat;
    private String lng;

    public TuijianPresenterImpl(TuijianView tuijianView, int uid, int grade_id, String address, String lat, String lng) {
        this.tuijianView = tuijianView;
        this.uid = uid;
        this.grade_id = grade_id;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public void getTuijianEntity() {
        ModelFactory.getInstance().getTuijianModelImlp().getTuijianData(new Callback<TuijianListEntity>() {
            @Override
            public void onResponse(Call<TuijianListEntity> call, Response<TuijianListEntity> response) {
                String error=response.body().getError();
                if (error.equals("ok")){
                    if (response.body().getContent() .size() == 0){
                        tuijianView.failTuijian("无推荐教师");
                    }
                        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        list = response.body().getContent();
                        tuijianView.successTuijian(list);

                }else {
                    tuijianView.failTuijian("无推荐教师");
                    Log.e("aa","获取推荐教师失败");
                }
            }

            @Override
            public void onFailure(Call<TuijianListEntity> call, Throwable t) {
                tuijianView.failTuijian("网络错误");
            }
        },uid,grade_id,address,lat,lng);
    }
}
