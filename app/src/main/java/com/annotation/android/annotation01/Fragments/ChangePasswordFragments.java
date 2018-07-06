package com.annotation.android.annotation01.Fragments;

import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.LoginResponseModal;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BlackHat on 6/25/2017.
 */

@EFragment(R.layout.fragment_change_password)
public class ChangePasswordFragments extends BaseFragements {
    @FragmentArg
    LoginResponseModal loginResponseModal;
    @ViewById
    EditText edtTxtOldPassword,edtTxtNewPassword;
    String oldPass="",newPass="",mobile="",output="",CHANGE_PASSWORD_URL = "http://www.divineinfosec.com/RealStateAndroid/changePassword.php";
    //CHANGE_PASSWORD_URL = "http://192.168.2.3/RealStateAndroid/changePassword.php";

    @AfterViews
    void loadView(){
        mobile = loginResponseModal.getResult().get(0).getMobile();
    }

    @Click(R.id.btnChangePassword)
    void performChangePassword() {
        oldPass=edtTxtOldPassword.getText().toString().trim();
        newPass=edtTxtNewPassword.getText().toString().trim();
        if (oldPass.length()>0 && newPass.length()>0) {
            changePassword(mobile, oldPass, newPass);
        } else {
            Toast.makeText(getActivity(), "Please enter your old password", Toast.LENGTH_LONG).show();
        }
    }

    @Background
    void changePassword(final String mobile,final String oldPass, final String newPass){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, CHANGE_PASSWORD_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.trim().length()>1){
                            output = response;
                            showUpdate(output);
                        }else{
                            output = response;
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
                map.put("oldPassword",oldPass);
                map.put("newPassword",newPass);
                map.put("mobile",mobile);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showUpdate(String result){
        if(result.equals("true")) {
            Toast.makeText(getActivity(), "Password Changed", Toast.LENGTH_LONG).show();
        }
        else if(result.equals("false")){
            Toast.makeText(getActivity(),"Time out... please try again",Toast.LENGTH_LONG).show();
        }
    }
}
