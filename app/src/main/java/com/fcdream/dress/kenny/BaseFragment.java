package com.fcdream.dress.kenny;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fcdream.dress.kenny.ioc.AnnotateUtil;

/**
 * Created by shmdu on 2017/8/31.
 */

public abstract class BaseFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
}
