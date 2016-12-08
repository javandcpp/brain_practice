package com.yzk.brain.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yzk.brain.R;

/**
 * Created by android on 12/5/16.
 */

public class ProgressDialog extends Dialog {

    private TextView tvScore;

    public ProgressDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public ProgressDialog(Context context) {
        super(context);
    }


    public static class Builder implements View.OnClickListener {


        private final Context mContext;
        private int mStatus;
        private TextView tvScore;
        private int mScore;
        private int mVisible;
        private ProgressBar progressBar;
        private BackListener back;

        public Builder(Context context) {
            this.mContext = context;
        }
        public Builder setBackClickListener(BackListener b){
            this.back=b;
            return this;
        }


        public ProgressDialog create() {
            final ProgressDialog ruleDialog = new ProgressDialog(mContext, R.style.FullScreenDialog);
            ruleDialog.setCancelable(false);
            ruleDialog.setCanceledOnTouchOutside(false);
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.progress_dialog_layout, null);
            progressBar = (ProgressBar) contentView.findViewById(R.id.progressbar);
            Button back= (Button) contentView.findViewById(R.id.back);
            back.setOnClickListener(this);
            ruleDialog.setContentView(contentView);

            return ruleDialog;
        }

        public void setProgress(int progress){
            progressBar.setProgress(progress);
        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.back:
                    if (null!=back){
                        back.back();
                        back=null;
                    }
                    break;
            }
        }

        public interface BackListener{
            void back();
        }
    }
}
