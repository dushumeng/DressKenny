package com.fcdream.dress.kenny.speech;

import android.content.Context;

/**
 * Created by shmdu on 2017/8/28.
 */

public abstract class BaseSpeech {

    protected SpeechListener speechListener;

    public BaseSpeech(SpeechListener speechListener) {
        this.speechListener = speechListener;
    }

    public abstract void init(Context context);

    public abstract void start();

    public abstract void destroy();

    public abstract void pause();

    public abstract void stop();

    public interface SpeechListener {
        void onListenStart();

        void onListening();

        void onListenError(String errorInfo);

        void onListenEnd(String info);
    }
}
