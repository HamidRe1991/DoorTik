package com.smr.estate.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.Internet.CheckInternet;
import com.smr.estate.R;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.Components.CustomProgressDialog;
import wiadevelopers.com.library.MaskdEditText.MaskedEditText;

//Send mobile number

public class LoginActivity extends AppCompatActivity
{
    private MaskedEditText edtLoginMobileNumber;
    private Button btnLogin;
    private CoordinatorLayout coordinatorLayoutLogin;
    private TextView tvtLoginTell, tvBtnLogin;

    private String strMobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLoginMobileNumber = findViewById(R.id.edtLoginMobileNumber);
        coordinatorLayoutLogin = findViewById(R.id.coordinatorLogin);
        btnLogin = findViewById(R.id.btnLogin);
        tvtLoginTell = findViewById(R.id.txtLoginTell);
        tvBtnLogin = findViewById(R.id.tvBtnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                strMobileNumber = edtLoginMobileNumber.getRawText().trim();

                if (CheckInternet.Checked(getApplicationContext()))
                {
                    if (edtLoginMobileNumber.getRawText().trim().length() > 0)
                    {
                        if (strMobileNumber.length() == 11)
                        {
                            if (strMobileNumber.startsWith("09"))
                            {
                                //Call PHP API
                                loginUser();

                            } else
                                SnackBar.Create(coordinatorLayoutLogin, "شماره ی وارد شده صحیح نمی باشد",3);
                        } else
                            SnackBar.Create(coordinatorLayoutLogin, "شماره ی وارد شده صحیح نمی باشد",3);
                    } else
                        SnackBar.Create(coordinatorLayoutLogin, "لطفا شماره موبایل خود را وارد نمایید",3);
                } else
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            SnackBar.Create(coordinatorLayoutLogin, "ارتباط با اینترنت قطع می باشد",3);
                        }
                    });
                }
            }
        });
    }

    private void loginUser()
    {
        //Data for send to server
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", strMobileNumber);
        final JSONObject jsonData = new JSONObject(hashMap);

        //login URL
        final String url = Constant.BASE_URL + "loginAppCst";

        //Config progress dialog
        final CustomProgressDialog progressDialog = new CustomProgressDialog(LoginActivity.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("در حال اتصال...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setCancelable(false);
        progressDialog.setTextSize(15);
        progressDialog.show();

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
                        YoYo.with(Techniques.SlideOutRight).duration(500).playOn(tvtLoginTell);
                        YoYo.with(Techniques.SlideOutRight).duration(500).playOn(edtLoginMobileNumber);
                        YoYo.with(Techniques.SlideOutRight).duration(500).playOn(btnLogin);
                        YoYo.with(Techniques.SlideOutRight).duration(500).listen(new AnimatorListenerAdapter()
                        {
                            @Override
                            public void onAnimationEnd(Animator animation)
                            {
                                super.onAnimationEnd(animation);

                                Write.writeDataInStorage("mobile", strMobileNumber, getApplicationContext());
                                startActivity(new Intent(LoginActivity.this, AuthActivity.class));
                                LoginActivity.this.finish();
                            }
                        }).playOn(tvBtnLogin);

                    } else
                    {
                        SnackBar.Create(coordinatorLayoutLogin, "عملیات نا موفق",3);
                    }
                } catch (JSONException e)
                {
                    Log.i(G.TAG, "onErrorResponse: " + e.getMessage());
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener)
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap();
                headers.put("Content-Type", "application/json; charset=utf-8");
                headers.put("User-agent", System.getProperty("http.agent"));
                return headers;
            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
