package com.fcdream.dress.kenny.cache;

import android.content.Context;

/**
 * Created by shmdu on 2017/9/2.
 */

public interface FCDreamCache<T> {

    public void put(Context context, String key, T content, long aging);

    public T get(Context context, String key);

    public void removeOutDate(Context context);

    public void removeByKey(Context context, String key);

    public void clear(Context context);
}
