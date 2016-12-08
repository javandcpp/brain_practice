package com.yzk.brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.adapter.RemberPracticeEnterAdapter;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.RemberPracticeResult;
import com.yzk.brain.busevent.BackgroudMusicEvent;
import com.yzk.brain.config.Config;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.ui.CircularProgressView;
import com.yzk.brain.ui.Controller;
import com.yzk.brain.ui.RuleDialog;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.ParseJson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class RemeberPracticeEnterActivity extends BaseFragmentActivity implements ResponseStringDataListener, Controller.ControllerCallBack {
    @Bind(R.id.controlPanel)
    Controller controllPanel;

    private static final int REQEUST_REMEMBER_TASK = 0x1;

    @Bind(R.id.loading)
    CircularProgressView loading;

    @Bind(R.id.voiceable)
    ImageButton voiceable;

    @Bind(R.id.gridView)
    GridView gridView;
    private ArrayList<RemberPracticeResult.Practice> tempList = new ArrayList<>();
    private boolean isTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isTest = getIntent().getBooleanExtra("isTest", false);
        setContentView(R.layout.remeber_practice_enter_layout);
    }

    @OnClick({R.id.begin, R.id.rule})
    public void click(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.begin:
                if (tempList.size() > 0) {
                    intent = new Intent(this, RemeberPracticeActivity.class);
                    intent.putExtra("isTest",isTest);
                    intent.putExtra("data", tempList);
                    startActivity(intent);
                }
                break;
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "2");
                builder.create().show();
                break;
        }

    }


    @Override
    protected void uIViewInit() {
        controllPanel.setClickCallBack(this);
        getDataFromNet();

    }

    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)) {
            loading.setVisibility(View.VISIBLE);
            if (isTest) {
                HttpRequestUtil.HttpRequestByGet(Config.TEST_REMEBER_PRACTICE_URL, this, REQEUST_REMEMBER_TASK);
            } else {
                HttpRequestUtil.HttpRequestByGet(Config.REMEBER_PRACTICE_URL, this, REQEUST_REMEMBER_TASK);

            }
        } else {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void uIViewDataApply() {

    }

    @Override
    public void onDataDelivered(int taskId, String data) {
        switch (taskId) {
            case REQEUST_REMEMBER_TASK:
                loading.setVisibility(View.GONE);
                RemberPracticeResult remberPracticeResult = ParseJson.parseJson(data, RemberPracticeResult.class);
                if (null != remberPracticeResult && null != remberPracticeResult.data && null != remberPracticeResult.data.memoryTrainWordsView) {
                    tempList.clear();
                    tempList.addAll(remberPracticeResult.data.memoryTrainWordsView);
                    RemberPracticeEnterAdapter adapter = new RemberPracticeEnterAdapter(remberPracticeResult.data.memoryTrainWordsView);
                    gridView.setAdapter(adapter);
                }
                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
        loading.setVisibility(View.GONE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroudMusicEvent.VoiceEvent voiceEvent) {
        if (1 == voiceEvent.voiceValue) {
            voiceable.setSelected(false);
        } else {
            voiceable.setSelected(true);
        }

    }

    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.retry:
                break;
            case R.id.back:
                finish();
                break;

            case R.id.play:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                        ((ImageButton) view).setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().pause();
                    } else {
                        ((ImageButton) view).setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().play();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }
}
