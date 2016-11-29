package com.yzk.practice_brain.module.mandalas;

import android.os.Bundle;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.utils.SoundEffect;

import butterknife.OnClick;

public class TestActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @OnClick({R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six})
    public void click(View view){
        switch (view.getId()){
            case R.id.one:
                SoundEffect.getInstance().play(1);
                break;
            case R.id.two:
                SoundEffect.getInstance().play(2);
                break;
            case R.id.three:
                SoundEffect.getInstance().play(3);
                break;
            case R.id.four:
                SoundEffect.getInstance().play(4);
                break;
            case R.id.five:
                SoundEffect.getInstance().play(5);
                break;
            case R.id.six:
                SoundEffect.getInstance().play(6);
                break;
        }
    }


    @Override
    protected void uIViewInit() {

    }

    @Override
    protected void uIViewDataApply() {

    }


}
