// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.practice_brain.module.mandalas;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.yzk.practice_brain.R;


public class ColorPicker extends Dialog {
    class ColorPickerView extends View {
        private int mCurrentColor;
        private float mCurrentHue;
        private int mCurrentX;
        private int mCurrentY;
        private int mDefaultColor;
        private final int[] mHueBarColors;
        private OnColorChangedListener mListener;
        private int[] mMainColors;
        private Paint mPaint;

        ColorPickerView(Context c, OnColorChangedListener l, int color, int defaultColor) {
            super(c);
            int v8 = 255;
            float v7 = 256f;
            float v6 = 6f;
            this.mCurrentHue = 0f;
            this.mCurrentX = 0;
            this.mCurrentY = 0;
            this.mHueBarColors = new int[258];
            this.mMainColors = new int[65536];
            this.mListener = l;
            this.mDefaultColor = defaultColor;
            float[] v0 = new float[3];
            Color.colorToHSV(color, v0);
            this.mCurrentHue = v0[0];
            this.updateMainColors();
            this.mCurrentColor = color;
            int v2 = 0;
            float v1;
            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(v8, 0, ((int)v1));
                ++v2;
            }

            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(255 - (((int)v1)), 0, v8);
                ++v2;
            }

            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(0, ((int)v1), v8);
                ++v2;
            }

            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(0, v8, 255 - (((int)v1)));
                ++v2;
            }

            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(((int)v1), v8, 0);
                ++v2;
            }

            for(v1 = 0f; v1 < v7; v1 += v6) {
                this.mHueBarColors[v2] = Color.rgb(v8, 255 - (((int)v1)), 0);
                ++v2;
            }

            this.mPaint = new Paint(1);
            this.mPaint.setTextAlign(Paint.Align.CENTER);
            this.mPaint.setTextSize(12f);
        }

        private int getCurrentMainColor() {
            int v3;
            int v8 = 255;
            float v6 = 256f;
            float v5 = 6f;
            int v2 = 255 - (((int)(this.mCurrentHue * 255f / 360f)));
            int v1 = 0;
            float v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    break;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(v8, 0, ((int)v0));
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

            v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    break;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(255 - (((int)v0)), 0, v8);
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

            v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    break;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(0, ((int)v0), v8);
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

            v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    break;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(0, v8, 255 - (((int)v0)));
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

            v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    break;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(((int)v0), v8, 0);
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

            v0 = 0f;
            while(true) {
                if(v0 >= v6) {
                    return -65536;
                }
                else if(v1 == v2) {
                    v3 = Color.rgb(v8, 255 - (((int)v0)), 0);
                }
                else {
                    ++v1;
                    v0 += v5;
                    continue;
                }

                return v3;
            }

//            return -65536;
        }

        protected void onDraw(Canvas canvas) {
            int v14 = 255 - (((int)(this.mCurrentHue * 255f / 360f)));
            int v15;
            for(v15 = 0; v15 < 256; ++v15) {
                if(v14 != v15) {
                    this.mPaint.setColor(this.mHueBarColors[v15]);
                    this.mPaint.setStrokeWidth(1f);
                }
                else {
                    this.mPaint.setColor(Color.parseColor("#FF000000"));
                    this.mPaint.setStrokeWidth(3f);
                }

                canvas.drawLine(((float)(v15 + 10)), 0f, ((float)(v15 + 10)), 40f, this.mPaint);
            }

            for(v15 = 0; v15 < 256; ++v15) {
                this.mPaint.setShader(new LinearGradient(0f, 50f, 0f, 306f, new int[]{this.mMainColors[
                        v15], -16777216}, null, Shader.TileMode.REPEAT));
                canvas.drawLine(((float)(v15 + 10)), 50f, ((float)(v15 + 10)), 306f, this.mPaint);
            }

            this.mPaint.setShader(null);
            if(this.mCurrentX != 0 && this.mCurrentY != 0) {
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setColor(Color.parseColor("#FF000000"));
                canvas.drawCircle(((float)this.mCurrentX), ((float)this.mCurrentY), 10f, this.mPaint);
            }

            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mCurrentColor);
            canvas.drawRect(10f, 316f, 138f, 356f, this.mPaint);
            if(Color.red(this.mCurrentColor) + Color.green(this.mCurrentColor) + Color.blue(this.mCurrentColor)
                     < 384) {
                this.mPaint.setColor(-1);
            }
            else {
                this.mPaint.setColor(Color.parseColor("#FF000000"));
            }

            canvas.drawText(this.getResources().getString(R.string.eligeColor), 74f, 340f, this.mPaint);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mPaint.setColor(this.mDefaultColor);
            canvas.drawRect(138f, 316f, 266f, 356f, this.mPaint);
            if(Color.red(this.mDefaultColor) + Color.green(this.mDefaultColor) + Color.blue(this.mDefaultColor)
                     < 384) {
                this.mPaint.setColor(-1);
            }
            else {
                this.mPaint.setColor(Color.parseColor("#FF000000"));
            }

            canvas.drawText("Pick", 202f, 340f, this.mPaint);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            this.setMeasuredDimension(276, 366);
        }

        public boolean onTouchEvent(MotionEvent event) {
            int v0;
            boolean v5;
            float v11 = 316f;
            float v10 = 255f;
            float v9 = 138f;
            float v8 = 266f;
            float v7 = 10f;
            if(event.getAction() != 0) {
                v5 = true;
            }
            else {
                float v3 = event.getX();
                float v4 = event.getY();
                if(v3 > v7 && v3 < v8 && v4 > 0f && v4 < 40f) {
                    this.mCurrentHue = (v10 - v3) * 360f / v10;
                    this.updateMainColors();
                    int v1 = this.mCurrentX - 10;
                    int v2 = this.mCurrentY - 60;
                    v0 = (v2 - 1) * 256 + v1;
                    if(v0 > 0 && v0 < this.mMainColors.length) {
                        this.mCurrentColor = this.mMainColors[(v2 - 1) * 256 + v1];
                    }

                    this.invalidate();
                }

                if(v3 > v7 && v3 < v8 && v4 > 50f && v4 < 306f) {
                    this.mCurrentX = ((int)v3);
                    this.mCurrentY = ((int)v4);
                    v0 = (this.mCurrentY - 61) * 256 + (this.mCurrentX - 10);
                    if(v0 > 0 && v0 < this.mMainColors.length) {
                        this.mCurrentColor = this.mMainColors[v0];
                        this.invalidate();
                    }
                }

                if(v3 > v7 && v3 < v9 && v4 > v11 && v4 < 356f) {
                    this.mListener.colorChanged("", this.mCurrentColor);
                }

                if(v3 > v9 && v3 < v8 && v4 > v11 && v4 < 356f) {
                    this.mListener.colorChanged("", this.mDefaultColor);
                }

                v5 = true;
            }

            return v5;
        }

        private void updateMainColors() {
            int v10 = 256;
            int v1 = this.getCurrentMainColor();
            int v0 = 0;
            int[] v2 = new int[v10];
            int v4;
            for(v4 = 0; v4 < v10; ++v4) {
                int v3;
                for(v3 = 0; v3 < v10; ++v3) {
                    if(v4 == 0) {
                        this.mMainColors[v0] = Color.rgb(255 - (255 - Color.red(v1)) * v3 / 255, 255
                                 - (255 - Color.green(v1)) * v3 / 255, 255 - (255 - Color.blue(v1)) * 
                                v3 / 255);
                        v2[v3] = this.mMainColors[v0];
                    }
                    else {
                        this.mMainColors[v0] = Color.rgb((255 - v4) * Color.red(v2[v3]) / 255, (255 - 
                                v4) * Color.green(v2[v3]) / 255, (255 - v4) * Color.blue(v2[v3]) / 255);
                    }

                    ++v0;
                }
            }
        }
    }

    public interface OnColorChangedListener {
        void colorChanged(String arg1, int arg2);
    }

    private int mDefaultColor;
    private int mInitialColor;
    private String mKey;
    private OnColorChangedListener mListener;

    public ColorPicker(Context context, OnColorChangedListener listener, String key, int initialColor, 
            int defaultColor) {
        super(context);
        this.mListener = listener;
        this.mKey = key;
        this.mInitialColor = initialColor;
        this.mDefaultColor = defaultColor;
    }

//    static OnColorChangedListener access$0(ColorPicker arg1) {
//        return arg1.mListener;
//    }
//
//    static String access$1(ColorPicker arg1) {
//        return arg1.mKey;
//    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(new ColorPickerView(this.getContext(), new OnColorChangedListener() {
            public void colorChanged(String key, int color) {
                ColorPicker.this.mListener.colorChanged(ColorPicker.this.mKey, color);
                ColorPicker.this.dismiss();
            }
        }, this.mInitialColor, this.mDefaultColor));
        this.setTitle(R.string.pickcolor);
    }
}

