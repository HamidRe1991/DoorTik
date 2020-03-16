package com.smr.estate.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.smr.estate.Activities.ShowConsListOfStates;
import com.smr.estate.Dialog.BuyCoin;
import com.smr.estate.Dialog.BuyCredit;
import com.smr.estate.Activities.LoginActivity;
import com.smr.estate.Dialog.ShowMapsActivity;
import com.smr.estate.Activities.UpdateProfileActivity;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.Internet.CheckInternet;
import com.smr.estate.R;
import com.smr.estate.Storage.Read;
import com.smr.estate.Storage.Write;
import com.smr.estate.Utils.AppSingleton;
import com.smr.estate.Utils.AsyncTaskShowConRequset;
import com.smr.estate.Utils.IdToString;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import wiadevelopers.com.library.Components.CustomProgressDialog;

public class ProfileFragment extends Fragment {
    private CircleImageView imgProfile;
    private TextView tvName, tvFamily, tvMobile, tvState, tvEmail, tvTell, tvAddress;
    private Button btnExit, btnEditProfile;
    private SharedPreferences setting;
    private LinearLayout lnrShowLoc, lnrBuyCoin, lnrBuyCredit, lnrCheckRequest;
    private ImageView imgLicense;
    private SwipeRefreshLayout swipeRefreshLayoutProfile;

    private String image, user_id;

    private CoordinatorLayout coordinatorLayoutProfile;

    private String Lat = "0";
    private String Lon = "0";

    private TextView tvDorCoin, tvCredit, tvCode;

    private boolean license = false;

    private int getRole;

