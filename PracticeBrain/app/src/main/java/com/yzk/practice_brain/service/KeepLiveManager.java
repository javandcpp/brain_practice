package com.yzk.practice_brain.service;

import android.app.Notification;
import android.app.Service;
import android.os.Build;

import com.yzk.practice_brain.log.LogUtil;

/**
 * Created by android on 12/2/16.
 */

public class KeepLiveManager {
    private KeepLiveManager(){}
    public static final KeepLiveManager instance=new KeepLiveManager();
    public static KeepLiveManager getInstance(){
        return instance;
    }

    public void setForeground(final Service keepliveService,final Service innerService){
        final int forgroundPushId=1;
        LogUtil.e("setForeground,keeplive:"+keepliveService+",inner service:"+innerService);
        if (null!=keepliveService){

            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN_MR2){
                keepliveService.startForeground(forgroundPushId,new Notification());
            }else{
                keepliveService.startForeground(forgroundPushId,new Notification());
                if (null!=innerService){
                    innerService.startForeground(forgroundPushId,new Notification());
                    innerService.stopSelf();
                }
            }
        }

    }

}
