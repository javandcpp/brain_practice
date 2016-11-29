package com.yzk.practice_brain.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.busevent.BackgroudMusicEvent;
import com.yzk.practice_brain.constants.Constants;
import com.yzk.practice_brain.preference.PreferenceHelper;
import com.yzk.practice_brain.utils.SizeUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {


    @Bind(R.id.right_image)
    ImageButton rightImage;

    @Bind(R.id.right_layout)
    RelativeLayout relativeLayout;

    private boolean rightSelected;
    private static Handler mHanlder = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PreferenceHelper.writeInt(Constants.TWENTY_ONE, 1);
    }


    @Subscribe(threadMode=ThreadMode.MAIN)
    public void onEventMainThread(BackgroudMusicEvent.MusicVoiceEvent musicVoiceEvent){
        if (musicVoiceEvent.play){
            rightImage.setSelected(false);
        }else{
            rightImage.setSelected(true);
        }
    }


    @Override
    protected void uIViewInit() {
        ViewGroup.LayoutParams layoutParams = relativeLayout.getLayoutParams();
        layoutParams.width = SizeUtils.dp2px(this, 35);
        relativeLayout.setLayoutParams(layoutParams);
        rightImage.setSelected(rightSelected);

    }
//    R.id.play,R.id.pause,R.id.stop,R.id.closeVolume,R.id.openVolume

    @OnClick({R.id.rlone, R.id.rltwo, R.id.rlthree, R.id.rlfour, R.id.right_layout})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rlone:
                intent.setClass(this, TwentyOnePracticeActivity.class);
                startActivity(intent);
                break;
            case R.id.rltwo:
                break;
            case R.id.rlthree:
                break;
            case R.id.rlfour:
                intent.setClass(this, EducationNewsActivity.class);
                startActivity(intent);
                break;
            case R.id.right_layout:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isSilent()) {
                        GlobalApplication.instance.getiMediaInterface().openVolume();
                        rightImage.setSelected(false);
                    } else {
                        GlobalApplication.instance.getiMediaInterface().closeVolume();
                        rightImage.setSelected(true);
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
//            case R.id.play:
//                try {
//                    GlobalApplication.instance.getiMediaInterface().play();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.stop:
//                try {
//                    GlobalApplication.instance.getiMediaInterface().stop();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.pause:
//                try {
//                    GlobalApplication.instance.getiMediaInterface().pause();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//            case R.id.closeVolume:
//                try {
//                    GlobalApplication.instance.getiMediaInterface().closeVolume();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
//
//            case R.id.openVolume:
//                try {
//                    GlobalApplication.instance.getiMediaInterface().openVolume();
//                } catch (RemoteException e) {
//                    e.printStackTrace();
//                }
//                break;
        }


    }

    @Override
    protected void uIViewDataApply() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            showTips();
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showTips() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("提醒")
                .setMessage("是否退出程序")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        try {
                            GlobalApplication.instance.getiMediaInterface().stop();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        GlobalApplication.instance.exitApp();
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }

                }).setNegativeButton("取消",

                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        }).create(); // 创建对话框
        alertDialog.show(); // 显示对话框
    }
}
