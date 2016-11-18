
package com.yzk.practice_brain.log;

import android.util.Log;

import com.yzk.practice_brain.BuildConfig;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 */
public class LogUtil {
    private static final boolean DEBUG = true;
    public static final String TAG = "practice_brain";

    public static void e(String msg) {
        if (DEBUG)
            Log.e(TAG, msg);
    }

    public static void w(String msg) {
        if (DEBUG)
            Log.w(TAG, msg);
    }

    public static void d(String msg) {
        if (DEBUG)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (DEBUG)
            Log.i(TAG, msg);
    }

    public static void v(String msg) {
        if (DEBUG)
            Log.v(TAG, msg);
    }

    public static boolean isDebugBuild() {
        boolean logDebug = false;
        try {
            final Class<?> activityThread = Class.forName("android.app.ActivityThread");
            final Method currentPackage = activityThread.getMethod("currentPackageName");
            final String packageName = (String) currentPackage.invoke(null, (Object[]) null);
            final Class<?> buildConfig = Class.forName(packageName + ".BuildConfig");
            final Field DEBUG = buildConfig.getField("LOG_DEBUG");
            DEBUG.setAccessible(true);
            logDebug = DEBUG.getBoolean(null);
        } catch (final Throwable t) {
            final String message = t.getMessage();
            if (message != null && message.contains("BuildConfig")) {
                // Proguard obfuscated build. Most likely a production build.
                logDebug = false;
            } else {
                logDebug = BuildConfig.DEBUG;
            }
        }
        return logDebug;
    }
}

