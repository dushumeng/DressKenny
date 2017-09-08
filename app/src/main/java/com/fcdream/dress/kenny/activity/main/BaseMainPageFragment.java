package com.fcdream.dress.kenny.activity.main;

import android.view.MotionEvent;

import com.fcdream.dress.kenny.BaseFragment;
import com.fcdream.dress.kenny.log.MyLog;
import com.fcdream.dress.kenny.player.XulMediaPlayer;
import com.fcdream.dress.kenny.speech.BaseSpeech;
import com.fcdream.dress.kenny.utils.MessageUtils;

import java.lang.ref.WeakReference;

/**
 * Created by shmdu on 2017/9/3.
 */

public abstract class BaseMainPageFragment extends BaseFragment implements BaseSpeech.SpeechListener
        , XulMediaPlayer.XulMediaPlayerEvents {

    protected final String TAG = this.getClass().getSimpleName();

    protected WeakReference<BaseMainFragmentIface> ifaceReference;

    public abstract String getFragmentType();

    public void setFragmentIface(BaseMainFragmentIface iface) {
        if (isFragmentIfaceValid()) {
            ifaceReference.clear();
            ifaceReference = null;
        }
        ifaceReference = new WeakReference<>(iface);
    }

    protected boolean isFragmentIfaceValid() {
        return ifaceReference != null && ifaceReference.get() != null;
    }

    @Override
    public void onListenStart() {
        MyLog.i(TAG, "---speech onListenStart---");
    }

    @Override
    public void onListening() {
        MyLog.i(TAG, "---speech onListening---");
    }

    @Override
    public void onListenError(String errorInfo) {
        MyLog.i(TAG, "---speech onListenError---" + errorInfo);
        MessageUtils.showToast(getActivity(), "抱歉，我没听懂你在说什么！！！");
    }

    @Override
    public void onListenEnd(String info) {
        MyLog.i(TAG, "---speech onListenEnd---" + info);
    }

    @Override
    public boolean onError(XulMediaPlayer xmp, int code, String msg) {
        MyLog.i(TAG, "---player onError---");
        return false;
    }

    @Override
    public boolean onPrepared(XulMediaPlayer xmp) {
        MyLog.i(TAG, "---player onPrepared---");
        return false;
    }

    @Override
    public boolean onSeekComplete(XulMediaPlayer xmp, long pos) {
        MyLog.i(TAG, "---player onSeekComplete---");
        return false;
    }

    @Override
    public boolean onComplete(XulMediaPlayer xmp) {
        MyLog.i(TAG, "---player onComplete---");
        return false;
    }

    @Override
    public boolean onBuffering(XulMediaPlayer xmp, boolean buffering, float percentage) {
        MyLog.i(TAG, "---player onBuffering---");
        return false;
    }

    @Override
    public boolean onProgress(XulMediaPlayer xmp, long pos) {
        MyLog.i(TAG, "---player onProgress---");
        return false;
    }

    @Override
    public void onDestroy(XulMediaPlayer xmp) {
        MyLog.i(TAG, "---player onDestroy---");
    }

    @Override
    public void onVideoSizeChanged(XulMediaPlayer player, int mVideoWidth, int mVideoHeight) {
        MyLog.i(TAG, "---player onVideoSizeChanged---" + mVideoWidth + "," + mVideoHeight);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (ifaceReference != null && ifaceReference.get() != null) {
//            ifaceReference.get().getSpeech().setSpeechListener(this);
//            ifaceReference.get().getMediaPlayer().setEventListener(this);
//        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isFragmentIfaceValid()) {
            ifaceReference.get().getSpeech().setSpeechListener(null);
            ifaceReference.get().getMediaPlayer().setEventListener(null);
        }
    }

    public void onShow(Object object) {
        if (ifaceReference != null && ifaceReference.get() != null) {
            ifaceReference.get().getSpeech().setSpeechListener(this);
            ifaceReference.get().getMediaPlayer().setEventListener(this);
        }
    }

    public boolean handleTouchEvent(MotionEvent event) {
        return false;
    }
}
