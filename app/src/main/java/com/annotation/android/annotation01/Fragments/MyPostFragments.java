package com.annotation.android.annotation01.Fragments;

import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
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
import com.annotation.android.annotation01.Adapters.BindMyPostRecyclerAdapter;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.interfaces.selectPost;
import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.annotation.android.annotation01.pojo.GetPostListPojo;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.pojo.LoginResponsePojo;
import com.annotation.android.annotation01.pojo.MyPostModal;
import com.annotation.android.annotation01.pojo.MyPostPojo;
import com.annotation.android.annotation01.utils.GlobalEnums;
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
 * Created by BlackHat on 6/26/2017.
 */
@EFragment(R.layout.fragment_my_post)
public class MyPostFragments extends BaseFragements implements selectPost {
    @ViewById
    RecyclerView recyclerViewMyPost;
    @ViewById
    TextView txtUserName;
    @FragmentArg
    LoginResponseModal loginResponseModal;
    @Bean
    BindMyPostRecyclerAdapter mAdapter;
    List<GetPostListModal> myPostModal;
    String uid="",output="";
    String GET_MY_POST="http://divineinfosec.com/wasteRecycle/myPost.php";
    String DEL_URL="http://divineinfosec.com/wasteRecycle/delPost.php";
    @AfterInject
    void injectView() {
        try {
            myPostModal = new ArrayList<>();
            mAdapter.setSelectPostObject(this);
            mAdapter.addAllItems(myPostModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    void loadViews(){
        recyclerViewMyPost.setAdapter(mAdapter);
        showProgressDialog();
        if(loginResponseModal.getResult() != null && loginResponseModal.getResult().size() > 0){
            txtUserName.setText(loginResponseModal.getResult().get(0).getName());
        }
        getMyPostData();
    }

    @Background
    void getMyPostData(){
        uid = loginResponseModal.getResult().get(0).getUid();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_MY_POST,
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
        requestQueue.getCache().remove(GET_MY_POST);
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showMyPost(String Output){
        hideProgressDialog();
        try {
            Gson gson = new Gson();
            GetPostListPojo myPostPojo= new GetPostListPojo();
            myPostPojo = gson.fromJson(output.toString(), GetPostListPojo.class);
            myPostModal.clear();
            for(GetPostListModal myPostModals : myPostPojo.getResult()){
                myPostModal.add(myPostModals);
                mAdapter.notifyItemInserted(myPostModal.size()-1);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void findPost(String pid) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new EditPostFragments_().builder().pid(pid).build(), GlobalEnums.FragmentEditPost.name()).commit();

    }

    @Override
    public void delPost(final String pid) {
        showProgressDialog();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DEL_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.length() > 0){
                            output = response;
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                    new MyPostFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentProfile.name()).commit();
                            //getMyPostData();
                            hideProgressDialog();
                        }else{//showLogin(response);
                            getMyPostData();
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
        requestQueue.add(stringRequest);
    }
}