package com.yzk.brain.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.yzk.brain.R;

/**
 * Created by android on 11/28/16.
 */

public class DialogFactory {

    public static Dialog createDialog(Context context, View view) {
        Dialog dialog = new Dialog(context, R.style.Dialog);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));
        return dialog;

    }

    public static void dismissDialog(Dialog dialog) {
        if (null != dialog && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


}
