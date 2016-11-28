package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/23/16.
 */

public class TwentyOneEnterActivity extends BaseFragmentActivity {
    @Bind(R.id.tvDayNumber)
    TextView tvDayNumber;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_enter_layout);
    }

    @Override
    protected void uIViewInit() {

    }

    @Override
    protected void uIViewDataApply() {

    }
    @OnClick({R.id.layout1,R.id.layout2,R.id.layout3,R.id.layout4,R.id.layout5,R.id.left_layout})
    public void click(View view){

        switch (view.getId()){
            case R.id.layout1:
                Intent intent=new Intent(this,ImageAndVoiceActivity.class);
                startActivity(intent);
                break;
            case R.id.layout2:
                intent=new Intent(this,SuTablePracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.layout3:
                intent=new Intent(this, MandalaEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.layout4:
                intent=new Intent(this,ImageRemeberPracticeEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.layout5:
                intent=new Intent(this,RemeberPracticeEnterActivity.class);
                startActivity(intent);
                break;
            case R.id.left_layout:
                finish();
                break;
        }































    }

}
