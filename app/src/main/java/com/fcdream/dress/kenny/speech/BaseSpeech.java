package com.fcdream.dress.kenny.speech;

import android.content.Context;

/**
 * Created by shmdu on 2017/8/28.
 */

public abstract class BaseSpeech {

    public static final String STATUS_START = "status_start";
    public static final String STATUS_LISTENING = "status_listening";
    public static final String STATUS_END = "status_end";

    protected SpeechListener speechListener;

    protected String state = STATUS_END;

    public BaseSpeech() {
    }

    public void setSpeechListener(SpeechListener speechListener) {
        this.speechListener = speechListener;
    }

    public abstract void init(Context context);

    public abstract void start();

    public abstract void destroy();

    public abstract void pause();

    public abstract void stop();

    public abstract boolean isListening();

    public interface SpeechListener {
        void onListenStart();

        void onListening();

        void onListenError(String errorInfo);

        void onListenEnd(String info);
    }
}
