package com.yzk.brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.busevent.BackgroudMusicEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/23/16.
 */

public class TwentyOneEnterActivity extends BaseFragmentActivity {
    @Bind(R.id.tvDayNumber)
    TextView tvDayNumber;


    @Bind(R.id.right_image)
    ImageButton rightImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_enter_layout);
    }

    @Subscribe(threadMode= ThreadMode.MAIN)
    public void onEventMainThread(BackgroudMusicEvent.MusicVoiceEvent musicVoiceEvent){
        if (musicVoiceEvent.play){
            rightImage.setSelected(false);
        }else{
            rightImage.setSelected(true);
        }
    }

    @Override
    protected void uIViewInit() {

    }

    @Override
    protected void uIViewDataApply() {

    }

    @OnClick({R.id.layout1, R.id.layout2, R.id.layout3, R.id.layout4, R.id.layout5, R.id.left_layout,R.id.right_layout})
    public void click(View view) {

        switch (view.getId()) {
            case R.id.layout1:
                Intent intent = new Intent(this, ImageAndVoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.layout2:
                intent = new Intent(this, SuTablePracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout3:
                intent = new Intent(this, MandalaEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.layout4:
                intent = new Intent(this, ImageRemeberPracticeEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.layout5:
                intent = new Intent(this, RemeberPracticeEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isSilent()) {
                        GlobalApplication.instance.getiMediaInterface().openVolume();
                        rightImage.setSelected(false);

                    } else {
                        GlobalApplication.instance.getiMediaInterface().closeVolume();
                        rightImage.setSelected(true);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

}
