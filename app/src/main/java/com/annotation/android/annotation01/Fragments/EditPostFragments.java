package com.annotation.android.annotation01.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.Adapters.BindCitySpinnerAdapter;
import com.annotation.android.annotation01.Adapters.BindLocalitySpinnerAdapter;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.GetCityModal;
import com.annotation.android.annotation01.pojo.GetCityPojo;
import com.annotation.android.annotation01.pojo.GetLocalityModal;
import com.annotation.android.annotation01.pojo.GetLocalityPojo;
import com.annotation.android.annotation01.pojo.GetSinglePostList;
import com.annotation.android.annotation01.pojo.GetSinglePostPojo;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.utils.Utility;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by BlackHat on 7/8/2017.
 */
@EFragment(R.layout.fragment_edit_post)
public class EditPostFragments extends BaseFragements {
    private static final int REQUEST_GALLERY = 1001;
    @FragmentArg
    String pid;
    @Bean
    Utility utility;
    @ViewById
    ImageView imageView;
    @ViewById
    Button uploadPic,btnEditPost;
    @ViewById
    EditText edtTxtPostLocation;
    @ViewById
    Spinner spinnerPostCategory;
    byte[] raw;
    String title="",address="",PhotoBase64String="";
    String output="";
    String FIND_POST_URL = "http://192.168.56.1/wasteRecycle/getSinglePost.php";
    String EDIT_POST_URL = "http://192.168.56.1/wasteRecycle/editPost.php";

    @AfterViews
    void loadView(){
        //utility.showAlertDialog(getActivity(),"Info",pid);
        findPostByPid(pid);
    }

    @Background
    void findPostByPid(String postID){
        if(!utility.internetConnectivity(getActivity())){
            showCustomAlertDialogForPid(getActivity().getResources().getString(R.string.no_network));
            return;
        }
        else {
            showProgressDialog();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, FIND_POST_URL,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            try{
                                output = response;
                                Gson gson = new Gson();
                                GetSinglePostPojo getPostPojo = new GetSinglePostPojo();
                                getPostPojo = gson.fromJson(output.toString(), GetSinglePostPojo.class);
                                List<GetSinglePostList> postData = getPostPojo.getResult();
                                setvalues(postData);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
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

    @UiThread
    void setvalues(List<GetSinglePostList> postData){
        try{
        if(postData.size() > 0 && postData != null) {
            //edtTxtPostTitle.setText(postData.get(0).getTitle());
            //edtTxtPostDescription.setText(postData.get(0).getDescription());
            //edtTxtPostAddress.setText(postData.get(0).getAddress());
            //edtTxtPostRate.setText(postData.get(0).getRate());
            edtTxtPostLocation.setText(postData.get(0).getLocation());
            //edtTxtPostCity.setText(postData.get(0).getCity());
            //edtTxtPostCity.setEnabled(false);
            edtTxtPostLocation.setEnabled(false);

            String logoPath = postData.get(0).getImage();
            String imgPath = "http://www.divineinfosec.com/RealStateAndroid" + logoPath;
            Picasso.with(getActivity()).load(imgPath).into(imageView);
            hideProgressDialog();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Click(R.id.btnEditPost)
    void getValues(){
        //title = edtTxtPostTitle.getText().toString().trim();
        address = edtTxtPostLocation.getText().toString().trim();
        //rate = edtTxtPostRate.getText().toString().trim();

        if (!utility.internetConnectivity(getActivity())) {
            utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),getResources().getString(R.string.no_network));
            return;
        }else {
            showProgressDialog();
            submitPost(title, address);
        }
    }
    @Background
    void submitPost(final String title, final String address){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_POST_URL,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.trim().length()>0){
                            output = response;
                            afterSubmitPost(output);
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
                map.put("title",title);
                map.put("address",address);
                map.put("image",PhotoBase64String);
                map.put("pid",pid);
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
    @UiThread
    void afterSubmitPost(String Output){
        hideProgressDialog();
        if(Output.equals("1")){
            Toast.makeText(getActivity(),"Post updated",Toast.LENGTH_LONG).show();
        }
        if(Output.equals("0")){
            Toast.makeText(getActivity(),"Please try again.",Toast.LENGTH_LONG).show();
        }
    }

    @Click(R.id.uploadPic)
    void uploadPicture(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
    }

    @OnActivityResult(REQUEST_GALLERY)
    void onImageResult(int resultCode, Intent data) {
        try {
            hideProgressDialog();
            if (resultCode == RESULT_OK) {
                Uri filePath = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                    imageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    raw = out.toByteArray();
                    PhotoBase64String = Base64.encodeToString(raw, Base64.DEFAULT);
                    //Toast.makeText(getActivity(), PhotoBase64String, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void showCustomAlertDialogForPid(final String message){
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                getActivity());
        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.info));
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (utility.internetConnectivity(getActivity())) {
                            showProgressDialog();
                            findPostByPid(pid);
                        } else {
                            showCustomAlertDialogForPid(message);
                        }
                    }
                })
                .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        hideProgressDialog();
                        hideProgress();
                        //finish();
                    }
                });
        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }
}
