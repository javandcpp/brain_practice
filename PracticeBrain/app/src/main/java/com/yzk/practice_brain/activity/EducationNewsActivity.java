package com.yzk.practice_brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.yzk.practice_brain.R;
import com.yzk.practice_brain.adapter.EducationNewsAdapter;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.EducationResult;
import com.yzk.practice_brain.config.Config;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.network.HttpRequestUtil;
import com.yzk.practice_brain.ui.CircularProgressView;
import com.yzk.practice_brain.utils.NetworkUtils;
import com.yzk.practice_brain.utils.ParseJson;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/17/16.
 */

public class EducationNewsActivity extends BaseFragmentActivity implements ResponseStringDataListener {


    private static final int REQUEST_NEWS = 0x1;
    @Bind(R.id.refresh)
    MaterialRefreshLayout materialRefreshLayout;
    @Bind(R.id.loading)
    CircularProgressView circularProgressView;

    private ListView listView;
    private EducationNewsAdapter educationNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_news_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    protected void uIViewInit() {
        listView = (ListView) findViewById(R.id.listView);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                requestData();
            }
        });
        requestData();
    }

    private void requestData() {
        if (NetworkUtils.isConnected(this)) {
            circularProgressView.setVisibility(View.VISIBLE);
            HttpRequestUtil.HttpRequestByGet(Config.EDUCATION_NEWS_URL, this, REQUEST_NEWS);
        } else {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
            materialRefreshLayout.finishRefresh();
        }


    }

    @Override
    protected void uIViewDataApply() {

    }

    @OnClick(R.id.left_layout)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
        }
    }

    @Override
    public void onDataDelivered(int taskId, String data) {
        LogUtil.e(data);
        switch (taskId) {
            case REQUEST_NEWS:
                circularProgressView.setVisibility(View.GONE);
                EducationResult educationResult = ParseJson.parseJson(data, EducationResult.class);
                if (null != educationResult && "0".equals(educationResult.code)) {
                    LogUtil.e(educationResult.toString());
                    if (null==educationNewsAdapter) {
                        educationNewsAdapter = new EducationNewsAdapter();
                        listView.setAdapter(educationNewsAdapter);
                    }
                    educationNewsAdapter.setData(educationResult.data);
                    materialRefreshLayout.finishRefresh();
                }
                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
        circularProgressView.setVisibility(View.GONE);
    }
}
