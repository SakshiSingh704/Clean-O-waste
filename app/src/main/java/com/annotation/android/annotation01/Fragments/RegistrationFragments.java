package com.annotation.android.annotation01.Fragments;

import android.widget.Button;
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
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.utils.GlobalEnums;
import com.annotation.android.annotation01.utils.Utility;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BlackHat on 7/1/2017.
 */
@EFragment(R.layout.fragment_registration)
public class RegistrationFragments extends BaseFragements{

    @ViewById
    Button btnReg;
    @ViewById
    TextView loginHeading;
    @ViewById
    EditText edtTxtName,edtTxtMobile,edtTxtEmail;
    String name="", email="", mobile="";
    //String REGISTRATION_URL = "http://192.168.56.1/wasteRecycle/signup.php";
    String REGISTRATION_URL = "http://divineinfosec.com/wasteRecycle/signup.php";
    //String REGISTRATION_URL = "http://divinehackerz.com/wasteRecycle/signup.php";
    @Bean
    Utility utility;

    @AfterViews
    void loadView(){
       // performRegistration();
        btnReg.setText("Sign Up");
        loginHeading.setText("Register");
        name = "";
        email = "";
        mobile = "";
    }

    @Click(R.id.btnReg)
    void checkValidation()
    {
        name = edtTxtName.getText().toString().trim();
        email = edtTxtEmail.getText().toString().trim();
        mobile = edtTxtMobile.getText().toString().trim();

        if(name.length()==0 || mobile.equals(null) || mobile.equals("")){
            edtTxtName.setError("Please enter your name");
        }
        else if(mobile.length()!=10 || mobile.equals(null) || mobile.equals("")){
            edtTxtMobile.setError("Please enter 10 digit mobile number");
        }
        else if(email.length()==0 || email.equals(null) || email.equals("") || email.length()<4){
            edtTxtEmail.setError("Please enter valid email address");
        }
        else {
            performRegistration(name,email,mobile);
        }
    }

    @Background
    void performRegistration(final String Name, final String Email, final String Mobile){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTRATION_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        if(response.trim().length()>0){
                            if(response.equals("1")) {
                                showResult(response);
                            }
                            else if(response.equals("0")){
                                showResult(response);
                            }
                            else
                                utility.showAlertDialog(getActivity(),"Information",response);
                        }else{
                            utility.showAlertDialog(getActivity(),"Error","Try after sometimes");
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
                map.put("name",Name);
                map.put("email",Email);
                map.put("mobile",Mobile);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(REGISTRATION_URL);
        requestQueue.add(stringRequest);
    }

    @UiThread
    void showResult(String response){
        if(response.equals("1")){
            utility.showAlertDialog(getActivity(),"Information","Registration success, Please check your mail inbox for password");
        }
        if(response.equals("0")){
            utility.showAlertDialog(getActivity(),"Information", "Some error occured, Please try again.");
        }
        if(response.equals("2")){
            utility.showAlertDialog(getActivity(),"Information", "Your mobile number is already registered, Please try another mobile number.");
        }
    }
}
