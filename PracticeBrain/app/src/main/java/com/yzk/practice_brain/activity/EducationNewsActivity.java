package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/17/16.
 */

public class EducationNewsActivity extends BaseFragmentActivity {


    @Bind(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_news_layout);
    }

    @Override
    protected void uIViewInit() {
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(EducationNewsActivity.this, "onRefresh", Toast.LENGTH_SHORT).show();
                materialRefreshLayout.finishRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(EducationNewsActivity.this, "onLoadMore", Toast.LENGTH_SHORT).show();
                materialRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    @Override
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
