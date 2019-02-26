package com.yunzhou.tdinformation.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/*
 *  @项目名：  TDInformation 
 *  @包名：    com.yunzhou.tdinformation.base
 *  @文件名:   BaseRvAdapter
 *  @创建者:   lz
 *  @创建时间:  2018/10/16 14:12
 *  @描述：    TODO
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter {


    protected List<T> mItems;
    protected Context mContext;
    protected LayoutInflater mInflater;
    private OnItemClickListener onItemClickListener;
    private OnClickListener onClickListener;

    public BaseRvAdapter(Context context) {
        mItems = new ArrayList<>();
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        initListener();
    }
    private void initListener() {
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(int position, long itemId) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, itemId);
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setData(List<T> items) {
        if (items != null) {
            int previousSize = mItems.size();
            this.mItems.clear();
            notifyItemRangeRemoved(0 , previousSize);
            this.mItems.addAll(items);
            notifyItemRangeChanged(0 , mItems.size());

        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createHolderView(parent , viewType);
    }

    protected abstract RecyclerView.ViewHolder createHolderView(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (mItems.size() <= position){
            return;
        }
        holder.itemView.setTag(holder);
        holder.itemView.setOnClickListener(onClickListener);
        bindHolderView(holder , mItems.get(position), position);
    }
    // 可以在这分发出去有header的
    protected abstract void bindHolderView(RecyclerView.ViewHolder holder, T t, int position);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(List<T> items) {
        if (items != null) {
            this.mItems.addAll(items);
            notifyItemRangeInserted(this.mItems.size(), items.size());
        }
    }

    public final void addItem(T item) {
        if (item != null) {
            this.mItems.add(item);
            notifyItemChanged(mItems.size());
        }
    }
    public void addItem(int position, T item) {
        if (item != null) {
            this.mItems.add(position, item);
            notifyItemInserted(position);
        }
    }

    public void replaceItem(int position, T item) {
        if (item != null) {
            this.mItems.set(position, item);
            notifyItemChanged(position);
        }
    }

    public void updateItem(int position) {
        if (getItemCount() > position) {
            notifyItemChanged(position);
        }
    }


    public final void removeItem(T item) {
        if (this.mItems.contains(item)) {
            int position = mItems.indexOf(item);
            this.mItems.remove(item);
            notifyItemRemoved(position);
        }
    }

    public final void removeItem(int position) {
        if (this.getItemCount() > position) {
            this.mItems.remove(position);
            notifyItemRemoved(position);
        }
    }

    public final void resetItem(List<T> items) {
        if (items != null) {
            this.mItems.clear();
            addAll(items);
        }
    }

    public final void clear() {
        this.mItems.clear();
        notifyDataSetChanged();
    }

    public T getItem(int pos) {
        return mItems.get(pos);
    }

    public List<T> getItems() {
        return mItems;
    }

    public static abstract class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder holder = (RecyclerView.ViewHolder) v.getTag();
            onClick(holder.getAdapterPosition(), holder.getItemId());
        }

        public abstract void onClick(int position, long itemId);
    }

    public interface OnItemClickListener {
        void onItemClick(int position, long itemId);
    }
}
