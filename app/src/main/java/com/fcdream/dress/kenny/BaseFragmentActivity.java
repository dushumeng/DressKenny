package com.fcdream.dress.kenny;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.baidu.mobstat.StatService;
import com.fcdream.dress.kenny.ioc.AnnotateUtil;
import com.fcdream.dress.kenny.message.XulMessageCenter;

/**
 * Created by shmdu on 2017/8/31.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        XulMessageCenter.getDefault().register(this);

        if (!isSupportActionBar()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
        }
        if (isHideBottomUIMenu()) {
            hideBottomUIMenu();
        }
        setContentView(AnnotateUtil.getBindLayoutResId(this));

        AnnotateUtil.initBindView(this);

        initParam();
        initView();
        initData();
    }

    protected boolean isSupportActionBar() {
        return false;
    }

    protected abstract void initParam();

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    @Override
    protected void onDestroy() {
        XulMessageCenter.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    protected boolean isHideBottomUIMenu() {
        return true;
    }

    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
