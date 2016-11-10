// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.practice_brain.module.mandalas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.yzk.practice_brain.R;


public class GrillaImagenes01 extends BaseAdapter {

    private final Context v2;

    class viewHolder {
        FrameLayout fl;
        ImageView imageView;
        TextView textView;

        viewHolder() {
            super();
        }
    }

    AbsListView.LayoutParams GridViewParams;
    private String[] ThumbString;
    Metricas dim;
    FilesHandler handler;
    LayoutInflater li;
    private String[] mThumbString;

    public GrillaImagenes01(View v, String nombreStringArray) {
        super();
        this.handler = new FilesHandler();
        this.dim = new Metricas();
        v2 = v.getContext().getApplicationContext();
        this.ThumbString = v2.getResources().getStringArray(v2.getResources().getIdentifier(nombreStringArray,
                "array", v2.getPackageName()));
        if (nombreStringArray == "indicePadre") {
            int v4 = this.ThumbString.length + 1;
            if (this.handler.comprobarGallery()) {
                this.mThumbString = new String[v4];
                String[] v5 = this.mThumbString;
                this.handler.getClass();
                v5[v4 - 1] = "mydocfolder";
            } else {
                this.mThumbString = new String[this.ThumbString.length];
            }

            int v3;
            for (v3 = 0; v3 < this.ThumbString.length; ++v3) {
                this.mThumbString[v3] = this.ThumbString[v3];
            }
        } else {
            this.mThumbString = this.ThumbString;
        }

        this.li = LayoutInflater.from(v2);
        int v0 = this.dim.MedirAnchoPantalla(v2);
        this.GridViewParams = new AbsListView.LayoutParams(v0, v0);
    }

    public int getCount() {
        return this.mThumbString.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public String getThumbString(int pos) {
        return this.mThumbString[pos];
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = this.li.inflate(R.layout.gridview_componentes, null);
            final viewHolder v0 = new viewHolder();
            v0.fl = (FrameLayout) convertView.findViewById(R.id.frame_l_item_sub_category_item);
            v0.textView = (TextView) convertView.findViewById(R.id.text_item_sub_cat_desc);
            v0.imageView = (ImageView) convertView.findViewById(R.id.image_item_sub_category);
            new BitmapWorkerTask(v0.imageView).execute(new String[]{this.mThumbString[position], "imagenes"});
            v0.textView.setText(this.mThumbString[position]);
            v0.fl.setLayoutParams(this.GridViewParams);
            convertView.setTag(v0);
            convertView.setContentDescription(this.mThumbString[position]);

//            convertView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    handler.devolverNombreTemp();
////                    handler.DeleteTempFile();
////                    handler.SaveDrawing("temp", v0.imageView, mThumbString[position]);
////                    String v2 = String.valueOf(v.getContentDescription());
//
//
////                    if(SubIndice.this.handler.comprobarTemp()) {
////                        new DialogsHandler(SubIndice.this).goToDraw(((View)arg6), v2, "deBiblioteca");
////                    }
////                    else {
//                    Intent v1 = new Intent(v2, Mandalas2Activity
//                            .class);
//                    v1.putExtra("mandala-tag",mThumbString[position]
//                    );
//                    v1.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                    v2.startActivity(v1);
//                }
//
////                }
//            });
        } else {
            convertView.getTag();
        }

        return convertView;
    }
}

