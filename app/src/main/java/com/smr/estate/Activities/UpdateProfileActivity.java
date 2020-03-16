package com.smr.estate.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Dialog.MapsActivity;
import com.smr.estate.Dialog.SelectStateActivity;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.Internet.CheckInternet;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;
import com.smr.estate.Utils.Picture;
import com.smr.estate.Utils.RuntimePermissionsActivity;
import com.smr.estate.Utils.StringToId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import wiadevelopers.com.library.Components.CustomProgressDialog;

//Update profile data

public class UpdateProfileActivity extends RuntimePermissionsActivity
{
    private RelativeLayout lnrUpdateProfile;
    private TextView tvUpdateCity, tvLoc;
    private EditText edtName, edtEmail, edtTell, edtAddress,edtPass;
    private LinearLayout lnrUpdateLocation, lnrUpdateAllowed;
    private CircleImageView imgProfile;
    private CoordinatorLayout coordinatorUpdateProfile;
    private AppCompatImageView imgEdit, imgCancel;

    private boolean isCitySelected = true, isImageSelected = false, isLocationSelect = true, isAllowedImageSelected = false;

    private String strName, strEmail, strTell, strAddress,strPass, strCity = null, strLat = "NO_LOCATION", strLon = "NO_LOCATION", user_id;

    private Bitmap bitmapProfile, bitmapAllowed;

    private static Context mContext;
    private boolean license;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        mContext = UpdateProfileActivity.this;

        user_id = Read.readDataFromStorage("user_id", "0", mContext);

        lnrUpdateProfile = findViewById(R.id.rtlUpdateProfile);
        lnrUpdateLocation = findViewById(R.id.lnrUpdateLocation);
        tvUpdateCity = findViewById(R.id.tvUpdateCity);
        tvLoc = findViewById(R.id.tvUpdateLoc);
        edtName = findViewById(R.id.edtUpdateName);
        edtEmail = findViewById(R.id.edtUpdateEmail);
        edtTell = findViewById(R.id.edtUpdateTell);
        edtAddress = findViewById(R.id.edtUpdateAddress);
        edtPass = findViewById(R.id.edtRegisterPass);
        imgCancel = findViewById(R.id.imgCancelEdit);
        imgProfile = findViewById(R.id.imgUpdateProfile);
        imgEdit = findViewById(R.id.imgEditProfile);
        coordinatorUpdateProfile = findViewById(R.id.coordinatorUpdateProfile);
        lnrUpdateAllowed = findViewById(R.id.lnrUpdateNum);

        imgCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UpdateProfileActivity.this.finish();
            }
        });

        Intent intent = getIntent();
        edtName.setText(intent.getStringExtra("profileName"));
        tvUpdateCity.setText(intent.getStringExtra("profileState"));
        edtAddress.setText(intent.getStringExtra("profileAddress"));
        edtTell.setText(intent.getStringExtra("profileTell"));
        edtEmail.setText(intent.getStringExtra("profileEmail"));
        strLat = intent.getStringExtra("Lat");
        strLon = intent.getStringExtra("Lon");
        isLocationSelect = true;
        license = intent.getBooleanExtra("license", false);

        if (license == true)
            lnrUpdateAllowed.setVisibility(View.INVISIBLE);
        else
            lnrUpdateAllowed.setVisibility(View.VISIBLE);

        if (!intent.getStringExtra("profileEmail").equals(""))
        {
            edtEmail.setBackgroundResource(R.drawable.edit_text_full_background);
            edtEmail.setTextColor(getResources().getColor(R.color.darkGray));
        }

        if (!intent.getStringExtra("profileTell").equals(""))
        {
            edtTell.setBackgroundResource(R.drawable.edit_text_full_background);
            edtTell.setTextColor(getResources().getColor(R.color.darkGray));
        }

        Glide.with(this)
                .load(intent.getStringExtra("profileImage"))
                .apply(new RequestOptions()
                        .error(R.drawable.register_profile)
                        .placeholder(R.drawable.register_profile)
                        .fitCenter())
                .into(imgProfile);

        lnrUpdateLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (isCitySelected)
                {
                    UpdateProfileActivity.super.requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.REQUEST_PERMISSIONS_LOCATION);
                } else
                    SnackBar.Create(coordinatorUpdateProfile, "ابتدا استان خود را انتخاب نمایید",3);
            }
        });

        tvUpdateCity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivityForResult(new Intent(UpdateProfileActivity.this, SelectStateActivity.class), Constant.REQUEST_SELECT_CITY);
                overridePendingTransition(R.anim.anim_slide_in_bottom, 0);
            }
        });

        lnrUpdateProfile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UpdateProfileActivity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_PROFILE);
            }
        });

        lnrUpdateAllowed.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                UpdateProfileActivity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ALLOWED);
            }
        });

        edtName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String value = edtName.getText().toString();

                if (!value.equals(""))
                {
                    edtName.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtName.setTextColor(getResources().getColor(R.color.darkGray));
                } else
                {
                    edtName.setBackgroundResource(R.drawable.edit_text_background);
                    edtName.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String value = edtEmail.getText().toString();

                if (!value.equals(""))
                {
                    edtEmail.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtEmail.setTextColor(getResources().getColor(R.color.darkGray));
                } else
                {
                    edtEmail.setBackgroundResource(R.drawable.edit_text_background);
                    edtEmail.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtTell.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String value = edtTell.getText().toString();

                if (!value.equals(""))
                {
                    edtTell.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtTell.setTextColor(getResources().getColor(R.color.darkGray));
                } else
                {
                    edtTell.setBackgroundResource(R.drawable.edit_text_background);
                    edtTell.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtAddress.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String value = edtAddress.getText().toString();

                if (!value.equals(""))
                {
                    edtAddress.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtAddress.setTextColor(getResources().getColor(R.color.darkGray));
                } else
                {
                    edtAddress.setBackgroundResource(R.drawable.edit_text_background);
                    edtAddress.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                strName = edtName.getText().toString();
                strEmail = edtEmail.getText().toString().trim();
                strTell = edtTell.getText().toString().trim();
                strAddress = edtAddress.getText().toString();
                strCity = tvUpdateCity.getText().toString();
                strPass = edtPass.getText().toString();

                if (CheckInternet.Checked(getApplicationContext()))
                {
                    if (isCitySelected)
                    {
                        if (!strName.equals(""))
                        {
                            if (!strAddress.equals(""))
                            {
                                if (isLocationSelect)
                                    registerUser();
                                else
                                    SnackBar.Create(coordinatorUpdateProfile, "لوکیشن خود را وارد نمایید",3);
                            } else
                                SnackBar.Create(coordinatorUpdateProfile, "آدرس دفتر املاک را وارد نمایید",3);
                        } else
                            SnackBar.Create(coordinatorUpdateProfile, "نام دفتر املاک را وارد نمایید",3);
                    } else
                        SnackBar.Create(coordinatorUpdateProfile, "استان مورد نظر را انتخاب نمایید",3);
                } else
                {
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            SnackBar.Create(coordinatorUpdateProfile, "اتصال به اینترنت قطع می باشد",3);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode)
    {
        if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_PROFILE)
            Picture.selectSingleImage(this, Constant.REQUEST_SELECT_PROFILE_IMAGE);
        else if (requestCode == Constant.REQUEST_PERMISSIONS_LOCATION)
            startActivityForResult(new Intent(UpdateProfileActivity.this, MapsActivity.class), Constant.REQUEST_SELECT_LOCATION);
        else if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ALLOWED)
            Picture.selectSingleImage(this, Constant.REQUEST_SELECT_ALLOWED_IMAGE);
    }

    @Override
    public void onPermissionsDeny(int requestCode)
    {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_SELECT_CITY && resultCode == Constant.RESULT_OK)
        {
            String city = data.getStringExtra("city");
            isCitySelected = true;
            tvUpdateCity.setBackgroundResource(R.drawable.edit_text_full_background);
            tvUpdateCity.setTextColor(getResources().getColor(R.color.darkGray));
            tvUpdateCity.setText(city);
        }

        if (requestCode == Constant.REQUEST_SELECT_LOCATION && resultCode == Constant.RESULT_OK)
        {
            strLat = data.getStringExtra("Lat");
            strLon = data.getStringExtra("Lon");
            isLocationSelect = true;
            tvLoc.setText("لوکیشن ست شد");
        }

        try
        {
            if (requestCode == Constant.REQUEST_SELECT_PROFILE_IMAGE && resultCode == RESULT_OK)
            {
                ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

                for (int i = 0; i < images.size(); i++)
                {
                    Uri imageUri = Uri.fromFile(new File(images.get(i).getPath()));
                    bitmapProfile = Picture.getBitmap(this, imageUri);
                    imgProfile.setImageBitmap(bitmapProfile);
                }

                isImageSelected = true;
            }
        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        }

        try
        {
            if (requestCode == Constant.REQUEST_SELECT_ALLOWED_IMAGE && resultCode == RESULT_OK)
            {
                ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

                for (int i = 0; i < images.size(); i++)
                {
                    Uri imageUri = Uri.fromFile(new File(images.get(i).getPath()));
                    bitmapAllowed = Picture.getBitmap(this, imageUri);
                }

                isAllowedImageSelected = true;
            }
        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        }
    }

    private void registerUser()
    {
        //Data for send to server
        HashMap hashMap = new HashMap();

        hashMap.put(Constant.REQUEST_TYPE, Constant.UPDATE);
        hashMap.put(Constant.CONS_ID, user_id);
        hashMap.put(Constant.NAME, strName);
        hashMap.put(Constant.MAIL, strEmail);
        hashMap.put(Constant.SELLER_TELL, strTell);
        hashMap.put(Constant.ADDRESS, strAddress);
        hashMap.put(Constant.PASS, strPass);

        hashMap.put(Constant.PROVINCE, StringToId.State(strCity));

        if (isImageSelected)
            hashMap.put(Constant.IMAGE, Picture.getStringImage(bitmapProfile, Constant.IMAGE_MAX_SIZE));

        if (isAllowedImageSelected)
            hashMap.put(Constant.LICENSE_IMAGE, Picture.getStringImage(bitmapAllowed, Constant.IMAGE_MAX_SIZE));

        if (isLocationSelect)
        {
            hashMap.put(Constant.GEO_LAT, strLat);
            hashMap.put(Constant.GEO_LON, strLon);
        } else
        {
            hashMap.put(Constant.GEO_LAT, "NO_LOCATION");
            hashMap.put(Constant.GEO_LON, "NO_LOCATION");
        }

        final JSONObject jsonData = new JSONObject(hashMap);

        final CustomProgressDialog progressDialog = new CustomProgressDialog(UpdateProfileActivity.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTextSize(15);
        progressDialog.show();

        //register URL
        final String url = Constant.BASE_URL + "registerCst";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    if (response.getString("result").equals("ok"))
                    {
                        UpdateProfileActivity.this.finish();
                    } else
                        SnackBar.Create(coordinatorUpdateProfile, "عملیات ثبت نام ناموفق بود",3);
                } catch (JSONException e)
                {
                    Log.i(G.TAG, e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError e)
            {
                Log.i(G.TAG, e.getMessage());
                AppSingleton.getInstance(getApplicationContext()).cancelPendingRequests();
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
