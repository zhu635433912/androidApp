package view.fee;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.deguan.xuelema.androidapp.Payment_Activty;
import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.init.Requirdetailed;

import java.util.List;
import java.util.Map;

import modle.alipay.PayResult;
import modle.getdata.Getdata;
import modle.user_ziliao.User_id;

/**
 * 充值碎片
 */

public class  Feechongzhi_fagment extends Fragment implements Requirdetailed ,View.OnClickListener{
    private TextView feequeding;
    private EditText chongzhijine;
    private int uid;
    //支付宝回调
    private final int SDK_PAY_FLAG = 1;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult=new PayResult((Map<String,String>) msg.obj);


            String resultInfo=payResult.getResult();//同步返回结果
            String resultStatus=payResult.getResultStatus();//同步返回结果

            switch (resultStatus){
                case "9000":
                    Toast.makeText(getActivity(),"订单支付成功",Toast.LENGTH_SHORT).show();
                    break;
                case "8000":
                    Toast.makeText(getActivity(),"正在处理中，支付结果未知（有可能已经支付成功）!",Toast.LENGTH_SHORT).show();
                    break;
                case "4000":
                    Toast.makeText(getActivity(),"订单支付失败",Toast.LENGTH_SHORT).show();
                    break;
                case "5000":
                    Toast.makeText(getActivity(),"重复请求",Toast.LENGTH_SHORT).show();
                    break;

                case "6001":
                    Toast.makeText(getActivity(),"取消支付",Toast.LENGTH_SHORT).show();
                    break;
                case "6002":
                    Toast.makeText(getActivity(),"网络连接出错",Toast.LENGTH_SHORT).show();
                    break;
                case "6004":
                    Toast.makeText(getActivity(),"支付结果未知（有可能已经支付成功）",Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.feechongzhi,null);
        feequeding= (TextView) view.findViewById(R.id.feequeding);
        chongzhijine= (EditText) view.findViewById(R.id.chongzhijine);
        uid=Integer.parseInt(User_id.getUid());

        feequeding.setOnClickListener(this);
        return view;
    }

    @Override
    public void Updatecontent(Map<String, Object> map) {

        //获取去服务器返回的支付宝订单信息再去唤起支付宝
        String info=(String) map.get("info");
        String ordert=info.substring(13);
        final String orderInfo = ordert;

        Log.e("aa",ordert);
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                Log.e("aa","唤起支付宝");
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;

                //开启回调获取支付结果
                mHandler.sendMessage(msg);

            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @Override
    public void Updatefee(List<Map<String, Object>> listmap) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feequeding:
                int fee=Integer.parseInt(chongzhijine.getText().toString());
                float flfee=fee;
                Getdata getdata=new Getdata();
                getdata.getsizechongzhi(uid,flfee,1,this);
                break;
        }
    }
}
