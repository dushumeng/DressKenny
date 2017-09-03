package com.fcdream.dress.kenny.bus;

/**
 * Created by shmdu on 2017/9/3.
 */

public interface MyCallback<T> {

    void callback(boolean success, T object);
}
