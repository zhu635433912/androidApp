package com.deguan.xuelema.androidapp.fragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by jash
 * Date: 16-2-19
 * Time: 上午11:47
 */
public class BarAdapter extends RecyclerView.Adapter<BarAdapter.BarViewHolder>{
    private Context context;
    private List<String> list;

    public BarAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public BarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new BarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BarViewHolder holder, int position) {
        holder.text.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BarViewHolder extends RecyclerView.ViewHolder{

        private TextView text;

        public BarViewHolder(View itemView) {
            super(itemView);
            text = ((TextView) itemView.findViewById(android.R.id.text1));
        }
    }
}
