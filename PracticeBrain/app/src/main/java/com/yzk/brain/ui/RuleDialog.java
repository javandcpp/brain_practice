package com.yzk.brain.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.inter.ResponseStringDataListener;
import com.yzk.brain.R;
import com.yzk.brain.application.ApplicationController;
import com.yzk.brain.bean.HelpResult;
import com.yzk.brain.bean.RuleResult;
import com.yzk.brain.config.Config;
import com.yzk.brain.log.LogUtil;
import com.yzk.brain.network.HttpRequestUtil;
import com.yzk.brain.utils.NetworkUtils;
import com.yzk.brain.utils.ParseJson;

/**
 * Created by android on 12/5/16.
 */

public class RuleDialog extends Dialog {

    public RuleDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public RuleDialog(Context context) {
        super(context);
    }


    public static class Builder implements ResponseStringDataListener {


        protected static final int REQUEST_RULE = 0x1;
        protected static final int REQUEST_HELP = 0x2;
        protected Context mContext;
        protected String mParmas;
        protected TextView tvRuleTxt;
        protected CircularProgressView circularProgressView;

        public Builder(Context context, String params) {
            this.mContext = context;
            this.mParmas = params;
        }

        public Builder(Context context) {
            this.mContext = context;
        }


        public void getDataFromNet() {
            if (NetworkUtils.isConnected(mContext)) {
                circularProgressView.setVisibility(View.VISIBLE);
                HttpRequestUtil.HttpRequestByGet(Config.RULE + mParmas, this, REQUEST_RULE);
            } else {
                Toast.makeText(mContext, R.string.net_error, Toast.LENGTH_SHORT).show();
            }
        }


        public RuleDialog create() {
            final RuleDialog ruleDialog = new RuleDialog(mContext, R.style.Dialog);
            View contentView = LayoutInflater.from(mContext).inflate(R.layout.dialog_rule_layout, null);
            ruleDialog.setContentView(contentView);
            tvRuleTxt = (TextView) contentView.findViewById(R.id.tvRuleTxt);
            circularProgressView = (CircularProgressView) contentView.findViewById(R.id.loading);
            Button button = (Button) contentView.findViewById(R.id.btClose);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != ruleDialog && ruleDialog.isShowing()) {
                        tvRuleTxt.setText("");
                        ruleDialog.dismiss();
                        ApplicationController.getInstance().cancelPendingRequests(this);
                    }
                }
            });

            getDataFromNet();

            return ruleDialog;
        }


        @Override
        public void onDataDelivered(int taskId, String data) {
            switch (taskId) {
                case REQUEST_RULE:
                    circularProgressView.setVisibility(View.GONE);
                    RuleResult ruleResult = ParseJson.parseJson(data, RuleResult.class);
                    if (null != ruleResult && "0".equals(ruleResult.code)) {
                        LogUtil.d(ruleResult.data.typeRulesContent);
                        tvRuleTxt.setText(ruleResult.data.typeRulesContent);
                    }
                    break;

                case REQUEST_HELP:
                    circularProgressView.setVisibility(View.GONE);
                    HelpResult helpResult = ParseJson.parseJson(data, HelpResult.class);
                    if (null != helpResult && "0".equals(helpResult.code)) {
                        LogUtil.d(helpResult.data.helpContent);
                        tvRuleTxt.setText(helpResult.data.helpContent);
                    }

                    break;
            }
        }

        @Override
        public void onErrorHappened(int taskId, String errorCode, String errorMessage) {
            circularProgressView.setVisibility(View.GONE);
        }


    }
}
