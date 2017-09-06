package com.fcdream.dress.kenny;

import android.app.Application;
import android.os.Handler;

import com.fcdream.dress.kenny.bo.CommonEntity;
import com.fcdream.dress.kenny.message.XulMessageCenter;

/**
 * Created by shmdu on 2017/8/28.
 */

public class App extends Application {

    private static App appInstance;

    private static Handler _appMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        _appMainHandler = new Handler(getMainLooper());

        XulMessageCenter.buildMessage()
                .setTag(CommonEntity.MESSAGE_ONE_SECOND)
                .setInterval(1000)
                .setRepeat(Integer.MAX_VALUE)
                .post();
    }

    public static App getAppInstance() {
        return appInstance;
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
