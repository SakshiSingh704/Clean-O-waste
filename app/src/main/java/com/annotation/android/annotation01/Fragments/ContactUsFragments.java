package com.annotation.android.annotation01.Fragments;

import com.annotation.android.annotation01.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by BlackHat on 8/9/2017.
 */
@EFragment(R.layout.fragment_contact_page)
public class ContactUsFragments extends BaseFragements {
    @AfterViews
    void loadViews(){

    }
}
