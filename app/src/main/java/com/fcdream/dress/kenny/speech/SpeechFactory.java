package com.fcdream.dress.kenny.speech;

import android.text.TextUtils;

import com.fcdream.dress.kenny.speech.baidu.BaiduSpeech;
import com.fcdream.dress.kenny.speech.baidu.BaiduSpeechSynthesizer;

/**
 * Created by shmdu on 2017/8/28.
 */

public class SpeechFactory {

    public static final String TYPE_BAIDU = "type_baidu";
    public static final String TYPE_IFLY = "type_ifly";

    public static BaseSpeech createSpeech(String type) {
        if (TextUtils.equals(TYPE_BAIDU, type)) {
            return new BaiduSpeech();
        }
        return null;
    }

    public static BaseSpeechSynthesizer createSpeechSynthesizer(String type) {
        if (TextUtils.equals(TYPE_BAIDU, type)) {
            return BaiduSpeechSynthesizer.getInstance();
        }
        return null;
    }
}
