package com.example.sampletest.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.sampletest.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Ketan on 06,September,2019
 */
public class CheckInternetConnection {

    //Variables declaration
    public Context context = null;
    public Activity mActivity;
    Dialog dialog;
    int TYPE_DIALOG = 0;
    android.app.ProgressDialog ProgressDialog;

    public CheckInternetConnection(Context context) {
        this.context = context;
    }

    public CheckInternetConnection(Activity context) {
        this.TYPE_DIALOG = 1;
        this.context = context;
        this.mActivity = context;
        initAlertDialog(context, context.getResources().getString(R.string.no_internet));
    }

    public boolean checkInternet() {
        this.TYPE_DIALOG = 0;
        return isOnline();
    }

    public boolean checkInternet(int checkType) {

        this.TYPE_DIALOG = checkType;

        return isOnline();
    }

    public Boolean isOnline() {
        if (isNetAvailable(context))
            return true;
        else {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3000); // This is time limit if the
                // connection time limit
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    return true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TYPE_DIALOG == 0) {

        } else if (TYPE_DIALOG == 1) {

            showToast(context, context.getResources().getString(R.string.no_internet));

        } else if (TYPE_DIALOG == 2) {

            showDialog();
        }

        return false;
    }

    public void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public void showShortToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public void showProgressDialogLoading() {
        try {
            ProgressDialog = new ProgressDialog(context);
            ProgressDialog.setTitle(R.string.app_name);
            ProgressDialog.setMessage(context.getResources().getString(R.string.please_wait));
            ProgressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void dismissProgressDialogLoading() {
        try {
            ProgressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method for  cheking internet is available or not
     */

    public synchronized static boolean isNetAvailable(Context context) {

        try {
            boolean isNetAvailable = false;
            if (context != null) {
                ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (mgr != null) {
                    boolean mobileNetwork = false;
                    boolean wifiNetwork = false;
                    boolean wiMaxNetwork = false;

                    boolean mobileNetworkConnecetd = false;
                    boolean wifiNetworkConnecetd = false;
                    boolean wiMaxNetworkConnected = false;

                    NetworkInfo mobileInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    NetworkInfo wifiInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                    NetworkInfo wiMaxInfo = mgr.getNetworkInfo(ConnectivityManager.TYPE_WIMAX);

                    if (mobileInfo != null)
                        mobileNetwork = mobileInfo.isAvailable();

                    if (wifiInfo != null)
                        wifiNetwork = wifiInfo.isAvailable();

                    if (wiMaxInfo != null)
                        wiMaxNetwork = wiMaxInfo.isAvailable();

                    if (wifiNetwork == true)
                        wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting();
                    if (mobileNetwork == true)
                        mobileNetworkConnecetd = mobileInfo.isConnectedOrConnecting();
                    if (wiMaxNetwork == true)
                        wiMaxNetworkConnected = wiMaxInfo.isConnectedOrConnecting();

                    isNetAvailable = (mobileNetworkConnecetd || wifiNetworkConnecetd || wiMaxNetworkConnected);
                }
            }
            return isNetAvailable;
        } catch (NullPointerException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }


    public void showDialog() {
        try {
            if (dialog != null && (!dialog.isShowing())) {
                Log.d("TAG", "SHOW DIALOG");
                dialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void initAlertDialog(Context context, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(context.getResources().getString(R.string.app_name));
        builder.setMessage(message);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        dialog = builder.create();

    }

}

