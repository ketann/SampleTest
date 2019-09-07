package com.example.sampletest.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sampletest.R;
import com.example.sampletest.adapter.DataAdapter;
import com.example.sampletest.model.ResponseData;
import com.example.sampletest.model.Row;
import com.example.sampletest.utils.API;
import com.example.sampletest.utils.CheckInternetConnection;
import com.example.sampletest.utils.Utilities;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public CheckInternetConnection chkNet;
    private Context mContext;
    private List<Row> mRowList;
    private DataAdapter dataAdapter;
    private RecyclerView recyclerViewRowData;
    private TextView txtActionTitle;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
    }

    private void initializeViews() {

        mContext = MainActivity.this;
        chkNet = new CheckInternetConnection(this);
        mRowList = new ArrayList<>();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout_event);
        swipeRefreshLayout.setOnRefreshListener(this);
        txtActionTitle = (TextView) findViewById(R.id.txtActionTitle);

        //recyclerView
        recyclerViewRowData = (RecyclerView) findViewById(R.id.recycler_view_row_data);
        recyclerViewRowData.setLayoutManager(new LinearLayoutManager(mContext));
        // adding inbuilt divider line
        recyclerViewRowData.addItemDecoration(new DividerItemDecoration(recyclerViewRowData.getContext(), DividerItemDecoration.VERTICAL));

        //here first check internet connection if network not available then popup display please check internet connection
        if (chkNet.checkInternet()) {
            //here call the api for get the data form url
            Call_WBS_SampleData();
        } else {
            Utilities.showAlertDialog(mContext, getString(R.string.no_internet));
        }
    }

    private void Call_WBS_SampleData() {
        //show the progressbar
        chkNet.showProgressDialogLoading();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }

        //creating a string request to send request to the url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API.getListAllData,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //hiding the progressbar after completion
                        chkNet.dismissProgressDialogLoading();
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d("Response", "--->" + response);

                        clearArrayList();

                        Gson gson = new Gson();
                        ResponseData responseData = gson.fromJson(response, ResponseData.class);
                        if (responseData != null && responseData.getRows() != null && responseData.getRows().size() > 0) {
                            txtActionTitle.setText(responseData.getTitle());
                            //Log.d("ActionTitle", responseData.getTitle());
                            mRowList.addAll(responseData.getRows());
                        } else {
                            Log.d("", "No data found!");
                            swipeRefreshLayout.setRefreshing(false);
                        }

                        //here update the adapter view
                        updateAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurs
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            Log.d("Error", getString(R.string.no_internet));
                        } else if (error instanceof AuthFailureError) {
                            Log.d("Error", getString(R.string.auth_problem_error));
                        } else if (error instanceof ServerError) {
                            Log.d("Error", getString(R.string.server_error));
                        } else if (error instanceof NetworkError) {
                            Log.d("Error", getString(R.string.no_internet));
                        } else if (error instanceof ParseError) {
                            Log.d("Error", getString(R.string.response_format_error));
                        } else {
                            Log.d("Error", error.getLocalizedMessage() != null ? error.getLocalizedMessage() : getString(R.string.something_wrong_error));
                        }
                        chkNet.dismissProgressDialogLoading();
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });

        //creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //adding the string request to request queue
        requestQueue.add(stringRequest);
    }

    private void updateAdapter() {
        if (dataAdapter == null) {
            dataAdapter = new DataAdapter(mContext, mRowList);
            // set the adapter
            recyclerViewRowData.setAdapter(dataAdapter);
        }else {
            dataAdapter.notifyDataSetChanged();
        }

    }

    private void clearArrayList() {

        if (mRowList != null && mRowList.size() > 0) {
            mRowList.clear();
        }
    }

    @Override
    public void onRefresh() {

        if (chkNet.checkInternet()) {
            Call_WBS_SampleData();
        } else {
            Utilities.showAlertDialog(mContext, getString(R.string.no_internet));
        }

    }
}
