package com.smr.estate.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Read {

    public static SharedPreferences setting;

    public static String readDataFromStorage(String key, String defaultValue, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        return setting.getString(key, defaultValue);
    }

    public static int readDataFromStorage(String key, int defaultValue, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        return setting.getInt(key, defaultValue);
    }

    public static long readDataFromStorage(String key, long defaultValue, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        return setting.getLong(key, defaultValue);
    }

    public static boolean readDataFromStorage(String key, boolean defaultValue, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        return setting.getBoolean(key, defaultValue);
    }
}
