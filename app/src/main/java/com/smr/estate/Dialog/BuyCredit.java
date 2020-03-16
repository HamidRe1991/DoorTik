package com.smr.estate.Dialog;

import android.graphics.Typeface;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.Components.CustomProgressDialog;

public class BuyCredit extends AppCompatActivity
{
    private TextView tvOne, tvTwo, tvThree, tvFour;
    private CoordinatorLayout coordinatorLayoutCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.CENTER);

        setContentView(R.layout.activity_buy_credit);

        findViews();
        setUpListener();
    }

    private void findViews()
    {
        tvOne = findViewById(R.id.tvOne);
        tvTwo = findViewById(R.id.tvTwo);
        tvThree = findViewById(R.id.tvThree);
        tvFour = findViewById(R.id.tvFour);
        coordinatorLayoutCredit = findViewById(R.id.coordinatorCredit);
    }

    private void setUpListener()
    {
        tvOne.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCredit("15");
            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCredit("30");
            }
        });

        tvThree.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCredit("90");
            }
        });

        tvFour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getCredit("180");
            }
        });
    }

    private void getCredit(String day)
    {
        final String url = Constant.BASE_URL + "buyCredit";

        String user_id = Read.readDataFromStorage("user_id", "0", getApplicationContext());

        HashMap hashMap = new HashMap();
        hashMap.put("cons_id", user_id);
        hashMap.put("day", day);

        final JSONObject jsonData = new JSONObject(hashMap);

        final CustomProgressDialog progressDialog = new CustomProgressDialog(BuyCredit.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTextSize(16);
        progressDialog.show();

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (response.getString("credit").equals("true"))
                    {
                        progressDialog.dismiss();
                        BuyCredit.this.finish();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        SnackBar.Create(coordinatorLayoutCredit, "تعداد دربن کافی نمی باشد",3);
                    }

                } catch (JSONException e)
                {
                    Log.i(G.TAG, e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i(G.TAG, "Error: " +error.getMessage());
                progressDialog.dismiss();
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };

        AppSingleton.getInstance(this).addToRequestQueue(request);
    }
}
