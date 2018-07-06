package com.annotation.android.annotation01.Fragments;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.AdminProfileActivity_;
import com.annotation.android.annotation01.MainActivity;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.UserProfileActivity_;
import com.annotation.android.annotation01.pojo.AdminLoginModal;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.pojo.LoginResponsePojo;
import com.annotation.android.annotation01.utils.Utility;
import com.annotation.android.annotation01.utils.WebServices;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackHat on 6/12/2017.
 */

@EFragment(R.layout.fragment_login)
public class LoginFragments extends BaseFragements{
    @FragmentArg
    String userCode="2";
    @ViewById
    EditText edtTxtUserName,edtTxtPassword;
    String username,password,output;
    //String LOGIN_URL = "http://192.168.56.1/wasteRecycle/login.php";
    String LOGIN_URL = "http://divineinfosec.com/wasteRecycle/login.php";
    //String LOGIN_URL = "http://divinehackerz.com/wasteRecycle/login.php";
    String ADMIN_LOGIN_URL = "http://divineinfosec.com/wasteRecycle/adminLogin.php";
    //String ADMIN_LOGIN_URL = "http://divinehackerz.com/wasteRecycle/adminLogin.php";
    @ViewById
    Toolbar toolbar;
    @Bean
    Utility utility;
    @AfterViews
    void loadView(){
        /*edtTxtUserName.setText("1234567890");
        edtTxtPassword.setText("qwerty");*/
        username = "";
        password = "";
        output = "";

    }

    @Click(R.id.btnLogin)
    void login(){
        username = edtTxtUserName.getText().toString();
        password = edtTxtPassword.getText().toString();
        if(username.length()!=10 || username.equals(null) || username.equals("")){
            edtTxtUserName.setError("Please enter your 10 digit registered mobile number");
        }
        else if(password.equals(null) || password.equals("")){
            edtTxtPassword.setError("Please enter correct password");
        }
        else {
            if (!utility.internetConnectivity(getActivity())) {
                utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),getResources().getString(R.string.no_network));
                return;
            }else {
                showProgressDialog();
                if(userCode.equals("2"))
                    performLogin(username, password);
                else if(userCode.equals("1"))
                    performAdminLogin(username,password);
            }
        }
    }

    //login method for users
    @Background
    void performLogin(String user,String pass){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                            output = response;
                            hideProgressDialog();
                            if(output.length()>1)
                                showLogin(output);
                            else
                                utility.showAlertDialog(getActivity(),"Information","Invalid login details. Please try again.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
                        //errors.setText(error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(LOGIN_URL);
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showLogin(String output){
        hideProgressDialog();
        try {
                Gson gson = new Gson();
                LoginResponseModal loginResponseModal = new LoginResponseModal();
                loginResponseModal = gson.fromJson(output.toString(), LoginResponseModal.class);
                if(loginResponseModal != null && loginResponseModal.getResult().size() > 0 )
                    openProfile(loginResponseModal);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openProfile(LoginResponseModal loginResponseModal){
        UserProfileActivity_.intent(this).loginResponseModal((LoginResponseModal)loginResponseModal).start();
    }

    //login methods for admin
    @Background
    void performAdminLogin(String user,String pass){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ADMIN_LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        output = response;
                        hideProgressDialog();
                        if(output.length() > 1)
                            showAdminLogin(output);
                        else
                            utility.showAlertDialog(getActivity(),"Information","Invalid login details. Please try again.");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
                        //errors.setText(error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put("username",username);
                map.put("password",password);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(ADMIN_LOGIN_URL);
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showAdminLogin(String output){
        hideProgressDialog();
        try {
            Gson gson = new Gson();
            AdminLoginModal adminLoginModal = new AdminLoginModal();
            adminLoginModal = gson.fromJson(output.toString(), AdminLoginModal.class);
            if(adminLoginModal != null && adminLoginModal.getResult().size() > 0)
                openAdminProfile(adminLoginModal);
            else
                utility.showAlertDialog(getActivity(),"Information","Please try again.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private void openAdminProfile(AdminLoginModal adminLoginModal){
        AdminProfileActivity_.intent(this).adminLoginModal((AdminLoginModal)adminLoginModal).start();
    }
}
