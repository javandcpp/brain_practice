package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.RuleDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeActivity extends BaseFragmentActivity implements Controller.ControllerCallBack {

    @Bind(R.id.controlPanel)
    Controller controlPanel;

    @OnClick(R.id.rule)
    public void click(View view){
        switch (view.getId()){
            case R.id.rule:
                RuleDialog.Builder builder=new RuleDialog.Builder(this,"4");
                builder.create().show();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_remeber_practice_layout);
    }


    @Override
    protected void uIViewInit() {
        controlPanel.setClickCallBack(this);
    }

    @Override
    protected void uIViewDataApply() {

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
