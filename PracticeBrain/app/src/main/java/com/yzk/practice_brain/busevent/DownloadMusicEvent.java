package com.yzk.practice_brain.busevent;

import android.os.Parcel;
import android.os.Parcelable;

import com.yzk.practice_brain.bean.MusicListResult;
import com.yzk.practice_brain.manager.DownLoadManager;

import java.util.Map;

/**
 * Created by android on 12/2/16.
 */

public class DownloadMusicEvent implements Parcelable {
    public DownLoadManager.MEDIA_TYPE mMedia_type;
    public String mHttpUrl;
    public MusicListResult.MusicEntity musicEntity;
    public DownLoadManager.METHOD mMethod;
    public String mCachePath;
    public Map<String, String> mHeaders;

    public DownloadMusicEvent(DownLoadManager.METHOD method, DownLoadManager.MEDIA_TYPE media_type, MusicListResult.MusicEntity musicEntity, String cachedPath, Map<String, String> headers) {
        this.mMedia_type = media_type;
        this.mHttpUrl = musicEntity.url;
        this.musicEntity = musicEntity;
        this.mMethod = method;
        this.mCachePath = cachedPath;
        this.mHeaders = headers;
    }

    protected DownloadMusicEvent(Parcel in) {
        mHttpUrl = in.readString();
        mCachePath = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mHttpUrl);
        dest.writeString(mCachePath);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DownloadMusicEvent> CREATOR = new Creator<DownloadMusicEvent>() {
        @Override
        public DownloadMusicEvent createFromParcel(Parcel in) {
            return new DownloadMusicEvent(in);
        }

        @Override
        public DownloadMusicEvent[] newArray(int size) {
            return new DownloadMusicEvent[size];
        }
    };

}
