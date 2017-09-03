package com.fcdream.dress.kenny.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shmdu on 2017/9/2.
 */

public class FCDreamSharedPreferences {

    public static final String DEFAULT_SHARE = "SICENT_SHARE";

    public static Object getValue(Context context, String key, Object defaultValue) {
        return getValue(context, key, DEFAULT_SHARE, defaultValue);
    }

    public static Object getValue(Context context, String key, String shareName, Object defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        if (defaultValue instanceof Boolean) {
            return settings.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof String) {
            return settings.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return settings.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Float) {
            return settings.getFloat(key, (Float) defaultValue);
        } else if (defaultValue instanceof Long) {
            return settings.getLong(key, (Long) defaultValue);
        } else {
            throw new RuntimeException("defaultValue is not correct");
        }
    }

    public static void setValue(Context context, String key, Object value) {
        setValue(context, key, DEFAULT_SHARE, value);
    }

    public static void setValue(Context context, String key, String shareName, Object value) {
        SharedPreferences settings = context.getSharedPreferences(shareName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else {
            throw new RuntimeException("value is not correct");
        }
        editor.commit();
    }
}
