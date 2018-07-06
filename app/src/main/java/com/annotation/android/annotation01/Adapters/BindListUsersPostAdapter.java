package com.annotation.android.annotation01.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.interfaces.adminApproval;
import com.annotation.android.annotation01.interfaces.selectPost;
import com.annotation.android.annotation01.pojo.UsersPostModal;
import com.annotation.android.annotation01.viewholders.BaseViewWrapper;
import com.annotation.android.annotation01.viewholders.ItemRowListUsersPost;
import com.annotation.android.annotation01.viewholders.ItemRowListUsersPost_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by CG-DTE on 05-10-2017.
 */
@EBean
public class BindListUsersPostAdapter extends BaseRecyclerViewAdapter<UsersPostModal,ItemRowListUsersPost> {

    adminApproval newAdminApproval;

    public adminApproval getNewAdminApproval() {
        return newAdminApproval;
    }

    public void setNewAdminApproval(adminApproval newAdminApproval) {
        this.newAdminApproval = newAdminApproval;
    }

    @RootContext
    Context mContext;

    @Override
    protected ItemRowListUsersPost onCreateItemView(ViewGroup parent, int viewType) {
        return ItemRowListUsersPost_.build(mContext);
    }

    @Override
    protected List<UsersPostModal> getItems() {
        return items;
    }

    @Override
    protected boolean addItems(UsersPostModal item) {
        return items.add(item);
    }

    @Override
    public void addAllItems(List<UsersPostModal> item) {
        this.items = item;
    }

    @Override
    public boolean removeItem(UsersPostModal item) {
        return items.remove(item);
    }

    @Override
    public int getPosition(UsersPostModal item) {
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
    public UsersPostModal getItem(int position) {
        return items.get(position);
    }

    @Override
    public void onBindViewHolder(BaseViewWrapper<ItemRowListUsersPost> itemRowMyApplicationBaseViewWrapper, int position) {

        final ItemRowListUsersPost viewHolder = itemRowMyApplicationBaseViewWrapper.getView();
        final UsersPostModal myApplicationModal = items.get(position);
        if(myApplicationModal!=null){
            viewHolder.bindData(myApplicationModal);
        }
        viewHolder.findViewById(R.id.hidePost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = myApplicationModal.getPostId();
                if(newAdminApproval != null)
                    newAdminApproval.hidePost(pid);
            }
        });

        viewHolder.findViewById(R.id.showPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = myApplicationModal.getPostId();
                if(newAdminApproval != null)
                    newAdminApproval.showPost(pid);
            }
        });
    }
}
