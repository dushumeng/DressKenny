package com.fcdream.dress.kenny.bo;

import android.os.Environment;

import com.fcdream.dress.kenny.utils.FileUtils;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/4
 * description：
 */
public class MyFilePathEntity {

    private String baiduSpeechPath;

    public static final String BAIDU_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    public static final String BAIDU_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    public static final String BAIDU_TEXT_MODEL_NAME = "bd_etts_text.dat";

    public static final String BAIDU_ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    public static final String BAIDU_ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    public static final String BAIDU_ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";

    private static final MyFilePathEntity instance = new MyFilePathEntity();

    private MyFilePathEntity() {
        String sdcardPath = Environment.getExternalStorageDirectory().toString();
        baiduSpeechPath = sdcardPath + "/baiduTTS";
    }

    public String getSpeechModelPath(String modelName) {
        return baiduSpeechPath + "/" + modelName;
    }

    public void init() {
        FileUtils.makeDir(baiduSpeechPath);
    }

    public static MyFilePathEntity getInstance() {
        return instance;
    }
}
