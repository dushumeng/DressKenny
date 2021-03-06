package com.fcdream.dress.kenny.helper;

import com.fcdream.dress.kenny.log.MyLog;

import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Copyright (c) 2017, 北京视达科科技有限责任公司 All rights reserved.
 * author：shumeng.du
 * date：2017/9/6
 * description：
 */
public class MyTime {

    private static final String TAG = MyTime.class.getSimpleName();
    private static long _xulTimeDelta = System.currentTimeMillis() - MyTime.timestamp();
    private static long _meanDifference = Integer.MAX_VALUE;
    private static int _tzOffset = 0;
    private static TimeZone _tzObject = null;

    public static final long ONE_HOUR_SEC = 60 * 60;
    public static final long ONE_HOUR_MS = ONE_HOUR_SEC * 1000;
    public static final long ONE_DAY_SEC = ONE_HOUR_SEC * 24;
    public static final long ONE_DAY_MS = ONE_HOUR_MS * 24;

    private static synchronized boolean doSyncTime(long serverTime, long timestamp, long delay) {
        MyLog.d(TAG, "doSyncTime begin serverTime:" + serverTime + ";timestamp:" + timestamp + ";delay:" + delay);
        delay /= 1.7;
        long serverTimeDelta = serverTime - timestamp + delay;
        if (delay >= _meanDifference || _meanDifference <= 0) {
            long diff = Math.abs(serverTimeDelta - _xulTimeDelta);
            if (diff < (_meanDifference + delay) * 3 / 4 || delay > diff) {
                MyLog.d(TAG, "doSyncTime error delta:" + _xulTimeDelta + " mean:" + _meanDifference);
                return false;
            }
            _meanDifference = diff * delay / (_meanDifference + delay);
        }
        double ratio = (double) delay / _meanDifference;
        _xulTimeDelta = (long) (serverTimeDelta + (_xulTimeDelta - serverTimeDelta) * ratio);
        _meanDifference = (long) (delay + (_meanDifference - delay) * ratio);
        MyLog.d(TAG, "doSyncTime delta:" + _xulTimeDelta + " mean:" + _meanDifference);
        return _meanDifference < 300;
    }

    public static boolean syncTime(long serverTime, long delay) {
        return doSyncTime(serverTime, MyTime.timestamp(), delay);
    }

    public static long timestampToTime(long timestamp) {
        return _xulTimeDelta + timestamp;
    }

    public static long timeToTimestamp(long time) {
        return time - _xulTimeDelta;
    }

    public static void setTimeZoneOffset(int tzOffset) {
        _tzOffset = tzOffset;
        _tzObject = null;
    }

    public static int getTimeZoneOffset() {
        return _tzOffset;
    }

    public static long getTimeZoneOffsetMS() {
        return _tzOffset * ONE_HOUR_MS;
    }

    public static TimeZone getTimeZone() {
        if (_tzObject == null) {
            _tzObject = new SimpleTimeZone((int) (_tzOffset * ONE_HOUR_MS), "");
        }
        return _tzObject;
    }

    public static boolean isSameDate(long t1, long t2) {
        long tzOffsetMS = _tzOffset * ONE_HOUR_MS;
        long date1 = (t1 + tzOffsetMS) / ONE_DAY_MS;
        long date2 = (t2 + tzOffsetMS) / ONE_DAY_MS;
        return date1 == date2;
    }

    public static long currentTimeMillis() {
        return MyTime.timestamp() + _xulTimeDelta;
    }

    public static long timestamp() {
        return System.nanoTime() / 1000000;
    }

    public static long timestamp_us() {
        return System.nanoTime() / 1000;
    }
}
