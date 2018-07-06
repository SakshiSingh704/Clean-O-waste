package com.annotation.android.annotation01.Fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.annotation.android.annotation01.Adapters.BindAllPostRecyclerAdapter;
import com.annotation.android.annotation01.Adapters.BindCitySpinnerAdapter;
import com.annotation.android.annotation01.Adapters.BindLocalitySpinnerAdapter;
import com.annotation.android.annotation01.R;
import com.annotation.android.annotation01.pojo.GetCityModal;
import com.annotation.android.annotation01.pojo.GetCityPojo;
import com.annotation.android.annotation01.pojo.GetCurrentLocationModal;
import com.annotation.android.annotation01.pojo.GetLocalityModal;
import com.annotation.android.annotation01.pojo.GetLocalityPojo;
import com.annotation.android.annotation01.pojo.GetMapLocationPojo;
import com.annotation.android.annotation01.pojo.GetPostListModal;
import com.annotation.android.annotation01.pojo.GetPostListPojo;
import com.annotation.android.annotation01.utils.GPSTracker;
import com.annotation.android.annotation01.utils.Utility;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by BlackHat on 6/27/2017.
 */
@EFragment(R.layout.fragment_home)
public class HomeFragments extends BaseFragements {
    @ViewById
    RecyclerView postList;
    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView address;
    @Bean
    BindAllPostRecyclerAdapter mAdapter;
    @Bean
    Utility utility;
    BindLocalitySpinnerAdapter localitySpinnerAdapter;
    List<GetPostListModal> postListModal;
    List<GetLocalityModal> localityList;
    GetLocalityPojo getLocalityPojo;
    String city="",location="",cityID="",locationID="";
    String GET_CITY_URL = "http://www.divineinfosec.com/RealStateAndroid/getCity.php";
    String GET_LOCALITY_URL = "http://www.divineinfosec.com/RealStateAndroid/getLocality.php";
    String GET_POST_LIST = "http://divineinfosec.com/wasteRecycle/showPostList.php";
    //String GET_POST_LIST = "http://divinehackerz.com/wasteRecycle/showPostList.php";
    //String GET_POST_LIST = "http://192.168.56.1/wasteRecycle/showPostList.php";

    /*@ViewById
    TextView selectCity;*/

    @AfterInject
    void beforeView(){
        postListModal = new ArrayList<>();
        mAdapter.addAllItems(postListModal);
    }

    @AfterViews
    void loadView(){
        if (!utility.internetConnectivity(getActivity())) {
            showCustomAlertDialog(getResources().getString(R.string.no_network));
            return;
        }
        if (toolbar != null) {
            ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setTitle("");
            //((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        postList.setNestedScrollingEnabled(false);
        //getCityList();
        postList.setAdapter(mAdapter);
        getLocation();
        searchPostList();
    }

    //@Click(R.id.btnSearchPostList)
    void searchPostList(){
        if (!utility.internetConnectivity(getActivity())) {
            utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),getResources().getString(R.string.no_network));
            return;
        }else {
            try {
                if (postListModal != null && postListModal.size() > 0) {
                    postListModal.clear();
                    postList.setAdapter(null);
                    //postList.setAdapter(mAdapter);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            showProgressDialog();
            StringRequest stringRequest = new StringRequest(Request.Method.POST, GET_POST_LIST,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if (response.trim().length() > 0) {
                                Gson gson = new Gson();
                                GetPostListPojo postListPojo = new GetPostListPojo();
                                if(response != null && response.length() > 0) {
                                    postListPojo = gson.fromJson(response.toString(), GetPostListPojo.class);
                                }
                                if(postListPojo.getResult() != null && postListPojo.getResult().size() > 0)
                                    listAllPost(postListPojo);
                                //utility.showAlertDialog(getActivity(),"Post list",response);

                                hideProgressDialog();
                            } else {
                                hideProgressDialog();
                                Toast.makeText(getActivity(), "Under Maintenance", Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            hideProgressDialog();
                            Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            stringRequest.setShouldCache(false);
            requestQueue.getCache().remove(GET_POST_LIST);
            requestQueue.add(stringRequest);
        }
    }

    @UiThread
    void listAllPost(GetPostListPojo postListPojo){
        hideProgressDialog();
        try {
            for (GetPostListModal allPostModals : postListPojo.getResult()) {
                postListModal.add(allPostModals);
                mAdapter.notifyItemInserted(postListModal.size() - 1);
            }
            mAdapter.notifyDataSetChanged();
            postList.setAdapter(mAdapter);
        }
        catch(Exception e){
            Toast.makeText(getActivity(),"Error occured",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    void showCustomAlertDialog(final String message){
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
                            getLocation();
                        } else {
                            showCustomAlertDialog(message);
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

    @UiThread
    public void getLocation() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        GPSTracker gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

        } else {
            gps.showSettingsAlert();
        }
        hideProgressDialog();
        showAddress(gps.getLatitude()+"|"+gps.getLongitude());
        //return gps.getLatitude()+"|"+gps.getLongitude();
    }

    @UiThread
    void showAddress(String lat_lon){
        address.setText(lat_lon);

        String latitude = lat_lon.substring(0,lat_lon.indexOf("|") );
        String longitude = lat_lon.substring(lat_lon.indexOf("|")+1,lat_lon.length() );

        /*Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());

        List<Address> addresses  = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(latitude),Double.parseDouble(longitude), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
            sb.append(addresses.get(0).getAddressLine(i)).append("\n");
        }

        String city = addresses.get(0).getLocality();
        String state = addresses.get(0).getAdminArea();
        String zip = addresses.get(0).getPostalCode();
        String country = addresses.get(0).getCountryName();
        String completeAddress =sb + ",\n" + city + ", " + state + "," + zip + "," + country;
        address.setText(completeAddress);*/
        //address.setText("Latitude : " + latitude + ", Longitude : " + longitude + "\n\n Address : " + completeAddress);
        findMyLocation(latitude,longitude);
    }

    void findMyLocation(String latitude,String longitude){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude + "&sensor=true",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if (response.trim().length() > 0) {
                            Gson gson = new Gson();
                            GetMapLocationPojo mapLocationPojo = new GetMapLocationPojo();
                            mapLocationPojo = gson.fromJson(response.toString(), GetMapLocationPojo.class);
                            List<GetCurrentLocationModal> completeAddress = mapLocationPojo.getResults();
                            //utility.showAlertDialog(getActivity(),"Post list",response);
                            //listAllPost(postListPojo);
                            //address.setText(completeAddress.get(0).getFormatted_address());
                            hideProgressDialog();
                        } else {
                            hideProgressDialog();
                            Toast.makeText(getActivity(), "Under Maintenance", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideProgressDialog();
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude + "&sensor=true");
        requestQueue.add(stringRequest);
    }
}
