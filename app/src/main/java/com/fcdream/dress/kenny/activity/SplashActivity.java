package com.fcdream.dress.kenny.activity;

import android.content.Intent;
import android.util.Log;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.ioc.BindLayout;

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
