package com.banglaguru.app.util;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutors {

    private static final AppExecutors instance = new AppExecutors();

    private final Executor diskIo = Executors.newSingleThreadExecutor();
    private final Executor mainThread = new MainThreadExecutor();

    public static AppExecutors getInstance() {
        return instance;
    }

    public Executor diskIO() {
        return diskIo;
    }

    public Executor mainThread() {
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mainHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            mainHandler.post(command);
        }
    }
}
