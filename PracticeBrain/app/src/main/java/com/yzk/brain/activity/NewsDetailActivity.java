package com.yzk.brain.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yzk.brain.R;
import com.yzk.brain.base.BaseFragmentActivity;
import com.yzk.brain.bean.EducationResult;
import com.yzk.brain.utils.TimeUtils;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by android on 12/8/16.
 */

public class NewsDetailActivity extends BaseFragmentActivity {


    @Bind(R.id.tvTitle)
    TextView tvTitle;

    @Bind(R.id.tvTime)
    TextView tvTime;

    @Bind(R.id.tvContent)
    TextView tvContent;
    private EducationResult.EducationNews news;

    @OnClick(R.id.left_layout)
    public void click(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news = (EducationResult.EducationNews) getIntent().getSerializableExtra("news");
        setContentView(R.layout.activity_news_detail_layout);


    }

    @Override
    protected void uIViewInit() {
        if (null != news) {
            tvTitle.setText(news.eduCounseTitle);
            tvTime.setText(TimeUtils.milliseconds2String(news.createTime));
            tvContent.setText(news.eduCounseContent);
        }
    }

    @Override
    protected void uIViewDataApply() {

    }
}
