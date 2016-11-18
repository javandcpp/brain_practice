package com.yzk.practice_brain.application;


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;

import com.yzk.practice_brain.IMediaInterface;
import com.yzk.practice_brain.log.LogUtil;
import com.yzk.practice_brain.service.BgMediaPlayerServce;

/**
 * Created by android on 9/10/15.
 */
public class GlobalApplication extends BaseApplication {


    public static GlobalApplication instance;
    public static Handler mHandler=new Handler();


    private ServiceConnection serviceConnection;
    private Intent intent;
    private IMediaInterface iMediaInterface;


    /**
     * 获取媒体控制
     * @return
     */
    public IMediaInterface getiMediaInterface(){
        return iMediaInterface;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        serviceConnection=new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iMediaInterface = (IMediaInterface.Stub.asInterface(iBinder));
                LogUtil.e("service Connected");
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                iMediaInterface=null;
            }
        };
       boolean statu=startService();

        LogUtil.d("service status:"+ statu);



    }


    public boolean startService(){
        intent = new Intent(GlobalApplication.instance, BgMediaPlayerServce.class);
        startService(intent);
       return bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    };


    /**
     * init
     */
    private void init() {

    }

    public void exitApp() {
        unbindService(serviceConnection);
        stopService(intent);
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
