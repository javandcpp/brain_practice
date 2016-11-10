// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.practice_brain.module.mandalas;

import android.content.Context;
import android.content.SharedPreferences;

public class GrabarPreferencias {
    Context c;

    public GrabarPreferencias(Context context) {
        super();
        this.c = context;
    }

    public boolean getEfectos() {
        return this.c.getSharedPreferences("efectos", 0).getBoolean("efectos", false);
    }

    public void setEfectos(Boolean dato) {
        SharedPreferences.Editor v0 = this.c.getSharedPreferences("efectos", 0).edit();
        v0.clear();
        v0.putBoolean("efectos", dato.booleanValue());
        v0.commit();
    }
}

