//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.BaseAdapter;
//import android.widget.CheckBox;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.yzk.practice_brain.R;
//
//import java.io.File;
//
//public class GrillaGallery extends BaseAdapter {
//    class viewHolder {
//        CheckBox cb;
//        RelativeLayout fl;
//        ImageView imageView;
//        TextView textView;
//
//        viewHolder() {
//            super();
//        }
//    }
//
//    AbsListView.LayoutParams GridViewParams;
//    Metricas dim;
//    FilesHandler handler;
//    LayoutInflater li;
//    private String[] mThumbString;
//
//    public GrillaGallery(View v) {
//        super();
//        this.dim = new Metricas();
//        this.handler = new FilesHandler();
//        this.mThumbString = new File(String.valueOf(this.handler.RAIZ) + "/Mandalas2/MyGalery/").list();
//        Context v1 = v.getContext().getApplicationContext();
//        this.li = LayoutInflater.from(v1);
//        int v0 = this.dim.MedirAnchoPantalla(v1);
//        this.GridViewParams = new AbsListView.LayoutParams(v0, v0);
//    }
//
//    public int getCount() {
//        return this.mThumbString.length;
//    }
//
//    public Object getItem(int position) {
//        return null;
//    }
//
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null) {
//            convertView = this.li.inflate(R.layout.gridview_componentes_gallery, null);
//            viewHolder v0 = new viewHolder();
//            v0.fl = (RelativeLayout) convertView.findViewById(R.id.frame_l_item_sub_category_item);
//            v0.textView = (TextView) convertView.findViewById(R.id.text_item_sub_cat_desc);
//            v0.imageView = (ImageView) convertView.findViewById(R.id.image_item_sub_category);
//            v0.cb = (CheckBox) convertView.findViewById(R.id.check_delete);
//            v0.cb.setClickable(true);
//            v0.cb.setFocusable(false);
//            v0.cb.setFocusableInTouchMode(false);
//            v0.cb.setContentDescription(this.mThumbString[position]);
//            new BitmapWorkerTask(v0.imageView).execute(new String[]{this.mThumbString[position], "gal"});
//            v0.textView.setText(this.mThumbString[position]);
//            v0.fl.setLayoutParams(this.GridViewParams);
//            convertView.setTag(v0);
//            convertView.setContentDescription(this.mThumbString[position]);
//        }
//        else {
//            convertView.getTag();
//        }
//
//        return convertView;
//    }
//}
//
