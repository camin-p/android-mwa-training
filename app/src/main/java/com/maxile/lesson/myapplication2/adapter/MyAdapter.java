package com.maxile.lesson.myapplication2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maxile.lesson.myapplication2.DetailActivity;
import com.maxile.lesson.myapplication2.R;
import com.maxile.lesson.myapplication2.adapter.model.RecyclerAdapterModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView detailTextView;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);

            this.titleTextView = (TextView)this.itemView.findViewById(R.id.title_adapter);
            this.detailTextView = (TextView)this.itemView.findViewById(R.id.detail_adapter);
            this.imageView = (ImageView)this.itemView.findViewById(R.id.adapter_image);
        }

        public void bind(final RecyclerAdapterModel model,final Context c){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Snackbar.make(view, model.title, Snackbar.LENGTH_LONG)
                    //        .setAction("Action", null).show();
                    Intent intent = new Intent(c, DetailActivity.class);
                    intent.putExtra("title",model.title);
                    intent.putExtra("cover_picture",model.cover_picture);
                    intent.putExtra("news",model.detail);
                    c.startActivity(intent);
                }
            });

        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    private List<RecyclerAdapterModel> items;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;

    private int visibleThreshold = 5;
    private OnLoadMoreListener onLoadMoreListener;
    private Context mContext;
    public MyAdapter(List<RecyclerAdapterModel> items, RecyclerView recyclerView,
                     Context context) {
        mContext = context;
        this.items = items;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                .getLayoutManager();
        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        totalItemCount = linearLayoutManager.getItemCount();
                        lastVisibleItem = linearLayoutManager
                                .findLastVisibleItemPosition();
                        if (!loading
                                && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                            // End has been reached
                            // Do something
                            if (onLoadMoreListener != null) {
                                onLoadMoreListener.onLoadMore();
                            }
                            loading = true;
                        }
                    }
                });
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_adapter, parent, false);
            return new MyViewHolder(view);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_adapter_progress, parent, false);

            return new ProgressViewHolder(v);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder)holder;
            final RecyclerAdapterModel sectionModel = items.get(position);
            myViewHolder.titleTextView.setText(sectionModel.title);
            myViewHolder.detailTextView.setText(sectionModel.detail);

            if (sectionModel.cover_picture.equals("")){
                sectionModel.cover_picture = "https://via.placeholder.com/100x100";
            }
            myViewHolder.bind(sectionModel,mContext);
            Picasso.get()
                    .load(sectionModel.cover_picture)
                    .into(myViewHolder.imageView);
        }else {
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }

    }
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
    public void setLoaded() {
        loading = false;
    }
    @Override
    public int getItemCount() {
        return items.size();
    }
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }
}
