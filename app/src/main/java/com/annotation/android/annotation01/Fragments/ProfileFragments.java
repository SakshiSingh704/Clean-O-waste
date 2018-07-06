package com.annotation.android.annotation01.Fragments;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.AdminLoginModal;
import com.annotation.android.annotation01.pojo.AdminLoginPojo;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.pojo.LoginResponsePojo;
import com.annotation.android.annotation01.utils.GlobalEnums;
import com.annotation.android.annotation01.utils.Utility;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by BlackHat on 6/25/2017.
 */
@EFragment(R.layout.fragment_profile)
public class ProfileFragments extends BaseFragements {
    @FragmentArg
    LoginResponseModal loginResponseModal = null;
    @FragmentArg
    String userCode="";
    @FragmentArg
    AdminLoginModal adminLoginModal = null;

    @ViewById
    TextView name,email,mobile;
    @Bean
    Utility utility;
    @ViewById
    Button btnEditProfile;
    String LOGIN_URL = "http://divineinfosec.com/wasteRecycle/login.php";
    //String LOGIN_URL = "http://divinehackerz.com/wasteRecycle/login.php";
    String username="",password="";

    public void setLoginResponseModal(LoginResponseModal loginResponseModal) {
        this.loginResponseModal = loginResponseModal;
    }

    @AfterViews
    void loadView(){
        name.setText("");
        email.setText("");
        mobile.setText("");
        if(userCode.equals("1")){
            loadAdminProfile();
        }
        else if(userCode.equals("2")){
            loadUserProfile();
        }

    }

    void loadUserProfile(){
        username = loginResponseModal.getResult().get(0).getMobile();
        password = loginResponseModal.getResult().get(0).getPassword();
        List<LoginResponsePojo> loginResponsePojo = loginResponseModal.getResult();
        if(loginResponsePojo.get(0).getName() != null && loginResponsePojo.get(0).getName().length() > 0)
            name.setText(loginResponsePojo.get(0).getName());
        if(loginResponsePojo.get(0).getEmail() != null && loginResponsePojo.get(0).getEmail().length() > 0)
            email.setText(loginResponsePojo.get(0).getEmail());
        if(loginResponsePojo.get(0).getMobile() != null && loginResponsePojo.get(0).getMobile().length() > 0)
            mobile.setText(loginResponsePojo.get(0).getMobile());

        //getProfile(username,password);
    }

    void loadAdminProfile(){
        username = adminLoginModal.getResult().get(0).getMobile();
        password = adminLoginModal.getResult().get(0).getPassword();
        List<AdminLoginPojo> adminLoginPojo = adminLoginModal.getResult();
        if(adminLoginPojo.get(0).getName() != null && adminLoginPojo.get(0).getName().length() > 0)
            name.setText(adminLoginPojo.get(0).getName());
        if(adminLoginPojo.get(0).getEmail() != null && adminLoginPojo.get(0).getEmail().length() > 0)
            email.setText(adminLoginPojo.get(0).getEmail());
        if(adminLoginPojo.get(0).getMobile() != null && adminLoginPojo.get(0).getMobile().length() > 0)
            mobile.setText(adminLoginPojo.get(0).getMobile());

        //getProfile(username,password);
    }

    void getProfile(final String user, final String pass){

        //String output = webservices.performLogin(getActivity(),user,pass);
        //showLogin(output);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //showProfile(response);
                        try {
                            Gson gson = new Gson();
                            LoginResponseModal loginResponseModal2 = new LoginResponseModal();
                            loginResponseModal2 = gson.fromJson(response.toString(), LoginResponseModal.class);
                            List<LoginResponsePojo> loginResponsePojo = loginResponseModal2.getResult();

                            if(loginResponsePojo.get(0).getName() != null && loginResponsePojo.get(0).getName().length() > 0)
                                name.setText(loginResponsePojo.get(0).getName());
                            //Toast.makeText(getActivity(),"Name = "+ loginResponsePojo.get(0).getName(),Toast.LENGTH_LONG).show();
                            if(loginResponsePojo.get(0).getEmail() != null && loginResponsePojo.get(0).getEmail().length() > 0)
                                email.setText(loginResponsePojo.get(0).getEmail());
                            if(loginResponsePojo.get(0).getMobile() != null && loginResponsePojo.get(0).getMobile().length() > 0)
                                mobile.setText(loginResponsePojo.get(0).getMobile());

                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG ).show();
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
    void showProfile(String response){
        //Toast.makeText(getActivity(),"Response = "+ response,Toast.LENGTH_LONG).show();
        try {
            Gson gson = new Gson();
            LoginResponseModal loginResponseModal2 = new LoginResponseModal();
            loginResponseModal2 = gson.fromJson(response.toString(), LoginResponseModal.class);
            List<LoginResponsePojo> loginResponsePojo = loginResponseModal2.getResult();

            if(loginResponsePojo.get(0).getName() != null && loginResponsePojo.get(0).getName().length() > 0)
                name.setText(loginResponsePojo.get(0).getName());
                //Toast.makeText(getActivity(),"Name = "+ loginResponsePojo.get(0).getName(),Toast.LENGTH_LONG).show();
            if(loginResponsePojo.get(0).getEmail() != null && loginResponsePojo.get(0).getEmail().length() > 0)
                email.setText(loginResponsePojo.get(0).getEmail());
            if(loginResponsePojo.get(0).getMobile() != null && loginResponsePojo.get(0).getMobile().length() > 0)
                mobile.setText(loginResponsePojo.get(0).getMobile());

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }

    @Click(R.id.btnEditProfile)
    void editProfile(){
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                new EditProfileFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentEditProfile.name()).commit();

    }
}
