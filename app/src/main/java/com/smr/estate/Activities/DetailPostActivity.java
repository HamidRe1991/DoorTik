package com.smr.estate.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.smr.estate.Dialog.ArchiveActivity;
import com.smr.estate.Dialog.OfferActivity;
import com.smr.estate.Dialog.SellerActivity;
import com.smr.estate.Dialog.ShowMapsActivity;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Application.G;
import com.smr.estate.Constant.Constant;
import com.smr.estate.CustomView.RowInfo;
import com.smr.estate.Model.BuildingPostDetails;
import com.smr.estate.Utils.AppSingleton;
import com.smr.estate.Utils.IdToString;
import com.smr.estate.Utils.StringToId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import wiadevelopers.com.library.DivarUtils;
import wiadevelopers.com.library.MaterialLoading.MaterialProgressBar;

//Show detail post class

public class DetailPostActivity extends AppCompatActivity
{
    private RelativeLayout rltContainer;
    private NestedScrollView scrollView;
    private View colorState, margin, viewNoInternetConnection;
    private LinearLayout lnrContainer;
    private TextView txtTitle, txtDate, txtPrice, txtType, txtPublic;
    private FloatingActionButton fabInfo;
    private ImageView imgBack, imgDelete, imgEdit, imgShare;
    private MaterialProgressBar progressBar;
    private Button btnArchive, btnOffer, btnMap;
    private CoordinatorLayout coordinatorLayoutDetail;
    private AlertDialog alertDialog;
    private CardView crdTools;

    private String strSellerName, strSellerTell, type, Lat, Lon, cat, vip = null;
    private String image1 = null, image2 = null, image3 = null, image4 = null, image5 = null;

    private int bannerHeight = -1, postId = 0, cons_id;
    private boolean isLoad = false, isLoc = false;