    public static String list = "";

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        initialize(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getRole == 2) {
            getStateProfile();
        }
        if (getRole == 1) {
            getConsultantProfile();
        }

    }


    private void initialize(View view) {
        findView(view);
        setUpListener();
        if (getRole == 2) {
            getStateProfile();
        }
        if (getRole == 1) {
            getConsultantProfile();
        }
    }

    private void findView(View view) {
        lnrShowLoc = view.findViewById(R.id.lnrShowLocation);
        coordinatorLayoutProfile = view.findViewById(R.id.coordinatorProfile);
        setting = PreferenceManager.getDefaultSharedPreferences(view.getContext());
        tvName = view.findViewById(R.id.tvProfileName);
        tvFamily = view.findViewById(R.id.tvProfileFamliy);
        tvMobile = view.findViewById(R.id.tvProfileMobile);
        tvState = view.findViewById(R.id.tvProfileState);
        tvEmail = view.findViewById(R.id.tvProfileEmail);
        tvTell = view.findViewById(R.id.tvProfileTell);
        tvAddress = view.findViewById(R.id.tvProfileAddress);
        imgProfile = view.findViewById(R.id.imgProfile);
        btnExit = view.findViewById(R.id.btnExitProfile);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        tvDorCoin = view.findViewById(R.id.tvCoin);
        tvCredit = view.findViewById(R.id.tvCredit);
        tvCode = view.findViewById(R.id.tvCode);
        lnrBuyCredit = view.findViewById(R.id.lnrBuyCredit);
        lnrBuyCoin = view.findViewById(R.id.lnrBuyCoin);
        imgLicense = view.findViewById(R.id.imgLicense);
        swipeRefreshLayoutProfile = view.findViewById(R.id.swipeRefreshProfile);
        //1
        lnrCheckRequest = view.findViewById(R.id.lnrRequest);

        getRole = Read.readDataFromStorage("role", 5, getActivity());
        if (getRole == 2) {
            lnrCheckRequest.setVisibility(View.VISIBLE);
        }
        if (getRole == 1) {
            lnrCheckRequest.setVisibility(View.GONE);
            lnrShowLoc.setVisibility(View.GONE);
            imgProfile.setVisibility(View.GONE);
            imgLicense.setVisibility(View.GONE);
        }
        Toast.makeText(getActivity(), getRole + "", Toast.LENGTH_SHORT).show();
    }

    private void setUpListener() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SnackBar.Create(coordinatorLayoutProfile, "برای خروج دکمه را نگه دارید", 3);
            }
        });

        btnExit.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences.Editor editor = setting.edit();
                editor.remove("user_id");
                editor.apply();

                startActivity(new Intent(getContext(), LoginActivity.class));
                return false;
            }
        });

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckInternet.Checked(getContext())) {
                    Intent intent = new Intent(getContext(), UpdateProfileActivity.class);
                    intent.putExtra("profileName", tvName.getText());
                    intent.putExtra("profileState", tvState.getText());
                    intent.putExtra("profileEmail", tvEmail.getText());
                    intent.putExtra("profileTell", tvTell.getText());
                    intent.putExtra("profileAddress", tvAddress.getText());
                    intent.putExtra("profileImage", image);
                    intent.putExtra("Lat", Lat);
                    intent.putExtra("Lon", Lon);
                    intent.putExtra("license", license);
                    startActivity(intent);
                } else
                    SnackBar.Create(coordinatorLayoutProfile, "ارتباط با اینترنت قطع می باشد", 3);
            }
        });

        lnrShowLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowMapsActivity.class);
                intent.putExtra("Lat", Lat);
                intent.putExtra("Lon", Lon);
                startActivity(intent);
            }
        });

        lnrBuyCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BuyCredit.class));
            }
        });

        lnrBuyCoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), BuyCoin.class));
            }
        });

        swipeRefreshLayoutProfile.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getStateProfile();
            }
        });

        lnrCheckRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowConsultantRequest();
            }
        });
    }

    private void ShowConsultantRequest() {
        new AsyncTaskShowConRequset(Constant.BASE_URL + "showAgent", user_id).execute();

        final CustomProgressDialog progressDialog = new CustomProgressDialog(getActivity());
        Typeface typeFace = ResourcesCompat.getFont(getActivity(), R.font.font_family);
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

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!(ShowConsListOfStates.consData.equals(""))) {
                            Toast.makeText(getActivity(), ShowConsListOfStates.consData + "", Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                            timer.cancel();

                            Intent intent = new Intent(getActivity(), ShowConsListOfStates.class);
                            startActivity(intent);
//                        }if(ShowConsListOfStates.consData.equals("")) {
//                            Confirmation confirmation = new Confirmation(getActivity(),"مشاوری جهت تایید ثبت نشده است");
//                            confirmation.show();
//                            timer.cancel();
//                            progressDialog.cancel();
//                            Toast.makeText(getActivity(),"dadei nis",Toast.LENGTH_SHORT).show();
//                        }
                        }
                    };
                });
            };
        }, 1, 1000);
    }


    private void getStateProfile() {
        final String url = Constant.BASE_URL + "getProfileCst";

        user_id = Read.readDataFromStorage("user_id", "0", getContext());

        HashMap hashMap = new HashMap();
        hashMap.put("user_id", user_id);

        final JSONObject jsonData = new JSONObject(hashMap);
        try {
            jsonData.put("user_id", user_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject profile = response.getJSONObject("profile");

                    String name = profile.getString("name");
                    if (!name.equals("null"))
                        tvName.setText(name);

                    String mobile = profile.getString("mobile");
                    if (!mobile.equals("null"))
                        tvMobile.setText(mobile);

                    String state = profile.getString("province");
                    tvState.setText(IdToString.State(state));

                    String email = profile.getString("mail");
                    if (!email.equals("null"))
                        tvEmail.setText(email);

//                    String tell = profile.getString("tel");
//                    if (!tell.equals("null"))
//                        tvTell.setText(tell);

                    String add = profile.getString("add");
                    if (!add.equals("null"))
                        tvAddress.setText(add);
                    tvDorCoin.setText(profile.getString("coin"));

                    tvCredit.setText(getDateDuration(profile.getString("credit")));

                    tvCode.setText(profile.getString("uniqid"));

//                    String licenseStr = profile.getString("license");
//
//                    if (licenseStr.equals("0"))
//                    {
//                        license = false;
//                        imgLicense.setVisibility(View.INVISIBLE);
//                        Write.writeDataInStorage("license", "0", getContext());
//                    } else
//                    {
//                        license = true;
//                        imgLicense.setVisibility(View.VISIBLE);
//                        Write.writeDataInStorage("license", "1", getContext());
//                    }
                    //1
//                    String family = profile.getString("manager");
//                    if (!family.equals("null"))
//                        tvFamily.setText(family);

//                    Lat = profile.getString("geo_lat");
//                    Lon = profile.getString("geo_lon");

//                    image = Constant.BASE_URL + "storage/" + profile.getString("image");
//
//                    Glide.with(getContext())
//                            .load(image)
//                            .apply(new RequestOptions()
//                                    .error(R.drawable.register_profile)
//                                    .placeholder(R.drawable.register_profile)
//                                    .fitCenter())
//                            .into(imgProfile);

                    swipeRefreshLayoutProfile.setRefreshing(false);

                } catch (JSONException e) {
                    Log.i(G.TAG, e.getMessage());
                    swipeRefreshLayoutProfile.setRefreshing(false);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(G.TAG, error.getMessage() + "");
                swipeRefreshLayoutProfile.setRefreshing(false);
            }
        };

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("Content-Type", "application/json; charset=utf-8");
                return hashMap;
            }
        };

        AppSingleton.getInstance(getContext()).addToRequestQueue(request);
    }

    //Function for set character date
    public static String getDateDuration(String date) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(c.getTime());

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = sdf.parse(date);
            d2 = sdf.parse(currentDate);
        } catch (ParseException var12) {
            var12.printStackTrace();
        }

        double diffIn = d1.getTime() - d2.getTime();
        double day = diffIn / 1000 / 60 / 60 / 24;
        int finalTemp = (int) Math.round(day);
        int finalNum = (finalTemp < 0 ? -finalTemp : finalTemp);

        String result = "";
        if (finalTemp >= 0)
            result = finalNum + "  روز مانده";
        else
            result = finalNum + "  روز گذشته";

        return result;
    }

    private void getConsultantProfile() {
        {
            final String url = Constant.BASE_URL + "getProfileAgent";

            user_id = Read.readDataFromStorage("user_id", "0", getContext());

            HashMap hashMap = new HashMap();
            hashMap.put("user_id", user_id);

            final JSONObject jsonData = new JSONObject(hashMap);
            try {
                jsonData.put("user_id", user_id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject profile = response.getJSONObject("profile");

                        tvDorCoin.setText(profile.getString("coin"));


                        String name = profile.getString("name");
                        if (!name.equals("null"))
                            tvName.setText(name);

                        String mobile = profile.getString("mobile");
                        if (!mobile.equals("null"))
                            tvMobile.setText(mobile);

                        String state = profile.getString("province");
                        tvState.setText(IdToString.State(state));

                        String email = profile.getString("mail");
                        if (!email.equals("null"))
                            tvEmail.setText(email);

                        String tell = profile.getString("tel");
                        if (!tell.equals("null"))
                            tvTell.setText(tell);

                        String add = profile.getString("add");
                        if (!add.equals("null"))
                            tvAddress.setText(add);

                        String licenseStr = profile.getString("license");

                        if (licenseStr.equals("0")) {
                            license = false;
                            imgLicense.setVisibility(View.INVISIBLE);
                            Write.writeDataInStorage("license", "0", getContext());
                        } else {
                            license = true;
                            imgLicense.setVisibility(View.VISIBLE);
                            Write.writeDataInStorage("license", "1", getContext());
                        }

                        String family = profile.getString("manager");
//                        if (!family.equals("null"))
//                            tvFamily.setText(family);

//                        Lat = profile.getString("geo_lat");
//                        Lon = profile.getString("geo_lon");


                        Toast.makeText(getActivity(),
                                profile.getString("coin"), Toast.LENGTH_LONG).show();

                        tvCredit.setText(getDateDuration(profile.getString("credit")));

                        tvCode.setText(profile.getString("uniqid"));

                        image = Constant.BASE_URL + "storage/" + profile.getString("image");

                        Glide.with(getContext())
                                .load(image)
                                .apply(new RequestOptions()
                                        .error(R.drawable.register_profile)
                                        .placeholder(R.drawable.register_profile)
                                        .fitCenter())
                                .into(imgProfile);

                        swipeRefreshLayoutProfile.setRefreshing(false);

                    } catch (JSONException e) {
                        Log.i(G.TAG, e.getMessage());
                        swipeRefreshLayoutProfile.setRefreshing(false);
                    }
                }
            };

            Response.ErrorListener errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i(G.TAG, error.getMessage() + "");
                    swipeRefreshLayoutProfile.setRefreshing(false);
                }
            };

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("Content-Type", "application/json; charset=utf-8");
                    return hashMap;
                }
            };

            AppSingleton.getInstance(getContext()).addToRequestQueue(request);
        }
    }

}
