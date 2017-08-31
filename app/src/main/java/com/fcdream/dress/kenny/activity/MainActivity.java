package com.fcdream.dress.kenny.activity;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.fcdream.dress.kenny.BaseFragment;
import com.fcdream.dress.kenny.BaseFragmentActivity;
import com.fcdream.dress.kenny.R;
import com.fcdream.dress.kenny.ioc.BindLayout;
import com.fcdream.dress.kenny.speech.BaseSpeech;

import java.util.HashMap;
import java.util.Map;

@BindLayout(layout = R.layout.activity_main)
public class MainActivity extends BaseFragmentActivity implements BaseSpeech.SpeechListener {

    private static final String TYPE_ROBOT = "type_robot";
    private static final String TYPE_MAIN_LIST = "type_main_list";

    private Map<String, BaseFragment> fragmentMap = new HashMap<>();

    private RobotFragment robotFragment;
    private MainListFragment mainListFragment;

    @Override
    public void onListenStart() {

    }

    @Override
    public void onListening() {

    }

    @Override
    public void onListenError(String errorInfo) {

    }

    @Override
    public void onListenEnd(String info) {

    }

    @Override
    protected void initView() {
        robotFragment = (RobotFragment) getSupportFragmentManager().findFragmentById(R.id.layout_robot);
        mainListFragment = (MainListFragment) getSupportFragmentManager().findFragmentById(R.id.layout_main_list);

        fragmentMap.put(TYPE_ROBOT, robotFragment);
        fragmentMap.put(TYPE_MAIN_LIST, mainListFragment);
        show(TYPE_ROBOT);
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
    }
}
