package com.fcdream.dress.kenny;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;

/**
 * Created by shmdu on 2017/8/30.
 */

public abstract class BaseActivity extends Activity {

    protected final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(AnnotateUtil.getBindLayoutResId(this));

        AnnotateUtil.initBindView(this);

        initView();
        initData();
    }

    /**
     * 初始化视图
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
