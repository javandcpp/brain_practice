package com.yzk.practice_brain.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.application.GlobalApplication;
import com.yzk.practice_brain.base.BaseFragmentActivity;

import butterknife.OnClick;

public class MainActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void uIViewInit() {


    }


    @OnClick({R.id.rlone, R.id.rltwo, R.id.rlthree, R.id.rlfour,R.id.play,R.id.pause,R.id.stop,R.id.closeVolume,R.id.openVolume})
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
            case R.id.play:
                try {
                    GlobalApplication.instance.getiMediaInterface().play();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.stop:
                try {
                    GlobalApplication.instance.getiMediaInterface().stop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.pause:
                try {
                    GlobalApplication.instance.getiMediaInterface().pause();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.closeVolume:
                try {
                    GlobalApplication.instance.getiMediaInterface().closeVolume();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.openVolume:
                try {
                    GlobalApplication.instance.getiMediaInterface().openVolume();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
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
