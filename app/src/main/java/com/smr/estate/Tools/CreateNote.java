package com.smr.estate.Tools;

import android.graphics.drawable.Drawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import wiadevelopers.com.library.Components.CustomProgressDialog;
import wiadevelopers.com.library.DivarUtils;

public class CreateNote extends AppCompatActivity
{
    private TextView txtToolbarTitle, txtSend;
    private EditText edtNote;
    private Boolean isEdit;
    private CoordinatorLayout coordinatorLayoutNote;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private String currentDate = sdf.format(new Date());

    private String event = null, cons_id = null;
    private int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        initialize();
    }

    private void initialize()
    {
        findViews();
        setupActivity();
        setUpListener();
    }

    private void setupActivity()
    {
        cons_id = Read.readDataFromStorage("user_id", "0", getApplicationContext());
        isEdit = getIntent().getBooleanExtra(Constant.KEY_NOTE_CHECK, false);

        if (isEdit == true)
        {
            noteId = getIntent().getIntExtra(Constant.KEY_NOTE_ID, 0);
            event = getIntent().getStringExtra(Constant.KEY_NOTE);

            edtNote.setText(event);
        }

        if (isEdit)
        {
            txtToolbarTitle.setText("ویرایش یادداشت");
            txtSend.setText("تغییر");
        } else
        {
            txtToolbarTitle.setText("درج یادداشت جدید");
            txtSend.setText(" ثبت");
        }

        Drawable img = DivarUtils.createDrawable(R.drawable.ic_check_white_48dp, 22);
        txtSend.setCompoundDrawables(null, null, img, null);
    }

    private void findViews()
    {
        txtSend = findViewById(R.id.txtSend);
        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        edtNote = findViewById(R.id.edtNote);
        coordinatorLayoutNote = findViewById(R.id.coordinatorNote);
    }

    private void setUpListener()
    {
        txtSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                event = edtNote.getText().toString();

                if (edtNote.getText() != null)
                {
                    JSONObject jsonObject = new JSONObject();
                    try
                    {
                        jsonObject.put(Constant.CONS_ID, cons_id);
                        jsonObject.put(Constant.KEY_EVENT, event);
                        jsonObject.put(Constant.KEY_EVENT_DATE, currentDate);

                        if (isEdit)
                            jsonObject.put(Constant.KEY_NOTE_ID, noteId);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    setNoteRequest(jsonObject);
                } else
                    SnackBar.Create(coordinatorLayoutNote, "متن یادداشت خالی می باشد",3);
            }
        });
    }

    private void setNoteRequest(JSONObject jsonData)
    {
        String url = null;

        if (isEdit)
            url = Constant.BASE_URL + "updateEvent";
        else
            url = Constant.BASE_URL + "newEvent";

        final CustomProgressDialog progressDialog = new CustomProgressDialog(CreateNote.this);
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
                CreateNote.this.finish();
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener);

        final int socketTimeout = 100000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }
}
