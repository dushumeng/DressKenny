package com.fcdream.dress.kenny.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by shmdu on 2017/10/23.
 */

public class AndroidUtils {

    /**
     * 关闭软键盘
     *
     * @param context
     * @param editText
     */
    public static void hideSoftInput(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }

    /**
     * 打开软键盘
     *
     * @param context
     * @param editText
     */
    public static void showSoftInput(Context context, EditText editText) {
        if (editText != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, 0);
        }
    }
}
