package com.yzk.practice_brain.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.GridAdapter;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.PracticeEntity;
import com.yzk.practice_brain.constants.Constants;
import com.yzk.practice_brain.preference.PreferenceHelper;
import com.yzk.practice_brain.ui.UnScrollGridView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/17/16.
 */

public class TwentyOnePracticeActivity extends BaseFragmentActivity {

    @Bind(R.id.gridview)
    UnScrollGridView gridView;


    private List<PracticeEntity> gridData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_one_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gridViewData();
        GridAdapter gridAdapter=new GridAdapter(this,gridData);
        gridView.setAdapter(gridAdapter);
    }

    @Override
    protected void uIViewInit() {

    }

    private void gridViewData() {
        for (int x = 0; x < 22; x++) {
            if (x>0&&x==8){
                gridData.add(getResource(0,x));
            }else if (x==15){
                gridData.add(getResource(0,x));
            }
           gridData.add(getResource(x,x));
        }
    }

    private PracticeEntity getResource(int index,int cursor) {
        String normal="normal_"+index;
        String seleted="seleted_"+index;

        Context ctx = getBaseContext();
        int normalRes = getResources().getIdentifier(normal, "drawable", ctx.getPackageName());
        int selectedRes = getResources().getIdentifier(seleted, "drawable", ctx.getPackageName());

        PracticeEntity practiceEntity=new PracticeEntity();

        int anInt = PreferenceHelper.getInt(Constants.TWENTY_ONE);
        if (cursor<=anInt){
            practiceEntity.locked=false;
        }else {
            practiceEntity.locked = true;
        }
        practiceEntity.normalResId=normalRes;
        practiceEntity.unlockResId=selectedRes;
        practiceEntity.index=index;
        return practiceEntity;
    }

    protected void uIViewDataApply() {


    }

    @OnClick({R.id.left_layout,R.id.right_layout})
    public void click(View view){
        switch (view.getId()){
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:

                break;

        }
    }
}
