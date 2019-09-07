package com.example.sampletest.network;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Ketan on 06,September,2019
 */

public class NetworkRequest {

    /**
     *
     * @param method                        Method type like is it Post or Get etc...,
     * @param url                           Api Url where we need to call
     * @param body                          Number of parameters which we need to send in api call request
     * @param networkResponseListeners      Listener instance for get response or update response on calling
     */

    public static void makeJsonObjectRequest(int method, final String url, JSONObject body, final NetworkResponseListeners
            networkResponseListeners){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                networkResponseListeners.onSuccessfulResponse(url, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkResponseListeners.onErrorResponse(url, error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.requestQueue.add(jsonObjectRequest);
    }
}
