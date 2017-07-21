package modle.getdata;

import android.util.Log;

import com.deguan.xuelema.androidapp.entities.PayEntity;
import com.deguan.xuelema.androidapp.viewimpl.PayView;

import java.util.HashMap;
import java.util.Map;

import modle.MyHttp.Data;
import modle.MyUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
 * 创建时间：2017-06-28 12:50
 * 修改人：zhuyunjian
 * 修改时间：2017-06-28 12:50
 * 修改备注：
 */
public class PayUtil {

    private Map<String,Object> map;
    private Retrofit retrofit;
    private Data data;

    //初始化网络访问对象
    public PayUtil(){
        map=new HashMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data=retrofit.create(Data.class);
    }

    //获取订单详细
    public void getPayDetails(int id, final int paytype,int reward_fee ,final PayView payView){
        Call<PayEntity> call=data.getpayMsg(id,paytype,reward_fee);

        call.enqueue(new Callback<PayEntity>() {
            @Override
            public void onResponse(Call<PayEntity> call, Response<PayEntity> response) {
                Map<String,Object> maps=new HashMap<>();
                if (paytype == 1) {
                    maps = response.body().getContent();
                }else if (paytype == 0){
                    maps.put("info", response.body().getContent()+"");
                }else if (paytype == 2){
                    maps.put("error",response.body().getError()+"");
                }
                payView.successPay(maps);
            }

            @Override
            public void onFailure(Call<PayEntity> call, Throwable t) {
                Log.e("aa","获取订单错误");
            }
        });
    }

}
