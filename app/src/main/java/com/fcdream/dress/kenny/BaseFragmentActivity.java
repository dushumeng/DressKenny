package com.fcdream.dress.kenny;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;

/**
 * Created by shmdu on 2017/8/31.
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

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
