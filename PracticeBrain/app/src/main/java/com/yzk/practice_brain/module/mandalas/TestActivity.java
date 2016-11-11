package com.yzk.practice_brain.module.mandalas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.yzk.practice_brain.R;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        initView();
    }

    private void initView() {
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                switch(action){

                    case MotionEvent.ACTION_DOWN:

                        int x= (int) motionEvent.getX();
                        int y= (int) motionEvent.getY();
                        int color = bitmap.getPixel(x, y);

                        int r = Color.red(color);
                        int g = Color.green(color);
                        int b = Color.blue(color);
                        int a = Color.alpha(color);
                        int rgb = Color.rgb(r, g, b);

                        Log.e("TAG",x+":"+y+":"+ Integer.toHexString(rgb));

                        break;
                }


                return true;
            }
        });
    }
}
