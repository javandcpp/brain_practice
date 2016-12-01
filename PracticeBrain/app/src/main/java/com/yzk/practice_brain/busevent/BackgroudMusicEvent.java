package com.yzk.practice_brain.busevent;

import com.yzk.practice_brain.manager.DownLoadManager;

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


    public static class DownloadMusicEvent{
        public final DownLoadManager.MEDIA_TYPE mMedia_type;
        public final String mHttpUrl;
        public final String mFileName;
        public final DownLoadManager.METHOD mMethod;
        public final String mCachePath;
        public final Map<String, String> mHeaders;

        public DownloadMusicEvent(DownLoadManager.METHOD method, DownLoadManager.MEDIA_TYPE media_type, String url, String fileName, String cachedPath, Map<String, String> headers){
            this.mMedia_type = media_type;
            this.mHttpUrl = url;
            this.mFileName = fileName;
            this.mMethod = method;
            this.mCachePath = cachedPath;
            this.mHeaders = headers;
        }

    }



}
