// Decompiled by JEB v1.5.201508100 - https://www.pnfsoftware.com

package com.yzk.practice_brain.module.mandalas;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yzk.practice_brain.R;


public class AppRater extends View {
    private static final String APP_PNAME = "com.mandalas.manadalas.coloring";
    private static final String APP_TITLE = "Manadalas Coloring";
    private static final int DAYS_UNTIL_PROMPT = 3;
    private static final int LAUNCHES_UNTIL_PROMPT = 5;
    private final Context context;



    public AppRater(Context context) {
        super(context);
        this.context = context;
    }

    public void app_launched(Context mContext) {
        long v9 = 0;
        SharedPreferences v4 = mContext.getSharedPreferences("apprater", 0);
        if(!v4.getBoolean("dontshowagain", false)) {
            SharedPreferences.Editor v1 = v4.edit();
            long v2 = v4.getLong("launch_count", v9) + 1;
            v1.putLong("launch_count", v2);
            Long v0 = Long.valueOf(v4.getLong("date_firstlaunch", v9));
            if(v0.longValue() == v9) {
                v0 = Long.valueOf(System.currentTimeMillis());
                v1.putLong("date_firstlaunch", v0.longValue());
            }

            if(v2 >= 5 && System.currentTimeMillis() >= v0.longValue() + 259200000) {
                this.showRateDialog(mContext, v1);
            }

            v1.commit();
        }
    }

    public void showRateDialog(Context mContext, final SharedPreferences.Editor editor) {
        final Dialog v3 = new Dialog(mContext);
        v3.setTitle(String.valueOf(mContext.getResources().getString(R.string.rate)) + " " + "Manadalas Coloring");
        LinearLayout v4 = new LinearLayout(mContext);
        v4.setOrientation(LinearLayout.VERTICAL);
        TextView v5 = new TextView(mContext);
        v5.setText(String.valueOf(mContext.getResources().getString(R.string.ifenjoy)) + " " + "Manadalas Coloring"
                 + " " + mContext.getResources().getString(R.string.pleaserateus));
        v5.setWidth(240);
        v5.setPadding(4, 0, 4, 10);
        v4.addView(((View)v5));
        Button v0 = new Button(mContext);
        v0.setText(String.valueOf(mContext.getResources().getString(R.string.rate)) + " " + "Manadalas Coloring");
        v0.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.mandalas.manadalas.coloring")));
                v3.dismiss();
            }
        });
        v4.addView(((View)v0));
        Button v1 = new Button(mContext);
        v1.setText(mContext.getResources().getString(R.string.recuerdame));
        v1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                v3.dismiss();
            }
        });
        v4.addView(((View)v1));
        Button v2 = new Button(mContext);
        v2.setText("新增字符串");
        v2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(editor != null) {
                    editor.putBoolean("dontshowagain", true);
                    editor.commit();
                }

                v3.dismiss();
            }
        });
        v4.addView(((View)v2));
        v3.setContentView(((View)v4));
        v3.show();
    }
}

