package com.smr.estate.Activities;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Dialog.MapsActivity;
import com.smr.estate.Dialog.SelectStateActivity;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.Internet.CheckInternet;
import com.smr.estate.Model.AreaNamesAndId;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;
import com.smr.estate.Utils.AsyncTaskGetStateName;
import com.smr.estate.Utils.Picture;
import com.smr.estate.Utils.RuntimePermissionsActivity;
import com.smr.estate.Utils.StringToId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import wiadevelopers.com.library.Components.CustomProgressDialog;

//Set profile data class

public class RegisterActivity extends RuntimePermissionsActivity {
    private RelativeLayout lnrProfile;
    private TextView tvSelectCity, tvLoc;
    private EditText edtName, edtEmail, edtTell, edtAddress, edtCode, edtPass;
    private LinearLayout lnrLocation, lnrAllowed;
    private Button btnRegister;
    private CircleImageView imgProfile;
    private ImageView miniCamera;
    private CoordinatorLayout coordinatorLayoutRegister;
    private Bitmap bitmapProfile, bitmapAllowed;
    //1
    private LinearLayout toggle;
    private TextView state, consultant;
    private TextView tvDesc;
    private EditText consultName, consultFamily;
    private Spinner consultStateName;
    private Spinner spnCity, spnArea;
    private ArrayList<String> cityList, areaList;
    private List<String> StatesNames;
    private List<Integer> StateNamesId;
    public static String areaName = "";
    private Integer _stateId;
    private Integer _areaCode;
    private Integer _cityId;


    private int role = 2;
    private Switch roleSwitch;

    private boolean isCitySelected = false, isProfileImageSelected = false, isLocationSelect = false, isAllowedImageSelected = false;

    private String mobileNumber, strName, strEmail, strTell, strAddress, strCity, strCode, strPass, strLat = "NO_LOCATION", strLon = "NO_LOCATION";
    private String strConsultName, strFamily, strConsultCity, strArea, strConsultPass, strConsultMobileNumber, strConsultStateName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findView();
        toggleSelectedRole();
        setSpnnerData();
        if (role == 2) {
            LocationAndAllowClickable();
        }



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role == 2) {
                    //inja add mishe spref
                    IfStateDataInserted();
                }
                if (role == 1) {
                    IfConsultDataInserted();
                }
            }

        });

    }

    private void LocationAndAllowClickable() {
        lnrLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.super.requestAppPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Constant.REQUEST_PERMISSIONS_LOCATION);
            }
        });

