package com.yzk.practice_brain.preference;

import android.content.SharedPreferences;

import com.yzk.practice_brain.application.GlobalApplication;

/**
 * Created by android on 11/23/16.
 */

public class PreferenceHelper {


    public static SharedPreferences getSharedPreference(){
       return GlobalApplication.instance.getSharedPreferences("app_cache", GlobalApplication.instance.MODE_PRIVATE);
    }


    public static void writeString(String key,String value){
        getSharedPreference().edit().putString(key,value).apply();
    }

    public static String getString(String key){
        return getSharedPreference().getString(key,"");
    }

    public static void writeInt(String key,int value){
        getSharedPreference().edit().putInt(key,value).apply();
    }

    public static int getInt(String key){
        return getSharedPreference().getInt(key,-1);
    }

    public static void writeBool(String key,boolean value){
        getSharedPreference().edit().putBoolean(key,value).apply();
    }

    public static boolean getBool(String key){
        return getSharedPreference().getBoolean(key,false);
    }


}
