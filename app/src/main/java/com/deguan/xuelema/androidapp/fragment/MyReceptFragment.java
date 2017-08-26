package com.deguan.xuelema.androidapp.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.entities.TuijianEntity;
import com.deguan.xuelema.androidapp.presenter.impl.OrderPresenterImpl;
import com.deguan.xuelema.androidapp.presenter.impl.TuijianPresenterImpl;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.deguan.xuelema.androidapp.viewimpl.TuijianView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.DmadAdapter;
import modle.Adapter.TuijianNewAdapter;
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.user_ziliao.User_id;

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
 * 创建时间：2017-07-22 13:29
 * 修改人：zhuyunjian
 * 修改时间：2017-07-22 13:29
 * 修改备注：
 */

@EFragment(R.layout.teacher_list_fragement)
public class MyReceptFragment  extends BaseFragment implements SimilarXuqiuView, SwipeRefreshLayout.OnRefreshListener {

    private List<Map<String,Object>> list=new ArrayList<>();
    private int uid;
    private DmadAdapter dmadAdapter;
    private Demand_init demand;
    private int page = 1;
    private boolean isLoading = false;

    @ViewById
    SwipeRefreshLayout tacher_top_list_swipe;

    @ViewById
    RecyclerView tacher_fragment_list;


    @Override
    public void initView() {
        //设置布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        tacher_fragment_list.setLayoutManager(layoutManager);
        dmadAdapter=new DmadAdapter(getContext(),list);
        tacher_fragment_list.setAdapter(dmadAdapter);

        dmadAdapter.setOnItemClickListener(new DmadAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String publisher_id=list.get(position).get("publisher_id")+"";
                String user_id=list.get(position).get("id")+"";
                String fee=list.get(position).get("fee")+"";
//                Toast.makeText(getActivity(),publisher_id,Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getActivity(), Xuqiuxiangx.class);
                intent.putExtra("publisher_id",publisher_id);
                intent.putExtra("user_id",user_id);
                intent.putExtra("fee",fee);
                intent.putExtra("course_id",list.get(position).get("course_id").toString());
                intent.putExtra("grade_id",list.get(position).get("grade_id").toString());
                startActivity(intent);
            }
        });

        tacher_fragment_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    RecyclerView.Adapter adapter1 = recyclerView.getAdapter();
                    View childAt = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
                    int position = recyclerView.getChildAdapterPosition(childAt);
                    if (adapter1.getItemCount() - position < 5) {
                        isLoading = true;
                        page++;
                        demand.getReceptDemand(Integer.parseInt(User_id.getUid()),page);
                    }
                }
            }
        });
        tacher_top_list_swipe.setOnRefreshListener(this);
    }

    @Override
    public void initData() {
        //        xuqiuAdapter = new XuqiuAdapter(list,getActivity());
        uid=Integer.parseInt(User_id.getUid());
        demand=new Demand(this);
        if (list.size() > 0){

        }else {
            demand.getReceptDemand(Integer.parseInt(User_id.getUid()),page);
        }

        tacher_top_list_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                demand.getReceptDemand(Integer.parseInt(User_id.getUid()),page);
            }
        });
    }


    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        if (maps != null) {
            if (page == 1) {
                list.clear();
            }
            for (int i = 0; i < maps.size(); i++) {
                if ((maps.get(i).get("status")).equals("1")||maps.get(i).get("status").equals("2")){
                    continue;
                }
                list.add(maps.get(i));
            }
            isLoading = false;
            dmadAdapter.notifyDataSetChanged();
        }

//        list.addAll(maps);
        tacher_top_list_swipe.setRefreshing(false);
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        tacher_top_list_swipe.setRefreshing(false);
        Toast.makeText(getActivity(),msg+"!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        page = 1;
        demand.getReceptDemand(Integer.parseInt(User_id.getUid()),page);
    }
}
