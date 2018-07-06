package com.annotation.android.annotation01.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by BlackHat on 6/26/2017.
 */

public class BaseViewWrapper<V extends View> extends RecyclerView.ViewHolder {
    private V view;
    public BaseViewWrapper(View itemView) {
        super(itemView);
        view = (V)itemView;
    }
    public V getView() {
        return view;
    }
}
