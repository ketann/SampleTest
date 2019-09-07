package com.example.sampletest.network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by Ketan on 06,September,2019
 */

public interface NetworkResponseListeners {

    void onSuccessfulResponse(String url, JSONObject response);
    void onErrorResponse(String url, VolleyError error);
}
