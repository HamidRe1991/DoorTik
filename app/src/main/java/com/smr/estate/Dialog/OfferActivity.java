package com.smr.estate.Dialog;

import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import wiadevelopers.com.library.Components.CustomProgressDialog;
import wiadevelopers.com.library.DivarUtils;

public class OfferActivity extends AppCompatActivity
{
    private TextView tvOne, tvTwo, tvThree;
    private CoordinatorLayout coordinatorLayoutOffer;
    private int cons_id, est_id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_offer);

        initialize();
    }

    private void initialize()
    {
        findViews();
        setUpListener();
        setUpActivity();
    }

    private void findViews()
    {
        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);
        tvThree = findViewById(R.id.tvThree);
        coordinatorLayoutOffer = findViewById(R.id.coordinatorOffer);
    }

    private void setUpListener()
    {
        tvOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                beFirstAds();
            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                offerAds("1");
            }
        });

        tvThree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                offerAds("2");
            }
        });
    }

    private void setUpActivity()
    {
        cons_id = getIntent().getIntExtra("cons_id", 0);
        est_id = getIntent().getIntExtra("est_id", 0);
    }

    private void beFirstAds()
    {
        final String url = Constant.BASE_URL + "beFirst";
        final CustomProgressDialog progressDialog = new CustomProgressDialog(OfferActivity.this);
        progressDialog.setMessage("در حال ارسال داده");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTypeface(DivarUtils.face);
        progressDialog.setTextSize(16);
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap map = new HashMap();
        map.put("type", "beFirst");
        map.put("cons_id", cons_id);
        map.put("est_id", est_id);

        JSONObject jsonObject = new JSONObject(map);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (response.getString("result").equals("ok"))
                    {
                        progressDialog.dismiss();
                        OfferActivity.this.finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        SnackBar.Create(coordinatorLayoutOffer, "تعداد دربن ها کافی نمی باشد",3);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i(G.TAG, "onErrorResponse: " + error.getMessage());
                AppSingleton.getInstance(getApplicationContext()).cancelPendingRequests();
                progressDialog.dismiss();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener);

        final int socketTimeout = 100000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void offerAds(String id)
    {
        final String url = Constant.BASE_URL + "beFirst";
        final CustomProgressDialog progressDialog = new CustomProgressDialog(OfferActivity.this);
        progressDialog.setMessage("در حال ارسال داده");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTypeface(DivarUtils.face);
        progressDialog.setTextSize(16);
        progressDialog.setCancelable(false);
        progressDialog.show();

        HashMap map = new HashMap();
        map.put("type", "offer");
        map.put("cons_id", cons_id);
        map.put("est_id", est_id);
        map.put("offer_id", id);

        JSONObject jsonObject = new JSONObject(map);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (response.getString("result").equals("ok"))
                    {
                        progressDialog.dismiss();
                        OfferActivity.this.finish();
                    }
                    {
                        progressDialog.dismiss();
                        SnackBar.Create(coordinatorLayoutOffer, "تعداد دربن ها کافی نمی باشد",3);
                    }

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i(G.TAG, "onErrorResponse: " + error.getMessage());
                AppSingleton.getInstance(getApplicationContext()).cancelPendingRequests();
                progressDialog.dismiss();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, listener, errorListener);

        final int socketTimeout = 100000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
