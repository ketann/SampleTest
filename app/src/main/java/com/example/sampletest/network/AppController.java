package com.example.sampletest.network;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Ketan on 06,September,2019
 */

public class AppController extends Application {

    public static RequestQueue requestQueue;

    // override method of super class
    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);
    }

}
