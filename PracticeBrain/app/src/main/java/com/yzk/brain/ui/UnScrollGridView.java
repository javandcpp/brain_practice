package com.yzk.brain.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

/**
 * Created by android on 11/23/16.
 */

public class UnScrollGridView extends GridView {

    public UnScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            return true;//true:禁止滚动
        }
        return super.dispatchTouchEvent(ev);
    }
}
