package com.maxile.lesson.myapplication2.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxile.lesson.myapplication2.R;
import com.maxile.lesson.myapplication2.adapter.model.RecyclerAdapterModel;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView detailTextView;
        public MyViewHolder(View view) {
            super(view);

            this.titleTextView = (TextView)itemView.findViewById(R.id.title_adapter);
            this.detailTextView = (TextView)itemView.findViewById(R.id.detail_adapter);
        }
    }
    private List<RecyclerAdapterModel> items;
    public MyAdapter(List<RecyclerAdapterModel> items) {
        this.items = items;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter, parent, false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RecyclerAdapterModel sectionModel = items.get(position);
        holder.titleTextView.setText(sectionModel.title);
        holder.detailTextView.setText(sectionModel.detail);
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
