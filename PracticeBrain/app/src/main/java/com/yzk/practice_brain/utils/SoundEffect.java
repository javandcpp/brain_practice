package com.yzk.practice_brain.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;

import java.util.HashMap;

/**
 * Created by android on 11/29/16.
 */

public class SoundEffect {


    private final HashMap<Integer, Integer> spMap;
    private final SoundPool sp;

    private SoundEffect() {
        sp = new SoundPool(6, AudioManager.STREAM_MUSIC, 0);
        spMap = new HashMap<Integer, Integer>();
        spMap.put(1, sp.load(GlobalApplication.instance, R.raw.correct_voice, 1));
        spMap.put(2, sp.load(GlobalApplication.instance, R.raw.fail_voice, 1));
        spMap.put(3, sp.load(GlobalApplication.instance, R.raw.failure, 1));
        spMap.put(4, sp.load(GlobalApplication.instance, R.raw.success, 1));
        spMap.put(5, sp.load(GlobalApplication.instance, R.raw.mandla_fill_error, 1));
        spMap.put(6, sp.load(GlobalApplication.instance, R.raw.mandla_fill_voice, 1));
    }

    private static final SoundEffect instance = new SoundEffect();

    public static SoundEffect getInstance() {
        return instance;
    }

    public void play(int number) {
        //实例化AudioManager对象，控制声音
        AudioManager am = (AudioManager) GlobalApplication.instance.getSystemService(Context.AUDIO_SERVICE);
        //最大音量
        float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        //当前音量
        float audioCurrentVolumn = am.getStreamVolume(AudioManager.STREAM_MUSIC);
        float volumnRatio = audioCurrentVolumn / audioMaxVolumn;
        //播放
        sp.play(spMap.get(number),     //声音资源
                volumnRatio,         //左声道
                volumnRatio,         //右声道
                1,             //优先级，0最低
                0,         //循环次数，0是不循环，-1是永远循环
                1);            //回放速度，0.5-2.0之间。1为正常速度
    }


    public void stop(int number){
        sp.stop(spMap.get(number));
    }
}
