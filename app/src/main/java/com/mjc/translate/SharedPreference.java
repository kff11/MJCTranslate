package com.mjc.translate;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String xmlFileName = "SC_Tarns_SharedPreferences";

    public SharedPreference(Context context) {
        this.context = context;
    }

    public String getSharedString(String key) {
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        return string;
    }

    public void setSharedString(String key, String string) {
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, string);
        editor.commit();
    }

    public int getSharedInteger(String key){
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        int integer = sharedPreferences.getInt(key, -1);
        return integer;
    }

    public void setSharedInteger(String key, int integer) {
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, integer);
        editor.commit();

    }

    public boolean getSharedboolean(String key) {
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        boolean aBoolean = sharedPreferences.getBoolean(key, false);
        return aBoolean;
    }

    public void setSharedboolean(String key, boolean aboolean){
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, aboolean);
        editor.commit();
    }

    public void delShared(String key) {
        sharedPreferences = context.getSharedPreferences(xmlFileName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
