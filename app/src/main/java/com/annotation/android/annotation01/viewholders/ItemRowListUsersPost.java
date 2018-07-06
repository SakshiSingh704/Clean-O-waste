package com.annotation.android.annotation01.viewholders;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.MyPostModal;
import com.annotation.android.annotation01.pojo.UsersPostModal;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by CG-DTE on 05-10-2017.
 */
@EViewGroup(R.layout.item_row_list_users_post)
public class ItemRowListUsersPost extends LinearLayout {
    Context mContext;
    @ViewById
    TextView txtTitle, txtLocality, txtDate;
    String output="",cityID="",LocID="",dateTime="",location="";
    public ItemRowListUsersPost(Context context) {
        super(context);
        this.mContext = context;
    }

    public void bindData(UsersPostModal myPostModal){
        try {
            txtTitle.setText(myPostModal.getTitle());
            location = myPostModal.getLocation();
            dateTime = myPostModal.getDateTime();
            txtLocality.setText(location);
            txtDate.setText(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

