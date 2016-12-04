package com.yzk.practice_brain.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;

import java.util.HashMap;

/**
 * Created by android on 11/29/16.
 */

public class SoundEffect {


    public static final int SUCCESS=1;
    public static final int FAILURE=2;
    public static final int CORRECT=3;
    public static final int FAIL=4;
    public static final int FILL_ERROR=5;
    public static final int FILL_CORRECT=6;

    public int beforeNumber=-1;


    private final HashMap<Integer, Integer> spMap;
    private final SoundPool sp;

    private SoundEffect() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool.Builder().setMaxStreams(6).build();
        } else {
            sp = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        }
        spMap = new HashMap<Integer, Integer>();
        spMap.put(CORRECT, sp.load(GlobalApplication.instance, R.raw.correct_voice, 1));
        spMap.put(FAIL, sp.load(GlobalApplication.instance, R.raw.fail_voice, 1));
        spMap.put(FAILURE, sp.load(GlobalApplication.instance, R.raw.failure, 1));
        spMap.put(SUCCESS, sp.load(GlobalApplication.instance, R.raw.success, 1));
        spMap.put(FILL_ERROR, sp.load(GlobalApplication.instance, R.raw.mandla_fill_error, 1));
        spMap.put(FILL_CORRECT, sp.load(GlobalApplication.instance, R.raw.mandla_fill_voice, 1));
    }

    private static final SoundEffect instance = new SoundEffect();

    public static SoundEffect getInstance() {
        return instance;
    }

    public void play(int number) {
        AudioManager am = (AudioManager) GlobalApplication.instance.getSystemService(Context.AUDIO_SERVICE);
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;

        sp.play(spMap.get(number),     //声音资源
                1,         //左声道
                1,         //右声道
                1,             //优先级，0最低
                0,         //循环次数，0是不循环，-1是永远循环
                1);            //回放速度，0.5-2.0之间。1为正常速度
    }


    public void stop(int number) {
        sp.stop(spMap.get(number));
    }
}
