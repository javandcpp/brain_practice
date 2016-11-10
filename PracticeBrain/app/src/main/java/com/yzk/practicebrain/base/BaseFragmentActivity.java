package com.yzk.practicebrain.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yzk.practicebrain.R;
import com.yzk.practicebrain.stack.ActivityStack;


/**
 * base activity
 */

public abstract class BaseFragmentActivity extends FragmentActivity {
    protected Handler mHandler = new Handler();
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.add(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            int greenColor = getResources().getColor(R.color.green_00AB9B);
            tintManager.setTintColor(greenColor);
        }

        activityCreate();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            parentView.setFitsSystemWindows(true);
        }
        try {
            uIViewInit();
        } catch (Exception e) {

        }
        uIViewDataApply();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        onWindowHasFocus(hasFocus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityDestroy();
        ActivityStack.remove(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * activity view 初始化
     */
    protected abstract void uIViewInit();

    /**
     * activity view 初始化完成调用
     */
    protected abstract void uIViewDataApply();

    /**
     * activity 创建
     */
    protected void activityCreate(){};

    /**
     * activity 销毁
     */
    protected void activityDestroy(){};

    /**
     * window 是否获取焦点
     */

    public  void onWindowHasFocus(boolean hasFocus){};


}
