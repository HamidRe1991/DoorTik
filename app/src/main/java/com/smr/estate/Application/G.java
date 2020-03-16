package com.smr.estate.Application;

import android.app.Application;

import com.smr.estate.Constant.Constant;
import com.smr.estate.Model.Category;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import wiadevelopers.com.library.DivarUtils;

public class G extends Application
{
    public static final ArrayList<Category> CATEGORIES = new ArrayList<>();
    public static final ArrayList<Category> AREA = new ArrayList<>();

    public static final String TAG = "MyErrorTag";

    @Override
    public void onCreate()
    {
        super.onCreate();

        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(Realm.DEFAULT_REALM_NAME)
                .build();

        Realm.getInstance(realmConfiguration);
        DivarUtils.init(getApplicationContext());

        setCategories();
        setArea();
    }

    //Set category data
    private void setCategories()
    {
        CATEGORIES.add(new Category("1", "فروش", false));

        CATEGORIES.add(new Category("11", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("11", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("11", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("11", Constant.HOUSE, true));
        CATEGORIES.add(new Category("11", Constant.VILLAGE, true));
        CATEGORIES.add(new Category("11", Constant.SUITES, true));

        CATEGORIES.add(new Category("12", Constant.OFFICE, true));
        CATEGORIES.add(new Category("12", Constant.SHOP, true));

        CATEGORIES.add(new Category("13", Constant.GARDEN, true));
        CATEGORIES.add(new Category("13", Constant.LAND_PLOTS, true));
        CATEGORIES.add(new Category("13", Constant.FARM, true));

        CATEGORIES.add(new Category("14", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("14", Constant.HALL, true));
        CATEGORIES.add(new Category("14", Constant.HOTEL, true));
        CATEGORIES.add(new Category("14", Constant.STORE, true));
        CATEGORIES.add(new Category("14", Constant.FACTORY, true));

        //******************************************************************************************

        CATEGORIES.add(new Category("2", "رهن کامل", false));

        CATEGORIES.add(new Category("21", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("21", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("21", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("21", Constant.HOUSE, true));
        CATEGORIES.add(new Category("21", Constant.VILLAGE, true));
        CATEGORIES.add(new Category("21", Constant.SUITES, true));

        CATEGORIES.add(new Category("22", Constant.OFFICE, true));
        CATEGORIES.add(new Category("22", Constant.SHOP, true));

        CATEGORIES.add(new Category("23", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("23", Constant.HALL, true));
        CATEGORIES.add(new Category("23", Constant.STORE, true));

        //******************************************************************************************

        CATEGORIES.add(new Category("3", "رهن و اجاره", false));

        CATEGORIES.add(new Category("31", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("31", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("31", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("31", Constant.HOUSE, true));
        CATEGORIES.add(new Category("31", Constant.VILLAGE, true));
        CATEGORIES.add(new Category("31", Constant.SUITES, true));

        CATEGORIES.add(new Category("32", Constant.OFFICE, true));
        CATEGORIES.add(new Category("32", Constant.SHOP, true));

        CATEGORIES.add(new Category("33", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("33", Constant.HALL, true));
        CATEGORIES.add(new Category("33", Constant.STORE, true));

        //******************************************************************************************

        CATEGORIES.add(new Category("4", "پیش خرید", false));

        CATEGORIES.add(new Category("41", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("41", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("41", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("41", Constant.HOUSE, true));
        CATEGORIES.add(new Category("41", Constant.SUITES, true));

        CATEGORIES.add(new Category("42", Constant.OFFICE, true));
        CATEGORIES.add(new Category("42", Constant.SHOP, true));

        CATEGORIES.add(new Category("43", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("43", Constant.HALL, true));
        CATEGORIES.add(new Category("43", Constant.HOTEL, true));
        CATEGORIES.add(new Category("43", Constant.STORE, true));
        CATEGORIES.add(new Category("43", Constant.FACTORY, true));

        //******************************************************************************************

        CATEGORIES.add(new Category("5", "معاوضه", false));

        CATEGORIES.add(new Category("51", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("51", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("51", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("51", Constant.HOUSE, true));
        CATEGORIES.add(new Category("51", Constant.VILLAGE, true));
        CATEGORIES.add(new Category("51", Constant.SUITES, true));

        CATEGORIES.add(new Category("52", Constant.OFFICE, true));
        CATEGORIES.add(new Category("52", Constant.SHOP, true));

        CATEGORIES.add(new Category("53", Constant.GARDEN, true));
        CATEGORIES.add(new Category("53", Constant.LAND_PLOTS, true));
        CATEGORIES.add(new Category("53", Constant.FARM, true));

        CATEGORIES.add(new Category("54", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("54", Constant.HALL, true));
        CATEGORIES.add(new Category("54", Constant.HOTEL, true));
        CATEGORIES.add(new Category("54", Constant.STORE, true));
        CATEGORIES.add(new Category("54", Constant.FACTORY, true));

        //******************************************************************************************

        CATEGORIES.add(new Category("6", "مشارکت", false));

        CATEGORIES.add(new Category("61", Constant.PENT_HOUSE, true));
        CATEGORIES.add(new Category("61", Constant.APARTMENT, true));
        CATEGORIES.add(new Category("61", Constant.RESIDENTIAL_COMPLEX, true));
        CATEGORIES.add(new Category("61", Constant.HOUSE, true));
        CATEGORIES.add(new Category("61", Constant.VILLAGE, true));
        CATEGORIES.add(new Category("61", Constant.SUITES, true));

        CATEGORIES.add(new Category("62", Constant.OFFICE, true));
        CATEGORIES.add(new Category("62", Constant.SHOP, true));

        CATEGORIES.add(new Category("63", Constant.GARDEN, true));
        CATEGORIES.add(new Category("63", Constant.LAND_PLOTS, true));
        CATEGORIES.add(new Category("63", Constant.FARM, true));

        CATEGORIES.add(new Category("64", Constant.WORKSHOP, true));
        CATEGORIES.add(new Category("64", Constant.HALL, true));
        CATEGORIES.add(new Category("64", Constant.HOTEL, true));
        CATEGORIES.add(new Category("64", Constant.STORE, true));
        CATEGORIES.add(new Category("64", Constant.FACTORY, true));
    }

    //Set area data
    private void setArea()
    {
        AREA.add(new Category("1", "فردیس", false));

        AREA.add(new Category("11", Constant.SARHADDI, true));
        AREA.add(new Category("11", Constant.EMAM_KHOMEINI, true));
        AREA.add(new Category("11", Constant.NASTARAN, true));
        AREA.add(new Category("11", Constant.CHEHELOHAST_DASTGAH, true));
        AREA.add(new Category("11", Constant.BAHARAN, true));
        AREA.add(new Category("11", Constant.MESHKIN_DASHT, true));
        AREA.add(new Category("11", Constant.MOHAMMAD_SHAHR, true));
        AREA.add(new Category("11", Constant.KHOSHNAM, true));
        AREA.add(new Category("11", Constant.DEHKADE, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_NAZ, true));
        AREA.add(new Category("11", Constant.MANZARIE, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_SADODAH, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_TALEGHANI, true));
        AREA.add(new Category("11", Constant.BOLVARE_EMAM_REZA, true));
        AREA.add(new Category("11", Constant.KHIABAN_EMAM_HOSSEIN, true));
        AREA.add(new Category("11", Constant.BOLVARE_BAYAT, true));
        AREA.add(new Category("11", Constant.POLE_ARTESH, true));
        AREA.add(new Category("11", Constant.FALAKE_AVAL, true));
        AREA.add(new Category("11", Constant.FALAKE_DOVOM, true));
        AREA.add(new Category("11", Constant.FALAKE_SEVOM, true));
        AREA.add(new Category("11", Constant.FALAKE_CHAHAROM, true));
        AREA.add(new Category("11", Constant.FALAKE_PANJOM, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_SEPAH, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_VAHDAT, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_SIMIN_DASHT, true));
        AREA.add(new Category("11", Constant.SHAHRAK_HAFEZIE, true));
        AREA.add(new Category("11", Constant.ROKN_ABAD, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_ERAM, true));
        AREA.add(new Category("11", Constant.KHIABAN_AHARI, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_GOLESTAN, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_PARNIAN, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_TABAN, true));
        AREA.add(new Category("11", Constant.BAHARAN, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_MAHAN, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_ALZAHRA, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_PASDAR, true));
        AREA.add(new Category("11", Constant.NAJAF_ABAD, true));
        AREA.add(new Category("11", Constant.SHAHRAKE_GOLHA, true));
        AREA.add(new Category("11", Constant.KANALE_FARDIS, true));
    }
}
