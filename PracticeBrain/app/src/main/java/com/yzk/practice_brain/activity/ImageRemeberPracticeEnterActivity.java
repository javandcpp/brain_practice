package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.busevent.BackgroudMusicEvent;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.RuleDialog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeEnterActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {
    @Bind(R.id.controlPanel)
    Controller controlPanel;

    @Bind(R.id.voiceable)
    ImageButton voicable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_remeber_practice_enter_layout);
    }

    @OnClick({R.id.begin,R.id.rule})
    public void click(View view){
        switch (view.getId()){
            case R.id.begin:
                Intent intent=new Intent(this,ImageRemeberPracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.rule:
                RuleDialog.Builder builder=new RuleDialog.Builder(this,"4");
                builder.create().show();
                break;
        }
    }

    @Override
    protected void uIViewInit() {
        controlPanel.setClickCallBack(this);
    }

    @Override
    protected void uIViewDataApply() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroudMusicEvent.VoiceEvent voiceEvent){
        if(1==voiceEvent.voiceValue){
            voicable.setSelected(false);
        }else{
            voicable.setSelected(true);
        }

    }

    @Override
    public void controll(View view) {
        switch (view.getId()){
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
