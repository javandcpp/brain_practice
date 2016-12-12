package com.yzk.brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.adapter.ImagePracticeEnterAdapter;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.ImageResult;
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
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageRemeberPracticeEnterActivity extends BaseFragmentActivity implements Controller.ControllerCallBack, ResponseStringDataListener {
    private static final int REQUEST_DATA = 0x1;
    @Bind(R.id.controlPanel)
    Controller controlPanel;

    @Bind(R.id.voiceable)
    ImageButton voicable;

    @Bind(R.id.gridView)
    GridView gridView;

    @Bind(R.id.loading)
    CircularProgressView circularProgressView;

    private ArrayList<ImageResult.Image> list;
    private ImagePracticeEnterAdapter imagePracticeEnterAdapter;
    private List<ImageResult.Image> tempList=new ArrayList<>();
    private ImageResult imageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_remeber_practice_enter_layout);
    }

    @OnClick({R.id.begin, R.id.rule})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.begin:
                if (tempList.size()>0) {
                    Intent intent = new Intent(this, ImageRemeberPracticeActivity.class);
                    intent.putExtra("data", list);
                    intent.putExtra("entity",imageResult.data);
                    startActivity(intent);
                }
                break;
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "4");
                builder.create().show();
                break;
        }
    }

    @Override
    protected void uIViewInit() {
        controlPanel.setClickCallBack(this);
    }

    @Override
    protected void uIViewDataApply() {
        getDataFromNet();

    }

    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)) {
            circularProgressView.setVisibility(View.VISIBLE);
            HttpRequestUtil.HttpRequestByGet(Config.IMAGE_REMEBER, this, REQUEST_DATA);
        } else {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(BackgroudMusicEvent.VoiceEvent voiceEvent) {
        if (1 == voiceEvent.voiceValue) {
            voicable.setSelected(false);
        } else {
            voicable.setSelected(true);
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

    @Override
    public void onDataDelivered(int taskId, String data) {
        switch (taskId) {
            case REQUEST_DATA:
                if (null!=circularProgressView) {
                    circularProgressView.setVisibility(View.GONE);
                }
                imageResult = ParseJson.parseJson(data, ImageResult.class);
                if (null != imageResult && null != imageResult.data && null != imageResult.data.list) {
                    list = (ArrayList<ImageResult.Image>) imageResult.data.list;
                    tempList.addAll(list);
                    if (null==imagePracticeEnterAdapter) {
                        imagePracticeEnterAdapter = new ImagePracticeEnterAdapter();
                        gridView.setAdapter(imagePracticeEnterAdapter);
                    }
                    imagePracticeEnterAdapter.setData(tempList);
                }
                break;
        }
    }

    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
        if (null!=circularProgressView) {
            circularProgressView.setVisibility(View.GONE);
        }
    }
}