//        tvSelectCity.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivityForResult(new Intent(RegisterActivity.this, SelectStateActivity.class), Constant.REQUEST_SELECT_CITY);
//                overridePendingTransition(R.anim.anim_slide_in_bottom, 0);
//            }
//        });

        lnrProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_PROFILE);
            }
        });

        lnrAllowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterActivity.super.requestAppPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ALLOWED);
            }
        });
    }

    private void setSpnnerData() {
        cityList = new ArrayList<>();
        cityList.add(Constant.SELECT_CITY);
        cityList.add(Constant.FARDIS);

        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, cityList);

        spnCity.setAdapter(areaAdapter);

        areaList = new ArrayList<String>();
        areaList.add(Constant.SELECT_AREA);
        areaList.add(Constant.SARHADDI);
        areaList.add(Constant.EMAM_KHOMEINI);
        areaList.add(Constant.NASTARAN);
        areaList.add(Constant.CHEHELOHAST_DASTGAH);
        areaList.add(Constant.BAHARAN);
        areaList.add(Constant.MESHKIN_DASHT);
        areaList.add(Constant.MOHAMMAD_SHAHR);
        areaList.add(Constant.KHOSHNAM);
        areaList.add(Constant.DEHKADE);
        areaList.add(Constant.SHAHRAKE_NAZ);
        areaList.add(Constant.MANZARIE);
        areaList.add(Constant.SHAHRAKE_SADODAH);
        areaList.add(Constant.SHAHRAKE_TALEGHANI);
        areaList.add(Constant.BOLVARE_EMAM_REZA);
        areaList.add(Constant.KHIABAN_EMAM_HOSSEIN);
        areaList.add(Constant.BOLVARE_BAYAT);
        areaList.add(Constant.POLE_ARTESH);
        areaList.add(Constant.FALAKE_AVAL);
        areaList.add(Constant.FALAKE_DOVOM);
        areaList.add(Constant.FALAKE_SEVOM);
        areaList.add(Constant.FALAKE_CHAHAROM);
        areaList.add(Constant.FALAKE_PANJOM);
        areaList.add(Constant.SHAHRAKE_SEPAH);
        areaList.add(Constant.SHAHRAKE_VAHDAT);
        areaList.add(Constant.SHAHRAKE_SIMIN_DASHT);
        areaList.add(Constant.SHAHRAK_HAFEZIE);
        areaList.add(Constant.ROKN_ABAD);
        areaList.add(Constant.SHAHRAKE_ERAM);
        areaList.add(Constant.KHIABAN_AHARI);
        areaList.add(Constant.SHAHRAKE_GOLESTAN);
        areaList.add(Constant.SHAHRAKE_PARNIAN);
        areaList.add(Constant.SHAHRAKE_TABAN);
        areaList.add(Constant.SHAHRAKE_MAHAN);
        areaList.add(Constant.SHAHRAKE_ALZAHRA);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, areaList);
        spnArea.setAdapter(cityAdapter);

        spnArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int postion, long l) {
                String areaString = spnArea.getSelectedItem().toString();
                _areaCode = Integer.valueOf(StringToId.Area(areaString));
                if (postion > 0 && role == 1) {

                    //    Toast.makeText(getApplicationContext(),areCode+"",Toast.LENGTH_SHORT).show();
                    GetStetesNames(_areaCode);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void findView() {
        lnrProfile = findViewById(R.id.rtlProfile);
        lnrLocation = findViewById(R.id.lnrLocation);
        tvSelectCity = findViewById(R.id.tvSelectCity);
        tvLoc = findViewById(R.id.tvRegisterLoc);
        edtName = findViewById(R.id.edtRegisterName);
        edtEmail = findViewById(R.id.edtRegisterEmail);
        edtTell = findViewById(R.id.edtRegisterTell);
        edtAddress = findViewById(R.id.edtRegisterAddress);
        edtCode = findViewById(R.id.edtRegisterCode);
        edtPass = findViewById(R.id.edtRegisterPass);
        btnRegister = findViewById(R.id.btnRegister);
        imgProfile = findViewById(R.id.imgRegisterProfile);
        coordinatorLayoutRegister = findViewById(R.id.coordinatorRegister);
        lnrAllowed = findViewById(R.id.lnrNumber);

        //1
        toggle = findViewById(R.id.toggle);
        state = findViewById(R.id.state);
        consultant = findViewById(R.id.Consultant);
        tvDesc = findViewById(R.id.tvDesc);
        consultName = findViewById(R.id.edtConsultName);
        consultFamily = findViewById(R.id.edtConsultFamily);
        spnCity = findViewById(R.id.spnCity);
        spnArea = findViewById(R.id.spnَArea);
        consultStateName = findViewById(R.id.spnConsultStateName);
        miniCamera = findViewById(R.id.ivMiniCamere);
    }

    private void toggleSelectedRole() {
        consultant.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ConsultantVisibilitiesAndInfo();
            }
        });

        state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StateVisibilitiesAndAction();
            }
        });
    }

    private void StateVisibilitiesAndAction() {
        role = 2;
        state.setBackgroundColor(getResources().getColor(R.color.toggleCheck));
        consultant.setBackgroundColor(getResources().getColor(R.color.toggleUnCheck));
        tvDesc.setVisibility(View.VISIBLE);
        lnrAllowed.setVisibility(View.VISIBLE);
        lnrLocation.setVisibility(View.VISIBLE);
        edtCode.setVisibility(View.VISIBLE);
        edtAddress.setVisibility(View.VISIBLE);
        edtEmail.setVisibility(View.VISIBLE);
        imgProfile.setVisibility(View.VISIBLE);
        tvSelectCity.setVisibility(View.GONE);
        edtName.setVisibility(View.VISIBLE);
        consultName.setVisibility(View.VISIBLE);
        consultFamily.setVisibility(View.VISIBLE);
        spnCity.setVisibility(View.VISIBLE);
        spnArea.setVisibility(View.VISIBLE);
        consultStateName.setVisibility(View.GONE);
        miniCamera.setVisibility(View.VISIBLE);

        consultName.setText("");
        consultFamily.setText("");

        consultName.setHint(" نام دفتر املاکی *");
        consultFamily.setHint("نام مدیر املاک *");


        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = edtName.getText().toString();

                if (!value.equals("")) {
                    edtName.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtName.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    edtName.setBackgroundResource(R.drawable.edit_text_background);
                    edtName.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = edtEmail.getText().toString();

                if (!value.equals("")) {
                    edtEmail.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtEmail.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    edtEmail.setBackgroundResource(R.drawable.edit_text_background);
                    edtEmail.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtTell.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = edtTell.getText().toString();

                if (!value.equals("")) {
                    edtTell.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtTell.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    edtTell.setBackgroundResource(R.drawable.edit_text_background);
                    edtTell.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });

        edtAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = edtAddress.getText().toString();

                if (!value.equals("")) {
                    edtAddress.setBackgroundResource(R.drawable.edit_text_full_background);
                    edtAddress.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    edtAddress.setBackgroundResource(R.drawable.edit_text_background);
                    edtAddress.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });
    }

    private void ConsultantVisibilitiesAndInfo() {
        role = 1;
        state.setBackgroundColor(getResources().getColor(R.color.toggleUnCheck));
        consultant.setBackgroundColor(getResources().getColor(R.color.toggleCheck));
        tvDesc.setVisibility(View.GONE);
        lnrAllowed.setVisibility(View.GONE);
        lnrLocation.setVisibility(View.GONE);
        edtCode.setVisibility(View.GONE);
        edtAddress.setVisibility(View.GONE);
        edtEmail.setVisibility(View.VISIBLE);
        tvSelectCity.setVisibility(View.GONE);
        edtName.setVisibility(View.GONE);
        consultName.setVisibility(View.VISIBLE);
        consultFamily.setVisibility(View.VISIBLE);
        spnCity.setVisibility(View.VISIBLE);
        spnArea.setVisibility(View.VISIBLE);
        consultStateName.setVisibility(View.VISIBLE);
        imgProfile.setVisibility(View.GONE);
        miniCamera.setVisibility(View.GONE);

        consultName.setText("");
        consultName.setText("");
        consultName.setHint("نام *");
        consultFamily.setHint("نام خانوادگی *");


        consultName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = consultName.getText().toString();

                if (!value.equals("")) {
                    consultName.setBackgroundResource(R.drawable.edit_text_full_background);
                    consultName.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    consultName.setBackgroundResource(R.drawable.edit_text_background);
                    consultName.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });
        consultFamily.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = consultFamily.getText().toString();

                if (!value.equals("")) {
                    consultFamily.setBackgroundResource(R.drawable.edit_text_full_background);
                    consultFamily.setTextColor(getResources().getColor(R.color.darkGray));
                } else {
                    consultFamily.setBackgroundResource(R.drawable.edit_text_background);
                    consultFamily.setTextColor(getResources().getColor(R.color.textColor));
                }
            }
        });
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_PROFILE)
            Picture.selectSingleImage(this, Constant.REQUEST_SELECT_PROFILE_IMAGE);
        else if (requestCode == Constant.REQUEST_PERMISSIONS_LOCATION)
            startActivityForResult(new Intent(RegisterActivity.this, MapsActivity.class), Constant.REQUEST_SELECT_LOCATION);
        else if (requestCode == Constant.REQUEST_PERMISSIONS_READ_EXTERNAL_STORAGE_FOR_ALLOWED)
            Picture.selectSingleImage(this, Constant.REQUEST_SELECT_ALLOWED_IMAGE);
    }

    @Override
    public void onPermissionsDeny(int requestCode) {
        Toast.makeText(this, "جواز داده نشد", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constant.REQUEST_SELECT_CITY && resultCode == Constant.RESULT_OK) {
            String city = data.getStringExtra("city");
            isCitySelected = true;
            tvSelectCity.setBackgroundResource(R.drawable.edit_text_full_background);
            tvSelectCity.setTextColor(getResources().getColor(R.color.darkGray));
            strCity = city;
            tvSelectCity.setText(city);
        }

        if (requestCode == Constant.REQUEST_SELECT_LOCATION && resultCode == Constant.RESULT_OK) {
            strLat = data.getStringExtra("Lat");
            strLon = data.getStringExtra("Lon");
            isLocationSelect = true;
            tvLoc.setText("لوکیشن ست شد");
        }

        try {
            if (requestCode == Constant.REQUEST_SELECT_PROFILE_IMAGE && resultCode == RESULT_OK) {
                ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

                for (int i = 0; i < images.size(); i++) {
                    Uri imageUri = Uri.fromFile(new File(images.get(i).getPath()));
                    bitmapProfile = Picture.getBitmap(this, imageUri);
                    imgProfile.setImageBitmap(bitmapProfile);
                }

                isProfileImageSelected = true;
            }
        } catch (Exception e) {
            Log.i(G.TAG, e.getMessage());
        }

        try {
            if (requestCode == Constant.REQUEST_SELECT_ALLOWED_IMAGE && resultCode == RESULT_OK) {
                ArrayList<Image> images = (ArrayList<Image>) ImagePicker.getImages(data);

                for (int i = 0; i < images.size(); i++) {
                    Uri imageUri = Uri.fromFile(new File(images.get(i).getPath()));
                    bitmapAllowed = Picture.getBitmap(this, imageUri);
                }

                isAllowedImageSelected = true;
            }
        } catch (Exception e) {
            Log.i(G.TAG, e.getMessage());
        }
    }

    public void GetStetesNames(Integer code) {
        new AsyncTaskGetStateName(Constant.BASE_URL + "selectAmlak", code + "").execute();

        final CustomProgressDialog progressDialog = new CustomProgressDialog(RegisterActivity.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("لطفا صبر کنید ...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTextSize(15);
        progressDialog.show();

        final Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StatesNames = new ArrayList<>();
                        StateNamesId = new ArrayList<>();
                        if (!areaName.equals("")) {
                            progressDialog.cancel();
                            timer.cancel();

                            try {
                                JSONArray jsonArray = new JSONArray(areaName);
                             //   Toast.makeText(getApplicationContext(), areaName + "" + "------" + jsonArray.length(), Toast.LENGTH_SHORT).show();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    String name = jsonObject.getString("name");
                                    int namesId = jsonObject.getInt("id");

                                    StatesNames.add(name);
                                    StateNamesId.add(namesId);
                                    // Toast.makeText(getApplicationContext(),StatesNames+"----"+i,Toast.LENGTH_SHORT).show();

                                }
                              //  Toast.makeText(getApplicationContext(), StatesNames + "     ", Toast.LENGTH_SHORT).show();

                                ArrayAdapter<String> spnStateNameListAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_item, StatesNames);
                                consultStateName.setAdapter(spnStateNameListAdapter);


                                consultStateName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        _stateId = StateNamesId.get(i);
                                       // Toast.makeText(getApplicationContext(), _stateId + "", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                                // StatesNames.clear();
                                // StatesNames.clear();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }


                    }
                });
            }
        }, 1, 1000);


