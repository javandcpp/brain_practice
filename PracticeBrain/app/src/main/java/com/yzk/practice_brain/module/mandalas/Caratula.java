//// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com
//
//package com.yzk.practice_brain.module.mandalas;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.ImageView;
//
//import com.yzk.practice_brain.R;
//
//
//public class Caratula extends Activity implements Animation.AnimationListener {
//    private ImageView ImgMessage;
//    Animation anim;
//
//    public Caratula() {
//        super();
//    }
//
//    public void onAnimationEnd(Animation animation) {
//        Intent v0 = new Intent(((Context)this), Indice.class);
//        this.anim = null;
//        this.startActivity(v0);
//    }
//
//    public void onAnimationRepeat(Animation animation) {
//    }
//
//    public void onAnimationStart(Animation animation) {
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.caratula);
//        this.ImgMessage = (ImageView) findViewById(R.id.imagenCaratula);
//        FilesHandler v0 = new FilesHandler();
//        v0.DeleteTempFile();
//        v0.ArmarDirs();
//        this.anim = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.animcaratula);
//        this.anim.setAnimationListener(((Animation.AnimationListener)this));
//        this.ImgMessage.startAnimation(this.anim);
//        this.ImgMessage.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Caratula.this.startActivity(new Intent(Caratula.this, Indice.class));
//            }
//        });
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        System.gc();
//    }
//
//    public void onPause() {
//        super.onPause();
//    }
//
//    public void onResume() {
//        super.onResume();
//    }
//
//    public void onStart() {
//        super.onStart();
//    }
//
//    public void onStop() {
//        super.onStop();
//        this.ImgMessage = null;
//        this.anim = null;
//    }
//}
//
