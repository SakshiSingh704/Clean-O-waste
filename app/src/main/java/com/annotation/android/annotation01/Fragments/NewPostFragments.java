package com.annotation.android.annotation01.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.widget.ArrayAdapter;
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
import com.annotation.android.annotation01.UserProfileActivity_;
import com.annotation.android.annotation01.pojo.GetCityModal;
import com.annotation.android.annotation01.pojo.GetCityPojo;
import com.annotation.android.annotation01.pojo.GetCurrentLocationModal;
import com.annotation.android.annotation01.pojo.GetLocalityModal;
import com.annotation.android.annotation01.pojo.GetLocalityPojo;
import com.annotation.android.annotation01.pojo.GetMapLocationPojo;
import com.annotation.android.annotation01.pojo.LoginResponseModal;
import com.annotation.android.annotation01.utils.GPSTracker;
import com.annotation.android.annotation01.utils.Utility;
import com.google.gson.Gson;

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
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * Created by BlackHat on 6/25/2017.
 */

@EFragment(R.layout.fragment_new_post)
public class NewPostFragments extends BaseFragements {
//    private static final int REQUEST_GALLERY = 1001;
    public static final int RESULT_OK = -1;
    private static final int REQUEST_CAMERA = 1001;
    private static final int REQUEST_GALLERY = 1002;
    @FragmentArg
    LoginResponseModal loginResponseModal;
    @Bean
    Utility utility;
    @ViewById
    ImageView imageView;
    @ViewById
    Button uploadPic,btnSubmitPost;
    @ViewById
    EditText edtTxtPostAddress,edtTxtLandmark;
    @ViewById
    Spinner spinnerPostCategory;
    byte[] raw;
    String title="",description="",city="",location="",cityid="",locationid="",address="",landmark="",PhotoBase64String="";
    String output="";
    String GET_CITY_URL = "http://www.divineinfosec.com/RealStateAndroid/getCity.php";
    String GET_LOCALITY_URL = "http://www.divineinfosec.com/RealStateAndroid/getLocality.php";
    String SUBMIT_POST_URL = "http://divineinfosec.com/wasteRecycle/submitPost.php";
    //String SUBMIT_POST_URL = "http://divinehackerz.com/wasteRecycle/submitPost.php";
    //String SUBMIT_POST_URL = "http://192.168.56.1/wasteRecycle/submitPost.php";

    @AfterViews
    void loadView(){
        showProgressDialog();
        //getLocations();
        getLocation();
        setCategory();
    }

