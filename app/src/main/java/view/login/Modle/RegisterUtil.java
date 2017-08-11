package view.login.Modle;

import java.util.HashMap;
import java.util.Map;

import modle.JieYse.User_Modle;
import modle.MyUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
 * 创建时间：2017-08-08 11:03
 * 修改人：zhuyunjian
 * 修改时间：2017-08-08 11:03
 * 修改备注：
 */


public class RegisterUtil {

    private Map<String,Object> map;
    private Retrofit retrofit;
    private RegisterApi data;

    //初始化网络访问对象
    public RegisterUtil(){
        map=new HashMap<String,Object>();

        retrofit=new Retrofit.Builder().baseUrl(MyUrl.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        data=retrofit.create(RegisterApi.class);
    }

    public void getLogin(String username, String password, final MobileView registerView){
        Call<RegisterEntity> call = data.getLoginEntity(username,password);
        call.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                if (response.body().getError().equals("ok")) {
                    registerView.successRegister(response.body().getUser_id());
                }else {
                    registerView.failRegister(response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {
                registerView.failRegister("网络错误");
            }
        });
    }

    public void getMobileNum(String mobile, final RegisterView registerView){
        Call<User_Modle> call = data.getMobileNumber(mobile,"signup");
        call.enqueue(new Callback<User_Modle>() {
            @Override
            public void onResponse(Call<User_Modle> call, Response<User_Modle> response) {
                if (response.body().getError().equals("ok")) {
                    registerView.successMobile(response.body().getContent());
                }else {
                    registerView.failMobile(response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<User_Modle> call, Throwable t) {
                registerView.failMobile("网络错误");
            }
        });
    }
    public void getRegisterEntity(int role, String username, String password, String yzm, String inv_code, final MobileView mobileView){
        Call<RegisterEntity> call = data.getRegisterEntity(role,username,password,yzm,inv_code);
        call.enqueue(new Callback<RegisterEntity>() {
            @Override
            public void onResponse(Call<RegisterEntity> call, Response<RegisterEntity> response) {
                if (response.body().getError().equals("ok")) {
                    mobileView.successRegister(response.body().getUser_id());
                }else {
                    mobileView.failRegister(response.body().getErrmsg());
                }
            }

            @Override
            public void onFailure(Call<RegisterEntity> call, Throwable t) {
                mobileView.failRegister("网络错误");
            }
        });
    }
}
