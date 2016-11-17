package com.yzk.practice_brain.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.GridAdapter;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.PracticeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/17/16.
 */

public class TwentyOnePracticeActivity extends BaseFragmentActivity {

    @Bind(R.id.gridview)
    GridView gridView;


    private List<PracticeEntity> gridData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twenty_one_layout);
    }

    @Override
    protected void uIViewInit() {
        gridViewData();
        GridAdapter gridAdapter=new GridAdapter(this,gridData);
        gridView.setAdapter(gridAdapter);
    }

    private void gridViewData() {
        for (int x = 0; x < 22; x++) {
            if (x>0&&x==8){
                gridData.add(getResource(0+""));
            }else if (x==15){
                gridData.add(getResource(0+""));
            }
           gridData.add(getResource(x+""));
        }
    }

    private PracticeEntity getResource(String index) {
        String normal="normal_"+index;
        String seleted="seleted_"+index;

        Context ctx = getBaseContext();
        int normalRes = getResources().getIdentifier(normal, "drawable", ctx.getPackageName());
        int selectedRes = getResources().getIdentifier(seleted, "drawable", ctx.getPackageName());

        PracticeEntity practiceEntity=new PracticeEntity();
        practiceEntity.locked=true;
        practiceEntity.normalResId=normalRes;
        practiceEntity.unlockResId=selectedRes;
        return practiceEntity;
    }

    protected void uIViewDataApply() {


    }

    @OnClick(R.id.left_layout)
    public void click(View view){
        switch (view.getId()){
            case R.id.left_layout:
                finish();
                break;
        }
    }
}
