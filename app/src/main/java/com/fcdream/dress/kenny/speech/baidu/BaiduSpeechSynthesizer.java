package com.fcdream.dress.kenny.speech.baidu;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizeBag;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.MyFilePathEntity;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.speech.BaseSpeechSynthesizer;
import com.fcdream.dress.kenny.speech.SpeechSynthesizerError;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/4
 * description：
 */
public class BaiduSpeechSynthesizer extends BaseSpeechSynthesizer implements SpeechSynthesizerListener {

    private static final String TAG = BaiduSpeechSynthesizer.class.getSimpleName();

    private static final String SAMPLE_DIR_NAME = "baiduTTS";

    private static BaiduSpeechSynthesizer instance;

    private String mSampleDirPath;

    private SpeechSynthesizer mSpeechSynthesizer;

    private BaiduSpeechSynthesizer() {
        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
    }

    public synchronized static BaiduSpeechSynthesizer getInstance() {
        if (instance == null) {
            instance = new BaiduSpeechSynthesizer();
        }
        return instance;
    }

    @Override
    public void init(Context context) {
        mSpeechSynthesizer.release();
        mSpeechSynthesizer.setContext(context);
        mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        mSpeechSynthesizer.setApiKey(context.getString(R.string.baidu_api_key), context.getString(R.string.baidu_secret_key));
        mSpeechSynthesizer.setAppId(context.getString(R.string.baidu_app_id));
        // 文本模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE
                , MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_TEXT_MODEL_NAME));
        // 声学模型文件路径 (离线引擎使用)
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE
                , MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_SPEECH_FEMALE_MODEL_NAME));
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        mSpeechSynthesizer.loadEnglishModel(MyFilePathEntity.BAIDU_ENGLISH_TEXT_MODEL_NAME
                , MyFilePathEntity.BAIDU_ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        printEngineInfo();
    }

    @Override
    public void speak(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        mSpeechSynthesizer.speak(new SpeechSynthesizeBag());
        int speak = mSpeechSynthesizer.speak(text);
        if (speak < 0) {
            MyLog.e(TAG, "speak:" + text + ";error=" + speak);
        }
    }

    @Override
    public void destroy() {
        mSpeechSynthesizer.release();
    }

    @Override
    public void pause() {
        mSpeechSynthesizer.pause();
    }

    @Override
    public void stop() {
        mSpeechSynthesizer.stop();
    }

    @Override
    public void onSynthesizeStart(String s) {
        if (listener != null) {
            listener.onSynthesizeStart(s);
        }
        MyLog.d(TAG, "onSynthesizeStart:" + s);
    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
        if (listener != null) {
            listener.onSynthesizeDataArrived(s, bytes, i);
        }
        MyLog.d(TAG, "onSynthesizeDataArrived:" + s + "," + i);
    }

    @Override
    public void onSynthesizeFinish(String s) {
        if (listener != null) {
            listener.onSynthesizeFinish(s);
        }
        MyLog.d(TAG, "onSynthesizeFinish:" + s);
    }

    @Override
    public void onSpeechStart(String s) {
        if (listener != null) {
            listener.onSpeechStart(s);
        }
        MyLog.d(TAG, "onSpeechStart:" + s);
    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {
        if (listener != null) {
            listener.onSpeechProgressChanged(s, i);
        }
        MyLog.d(TAG, "onSpeechProgressChanged:" + s + "," + i);
    }

    @Override
    public void onSpeechFinish(String s) {
        if (listener != null) {
            listener.onSpeechFinish(s);
        }
        MyLog.d(TAG, "onSpeechFinish:" + s);
    }

    @Override
    public void onError(String s, SpeechError speechError) {
        if (listener != null) {
            listener.onError(s, new SpeechSynthesizerError(speechError.code, speechError.description));
        }
        MyLog.d(TAG, "onError:" + s + "," + speechError);
    }

    private void printEngineInfo() {
        MyLog.i(TAG, "EngineVersion=" + SynthesizerTool.getEngineVersion());
        MyLog.i(TAG, "EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_TEXT_MODEL_NAME));
        MyLog.i(TAG, "textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_SPEECH_FEMALE_MODEL_NAME));
        MyLog.i(TAG, "speechModelInfo=" + speechModelInfo);
    }
}
