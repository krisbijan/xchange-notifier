package com.krisbijan.xchangenotifier.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Time;
import java.util.ArrayList;

public class Settings {

    static SharedPreferences sharedPref;


    private static Settings instance;

    private  Settings(Context context){
        sharedPref = context.getSharedPreferences("com.krisbijan.xchangenotifier", Context.MODE_PRIVATE);

        if (sharedPref.getString("mainCurr","")==null || sharedPref.getString("mainCurr","").equalsIgnoreCase("")){
            sharedPref.edit().putString("mainCurr","EUR").apply();
        }

        if (sharedPref.getInt("hour",0)==0 )
            sharedPref.edit().putInt("hour",0).apply();

        if (sharedPref.getInt("minute",0)==0 )
            sharedPref.edit().putInt("minute",0).apply();

    }

    public static synchronized Settings getInstance(Context context) {

        if (instance == null) {
            instance = new Settings(context);
        }
        return instance;
    }

    public String getMainCurrency() {
        return sharedPref.getString("mainCurr","EUR");
    }

    public void setMainCurrency(String mainCurrency) {
        sharedPref.edit().putString("mainCurr",mainCurrency).apply();
    }

    public int getHour() {
        return sharedPref.getInt("hour",0);
    }

    public void setHour(int hour) {
        sharedPref.edit().putInt("hour",hour).apply();
    }

    public int getMinute() {
        return sharedPref.getInt("minute",0);
    }

    public void setMinute(int minute) {
        sharedPref.edit().putInt("minute",minute).apply();
    }
}
