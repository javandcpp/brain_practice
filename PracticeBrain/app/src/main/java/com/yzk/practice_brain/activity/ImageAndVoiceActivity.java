package com.yzk.practice_brain.activity;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.bean.ExplainResult;
import com.yzk.practice_brain.config.Config;
import com.yzk.practice_brain.constants.Constants;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.manager.DownLoadManager;
import com.yzk.practice_brain.network.HttpRequestUtil;
import com.yzk.practice_brain.task.DownloadRunnable;
import com.yzk.practice_brain.ui.Controller;
import com.yzk.practice_brain.ui.ProgressDialog;
import com.yzk.practice_brain.ui.RuleDialog;
import com.yzk.practice_brain.utils.NetworkUtils;
import com.yzk.practice_brain.utils.ParseJson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class ImageAndVoiceActivity extends BaseFragmentActivity implements Controller.ControllerCallBack, ResponseStringDataListener, DownloadRunnable.DownloadProgressListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, ProgressDialog.Builder.BackListener {

    private static final int REQUEST_DATA = 0x1;
    private static final int MP3DOWNLOAD_SUCCESS = 100;
    private static final int IMAGEDOWNLOAD_SUCCESS = 101;
    @Bind(R.id.controlPanel)
    Controller controllPanel;

    @Bind(R.id.play)
    ImageButton play;


    @Bind(R.id.draweeView)
    SimpleDraweeView draweeView;
    private ProgressDialog.Builder builder;
    private ProgressDialog progressDialog;
    private ControllerListener controllerListener;

    private boolean imageLoadFinish;
    private boolean mp3DownFinish;
    private boolean personCloseMusic;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            return false;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MP3DOWNLOAD_SUCCESS:
                    JSONObject data = (JSONObject) msg.obj;
                    try {
                        path = data.getString("path");
                        name = data.getString("name");
                        if (imageLoadFinish) {
                            closeDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case IMAGEDOWNLOAD_SUCCESS:
                    if (mp3DownFinish) {
                        closeDialog();
                    }

                    break;
            }
        }
    };
    private String path;
    private String name;
    private MediaPlayer mMediaPlayer;
    private AudioManager audioManager;
    private int maxVolume;
    private int curVolume;

    @Override
    public void onLeftClick() {//退出此界面,默认开始播放音乐
        try {
            if (personCloseMusic) {
                if (GlobalApplication.instance.getiMediaInterface().isPause()) {
                    play.setSelected(true);
                    GlobalApplication.instance.getiMediaInterface().play();
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        finish();
    }


    private void closeDialog() {

        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                            play.setSelected(false);
                            GlobalApplication.instance.getiMediaInterface().pause();
                        }
                            try {
                                mMediaPlayer.setDataSource(new File(path + File.separator + name).getAbsolutePath());
                                mMediaPlayer.prepare();
                                mMediaPlayer.start();

                                Toast.makeText(GlobalApplication.instance, "begin start media player:" + name + "," + path, Toast.LENGTH_SHORT).show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 200);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builder = new ProgressDialog.Builder(this);
        progressDialog = builder.setBackClickListener(this).create();
        initMediaPlayer();

        setContentView(R.layout.imageandvoice_layout);
    }

    @OnClick(R.id.rule)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rule:
                RuleDialog.Builder builder = new RuleDialog.Builder(this, "3");
                builder.create().show();
                break;
        }
    }


    @Override
    protected void uIViewInit() {
        controllerListener = new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(
                    String id,
                    @Nullable ImageInfo imageInfo,
                    @Nullable Animatable anim) {
                if (imageInfo == null) {
                    return;
                }
                imageLoadFinish = true;
                LogUtil.e("success---------------------imageInfo:" + imageInfo);
                mHandler.sendEmptyMessage(IMAGEDOWNLOAD_SUCCESS);
            }

            @Override
            public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
            }

            @Override
            public void onFailure(String id, Throwable throwable) {
                LogUtil.e("failure---------------fresco");
            }
        };
        controllPanel.setClickCallBack(this);
        getDataFromNet();
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     *
     */
    private void getDataFromNet() {
        if (NetworkUtils.isConnected(this)) {
            HttpRequestUtil.HttpRequestByGet(Config.EXPLAIN, this, REQUEST_DATA);
        } else {
            Toast.makeText(this, R.string.net_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void uIViewDataApply() {

    }

    private void retry() {
    }


    @Override
    public void controll(View view) {
        switch (view.getId()) {
            case R.id.back:
                onLeftClick();
                break;
            case R.id.retry:
                retry();
                break;
            case R.id.play:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                        ((ImageButton) view).setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().pause();
                        personCloseMusic = true;
                    } else {
                        ((ImageButton) view).setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().play();
                        personCloseMusic = false;
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
                ExplainResult explainResult = ParseJson.parseJson(data, ExplainResult.class);
                if (null != explainResult && null != explainResult.data) {
                    download(explainResult.data.get(0));
                }
                break;
        }
    }

    /**
     * 下载解说资源
     *
     * @param explain
     */
    private void download(ExplainResult.Explain explain) {
        if (null != explain) {
            Uri uri = Uri.parse(explain.imgUrl);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(controllerListener)
                    .setUri(uri)
                    .build();
            draweeView.setController(controller);
            DownloadRunnable downloadRunnable = new DownloadRunnable(0, 1, explain.url, explain.name, explain.fileLength, 0, Constants.EXPLAIN_PATH, this);
            DownLoadManager.getInstance().getQueue().add(downloadRunnable);

        }
    }


    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 初始化音量大概为最大音量的1/2
        curVolume = maxVolume / 2;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);


    }


    @Override
    public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
        Toast.makeText(this, R.string.request_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void downloading(long downloadSize, int percent) {
        LogUtil.e("download:" + downloadSize + ",percent:" + percent);
        builder.setProgress(percent);
    }

    @Override
    public void downloadError(String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ImageAndVoiceActivity.this, "资源加载失败", Toast.LENGTH_SHORT).show();
                builder.setProgress(0);
            }
        });
    }

    @Override
    public void downloadSuccess(String fileName, String path) {
        LogUtil.e("下载成功");
        mp3DownFinish = true;
        if (null != progressDialog && progressDialog.isShowing()) {
            Message message = mHandler.obtainMessage();
            JSONObject object = new JSONObject();
            try {
                object.put("path", path);
                object.put("name", fileName);
                message.obj = object;
                message.what = MP3DOWNLOAD_SUCCESS;
                mHandler.sendMessage(message);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
        mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public void back() {
        onLeftClick();
    }
}
