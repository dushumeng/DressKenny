package com.fcdream.dress.kenny.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

/**
 * Created by shmdu on 2017/9/2.
 */

public class MessageUtils {

    public static final int BUTTON_CONFIRM = 1;
    public static final int BUTTON_CANCEL = 2;


    /**
     * 显示toast
     *
     * @param context
     * @param text
     */
    public static void showToast(final Context context, final String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Toast toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            Looper.prepare();
            Toast toast = Toast.makeText(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Looper.loop();
        }
    }

    /**
     * 显示toast
     *
     * @param context
     * @param resId
     */
    public static void showToast(final Context context, final int resId) {
        showToast(context, context.getString(resId));
    }

    /**
     * 显示toast
     *
     * @param context
     * @param resId
     * @param formatArgs
     */
    public static void showToast(final Context context, final int resId, final Object... formatArgs) {
        showToast(context, context.getString(resId, formatArgs));
    }

    // private static class MyToast {
    //
    // private static final Toast toast =
    // Toast.makeText(FCDreamApplication.getInstance(), "", Toast.LENGTH_SHORT);
    //
    // public static void showToast(Context context, String text) {
    // try {
    // toast.setText(text);
    // toast.setDuration(Toast.LENGTH_SHORT);
    // toast.setGravity(Gravity.CENTER, 0, 0);
    // toast.show();
    // } catch (Throwable tr) {
    // tr.printStackTrace();
    // }
    // }
    // }

    /**
     * 返回一个确认类型的dialog
     *
     * @param context
     * @param titleresid
     * @param msgresid
     * @param msgCancelId
     * @return
     */
    public static Dialog showRemindDialog(Context context, int titleresid, int msgresid, int msgCancelId) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle(context.getString(titleresid)).setMessage(context.getString(msgresid))
                .setNegativeButton(msgCancelId, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
        dialog.setOnKeyListener(new android.content.DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    dialog.dismiss();
                }
                return false;
            }

        });
        try {
            dialog.show();
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
        return dialog;
    }

    /**
     * 返回一个确认类型的dialog
     *
     * @param context
     * @param titleresid
     * @param msgresid
     * @param msgConfirmId
     * @param msgCancelId
     * @param listener
     * @return
     */
    public static Dialog showConfirmDialog(Context context, int titleresid, int msgresid, int msgConfirmId, int msgCancelId,
                                           DialogInterface.OnClickListener listener) {
        return showConfirmDialog(context, context.getString(titleresid), context.getString(msgresid), msgConfirmId, msgCancelId, listener);
    }

    /**
     * 返回一个确认类型的dialog
     *
     * @param context
     * @param title
     * @param msg
     * @param msgConfirmId
     * @param msgCancelId
     * @param listener
     * @return
     */
    public static Dialog showConfirmDialog(Context context, String title, String msg, int msgConfirmId, int msgCancelId,
                                           DialogInterface.OnClickListener listener) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle(title).setMessage(msg).setNegativeButton(msgCancelId, listener)
                .setPositiveButton(msgConfirmId, listener).create();

        dialog.setCancelable(true);
        try {
            dialog.show();
        } catch (Throwable tr) {
            tr.printStackTrace();
        }
        return dialog;
    }

}
