// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.brain.module.mandalas;

import android.content.Context;
import android.media.MediaPlayer;

import com.yzk.brain.R;


public class EfectosSonoros {
    private MediaPlayer click_btn;
    Context context;
    public boolean efectoclick;

    public EfectosSonoros(Context context) {
        super();
        this.efectoclick = false;
        this.context = context;
    }

    public void EfectoAlTocar() {
        this.click_btn = MediaPlayer.create(this.context, R.raw.water_droplet_1);
        try {
            this.click_btn.start();
            this.click_btn.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.stop();
                    mp.release();
                }
            });
        }
        catch(Exception v0) {
        }
    }

    public void clickStop() {
        if(this.click_btn != null) {
            this.click_btn.release();
        }
    }
}

