package com.fcdream.dress.kenny.activity;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseFragmentActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.activity.main.BaseMainFragmentIface;
import com.fcdream.dress.kenny.activity.main.BaseMainPageFragment;
import com.fcdream.dress.kenny.activity.main.MainListFragment;
import com.fcdream.dress.kenny.activity.main.RobotFragment;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.player.XulMediaPlayer;
import com.fcdream.dress.kenny.player.impl.XulAndroidPlayer;
import com.fcdream.dress.kenny.speech.BaseSpeech;
import com.fcdream.dress.kenny.speech.SpeechFactory;
import com.fcdream.dress.kenny.utils.GeneralUtils;
import com.fcdream.dress.kenny.utils.MessageUtils;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layout = R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity implements BaseMainFragmentIface {

    private static final String TAG = MainActivity.class.getSimpleName();

    /* 双击退出时间间隔 */
    private static final int DEFAULT_TIME_LEN = 2 * 1000;
    // 退出时间间隔
    private long exitTime = 0;

    private Map<String, BaseMainPageFragment> fragmentMap = new HashMap<>();

    private XulMediaPlayer mediaPlayer;
    private BaseSpeech baseSpeech;

    private BaseMainPageFragment showFragment;

    @Override
    protected void initView() {
        mediaPlayer = new XulAndroidPlayer(true);
        mediaPlayer.init(this);
        baseSpeech = SpeechFactory.createSpeech(SpeechFactory.TYPE_BAIDU);
        baseSpeech.init(this);
        RobotFragment robotFragment = (RobotFragment) getSupportFragmentManager().findFragmentById(R.id.layout_robot);
        robotFragment.setFragmentIface(this);
        MainListFragment mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentById(R.id.layout_main_list);
        mainListFragment.setFragmentIface(this);

        fragmentMap.put(robotFragment.getFragmentType(), robotFragment);
        fragmentMap.put(mainListFragment.getFragmentType(), mainListFragment);
        //测试
        show(TYPE_ROBOT, null);
    }

    @Override
    protected void initData() {

    }

    private void show(String type, Object param) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (String key : fragmentMap.keySet()) {
            BaseMainPageFragment baseFragment = fragmentMap.get(key);
            if (TextUtils.equals(type, key)) {
                showFragment = baseFragment;
                transaction.show(baseFragment);
            } else {
                transaction.hide(baseFragment);
            }
        }
        transaction.commit();
        if (showFragment != null) {
            showFragment.onShow(param);
        }
    }

    @Override
    public XulMediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public BaseSpeech getSpeech() {
        return baseSpeech;
    }

    @Override
    public void handleSpeech(String info) {
        if (TextUtils.isEmpty(info)) {
            return;
        }
        show(TYPE_MAIN_LIST, info);
    }

    @Override
    public void dealFragmentBack(BaseMainPageFragment fragment) {
        if (fragment != null && TextUtils.equals(fragment.getFragmentType(), TYPE_MAIN_LIST)) {
            show(TYPE_ROBOT, null);
        }
    }

    @Override
    protected void onPause() {
        if (baseSpeech != null) {
            baseSpeech.pause();
        }
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (baseSpeech != null) {
            baseSpeech.stop();
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (baseSpeech != null) {
            baseSpeech.destroy();
            baseSpeech = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.destroy();
            mediaPlayer = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (GeneralUtils.isHomeApp(App.getAppInstance())) {
                MyLog.d(TAG, "app is Launcher,skip backPressed");
            } else if (System.currentTimeMillis() - exitTime < DEFAULT_TIME_LEN) {
                finish();
            } else {
                MessageUtils.showToast(this, "再按一次退出");
                exitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (showFragment != null && showFragment.handleTouchEvent(event)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }
}
