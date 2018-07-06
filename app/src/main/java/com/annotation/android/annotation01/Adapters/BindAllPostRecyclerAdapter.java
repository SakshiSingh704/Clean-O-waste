package com.annotation.android.annotation01.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.annotation.android.annotation01.Fragments.LoginFragments_;
import com.annotation.android.annotation01.SinglePostFragments_;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.annotation.android.annotation01.utils.GlobalEnums;
import com.annotation.android.annotation01.viewholders.BaseViewWrapper;
import com.annotation.android.annotation01.viewholders.ItemRowAllPost;
import com.annotation.android.annotation01.viewholders.ItemRowAllPost_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by BlackHat on 6/28/2017.
 */
@EBean
public class BindAllPostRecyclerAdapter extends BaseRecyclerViewAdapter<GetPostListModal,ItemRowAllPost> {

    @RootContext
    Context mContext;

    @Override
    protected ItemRowAllPost onCreateItemView(ViewGroup parent, int viewType) {
        return ItemRowAllPost_.build(mContext);
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
    public void onBindViewHolder(BaseViewWrapper<ItemRowAllPost> itemRowAllApplicationBaseViewWrapper, int position) {

        final ItemRowAllPost viewHolder = itemRowAllApplicationBaseViewWrapper.getView();
        final GetPostListModal postListModal = items.get(position);
        if(postListModal!=null){
            viewHolder.bindData(postListModal);
        }
        viewHolder.findViewById(R.id.eachPostItem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SinglePostFragments_.intent(mContext).
                        postRecord(postListModal).start();
               /* getSupportFragmentManager().beginTransaction().replace(R.id.container,
                        new LoginFragments_().builder().build(), GlobalEnums.FragmentContactUs.name()).commit();*/

                //Toast.makeText(mContext,postListModal.getTitle(),Toast.LENGTH_LONG).show();
            }
        });

        /*viewHolder.findViewById(R.id.editPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*EditApplication_.intent(mContext).
                        registrationId(myApplicationModal.getStudent_reg_id1()).
                        applicationId(String.valueOf(myApplicationModal.getStudent_application_id())).start();*//*
            }
        });*/
    }
}
