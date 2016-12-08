//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.content.Context;
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.widget.ImageView;
//
//import java.io.File;
//import java.lang.ref.WeakReference;
//
//public class BitmapWorkerTask extends AsyncTask<String,Integer,Bitmap> {
//    Context c;
//    private String data;
//    Metricas dim;
//    private final WeakReference imageViewReference;
//
//    public BitmapWorkerTask(ImageView imageView) {
//        super();
//        this.data = "";
//        this.dim = new Metricas();
//        this.imageViewReference = new WeakReference(imageView);
//        this.c = imageView.getContext().getApplicationContext();
//    }
//
//    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        int v2 = options.outHeight;
//        int v4 = options.outWidth;
//        int v3 = 1;
//        if(v2 > reqHeight || v4 > reqWidth) {
//            int v0 = v2 / 2;
//            int v1 = v4 / 2;
//            while(v0 / v3 > reqHeight) {
//                if(v1 / v3 <= reqWidth) {
//                    return v3;
//                }
//
//                v3 *= 2;
//            }
//        }
//
//        return v3;
//    }
//
//    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
//        BitmapFactory.Options v0 = new BitmapFactory.Options();
//        v0.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, v0);
//        v0.inSampleSize = this.calculateInSampleSize(v0, reqWidth, reqHeight);
//        v0.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, v0);
//    }
//
//    public Bitmap decodeSampledBitmapFromResource(Resources res, int reqWidth, int reqHeight, String
//            pathName) {
//        BitmapFactory.Options v0 = new BitmapFactory.Options();
//        v0.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(pathName, v0);
//        v0.inSampleSize = this.calculateInSampleSize(v0, reqWidth, reqHeight);
//        v0.inJustDecodeBounds = false;
//        return BitmapFactory.decodeFile(pathName, v0);
//    }
//    @Override
//    public Bitmap doInBackground(String... params) {
//        Bitmap v6;
//        this.data = params[0];
//        String v5 = params[1];
//        int v0 = this.dim.MedirAnchoPantalla(this.c.getApplicationContext());
//        if(v5 == "gal") {
//            v6 = this.decodeSampledBitmapFromResource(this.c.getResources(), v0, v0, new File(String
//                    .valueOf(Environment.getExternalStorageDirectory().getPath()) + "/Mandalas2/MyGalery/"
//                     + this.data).getAbsolutePath());
//        }
//        else if(v5 == "temp") {
//            v6 = this.decodeSampledBitmapFromResource(this.c.getResources(), v0, v0, new File(this.data)
//                    .getAbsolutePath());
//        }
//        else {
//            v6 = this.decodeSampledBitmapFromResource(this.c.getResources(), this.c.getResources().getIdentifier(
//                    String.valueOf(this.data), "drawable", this.c.getPackageName()), v0, v0);
//        }
//
//        return v6;
//    }
////    @Override
////    public Object doInBackground(Object[] arg2) {
////        return this.doInBackground(((String[])arg2));
////    }
//
//    @Override
//    public void onPostExecute(Bitmap bitmap) {
//        if(this.imageViewReference != null && bitmap != null) {
//            Object v0 = this.imageViewReference.get();
//            if(v0 != null) {
//                ((ImageView)v0).setImageBitmap(bitmap);
//            }
//        }
//
//        this.c = null;
//    }
//
////    protected void onPostExecute(Object arg1) {
////        this.onPostExecute(((Bitmap)arg1));
////    }
//}
//
