package com.fcdream.dress.kenny;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;

/**
 * Created by shmdu on 2017/8/31.
 */

public abstract class BaseFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
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
