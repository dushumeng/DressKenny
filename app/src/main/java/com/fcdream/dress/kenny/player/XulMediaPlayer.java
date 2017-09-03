package com.fcdream.dress.kenny.player;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by hy on 2015/10/30.
 */
public abstract class XulMediaPlayer {

    public abstract View init(Context ctx, ViewGroup parent);

    public abstract void init(Context ctx);

    public boolean setScaleMode(int mode) {
        return false;
    }

    public abstract long getDuration();

    public abstract long getCurrentPosition();

    public abstract boolean seekTo(long pos);

    public abstract boolean stop();

    public abstract boolean pause();

    public abstract boolean play();

    public abstract boolean open(String url);

    public abstract void destroy();

    public abstract void updateProgress();

    public boolean sendCommand(String cmd, Bundle extInfo) {
        return false;
    }

    public abstract void setEventListener(XulMediaPlayerEvents listener);

    public abstract boolean isPlaying();

    public abstract void setTag(String tag);

    public abstract String getTag();

    public interface XulMediaPlayerEvents {
        boolean onError(XulMediaPlayer xmp, int code, String msg);

        boolean onPrepared(XulMediaPlayer xmp);

        boolean onSeekComplete(XulMediaPlayer xmp, long pos);

        boolean onComplete(XulMediaPlayer xmp);

        boolean onBuffering(XulMediaPlayer xmp, boolean buffering, float percentage);

        boolean onProgress(XulMediaPlayer xmp, long pos);

        void onDestroy(XulMediaPlayer xmp);

        void onVideoSizeChanged(XulMediaPlayer player, int mVideoWidth, int mVideoHeight);
    }
}
