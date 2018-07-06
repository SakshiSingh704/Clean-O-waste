package com.annotation.android.annotation01.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import java.util.List;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.interfaces.selectPost;
import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.annotation.android.annotation01.viewholders.BaseViewWrapper;
import com.annotation.android.annotation01.viewholders.ItemRowMyPost;
import com.annotation.android.annotation01.viewholders.ItemRowMyPost_;


/**
 * Created by BlackHat on 6/26/2017.
 */

@EBean
public class BindMyPostRecyclerAdapter extends BaseRecyclerViewAdapter<GetPostListModal,ItemRowMyPost> {

    selectPost selectPostObject;

    public selectPost getSelectPostObject() {
        return selectPostObject;
    }

    public void setSelectPostObject(selectPost selectPostObject) {
        this.selectPostObject = selectPostObject;
    }

    @RootContext
    Context mContext;

    @Override
    protected ItemRowMyPost onCreateItemView(ViewGroup parent, int viewType) {
        return ItemRowMyPost_.build(mContext);
    }

    @Override
    protected List<GetPostListModal> getItems() {
        return items;
    }

    @Override
    protected boolean addItems(GetPostListModal item) {
        return items.add(item);
    }

    @Override
    public void addAllItems(List<GetPostListModal> item) {
        this.items = item;
    }

    @Override
    public boolean removeItem(GetPostListModal item) {
        return items.remove(item);
    }

    @Override
    public int getPosition(GetPostListModal item) {
        for (int i = 0; i < items.size(); i++) {
            if (getItem(i).equals(item)) {
                return i;
            }
        }
        return 0;
    }

    public boolean getItem(Object item) {
        for (int i = 0; i < items.size(); i++) {
            if (getItem(i).equals(item)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GetPostListModal getItem(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(BaseViewWrapper<ItemRowMyPost> itemRowMyApplicationBaseViewWrapper, int position) {

        final ItemRowMyPost viewHolder = itemRowMyApplicationBaseViewWrapper.getView();
        final GetPostListModal myApplicationModal = items.get(position);
        if(myApplicationModal!=null){
            viewHolder.bindData(myApplicationModal);
        }
        viewHolder.findViewById(R.id.deletePost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = myApplicationModal.getPostid();
                if(selectPostObject != null)
                    selectPostObject.delPost(pid);
            }
        });

        viewHolder.findViewById(R.id.editPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = myApplicationModal.getPostid();
                if(selectPostObject != null)
                    selectPostObject.findPost(pid);
                /*EditApplication_.intent(mContext).
                        registrationId(myApplicationModal.getStudent_reg_id1()).
                        applicationId(String.valueOf(myApplicationModal.getStudent_application_id())).start();*/
            }
        });
    }
}
