package com.yzk.brain.preference;

import android.content.SharedPreferences;

import com.yzk.brain.application.GlobalApplication;

/**
 * Created by android on 11/23/16.
 */

public class PreferenceHelper {


    public static SharedPreferences getSharedPreference(){
       return GlobalApplication.instance.getSharedPreferences("app_cache", GlobalApplication.instance.MODE_PRIVATE);
    }


    public static void writeString(String key,String value){
        getSharedPreference().edit().putString(key,value).commit();
    }

    public static String getString(String key){
        return getSharedPreference().getString(key,"");
    }

    public static void writeInt(String key,int value){
        getSharedPreference().edit().putInt(key,value).commit();
    }

    public static int getInt(String key){
        return getSharedPreference().getInt(key,0);
    }

    public static void writeBool(String key,boolean value){
        getSharedPreference().edit().putBoolean(key,value).commit();
    }

    public static boolean getBool(String key){
        return getSharedPreference().getBoolean(key,false);
    }

    public static int getVoiceInt(String key){
        return getSharedPreference().getInt(key,1);
    }

}
