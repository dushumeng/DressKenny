package com.fcdream.dress.kenny.speech;

import android.content.Context;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/4
 * description：
 */
public abstract class BaseSpeechSynthesizer {

    protected SpeechSynthesizerListener listener;

    public BaseSpeechSynthesizer() {

    }

    public void setSpeechSynthesizerListener(SpeechSynthesizerListener speechSynthesizerListener) {
        this.listener = speechSynthesizerListener;
    }

    public abstract void init(Context context);

    public abstract void speak(String text);

    public abstract void destroy();

    public abstract void pause();

    public abstract void stop();

    public interface SpeechSynthesizerListener {

        void onSynthesizeStart(String s);

        void onSynthesizeDataArrived(String s, byte[] bytes, int i);

        void onSynthesizeFinish(String s);

        void onSpeechStart(String s);

        void onSpeechProgressChanged(String s, int i);

        void onSpeechFinish(String s);

        void onError(String s, SpeechSynthesizerError speechError);
    }

}
