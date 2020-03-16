package com.smr.estate.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.ramotion.foldingcell.FoldingCell;
import com.smr.estate.Application.G;
import com.smr.estate.Application.Masking;
import com.smr.estate.CreatePostItems.Checkbox;
import com.smr.estate.CreatePostItems.CreatePossRootView;
import com.smr.estate.CreatePostItems.InputAndSuffixForNumber;
import com.smr.estate.Dialog.MapsActivity;
import com.smr.estate.Dialog.SelectAreaActivity;
import com.smr.estate.Interface.SnackBar;
import com.smr.estate.R;
import com.smr.estate.Constant.Constant;
import com.smr.estate.CreatePostItems.ContainItems;
import com.smr.estate.CreatePostItems.CreatePostRootView;
import com.smr.estate.CreatePostItems.InputAndSuffix;
import com.smr.estate.CreatePostItems.JustInput;
import com.smr.estate.CreatePostItems.Optional;
import com.smr.estate.CreatePostItems.SelectImage;
import com.smr.estate.Dialog.SelectCategoryActivity;
import com.smr.estate.Storage.Read;
import com.smr.estate.Utils.AppSingleton;
import com.smr.estate.Utils.IdToString;
import com.smr.estate.Utils.Picture;
import com.smr.estate.Utils.RuntimePermissionsActivity;
import com.smr.estate.Utils.StringToId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import wiadevelopers.com.library.Components.CustomProgressDialog;
import wiadevelopers.com.library.DivarUtils;

//Create Ads class

public class CreatePostActivity extends RuntimePermissionsActivity
{
    private TextView txtToolbarTitle, txtSend;
    private Button btnChoose1, btnChoose2, btnChoose3, btnChooseArea, btnChooseLoc;
    private LinearLayout lnrContainer1, lnrInsertPoint, lnrInsertPoss;
    private RelativeLayout lnrContainer2, lnrContainer4, lnrContainer5;
    private CoordinatorLayout coordinatorLayoutCreate;
    private FoldingCell foldingCell;



    //1
    private FrameLayout frameLayoutSabteTajhizat;


    private String image1 = null, image2 = null, image3 = null, image4 = null, image5 = null;

    private ArrayList<CreatePostRootView> rootViews = new ArrayList<>();
    private ArrayList<CreatePossRootView> posViews = new ArrayList<>();

    private ArrayList<Bitmap> arrayBitmap = new ArrayList<>();

    private String areaTitle, post_id = null, typeId = null, title, id, strLat, strLon;

    private boolean isEdit = false, selectLoc = false, selectArea = false, isPublic = false;

