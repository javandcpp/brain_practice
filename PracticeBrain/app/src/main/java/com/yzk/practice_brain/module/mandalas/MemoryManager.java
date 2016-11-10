// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.practice_brain.module.mandalas;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

public class MemoryManager {
    public MemoryManager() {
        super();
    }

    public void cleangal(GridView gv) {
        ListAdapter v5 = null;
        int v0 = gv.getChildCount();
        int v1;
        for(v1 = 0; v1 < v0; ++v1) {
            View v3 = gv.getChildAt(v1);
            if(v3 != null) {
                View v2 = ((RelativeLayout)v3).getChildAt(0);
                if(v2 != null) {
                    ((ImageView)v2).setImageDrawable(((Drawable)v5));
                    ((ImageView)v2).setImageResource(0);
                }
            }
        }

        gv.removeViewsInLayout(0, v0);
        gv.setAdapter(v5);
        System.gc();
    }

    public void cleanmem(GridView gv) {
        ListAdapter v5 = null;
        int v0 = gv.getChildCount();
        int v1;
        for(v1 = 0; v1 < v0; ++v1) {
            View v3 = gv.getChildAt(v1);
            if(v3 != null) {
                View v2 = ((FrameLayout)v3).getChildAt(0);
                if(v2 != null) {
                    ((ImageView)v2).setImageDrawable(((Drawable)v5));
                    ((ImageView)v2).setImageResource(0);
                }
            }
        }

        gv.removeViewsInLayout(0, v0);
        gv.setAdapter(v5);
        System.gc();
    }

    public void limpiarImageview(ImageView iv) {
        if(iv != null) {
            iv.setImageDrawable(null);
            iv.setImageResource(0);
        }
    }
}

