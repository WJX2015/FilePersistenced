package com.example.lenovo_g50_70.filepersistenced;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lenovo-G50-70 on 2017/4/14.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setupLeakCanary();
    }

    /**
     * 配置LeakCanary
     */
    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
