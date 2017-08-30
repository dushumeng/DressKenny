package com.fcdream.dress.kenny;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created by shmdu on 2017/8/30.
 */

public class SplashActivity extends BaseActivity {


    private CountDownLatch latch = new CountDownLatch(2);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        App.postDelayToMainLooper(() -> latch.countDown(), 3 * 1000);
        App.postDelayToMainLooper(() -> latch.countDown(), 2 * 1000);
        waitCompleted();
    }


    private void waitCompleted() {
        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    Log.w(TAG, "Wait splash video play completed failed!", e);
                }
                App.postToMainLooper(() -> {
                    startMainPage();
                });
            }
        });
    }

    private void startMainPage() {
        startActivity(new Intent(this, MainActivity.class));

        finish();
    }

}
