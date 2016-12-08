package com.yzk.brain.busevent;

import com.yzk.brain.bean.MusicListResult;
import com.yzk.brain.manager.DownLoadManager;

import java.util.Map;

/**
 * Created by android on 11/29/16.
 */

public class BackgroudMusicEvent {

    public static class MusicVoiceEvent{
        public final boolean play;

        public MusicVoiceEvent(boolean tag){
            this.play=tag;
        }

    }


    public class DownloadMusicEvent{
        public final DownLoadManager.MEDIA_TYPE mMedia_type;
        public final String mHttpUrl;
        public final MusicListResult.MusicEntity musicEntity;
        public final DownLoadManager.METHOD mMethod;
        public final String mCachePath;
        public final Map<String, String> mHeaders;

        public DownloadMusicEvent(DownLoadManager.METHOD method, DownLoadManager.MEDIA_TYPE media_type,MusicListResult.MusicEntity musicEntity, String cachedPath, Map<String, String> headers){
            this.mMedia_type = media_type;
            this.mHttpUrl = musicEntity.url;
            this.musicEntity = musicEntity;
            this.mMethod = method;
            this.mCachePath = cachedPath;
            this.mHeaders = headers;
        }

    }

    public class DownloadFinishEvent{


        public final String mMusicName;

        public DownloadFinishEvent(String fileName){
            this.mMusicName=fileName;
        }
    }


    public class VoiceEvent{

        public final int voiceValue;
        public VoiceEvent(int voiceValue){
            this.voiceValue=voiceValue;
        }
    }




}
