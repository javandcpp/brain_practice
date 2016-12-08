package com.yzk.brain.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yzk.brain.R;
import com.yzk.brain.config.Config;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.utils.NetworkUtils;

/**
 * Created by android on 12/8/16.
 */

public class HelpDialog extends RuleDialog {
    public HelpDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public HelpDialog(Context context) {
        super(context);
    }


    public static class Builder extends RuleDialog.Builder{

        public Builder(Context context, String params) {
            super(context, params);
        }
        
        public Builder(Context context){
            super(context);
        }

        public HelpDialog create() {
            final HelpDialog helpDialog = new HelpDialog(mContext, R.style.Dialog);
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_rule_layout, null);
            helpDialog.setContentView(contentView);
            TextView title = (TextView) contentView.findViewById(R.id.tvTitle);
            title.setText("帮助说明");
            tvRuleTxt = (TextView) contentView.findViewById(R.id.tvRuleTxt);
            circularProgressView = (CircularProgressView) contentView.findViewById(R.id.loading);
            Button button = (Button) contentView.findViewById(R.id.btClose);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != helpDialog && helpDialog.isShowing()) {
                        tvRuleTxt.setText("");
                        helpDialog.dismiss();
                    }
                }
            });

            getDataFromNet();

            return helpDialog;
        }


        @Override
        public void getDataFromNet() {
            if (NetworkUtils.isConnected(mContext)) {
                circularProgressView.setVisibility(View.VISIBLE);
                HttpRequestUtil.HttpRequestByGet(Config.HELP_URL, this, REQUEST_HELP);
            } else {
                Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
            }
        }
    }



}
