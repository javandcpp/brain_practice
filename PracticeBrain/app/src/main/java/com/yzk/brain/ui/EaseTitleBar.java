package com.yzk.brain.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzk.brain.R;


/**
 * 标题栏
 *
 */
public class EaseTitleBar extends RelativeLayout{

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageButton rightImage;
    protected TextView titleView;
    protected RelativeLayout titleLayout;
    private TextView tvRight;
    private TextView tvLeft;

    public EaseTitleBar(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseTitleBar(Context context) {
        super(context);
        init(context, null);
    }
    
    private void init(Context context, AttributeSet attrs){
        LayoutInflater.from(context).inflate(R.layout.ease_widget_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        rightImage = (ImageButton) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        titleLayout = (RelativeLayout) findViewById(R.id.root);
        tvRight = (TextView) findViewById(R.id.titleBar_Right_tv);
        tvLeft = (TextView) findViewById(R.id.titleBar_Left_tv);
        parseStyle(context, attrs);
    }
    
    private void parseStyle(Context context, AttributeSet attrs){
        if(attrs != null){
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.EaseTitleBar);
            String title = ta.getString(R.styleable.EaseTitleBar_titleBarTitle);
            String rightText = ta.getString(R.styleable.EaseTitleBar_titleBarRightText);
            String leftText = ta.getString(R.styleable.EaseTitleBar_titleBarLeftText);
            if (null!=title) {
                titleView.setText(title);
            }
            if (null!=rightText) {
                tvRight.setText(rightText);
            }
            
            Drawable leftDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            }
            Drawable rightDrawable = ta.getDrawable(R.styleable.EaseTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setBackground(rightDrawable);
            }
        
            Drawable background = ta.getDrawable(R.styleable.EaseTitleBar_titleBarBackground);
            if(null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

            if (null!=leftText) {
                tvLeft.setText(leftText);
            }
            
            ta.recycle();
        }
    }
    
    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }
    
    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
    }
    
    public void setLeftLayoutClickListener(OnClickListener listener){
        leftLayout.setOnClickListener(listener);
    }
    
    public void setRightLayoutClickListener(OnClickListener listener){
        rightLayout.setOnClickListener(listener);
    }
    
    public void setLeftLayoutVisibility(int visibility){
        leftLayout.setVisibility(visibility);
    }
    
    public void setRightLayoutVisibility(int visibility){
        rightLayout.setVisibility(visibility);
    }
    
    public void setTitle(String title){
        titleView.setText(title);
    }
    
    public void setBackgroundColor(int color){
        titleLayout.setBackgroundColor(color);
    }
    
    public RelativeLayout getLeftLayout(){
        return leftLayout;
    }
    
    public RelativeLayout getRightLayout(){
        return rightLayout;
    }
    public TextView getTvRight(){
        return tvRight;
    }


}
