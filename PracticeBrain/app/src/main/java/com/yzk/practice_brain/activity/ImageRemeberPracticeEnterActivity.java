package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;

import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeEnterActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_remeber_practice_enter_layout);
    }

    @OnClick({R.id.begin})
    public void click(View view){
        switch (view.getId()){
            case R.id.begin:
                Intent intent=new Intent(this,ImageRemeberPracticeActivity.class);
                startActivity(intent);
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