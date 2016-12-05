package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.ui.RuleDialog;

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

    }

    @Override
    protected void uIViewDataApply() {

    }
}
