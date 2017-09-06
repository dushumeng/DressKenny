package com.fcdream.dress.kenny;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.message.XulMessageCenter;

/**
 * Created by shmdu on 2017/8/31.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private String TAG_LOG = this.getClass().getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MyLog.d(TAG_LOG, "fragment---onCreateView");
        View sourceView = inflater.inflate(AnnotateUtil.getBindLayoutResId(this), container, false);
        AnnotateUtil.initBindView(this, sourceView);

        final Activity activity = getActivity();
        if (activity != null) {
            initView(activity, sourceView);
        }
        return sourceView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        MyLog.d(TAG_LOG, "fragment---onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        if (activity != null) {
            initData(activity);
        }
    }

    abstract protected void initView(Activity activity, View sourceView);

    abstract protected void initData(Activity activity);

    @Override
    public void onClick(View v) {
        AnnotateUtil.invokeMehode(v, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        MyLog.d(TAG_LOG, "fragment---onCreate");
        super.onCreate(savedInstanceState);

        XulMessageCenter.getDefault().register(this);
    }

    @Override
    public void onResume() {
        MyLog.d(TAG_LOG, "fragment---onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        MyLog.d(TAG_LOG, "fragment---onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        MyLog.d(TAG_LOG, "fragment---onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        MyLog.d(TAG_LOG, "fragment---onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        MyLog.d(TAG_LOG, "fragment---onDestroy");
        XulMessageCenter.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        MyLog.d(TAG_LOG, "fragment---onDetach");
        super.onDetach();
    }
}
