package control;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.deguan.xuelema.androidapp.init.Student_init;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;
import java.util.Map;

import modle.toos.MyListview;

/**
 *
 */

public interface Myconteol_init {
    /*
    获取listview
     * @param int   $uid    用户id
     * @param int   $lat    纬度
     * @param int   $lng    经度
     */
    public void setlist_a(int uid, int role , double lat, double ing, PullToRefreshListView listView,
                          Context context, int order, String state, int gender, int speciality, int grade_type,int order_rank,int page);

    public void huidiao(List<Map<String,Object>> listmap,int role, PullToRefreshListView listView,Context context);

    /*
    获取地区listview
    shen 省份
     */
    public void setlist_d(String shen,ListView listView, Context context);

    public void setlist_dqiandao(String shi,ListView listView, Context context);

    /*
    获取订单
     */
    public void getorderconlist(int uid,int tiaojian,int startu,int page,MyListview listView, Context context);

    public void getorder_huidiao(List<Map<String,Object>> listmap,  MyListview listView,Context context);


}
