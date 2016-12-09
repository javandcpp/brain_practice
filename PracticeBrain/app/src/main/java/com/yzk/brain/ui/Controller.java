package com.yzk.brain.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.yzk.brain.R;
import com.yzk.brain.application.GlobalApplication;
import com.yzk.brain.setting.Setting;
import com.yzk.brain.utils.SizeUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by android on 11/28/16.
 */

public class Controller extends LinearLayout {
    @Bind(R.id.voiceable)
    ImageButton voiceable;

    @Bind(R.id.musicable)
    ImageButton musicable;

    @Bind(R.id.play)
    ImageButton play;

    @Bind(R.id.retry)
    ImageButton retry;

    @Bind(R.id.back)
    ImageButton back;

    @Bind(R.id.rootView)
    LinearLayout rootView;
    private int orientation;
    private ControllerCallBack mControllerCall;
    private boolean retryable;
    private boolean music;

    @OnClick({R.id.retry, R.id.back, R.id.play})
    public void clickEvent(View view) {
        if (null != mControllerCall) {
            mControllerCall.controll(view);
        }
    }


    @OnClick({R.id.musicable, R.id.voiceable})
    public void clickOne(View view) {

        switch (view.getId()) {
            case R.id.musicable:
                try {
                    if (GlobalApplication.instance.getiMediaInterface().isSilent()) {
                        view.setSelected(false);
                        GlobalApplication.instance.getiMediaInterface().openVolume();
                    } else {
                        view.setSelected(true);
                        GlobalApplication.instance.getiMediaInterface().closeVolume();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.voiceable:
                int systemKeyBoardVoice = Setting.getVoice();
                if (0 == systemKeyBoardVoice) {
                    view.setSelected(false);
                    Setting.setSystemKeyBoardVoice(1);
                } else {
                    view.setSelected(true);
                    Setting.setSystemKeyBoardVoice(0);
                }
                break;
        }
    }


    public Controller(Context context) {
        super(context);
        initView();
    }

    public Controller(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseStyle(context, attrs);
        initView();
    }

    private void initView() {


        if (1 == orientation) {//横向
            LayoutInflater.from(getContext()).inflate(R.layout.layout_controller, this, true);

        } else {//纵向
            LayoutInflater.from(getContext()).inflate(R.layout.layout_controller_vertical, this, true);

        }
        ButterKnife.bind(this);

        if (!retryable) {
            retry.setVisibility(GONE);
                ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            if (1 == orientation) {
                layoutParams.width= SizeUtils.dp2px(getContext(),160);
                rootView.setLayoutParams(layoutParams);
            } else {
                layoutParams.height= SizeUtils.dp2px(getContext(),160);
                rootView.setLayoutParams(layoutParams);
            }

        }else{
            retry.setVisibility(VISIBLE);
        }

        if (!music) {
            musicable.setVisibility(GONE);
            ViewGroup.LayoutParams layoutParams = rootView.getLayoutParams();
            if (1 == orientation) {
                layoutParams.width= SizeUtils.dp2px(getContext(),160);
                rootView.setLayoutParams(layoutParams);
            } else {
                layoutParams.height= SizeUtils.dp2px(getContext(),160);
                rootView.setLayoutParams(layoutParams);
            }

        }else{
            musicable.setVisibility(VISIBLE);
        }




        try {
            if (GlobalApplication.instance.getiMediaInterface().isPlaying()) {
                play.setSelected(true);
            } else {
                play.setSelected(false);
            }


            if (GlobalApplication.instance.getiMediaInterface().isSilent()) {
                musicable.setSelected(true);
            } else {
                musicable.setSelected(false);
            }

            int systemKeyBoardVoice = Setting.getVoice();
            if (0 == systemKeyBoardVoice) {
                voiceable.setSelected(true);
            } else {
                voiceable.setSelected(false);
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Controller);
            orientation = ta.getInteger(R.styleable.Controller_orientation, -1);
            retryable = ta.getBoolean(R.styleable.Controller_retry, true);
            music=ta.getBoolean(R.styleable.Controller_music,true);
            ta.recycle();
        }
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ButterKnife.unbind(this);
    }

    public interface ControllerCallBack {
        void controll(View view);
    }

    public void setClickCallBack(ControllerCallBack controllerBack) {
        this.mControllerCall = controllerBack;
    }

}
