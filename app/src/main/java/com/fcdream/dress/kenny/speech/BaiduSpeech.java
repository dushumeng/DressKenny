package com.fcdream.dress.kenny.speech;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import com.baidu.speech.VoiceRecognitionService;

/**
 * Created by shmdu on 2017/8/28.
 */

public class BaiduSpeech extends BaseSpeech implements RecognitionListener {

    private SpeechRecognizer speechRecognizer;


    public BaiduSpeech(SpeechListener speechListener) {
        super(speechListener);
    }

    @Override
    public void init(Context context) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context, new ComponentName(context, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    public void start() {
        speechRecognizer.startListening(createParamIntent());
    }

    @Override
    public void destroy() {
        if (speechRecognizer != null) {
            speechRecognizer.destroy();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }

    private Intent createParamIntent() {
        Intent intent = new Intent();
        return intent;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
