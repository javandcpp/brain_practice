package com.yzk.practice_brain.application;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.IBinder;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.yzk.practice_brain.IDownloadInterface;
import com.yzk.practice_brain.IMediaInterface;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.service.BgMediaPlayerServce;
import com.yzk.practice_brain.service.DownloadMusicService;

/**
 * Created by android on 9/10/15.
 */
public class GlobalApplication extends BaseApplication {


    public static GlobalApplication instance;
    public static Handler mHandler = new Handler();


    private ServiceConnection serviceConnectionMediaPlayer;
    private Intent intent;
    private IMediaInterface iMediaInterface;
    private ServiceConnection serviceConnectionDownload;
    private Intent intentDownload;
    private Intent intentMediaPlayer;
    private IDownloadInterface iDownLoadInter;


    /**
     * 获取媒体控制
     *
     * @return
     */
    public IMediaInterface getiMediaInterface() {
        return iMediaInterface;
    }

    public IDownloadInterface getIDownloadInterface() {
        return iDownLoadInter;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        connectionMediaPlayer();
        connectionDownload();
        boolean status1 = startBgPlayerService();//开启背景音乐服务
        boolean status2 = startDownLoadService();//开启下载服务

        LogUtil.d("mediaplay service status:" + status1);
        LogUtil.d("download service status:" + status2);

        initFresco();
    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    private void connectionDownload() {
        serviceConnectionDownload = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iDownLoadInter = IDownloadInterface.Stub.asInterface(iBinder);
                LogUtil.e("download service Connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                iDownLoadInter = null;
            }
        };
    }

    private void connectionMediaPlayer() {
        serviceConnectionMediaPlayer = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iMediaInterface = (IMediaInterface.Stub.asInterface(iBinder));
                LogUtil.e("media play service Connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                iMediaInterface = null;
            }
        };
    }

    /**
     * @return
     */
    public boolean startBgPlayerService() {
        intentMediaPlayer = new Intent(GlobalApplication.instance, BgMediaPlayerServce.class);
        startService(intentMediaPlayer);
        return bindService(intentMediaPlayer, serviceConnectionMediaPlayer, Context.BIND_AUTO_CREATE);
    }

    ;

    /**
     * 开启绑定下载服务
     *
     * @return
     */
    public boolean startDownLoadService() {
        intentDownload = new Intent(GlobalApplication.instance, DownloadMusicService.class);
        startService(intentDownload);
        return bindService(intentDownload, serviceConnectionDownload, Context.BIND_AUTO_CREATE);
    }

    ;

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    /**
     * init
     */
    private void init() {

    }

    public void exitApp() {
        unbindService(serviceConnectionDownload);
        stopService(intentDownload);
        unbindService(serviceConnectionMediaPlayer);
        stopService(intentMediaPlayer);
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ActivityStack.finishProgram();
//                android.os.Process.killProcess(android.os.Process.myPid());
//                System.exit(0);
//            }
//        }, 200);
    }
}
