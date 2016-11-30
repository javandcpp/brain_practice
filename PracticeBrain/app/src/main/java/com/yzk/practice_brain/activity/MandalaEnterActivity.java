package com.yzk.practice_brain.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.yzk.practice_brain.R;
import com.yzk.practice_brain.base.BaseFragmentActivity;
import com.yzk.practice_brain.module.mandalas.Mandalas2Activity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 11/24/16.
 */

public class MandalaEnterActivity extends BaseFragmentActivity {

    @Bind(R.id.imagen2)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mandalas_enter);

//        imageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                switch (motionEvent.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
//                        ImageUtils.getImageColorValue(bitmap,(int) motionEvent.getX(),(int) motionEvent.getY());
//                        break;
//                }



//                return true;
//            }
//        });
    }
    @OnClick({R.id.btnStart})
    public void click(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.btnStart:
                Intent v1 = new Intent(this, Mandalas2Activity
                        .class);
//                    v1.putExtra("mandala-tag", String.valueOf(v2));
                v1.putExtra("mandala-tag", "myimage");
                startActivity(v1);
                break;
        }
    }


    @Override
    protected void uIViewInit() {

    }

    @Override
    protected void uIViewDataApply() {

    }


}
