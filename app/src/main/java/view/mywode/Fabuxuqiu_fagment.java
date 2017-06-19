package view.mywode;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.deguan.xuelema.androidapp.R;
import com.deguan.xuelema.androidapp.UserxinxiActivty;
import com.deguan.xuelema.androidapp.Xuqiuxiangx;
import com.deguan.xuelema.androidapp.entities.XuqiuEntity;
import com.deguan.xuelema.androidapp.viewimpl.SimilarXuqiuView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modle.Adapter.XuqiuAdapter;
import modle.Demand_Modle.Demand;
import modle.Demand_Modle.Demand_init;
import modle.toos.MyListview;

/**
 * 推荐需求 我的发布 碎片
 */

public class Fabuxuqiu_fagment extends Fragment implements
//        PullToRefreshBase.OnRefreshListener,
        SimilarXuqiuView{
    private List<XuqiuEntity> listmap = new ArrayList<>();
    ListView listView;
    private XuqiuAdapter xuqiuAdapter;
    private Demand_init demand_init;

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

         View view=inflater.inflate(R.layout.fragment_tuijian_xuqiu,null);
        listView= (ListView) view.findViewById(R.id.xuqiu_tuijian_listview);


        //取消listview下拉上啦默认
        //相似需求
//        listView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        listView.setOnRefreshListener(this);
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, Data(), R.layout.myindex_list, new String[]{"text"}, new int[]{R.id.myindeusername});
        xuqiuAdapter = new XuqiuAdapter(listmap,getActivity());
        listView.setAdapter(xuqiuAdapter);

        //需求详细信息展示
        demand_init=new Demand(this);
        demand_init.getTuijianDemand_list(0,0,null,null,null,null,null,null,getContext(),null);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), Xuqiuxiangx.class);
                intent.putExtra("user_id",listmap.get(position).getId());
                intent.putExtra("fee",listmap.get(position).getFee());
                intent.putExtra("publisher_id",listmap.get(position).getPublisher_id());
//                Intent intent = new Intent(getActivity(), UserxinxiActivty.class);
////                    intent.putExtra("user_id", uid);
//                intent.putExtra("head_image",listmap.get(position).getPublisher_headimg());
//                intent.putExtra("user_id",listmap.get(position).getPublisher_id());
                startActivity(intent);
            }
        });
        return view;
    }

    private String status;
    @Subscriber(tag = "status")
    public void getTuijian(String stats){
        status = stats;
        demand_init.getTuijianDemand_list(0,0,null,null,null,null,status,null,getContext(),null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void successSimilarXuqiu(List<Map<String, Object>> maps) {
//        listView.onRefreshComplete();
        listmap.clear();
        List<XuqiuEntity> lists = new ArrayList<>();
        for (int i = 0; i < maps.size(); i++) {
            XuqiuEntity entity = new XuqiuEntity();
            entity.setPublisher_id((String) maps.get(i).get("publisher_id"));
            entity.setPublisher_name((String) maps.get(i).get("publisher_name"));
            entity.setService_type((String)maps.get(i).get("service_type"));
            entity.setService_type_txt((String) maps.get(i).get("service_type_txt"));
            entity.setCourse_name((String) maps.get(i).get("course_name"));
            entity.setContent((String) maps.get(i).get("content"));
            entity.setCreated((String) maps.get(i).get("created"));
            entity.setId((String) maps.get(i).get("id"));
            entity.setPublisher_headimg((String) maps.get(i).get("publisher_headimg"));
            entity.setDistance((String) maps.get(i).get("distance"));
            entity.setFee(String.valueOf(maps.get(i).get("fee")));
            entity.setGrade_name((String)maps.get(i).get("grade_name"));
            lists.add(entity);
        }
        listmap.addAll(lists);
        xuqiuAdapter.notifyDataSetChanged();
    }

    @Override
    public void failSimilarXuqiu(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onRefresh(PullToRefreshBase refreshView) {
//        demand_init.getTuijianDemand_list(0,0,null,null,null,null,status,listView,getContext(),null);
//    }
}