    private Gson gson = new Gson();
    private BuildingPostDetails details;
    private JSONObject jsonObjectEdit = new JSONObject();
    private SliderLayout sliderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_post);

        initialize();
    }

    private void initialize()
    {
        findViews();
        setupActivity();
        setupListeners();
    }

    private void findViews()
    {
        btnOffer = findViewById(R.id.btnFirst);
        imgShare = findViewById(R.id.imgShare);
        txtPublic = findViewById(R.id.txtPublic);
        coordinatorLayoutDetail = findViewById(R.id.coordinatorDetail);
        btnMap = findViewById(R.id.btnMap);
        txtType = findViewById(R.id.txtType);
        imgEdit = findViewById(R.id.imgEdit);
        scrollView = findViewById(R.id.scrollView);
        rltContainer = findViewById(R.id.rltContainer);
        viewNoInternetConnection = findViewById(R.id.viewNoInternetConnection);
        progressBar = findViewById(R.id.progressBar);
        margin = findViewById(R.id.margin);
        txtTitle = findViewById(R.id.txtTitle);
        txtDate = findViewById(R.id.txtDate);
        lnrContainer = findViewById(R.id.lnrContainer);
        sliderLayout = findViewById(R.id.imageSlider);
        fabInfo = findViewById(R.id.fabInfo);
        imgBack = findViewById(R.id.imgBack);
        imgDelete = findViewById(R.id.imgDelete);
        txtPrice = findViewById(R.id.txtPrice);
        colorState = findViewById(R.id.colorState);
        btnArchive = findViewById(R.id.btnArchive);
        crdTools = findViewById(R.id.crdTools);
    }

    private void setupActivity()
    {
        postId = getIntent().getIntExtra(Constant.KEY_POST_ID, 0);

        image1 = getIntent().getStringExtra(Constant.KEY_IMAGE1);
        image2 = getIntent().getStringExtra(Constant.KEY_IMAGE2);
        image3 = getIntent().getStringExtra(Constant.KEY_IMAGE3);
        image4 = getIntent().getStringExtra(Constant.KEY_IMAGE4);
        image5 = getIntent().getStringExtra(Constant.KEY_IMAGE5);

        bannerHeight = DivarUtils.widthPX;
        sliderLayout.getLayoutParams().height = bannerHeight;
        margin.getLayoutParams().height = bannerHeight;

        setSliderViews();
        getPostDetails();
    }

    //Get details for post
    private void getPostDetails()
    {
        String url = Constant.BASE_URL + "estatePoss";

        rltContainer.setVisibility(View.VISIBLE);
        viewNoInternetConnection.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        sliderLayout.setVisibility(View.GONE);

        JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put(Constant.ESTATE_ID, postId);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(jsonData);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                rltContainer.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                sliderLayout.setVisibility(View.VISIBLE);

                isLoad = true;

                details = gson.fromJson(response.toString(), BuildingPostDetails.class);

                jsonObjectEdit = response;

                cons_id = details.getEstate().getCons_id();

                type = String.valueOf(details.getSell().get(0).getSell_id());

                if (getIntent().getBooleanExtra(Constant.KEY_IS_EXPIRE, false))
                {
                    txtPrice.setText(details.getEstate().getPrice());
                    crdTools.setVisibility(View.INVISIBLE);
                } else
                {
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###", symbols);

                    String price = details.getEstate().getPrice();
                    if (type.equals(Constant.SALE_ID))
                    {
                        txtPrice.setText("قیمت کل: " + decimalFormat.format(Integer.parseInt(price)) + " تومان");
                    } else if (type.equals(Constant.FULL_MORTGAGE_ID))
                    {
                        txtPrice.setText("قیمت کل: " + decimalFormat.format(Integer.parseInt(price)) + " تومان");
                    } else if (type.equals(Constant.RENT_ID))
                    {
                        String rent = details.getEstate().getRent();
                        txtPrice.setText("ودیعه: " + decimalFormat.format(Integer.parseInt(price)) + " تومان" + "\n" + "اجاره: " + decimalFormat.format(Integer.parseInt(rent)) + " تومان");
                    } else if (type.equals(Constant.PRE_BUY_ID))
                    {
                        txtPrice.setText("ارزش ملک: " + decimalFormat.format(Integer.parseInt(price)) + " تومان");
                    } else if (type.equals(Constant.SWAP_ID))
                    {
                        txtPrice.setText("ارزش ملک: " + decimalFormat.format(Integer.parseInt(price)) + " تومان");
                    } else if (type.equals(Constant.TAKING_PART_ID))
                    {
                        txtPrice.setText("ارزش ملک: " + decimalFormat.format(Integer.parseInt(price)) + " تومان");
                    }
                }

                try
                {
                    Lat = details.getEstate().getGeo_lat();
                    Lon = details.getEstate().getGeo_lon();
                    if (!Lat.equals("NO_LOCATION") && !Lat.equals("NO_LOCATION"))
                        isLoc = true;
                } catch (Exception e)
                {
                    Log.i(G.TAG, e.getMessage());
                }

                try
                {
                    int publicAds = details.getEstate().getStatus();

                    if (publicAds == 1)
                    {
                        if (!details.getOffer().get(0).getName().isEmpty())
                            vip = details.getOffer().get(0).getName();
                        if (vip != null)
                        {
                            txtPublic.setText("عمومی " + "(" + vip + ")");
                        } else
                            txtPublic.setText("عمومی");
                    } else
                    {
                        txtPublic.setText("غیر عمومی");
                    }
                } catch (Exception e)
                {
                    Log.i(G.TAG, e.getMessage());
                }

                txtDate.setText(details.getEstate().getCreated_at());
                txtTitle.setText(details.getEstate().getName());

                cat = IdToString.Category(String.valueOf(details.getEstate().getType()));
                txtType.setText(IdToString.Selling(type) + " (" + cat + ")");

                strSellerName = details.getEstate().getSeller_name();
                strSellerTell = details.getEstate().getTell();

                ArrayList<RowInfo> rowInfoTemp = new ArrayList<>();

                rowInfoTemp.add(new RowInfo("متراژ", String.valueOf(details.getEstate().getBreadth())));

                rowInfoTemp.add(new RowInfo("سال ساخت", String.valueOf(details.getEstate().getBuilt_in())));

                String areaName;
                String area = String.valueOf(details.getEstate().getArea());
                areaName = IdToString.Area(area);
                rowInfoTemp.add(new RowInfo(("منطقه"), areaName));
                rowInfoTemp.add(new RowInfo(("آدرس"), details.getEstate().getAdd()));

                int officialDoc = details.getEstate().getOfficial_doc();
                String docName;
                if (officialDoc == 1)
                    docName = "دارد";
                else
                    docName = "ندارد";
                rowInfoTemp.add(new RowInfo("سند", docName));

                rowInfoTemp.add(new RowInfo("توضیحات", details.getEstate().getDiscription() + "\n"));

                if (details.getPoss().size() > 0)
                {
                    String posCount = "";
                    String posUnCount = "";

                    for (int i = 0; i < details.getPoss().size(); i++)
                    {
                        if (!details.getPoss().get(i).getValue().equals("uncountable"))
                        {
                            posCount += details.getPoss().get(i).getName() + " (" + details.getPoss().get(i).getValue() + " عدد)" + "\n";
                        } else
                        {
                            posUnCount += details.getPoss().get(i).getName() + "\n";
                        }
                    }

                    rowInfoTemp.add(new RowInfo("امکانات", posCount));
                    rowInfoTemp.add(new RowInfo("تجهیزات", posUnCount));
                }


                for (int i = 0; i < rowInfoTemp.size(); i++)
                    lnrContainer.addView(rowInfoTemp.get(i).getView());
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                scrollView.setVisibility(View.GONE);
                sliderLayout.setVisibility(View.GONE);
                rltContainer.setVisibility(View.VISIBLE);
                viewNoInternetConnection.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
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

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void setSliderViews()
    {
        int Count = 0;
        for (int i = 0; i < 5; i++)
        {
            SliderView sliderView = new SliderView(this);

            switch (i)
            {
                case 0:
                    if (!image1.equals(Constant.IMG_CHECK) && !image1.equals(Constant.IMAGE_URL) && !image1.equals(Constant.KEY_NO_IMAGE) && image1 != null)
                    {
                        sliderView.setImageUrl(image1);
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        sliderLayout.addSliderView(sliderView);
                        Count++;
                    }
                    break;
                case 1:
                    if (!image2.equals(Constant.IMAGE_URL + "null") && !image2.equals(Constant.KEY_NO_IMAGE) && image2 != null)
                    {
                        sliderView.setImageUrl(image2);
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        sliderLayout.addSliderView(sliderView);
                        Count++;
                    }
                    break;
                case 2:
                    if (!image3.equals(Constant.IMAGE_URL + "null") && !image3.equals(Constant.KEY_NO_IMAGE) && image3 != null)
                    {
                        sliderView.setImageUrl(image3);
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        sliderLayout.addSliderView(sliderView);
                        Count++;
                    }
                    break;
                case 3:
                    if (!image4.equals(Constant.IMAGE_URL + "null") && !image4.equals(Constant.KEY_NO_IMAGE) && image4 != null)
                    {
                        sliderView.setImageUrl(image4);
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        sliderLayout.addSliderView(sliderView);
                        Count++;
                    }
                    break;
                case 4:
                    if (!image5.equals(Constant.IMAGE_URL + "null") && !image1.equals(Constant.KEY_NO_IMAGE) && image5 != null)
                    {
                        sliderView.setImageUrl(image5);
                        sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                        sliderLayout.addSliderView(sliderView);
                        Count++;
                    }
                    break;
            }
        }

        if (Count == 0)
        {
            SliderView sliderView = new SliderView(this);
            sliderView.setImageUrl(Constant.IMG_CHECK);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderLayout.addSliderView(sliderView);
        }
    }

    public static int getScrollViewUpperBound(NestedScrollView scrollView)
    {
        int diff = scrollView.getChildAt(scrollView.getChildCount() - 1).getMeasuredHeight() - scrollView.getMeasuredHeight();
        return diff;
    }

    private void setupListeners()
    {
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener()
        {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY)
            {
                int diff = bannerHeight - scrollY;

                if (diff < 0)
                    DivarUtils.setViewHeigth(sliderLayout, 0);
                else
                    DivarUtils.setViewHeigth(sliderLayout, DivarUtils.Px2Dp(diff));

                float upperBound = getScrollViewUpperBound(v);

                float ratio = scrollY / upperBound;

                colorState.setAlpha(ratio);
            }
        });

        viewNoInternetConnection.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getPostDetails();
            }
        });

        imgDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                deleteAdsDialog(String.valueOf(postId));
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DetailPostActivity.this.finish();
            }
        });

        fabInfo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailPostActivity.this, SellerActivity.class);
                intent.putExtra("sellerName", strSellerName);
                intent.putExtra("sellerTell", strSellerTell);
                startActivity(intent);
            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editAds();
            }
        });

        btnMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isLoc)
                {
                    Intent intent = new Intent(DetailPostActivity.this, ShowMapsActivity.class);
                    intent.putExtra("Lat", Lat);
                    intent.putExtra("Lon", Lon);
                    startActivity(intent);
                } else
                    SnackBar.Create(coordinatorLayoutDetail, "مکانی برای این ملک ثبت نشده است",3);
            }
        });

        btnArchive.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Gson gson = new Gson();
                BuildingPostDetails details = gson.fromJson(jsonObjectEdit.toString(), BuildingPostDetails.class);

                Intent intent = new Intent(DetailPostActivity.this, ArchiveActivity.class);

                intent.putExtra(Constant.KEY_PRICE, details.getEstate().getPrice());
                intent.putExtra(Constant.KEY_POST_ID, String.valueOf(details.getEstate().getId()));
                intent.putExtra(Constant.KEY_TYPE, String.valueOf(details.getSell().get(0).getSell_id()));
                Log.i(G.TAG, String.valueOf(details.getEstate().getType()));

                startActivity(intent);
            }
        });

        imgShare.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (isLoc)
                {
                    final String appName = "org.telegram.messenger";
                    final boolean isAppInstalled = isAppAvailable(getApplicationContext(), appName);

                    String url = "http://maps.google.com/maps?q=" + Lat + "," + Lon;

                    if (isAppInstalled)
                    {
                        Intent myIntent = new Intent(Intent.ACTION_SEND);
                        myIntent.setType("text/plain");
                        myIntent.setPackage(appName);
                        myIntent.putExtra(Intent.EXTRA_TEXT, url);
                        startActivity(Intent.createChooser(myIntent, "Share with"));
                    } else
                    {
                        SnackBar.Create(coordinatorLayoutDetail, "برنامه ی تلگرام نصب نیست", 3);
                    }
                } else
                {
                    SnackBar.Create(coordinatorLayoutDetail, "مکانی برای این ملک ثبت نشده است", 3);
                }
            }
        });

        btnOffer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(DetailPostActivity.this, OfferActivity.class);
                intent.putExtra("est_id", postId);
                intent.putExtra("cons_id", cons_id);
                startActivity(intent);
            }
        });
    }

    //Check install app by package name
    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }

    private void deleteAds(String ads_id)
    {
        String url = Constant.BASE_URL + "deleteEstate";

        final JSONObject jsonData = new JSONObject();
        try
        {
            jsonData.put(Constant.KEY_ID, ads_id);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Intent intent = new Intent(DetailPostActivity.this, MainActivity.class);
                startActivityForResult(intent, Constant.REQUEST_DELETE_ADS);
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.i(G.TAG, error.getMessage());
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

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);
    }

    private void deleteAdsDialog(final String id)
    {
        try
        {
            LayoutInflater myLayout = LayoutInflater.from(this);
            final View view = myLayout.inflate(R.layout.dialog_yes_or_no, null);

            final TextView tvTitle, tvYes, tvNo;

            tvTitle = view.findViewById(R.id.tvDialogTitle);
            tvYes = view.findViewById(R.id.tvYes);
            tvNo = view.findViewById(R.id.tvNo);

            tvTitle.setText("آگهی مورد نظر حذف شود؟");

            tvNo.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    alertDialog.dismiss();
                }
            });

            tvYes.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    deleteAds(id);
                    alertDialog.dismiss();
                }
            });

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setView(view);
            alertDialogBuilder.setCancelable(true);
            alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        }
    }

    private void editAds()
    {
        details = gson.fromJson(jsonObjectEdit.toString(), BuildingPostDetails.class);

        HashMap map = new HashMap();
        HashMap mapPoss = new HashMap();

        map.put(Constant.KEY_TYPE, IdToString.Selling(type));
        map.put(Constant.KEY_CAT, cat);
        map.put(Constant.KEY_POST_ID, details.getEstate().getId());
        map.put(Constant.NAME, details.getEstate().getName());
        map.put(Constant.BREADTH, details.getEstate().getBreadth());
        map.put(Constant.PRICE, details.getEstate().getPrice());
        map.put(Constant.GEO_LAT, details.getEstate().getGeo_lat());
        map.put(Constant.GEO_LON, details.getEstate().getGeo_lon());
        map.put(Constant.ADDRESS, details.getEstate().getAdd());
        map.put(Constant.AREA, details.getEstate().getArea());
        map.put(Constant.SELLER_NAME, details.getEstate().getSeller_name());
        map.put(Constant.SELLER_TELL, details.getEstate().getTell());
        map.put(Constant.BUILT_IN, details.getEstate().getBuilt_in());
        map.put(Constant.DESCRIPTION, details.getEstate().getDiscription());
        map.put(Constant.OFFICIAL_DOCUMENT, details.getEstate().getOfficial_doc());

        map.put(Constant.KEY_IMAGE1, image1);
        map.put(Constant.KEY_IMAGE2, image2);
        map.put(Constant.KEY_IMAGE3, image3);
        map.put(Constant.KEY_IMAGE4, image4);
        map.put(Constant.KEY_IMAGE5, image5);

        try
        {
            int publicAds = details.getEstate().getStatus();
            String publicStr;
            int vipId = -1;

            if (publicAds == 1)
            {
                publicStr = "1";
                vipId = details.getOffer().get(0).getOffer_id();

                if (vipId != -1)
                    map.put(Constant.OFFER, vipId);
                else
                    map.put(Constant.OFFER, "NO_OFFER");

            } else
                publicStr = "0";

            map.put(Constant.IS_PUBLIC, publicStr);
        } catch (Exception e)
        {
            Log.i(G.TAG, e.getMessage());
        }

        //Restore Image
        map.put(Constant.IMAGE, "NO_IMAGE");

        if (type.equals(Constant.RENT))
        {
            map.put(Constant.RENT, details.getEstate().getRent());
        }

        map.put(Constant.KEY_SET_ADS, getSetAdsId(type, cat));

        for (int i = 0; i < details.getPoss().size(); i++)
            mapPoss.put(String.valueOf(StringToId.Possibilities(details.getPoss().get(i).getName())), details.getPoss().get(i).getValue());

        JSONObject jsonObject = new JSONObject(map);
        JSONObject jsonObjectPoss = new JSONObject(mapPoss);

        Intent intent = new Intent(DetailPostActivity.this, CreatePostActivity.class);
        intent.putExtra(Constant.KEY_DRAFT, jsonObject.toString());
        intent.putExtra(Constant.KEY_DRAFT_POSS, jsonObjectPoss.toString());
        intent.putExtra(Constant.KEY_DRAFT_CHECK, true);
        startActivity(intent);
    }

    private static String getSetAdsId(String sell, String cat)
    {
        //Sale
        if (sell.equals(Constant.SALE_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.VILLAGE) ||
                cat.equals(Constant.SUITES))
            return "11";
        else if (sell.equals(Constant.SALE_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP))
            return "12";
        else if (sell.equals(Constant.SALE_ID) &&
                cat.equals(Constant.GARDEN) ||
                cat.equals(Constant.LAND_PLOTS) ||
                cat.equals(Constant.FARM))
            return "13";
        else if (sell.equals(Constant.SALE_ID) &&
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.HOTEL) ||
                cat.equals(Constant.STORE) ||
                cat.equals(Constant.FACTORY))
            return "14";

            //Full Mortgage
        else if (sell.equals(Constant.FULL_MORTGAGE_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.VILLAGE) ||
                cat.equals(Constant.SUITES))
            return "21";
        else if (sell.equals(Constant.FULL_MORTGAGE_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP))
            return "22";
        else if (sell.equals(Constant.FULL_MORTGAGE_ID) &&
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.STORE))
            return "24";

            //Rent
        else if (sell.equals(Constant.RENT_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.VILLAGE) ||
                cat.equals(Constant.SUITES))
            return "31";
        else if (sell.equals(Constant.RENT_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP) ||
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.STORE))
            return "32";

            //Pre Buy
        else if (sell.equals(Constant.PRE_BUY_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.SUITES))
            return "41";
        else if (sell.equals(Constant.PRE_BUY_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP))
            return "42";
        else if (sell.equals(Constant.PRE_BUY_ID) &&
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.HOTEL) ||
                cat.equals(Constant.STORE) ||
                cat.equals(Constant.FACTORY))
            return "43";

            //Swap
        else if (sell.equals(Constant.SWAP_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.VILLAGE) ||
                cat.equals(Constant.SUITES))
            return "51";
        else if (sell.equals(Constant.SWAP_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP))
            return "52";
        else if (sell.equals(Constant.SWAP_ID) &&
                cat.equals(Constant.GARDEN) ||
                cat.equals(Constant.LAND_PLOTS) ||
                cat.equals(Constant.FARM))
            return "53";
        else if (sell.equals(Constant.SWAP_ID) &&
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.HOTEL) ||
                cat.equals(Constant.STORE) ||
                cat.equals(Constant.FACTORY))
            return "54";

            //Taking Part
        else if (sell.equals(Constant.TAKING_PART_ID) &&
                cat.equals(Constant.PENT_HOUSE) ||
                cat.equals(Constant.APARTMENT) ||
                cat.equals(Constant.RESIDENTIAL_COMPLEX) ||
                cat.equals(Constant.HOUSE) ||
                cat.equals(Constant.VILLAGE) ||
                cat.equals(Constant.SUITES))
            return "61";
        else if (sell.equals(Constant.TAKING_PART_ID) &&
                cat.equals(Constant.OFFICE) ||
                cat.equals(Constant.SHOP))
            return "62";
        else if (sell.equals(Constant.TAKING_PART_ID) &&
                cat.equals(Constant.GARDEN) ||
                cat.equals(Constant.LAND_PLOTS) ||
                cat.equals(Constant.FARM) ||
                cat.equals(Constant.WORKSHOP) ||
                cat.equals(Constant.HALL) ||
                cat.equals(Constant.HOTEL) ||
                cat.equals(Constant.STORE) ||
                cat.equals(Constant.FACTORY))
            return "63";
        else
            return null;
    }
}