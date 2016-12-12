package com.yzk.brain.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.MandalaResult;
import com.yzk.brain.busevent.BackgroudMusicEvent;
import com.yzk.brain.config.Config;
import com.yzk.brain.module.mandalas.Mandalas2Activity;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.ui.Controller;
import com.yzk.brain.ui.RuleDialog;
import com.yzk.brain.utils.ImageUtils;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.ParseJson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class MandalaEnterActivity extends BaseFragmentActivity implements Controller.ControllerCallBack, ResponseStringDataListener {

    private static final int REQUEST_TASK =0x1 ;
    @Bind(R.id.imagen2)
    ImageView imageView;

    @Bind(R.id.controlPanel)
    Controller controller;


    @Bind(R.id.voiceable)
    ImageButton voiceable;
    private MandalaResult mandalaResult;

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
                if (null!=mandalaResult) {
                    Intent v1 = new Intent(this, Mandalas2Activity
                            .class);
//                    v1.putExtra("mandala-tag", String.valueOf(v2));
                    v1.putExtra("mandala-tag", "myimage");
                    v1.putExtra("entity", mandalaResult.data);
                    startActivity(v1);
                }
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

        getDataFromNet();

    }

    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)){
            HttpRequestUtil.HttpRequestByGet(Config.MANDALA,this,REQUEST_TASK);
        }else{
            Toast.makeText(MandalaEnterActivity.this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onDataDelivered(int taskId, String data) {
        switch (taskId){
            case REQUEST_TASK:
                try {
                    mandalaResult = ParseJson.parseJson(data, MandalaResult.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(MandalaEnterActivity.this, R.string.request_error, Toast.LENGTH_SHORT).show();
    }
}
