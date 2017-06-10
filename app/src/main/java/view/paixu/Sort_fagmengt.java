package view.paixu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.deguan.xuelema.androidapp.R;

/**
 * 星级排序
 */

public class Sort_fagmengt extends Fragment implements View.OnClickListener {
    private TextView xinjipaixu;
    private TextView jiagepaixu;
    private TextView renqipaixu;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.sort,null);

        xinjipaixu= (TextView) view.findViewById(R.id.xinjipaixu);
        jiagepaixu= (TextView) view.findViewById(R.id.jiagepaixu);
        renqipaixu= (TextView) view.findViewById(R.id.renqipaixu);

        renqipaixu.setOnClickListener(this);
        jiagepaixu.setOnClickListener(this);
        xinjipaixu.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.xinjipaixu:
                //星级排序

                break;
            case R.id.jiagepaixu:
                //价格排序

                break;
            case R.id.renqipaixu:
                //人气排序

                break;
        }
    }
}
