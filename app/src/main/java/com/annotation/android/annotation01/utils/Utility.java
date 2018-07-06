package com.annotation.android.annotation01.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;
import android.widget.Toast;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by BlackHat on 6/29/2017.
 */

@EBean
public class Utility {

    @RootContext
    Context mContext;
    //Check InternetConnectivity
    public boolean internetConnectivity(Context context) {

        boolean isConnected = false;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void showAlertDialog(Context context, String heading, String message) {
        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                context);
        // set title
        alertDialogBuilder.setTitle(heading);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });


        // create alert dialog
        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();
    }

    public void showToast(String message) {
        Toast.makeText(mContext.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    public String getCurrentDate(String format){
        Date date = Calendar.getInstance().getTime();
        DateFormat formatter = new SimpleDateFormat(format);
        String today = formatter.format(date);
        return today;
    }

    public String convertDate(Date date, String format){
        Date convertDate = date;
        DateFormat df = new SimpleDateFormat(format);
        String convertedDate = df.format(convertDate);
        return convertedDate;
    }

    public String encodeBase64(String encode)
    {
        System.out.println("[encodeBase64] Base64 encode : "+encode);
        String decode=null;
        try {
           // decode=  Base64.encode(encode.getBytes());
        } catch (Exception e) {
            System.out.println("Unable to decode : "+ e);
        }
        return  decode;
    }

    public boolean isValidEmaillId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }


}
