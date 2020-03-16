package com.smr.estate.Dialog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.smr.estate.Activities.MainActivity;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONObject;

import java.util.HashMap;

import wiadevelopers.com.library.Components.CustomProgressDialog;
import wiadevelopers.com.library.DivarUtils;
import wiadevelopers.com.library.MaskdEditText.MaskedEditText;

public class ArchiveActivity extends AppCompatActivity
{
    private Button btnClose, btnSubmit;
    private MaskedEditText edtCommission;
    private RadioButton rdbCommission, rdbExpire;
    private String sell_id, commission, price, cons_id, est_id, req_type;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);

        initialize();
    }

    private void initialize()
    {
        findViews();
        setupActivity();
        setOnListener();
    }

    private void findViews()
    {
        btnClose = findViewById(R.id.btnCloseExpire);
        btnSubmit = findViewById(R.id.btnSubmitExpire);
        edtCommission = findViewById(R.id.edtCommission);
        rdbCommission = findViewById(R.id.rdbCommission);
        rdbExpire = findViewById(R.id.rdbExpire);
    }

    private void setupActivity()
    {
        sell_id = getIntent().getStringExtra(Constant.KEY_TYPE);
        price = getIntent().getStringExtra(Constant.KEY_PRICE);
        est_id = getIntent().getStringExtra(Constant.KEY_POST_ID);
        cons_id = Read.readDataFromStorage("user_id", "0", getApplicationContext());
        req_type = "expire";
    }

    private void setOnListener()
    {
        btnClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ArchiveActivity.this.finish();
            }
        });

        rdbExpire.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edtCommission.setEnabled(false);
            }
        });
        rdbCommission.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edtCommission.setEnabled(true);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                HashMap map = new HashMap();

                if (rdbExpire.isChecked())
                {
                    req_type = "expire";
                    map.put("type", req_type);
                    map.put("est_id", est_id);
                    map.put("cons_id", cons_id);
                    map.put("sell_id", sell_id);
                    map.put("offer_id", "1");

                } else if (rdbCommission.isChecked())
                {
                    req_type = "action";
                    commission = edtCommission.getRawText();

                    map.put("type", req_type);
                    map.put("est_id", est_id);
                    map.put("cons_id", cons_id);
                    map.put("commission", commission);
                    map.put("price", price);
                    map.put("sell_id", sell_id);
                    map.put("offer_id", "1");
                }

                JSONObject jsonObject = new JSONObject(map);

                final String url = Constant.BASE_URL + "expireEstate";
                final CustomProgressDialog progressDialog = new CustomProgressDialog(ArchiveActivity.this);
                progressDialog.setMessage("در حال ارسال داده");
                progressDialog.setIndicatorColor(R.color.mainColor);
                progressDialog.setTextColor(R.color.textColor);
                progressDialog.setTypeface(DivarUtils.face);
                progressDialog.setTextSize(16);
                progressDialog.setCancelable(false);
                progressDialog.show();

                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        progressDialog.dismiss();
                        startActivity(new Intent(ArchiveActivity.this, MainActivity.class));
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
        });
    }
}
