package com.smr.estate.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alimuzaffar.lib.pin.PinEntryEditText;
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
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//Class for verify sms

public class AuthActivity extends AppCompatActivity {
    private LinearLayout linearLayoutError;
    public  static PinEntryEditText pinEntry ;
    private TextView tvResend;
    private CountDownTimer countDownTimer;
    private String mobileNumber, strCode;

    public AuthActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        tvResend = findViewById(R.id.tvResend);
        pinEntry = findViewById(R.id.edtPinCode);
        linearLayoutError = findViewById(R.id.lnrError);

        countDownTimer = new CountDownTimer(120000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                tvResend.setText("ارسال مجدد: " + millisUntilFinished / 1000);

            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                startActivity(new Intent(AuthActivity.this, LoginActivity.class));
            }

        }.start();
        mobileNumber = Read.readDataFromStorage("mobile", "NO_MOBILE", getApplicationContext());
        if (pinEntry != null) {
                    pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
                        @Override
                        public void onPinEntered(CharSequence str) {
                            strCode = str.toString();
                            verifyUser();
                        }
                    });
                }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AuthActivity.this, LoginActivity.class));
    }

    private void verifyUser(){
        //Data for send to server
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", mobileNumber);
        hashMap.put("reg_key", strCode);
        final JSONObject jsonData = new JSONObject(hashMap);

        //verify URL
        final String url = Constant.BASE_URL + "verifySmsCst";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("msg").equals("wrong key")) {
                        linearLayoutError.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.Tada).duration(700).playOn(linearLayoutError);
                        pinEntry.setText(null);

                    } else if (response.getString("msg").equals("0")) {
                        countDownTimer.onFinish();
                        countDownTimer.cancel();
                        startActivity(new Intent(AuthActivity.this, RegisterActivity.class));
                        AuthActivity.this.finish();

                    } else {
                        countDownTimer.onFinish();
                        countDownTimer.cancel();
                        Write.writeDataInStorage("user_id", response.getString("msg"), getApplicationContext());
                        startActivity(new Intent(AuthActivity.this, MainActivity.class));
                        AuthActivity.this.finish();
                    }
                } catch (JSONException e) {
                    Log.i(G.TAG, "onErrorResponse: " + e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(G.TAG, "onErrorResponse: " + error.getMessage());
                AppSingleton.getInstance(getApplicationContext()).cancelPendingRequests();
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

        request.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1.0f));
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
