package view.mywode;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.deguan.xuelema.androidapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import modle.toos.MyListview;

/**
 * 推荐需求 我的发布 碎片
 */

public class Fabuxuqiu_fagment extends Fragment implements MyListview.IReflashListener,MyListview.RemoveListener {
    List<Map<String,Object>> listmap;
    MyListview listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.myindex_list_foagment,null);
        listView= (MyListview)view.findViewById(R.id.listmapsi);
        listView.setInterface(this);
        listmap=new ArrayList<Map<String, Object>>();

        //取消listview下拉上啦默认
        listView.setOverScrollMode(view.OVER_SCROLL_NEVER);
        listView.setVerticalScrollBarEnabled(false);
        listView.setRemoveListener(this);



        SimpleAdapter simpleAdapter=new SimpleAdapter(getActivity(),Data(),R.layout.myindex_list,new String[]{"text"},new int[]{R.id.myindeusername});
        listView.setAdapter(simpleAdapter);

        return view;
    }

    private List<Map<String,Object>> Data() {
        for (int i=0;i<5;i++){
            Map<String,Object> map=new ArrayMap<>();
            map.put("text",i+"玩家");
            listmap.add(map);
        }
        return listmap;
    }

    @Override
    public void onReflash() {
        listView.reflashComplet();
    }

    @Override
    public void removeItem(MyListview.RemoveDirection direction, int position) {

    }
}
