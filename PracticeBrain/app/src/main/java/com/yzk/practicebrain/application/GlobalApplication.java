package com.yzk.practicebrain.application;


import android.os.Handler;

import com.yzk.practicebrain.stack.ActivityStack;

/**
 * Created by android on 9/10/15.
 */
public class GlobalApplication extends BaseApplication {


    public static GlobalApplication instance;
    public static Handler mHandler=new Handler();


    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

    }

    /**
     * init
     */
    private void init() {

    }

    public void exitApp() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityStack.finishProgram();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        }, 200);
    }
}
