package com.codewithmuddasir.statussaver.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class LocalStorage {

    public static String PREF_KEY_WP_PERMISSION_GRANTED = "PREF_KEY_PERMISSION_GRANTED";
    public static String PREF_KEY_WP_TREE_URI = "PREF_KEY_TREE_URI";
    public static String PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED = "PREF_KEY_WP_BUSINESS_PERMISSION_GRANTED";
    public static String PREF_KEY_WP_BUSINESS_TREE_URI = "PREF_KEY_WP_BUSINESS_TREE_URI";

    public static String PREF_KEY_IS_PERMISSIONS_GRANTED = "PREF_KEY_IS_PERMISSIONS_GRANTED";
    public static String PREF_KEY_IS_APP_FOLDER_URI = "PREF_KEY_IS_APP_FOLDER_URI";

    public static String APP_FIRST_TIME_OPEN = "APP_FIRST_TIME_OPEN";

    public static void putStringValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void putBooleanValue(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static String getStringValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    public static boolean getBooleanValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
}
