package modle.Gaode;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.deguan.xuelema.androidapp.init.Gaodehuidiao_init;
import com.deguan.xuelema.androidapp.init.Requirdetailed;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */

public class Gaode_dinwei {
    private Gaodehuidiao_init requirdetailed;
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    Log.e("aa","定位成功");
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("Province",aMapLocation.getProvince().toString());
                    map.put("City",aMapLocation.getCity().toString());
                    map.put("District",aMapLocation.getDistrict().toString());
                    map.put("lat",aMapLocation.getLatitude());
                    map.put("lng",aMapLocation.getLongitude());
                    map.put("address",aMapLocation.getProvince().toString() + aMapLocation.getCity().toString()
                            + aMapLocation.getDistrict().toString() + aMapLocation.getStreet().toString() +
                            aMapLocation.getStreetNum().toString());
                    requirdetailed.Updategaode(map);

                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("aa","定位失败");
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                    Map<String,Object> map=new HashMap<String, Object>();
                    map.put("error",aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
                    requirdetailed.Updatecuowu(map);

                }
            }
        }
    };
    private Context context;

    public Gaode_dinwei(Gaodehuidiao_init requirdetailed, Context context){
        this.requirdetailed=requirdetailed;
        this.context=context;
        stegodin();
    }



    private void stegodin(){

        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        Log.e("aa","启动定位");

    }

}
