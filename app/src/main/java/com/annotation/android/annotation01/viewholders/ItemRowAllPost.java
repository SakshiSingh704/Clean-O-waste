package com.annotation.android.annotation01.viewholders;

import android.content.Context;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BlackHat on 6/28/2017.
 */

@EViewGroup(R.layout.item_row_all_post_adapter)
public class ItemRowAllPost extends LinearLayout {
    Context mContext;
    @ViewById
    ImageView imgView;
    @ViewById
    TextView txtTitle, txtLocality, txtTime;
    String LocID="";//,city="",location="";
    //String GET_CITY_BY_ID_URL = "http://www.divineinfosec.com/RealStateAndroid/getCityById.php";
    //String GET_LOCATION_BY_ID_URL = "http://www.divineinfosec.com/RealStateAndroid/getLocationById.php";
    public ItemRowAllPost(Context context) {
        super(context);
        this.mContext = context;
    }

    public void bindData(GetPostListModal myPostModal){
        try {
            txtTitle.setText(myPostModal.getTitle());
            LocID = myPostModal.getLocation();
            txtLocality.setText(LocID);
            txtTime.setText(myPostModal.getDatetime());

            String logoPath = myPostModal.getImg();
            String imgPath = "http://divineinfosec.com/wasteRecycle"+logoPath;
            Picasso.with(mContext).load(imgPath).into(imgView);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}