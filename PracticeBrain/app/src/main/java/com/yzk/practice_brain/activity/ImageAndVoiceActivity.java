package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.ui.Controller;

import butterknife.Bind;

/**
 * Created by android on 11/24/16.
 */

public class ImageAndVoiceActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    @Bind(R.id.controlPanel)
    Controller controllPanel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageandvoice_layout);
    }

    @Override
    protected void uIViewInit() {
        controllPanel.setClickCallBack(this);


    }

    @Override
    protected void uIViewDataApply() {

    }

    private void retry(){}


    @Override
    public void controll(View view) {
        switch (view.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.retry:
                retry();
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
