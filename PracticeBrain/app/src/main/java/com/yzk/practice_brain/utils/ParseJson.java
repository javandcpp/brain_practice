package com.yzk.practice_brain.utils;

import android.util.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by android on 11/29/16.
 */

public class ParseJson {


    static {
        gson = new GsonBuilder().create();
    }

    private static Gson gson;

    private ParseJson() {

    }


    public static <T> T parseJson(String json, Class<T> clazz) {
        T t = null;
        try {
            t = gson.fromJson(json, clazz);
        } catch (Exception e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return t;
    }

    public static <T> List<T> parseJsonToList(String json, TypeToken<List<T>> typeToken) {
        return gson.fromJson(json, typeToken.getType());

    }
}
