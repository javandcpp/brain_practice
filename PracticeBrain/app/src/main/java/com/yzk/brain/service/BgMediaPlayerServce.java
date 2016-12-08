package com.yzk.brain.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.yzk.brain.IMediaInterface;
import com.yzk.brain.R;
import com.yzk.brain.busevent.BackgroudMusicEvent;
import com.yzk.brain.constants.Constants;
import com.yzk.brain.log.LogUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * Created by android on 11/18/16.
 */

public class BgMediaPlayerServce extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {


    private MediaPlayer mMediaPlayer;
    private AssetManager mAssetManager;
    private List<AssetFileDescriptor> mMedaiList;
    private List<File> sdCardMusic;
    private int currentCursor = 0;
    private AudioManager audioManager;
    private int maxVolume;
    private int curVolume;
    private int stepVolume;
    private boolean isPause;
    public boolean isSilent;
    public boolean playSdcard = true;
    private Handler mHandler;

    private void initMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        // 获取最大音乐音量
        maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // 初始化音量大概为最大音量的1/2
        curVolume = maxVolume / 2;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
        // 每次调整的音量大概为最大音量的1/6
        stepVolume = maxVolume / 6;
//        mMediaPlayer.set
        /* 监听播放是否完成 */
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);

    }

    private void destroyMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
            startForeground(250, builder.build());
            startService(new Intent(this, DaemonService.class));
        } else {
            startForeground(250, new Notification());
        }

        HermesEventBus.getDefault().register(this);
        mHandler = new Handler();
        LogUtil.d(BgMediaPlayerServce.class.getSimpleName() + " onCreate");
        mMedaiList = new ArrayList<>();
        sdCardMusic = new ArrayList<>();
        if (playSdcard && getSdMusic()) {//如果从SD开始播放,加载数据
            playSdcard = true;
            LogUtil.e("playsdcard:" + getSdMusic() + ":" + playSdcard);
        } else {//否则加载APK资源
            playSdcard = false;
            LogUtil.e("playraw:" + !playSdcard);
            getRawMediaResource();
        }

        initMediaPlayer();

    }

    /**
     * 获取打包音乐
     */
    private void getRawMediaResource() {
        mAssetManager = getAssets();
        try {
            for (int i = 1; i < 4; i++) {

                String musicName = "music" + i + ".mp3";
                AssetFileDescriptor fileDescriptor = mAssetManager.openFd(musicName);
                mMedaiList.add(fileDescriptor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取sdcard下音乐
     */
    private boolean getSdMusic() {
        File sdDir = new File(Constants.MUSIC_PATH);
        if (!sdDir.exists()) {
            return false;
        }
        File[] files = sdDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
                return null != s ? s.endsWith(".mp3") : false;
            }
        });
        if (files.length > 1) {
            for (File file : files
                    ) {
                LogUtil.e("sdmusic file------------>" + file.getAbsolutePath());
                sdCardMusic.add(file);
            }
        }
        return files.length > 1;

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MediaBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        Notification noti = new Notification.Builder(this)
//                .setContentTitle(getResources().getString(R.string.app_name))
//                .setContentText("")
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentIntent(pendingIntent)
//                .build();
//        startForeground(12346, noti);
        return START_STICKY;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onevent(final BackgroudMusicEvent.DownloadFinishEvent downloadFinishEvent) {
        //获取SD卡下载的音乐
        new Thread() {
            @Override
            public void run() {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (getSdMusic()) {//如果从SD开始播放,加载数据
                            playSdcard = true;
                        } else {//否则加载APK资源
                            playSdcard = false;
                        }
                        LogUtil.e("download finish:" + downloadFinishEvent.mMusicName + ",playsdcard:" + playSdcard);

                    }
                }, 200);

            }
        }.start();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
        HermesEventBus.getDefault().destroy();
        mHandler.removeCallbacksAndMessages(null);
        LogUtil.d(BgMediaPlayerServce.class.getSimpleName() + " onDestroy");
        destroyMediaPlayer();
    }


    private void mediaPlayerPlay() {
        try {
            if (!playSdcard) {
                if (mMedaiList.size() == 0) {
                    getRawMediaResource();
                }
                mMediaPlayer.setDataSource(mMedaiList.get(currentCursor).getFileDescriptor(), mMedaiList.get(currentCursor).getStartOffset(),
                        mMedaiList.get(currentCursor).getLength());
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } else {
                if (sdCardMusic.size() == 0) {
                    getSdMusic();
                    if (sdCardMusic.size() == 0) {
                        playSdcard = false;
                        currentCursor = 0;//如果SD下音乐文件被删除,则继续播放raw下音乐
                        servicePlay();
                    }
                }
                mMediaPlayer.setDataSource(sdCardMusic.get(currentCursor).getAbsolutePath());
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            }
            isPause = false;
            if (playSdcard) {
                LogUtil.e("mdiaplayer play sdFile  :" + sdCardMusic.get(currentCursor).toString());
            } else {
                LogUtil.e("mdiaplayer play rawFile :" + mMedaiList.get(currentCursor).getFileDescriptor().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
            currentCursor=0;
            playSdcard=false;
            mediaPlayerPlay();

        }
    }


    /**
     * 开始播放
     *
     * @return
     */
    private boolean servicePlay() {
        boolean flag = false;
        LogUtil.e("service play :" + currentCursor + ",isPause:" + isPause + ",mediaplayer object:" + mMediaPlayer);
        try {
            if (isPause && null != mMediaPlayer) {
                mMediaPlayer.start();
            } else {
                if (null == mMediaPlayer) {
                    mMediaPlayer = new MediaPlayer();
                }
                mMediaPlayer.reset();
                mediaPlayerPlay();
            }
            isPause = false;
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            mMediaPlayer.reset();
            currentCursor = 0;

            mediaPlayerPlay();

        } finally {
            LogUtil.e("media player play:" + flag);
        }

        return flag;
    }

    /**
     * 播放暂停
     *
     * @return
     */
    private boolean serviceStop() {
        boolean flag = false;
        if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            flag = true;
            isPause = false;
        } else {
            flag = false;
        }
        LogUtil.e("media player stop :" + flag);
        return flag;
    }


    private boolean servicePause() {
        boolean flag = false;
        if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPause = true;
            flag = true;
        } else {
            flag = false;
        }
        LogUtil.e("media player pause:" + flag);
        return flag;
    }

    private boolean servicePlayOther(String string) {
        return false;
    }

    /**
     * service close volume
     */
    private void serviceCloseVolume() {
        isSilent = true;
        mMediaPlayer.setVolume(0,0);

//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        HermesEventBus.getDefault().post(new BackgroudMusicEvent.MusicVoiceEvent(false));


    }

    /**
     * open volume
     */
    private void serviceOpenVolume() {
        if (isSilent) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            LogUtil.e("voice volume :" + maxVolume);
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume / 2, 0);
            curVolume = maxVolume / 2;
            isSilent = false;
            mMediaPlayer.setVolume(1,1);

            HermesEventBus.getDefault().post(new BackgroudMusicEvent.MusicVoiceEvent(true));

        }

    }

    /**
     * 调节音量
     *
     * @param volume
     */
    private void adjustVolume(int volume) {
        curVolume = volume;
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);//tempVolume:音量绝对值
    }


    private class MediaBinder extends IMediaInterface.Stub {


        private BgMediaPlayerServce getService() {
            return BgMediaPlayerServce.this;
        }


        @Override
        public boolean play() throws RemoteException {

            return servicePlay();
        }

        @Override
        public boolean stop() throws RemoteException {
            return serviceStop();
        }

        @Override
        public boolean pause() throws RemoteException {
            return servicePause();
        }

        @Override
        public boolean playOther(String mediaName) throws RemoteException {
            return servicePlayOther(mediaName);
        }

        /**
         * 静音
         *
         * @throws RemoteException
         */
        @Override
        public void closeVolume() throws RemoteException {
            getService().serviceCloseVolume();
        }


        /**
         * 开启
         *
         * @throws RemoteException
         */
        @Override
        public void openVolume() throws RemoteException {

            getService().serviceOpenVolume();
        }

        /**
         * 调节音量
         *
         * @throws RemoteException
         */
        @Override
        public void adjustVolume(int volume) throws RemoteException {
            getService().adjustVolume(volume);
        }

        @Override
        public boolean isPlaying() throws RemoteException {
            if (null != mMediaPlayer && mMediaPlayer.isPlaying()) {
                return true;
            }
            return false;
        }

        @Override
        public boolean isPause() throws RemoteException {
            return isPause;
        }

        @Override
        public boolean isSilent() throws RemoteException {
            return getService().isSilent;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }


    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        ++currentCursor;
        if (playSdcard) {
            if (currentCursor > sdCardMusic.size() - 1) {
                currentCursor = 0;
            }
        } else {
            if (currentCursor > mMedaiList.size() - 1) {
                currentCursor = 0;
            }
        }
        BgMediaPlayerServce.this.servicePlay();
        LogUtil.e("mediaplayer completion play,ready for next music and current cursor:" + currentCursor);
    }


    @Override
    public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
        LogUtil.e("media player error :" + i + "," + i1);
        return false;
    }
}