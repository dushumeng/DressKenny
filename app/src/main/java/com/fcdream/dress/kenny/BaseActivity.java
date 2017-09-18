package com.fcdream.dress.kenny;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;
import com.fcdream.dress.kenny.message.XulMessageCenter;

/**
 * Created by shmdu on 2017/8/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected final String TAG = this.getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isSupportActionBar()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getSupportActionBar().hide();
        }


        XulMessageCenter.getDefault().register(this);

        setContentView(AnnotateUtil.getBindLayoutResId(this));

        AnnotateUtil.initBindView(this);

        initParam();
        initView();
        initData();
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

    public boolean isSupportActionBar() {
        return false;
    }

    @Override
    public void onClick(View v) {
        AnnotateUtil.invokeMehode(v, this);
    }
}
