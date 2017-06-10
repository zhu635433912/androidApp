package modle.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/5.
 */

public class My_PagerAdapter extends PagerAdapter {
    private List<View> listview;

    public My_PagerAdapter(List<View> listview){
        this.listview=listview;
    }

    @Override
    public int getCount() {
        return listview.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object){
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(listview.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {


        container.addView(listview.get(position));

        return listview.get(position);
    }
}
