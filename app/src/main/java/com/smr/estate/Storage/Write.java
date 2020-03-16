package com.smr.estate.Storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Write {

    public static SharedPreferences.Editor editor;
    public static SharedPreferences setting;

    public static void writeDataInStorage(String key, String value, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        editor = setting.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void writeDataInStorage(String key, int value, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        editor = setting.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void writeDataInStorage(String key, long value, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        editor = setting.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static void writeDataInStorage(String key, boolean value, Context context) {
        setting = PreferenceManager.getDefaultSharedPreferences(context);
        editor = setting.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
