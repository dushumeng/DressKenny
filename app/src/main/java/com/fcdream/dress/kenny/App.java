package com.fcdream.dress.kenny;

import android.app.Application;
import android.os.Handler;

/**
 * Created by shmdu on 2017/8/28.
 */

public class App extends Application {

    private static App instance;

    private static Handler _appMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        _appMainHandler = new Handler(getMainLooper());
    }

    public static App getInstance() {
        return instance;
    }

    public static void postToMainLooper(Runnable runnable) {
        if (_appMainHandler == null) {
            return;
        }
        _appMainHandler.post(runnable);
    }

    public static void postDelayToMainLooper(Runnable runnable, long ms) {
        if (_appMainHandler == null) {
            return;
        }
        _appMainHandler.postDelayed(runnable, ms);
    }
}