    void setCategory(){
        //spinnerPostCategory

        try {
            String[] categoryList = {"Dead Animals","Dustbin Not Cleaned","Garbage Dump","Garbage veehicle not arrived","sweeping not done","no electricity in public toilet(s)",
            "no water supply in public toilet(s)","public toilet(s) blockage","public toilet(s) cleaning"};
            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,categoryList);
            spinnerPostCategory.setAdapter(categoryAdapter);
        } catch (Exception e) {
            utility.showToast("Error : " + e);
            return;
        }
    }

    @ItemSelect(R.id.spinnerPostCategory)
    public void getCityValue(boolean selected, int position) {
        try {
            if(spinnerPostCategory.getAdapter()!=null) {
                if (selected && position > 0) {
                    String category = (String) spinnerPostCategory.getAdapter().getItem(position);
                    title = category;
                } else {
                    city = "";
                    cityid="";
                }
                //Toast.makeText(getActivity(),city,Toast.LENGTH_LONG).show();
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Click(R.id.btnSubmitPost)
    void getValues(){
        landmark = edtTxtLandmark.getText().toString().trim();
        address = landmark + ", " + edtTxtPostAddress.getText().toString().trim();

        if (!utility.internetConnectivity(getActivity())) {
            utility.showAlertDialog(getActivity(),getActivity().getResources().getString(R.string.info),getResources().getString(R.string.no_network));
            return;
        }else {
            if (PhotoBase64String != null && PhotoBase64String.length() > 0){
                showProgressDialog();
                submitPost(title, address);
            }
            else{
                hideProgressDialog();
                utility.showAlertDialog(getActivity(),"Information","Please select any picture.");
            }
        }
    }
    @Background
    void submitPost(final String title, final String address){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SUBMIT_POST_URL,
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
                        hideProgressDialog();
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
                map.put("title",title);
                map.put("address",address);
                map.put("image",PhotoBase64String);
                map.put("uid",loginResponseModal.getResult().get(0).getUid());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove(SUBMIT_POST_URL);
        requestQueue.add(stringRequest);
    }
    @UiThread
    void afterSubmitPost(String Output){
        hideProgressDialog();

        if(Output.equals("1")){
            utility.showAlertDialog(getActivity(),"Information","Post Submited");
            //Toast.makeText(getActivity(),"Post Submited",Toast.LENGTH_LONG).show();
            UserProfileActivity_.intent(this).loginResponseModal((LoginResponseModal)loginResponseModal).start();
        }
        if(Output.equals("0")){
            Toast.makeText(getActivity(),"Something went wrong, Please try again.",Toast.LENGTH_LONG).show();
        }
    }

    /*@Click(R.id.uploadPic)
    void uploadPicture(){
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
        showProgressDialog();
    }*/

    @Click(R.id.uploadPic)
    void uploadPic() {
        try {
            chooseCameraGellery();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void chooseCameraGellery() {
        try {
            CharSequence colors[] = new CharSequence[]{"Camera", "Gallery"};
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Select option to upload");
            builder.setItems(colors, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            if(isDeviceSupportCamera()){
                                if(isStorageCameraPermissionGranted()){
                                    captureImage();
                                }
                                else{
                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1);
                                }
                            }
                            else{
                                utility.showAlertDialog(getActivity(),getResources().getString(R.string.info),
                                        "This device does not have a Camera");
                            }
                            break;
                        case 1:
                            if(isStorageGalleryAccessPermissionGranted()) {
                                Intent intent = new Intent();
                                intent.setType("image/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
                            }
                            break;
                    }
                    dialog.cancel();
                }
            });
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    PhotoBase64String = Base64.encodeToString(raw, Base64.DEFAULT);//Base64.encodeToString(raw, Base64.DEFAULT);
                } catch (IOException e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnActivityResult(REQUEST_CAMERA)
    void onCameraResult(int resultCode, Intent data) {
        try {
            hideProgressDialog();
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    // bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
                    raw = out.toByteArray();
                    PhotoBase64String = Base64.encodeToString(raw, Base64.DEFAULT);//Base64.encodeToString(raw, Base64.DEFAULT);
                } catch (Exception e) {
                    e.printStackTrace();
                    hideProgressDialog();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void captureImage() {
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isStorageCameraPermissionGranted() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if ((getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)
                        && getActivity().checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }


    public boolean isStorageGalleryAccessPermissionGranted() {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if ((getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED)) {
                    return true;
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    return false;
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checking device has camera hardware or not
     * */
    private boolean isDeviceSupportCamera() {
        try {
            if (getActivity().getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA)) {
                // this device has a camera
                return true;
            } else {
                // no camera on this device
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if(requestCode == 1) {
                if (!((grantResults[0] == PackageManager.PERMISSION_GRANTED)
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    utility.showAlertDialog(getActivity(), getResources().getString(R.string.info), getResources().getString(R.string.permission_needed));
                }
                else{
                    captureImage();
                }
            }
            if(requestCode == 2){
                if (!(grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    utility.showAlertDialog(getActivity(), getResources().getString(R.string.info), getResources().getString(R.string.permission_needed));
                }
                else{
                    Intent intent = new Intent();
                    intent.setType("image");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_GALLERY);
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getLocation() {
        ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        GPSTracker gps = new GPSTracker(getActivity());

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "+ latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        showAddress(gps.getLatitude()+"|"+gps.getLongitude());
        //return gps.getLatitude()+"|"+gps.getLongitude();
    }

    void showAddress(String lat_lon){
        //edtTxtPostAddress.setText(lat_lon);

        String latitude = lat_lon.substring(0,lat_lon.indexOf("|") );
        String longitude = lat_lon.substring(lat_lon.indexOf("|")+1,lat_lon.length() );

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
                            edtTxtPostAddress.setText(completeAddress.get(0).getFormatted_address());
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
                /*@Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("city", city);
                    map.put("location", location);
                    return map;
                }*/
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setShouldCache(false);
        requestQueue.getCache().remove("http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitude+","+longitude + "&sensor=true");
        requestQueue.add(stringRequest);
    }
}