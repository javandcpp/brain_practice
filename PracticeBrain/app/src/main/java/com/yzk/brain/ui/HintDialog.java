package com.yzk.brain.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yzk.brain.R;
import com.yzk.brain.activity.TwentyOneEnterActivity;
import com.yzk.brain.activity.TwentyOnePracticeActivity;

/**
 * Created by android on 12/5/16.
 */

public class HintDialog extends Dialog {

    private TextView tvScore;

    public HintDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public HintDialog(Context context) {
        super(context);
    }


    public static class Builder {


        private final Context mContext;
        private int mStatus;
        private TextView tvScore;
        private int mScore;
        private int mVisible;
        private boolean mTest;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setTvScore(int score) {
            this.mScore = score;

            return this;
        }

        public Builder setScoreVisiblle(int visiblle) {
            this.mVisible = visiblle;
            return this;
        }

        public Builder setTest(boolean isTest) {
            this.mTest = isTest;
            return this;
        }


        public Builder setStatus(int status) {
            this.mStatus = status;
            return this;
        }

        public HintDialog create() {
            final HintDialog ruleDialog = new HintDialog(mContext, R.style.Dialog);
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_hint_layout, null);
            ruleDialog.setContentView(contentView);
            ruleDialog.setCancelable(false);
            ruleDialog.setCanceledOnTouchOutside(false);
            tvScore = (TextView) contentView.findViewById(R.id.tvScore);
            Button button = (Button) contentView.findViewById(R.id.btClose);
            RelativeLayout root = (RelativeLayout) contentView.findViewById(R.id.root);
            if (1 == mVisible) {
                tvScore.setVisibility(View.VISIBLE);
            } else {
                tvScore.setVisibility(View.GONE);
            }

            if (1 == mStatus) {
                tvScore.setText("获得" + String.valueOf(mScore) + "分");
                button.setBackgroundResource(R.drawable.home_white);
                root.setBackgroundResource(R.drawable.home_victory);
            } else {
                tvScore.setText("");
                button.setBackgroundResource(R.drawable.home_grey);
                root.setBackgroundResource(R.drawable.home_go_burst);
            }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != ruleDialog && ruleDialog.isShowing()) {
                        tvScore.setText("");
                        mVisible=0;
                        mStatus=0;

                        Intent intent = null;
                        if (mTest) {
                            intent = new Intent(mContext, TwentyOnePracticeActivity.class);
                        } else {
                            intent = new Intent(mContext, TwentyOneEnterActivity.class);
                        }
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        mContext.startActivity(intent);
                        ruleDialog.dismiss();
                    }
                }
            });

            return ruleDialog;
        }


    }
}
