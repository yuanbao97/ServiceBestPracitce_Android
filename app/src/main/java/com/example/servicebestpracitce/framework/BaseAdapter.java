package com.example.servicebestpracitce.framework;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, V extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<V>{
    protected final List<T> temp; // 用于保存修改之前的数据源的副本
    protected final List<T> datas; // 数据源
    public BaseAdapter(List<T> datas) {
        this.datas = datas;
        temp = new ArrayList<>(datas);
    }
    protected abstract boolean areItemsTheSame(T oldItem, T newItem);
    protected abstract boolean areContentsTheSame(T oldItem, T newItem);

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void notifyDiff() {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return temp.size();
            }
            @Override
            public int getNewListSize() {
                return datas.size();
            }
            // 判断是否是同一个 item
            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areItemsTheSame(temp.get(oldItemPosition), datas.get(newItemPosition));
            }
            // 如果是同一个 item 判断内容是否相同
            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areContentsTheSame(temp.get(oldItemPosition), datas.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
        // 通知刷新了之后，要更新副本数据到最新
        temp.clear();
        temp.addAll(datas);
    }
}
