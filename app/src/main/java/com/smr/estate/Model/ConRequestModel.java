package com.smr.estate.Model;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smr.estate.Activities.MainActivity;
import com.smr.estate.Activities.RegisterActivity;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.Components.CustomProgressDialog;

public class ConRequestModel extends LinearLayout {

    public static TextView tvName, tvMobile, tvReady;
    public static Integer agentId, status;

    public ConRequestModel(Context context) {
        //,int id,String name,String lastName,int mobile
        super(context);

        init(context);
    }

    public ConRequestModel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ConRequestModel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_consultant_request, this, true);
        FindViewById();
        Listener();
    }

    private void Listener() {
        tvReady.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                HashMap hashMap = new HashMap();
                hashMap.put("agent_id", agentId);

                final JSONObject jsonData = new JSONObject(hashMap);
                final String url = Constant.BASE_URL + "activeAgent";
                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("msg").equals("1")) {
                                tvReady.setBackground(getResources().getDrawable(R.drawable.buy_item_background));
                                ConRequestModel.tvReady.setText("کاربر فعال");
                                tvReady.setTextColor(getResources().getColor(R.color.white));
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            if (response.getString("msg").equals("0")) {
                                ConRequestModel.tvReady.setBackgroundColor(getResources().getColor(R.color.yellow));
                                ConRequestModel.tvReady.setText("کاربر غیر فعال");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        //1
                        Log.i(G.TAG, String.valueOf(e));
                        AppSingleton.getInstance(getContext()).cancelPendingRequests();
                    }
                };

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        headers.put("User-agent", System.getProperty("http.agent"));
                        return headers;
                    }
                };

                request.setRetryPolicy(new

                        DefaultRetryPolicy(10000, 1, 1.0f));
                AppSingleton.getInstance(

                        getContext()).

                        addToRequestQueue(request);
            }
        });
    }

    private void FindViewById() {
        tvName = findViewById(R.id.idConsName);
        tvMobile = findViewById(R.id.idConsMob);
        tvReady = findViewById(R.id.idReadyOrUnready);
    }
}
