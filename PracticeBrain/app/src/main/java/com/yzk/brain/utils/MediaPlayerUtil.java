package com.yzk.brain.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.os.PowerManager;

import com.yzk.brain.application.GlobalApplication;

import java.io.File;

/**
 * Created by android on 11/28/16.
 */

public class MediaPlayerUtil implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {

    private final AudioManager audioManager;
    public static final String CACHE_PATH=Environment.getDataDirectory().getAbsolutePath()+ File.separator+"cached_path";
    private MediaPlayer mMediaPlayer;

    private MediaPlayerUtil(){

        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(GlobalApplication.instance.getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        audioManager = (AudioManager) GlobalApplication.instance.getSystemService(Context.AUDIO_SERVICE);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
    }

    private static final MediaPlayerUtil instance=new MediaPlayerUtil();

    public static MediaPlayerUtil getInstance(){
        return instance;
    }


    public void play(){

        try {
//            mMediaPlayer.setDataSource();
//            mMediaPlayer.prepare();
//            mMediaPlayer.start();
//            LogUtil.e("mdiaplayer play  :" +del);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 播放完毕
     * @param mediaPlayer
     */
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

    }

    /**
     * 播放器错误
     * @param mediaPlayer
     * @param i
     * @param i1
     * @return
     */
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        return false;
    }
}
