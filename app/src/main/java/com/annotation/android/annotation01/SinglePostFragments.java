package com.annotation.android.annotation01;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by BlackHat on 7/1/2017.
 */

@EActivity(R.layout.fragment_single_post)
public class SinglePostFragments extends BaseActivity {

    @Extra
    GetPostListModal postRecord;
    @ViewById
    ImageView loadPic;
    @ViewById
    TextView postTitle,postAddress,postDate;
    @AfterViews
    void loadView(){
        setValues();
    }

    void setValues(){
        postTitle.setText(postRecord.getTitle());
        postAddress.setText(postRecord.getLocation());
        //postLocality.setText(postRecord.getLocation() + ", " + postRecord.getCity());
        postDate.setText(postRecord.getDatetime());
        //postDescription.setText(postRecord.getDescription());
        //postRate.setText("Price : " + postRecord.getRate());

        String logoPath = postRecord.getImg();
        String imgPath = "http://www.divineinfosec.com/wasteRecycle"+logoPath;
        Picasso.with(this).load(imgPath).into(loadPic);
    }

}
