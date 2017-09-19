package com.fcdream.dress.kenny.log;

import android.util.Log;

/**
 * Created by shmdu on 2017/9/2.
 */

public class MyLog {

    public static int level = Log.DEBUG;

    public static void i(String tag, String log) {
        Log.e(tag, log);
    }

    public static void d(String tag, String log) {
        Log.e(tag, log);
    }

    public static void w(String tag, String log) {
        Log.w(tag, log);
    }

    public static void e(String tag, String log) {
        Log.e(tag, log);
    }


}
