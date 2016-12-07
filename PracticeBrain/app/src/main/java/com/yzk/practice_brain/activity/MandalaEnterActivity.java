package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.busevent.BackgroudMusicEvent;
import com.yzk.practice_brain.module.mandalas.Mandalas2Activity;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.RuleDialog;
import com.yzk.practice_brain.utils.ImageUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class MandalaEnterActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    @Bind(R.id.imagen2)
    ImageView imageView;

    @Bind(R.id.controlPanel)
    Controller controller;


    @Bind(R.id.voiceable)
    ImageButton voiceable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandalas_enter);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                        ImageUtils.getImageColorValue(bitmap,(int) motionEvent.getX(),(int) motionEvent.getY());
                        break;
                }


                return true;
            }
        });
    }

    @OnClick({R.id.btnStart, R.id.rule})
    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnStart:
                Intent v1 = new Intent(this, Mandalas2Activity
                        .class);
//                    v1.putExtra("mandala-tag", String.valueOf(v2));
                v1.putExtra("mandala-tag", "myimage");
                startActivity(v1);
                break;
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "5");
                builder.create().show();
                break;
        }
    }


    @Override
    protected void uIViewInit() {
        controller.setClickCallBack(this);
    }

    @Override
    protected void uIViewDataApply() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroudMusicEvent.VoiceEvent voiceEvent) {
        if (1 == voiceEvent.voiceValue) {
            voiceable.setSelected(false);
        } else {
            voiceable.setSelected(true);
        }
    }

    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.retry:
                break;
            case R.id.back:
                finish();
                break;
            case R.id.play:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                        ((ImageButton) view).setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().pause();
                    } else {
                        ((ImageButton) view).setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
