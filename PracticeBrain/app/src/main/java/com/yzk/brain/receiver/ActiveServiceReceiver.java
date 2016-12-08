package com.yzk.brain.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.log.LogUtil;
import com.yzk.brain.service.BgMediaPlayerServce;
import com.yzk.brain.utils.AppUtils;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by android on 11/18/16.
 */

public class ActiveServiceReceiver extends BroadcastReceiver {

    private boolean isServiceRunning;

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (null != action && (action.equals(Intent.ACTION_TIME_TICK) || action.equals(Intent.ACTION_BOOT_COMPLETED))) {

            Observable.just("").observeOn(Schedulers.io()).subscribeOn(Schedulers.io()).subscribe(new Subscriber<String>() {
                @Override
                public void onCompleted() {

                    if (!isServiceRunning) {
//                        GlobalApplication.instance.startBgPlayerService();
                        LogUtil.d(BgMediaPlayerServce.class.getCanonicalName() + " start");
                    } else {
                        LogUtil.d(BgMediaPlayerServce.class.getCanonicalName() + " is running");
                    }
                }

                @Override
                public void onError(Throwable e) {
                    LogUtil.e(e.toString());
                }

                @Override
                public void onNext(String s) {
                    isServiceRunning=AppUtils.isServiceRunning(GlobalApplication.instance, BgMediaPlayerServce.class.getSimpleName());
                }
            });
        }
    }
}