//        String url1 = Constant.BASE_URL+"selectAmlak";
//        JsonArray jsonArray = new JsonArray();
//        try {
//
//        }
//        JsonObject generateJsonObject(String keyName,String value)
//        {
//            JsonObject object = new JsonObject();
//            object.put("area","setKhahadSHod");
//            return object;
//        }
//        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//            }
//        };
//        Response.ErrorListener errorListener = new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        };
//        JsonArrayRequest request = (Request.Method.POST,url1,l)


    }

    private void IfConsultDataInserted() {
        mobileNumber = Read.readDataFromStorage("mobile", "NO_MOBILE", getApplicationContext());
        strName = consultName.getText().toString();
        strFamily = consultFamily.getText().toString();

        strCity = spnCity.getSelectedItem().toString();
        if(!strCity.equals("")){
            _cityId = Integer.valueOf(StringToId.City(strCity));
        }
        strArea = spnArea.getSelectedItem().toString();
        strConsultStateName = consultStateName.getSelectedItem().toString();

        strPass = edtPass.getText().toString();

        strEmail = edtEmail.getText().toString().trim();


        if (CheckInternet.Checked(getApplicationContext())) {
            if (!strName.equals("")) {
                if (!strFamily.equals("")) {
                    if (!strCity.equals(Constant.SELECT_CITY)) {
                        if (!strArea.equals(Constant.SELECT_AREA)) {
                            if (!strConsultStateName.equals("")) {
                                if (!strPass.equals("")) {
                                    registerConsultant();
                                } else
                                    SnackBar.Create(coordinatorLayoutRegister, "وارد کردن رمز عبور برای ورود به وبسایت الزامی می باشد", 3);
                            } else
                                SnackBar.Create(coordinatorLayoutRegister, "لطفا نام املاکی مورد نظر خود را انتخاب نمایید", 3);
                        } else
                            SnackBar.Create(coordinatorLayoutRegister, "لطفا ناحیه مورد نظر خود را انتخاب نمایید", 3);
                    } else
                        SnackBar.Create(coordinatorLayoutRegister, "لطفا شهر مورد نظر خود را انتخاب نمایید", 3);
                } else
                    SnackBar.Create(coordinatorLayoutRegister, "لطفا نام خانوادگی خود را وارد نمایید", 3);
            } else
                SnackBar.Create(coordinatorLayoutRegister, "لطفا نام خود را وارد نمایید", 3);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SnackBar.Create(coordinatorLayoutRegister, "اتصال به اینترنت قطع می باشد", 3);
                }
            });
        }
    }

    private void IfStateDataInserted() {
        mobileNumber = Read.readDataFromStorage("mobile", "NO_MOBILE", getApplicationContext());
        strName = consultName.getText().toString();
        strFamily = consultFamily.getText().toString();
        strEmail = edtEmail.getText().toString().trim();
        // strTell = edtTell.getText().toString().trim();
        strAddress = edtAddress.getText().toString();
        strCity = spnCity.getSelectedItem().toString();
        if(!strCity.equals("")){
            _cityId = Integer.valueOf(StringToId.City(strCity));
        }
        strArea = spnArea.getSelectedItem().toString();
        strCode = edtCode.getText().toString();
        strPass = edtPass.getText().toString();

        if (CheckInternet.Checked(getApplicationContext())) {
            if (!strName.equals("")) {
                if (!strFamily.equals("")) {
                    if (!strCity.equals(Constant.SELECT_CITY)) {
                        if (!strArea.equals(Constant.SELECT_AREA)) {
                            if (!strAddress.equals("")) {
                                //1
                                if (!strPass.equals("")) {
                                    if (isLocationSelect)
                                        registerStatment();
                                    else
                                        SnackBar.Create(coordinatorLayoutRegister, "لوکیشن خود را وارد نمایید", 3);
                                } else
                                    SnackBar.Create(coordinatorLayoutRegister, "وارد کردن رمز عبور برای ورود به وبسایت الزامی می باشد", 3);
                            } else
                                SnackBar.Create(coordinatorLayoutRegister, "آدرس دفتر املاک را وارد نمایید", 3);
                        } else
                            SnackBar.Create(coordinatorLayoutRegister, "لطفا ناحیه مورد نظر خود را انتخاب نمایید", 3);
                    }else
                        SnackBar.Create(coordinatorLayoutRegister, "لطفا شهر مورد نظر خود را انتخاب نمایید", 3);
                } else
                    SnackBar.Create(coordinatorLayoutRegister, "نام و نام خانوادگی خود را وارد نمایید", 3);
            } else
                SnackBar.Create(coordinatorLayoutRegister, "نام دفتر املاک را وارد نمایید", 3);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    SnackBar.Create(coordinatorLayoutRegister, "اتصال به اینترنت قطع می باشد", 3);
                }
            });
        }
    }

    private void registerStatment() {
        //Data for send to server
        HashMap hashMap = new HashMap();
        hashMap.put(Constant.NAME, strName);
        hashMap.put(Constant.LAST_NAME, strFamily);
        hashMap.put(Constant.REQUEST_TYPE, Constant.INSERT);
        hashMap.put(Constant.MOBILE, mobileNumber);
        hashMap.put(Constant.MAIL, strEmail);

        hashMap.put(Constant.AREA, _areaCode);
        hashMap.put(Constant.CITY, _cityId);
        //   hashMap.put(Constant.SELLER_TELL, strTell);
        hashMap.put(Constant.ADDRESS, strAddress);
        hashMap.put(Constant.PASS, strPass);
        //1
        hashMap.put("role", role);

        hashMap.put(Constant.STATES_ID, strCode);

        //  hashMap.put(Constant.PROVINCE, StringToId.State(strCity));

        if (isProfileImageSelected)
            hashMap.put(Constant.IMAGE, Picture.getStringImage(bitmapProfile, Constant.IMAGE_MAX_SIZE));

        if (isAllowedImageSelected)
            hashMap.put(Constant.LICENSE_IMAGE, Picture.getStringImage(bitmapAllowed, Constant.IMAGE_MAX_SIZE));

        if (isLocationSelect) {
            hashMap.put(Constant.GEO_LAT, strLat);
            hashMap.put(Constant.GEO_LON, strLon);
        } else {
            hashMap.put(Constant.GEO_LAT, "NO_LOCATION");
            hashMap.put(Constant.GEO_LON, "NO_LOCATION");
        }

        final JSONObject jsonData = new JSONObject(hashMap);

        final CustomProgressDialog progressDialog = new CustomProgressDialog(RegisterActivity.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTextSize(15);
        progressDialog.show();

        //register URL
        final String url = Constant.BASE_URL + "registerCst";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("result").equals("ok")) {
                        Write.writeDataInStorage(Constant.USER_ID, response.getString("user_id"), getApplicationContext());
                        //1
                        Write.writeDataInStorage(Constant.ROLE,role,getApplicationContext());
                        //1
                        //Write.writeDataInStorage(Constant.ROLE, response.getString("user_id"), getApplicationContext());

                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        RegisterActivity.this.finish();
                    } else
                        SnackBar.Create(coordinatorLayoutRegister, "عملیات ثبت نام ناموفق بود", 3);
                } catch (JSONException e) {
                    Log.i(G.TAG, e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                //1
                Log.i(G.TAG, String.valueOf(e));
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

    private void registerConsultant() {
        //Data for send to server
        HashMap hashMap = new HashMap();

        hashMap.put(Constant.REQUEST_TYPE, Constant.INSERT);
        hashMap.put(Constant.NAME, strName);
        hashMap.put(Constant.LAST_NAME, strFamily);
        hashMap.put(Constant.MOBILE, mobileNumber);
        hashMap.put(Constant.MAIL, strEmail);
        hashMap.put(Constant.AREA, _areaCode);
        hashMap.put(Constant.PROVINCE, _cityId);
        // hashMap.put(Constant.CONSULT_STATE_NAME,strConsultStateName);
        hashMap.put(Constant.CONS_ID, _stateId);
        //hashMap.put(Constant.STATES_ID, strCode);
        // hashMap.put(Constant.SELLER_TELL, strTell);
        hashMap.put(Constant.PASS, strPass);
        //1
        hashMap.put(Constant.ROLE, role);

        //  hashMap.put(Constant.PROVINCE, StringToId.State(strArea));

        final JSONObject jsonData = new JSONObject(hashMap);

        final CustomProgressDialog progressDialog = new CustomProgressDialog(RegisterActivity.this);
        Typeface typeFace = ResourcesCompat.getFont(this, R.font.font_family);
        progressDialog.setTypeface(typeFace);
        progressDialog.setMessage("در حال ارسال...");
        progressDialog.setIndicatorColor(R.color.mainColor);
        progressDialog.setTextColor(R.color.textColor);
        progressDialog.setTextSize(15);
        progressDialog.show();

        //register URL
        final String url = Constant.BASE_URL + "registerAgent";

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getString("result").equals("ok")) {
                        Write.writeDataInStorage(Constant.USER_ID, response.getString("agent_id"), getApplicationContext());
                        //1
                        Write.writeDataInStorage(Constant.ROLE,role,getApplicationContext());
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                        RegisterActivity.this.finish();
                    } else
                        SnackBar.Create(coordinatorLayoutRegister, "عملیات ثبت نام ناموفق بود", 3);
                } catch (JSONException e) {
                    Log.i(G.TAG, e.getMessage());
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                //1
                Log.i(G.TAG, String.valueOf(e));
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
