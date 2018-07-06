package com.annotation.android.annotation01.Fragments;

import android.widget.Button;
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
import com.annotation.android.annotation01.UserProfileActivity;
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
 * Created by BlackHat on 7/3/2017.
 */
@EFragment(R.layout.fragment_edit_profile)
public class EditProfileFragments extends BaseFragements {

    @ViewById
    EditText edtTxtName,edtTxtEmail,edtTxtCity;
    @ViewById
    Button btnUpdate;
    @Bean
    Utility utility;
    @FragmentArg
    LoginResponseModal loginResponseModal;
    String EDIT_URL = "http://divineinfosec.com/RealStateAndroid/edituser.php";
    String LOGIN_URL = "http://www.divineinfosec.com/RealStateAndroid/login.php";

    @AfterViews
    void loadViews(){
        fetchValues();
    }

    void fetchValues(){
        if(loginResponseModal != null && loginResponseModal.getResult().size()>0) {
            edtTxtName.setText(loginResponseModal.getResult().get(0).getName());
            edtTxtCity.setText(loginResponseModal.getResult().get(0).getCity());
            edtTxtEmail.setText(loginResponseModal.getResult().get(0).getEmail());
        }
    }

    @Click(R.id.btnUpdate)
    void updateProfile(){
        performUpdate();
    }

    @Background
    void performUpdate(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                            showUpdateResult(response);
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
                map.put("name",edtTxtName.getText().toString().trim());
                map.put("email",edtTxtEmail.getText().toString().trim());
                map.put("city",edtTxtCity.getText().toString().trim());
                map.put("mobile",loginResponseModal.getResult().get(0).getMobile());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showUpdateResult(String output){
        if(output.equals("1")){
            //utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),"User Profile Updated...!");
            showUpdatedProfile();
        }
        else if(output.equals("0")){
            utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),"Error occured. Please try later.");
        }
    }

    void showUpdatedProfile() {
        getProfile(loginResponseModal.getResult().get(0).getMobile(),loginResponseModal.getResult().get(0).getPassword());
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
                            //LoginResponseModal loginResponseModal2 = new LoginResponseModal();
                            loginResponseModal = gson.fromJson(response.toString(), LoginResponseModal.class);
                            ProfileFragments profileFragments = new ProfileFragments();
                            UserProfileActivity userProfileActivity = new UserProfileActivity();
                            profileFragments.setLoginResponseModal(loginResponseModal);
                            userProfileActivity.setLoginResponseModal(loginResponseModal);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                    new ProfileFragments_().builder().loginResponseModal(loginResponseModal).build(), GlobalEnums.FragmentProfile.name()).commit();

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
                map.put("username",user);
                map.put("password",pass);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}