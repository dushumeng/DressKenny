package com.fcdream.dress.kenny.activity;

import android.content.Intent;
import android.util.Log;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.bo.MyFilePathEntity;
import com.fcdream.dress.kenny.helper.MyExecutors;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.utils.FileUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by shmdu on 2017/8/30.
 */
@BindLayout(layout = R.layout.activity_splash)
public class SplashActivity extends BaseActivity {


    private CountDownLatch latch = new CountDownLatch(2);

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        MyExecutors.getInstance().exec(() -> {
            initSpeechSynthesizer();
            latch.countDown();
        });
        App.postDelayToMainLooper(() -> latch.countDown(), 3 * 1000);
        waitCompleted();
    }


    private void waitCompleted() {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                latch.await();
            } catch (InterruptedException e) {
                Log.w(TAG, "Wait splash video play completed failed!", e);
            }
            App.postToMainLooper(() -> {
                startMainPage();
            });
        });
    }

    private void startMainPage() {
        startActivity(new Intent(this, MainActivity.class));

        finish();
    }

    private void initSpeechSynthesizer(){
        MyFilePathEntity.getInstance().init();
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_SPEECH_FEMALE_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_SPEECH_FEMALE_MODEL_NAME));
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_SPEECH_MALE_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_SPEECH_MALE_MODEL_NAME));
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_TEXT_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_TEXT_MODEL_NAME));
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_ENGLISH_SPEECH_FEMALE_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_ENGLISH_SPEECH_FEMALE_MODEL_NAME));
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_ENGLISH_SPEECH_MALE_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_ENGLISH_SPEECH_MALE_MODEL_NAME));
        FileUtils.copyFromAssetsToSdcard(false, MyFilePathEntity.BAIDU_ENGLISH_TEXT_MODEL_NAME, MyFilePathEntity.getInstance().getSpeechModelPath(MyFilePathEntity.BAIDU_ENGLISH_TEXT_MODEL_NAME));
    }
}
