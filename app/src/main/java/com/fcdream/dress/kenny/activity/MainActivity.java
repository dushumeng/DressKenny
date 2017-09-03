package com.fcdream.dress.kenny.activity;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.fcdream.dress.kenny.App;
import com.fcdream.dress.kenny.BaseFragment;
import com.fcdream.dress.kenny.BaseFragmentActivity;
import com.fcdream.dress.kenny.R;
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

    private Map<String, BaseFragment> fragmentMap = new HashMap<>();

    private RobotFragment robotFragment;
    private MainListFragment mainListFragment;

    private XulMediaPlayer mediaPlayer;
    private BaseSpeech baseSpeech;

    @Override
    protected void initView() {
        mediaPlayer = new XulAndroidPlayer(true);
        mediaPlayer.init(this);
        baseSpeech = SpeechFactory.createSpeech(SpeechFactory.TYPE_BAIDU);
        baseSpeech.init(this);
        robotFragment = (RobotFragment) getSupportFragmentManager().findFragmentById(R.id.layout_robot);
        robotFragment.setFragmentIface(this);
        mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentById(R.id.layout_main_list);
        mainListFragment.setFragmentIface(this);

        fragmentMap.put(robotFragment.getFragmentType(), robotFragment);
        fragmentMap.put(mainListFragment.getFragmentType(), mainListFragment);
        show(TYPE_MAIN_LIST);
    }

    @Override
    protected void initData() {

    }

    private void show(String type) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (String key : fragmentMap.keySet()) {
            BaseFragment baseFragment = fragmentMap.get(key);
            if (TextUtils.equals(type, key)) {
                transaction.show(baseFragment);
            } else {
                transaction.hide(baseFragment);
            }
        }
        transaction.commit();
        if (TextUtils.equals(type, TYPE_MAIN_LIST)) {
            mainListFragment.startSearch("红色大衣");
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
        show(TYPE_MAIN_LIST);
        mainListFragment.startSearch(info);
    }

    @Override
    public void dealFragmentBack(BaseMainPageFragment fragment) {
        if (fragment != null && TextUtils.equals(fragment.getFragmentType(), TYPE_MAIN_LIST)) {
            show(TYPE_ROBOT);
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
    public void onBackPressed() {

        if (GeneralUtils.isHomeApp(App.getAppInstance())) {
            MyLog.d(TAG, "app is Launcher,skip backPressed");
            return;
        }

        MessageUtils.showConfirmDialog(this, "确认", "确认退出APP？"
                , MessageUtils.BUTTON_CONFIRM, MessageUtils.BUTTON_CANCEL, (dialogInterface, i) -> {
                    if (i == MessageUtils.BUTTON_CONFIRM) {
                        finish();
                    } else if (i == MessageUtils.BUTTON_CANCEL) {

                    }
                });

//        super.onBackPressed();
    }
}
