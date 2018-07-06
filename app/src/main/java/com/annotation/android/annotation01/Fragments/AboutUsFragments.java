package com.annotation.android.annotation01.Fragments;

import com.annotation.android.annotation01.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by CG-DTE on 06-10-2017.
 */
@EFragment(R.layout.fragment_about_us)
public class AboutUsFragments extends BaseFragements {
    @AfterViews
    void loadView(){

    }
}