package com.yzk.brain.setting;

import com.yzk.brain.busevent.BackgroudMusicEvent;
import com.yzk.brain.preference.PreferenceHelper;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by android on 11/23/16.
 */

public class Setting {
//    系统按键音获取：
//
//    Settings.System.getInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0);
//
//    1、需要import包android.provider
//    2、取出的值为int类型，0关闭，1开启
//
//
//    系统按键音设定：
//
//            Settings.System.putInt(getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 1);
//
//    1、需要import包android.provider
//    2、0关闭，1开启


    /**
     * 系统按键音获取
     *
     * @return
     */
    public static int getVoice() {
        return PreferenceHelper.getVoiceInt("voice");
//        return Settings.System.getInt(GlobalApplication.instance.getContentResolver(), Settings.System.SOUND_EFFECTS_ENABLED, 0);
    }

    /**
     * 系统按键音设定
     *
     * @param keyBoardVoice 0关闭，1开启
     * @return
     */
    public static void setSystemKeyBoardVoice(int keyBoardVoice) {
        HermesEventBus.getDefault().post(new BackgroudMusicEvent().new VoiceEvent(keyBoardVoice));
        PreferenceHelper.writeInt("voice", keyBoardVoice);
    }


}
