package com.annotation.android.annotation01.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import com.annotation.android.annotation01.viewholders.BaseViewWrapper;


/**
 * Created by BlackHat on 6/26/2017.
 */

public abstract class BaseRecyclerViewAdapter<T, V extends View> extends RecyclerView.Adapter<BaseViewWrapper<V>>{

    protected List<T> items =  new ArrayList<T>();

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public BaseViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewWrapper<V>(onCreateItemView(parent, viewType));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    protected abstract List<T> getItems();

    protected abstract boolean addItems(T item);

    public abstract void addAllItems(List<T> item);

    public abstract boolean removeItem(T item);

    public abstract int getPosition(T item);

    public abstract T getItem(int position);

}
