package com.fcdream.dress.kenny.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shmdu on 2017/8/30.
 */

public class MyExecutors {

    private static MyExecutors instance;

    private ExecutorService executors;

    private MyExecutors(){
        executors = Executors.newSingleThreadExecutor();
    }

    public synchronized static MyExecutors getInstance() {
        if (instance == null) {
            instance = new MyExecutors();
        }
        return instance;
    }

    public void exec(Runnable runnable) {
        if (runnable == null) {
            return;
        }
        executors.submit(runnable);
    }
}
