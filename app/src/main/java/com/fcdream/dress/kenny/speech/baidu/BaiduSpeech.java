package com.fcdream.dress.kenny.speech.baidu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.text.TextUtils;

import com.baidu.speech.VoiceRecognitionService;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.speech.BaseSpeech;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by shmdu on 2017/8/28.
 */

public class BaiduSpeech extends BaseSpeech implements RecognitionListener {

    private static final String TAG = BaiduSpeech.class.getSimpleName();

    private SpeechRecognizer speechRecognizer;

    public BaiduSpeech() {
    }

    @Override
    public void init(Context context) {
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context, new ComponentName(context, VoiceRecognitionService.class));
        speechRecognizer.setRecognitionListener(this);
    }

    @Override
    public void start() {
        if (!TextUtils.equals(state, STATUS_END)) {
            return;
        }
        speechRecognizer.startListening(createParamIntent());
        state = STATUS_START;
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
        state = STATUS_END;
        if (speechRecognizer != null) {
            speechRecognizer.cancel();
        }
    }

    @Override
    public boolean isListening() {
        return state != STATUS_END;
    }

    private Intent createParamIntent() {
        Intent intent = new Intent();
        //初始化语音文件
        intent.putExtra(Constant.EXTRA_SOUND_START, R.raw.bdspeech_recognition_start);
        intent.putExtra(Constant.EXTRA_SOUND_END, R.raw.bdspeech_speech_end);
        intent.putExtra(Constant.EXTRA_SOUND_SUCCESS, R.raw.bdspeech_recognition_success);
        intent.putExtra(Constant.EXTRA_SOUND_ERROR, R.raw.bdspeech_recognition_error);
        intent.putExtra(Constant.EXTRA_SOUND_CANCEL, R.raw.bdspeech_recognition_cancel);
        //保存识别过程产生的录音文件
        intent.putExtra(Constant.EXTRA_OUTFILE, "sdcard/outfile.pcm");
        intent.putExtra(Constant.EXTRA_GRAMMAR, "assets:///baidu_speech_grammar.bsg");
        intent.putExtra(Constant.EXTRA_SAMPLE, 16000);
        intent.putExtra(Constant.EXTRA_LANGUAGE, "cmn-Hans-CN");
        intent.putExtra(Constant.EXTRA_NLU, "enable");
        intent.putExtra(Constant.EXTRA_VAD, "search");
        intent.putExtra(Constant.EXTRA_PROP, "20000");
        intent.putExtra(Constant.EXTRA_OFFLINE_LM_RES_FILE_PATH, "/sdcard/easr/s_2_InputMethod");
        //intent.putExtra("license-file-path", "assets:///temp_license_2017-08-29");

        return intent;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        MyLog.i(TAG, "准备就绪");
    }

    @Override
    public void onBeginningOfSpeech() {
        state = STATUS_LISTENING;
        MyLog.i(TAG, "开始说话处理");
    }

    @Override
    public void onRmsChanged(float v) {
        MyLog.i(TAG, "音量变化处理");
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        MyLog.i(TAG, "录音数据传出处理");
    }

    @Override
    public void onEndOfSpeech() {
        MyLog.i(TAG, "说话结束处理");
    }

    @Override
    public void onError(int error) {
        StringBuilder sb = new StringBuilder();
        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                sb.append("音频问题");
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                sb.append("没有语音输入");
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                sb.append("其它客户端错误");
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                sb.append("权限不足");
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                sb.append("网络问题");
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                sb.append("没有匹配的识别结果");
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                sb.append("引擎忙");
                break;
            case SpeechRecognizer.ERROR_SERVER:
                sb.append("服务端错误");
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                sb.append("连接超时");
                break;
        }
        sb.append(":" + error);
        MyLog.i(TAG, sb.toString());
        if (speechListener != null) {
            speechListener.onListenError(sb.toString());
        }

        stop();
    }

    @Override
    public void onResults(Bundle results) {
        ArrayList<String> nbest = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        StringBuffer resultSb = new StringBuffer("");
        for (int i = 0; nbest != null && i < nbest.size(); i++) {
            resultSb.append(",").append(nbest.get(i));
        }
        String result = resultSb.length() > 0 ? resultSb.substring(1) : resultSb.toString();
        MyLog.i(TAG, "识别成功：" + Arrays.toString(nbest.toArray(new String[nbest.size()])));
        String json_res = results.getString("origin_result");
        MyLog.i(TAG, json_res);

        if (speechListener != null) {
            speechListener.onListenEnd(result);
        }

        stop();
    }

    @Override
    public void onPartialResults(Bundle bundle) {

    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        MyLog.i(TAG, "事件回掉处理");
    }
}
