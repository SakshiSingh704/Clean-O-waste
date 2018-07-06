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
import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.annotation.android.annotation01.pojo.MyPostModal;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BlackHat on 6/26/2017.
 */
@EViewGroup(R.layout.item_row_my_post_adapter)
public class ItemRowMyPost extends LinearLayout {
    Context mContext;
    @ViewById
    TextView txtTitle, txtAddress, txtTime;
    String output="",address="",dateTime="",title="";
    String GET_CITY_BY_ID_URL = "http://www.divineinfosec.com/wasteRecycle/getCityById.php";

    public ItemRowMyPost(Context context) {
        super(context);
        this.mContext = context;
    }

    public void bindData(GetPostListModal myPostModal){
        try {
            txtTitle.setText(myPostModal.getTitle());
            txtAddress.setText(myPostModal.getLocation());
            txtTime.setText(myPostModal.getDatetime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
