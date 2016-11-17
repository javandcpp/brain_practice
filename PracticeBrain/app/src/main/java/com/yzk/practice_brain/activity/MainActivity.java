package com.yzk.practice_brain.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;

import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void uIViewInit() {


    }


    @OnClick({R.id.rlone, R.id.rltwo,R.id.rlthree,R.id.rlfour})
    public void onClick(View view) {
        Intent intent=new Intent();
        switch (view.getId()) {
            case R.id.rlone:
                intent.setClass(this,TwentyOnePracticeActivity.class);
                break;
            case R.id.rltwo:
                break;
            case R.id.rlthree:
                break;
            case R.id.rlfour:
                intent.setClass(this,EducationNewsActivity.class);
                break;
        }
        startActivity(intent);

    }

    @Override
    protected void uIViewDataApply() {

    }
}
