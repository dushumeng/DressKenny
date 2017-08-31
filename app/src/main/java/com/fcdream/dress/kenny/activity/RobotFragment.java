package com.fcdream.dress.kenny.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseFragment;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_robot)
public class RobotFragment extends BaseFragment {

    @BindView(id = R.id.robot_tip)
    private TextView robotTipText;

    @BindView(id = R.id.robot_mouth)
    private ImageView robotMouthImage;

    @BindView(id = R.id.robot_mic, click = true, clickEvent = "onRobotMicClick")
    private ImageView robotMicImage;

    private AnimationDrawable robotMouthAnim;

    @Override
    protected void initView(Activity activity, View sourceView) {
        robotMouthAnim = (AnimationDrawable) robotMouthImage.getDrawable();
        robotMouthAnim.start();
        App.postDelayToMainLooper(new Runnable() {
            @Override
            public void run() {
                robotMouthAnim.stop();
                robotTipText.setEllipsize(null);
            }
        }, 10 * 1000);
        App.postDelayToMainLooper(new Runnable() {
            @Override
            public void run() {
                robotMouthAnim.start();
                robotTipText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            }
        }, 20 * 1000);
    }

    @Override
    protected void initData(Activity activity) {

    }

    public void onRobotMicClick(View view) {

    }
}
