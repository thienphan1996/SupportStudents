package com.project.thienphan.timesheet.Common;

import android.content.Context;
import android.content.SharedPreferences;

public class TimesheetPreferences {
    private String prefsName = "TIMESHEET_PREFS";
    private SharedPreferences myPreferences;
    private Context context;

    public TimesheetPreferences(Context context) {
        this.context = context;
        myPreferences = context.getSharedPreferences(prefsName,Context.MODE_PRIVATE);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) myPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(myPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(myPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(myPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(myPreferences.getLong(key, 0));
        }
        return null;
    }

    public <T> void put(String key, T data) {
        SharedPreferences.Editor editor = myPreferences.edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        editor.apply();
    }

    public void clear() {
        myPreferences.edit().clear().apply();
    }
}
