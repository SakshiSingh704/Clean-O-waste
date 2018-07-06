package com.annotation.android.annotation01.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

/**
 * Created by BlackHat on 6/10/2017.
 */
@EFragment
public class BaseFragements extends Fragment {
    ProgressDialog progressDialog;

    public void hideKeyBoard() {
        try {
            View view = getActivity().getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        List<Fragment> fragments = getFragmentManager().getFragments();
        if (fragments != null && fragments.size()>0) {
            for(Fragment frag:fragments){
                if(frag instanceof FragmentOnlineApplication_){
                    frag.onActivityResult(requestCode, resultCode, intent);
                    break;
                }
            }
        }
    }*/

    @Override
    public void onDestroy() {
        super.onDestroy();
        BackgroundExecutor.cancelAll("cancellable_task",true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BackgroundExecutor.cancelAll("cancellable_task",true);
    }

    @Override
    public void onStop() {
        super.onStop();
        BackgroundExecutor.cancelAll("cancellable_task",true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BackgroundExecutor.cancelAll("cancellable_task",true);
    }

    public void showProgressDialog(){
        try {
            if(progressDialog==null){
                progressDialog = new ProgressDialog(getActivity());
            }
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Please wait...");
            if(progressDialog!=null && (!progressDialog.isShowing())) {
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @UiThread
    public void hideProgress(){
        hideProgressDialog();
    }

    public void hideProgressDialog(){
        try {
            if(progressDialog!=null && progressDialog.isShowing()){
                progressDialog.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
