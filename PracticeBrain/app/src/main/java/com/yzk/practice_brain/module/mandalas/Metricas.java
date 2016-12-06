//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.content.Context;
//import android.util.DisplayMetrics;
//import android.view.WindowManager;
//
//public class Metricas {
//    public Metricas() {
//        super();
//    }
//
//    public int MedirAnchoPaleta(Context mContext) {
//        Object v3 = mContext.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics v2 = new DisplayMetrics();
//        ((WindowManager)v3).getDefaultDisplay().getMetrics(v2);
//        return v2.widthPixels / 17;
//    }
//
//    public int MedirAnchoPantalla(Context mContext) {
//        Object v3 = mContext.getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics v2 = new DisplayMetrics();
//        ((WindowManager)v3).getDefaultDisplay().getMetrics(v2);
//        return v2.widthPixels / 3;
//    }
//}
//
