package com.yzk.practice_brain.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yzk.practice_brain.IDownloadInterface;
import com.yzk.practice_brain.busevent.BackgroudMusicEvent;
import com.yzk.practice_brain.manager.DownLoadManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by android on 12/1/16.
 */

public class DownloadMusicService extends Service {

    private DownLoadManager downLoadManager;

    @Override
    public void onCreate() {
        super.onCreate();
        downLoadManager = DownLoadManager.getInstance();
        HermesEventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new DownloadBinder();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
        startService(new Intent(this, DownloadMusicService.class));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BackgroudMusicEvent.DownloadMusicEvent downloadMusicEvent) {
        DownLoadManager.getInstance().startDownLoad(downloadMusicEvent.mMethod, downloadMusicEvent.mMedia_type, downloadMusicEvent.mHttpUrl, downloadMusicEvent.mFileName, downloadMusicEvent.mCachePath, downloadMusicEvent.mHeaders);
    }


    /**
     * 文件下载
     *
     * @param fileName
     */
    private void startDownLoad(DownLoadManager.MEDIA_TYPE media_type, String fileName) {


    }


    public class DownloadBinder extends IDownloadInterface.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void downLoadMusic(String name) throws RemoteException {
        }
    }


}
