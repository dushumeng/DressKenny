package com.fcdream.dress.kenny.activity.main;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.ioc.BindView;
import com.fcdream.dress.kenny.player.XulMediaPlayer;

/**
 * Created by shmdu on 2017/8/31.
 */

@BindLayout(layout = R.layout.fragment_robot)
public class RobotFragment extends BaseMainPageFragment {

    private static final String TAG = RobotFragment.class.getSimpleName();

    @BindView(id = R.id.robot_tip)
    private TextView robotTipText;

    @BindView(id = R.id.robot_mouth)
    private ImageView robotMouthImage;

    @BindView(id = R.id.robot_mic, click = true, clickEvent = "onRobotMicClick")
    private ImageView robotMicImage;

    @BindView(id = R.id.robot_mic_line)
    private ImageView robotMicLineImage;

    private AnimationDrawable robotMouthAnim;

    private Animation robotMicLineAnimation;

    private String vodTipPath = "android.resource://" + App.getAppInstance().getApplicationContext().getPackageName() + "/" + R.raw.robot_tip_voice;

    private boolean firstSpeech = true;

    @Override
    protected void initView(Activity activity, View sourceView) {
        robotMouthAnim = (AnimationDrawable) activity.getResources().getDrawable(R.drawable.anim_mouth);
        robotTipText.setEllipsize(null);

        int y = (int) activity.getResources().getDimension(R.dimen.robot_mic_bg_height);
        y = y * 3 / 4;
        robotMicLineAnimation = new TranslateAnimation(0, 0, -y, y);
        robotMicLineAnimation.setDuration(3000);
        robotMicLineAnimation.setRepeatCount(Animation.INFINITE);
        robotMicLineImage.setAnimation(robotMicLineAnimation);
        robotMicLineAnimation.cancel();
    }

    @Override
    protected void initData(Activity activity) {

    }

    @Override
    public void onStop() {
        super.onStop();
        dealEndTips();
        dealEndSpeech();
    }

    @Override
    public String getPageName() {
        return "page_main_robot";
    }

    public void onRobotMicClick(View view) {
        dealStartTips();
    }

    @Override
    public String getFragmentType() {
        return BaseMainFragmentIface.TYPE_ROBOT;
    }

    @Override
    public void onListenError(String errorInfo) {
        super.onListenError(errorInfo);
        dealEndSpeech();
    }

    @Override
    public void onListenEnd(String info) {
        super.onListenEnd(info);
        dealEndSpeech();
        if (isFragmentIfaceValid()) {
            ifaceReference.get().handleSpeech(info);
        }
    }

    private void dealStartTips() {
        if (!firstSpeech) {
            dealStartSpeech();
            return;
        }
        firstSpeech = false;
        dealSpeakAnimation(true);
        robotTipText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        if (isFragmentIfaceValid()) {
            XulMediaPlayer mediaPlayer = ifaceReference.get().getMediaPlayer();
            mediaPlayer.stop();
            mediaPlayer.open(vodTipPath);
            mediaPlayer.play();
        }
    }

    private void dealEndTips() {
        dealSpeakAnimation(false);
        robotTipText.setEllipsize(null);
    }

    private void dealStartSpeech() {
        dealEndTips();
        if (isFragmentIfaceValid()) {
            ifaceReference.get().getSpeech().start();
        }
        robotMicLineAnimation.start();
    }

    private void dealEndSpeech() {
        if (isFragmentIfaceValid()) {
            ifaceReference.get().getSpeech().stop();
        }
        robotMicLineAnimation.cancel();
    }

    @Override
    public boolean onError(XulMediaPlayer xmp, int code, String msg) {
        dealStartSpeech();
        return true;
    }

    @Override
    public boolean onComplete(XulMediaPlayer xmp) {
        dealStartSpeech();
        if (xmp != null) {
            xmp.stop();
        }
        return true;
    }

    private void dealSpeakAnimation(boolean speak) {
        if (speak) {
            robotMouthImage.setImageDrawable(robotMouthAnim);
            robotMouthAnim.start();
        } else {
            robotMouthImage.setImageResource(R.drawable.mouth1);
            robotMouthAnim.stop();
        }
    }
}