    private ProgressDialog progressdialog;
    public static final int Progress_Dialog_Progress = 0;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_post);


        initialize();

        if (getIntent().getBooleanExtra(Constant.KEY_DRAFT_CHECK, false))
        {
            restoreData();
            btnChoose1.setEnabled(false);
            btnChoose2.setEnabled(false);
            btnChoose3.setEnabled(false);
        } else
        {
            //this playe load first dialog
            Intent intent = new Intent(CreatePostActivity.this, SelectCategoryActivity.class);
            startActivityForResult(intent, Constant.REQUEST_SELECT_CATEGORY);
        }
    }

    private void initialize()
    {
        CreatePostRootView.setContext(CreatePostActivity.this);
        findViews();
        setupActivity();
        setTypefaces();
        setupListeners();
    }

    @Override
    public void onPermissionsGranted(int requestCode)
    {
        for (int i = 0; i < rootViews.size(); i++)
        {
            CreatePostRootView rootView = rootViews.get(i);
            if (rootView instanceof SelectImage)
            {
                SelectImage selectImage = (SelectImage) rootView;
                selectImage.onPermissionsGranted(requestCode);
            }
        }
    }

    @Override
    public void onPermissionsDeny(int requestCode)
    {
        for (int i = 0; i < rootViews.size(); i++)
        {
            CreatePostRootView rootView = rootViews.get(i);
            if (rootView instanceof SelectImage)
            {
                SelectImage selectImage = (SelectImage) rootView;
                selectImage.onPermissionsDeny(requestCode);
            }
        }
    }

    private void findViews()
    {
        coordinatorLayoutCreate = findViewById(R.id.coordinatorCreate);
        txtSend = findViewById(R.id.txtSend);
        txtToolbarTitle = findViewById(R.id.txtToolbarTitle);
        lnrContainer1 = findViewById(R.id.lnrContainer1);
        lnrContainer2 = findViewById(R.id.lnrContainer2);
        btnChoose1 = findViewById(R.id.btnChoose1);
        btnChoose2 = findViewById(R.id.btnChoose2);
        btnChoose3 = findViewById(R.id.btnChoose3);
        btnChooseArea = findViewById(R.id.btnChooseArea);
        btnChooseLoc = findViewById(R.id.btnChooseLoc);
        lnrInsertPoint = findViewById(R.id.lnrContainer3);
        lnrContainer4 = findViewById(R.id.lnrContainer4);
        lnrContainer5 = findViewById(R.id.lnrContainer5);
        lnrInsertPoss = findViewById(R.id.lnrContainer6);
        foldingCell = findViewById(R.id.folding_cell);

        frameLayoutSabteTajhizat = findViewById(R.id.cellTitleView);
    }

    private void setTypefaces()
    {
        txtToolbarTitle.setTypeface(DivarUtils.face);
        txtSend.setTypeface(DivarUtils.face);
        btnChoose1.setTypeface(DivarUtils.faceLight);
        btnChoose2.setTypeface(DivarUtils.faceLight);
    }

    private void setupActivity()
    {
        user_id = Read.readDataFromStorage(Constant.USER_ID, "0", getApplicationContext());

        lnrContainer1.setVisibility(View.VISIBLE);
        lnrContainer2.setVisibility(View.GONE);
        lnrContainer4.setVisibility(View.GONE);
        lnrContainer5.setVisibility(View.GONE);

        if (getIntent().getBooleanExtra(Constant.KEY_DRAFT_CHECK, false))
        {
            txtToolbarTitle.setText("ویرایش آگهی");
            txtSend.setText(" ثبت تغییرات");
        } else
        {
            txtToolbarTitle.setText("درج آگهی جدید");
            txtSend.setText(" ثبت");
        }

        DivarUtils.statusBarColor(getWindow(), R.color.mainColor);

        Drawable img = DivarUtils.createDrawable(R.drawable.ic_check_white_48dp, 22);
        txtSend.setCompoundDrawables(null, null, img, null);
    }

    private void setupListeners()
    {
        foldingCell.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                foldingCell.toggle(false);
            }
        });

        txtSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                boolean isAllow = true;
                for (int i = 0; i < rootViews.size(); i++)
                {
                    if (rootViews.get(i).isFill())
                        rootViews.get(i).noColoredView();
                    else
                    {
                        isAllow = false;
                        rootViews.get(i).coloredView();
                    }
                }

                if (!isAllow)
                    SnackBar.Create(coordinatorLayoutCreate, "لطفا فیلد های رنگی را پر کنید", 3);
                else
                {
                    if (selectArea)
                    {
                        if (isEdit)
                            setEdit();
                        else
                            planDialog();
                    } else
                        SnackBar.Create(coordinatorLayoutCreate, "لطفا منطقه خود را انتخاب نمایید", 3);
                }
            }
        });

        btnChoose1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(CreatePostActivity.this, SelectCategoryActivity.class);
                startActivityForResult(intent, Constant.REQUEST_SELECT_CATEGORY);
            }
        });

        btnChoose2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreatePostActivity.this, SelectCategoryActivity.class);
                startActivityForResult(intent, Constant.REQUEST_SELECT_CATEGORY);
            }
        });

        btnChoose3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreatePostActivity.this, SelectCategoryActivity.class);
                startActivityForResult(intent, Constant.REQUEST_SELECT_CATEGORY);
            }
        });

        btnChooseLoc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreatePostActivity.this, MapsActivity.class);
                startActivityForResult(intent, Constant.REQUEST_SELECT_LOCATION);
            }
        });

        btnChooseArea.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CreatePostActivity.this, SelectAreaActivity.class);
                startActivityForResult(intent, Constant.REQUEST_SELECT_AREA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_SELECT_CATEGORY && resultCode == Constant.RESULT_OK && data != null)
        {
            id = data.getStringExtra(Constant.KEY_ID);
            title = data.getStringExtra(Constant.KEY_TITLE);

            btnChoose1.setText(title);
            btnChoose2.setText(title);

            lnrContainer1.setVisibility(View.GONE);
            frameLayoutSabteTajhizat.setVisibility(View.VISIBLE);
            lnrContainer2.setVisibility(View.VISIBLE);
            lnrContainer4.setVisibility(View.VISIBLE);
            lnrContainer5.setVisibility(View.VISIBLE);

            getSellId();
            setData(id);

        } else if (requestCode == Constant.REQUEST_SELECT_ADS_IMAGE)
        {
            if (resultCode == Constant.RESULT_OK)
            {
                for (int i = 0; i < rootViews.size(); i++)
                {
                    CreatePostRootView rootView = rootViews.get(i);
                    if (rootView instanceof SelectImage)
                    {
                        SelectImage selectImage = (SelectImage) rootView;
                        selectImage.onActivityResult(requestCode, resultCode, data);
                    }
                }
            }
        } else if (requestCode == Constant.REQUEST_SELECT_LOCATION && resultCode == Constant.RESULT_OK)
        {
            strLat = data.getStringExtra("Lat");
            strLon = data.getStringExtra("Lon");
            selectLoc = true;
            btnChooseLoc.setText("لوکیشن ست شد");
        } else if (requestCode == Constant.REQUEST_SELECT_AREA && resultCode == Constant.RESULT_OK && data != null)
        {
            areaTitle = data.getStringExtra(Constant.KEY_TITLE);
            selectArea = true;
            btnChooseArea.setText(areaTitle);
        }
    }

    //Getting id for set selling type
    private void getSellId()
    {
        if (id.startsWith("1"))
        {
            btnChoose3.setText("فروش");
            typeId = "1";
        } else if (id.startsWith("2"))
        {
            btnChoose3.setText("رهن کامل");
            typeId = "2";
        } else if (id.startsWith("3"))
        {
            btnChoose3.setText("رهن و اجاره");
            typeId = "3";
        } else if (id.startsWith("4"))
        {
            btnChoose3.setText("پیش خرید");
            typeId = "4";
        } else if (id.startsWith("5"))
        {
            btnChoose3.setText("معاوضه");
            typeId = "5";
        } else if (id.startsWith("6"))
        {
            btnChoose3.setText("مشارکت");
            typeId = "5";
        }
    }

    //Set create data in page
    private void setData(String mId)
    {
        int id = Integer.parseInt(mId.trim());

        if (rootViews.size() != 0)
            rootViews.clear();

        if (posViews.size() != 0)
            posViews.clear();

        if (id == 11)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
        } else if (id == 12)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 13)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WATER_WELL, Constant.WATER_WELL_ID));
        } else if (id == 14)
        {
            //geymat
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 21)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
        } else if (id == 22)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 23)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 31)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));
            rootViews.add(new InputAndSuffix("اجاره", Constant.RENT_PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
        } else if (id == 32)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));
            rootViews.add(new InputAndSuffix("اجاره", Constant.RENT_PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 33)
        {
            rootViews.add(new InputAndSuffix("ودیعه", Constant.PRICE, "0"));
            rootViews.add(new InputAndSuffix("اجاره", Constant.RENT_PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 41)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
        } else if (id == 42)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 43)
        {
            rootViews.add(new InputAndSuffix("قیمت کل", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 51 || id == 61)
        {
            rootViews.add(new InputAndSuffix("ارزش ملک", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
        } else if (id == 52 || id == 62)
        {
            rootViews.add(new InputAndSuffix("ارزش ملک", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        } else if (id == 53 || id == 63)
        {
            rootViews.add(new InputAndSuffix("ارزش ملک", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WATER_WELL, Constant.WATER_WELL_ID));
        } else if (id == 54 || id == 64)
        {
            rootViews.add(new InputAndSuffix("ارزش ملک", Constant.PRICE, "0"));

            rootViews.add(new InputAndSuffixForNumber("متراژ", Constant.BREADTH, "0", "متر"));
            rootViews.add(new InputAndSuffixForNumber("سال ساخت", Constant.BUILT_IN, "0", "سال"));

            rootViews.add(new JustInput("عنوان", Constant.NAME, "", "از عنوان هایی استفاده کنید که در یاد آوری آگهی به شما کمک کند"));
            rootViews.add(new JustInput("توضیحات", Constant.DESCRIPTION, "", "توضیحات تکمیلی برای ملک ثبت شده"));
            rootViews.add(new JustInput("آدرس", Constant.ADDRESS, ""));
            rootViews.add(new JustInput("نام مالک", Constant.SELLER_NAME, ""));
            rootViews.add(new JustInput("شماره تماس مالک", Constant.SELLER_TELL, ""));

            rootViews.add(new Optional("سند اداری", Constant.OFFICIAL_DOCUMENT, "سند 6 دانگ", "سند مشاعی", "قولنامه", "سایر"));

            rootViews.add(new SelectImage("عکس"));

            ArrayList<String> items = new ArrayList<>();
            items.add("0");
            items.add("1");
            items.add("2");
            items.add("3");
            items.add("4");
            items.add("5");

            posViews.add(new ContainItems(Constant.ROOM, Constant.ROOM_ID, items));
            posViews.add(new ContainItems(Constant.WALL_CUPBOARD, Constant.WALL_CUPBOARD_ID, items));
            posViews.add(new ContainItems(Constant.KITCHEN, Constant.KITCHEN_ID, items));
            posViews.add(new ContainItems(Constant.BATHROOM, Constant.BATHROOM_ID, items));
            posViews.add(new ContainItems(Constant.WC_STATION, Constant.WC_STATION_ID, items));
            posViews.add(new ContainItems(Constant.WC, Constant.WC_ID, items));
            posViews.add(new ContainItems(Constant.TELEPHONE, Constant.TELEPHONE_ID, items));
            posViews.add(new ContainItems(Constant.PARKING, Constant.PARKING_ID, items));

            posViews.add(new Checkbox(Constant.WATER_METER, Constant.WATER_METER_ID));
            posViews.add(new Checkbox(Constant.ELECTRICITY_METER, Constant.ELECTRICITY_METER_ID));
            posViews.add(new Checkbox(Constant.GAS_METER, Constant.GAS_METER_ID));
            posViews.add(new Checkbox(Constant.WAREHOUSE, Constant.WAREHOUSE_ID));
            posViews.add(new Checkbox(Constant.PAINT, Constant.PAINT_ID));
            posViews.add(new Checkbox(Constant.TERRACE, Constant.TERRACE_ID));
            posViews.add(new Checkbox(Constant.IPHONE_VIDEO, Constant.IPHONE_VIDEO_ID));
            posViews.add(new Checkbox(Constant.ELEVATOR, Constant.ELEVATOR_ID));
            posViews.add(new Checkbox(Constant.ELECTRIC_DOOR, Constant.ELECTRIC_DOOR_ID));
            posViews.add(new Checkbox(Constant.SAUNA, Constant.SAUNA_ID));
            posViews.add(new Checkbox(Constant.SWIMMING_POOL, Constant.SWIMMING_POOL_ID));
            posViews.add(new Checkbox(Constant.WALLPAPER, Constant.WALLPAPER_ID));
            posViews.add(new Checkbox(Constant.WATER_COOLER, Constant.WATER_COOLER_ID));
            posViews.add(new Checkbox(Constant.PACKAGE, Constant.PACKAGE_ID));
            posViews.add(new Checkbox(Constant.WATER_HEATER, Constant.WATER_HEATER_ID));
            posViews.add(new Checkbox(Constant.GAS_COOLER, Constant.GAS_COOLER_ID));
            posViews.add(new Checkbox(Constant.RADIANT, Constant.RADIANT_ID));
            posViews.add(new Checkbox(Constant.FLOOR_HEATING, Constant.FLOOR_HEATING_ID));
            posViews.add(new Checkbox(Constant.AIR_CONDITIONER, Constant.AIR_CONDITIONER_ID));
            posViews.add(new Checkbox(Constant.CHILLER, Constant.CHILLER_ID));
            posViews.add(new Checkbox(Constant.BURGLAR_ALARM, Constant.BURGLAR_ALARM_ID));
            posViews.add(new Checkbox(Constant.GUARD, Constant.GUARD_ID));
            posViews.add(new Checkbox(Constant.ABATTOIR, Constant.ABATTOIR_ID));
            posViews.add(new Checkbox(Constant.FIRE_ALARM, Constant.FIRE_ALARM_ID));
            posViews.add(new Checkbox(Constant.RESTAURANT, Constant.RESTAURANT_ID));
            posViews.add(new Checkbox(Constant.PARTY_ROOM, Constant.PARTY_ROOM_ID));
            posViews.add(new Checkbox(Constant.SPORT_ROOM, Constant.SPORT_ROOM_ID));
            posViews.add(new Checkbox(Constant.LOBBY, Constant.LOBBY_ID));
            posViews.add(new Checkbox(Constant.COFFEE_SHOP, Constant.COFFEE_SHOP_ID));
            posViews.add(new Checkbox(Constant.FURNITURE, Constant.FURNITURE_ID));
            posViews.add(new Checkbox(Constant.KIDS_PARK, Constant.KIDS_PARK_ID));
            posViews.add(new Checkbox(Constant.FOUNTAIN, Constant.FOUNTAIN_ID));
            posViews.add(new Checkbox(Constant.CCTV, Constant.CCTV_ID));
            posViews.add(new Checkbox(Constant.AMUSEMENT, Constant.AMUSEMENT_ID));
            posViews.add(new Checkbox(Constant.YARD, Constant.YARD_ID));
            posViews.add(new Checkbox(Constant.SHELF, Constant.SHELF_ID));
            posViews.add(new Checkbox(Constant.SHOWCASE, Constant.SHOWCASE_ID));
        }

        setupLayouts();
    }

    private void setupLayouts()
    {
        lnrInsertPoint.removeAllViews();
        for (int i = 0; i < rootViews.size(); i++)
        {
            View view = rootViews.get(i).getView();
            lnrInsertPoint.addView(view);
        }
        for (int i = 0; i < posViews.size(); i++)
        {
            View view = posViews.get(i).getView();
            lnrInsertPoss.addView(view);
        }
    }

    //Create send data for edit ads
    private void setEdit()
    {
        HashMap map = new HashMap();

        map.put(Constant.CONS_ID, user_id);

        JSONObject object = new JSONObject();
        try
        {
            object.put(Constant.TYPE, typeId);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        map.put(Constant.SELLING_TYPE, object);

        map.put(Constant.TYPE, StringToId.Category(title));

        if (selectLoc)
        {
            map.put(Constant.GEO_LAT, strLat);
            map.put(Constant.GEO_LON, strLon);
        } else
        {
            map.put(Constant.GEO_LAT, "NO_LOCATION");
            map.put(Constant.GEO_LON, "NO_LOCATION");
        }

        map.put(Constant.IS_EXPIRE, "0");

        map.put(Constant.AREA, StringToId.Area(areaTitle));

        for (int i = 0; i < rootViews.size(); i++)
            map.putAll(rootViews.get(i).getData());

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < posViews.size(); i++)
            jsonArray.put(posViews.get(i).getData());

        HashMap mapPoss = new HashMap();
        JSONArray temp = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try
            {
                if (jsonArray.get(i) != null)
                    temp.put(jsonArray.get(i));
            } catch (JSONException e)
            {
                Log.i(G.TAG, e.getMessage());
            }
        }
        mapPoss.put(Constant.POSSIBILITIES, temp);

        map.putAll(mapPoss);

        map.put("est_id", post_id);

        if (isPublic)
            map.put("status", "1");
        else
            map.put("status", "0");

        JSONObject jsonObject = new JSONObject(map);
        setAdRequest(jsonObject);
    }

    //Create send data for new ads
    private void planDialog()
    {
        final Switch switchPublic;
        final RadioButton rdbFree, rdBtnCostVip;
        final TextView txtPay, txtCancel, txtError;

        View promptsView = LayoutInflater.from(this).inflate(R.layout.dialog_choose_plan, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        switchPublic = promptsView.findViewById(R.id.switchPublic);
        rdbFree = promptsView.findViewById(R.id.rdbFree);
        rdBtnCostVip = promptsView.findViewById(R.id.rdbCostVip);
        txtPay = promptsView.findViewById(R.id.txtPay);
        txtCancel = promptsView.findViewById(R.id.txtCancel);
        txtError = promptsView.findViewById(R.id.txtError);

        Typeface typeface = DivarUtils.faceLight;
        switchPublic.setTypeface(typeface);
        rdbFree.setTypeface(typeface);
        rdBtnCostVip.setTypeface(typeface);
        txtCancel.setTypeface(DivarUtils.face);
        txtPay.setTypeface(DivarUtils.face);

        String check = Read.readDataFromStorage("license", "0", getApplicationContext());

        if (check.equals("0"))
        {
            txtError.setVisibility(View.VISIBLE);
            switchPublic.setEnabled(false);
            rdbFree.setEnabled(false);
            rdBtnCostVip.setEnabled(false);
            switchPublic.setText("نمایش آگهی تنها برای خودم");
        }
        else
        {
            txtError.setVisibility(View.VISIBLE);
            switchPublic.setEnabled(true);
            rdbFree.setEnabled(true);
            rdBtnCostVip.setEnabled(true);
            switchPublic.setText("نمایش آگهی به صورت عمومی");
        }

        switchPublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (switchPublic.isChecked())
                {
                    rdbFree.setEnabled(true);
                    rdBtnCostVip.setEnabled(true);
                    switchPublic.setText("نمایش آگهی به صورت عمومی");
                }
                else
                {
                    rdbFree.setEnabled(false);
                    rdBtnCostVip.setEnabled(false);
                    switchPublic.setText("نمایش آگهی تنها برای خودم");
                }
            }
        });

        final AlertDialog alertDialog = alertDialogBuilder.create();
        txtPay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                HashMap map = new HashMap();

                map.put(Constant.CONS_ID, user_id);

                JSONObject object = new JSONObject();
                try
                {
                    object.put(Constant.TYPE, typeId);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                map.put(Constant.SELLING_TYPE, object);

                map.put(Constant.TYPE, StringToId.Category(title));

                if (switchPublic.isChecked())
                    map.put(Constant.IS_PUBLIC, "1");
                else
                    map.put(Constant.IS_PUBLIC, "0");

                if (selectLoc)
                {
                    map.put(Constant.GEO_LAT, strLat);
                    map.put(Constant.GEO_LON, strLon);
                } else
                {
                    map.put(Constant.GEO_LAT, "NO_LOCATION");
                    map.put(Constant.GEO_LON, "NO_LOCATION");
                }

                JSONObject obj = new JSONObject();
                try
                {
                    if (rdBtnCostVip.isChecked())
                        obj.put(Constant.OFFER, Constant.VIP_ID);

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                map.put(Constant.OFFER, obj);

                map.put(Constant.IS_EXPIRE, "0");

                map.put(Constant.AREA, StringToId.Area(areaTitle));

                for (int i = 0; i < rootViews.size(); i++)
                    map.putAll(rootViews.get(i).getData());

                JSONArray jsonArray = new JSONArray();
                for (int i = 0; i < posViews.size(); i++)
                    jsonArray.put(posViews.get(i).getData());

                HashMap mapPoss = new HashMap();
                JSONArray temp = new JSONArray();
                for (int i = 0; i < jsonArray.length(); i++)
                {
                    try
                    {
                        if (jsonArray.get(i) != null)
                            temp.put(jsonArray.get(i));
                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                mapPoss.put(Constant.POSSIBILITIES, temp);

                map.putAll(mapPoss);

                JSONObject jsonObject = new JSONObject(map);
                setAdRequest(jsonObject);
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }



    private void setAdRequest(JSONObject jsonData)
    {
        String url;
        if (isEdit)
            url = Constant.BASE_URL + "updateEstate";
        else
            url = Constant.BASE_URL + "newEstate";

        final CustomProgressDialog progressDialog = new CustomProgressDialog(CreatePostActivity.this);
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
                String str = "";
                try
                {
                    if (response.getString("result").equals("ok"))
                    {
                        progressDialog.dismiss();
                        startActivity(new Intent(CreatePostActivity.this, MainActivity.class));
                        CreatePostActivity.this.finish();
                    } else if (response.getString("result").equals("noCredit"))
                    {
                        progressDialog.dismiss();
                        SnackBar.Create(coordinatorLayoutCreate, "زمان ثبت آگهی شما به پایان رسیده است", 3);
                    } else
                    {
                        progressDialog.dismiss();
                        SnackBar.Create(coordinatorLayoutCreate, "تعداد دربن کافی نمی باشد", 3);
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

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonData, listener, errorListener);


        final int socketTimeout = 100000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);

        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(request);



    }

    //Restore edit data
    private void restoreData()
    {
        String data = getIntent().getStringExtra(Constant.KEY_DRAFT);
        String poss = getIntent().getStringExtra(Constant.KEY_DRAFT_POSS);

        if (data != null)
        {
            try
            {
                JSONObject jsonObject = new JSONObject(data);
                JSONObject jsonObjectPoss = new JSONObject(poss);

                isEdit = true;

                post_id = jsonObject.getString(Constant.KEY_POST_ID);

                lnrContainer1.setVisibility(View.GONE);
                lnrContainer2.setVisibility(View.VISIBLE);
                lnrContainer4.setVisibility(View.VISIBLE);
                lnrContainer5.setVisibility(View.VISIBLE);

                btnChoose3.setText(jsonObject.getString(Constant.KEY_TYPE));
                btnChoose2.setText(jsonObject.getString(Constant.KEY_CAT));

                title = jsonObject.getString(Constant.KEY_CAT);

                image1 = jsonObject.getString(Constant.KEY_IMAGE1);
                image2 = jsonObject.getString(Constant.KEY_IMAGE2);
                image3 = jsonObject.getString(Constant.KEY_IMAGE3);
                image4 = jsonObject.getString(Constant.KEY_IMAGE4);
                image5 = jsonObject.getString(Constant.KEY_IMAGE5);
                new imageDownload().execute();

                String areaId = jsonObject.getString(Constant.AREA);
                areaTitle = IdToString.Area(areaId);
                btnChooseArea.setText(areaTitle);

                typeId = StringToId.Selling(btnChoose3.getText().toString());

                selectArea = true;

                if (jsonObject.getString(Constant.IS_PUBLIC).equals("1"))
                    isPublic = true;

                String Lat = jsonObject.getString(Constant.GEO_LAT);
                String Lon = jsonObject.getString(Constant.GEO_LON);
                if (!Lat.equals("NO_LOCATION") || !Lon.equals("NO_LOCATION"))
                {
                    selectLoc = true;
                    btnChooseLoc.setText("لوکیشن ست شد");
                    strLat = Lat;
                    strLon = Lon;
                }

                setData(jsonObject.getString(Constant.KEY_SET_ADS));

                for (int i = 0; i < rootViews.size(); i++)
                {
                    String Key = rootViews.get(i).getKey();

                    if (!jsonObject.getString(Key).equals("NO_IMAGE"))
                        rootViews.get(i).restoreData(jsonObject.getString(Key));
                    else
                        rootViews.get(i).restoreData(arrayBitmap);
                }

                for (int i = 0; i < posViews.size(); i++)
                {
                    String Key = posViews.get(i).getKey();
                    if (jsonObjectPoss.has(Key))
                        posViews.get(i).restoreData(jsonObjectPoss.getString(Key));
                }

            } catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    //Download image for edit ads
    class imageDownload extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids)
        {
            if (!image1.equals(Constant.IMG_CHECK) && !image1.equals(Constant.IMAGE_URL) && !image1.equals(Constant.KEY_NO_IMAGE) && image1 != null)
            {
                try
                {
                    arrayBitmap.add(Picture.getBitmapFromURL(image1));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (!image2.equals(Constant.IMAGE_URL + "null") && !image2.equals(Constant.KEY_NO_IMAGE) && image2 != null)
            {
                try
                {
                    arrayBitmap.add(Picture.getBitmapFromURL(image2));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (!image3.equals(Constant.IMAGE_URL + "null") && !image3.equals(Constant.KEY_NO_IMAGE) && image3 != null)
            {
                try
                {
                    arrayBitmap.add(Picture.getBitmapFromURL(image3));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (!image4.equals(Constant.IMAGE_URL + "null") && !image4.equals(Constant.KEY_NO_IMAGE) && image4 != null)
            {
                try
                {
                    arrayBitmap.add(Picture.getBitmapFromURL(image4));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            if (!image5.equals(Constant.IMAGE_URL + "null") && !image1.equals(Constant.KEY_NO_IMAGE) && image5 != null)
            {
                try
                {
                    arrayBitmap.add(Picture.getBitmapFromURL(image5));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute()
        {
            showDialog(Progress_Dialog_Progress);
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            dismissDialog(Progress_Dialog_Progress);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch (id)
        {
            case Progress_Dialog_Progress:

                progressdialog = new ProgressDialog(CreatePostActivity.this);
                progressdialog.setMessage("در حال دریافت اطلاعات...");
                progressdialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressdialog.setCancelable(false);
                progressdialog.show();
                return progressdialog;

            default:
                return null;
        }
    }
}
