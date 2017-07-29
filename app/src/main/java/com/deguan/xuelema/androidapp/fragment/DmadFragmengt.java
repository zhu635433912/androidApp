package com.deguan.xuelema.androidapp.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.init.Student_init;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.Adapter.DmadAdapter;
import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.user_ziliao.User_id;

/**
 * 推荐需求
 * Created by Administrator on 2017/6/22 0022.
 */
@EFragment(R.layout.teacher_list_fragement)
public class DmadFragmengt extends BaseFragment implements SimilarXuqiuView {

    private List<Map<String,Object>> list=new ArrayList<>();
    private int uid;
    private XuqiuAdapter xuqiuAdapter;
    private DmadAdapter dmadAdapter;
    private Demand_init demand;

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
    }

    @Override
    public void initData() {
        //        xuqiuAdapter = new XuqiuAdapter(list,getActivity());
            uid=Integer.parseInt(User_id.getUid());
        demand=new Demand(this);
        if (list.size() > 0){

        }else {
            demand.getTuijianDemand_list(0,User_id.getUid(),User_id.getLat()+"",""+User_id.getLng(),"",null,null,null,null,null);
        }

        tacher_top_list_swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    demand.getTuijianDemand_list(0,User_id.getUid(),User_id.getLat()+"",""+User_id.getLng(),"",null,null,null,null,null);
                }
            });
    }


    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
        list.clear();
        list.addAll(maps);
        dmadAdapter.notifyDataSetChanged();
        tacher_top_list_swipe.setRefreshing(false);
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        tacher_top_list_swipe.setRefreshing(false);
        Toast.makeText(getActivity(),msg+"!",Toast.LENGTH_LONG).show();
    }
}
