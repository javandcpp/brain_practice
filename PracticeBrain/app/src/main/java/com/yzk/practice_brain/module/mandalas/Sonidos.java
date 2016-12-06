//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.content.Context;
//import android.media.MediaPlayer;
//
//import com.yzk.practice_brain.R;
//
//
//public class Sonidos {
//    Context context;
//    private MediaPlayer music;
//
//    public Sonidos(Context context) {
//        super();
//        this.context = context;
//    }
//
//    public void PlayMusic() {
//        this.music = MediaPlayer.create(this.context, R.raw.rain03);
//        this.music.start();
//        this.music.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            public void onCompletion(MediaPlayer mp) {
//                mp.stop();
//            }
//        });
//    }
//
//    public void StopMusic() {
//        this.music.stop();
//        this.music.release();
//    }
//}
//
