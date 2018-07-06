package com.annotation.android.annotation01.Fragments;

import android.support.v7.widget.RecyclerView;
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
import com.annotation.android.annotation01.Adapters.BindListUsersPostAdapter;
import com.annotation.android.annotation01.Adapters.BindMyPostRecyclerAdapter;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.interfaces.adminApproval;
import com.annotation.android.annotation01.interfaces.selectPost;
import com.annotation.android.annotation01.pojo.AdminLoginModal;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.pojo.MyPostModal;
import com.annotation.android.annotation01.pojo.MyPostPojo;
import com.annotation.android.annotation01.pojo.UsersPostModal;
import com.annotation.android.annotation01.pojo.UsersPostPojo;
import com.annotation.android.annotation01.utils.GlobalEnums;
import com.annotation.android.annotation01.utils.Utility;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by CG-DTE on 04-10-2017.
 */
@EFragment(R.layout.fragment_list_users_post)
public class ListUserPostFragments extends BaseFragements implements adminApproval {
    @ViewById
    RecyclerView recyclerViewUsersPost;
    /*@ViewById
    ProgressBar progressBar;*/
    @FragmentArg
    AdminLoginModal adminLoginModal;
    @Bean
    BindListUsersPostAdapter mAdapter;
    @Bean
    Utility utility;
    List<UsersPostModal> usersPostModal;
    String uid="",output="";
    String GET_USERS_POST="http://divineinfosec.com/wasteRecycle/allPost.php";
    String SHOW_POST="http://divineinfosec.com/wasteRecycle/adminShowPost.php";
    String HIDE_POST="http://divineinfosec.com/wasteRecycle/adminHidePost.php";
    @AfterInject
    void injectView() {
        try {
            usersPostModal = new ArrayList<>();
            mAdapter.setNewAdminApproval(this);
            mAdapter.addAllItems(usersPostModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    void loadViews(){
        recyclerViewUsersPost.setAdapter(mAdapter);
        showProgressDialog();
        getMyPostData();
    }

    @Background
    void getMyPostData(){
        uid = adminLoginModal.getResult().get(0).getUsername();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_USERS_POST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.trim().length()>0){
                            output = response;
                            showMyPost(output);
                        }else{
                            output = response;
                            hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("uid",uid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(GET_USERS_POST);
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showMyPost(String Output){
        hideProgressDialog();
        try {
            Gson gson = new Gson();
            UsersPostPojo userPostPojo= new UsersPostPojo();
            userPostPojo = gson.fromJson(output.toString(), UsersPostPojo.class);
            usersPostModal.clear();
            for(UsersPostModal myPostModals : userPostPojo.getResult()){
                usersPostModal.add(myPostModals);
                mAdapter.notifyItemInserted(usersPostModal.size()-1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showPost(final String pid) {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SHOW_POST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.length() > 0){
                            output = response;
                            utility.showAlertDialog(getActivity(),"Success show post","Post is active now");

                            hideProgressDialog();
                        }else{
                            output = response;
                            hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("pid",pid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(SHOW_POST);
        requestQueue.add(stringRequest);
    }

    @Override
    public void hidePost(final String pid) {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HIDE_POST,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.length() > 0){
                            output = response;
                            utility.showAlertDialog(getActivity(),"Success hide post","Post is inactive now");
                           // getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                             //       new MyPostFragments_().builder().adminLoginModal(adminLoginModal).build(), GlobalEnums.FragmentProfile.name()).commit();
                            //getMyPostData();
                            hideProgressDialog();
                        }else{//showLogin(response);
                            //getMyPostData();
                            output = response;
                            hideProgressDialog();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("pid",pid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(HIDE_POST);
        requestQueue.add(stringRequest);
    }
}
